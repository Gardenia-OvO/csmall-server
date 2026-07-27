package cn.hqu.csmall.product.service;


import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.hqu.csmall.product.pojo.param.CategoryUpdateParam;
import cn.hqu.csmall.product.pojo.vo.CategoryListItemVO;
import cn.hqu.csmall.product.pojo.vo.CategoryStandardVO;

import java.util.List;

//类别业务接口
public interface ICategoryService {

    String[] ENABLE_TEXT = {"禁用", "启用"};

    void addNew(CategoryAddNewParam categoryAddNewParam) throws ServiceException;

    void delete(Long id);

    void updateById(Long id, CategoryUpdateParam categoryUpdateParam);

    void setEnable(Long id);

    void setDisable(Long id);

    PageData<CategoryListItemVO> list(Integer pageNum, Integer pageSize);

    PageData<CategoryListItemVO> list(Integer pageNum);

    PageData<CategoryListItemVO> search(String name, Long id, Integer pageNum, Integer pageSize);

    PageData<CategoryListItemVO> search(String name, Long id, Integer pageNum);

    CategoryStandardVO getStandardById(Long id);

    List<CategoryListItemVO> getChildrenByParentId(Long parentId);
}
