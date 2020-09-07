package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsAttendanceRegister;
import com.jlkj.project.ws.service.IWsAttendanceRegisterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 人员考勤平台注册Controller
 *
 * @author gaowei
 * @date 2020-06-23
 */
@RestController
@Api(tags = "人员考勤平台注册")
@RequestMapping("/ws/attendanceRegister")
public class WsAttendanceRegisterController extends BaseController
{
    @Autowired
    private IWsAttendanceRegisterService wsAttendanceRegisterService;

    /**
     * 查询人员考勤平台注册列表
     */
    @PreAuthorize("@ss.hasPermi('ws:attendanceRegister:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsAttendanceRegister wsAttendanceRegister)
    {
        startPage();
        List<WsAttendanceRegister> list = wsAttendanceRegisterService.selectWsAttendanceRegisterList(wsAttendanceRegister);
        return getDataTable(list);
    }

    /**
     * 导出人员考勤平台注册列表
     */
    @PreAuthorize("@ss.hasPermi('ws:attendanceRegister:export')")
    @Log(title = "人员考勤平台注册", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsAttendanceRegister wsAttendanceRegister)
    {
        List<WsAttendanceRegister> list = wsAttendanceRegisterService.selectWsAttendanceRegisterList(wsAttendanceRegister);
        ExcelUtil<WsAttendanceRegister> util = new ExcelUtil<WsAttendanceRegister>(WsAttendanceRegister.class);
        return util.exportExcel(list, "attendanceRegister");
    }

    /**
     * 获取人员考勤平台注册详细信息
     */
    @PreAuthorize("@ss.hasPermi('ws:attendanceRegister:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsAttendanceRegisterService.selectWsAttendanceRegisterById(id));
    }

    /**
     * 新增人员考勤平台注册
     */
    @PreAuthorize("@ss.hasPermi('ws:attendanceRegister:add')")
    @Log(title = "人员考勤平台注册", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WsAttendanceRegister wsAttendanceRegister)
    {
        return toAjax(wsAttendanceRegisterService.insertWsAttendanceRegister(wsAttendanceRegister));
    }

    /**
     * 修改人员考勤平台注册
     */
    @PreAuthorize("@ss.hasPermi('ws:attendanceRegister:edit')")
    @Log(title = "人员考勤平台注册", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsAttendanceRegister wsAttendanceRegister)
    {
        return toAjax(wsAttendanceRegisterService.updateWsAttendanceRegister(wsAttendanceRegister));
    }

    /**
     * 删除人员考勤平台注册
     */
    @PreAuthorize("@ss.hasPermi('ws:attendanceRegister:remove')")
    @Log(title = "人员考勤平台注册", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wsAttendanceRegisterService.deleteWsAttendanceRegisterByIds(ids));
    }
}
