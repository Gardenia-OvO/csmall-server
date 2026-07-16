package cn.hqu.csmall.product.mapper;

import cn.hqu.csmall.product.pojo.entity.AttributeTemplate;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AttributeTemplateMapperTest {
    @Autowired
    private AttributeTemplateMapper mapper;

    @Test
    void testInsertAttributeTemplate() {
       AttributeTemplate data = new AttributeTemplate();
       data.setName("测试相册05");
       data.setPinyin("ceshixiangce05");
       data.setKeywords("key01,key02,key03");
       data.setSort(5);
       data.setGmtCreated(LocalDateTime.now());
       data.getGmtModified(LocalDateTime.now());
       System.out.println("插入之前：ID=" + data.getId());
       int rows = mapper.insert(data);
       System.out.println("插入之后：ID=" + data.getId());
       System.out.println("受影响的行数rows = " + rows);
    }
}
