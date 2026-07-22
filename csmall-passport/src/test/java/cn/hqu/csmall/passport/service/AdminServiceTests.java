package cn.hqu.csmall.passport.service;

import cn.hqu.csmall.passport.ex.ServiceException;
import cn.hqu.csmall.passport.pojo.param.AdminAddNewParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//测试时会在SpringBoot环境下测试
@SpringBootTest
public class AdminServiceTests {
    @Autowired
    private IAdminService service;
    //单元测试
    //测试相册新增
    @Test
    public void testSaveNewAdmin() {
        AdminAddNewParam data = new AdminAddNewParam();
        data.setUsername("test48");
        data.setPassword("123456");
        data.setAvatar("test04.jpg");
        data.setDescription("test04的描述");
        data.setRoleIds(new Long[]{1L,2L,3L});
        try {
            service.addNew(data);
            System.out.println("添加成功");
        }catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }catch (Throwable e){
            System.out.println("未知异常");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


}
