package cn.hqu.csmall.product.controller;


import cn.hqu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.hqu.csmall.product.service.IAlbumService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.google.protobuf.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @ApiOperationSupport(order = 100)
    public String addNew(AlbumAddNewParam albumAddNewParam) throws ServiceException {
            albumService.addNew(albumAddNewParam);
            return "添加成功！";
    }
    @PostMapping("/delete")
    @ApiOperation("删除相册")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ID",value = "相册id",required = true,dataType = "long"),
            @ApiImplicitParam(name = "UserID",value = "用户id",required = true,dataType = "long")
    })

    public String delete(Long id) {
        throw new RuntimeException("功能未实现");
    }
}
