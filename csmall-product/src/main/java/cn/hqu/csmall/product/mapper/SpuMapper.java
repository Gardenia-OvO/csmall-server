package cn.hqu.csmall.product.mapper;

import cn.hqu.csmall.product.pojo.entity.Spu;
import cn.hqu.csmall.product.pojo.vo.SpuFullInfoVO;
import cn.hqu.csmall.product.pojo.vo.SpuListItemVO;
import cn.hqu.csmall.product.pojo.vo.SpuStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpuMapper extends BaseMapper<Spu> {

    List<SpuListItemVO> list();

    SpuStandardVO getStandardById(Long id);

    SpuFullInfoVO getFullInfoById(Long id);
}
