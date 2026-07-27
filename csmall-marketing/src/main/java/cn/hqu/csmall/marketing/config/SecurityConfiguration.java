package cn.hqu.csmall.marketing.config;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.PrintWriter;

@Slf4j @Configuration @EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean @Override public AuthenticationManager authenticationManagerBean() throws Exception { return super.authenticationManagerBean(); }
    @Autowired private JwtAuthorizationFilter jwtAuthorizationFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().authenticationEntryPoint((req,res,e)->{res.setContentType("application/json;charset=utf-8");PrintWriter w=res.getWriter();w.write(JSON.toJSONString(JsonResult.fail(ServiceCode.ERROR_UNAUTHORIZED,"请先登录")));w.close();});
        http.exceptionHandling().accessDeniedHandler((req,res,e)->{res.setContentType("application/json;charset=utf-8");PrintWriter w=res.getWriter();w.write(JSON.toJSONString(JsonResult.fail(ServiceCode.ERROR_FORBIDDEN,"无权限")));w.close();});
        http.csrf().disable().cors();
        http.authorizeRequests().mvcMatchers("/doc.html","/**/*.css","/**/*.js","/swagger-resources","/v2/api-docs").permitAll().anyRequest().authenticated();
    }
}
