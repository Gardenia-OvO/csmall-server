package cn.hqu.csmall.product.mapper;

import cn.hqu.csmall.product.pojo.entity.Album;
import cn.hqu.csmall.product.pojo.entity.Brand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandMapper extends BaseMapper<Brand>{

    int insert(Brand brand);
}
