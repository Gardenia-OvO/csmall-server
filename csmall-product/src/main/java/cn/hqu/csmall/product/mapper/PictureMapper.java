package cn.hqu.csmall.product.mapper;


import cn.hqu.csmall.product.pojo.entity.Picture;
import cn.hqu.csmall.product.pojo.vo.PictureListItemVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PictureMapper extends BaseMapper<Picture> {
    List<PictureListItemVO> listByAlbumId(@Param("albumId") Long albumId);
}
