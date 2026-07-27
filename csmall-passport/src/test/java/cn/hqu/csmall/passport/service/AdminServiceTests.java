package cn.hqu.csmall.passport.service;

import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.hqu.csmall.passport.pojo.param.AdminPasswordUpdateParam;
import cn.hqu.csmall.passport.pojo.param.AdminUpdateParam;
import cn.hqu.csmall.passport.pojo.vo.AdminListItemVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

    @Test
    public void testChangePasswordSuccess() {
        AdminPasswordUpdateParam param = new AdminPasswordUpdateParam();
        param.setId(3L);  // 使用一个存在的管理员ID
        param.setPassword("newTestPassword456");
        try {
            service.changePassword(param);
            System.out.println("修改密码成功！");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Throwable e) {
            System.out.println("未知错误！");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testChangePasswordNotFound() {
        AdminPasswordUpdateParam param = new AdminPasswordUpdateParam();
        param.setId(90L);  // 不存在的ID
        param.setPassword("newTestPassword456");
        try {
            service.changePassword(param);
            System.out.println("修改密码成功！");
        } catch (ServiceException e) {
            System.out.println("预期异常：" + e.getMessage());
        } catch (Throwable e) {
            System.out.println("未知错误！");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testList() {
        Integer pageNum = 1;
        PageData<AdminListItemVO> pageData = service.list(pageNum);
        List<AdminListItemVO> list = pageData.getList();
        System.out.println("查询列表数量: " + list.size());
        System.out.println("总记录数: " + pageData.getTotal());
        System.out.println("总页数: " + pageData.getMaxPage());
        System.out.println("当前页码: " + pageData.getCurrentPage());
        System.out.println("每页记录数: " + pageData.getPageSize());
        for (AdminListItemVO item : list) {
            System.out.println(item);
        }
    }

    @Test
    public void testUpdateSuccess() {
        try {
            AdminUpdateParam data = new AdminUpdateParam();
            data.setId(90L);
            data.setNickname("测试更新昵称");
            data.setPhone("13900000001");
            data.setEmail("updated@test.com");
            data.setDescription("更新后的描述");
            data.setEnable(1);
            service.update(data);
            System.out.println("修改成功！");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getServiceCode().getValue());
        } catch (Throwable e) {
            System.out.println("未知错误！");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteSuccess() {
        try {
            Long id = 90L;
            service.delete(id);
            System.out.println("删除成功！");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getServiceCode().getValue());
        } catch (Throwable e) {
            System.out.println("未知错误！");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
