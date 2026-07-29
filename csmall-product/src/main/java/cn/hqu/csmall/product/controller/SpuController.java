package cn.hqu.csmall.product.controller;

import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.commons.security.LoginPrincipal;
import cn.hqu.csmall.commons.web.JsonResult;
import cn.hqu.csmall.product.pojo.param.SpuAddNewParam;
import cn.hqu.csmall.product.pojo.param.SpuUpdateParam;
import cn.hqu.csmall.product.pojo.vo.SpuFullInfoVO;
import cn.hqu.csmall.product.pojo.vo.SpuListItemVO;
import cn.hqu.csmall.product.pojo.vo.SpuStandardVO;
import cn.hqu.csmall.product.service.ISpuService;
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
@RequestMapping("/spu")
@Validated
@Api(tags = "07. SPU管理模块")
public class SpuController {

    @Autowired
    private ISpuService spuService;

    @PostMapping("/add-new")
    @PreAuthorize("hasAuthority('/pms/product/add-new')")
    @ApiOperation("新增SPU")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid @RequestBody SpuAddNewParam spuAddNewParam,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("开始处理【新增SPU】的请求，参数：{}", spuAddNewParam);
        spuService.addNew(spuAddNewParam);
        return JsonResult.created("新增SPU成功");
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('/pms/product/delete')")
    @ApiOperation("删除SPU")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "SPU ID", required = true, dataType = "long")
    public JsonResult delete(@Range(min = 1, message = "根据id删除SPU，请提供合法的id")
                             @RequestParam Long id,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("开始处理【删除SPU】的请求，id:{}", id);
        spuService.delete(id);
        return JsonResult.ok("删除SPU成功");
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('/pms/product/update')")
    @ApiOperation("修改SPU")
    @ApiOperationSupport(order = 300)
    public JsonResult update(@Valid @RequestBody SpuUpdateParam spuUpdateParam,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("开始处理【修改SPU】的请求，参数为：{}", spuUpdateParam);
        spuService.updateById(spuUpdateParam.getId(), spuUpdateParam);
        return JsonResult.ok();
    }

    @GetMapping("/{id:[0-9]+}")
    @PreAuthorize("hasAuthority('/pms/product/read')")
    @ApiOperation("根据ID查询SPU信息")
    @ApiOperationSupport(order = 410)
    @ApiImplicitParam(name = "id", value = "SPU ID", required = true, dataType = "long")
    public JsonResult getStandardById(@PathVariable @Range(min = 1) Long id,
                                      @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("开始处理【根据ID查询SPU】的请求，参数：{}", id);
        SpuStandardVO spu = spuService.getStandardById(id);
        return JsonResult.ok(spu);
    }

    @GetMapping("/{id:[0-9]+}/full-info")
    @PreAuthorize("hasAuthority('/pms/product/read')")
    @ApiOperation("根据ID查询SPU完整信息")
    @ApiOperationSupport(order = 411)
    @ApiImplicitParam(name = "id", value = "SPU ID", required = true, dataType = "long")
    public JsonResult getFullInfoById(@PathVariable @Range(min = 1) Long id,
                                      @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("开始处理【根据ID查询SPU完整信息】的请求，参数：{}", id);
        SpuFullInfoVO spuFullInfo = spuService.getFullInfoById(id);
        return JsonResult.ok(spuFullInfo);
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('/pms/product/read')")
    @ApiOperation("查询SPU列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", paramType = "query", dataType = "int")
    })
    public JsonResult list(@Range(min = 1) @RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "5") Integer pageSize,
                           @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("开始处理【查询SPU列表】的请求，页码：{}", pageNum);
        if (pageNum == null || pageNum < 1) pageNum = 1;
        return JsonResult.ok(spuService.list(pageNum, pageSize));
    }
}
