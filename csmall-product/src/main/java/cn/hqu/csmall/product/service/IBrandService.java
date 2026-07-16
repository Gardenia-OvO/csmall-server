package cn.hqu.csmall.product.service;


import cn.hqu.csmall.product.ex.ServiceException;
import cn.hqu.csmall.product.pojo.param.BrandAddNewParam;

//品牌业务接口
public interface IBrandService {

    void addNew(BrandAddNewParam brandAddNewParam) throws ServiceException;


    void setSort(int i);
}
