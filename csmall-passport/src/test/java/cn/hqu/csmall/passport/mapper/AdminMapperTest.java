package cn.hqu.csmall.passport.mapper;


import cn.hqu.csmall.passport.mapper.AdminMapper;
import cn.hqu.csmall.passport.pojo.entity.Admin;
import cn.hqu.csmall.passport.pojo.vo.AdminLoginInfoVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AdminMapperTest {
    @Autowired
    private AdminMapper mapper;
    @Test
    void testInsertAlbum(){
        Admin data = new Admin();
        data.setUsername("测试用户01");
        data.setPassword("测试密码123456");
        data.setDescription("测试用户简介");
        data.setGmtCreate(LocalDateTime.now());
        data.setGmtModified(LocalDateTime.now());
        System.out.println("插入之前：id="+data.getId());
        int rows = mapper.insert(data);
        System.out.println("插入之后：id="+data.getId());
        System.out.println("受影响的行数：" + rows);
    }

    @Test
    void testGetLoginInfoByUsername(){
        AdminLoginInfoVO loginInfo = mapper.getLoginInfoByUsername("root");
        System.out.println(loginInfo);
    }

}
