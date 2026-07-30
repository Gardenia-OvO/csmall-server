package cn.hqu.csmall.merchant.service;

import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.merchant.pojo.param.MerchantAddNewParam;
import cn.hqu.csmall.merchant.pojo.param.MerchantUpdateParam;
import cn.hqu.csmall.merchant.pojo.vo.MerchantListItemVO;
import cn.hqu.csmall.merchant.pojo.vo.MerchantStandardVO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IMerchantService {

    void addNew(MerchantAddNewParam merchantAddNewParam);

    void delete(Long id);

    void updateById(Long id, MerchantUpdateParam merchantUpdateParam);

    void approve(Long id);

    void disable(Long id);

    void enable(Long id);

    PageData<MerchantListItemVO> list(Integer pageNum, Integer pageSize);

    PageData<MerchantListItemVO> list(Integer pageNum);

    PageData<MerchantListItemVO> search(String name, Long id, Integer pageNum, Integer pageSize);

    PageData<MerchantListItemVO> search(String name, Long id, Integer pageNum);

    MerchantStandardVO getStandardById(Long id);
}
