package cn.hqu.csmall.order.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("oms_order")
public class Order implements Serializable {

    @TableId(type = IdType.AUTO)
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
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
