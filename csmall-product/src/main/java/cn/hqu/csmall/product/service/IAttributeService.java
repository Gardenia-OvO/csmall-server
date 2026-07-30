package cn.hqu.csmall.product.service;

import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.product.pojo.param.AttributeAddNewParam;
import cn.hqu.csmall.product.pojo.param.AttributeUpdateParam;
import cn.hqu.csmall.product.pojo.vo.AttributeListItemVO;
import cn.hqu.csmall.product.pojo.vo.AttributeStandardVO;

import java.util.List;

public interface IAttributeService {

    void addNew(AttributeAddNewParam attributeAddNewParam) throws ServiceException;

    void delete(Long id);

    void updateById(Long id, AttributeUpdateParam attributeUpdateParam);

    AttributeStandardVO getStandardById(Long id);

    List<AttributeListItemVO> listByTemplateId(Long templateId);
}
