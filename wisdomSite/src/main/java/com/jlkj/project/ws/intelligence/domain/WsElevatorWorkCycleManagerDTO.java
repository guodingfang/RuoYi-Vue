package com.jlkj.project.ws.intelligence.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName WsElevatorMangerDTO
 * @Description 升降机设备工作记录管理DTO
 * @Author liujie
 * @Date 2020/7/3 20:59
 **/
@ApiModel("施工电梯设备工作记录管理DTO")
public class WsElevatorWorkCycleManagerDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty("数据类型，1=工作记录")
    private Integer dataType;
    @ApiModelProperty("统计日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date statisticsDate;
    @ApiModelProperty("统计天数，负则向前统计，正向后统计")
    private Integer days;

    @ApiModelProperty("施工单位ID")
    private Integer constructionId;
    @ApiModelProperty("项目ID")
    private Integer projectId;

    @ApiModelProperty("设备编号")
    private String  serialNumber;

    @ApiModelProperty("施工电梯设备ID")
    private Integer  elevotorId;

    private Date beginTime;

    private Date endTime;

    @ApiModelProperty("设备名称")
    private  String deviceName;
    @ApiModelProperty("工程名称")
    private String projectName;

    @ApiModelProperty("备案编号")
    private String recordNumber;

    @ApiModelProperty("设备类型")
    private String deviceType;

    @ApiModelProperty("告警类型:1=预警，2=告警")
    private String alarmingType;

    private String[] serialNumbers;

    @ApiModelProperty("记录行数")
    private Integer rowNumber;


    @ApiModelProperty("企业Id")
    private Long companyId;

    @ApiModelProperty("关键字类型")
    private String keyType;

    @ApiModelProperty("关键字名称")
    private String keyWordName;

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getAlarmingType() {
        return alarmingType;
    }

    public void setAlarmingType(String alarmingType) {
        this.alarmingType = alarmingType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String[] getSerialNumbers() {
        return serialNumbers;
    }

    public void setSerialNumbers(String[] serialNumbers) {
        this.serialNumbers = serialNumbers;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Date getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(Date statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(Integer constructionId) {
        this.constructionId = constructionId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getElevotorId() {
        return elevotorId;
    }

    public void setElevotorId(Integer elevotorId) {
        this.elevotorId = elevotorId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getKeyWordName() {
        return keyWordName;
    }

    public void setKeyWordName(String keyWordName) {
        this.keyWordName = keyWordName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsElevatorWorkCycleMangerDTO{");
        sb.append("dataType=").append(dataType);
        sb.append(", statisticsDate=").append(statisticsDate);
        sb.append(", days=").append(days);
        sb.append(", constructionId=").append(constructionId);
        sb.append(", projectId=").append(projectId);
        sb.append(", serialNumber='").append(serialNumber).append('\'');
        sb.append(", elevotorId=").append(elevotorId);
        sb.append('}');
        return sb.toString();
    }
}
