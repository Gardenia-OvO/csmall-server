package cn.hqu.csmall.commons.pojo.vo;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class PageData<T> implements Serializable {
    //每页记录数
    private Integer pageSize;

    //记录总数
    private Integer total;

    //当前页码
    private Integer currentPage;

    //最大页码
    private Integer maxPage;

    //列表数据
    private List<T> list;
}
