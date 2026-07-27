package cn.hqu.csmall.product.service.impl;

import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.web.ServiceCode;
import cn.hqu.csmall.product.mapper.BrandMapper;
import cn.hqu.csmall.product.mapper.SpuMapper;
import cn.hqu.csmall.product.pojo.entity.Brand;
import cn.hqu.csmall.product.pojo.entity.Spu;
import cn.hqu.csmall.product.pojo.param.BrandAddNewParam;
import cn.hqu.csmall.product.pojo.param.BrandUpdateParam;
import cn.hqu.csmall.product.pojo.vo.BrandListItemVO;
import cn.hqu.csmall.product.pojo.vo.BrandStandardVO;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.commons.util.PageInfoToPageDataConverter;
import cn.hqu.csmall.product.service.IBrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//@Service表示这是业务层组件
@Slf4j
@Service
public class BrandServiceImpl implements IBrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SpuMapper spuMapper;
    @Override
    public void addNew(BrandAddNewParam brandAddNewParam) throws ServiceException {
        log.debug("开始处理【新增品牌】的业务，参数为:{}",brandAddNewParam);
        //检测品牌名称是否被占用（调用select）  QueryWrapper：条件对象  拼接where部分内容
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",brandAddNewParam.getName());
        int count = brandMapper.selectCount(queryWrapper);
        log.debug("根据品牌名称查询品牌表中是否有同名品牌，检测结果为：{}",count);
        if(count > 0){
            String message = "品牌名称已经被占用，请更换";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        //select * from tb_album where name = #{name}

        //将品牌信息插入到数据库中（调用insert）
        Brand brand = new Brand();
        //将品牌信息从参数对象brandAddNewParam中拷贝到brand对象中
        BeanUtils.copyProperties(brandAddNewParam,brand);
        //设置品牌的默认值
        brand.setSales(0);
        brand.setProductCount(0);
        brand.setCommentCount(0);
        brand.setPositiveCommentCount(0);
        //设置品牌的创建时间以及修改时间，保证数据的完整性
        brand.setGmtCreated(LocalDateTime.now());
        brand.setGmtModified(LocalDateTime.now());
        log.debug("准备插入品牌信息到数据库中，品牌信息为：{}",brand);
        //调用insert
        brandMapper.insert(brand);
        log.debug("新增品牌成功");
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【删除品牌】的业务，id为:{}", id);
        // 检查品牌是否存在
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        int count = brandMapper.selectCount(queryWrapper);
        log.debug("根据品牌id查询品牌表中是否存在该品牌，检测结果为：{}", count);
        if (count == 0) {
            String message = "删除品牌失败，品牌数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        // 检查是否有SPU与该品牌关联
        QueryWrapper<Spu> queryWrapper02 = new QueryWrapper<>();
        queryWrapper02.eq("brand_id", id);
        count = spuMapper.selectCount(queryWrapper02);
        log.debug("根据品牌ID查询SPU表是否存在关联的SPU，查询结果：{}", count);
        if (count > 0) {
            String message = "删除品牌失败，该品牌存在关联SPU！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        brandMapper.deleteById(id);
        log.debug("处理【根据id删除品牌】的业务完成！");
    }

    @Override
    public void updateById(Long id, BrandUpdateParam brandUpdateParam) {
        log.debug("开始处理【根据ID修改品牌信息】的业务，id为:{}", id);
        //检查品牌是否存在
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        int count = brandMapper.selectCount(queryWrapper);

        log.debug("根据品牌id查询品牌表中是否存在该品牌，检测结果为：{}",count);

        if (count == 0){
            String message = "修改品牌失败，品牌数据不存在!";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND,message);
        }
        //检查品牌名称是否重复
        QueryWrapper<Brand> queryWrapper02 = new QueryWrapper<>();
        queryWrapper02.eq("name",brandUpdateParam.getName())
                .ne("id",id);


        count = brandMapper.selectCount(queryWrapper02);
        log.debug("根据品牌名称查询是否存在同名品牌，查询结果：{}",count);
        if (count >0 ){
            String message = "修改品牌失败，品牌名称已存在!";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT,message);
        }

        Brand brand = new Brand();
        BeanUtils.copyProperties(brandUpdateParam,brand);
        brand.setId(id);
        brand.setGmtModified(LocalDateTime.now());
        brandMapper.updateById(brand);
        log.debug("处理【根据id修改品牌】的业务完成！");
    }

    @Override
    public PageData<BrandListItemVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询品牌列表】的业务，页码：{}，每页记录数：{}",pageNum,pageSize);
        PageHelper.startPage(pageNum,pageSize);
        List<BrandListItemVO> list = brandMapper.list();
        PageInfo<BrandListItemVO> pageInfo = new PageInfo<>(list);
        PageData<BrandListItemVO> pageData = PageInfoToPageDataConverter.convert(pageInfo);
        log.debug("处理【查询品牌列表】的业务完成，结果：{}",pageData);
        return pageData;
    }

    @Override
    public PageData<BrandListItemVO> list(Integer pageNum) {
        Integer pageSize = 5;
        log.debug("开始处理【查询品牌列表】的业务，页码：{}，每页记录数(默认)：{}",pageNum,pageSize);
        return list(pageNum,pageSize);
    }

    @Override
    public BrandStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据id查询品牌】的业务，id：{}",id);
        BrandStandardVO brandStandardVO = brandMapper.getStandardById(id);
        if(brandStandardVO == null){
            log.warn("品牌不存在");
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND,"品牌不存在");
        }
        return brandStandardVO;
    }

    @Override
    public PageData<BrandListItemVO> search(String name, Long id, Integer pageNum, Integer pageSize){
        log.debug("开始处理【搜索品牌】的业务，名称：{}，ID：{}，页码：{}，每页记录数：{}", name, id, pageNum, pageSize);
        if ((name == null || name.trim().isEmpty()) && id == null) {
            log.debug("搜索参数（名称和ID）均为空，返回空结果");
            return PageData.empty();
        }
        PageHelper.startPage(pageNum, pageSize);
        List<BrandListItemVO> list = brandMapper.search(name, id);
        PageInfo<BrandListItemVO> pageInfo = new PageInfo<>(list);
        PageData<BrandListItemVO> pageData = PageInfoToPageDataConverter.convert(pageInfo);
        log.debug("处理【搜索品牌】的业务结束，结果：{}",pageData);
        return pageData;
    }

    @Override
    public PageData<BrandListItemVO> search(String name, Long id, Integer pageNum){
        Integer pageSize = 5;
        log.debug("开始处理【搜索品牌】的业务，名称：{}，ID：{}，页码：{}，每页记录数(默认)：{}", name, id, pageNum, pageSize);
        return search(name, id, pageNum, pageSize);
    }

}
