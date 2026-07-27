package cn.hqu.csmall.order.pojo.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderListItemVO implements Serializable {
    private Long id;
    private String orderNo;
    private String buyerName;
    private String buyerPhone;
    private String productTitle;
    private Integer quantity;
    private BigDecimal totalAmount;
    private Integer status;
}
