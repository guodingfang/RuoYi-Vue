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
import com.jlkj.project.ws.domain.WsQualityAcceptanceRecord;
import com.jlkj.project.ws.service.IWsQualityAcceptanceRecordService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 质量验收记录 Controller
 *
 * @author gaowei
 * @date 2020-06-09
 */
@Api(value = "质量验收记录接口",tags = "质量验收记录页面")
@RestController
@RequestMapping("/ws/record")
public class WsQualityAcceptanceRecordController extends BaseController
{
    @Autowired
    private IWsQualityAcceptanceRecordService wsQualityAcceptanceRecordService;

    /**
     * 查询质量验收记录 列表
     */
    @PreAuthorize("@ss.hasPermi('ws:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsQualityAcceptanceRecord wsQualityAcceptanceRecord)
    {
        startPage();
        List<WsQualityAcceptanceRecord> list = wsQualityAcceptanceRecordService.selectWsQualityAcceptanceRecordList(wsQualityAcceptanceRecord);
        return getDataTable(list);
    }

    /**
     * 导出质量验收记录 列表
     */
    @PreAuthorize("@ss.hasPermi('ws:record:export')")
    @Log(title = "质量验收记录 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsQualityAcceptanceRecord wsQualityAcceptanceRecord)
    {
        List<WsQualityAcceptanceRecord> list = wsQualityAcceptanceRecordService.selectWsQualityAcceptanceRecordList(wsQualityAcceptanceRecord);
        ExcelUtil<WsQualityAcceptanceRecord> util = new ExcelUtil<WsQualityAcceptanceRecord>(WsQualityAcceptanceRecord.class);
        return util.exportExcel(list, "record");
    }

    /**
     * 获取质量验收记录 详细信息
     */
   /* @PreAuthorize("@ss.hasPermi('ws:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsQualityAcceptanceRecordService.selectWsQualityAcceptanceRecordById(id));
    }*/


    /**
     * 获取质量验收记录 详细信息
     */
    @ApiOperation("质量验收记录详细信息")
    @ApiImplicitParam(name = "wsQualityAcceptanceRecord", value = "质量验收记录实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:record:query')")
    @GetMapping(value = "/{applyId}")
    public AjaxResult getInfoApplyId(@PathVariable("applyId") Integer applyId)
    {
        List<WsQualityAcceptanceRecord> list = wsQualityAcceptanceRecordService.listWsQualityAcceptanceRecordByApplyId(applyId);
        return AjaxResult.success(list);
    }

    /**
     * 新增质量验收记录
     */
    @ApiOperation("质量验收")
    @ApiImplicitParam(name = "wsQualityAcceptanceRecord", value = "质量验收记录实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:record:add')")
    @Log(title = "质量验收记录 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsQualityAcceptanceRecord wsQualityAcceptanceRecord)
    {
        return toAjax(wsQualityAcceptanceRecordService.insertWsQualityAcceptanceRecord(wsQualityAcceptanceRecord));
    }

    /**
     * 修改质量验收记录 
     */
    @PreAuthorize("@ss.hasPermi('ws:record:edit')")
    @Log(title = "质量验收记录 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsQualityAcceptanceRecord wsQualityAcceptanceRecord)
    {
        return toAjax(wsQualityAcceptanceRecordService.updateWsQualityAcceptanceRecord(wsQualityAcceptanceRecord));
    }

    /**
     * 删除质量验收记录 
     */
    @PreAuthorize("@ss.hasPermi('ws:record:remove')")
    @Log(title = "质量验收记录 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wsQualityAcceptanceRecordService.deleteWsQualityAcceptanceRecordByIds(ids));
    }
}
