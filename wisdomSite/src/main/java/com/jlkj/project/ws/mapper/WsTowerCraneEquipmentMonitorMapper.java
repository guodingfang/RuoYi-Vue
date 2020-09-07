package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 塔机监测 Mapper接口
 *
 * @author gaowei
 * @date 2020-06-29
 */
public interface WsTowerCraneEquipmentMonitorMapper
{

    WsTowerCraneMonitoringRealtimeDTO selectRealTimeMonitoringTowerCraneNum(Integer[] projectIds);

    WsTowerCraneMonitoringRealtimeDTO selectRealTimeMonitoringMonitoringEquipmentNum(Integer[] projectIds);

    List<WsTowerCraneMonitoringRealtimeDTO> selectEquipmentNoList(Integer[] projectIds);

    WsTowerCraneMonitoringRealtimeDTO selectOnlineNum(@Param("monitoringEquipmentNos") String[] monitoringEquipmentNos);

    WsTowerCraneMonitoringRealtimeDTO selectTodayIllegalNum(@Param("monitoringEquipmentNos") String[] monitoringEquipmentNos);

    List<WsTowerCraneMonitoringRealtimeDTO> selectProjectIllegalNum(@Param("monitoringEquipmentNos") String[] monitoringEquipmentNos);

    List<WsTowerCraneMonitoringRealtimeDTO> selectEquipmentList(@Param("towerCraneMonitoringRealtimeDTO") WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO, @Param("projectIds") Integer[] projectIds);

    WsTowerCraneMonitoringRealtimeDTO selectOnlineNumAndOfflineNum(String monitoringEquipmentNo);

    List<WsTowerCraneMonitoringRealtimeDTO> selectViolationContent(String monitoringEquipmentNo,String startTime,String endTime);

    List<WsTowerCraneMonitoringStatisticsDTO> selectMonitoringEquipmentAnalysisSeven(Integer[] projectIds);

    List<WsTowerCraneMonitoringStatisticsDTO> selectMonitoringEquipmentAnalysisThirty(Integer[] projectIds);

    List<WsTowerCraneMonitoringStatisticsDTO> selectEquipmentViolationAnalysisSeven(String[] monitoringEquipmentNos);

    List<WsTowerCraneMonitoringStatisticsDTO> selectEquipmentViolationAnalysisThirty(String[] monitoringEquipmentNos);

    List<WsTowerCraneMonitoringStatisticsDTO> selectQueryIllegalEquipmentSeven(String[] monitoringEquipmentNos);

    List<WsTowerCraneMonitoringStatisticsDTO> selectProjectIllegalSeven(String[] monitoringEquipmentNos);

    List<WsTowerCraneMonitoringStatisticsDTO> selectQueryIllegalEquipmentThirty(String[] monitoringEquipmentNos);

    List<WsTowerCraneMonitoringStatisticsDTO> selectProjectIllegalThirty(String[] monitoringEquipmentNos);

    List<WsTowerCraneMonitoringRealtimeDTO> selectEquipmentNoListByProjectId(Integer projectId);

    WsTowerCraneMonitoringStatisticsDTO selectEquipmentNoListByProjectIdSeven(String[] monitoringEquipmentNos);

    WsTowerCraneMonitoringStatisticsDTO selectEquipmentNoListByProjectIdThirty(String[] monitoringEquipmentNos);

    List<WsTowerCraneMonitoringRealtimeDTO> selectEquipmentViolationList(Integer projectId);

    WsTowerCraneMonitoringStatisticsDTO selectEquipmentViolationConditionsDate(String monitoringEquipmentNo, String conditionsDate);

    WsTowerCraneMonitoringStatisticsDTO selectEquipmentViolationConditionsDateThirty(String monitoringEquipmentNo);

    WsTowerCraneMonitoringStatisticsDTO selectEquipmentViolationConditionsDateSeven(String monitoringEquipmentNo);

    WsTowerCraneMonitoringStatisticsDTO selectEquipmentWorkRecordSeven(String monitoringEquipmentNo);

    WsTowerCraneMonitoringStatisticsDTO selectEquipmentWorkRecordThirty(String monitoringEquipmentNo);

    WsTowerCraneMonitoringStatisticsDTO selectEquipmentWorkRecordData(String monitoringEquipmentNo, String conditionsDate);

    WsTowerCraneMonitoringStatisticsDTO selectEquipmentBePresent(@Param("projectIds") Integer[] projectIds,String conditionsDate);

    List<WsTowerCraneMonitoringRealtimeDTO> selectMonitoringEquipmentOrViolation(@Param("projectIds") Integer[] projectIds,String conditionsDate);

