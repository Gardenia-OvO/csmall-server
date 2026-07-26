package cn.hqu.csmall.passport.controller;

import cn.hqu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.hqu.csmall.passport.pojo.param.AdminLoginInfoParam;
import cn.hqu.csmall.passport.pojo.param.AdminUpdateParam;
import cn.hqu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.hqu.csmall.passport.security.AdminDetail;
import cn.hqu.csmall.passport.security.LoginPrincipal;
import cn.hqu.csmall.passport.service.IAdminService;
import cn.hqu.csmall.passport.web.JsonResult;
import cn.hqu.csmall.product.pojo.vo.PageData;
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

}
