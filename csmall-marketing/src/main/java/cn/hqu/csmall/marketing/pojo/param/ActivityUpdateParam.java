package cn.hqu.csmall.marketing.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ActivityUpdateParam implements Serializable {
    @NotNull @ApiModelProperty(value = "活动ID", required = true)
    private Long id;
    @NotNull @ApiModelProperty(value = "活动名称", required = true)
    private String title;
    @ApiModelProperty(value = "活动类型")
    private Integer type;
    @ApiModelProperty(value = "优惠规则")
    private String discountRule;
    @ApiModelProperty(value = "排序号")
    private Integer sort;
}
