package cn.hqu.csmall.product.pojo.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class AttributeTemplateListItemVO implements Serializable {
    private Long id;
    private String name;
    private Integer sort;
    private String pinyin;
    private String keywords;
}
