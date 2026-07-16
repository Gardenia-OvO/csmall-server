package cn.hqu.csmall.product.service;

import cn.hqu.csmall.product.pojo.param.AttributeTemplateAddNewParam;


public interface IAttributeTemplateService {
    /**
     * 新增属性模板
     * @param attributeTemplateAddNewParam 属性模板新增参数
     */

    void addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam);

    void delete(Long id);
}
