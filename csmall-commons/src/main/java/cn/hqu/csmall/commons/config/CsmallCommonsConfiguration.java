package cn.hqu.csmall.commons.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan({
        "cn.hqu.csmall.commons.config",
        "cn.hqu.csmall.commons.ex.handler",
        "cn.hqu.csmall.commons.filter"
})
public class CsmallCommonsConfiguration {
}
