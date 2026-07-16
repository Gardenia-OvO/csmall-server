package cn.hqu.csmall.product.controller;


import cn.hqu.csmall.product.pojo.param.BrandAddNewParam;
import cn.hqu.csmall.product.service.IBrandService;
import cn.hqu.csmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags = "品牌管理模块")
@Validated
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @PostMapping("/add-new")
    @ApiOperation("新增品牌")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid BrandAddNewParam brandAddNewParam) {
        log.debug("开始处理【添加品牌】的请求，参数:{}", brandAddNewParam);
        brandService.addNew(brandAddNewParam);
        log.debug("处理【添加品牌】的请求，完成！");
        return JsonResult.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除相册")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ID", value = "相册id", required = true, dataType = "long"),
            @ApiImplicitParam(name = "UserID", value = "用户id", required = true, dataType = "long")
    })
    public String delete(@Range(min = 1, message = "根据id删除相册，请提供合法的id")
                         @RequestParam long albumId) {
        throw new RuntimeException("功能未实现");
    }
}
