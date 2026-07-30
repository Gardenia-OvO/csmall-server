package cn.hqu.csmall.product.service;

import cn.hqu.csmall.product.pojo.param.SkuAddNewParam;
import cn.hqu.csmall.product.pojo.param.SkuUpdateParam;
import cn.hqu.csmall.product.pojo.vo.SkuListItemVO;
import cn.hqu.csmall.product.pojo.vo.SkuStandardVO;

import java.util.List;

public interface ISkuService {
    void addNew(SkuAddNewParam param);
    void updateById(Long id, SkuUpdateParam param);
    void delete(Long id);
    SkuStandardVO getStandardById(Long id);
    List<SkuListItemVO> listBySpuId(Long spuId);
}
