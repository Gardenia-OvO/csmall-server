package cn.hqu.csmall.passport.controller;

import cn.hqu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.hqu.csmall.passport.service.IAdminService;
import cn.hqu.csmall.passport.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
    public JsonResult addNew(@Valid AdminAddNewParam adminAddNewParam)  {
        log.debug("开始处理【添加管理员】的业务，参数：{}",adminAddNewParam);
        adminService.addNew(adminAddNewParam);
        log.debug("处理【添加管理员】的业务结束");
        return JsonResult.ok();
    }
}
