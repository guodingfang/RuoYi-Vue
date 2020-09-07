package com.jlkj.project.ws.intelligence.controller;

import com.jlkj.common.utils.StringUtils;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.interceptor.annotation.RepeatSubmit;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsArea;
import com.jlkj.project.ws.excel.CommonExportExcel;
import com.jlkj.project.ws.intelligence.domain.WsBlackBox;
import com.jlkj.project.ws.intelligence.domain.WsElevatorDriverInfo;
import com.jlkj.project.ws.intelligence.domain.WsElevatorManager;
import com.jlkj.project.ws.intelligence.domain.WsElevatorManagerDTO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO;
import com.jlkj.project.ws.intelligence.service.IWsBlackBoxService;
import com.jlkj.project.ws.intelligence.service.IWsElevatorManagerService;
import com.jlkj.project.ws.service.IWsAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 施工电梯设备管理Controller
 *
 * @author liujie
 * @date 2020-07-03
 */
@Api(tags = "施工电梯设备管理")
@RestController
@RequestMapping("/ws/elevatorManager")
public class WsElevatorManagerController extends BaseController
{
    @Autowired
    private IWsElevatorManagerService wsElevatorManagerService;

    @Autowired
    private IWsBlackBoxService wsBlackBoxService;

    @Autowired
    private IWsAreaService wsAreaService;

    /**
     * 查询施工电梯设备管理列表
     */
   // @PreAuthorize("@ss.hasPermi('ws:elevatorManager:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsElevatorManager wsElevatorManager)
    {
        startPage();
        List<WsElevatorManagerVO> list = wsElevatorManagerService.selectWsElevatorManagerList(wsElevatorManager);
        return getDataTable(list);
    }

    /**
     * 查询施工电梯设备在线状态列表
     */
    // @PreAuthorize("@ss.hasPermi('ws:elevatorManager:onlineState')")
    @GetMapping("list/onlineState")
    public AjaxResult deviceOnlineStateList(WsElevatorManagerDTO wsElevatorManagerDTO)
    {
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorManagerService.selectWsElevatorManagerVOListByDTO(wsElevatorManagerDTO);
        String[] serialNumbers = wsElevatorManagerService.selectMonitorDeviceSerialNumberList(wsElevatorManagerDTO);
        WsBlackBox wsBlackBox = new WsBlackBox();
        wsBlackBox.setSerialNumbers(serialNumbers);
        List<WsBlackBox> wsBlackBoxes = wsBlackBoxService.selectWsBlackBoxList(wsBlackBox);
        List<WsElevatorManagerVO> list = wsElevatorManagerService.assembleWsElevatorManagerOnlineStateList(wsBlackBoxes,wsElevatorManagerVOS);
        return AjaxResult.success(list);
    }



