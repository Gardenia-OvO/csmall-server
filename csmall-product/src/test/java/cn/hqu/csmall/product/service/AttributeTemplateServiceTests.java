package cn.hqu.csmall.product.service;

import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.product.pojo.param.AttributeTemplateAddNewParam;
import cn.hqu.csmall.product.pojo.param.AttributeTemplateUpdateParam;
import cn.hqu.csmall.product.pojo.vo.AttributeTemplateListItemVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AttributeTemplateServiceTests {

    @Autowired
    private IAttributeTemplateService service;

    @Test
    public void testAddNewSuccess() {
        AttributeTemplateAddNewParam param = new AttributeTemplateAddNewParam();
        param.setName("测试属性模板01");
        param.setPinyin("ceshishuxingmoban01");
        param.setKeywords("测试,属性模板");
        param.setSort(1);
        try {
            service.addNew(param);
            System.out.println("添加成功！");
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
    public void testAddNewDuplicateName() {
        AttributeTemplateAddNewParam param = new AttributeTemplateAddNewParam();
        param.setName("测试属性模板01");  // 重复名称
        param.setPinyin("ceshishuxingmoban02");
        param.setSort(1);
        try {
            service.addNew(param);
            System.out.println("添加成功！");
        } catch (ServiceException e) {
            System.out.println("预期异常：" + e.getMessage());
        } catch (Throwable e) {
            System.out.println("未知错误！");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteSuccess() {
        try {
            Long id = 1L;
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

    @Test
    public void testDeleteNotFound() {
        try {
            Long id = 99999999L;
            service.delete(id);
            System.out.println("删除成功！");
        } catch (ServiceException e) {
            System.out.println("预期异常：" + e.getMessage());
        } catch (Throwable e) {
            System.out.println("未知错误！");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateSuccess() {
        try {
            Long id = 1L;
            AttributeTemplateUpdateParam data = new AttributeTemplateUpdateParam();
            data.setName("更新后的属性模板");
            data.setPinyin("gengxinhou");
            data.setKeywords("更新,测试");
            data.setSort(50);
            service.updateById(id, data);
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
    public void testUpdateNotFound() {
        try {
            Long id = 99999999L;
            AttributeTemplateUpdateParam data = new AttributeTemplateUpdateParam();
            data.setName("不存在的模板");
            data.setPinyin("bucunzai");
            data.setKeywords("测试");
            data.setSort(1);
            service.updateById(id, data);
            System.out.println("修改成功！");
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
        PageData<AttributeTemplateListItemVO> pageData = service.list(pageNum);
        List<AttributeTemplateListItemVO> list = pageData.getList();
        System.out.println("查询列表数量: " + list.size());
        System.out.println("总记录数: " + pageData.getTotal());
        System.out.println("总页数: " + pageData.getMaxPage());
        System.out.println("当前页码: " + pageData.getCurrentPage());
        System.out.println("每页记录数: " + pageData.getPageSize());
        for (AttributeTemplateListItemVO item : list) {
            System.out.println(item);
        }
    }
}
