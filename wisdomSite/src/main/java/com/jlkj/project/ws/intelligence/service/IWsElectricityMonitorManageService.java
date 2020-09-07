package com.jlkj.project.ws.intelligence.service;

import java.util.List;

import com.jlkj.project.ws.domain.WsProjectEquipment;
import com.jlkj.project.ws.domain.WsProjectSitePartition;
import com.jlkj.project.ws.intelligence.domain.WsElecBoxMonitorData;
import com.jlkj.project.ws.intelligence.domain.WsElectricityMonitorManage;

/**
 * 电能监测设备管理 Service接口
 * 
 * @author gaowei
 * @date 2020-07-14
 */
public interface IWsElectricityMonitorManageService 
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
    public List<WsElectricityMonitorManage> selectWsElectricityMonitorManageList(WsElectricityMonitorManage wsElectricityMonitorManage);

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
     * 批量删除电能监测设备管理 
     * 
     * @param ids 需要删除的电能监测设备管理 ID
     * @return 结果
     */
    public int deleteWsElectricityMonitorManageByIds(Integer[] ids);

    /**
     * 删除电能监测设备管理 信息
     * 
     * @param id 电能监测设备管理 ID
     * @return 结果
     */
    public int deleteWsElectricityMonitorManageById(Integer id);

    /**
     * 查询监测设备（电能监测设备）
     * @param wsProjectEquipment
     * @return
     */
    public List<WsProjectEquipment> listMonitoringEquipment(WsProjectEquipment wsProjectEquipment);

    /**
     * 查询安装区域
     * @param wsProjectSitePartition
     * @return
     */
    public List<WsProjectSitePartition> listProjectSitePartition(WsProjectSitePartition wsProjectSitePartition);

    /**
     * 启用，禁用
     * @param wsElectricityMonitorManage
     * @return
     */
    public int updateEnableDisable(WsElectricityMonitorManage wsElectricityMonitorManage);

    /**
     * 用电安全设备信息列表
     * @param wsElectricityMonitorManage
     * @return
     */
    public List<WsElectricityMonitorManage> selectElectricitySecurityList(WsElectricityMonitorManage wsElectricityMonitorManage);

    /**
     * 查询用电安全-监测详情-设备信息及实时数据
     * @param wsElecBoxMonitorData
     * @return
     */
    public WsElecBoxMonitorData selectRealTimeMonitoring(WsElecBoxMonitorData wsElecBoxMonitorData);

    /**
     * 查询用电安全-监测详情-监测记录
     * @param wsElecBoxMonitorData
     * @return
     */
    public List<WsElecBoxMonitorData> selectMonitoringRecordsList(WsElecBoxMonitorData wsElecBoxMonitorData);

    /**
     * 查询能耗监测-用电量信息
     * @param wsElecBoxMonitorData
     * @return
     */
    public WsElecBoxMonitorData selectElectricityConsumption(WsElecBoxMonitorData wsElecBoxMonitorData);

    /**
     * 查询能耗监测-能耗分析
     * @param wsElecBoxMonitorData
     * @return
     */
    public List<WsElecBoxMonitorData> selectEnergyConsumptionAnalysisList(WsElecBoxMonitorData wsElecBoxMonitorData);

    /**
     * 查询能耗监测-电表详情
     * @param wsElectricityMonitorManage
     * @return
     */
    public List<WsElectricityMonitorManage> selectMeterDetailsList(WsElectricityMonitorManage wsElectricityMonitorManage);

    /**
     * 查询能耗监测-用电详情
     * @param wsElectricityMonitorManage
     * @return
     */
    public List<WsElectricityMonitorManage> selectPowerConsumptionDetailsList(WsElectricityMonitorManage wsElectricityMonitorManage);
}