    /**
     * 获取施工电梯设备管理详细信息
     */
    //@RepeatSubmit
    @ApiOperation("获取施工电梯设备管理详细信息")
  //  @PreAuthorize("@ss.hasPermi('ws:elevatorManager:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsElevatorManagerService.selectWsElevatorManagerById(id));
    }

    /**
     * 新增施工电梯设备管理
     */
    @RepeatSubmit
    @ApiOperation("新增施工电梯设备管理")
   // @PreAuthorize("@ss.hasPermi('ws:elevatorManager:add')")
    @Log(title = "施工电梯设备管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody WsElevatorManagerDTO wsElevatorMangerDTO)
    {

        return toAjax(wsElevatorManagerService.insertWsElevatorManager(wsElevatorMangerDTO));
    }

    /**
     * 批量标记点位
     */
    @ApiOperation("批量标记点位")
    // @PreAuthorize("@ss.hasPermi('ws:elevatorManager:bacthSign')")
    @Log(title = "批量标记点位", businessType = BusinessType.UPDATE)
    @PostMapping("/bacthSign")
    public AjaxResult bacthSignPoint(@RequestBody WsElevatorManagerDTO wsElevatorMangerDTO)
    {
        return toAjax(wsElevatorManagerService.bacthSignPoint(wsElevatorMangerDTO));
    }

    /**
      * 删除设备点位标记
     */
    @ApiOperation("删除设备点位标记")
    // @PreAuthorize("@ss.hasPermi('ws:elevatorManager:unSign')")
    @Log(title = "批量标记点位", businessType = BusinessType.UPDATE)
    @PutMapping("/unSign/{id}")
    public AjaxResult unSignPoint(@PathVariable("id")Integer id)
    {
        return toAjax(wsElevatorManagerService.unSignPoint(id));
    }

    /**
     * 修改施工电梯设备管理
     */
    @ApiOperation("修改施工电梯设备管理")
    //@PreAuthorize("@ss.hasPermi('ws:elevatorManager:edit')")
    @Log(title = "施工电梯设备管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody WsElevatorManagerDTO wsElevatorMangerDTO)
    {
        return toAjax(wsElevatorManagerService.updateWsElevatorManager(wsElevatorMangerDTO));
    }


    /**
     * 施工电梯设备退场
     */
    @ApiOperation("施工电梯设备退场")
    //@PreAuthorize("@ss.hasPermi('ws:elevatorManager:offSite')")
    @Log(title = "施工电梯设备", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/offSite")
    public AjaxResult offSite( @PathVariable("id")Integer id)
    {
        return toAjax(wsElevatorManagerService.offSite(id));
    }

    /**
     * 删除施工电梯设备管理
     */
    @ApiOperation("施工电梯设备删除")
   // @PreAuthorize("@ss.hasPermi('ws:elevatorManager:remove')")
    @Log(title = "施工电梯设备管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {

        return toAjax(wsElevatorManagerService.deleteWsElevatorManagerByIds(ids));
    }



    /**
     * 监控设备分析列表
     */
    // @PreAuthorize("@ss.hasPermi('ws:elevatorManager:statistics')")
    @GetMapping("/statistics/list")
    public AjaxResult monitorDeviceAnalyszeList(WsElevatorManagerDTO wsElevatorManagerDTO)
    {
        List<WsElevatorManagerStatisticsVO> wsElevatorManagerStatisticsVOS = wsElevatorManagerService.statisticsDeviceManagerList(wsElevatorManagerDTO);
        return AjaxResult.success(wsElevatorManagerStatisticsVOS);
    }

    /**
     * 统计项目施工电梯设备管理列表
     */
    // @PreAuthorize("@ss.hasPermi('ws:elevatorManager:statisticsProject')")
    @GetMapping("/statistics/project/list")
    public TableDataInfo statisticsDeviceListByProject(WsElevatorManagerDTO wsElevatorManagerDTO)
    {
        startPage();
        List<WsElevatorManagerStatisticsVO> wsElevatorManagerStatisticsVOS = wsElevatorManagerService.statisticsDeviceManagerListByProject(wsElevatorManagerDTO);
        return  getDataTable(wsElevatorManagerStatisticsVOS);
    }


    /**
     * 统计项目施工电梯设备在线情况
     */
    // @PreAuthorize("@ss.hasPermi('ws:elevatorManager:statisticsOnline')")
    @GetMapping("/statistics/online")
    public AjaxResult statisticsDeviceOnline(WsElevatorManagerDTO wsElevatorManagerDTO)
    {
        String[] serialNumbers = wsElevatorManagerService.selectMonitorDeviceSerialNumberList(wsElevatorManagerDTO);
        WsElevatorManagerStatisticsVO wsElevatorManagerStatisticsVO = wsBlackBoxService.statisticsOnline(serialNumbers);
        return AjaxResult.success(wsElevatorManagerStatisticsVO);
    }


    /**
     * 项目设备统计详情列表
     */
    // @PreAuthorize("@ss.hasPermi('ws:elevatorManager:projectDetails')")
    @GetMapping("/project/details")
    public TableDataInfo projectStatisticsDetailsList(WsElevatorManagerDTO wsElevatorManagerDTO)
    {
        startPage();
        List<WsElevatorManagerStatisticsVO> wsElevatorManagerStatisticsVOS = wsElevatorManagerService.statisticsProjectDeviceList(wsElevatorManagerDTO);

        String[]serialNumbers =wsElevatorManagerService.selectMonitorDeviceSerialNumberList(wsElevatorManagerDTO);

        WsBlackBox wsBlackBox = new WsBlackBox();
        wsBlackBox.setSerialNumbers(serialNumbers);
        wsBlackBox.setEquipmentType("2");
        List<WsBlackBox> wsBlackBoxes = wsBlackBoxService.selectWsBlackBoxList(wsBlackBox);
        List<WsElevatorManagerStatisticsVO> result=wsElevatorManagerService.assembleProjectDeviceStatisticsList(wsBlackBoxes,wsElevatorManagerStatisticsVOS);
        return getDataTable(result);
    }

    /**
     * 导出设备详情 列表
     */
    @ApiOperation("导出设备详情项目统计列表")
    // @PreAuthorize("@ss.hasPermi('ws:elevatorManager:exportDetails')")
    @Log(title = "导出设备详情项目统计列表 ", businessType = BusinessType.EXPORT)
    @GetMapping("/project/exportDetails")
    public AjaxResult exportProjectStatisticsDetails(WsElevatorManagerDTO wsElevatorManagerDTO, String sheetName)
    {
        List<WsElevatorManagerStatisticsVO> wsElevatorManagerStatisticsVOS = wsElevatorManagerService.statisticsProjectDeviceList(wsElevatorManagerDTO);

        String[]serialNumbers =wsElevatorManagerService.selectMonitorDeviceSerialNumberList(wsElevatorManagerDTO);

        WsBlackBox wsBlackBox = new WsBlackBox();
        wsBlackBox.setSerialNumbers(serialNumbers);
        wsBlackBox.setEquipmentType("2");
        List<WsBlackBox> wsBlackBoxes = wsBlackBoxService.selectWsBlackBoxList(wsBlackBox);
        List<WsElevatorManagerStatisticsVO> result=wsElevatorManagerService.assembleProjectDeviceStatisticsList(wsBlackBoxes,wsElevatorManagerStatisticsVOS);
        CommonExportExcel excel =  wsElevatorManagerService.genProjectStatisticsDetailExcel(result);
        return excel.exportExcel();

    }

    /**
     * 项目施工电梯设备详情列表（含在线，离线）
     */
    // @PreAuthorize("@ss.hasPermi('ws:elevatorManager:details')")
    @GetMapping("/details")
    public TableDataInfo selectDeviceDetailsList(WsElevatorManagerDTO wsElevatorManagerDTO)
    {
        startPage();
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorManagerService.selectWsElevatorManagerVOListByDTO(wsElevatorManagerDTO);

        String[] serialNumbers = wsElevatorManagerService.getSerialNumbers(wsElevatorManagerVOS);

        WsBlackBox wsBlackBox = new WsBlackBox();
        wsBlackBox.setSerialNumbers(serialNumbers);
        wsBlackBox.setEquipmentType("2");
        List<WsBlackBox> wsBlackBoxes = wsBlackBoxService.selectWsBlackBoxList(wsBlackBox);
        List<WsElevatorManagerVO> result=wsElevatorManagerService.assembleWsElevatorManagerVOList(wsBlackBoxes,wsElevatorManagerVOS);
        return getDataTable(result);
    }


    /**
     * 导出设备详情 列表
     */
    @ApiOperation("导出设备详情列表")
   // @PreAuthorize("@ss.hasPermi('ws:elevatorManager:exportDetails')")
    @Log(title = "导出设备详情列表 ", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDetails")
    public AjaxResult export(WsElevatorManagerDTO wsElevatorManagerDTO, String sheetName)
    {
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorManagerService.selectWsElevatorManagerVOListByDTO(wsElevatorManagerDTO);

        String[] serialNumbers = wsElevatorManagerService.getSerialNumbers(wsElevatorManagerVOS);

        WsBlackBox wsBlackBox = new WsBlackBox();
        wsBlackBox.setSerialNumbers(serialNumbers);
        wsBlackBox.setEquipmentType("2");
        List<WsBlackBox> wsBlackBoxes = wsBlackBoxService.selectWsBlackBoxList(wsBlackBox);
        List<WsElevatorManagerVO> result=wsElevatorManagerService.assembleWsElevatorManagerVOList(wsBlackBoxes,wsElevatorManagerVOS);
        CommonExportExcel excel =  wsElevatorManagerService.genDetailExcel(result);
        return excel.exportExcel();

    }

    /**
     * 获取最新司机信息
     */
    @ApiOperation("获取最新司机信息")
    //@PreAuthorize("@ss.hasPermi('ws:elevatorManager:driver')")
    @GetMapping(value = "/driver")
    public AjaxResult getDriverDetail( String serialNumber)
    {
        return AjaxResult.success(wsElevatorManagerService.findNewestRecognitionBySerialNumber(serialNumber));
    }

    /**
     * 获取历史司机认证信息
     */

    @ApiOperation("获取历史司机认证信息")
    //@PreAuthorize("@ss.hasPermi('ws:elevatorManager:drivers')")
    @GetMapping(value = "/driverList")
    public TableDataInfo getDriverRecognitionList( WsElevatorDriverInfo wsElevatorDriverInfo)
    {
        startPage();
        List<WsElevatorDriverInfo> result = wsElevatorManagerService.selectRecognitionListByDriver(wsElevatorDriverInfo);
        return getDataTable(result);
    }

    /**
     * 获取区域设备统计列表assembleMonitorDeviceOnlineStatistics
     */

    @ApiOperation("获取区域设备统计列表")
    //@PreAuthorize("@ss.hasPermi('ws:elevatorManager:area')")
    @PostMapping(value = "/area")
    public AjaxResult getAreaDeviceStatisticsList(@RequestBody WsElevatorManagerDTO wsElevatorManagerDTO)
    {

        if (getAreaCode(wsElevatorManagerDTO)) return AjaxResult.error("区域信息不能为空");
        return AjaxResult.success(wsElevatorManagerService.getAreaDeviceStatisticsList(wsElevatorManagerDTO));
    }

    private boolean getAreaCode(WsElevatorManagerDTO wsElevatorManagerDTO) {
        if(StringUtils.isNull(wsElevatorManagerDTO.getWsArea())) return true;
        WsArea wsArea = wsElevatorManagerDTO.getWsArea();
        String areaCode = wsAreaService.getZoomCode(wsArea.getZoom(),wsArea.getLongitude(),wsArea.getLatitude());
        wsElevatorManagerDTO.setAreaCode(areaCode);
        return false;
    }

    /**
     * 获取区域设备整体统计
     */

    @ApiOperation("获取区域设备整体统计")
    //@PreAuthorize("@ss.hasPermi('ws:elevatorManager:areaAll')")
    @PostMapping(value = "/area/all")
    public AjaxResult getAreaAllDeviceStatistics( @RequestBody  WsElevatorManagerDTO wsElevatorManagerDTO)
    {
        if (getAreaCode(wsElevatorManagerDTO)) return AjaxResult.error("区域信息不能为空");
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorManagerService.selectWsElevatorMonitorDeviceListByDTO(wsElevatorManagerDTO);
        WsElevatorManagerStatisticsVO  vo=  wsElevatorManagerService.statisticsAllDeviceData(wsElevatorManagerDTO);

        WsElevatorManagerStatisticsVO wsElevatorManagerStatisticsVO =  wsElevatorManagerService.getDeviceOnlineAndAlarmingStatistics(wsElevatorManagerVOS);
        if (StringUtils.isNotNull(vo)) {
            wsElevatorManagerStatisticsVO.setProjectNumber(vo.getProjectNumber());
            wsElevatorManagerStatisticsVO.setDoubleDeviceNumber(vo.getDoubleDeviceNumber());
            wsElevatorManagerStatisticsVO.setDeviceNumber(vo.getDeviceNumber());
        }
        return AjaxResult.success(wsElevatorManagerStatisticsVO);
    }

    /**
     * 获取项目设备整体统计
     */

    @ApiOperation("获取项目设备整体统计")
    //@PreAuthorize("@ss.hasPermi('ws:elevatorManager:projectAll')")
    @GetMapping(value = "/project/all")
    public AjaxResult getProjectAllDeviceStatistics( WsElevatorManagerDTO wsElevatorManagerDTO)
    {
        List<WsElevatorManagerStatisticsVO> vos = wsElevatorManagerService.statisticsProjectDeviceList(wsElevatorManagerDTO);
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorManagerService.selectWsElevatorMonitorDeviceListByDTO(wsElevatorManagerDTO);
        WsElevatorManagerStatisticsVO vo =StringUtils.isEmpty(vos)?null:vos.get(0);
        WsElevatorManagerStatisticsVO wsElevatorManagerStatisticsVO =  wsElevatorManagerService.getDeviceOnlineAndAlarmingStatistics(wsElevatorManagerVOS);
        if (StringUtils.isNotNull(vo)) {
            wsElevatorManagerStatisticsVO.setProjectNumber(vo.getProjectNumber());
            wsElevatorManagerStatisticsVO.setDoubleDeviceNumber(vo.getDoubleDeviceNumber());
            wsElevatorManagerStatisticsVO.setDeviceNumber(vo.getDeviceNumber());
        }else{
            wsElevatorManagerStatisticsVO.setProjectNumber(0);
            wsElevatorManagerStatisticsVO.setDoubleDeviceNumber(0);
            wsElevatorManagerStatisticsVO.setDeviceNumber(0);
        }
        return AjaxResult.success(wsElevatorManagerStatisticsVO);
    }


}
