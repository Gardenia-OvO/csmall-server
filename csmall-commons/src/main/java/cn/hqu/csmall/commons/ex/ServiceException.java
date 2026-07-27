package cn.hqu.csmall.commons.ex;

import cn.hqu.csmall.commons.web.ServiceCode;

public class ServiceException extends RuntimeException{
    private ServiceCode serviceCode;



    public ServiceException(){
    }

    public ServiceException(String message){
        super(message);
    }

    public ServiceException(String message, Throwable cause){
        super(message, cause);
    }

    public ServiceException(ServiceCode serviceCode, String message){
        super(message);
        this.serviceCode = serviceCode;
    }

    public ServiceCode getServiceCode(){
        return serviceCode;
    }
}
