package cn.hqu.csmall.product.mapper;


import cn.hqu.csmall.product.pojo.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class CategoryMapperTest {
    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    void testInsertCategory() {
        Category category = new Category();
        category.setName("测试类别01");
        category.setParentId(0L);
        category.setKeywords("测试,类别");
        category.setSort(1);
        category.setIcon("http://example.com/icon.png");
        category.setEnable(1);
        category.setIsDisplay(1);
        category.setGmtCreate(LocalDateTime.now());
        category.setGmtModified(LocalDateTime.now());
        System.out.println("插入之前：ID=" + category.getId());
        int rows = categoryMapper.insert(category);
        System.out.println("插入之后：ID=" + category.getId());
        System.out.println("受影响的行数rows = " + rows);
    }
}
