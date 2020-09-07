package com.jlkj.project.ws.intelligence.service;

import java.util.List;

import com.jlkj.project.ws.domain.WsProjectEquipment;
import com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout;
import com.jlkj.project.ws.intelligence.domain.WsUnloadingPlatformManage;
import com.jlkj.project.ws.intelligence.dto.WsUnloadingPlatformManageDTO;

/**
 * 卸料平台设备管理 Service接口
 * 
 * @author gaowei
 * @date 2020-07-13
 */
public interface IWsUnloadingPlatformManageService 
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
     * 批量删除卸料平台设备管理 
     * 
     * @param ids 需要删除的卸料平台设备管理 ID
     * @return 结果
     */
    public int deleteWsUnloadingPlatformManageByIds(Integer[] ids);

    /**
     * 删除卸料平台设备管理 信息
     * 
     * @param id 卸料平台设备管理 ID
     * @return 结果
     */
    public int deleteWsUnloadingPlatformManageById(Integer id);

    public List<WsProjectEquipment> listMonitoringEquipment(WsProjectEquipment wsProjectEquipment);

    public int insertWsProjectFieldLayout(WsProjectFieldLayout wsProjectFieldLayout);

    public List<WsUnloadingPlatformManage> selectlistUnloadingPlatformManage(WsUnloadingPlatformManage wsUnloadingPlatformManage);

    public int insertPosition(WsUnloadingPlatformManage wsUnloadingPlatformManage);

    /**
     * 查询卸料平台-实时监控-设备数量信息
     * @param wsUnloadingPlatformManageDTO
     * @return
     */
    public WsUnloadingPlatformManageDTO selectRealTimeMonitoring(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO);

    /**
     * 查询卸料平台-实时监控-实时数据信息
     * @param wsUnloadingPlatformManageDTO
     * @return
     */
    public List<WsUnloadingPlatformManageDTO> selectRealTimeMonitoringDataList(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO);

    /**
     * 查询卸料平台-监控详情-设备信息和实时数据信息
     * @param wsUnloadingPlatformManageDTO
     * @return
     */
    public WsUnloadingPlatformManageDTO selectEquipmentRealTimeInformation(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO);

    /**
     * 查询卸料平台-监控详情-工作记录-今日
     * @param wsUnloadingPlatformManageDTO
     * @return
     */
    public List<WsUnloadingPlatformManageDTO> selectTodayWorkRecordList(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO);

    /**
     * 查询卸料平台-监控详情-工作记录-15天
     * @param wsUnloadingPlatformManageDTO
     * @return
     */
    public List<WsUnloadingPlatformManageDTO> selectFifteenWorkRecordList(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO);

    /**
     * 查询卸料平台-监控详情-告警记录-15天
     * @param wsUnloadingPlatformManageDTO
     * @return
     */
    public List<WsUnloadingPlatformManageDTO> selectFifteenAlarmLogList(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO);

    /**
     *查询卸料平台-监控详情-工作记录-更多详情
     * @param wsUnloadingPlatformManageDTO
     * @return
     */
    public List<WsUnloadingPlatformManageDTO> selectEquipmentMoredetailsList(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO);

    public int updateWsUnloadingPlatformManages(WsUnloadingPlatformManage wsUnloadingPlatformManage);

    public WsUnloadingPlatformManageDTO selectTodayAlarmLog(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO);

    public int editUnloadingPlatformManage(String id);
}
