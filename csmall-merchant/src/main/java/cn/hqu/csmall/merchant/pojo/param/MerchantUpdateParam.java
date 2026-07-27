package cn.hqu.csmall.merchant.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class MerchantUpdateParam implements Serializable {

    @NotNull(message = "商家ID不能为空")
    @ApiModelProperty(value = "商家ID", required = true, example = "1")
    private Long id;

    @NotNull(message = "商家名称不能为空")
    @ApiModelProperty(value = "商家名称", required = true, example = "华为旗舰店")
    private String name;

    @ApiModelProperty(value = "联系人", example = "张三")
    private String contactPerson;

    @ApiModelProperty(value = "联系电话", example = "13800138000")
    private String phone;

    @ApiModelProperty(value = "邮箱", example = "merchant@example.com")
    private String email;

    @ApiModelProperty(value = "地址", example = "北京市朝阳区")
    private String address;

    @ApiModelProperty(value = "商家Logo", example = "http://example.com/logo.png")
    private String logo;

    @ApiModelProperty(value = "商家简介", example = "知名品牌官方旗舰店")
    private String description;

    @NotNull(message = "排序不能为空")
    @Range(min = 0, max = 99, message = "排序序号必须在0-99之间")
    @ApiModelProperty(value = "排序序号", required = true, example = "1")
    private Integer sort;
}
