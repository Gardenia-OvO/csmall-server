package cn.hqu.csmall.product.web;


import cn.hqu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.hqu.csmall.product.pojo.vo.PageData;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class JsonResult implements Serializable {

    private Integer state;
    private String message;
    private Object data;

    public static JsonResult created(String message){
        JsonResult result = new JsonResult();
        result.setState(ServiceCode.CREATED.getValue());
        result.setMessage(message);
        return result;
    }

    public static JsonResult ok(){
        JsonResult result = new JsonResult();
        result.setState(ServiceCode.OK.getValue());
        return result;
    }

    public static JsonResult ok(Object data){
        JsonResult result = new JsonResult();
        result.setState(ServiceCode.OK.getValue());
        result.setData(data);
        return result;
    }

    public static JsonResult fail(ServiceCode serviceCode,String message){
        JsonResult result = new JsonResult();
        result.setState(serviceCode.getValue());
        result.setMessage(message);
        return result;
    }
}
