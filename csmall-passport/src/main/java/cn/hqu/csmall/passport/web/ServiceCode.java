package cn.hqu.csmall.passport.web;

public enum ServiceCode {
    OK(20000),
    CREATED(20100),
    UNAUTHORIZED(40100),
    FORBIDDEN(40300),
    NOT_FOUND(40400),
    ERR_BAD_REQUEST(40000),
    ERR_CONFLICT(40900),
    ERR_INSERT(50000),
    ERR_UPDATE(50001),
    ERR_DELETE(50002),
    ERR_KNOWN(99999);


    private Integer value;
    ServiceCode(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }
}
