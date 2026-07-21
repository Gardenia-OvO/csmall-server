package cn.hqu.csmallpassport.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("cn.hqu.csmallpassport.mapper")
@Configuration
//扫描mapper接口
public class MyBatisConfiguration {
}
