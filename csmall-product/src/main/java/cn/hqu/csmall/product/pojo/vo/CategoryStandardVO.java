package cn.hqu.csmall.product.pojo.vo;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CategoryStandardVO implements Serializable {
    private Long id;
    private Long parentId;
    private String name;
    private String keywords;
    private Integer sort;
    private Integer enable;
    private Integer depth;
    private Integer isParent;
}
