package cn.hqu.csmall.product.controller;


import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.commons.security.LoginPrincipal;
import cn.hqu.csmall.commons.web.JsonResult;
import cn.hqu.csmall.product.pojo.param.CategoryAddNewParam;
import cn.hqu.csmall.product.pojo.param.CategoryUpdateParam;
import cn.hqu.csmall.product.pojo.vo.CategoryListItemVO;
import cn.hqu.csmall.product.pojo.vo.CategoryStandardVO;
import cn.hqu.csmall.product.service.ICategoryService;
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
import java.util.List;

@Slf4j
@RestController
@Api(tags = "类别管理模块")
@Validated
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping("/add-new")
    @ApiOperation("新增类别")
    @PreAuthorize("hasAuthority('/pms/category/add-new')")
    @ApiOperationSupport(order = 100)
    public JsonResult addNew(@Valid @RequestBody CategoryAddNewParam categoryAddNewParam,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【添加类别】的请求，参数:{}", categoryAddNewParam);
        categoryService.addNew(categoryAddNewParam);
        log.debug("处理【添加类别】的请求，完成！");
        return JsonResult.created("新增类别成功");
    }

    @PostMapping("/delete")
    @ApiOperation("删除类别")
    @PreAuthorize("hasAuthority('/pms/category/delete')")
    @ApiOperationSupport(order = 200)
    @ApiImplicitParam(name = "id", value = "类别id", required = true, dataType = "long")
    public JsonResult delete(@Range(min = 1, message = "根据id删除类别，请提供合法的id")
                             @RequestParam Long id,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【删除类别】的请求，id:{}", id);
        categoryService.delete(id);
        log.debug("处理【删除类别】的请求，完成！");
        return JsonResult.ok("删除类别成功");
    }

    @PostMapping("/enable")
    @ApiOperation("启用类别")
    @PreAuthorize("hasAuthority('/pms/category/update')")
    @ApiOperationSupport(order = 250)
    @ApiImplicitParam(name = "id", value = "类别id", required = true, dataType = "long")
    public JsonResult enable(@Range(min = 1, message = "启用类别失败，请提供合法的id")
                             @RequestParam Long id,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【启用类别】的请求，id:{}", id);
        categoryService.setEnable(id);
        log.debug("处理【启用类别】的请求，完成！");
        return JsonResult.ok("启用类别成功");
    }

    @PostMapping("/disable")
    @ApiOperation("禁用类别")
    @PreAuthorize("hasAuthority('/pms/category/update')")
    @ApiOperationSupport(order = 260)
    @ApiImplicitParam(name = "id", value = "类别id", required = true, dataType = "long")
    public JsonResult disable(@Range(min = 1, message = "禁用类别失败，请提供合法的id")
                              @RequestParam Long id,
                              @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【禁用类别】的请求，id:{}", id);
        categoryService.setDisable(id);
        log.debug("处理【禁用类别】的请求，完成！");
        return JsonResult.ok("禁用类别成功");
    }

    @PostMapping("/update")
    @ApiOperation("修改商品类别")
    @PreAuthorize("hasAuthority('/pms/category/update')")
    @ApiOperationSupport(order = 300)
    public JsonResult update(@Valid @RequestBody CategoryUpdateParam categoryUpdateParam,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【修改商品类别】的请求，参数为：{}", categoryUpdateParam);
        categoryService.updateById(categoryUpdateParam.getId(), categoryUpdateParam);
        log.debug("处理【修改商品类别】的请求，完成！");
        return JsonResult.ok();
    }

    @GetMapping("/list")
    @ApiOperation("查询商品类别列表")
    @PreAuthorize("hasAuthority('/pms/category/read')")
    @ApiOperationSupport(order = 420)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult list(@Range(min = 1, message = "查询商品类别列表失败，请提供正确的页码值！")
                           @RequestParam(defaultValue = "1") Integer pageNum,
                           @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【查询商品类别列表】的业务，参数：{}", pageNum);
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        PageData<CategoryListItemVO> pageData = categoryService.list(pageNum);
        return JsonResult.ok(pageData);
    }


    @GetMapping("/search")
    @ApiOperation("搜索类别")
    @PreAuthorize("hasAuthority('/pms/category/read')")
    @ApiOperationSupport(order = 430)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "类别名称（关键词搜索）", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "类别ID（关键词搜索）", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "parentId", value = "父级类别ID", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query")
    })
    public JsonResult search(@RequestParam(required = false) String name,
                             @RequestParam(required = false) Long id,
                             @RequestParam(required = false) Long parentId,
                             @Range(min = 1, message = "搜索类别失败，请提供正确的页码值！")
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【搜索类别】的请求，名称：{}，ID：{}，parentId：{}，页码：{}", name, id, parentId, pageNum);
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        PageData<CategoryListItemVO> pageData = categoryService.search(name, id, parentId, pageNum);
        return JsonResult.ok(pageData);
    }


    @GetMapping("/standard")
    @ApiOperation("查询类别详细信息")
    @PreAuthorize("hasAuthority('/pms/category/read')")
    @ApiOperationSupport(order = 450)
    @ApiImplicitParam(name = "id", value = "类别id", required = true, dataType = "long")
    public JsonResult standard(@Range(min = 1, message = "根据id查询类别，请提供合法的id")
                             @RequestParam Long id,
                             @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【查询类别信息】的请求，id:{}", id);
        CategoryStandardVO result = categoryService.getStandardById(id);
        log.debug("处理【查询类别信息】的请求，完成！");
        return JsonResult.ok(result);
    }

    @GetMapping("/children")
    @ApiOperation("根据父级类别查询子级类别列表")
    @PreAuthorize("hasAuthority('/pms/category/read')")
    @ApiOperationSupport(order = 460)
    @ApiImplicitParam(name = "parentId", value = "父级类别id", required = true, dataType = "long")
    public JsonResult children(@Range(min = 0, message = "根据父级id查询子级类别，请提供合法的父级id")
                               @RequestParam Long parentId,
                               @ApiIgnore @AuthenticationPrincipal LoginPrincipal user) {
        log.debug("当事人username:{}", user.getUsername());
        log.debug("当事人id:{}", user.getId());
        log.debug("开始处理【根据父级类别查询子级类别列表】的请求，parentId:{}", parentId);
        List<CategoryListItemVO> list = categoryService.getChildrenByParentId(parentId);
        log.debug("处理【根据父级类别查询子级类别列表】的请求，完成！");
        return JsonResult.ok(list);
    }

    @GetMapping("/tree")
    @ApiOperation("获取分类树")
    @PreAuthorize("hasAuthority('/pms/category/read')")
    @ApiOperationSupport(order = 470)
    public JsonResult tree() {
        return JsonResult.ok(categoryService.getTree());
    }

}
