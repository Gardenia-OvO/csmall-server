package cn.hqu.csmall.product.web;

import lombok.Getter;

public enum ServiceCode {
    OK(20000),
    CREATED(20100),
    ERR_UNAUTHORIZED(40100),
    ERR_FORBIDDEN(40300),
    ERR_NOT_FOUND(40400),
    ERR_BAD_REQUEST(40000),
    ERR_CONFLICT(40900),
    ERR_KNOWN(99999);

    private Integer value;
    ServiceCode(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }
}
