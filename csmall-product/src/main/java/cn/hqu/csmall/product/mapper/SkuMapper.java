package cn.hqu.csmall.product.mapper;

import cn.hqu.csmall.product.pojo.entity.Sku;
import cn.hqu.csmall.product.pojo.vo.SkuListItemVO;
import cn.hqu.csmall.product.pojo.vo.SkuStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkuMapper extends BaseMapper<Sku> {
    List<SkuListItemVO> listBySpuId(Long spuId);
    SkuStandardVO getStandardById(Long id);
}
