package cn.hqu.csmall.product.controller;

import cn.hqu.csmall.commons.security.LoginPrincipal;
import cn.hqu.csmall.commons.web.JsonResult;
import cn.hqu.csmall.product.pojo.param.SkuAddNewParam;
import cn.hqu.csmall.product.pojo.vo.SkuListItemVO;
import cn.hqu.csmall.product.pojo.vo.SkuStandardVO;
import cn.hqu.csmall.product.service.ISkuService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sku")
@Validated
@Api(tags = "08. SKU管理模块")
public class SkuController {

    @Autowired
    private ISkuService skuService;

    @PostMapping("/add-new")
    @PreAuthorize("hasAuthority('/pms/product/add-new')")
    @ApiOperation("新增SKU")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid @RequestBody SkuAddNewParam param,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        skuService.addNew(param);
        return JsonResult.created("新增SKU成功");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('/pms/product/read')")
    @ApiOperation("根据ID查询SKU")
    @ApiOperationSupport(order = 410)
    @ApiImplicitParam(name = "id", value = "SKU ID", required = true, dataType = "long")
    public JsonResult getStandardById(@PathVariable @Range(min = 1) Long id,
                                       @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        SkuStandardVO sku = skuService.getStandardById(id);
        return JsonResult.ok(sku);
    }

    @GetMapping("/list-by-spu")
    @PreAuthorize("hasAuthority('/pms/product/read')")
    @ApiOperation("根据SPU查询SKU列表")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParam(name = "spuId", value = "SPU ID", required = true, paramType = "query", dataType = "long")
    public JsonResult listBySpuId(@RequestParam @Range(min = 1) Long spuId,
                                   @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        List<SkuListItemVO> list = skuService.listBySpuId(spuId);
        return JsonResult.ok(list);
    }
}
