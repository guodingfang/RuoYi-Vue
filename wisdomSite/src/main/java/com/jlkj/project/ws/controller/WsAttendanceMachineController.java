package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.interceptor.annotation.RepeatSubmit;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsAttendanceMachine;
import com.jlkj.project.ws.service.IWsAttendanceMachineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考勤机 Controller
 *
 * @author liujie
 * @date 2020-06-23
 */
@Api(tags = "考勤机管理")
@RestController
@RequestMapping("/ws/attendanceMachine")
public class WsAttendanceMachineController extends BaseController
{
    @Autowired
    private IWsAttendanceMachineService wsAttendanceMachineService;

    /**
     * 查询考勤机 列表
     */
    @ApiOperation("查询考勤机列表")
    @ApiImplicitParam(name = "考勤机",value = "wsAttendanceMachine",dataType = "WsAttendanceMachine")
    @PreAuthorize("@ss.hasPermi('ws:attendanceMachine:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsAttendanceMachine wsAttendanceMachine)
    {
        startPage();
        List<WsAttendanceMachine> list = wsAttendanceMachineService.selectWsAttendanceMachineList(wsAttendanceMachine);
        return getDataTable(list);
    }

    /**
     * 导出考勤机 列表
     */
    @PreAuthorize("@ss.hasPermi('ws:attendanceMachine:export')")
    @Log(title = "考勤机 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsAttendanceMachine wsAttendanceMachine)
    {
        List<WsAttendanceMachine> list = wsAttendanceMachineService.selectWsAttendanceMachineList(wsAttendanceMachine);
        ExcelUtil<WsAttendanceMachine> util = new ExcelUtil<WsAttendanceMachine>(WsAttendanceMachine.class);
        return util.exportExcel(list, "attendanceMachine");
    }

    /**
     * 获取考勤机 详细信息
     */
    @ApiOperation("删除考勤机")
    @ApiImplicitParam(name = "考勤机id",value = "id",dataType = "Integer",paramType = "path")
    @PreAuthorize("@ss.hasPermi('ws:attendanceMachine:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsAttendanceMachineService.selectWsAttendanceMachineById(id));
    }

    /**
     * 新增考勤机
     */
    @RepeatSubmit
    @ApiOperation("新增考勤机")
    @ApiImplicitParam(name = "考勤机",value = "wsAttendanceMachine",dataType = "WsAttendanceMachine",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:attendanceMachine:add')")
    @Log(title = "考勤机 ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated@RequestBody WsAttendanceMachine wsAttendanceMachine)
    {
        return toAjax(wsAttendanceMachineService.insertWsAttendanceMachine(wsAttendanceMachine));
    }

    /**
     * 修改考勤机
     *
     */
    @RepeatSubmit
    @ApiOperation("更新考勤机")
    @ApiImplicitParam(name = "考勤机",value = "wsAttendanceMachine",dataType = "WsAttendanceMachine",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:attendanceMachine:edit')")
    @Log(title = "考勤机 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody WsAttendanceMachine wsAttendanceMachine)
    {
        return toAjax(wsAttendanceMachineService.updateWsAttendanceMachine(wsAttendanceMachine));
    }

    /**
     * 删除考勤机
     */
    @ApiOperation("删除考勤机")
    @ApiImplicitParam(name = "考勤机id",value = "id",dataType = "Integer",paramType = "path")
    @PreAuthorize("@ss.hasPermi('ws:attendanceMachine:remove')")
    @Log(title = "考勤机 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(wsAttendanceMachineService.deleteWsAttendanceMachineById(id));
    }
}
