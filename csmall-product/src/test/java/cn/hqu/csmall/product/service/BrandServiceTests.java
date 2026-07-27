package cn.hqu.csmall.product.service;


import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.product.pojo.param.BrandAddNewParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BrandServiceTests {
    @Autowired
    private IBrandService brandService;

    @Test
    public void testAddNewSuccess() {
        BrandAddNewParam param = new BrandAddNewParam();
        param.setName("测试品牌02");
        param.setPinyin("ceshipinpai02");
        param.setKeywords("测试,品牌");
        param.setSort(1);
        param.setEnable(1);
        try {
            brandService.addNew(param);
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
        BrandAddNewParam param = new BrandAddNewParam();
        param.setName("测试品牌02");  // 与上面用同一个名称
        param.setPinyin("ceshipinpai02");
        param.setSort(1);
        param.setEnable(1);
        try {
            brandService.addNew(param);
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
