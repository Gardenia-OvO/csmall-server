package cn.hqu.csmall.passport.controller;

import cn.hqu.csmall.commons.security.LoginPrincipal;
import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.web.JsonResult;
import cn.hqu.csmall.commons.web.ServiceCode;
import cn.hqu.csmall.passport.pojo.param.RoleAddNewParam;
import cn.hqu.csmall.passport.pojo.param.RoleUpdateParam;
import cn.hqu.csmall.passport.service.IRoleService;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.passport.pojo.vo.RoleListItemVO;
import cn.hqu.csmall.passport.pojo.entity.Permission;
import cn.hqu.csmall.passport.mapper.PermissionMapper;
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
import java.util.List;

@Slf4j
@RequestMapping("/role")
@Api(tags = "02. 角色管理模块")
@RestController
@Validated
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private PermissionMapper permissionMapper;

    @PostMapping("/add-new")
    @ApiOperation("添加角色")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid @RequestBody RoleAddNewParam roleAddNewParam,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        // 只有 root 和 super_admin 可以添加角色
        if (!"root".equals(user.getUsername()) && !"super_admin".equals(user.getUsername())) {
            throw new ServiceException(ServiceCode.ERROR_FORBIDDEN, "仅超级管理员可添加角色");
        }
        log.debug("开始处理【添加角色】的业务，参数：{}", roleAddNewParam);
        roleService.addNew(roleAddNewParam);
        return JsonResult.created("新增角色成功");
    }

    @PostMapping("/update")
    @ApiOperation("修改角色")
    @PreAuthorize("hasAuthority('/ams/admin/update')")
    @ApiOperationSupport(order = 200)
    public JsonResult update(@Valid @RequestBody RoleUpdateParam roleUpdateParam,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        if (!"root".equals(user.getUsername()) && !"super_admin".equals(user.getUsername())) {
            throw new ServiceException(ServiceCode.ERROR_FORBIDDEN, "仅超级管理员可修改角色");
        }
        roleService.update(roleUpdateParam);
        return JsonResult.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除角色")
    @PreAuthorize("hasAuthority('/ams/admin/delete')")
    @ApiOperationSupport(order = 400)
    @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "long")
    public JsonResult delete(@Range(min = 1) @RequestParam Long id,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        if (!"root".equals(user.getUsername()) && !"super_admin".equals(user.getUsername())) {
            throw new ServiceException(ServiceCode.ERROR_FORBIDDEN, "仅超级管理员可删除角色");
        }
        roleService.delete(id);
        return JsonResult.ok("删除角色成功");
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('/ams/admin/read')")
    @ApiOperation("查询角色列表")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")})
    public JsonResult list(@Range(min = 1) @RequestParam(defaultValue = "1") Integer pageNum,
                           @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        if (pageNum == null || pageNum < 1) pageNum = 1;
        PageData<RoleListItemVO> pageData = roleService.list(pageNum);
        return JsonResult.ok(pageData);
    }

    @GetMapping("/permissions")
    @ApiOperation("查询所有权限列表")
    @PreAuthorize("hasAuthority('/ams/admin/read')")
    @ApiOperationSupport(order = 500)
    public JsonResult listPermissions(@ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        List<Permission> permissions = permissionMapper.selectList(null);
        return JsonResult.ok(permissions);
    }
}
