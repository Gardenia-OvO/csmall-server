package cn.hqu.csmall.product.pojo.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class BrandListItemVO implements Serializable {
    private Long id;
    private String name;
    private String pinyin;
    private String logo;
    private String keywords;
    private Integer sort;
    private Integer enable;
    private Integer sales;
    private Integer productCount;
}
