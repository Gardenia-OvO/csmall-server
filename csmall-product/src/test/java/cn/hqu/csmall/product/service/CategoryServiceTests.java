package cn.hqu.csmall.product.service;


import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.product.pojo.param.CategoryAddNewParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
