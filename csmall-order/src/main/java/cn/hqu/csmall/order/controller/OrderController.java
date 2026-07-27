package cn.hqu.csmall.order.controller;

import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.commons.security.LoginPrincipal;
import cn.hqu.csmall.commons.web.JsonResult;
import cn.hqu.csmall.order.pojo.param.OrderAddNewParam;
import cn.hqu.csmall.order.pojo.vo.OrderListItemVO;
import cn.hqu.csmall.order.pojo.vo.OrderStandardVO;
import cn.hqu.csmall.order.service.IOrderService;
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
@Api(tags = "订单管理模块")
@Validated
@RequestMapping("/order")
public class OrderController {
    @Autowired private IOrderService orderService;

    @PostMapping("/add-new")
    @ApiOperation("新增订单")
    @PreAuthorize("hasAuthority('/oms/order/add-new')")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid @RequestBody OrderAddNewParam param,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        orderService.addNew(param);
        return JsonResult.created("新增订单成功");
    }

    @PostMapping("/delete")
    @ApiOperation("删除订单")
    @PreAuthorize("hasAuthority('/oms/order/delete')")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "long")
    public JsonResult delete(@Range(min = 1) @RequestParam Long id) {
        orderService.delete(id);
        return JsonResult.ok("删除订单成功");
    }

    @PostMapping("/ship")
    @ApiOperation("发货")
    @PreAuthorize("hasAuthority('/oms/order/update')")
    @ApiOperationSupport(order = 310)
    @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "long")
    public JsonResult ship(@Range(min = 1) @RequestParam Long id) {
        orderService.ship(id);
        return JsonResult.ok("发货成功");
    }

    @PostMapping("/complete")
    @ApiOperation("完成订单")
    @PreAuthorize("hasAuthority('/oms/order/update')")
    @ApiOperationSupport(order = 320)
    @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "long")
    public JsonResult complete(@Range(min = 1) @RequestParam Long id) {
        orderService.complete(id);
        return JsonResult.ok("订单已完成");
    }

    @PostMapping("/cancel")
    @ApiOperation("取消订单")
    @PreAuthorize("hasAuthority('/oms/order/update')")
    @ApiOperationSupport(order = 330)
    @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "long")
    public JsonResult cancel(@Range(min = 1) @RequestParam Long id) {
        orderService.cancel(id);
        return JsonResult.ok("订单已取消");
    }

    @GetMapping("/list")
    @ApiOperation("查询订单列表")
    @PreAuthorize("hasAuthority('/oms/order/read')")
    @ApiOperationSupport(order = 420)
    public JsonResult list(@Range(min = 1) @RequestParam(defaultValue = "1") Integer pageNum) {
        if (pageNum == null || pageNum < 1) pageNum = 1;
        return JsonResult.ok(orderService.list(pageNum));
    }

    @GetMapping("/search")
    @ApiOperation("搜索订单")
    @PreAuthorize("hasAuthority('/oms/order/read')")
    @ApiOperationSupport(order = 430)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderNo", value = "订单号", paramType = "query"),
        @ApiImplicitParam(name = "id", value = "订单ID", paramType = "query", dataType = "long"),
        @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult search(@RequestParam(required = false) String orderNo,
                             @RequestParam(required = false) Long id,
                             @Range(min = 1) @RequestParam(defaultValue = "1") Integer pageNum) {
        if (pageNum == null || pageNum < 1) pageNum = 1;
        return JsonResult.ok(orderService.search(orderNo, id, pageNum));
    }

    @GetMapping("/standard")
    @ApiOperation("查询订单详情")
    @PreAuthorize("hasAuthority('/oms/order/read')")
    @ApiOperationSupport(order = 450)
    @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "long")
    public JsonResult standard(@Range(min = 1) @RequestParam Long id) {
        return JsonResult.ok(orderService.getStandardById(id));
    }
}
