package cn.hqu.csmall.product.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("cn.hqu.csmall.product.mapper")
@Configuration
//扫描mapper接口
public class MyBatisConfiguration {
}
