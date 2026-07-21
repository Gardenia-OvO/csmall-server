package cn.hqu.csmallpassport.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;

@Slf4j
@Configuration
public class ValidationConfiguartion {
    public ValidationConfiguartion(){
        log.debug("创建配置类对象：ValidationConfiguartion");
    }
    @Bean
    public javax.validation.Validator validator(){
        return Validation.byProvider(HibernateValidator.class)
                .configure()//开始配置
                .failFast(true)//快速失败
                .buildValidatorFactory()//创建工厂
                .getValidator();//获取验证器
    }
}
