package cn.hqu.csmall.passport.mapper;


import cn.hqu.csmall.passport.pojo.entity.Admin;
import cn.hqu.csmall.passport.pojo.entity.AdminRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AdminRoleMapperTest {
    @Autowired
    private AdminRoleMapper mapper;
    @Test
    public void testInsert(){
        AdminRole[] adminRoles = new AdminRole[5];
        for (long i = 0; i < adminRoles.length; i++) {
            AdminRole data = new AdminRole();
            data.setAdminId(91L);
            data.setRoleId(i+1);
            data.setGmtCreate(LocalDateTime.now());
            data.setGmtModified(LocalDateTime.now());
            adminRoles[(int)i] = data;
        }
        int rows = mapper.insertBatch(adminRoles);
        System.out.println("受影响的行数：" + rows);
    }
}
