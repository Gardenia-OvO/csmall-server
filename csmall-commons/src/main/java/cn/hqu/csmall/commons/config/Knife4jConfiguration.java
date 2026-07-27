package cn.hqu.csmall.commons.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Knife4j配置类（属性驱动，各模块通过 application.yml 配置）
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Configuration
@EnableSwagger2WebMvc
@ConditionalOnProperty("knife4j.base-package")
public class Knife4jConfiguration {

    @Value("${knife4j.base-package}")
    private String basePackage;

    @Value("${knife4j.group-name}")
    private String groupName;

    @Value("${knife4j.host}")
    private String host;

    @Value("${knife4j.title}")
    private String title;

    @Value("${knife4j.description}")
    private String description;

    @Value("${knife4j.terms-of-service-url:http://www.apache.org/licenses/LICENSE-2.0}")
    private String termsOfServiceUrl;

    @Value("${knife4j.contact-name}")
    private String contactName;

    @Value("${knife4j.contact-url}")
    private String contactUrl;

    @Value("${knife4j.contact-email}")
    private String contactEmail;

    @Value("${knife4j.version:1.0.0}")
    private String version;

    public Knife4jConfiguration() {
        log.debug("创建配置类对象：Knife4jConfiguration");
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .apiInfo(apiInfo())
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .version(version)
                .build();
    }
}
