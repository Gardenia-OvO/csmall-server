package cn.hqu.csmall.product.pojo.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryListItemVO implements Serializable {
    private Long id;
    private Long parentId;
    private String name;
    private String keywords;
    private Integer sort;
    private Integer enable;
    private Integer depth;
    private Integer isParent;
}
