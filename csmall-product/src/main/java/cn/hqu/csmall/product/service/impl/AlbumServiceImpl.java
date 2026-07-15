package cn.hqu.csmall.product.service.impl;

import cn.hqu.csmall.product.mapper.AlbumMapper;
import cn.hqu.csmall.product.pojo.entity.Album;
import cn.hqu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.hqu.csmall.product.service.IAlbumService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.hqu.csmall.product.ex.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;

//@Service表示这是业务层组件
@Slf4j
@Service
public class AlbumServiceImpl implements IAlbumService {
    @Autowired
    private AlbumMapper albumMapper;
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
            throw new ServiceException(message);
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
    public void setSort(int i) {

    }
}
