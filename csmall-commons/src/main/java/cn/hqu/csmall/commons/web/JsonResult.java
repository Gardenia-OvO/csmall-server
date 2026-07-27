package cn.hqu.csmall.commons.web;

import cn.hqu.csmall.commons.ex.ServiceException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果类型
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class JsonResult<T> implements Serializable {

    /**
     * 操作结果的状态码（状态标识）
     */
    @ApiModelProperty("业务状态码")
    private Integer state;
    /**
     * 操作失败时的提示文本
     */
    @ApiModelProperty("提示文本")
    private String message;
    /**
     * 操作成功时响应的数据
     */
    @ApiModelProperty("数据")
    private T data;

    /**
     * 生成表示"成功"的响应对象
     *
     * @return 表示"成功"的响应对象
     */
    public static JsonResult<Void> ok() {
        return ok(null);
    }

    /**
     * 生成表示"成功"的响应对象，此对象中将包含响应到客户端的数据
     *
     * @param data 响应到客户端的数据
     * @return 表示"成功"的响应对象
     */
    public static <T> JsonResult<T> ok(T data) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.state = ServiceCode.OK.getValue();
        jsonResult.data = data;
        return jsonResult;
    }

    /**
     * 生成表示"失败"的响应对象
     *
     * @param e 业务异常
     * @return 表示"失败"的响应对象
     */
    public static JsonResult<Void> fail(ServiceException e) {
        return fail(e.getServiceCode(), e.getMessage());
    }

    /**
     * 生成表示"失败"的响应对象
     *
     * @param serviceCode 业务状态码
     * @param message     提示文本
     * @return 表示"失败"的响应对象
     */
    public static JsonResult<Void> fail(ServiceCode serviceCode, String message) {
        JsonResult<Void> jsonResult = new JsonResult<>();
        jsonResult.state = serviceCode.getValue();
        jsonResult.message = message;
        return jsonResult;
    }

}