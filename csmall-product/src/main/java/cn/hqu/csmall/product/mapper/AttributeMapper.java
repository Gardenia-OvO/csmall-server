package cn.hqu.csmall.product.mapper;

import cn.hqu.csmall.product.pojo.entity.Attribute;
import cn.hqu.csmall.product.pojo.vo.AttributeListItemVO;
import cn.hqu.csmall.product.pojo.vo.AttributeStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeMapper extends BaseMapper<Attribute> {

    AttributeStandardVO getStandardById(Long id);

    List<AttributeListItemVO> listByTemplateId(Long templateId);
}
