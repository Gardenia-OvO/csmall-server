package cn.hqu.csmall.product.service;

import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.hqu.csmall.product.pojo.param.AlbumUpdateParam;
import cn.hqu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

//测试时会在SpringBoot环境下测试
@SpringBootTest
public class AlbumServiceTests {
    @Autowired
    private IAlbumService albumService;
    //单元测试
    //测试相册新增
    @Test
    public void testSaveNewAlbum() {
        AlbumAddNewParam albumAddNewParam = new AlbumAddNewParam();
        albumAddNewParam.setName("测试相册04");
        albumAddNewParam.setDescription("测试相册04的描述");
        albumAddNewParam.setSort(100);
        try {
            albumService.addNew(albumAddNewParam);
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
    void list(){
        Integer pageNum = 1;
        PageData<AlbumListItemVO> pageData =
                albumService.list(pageNum);
        List<AlbumListItemVO> list = pageData.getList();
        System.out.println("查询列表数量" + list.size());
        System.out.println("总记录数" + pageData.getTotal());
        System.out.println("总页数" + pageData.getMaxPage());
        System.out.println("当前页码" + pageData.getCurrentPage());
        System.out.println("每页记录数" + pageData.getPageSize());
        for (AlbumListItemVO albumListItemVO : list) {
            System.out.println(albumListItemVO);
        }
    }

    @Test
    void deleteById() {
        try {
            Long id = 7L;
            albumService.deleteById(id);
            System.out.println("删除成功");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getServiceCode().getValue());
            // e.printStackTrace();
        } catch (Throwable e) {
            System.out.println("未知异常");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    void updateById() {
        try {
            Long id = 1L;
            AlbumUpdateParam data = new AlbumUpdateParam();
            data.setName ("华为Mate20的相册");
            data.setDescription("测试相册9日的描述");
            data.setSort(100);
            albumService.updateById(id, data) ;
            System.out.println("修改成功");
        }catch (ServiceException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getServiceCode().getValue());
        }catch (Throwable e) {
            System.out.println("未知异常");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
