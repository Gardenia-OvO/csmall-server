package cn.hqu.csmall.product.controller;


import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.commons.security.LoginPrincipal;
import cn.hqu.csmall.commons.web.JsonResult;
import cn.hqu.csmall.product.pojo.param.AlbumAddNewParam;
import cn.hqu.csmall.product.pojo.param.AlbumUpdateParam;
import cn.hqu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.hqu.csmall.product.pojo.vo.AlbumStandardVO;
import cn.hqu.csmall.product.pojo.param.PictureAddNewParam;
import cn.hqu.csmall.product.pojo.vo.PictureListItemVO;
import cn.hqu.csmall.product.service.IAlbumService;
import cn.hqu.csmall.product.service.IPictureService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags = "相册管理模块")
@Validated
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private IAlbumService albumService;

    @Autowired
    private IPictureService pictureService;

    @PostMapping("/add-new")
    @ApiOperation("新增相册")
    @PreAuthorize("hasAuthority('/pms/album/add-new')")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid @RequestBody AlbumAddNewParam albumAddNewParam,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【添加相册】的请求，参数:{}", albumAddNewParam);
        albumService.addNew(albumAddNewParam);
        log.debug("处理【添加相册】的请求，完成！");
        return JsonResult.created("新增相册成功");
    }

    @PostMapping("/delete")
    @ApiOperation("删除相册")
    @PreAuthorize("hasAuthority('/pms/album/delete')")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "相册id", required = true, dataType = "long")
    public JsonResult delete(@Range(min = 1, message = "根据id删除相册，请提供合法的id")
                             @RequestParam Long id,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【删除相册】的请求，id:{}", id);
        albumService.deleteById(id);
        log.debug("处理【删除相册】的请求，完成！");
        return JsonResult.ok("删除相册成功");
    }

    @PostMapping("/update")
    @ApiOperation("修改相册")
    @PreAuthorize("hasAuthority('/pms/album/update')")
    @ApiOperationSupport(order = 300)
    public JsonResult update(@Valid @RequestBody AlbumUpdateParam albumUpdateParam,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【修改相册】的请求，参数为：{}", albumUpdateParam);
        albumService.updateById(albumUpdateParam.getId(), albumUpdateParam);
        log.debug("处理【修改相册】的请求，完成！");
        return JsonResult.ok();
    }

    @GetMapping("/list")
    @ApiOperation("查询相册列表")
    @PreAuthorize("hasAuthority('/pms/album/read')")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult list(@Range(min = 1, message = "查询相册列表失败，请提供正确的页码值！")
                           @RequestParam(defaultValue = "1") Integer pageNum,
                           @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}",user.getUsername());
        log.debug("当事人id:{}",user.getId());
        log.debug("开始处理【查询相册列表】的业务，参数：{}", pageNum);
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        PageData<AlbumListItemVO> pageData = albumService.list(pageNum);
        return JsonResult.ok(pageData);
    }

    @GetMapping("/search")
    @ApiOperation("搜索相册")
    @PreAuthorize("hasAuthority('/pms/album/read')")
    @ApiOperationSupport(order = 430)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "相册名称（关键词搜索）", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "相册ID（关键词搜索）", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult search(@RequestParam(required = false) String name,
                             @RequestParam(required = false) Long id,
                             @Range(min = 1, message = "搜索相册失败，请提供正确的页码值！")
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【搜索相册】的请求，名称：{}，ID：{}，页码：{}", name, id, pageNum);
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        PageData<AlbumListItemVO> pageData = albumService.search(name, id, pageNum);
        return JsonResult.ok(pageData);
    }


    @GetMapping("/standard")
    @ApiOperation("查询相册详细信息")
    @PreAuthorize("hasAuthority('/pms/album/read')")
    @ApiOperationSupport(order = 450)
    @ApiImplicitParam(name = "id", value = "相册id", required = true, dataType = "long")
    public JsonResult standard(@Range(min = 1, message = "根据id查询相册，请提供合法的id")
                             @RequestParam Long id,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【查询相册信息】的请求，id:{}", id);
        AlbumStandardVO result = albumService.getStandardById(id);
        log.debug("处理【查询相册信息】的请求，完成！");
        return JsonResult.ok(result);
    }

    // ========== 图片管理 ==========

    @PostMapping("/picture/add-new")
    @ApiOperation("添加图片到相册")
    @PreAuthorize("hasAuthority('/pms/album/add-new')")
    @ApiOperationSupport(order = 500)
    public JsonResult addPicture(@Valid @RequestBody PictureAddNewParam param,
                                 @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        pictureService.addNew(param);
        return JsonResult.created("添加图片成功");
    }

    @GetMapping("/picture/list-by-album")
    @ApiOperation("根据相册ID查询图片列表")
    @PreAuthorize("hasAuthority('/pms/album/read')")
    @ApiOperationSupport(order = 510)
    @ApiImplicitParam(name = "albumId", value = "相册id", required = true, dataType = "long")
    public JsonResult listPictures(@RequestParam Long albumId) {
        return JsonResult.ok(pictureService.listByAlbumId(albumId));
    }

    @PostMapping("/picture/delete")
    @ApiOperation("删除图片")
    @PreAuthorize("hasAuthority('/pms/album/delete')")
    @ApiOperationSupport(order = 520)
    @ApiImplicitParam(name = "id", value = "图片id", required = true, dataType = "long")
    public JsonResult deletePicture(@Range(min = 1) @RequestParam Long id) {
        pictureService.delete(id);
        return JsonResult.ok("删除图片成功");
    }

}