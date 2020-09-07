package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsAttendanceZone;

import java.util.List;

/**
 * 考勤分区 Service接口
 *
 * @author gaowei
 * @date 2020-06-23
 */
public interface IWsAttendanceZoneService
{
    /**
     * 查询考勤分区
     *
     * @param id 考勤分区 ID
     * @return 考勤分区
     */
    public WsAttendanceZone selectWsAttendanceZoneById(Integer id);

    /**
     * 查询考勤分区 列表
     *
     * @param wsAttendanceZone 考勤分区
     * @return 考勤分区 集合
     */
    public List<WsAttendanceZone> selectWsAttendanceZoneList(WsAttendanceZone wsAttendanceZone);

    /**
     * 新增考勤分区
     *
     * @param wsAttendanceZone 考勤分区
     * @return 结果
     */
    public int insertWsAttendanceZone(WsAttendanceZone wsAttendanceZone);

    /**
     * 修改考勤分区
     *
     * @param wsAttendanceZone 考勤分区
     * @return 结果
     */
    public int updateWsAttendanceZone(WsAttendanceZone wsAttendanceZone);

    /**
     * 批量删除考勤分区
     *
     * @param ids 需要删除的考勤分区 ID
     * @return 结果
     */
    public int deleteWsAttendanceZoneByIds(Integer[] ids);

    /**
     * 删除考勤分区 信息
     *
     * @param id 考勤分区 ID
     * @return 结果
     */
    public int deleteWsAttendanceZoneById(Integer id);


    /**
        * @Author liujie
        * @Description 通过区域获取设备统计列表
        * @Date 2020/7/18 16:25
        * @Param [wsAttendanceZone]
        * @Return java.util.List<com.jlkj.project.ws.domain.WsAttendanceZone>
       **/
    List<WsAttendanceZone> statisticsMachineListByZone(WsAttendanceZone wsAttendanceZone);
}
