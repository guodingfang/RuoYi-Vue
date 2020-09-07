package com.jlkj.project.ws.intelligence.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.excel.CommonExportExcel;
import com.jlkj.project.ws.intelligence.domain.WsElevatorManager;
import com.jlkj.project.ws.intelligence.domain.WsElevatorWorkCycleManagerDTO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleManagerVO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleStatisticsVO;
import com.jlkj.project.ws.intelligence.service.IWsElevatorManagerService;
import com.jlkj.project.ws.intelligence.service.IWsElevatorWorkCycleManagerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 升降机工作循环信息管理Controller
 *
 * @author liujie
 * @date 2020-07-03
 */
@RestController
@RequestMapping("/ws/elevatorWorkCycleManager")
public class WsElevatorWorkCycleManagerController extends BaseController
{
    @Autowired
    private IWsElevatorWorkCycleManagerService wsElevatorWorkCycleManagerService;

   @Autowired
    private IWsElevatorManagerService wsElevatorManagerService;
    /**
     * 查询升降机工作循环信息管理统计列表
     */
   // @PreAuthorize("@ss.hasPermi('ws:elevatorWorkCycleManager:statistics')")
    @GetMapping("/statistics")
    public AjaxResult statisticsWorkRecordList(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO)
    {
        WsElevatorManager wsElevatorManager = new WsElevatorManager();
        wsElevatorManager.setProjectId(wsElevatorWorkCycleMangerDTO.getProjectId());
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorManagerService.selectWsElevatorManagerList(wsElevatorManager);
        String[] serialNumbers =wsElevatorManagerService.getSerialNumbers(wsElevatorManagerVOS);
        wsElevatorWorkCycleMangerDTO.setSerialNumbers(serialNumbers);
        List<WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVOS = wsElevatorWorkCycleManagerService.statisticsElevatorWorkRecord(wsElevatorWorkCycleMangerDTO);
        List<WsElevatorWorkCycleStatisticsVO>  list = wsElevatorWorkCycleManagerService.assembleWorkCycleStatistics(wsElevatorManagerVOS,wsElevatorWorkCycleStatisticsVOS);
        return AjaxResult.success(list);
    }

    /**
     * 查询升降机司机不良操作统计列表
     */
    //@PreAuthorize("@ss.hasPermi('ws:elevatorWorkCycleManager:statisticsDriver')")
    @GetMapping("/statistics/driver")
    public AjaxResult statisticsDriverIllegalWorkRecordList(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO)
    {
        WsElevatorManager wsElevatorManager = new WsElevatorManager();
        wsElevatorManager.setProjectId(wsElevatorWorkCycleMangerDTO.getProjectId());
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorManagerService.selectWsElevatorManagerList(wsElevatorManager);
        String[] serialNumbers =wsElevatorManagerService.getSerialNumbers(wsElevatorManagerVOS);
        wsElevatorWorkCycleMangerDTO.setSerialNumbers(serialNumbers);
        List<WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVOS = wsElevatorWorkCycleManagerService.statisticsDriverIllegalRecord(wsElevatorWorkCycleMangerDTO);
        return AjaxResult.success(wsElevatorWorkCycleStatisticsVOS);
    }




    /**
     * 查询设备工作记录详情列表
     */
    //@PreAuthorize("@ss.hasPermi('ws:elevatorWorkCycleManager:details')")
    @GetMapping("/details")
    public TableDataInfo workRecordDetails(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO)
    {
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorWorkCycleManagerService.selectWsElevatorManagerVOList(wsElevatorWorkCycleMangerDTO);
        String[] serialNumbers =wsElevatorManagerService.getSerialNumbers(wsElevatorManagerVOS);
        wsElevatorWorkCycleMangerDTO.setSerialNumbers(serialNumbers);
        startPage();
        List<WsElevatorWorkCycleManagerVO> wsElevatorWorkCycleManagers = wsElevatorWorkCycleManagerService.selectElevatorWorkRecord(wsElevatorWorkCycleMangerDTO);
        List<WsElevatorWorkCycleManagerVO>  records =  wsElevatorWorkCycleManagerService.assembleWorkCycle(wsElevatorManagerVOS,wsElevatorWorkCycleManagers);

        return getDataTable(records);
    }

