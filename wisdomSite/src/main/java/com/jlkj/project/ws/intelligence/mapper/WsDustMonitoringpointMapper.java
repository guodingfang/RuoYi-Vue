package com.jlkj.project.ws.intelligence.mapper;

import java.util.List;
import com.jlkj.project.ws.intelligence.domain.WsDustMonitoringpoint;

/**
 * 扬尘监测点视频管理 Mapper接口
 * 
 * @author gaowei
 * @date 2020-07-07
 */
public interface WsDustMonitoringpointMapper
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
     * 删除扬尘监测点视频管理 
     * 
     * @param id 扬尘监测点视频管理 ID
     * @return 结果
     */
    public int deleteWsDustMonitoringpointById(Integer id);

    /**
     * 批量删除扬尘监测点视频管理 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsMonitoringpointVideoByIds(Integer[] ids);

    public List<WsDustMonitoringpoint> listHookVideoRepeat(WsDustMonitoringpoint wsMonitoringpointVideo);

    public int updateWsDustMonitoringpointIsDelete(WsDustMonitoringpoint wsDustMonitoringpoint);
}
