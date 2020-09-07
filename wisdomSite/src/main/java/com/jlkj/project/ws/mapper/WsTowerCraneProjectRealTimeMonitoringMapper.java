package com.jlkj.project.ws.mapper;


import com.jlkj.project.ws.dto.WsTowerCraneEquipmentWorkDetailsDTO;
import com.jlkj.project.ws.dto.WsTowerCraneProjectMonitoringDetailsDTO;
import com.jlkj.project.ws.dto.WsTowerCraneProjectRealTimeMonitoringDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 塔机监测 Mapper接口
 *
 * @author gaowei
 * @date 2020-07-2
 */
public interface WsTowerCraneProjectRealTimeMonitoringMapper
{
    WsTowerCraneProjectRealTimeMonitoringDTO selectTowerCraneOnSiteNumQuery(WsTowerCraneProjectRealTimeMonitoringDTO wsTowerCraneProjectRealTimeMonitoringDTO);

    List<WsTowerCraneProjectRealTimeMonitoringDTO> selectTowerCraneOnSiteNumQueryList(WsTowerCraneProjectRealTimeMonitoringDTO wsTowerCraneProjectRealTimeMonitoringDTO);

    WsTowerCraneProjectRealTimeMonitoringDTO selectMonitoringEquipmentNosOnlineRate(String[] monitoringEquipmentNos);

    WsTowerCraneProjectRealTimeMonitoringDTO selectMonitoringEquipmentNosViolationTodayNum(String[] monitoringEquipmentNos);

    List<WsTowerCraneProjectRealTimeMonitoringDTO> selectProjectTowerCraneList(WsTowerCraneProjectRealTimeMonitoringDTO wsTowerCraneProjectRealTimeMonitoringDTO);

    WsTowerCraneProjectRealTimeMonitoringDTO selectProjectfieldLayout(WsTowerCraneProjectRealTimeMonitoringDTO wsTowerCraneProjectRealTimeMonitoringDTO);

    WsTowerCraneProjectRealTimeMonitoringDTO selectTowerCraneEquipmentWorkDetailsQuery(String monitoringEquipmentNo);

    WsTowerCraneProjectRealTimeMonitoringDTO selectWarningStatusOrAlarmStatusQuery(String monitoringEquipmentNo);

    WsTowerCraneProjectRealTimeMonitoringDTO selectTowerCraneEquipmentFaceNoQuery(String faceId);

    WsTowerCraneProjectRealTimeMonitoringDTO selectTowerCraneEquipmentIdCardNoQuery(String faceNo);

    WsTowerCraneProjectRealTimeMonitoringDTO selectTowerCraneEquipmentPhotoAddressQuery(String idCardNo, Integer projectId);

    WsTowerCraneProjectMonitoringDetailsDTO selectProjectTowerCraneInformation(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO);

    WsTowerCraneProjectMonitoringDetailsDTO selectProjectMonitorrRealTimeData(@Param("monitoringEquipmentNo") String monitoringEquipmentNo);

    WsTowerCraneProjectMonitoringDetailsDTO selectProjectMonitorrRealTimeMaxMinData(String monitoringEquipmentNo);

    WsTowerCraneProjectMonitoringDetailsDTO selectTowerCraneDriverInformationQuery(String idCardNo, Integer projectId);

    WsTowerCraneProjectMonitoringDetailsDTO selectProjectMonitorrForewarningData(@Param("monitoringEquipmentNo") String monitoringEquipmentNo);

    WsTowerCraneProjectMonitoringDetailsDTO selectEquipmentOnlineStatusData(String monitoringEquipmentNo);

    List<WsTowerCraneProjectMonitoringDetailsDTO> selectEquipmentWorkCycleRecordOneList(String monitoringEquipmentNo);

    List<WsTowerCraneProjectMonitoringDetailsDTO> selectEquipmentWorkCycleRecordfifteenList(String monitoringEquipmentNo);

    List<WsTowerCraneProjectMonitoringDetailsDTO> selectEquipmentWorkCycleDetails(String monitoringEquipmentNo, String searchTime);

    List<WsTowerCraneProjectMonitoringDetailsDTO> selectEquipmentWorkDriverInformation(String faceNo, String searchTime, String driversName);

    List<WsTowerCraneProjectMonitoringDetailsDTO> selectProjectMonitorrRealTimeAlarmData(@Param("monitoringEquipmentNo") String monitoringEquipmentNo);

    WsTowerCraneProjectMonitoringDetailsDTO selectProjectMonitorrWarningTodayData(@Param("monitoringEquipmentNo") String monitoringEquipmentNo);

    WsTowerCraneProjectRealTimeMonitoringDTO selectTowerCraneEquipmentNameQuery(String faceNo);

    List<WsTowerCraneProjectRealTimeMonitoringDTO> selectDetailsProjectViolationQuerySeven(String monitoringEquipmentNo);

    List<WsTowerCraneProjectRealTimeMonitoringDTO> selectProjectTowerCraneDetailsList(WsTowerCraneProjectRealTimeMonitoringDTO wsTowerCraneProjectRealTimeMonitoringDTO);

    List<WsTowerCraneProjectRealTimeMonitoringDTO> selectDetailsProjectViolationQueryThirty(String monitoringEquipmentNo);

    List<WsTowerCraneProjectRealTimeMonitoringDTO> selectDetailsProjectViolationQuerySame(String monitoringEquipmentNo, String conditionsDate);

    WsTowerCraneProjectMonitoringDetailsDTO selectRecentViolationRateData(@Param("monitoringEquipmentNo") String monitoringEquipmentNo);

    WsTowerCraneProjectMonitoringDetailsDTO selectYesterdayHoistingData(@Param("monitoringEquipmentNo") String monitoringEquipmentNo);

    WsTowerCraneProjectMonitoringDetailsDTO selectTodayHoistingData(@Param("monitoringEquipmentNo") String monitoringEquipmentNo);

    List<WsTowerCraneProjectMonitoringDetailsDTO> selectEquipmentStatusAnalysis(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO);

    List<WsTowerCraneProjectMonitoringDetailsDTO> selectHoistingCycleSeven(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO);

    List<WsTowerCraneProjectMonitoringDetailsDTO> selectHoistingCycleFifteen(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO);

    List<WsTowerCraneProjectMonitoringDetailsDTO> selectHoistingCycleThirty(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO);
}
