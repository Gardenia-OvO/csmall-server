package cn.hqu.csmall.passport.config;


import cn.hqu.csmall.passport.filter.JwtAuthorizationFilter;
import cn.hqu.csmall.passport.web.JsonResult;
import cn.hqu.csmall.passport.web.ServiceCode;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Configuration
//开启全局基于方法的安全检查，在方法中添加注解检查权限
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.debug("配置Spring Security的HttpSecurity");
        // 添加JWT认证过滤器
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        // 处理未通过认证访问受保护的资源
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                response.setContentType("application/json; charset=utf-8");
                PrintWriter printWriter = response.getWriter();
                String message = "您当前未登录，请先登录";
                JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_UNAUTHORIZED, message);
                String jsonString = JSON.toJSONString(jsonResult);
                printWriter.write(jsonString);
                printWriter.close();
            }
        });

        // 禁用CSRF（用于API服务），启用CORS
        http.csrf().disable()
                .cors();

        // 白名单：放行Knife4j文档、登录等接口
        String[] urls = {
                "/doc.html", "/**/*.css", "/**/*.js", "/swagger-resources", "/v2/api-docs",
                "/admin/login",             // 登录接口
                "/admin/add-new",           // 新增管理员（首次初始化）
                "/role/**",                 // 暂时放行角色相关接口
        };

        http.authorizeRequests()
                .mvcMatchers(urls)
                .permitAll()
                .anyRequest()
                .authenticated();


    }
}
