package cn.hqu.csmall.product.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * SPU完整信息VO类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
public class SpuFullInfoVO implements Serializable {

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
    private String brandName;
    private Long categoryId;
    private String categoryName;
    private Long attributeTemplateId;
    private Long albumId;
    private String pictures;
    private String keywords;
    private String tags;
    private Integer sales;
    private Integer commentCount;
    private Integer positiveCommentCount;
    private Integer sort;
    private Integer isDeleted;
    private Integer isPublished;
    private Integer isNewArrival;
    private Integer isRecommend;
    private Integer isChecked;
    private String checkUser;
    private LocalDateTime gmtCheck;
    private String detail;
}
