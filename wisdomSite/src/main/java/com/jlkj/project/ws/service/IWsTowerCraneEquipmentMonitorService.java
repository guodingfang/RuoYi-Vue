package com.jlkj.project.ws.service;


import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.dto.*;
import com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout;
import com.jlkj.project.ws.intelligence.domain.WsTowercraneEquipment;

import java.util.List;

/**
 * 塔机监测 Service接口
 *
 * @author gaowei
 * @date 2020-06-29
 */
public interface IWsTowerCraneEquipmentMonitorService
{
    /*塔机监控-企业级*/
    public WsTowerCraneMonitoringRealtimeDTO selectRealTimeMonitoring(WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO);

    public List<WsTowerCraneMonitoringRealtimeDTO> selectEquipmentList(WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO);

    public List<WsTowerCraneMonitoringRealtimeDTO> selectIllegalList(WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO);

    public List<WsTowerCraneMonitoringStatisticsDTO> selectMonitoringEquipmentAnalysis(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO);

    public List<WsTowerCraneMonitoringStatisticsDTO> selectEquipmentViolationAnalysis(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO);

    public WsTowerCraneMonitoringStatisticsDTO selectProjectViolationProportion(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO);

    public List<WsTowerCraneMonitoringStatisticsDTO> selectRankingOfViolations(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO);

    public List<WsTowerCraneMonitoringStatisticsDTO> selectBadOperationDrive(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO);

    public List<WsTowerCraneMonitoringStatisticsDTO> selectEquipmentViolation(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO);

    public List<WsTowerCraneMonitoringStatisticsDTO> selectEquipmentWorkRecord(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO);

    public WsTowerCraneMonitoringStatisticsDTO selectMonitoringEquipmentOrViolation(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO);

    public WsTowerCraneMonitoringStatisticsDTO selectProjectViolationProportionCustom(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO);

    public List<WsTowerCraneMonitoringStatisticsDTO> selectRankingOfViolationsCustom(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO);

    public List<WsTowerCraneEquipmentDetailsDTO> selectDeviceDetails(WsTowerCraneEquipmentDetailsDTO wsTowerCraneEquipmentDetailsDTO);

    public List<WsTowerCraneEquipmentViolationDetailsDTO> selectViolationDetails(WsTowerCraneEquipmentViolationDetailsDTO wsTowerCraneEquipmentViolationDetailsDTO);

    public List<WsTowerCraneEquipmentViolationDetailsDTO> selectViolationDetailsProject(WsTowerCraneEquipmentViolationDetailsDTO wsTowerCraneEquipmentViolationDetailsDTO);

    public List<WsTowerCraneEquipmentWorkDetailsDTO> selectWorkDetailsProject(WsTowerCraneEquipmentWorkDetailsDTO wsTowerCraneEquipmentWorkDetailsDTO);

    /* 塔机监控-项目级*/
    public WsTowerCraneProjectRealTimeMonitoringDTO selectProjectRealTimeMonitoring(WsTowerCraneProjectRealTimeMonitoringDTO wsTowerCraneProjectRealTimeMonitoringDTO);

    public List<WsTowerCraneProjectRealTimeMonitoringDTO> selectEquipmentRealtimeMonitoring(WsTowerCraneProjectRealTimeMonitoringDTO wsTowerCraneProjectRealTimeMonitoringDTO);

    /* 塔机监控-项目级-监控详情*/
    public WsTowerCraneProjectMonitoringDetailsDTO selectProjectMonitoringDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO);

    public WsTowerCraneProjectMonitoringDetailsDTO selectHookVideoDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO);

    public List<WsTowerCraneProjectMonitoringDetailsDTO> selectEquipmentWorkCycleRecord(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO);

    public List<WsTowerCraneProjectMonitoringDetailsDTO> selectEquipmentWorkCycleDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO);

    public List<WsTowerCraneProjectMonitoringDetailsDTO> selectEquipmentWorkDriverDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO);

    public List<WsTowerCraneProjectMonitoringDetailsDTO> selectEquipmentWorkAlarmDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO);

    public WsTowerCraneProjectMonitoringDetailsDTO selectEquipmentWorkWarningTodayDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO);

    public List<WsTowerCraneProjectRealTimeMonitoringDTO> selectDetailsProjectViolation(WsTowerCraneProjectRealTimeMonitoringDTO wsTowerCraneProjectRealTimeMonitoringDTO);

    public List<WsTowerCraneEquipmentWorkDetailsDTO> selectProjectDetailsQuery(WsTowerCraneEquipmentWorkDetailsDTO wsTowerCraneEquipmentWorkDetailsDTO);

    public List<WsProject> selectRealTimeProjectSearch(WsProject wsProject);

    public List<WsProjectFieldLayout> selectRealTimeProjectFieldLayout(WsProjectFieldLayout wsProjectFieldLayout);

    public List<WsTowercraneEquipment> selectRealTimeTowercraneEquipment(WsTowercraneEquipment wsTowercraneEquipment);

    public List<WsTowerCraneMonitoringRealtimeDTO> getAreaDeviceStatisticsList(WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO);

    public List<WsTowerCraneProjectMonitoringDetailsDTO> selectEquipmentStatusAnalysis(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO);

    public List<WsTowerCraneProjectMonitoringDetailsDTO> selectHoistingCycle(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO);

    public List<WsTowerCraneMonitoringRealtimeDTO> getAreaDeviceStatisticsListProject(WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO);
}
