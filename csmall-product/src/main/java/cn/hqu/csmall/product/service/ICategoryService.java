package cn.hqu.csmall.product.service;


import cn.hqu.csmall.product.ex.ServiceException;
import cn.hqu.csmall.product.pojo.param.CategoryAddNewParam;

//类别业务接口
public interface ICategoryService {

    void addNew(CategoryAddNewParam categoryAddNewParam) throws ServiceException;
}
