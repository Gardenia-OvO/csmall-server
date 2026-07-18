package cn.hqu.csmall.product.pojo.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageData<T> implements Serializable {
    private Integer currentPage;
    private Integer pageSize;
    private Integer maxPage;
    private Long total;
    private List<T> list;
}
