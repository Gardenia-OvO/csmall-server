package cn.hqu.csmall.product.service.impl;

import cn.hqu.csmall.product.ex.ServiceException;
import cn.hqu.csmall.product.mapper.BrandMapper;
import cn.hqu.csmall.product.pojo.entity.Brand;
import cn.hqu.csmall.product.pojo.param.BrandAddNewParam;
import cn.hqu.csmall.product.service.IBrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

//@Service表示这是业务层组件
@Slf4j
@Service
public class BrandServiceImpl implements IBrandService {
    @Autowired
    private BrandMapper brandMapper;
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
            throw new ServiceException(message);
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
        brandMapper.deleteById(id);
        log.debug("删除品牌成功");
    }

    @Override
    public void setSort(int i) {

    }
}
