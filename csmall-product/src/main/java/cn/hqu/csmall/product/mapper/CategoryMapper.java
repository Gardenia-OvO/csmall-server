package cn.hqu.csmall.product.mapper;

import cn.hqu.csmall.product.pojo.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    int insert(Category category);
}
