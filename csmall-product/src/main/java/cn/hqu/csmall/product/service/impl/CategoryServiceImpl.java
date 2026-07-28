package cn.hqu.csmall.product.service.impl;

import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.web.ServiceCode;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.product.pojo.param.CategoryUpdateParam;
import cn.hqu.csmall.product.pojo.vo.CategoryListItemVO;
import cn.hqu.csmall.product.pojo.vo.CategoryStandardVO;
import cn.hqu.csmall.product.pojo.vo.CategoryTreeVO;
import cn.hqu.csmall.product.mapper.BrandCategoryMapper;
import cn.hqu.csmall.product.mapper.CategoryAttributeTemplateMapper;
import cn.hqu.csmall.product.mapper.CategoryMapper;
import cn.hqu.csmall.product.mapper.SpuMapper;
import cn.hqu.csmall.product.pojo.entity.BrandCategory;
import cn.hqu.csmall.product.pojo.entity.Category;
import cn.hqu.csmall.product.pojo.entity.CategoryAttributeTemplate;
import cn.hqu.csmall.product.pojo.entity.Spu;
import cn.hqu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.hqu.csmall.product.service.ICategoryService;
import cn.hqu.csmall.commons.util.PageInfoToPageDataConverter;
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

    @Autowired
    private BrandCategoryMapper brandCategoryMapper;

    @Autowired
    private CategoryAttributeTemplateMapper categoryAttributeTemplateMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Override
    public void addNew(CategoryAddNewParam categoryAddNewParam) throws ServiceException {
        log.debug("开始处理【新增类别】的业务，参数为:{}", categoryAddNewParam);

        // 父级ID为null时默认为0（根级目录）
        Long parentId = categoryAddNewParam.getParentId();
        if (parentId == null) {
            parentId = 0L;
            categoryAddNewParam.setParentId(0L);
        }

        // 检查父级类别是否存在，并计算depth
        Integer depth = 1;              // 默认根级深度
        Category parentCategory = null; // 保存父级类别，后续更新isParent时复用
        if (parentId != 0) {
            parentCategory = categoryMapper.selectById(parentId);
            if (parentCategory == null) {
                String message = "添加类别失败，父级类别不存在！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
            }
            log.debug("父级类别信息：{}", parentCategory);
            depth = parentCategory.getDepth() + 1;
        }

        // 检测类别名称在同一个父级下是否重复
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", categoryAddNewParam.getName())
                .eq("parent_id", parentId);
        int count = categoryMapper.selectCount(queryWrapper);
        log.debug("根据类别名称和父级ID查询，检测结果为：{}", count);
        if (count > 0) {
            String message = "添加类别失败，该父级下已存在同名类别！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        // 将类别信息插入到数据库中
        Category category = new Category();
        BeanUtils.copyProperties(categoryAddNewParam, category);
        category.setDepth(depth);
        category.setIsParent(0);
        category.setGmtCreate(LocalDateTime.now());
        category.setGmtModified(LocalDateTime.now());
        log.debug("准备插入类别信息到数据库中，类别信息为：{}", category);
        categoryMapper.insert(category);
        log.debug("新增类别成功");

        // 如果父级类别的isParent为0，则需要更新为1（复用之前的查询结果）
        if (parentId != 0 && parentCategory.getIsParent() == 0) {
            Category updateParent = new Category();
            updateParent.setId(parentId);
            updateParent.setIsParent(1);
            updateParent.setGmtModified(LocalDateTime.now());
            categoryMapper.updateById(updateParent);
            log.debug("已将父级类别(id={})的isParent更新为1", parentId);
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【删除类别】的业务，id为:{}", id);

        // 检查类别是否存在（通过getStandardById判断，不存在会抛异常）
        CategoryStandardVO standardVO = categoryMapper.getStandardById(id);
        if (standardVO == null) {
            String message = "删除类别失败，类别数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        log.debug("类别存在，信息：{}", standardVO);

        // 获取完整的Category实体，用于后续的isParent和parentId判断
        Category currentCategory = categoryMapper.selectById(id);

        // 检查是否有子级类别（通过is_parent字段判断）
        if (currentCategory.getIsParent() == 1) {
            String message = "删除类别失败，该类别存在子级类别！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        // 检查类别是否关联了品牌
        QueryWrapper<BrandCategory> brandCategoryWrapper = new QueryWrapper<>();
        brandCategoryWrapper.eq("category_id", id);
        int count = brandCategoryMapper.selectCount(brandCategoryWrapper);
        log.debug("根据类别ID查询品牌关联数量：{}", count);
        if (count > 0) {
            String message = "删除类别失败，该类别存在关联品牌！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        // 检查类别是否关联了属性模板
        QueryWrapper<CategoryAttributeTemplate> catAttrWrapper = new QueryWrapper<>();
        catAttrWrapper.eq("category_id", id);
        count = categoryAttributeTemplateMapper.selectCount(catAttrWrapper);
        log.debug("根据类别ID查询属性模板关联数量：{}", count);
        if (count > 0) {
            String message = "删除类别失败，该类别存在关联属性模板！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        // 检查是否有SPU关联了该类别
        QueryWrapper<Spu> spuWrapper = new QueryWrapper<>();
        spuWrapper.eq("category_id", id);
        count = spuMapper.selectCount(spuWrapper);
        log.debug("根据类别ID查询SPU关联数量：{}", count);
        if (count > 0) {
            String message = "删除类别失败，该类别存在关联SPU！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        Long parentId = currentCategory.getParentId();

        // 执行删除
        categoryMapper.deleteById(id);
        log.debug("处理【根据id删除类别】的业务完成！");

        // 如果删除的是父级中的最后一个子级，更新父级的is_parent为0
        if (parentId != null && parentId != 0) {
            QueryWrapper<Category> childrenWrapper = new QueryWrapper<>();
            childrenWrapper.eq("parent_id", parentId);
            count = categoryMapper.selectCount(childrenWrapper);
            log.debug("父级类别(id={})剩余子级数量：{}", parentId, count);
            if (count == 0) {
                Category updateParent = new Category();
                updateParent.setId(parentId);
                updateParent.setIsParent(0);
                updateParent.setGmtModified(LocalDateTime.now());
                categoryMapper.updateById(updateParent);
                log.debug("已将父级类别(id={})的isParent更新为0", parentId);
            }
        }
    }

    @Override
    public void setEnable(Long id) {
        updateEnableById(id, 1);
    }

    @Override
    public void setDisable(Long id) {
        updateEnableById(id, 0);
    }

    private void updateEnableById(Long id, Integer enable) {
        String message = ENABLE_TEXT[enable] + "类别失败，类别数据不存在！";
        log.debug("开始处理【{}类别】的业务，id:{}", ENABLE_TEXT[enable], id);
        // 检查类别是否存在
        CategoryStandardVO standardVO = categoryMapper.getStandardById(id);
        if (standardVO == null) {
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        Category category = new Category();
        category.setId(id);
        category.setEnable(enable);
        category.setGmtModified(LocalDateTime.now());
        categoryMapper.updateById(category);
        log.debug("处理【{}类别】的业务完成！", ENABLE_TEXT[enable]);
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
    public PageData<CategoryListItemVO> search(String name, Long id, Long parentId, Integer pageNum, Integer pageSize) {
        log.debug("开始处理【搜索类别】的业务，名称：{}，ID：{}，parentId：{}，页码：{}，每页记录数：{}", name, id, parentId, pageNum, pageSize);
        if ((name == null || name.trim().isEmpty()) && id == null && parentId == null) {
            log.debug("搜索参数均为空，返回空结果");
            return PageData.empty();
        }
        PageHelper.startPage(pageNum, pageSize);
        List<CategoryListItemVO> list = categoryMapper.search(name, id, parentId);
        PageInfo<CategoryListItemVO> pageInfo = new PageInfo<>(list);
        PageData<CategoryListItemVO> pageData = PageInfoToPageDataConverter.convert(pageInfo);
        log.debug("处理【搜索类别】的业务完成，结果：{}", pageData);
        return pageData;
    }

    @Override
    public PageData<CategoryListItemVO> search(String name, Long id, Long parentId, Integer pageNum) {
        Integer pageSize = 5;
        log.debug("开始处理【搜索类别】的业务，名称：{}，ID：{}，parentId：{}，页码：{}，每页记录数(默认)：{}", name, id, parentId, pageNum, pageSize);
        return search(name, id, parentId, pageNum, pageSize);
    }

    @Override
    public CategoryStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据id查询商品类别】的业务，id：{}",id);
        CategoryStandardVO categoryStandardVO = categoryMapper.getStandardById(id);
        if(categoryStandardVO == null){
            log.warn("商品类别不存在");
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND,"商品类别不存在");
        }
        return categoryStandardVO;
    }

    @Override
    public List<CategoryListItemVO> getChildrenByParentId(Long parentId) {
        log.debug("开始处理【根据父级ID查询子级类别列表】的业务，parentId：{}", parentId);
        List<CategoryListItemVO> list = categoryMapper.getChildrenByParentId(parentId);
        log.debug("处理【根据父级ID查询子级类别列表】的业务完成，结果数量：{}", list.size());
        return list;
    }

    @Override
    public List<CategoryTreeVO> getTree() {
        List<Category> all = categoryMapper.selectList(null);
        return buildTree(all, 0L);
    }

    private List<CategoryTreeVO> buildTree(List<Category> all, Long parentId) {
        List<CategoryTreeVO> result = new java.util.ArrayList<>();
        for (Category c : all) {
            if (c.getParentId().equals(parentId)) {
                CategoryTreeVO vo = new CategoryTreeVO();
                vo.setValue(c.getId());
                vo.setLabel(c.getName());
                if (c.getIsParent() == 1) {
                    vo.setChildren(buildTree(all, c.getId()));
                }
                result.add(vo);
            }
        }
        return result;
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
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND,message);
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
            throw new ServiceException(ServiceCode.ERROR_CONFLICT,message);
        }

        Category category = new Category();
        BeanUtils.copyProperties(categoryUpdateParam,category);
        category.setId(id);
        category.setGmtModified(LocalDateTime.now());
        categoryMapper.updateById(category);
        log.debug("处理【根据id修改商品类别】的业务完成！");
    }
}
