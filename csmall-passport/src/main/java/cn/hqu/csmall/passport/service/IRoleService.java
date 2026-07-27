package cn.hqu.csmall.passport.service;

import cn.hqu.csmall.passport.pojo.param.RoleAddNewParam;
import cn.hqu.csmall.passport.pojo.param.RoleUpdateParam;
import cn.hqu.csmall.passport.pojo.vo.RoleListItemVO;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IRoleService {
    void addNew(RoleAddNewParam roleAddNewParam);

    PageData<RoleListItemVO> list(Integer pageNum, Integer pageSize);

    PageData<RoleListItemVO> list(Integer pageNum);

    void update(RoleUpdateParam roleUpdateParam);

    void delete(Long id);
}
