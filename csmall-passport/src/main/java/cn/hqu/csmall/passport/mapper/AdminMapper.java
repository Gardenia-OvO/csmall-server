package cn.hqu.csmall.passport.mapper;


import cn.hqu.csmall.passport.pojo.entity.Admin;
import cn.hqu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.hqu.csmall.passport.pojo.vo.AdminLoginInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper extends BaseMapper<Admin> {

    List<AdminListItemVO> list();

    AdminLoginInfoVO getLoginInfoByUsername(String username);

}
