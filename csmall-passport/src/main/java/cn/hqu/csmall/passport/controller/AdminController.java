package cn.hqu.csmall.passport.controller;

import cn.hqu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.hqu.csmall.passport.pojo.vo.AdminListItemVO;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/admin")
@Api(tags = "01. 管理员管理模块")
@RestController
@Validated
public class AdminController {
    @Autowired
    private IAdminService adminService;
    @PostMapping("/add-new")
    @ApiOperation("添加管理员")
    @ApiOperationSupport(order = 100)   //
    public JsonResult addNew(@Valid @RequestBody AdminAddNewParam adminAddNewParam)  {
        log.debug("开始处理【添加管理员】的业务，参数：{}",adminAddNewParam);
        adminService.addNew(adminAddNewParam);
        log.debug("处理【添加管理员】的业务结束");
        return JsonResult.created("新增管理员成功");
    }

    @GetMapping("/list")
    @ApiOperation("查询管理员列表")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult list(@Range(min = 1, message = "查询管理员列表失败，请提供正确的页码值！")
                           @RequestParam(defaultValue = "1") Integer pageNum) {
        log.debug("开始处理【查询管理员列表】的业务，参数：{}", pageNum);
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        PageData<AdminListItemVO> pageData = adminService.list(pageNum);
        return JsonResult.ok(pageData);
    }
}
