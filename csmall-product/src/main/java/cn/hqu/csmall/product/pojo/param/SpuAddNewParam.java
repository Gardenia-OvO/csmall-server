package cn.hqu.csmall.product.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 新增SPU的参数类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class SpuAddNewParam implements Serializable {

    @NotNull(message = "SPU名称不能为空")
    @ApiModelProperty(value = "SPU名称", required = true, example = "iPhone 15")
    private String name;

    @ApiModelProperty(value = "SPU编号", example = "IP15-001")
    private String typeNumber;

    @ApiModelProperty(value = "标题", example = "iPhone 15 256GB 黑色")
    private String title;

    @ApiModelProperty(value = "简介", example = "Apple最新款智能手机")
    private String description;

    @NotNull(message = "价格不能为空")
    @ApiModelProperty(value = "价格（显示在列表中）", required = true, example = "6999.00")
    private BigDecimal listPrice;

    @ApiModelProperty(value = "当前库存", example = "100")
    private Integer stock;

    @ApiModelProperty(value = "库存预警阈值", example = "10")
    private Integer stockThreshold;

    @ApiModelProperty(value = "计件单位", example = "台")
    private String unit;

    @NotNull(message = "品牌ID不能为空")
    @ApiModelProperty(value = "品牌ID", required = true, example = "1")
    private Long brandId;

    @NotNull(message = "类别ID不能为空")
    @ApiModelProperty(value = "类别ID", required = true, example = "1")
    private Long categoryId;

    @ApiModelProperty(value = "属性模板ID", example = "1")
    private Long attributeTemplateId;

    @ApiModelProperty(value = "相册ID", example = "1")
    private Long albumId;

    @ApiModelProperty(value = "组图URLs，使用JSON格式表示")
    private String pictures;

    @ApiModelProperty(value = "关键词列表，各关键词使用英文的逗号分隔")
    private String keywords;

    @ApiModelProperty(value = "标签列表，各标签使用英文的逗号分隔，原则上最多3个")
    private String tags;

    @NotNull(message = "排序序号不能为空")
    @ApiModelProperty(value = "排序序号", required = true, example = "99")
    private Integer sort;

    @ApiModelProperty(value = "SPU详情，应该使用HTML富文本")
    private String detail;
}
