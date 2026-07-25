package cn.hqu.csmall.passport.filter;


import cn.hqu.csmall.passport.security.LoginPrincipal;
import cn.hqu.csmall.passport.web.JsonResult;
import cn.hqu.csmall.passport.web.ServiceCode;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Value("${csmall.jwt.secret-key}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        log.debug("JwtAuthorizationFilter过滤器开始执行");
        String jwt = request.getHeader("Authorization");
        log.debug("获取到的JWT是：{}",jwt);
        if(!StringUtils.hasText(jwt)){
            chain.doFilter(request,response);
            return;
        }

        response.setContentType("application/json; charset=utf-8");//设置响应数据的类型和编码

        //解析JWT
        Claims claims =null;
        try {
            //解析jwt
            claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
        }catch (MalformedJwtException e){
            PrintWriter printWriter = response.getWriter();//获取响应数据的输出流
            String message = "非法访问!";
            log.warn("程序运行过程中出现了MalformedJwtException异常，将向客户端响应错误信息");
            log.warn("错误信息：{}",message);
            log.warn("异常",e);
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_UNAUTHORIZED,message);//创建失败结果对象
            String jsonString = JSON.toJSONString(jsonResult);//将失败结果对象转换为JSON格式的字符串
            printWriter.write(jsonString);//将JSON格式的字符串写入到响应数据中
            printWriter.close();
            return;
        }catch (ExpiredJwtException e){
            //response.setContentType("application/json; charset=utf-8");//设置响应数据的类型和编码
            PrintWriter printWriter = response.getWriter();//获取响应数据的输出流
            String message = "JWT已过期!请重新登录";
            log.warn("程序运行过程中出现了ExpiredJwtException异常，将向客户端响应错误信息");
            log.warn("错误信息：{}",message);
            log.warn("异常",e);
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_UNAUTHORIZED,message);//创建失败结果对象
            String jsonString = JSON.toJSONString(jsonResult);//将失败结果对象转换为JSON格式的字符串
            printWriter.write(jsonString);//将JSON格式的字符串写入到响应数据中
            printWriter.close();
            return;
        }catch (SignatureException e){
            //response.setContentType("application/json; charset=utf-8");//设置响应数据的类型和编码
            PrintWriter printWriter = response.getWriter();//获取响应数据的输出流
            String message = "非法访问!";
            log.warn("程序运行过程中出现了SignatureException异常，将向客户端响应错误信息");
            log.warn("错误信息：{}",message);
            log.warn("异常",e);
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_UNAUTHORIZED,message);//创建失败结果对象
            String jsonString = JSON.toJSONString(jsonResult);//将失败结果对象转换为JSON格式的字符串
            printWriter.write(jsonString);//将JSON格式的字符串写入到响应数据中
            printWriter.close();
            return;
        }catch (Throwable e){
            //response.setContentType("application/json; charset=utf-8");//设置响应数据的类型和编码
            PrintWriter printWriter = response.getWriter();//获取响应数据的输出流
            String message = "服务器忙，请稍后再试!";
            log.debug("解析JWT中出选了Throwable,将统一处理!" +
                    "【若在开发中出现此提示信息，说明解析JWT时出现了其他异常，需要查看控制台，并此异常处理添加新的异常处理分支】");
            log.warn("异常信息,{}",e.getMessage());
            log.warn("异常",e);
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_UNAUTHORIZED,message);//创建失败结果对象
            String jsonString = JSON.toJSONString(jsonResult);//将失败结果对象转换为JSON格式的字符串
            printWriter.write(jsonString);//将JSON格式的字符串写入到响应数据中
            printWriter.close();
            return;
        }

        Long id = claims.get("id", Long.class);
        String username = claims.get("username", String.class);
        log.debug("解析JWT得到id是：{}，username是：{}",id,username);

        //封装当事人
        Object principals = new LoginPrincipal().setId(id).setUsername(username);
        Object credentials = null;

        //从JWT中解析真实权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        String permissionsJson = claims.get("permissions", String.class);
        if (StringUtils.hasText(permissionsJson)) {
            List<String> permissions = JSON.parseArray(permissionsJson, String.class);
            for (String permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }
            log.debug("从JWT中解析到权限：{}", permissions);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(principals,credentials,authorities);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        chain.doFilter(request,response);
    }
}