    WsTowerCraneMonitoringStatisticsDTO selectMonitoringEquipmentNosOrConditionsDate(String[] monitoringEquipmentNos);

    WsTowerCraneMonitoringStatisticsDTO selectMonitoringEquipmentNoViolation(@Param("monitoringEquipmentNos") String[] monitoringEquipmentNos, String conditionsDate);

    List<WsTowerCraneMonitoringStatisticsDTO> selectQueryIllegalEquipmentSameDay(@Param("monitoringEquipmentNos") String[] monitoringEquipmentNos, String conditionsDate);

    List<WsTowerCraneMonitoringStatisticsDTO> selectProjectIllegalSameDay(String[] quipmentNos);

    WsTowerCraneMonitoringStatisticsDTO selectEquipmentNoListByProjectIdCustom(@Param("monitoringEquipmentNos") String[] monitoringEquipmentNos,@Param("conditionsDate") String conditionsDate);

    WsTowerCraneEquipmentDetailsDTO selectDeviceDetailsByProjectId(Integer projectId);

    List<WsTowerCraneEquipmentViolationDetailsDTO> selectViolationDetailsList(@Param("wsTowerCraneEquipmentViolationDetailsDTO") WsTowerCraneEquipmentViolationDetailsDTO wsTowerCraneEquipmentViolationDetailsDTO,@Param("projectIds") Integer[] projectIds);

    List<WsTowerCraneEquipmentViolationDetailsDTO> selectViolationDetailsProjectList(WsTowerCraneEquipmentViolationDetailsDTO wsTowerCraneEquipmentViolationDetailsDTO);

    List<WsTowerCraneEquipmentWorkDetailsDTO> selectWorkDetailsProjectList(WsTowerCraneEquipmentWorkDetailsDTO wsTowerCraneEquipmentWorkDetailsDTO);

    List<WsTowerCraneEquipmentWorkDetailsDTO> selectTowerCraneEquipmentWorkDetails(String monitoringEquipmentNo);

    List<WsTowerCraneMonitoringStatisticsDTO> selectEquipmentNoListBymonitoringEquipmentNos(String[] monitoringEquipmentNos);

    WsTowerCraneMonitoringStatisticsDTO selectEquipmentNoListByFaceNumber(String faceNumber);

    WsTowerCraneMonitoringStatisticsDTO selectEquipmentNoListByMonitoringEquipmentNo(String monitoringEquipmentNo);

    WsTowerCraneMonitoringStatisticsDTO selectEquipmentNoListByMonitoringEquipmentNoThirty(String monitoringEquipmentNo);

    WsTowerCraneMonitoringStatisticsDTO selectEquipmentMonitoringEquipmentNoOneDay(String monitoringEquipmentNo, String conditionsDate);

    List<WsTowerCraneEquipmentWorkDetailsDTO> selectProjectDetailsQuerySeven(String monitoringEquipmentNo);

    List<WsTowerCraneEquipmentWorkDetailsDTO> selectProjectDetailsQueryThirty(String monitoringEquipmentNo);

    List<WsTowerCraneEquipmentWorkDetailsDTO> selectProjectDetailsQueryToday(String monitoringEquipmentNo, String conditionsDate);

    List<WsTowerCraneMonitoringRealtimeDTO> selectDeviceListByArea(@Param("areaCode") String areaCode);

    WsTowerCraneMonitoringStatisticsDTO selectMonitoringEquipmentAnalysisSevenTj(@Param("projectIds") Integer[] projectIds,@Param("statisticsTime") String statisticsTime);

    List<WsTowerCraneMonitoringStatisticsDTO> selectMonitoringEquipmentTjs(@Param("projectIds") Integer[] projectIds,@Param("statisticsTime") String statisticsTime);

    List<WsTowerCraneMonitoringStatisticsDTO> selectMonitoringEquipmentTjZxl(@Param("monitoringEquipmentNo") String monitoringEquipmentNo,@Param("statisticsTime") String statisticsTime);

    List<WsTowerCraneMonitoringRealtimeDTO> selectlistRealtimeDTO(@Param("projectId") Integer projectId);

    List<WsTowerCraneMonitoringRealtimeDTO> selectDeviceListByAreaProject(@Param("areaCode") String areaCode);

    WsTowerCraneMonitoringRealtimeDTO selectListByAreaProject(@Param("projectId") Integer projectId);

    List<WsTowerCraneMonitoringRealtimeDTO> selectlistRealtimeDTOProjectIds(@Param("projectIds") Integer[] projectIds);

    List<WsTowerCraneMonitoringRealtimeDTO> selectlistRealtimeDTOProjectNum(@Param("projectIds") Integer[] projectIds);
}
