package cn.hqu.csmall.product.pojo.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class PictureListItemVO implements Serializable {
    private Long id;
    private Long albumId;
    private String url;
    private String description;
    private Integer isCover;
    private Integer sort;
}
