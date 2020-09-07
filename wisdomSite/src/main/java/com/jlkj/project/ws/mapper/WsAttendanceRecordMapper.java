package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsAttendanceRecord;
import com.jlkj.project.ws.domain.vo.WsAttendanceRecordDetailVO;
import com.jlkj.project.ws.domain.vo.WsAttendanceRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 考勤记录 Mapper接口
 *
 * @author gaowei
 * @date 2020-06-23
 */
public interface WsAttendanceRecordMapper
{
    /**
     * 查询考勤记录
     *
     * @param id 考勤记录 ID
     * @return 考勤记录
     */
    public WsAttendanceRecord selectWsAttendanceRecordById(Integer id);

    /**
     * 查询考勤记录 列表
     *
     * @param wsAttendanceRecordVO 考勤记录
     * @return 考勤记录 集合
     */
    public List<WsAttendanceRecordVO> selectWsAttendanceRecordList(WsAttendanceRecordVO wsAttendanceRecordVO);

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
     * 删除考勤记录
     *
     * @param id 考勤记录 ID
     * @return 结果
     */
    public int deleteWsAttendanceRecordById(Integer id);

    /**
     * 批量删除考勤记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsAttendanceRecordByIds(Integer[] ids);

    /**
        * @Author liujie
        * @Description 获取工程人员考勤详细列表
        * @Date 2020/7/2 15:04
        * @Param [wsAttendanceRecordDetailVO]
        * @Return java.util.List<com.jlkj.project.ws.domain.vo.WsAttendanceRecordDetailVO>
       **/
    List<WsAttendanceRecordDetailVO>selectWsAttendanceRecordDetailList(WsAttendanceRecordDetailVO wsAttendanceRecordDetailVO);

    public List<WsAttendanceRecord> selectAttendanceRecordList(@Param("wsAttendanceRecord") WsAttendanceRecord wsAttendanceRecord,@Param("projectIds") Integer[] projectIds);

    /**
        * @Author liujie
        * @Description 获取考勤报表
        * @Date 2020/8/5 18:46
        * @Param [wsAttendanceRecordVO]
        * @Return java.util.List<com.jlkj.project.ws.domain.vo.WsAttendanceRecordVO>
       **/
    List<WsAttendanceRecordVO> selectAttendanceReportForms (WsAttendanceRecordVO wsAttendanceRecordVO);

    /**
        * @Author liujie
        * @Description 获取整点出勤数据
        * @Date 2020/8/25 21:13
        * @Param [wsAttendanceRecordVO]
        * @Return java.util.List<com.jlkj.project.ws.domain.vo.WsAttendanceRecordVO>
       **/
    List<WsAttendanceRecordVO>  selectPersonHourPointAttendance(WsAttendanceRecordVO wsAttendanceRecordVO );
}
