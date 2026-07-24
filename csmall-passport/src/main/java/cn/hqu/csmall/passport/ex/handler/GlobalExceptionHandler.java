package cn.hqu.csmall.passport.ex.handler;

import cn.hqu.csmall.passport.ex.ServiceException;
import cn.hqu.csmall.passport.web.JsonResult;
import cn.hqu.csmall.passport.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler
    public JsonResult handleServiceException(ServiceException e) {
        log.debug("处理ServiceException");
        log.warn("业务异常：{}", e.getMessage());
        ServiceCode code = e.getServiceCode() != null ? e.getServiceCode() : ServiceCode.ERR_CONFLICT;
        return JsonResult.fail(code, e.getMessage());
    }

    /**
     * 请求参数绑定校验异常
     */
    @ExceptionHandler
    public JsonResult handleBindException(BindException e) {
        log.debug("处理BindException");
        log.warn("参数校验异常：{}", e.getMessage());
        String message = e.getFieldError() != null
                ? e.getFieldError().getDefaultMessage()
                : "请求参数校验失败";
        return JsonResult.fail(ServiceCode.ERR_BAD_REQUEST, message);
    }

    /**
     * JSR-303 约束校验异常
     */
    @ExceptionHandler
    public JsonResult handleConstraintViolationException(ConstraintViolationException e) {
        log.debug("处理ConstraintViolationException");
        log.warn("约束校验异常：{}", e.getMessage());
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String message = "请求参数校验失败";
        for (ConstraintViolation<?> violation : violations) {
            message = violation.getMessage();
        }
        return JsonResult.fail(ServiceCode.ERR_BAD_REQUEST, message);
    }

    /**
     * 未预知的异常（兜底）
     */
    @ExceptionHandler
    public JsonResult handleThrowable(Throwable e) {
        log.debug("处理Throwable");
        log.error("未知异常", e);
        return JsonResult.fail(ServiceCode.ERR_KNOWN, "服务器内部错误，请稍后重试");
    }

    /**
     * Spring Security 访问拒绝异常（@PreAuthorize 校验失败）
     */
    @ExceptionHandler
    public JsonResult handleAccessDeniedException(AccessDeniedException e) {
        log.debug("处理AccessDeniedException");
        log.warn("访问被拒绝：{}", e.getMessage());
        // 判断是否已登录：已登录返回403，未登录返回401
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !"anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            return JsonResult.fail(ServiceCode.ERR_FORBIDDEN, "您没有权限执行此操作");
        }
        return JsonResult.fail(ServiceCode.ERR_UNAUTHORIZED, "您当前未登录，请先登录");
    }

    @ExceptionHandler({
            AuthenticationCredentialsNotFoundException.class
    })
    public JsonResult handleAuthenticationCredentialsNotFoundException(AuthenticationException e) {
        log.debug("处理AuthenticationCredentialsNotFoundException");
        log.warn("未登录访问受保护资源：{}", e.getMessage());
        return JsonResult.fail(ServiceCode.ERR_UNAUTHORIZED, "您当前未登录，请先登录");
    }

    @ExceptionHandler({
            BadCredentialsException.class,
            UsernameNotFoundException.class
    })
    public JsonResult handleAuthenticationException(AuthenticationException e) {
        log.debug("程序运行过程中出现了AuthenticationException，将统一处理");
        String message = "登录失败，用户名或密码错误";
        log.warn("异常",e);
        return JsonResult.fail(ServiceCode.ERR_UNAUTHORIZED, message);
    }

    @ExceptionHandler
    public JsonResult handleDisabledException(DisabledException e) {
        log.debug("程序运行过程中出现了DisabledException，将统一处理");
        String message = "登录失败，账户已被禁用";
        log.warn("异常",e);
        return JsonResult.fail(ServiceCode.ERR_UNAUTHORIZED_DISABLED, message);
    }
}
