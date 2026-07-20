package cn.hqu.csmall.product.service.impl;

import cn.hqu.csmall.product.ex.ServiceException;
import cn.hqu.csmall.product.mapper.AttributeTemplateMapper;
import cn.hqu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.hqu.csmall.product.pojo.vo.AttributeTemplateListItemVO;
import cn.hqu.csmall.product.pojo.vo.PageData;
import cn.hqu.csmall.product.util.PageInfoToPageDataConverter;
import cn.hqu.csmall.product.web.ServiceCode;
import cn.hqu.csmall.product.pojo.entity.AttributeTemplate;
import cn.hqu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.hqu.csmall.product.service.IAttributeTemplateService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AttributeTemplateServiceImpl implements IAttributeTemplateService {
    @Autowired
    private AttributeTemplateMapper attributeTemplateMapper;

    @Override
    public void addNew(AttributeTemplateAddNewParam attributeTemplateAddNewParam) {
        log.debug("开始处理【新增属性模板】的业务，参数为:{}", attributeTemplateAddNewParam);

        // 检查属性模板名称是否被占用
        QueryWrapper<AttributeTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", attributeTemplateAddNewParam.getName());
        int count = attributeTemplateMapper.selectCount(queryWrapper);
        log.debug("根据属性模板名称查询表中是否有同名模板，检测结果为：{}", count);
        if (count > 0) {
            String message = "属性模板名称已经被占用，请更换";
            log.warn(message);
            throw new ServiceException(message);
        }

        // 向数据库中插入属性模板数据
        AttributeTemplate attributeTemplate = new AttributeTemplate();
        BeanUtils.copyProperties(attributeTemplateAddNewParam, attributeTemplate);
        attributeTemplate.setGmtCreate(LocalDateTime.now());
        attributeTemplate.setGmtModified(LocalDateTime.now());

        log.debug("准备插入属性模板数据到数据库，属性模板信息为：{}", attributeTemplate);
        attributeTemplateMapper.insert(attributeTemplate);
        log.debug("新增属性模板成功");
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【删除属性模板】的业务，id为:{}", id);
        int rows = attributeTemplateMapper.deleteById(id);
        if (rows == 0) {
            String message = "删除属性模板失败，该数据已删除或不存在";
            log.warn(message);
            throw new ServiceException(ServiceCode.NOT_FOUND, message);
        }
        log.debug("删除属性模板成功");
    }

    @Override
    public PageData<AttributeTemplateListItemVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询属性模版列表】的业务，页码：{}，每页记录数：{}",pageNum,pageSize);
        PageHelper.startPage(pageNum,pageSize);
        List<AttributeTemplateListItemVO> list = attributeTemplateMapper.list();
        PageInfo<AttributeTemplateListItemVO> pageInfo = new PageInfo<>(list);
        PageData<AttributeTemplateListItemVO> pageData = PageInfoToPageDataConverter.convert(pageInfo);
        log.debug("处理【查询属性模版列表】的业务完成，结果：{}",pageData);
        return pageData;
    }

    @Override
    public PageData<AttributeTemplateListItemVO> list(Integer pageNum) {
        Integer pageSize = 5;
        log.debug("开始处理【查询属性模版列表】的业务，页码：{}，每页记录数(默认)：{}",pageNum,pageSize);
        return list(pageNum,pageSize);
    }
}
