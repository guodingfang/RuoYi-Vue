package com.jlkj.project.ws.dto;

import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @ClassName WsSiteAttendanceStatisticsDTO
 * @Description
 * @Author Thinkpad
 * @Date 2020/6/15 19:01
 * @Version 1.0
 **/
@ApiModel("工地考勤首页统计DTO")
public class WsSiteAttendanceStatisticsDTO extends BaseEntity {
    @ApiModelProperty("在岗")
    private Integer onDuty;

    /** 今日出勤 */
    @ApiModelProperty("今日出勤")
    private Integer attendanceToday;

    /** 今日出勤率 */
    @ApiModelProperty("今日出勤率")
    private double attendanceTodayRate;

    /** 在场*/
    @ApiModelProperty("在场")
    private Integer bePresent;

    /** 在册*/
    @ApiModelProperty("在册")
    private Integer registered;

    /** 项目*/
    @ApiModelProperty("项目")
    private Integer projectNumber;

    /** 参建单位 */
    @ApiModelProperty("参建单位")
    private Integer participatingUnitNumber;

    @ApiModelProperty("在场，出勤近7日日期")
    private Date bePresentORattendanceTodayTime;

    @ApiModelProperty("在场近7日数")
    private Integer bePresentNumber;

    @ApiModelProperty("出勤近7日数")
    private Integer attendanceTodayNumber;

    @ApiModelProperty("各项目在场人员占比")
    private Integer personnelProjectProportion;

    @ApiModelProperty("各项目在场人员占比比例")
    private String proportion;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("下属项目出勤统计/下属参建单位统计:现场")
    private Integer sceneNumber;

    @ApiModelProperty("下属项目出勤统计/下属参建单位统计:出勤")
    private Integer attendanceNumber;

    @ApiModelProperty("下属项目出勤统计/下属参建单位统计:在场")
    private Integer presenceNumber;

    @ApiModelProperty("现场人员变化趋势:管理人员")
    private Integer managementNumber;

    @ApiModelProperty("现场人员变化趋势:建筑工人")
    private Integer constructionWorkerNumber;

    @ApiModelProperty("现场人员变化趋势:时间段")
    private String timeQuantum;

    @ApiModelProperty("近7日人员出勤:管理人员")
    private Integer sevenDaysManagementNumber;

    @ApiModelProperty("近7日人员出勤:建筑工人")
    private Integer sevenDaysConstructionWorkerNumber;

    @ApiModelProperty("近7日人员出勤:全部人员")
    private Integer sevenDaysWholeNumber;

    @ApiModelProperty("现场工种前十:工种数")
    private Integer numberOfJobs;

    @ApiModelProperty("现场工种前十:工种名称")
    private String workName;

    @ApiModelProperty("建筑工人分析前十:分析数量")
    private Integer professionNumber;

    @ApiModelProperty("建筑工人分析前十:分析名称")
    private String professionName;

    @ApiModelProperty("下属参建单位统计:参建单位名称")
    private String unitName;

    private Integer projectId;

    private String randomData;

    private String type;



    public Integer getOnDuty() {
        return onDuty;
    }

    public void setOnDuty(Integer onDuty) {
        this.onDuty = onDuty;
    }

    public Integer getAttendanceToday() {
        return attendanceToday;
    }

    public void setAttendanceToday(Integer attendanceToday) {
        this.attendanceToday = attendanceToday;
    }

    public double getAttendanceTodayRate() {
        return attendanceTodayRate;
    }

    public void setAttendanceTodayRate(double attendanceTodayRate) {
        this.attendanceTodayRate = attendanceTodayRate;
    }

    public Integer getBePresent() {
        return bePresent;
    }

    public void setBePresent(Integer bePresent) {
        this.bePresent = bePresent;
    }

    public Integer getRegistered() {
        return registered;
    }

    public void setRegistered(Integer registered) {
        this.registered = registered;
    }

    public Integer getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(Integer projectNumber) {
        this.projectNumber = projectNumber;
    }

    public Integer getParticipatingUnitNumber() {
        return participatingUnitNumber;
    }

    public void setParticipatingUnitNumber(Integer participatingUnitNumber) {
        this.participatingUnitNumber = participatingUnitNumber;
    }

    public Date getBePresentORattendanceTodayTime() {
        return bePresentORattendanceTodayTime;
    }

    public void setBePresentORattendanceTodayTime(Date bePresentORattendanceTodayTime) {
        this.bePresentORattendanceTodayTime = bePresentORattendanceTodayTime;
    }

    public Integer getBePresentNumber() {
        return bePresentNumber;
    }

    public void setBePresentNumber(Integer bePresentNumber) {
        this.bePresentNumber = bePresentNumber;
    }

    public Integer getAttendanceTodayNumber() {
        return attendanceTodayNumber;
    }

    public void setAttendanceTodayNumber(Integer attendanceTodayNumber) {
        this.attendanceTodayNumber = attendanceTodayNumber;
    }

    public Integer getPersonnelProjectProportion() {
        return personnelProjectProportion;
    }

    public void setPersonnelProjectProportion(Integer personnelProjectProportion) {
        this.personnelProjectProportion = personnelProjectProportion;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getSceneNumber() {
        return sceneNumber;
    }

    public void setSceneNumber(Integer sceneNumber) {
        this.sceneNumber = sceneNumber;
    }

    public Integer getAttendanceNumber() {
        return attendanceNumber;
    }

    public void setAttendanceNumber(Integer attendanceNumber) {
        this.attendanceNumber = attendanceNumber;
    }

    public Integer getPresenceNumber() {
        return presenceNumber;
    }

    public void setPresenceNumber(Integer presenceNumber) {
        this.presenceNumber = presenceNumber;
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

    public Integer getSevenDaysManagementNumber() {
        return sevenDaysManagementNumber;
    }

    public void setSevenDaysManagementNumber(Integer sevenDaysManagementNumber) {
        this.sevenDaysManagementNumber = sevenDaysManagementNumber;
    }

    public Integer getSevenDaysConstructionWorkerNumber() {
        return sevenDaysConstructionWorkerNumber;
    }

    public void setSevenDaysConstructionWorkerNumber(Integer sevenDaysConstructionWorkerNumber) {
        this.sevenDaysConstructionWorkerNumber = sevenDaysConstructionWorkerNumber;
    }

    public Integer getSevenDaysWholeNumber() {
        return sevenDaysWholeNumber;
    }

    public void setSevenDaysWholeNumber(Integer sevenDaysWholeNumber) {
        this.sevenDaysWholeNumber = sevenDaysWholeNumber;
    }

    public Integer getNumberOfJobs() {
        return numberOfJobs;
    }

    public void setNumberOfJobs(Integer numberOfJobs) {
        this.numberOfJobs = numberOfJobs;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public Integer getProfessionNumber() {
        return professionNumber;
    }

    public void setProfessionNumber(Integer professionNumber) {
        this.professionNumber = professionNumber;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getRandomData() {
        return randomData;
    }

    public void setRandomData(String randomData) {
        this.randomData = randomData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }
}
