package com.jlkj.project.ws.intelligence.mapper;

import java.util.List;
import com.jlkj.project.ws.intelligence.domain.WsUnloadingPlatformManage;
import com.jlkj.project.ws.intelligence.dto.WsUnloadingPlatformManageDTO;

/**
 * 卸料平台设备管理 Mapper接口
 * 
 * @author gaowei
 * @date 2020-07-13
 */
public interface WsUnloadingPlatformManageMapper 
{
    /**
     * 查询卸料平台设备管理 
     * 
     * @param id 卸料平台设备管理 ID
     * @return 卸料平台设备管理 
     */
    public WsUnloadingPlatformManage selectWsUnloadingPlatformManageById(Integer id);

    /**
     * 查询卸料平台设备管理 列表
     * 
     * @param wsUnloadingPlatformManage 卸料平台设备管理 
     * @return 卸料平台设备管理 集合
     */
    public List<WsUnloadingPlatformManage> selectWsUnloadingPlatformManageList(WsUnloadingPlatformManage wsUnloadingPlatformManage);

    /**
     * 新增卸料平台设备管理 
     * 
     * @param wsUnloadingPlatformManage 卸料平台设备管理 
     * @return 结果
     */
    public int insertWsUnloadingPlatformManage(WsUnloadingPlatformManage wsUnloadingPlatformManage);

    /**
     * 修改卸料平台设备管理 
     * 
     * @param wsUnloadingPlatformManage 卸料平台设备管理 
     * @return 结果
     */
    public int updateWsUnloadingPlatformManage(WsUnloadingPlatformManage wsUnloadingPlatformManage);

    /**
     * 删除卸料平台设备管理 
     * 
     * @param id 卸料平台设备管理 ID
     * @return 结果
     */
    public int deleteWsUnloadingPlatformManageById(Integer id);

    /**
     * 批量删除卸料平台设备管理 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsUnloadingPlatformManageByIds(Integer[] ids);

    public List<WsUnloadingPlatformManage> selectlistUnloadingPlatformManage(WsUnloadingPlatformManage wsUnloadingPlatformManage);

    public int updateWsUnloadingPlatformManageList(WsUnloadingPlatformManage wsUnloadingPlatformManage);

    public List<WsUnloadingPlatformManage> selectRealTimeMonitoring(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO);

    public List<WsUnloadingPlatformManageDTO> selectRealTimeMonitoringUnloadingPlatform(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO);

    public WsUnloadingPlatformManageDTO selectEquipmentIdleRate(String[] monitoringEquipmentNos);

    public WsUnloadingPlatformManageDTO selectRealTimeMonitoringData(String monitoringEquipmentNo);

    public WsUnloadingPlatformManageDTO selectUnloadingEquipmentName(String monitoringEquipmentNo);

    public WsUnloadingPlatformManageDTO selectRealTimeInformation(String monitoringEquipmentNo);

    public List<WsUnloadingPlatformManageDTO> selectTodayWorkRecordList(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO);

    public List<WsUnloadingPlatformManageDTO> selectFifteenWorkRecordList(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO);

    public List<WsUnloadingPlatformManageDTO> selectFifteenAlarmLogList(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO);

    public List<WsUnloadingPlatformManageDTO> selectEquipmentMoredetailsList(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO);

    public int updateWsUnloadingPlatformManages(WsUnloadingPlatformManage wsUnloadingPlatformManage);

    public WsUnloadingPlatformManageDTO selectTodayAlarmLog(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO);

    public int editUnloadingPlatformManage(WsUnloadingPlatformManage wsUnloadingPlatformManage);

    public List<WsUnloadingPlatformManage> selectRealTimeMonitorings(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO);
}
