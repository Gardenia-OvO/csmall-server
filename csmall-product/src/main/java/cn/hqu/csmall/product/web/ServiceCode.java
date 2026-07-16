package cn.hqu.csmall.product.web;

public enum ServiceCode {
    OK(20000),
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
