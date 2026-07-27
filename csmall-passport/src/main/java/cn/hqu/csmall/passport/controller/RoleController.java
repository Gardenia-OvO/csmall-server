package cn.hqu.csmall.passport.controller;

import cn.hqu.csmall.passport.pojo.param.RoleAddNewParam;
import cn.hqu.csmall.passport.pojo.param.RoleUpdateParam;
import cn.hqu.csmall.passport.pojo.vo.RoleListItemVO;
import cn.hqu.csmall.passport.service.IRoleService;
import cn.hqu.csmall.commons.web.JsonResult;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/role")
@Api(tags = "02. 角色管理模块")
@RestController
@Validated
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @PostMapping("/add-new")
    @ApiOperation("添加角色")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid @RequestBody RoleAddNewParam roleAddNewParam) {
        log.debug("开始处理【添加角色】的业务，参数：{}", roleAddNewParam);
        roleService.addNew(roleAddNewParam);
        log.debug("处理【添加角色】的业务结束");
        return JsonResult.created("新增角色成功");
    }

    @PostMapping("/update")
    @ApiOperation("修改角色")
    @PreAuthorize("hasAuthority('/ams/admin/update')")
    @ApiOperationSupport(order = 200)
    public JsonResult update(@Valid @RequestBody RoleUpdateParam roleUpdateParam) {
        log.debug("开始处理【修改角色】的请求，参数：{}", roleUpdateParam);
        roleService.update(roleUpdateParam);
        log.debug("处理【修改角色】的请求，完成！");
        return JsonResult.ok();
    }

    @GetMapping("/list")
    @PreAuthorize(("hasAuthority('/ams/admin/read')"))
    @ApiOperation("查询角色列表")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult list(@Range(min = 1, message = "查询角色列表失败，请提供正确的页码值！")
                           @RequestParam(defaultValue = "1") Integer pageNum) {
        log.debug("开始处理【查询角色列表】的业务，参数：{}", pageNum);
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        PageData<RoleListItemVO> pageData = roleService.list(pageNum);
        return JsonResult.ok(pageData);
    }
}
