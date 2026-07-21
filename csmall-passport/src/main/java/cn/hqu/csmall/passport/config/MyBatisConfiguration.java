package cn.hqu.csmall.passport.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("cn.hqu.csmall.passport.mapper")
@Configuration
//扫描mapper接口
public class MyBatisConfiguration {
}
