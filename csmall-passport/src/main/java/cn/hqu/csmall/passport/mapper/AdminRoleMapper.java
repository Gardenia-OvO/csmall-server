package cn.hqu.csmall.passport.mapper;


import cn.hqu.csmall.passport.pojo.entity.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRoleMapper extends BaseMapper<AdminRole> {
    int insertBatch(AdminRole[] adminRoles);
}
