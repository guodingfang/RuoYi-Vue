package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.interceptor.annotation.RepeatSubmit;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsAttendanceZone;
import com.jlkj.project.ws.service.IWsAttendanceZoneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考勤分区 Controller
 *
 * @author gaowei
 * @date 2020-06-23
 */
@Api(tags = "工地考勤分区管理")
@RestController
@RequestMapping("/ws/attendanceZone")
public class WsAttendanceZoneController extends BaseController
{
    @Autowired
    private IWsAttendanceZoneService wsAttendanceZoneService;

    /**
     * 查询考勤分区 列表
     */
    @ApiOperation("查询考勤分区列表")
    @ApiImplicitParam(name = "考勤分区",value = "wsAttendanceZone",dataType = "WsAttendanceZone")
    @PreAuthorize("@ss.hasPermi('ws:attendanceZone:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsAttendanceZone wsAttendanceZone)
    {
        startPage();
        List<WsAttendanceZone> list = wsAttendanceZoneService.selectWsAttendanceZoneList(wsAttendanceZone);
        return getDataTable(list);
    }


    /**
     * 查询考勤分区设备统计列表
     */
    @ApiOperation("查询考勤分区设备统计列表")
    @ApiImplicitParam(name = "考勤分区",value = "wsAttendanceZone",dataType = "WsAttendanceZone")
    @PreAuthorize("@ss.hasPermi('ws:attendanceZone:machines')")
    @GetMapping("/list/machines")
    public AjaxResult statisticsMachineListByZone(WsAttendanceZone wsAttendanceZone)
    {
        List<WsAttendanceZone> list = wsAttendanceZoneService.statisticsMachineListByZone(wsAttendanceZone);
        return AjaxResult.success(list);
    }


    /**
     * 导出考勤分区 列表
     */
    @PreAuthorize("@ss.hasPermi('ws:attendanceZone:export')")
    @Log(title = "考勤分区 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsAttendanceZone wsAttendanceZone)
    {
        List<WsAttendanceZone> list = wsAttendanceZoneService.selectWsAttendanceZoneList(wsAttendanceZone);
        ExcelUtil<WsAttendanceZone> util = new ExcelUtil<WsAttendanceZone>(WsAttendanceZone.class);
        return util.exportExcel(list, "attendanceZone");
    }

    /**
     * 获取考勤分区 详细信息
     */
    @ApiOperation("查询考勤分区详细")
    @ApiImplicitParam(name = "分区ID",value = "id",dataType = "Integer")
    @PreAuthorize("@ss.hasPermi('ws:attendanceZone:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsAttendanceZoneService.selectWsAttendanceZoneById(id));
    }

    /**
     * 新增考勤分区
     */
    @RepeatSubmit
    @ApiOperation("新增考勤分区")
    @ApiImplicitParam(name = "考勤分区",value = "wsAttendanceZone",dataType = "WsAttendanceZone",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:attendanceZone:add')")
    @Log(title = "考勤分区 ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody WsAttendanceZone wsAttendanceZone)
    {
        return toAjax(wsAttendanceZoneService.insertWsAttendanceZone(wsAttendanceZone));
    }

    /**
     * 修改考勤分区
     *
     */
    @RepeatSubmit
    @ApiOperation("修改考勤分区")
    @ApiImplicitParam(name = "考勤分区",value = "wsAttendanceZone",dataType = "WsAttendanceZone",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:attendanceZone:edit')")
    @Log(title = "考勤分区 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated@RequestBody WsAttendanceZone wsAttendanceZone)
    {
        return toAjax(wsAttendanceZoneService.updateWsAttendanceZone(wsAttendanceZone));
    }

    /**
     * 删除考勤分区
     */
    @ApiOperation("删除考勤分区")
    @ApiImplicitParam(name = "分区ID",value = "id",dataType = "Integer")
    @PreAuthorize("@ss.hasPermi('ws:attendanceZone:remove')")
    @Log(title = "考勤分区 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(wsAttendanceZoneService.deleteWsAttendanceZoneById(id));
    }
}
