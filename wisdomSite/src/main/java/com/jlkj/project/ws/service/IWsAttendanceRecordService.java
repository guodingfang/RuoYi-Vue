package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsAttendanceRecord;
import com.jlkj.project.ws.domain.WsTeamPerson;
import com.jlkj.project.ws.domain.vo.WsAttendanceRecordDetailVO;
import com.jlkj.project.ws.domain.vo.WsAttendanceRecordVO;
import com.jlkj.project.ws.dto.WsSiteAttendanceStatisticsDTO;

import java.util.HashMap;
import java.util.List;

/**
 * 考勤记录 Service接口
 *
 * @author gaowei
 * @date 2020-06-23
 */
public interface IWsAttendanceRecordService
{
    /**
     * 查询考勤记录
     *
     * @param id 考勤记录 ID
     * @return 考勤记录
     */
    public WsAttendanceRecord selectWsAttendanceRecordById(Integer id);



    /**
     * @Author liujie
     * @Description 获取考勤报表
     * @Date 2020/8/5 18:46
     * @Param [wsAttendanceRecordVO]
     * @Return java.util.List<com.jlkj.project.ws.domain.WsTeamPerson>
     **/
    List<WsTeamPerson> selectAttendanceReportForms (WsAttendanceRecordVO wsAttendanceRecordVO);
    /**
     * 新增考勤记录
     *
     * @param wsAttendanceRecord 考勤记录
     * @return 结果
     */
    public int insertWsAttendanceRecord(WsAttendanceRecord wsAttendanceRecord);

    /**
     * 修改考勤记录
     *
     * @param wsAttendanceRecord 考勤记录
     * @return 结果
     */
    public int updateWsAttendanceRecord(WsAttendanceRecord wsAttendanceRecord);

    /**
     * 批量删除考勤记录
     *
     * @param ids 需要删除的考勤记录 ID
     * @return 结果
     */
    public int deleteWsAttendanceRecordByIds(Integer[] ids);

    /**
     * 删除考勤记录 信息
     *
     * @param id 考勤记录 ID
     * @return 结果
     */
    public int deleteWsAttendanceRecordById(Integer id);

    public WsSiteAttendanceStatisticsDTO selectPersonnelNumber();


    public List<WsSiteAttendanceStatisticsDTO> selectBePresentORattendanceToday();

    public List<WsSiteAttendanceStatisticsDTO> selectPersonnelProjectProportion();

    public List<WsSiteAttendanceStatisticsDTO> selectProjectAttendance(String randomData);

    public List<WsSiteAttendanceStatisticsDTO> selectScenePersonnelChangeTrend();

    public List<WsSiteAttendanceStatisticsDTO> selectSevenDaysPersonnelAttendance();

    public List<WsSiteAttendanceStatisticsDTO> selectFieldWork();

    public List<WsSiteAttendanceStatisticsDTO> selectConstructionWorker(WsSiteAttendanceStatisticsDTO attendanceStatisticsDTO);

    public List<WsSiteAttendanceStatisticsDTO> selectParticipatingUnits(String randomData);

    public List<WsSiteAttendanceStatisticsDTO> selectPersonnelProjectProportionWhole();

    public List<WsSiteAttendanceStatisticsDTO> selectFieldWorkWhole();

    public List<WsSiteAttendanceStatisticsDTO> selectConstructionWorkerWhole(WsSiteAttendanceStatisticsDTO attendanceStatisticsDTO);

    /** 获取考勤过滤条件*/
    HashMap<String, Object> getFilterInfo(WsAttendanceRecordVO wsAttendanceRecordVO);

    /**
        * @Author liujie
        * @Description 查询工程人员出勤记录详细
        * @Date 2020/7/2 15:15
        * @Param [wsAttendanceRecordDetailVO, teamPersonId]
        * @Return com.jlkj.project.ws.domain.vo.WsAttendanceRecordDetailVO
       **/
    WsAttendanceRecordDetailVO selectWsAttendanceRecordDetailList(WsAttendanceRecordDetailVO wsAttendanceRecordDetailVO);

    public List<WsAttendanceRecord> selectAttendanceRecordList(WsAttendanceRecord wsAttendanceRecord);


    /**
     * @Author liujie
     * @Description 获取整点出勤数据
     * @Date 2020/8/25 21:13
     * @Param [wsAttendanceRecord]
     * @Return java.util.List<com.jlkj.project.ws.domain.vo.WsSiteAttendanceStatisticsDTO>
     **/
    List<WsSiteAttendanceStatisticsDTO>  selectPersonAttendanceTrend (WsAttendanceRecordVO wsAttendanceRecordVO );
}
