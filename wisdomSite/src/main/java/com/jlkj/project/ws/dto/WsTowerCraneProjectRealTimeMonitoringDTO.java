package com.jlkj.project.ws.dto;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName WsTowerCraneProjectRealTimeMonitoringDTO
 * @Description TODO
 * @Author gaowei
 * @Date 2020/7/2 11:47
 * @Version 1.0
 **/
@ApiModel("塔机监测-项目-实时监控DTO")
public class WsTowerCraneProjectRealTimeMonitoringDTO extends BaseEntity {

    @ApiModelProperty("塔吊id")
    private Integer towerCraneId;

    /** 项目id */
    @ApiModelProperty("项目id")
    private Integer projectId;

    @ApiModelProperty("在场塔吊数")
    private String towerCraneOnSiteNum;

    /** 监控在线率 */
    @ApiModelProperty("监控在线率")
    private String monitoringOnlineRate;

    /** 今日违章 */
    @ApiModelProperty("今日违章")
    private String violationTodayNum;

    /** 今日违章 */
    @ApiModelProperty("违章率")
    private String violationRate;

    /** 监控在线数 */
    private String monitoringOnlineNum;

    /*实时监控*/

    /** 监控设备编号 */
    @ApiModelProperty("监控设备编号")
    private String monitoringEquipmentNo;

    /** 设备名称 */
    @ApiModelProperty("设备名称")
    private String towerCraneName;

    /** 今日吊次 */
    @ApiModelProperty("单设备-今日吊次")
    private String hoistingToday;

    /** 今日总违章 */
    @ApiModelProperty("单设备-今日总违章")
    private String totalViolationToday;

    /** 吊重 */
    @ApiModelProperty("单设备-吊重")
    private String liftingWeight;

    /** 塔机司机照片 */
    @ApiModelProperty("单设备-塔机司机照片地址")
    private String photoAddress;

    /** 预警状态 */
    @ApiModelProperty("单设备-预警状态(0:正常，1：预警)")
    private String warningStatus;

    /** 报警状态 */
    @ApiModelProperty("单设备-报警状态(0：正常，1：报警)")
    private String alarmStatus;

    /** 场布图 */
    @ApiModelProperty("场布图")
    private String fieldLayout;

    /** 设备坐标x */
    @ApiModelProperty("单设备-设备坐标x")
    private String coordinateX;

    /** 设备坐标y */
    @ApiModelProperty("单设备-设备坐标y")
    private String coordinateY;

    /** 监控在线状态 */
    @ApiModelProperty("单设备-监控在线状态")
    private String monitoringOnlineStatus;

    /** 设备在场状态 */
    @ApiModelProperty("单设备-设备在场状态")
    private String equipmentSiteStatus;

    private String faceId;//塔机人脸id

    private String faceNo;//塔机人脸编号

    private String idCardNo;//塔机司机身份证号

    private String identificationTime;//

    @ApiModelProperty("条件天数")
    private String days;

    @ApiModelProperty("条件日期时间")
    private String conditionsDate;

    /*塔机监控-项目 - 违章详情*/
    @ApiModelProperty("违章详情-违章时间")
    @Excel(name = "违章时间")
    private String violationTime;

    @ApiModelProperty("违章详情-违章类型")
    @Excel(name = "违章类型")
    private String violationType;

    @ApiModelProperty("违章详情-违章内容")
    @Excel(name = "违章内容")
    private String violationContent;

    @ApiModelProperty("违章详情-设备名称")
    @Excel(name = "设备名称")
    private String equipmentName;

    @ApiModelProperty("违章详情-司机姓名")
    @Excel(name = "司机姓名")
    private String driverName;


    public String getLiftingWeight() {
        return liftingWeight;
    }

