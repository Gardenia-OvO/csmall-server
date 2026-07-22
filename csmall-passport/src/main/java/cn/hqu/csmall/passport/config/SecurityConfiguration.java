package cn.hqu.csmall.passport.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Slf4j
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.debug("配置Spring Security的HttpSecurity");
        // 禁用CSRF（用于API服务），启用CORS
        http.csrf().disable()
                .cors();

        // 白名单：放行Knife4j文档及API接口
        String[] urls = {
                "/doc.html", "/**/*.css", "/**/*.js", "/swagger-resources", "/v2/api-docs",
                "/admin/**"  // 暂时放行管理员相关接口
        };
        http.authorizeRequests()
                .mvcMatchers(urls)
                .permitAll()
                .anyRequest()
                .authenticated();
    }
}
