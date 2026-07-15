package cn.hqu.csmall.product.service;


import cn.hqu.csmall.product.pojo.param.AlbumAddNewParam;
import com.google.protobuf.ServiceException;

//相册业务接口
public interface IAlbumService {
   /**
    * 新增相册
    * @param albumAddNewParam **/
    void addNew(AlbumAddNewParam albumAddNewParam) throws ServiceException;

    void setSort(int i);
}
