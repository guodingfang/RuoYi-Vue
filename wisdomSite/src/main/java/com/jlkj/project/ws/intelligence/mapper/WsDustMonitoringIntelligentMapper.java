package com.jlkj.project.ws.intelligence.mapper;


import com.jlkj.project.ws.intelligence.dto.WsDustMonitoringDetailsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 智能监测：扬尘智能监测  Mapper接口
 * 
 * @author gaowei
 * @date 2020-07-09
 */
public interface WsDustMonitoringIntelligentMapper
{
    List<WsDustMonitoringDetailsDTO> selectProjectEquipmentList(Integer[] projectIds, Integer projectId);

    List<WsDustMonitoringDetailsDTO> selectEquipmentList(Integer projectId);

    List<WsDustMonitoringDetailsDTO> selectAlarmStatusCodelist(String[] monitoringEquipmentNos);

    WsDustMonitoringDetailsDTO selectTodayAlarmNumMonitoringEquipmentNo(@Param("monitoringEquipmentNo") String monitoringEquipmentNo);

    List<WsDustMonitoringDetailsDTO> selectNothingMonitoringList(Integer[] projectIds);

    List<WsDustMonitoringDetailsDTO> selectProjectNothingMonitoringList(Integer[] projectIds);

    List<WsDustMonitoringDetailsDTO> selectProjectIdMonitoringEquipmentNo(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    WsDustMonitoringDetailsDTO selectOnlineOfflineName(String monitoringEquipmentNo);

    WsDustMonitoringDetailsDTO selectMonitoringEquipmentNoDust(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    List<WsDustMonitoringDetailsDTO> selectDustSevenDaySituation(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    List<WsDustMonitoringDetailsDTO> selectNoiseSevenDaySituation(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    List<WsDustMonitoringDetailsDTO> selectSameDayDustPm25OrDustPm10(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    List<WsDustMonitoringDetailsDTO> selectMonthlyDayDustPm25OrDustPm10(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    List<WsDustMonitoringDetailsDTO> selectSameDayDustStrepitus(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    List<WsDustMonitoringDetailsDTO> selectMonthlyDayDustStrepitus(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    List<WsDustMonitoringDetailsDTO> selectSameDayDustTemperature(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    List<WsDustMonitoringDetailsDTO> selectMonthlyDayDustTemperature(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    List<WsDustMonitoringDetailsDTO> selectSameDayDustWindSpeed(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    List<WsDustMonitoringDetailsDTO> selectMonthlyDayDustWindSpeed(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    WsDustMonitoringDetailsDTO selectAverageDustMonitoringDetailsDay(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    WsDustMonitoringDetailsDTO selectAverageDustMonitoringDetailsMonth(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);
}
