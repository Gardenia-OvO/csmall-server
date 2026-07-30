package cn.hqu.csmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("pms_sku")
public class Sku implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long spuId;
    private String title;
    private String barCode;
    private Long attributeTemplateId;
    private String specifications;
    private Long albumId;
    private String pictures;
    private BigDecimal price;
    private Integer stock;
    private Integer stockThreshold;
    private Integer sales;
    private Integer commentCount;
    private Integer positiveCommentCount;
    private Integer sort;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
