package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsAttendanceZone;
import com.jlkj.project.ws.domain.WsProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 考勤分区 Mapper接口
 *
 * @author gaowei
 * @date 2020-06-23
 */
public interface WsAttendanceZoneMapper
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
     * @param wsAttendanceZone 考勤分区,dataScope 数据范围
     * @return 考勤分区 集合
     */
    public List<WsAttendanceZone> selectWsAttendanceZoneList(@Param("wsAttendanceZone") WsAttendanceZone wsAttendanceZone,@Param("dataScope")  List<WsProject>dataScope);

    /**
        * @Author liujie
        * @Description 获取考勤分区列表（含打卡机拼接字符）
        * @Date 2020/6/27 9:12
        * @Param [wsAttendanceZone],项目范围projectScope
        * @Return java.util.List<com.jlkj.project.ws.domain.WsAttendanceZone>
       **/
    public List<WsAttendanceZone> selectWsAttendanceZoneWithMachineList(@Param("wsAttendanceZone") WsAttendanceZone wsAttendanceZone,@Param("dataScope") List<WsProject>dataScope);
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
     * 删除考勤分区
     *
     * @param id 考勤分区 ID
     * @return 结果
     */
    public int deleteWsAttendanceZoneById(Integer id);

    /**
     * 批量删除考勤分区
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsAttendanceZoneByIds(Integer[] ids);

    /**
        * @Author liujie
        * @Description  判断分区下是否存在打卡机
        * @Date 2020/6/27 9:37
        * @Param [id]
        * @Return int
       **/
    int existedAttendanceMachine(Integer id);

    /**
        * @Author liujie
        * @Description 获取分区下设备的统计
        * @Date 2020/7/18 16:08
        * @Param [wsAttendanceZone]
        * @Return java.util.List<com.jlkj.project.ws.domain.WsAttendanceZone>
       **/
    List<WsAttendanceZone> statisticsMachineOfByZone(WsAttendanceZone wsAttendanceZone);
}
