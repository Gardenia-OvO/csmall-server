package cn.hqu.csmall.product.service;


import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.hqu.csmall.product.pojo.param.CategoryUpdateParam;
import cn.hqu.csmall.product.pojo.vo.CategoryListItemVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CategoryServiceTests {
    @Autowired
    private ICategoryService categoryService;

    @Test
    public void testAddNewSuccess() {
        CategoryAddNewParam param = new CategoryAddNewParam();
        param.setName("测试类别02");
        param.setParentId(0L);
        param.setKeywords("测试,类别");
        param.setSort(1);
        param.setIcon("http://example.com/icon.png");
        param.setEnable(1);
        param.setIsDisplay(1);
        try {
            categoryService.addNew(param);
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
        CategoryAddNewParam param = new CategoryAddNewParam();
        param.setName("测试类别02");  // 与上面用同一个名称
        param.setParentId(0L);
        param.setSort(1);
        param.setEnable(1);
        try {
            categoryService.addNew(param);
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
            categoryService.delete(id);
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
            categoryService.delete(id);
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
            CategoryUpdateParam data = new CategoryUpdateParam();
            data.setName("更新后的类别");
            data.setParentId(0L);
            data.setKeywords("更新,类别");
            data.setSort(50);
            data.setIcon("http://example.com/updated.png");
            data.setEnable(1);
            data.setIsDisplay(1);
            categoryService.updateById(id, data);
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
    public void testSetEnable() {
        try {
            Long id = 1L;
            categoryService.setEnable(id);
            System.out.println("启用成功！");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        } catch (Throwable e) {
            System.out.println("未知错误！");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testSetDisable() {
        try {
            Long id = 1L;
            categoryService.setDisable(id);
            System.out.println("禁用成功！");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        } catch (Throwable e) {
            System.out.println("未知错误！");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testList() {
        Integer pageNum = 1;
        PageData<CategoryListItemVO> pageData = categoryService.list(pageNum);
        List<CategoryListItemVO> list = pageData.getList();
        System.out.println("查询列表数量: " + list.size());
        System.out.println("总记录数: " + pageData.getTotal());
        System.out.println("总页数: " + pageData.getMaxPage());
        System.out.println("当前页码: " + pageData.getCurrentPage());
        System.out.println("每页记录数: " + pageData.getPageSize());
        for (CategoryListItemVO item : list) {
            System.out.println(item);
        }
    }

}
