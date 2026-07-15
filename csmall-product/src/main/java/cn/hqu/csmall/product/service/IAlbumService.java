package cn.hqu.csmall.product.service;


import cn.hqu.csmall.product.pojo.param.AlbumAddNewParam;

//相册业务接口
public interface IAlbumService {
   /**
    * 新增相册
    * @param albumAddNewParam **/
    void addNew(AlbumAddNewParam albumAddNewParam);

    void setSort(int i);
}
