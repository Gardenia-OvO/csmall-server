package cn.hqu.csmall.product.pojo.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SkuListItemVO implements Serializable {
    private Long id;
    private Long spuId;
    private String title;
    private String barCode;
    private BigDecimal price;
    private Integer stock;
    private Integer sales;
    private Integer sort;
}
