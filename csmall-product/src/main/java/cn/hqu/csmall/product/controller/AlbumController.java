package cn.hqu.csmall.product.controller;


import cn.hqu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.hqu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.hqu.csmall.product.pojo.vo.PageData;
import cn.hqu.csmall.product.service.IAlbumService;
import cn.hqu.csmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags = "相册管理模块")
@Validated
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private IAlbumService albumService;

    @PostMapping("/add-new")
    @ApiOperation("新增相册")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid @RequestBody AlbumAddNewParam albumAddNewParam) {
        log.debug("开始处理【添加相册】的请求，参数:{}", albumAddNewParam);
        albumService.addNew(albumAddNewParam);
        log.debug("处理【添加相册】的请求，完成！");
        return JsonResult.created("新增相册成功");
    }

    @PostMapping("/delete")
    @ApiOperation("删除相册")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "相册id", required = true, dataType = "long")
    public JsonResult delete(@Range(min = 1, message = "根据id删除相册，请提供合法的id")
                             @RequestParam Long id) {
        log.debug("开始处理【删除相册】的请求，id:{}", id);
        albumService.delete(id);
        log.debug("处理【删除相册】的请求，完成！");
        return JsonResult.ok();
    }

    @GetMapping("/list")
    @ApiOperation("查询相册列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult list(@Range(min = 1, message = "查询相册列表失败，请提供正确的页码值！")
                           @RequestParam(defaultValue = "1") Integer pageNum) {
        log.debug("开始处理【查询相册列表】的业务，参数：{}", pageNum);
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        PageData<AlbumListItemVO> pageData = albumService.list(pageNum);
        return JsonResult.ok(pageData);
    }
}