package com.jlkj.project.ws.intelligence.mapper;

import java.util.List;

import com.jlkj.project.ws.intelligence.domain.WsElecBoxMonitorData;
import com.jlkj.project.ws.intelligence.domain.WsElectricityMonitorManage;
import org.apache.ibatis.annotations.Param;

/**
 * 电能监测设备管理 Mapper接口
 * 
 * @author gaowei
 * @date 2020-07-14
 */
public interface WsElectricityMonitorManageMapper 
{
    /**
     * 查询电能监测设备管理 
     * 
     * @param id 电能监测设备管理 ID
     * @return 电能监测设备管理 
     */
    public WsElectricityMonitorManage selectWsElectricityMonitorManageById(Integer id);

    /**
     * 查询电能监测设备管理 列表
     * 
     * @param wsElectricityMonitorManage 电能监测设备管理 
     * @return 电能监测设备管理 集合
     */
    public List<WsElectricityMonitorManage> selectWsElectricityMonitorManageList(@Param("wsElectricityMonitorManage") WsElectricityMonitorManage wsElectricityMonitorManage,@Param("projectIds") Integer[] projectIds);

    /**
     * 新增电能监测设备管理 
     * 
     * @param wsElectricityMonitorManage 电能监测设备管理 
     * @return 结果
     */
    public int insertWsElectricityMonitorManage(WsElectricityMonitorManage wsElectricityMonitorManage);

    /**
     * 修改电能监测设备管理 
     * 
     * @param wsElectricityMonitorManage 电能监测设备管理 
     * @return 结果
     */
    public int updateWsElectricityMonitorManage(WsElectricityMonitorManage wsElectricityMonitorManage);

    /**
     * 删除电能监测设备管理 
     * 
     * @param id 电能监测设备管理 ID
     * @return 结果
     */
    public int deleteWsElectricityMonitorManageById(Integer id);

    /**
     * 批量删除电能监测设备管理 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsElectricityMonitorManageByIds(Integer[] ids);

    public WsElectricityMonitorManage selectElectricityMonitorMonitoringStatus(String monitoringEquipment);

    public WsElecBoxMonitorData selectRealTimeMonitoringEquipmentName(WsElecBoxMonitorData wsElecBoxMonitorData);

    public WsElecBoxMonitorData selectRealTimeMonitoringInformation(WsElecBoxMonitorData wsElecBoxMonitorData);

    public List<WsElecBoxMonitorData> selectMonitoringRecordsListTwentyFourTime(WsElecBoxMonitorData wsElecBoxMonitorData);

    public List<WsElecBoxMonitorData> selectMonitoringRecordsListThreeDays(WsElecBoxMonitorData wsElecBoxMonitorData);

    public List<WsElecBoxMonitorData> selectMonitoringRecordsListSevenDays(WsElecBoxMonitorData wsElecBoxMonitorData);

    public List<WsElecBoxMonitorData> selectMonitoringEquipmentList(WsElecBoxMonitorData wsElecBoxMonitorData);

    public WsElecBoxMonitorData selectElectricityConsumptionORpower(String[] monitoringEquipments);

    public List<WsElectricityMonitorManage> selectWsElectricityMonitorManage(int installationArea);

    public WsElectricityMonitorManage selectWsElectricityTodayElectricity(@Param("monitoringEquipment") String monitoringEquipment);

    public WsElectricityMonitorManage selectWsElectricityYesterdayElectricity(@Param("monitoringEquipment") String monitoringEquipment);

    public WsElectricityMonitorManage selectWsElectricityLastMonthElectricity(@Param("monitoringEquipment") String monitoringEquipment);

    public WsElectricityMonitorManage selectWsElectricitySameMonthElectricity(@Param("monitoringEquipment") String monitoringEquipment);

    public List<WsElectricityMonitorManage> selectPowerConsumptionDetailsDayList(WsElectricityMonitorManage wsElectricityMonitorManage);

    public List<WsElectricityMonitorManage> selectPowerConsumptionDetailsMonthList(WsElectricityMonitorManage wsElectricityMonitorManage);


    List<WsElectricityMonitorManage> selectWsElectricityMonitorManageLists(Integer[] projectIds);
}
