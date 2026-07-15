package cn.hqu.csmall.product.service;


import cn.hqu.csmall.product.ex.ServiceException;
import cn.hqu.csmall.product.pojo.param.AlbumAddNewParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//测试时会在SpringBoot环境下进行
@SpringBootTest
public class AlbumServiceTests {
    @Autowired
    private IAlbumService albumService;

    //单元测试
    @Test
    public void testSaveNewAlbum(){
        AlbumAddNewParam albumAddNewParam = new AlbumAddNewParam();
        albumAddNewParam.setName("测试相册04");
        albumAddNewParam.setDescription("测试相册04的描述");
        albumService.setSort(100);
        try {
            albumService.addNew(albumAddNewParam);
            System.out.println("添加成功！");
        }catch (ServiceException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }catch(Throwable e){
            System.out.println("未知错误！");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
