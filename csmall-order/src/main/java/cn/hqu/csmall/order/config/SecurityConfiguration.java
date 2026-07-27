package cn.hqu.csmall.order.config;

import cn.hqu.csmall.commons.filter.JwtAuthorizationFilter;
import cn.hqu.csmall.commons.web.JsonResult;
import cn.hqu.csmall.commons.web.ServiceCode;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean @Override public AuthenticationManager authenticationManagerBean() throws Exception { return super.authenticationManagerBean(); }
    @Autowired private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().authenticationEntryPoint((request, response, e) -> {
            response.setContentType("application/json; charset=utf-8");
            PrintWriter w = response.getWriter();
            w.write(JSON.toJSONString(JsonResult.fail(ServiceCode.ERROR_UNAUTHORIZED, "您当前未登录，请先登录")));
            w.close();
        });
        http.exceptionHandling().accessDeniedHandler((request, response, e) -> {
            response.setContentType("application/json; charset=utf-8");
            PrintWriter w = response.getWriter();
            w.write(JSON.toJSONString(JsonResult.fail(ServiceCode.ERROR_FORBIDDEN, "当前用户账户无此权限，禁止访问！")));
            w.close();
        });
        http.csrf().disable().cors();
        String[] urls = { "/doc.html", "/**/*.css", "/**/*.js", "/swagger-resources", "/v2/api-docs" };
        http.authorizeRequests().mvcMatchers(urls).permitAll().anyRequest().authenticated();
    }
}
