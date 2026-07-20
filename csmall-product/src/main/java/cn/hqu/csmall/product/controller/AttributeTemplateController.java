package cn.hqu.csmall.product.controller;

import cn.hqu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.hqu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.hqu.csmall.product.pojo.vo.AttributeTemplateListItemVO;
import cn.hqu.csmall.product.pojo.vo.PageData;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam) {
        log.debug("开始处理【新增属性模板】的请求，参数为:{}", attributeTemplateAddNewParam);
        attributeTemplateService.addNew(attributeTemplateAddNewParam);
        return JsonResult.created("新增属性模板成功");
    }

    @PostMapping("/delete")
    @ApiOperation("删除属性模板")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "属性模板id", required = true, dataType = "long")
    public JsonResult delete(@Range(min = 1, message = "根据id删除属性模板，请提供合法的id")
                             @RequestParam Long id) {
        log.debug("开始处理【删除属性模板】的请求，id:{}", id);
        attributeTemplateService.delete(id);
        log.debug("处理【删除属性模板】的请求，完成！");
        return JsonResult.ok("删除属性模板成功");
    }

    @GetMapping("/list")
    @ApiOperation("查询属性模版列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult list(@Range(min = 1, message = "查询属性模版列表失败，请提供正确的页码值！")
                           @RequestParam Integer pageNum) {
        log.debug("开始处理【查询属性模版列表】的业务，参数：{}", pageNum);
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        PageData<AttributeTemplateListItemVO> pageData = attributeTemplateService.list(pageNum);
        return JsonResult.ok(pageData);
    }
}
