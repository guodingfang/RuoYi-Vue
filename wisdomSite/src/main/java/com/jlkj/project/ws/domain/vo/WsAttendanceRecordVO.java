package com.jlkj.project.ws.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.util.List;

/**
 * 考勤记录VO 对象 t_ws_attendance_record
 *
 * @author gaowei
 * @date 2020-06-23
 */
@ApiModel("考勤记录VO")
public class WsAttendanceRecordVO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;

    /** 班组人员ID */
    private Integer teamPersonId;

    /** 考勤机ID */
    private Integer machineId;

    /** 考勤时间 */
    @ApiModelProperty("考勤时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考勤时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date attendanceTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;

    @ApiModelProperty("姓名")
    @Excel(name = "姓名")
    private String personName;//人员姓名

    @ApiModelProperty("身份证号码")
    @Transient
    @Excel(name = "身份证号码")
    private String idNumber;//身份证号码

    @ApiModelProperty("电话号码")
    @Transient
    @Excel(name = "电话号码")
    private String mobilePhone;//电话号码

    @ApiModelProperty("参建单位")
    @Excel(name = "参建单位")
    private String unitName;//参建单位

    @ApiModelProperty("班组")
    @Excel(name = "班组")
    private String teamName;//班组

    @ApiModelProperty("工种")
    @Excel(name = "工种")
    private String workType;//工种

    @ApiModelProperty("工种名称")
    @Excel(name = "工种名称")
    private String workTypeName;//工种名称

    @ApiModelProperty("方向")
    @Excel(name = "方向")
    private String direction;//方向

    @ApiModelProperty("考勤设备")
    @Excel(name = "考勤设备")
    private String machineName;//考勤设备

    @ApiModelProperty("projectId")
    private Integer projectId;//项目id

    @ApiModelProperty("unitId")
    private Integer unitId;//参建单位id

    @ApiModelProperty("teamId")
    private Integer teamId;//班组id

    /** 性别  */
    @ApiModelProperty("性别")
    @Excel(name = "性别",readConverterExp = "1=男,0=女")
    private Integer sex;

    /** 某月第几天  */
    @ApiModelProperty("某月第几天")
    private Integer monthOfDay;

    /**  工时 */
    @ApiModelProperty("工时")
    private Float manHour;

    /** 工时列表 */
    @ApiModelProperty("工时列表")
    private List<Float> manHours;

    /** 人员类型 */
    @ApiModelProperty("人员类型")
    private String personType;

    /**累计出勤天数  */
    @ApiModelProperty("累计出勤天数")
    private Integer totalAttendanceDays;
    /** 累计出勤时长 */
    @ApiModelProperty("累计出勤时长")
    private Float totalAttendanceHours;
    /** 筛选月份 */
    @JsonFormat(pattern = "yyyy-MM")
    @ApiModelProperty("筛选月份")
    private Date yearAndMonth;

    /** 项目名称 */
    @ApiModelProperty("项目名称")
    private String projectName;


    /** 一天某小时 */
    @ApiModelProperty("一天某小时")
    private String hourOfDay;

    @ApiModelProperty("现场人员变化趋势:管理人员")
    private Integer managementNumber;

    @ApiModelProperty("现场人员变化趋势:建筑工人")
    private Integer constructionWorkerNumber;

    @ApiModelProperty("现场人员变化趋势:时间段")
    private String timeQuantum;


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getYearAndMonth() {
        return yearAndMonth;
    }


    public void setYearAndMonth(Date yearAndMonth) {
        this.yearAndMonth = yearAndMonth;
    }

    public Integer getTotalAttendanceDays() {
        return totalAttendanceDays;
    }

    public void setTotalAttendanceDays(Integer totalAttendanceDays) {
        this.totalAttendanceDays = totalAttendanceDays;
    }

    public Float getTotalAttendanceHours() {
        return totalAttendanceHours;
    }

    public void setTotalAttendanceHours(Float totalAttendanceHours) {
        this.totalAttendanceHours = totalAttendanceHours;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public Integer getMonthOfDay() {
        return monthOfDay;
    }

    public void setMonthOfDay(Integer monthOfDay) {
        this.monthOfDay = monthOfDay;
    }

    public Float getManHour() {
        return manHour;
    }

    public void setManHour(Float manHour) {
        this.manHour = manHour;
    }

    public List<Float> getManHours() {
        return manHours;
    }

    public void setManHours(List<Float> manHours) {
        this.manHours = manHours;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setTeamPersonId(Integer teamPersonId)
    {
        this.teamPersonId = teamPersonId;
    }

    public Integer getTeamPersonId()
    {
        return teamPersonId;
    }
    public void setMachineId(Integer machineId)
    {
        this.machineId = machineId;
    }

    public Integer getMachineId()
    {
        return machineId;
    }
    public void setAttendanceTime(Date attendanceTime)
    {
        this.attendanceTime = attendanceTime;
    }

    public Date getAttendanceTime()
    {
        return attendanceTime;
    }
    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
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

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public Integer getManagementNumber() {
        return managementNumber;
    }

    public void setManagementNumber(Integer managementNumber) {
        this.managementNumber = managementNumber;
    }

    public Integer getConstructionWorkerNumber() {
        return constructionWorkerNumber;
    }

    public void setConstructionWorkerNumber(Integer constructionWorkerNumber) {
        this.constructionWorkerNumber = constructionWorkerNumber;
    }

    public String getTimeQuantum() {
        return timeQuantum;
    }

    public void setTimeQuantum(String timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    public String getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(String hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsAttendanceRecordVO{");
        sb.append("id=").append(id);
        sb.append(", teamPersonId=").append(teamPersonId);
        sb.append(", machineId=").append(machineId);
        sb.append(", attendanceTime=").append(attendanceTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", personName='").append(personName).append('\'');
        sb.append(", idNumber='").append(idNumber).append('\'');
        sb.append(", mobilePhone='").append(mobilePhone).append('\'');
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", teamName='").append(teamName).append('\'');
        sb.append(", workType='").append(workType).append('\'');
        sb.append(", workTypeName='").append(workTypeName).append('\'');
        sb.append(", direction='").append(direction).append('\'');
        sb.append(", machineName='").append(machineName).append('\'');
        sb.append(", projectId=").append(projectId);
        sb.append(", unitId=").append(unitId);
        sb.append(", teamId=").append(teamId);
        sb.append(", sex=").append(sex);
        sb.append(", monthOfDay=").append(monthOfDay);
        sb.append(", manHour=").append(manHour);
        sb.append(", manHours=").append(manHours);
        sb.append(", personType='").append(personType).append('\'');
        sb.append(", totalAttendanceDays=").append(totalAttendanceDays);
        sb.append(", totalAttendanceHours=").append(totalAttendanceHours);
        sb.append(", yearAndMonth=").append(yearAndMonth);
        sb.append(", projectName='").append(projectName).append('\'');
        sb.append(", managementNumber=").append(managementNumber);
        sb.append(", constructionWorkerNumber=").append(constructionWorkerNumber);
        sb.append(", timeQuantum='").append(timeQuantum).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
