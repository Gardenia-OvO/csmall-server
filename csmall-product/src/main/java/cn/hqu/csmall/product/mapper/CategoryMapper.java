package cn.hqu.csmall.product.mapper;

import cn.hqu.csmall.product.pojo.entity.Category;
import cn.hqu.csmall.product.pojo.vo.CategoryListItemVO;
import cn.hqu.csmall.product.pojo.vo.CategoryStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    int insert(Category category);

    List<CategoryListItemVO> list();

    CategoryStandardVO getStandardById(Long id);

    List<CategoryListItemVO> search(@Param("name") String name, @Param("id") Long id, @Param("parentId") Long parentId);

    List<CategoryListItemVO> getChildrenByParentId(@Param("parentId") Long parentId);
}
