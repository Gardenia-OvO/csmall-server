package cn.hqu.csmall.product.service.impl;

import cn.hqu.csmall.product.ex.ServiceException;
import cn.hqu.csmall.product.mapper.CategoryMapper;
import cn.hqu.csmall.product.pojo.entity.Category;
import cn.hqu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.hqu.csmall.product.service.ICategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void addNew(CategoryAddNewParam categoryAddNewParam) throws ServiceException {
        log.debug("开始处理【新增类别】的业务，参数为:{}", categoryAddNewParam);
        //检测类别名称是否被占用
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", categoryAddNewParam.getName());
        int count = categoryMapper.selectCount(queryWrapper);
        log.debug("根据类别名称查询类别表中是否有同名类别，检测结果为：{}", count);
        if (count > 0) {
            String message = "类别名称已经被占用，请更换";
            log.warn(message);
            throw new ServiceException(message);
        }

        //将类别信息插入到数据库中
        Category category = new Category();
        BeanUtils.copyProperties(categoryAddNewParam, category);
        //设置创建时间和修改时间
        category.setGmtCreate(LocalDateTime.now());
        category.setGmtModified(LocalDateTime.now());
        log.debug("准备插入类别信息到数据库中，类别信息为：{}", category);
        categoryMapper.insert(category);
        log.debug("新增类别成功");
    }
}
