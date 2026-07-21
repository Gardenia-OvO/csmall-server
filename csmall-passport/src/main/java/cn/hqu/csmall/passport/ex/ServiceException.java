package cn.hqu.csmall.passport.ex;


import cn.hqu.csmall.passport.web.ServiceCode;
import lombok.Getter;

public class ServiceException extends RuntimeException{
    public ServiceException() {
    }

    @Getter
    private ServiceCode serviceCode;

    public ServiceException(ServiceCode serviceCode,String message) {
        super(message);
        this.serviceCode = serviceCode;
    }


    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
