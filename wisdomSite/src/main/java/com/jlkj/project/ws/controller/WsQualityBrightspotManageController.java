package com.jlkj.project.ws.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.project.ws.domain.WsQualityBrightspotManage;
import com.jlkj.project.ws.service.IWsQualityBrightspotManageService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 亮点管理 Controller
 *
 * @author gaowei
 * @date 2020-06-11
 */
@Api(value = "亮点管理接口",tags = "亮点管理页面")
@RestController
@RequestMapping("/ws/brightspotManage")
public class WsQualityBrightspotManageController extends BaseController
{
    @Autowired
    private IWsQualityBrightspotManageService wsQualityBrightspotManageService;

    /**
     * 查询亮点管理 列表
     */
    @ApiOperation("亮点管理列表")
    @ApiImplicitParam(name = "wsQualityBrightspotManage", value = "亮点管理实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:brightspotManage:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsQualityBrightspotManage wsQualityBrightspotManage)
    {
        startPage();
        List<WsQualityBrightspotManage> list = wsQualityBrightspotManageService.selectWsQualityBrightspotManageList(wsQualityBrightspotManage);
        return getDataTable(list);
    }

    /**
     * 导出亮点管理 列表
     */
    @PreAuthorize("@ss.hasPermi('ws:brightspotManage:export')")
    @Log(title = "亮点管理 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsQualityBrightspotManage wsQualityBrightspotManage)
    {
        List<WsQualityBrightspotManage> list = wsQualityBrightspotManageService.selectWsQualityBrightspotManageList(wsQualityBrightspotManage);
        ExcelUtil<WsQualityBrightspotManage> util = new ExcelUtil<WsQualityBrightspotManage>(WsQualityBrightspotManage.class);
        return util.exportExcel(list, "brightspotManage");
    }

    /**
     * 获取亮点管理 详细信息
     */
    @ApiOperation("亮点管理详细信息")
    @PreAuthorize("@ss.hasPermi('ws:brightspotManage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsQualityBrightspotManageService.selectWsQualityBrightspotManageById(id));
    }

    /**
     * 新增亮点管理 
     */
    @ApiOperation("新增亮点管理")
    @ApiImplicitParam(name = "wsQualityBrightspotManage", value = "亮点管理实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:brightspotManage:add')")
    @Log(title = "亮点管理 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsQualityBrightspotManage wsQualityBrightspotManage)
    {
        return toAjax(wsQualityBrightspotManageService.insertWsQualityBrightspotManage(wsQualityBrightspotManage));
    }

    /**
     * 修改亮点管理 
     */
    @PreAuthorize("@ss.hasPermi('ws:brightspotManage:edit')")
    @Log(title = "亮点管理 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsQualityBrightspotManage wsQualityBrightspotManage)
    {
        return toAjax(wsQualityBrightspotManageService.updateWsQualityBrightspotManage(wsQualityBrightspotManage));
    }

    /**
     * 删除亮点管理 
     */
    @PreAuthorize("@ss.hasPermi('ws:brightspotManage:remove')")
    @Log(title = "亮点管理 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wsQualityBrightspotManageService.deleteWsQualityBrightspotManageByIds(ids));
    }
}
