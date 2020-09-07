package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsAttendanceBinding;
import com.jlkj.project.ws.dto.WsAttendanceBindingDTO;
import com.jlkj.project.ws.service.IWsAttendanceBindingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考勤绑定 Controller
 *
 * @author liujie
 * @date 2020-06-23
 */
@Api(tags = "人员考勤机绑定管理")
@RestController
@RequestMapping("/ws/attendanceBinding")
public class WsAttendanceBindingController extends BaseController
{
    @Autowired
    private IWsAttendanceBindingService wsAttendanceBindingService;

    /**
     * 查询考勤绑定 列表
     */
    @PreAuthorize("@ss.hasPermi('ws:attendanceBinding:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsAttendanceBinding wsAttendanceBinding)
    {
        startPage();
        List<WsAttendanceBinding> list = wsAttendanceBindingService.selectWsAttendanceBindingList(wsAttendanceBinding);
        return getDataTable(list);
    }

    /**
     * 导出考勤绑定 列表
     */
    @PreAuthorize("@ss.hasPermi('ws:attendanceBinding:export')")
    @Log(title = "考勤绑定 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsAttendanceBinding wsAttendanceBinding)
    {
        List<WsAttendanceBinding> list = wsAttendanceBindingService.selectWsAttendanceBindingList(wsAttendanceBinding);
        ExcelUtil<WsAttendanceBinding> util = new ExcelUtil<WsAttendanceBinding>(WsAttendanceBinding.class);
        return util.exportExcel(list, "attendanceBinding");
    }



    /**
     * 新增考勤绑定
     */
    @ApiOperation("新增人员考勤绑定")
    @ApiImplicitParam(name = "考勤绑定",value = "wsAttendanceBinding",dataType = "WsAttendanceBinding",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:attendanceBinding:add')")
    @Log(title = "考勤绑定 ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WsAttendanceBinding wsAttendanceBinding)
    {
        return toAjax(wsAttendanceBindingService.insertWsAttendanceBinding(wsAttendanceBinding));
    }

    /**
     * 新增考勤绑定
     */
    @ApiOperation("批量新增人员考勤绑定")
    @ApiImplicitParam(name = "人员考勤绑定DTO",value = "wsAttendanceBindingDTO",dataType = "WsAttendanceBindingDTO",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:attendanceBinding:batch')")
    @Log(title = "考勤绑定 ", businessType = BusinessType.INSERT)
    @PostMapping("/batch")
    public AjaxResult batchAdd(@RequestBody WsAttendanceBindingDTO wsAttendanceBindingDTO)
    {

        AjaxResult ajax = AjaxResult.success();
        int authCount=wsAttendanceBindingService.batchAddWsAttendanceBinding(wsAttendanceBindingDTO);
        ajax.put("authCount",authCount);
        ajax.put("totalCount",wsAttendanceBindingDTO.getGuids().length);
        ajax.put("unAuthCount",wsAttendanceBindingDTO.getGuids().length-authCount);
        return ajax;
    }

    /**
     * 修改考勤绑定
     */
    @PreAuthorize("@ss.hasPermi('ws:attendanceBinding:edit')")
    @Log(title = "考勤绑定 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsAttendanceBinding wsAttendanceBinding)
    {
        return toAjax(wsAttendanceBindingService.updateWsAttendanceBinding(wsAttendanceBinding));
    }

    /**
     * 删除人员考勤绑定
     */
    @ApiOperation("批量删除人员考勤绑定")
    @ApiImplicitParam(name = "人员云平台唯一标识",value = "guids",dataType = "String",paramType = "path")
    @PreAuthorize("@ss.hasPermi('ws:attendanceBinding:remove')")
    @Log(title = "考勤绑定 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{guids}")
    public AjaxResult remove(@PathVariable("guids") String... guids)
    {
        return toAjax(wsAttendanceBindingService.deleteWsAttendanceBindingByGuids(guids));
    }
}
