package cn.hqu.csmall.product.controller;


import cn.hqu.csmall.product.ex.ServiceException;
import cn.hqu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.hqu.csmall.product.service.IAlbumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "相册管理模块")
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private IAlbumService albumService;

    @PostMapping("/add-new")
    @ApiOperation("新增相册")
    public String addNew(AlbumAddNewParam albumAddNewParam) {
        try{
            albumService.addNew(albumAddNewParam);
            return "添加成功！";
        }catch (ServiceException e){
            return e.getMessage();
        }catch (Throwable e){
            return "添加失败，出现未知错误";
        }
    }
}
