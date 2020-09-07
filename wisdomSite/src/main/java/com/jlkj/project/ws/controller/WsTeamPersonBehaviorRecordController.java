package com.jlkj.project.ws.controller;

import cn.hutool.core.date.DateUtil;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.interceptor.annotation.RepeatSubmit;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsTeamPersonBehaviorRecord;
import com.jlkj.project.ws.domain.vo.WsTeamPersonBehaviorRecordVO;
import com.jlkj.project.ws.excel.CommonExportExcel;
import com.jlkj.project.ws.service.IWsTeamPersonBehaviorRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 班组人员行为记录Controller
 *
 * @author liujie
 * @date 2020-07-23
 */
@Api(value = "班组人员行为记录")
@RestController
@RequestMapping("/ws/teamPersonBehaviorRecord")
public class WsTeamPersonBehaviorRecordController extends BaseController
{
    @Autowired
    private IWsTeamPersonBehaviorRecordService wsTeamPersonBehaviorRecordService;

    /**
     * 查询班组人员行为记录列表
     */
    @ApiOperation("查询班组人员行为记录列表")
    //@PreAuthorize("@ss.hasPermi('ws:teamPersonBehaviorRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord)
    {
        startPage();
        List<WsTeamPersonBehaviorRecordVO> list = wsTeamPersonBehaviorRecordService.selectWsTeamPersonBehaviorRecordList(wsTeamPersonBehaviorRecord);
        return getDataTable(list);
    }

    /**
     * 按人员查询班组人员行为记录列表
     */
    @ApiOperation("按人员查询班组人员行为记录列表")
    //@PreAuthorize("@ss.hasPermi('ws:teamPersonBehaviorRecord:persons')")
    @GetMapping("/list/persons")
    public TableDataInfo listPersons(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord)
    {
        startPage();
        List<WsTeamPersonBehaviorRecordVO> list = wsTeamPersonBehaviorRecordService.selectWsTeamPersonBehaviorRecordListByPerson(wsTeamPersonBehaviorRecord);
        return getDataTable(list);
    }

    /**
     * 按班组查询班组人员行为记录列表
     */
    @ApiOperation("按班组查询班组人员行为记录列表")
    @PreAuthorize("@ss.hasPermi('ws:teamPersonBehaviorRecord:teams')")
    @GetMapping("/list/teams")
    public TableDataInfo listTeams(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord)
    {
        startPage();

        List<WsTeamPersonBehaviorRecordVO> list = wsTeamPersonBehaviorRecordService.selectWsTeamPersonBehaviorRecordListByTeam(wsTeamPersonBehaviorRecord);
        return getDataTable(list);
    }

