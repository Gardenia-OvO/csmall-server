package cn.hqu.csmall.product.web;


import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult implements Serializable {

    private Integer state;
    private String message;

    public static JsonResult ok(){
        JsonResult result = new JsonResult();
        result.setState(1);
        return result;
    }

    public static JsonResult fail(Integer state, String message){
        JsonResult result = new JsonResult();
        result.setState(state);
        result.setMessage(message);
        return result;
    }
}
