package cn.hqu.csmall.marketing.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ActivityAddNewParam implements Serializable {
    @NotNull @ApiModelProperty(value = "活动名称", required = true, example = "618年中大促")
    private String title;
    @ApiModelProperty(value = "活动类型：0=满减 1=折扣 2=秒杀 3=拼团", example = "0")
    private Integer type;
    @ApiModelProperty(value = "优惠规则", example = "满300减50")
    private String discountRule;
    @ApiModelProperty(value = "排序号", example = "1")
    private Integer sort;
}
