package cn.hqu.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SkuAddNewParam implements Serializable {
    @NotNull(message = "SPU ID不能为空")
    @ApiModelProperty(value = "SPU ID", required = true)
    private Long spuId;
    @ApiModelProperty(value = "标题", example = "iPhone 15 Pro Max 256GB 原色")
    private String title;
    @ApiModelProperty(value = "条形码", example = "6901234567890")
    private String barCode;
    @ApiModelProperty(value = "属性模板ID")
    private Long attributeTemplateId;
    @ApiModelProperty(value = "规格属性(JSON)")
    private String specifications;
    @ApiModelProperty(value = "相册ID")
    private Long albumId;
    @ApiModelProperty(value = "图片URLs(JSON)")
    private String pictures;
    @ApiModelProperty(value = "单价", example = "9999.00")
    private BigDecimal price;
    @ApiModelProperty(value = "库存", example = "100")
    private Integer stock;
    @ApiModelProperty(value = "预警阈值", example = "10")
    private Integer stockThreshold;
    @ApiModelProperty(value = "排序", example = "99")
    private Integer sort;
}