    /**
     * 导出工作记录详情列表
     */
   // @PreAuthorize("@ss.hasPermi('ws:elevatorWorkCycleManager:exportDetails')")
    @Log(title = "导出工作记录详情列表", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDetails")
    public AjaxResult exportDetails(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO)
    {
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorWorkCycleManagerService.selectWsElevatorManagerVOList(wsElevatorWorkCycleMangerDTO);
        String[] serialNumbers =wsElevatorManagerService.getSerialNumbers(wsElevatorManagerVOS);
        wsElevatorWorkCycleMangerDTO.setSerialNumbers(serialNumbers);
        List<WsElevatorWorkCycleManagerVO> wsElevatorWorkCycleManagers = wsElevatorWorkCycleManagerService.selectElevatorWorkRecord(wsElevatorWorkCycleMangerDTO);
        List<WsElevatorWorkCycleManagerVO>  records =  wsElevatorWorkCycleManagerService.assembleWorkCycle(wsElevatorManagerVOS,wsElevatorWorkCycleManagers);
        ExcelUtil<WsElevatorWorkCycleManagerVO> util = new ExcelUtil<WsElevatorWorkCycleManagerVO>(WsElevatorWorkCycleManagerVO.class);
        return util.exportExcel(records, "工作记录详情");
    }

    /**
     * 查询设备工作记录列表(设备状态分析)
     */
    //@PreAuthorize("@ss.hasPermi('ws:elevatorWorkCycleManager:list')")
    @GetMapping("/list")
    public AjaxResult list(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO)
    {
        List<WsElevatorWorkCycleManagerVO> wsElevatorWorkCycleManagers = wsElevatorWorkCycleManagerService.selectElevatorWorkRecord(wsElevatorWorkCycleMangerDTO);

        return AjaxResult.success(wsElevatorWorkCycleManagers);
    }



    /**
     * 查询设备工作记录详情列表
     */
    //@PreAuthorize("@ss.hasPermi('ws:elevatorWorkCycleManager:illegals')")
    @GetMapping("/illegals")
    public TableDataInfo workRecordIllegalList(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO)
    {
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorWorkCycleManagerService.selectWsElevatorManagerVOList(wsElevatorWorkCycleMangerDTO);

        String[] serialNumbers =wsElevatorManagerService.getSerialNumbers(wsElevatorManagerVOS);
        wsElevatorWorkCycleMangerDTO.setSerialNumbers(serialNumbers);
        startPage();
        List<WsElevatorWorkCycleManagerVO> wsElevatorWorkCycleManagers = wsElevatorWorkCycleManagerService.selectElevatorIllegalWorkRecord(wsElevatorWorkCycleMangerDTO);
        List<WsElevatorWorkCycleManagerVO>  records =  wsElevatorWorkCycleManagerService.assembleWorkCycle(wsElevatorManagerVOS,wsElevatorWorkCycleManagers);

        return getDataTable(records);
    }

    /**
     * 导出工作违章列表
     */
    //@PreAuthorize("@ss.hasPermi('ws:elevatorWorkCycleManager:exportIllegals')")
    @Log(title = "导出工作违章列表", businessType = BusinessType.EXPORT)
    @GetMapping("/exportIllegals")
    public AjaxResult exportIllegals(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO)
    {
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorWorkCycleManagerService.selectWsElevatorManagerVOList(wsElevatorWorkCycleMangerDTO);
        String[] serialNumbers =wsElevatorManagerService.getSerialNumbers(wsElevatorManagerVOS);
        wsElevatorWorkCycleMangerDTO.setSerialNumbers(serialNumbers);
        List<WsElevatorWorkCycleManagerVO> wsElevatorWorkCycleManagers = wsElevatorWorkCycleManagerService.selectElevatorIllegalWorkRecord(wsElevatorWorkCycleMangerDTO);
        List<WsElevatorWorkCycleManagerVO>  records =  wsElevatorWorkCycleManagerService.assembleWorkCycle(wsElevatorManagerVOS,wsElevatorWorkCycleManagers);
        CommonExportExcel excel =  wsElevatorWorkCycleManagerService.genIllegalExcel(records);
        return excel.exportExcel();
    }


    /**
     * 查询告设备告警分析
     */
    //@PreAuthorize("@ss.hasPermi('ws:elevatorWorkCycleManager:statisticsAlarming')")
    @GetMapping("/statistics/alarming")
    public AjaxResult deviceAlarmingAnalyze(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO)
    {
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorWorkCycleManagerService.selectWsElevatorManagerVOList(wsElevatorWorkCycleMangerDTO);
        String[] serialNumbers =wsElevatorManagerService.getSerialNumbers(wsElevatorManagerVOS);
        wsElevatorWorkCycleMangerDTO.setSerialNumbers(serialNumbers);
        List<WsElevatorWorkCycleManagerVO> wsElevatorWorkCycleManagers = wsElevatorWorkCycleManagerService.selectElevatorIllegalWorkRecord(wsElevatorWorkCycleMangerDTO);
        List<WsElevatorWorkCycleStatisticsVO>  list = wsElevatorWorkCycleManagerService.assembleAlarmingStatisticsByDate(wsElevatorWorkCycleMangerDTO,wsElevatorWorkCycleManagers);
        return AjaxResult.success(list);

    }

    /**
     * 查询项目报警列表
     */
    //@PreAuthorize("@ss.hasPermi('ws:elevatorWorkCycleManager:projectAlarmingList')")
    @GetMapping("/project/alarming/list")
    public AjaxResult projectAlarmingList(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO)
    {
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorWorkCycleManagerService.selectWsElevatorManagerVOList(wsElevatorWorkCycleMangerDTO);
        String[] serialNumbers =wsElevatorManagerService.getSerialNumbers(wsElevatorManagerVOS);
        wsElevatorWorkCycleMangerDTO.setSerialNumbers(serialNumbers);
        List<WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVOS = wsElevatorWorkCycleManagerService.statisticsElevatorWorkRecord(wsElevatorWorkCycleMangerDTO);
        List<WsElevatorWorkCycleStatisticsVO>  list = wsElevatorWorkCycleManagerService.assembleAlarmingByProject(wsElevatorManagerVOS,wsElevatorWorkCycleStatisticsVOS);
        return AjaxResult.success(list);

    }



    /**
     * 查询项目报警分析
     */
   // @PreAuthorize("@ss.hasPermi('ws:elevatorWorkCycleManager:projectAlarming')")
    @GetMapping("/project/alarming")
    public AjaxResult projectAlarmingAnalyze(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO)
    {
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorWorkCycleManagerService.selectWsElevatorManagerVOList(wsElevatorWorkCycleMangerDTO);

        String[] serialNumbers =wsElevatorManagerService.getSerialNumbers(wsElevatorManagerVOS);
        wsElevatorWorkCycleMangerDTO.setSerialNumbers(serialNumbers);
        List<WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVOS = wsElevatorWorkCycleManagerService.statisticsElevatorWorkRecord(wsElevatorWorkCycleMangerDTO);
        WsElevatorWorkCycleStatisticsVO  result = wsElevatorWorkCycleManagerService.assembleAllProjectAlarming(wsElevatorManagerVOS,wsElevatorWorkCycleStatisticsVOS);
        return AjaxResult.success(result);

    }


    /**
     * 查询设备实时监控列表
     */
    //@PreAuthorize("@ss.hasPermi('ws:elevatorWorkCycleManager:realTime')")
    @GetMapping("/statistics/realTime")
    public AjaxResult statisticsDeviceRealTimeDataList(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO)
    {
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorWorkCycleManagerService.selectWsElevatorMonitorDeviceList(wsElevatorWorkCycleMangerDTO);
        String[] serialNumbers =wsElevatorManagerService.getSerialNumbers(wsElevatorManagerVOS);
        List<WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVOS = wsElevatorWorkCycleManagerService.statisticsTodayAllInfo(serialNumbers);
        List<WsElevatorManagerVO> result =wsElevatorWorkCycleManagerService.assembleTodayAllInfo(wsElevatorWorkCycleStatisticsVOS,wsElevatorManagerVOS);
        return AjaxResult.success(result);

    }

    /**
     * 按日统计设备工作记录
     */
    //@PreAuthorize("@ss.hasPermi('ws:elevatorWorkCycleManager:days')")
    @ApiOperation("按日统计设备工作记录")
    @GetMapping("/statistics/days")
    public AjaxResult statisticsRecordsForDays(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO){
        List<WsElevatorWorkCycleStatisticsVO> list = wsElevatorWorkCycleManagerService.statisticsRecordsForDays(wsElevatorWorkCycleMangerDTO);
        return AjaxResult.success(list);

    }


    /**
     * 统计设备近期工作情况
     */
    ///@PreAuthorize("@ss.hasPermi('ws:elevatorWorkCycleManager:times')")
    @ApiOperation("统计设备近期工作情况")
    @GetMapping("/statistics/times")
    public AjaxResult statisticsRecentRecordForTimes(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO)
    {
        WsElevatorWorkCycleStatisticsVO wsElevatorWorkCycleStatisticsVO = wsElevatorWorkCycleManagerService.statisticsRecentRecord(wsElevatorWorkCycleMangerDTO);
        return AjaxResult.success(wsElevatorWorkCycleStatisticsVO);

    }

    /**
     * 统计设备今日和昨日工作情况
     */
    //@PreAuthorize("@ss.hasPermi('ws:elevatorWorkCycleManager:yesterdayAndToday')")
    @ApiOperation("统计设备今日和昨日工作情况")
    @GetMapping("/statistics/yesterdayAndToday")
    public AjaxResult statisticsYesterdayAndTodayrecord(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO)
    {
        Map<String,WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVO = wsElevatorWorkCycleManagerService.statisticsYesterdayAndTodayRecord(wsElevatorWorkCycleMangerDTO);
        return AjaxResult.success(wsElevatorWorkCycleStatisticsVO);

    }

    /**
     * 导出告警记录列表
     */
    //@PreAuthorize("@ss.hasPermi('ws:elevatorWorkCycleManager:exportAlarming')")
    @Log(title = "导出告警记录列表", businessType = BusinessType.EXPORT)
    @GetMapping("/exportAlarming")
    public AjaxResult exportAlarming(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO)
    {
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorWorkCycleManagerService.selectWsElevatorManagerVOList(wsElevatorWorkCycleMangerDTO);
        String[] serialNumbers =wsElevatorManagerService.getSerialNumbers(wsElevatorManagerVOS);
        wsElevatorWorkCycleMangerDTO.setSerialNumbers(serialNumbers);
        List<WsElevatorWorkCycleManagerVO> wsElevatorWorkCycleManagers = wsElevatorWorkCycleManagerService.selectElevatorIllegalWorkRecord(wsElevatorWorkCycleMangerDTO);
        List<WsElevatorWorkCycleManagerVO>  records =  wsElevatorWorkCycleManagerService.assembleWorkCycle(wsElevatorManagerVOS,wsElevatorWorkCycleManagers);
        ExcelUtil<WsElevatorWorkCycleManagerVO> util = new ExcelUtil<WsElevatorWorkCycleManagerVO>(WsElevatorWorkCycleManagerVO.class);
        return util.exportExcel(records, "告警记录");
    }

}