    public void setLiftingWeight(String liftingWeight) {
        this.liftingWeight = liftingWeight;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getTowerCraneOnSiteNum() {
        return towerCraneOnSiteNum;
    }

    public void setTowerCraneOnSiteNum(String towerCraneOnSiteNum) {
        this.towerCraneOnSiteNum = towerCraneOnSiteNum;
    }

    public String getMonitoringOnlineRate() {
        return monitoringOnlineRate;
    }

    public void setMonitoringOnlineRate(String monitoringOnlineRate) {
        this.monitoringOnlineRate = monitoringOnlineRate;
    }

    public String getViolationTodayNum() {
        return violationTodayNum;
    }

    public void setViolationTodayNum(String violationTodayNum) {
        this.violationTodayNum = violationTodayNum;
    }

    public String getTowerCraneName() {
        return towerCraneName;
    }

    public void setTowerCraneName(String towerCraneName) {
        this.towerCraneName = towerCraneName;
    }

    public String getTotalViolationToday() {
        return totalViolationToday;
    }

    public void setTotalViolationToday(String totalViolationToday) {
        this.totalViolationToday = totalViolationToday;
    }

    public String getPhotoAddress() {
        return photoAddress;
    }

    public void setPhotoAddress(String photoAddress) {
        this.photoAddress = photoAddress;
    }

    public String getWarningStatus() {
        return warningStatus;
    }

    public void setWarningStatus(String warningStatus) {
        this.warningStatus = warningStatus;
    }

    public String getFieldLayout() {
        return fieldLayout;
    }

    public void setFieldLayout(String fieldLayout) {
        this.fieldLayout = fieldLayout;
    }

    public String getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(String coordinateX) {
        this.coordinateX = coordinateX;
    }

    public String getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(String coordinateY) {
        this.coordinateY = coordinateY;
    }

    public String getMonitoringOnlineStatus() {
        return monitoringOnlineStatus;
    }

    public void setMonitoringOnlineStatus(String monitoringOnlineStatus) {
        this.monitoringOnlineStatus = monitoringOnlineStatus;
    }

    public String getMonitoringEquipmentNo() {
        return monitoringEquipmentNo;
    }

    public void setMonitoringEquipmentNo(String monitoringEquipmentNo) {
        this.monitoringEquipmentNo = monitoringEquipmentNo;
    }

    public String getMonitoringOnlineNum() {
        return monitoringOnlineNum;
    }

    public void setMonitoringOnlineNum(String monitoringOnlineNum) {
        this.monitoringOnlineNum = monitoringOnlineNum;
    }

    public String getEquipmentSiteStatus() {
        return equipmentSiteStatus;
    }

    public void setEquipmentSiteStatus(String equipmentSiteStatus) {
        this.equipmentSiteStatus = equipmentSiteStatus;
    }

    public String getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(String alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getFaceNo() {
        return faceNo;
    }

    public void setFaceNo(String faceNo) {
        this.faceNo = faceNo;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public Integer getTowerCraneId() {
        return towerCraneId;
    }

    public void setTowerCraneId(Integer towerCraneId) {
        this.towerCraneId = towerCraneId;
    }

    public String getIdentificationTime() {
        return identificationTime;
    }

    public void setIdentificationTime(String identificationTime) {
        this.identificationTime = identificationTime;
    }

    public String getViolationTime() {
        return violationTime;
    }

    public void setViolationTime(String violationTime) {
        this.violationTime = violationTime;
    }

    public String getViolationContent() {
        return violationContent;
    }

    public void setViolationContent(String violationContent) {
        this.violationContent = violationContent;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getViolationType() {
        return violationType;
    }

    public void setViolationType(String violationType) {
        this.violationType = violationType;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getConditionsDate() {
        return conditionsDate;
    }

    public void setConditionsDate(String conditionsDate) {
        this.conditionsDate = conditionsDate;
    }

    public String getViolationRate() {
        return violationRate;
    }

    public void setViolationRate(String violationRate) {
        this.violationRate = violationRate;
    }

    public String getHoistingToday() {
        return hoistingToday;
    }

    public void setHoistingToday(String hoistingToday) {
        this.hoistingToday = hoistingToday;
    }
}
