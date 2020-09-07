package com.jlkj.project.ws.dto;

import com.jlkj.framework.web.domain.BaseEntity;
import com.jlkj.project.ws.domain.WsArea;
import com.jlkj.project.ws.domain.WsProject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName WsTowerCraneMonitoringStatisticsDTO
 * @Description TODO
 * @Author gaowei
 * @Date 2020/6/29 21:00
 * @Version 1.0
 **/
@ApiModel("塔机监测-企业-监控统计DTO")
public class WsTowerCraneMonitoringStatisticsDTO extends BaseEntity {

    /** 项目id */
    @ApiModelProperty("项目id")
    private Integer projectId;

    @ApiModelProperty("监控设备分析：塔吊设备数")
    private Integer towerCraneEquipmentNum;

    @ApiModelProperty("监控设备分析：塔吊监控设备数")
    private Integer towerCraneMonitoringEquipmentNum;

    @ApiModelProperty("监控设备分析：监控在线率")
    private String monitoringOnlineRate;

    @ApiModelProperty("监控设备分析：监控在线数")
    private Integer monitoringOnlineNum;

    @ApiModelProperty("设备违章分析：违章次数")
    private Integer violationsNum;

    @ApiModelProperty("项目违章占比：违章项目占比")
    private String proportionProjectViolation;

    @ApiModelProperty("违章项目排名：项目名称")
    private String projectName;

    @ApiModelProperty("违章项目排名：项目数")
    private Integer projectNumber;

    @ApiModelProperty("日期")
    private String statisticsTime;

    @ApiModelProperty("条件天数")
    private Integer days;

    @ApiModelProperty("条件公司id")
    private Integer companyId;

    @ApiModelProperty("监控设备")
    private String monitoringEquipmentNo;


    @ApiModelProperty("塔机人脸编号")
    private String faceNumber;


    @ApiModelProperty("司机不良操作/设备违章：违章次数")
    private Integer violationsNumber;

    @ApiModelProperty("司机不良操作：违章率")
    private String violationsRate;

    @ApiModelProperty("司机不良操作：司机姓名")
    private String driverName;

    @ApiModelProperty("设备违章/设备工作记录：塔吊设备名称")
    private String towerCraneEquipmentName;

    @ApiModelProperty("设备工作记录：吊重")
    private String liftingWeight;

    @ApiModelProperty("设备工作记录：吊次")
    private String hoistingTimes;

    @ApiModelProperty("设备工作记录：工作时长")
    private String workingHours;

    @ApiModelProperty("设备工作记录：吊程")
    private String liftingDistance;

    @ApiModelProperty("自定义：日期条件")
    private String conditionsDate;


    /*自定义-企业*/
    @ApiModelProperty("自定义-企业：在场塔吊数")
    private String towerCraneOnSiteNum;

    @ApiModelProperty("自定义-企业：监控设备数")
    private String tonitoringEquipmentNum;

    @ApiModelProperty("自定义-企业：监控在线数")
    private String monitorOnLineNum;

    @ApiModelProperty("自定义-企业：监控在线率")
    private String monitorOnLineRate;

    @ApiModelProperty("自定义-企业：设备违章率")
    private String equipmentViolationRate;

    @ApiModelProperty("自定义-企业：设备吊装次数")
    private String equipmentHoistingNum;

    @ApiModelProperty("自定义-企业：设备违章次数")
    private String equipmentViolationNum;


    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getTowerCraneEquipmentNum() {
        return towerCraneEquipmentNum;
    }

    public void setTowerCraneEquipmentNum(Integer towerCraneEquipmentNum) {
        this.towerCraneEquipmentNum = towerCraneEquipmentNum;
    }

    public Integer getTowerCraneMonitoringEquipmentNum() {
        return towerCraneMonitoringEquipmentNum;
    }

    public void setTowerCraneMonitoringEquipmentNum(Integer towerCraneMonitoringEquipmentNum) {
        this.towerCraneMonitoringEquipmentNum = towerCraneMonitoringEquipmentNum;
    }

    public String getMonitoringOnlineRate() {
        return monitoringOnlineRate;
    }

    public void setMonitoringOnlineRate(String monitoringOnlineRate) {
        this.monitoringOnlineRate = monitoringOnlineRate;
    }

