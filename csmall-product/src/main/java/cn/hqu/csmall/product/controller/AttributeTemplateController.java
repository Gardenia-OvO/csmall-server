package cn.hqu.csmall.product.controller;

import cn.hqu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.hqu.csmall.product.pojo.param.AttributeTemplateUpdateParam;
import cn.hqu.csmall.product.pojo.vo.AttributeTemplateListItemVO;
import cn.hqu.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import cn.hqu.csmall.product.pojo.vo.PageData;
import cn.hqu.csmall.product.security.LoginPrincipal;
import cn.hqu.csmall.product.service.IAttributeTemplateService;
import cn.hqu.csmall.product.web.JsonResult;
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
@RestController
@Api(tags = "属性模板管理模块")
@Validated
@RequestMapping("/attribute-templates")
public class AttributeTemplateController {
    @Autowired
    private IAttributeTemplateService attributeTemplateService;

    @PostMapping("/add-new")
    @ApiOperation("添加属性模版")
    @PreAuthorize("hasAuthority('/pms/attribute-template/add-new')")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid @RequestBody AttributeTemplateAddNewParam attributeTemplateAddNewParam,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【新增属性模板】的请求，参数为:{}", attributeTemplateAddNewParam);
        attributeTemplateService.addNew(attributeTemplateAddNewParam);
        return JsonResult.created("新增属性模板成功");
    }

    @PostMapping("/delete")
    @ApiOperation("删除属性模板")
    @PreAuthorize("hasAuthority('/pms/attribute-template/delete')")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "属性模板id", required = true, dataType = "long")
    public JsonResult delete(@Range(min = 1, message = "根据id删除属性模板，请提供合法的id")
                             @RequestParam Long id,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【删除属性模板】的请求，id:{}", id);
        attributeTemplateService.delete(id);
        log.debug("处理【删除属性模板】的请求，完成！");
        return JsonResult.ok("删除属性模板成功");
    }

    @PostMapping("/update")
    @ApiOperation("修改属性模版")
    @PreAuthorize("hasAuthority('/pms/attribute-template/update')")
    @ApiOperationSupport(order = 300)
    public JsonResult update(@Valid @RequestBody AttributeTemplateUpdateParam attributeTemplateUpdateParam,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【修改属性模版】的请求，参数为：{}", attributeTemplateUpdateParam);
        attributeTemplateService.updateById(attributeTemplateUpdateParam.getId(), attributeTemplateUpdateParam);
        log.debug("处理【修改属性模版】的请求，完成！");
        return JsonResult.ok();
    }

    @GetMapping("/list")
    @ApiOperation("查询属性模版列表")
    @PreAuthorize("hasAuthority('/pms/attribute-template/read')")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult list(@Range(min = 1, message = "查询属性模版列表失败，请提供正确的页码值！")
                           @RequestParam(defaultValue = "1") Integer pageNum,
                           @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【查询属性模版列表】的业务，参数：{}", pageNum);
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        PageData<AttributeTemplateListItemVO> pageData = attributeTemplateService.list(pageNum);
        return JsonResult.ok(pageData).setMessage("查询属性模板列表成功");
    }

    @GetMapping("/search")
    @ApiOperation("搜索属性模版")
    @PreAuthorize("hasAuthority('/pms/attribute-template/read')")
    @ApiOperationSupport(order = 430)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "属性模版名称（精确匹配）", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "属性模版ID（精确匹配）", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult search(@RequestParam(required = false) String name,
                             @RequestParam(required = false) Long id,
                             @Range(min = 1, message = "搜索属性模版失败，请提供正确的页码值！")
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【搜索属性模版】的请求，名称：{}，ID：{}，页码：{}", name, id, pageNum);
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        PageData<AttributeTemplateListItemVO> pageData = attributeTemplateService.search(name, id, pageNum);
        return JsonResult.ok(pageData);
    }


    @GetMapping("/standard")
    @ApiOperation("查询属性模版详细信息")
    @PreAuthorize("hasAuthority('/pms/attribute-template/read')")
    @ApiOperationSupport(order = 450)
    @ApiImplicitParam(name = "id", value = "属性模版id", required = true, dataType = "long")
    public JsonResult standard(@Range(min = 1, message = "根据id查询属性模版，请提供合法的id")
                             @RequestParam Long id,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【查询属性模版信息】的请求，id:{}", id);
        AttributeTemplateStandardVO result = attributeTemplateService.getStandardById(id);
        log.debug("处理【查询属性模版信息】的请求，完成！");
        return JsonResult.ok(result);
    }

}
