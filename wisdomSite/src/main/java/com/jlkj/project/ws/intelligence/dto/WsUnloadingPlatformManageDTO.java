package com.jlkj.project.ws.intelligence.dto;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName WsUnloadingPlatformManageDTO
 * @Description TODO
 * @Author gaowei
 * @Date 2020/7/13 11:30
 * @Version 1.0
 **/
@ApiModel("卸料平台-实时监控")
public class WsUnloadingPlatformManageDTO extends BaseEntity {

    @ApiModelProperty("项目id")
    private Integer projectId;

    @ApiModelProperty("实时监控：在场设备数")
    private int equipmentNum;

    @ApiModelProperty("实时监控：当前设备空闲率")
    private String equipmentIdleRate;

    @ApiModelProperty("实时监控：当前报警设备数")
    private String callPoliceEquipmentNum;

    @ApiModelProperty("实时监控：设备名称")
    private String equipmentName;

    @ApiModelProperty("实时监控：监测设备编号")
    private String monitoringEquipmentNo;

    @ApiModelProperty("实时监控：重量")
    private String weight;

    @ApiModelProperty("实时监控：电压")
    private String voltage;

    @ApiModelProperty("实时监控：预报警状态（0：正常，1：预警，2：报警）")
    private String warningStatus;

    @ApiModelProperty("实时监控：场布图地址")
    private String fieldlayoutAddress;

    @ApiModelProperty("监控详情设备信息：设备名称")
    private String unloadingEquipmentName;

    @ApiModelProperty("监控详情设备信息：监测设备编号")
    private String unloadingMonitoringEquipmentNo;

    @ApiModelProperty("监控详情实时数据：更新时间")
    private String realUpdateTime;

    @ApiModelProperty("监控详情实时数据：当前载重")
    private String currentLoad;

    @ApiModelProperty("监控详情实时数据：最大允许载重")
    private String maxAllowableLoad;

    @ApiModelProperty("监控详情实时数据：电量百分比")
    private String batteryPercentage;

    @ApiModelProperty("监控详情工作记录：载重")
    private String loadweight;

    @ApiModelProperty("监控详情工作记录：工作记录日期时间")
    private String workRecordTime;

    @ApiModelProperty("监控详情告警记录：告警记录日期时间")
    private String alarmTime;

    @ApiModelProperty("监控详情告警记录：预警次数")
    private String earlyWarningNum;

    @ApiModelProperty("监控详情告警记录：报警次数")
    private String callPoliceNum;

    @ApiModelProperty("监控详情告警记录：未报警数")
    private String totalNum;


    @ApiModelProperty("更多详情历史数据：开始时间")
    private String startTime;

    @ApiModelProperty("更多详情历史数据：结束时间")
    private String endTime;

    @ApiModelProperty("更多详情历史数据：时间")
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    private String reportingTime;

    @ApiModelProperty("更多详情历史数据：当前载重")
    @Excel(name = "当前载重")
    private String presentLoad;

    @ApiModelProperty("更多详情历史数据：载重百分比")
    @Excel(name = "载重百分比")
    private String percentageLoad;

    @ApiModelProperty("更多详情历史数据：工作状态")
    @Excel(name = "工作状态")
    private String workingCondition;

    @ApiModelProperty("更多详情历史数据：报警状态")
    @Excel(name = "报警状态")
    private String alarmStatus;

    @ApiModelProperty("启禁用状态 1：启用；2：禁用")
    private Integer enableDisable;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public int getEquipmentNum() {
        return equipmentNum;
    }

    public void setEquipmentNum(int equipmentNum) {
        this.equipmentNum = equipmentNum;
    }

    public String getEquipmentIdleRate() {
        return equipmentIdleRate;
    }

    public void setEquipmentIdleRate(String equipmentIdleRate) {
        this.equipmentIdleRate = equipmentIdleRate;
    }

    public String getCallPoliceEquipmentNum() {
        return callPoliceEquipmentNum;
    }

    public void setCallPoliceEquipmentNum(String callPoliceEquipmentNum) {
        this.callPoliceEquipmentNum = callPoliceEquipmentNum;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getMonitoringEquipmentNo() {
        return monitoringEquipmentNo;
    }

    public void setMonitoringEquipmentNo(String monitoringEquipmentNo) {
        this.monitoringEquipmentNo = monitoringEquipmentNo;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getWarningStatus() {
        return warningStatus;
    }

    public void setWarningStatus(String warningStatus) {
        this.warningStatus = warningStatus;
    }

    public String getFieldlayoutAddress() {
        return fieldlayoutAddress;
    }

    public void setFieldlayoutAddress(String fieldlayoutAddress) {
        this.fieldlayoutAddress = fieldlayoutAddress;
    }

    public String getUnloadingEquipmentName() {
        return unloadingEquipmentName;
    }

    public void setUnloadingEquipmentName(String unloadingEquipmentName) {
        this.unloadingEquipmentName = unloadingEquipmentName;
    }

    public String getUnloadingMonitoringEquipmentNo() {
        return unloadingMonitoringEquipmentNo;
    }

    public void setUnloadingMonitoringEquipmentNo(String unloadingMonitoringEquipmentNo) {
        this.unloadingMonitoringEquipmentNo = unloadingMonitoringEquipmentNo;
    }

    public String getRealUpdateTime() {
        return realUpdateTime;
    }

    public void setRealUpdateTime(String realUpdateTime) {
        this.realUpdateTime = realUpdateTime;
    }

    public String getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(String currentLoad) {
        this.currentLoad = currentLoad;
    }

    public String getMaxAllowableLoad() {
        return maxAllowableLoad;
    }

    public void setMaxAllowableLoad(String maxAllowableLoad) {
        this.maxAllowableLoad = maxAllowableLoad;
    }

    public String getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(String batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public String getLoadweight() {
        return loadweight;
    }

    public void setLoadweight(String loadweight) {
        this.loadweight = loadweight;
    }

    public String getWorkRecordTime() {
        return workRecordTime;
    }

    public void setWorkRecordTime(String workRecordTime) {
        this.workRecordTime = workRecordTime;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getEarlyWarningNum() {
        return earlyWarningNum;
    }

    public void setEarlyWarningNum(String earlyWarningNum) {
        this.earlyWarningNum = earlyWarningNum;
    }

    public String getCallPoliceNum() {
        return callPoliceNum;
    }

    public void setCallPoliceNum(String callPoliceNum) {
        this.callPoliceNum = callPoliceNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public String getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getReportingTime() {
        return reportingTime;
    }

    public void setReportingTime(String reportingTime) {
        this.reportingTime = reportingTime;
    }

    public String getPresentLoad() {
        return presentLoad;
    }

    public void setPresentLoad(String presentLoad) {
        this.presentLoad = presentLoad;
    }

    public String getPercentageLoad() {
        return percentageLoad;
    }

    public void setPercentageLoad(String percentageLoad) {
        this.percentageLoad = percentageLoad;
    }

    public String getWorkingCondition() {
        return workingCondition;
    }

    public void setWorkingCondition(String workingCondition) {
        this.workingCondition = workingCondition;
    }

    public String getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(String alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getEnableDisable() {
        return enableDisable;
    }

    public void setEnableDisable(Integer enableDisable) {
        this.enableDisable = enableDisable;
    }
}
