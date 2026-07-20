package cn.hqu.csmall.product.web;

import lombok.Getter;

public enum ServiceCode {
    OK(20000),
    CREATED(20100),
    UNAUTHORIZED(40100),
    FORBIDDEN(40300),
    NOT_FOUND(40400),
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
