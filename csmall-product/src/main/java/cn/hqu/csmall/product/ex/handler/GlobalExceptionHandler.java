package cn.hqu.csmall.product.ex.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public String handleException(Throwable e) {
        log.debug("程序运行中出现了ServiceException，将统一处理");
        log.warn("发生异常：{}", e.getMessage(), e);
        return e.getMessage();
    }
}
