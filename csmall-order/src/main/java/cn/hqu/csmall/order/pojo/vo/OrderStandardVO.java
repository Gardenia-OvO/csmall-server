package cn.hqu.csmall.order.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class OrderStandardVO implements Serializable {
    private Long id;
    private String orderNo;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String productTitle;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
    private Integer status;
    private String remark;
}
