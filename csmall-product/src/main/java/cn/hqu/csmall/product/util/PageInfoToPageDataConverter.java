package cn.hqu.csmall.product.util;

import cn.hqu.csmall.product.pojo.vo.PageData;
import com.github.pagehelper.PageInfo;

/**
 * PageInfo转PageData转换器
 */

public class PageInfoToPageDataConverter {

    public static <T> PageData<T> convert(PageInfo<T> pageInfo) {
        PageData<T> pageData = new PageData<>();
        pageData.setList(pageInfo.getList());
        pageData.setMaxPage(pageInfo.getPages());
        pageData.setPageSize(pageInfo.getPageSize());
        pageData.setTotal(pageInfo.getTotal());
        pageData.setCurrentPage(pageInfo.getPageNum());
        return pageData;
    }
}
