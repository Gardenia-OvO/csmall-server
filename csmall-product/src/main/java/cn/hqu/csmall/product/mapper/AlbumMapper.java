package cn.hqu.csmall.product.mapper;

import cn.hqu.csmall.product.pojo.entity.Album;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumMapper extends BaseMapper<Album>{
    /**
     * 插入相册数据
     * @param album
     * @return
     */
    int insert(Album album);
}
