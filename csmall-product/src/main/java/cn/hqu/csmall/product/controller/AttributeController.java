package cn.hqu.csmall.product.controller;

import cn.hqu.csmall.commons.security.LoginPrincipal;
import cn.hqu.csmall.commons.web.JsonResult;
import cn.hqu.csmall.product.pojo.param.AttributeAddNewParam;
import cn.hqu.csmall.product.pojo.param.AttributeUpdateParam;
import cn.hqu.csmall.product.pojo.vo.AttributeListItemVO;
import cn.hqu.csmall.product.pojo.vo.AttributeStandardVO;
import cn.hqu.csmall.product.service.IAttributeService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
@RestController
@RequestMapping("/attributes")
@Validated
@Api(tags = "03. 属性管理模块")
public class AttributeController {

    @Autowired
    private IAttributeService attributeService;

    @PostMapping("/add-new")
    @PreAuthorize("hasAuthority('/pms/product/add-new')")
    @ApiOperation("添加属性")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid @RequestBody AttributeAddNewParam attributeAddNewParam,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("开始处理【添加属性】的请求，参数：{}", attributeAddNewParam);
        attributeService.addNew(attributeAddNewParam);
        return JsonResult.created("添加属性成功");
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('/pms/product/delete')")
    @ApiOperation("根据ID删除属性")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "属性ID", required = true, dataType = "long")
    public JsonResult delete(@PathVariable @Range(min = 1) Long id,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("开始处理【根据id删除属性】的请求，参数：{}", id);
        attributeService.delete(id);
        return JsonResult.ok("删除属性成功");
    }

    @PostMapping("/{id}/update")
    @PreAuthorize("hasAuthority('/pms/product/update')")
    @ApiOperation("修改属性")
    @ApiOperationSupport(order = 300)
    @ApiImplicitParam(name = "id", value = "属性ID", required = true, dataType = "long")
    public JsonResult update(@PathVariable @Range(min = 1) Long id,
                             @Valid @RequestBody AttributeUpdateParam attributeUpdateParam,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        attributeService.updateById(id, attributeUpdateParam);
        return JsonResult.ok();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('/pms/product/read')")
    @ApiOperation("根据ID查询属性详情")
    @ApiOperationSupport(order = 410)
    @ApiImplicitParam(name = "id", value = "属性ID", required = true, dataType = "long")
    public JsonResult getStandardById(@PathVariable @Range(min = 1) Long id,
                                      @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        AttributeStandardVO attribute = attributeService.getStandardById(id);
        return JsonResult.ok(attribute);
    }

    @GetMapping("/list-by-template")
    @PreAuthorize("hasAuthority('/pms/product/read')")
    @ApiOperation("根据属性模板查询属性列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParam(name = "templateId", value = "属性模板ID", required = true, paramType = "query", dataType = "long")
    public JsonResult listByTemplateId(@RequestParam @Range(min = 1) Long templateId,
                                       @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        List<AttributeListItemVO> list = attributeService.listByTemplateId(templateId);
        return JsonResult.ok(list);
    }
}