    public Integer getViolationsNum() {
        return violationsNum;
    }

    public void setViolationsNum(Integer violationsNum) {
        this.violationsNum = violationsNum;
    }

    public String getProportionProjectViolation() {
        return proportionProjectViolation;
    }

    public void setProportionProjectViolation(String proportionProjectViolation) {
        this.proportionProjectViolation = proportionProjectViolation;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(Integer projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getStatisticsTime() {
        return statisticsTime;
    }

    public void setStatisticsTime(String statisticsTime) {
        this.statisticsTime = statisticsTime;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getMonitoringOnlineNum() {
        return monitoringOnlineNum;
    }

    public void setMonitoringOnlineNum(Integer monitoringOnlineNum) {
        this.monitoringOnlineNum = monitoringOnlineNum;
    }

    public String getMonitoringEquipmentNo() {
        return monitoringEquipmentNo;
    }

    public void setMonitoringEquipmentNo(String monitoringEquipmentNo) {
        this.monitoringEquipmentNo = monitoringEquipmentNo;
    }

    public Integer getViolationsNumber() {
        return violationsNumber;
    }

    public void setViolationsNumber(Integer violationsNumber) {
        this.violationsNumber = violationsNumber;
    }

    public String getViolationsRate() {
        return violationsRate;
    }

    public void setViolationsRate(String violationsRate) {
        this.violationsRate = violationsRate;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getTowerCraneEquipmentName() {
        return towerCraneEquipmentName;
    }

    public void setTowerCraneEquipmentName(String towerCraneEquipmentName) {
        this.towerCraneEquipmentName = towerCraneEquipmentName;
    }

    public String getLiftingWeight() {
        return liftingWeight;
    }

    public void setLiftingWeight(String liftingWeight) {
        this.liftingWeight = liftingWeight;
    }

    public String getHoistingTimes() {
        return hoistingTimes;
    }

    public void setHoistingTimes(String hoistingTimes) {
        this.hoistingTimes = hoistingTimes;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getLiftingDistance() {
        return liftingDistance;
    }

    public void setLiftingDistance(String liftingDistance) {
        this.liftingDistance = liftingDistance;
    }

    public String getConditionsDate() {
        return conditionsDate;
    }

    public void setConditionsDate(String conditionsDate) {
        this.conditionsDate = conditionsDate;
    }

    public String getTowerCraneOnSiteNum() {
        return towerCraneOnSiteNum;
    }

    public void setTowerCraneOnSiteNum(String towerCraneOnSiteNum) {
        this.towerCraneOnSiteNum = towerCraneOnSiteNum;
    }

    public String getTonitoringEquipmentNum() {
        return tonitoringEquipmentNum;
    }

    public void setTonitoringEquipmentNum(String tonitoringEquipmentNum) {
        this.tonitoringEquipmentNum = tonitoringEquipmentNum;
    }

    public String getMonitorOnLineNum() {
        return monitorOnLineNum;
    }

    public void setMonitorOnLineNum(String monitorOnLineNum) {
        this.monitorOnLineNum = monitorOnLineNum;
    }

    public String getMonitorOnLineRate() {
        return monitorOnLineRate;
    }

    public void setMonitorOnLineRate(String monitorOnLineRate) {
        this.monitorOnLineRate = monitorOnLineRate;
    }

    public String getEquipmentViolationRate() {
        return equipmentViolationRate;
    }

    public void setEquipmentViolationRate(String equipmentViolationRate) {
        this.equipmentViolationRate = equipmentViolationRate;
    }

    public String getEquipmentHoistingNum() {
        return equipmentHoistingNum;
    }

    public void setEquipmentHoistingNum(String equipmentHoistingNum) {
        this.equipmentHoistingNum = equipmentHoistingNum;
    }

    public String getEquipmentViolationNum() {
        return equipmentViolationNum;
    }

    public void setEquipmentViolationNum(String equipmentViolationNum) {
        this.equipmentViolationNum = equipmentViolationNum;
    }

    public String getFaceNumber() {
        return faceNumber;
    }

    public void setFaceNumber(String faceNumber) {
        this.faceNumber = faceNumber;
    }
}
