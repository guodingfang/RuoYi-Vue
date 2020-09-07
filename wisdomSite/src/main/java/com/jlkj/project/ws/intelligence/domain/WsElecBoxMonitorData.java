package com.jlkj.project.ws.intelligence.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 电箱监测数据信息对象
 *
 * @author gaowei
 * @date 2020-07-15
 */
public class WsElecBoxMonitorData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("项目id")
    private Integer projectId;

    /** 电箱编号 */
    @ApiModelProperty("设备编号;电箱监测设备")
    private String monitoringEquipment;

    @ApiModelProperty("设备名称")
    private String equipmentName;

    /** 上报时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("上报时间(yyyy-MM-dd)")
    private String insertTime;

    /** U相电压 */
    @ApiModelProperty("A相电压")
    private String voltageU;

    /** V相电压 */
    @ApiModelProperty("B相电压")
    private String voltageV;

    /** W相电压 */
    @ApiModelProperty("C相电压")
    private String voltageW;

    /** U相电流 */
    @ApiModelProperty("A相电流")
    private String currentU;

    /** V相电流 */
    @ApiModelProperty("B相电流")
    private String currentV;

    /** W相电流 */
    @ApiModelProperty("C相电流")
    private String currentW;

    /** 有功功率 */
    @ApiModelProperty("有功功率")
    private String activePower;

    /** 无功功率 */
    @ApiModelProperty("无功功率")
    private String reactivePower;

    /** 有功电能 */
    @ApiModelProperty("有功电能")
    private String activeElectricity;

    /** 无功电能 */
    @ApiModelProperty("无功电能")
    private String reactiveElectricity;

    @ApiModelProperty("监测时间类型（1:近24小时；2:近3天；3：近7天）")
    private String monitorType;

    @ApiModelProperty("区域")
    private String region;

    @ApiModelProperty("总用电量")
    private String totalPower;

    @ApiModelProperty("能耗分析类型（1:按日；2:按月；3：按区域）")
    private String analysisType;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getMonitoringEquipment() {
        return monitoringEquipment;
    }

    public void setMonitoringEquipment(String monitoringEquipment) {
        this.monitoringEquipment = monitoringEquipment;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getVoltageU() {
        return voltageU;
    }

    public void setVoltageU(String voltageU) {
        this.voltageU = voltageU;
    }

    public String getVoltageV() {
        return voltageV;
    }

    public void setVoltageV(String voltageV) {
        this.voltageV = voltageV;
    }

    public String getVoltageW() {
        return voltageW;
    }

    public void setVoltageW(String voltageW) {
        this.voltageW = voltageW;
    }

    public String getCurrentU() {
        return currentU;
    }

    public void setCurrentU(String currentU) {
        this.currentU = currentU;
    }

    public String getCurrentV() {
        return currentV;
    }

    public void setCurrentV(String currentV) {
        this.currentV = currentV;
    }

    public String getCurrentW() {
        return currentW;
    }

    public void setCurrentW(String currentW) {
        this.currentW = currentW;
    }

    public String getActivePower() {
        return activePower;
    }

    public void setActivePower(String activePower) {
        this.activePower = activePower;
    }

    public String getReactivePower() {
        return reactivePower;
    }

    public void setReactivePower(String reactivePower) {
        this.reactivePower = reactivePower;
    }

    public String getActiveElectricity() {
        return activeElectricity;
    }

    public void setActiveElectricity(String activeElectricity) {
        this.activeElectricity = activeElectricity;
    }

    public String getReactiveElectricity() {
        return reactiveElectricity;
    }

    public void setReactiveElectricity(String reactiveElectricity) {
        this.reactiveElectricity = reactiveElectricity;
    }

    public String getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(String monitorType) {
        this.monitorType = monitorType;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(String totalPower) {
        this.totalPower = totalPower;
    }

    public String getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("insertTime", getInsertTime())
            .append("voltageU", getVoltageU())
            .append("voltageV", getVoltageV())
            .append("voltageW", getVoltageW())
            .append("currentU", getCurrentU())
            .append("currentV", getCurrentV())
            .append("currentW", getCurrentW())
            .append("activePower", getActivePower())
            .append("reactivePower", getReactivePower())
            .append("activeElectricity", getActiveElectricity())
            .append("reactiveElectricity", getReactiveElectricity())
            .toString();
    }
}
