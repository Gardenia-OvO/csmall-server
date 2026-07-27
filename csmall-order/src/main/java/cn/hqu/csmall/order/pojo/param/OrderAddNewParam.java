package cn.hqu.csmall.order.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderAddNewParam implements Serializable {
    @NotNull @ApiModelProperty(value = "买家名称", required = true, example = "张三")
    private String buyerName;
    @NotNull @ApiModelProperty(value = "买家电话", required = true, example = "13800138000")
    private String buyerPhone;
    @NotNull @ApiModelProperty(value = "收货地址", required = true, example = "北京市朝阳区")
    private String buyerAddress;
    @NotNull @ApiModelProperty(value = "商品名称", required = true, example = "华为Mate60 Pro")
    private String productTitle;
    @NotNull @ApiModelProperty(value = "数量", required = true, example = "1")
    private Integer quantity;
    @NotNull @ApiModelProperty(value = "单价", required = true, example = "6999.00")
    private BigDecimal unitPrice;
    @ApiModelProperty(value = "备注", example = "请尽快发货")
    private String remark;
}
