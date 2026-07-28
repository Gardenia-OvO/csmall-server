package cn.hqu.csmall.product.service;

import cn.hqu.csmall.product.pojo.param.PictureAddNewParam;
import cn.hqu.csmall.product.pojo.vo.PictureListItemVO;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public interface IPictureService {
    void addNew(PictureAddNewParam param);
    List<PictureListItemVO> listByAlbumId(Long albumId);
    void delete(Long id);
}
