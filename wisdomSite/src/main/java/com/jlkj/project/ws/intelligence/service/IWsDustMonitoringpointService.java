package com.jlkj.project.ws.intelligence.service;

import java.util.List;

import com.jlkj.project.ws.domain.WsProjectEquipment;
import com.jlkj.project.ws.domain.WsVideoChannelManagement;
import com.jlkj.project.ws.intelligence.domain.WsDustMonitoringpoint;

/**
 * 扬尘监测点视频管理 Service接口
 * 
 * @author gaowei
 * @date 2020-07-07
 */
public interface IWsDustMonitoringpointService
{
    /**
     * 查询扬尘监测点视频管理 
     * 
     * @param id 扬尘监测点视频管理 ID
     * @return 扬尘监测点视频管理 
     */
    public WsDustMonitoringpoint selectWsDustMonitoringpointById(Integer id);

    /**
     * 查询扬尘监测点视频管理 列表
     * 
     * @param wsDustMonitoringpoint 扬尘监测点视频管理
     * @return 扬尘监测点视频管理 集合
     */
    public List<WsDustMonitoringpoint> selectWsDustMonitoringpointList(WsDustMonitoringpoint wsDustMonitoringpoint);

    /**
     * 新增扬尘监测点视频管理 
     * 
     * @param wsDustMonitoringpoint 扬尘监测点视频管理
     * @return 结果
     */
    public int insertWsDustMonitoringpoint(WsDustMonitoringpoint wsDustMonitoringpoint);

    /**
     * 修改扬尘监测点视频管理 
     * 
     * @param wsDustMonitoringpoint 扬尘监测点视频管理
     * @return 结果
     */
    public int updateWsDustMonitoringpoint(WsDustMonitoringpoint wsDustMonitoringpoint);

    /**
     * 批量删除扬尘监测点视频管理 
     * 
     * @param ids 需要删除的扬尘监测点视频管理 ID
     * @return 结果
     */
    public int deleteWsMonitoringpointVideoByIds(Integer[] ids);

    /**
     * 删除扬尘监测点视频管理 信息
     * 
     * @param id 扬尘监测点视频管理 ID
     * @return 结果
     */
    public int deleteWsDustMonitoringpointById(Integer id);

    public List<WsProjectEquipment> selectListDustMonitoring(WsProjectEquipment wsProjectEquipment);

    public List<WsDustMonitoringpoint> listHookVideoRepeat(WsDustMonitoringpoint wsDustMonitoringpoint);

    public List<WsVideoChannelManagement> listHookVideo(WsVideoChannelManagement wsVideoChannelManagement);

    public int enableDisableWsDustMonitoringpoint(String isDelete,Integer id);
}
