package cn.hqu.csmall.product.service;

import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.product.pojo.param.SpuAddNewParam;
import cn.hqu.csmall.product.pojo.param.SpuUpdateParam;
import cn.hqu.csmall.product.pojo.vo.SpuFullInfoVO;
import cn.hqu.csmall.product.pojo.vo.SpuListItemVO;
import cn.hqu.csmall.product.pojo.vo.SpuStandardVO;

public interface ISpuService {

    void addNew(SpuAddNewParam spuAddNewParam) throws ServiceException;

    void updateById(Long id, SpuUpdateParam spuUpdateParam) throws ServiceException;

    void delete(Long id);

    SpuStandardVO getStandardById(Long id);

    SpuFullInfoVO getFullInfoById(Long id);

    PageData<SpuListItemVO> list(Integer pageNum);

    PageData<SpuListItemVO> list(Integer pageNum, Integer pageSize);
}
