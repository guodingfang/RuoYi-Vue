package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsTeamPersonExchangeRecord;
import com.jlkj.project.ws.domain.WsTeamPersonExchangeRecordDTO;
import com.jlkj.project.ws.domain.vo.WsTeamPersonExchangeRecordVO;
import com.jlkj.project.ws.service.IWsTeamPersonExchangeRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班组人员兑换记录Controller
 *
 * @author liujie
 * @date 2020-07-23
 */
@Api(value = "班组人员兑换记录管理")
@RestController
@RequestMapping("/ws/teamPersonExchangeRecord")
public class WsTeamPersonExchangeRecordController extends BaseController
{
    @Autowired
    private IWsTeamPersonExchangeRecordService wsTeamPersonExchangeRecordService;

    /**
     * 查询班组人员兑换记录列表
     */
    @ApiOperation("查询班组人员兑换记录列表")
   // @PreAuthorize("@ss.hasPermi('ws:teamPersonExchangeRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsTeamPersonExchangeRecord wsTeamPersonExchangeRecord)
    {
        startPage();
        List<WsTeamPersonExchangeRecordVO> list = wsTeamPersonExchangeRecordService.selectWsTeamPersonExchangeRecordList(wsTeamPersonExchangeRecord);
        return getDataTable(list);
    }

    /**
     * 导出班组人员兑换记录列表
     */
    @ApiOperation("导出班组人员兑换记录列表")
    @PreAuthorize("@ss.hasPermi('ws:teamPersonExchangeRecord:export')")
    @Log(title = "班组人员兑换记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsTeamPersonExchangeRecord wsTeamPersonExchangeRecord)
    {
        List<WsTeamPersonExchangeRecordVO> list = wsTeamPersonExchangeRecordService.selectWsTeamPersonExchangeRecordList(wsTeamPersonExchangeRecord);
        ExcelUtil<WsTeamPersonExchangeRecordVO> util = new ExcelUtil<WsTeamPersonExchangeRecordVO>(WsTeamPersonExchangeRecordVO.class);
        return util.exportExcel(list, "teamPersonExchangeRecord");
    }

    /**
     * 获取班组人员兑换记录详细信息
     */
    @ApiOperation("获取班组人员兑换记录详细信息")
    @PreAuthorize("@ss.hasPermi('ws:teamPersonExchangeRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsTeamPersonExchangeRecordService.selectWsTeamPersonExchangeRecordById(id));
    }

    /**
     * 获取班组人员已兑换点券信息
     */
    @ApiOperation("获取班组人员已兑换点券信息")
    @PreAuthorize("@ss.hasPermi('ws:teamPersonExchangeRecord:points')")
    @GetMapping(value = "/points/{personId}")
    public AjaxResult statisticstPersonExchangedPoints(@PathVariable("personId") Integer personId)
    {
        return AjaxResult.success(wsTeamPersonExchangeRecordService.statisticstPersonExchangedPoints(personId));
    }

    /**
     * 新增班组人员兑换记录
     */
    @ApiOperation("新增班组人员兑换记录")
    @PreAuthorize("@ss.hasPermi('ws:teamPersonExchangeRecord:add')")
    @Log(title = "班组人员兑换记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WsTeamPersonExchangeRecord wsTeamPersonExchangeRecord)
    {
        return toAjax(wsTeamPersonExchangeRecordService.insertWsTeamPersonExchangeRecord(wsTeamPersonExchangeRecord));
    }

    /**
     * 批量新增班组人员兑换记录
     */
    @ApiOperation("批量新增班组人员兑换记录")
    @PreAuthorize("@ss.hasPermi('ws:teamPersonExchangeRecord:batch')")
    @Log(title = "班组人员兑换记录", businessType = BusinessType.INSERT)
    @PostMapping("/batch")
    public AjaxResult batchAdd(@Validated @RequestBody WsTeamPersonExchangeRecordDTO wsTeamPersonExchangeRecordDTO)
    {
        return toAjax(wsTeamPersonExchangeRecordService.bacthWsTeamPersonExchangeRecord(wsTeamPersonExchangeRecordDTO));
    }

    /**
     * 修改班组人员兑换记录
     */
    @ApiOperation("修改班组人员兑换记录")
    @PreAuthorize("@ss.hasPermi('ws:teamPersonExchangeRecord:edit')")
    @Log(title = "班组人员兑换记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsTeamPersonExchangeRecord wsTeamPersonExchangeRecord)
    {
        return toAjax(wsTeamPersonExchangeRecordService.updateWsTeamPersonExchangeRecord(wsTeamPersonExchangeRecord));
    }

    /**
     * 删除班组人员兑换记录
     */
    @ApiOperation("删除班组人员兑换记录")
    @PreAuthorize("@ss.hasPermi('ws:teamPersonExchangeRecord:remove')")
    @Log(title = "班组人员兑换记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wsTeamPersonExchangeRecordService.deleteWsTeamPersonExchangeRecordByIds(ids));
    }
}
