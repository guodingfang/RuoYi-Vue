package com.jlkj.project.ws.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.util.List;

/**
 * 考勤记录 对象 t_ws_attendance_record
 *
 * @author gaowei
 * @date 2020-06-23
 */
public class WsAttendanceRecord extends BaseEntity
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "考勤时间")
    private Date attendanceTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;

    @ApiModelProperty("姓名")
    @Transient
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
    @Transient
    @Excel(name = "参建单位")
    private String unitName;//参建单位

    @ApiModelProperty("班组")
    @Transient
    @Excel(name = "班组")
    private String teamName;//班组

    @ApiModelProperty("工种")
    @Transient
    @Excel(name = "工种")
    private String workTypeName;//工种

    @ApiModelProperty("方向")
    @Excel(name = "方向")
    private String direction;//方向

    @ApiModelProperty("考勤设备")
    @Transient
    @Excel(name = "考勤设备")
    private String machineName;//考勤设备

    @ApiModelProperty("projectId")
    @Transient
    private String projectId;//项目id

    @ApiModelProperty("unitId")
    @Transient
    private String unitId;//参建单位id

    @ApiModelProperty("班组id")
    @Transient
    private String teamId;//班组id

    @ApiModelProperty("人员id")
    @Transient
    private String personId;//人员id

    @ApiModelProperty("条件日期")
    @Transient
    private String attenDate;

    /** 考勤记录列表 */
    @ApiModelProperty("考勤记录列表")
    private List<WsAttendanceRecord> records;


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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public List<WsAttendanceRecord> getRecords() {
        return records;
    }

    public void setRecords(List<WsAttendanceRecord> records) {
        this.records = records;
    }

    public String getAttenDate() {
        return attenDate;
    }

    public void setAttenDate(String attenDate) {
        this.attenDate = attenDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("teamPersonId", getTeamPersonId())
            .append("machineId", getMachineId())
            .append("attendanceTime", getAttendanceTime())
            .append("updatedTime", getUpdatedTime())
            .append("createdTime", getCreatedTime())
            .toString();
    }
}
