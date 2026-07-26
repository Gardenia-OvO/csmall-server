package cn.hqu.csmall.product.controller;


import cn.hqu.csmall.product.pojo.param.BrandAddNewParam;
import cn.hqu.csmall.product.pojo.param.BrandUpdateParam;
import cn.hqu.csmall.product.pojo.vo.BrandListItemVO;
import cn.hqu.csmall.product.pojo.vo.BrandStandardVO;
import cn.hqu.csmall.product.pojo.vo.PageData;
import cn.hqu.csmall.product.security.LoginPrincipal;
import cn.hqu.csmall.product.service.IBrandService;
import cn.hqu.csmall.product.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
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
@Api(tags = "品牌管理模块")
@Validated
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @PostMapping("/add-new")
    @ApiOperation("新增品牌")
    @PreAuthorize("hasAuthority('/pms/brand/add-new')")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid @RequestBody BrandAddNewParam brandAddNewParam,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【添加品牌】的请求，参数:{}", brandAddNewParam);
        brandService.addNew(brandAddNewParam);
        log.debug("处理【添加品牌】的请求，完成！");
        return JsonResult.created("新增品牌成功");
    }

    @PostMapping("/delete")
    @ApiOperation("删除品牌")
    @PreAuthorize("hasAuthority('/pms/brand/delete')")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "品牌id", required = true, dataType = "long")
    public JsonResult delete(@Range(min = 1, message = "根据id删除品牌，请提供合法的id")
                             @RequestParam Long id,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【删除品牌】的请求，id:{}", id);
        brandService.delete(id);
        log.debug("处理【删除品牌】的请求，完成！");
        return JsonResult.ok("删除品牌成功");
    }

    @PostMapping("/update")
    @ApiOperation("修改品牌")
    @PreAuthorize("hasAuthority('/pms/brand/update')")
    @ApiOperationSupport(order = 300)
    public JsonResult update(@Valid @RequestBody BrandUpdateParam brandUpdateParam,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【修改品牌】的请求，参数为：{}", brandUpdateParam);
        brandService.updateById(brandUpdateParam.getId(), brandUpdateParam);
        log.debug("处理【修改品牌】的请求，完成！");
        return JsonResult.ok();
    }

    @GetMapping("/list")
    @ApiOperation("查询品牌列表")
    @PreAuthorize("hasAuthority('/pms/brand/read')")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult list(@Range(min = 1, message = "查询品牌列表失败，请提供正确的页码值！")
                           @RequestParam(defaultValue = "1") Integer pageNum,
                           @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【查询品牌列表】的业务，参数：{}", pageNum);
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        PageData<BrandListItemVO> pageData = brandService.list(pageNum);
        return JsonResult.ok(pageData);
    }

    @GetMapping("/search")
    @ApiOperation("搜索品牌")
    @PreAuthorize("hasAuthority('/pms/brand/read')")
    @ApiOperationSupport(order = 430)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "品牌名称（精确匹配）", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "品牌ID（精确匹配）", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult search(@RequestParam(required = false) String name,
                             @RequestParam(required = false) Long id,
                             @Range(min = 1, message = "搜索品牌失败，请提供正确的页码值！")
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【搜索品牌】的请求，名称：{}，ID：{}，页码：{}", name, id, pageNum);
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        PageData<BrandListItemVO> pageData = brandService.search(name, id, pageNum);
        return JsonResult.ok(pageData);
    }


    @GetMapping("/standard")
    @ApiOperation("查询品牌详细信息")
    @PreAuthorize("hasAuthority('/pms/brand/read')")
    @ApiOperationSupport(order = 450)
    @ApiImplicitParam(name = "id", value = "品牌id", required = true, dataType = "long")
    public JsonResult standard(@Range(min = 1, message = "根据id查询品牌，请提供合法的id")
                             @RequestParam Long id,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【查询品牌信息】的请求，id:{}", id);
        BrandStandardVO result = brandService.getStandardById(id);
        log.debug("处理【查询品牌信息】的请求，完成！");
        return JsonResult.ok(result);
    }

}
