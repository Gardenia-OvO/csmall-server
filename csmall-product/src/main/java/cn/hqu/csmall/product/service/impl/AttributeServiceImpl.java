package cn.hqu.csmall.product.service.impl;

import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.web.ServiceCode;
import cn.hqu.csmall.product.mapper.AttributeMapper;
import cn.hqu.csmall.product.mapper.AttributeTemplateMapper;
import cn.hqu.csmall.product.pojo.entity.Attribute;
import cn.hqu.csmall.product.pojo.param.AttributeAddNewParam;
import cn.hqu.csmall.product.pojo.param.AttributeUpdateParam;
import cn.hqu.csmall.product.pojo.vo.AttributeListItemVO;
import cn.hqu.csmall.product.pojo.vo.AttributeStandardVO;
import cn.hqu.csmall.product.service.IAttributeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AttributeServiceImpl implements IAttributeService {

    @Autowired
    private AttributeMapper attributeMapper;

    @Autowired
    private AttributeTemplateMapper attributeTemplateMapper;

    @Override
    public void addNew(AttributeAddNewParam attributeAddNewParam) throws ServiceException {
        log.debug("开始处理【添加属性】的业务，参数：{}", attributeAddNewParam);

        // 检查属性模板是否存在
        Long templateId = attributeAddNewParam.getTemplateId();
        int templateCount = attributeTemplateMapper.selectCount(
                new QueryWrapper<cn.hqu.csmall.product.pojo.entity.AttributeTemplate>().eq("id", templateId));
        if (templateCount == 0) {
            String message = "添加属性失败，属性模板不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        // 检查同一模板下属性名称是否重复
        String name = attributeAddNewParam.getName();
        QueryWrapper<Attribute> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("template_id", templateId).eq("name", name);
        int count = attributeMapper.selectCount(queryWrapper);
        if (count > 0) {
            String message = "添加属性失败，该属性模板中已存在名称为【" + name + "】的属性！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        Attribute attribute = new Attribute();
        BeanUtils.copyProperties(attributeAddNewParam, attribute);
        int rows = attributeMapper.insert(attribute);
        if (rows != 1) {
            throw new ServiceException(ServiceCode.ERROR_INSERT, "添加属性失败，服务器忙！");
        }
        log.debug("添加属性成功");
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【根据id删除属性】的业务，参数：{}", id);
        QueryWrapper<Attribute> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        int count = attributeMapper.selectCount(queryWrapper);
        if (count == 0) {
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "删除属性失败，数据不存在！");
        }
        attributeMapper.deleteById(id);
        log.debug("删除属性成功");
    }

    @Override
    public void updateById(Long id, AttributeUpdateParam attributeUpdateParam) {
        log.debug("开始处理【修改属性】的业务，id：{}，参数：{}", id, attributeUpdateParam);
        QueryWrapper<Attribute> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        int count = attributeMapper.selectCount(queryWrapper);
        if (count == 0) {
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "修改属性失败，数据不存在！");
        }
        Attribute attribute = new Attribute();
        BeanUtils.copyProperties(attributeUpdateParam, attribute);
        attribute.setId(id);
        attributeMapper.updateById(attribute);
        log.debug("修改属性成功");
    }

    @Override
    public AttributeStandardVO getStandardById(Long id) {
        AttributeStandardVO result = attributeMapper.getStandardById(id);
        if (result == null) {
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "查询属性失败，数据不存在！");
        }
        return result;
    }

    @Override
    public List<AttributeListItemVO> listByTemplateId(Long templateId) {
        return attributeMapper.listByTemplateId(templateId);
    }
}
