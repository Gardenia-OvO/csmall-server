package cn.hqu.csmall.product.mapper;

import cn.hqu.csmall.product.pojo.entity.Album;
import cn.hqu.csmall.product.pojo.entity.Picture;
import cn.hqu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.hqu.csmall.product.pojo.vo.AlbumStandardVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import org.apache.ibatis.annotations.Param;

import java.util.List;

@Repository
public interface AlbumMapper extends BaseMapper<Album>{
    AlbumStandardVO getStandardById(Long id);

    List<AlbumListItemVO> list();

    List<AlbumListItemVO> search(@Param("name") String name, @Param("id") Long id);

    int selectCount(QueryWrapper<Picture> queryWrapper02);
}
