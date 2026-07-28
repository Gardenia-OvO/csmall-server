package cn.hqu.csmall.product.pojo.vo;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class CategoryTreeVO implements Serializable {
    private Long value;
    private String label;
    private List<CategoryTreeVO> children;
}
