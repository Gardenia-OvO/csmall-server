package cn.hqu.csmall.commons.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@MapperScan("${mybatis.mapper-base-package}")
@Configuration
@ConditionalOnProperty("mybatis.mapper-base-package")
public class MyBatisConfiguration {
}
