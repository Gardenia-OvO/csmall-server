package cn.hqu.csmall.product.service.impl;

import cn.hqu.csmall.product.ex.ServiceException;
import cn.hqu.csmall.product.pojo.param.CategoryUpdateParam;
import cn.hqu.csmall.product.pojo.vo.CategoryListItemVO;
import cn.hqu.csmall.product.pojo.vo.CategoryStandardVO;
import cn.hqu.csmall.product.pojo.vo.PageData;
import cn.hqu.csmall.product.web.ServiceCode;
import cn.hqu.csmall.product.mapper.CategoryMapper;
import cn.hqu.csmall.product.pojo.entity.Category;
import cn.hqu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.hqu.csmall.product.service.ICategoryService;
import cn.hqu.csmall.product.util.PageInfoToPageDataConverter;
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
        // 父级ID为null时默认为0（根级目录）
        if (category.getParentId() == null) {
            category.setParentId(0L);
        }
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
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
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

    @Override
    public PageData<CategoryListItemVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询类别列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<CategoryListItemVO> list = categoryMapper.list();
        PageInfo<CategoryListItemVO> pageInfo = new PageInfo<>(list);
        PageData<CategoryListItemVO> pageData = PageInfoToPageDataConverter.convert(pageInfo);
        log.debug("处理【查询类别列表】的业务完成，结果：{}", pageData);
        return pageData;
    }

    @Override
    public PageData<CategoryListItemVO> list(Integer pageNum) {
        Integer pageSize = 5;
        log.debug("开始处理【查询类别列表】的业务，页码：{}，每页记录数(默认)：{}", pageNum, pageSize);
        return list(pageNum, pageSize);
    }

    @Override
    public PageData<CategoryListItemVO> search(String name, Long id, Integer pageNum, Integer pageSize) {
        log.debug("开始处理【搜索类别】的业务，名称：{}，ID：{}，页码：{}，每页记录数：{}", name, id, pageNum, pageSize);
        if ((name == null || name.trim().isEmpty()) && id == null) {
            log.debug("搜索参数（名称和ID）均为空，返回空结果");
            return PageData.empty();
        }
        PageHelper.startPage(pageNum, pageSize);
        List<CategoryListItemVO> list = categoryMapper.search(name, id);
        PageInfo<CategoryListItemVO> pageInfo = new PageInfo<>(list);
        PageData<CategoryListItemVO> pageData = PageInfoToPageDataConverter.convert(pageInfo);
        log.debug("处理【搜索类别】的业务完成，结果：{}", pageData);
        return pageData;
    }

    @Override
    public PageData<CategoryListItemVO> search(String name, Long id, Integer pageNum) {
        Integer pageSize = 5;
        log.debug("开始处理【搜索类别】的业务，名称：{}，ID：{}，页码：{}，每页记录数(默认)：{}", name, id, pageNum, pageSize);
        return search(name, id, pageNum, pageSize);
    }

    @Override
    public CategoryStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据id查询商品类别】的业务，id：{}",id);
        CategoryStandardVO categoryStandardVO = categoryMapper.getStandardById(id);
        if(categoryStandardVO == null){
            log.warn("商品类别不存在");
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,"商品类别不存在");
        }
        return categoryStandardVO;
    }

    @Override
    public void updateById(Long id, CategoryUpdateParam categoryUpdateParam) {
        log.debug("开始处理【根据ID修改商品类别信息】的业务，id为:{}", id);
        //检查商品类别是否存在
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        int count = categoryMapper.selectCount(queryWrapper);

        log.debug("根据id查询商品类别表中是否存在该商品类别，检测结果为：{}",count);

        if (count == 0){
            String message = "修改商品类别失败，商品类别数据不存在!";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,message);
        }
        //检查商品类别名称是否重复
        QueryWrapper<Category> queryWrapper02 = new QueryWrapper<>();
        queryWrapper02.eq("name",categoryUpdateParam.getName())
                .ne("id",id);


        count = categoryMapper.selectCount(queryWrapper02);
        log.debug("根据商品类别名称查询是否存在同名商品类别，查询结果：{}",count);
        if (count >0 ){
            String message = "修改商品类别失败，商品类别名称已存在!";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        Category category = new Category();
        BeanUtils.copyProperties(categoryUpdateParam,category);
        category.setId(id);
        category.setGmtModified(LocalDateTime.now());
        categoryMapper.updateById(category);
        log.debug("处理【根据id修改商品类别】的业务完成！");
    }
}
