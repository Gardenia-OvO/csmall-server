package cn.hqu.csmall.product.service.impl;

import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.commons.util.PageInfoToPageDataConverter;
import cn.hqu.csmall.commons.web.ServiceCode;
import cn.hqu.csmall.product.mapper.AlbumMapper;
import cn.hqu.csmall.product.mapper.BrandMapper;
import cn.hqu.csmall.product.mapper.CategoryMapper;
import cn.hqu.csmall.product.mapper.SpuDetailMapper;
import cn.hqu.csmall.product.mapper.SpuMapper;
import cn.hqu.csmall.product.pojo.entity.Spu;
import cn.hqu.csmall.product.pojo.entity.SpuDetail;
import cn.hqu.csmall.product.pojo.param.SpuAddNewParam;
import cn.hqu.csmall.product.pojo.param.SpuUpdateParam;
import cn.hqu.csmall.product.pojo.vo.SpuFullInfoVO;
import cn.hqu.csmall.product.pojo.vo.SpuListItemVO;
import cn.hqu.csmall.product.pojo.vo.SpuStandardVO;
import cn.hqu.csmall.product.service.ISpuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 处理SPU的业务实现类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Service
public class SpuServiceImpl implements ISpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private AlbumMapper albumMapper;

    @Override
    public void addNew(SpuAddNewParam spuAddNewParam) throws ServiceException {
        log.debug("开始处理【新增SPU】的业务，参数：{}", spuAddNewParam);

        // 检查品牌是否存在，并获取品牌名称
        Long brandId = spuAddNewParam.getBrandId();
        QueryWrapper<cn.hqu.csmall.product.pojo.entity.Brand> brandQuery = new QueryWrapper<>();
        brandQuery.eq("id", brandId);
        cn.hqu.csmall.product.pojo.entity.Brand brand = brandMapper.selectOne(brandQuery);
        if (brand == null) {
            String message = "新增SPU失败，品牌数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        // 检查类别是否存在，并获取类别名称
        Long categoryId = spuAddNewParam.getCategoryId();
        QueryWrapper<cn.hqu.csmall.product.pojo.entity.Category> categoryQuery = new QueryWrapper<>();
        categoryQuery.eq("id", categoryId);
        cn.hqu.csmall.product.pojo.entity.Category category = categoryMapper.selectOne(categoryQuery);
        if (category == null) {
            String message = "新增SPU失败，类别数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        // 检查相册是否存在（若提供了相册ID）
        Long albumId = spuAddNewParam.getAlbumId();
        if (albumId != null) {
            QueryWrapper<cn.hqu.csmall.product.pojo.entity.Album> albumQuery = new QueryWrapper<>();
            albumQuery.eq("id", albumId);
            int albumCount = albumMapper.selectCount(albumQuery);
            if (albumCount == 0) {
                String message = "新增SPU失败，相册数据不存在！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
            }
        }

        // 创建SPU对象
        Spu spu = new Spu();
        BeanUtils.copyProperties(spuAddNewParam, spu);
        // 补全冗余字段：品牌名、类别名
        spu.setBrandName(brand.getName());
        spu.setCategoryName(category.getName());
        // 补全默认值
        spu.setSales(0);
        spu.setCommentCount(0);
        spu.setPositiveCommentCount(0);
        spu.setIsDeleted(0);
        spu.setIsPublished(0);
        spu.setIsNewArrival(0);
        spu.setIsRecommend(0);
        spu.setIsChecked(0);

        log.debug("准备插入SPU数据：{}", spu);
        int rows = spuMapper.insert(spu);
        if (rows != 1) {
            String message = "新增SPU失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }

        // 创建SpuDetail对象
        if (spuAddNewParam.getDetail() != null) {
            SpuDetail spuDetail = new SpuDetail();
            spuDetail.setSpuId(spu.getId());
            spuDetail.setDetail(spuAddNewParam.getDetail());
            rows = spuDetailMapper.insert(spuDetail);
            if (rows != 1) {
                String message = "新增SPU失败，服务器忙，请稍后再尝试！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_INSERT, message);
            }
        }

        log.debug("新增SPU成功，ID：{}", spu.getId());
    }

    @Override
    public void updateById(Long id, SpuUpdateParam spuUpdateParam) throws ServiceException {
        log.debug("开始处理【修改SPU】的业务，id：{}，参数：{}", id, spuUpdateParam);
        // 检查SPU是否存在
        QueryWrapper<Spu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        int count = spuMapper.selectCount(queryWrapper);
        if (count == 0) {
            String message = "修改SPU失败，SPU数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        Spu spu = new Spu();
        BeanUtils.copyProperties(spuUpdateParam, spu);
        spu.setId(id);
        // 更新品牌名冗余
        if (spuUpdateParam.getBrandId() != null) {
            cn.hqu.csmall.product.pojo.entity.Brand brand = brandMapper.selectById(spuUpdateParam.getBrandId());
            if (brand != null) {
                spu.setBrandName(brand.getName());
            }
        }
        // 更新类别名冗余
        if (spuUpdateParam.getCategoryId() != null) {
            cn.hqu.csmall.product.pojo.entity.Category category = categoryMapper.selectById(spuUpdateParam.getCategoryId());
            if (category != null) {
                spu.setCategoryName(category.getName());
            }
        }
        spuMapper.updateById(spu);
        log.debug("修改SPU成功");
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【删除SPU】的业务，id：{}", id);
        QueryWrapper<Spu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        int count = spuMapper.selectCount(queryWrapper);
        if (count == 0) {
            String message = "删除SPU失败，SPU数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        spuMapper.deleteById(id);
        log.debug("删除SPU成功");
    }

    @Override
    public void check(Long id, String checkUser) {
        QueryWrapper<Spu> qw = new QueryWrapper<>();
        qw.eq("id", id);
        if (spuMapper.selectCount(qw) == 0)
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "SPU不存在");
        Spu spu = new Spu();
        spu.setId(id);
        spu.setIsChecked(1);
        spu.setCheckUser(checkUser);
        spu.setGmtCheck(LocalDateTime.now());
        spuMapper.updateById(spu);
    }

    @Override
    public SpuStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据ID查询SPU详情】的业务，参数：{}", id);
        SpuStandardVO queryResult = spuMapper.getStandardById(id);
        if (queryResult == null) {
            String message = "根据ID查询SPU详情失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        return queryResult;
    }

    @Override
    public SpuFullInfoVO getFullInfoById(Long id) {
        log.debug("开始处理【根据ID查询SPU完整信息】的业务，参数：{}", id);
        SpuFullInfoVO queryResult = spuMapper.getFullInfoById(id);
        if (queryResult == null) {
            String message = "根据ID查询SPU完整信息失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        return queryResult;
    }

    @Override
    public PageData<SpuListItemVO> list(Integer pageNum) {
        Integer pageSize = 5;
        log.debug("开始处理【查询SPU列表】的业务，页码：{}，每页记录数(默认)：{}", pageNum, pageSize);
        return list(pageNum, pageSize);
    }

    @Override
    public PageData<SpuListItemVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询SPU列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<SpuListItemVO> list = spuMapper.list();
        PageInfo<SpuListItemVO> pageInfo = new PageInfo<>(list);
        PageData<SpuListItemVO> pageData = PageInfoToPageDataConverter.convert(pageInfo);
        log.debug("处理【查询SPU列表】的业务完成，结果：{}", pageData);
        return pageData;
    }
}
