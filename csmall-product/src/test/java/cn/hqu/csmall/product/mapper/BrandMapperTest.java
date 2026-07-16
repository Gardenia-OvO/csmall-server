package cn.hqu.csmall.product.mapper;


import cn.hqu.csmall.product.pojo.entity.Album;
import cn.hqu.csmall.product.pojo.entity.Brand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class BrandMapperTest {
    @Autowired
    private BrandMapper brandMapper;
    @Test
    void testInsertBrand(){
        Brand brand = new Brand();
        brand.setName("测试品牌01");
        brand.setPinyin("ceshipinpai01");
        brand.setDescription("测试品牌01的描述");
        brand.setKeywords("测试,品牌");
        brand.setSort(1);
        brand.setEnable(1);
        brand.setSales(0);
        brand.setProductCount(0);
        brand.setCommentCount(0);
        brand.setPositiveCommentCount(0);
        brand.setGmtCreated(LocalDateTime.now());
        brand.setGmtModified(LocalDateTime.now());
        System.out.println("插入之前：ID=" + brand.getId());
        int rows = brandMapper.insert(brand);
        System.out.println("插入之后：ID=" + brand.getId());
        System.out.println("受影响的行数rows = " + rows);
    }
}
