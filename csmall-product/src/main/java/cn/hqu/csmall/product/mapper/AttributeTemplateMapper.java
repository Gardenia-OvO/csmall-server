package cn.hqu.csmall.product.mapper;

import cn.hqu.csmall.product.pojo.entity.AttributeTemplate;
import cn.hqu.csmall.product.pojo.vo.AttributeTemplateListItemVO;
import cn.hqu.csmall.product.pojo.vo.AttributeTemplateStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AttributeTemplateMapper extends BaseMapper<AttributeTemplate> {
    List<AttributeTemplateListItemVO> list();

    AttributeTemplateStandardVO getStandardById(Long id);
}
