package com.jlkj.project.ws.intelligence.domain.vo;

import com.jlkj.project.ws.domain.WsArea;
import com.jlkj.project.ws.domain.WsProject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 施工电梯设备管理VO t_ws_elevator_manager
 *
 * @author liujie
 * @date 2020-07-03
 */
@ApiModel("施工电梯设备统计VO")
public class WsElevatorManagerStatisticsVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 工程名称*/
    @ApiModelProperty("工程名称")
    private String projectName;

    /** 工程id*/
    @ApiModelProperty("工程id")
    private Integer projectId;

    /** 设备数量*/
    @ApiModelProperty("设备数量")
    private Integer deviceNumber;

    /** 双笼设备数量*/
    @ApiModelProperty("双笼设备数量")
    private Integer doubleDeviceNumber;

    /** 监控覆盖设备数量*/
    @ApiModelProperty("监控覆盖设备数量")
    private Integer coverDeviceNumber;

    /** 监控设备总数*/
    @ApiModelProperty("监控设备总数")
    private Integer totalMonitorDeviceNumber;

    /** 双笼监控设备数量*/
    @ApiModelProperty("双笼监控设备数量")
    private Integer doubleMonitorDeviceNumber;

    /** 在线设备数量*/
    @ApiModelProperty("在线设备数量")
    private Integer onlineMonitorDeviceNumber;

    /**统计日期*/
    private Date statisticsDate;


    private String serialNumber;

    private String areaCode;

    private String areaName;

    private Integer alarmingNumber;
    private Integer preAlarmingNumber;

    private Integer alarmingProjectNumber;


    private Integer projectNumber;

    @ApiModelProperty("设备编号集合")
    private String serialNumbers;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("经度")
    private Double longitude;

    @ApiModelProperty("纬度")
    private Double latitude;

    @ApiModelProperty("数据类型：1省级，2市级，3县区级，4项目")
    private String dataType;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getPreAlarmingNumber() {
        return preAlarmingNumber;
    }

    public void setPreAlarmingNumber(Integer preAlarmingNumber) {
        this.preAlarmingNumber = preAlarmingNumber;
    }

    public Integer getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(Integer projectNumber) {
        this.projectNumber = projectNumber;
    }

    public Integer getAlarmingNumber() {
        return alarmingNumber;
    }

    public void setAlarmingNumber(Integer alarmingNumber) {
        this.alarmingNumber = alarmingNumber;
    }

    public Integer getAlarmingProjectNumber() {
        return alarmingProjectNumber;
    }

    public void setAlarmingProjectNumber(Integer alarmingProjectNumber) {
        this.alarmingProjectNumber = alarmingProjectNumber;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getDoubleDeviceNumber() {
        return doubleDeviceNumber;
    }

    public void setDoubleDeviceNumber(Integer doubleDeviceNumber) {
        this.doubleDeviceNumber = doubleDeviceNumber;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCoverDeviceNumber() {
        return coverDeviceNumber;
    }

    public void setCoverDeviceNumber(Integer coverDeviceNumber) {
        this.coverDeviceNumber = coverDeviceNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(Date statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(Integer deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public Integer getTotalMonitorDeviceNumber() {
        return totalMonitorDeviceNumber;
    }

    public void setTotalMonitorDeviceNumber(Integer totalMonitorDeviceNumber) {
        this.totalMonitorDeviceNumber = totalMonitorDeviceNumber;
    }

    public Integer getDoubleMonitorDeviceNumber() {
        return doubleMonitorDeviceNumber;
    }

    public void setDoubleMonitorDeviceNumber(Integer doubleMonitorDeviceNumber) {
        this.doubleMonitorDeviceNumber = doubleMonitorDeviceNumber;
    }

    public Integer getOnlineMonitorDeviceNumber() {
        return onlineMonitorDeviceNumber;
    }

    public void setOnlineMonitorDeviceNumber(Integer onlineMonitorDeviceNumber) {
        this.onlineMonitorDeviceNumber = onlineMonitorDeviceNumber;
    }

    public String getSerialNumbers() {
        return serialNumbers;
    }

    public void setSerialNumbers(String serialNumbers) {
        this.serialNumbers = serialNumbers;
    }

    public WsElevatorManagerStatisticsVO() {
    }

    public WsElevatorManagerStatisticsVO(WsElevatorManagerVO wsElevatorManagerVO) {
        this.address= wsElevatorManagerVO.getProjectName();
        this.projectId = wsElevatorManagerVO.getProjectId();
        this.serialNumber =wsElevatorManagerVO.getSerialNumber();
    }

    public WsElevatorManagerStatisticsVO(WsArea wsArea) {
        this.areaCode = wsArea.getCode();
        this.address = wsArea.getName();
        this.latitude = wsArea.getLatitude();
        this.longitude = wsArea.getLongitude();
    }

    public WsElevatorManagerStatisticsVO(WsProject wsProject) {
        this.projectId = wsProject.getId();
        this.address = wsProject.getProjectName();
        this.latitude = wsProject.getLatitude();
        this.longitude = wsProject.getLongitude();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsElevatorManagerStatisticsVO{");
        sb.append("projectName='").append(projectName).append('\'');
        sb.append(", projectId=").append(projectId);
        sb.append(", deviceNumber=").append(deviceNumber);
        sb.append(", doubleDeviceNumber=").append(doubleDeviceNumber);
        sb.append(", coverDeviceNumber=").append(coverDeviceNumber);
        sb.append(", totalMonitorDeviceNumber=").append(totalMonitorDeviceNumber);
        sb.append(", doubleMonitorDeviceNumber=").append(doubleMonitorDeviceNumber);
        sb.append(", onlineMonitorDeviceNumber=").append(onlineMonitorDeviceNumber);
        sb.append(", statisticsDate=").append(statisticsDate);
        sb.append(", serialNumber='").append(serialNumber).append('\'');
        sb.append(", areaCode='").append(areaCode).append('\'');
        sb.append(", areaName='").append(areaName).append('\'');
        sb.append(", alarmingNumber=").append(alarmingNumber);
        sb.append(", preAlarmingNumber=").append(preAlarmingNumber);
        sb.append(", alarmingProjectNumber=").append(alarmingProjectNumber);
        sb.append(", projectNumber=").append(projectNumber);
        sb.append(", serialNumbers='").append(serialNumbers).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", dataType='").append(dataType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
