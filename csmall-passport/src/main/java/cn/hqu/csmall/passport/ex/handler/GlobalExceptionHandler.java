package cn.hqu.csmall.passport.ex.handler;

import cn.hqu.csmall.passport.ex.ServiceException;
import cn.hqu.csmall.passport.web.JsonResult;
import cn.hqu.csmall.passport.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
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
}
