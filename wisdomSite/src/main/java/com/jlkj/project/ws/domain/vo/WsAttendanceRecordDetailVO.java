package com.jlkj.project.ws.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 考勤记录详细VO对象 t_ws_attendance_record
 *
 * @author liujie
 * @date 2020-06-23
 */
@ApiModel("考勤记录详细VO")
public class WsAttendanceRecordDetailVO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;

    @ApiModelProperty("班组人员ID")
    /** 班组人员ID */
    private Integer teamPersonId;


    /** 考勤时间 */
    @ApiModelProperty("考勤时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考勤时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date attendanceTime;


    @ApiModelProperty("参建单位")
    @Excel(name = "参建单位")
    private String unitName;//参建单位

    @ApiModelProperty("班组")
    @Excel(name = "班组")
    private String teamName;//班组


    @ApiModelProperty("工种名称")
    @Excel(name = "工种名称")
    private String workTypeName;//工种名称

    @ApiModelProperty("方向(1=进场,2=出场)")
    @Excel(name = "方向",readConverterExp = "1=进场,2=出场")
    private String direction;//方向


    /** 某月第几天  */
    @ApiModelProperty("某月第几天")
    private Integer monthOfDay;


    /**累计出勤天数  */
    @ApiModelProperty("累计出勤天数")
    private Integer totalAttendanceDays;

    /** 筛选月份 */
    @JsonFormat(pattern = "yyyy-MM")
    @ApiModelProperty("筛选月份")
    private Date yearAndMonth;

    /** 多天考勤记录详情列表 */
    @ApiModelProperty("n天考勤记录详情列表")
    private List<WsAttendanceRecordDetailVO> recordDays;

    /** 人员月考勤数据列表 */
    private List<List<Map<String,String>>> attendanceDataOfMonth;

    private Integer attendanceDays;


    public List<List<Map<String, String>>> getAttendanceDataOfMonth() {
        return attendanceDataOfMonth;
    }

    public void setAttendanceDataOfMonth(List<List<Map<String, String>>> attendanceDataOfMonth) {
        this.attendanceDataOfMonth = attendanceDataOfMonth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeamPersonId() {
        return teamPersonId;
    }

    public void setTeamPersonId(Integer teamPersonId) {
        this.teamPersonId = teamPersonId;
    }

    public Date getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(Date attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getMonthOfDay() {
        return monthOfDay;
    }

    public void setMonthOfDay(Integer monthOfDay) {
        this.monthOfDay = monthOfDay;
    }

    public Integer getTotalAttendanceDays() {
        return totalAttendanceDays;
    }

    public void setTotalAttendanceDays(Integer totalAttendanceDays) {
        this.totalAttendanceDays = totalAttendanceDays;
    }

    public Date getYearAndMonth() {
        return yearAndMonth;
    }

    public void setYearAndMonth(Date yearAndMonth) {
        this.yearAndMonth = yearAndMonth;
    }

    public List<WsAttendanceRecordDetailVO> getRecordDays() {
        return recordDays;
    }

    public void setRecordDays(List<WsAttendanceRecordDetailVO> recordDays) {
        this.recordDays = recordDays;
    }

    public Integer getAttendanceDays() {
        return attendanceDays;
    }

    public void setAttendanceDays(Integer attendanceDays) {
        this.attendanceDays = attendanceDays;
    }
}
