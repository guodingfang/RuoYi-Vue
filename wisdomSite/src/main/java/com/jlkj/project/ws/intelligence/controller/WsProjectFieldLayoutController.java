package com.jlkj.project.ws.intelligence.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout;
import com.jlkj.project.ws.intelligence.service.IWsProjectFieldLayoutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工程场布图管理 Controller
 *
 * @author liujie
 * @date 2020-07-03
 */
@Api(tags= "工程场布图管理" )
@RestController
@RequestMapping("/ws/projectFieldLayout")
public class WsProjectFieldLayoutController extends BaseController
{
    @Autowired
    private IWsProjectFieldLayoutService wsProjectFieldLayoutService;

    /**
     * 查询工程场布图管理 列表
     */
    //@PreAuthorize("@ss.hasPermi('ws:projectFieldLayout:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsProjectFieldLayout wsProjectFieldLayout)
    {
        startPage();
        List<WsProjectFieldLayout> list = wsProjectFieldLayoutService.selectWsProjectFieldLayoutList(wsProjectFieldLayout);
        return getDataTable(list);
    }

    /**
     * 导出工程场布图管理 列表
     */
    @PreAuthorize("@ss.hasPermi('ws:projectFieldLayout:export')")
    @Log(title = "工程场布图管理 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsProjectFieldLayout wsProjectFieldLayout)
    {
        List<WsProjectFieldLayout> list = wsProjectFieldLayoutService.selectWsProjectFieldLayoutList(wsProjectFieldLayout);
        ExcelUtil<WsProjectFieldLayout> util = new ExcelUtil<WsProjectFieldLayout>(WsProjectFieldLayout.class);
        return util.exportExcel(list, "projectFieldLayout");
    }

    /**
     * 获取工程场布图管理 详细信息
     */
    @ApiOperation("获取工程场布图")
   // @PreAuthorize("@ss.hasPermi('ws:projectFieldLayout:query')")
    @GetMapping(value = "/{projectId}/{equipmentType}")
    public AjaxResult getInfo(@PathVariable("projectId") Integer projectId,@PathVariable("equipmentType")Integer equipmentType)
    {
        return AjaxResult.success(wsProjectFieldLayoutService.selectWsProjectFieldLayoutByProjectAndType(projectId,equipmentType));
    }

    /**
     * 新增工程场布图管理
     */
    @ApiOperation("新增工程场布图")
    //@PreAuthorize("@ss.hasPermi('ws:projectFieldLayout:add')")
    @Log(title = "工程场布图管理 ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WsProjectFieldLayout wsProjectFieldLayout)
    {
        return toAjax(wsProjectFieldLayoutService.insertWsProjectFieldLayout(wsProjectFieldLayout));
    }

    /**
     * 修改工程场布图管理
     */
    @ApiOperation("修改工程场布图")
   // @PreAuthorize("@ss.hasPermi('ws:projectFieldLayout:edit')")
    @Log(title = "工程场布图管理 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsProjectFieldLayout wsProjectFieldLayout)
    {
        return toAjax(wsProjectFieldLayoutService.updateWsProjectFieldLayout(wsProjectFieldLayout));
    }

    /**
     * 删除工程场布图管理
     */
    @PreAuthorize("@ss.hasPermi('ws:projectFieldLayout:remove')")
    @Log(title = "工程场布图管理 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wsProjectFieldLayoutService.deleteWsProjectFieldLayoutByIds(ids));
    }
}
