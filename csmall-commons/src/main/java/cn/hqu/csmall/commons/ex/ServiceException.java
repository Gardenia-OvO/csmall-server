package cn.hqu.csmall.commons.ex;

import cn.hqu.csmall.commons.web.ServiceCode;
import lombok.Getter;

public class ServiceException extends RuntimeException{

    @Getter
    private ServiceCode serviceCode;

    public ServiceException(){
    }

    public ServiceException(String message){
        super(message);
    }

    public ServiceException(String message, Throwable cause){
        super(message, cause);
    }

    public ServiceException(Throwable cause){
        super(cause);
    }

    public ServiceException(ServiceCode serviceCode, String message){
        super(message);
        this.serviceCode = serviceCode;
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
