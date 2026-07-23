package cn.hqu.csmall.passport.config;


import cn.hqu.csmall.passport.web.JsonResult;
import cn.hqu.csmall.passport.web.ServiceCode;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Configuration
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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.debug("配置Spring Security的HttpSecurity");
        //处理未通过认证访问受保护的资源时拒绝访问
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            //匿名内部类对象
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                response.setContentType("application/json; charset=utf-8");//设置响应数据的类型和编码
                PrintWriter printWriter = response.getWriter();//获取响应数据的输出流
                String message = "您当前未登录,请先登录";
                JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_UNAUTHORIZED,message);//创建失败结果对象
                String jsonString = JSON.toJSONString(jsonResult);//将失败结果对象转换为JSON格式的字符串
                printWriter.write(jsonString);//将JSON格式的字符串写入到响应数据中
                printWriter.close();
            }
        });
        // 禁用CSRF（用于API服务），启用CORS
        http.csrf().disable()
                .cors();

        // 白名单：放行Knife4j文档及API接口
        String[] urls = {
                "/doc.html", "/**/*.css", "/**/*.js", "/swagger-resources", "/v2/api-docs",
                "/admin/**",  // 暂时放行管理员相关接口
                "/role/**" ,   // 暂时放行角色相关接口
        };

        http.authorizeRequests()
                .mvcMatchers(urls)
                .permitAll()
                .anyRequest()
                .authenticated();
    }
}
