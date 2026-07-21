package cn.hqu.csmall.product.service.impl;

import cn.hqu.csmall.product.mapper.AlbumMapper;
import cn.hqu.csmall.product.mapper.PictureMapper;
import cn.hqu.csmall.product.pojo.entity.Album;
import cn.hqu.csmall.product.pojo.entity.Picture;
import cn.hqu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.hqu.csmall.product.pojo.vo.AlbumStandardVO;
import cn.hqu.csmall.product.service.IAlbumService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.hqu.csmall.product.ex.ServiceException;
import cn.hqu.csmall.product.web.ServiceCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.hqu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.hqu.csmall.product.pojo.vo.PageData;
import cn.hqu.csmall.product.util.PageInfoToPageDataConverter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.time.LocalDateTime;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

//@Service表示这是业务层组件
@Slf4j
@Service
public class AlbumServiceImpl implements IAlbumService {
    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public void addNew(AlbumAddNewParam albumAddNewParam) throws ServiceException {
        log.debug("开始处理【新增相册】的业务，参数为:{}",albumAddNewParam);
        //检测相册名称是否被占用（调用select）  QueryWrapper：条件对象  拼接where部分内容
        QueryWrapper<Album> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",albumAddNewParam.getName());
        int count = albumMapper.selectCount(queryWrapper);
        log.debug("根据相册名称查询相册表中是否有同名相册，检测结果为：{}",count);
        if(count > 0){
            String message = "相册名称已经被占用，请更换";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }
        //select * from tb_album where name = #{name}

        //将相册信息插入到数据库中（调用insert）
        Album album = new Album();
        //将相册信息从参数对象albumAddNewParam中拷贝到album对象中
        BeanUtils.copyProperties(albumAddNewParam,album);
        //设置相册的创建时间以及修改时间，保证数据的完整性
        album.setGmtCreated(LocalDateTime.now());
        album.setGmtModified(LocalDateTime.now());
        log.debug("准备插入相册信息到数据库中，相册信息为：{}",album);
        //调用insert
        albumMapper.insert(album);
        log.debug("新增相册成功");
    }

    @Override
    public void deleteById(Long id) {
        log.debug("开始处理【删除相册】的业务，id为:{}", id);
        //检查相册是否存在
        QueryWrapper<Album> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        int count = albumMapper.selectCount(queryWrapper);

        log.debug("根据相册id查询相册表中是否存在该相册，检测结果为：{}",count);

        if (count == 0){
            String message = "删除相册失败，相册数据不存在!";
            log.warn(message);
            throw new ServiceException(ServiceCode.NOT_FOUND,message);
        }
        //检查是否有图片与该相册相连
        QueryWrapper<Picture> queryWrapper02 = new QueryWrapper<>();
        queryWrapper02.eq("album_id",id);

        count = pictureMapper.selectCount(queryWrapper02);
        log.debug("根据相册ID查询图片表是否存在关联的图片，查询结果：{}",count);
        if (count >0 ){
            String message = "删除相册失败，该相册存在关联图片!";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }
        albumMapper.deleteById(id);
        log.debug("处理【根据id删除相册】的业务完成！");
    }

    @Override
    public void setSort(int i) {

    }

    @Override
    public PageData<AlbumListItemVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询相册列表】的业务，页码：{}，每页记录数：{}",pageNum,pageSize);
        PageHelper.startPage(pageNum,pageSize);
        List<AlbumListItemVO> list = albumMapper.list();
        PageInfo<AlbumListItemVO> pageInfo = new PageInfo<>(list);
        PageData<AlbumListItemVO> pageData = PageInfoToPageDataConverter.convert(pageInfo);
        log.debug("处理【查询相册列表】的业务完成，结果：{}",pageData);
        return pageData;
    }

    @Override
    public PageData<AlbumListItemVO> list(Integer pageNum) {
        Integer pageSize = 5;
        log.debug("开始处理【查询相册列表】的业务，页码：{}，每页记录数(默认)：{}",pageNum,pageSize);
        return list(pageNum,pageSize);
    }

    @Override
    public AlbumStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据id查询相册】的业务，id：{}",id);
        AlbumStandardVO albumStandardVO = albumMapper.getStandardById(id);
        if(albumStandardVO == null){
            log.warn("相册不存在");
            throw new ServiceException(ServiceCode.NOT_FOUND,"相册不存在");
        }
        return albumStandardVO;
    }
}
