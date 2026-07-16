package cn.hqu.csmall.product.controller;

import cn.hqu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.hqu.csmall.product.service.IAttributeTemplateService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/attribute-templates")
public class AttributeTemplateController {
    @Autowired
    private IAttributeTemplateService attributeTemplateService;

    @PostMapping("/add-new")
    @ApiOperation("添加属性模版")
    @ApiOperationSupport(order = 100)
    public String addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam) {
        try {
            attributeTemplateService.addNew(attributeTemplateAddNewParam);
            return "属性添加成功";
        } catch (RuntimeException e) {
            log.warn("属性添加失败，原因：{}",e.getMessage());
            return e.getMessage();
        }catch(Throwable e){
            log.error("新增属性出现未知错误",e);
            return "添加失败，出现未知错误";
        }
    }
}
