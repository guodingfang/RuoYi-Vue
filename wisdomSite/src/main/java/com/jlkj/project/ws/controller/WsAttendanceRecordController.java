package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsAttendanceRecord;
import com.jlkj.project.ws.domain.WsTeamPerson;
import com.jlkj.project.ws.domain.vo.WsAttendanceRecordDetailVO;
import com.jlkj.project.ws.domain.vo.WsAttendanceRecordVO;
import com.jlkj.project.ws.excel.AttendanceRecordExcel;
import com.jlkj.project.ws.service.IWsAttendanceRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 考勤记录 Controller
 *
 * @author gaowei
 * @date 2020-06-23
 */
@Api(tags = "考勤记录")
@RestController
@RequestMapping("/ws/attendanceRecord")
public class WsAttendanceRecordController extends BaseController
{
    @Autowired
    private IWsAttendanceRecordService wsAttendanceRecordService;

    /**
     * 查询考勤记录 列表
     */
    @ApiOperation("考勤记录")
    @ApiImplicitParam(name="wsAttendanceRecordVO", value="考勤记录",paramType = "WsAttendanceRecordVO")
    @PreAuthorize("@ss.hasPermi('ws:attendanceRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsAttendanceRecordVO wsAttendanceRecordVO)
    {
        startPage();
        List<WsTeamPerson> list = wsAttendanceRecordService.selectAttendanceReportForms(wsAttendanceRecordVO);
        return getDataTable(list);
    }

    /**
     * 查询人员考勤记录详情 列表
     */
    @ApiOperation("查询人员考勤记录详情 列表")
    @PreAuthorize("@ss.hasPermi('ws:wsAttendanceRecord:detailList')")
    @GetMapping("/list/{teamPersonId}")
    public AjaxResult detailList( WsAttendanceRecordDetailVO wsAttendanceRecordDetailVO,@PathVariable("teamPersonId")Integer teamPersonId)
    {
        wsAttendanceRecordDetailVO.setTeamPersonId(teamPersonId);
       WsAttendanceRecordDetailVO record = wsAttendanceRecordService.selectWsAttendanceRecordDetailList(wsAttendanceRecordDetailVO);

        return AjaxResult.success(record);
    }

    /**
     * 导出考勤记录 列表
     */
    @ApiOperation("人员考勤报表导出")

    @PreAuthorize("@ss.hasPermi('ws:attendanceRecord:export')")
    @Log(title = "人员考勤报表 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export/{flag}")
    public AjaxResult exportReport(WsAttendanceRecordVO wsAttendanceRecordVO,@PathVariable("flag") String flag)
    {
        List<WsTeamPerson> list = wsAttendanceRecordService.selectAttendanceReportForms(wsAttendanceRecordVO);
        ExcelUtil<WsTeamPerson> util = new ExcelUtil<WsTeamPerson>(WsTeamPerson.class);
        HashMap<String,Object> excelMap =wsAttendanceRecordService.getFilterInfo(wsAttendanceRecordVO);
        excelMap.put("flag",flag);
        excelMap.put("data",list);
        AttendanceRecordExcel attendanceRecordExcel = new AttendanceRecordExcel();

        return attendanceRecordExcel.exportExcel(excelMap,"人员考勤报表");
    }

    /**
     * 获取考勤记录 详细信息
     */
    @PreAuthorize("@ss.hasPermi('ws:attendanceRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsAttendanceRecordService.selectWsAttendanceRecordById(id));
    }

    /**
     * 新增考勤记录
     */
    @PreAuthorize("@ss.hasPermi('ws:attendanceRecord:add')")
    @Log(title = "考勤记录 ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WsAttendanceRecord wsAttendanceRecord)
    {
        return toAjax(wsAttendanceRecordService.insertWsAttendanceRecord(wsAttendanceRecord));
    }

    /**
     * 修改考勤记录
     */
    @PreAuthorize("@ss.hasPermi('ws:attendanceRecord:edit')")
    @Log(title = "考勤记录 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsAttendanceRecord wsAttendanceRecord)
    {
        return toAjax(wsAttendanceRecordService.updateWsAttendanceRecord(wsAttendanceRecord));
    }

    /**
     * 删除考勤记录
     */
    @PreAuthorize("@ss.hasPermi('ws:attendanceRecord:remove')")
    @Log(title = "考勤记录 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wsAttendanceRecordService.deleteWsAttendanceRecordByIds(ids));
    }

    /**
     * 查询出入纪录 列表
     */
    @ApiOperation("出入纪录")
    @PreAuthorize("@ss.hasPermi('ws:attendanceRecord:list')")
    @GetMapping("/listRecord")
    public TableDataInfo list(WsAttendanceRecord wsAttendanceRecord)
    {
        startPage();
        List<WsAttendanceRecord> list = wsAttendanceRecordService.selectAttendanceRecordList(wsAttendanceRecord);
        return getDataTable(list);
    }

    /**
     * 导出出入纪录 列表
     */
    @ApiOperation("导出数据")
    @PreAuthorize("@ss.hasPermi('ws:attendanceRecord:exportReport')")
    @Log(title = "出入纪录 ", businessType = BusinessType.EXPORT)
    @GetMapping("/exportRecord")
    public AjaxResult export(WsAttendanceRecord wsAttendanceRecord)
    {
        List<WsAttendanceRecord> list = wsAttendanceRecordService.selectAttendanceRecordList(wsAttendanceRecord);
        ExcelUtil<WsAttendanceRecord> util = new ExcelUtil<WsAttendanceRecord>(WsAttendanceRecord.class);
        return util.exportExcel(list, "出场纪录");
    }

}
