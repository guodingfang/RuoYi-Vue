package com.jlkj.project.ws.intelligence.service;


import com.jlkj.project.ws.intelligence.dto.WsDustMonitoringDetailsDTO;

import java.util.List;

/**
 * 智能监测：扬尘智能监测 Service接口
 * 
 * @author gaowei
 * @date 2020-07-09
 */
public interface IWsDustMonitoringIntelligentService
{
    /**
     * 查询扬尘监测列表-企业
     * @param wsDustMonitoringDetailsDTO
     * @return
     */
    public List<WsDustMonitoringDetailsDTO> selectWsDustMonitoringDetailsList(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    /**
     * 询扬尘监测列表中 无监测项目数-企业
     * @param wsDustMonitoringDetailsDTO
     * @return
     */
    public WsDustMonitoringDetailsDTO selectNothingMonitoring(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    /**
     * 查询无监测项目列表数据-企业
     * @param wsDustMonitoringDetailsDTO
     * @return
     */
    public List<WsDustMonitoringDetailsDTO> selectNothingMonitoringList(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    /**
     * 根据项目查询监测设备-企业-项目
     * @param wsDustMonitoringDetailsDTO
     * @return
     */
    public List<WsDustMonitoringDetailsDTO> selectProjectIdMonitoringEquipmentNo(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    /**
     *根据监测设备查询环境监测数据-企业-项目
     * @param wsDustMonitoringDetailsDTO
     * @return
     */
    public WsDustMonitoringDetailsDTO selectMonitoringEquipmentNoDust(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    /**
     * 根据监测设备查询监测统计：近7天扬尘变化情况-企业-项目
     * @param wsDustMonitoringDetailsDTO
     * @return
     */
    public List<WsDustMonitoringDetailsDTO> selectDustSevenDaySituation(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    /**
     * 根据监测设备查询监测统计：近7天噪音变化情况-企业-项目
     * @param wsDustMonitoringDetailsDTO
     * @return
     */
    public List<WsDustMonitoringDetailsDTO> selectNoiseSevenDaySituation(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    /**
     * 根据监测设备查询监测统计：扬尘（每日）-企业-项目
     * @param wsDustMonitoringDetailsDTO
     * @return
     */
    public List<WsDustMonitoringDetailsDTO> selectSameDayDustPm25OrDustPm10(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    /**
     * 根据监测设备查询监测统计：扬尘（每月）-企业-项目
     * @param wsDustMonitoringDetailsDTO
     * @return
     */
    public List<WsDustMonitoringDetailsDTO> selectMonthlyDayDustPm25OrDustPm10(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    /**
     * 根据监测设备查询监测统计：噪音（每日）-企业-项目
     * @param wsDustMonitoringDetailsDTO
     * @return
     */
    public List<WsDustMonitoringDetailsDTO> selectSameDayDustStrepitus(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    /**
     * 根据监测设备查询监测统计：噪音（每月）-企业-项目
     * @param wsDustMonitoringDetailsDTO
     * @return
     */
    public List<WsDustMonitoringDetailsDTO> selectMonthlyDayDustStrepitus(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    /**
     * 根据监测设备查询监测统计：温度（每日）-企业-项目
     * @param wsDustMonitoringDetailsDTO
     * @return
     */
    public List<WsDustMonitoringDetailsDTO> selectSameDayDustTemperature(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    /**
     * 根据监测设备查询监测统计：温度（每月）-企业-项目
     * @param wsDustMonitoringDetailsDTO
     * @return
     */
    public List<WsDustMonitoringDetailsDTO> selectMonthlyDayDustTemperature(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    /**
     * 根据监测设备查询监测统计：风速（每日）-企业-项目
     * @param wsDustMonitoringDetailsDTO
     * @return
     */
    public List<WsDustMonitoringDetailsDTO> selectSameDayDustWindSpeed(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    /**
     * 根据监测设备查询监测统计：风速（每月）-企业-项目
     * @param wsDustMonitoringDetailsDTO
     * @return
     */
    public List<WsDustMonitoringDetailsDTO> selectMonthlyDayDustWindSpeed(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    /**
     * 根据监测视频Id查询监测设备-监测视频-企业-项目
     * @param wsDustMonitoringDetailsDTO
     * @return
     */
    public WsDustMonitoringDetailsDTO selectMonitoringVideoDetails(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    /**
     * 根据监测设备查询监测统计：pm10,噪音，温度，风速-每日
     */
    public WsDustMonitoringDetailsDTO selectAverageDustMonitoringDetailsDay(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);

    /**
     * 根据监测设备查询监测统计：pm10,噪音，温度，风速-每月
     */
    public WsDustMonitoringDetailsDTO selectAverageDustMonitoringDetailsMonth(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO);
}
