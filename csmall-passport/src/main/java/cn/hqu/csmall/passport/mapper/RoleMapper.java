package cn.hqu.csmall.passport.mapper;

import cn.hqu.csmall.passport.pojo.entity.Role;
import cn.hqu.csmall.passport.pojo.vo.RoleListItemVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<Role> {

    List<RoleListItemVO> list();
}
