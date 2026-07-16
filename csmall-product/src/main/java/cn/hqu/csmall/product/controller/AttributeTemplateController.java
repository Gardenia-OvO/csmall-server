package cn.hqu.csmall.product.controller;

import cn.hqu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.hqu.csmall.product.service.IAttributeTemplateService;
import cn.hqu.csmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
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
        return JsonResult.ok();
    }
}
