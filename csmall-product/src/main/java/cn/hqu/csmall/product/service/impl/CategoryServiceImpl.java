package cn.hqu.csmall.product.service.impl;

import cn.hqu.csmall.product.ex.ServiceException;
import cn.hqu.csmall.product.web.ServiceCode;
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

    @Override
    public void delete(Long id) {
        log.debug("开始处理【删除类别】的业务，id为:{}", id);
        // 检查类别是否存在
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        int count = categoryMapper.selectCount(queryWrapper);
        log.debug("根据类别id查询类别表中是否存在该类别，检测结果为：{}", count);
        if (count == 0) {
            String message = "删除类别失败，类别数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.NOT_FOUND, message);
        }
        // 检查是否有子级类别与该类别关联
        QueryWrapper<Category> queryWrapper02 = new QueryWrapper<>();
        queryWrapper02.eq("parent_id", id);
        count = categoryMapper.selectCount(queryWrapper02);
        log.debug("根据类别ID查询是否存在子级类别，查询结果：{}", count);
        if (count > 0) {
            String message = "删除类别失败，该类别存在子级类别！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }
        categoryMapper.deleteById(id);
        log.debug("处理【根据id删除类别】的业务完成！");
    }
}
