package cn.hqu.csmall.product.controller;


import cn.hqu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.hqu.csmall.product.service.ICategoryService;
import cn.hqu.csmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
@Api(tags = "类别管理模块")
@Validated
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping("/add-new")
    @ApiOperation("新增类别")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid CategoryAddNewParam categoryAddNewParam) {
        log.debug("开始处理【添加类别】的请求，参数:{}", categoryAddNewParam);
        categoryService.addNew(categoryAddNewParam);
        log.debug("处理【添加类别】的请求，完成！");
        return JsonResult.created("新增类别成功");
    }

    @PostMapping("/delete")
    @ApiOperation("删除类别")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "类别id", required = true, dataType = "long")
    public JsonResult delete(@Range(min = 1, message = "根据id删除类别，请提供合法的id")
                             @RequestParam Long id) {
        log.debug("开始处理【删除类别】的请求，id:{}", id);
        categoryService.delete(id);
        log.debug("处理【删除类别】的请求，完成！");
        return JsonResult.ok("删除类别成功");
    }
}
