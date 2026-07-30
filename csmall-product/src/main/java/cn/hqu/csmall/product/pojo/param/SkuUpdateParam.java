package cn.hqu.csmall.product.pojo.param;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SkuUpdateParam implements Serializable {
    private String title;
    private String barCode;
    private BigDecimal price;
    private Integer stock;
    private Integer stockThreshold;
    private Integer sort;
    private String specifications;
}
