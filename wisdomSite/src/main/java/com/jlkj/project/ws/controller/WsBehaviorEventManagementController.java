package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.interceptor.annotation.RepeatSubmit;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsBehaviorEventManagement;
import com.jlkj.project.ws.service.IWsBehaviorEventManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 行为事件管理Controller
 *
 * @author liujie
 * @date 2020-07-23
 */
@Api(value = "行为事件管理")
@RestController
@RequestMapping("/ws/behaviorEventManagement")
public class WsBehaviorEventManagementController extends BaseController
{
    @Autowired
    private IWsBehaviorEventManagementService wsBehaviorEventManagementService;

    /**
     * 查询行为事件管理列表
     */
    @ApiOperation("查询行为事件管理列表")
    //@PreAuthorize("@ss.hasPermi('ws:behaviorEventManagement:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsBehaviorEventManagement wsBehaviorEventManagement)
    {
        startPage();
        List<WsBehaviorEventManagement> list = wsBehaviorEventManagementService.selectWsBehaviorEventManagementList(wsBehaviorEventManagement);
        return getDataTable(list);
    }

    /**
     * 导出行为事件管理列表
     */
    @ApiOperation("查询行为事件管理列表")
    @PreAuthorize("@ss.hasPermi('ws:behaviorEventManagement:export')")
    @Log(title = "行为事件管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsBehaviorEventManagement wsBehaviorEventManagement)
    {
        List<WsBehaviorEventManagement> list = wsBehaviorEventManagementService.selectWsBehaviorEventManagementList(wsBehaviorEventManagement);
        ExcelUtil<WsBehaviorEventManagement> util = new ExcelUtil<WsBehaviorEventManagement>(WsBehaviorEventManagement.class);
        return util.exportExcel(list, "behaviorEventManagement");
    }

    /**
     * 获取行为事件管理详细信息
     */
    @ApiOperation("获取行为事件管理详细信息")
    @PreAuthorize("@ss.hasPermi('ws:behaviorEventManagement:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsBehaviorEventManagementService.selectWsBehaviorEventManagementById(id));
    }

    /**
     * 新增行为事件管理
     */
    @RepeatSubmit
    @ApiOperation("新增行为事件管理")
    @PreAuthorize("@ss.hasPermi('ws:behaviorEventManagement:add')")
    @Log(title = "行为事件管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody WsBehaviorEventManagement wsBehaviorEventManagement)
    {
        return toAjax(wsBehaviorEventManagementService.insertWsBehaviorEventManagement(wsBehaviorEventManagement));
    }

    /**
     * 修改行为事件管理
     */
    @RepeatSubmit
    @ApiOperation("修改行为事件管理")
    @PreAuthorize("@ss.hasPermi('ws:behaviorEventManagement:edit')")
    @Log(title = "行为事件管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody WsBehaviorEventManagement wsBehaviorEventManagement)
    {
        return toAjax(wsBehaviorEventManagementService.updateWsBehaviorEventManagement(wsBehaviorEventManagement));
    }

    /**
     * 删除行为事件管理
     */
    @ApiOperation("删除行为事件管理")
    @PreAuthorize("@ss.hasPermi('ws:behaviorEventManagement:remove')")
    @Log(title = "行为事件管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(wsBehaviorEventManagementService.deleteWsBehaviorEventManagementById(id));
    }
}
