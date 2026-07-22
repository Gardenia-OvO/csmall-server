package cn.hqu.csmall.product.mapper;

import cn.hqu.csmall.product.pojo.entity.Brand;
import cn.hqu.csmall.product.pojo.vo.BrandListItemVO;
import cn.hqu.csmall.product.pojo.vo.BrandStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandMapper extends BaseMapper<Brand>{

    int insert(Brand brand);

    List<BrandListItemVO> list();

    BrandStandardVO getStandardById(Long id);
}
