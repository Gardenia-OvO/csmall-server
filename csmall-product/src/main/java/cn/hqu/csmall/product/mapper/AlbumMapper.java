package cn.hqu.csmall.product.mapper;

import cn.hqu.csmall.product.pojo.entity.Album;
import cn.hqu.csmall.product.pojo.vo.AlbumListItemVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumMapper extends BaseMapper<Album>{

    List<AlbumListItemVO> list();
}
