package cn.hqu.csmall.passport.service;

import cn.hqu.csmall.passport.pojo.entity.Admin;
import cn.hqu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.hqu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.hqu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.hqu.csmall.product.pojo.vo.PageData;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface IAdminService {
    void addNew(AdminAddNewParam adminAddNewParam);

    PageData<AdminListItemVO> list(Integer pageNum, Integer pageSize);

    PageData<AdminListItemVO> list(Integer pageNum);
}