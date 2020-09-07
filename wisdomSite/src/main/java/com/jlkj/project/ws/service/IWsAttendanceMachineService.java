package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsAttendanceMachine;

import java.util.List;

/**
 * 考勤机 Service接口
 *
 * @author gaowei
 * @date 2020-06-23
 */
public interface IWsAttendanceMachineService
{
    /**
     * 查询考勤机
     *
     * @param id 考勤机 ID
     * @return 考勤机
     */
    public WsAttendanceMachine selectWsAttendanceMachineById(Integer id);

    /**
     * 查询考勤机 列表
     *
     * @param wsAttendanceMachine 考勤机
     * @return 考勤机 集合
     */
    public List<WsAttendanceMachine> selectWsAttendanceMachineList(WsAttendanceMachine wsAttendanceMachine);

    /**
        * @Author liujie
        * @Description 通过考勤区域获取考勤机列表
        * @Date 2020/6/24 16:48
        * @Param [zoneId]
        * @Return java.util.List<com.jlkj.project.ws.domain.WsAttendanceMachine>
       **/
    List<WsAttendanceMachine> selectWsAttendanceMachineListByZone(Integer zoneId);
    /**
     * 新增考勤机
     *
     * @param wsAttendanceMachine 考勤机
     * @return 结果
     */
    public int insertWsAttendanceMachine(WsAttendanceMachine wsAttendanceMachine);

    /**
     * 修改考勤机
     *
     * @param wsAttendanceMachine 考勤机
     * @return 结果
     */
    public int updateWsAttendanceMachine(WsAttendanceMachine wsAttendanceMachine);

    /**
     * 批量删除考勤机
     *
     * @param ids 需要删除的考勤机 ID
     * @return 结果
     */
    public int deleteWsAttendanceMachineByIds(Integer[] ids);

    /**
     * 删除考勤机 信息
     *
     * @param id 考勤机 ID
     * @return 结果
     */
    public int deleteWsAttendanceMachineById(Integer id);


    /**
     * @Author liujie
     * @Description 根据设备编号查询考勤机
     * @Date 2020/8/13 11:04
     * @Param [serialNumber]
     * @Return com.jlkj.project.ws.domain.WsAttendanceMachine
     **/
    WsAttendanceMachine findWsAttendanceMachineBySerialNumber( String serialNumber);
}
