package cn.hqu.csmall.merchant.controller;

import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.commons.security.LoginPrincipal;
import cn.hqu.csmall.commons.web.JsonResult;
import cn.hqu.csmall.merchant.pojo.param.MerchantAddNewParam;
import cn.hqu.csmall.merchant.pojo.param.MerchantUpdateParam;
import cn.hqu.csmall.merchant.pojo.vo.MerchantListItemVO;
import cn.hqu.csmall.merchant.pojo.vo.MerchantStandardVO;
import cn.hqu.csmall.merchant.service.IMerchantService;
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
@Api(tags = "商家管理模块")
@Validated
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private IMerchantService merchantService;

    @PostMapping("/add-new")
    @ApiOperation("新增商家")
    @PreAuthorize("hasAuthority('/mms/merchant/add-new')")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid @RequestBody MerchantAddNewParam merchantAddNewParam,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【添加商家】的请求，参数:{}", merchantAddNewParam);
        merchantService.addNew(merchantAddNewParam);
        log.debug("处理【添加商家】的请求，完成！");
        return JsonResult.created("新增商家成功");
    }

    @PostMapping("/delete")
    @ApiOperation("删除商家")
    @PreAuthorize("hasAuthority('/mms/merchant/delete')")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "商家id", required = true, dataType = "long")
    public JsonResult delete(@Range(min = 1, message = "根据id删除商家，请提供合法的id")
                             @RequestParam Long id,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【删除商家】的请求，id:{}", id);
        merchantService.delete(id);
        log.debug("处理【删除商家】的请求，完成！");
        return JsonResult.ok("删除商家成功");
    }

    @PostMapping("/update")
    @ApiOperation("修改商家")
    @PreAuthorize("hasAuthority('/mms/merchant/update')")
    @ApiOperationSupport(order = 300)
    public JsonResult update(@Valid @RequestBody MerchantUpdateParam merchantUpdateParam,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【修改商家】的请求，参数为：{}", merchantUpdateParam);
        merchantService.updateById(merchantUpdateParam.getId(), merchantUpdateParam);
        log.debug("处理【修改商家】的请求，完成！");
        return JsonResult.ok();
    }

    @PostMapping("/approve")
    @ApiOperation("审核通过商家")
    @PreAuthorize("hasAuthority('/mms/merchant/update')")
    @ApiOperationSupport(order = 350)
    @ApiImplicitParam(name = "id", value = "商家id", required = true, dataType = "long")
    public JsonResult approve(@Range(min = 1, message = "审核通过商家失败，请提供合法的id")
                              @RequestParam Long id,
                              @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【审核通过商家】的请求，id:{}", id);
        merchantService.approve(id);
        log.debug("处理【审核通过商家】的请求，完成！");
        return JsonResult.ok("审核通过成功");
    }

    @PostMapping("/disable")
    @ApiOperation("禁用商家")
    @PreAuthorize("hasAuthority('/mms/merchant/update')")
    @ApiOperationSupport(order = 360)
    @ApiImplicitParam(name = "id", value = "商家id", required = true, dataType = "long")
    public JsonResult disable(@Range(min = 1, message = "禁用商家失败，请提供合法的id")
                              @RequestParam Long id,
                              @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【禁用商家】的请求，id:{}", id);
        merchantService.disable(id);
        log.debug("处理【禁用商家】的请求，完成！");
        return JsonResult.ok("禁用商家成功");
    }

    @GetMapping("/list")
    @ApiOperation("查询商家列表")
    @PreAuthorize("hasAuthority('/mms/merchant/read')")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult list(@Range(min = 1, message = "查询商家列表失败，请提供正确的页码值！")
                           @RequestParam(defaultValue = "1") Integer pageNum,
                           @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【查询商家列表】的业务，参数：{}", pageNum);
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        PageData<MerchantListItemVO> pageData = merchantService.list(pageNum);
        return JsonResult.ok(pageData);
    }

    @GetMapping("/search")
    @ApiOperation("搜索商家")
    @PreAuthorize("hasAuthority('/mms/merchant/read')")
    @ApiOperationSupport(order = 430)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "商家名称（关键词搜索）", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "商家ID（关键词搜索）", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult search(@RequestParam(required = false) String name,
                             @RequestParam(required = false) Long id,
                             @Range(min = 1, message = "搜索商家失败，请提供正确的页码值！")
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【搜索商家】的请求，名称：{}，ID：{}，页码：{}", name, id, pageNum);
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        PageData<MerchantListItemVO> pageData = merchantService.search(name, id, pageNum);
        return JsonResult.ok(pageData);
    }

    @GetMapping("/standard")
    @ApiOperation("查询商家详细信息")
    @PreAuthorize("hasAuthority('/mms/merchant/read')")
    @ApiOperationSupport(order = 450)
    @ApiImplicitParam(name = "id", value = "商家id", required = true, dataType = "long")
    public JsonResult standard(@Range(min = 1, message = "根据id查询商家，请提供合法的id")
                               @RequestParam Long id,
                               @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【查询商家信息】的请求，id:{}", id);
        MerchantStandardVO result = merchantService.getStandardById(id);
        log.debug("处理【查询商家信息】的请求，完成！");
        return JsonResult.ok(result);
    }
}
