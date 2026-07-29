package cn.hqu.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SpuUpdateParam implements Serializable {
    @NotNull(message = "SPU ID不能为空")
    @ApiModelProperty(value = "SPU ID", required = true, example = "1")
    private Long id;
    private String name;
    private String typeNumber;
    private String title;
    private String description;
    private BigDecimal listPrice;
    private Integer stock;
    private Integer stockThreshold;
    private String unit;
    private Long brandId;
    private Long categoryId;
    private Long attributeTemplateId;
    private Long albumId;
    private String pictures;
    private String keywords;
    private String tags;
    private Integer sort;
    private String detail;
}
