package cn.hqu.csmall.product.service;

import cn.hqu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.hqu.csmall.product.pojo.param.AttributeTemplateUpdateParam;
import cn.hqu.csmall.product.pojo.vo.AttributeTemplateListItemVO;
import cn.hqu.csmall.product.pojo.vo.PageData;


public interface IAttributeTemplateService {
    /**
     * 新增属性模板
     * @param attributeTemplateAddNewParam 属性模板新增参数
     */

    void addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam);

    void delete(Long id);

    void updateById(Long id, AttributeTemplateUpdateParam attributeTemplateUpdateParam);

    PageData<AttributeTemplateListItemVO> list(Integer pageNum, Integer pageSize);

    PageData<AttributeTemplateListItemVO> list(Integer pageNum);
}
