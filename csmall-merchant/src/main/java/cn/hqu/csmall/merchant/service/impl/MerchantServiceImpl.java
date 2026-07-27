package cn.hqu.csmall.merchant.service.impl;

import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.web.ServiceCode;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.commons.util.PageInfoToPageDataConverter;
import cn.hqu.csmall.merchant.mapper.MerchantMapper;
import cn.hqu.csmall.merchant.pojo.entity.Merchant;
import cn.hqu.csmall.merchant.pojo.param.MerchantAddNewParam;
import cn.hqu.csmall.merchant.pojo.param.MerchantUpdateParam;
import cn.hqu.csmall.merchant.pojo.vo.MerchantListItemVO;
import cn.hqu.csmall.merchant.pojo.vo.MerchantStandardVO;
import cn.hqu.csmall.merchant.service.IMerchantService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class MerchantServiceImpl implements IMerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public void addNew(MerchantAddNewParam merchantAddNewParam) {
        log.debug("开始处理【新增商家】的业务，参数为:{}", merchantAddNewParam);
        // 检测商家名称是否被占用
        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", merchantAddNewParam.getName());
        int count = merchantMapper.selectCount(queryWrapper);
        log.debug("根据商家名称查询表中是否有同名商家，检测结果为：{}", count);
        if (count > 0) {
            String message = "商家名称已经被占用，请更换";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        // 插入数据
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(merchantAddNewParam, merchant);
        merchant.setStatus(0);  // 默认待审核
        merchant.setSales(0);
        merchant.setGmtCreate(LocalDateTime.now());
        merchant.setGmtModified(LocalDateTime.now());
        log.debug("准备插入商家信息到数据库中，商家信息为：{}", merchant);
        merchantMapper.insert(merchant);
        log.debug("新增商家成功");
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【删除商家】的业务，id为:{}", id);
        // 检查商家是否存在
        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        int count = merchantMapper.selectCount(queryWrapper);
        log.debug("根据商家id查询表中是否存在该商家，检测结果为：{}", count);
        if (count == 0) {
            String message = "删除商家失败，商家数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        // TODO: 检查是否有SPU关联该商家（后续扩展SPU关联商家后启用）
        merchantMapper.deleteById(id);
        log.debug("处理【根据id删除商家】的业务完成！");
    }

    @Override
    public void updateById(Long id, MerchantUpdateParam merchantUpdateParam) {
        log.debug("开始处理【根据ID修改商家信息】的业务，id为:{}", id);
        // 检查商家是否存在
        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        int count = merchantMapper.selectCount(queryWrapper);
        log.debug("根据商家id查询表中是否存在该商家，检测结果为：{}", count);
        if (count == 0) {
            String message = "修改商家失败，商家数据不存在!";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        // 检查商家名称是否重复
        QueryWrapper<Merchant> queryWrapper02 = new QueryWrapper<>();
        queryWrapper02.eq("name", merchantUpdateParam.getName()).ne("id", id);
        count = merchantMapper.selectCount(queryWrapper02);
        log.debug("根据商家名称查询是否存在同名商家，查询结果：{}", count);
        if (count > 0) {
            String message = "修改商家失败，商家名称已存在!";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(merchantUpdateParam, merchant);
        merchant.setId(id);
        merchant.setGmtModified(LocalDateTime.now());
        merchantMapper.updateById(merchant);
        log.debug("处理【根据id修改商家】的业务完成！");
    }

    @Override
    public void approve(Long id) {
        log.debug("开始处理【审核通过商家】的业务，id为:{}", id);
        Merchant merchant = merchantMapper.selectById(id);
        if (merchant == null) {
            String message = "审核商家失败，商家数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        if (merchant.getStatus() != 0) {
            String message = "审核商家失败，仅待审核状态的商家可审核通过！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        Merchant updateMerchant = new Merchant();
        updateMerchant.setId(id);
        updateMerchant.setStatus(1);
        updateMerchant.setGmtModified(LocalDateTime.now());
        merchantMapper.updateById(updateMerchant);
        log.debug("处理【审核通过商家】的业务完成！");
    }

    @Override
    public void disable(Long id) {
        log.debug("开始处理【禁用商家】的业务，id为:{}", id);
        Merchant merchant = merchantMapper.selectById(id);
        if (merchant == null) {
            String message = "禁用商家失败，商家数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        Merchant updateMerchant = new Merchant();
        updateMerchant.setId(id);
        updateMerchant.setStatus(2);
        updateMerchant.setGmtModified(LocalDateTime.now());
        merchantMapper.updateById(updateMerchant);
        log.debug("处理【禁用商家】的业务完成！");
    }

    @Override
    public PageData<MerchantListItemVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询商家列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<MerchantListItemVO> list = merchantMapper.list();
        PageInfo<MerchantListItemVO> pageInfo = new PageInfo<>(list);
        PageData<MerchantListItemVO> pageData = PageInfoToPageDataConverter.convert(pageInfo);
        log.debug("处理【查询商家列表】的业务完成，结果：{}", pageData);
        return pageData;
    }

    @Override
    public PageData<MerchantListItemVO> list(Integer pageNum) {
        Integer pageSize = 5;
        log.debug("开始处理【查询商家列表】的业务，页码：{}，每页记录数(默认)：{}", pageNum, pageSize);
        return list(pageNum, pageSize);
    }

    @Override
    public PageData<MerchantListItemVO> search(String name, Long id, Integer pageNum, Integer pageSize) {
        log.debug("开始处理【搜索商家】的业务，名称：{}，ID：{}，页码：{}，每页记录数：{}", name, id, pageNum, pageSize);
        if ((name == null || name.trim().isEmpty()) && id == null) {
            log.debug("搜索参数（名称和ID）均为空，返回空结果");
            return PageData.empty();
        }
        PageHelper.startPage(pageNum, pageSize);
        List<MerchantListItemVO> list = merchantMapper.search(name, id);
        PageInfo<MerchantListItemVO> pageInfo = new PageInfo<>(list);
        PageData<MerchantListItemVO> pageData = PageInfoToPageDataConverter.convert(pageInfo);
        log.debug("处理【搜索商家】的业务完成，结果：{}", pageData);
        return pageData;
    }

    @Override
    public PageData<MerchantListItemVO> search(String name, Long id, Integer pageNum) {
        Integer pageSize = 5;
        log.debug("开始处理【搜索商家】的业务，名称：{}，ID：{}，页码：{}，每页记录数(默认)：{}", name, id, pageNum, pageSize);
        return search(name, id, pageNum, pageSize);
    }

    @Override
    public MerchantStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据id查询商家】的业务，id：{}", id);
        MerchantStandardVO merchantStandardVO = merchantMapper.getStandardById(id);
        if (merchantStandardVO == null) {
            log.warn("商家不存在");
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "商家不存在");
        }
        return merchantStandardVO;
    }
}
