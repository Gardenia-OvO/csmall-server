package cn.hqu.csmall.marketing.controller;

import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.commons.security.LoginPrincipal;
import cn.hqu.csmall.commons.web.JsonResult;
import cn.hqu.csmall.marketing.pojo.param.ActivityAddNewParam;
import cn.hqu.csmall.marketing.pojo.param.ActivityUpdateParam;
import cn.hqu.csmall.marketing.pojo.vo.ActivityListItemVO;
import cn.hqu.csmall.marketing.pojo.vo.ActivityStandardVO;
import cn.hqu.csmall.marketing.service.IActivityService;
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

@Slf4j @RestController @Api(tags = "营销管理模块") @Validated @RequestMapping("/activity")
public class ActivityController {
    @Autowired private IActivityService service;

    @PostMapping("/add-new") @ApiOperation("新增活动") @PreAuthorize("hasAuthority('/promotion/activity/add-new')") @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid @RequestBody ActivityAddNewParam param) {
        service.addNew(param); return JsonResult.created("新增活动成功");
    }
    @PostMapping("/delete") @ApiOperation("删除活动") @PreAuthorize("hasAuthority('/promotion/activity/delete')") @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "活动id", required = true, dataType = "long")
    public JsonResult delete(@Range(min=1) @RequestParam Long id) { service.delete(id); return JsonResult.ok("删除成功"); }
    @PostMapping("/update") @ApiOperation("修改活动") @PreAuthorize("hasAuthority('/promotion/activity/update')") @ApiOperationSupport(order = 300)
    public JsonResult update(@Valid @RequestBody ActivityUpdateParam param) {
        service.updateById(param.getId(), param); return JsonResult.ok();
    }
    @PostMapping("/start") @ApiOperation("开始活动") @PreAuthorize("hasAuthority('/promotion/activity/update')") @ApiOperationSupport(order = 310)
    @ApiImplicitParam(name = "id", value = "活动id", required = true, dataType = "long")
    public JsonResult start(@Range(min=1) @RequestParam Long id) { service.start(id); return JsonResult.ok("活动已开始"); }
    @PostMapping("/end") @ApiOperation("结束活动") @PreAuthorize("hasAuthority('/promotion/activity/update')") @ApiOperationSupport(order = 320)
    @ApiImplicitParam(name = "id", value = "活动id", required = true, dataType = "long")
    public JsonResult end(@Range(min=1) @RequestParam Long id) { service.end(id); return JsonResult.ok("活动已结束"); }
    @GetMapping("/list") @ApiOperation("查询活动列表") @PreAuthorize("hasAuthority('/promotion/activity/read')") @ApiOperationSupport(order = 420)
    public JsonResult list(@Range(min=1) @RequestParam(defaultValue="1") Integer pageNum) {
        if (pageNum==null||pageNum<1) pageNum=1; return JsonResult.ok(service.list(pageNum));
    }
    @GetMapping("/search") @ApiOperation("搜索活动") @PreAuthorize("hasAuthority('/promotion/activity/read')") @ApiOperationSupport(order = 430)
    @ApiImplicitParams({@ApiImplicitParam(name="title",value="活动名称",paramType="query"),@ApiImplicitParam(name="id",value="活动ID",paramType="query",dataType="long"),@ApiImplicitParam(name="pageNum",value="页码",paramType="query")})
    public JsonResult search(@RequestParam(required=false) String title, @RequestParam(required=false) Long id, @Range(min=1) @RequestParam(defaultValue="1") Integer pageNum) {
        if (pageNum==null||pageNum<1) pageNum=1; return JsonResult.ok(service.search(title, id, pageNum));
    }
    @GetMapping("/standard") @ApiOperation("查询活动详情") @PreAuthorize("hasAuthority('/promotion/activity/read')") @ApiOperationSupport(order = 450)
    @ApiImplicitParam(name = "id", value = "活动id", required = true, dataType = "long")
    public JsonResult standard(@Range(min=1) @RequestParam Long id) { return JsonResult.ok(service.getStandardById(id)); }
}
