package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsAttendanceMachine;
import com.jlkj.project.ws.domain.WsProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 考勤机 Mapper接口
 *
 * @author gaowei
 * @date 2020-06-23
 */
public interface WsAttendanceMachineMapper
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
     * @param wsAttendanceMachine 考勤机,dataScope 数据范围
     * @return 考勤机 集合
     */
    public List<WsAttendanceMachine> selectWsAttendanceMachineList(@Param("wsAttendanceMachine")WsAttendanceMachine wsAttendanceMachine,@Param("dataScope")  List<WsProject>dataScope);

    /**
        * @Author liujie
        * @Description 通过考勤区域获取考勤机列表
        * @Date 2020/6/24 16:47
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
     * 删除考勤机
     *
     * @param id 考勤机 ID
     * @return 结果
     */
    public int deleteWsAttendanceMachineById(Integer id);

    /**
     * 批量删除考勤机
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsAttendanceMachineByIds(Integer[] ids);

    /**
        * @Author liujie
        * @Description 通过设备序列号查询考勤机
        * @Date 2020/6/24 11:14
        * @Param [serialNumber]
        * @Return int
       **/
    int countWsAttendanceBySerialNumber(@Param("serialNumber") String serialNumber);
    /**
        * @Author liujie
        * @Description 批量更新区域
        * @Date 2020/7/16 20:58
        * @Param [serialNumbers, zoneId]
        * @Return int
       **/
    int batchUpdateZone(@Param("serialNumbers")String[] serialNumbers,@Param("zoneId") Integer zoneId);

    /**
        * @Author liujie
        * @Description 根据设备编号查询考勤机
        * @Date 2020/8/13 11:04
        * @Param [serialNumber]
        * @Return com.jlkj.project.ws.domain.WsAttendanceMachine
       **/
    WsAttendanceMachine findWsAttendanceMachineBySerialNumber(@Param("serialNumber") String serialNumber);
}
