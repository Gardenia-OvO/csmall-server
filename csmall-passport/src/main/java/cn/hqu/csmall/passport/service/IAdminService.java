package cn.hqu.csmall.passport.service;

import cn.hqu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.hqu.csmall.passport.pojo.param.AdminLoginInfoParam;
import cn.hqu.csmall.passport.pojo.param.AdminUpdateParam;
import cn.hqu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.hqu.csmall.passport.security.AdminDetail;
import cn.hqu.csmall.product.pojo.vo.PageData;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface IAdminService {
    void addNew(AdminAddNewParam adminAddNewParam);

    PageData<AdminListItemVO> list(Integer pageNum, Integer pageSize);

    PageData<AdminListItemVO> list(Integer pageNum);

    String login(AdminLoginInfoParam adminLoginInfoParam);

    void update(AdminUpdateParam adminUpdateParam);

    void delete(Long id);
}