package cn.hqu.csmall.product.service.impl;

import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.web.ServiceCode;
import cn.hqu.csmall.product.mapper.SkuMapper;
import cn.hqu.csmall.product.pojo.entity.Sku;
import cn.hqu.csmall.product.pojo.param.SkuAddNewParam;
import cn.hqu.csmall.product.pojo.param.SkuUpdateParam;
import cn.hqu.csmall.product.pojo.vo.SkuListItemVO;
import cn.hqu.csmall.product.pojo.vo.SkuStandardVO;
import cn.hqu.csmall.product.service.ISkuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SkuServiceImpl implements ISkuService {

    @Autowired
    private SkuMapper skuMapper;

    @Override
    public void addNew(SkuAddNewParam param) {
        Sku sku = new Sku();
        BeanUtils.copyProperties(param, sku);
        sku.setSales(0);
        sku.setCommentCount(0);
        sku.setPositiveCommentCount(0);
        skuMapper.insert(sku);
    }

    @Override
    public void updateById(Long id, SkuUpdateParam param) {
        if (skuMapper.selectCount(new QueryWrapper<Sku>().eq("id", id)) == 0)
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "SKU不存在");
        Sku sku = new Sku();
        BeanUtils.copyProperties(param, sku);
        sku.setId(id);
        skuMapper.updateById(sku);
    }

    @Override
    public void delete(Long id) {
        if (skuMapper.selectCount(new QueryWrapper<Sku>().eq("id", id)) == 0)
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "SKU不存在");
        skuMapper.deleteById(id);
    }

    @Override
    public SkuStandardVO getStandardById(Long id) {
        SkuStandardVO vo = skuMapper.getStandardById(id);
        if (vo == null) throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "SKU不存在");
        return vo;
    }

    @Override
    public List<SkuListItemVO> listBySpuId(Long spuId) {
        return skuMapper.listBySpuId(spuId);
    }
}