    /**
     * 导出班组人员行为记录列表
     */
    @ApiOperation("导出班组人员行为记录列表")
    @PreAuthorize("@ss.hasPermi('ws:teamPersonBehaviorRecord:export')")
    @Log(title = "班组人员行为记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord)
    {
        List<WsTeamPersonBehaviorRecordVO> list = wsTeamPersonBehaviorRecordService.selectWsTeamPersonBehaviorRecordList(wsTeamPersonBehaviorRecord);
        ExcelUtil<WsTeamPersonBehaviorRecordVO> util = new ExcelUtil<WsTeamPersonBehaviorRecordVO>(WsTeamPersonBehaviorRecordVO.class);
        return util.exportExcel(list, "teamPersonBehaviorRecord");
    }


    /**
     * 导出班组人员行为记录列表
     */
    @ApiOperation("导出班组人员行为记录列表")
    @PreAuthorize("@ss.hasPermi('ws:teamPersonBehaviorRecord:export')")
    @Log(title = "班组人员行为记录", businessType = BusinessType.EXPORT)
    @GetMapping("/test/export")
    public AjaxResult testexport(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord)
    {
        wsTeamPersonBehaviorRecord.setMonth(DateUtil.parse("2020-07","yyyy-MM"));

        List<WsTeamPersonBehaviorRecordVO> records = wsTeamPersonBehaviorRecordService.selectWsTeamPersonBehaviorRecordList(wsTeamPersonBehaviorRecord);
        ArrayList<String> header = new ArrayList<>();
        ArrayList<List<String>> body = new ArrayList<>();
        ArrayList<String> data;
        for (WsTeamPersonBehaviorRecordVO record : records) {
            if(header.size()==0){
                header.add("人员姓名");
                header.add("单位姓名");
                header.add("班组姓名");
                header.add("工种");
                header.add("人脸照片");
                header.add("已有点券");
            }
            data = new ArrayList<>();
            data.add(record.getPersonName());
            data.add(record.getUnitName());
            data.add(record.getTeamName());
            data.add(record.getWorkTypeName());
            data.add(record.getFaceUrl());
            data.add(String.valueOf(record.getHasPointPersons()));
            body.add(data);
        }

        CommonExportExcel commonExportExcel = new CommonExportExcel("行为记录",body,header);
       return commonExportExcel.exportExcel();
    }

    /**
     * 统计班组人员点券/积分值
     */
    @ApiOperation("统计班组人员点券/积分值")
    @PreAuthorize("@ss.hasPermi('ws:teamPersonBehaviorRecord:points')")
    @GetMapping(value = "/points/{personId}")
    public AjaxResult statisticsPersonPoints(@PathVariable("personId") Integer personId)
    {
        WsTeamPersonBehaviorRecordVO point =wsTeamPersonBehaviorRecordService.statisticstPersonTotalPoints(personId);
        return AjaxResult.success(point);
    }


    /**
     * 统计班组人员可兑换点券
     */
    @ApiOperation("统计班组人员可兑换点券")
    @PreAuthorize("@ss.hasPermi('ws:teamPersonBehaviorRecord:exchangeable')")
    @GetMapping(value = "exchangeable/{personId}")
    public AjaxResult statisticsPersonExchangeablePoints(@PathVariable("personId") Integer personId)
    {
        WsTeamPersonBehaviorRecordVO point =wsTeamPersonBehaviorRecordService.statisticstPersonExchangeablePoints(personId);
        return AjaxResult.success(point);
    }

    /**
     * 获取人员行为评价
     */
    @ApiOperation("获取人员行为评价")
   // @PreAuthorize("@ss.hasPermi('ws:teamPersonBehaviorRecord:evaluate')")
    @GetMapping(value = "/evaluate")
    public TableDataInfo personBehaviorEvaluate(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord)
    {
        startPage();
        List<WsTeamPersonBehaviorRecordVO> wsTeamPersonBehaviorRecordVOS = wsTeamPersonBehaviorRecordService.selectPersonBehaviorEvaluate(wsTeamPersonBehaviorRecord);
        return getDataTable(wsTeamPersonBehaviorRecordVOS);
    }


    /**
     * 新增班组人员行为记录
     */
    @RepeatSubmit
    @ApiOperation("新增班组人员行为记录")
    @PreAuthorize("@ss.hasPermi('ws:teamPersonBehaviorRecord:add')")
    @Log(title = "班组人员行为记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord)
    {
        return toAjax(wsTeamPersonBehaviorRecordService.insertWsTeamPersonBehaviorRecord(wsTeamPersonBehaviorRecord));
    }

    /**
     * 修改班组人员行为记录
     */
    @RepeatSubmit
    @ApiOperation("修改班组人员行为记录")
    @PreAuthorize("@ss.hasPermi('ws:teamPersonBehaviorRecord:edit')")
    @Log(title = "班组人员行为记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord)
    {
        return toAjax(wsTeamPersonBehaviorRecordService.updateWsTeamPersonBehaviorRecord(wsTeamPersonBehaviorRecord));
    }

    /**
     * 删除班组人员行为记录
     */
    @PreAuthorize("@ss.hasPermi('ws:teamPersonBehaviorRecord:remove')")
    @Log(title = "班组人员行为记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wsTeamPersonBehaviorRecordService.deleteWsTeamPersonBehaviorRecordByIds(ids));
    }
}
