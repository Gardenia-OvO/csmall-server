package cn.hqu.csmall.product.service;


import cn.hqu.csmall.product.ex.ServiceException;
import cn.hqu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.hqu.csmall.product.pojo.param.CategoryUpdateParam;
import cn.hqu.csmall.product.pojo.vo.CategoryListItemVO;
import cn.hqu.csmall.product.pojo.vo.CategoryStandardVO;
import cn.hqu.csmall.product.pojo.vo.PageData;

//类别业务接口
public interface ICategoryService {

    void addNew(CategoryAddNewParam categoryAddNewParam) throws ServiceException;

    void delete(Long id);

    void updateById(Long id, CategoryUpdateParam categoryUpdateParam);

    PageData<CategoryListItemVO> list(Integer pageNum, Integer pageSize);

    PageData<CategoryListItemVO> list(Integer pageNum);

    CategoryStandardVO getStandardById(Long id);
}
