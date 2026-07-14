package cn.hqu.csmall.product.mapper;

import cn.hqu.csmall.product.pojo.entity.Album;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumMapper {
    /**
     * 插入相册数据
     * @param album
     * @return
     */
    int insert(Album album);
}
