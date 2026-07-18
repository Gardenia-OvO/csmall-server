package cn.hqu.csmall.product.web;

public enum ServiceCode {
    OK(200),
    CREATED(201),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    ERR_BAD_REQUEST(400),
    ERR_CONFLICT(409),
    ERR_KNOWN(500);

    private Integer value;
    ServiceCode(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }
}
