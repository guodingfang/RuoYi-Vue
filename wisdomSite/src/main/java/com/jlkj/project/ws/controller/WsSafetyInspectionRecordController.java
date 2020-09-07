package com.jlkj.project.ws.controller;

import java.util.Arrays;
import java.util.List;

import io.swagger.annotations.Api;
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
import com.jlkj.project.ws.domain.WsSafetyInspectionRecord;
import com.jlkj.project.ws.service.IWsSafetyInspectionRecordService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 安全巡视记录 Controller
 *
 * @author jlkj
 * @date 2020-07-13
 */
@Api(tags = "安全巡视记录接口")
@RestController
@RequestMapping("/ws/inspection/record")
public class WsSafetyInspectionRecordController extends BaseController
{
    @Autowired
    private IWsSafetyInspectionRecordService wsSafetyInspectionRecordService;

    /**
     * 查询安全巡视记录 列表
     */
    @ApiOperation("动态条件查询安全巡视记录")
    @PreAuthorize("@ss.hasPermi('ws:inspection/record:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsSafetyInspectionRecord wsSafetyInspectionRecord)
    {
        startPage();
        List<WsSafetyInspectionRecord> list = wsSafetyInspectionRecordService.selectWsSafetyInspectionRecordList(wsSafetyInspectionRecord);
        return getDataTable(list);
    }

    /**
     * 导出安全巡视记录 列表
     */
    @ApiOperation("导出安全巡视记录")
    @PreAuthorize("@ss.hasPermi('ws:inspection/record:export')")
    @Log(title = "安全巡视记录 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsSafetyInspectionRecord wsSafetyInspectionRecord)
    {
        List<WsSafetyInspectionRecord> list = wsSafetyInspectionRecordService.selectWsSafetyInspectionRecordList(wsSafetyInspectionRecord);
        for (int i = 0; i < list.size(); i++) {
            String senderName = list.get(i).getSenderName();
            list.get(i).setSenderNames(String.join(",", senderName));
        }
        ExcelUtil<WsSafetyInspectionRecord> util = new ExcelUtil<WsSafetyInspectionRecord>(WsSafetyInspectionRecord.class);
        return util.exportExcel(list, "inspectionRecord");
    }

    /**
     * 获取安全巡视记录 详细信息
     */
    @ApiOperation("查询单个安全巡视记录")
    @PreAuthorize("@ss.hasPermi('ws:inspection/record:select')")
    @GetMapping(value = "/select/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsSafetyInspectionRecordService.selectWsSafetyInspectionRecordById(id));
    }

    /**
     * 查看安全巡视记录及隐患整改和验证
     */
    @ApiOperation("查看安全巡视记录及隐患整改和验证")
    @PreAuthorize("@ss.hasPermi('ws:inspection/record:query')")
    @GetMapping(value = "/query/{id}")
    public AjaxResult getDetail(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsSafetyInspectionRecordService.checkSafetyInspectionRectificationVerificationDetails(id));
    }

    /**
     * 新增安全巡视记录 
     */
    @ApiOperation("新增安全巡视记录")
    @PreAuthorize("@ss.hasPermi('ws:inspection/record:add')")
    @Log(title = "安全巡视记录 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsSafetyInspectionRecord wsSafetyInspectionRecord)
    {
        return toAjax(wsSafetyInspectionRecordService.insertWsSafetyInspectionRecord(wsSafetyInspectionRecord));
    }



}
