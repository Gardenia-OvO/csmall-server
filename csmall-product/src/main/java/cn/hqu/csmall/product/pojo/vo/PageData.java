package cn.hqu.csmall.product.pojo.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
public class PageData<T> implements Serializable {
    private Integer currentPage;
    private Integer pageSize;
    private Integer maxPage;
    private Long total;
    private List<T> list;

    public static <T> PageData<T> empty() {
        PageData<T> pageData = new PageData<>();
        pageData.setCurrentPage(1);
        pageData.setPageSize(0);
        pageData.setMaxPage(0);
        pageData.setTotal(0L);
        pageData.setList(Collections.emptyList());
        return pageData;
    }
}
