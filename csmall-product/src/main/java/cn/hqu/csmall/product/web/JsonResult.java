package cn.hqu.csmall.product.web;


import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult implements Serializable {

    private Integer state;
    private String message;

    public static JsonResult ok(){
        JsonResult result = new JsonResult();
        result.setState(ServiceCode.OK.getValue());
        result.setMessage("操作成功");
        return result;
    }

    public static JsonResult ok(String message){
        JsonResult result = new JsonResult();
        result.setState(ServiceCode.OK.getValue());
        result.setMessage(message);
        return result;
    }

    public static JsonResult created(String message){
        JsonResult result = new JsonResult();
        result.setState(ServiceCode.CREATED.getValue());
        result.setMessage(message);
        return result;
    }

    public static JsonResult fail(ServiceCode serviceCode, String message){
        JsonResult result = new JsonResult();
        result.setState(serviceCode.getValue());
        result.setMessage(message);
        return result;
    }
}
