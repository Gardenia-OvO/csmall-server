package cn.hqu.csmall.commons.filter;

import cn.hqu.csmall.commons.security.LoginPrincipal;
import cn.hqu.csmall.commons.web.JsonResult;
import cn.hqu.csmall.commons.web.ServiceCode;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * JWT授权过滤器
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Component
@ConditionalOnProperty("csmall.jwt.secret-key")
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
        log.debug("获取到的JWT是：{}", jwt);
        if (!StringUtils.hasText(jwt)) {
            chain.doFilter(request, response);
            return;
        }
        //去除"Bearer "前缀
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
            log.debug("去除Bearer前缀后的JWT是：{}", jwt);
        }

        response.setContentType("application/json; charset=utf-8");

        //解析JWT
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
        } catch (MalformedJwtException e) {
            PrintWriter printWriter = response.getWriter();
            String message = "非法访问!";
            log.warn("程序运行过程中出现了MalformedJwtException异常，将向客户端响应错误信息");
            log.warn("错误信息：{}", message);
            log.warn("异常", e);
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_UNAUTHORIZED, message);
            String jsonString = JSON.toJSONString(jsonResult);
            printWriter.write(jsonString);
            printWriter.close();
            return;
        } catch (ExpiredJwtException e) {
            PrintWriter printWriter = response.getWriter();
            String message = "JWT已过期!请重新登录";
            log.warn("程序运行过程中出现了ExpiredJwtException异常，将向客户端响应错误信息");
            log.warn("错误信息：{}", message);
            log.warn("异常", e);
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_UNAUTHORIZED, message);
            String jsonString = JSON.toJSONString(jsonResult);
            printWriter.write(jsonString);
            printWriter.close();
            return;
        } catch (SignatureException e) {
            PrintWriter printWriter = response.getWriter();
            String message = "非法访问!";
            log.warn("程序运行过程中出现了SignatureException异常，将向客户端响应错误信息");
            log.warn("错误信息：{}", message);
            log.warn("异常", e);
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_UNAUTHORIZED, message);
            String jsonString = JSON.toJSONString(jsonResult);
            printWriter.write(jsonString);
            printWriter.close();
            return;
        } catch (Throwable e) {
            PrintWriter printWriter = response.getWriter();
            String message = "服务器忙，请稍后再试!";
            log.debug("解析JWT中出现了Throwable,将统一处理!" +
                    "【若在开发中出现此提示信息，说明解析JWT时出现了其他异常，需要查看控制台，并此异常处理添加新的异常处理分支】");
            log.warn("异常信息,{}", e.getMessage());
            log.warn("异常", e);
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_UNAUTHORIZED, message);
            String jsonString = JSON.toJSONString(jsonResult);
            printWriter.write(jsonString);
            printWriter.close();
            return;
        }

        Long id = claims.get("id", Long.class);
        String username = claims.get("username", String.class);
        log.debug("解析JWT得到id是：{}，username是：{}", id, username);

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

        Authentication authentication = new UsernamePasswordAuthenticationToken(principals, credentials, authorities);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
