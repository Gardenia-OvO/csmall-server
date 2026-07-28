package cn.hqu.csmall.passport.controller;

import cn.hqu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.hqu.csmall.passport.pojo.param.AdminLoginInfoParam;
import cn.hqu.csmall.passport.pojo.param.AdminPasswordUpdateParam;
import cn.hqu.csmall.passport.pojo.param.AdminUpdateParam;
import cn.hqu.csmall.passport.pojo.entity.Admin;
import cn.hqu.csmall.passport.mapper.AdminMapper;
import cn.hqu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.hqu.csmall.passport.security.AdminDetail;
import cn.hqu.csmall.commons.security.LoginPrincipal;
import cn.hqu.csmall.commons.web.JsonResult;
import cn.hqu.csmall.commons.web.ServiceCode;
import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.passport.service.IAdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.Map;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/admin")
@Api(tags = "01. 管理员管理模块")
@RestController
@Validated
public class AdminController {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @ApiOperation("管理员登录")
    @ApiOperationSupport(order = 500)   //
    public JsonResult login(@Valid @RequestBody AdminLoginInfoParam adminLoginInfoParam)  {
        log.debug("开始处理【管理员登录】的请求，参数：{}", adminLoginInfoParam);
        String jwt = adminService.login(adminLoginInfoParam);
        log.debug("处理【管理员登录】的请求结束");
        return JsonResult.ok(jwt);
    }
    @PostMapping("/add-new")
    @ApiOperation("添加管理员")
    @ApiOperationSupport(order = 100)   //
    public JsonResult addNew(@Valid @RequestBody AdminAddNewParam adminAddNewParam)  {
        log.debug("开始处理【添加管理员】的请求，参数：{}",adminAddNewParam);
        adminService.addNew(adminAddNewParam);
        log.debug("处理【添加管理员】的请求结束");
        return JsonResult.created("新增管理员成功");
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('/ams/admin/update')")
    @ApiOperation("修改管理员")
    @ApiOperationSupport(order = 200)
    public JsonResult update(@Valid @RequestBody AdminUpdateParam adminUpdateParam) {
        log.debug("开始处理【修改管理员】的请求，参数：{}", adminUpdateParam);
        adminService.update(adminUpdateParam);
        log.debug("处理【修改管理员】的请求结束");
        return JsonResult.ok();
    }

    @PostMapping("/change-password")
    @ApiOperation("修改管理员密码")
    @PreAuthorize("hasAuthority('/ams/admin/update')")
    @ApiOperationSupport(order = 250)
    public JsonResult changePassword(@Valid @RequestBody AdminPasswordUpdateParam adminPasswordUpdateParam,
                                     @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【修改管理员密码】的请求，参数：{}", adminPasswordUpdateParam);
        adminService.changePassword(adminPasswordUpdateParam);
        log.debug("处理【修改管理员密码】的请求完成！");
        return JsonResult.ok("修改管理员密码成功");
    }


    @PostMapping("/delete")
    @ApiOperation("删除管理员")
    @PreAuthorize("hasAuthority('/ams/admin/delete')")
    @ApiOperationSupport(order = 400)
    @ApiImplicitParam(name = "id", value = "管理员id", required = true, dataType = "long")
    public JsonResult delete(@Range(min = 1, message = "根据id删除管理员失败，请提供合法的id")
                             @RequestParam Long id,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【删除管理员】的请求，id:{}", id);
        adminService.delete(id);
        log.debug("处理【删除管理员】的请求，完成！");
        return JsonResult.ok("删除管理员成功");
    }
    @GetMapping("/list")
    @PreAuthorize(("hasAuthority('/ams/admin/read')"))
    @ApiOperation("查询管理员列表")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult list(
            @Range(min = 1, message = "查询管理员列表失败，请提供正确的页码值！")
            @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiIgnore
            @AuthenticationPrincipal
            AdminDetail user
    ) {
        log.debug("开始处理【查询管理员列表】的请求，参数：{}", pageNum);

        if (user != null) {
            log.debug("当事人用户名:{}", user.getUsername());
            log.debug("当事人的ID：{}", user.getId());
        }

        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }

        PageData<AdminListItemVO> pageData = adminService.list(pageNum);

        return JsonResult.ok(pageData);
    }

    // ========== 个人中心 ==========

    @GetMapping("/me")
    @ApiOperation("获取当前登录用户信息")
    @ApiOperationSupport(order = 590)
    public JsonResult me(@ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        Admin admin = adminMapper.selectById(user.getId());
        Map<String, Object> result = new java.util.HashMap<>();
        result.put("id", admin.getId());
        result.put("username", admin.getUsername());
        result.put("nickname", admin.getNickname());
        result.put("phone", admin.getPhone());
        result.put("email", admin.getEmail());
        result.put("description", admin.getDescription());
        result.put("avatar", admin.getAvatar());
        return JsonResult.ok(result);
    }

    @PostMapping("/update-profile")
    @ApiOperation("修改个人资料")
    @ApiOperationSupport(order = 600)
    public JsonResult updateProfile(@RequestBody Map<String, String> profile,
                                    @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        Admin admin = adminMapper.selectById(user.getId());
        if (profile.containsKey("nickname")) admin.setNickname(profile.get("nickname"));
        if (profile.containsKey("phone")) admin.setPhone(profile.get("phone"));
        if (profile.containsKey("email")) admin.setEmail(profile.get("email"));
        if (profile.containsKey("description")) admin.setDescription(profile.get("description"));
        admin.setGmtModified(LocalDateTime.now());
        adminMapper.updateById(admin);
        return JsonResult.ok("资料修改成功");
    }

    @PostMapping("/update-avatar")
    @ApiOperation("修改头像")
    @ApiOperationSupport(order = 610)
    public JsonResult updateAvatar(@RequestBody Map<String, String> body,
                                   @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        Admin admin = adminMapper.selectById(user.getId());
        admin.setAvatar(body.get("avatar"));
        admin.setGmtModified(LocalDateTime.now());
        adminMapper.updateById(admin);
        return JsonResult.ok("头像修改成功");
    }

    @PostMapping("/change-own-password")
    @ApiOperation("修改自己的密码")
    @ApiOperationSupport(order = 620)
    public JsonResult changeOwnPassword(@RequestBody Map<String, String> body,
                                        @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        String oldPassword = body.get("oldPassword");
        String newPassword = body.get("newPassword");
        if (oldPassword == null || newPassword == null)
            throw new ServiceException(ServiceCode.ERROR_BAD_REQUEST, "新旧密码不能为空");
        Admin admin = adminMapper.selectById(user.getId());
        if (!passwordEncoder.matches(oldPassword, admin.getPassword()))
            throw new ServiceException(ServiceCode.ERROR_BAD_REQUEST, "原密码错误");
        admin.setPassword(passwordEncoder.encode(newPassword));
        admin.setGmtModified(LocalDateTime.now());
        adminMapper.updateById(admin);
        return JsonResult.ok("密码修改成功");
    }

}
