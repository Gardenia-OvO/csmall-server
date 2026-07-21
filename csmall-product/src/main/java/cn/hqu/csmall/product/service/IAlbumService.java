package cn.hqu.csmall.product.service;

import cn.hqu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.hqu.csmall.product.pojo.param.AlbumUpdateParam;
import cn.hqu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.hqu.csmall.product.pojo.vo.AlbumStandardVO;
import cn.hqu.csmall.product.pojo.vo.PageData;

/**
 * 相册业务接口
 */
public interface IAlbumService {
    /**
     * 新增相册
     * @param albumAddNewParam
     */
    void addNew(AlbumAddNewParam albumAddNewParam) ;

    void deleteById(Long id);

    void setSort(int i);

    void updateById(Long id, AlbumUpdateParam albumUpdateParam);

    PageData<AlbumListItemVO> list(Integer pageNum, Integer pageSize);

    PageData<AlbumListItemVO> list(Integer pageNum);

    AlbumStandardVO getStandardById(Long id);


}
