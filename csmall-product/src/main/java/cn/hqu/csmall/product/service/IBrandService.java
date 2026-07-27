package cn.hqu.csmall.product.service;


import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.product.pojo.param.BrandAddNewParam;
import cn.hqu.csmall.product.pojo.param.BrandUpdateParam;
import cn.hqu.csmall.product.pojo.vo.BrandListItemVO;
import cn.hqu.csmall.product.pojo.vo.BrandStandardVO;

//品牌业务接口
public interface IBrandService {

    void addNew(BrandAddNewParam brandAddNewParam) throws ServiceException;

    void delete(Long id);

    void updateById(Long id, BrandUpdateParam brandUpdateParam);

    PageData<BrandListItemVO> list(Integer pageNum, Integer pageSize);

    PageData<BrandListItemVO> list(Integer pageNum);

    BrandStandardVO getStandardById(Long id);

    PageData<BrandListItemVO> search(String name, Long id, Integer pageNum, Integer pageSize);

    PageData<BrandListItemVO> search(String name, Long id, Integer pageNum);
}
