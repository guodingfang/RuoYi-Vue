package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.interceptor.annotation.RepeatSubmit;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsExchangeItemManagement;
import com.jlkj.project.ws.service.IWsExchangeItemManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 物品兑换管理
Controller
 *
 * @author liujie
 * @date 2020-07-23
 */
@Api(value = "物品兑换管理")
@RestController
@RequestMapping("/ws/exchangeItemManagement")
public class WsExchangeItemManagementController extends BaseController
{
    @Autowired
    private IWsExchangeItemManagementService wsExchangeItemManagementService;

    /**
     * 查询物品兑换管理
列表
     */
    @ApiOperation("查询物品兑换管理")
    //@PreAuthorize("@ss.hasPermi('ws:exchangeItemManagement:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsExchangeItemManagement wsExchangeItemManagement)
    {
        startPage();
        List<WsExchangeItemManagement> list = wsExchangeItemManagementService.selectWsExchangeItemManagementList(wsExchangeItemManagement);
        return getDataTable(list);
    }

    /**
     * 导出物品兑换管理
列表
     */
    @ApiOperation("导出物品兑换管理")
    @PreAuthorize("@ss.hasPermi('ws:exchangeItemManagement:export')")
    @Log(title = "物品兑换管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsExchangeItemManagement wsExchangeItemManagement)
    {
        List<WsExchangeItemManagement> list = wsExchangeItemManagementService.selectWsExchangeItemManagementList(wsExchangeItemManagement);
        ExcelUtil<WsExchangeItemManagement> util = new ExcelUtil<WsExchangeItemManagement>(WsExchangeItemManagement.class);
        return util.exportExcel(list, "exchangeItemManagement");
    }

    /**
     * 获取物品兑换管理
详细信息
     */
    @ApiOperation("获取物品兑换管理")
    @PreAuthorize("@ss.hasPermi('ws:exchangeItemManagement:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsExchangeItemManagementService.selectWsExchangeItemManagementById(id));
    }

    /**
     * 新增物品兑换管理

     */
    @RepeatSubmit
    @ApiOperation("新增物品兑换管理")
    @PreAuthorize("@ss.hasPermi('ws:exchangeItemManagement:add')")
    @Log(title = "物品兑换管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody WsExchangeItemManagement wsExchangeItemManagement)
    {
        return toAjax(wsExchangeItemManagementService.insertWsExchangeItemManagement(wsExchangeItemManagement));
    }

    /**
     * 修改物品兑换管理

     */
    @RepeatSubmit
    @ApiOperation("修改物品兑换管理")
    @PreAuthorize("@ss.hasPermi('ws:exchangeItemManagement:edit')")
    @Log(title = "物品兑换管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsExchangeItemManagement wsExchangeItemManagement)
    {
        return toAjax(wsExchangeItemManagementService.updateWsExchangeItemManagement(wsExchangeItemManagement));
    }

    /**
     * 删除物品兑换管理

     */
    @ApiOperation("删除物品兑换管理")
    @PreAuthorize("@ss.hasPermi('ws:exchangeItemManagement:remove')")
    @Log(title = "物品兑换管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(wsExchangeItemManagementService.deleteWsExchangeItemManagementById(id));
    }
}
