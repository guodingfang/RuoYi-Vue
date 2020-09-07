package com.jlkj.project.ws.intelligence.domain;

import java.beans.Transient;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 电能监测设备管理 对象 t_ws_electricity_monitor_manage
 *
 * @author gaowei
 * @date 2020-07-14
 */
public class WsElectricityMonitorManage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("主键id")
    private Integer id;

    /** 项目id */
    @ApiModelProperty("项目id")
    private Integer projectId;

    /** 设备名称 */
    @ApiModelProperty("设备名称")
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 监测设备编号 */
    @ApiModelProperty("监测设备编号")
    @Excel(name = "监测设备编号")
    private String monitoringEquipment;

    /** 安装区域 */
    @ApiModelProperty("安装区域")
    @Excel(name = "安装区域")
    private Integer installationArea;

    private String installationAreaName;

    /** 是否总表 1：是；2：否 */
    @ApiModelProperty("是否总表 1：是；2：否")
    @Excel(name = "是否总表 1：是；2：否")
    private Integer whether;

    /** 启禁用状态 1：启用；2：禁用 */
    @ApiModelProperty("启禁用状态 1：启用；2：禁用")
    @Excel(name = "启禁用状态 1：启用；2：禁用")
    private Integer enableDisable;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    @ApiModelProperty("监测状态")
    private String monitoringStatus;

    @ApiModelProperty("今日电量")
    private String todayElectricity;

    @ApiModelProperty("本月电量")
    private String monthElectricity;

    @ApiModelProperty("用电详情：统计周期")
    private String statisticalPeriod;

    @ApiModelProperty("用电详情：时间段电量")
    private String electricityQuantity;

    @ApiModelProperty("用电详情：时间段")
    private String timeSlot;

    @ApiModelProperty("用电详情：起码电量")
    private String initialCharge;

    @ApiModelProperty("用电详情：止码电量")
    private String stopCodePower;

    @ApiModelProperty("用电详情：查询类型（1：按日；2：按月）")
    private String queryType;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setEquipmentName(String equipmentName)
    {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName()
    {
        return equipmentName;
    }
    public void setMonitoringEquipment(String monitoringEquipment)
    {
        this.monitoringEquipment = monitoringEquipment;
    }

    public String getMonitoringEquipment()
    {
        return monitoringEquipment;
    }
    public void setInstallationArea(Integer installationArea)
    {
        this.installationArea = installationArea;
    }

    public Integer getInstallationArea()
    {
        return installationArea;
    }
    public void setWhether(Integer whether)
    {
        this.whether = whether;
    }

    public Integer getWhether()
    {
        return whether;
    }
    public void setEnableDisable(Integer enableDisable)
    {
        this.enableDisable = enableDisable;
    }

    public Integer getEnableDisable()
    {
        return enableDisable;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }
    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getMonitoringStatus() {
        return monitoringStatus;
    }

    public void setMonitoringStatus(String monitoringStatus) {
        this.monitoringStatus = monitoringStatus;
    }

    public String getTodayElectricity() {
        return todayElectricity;
    }

    public void setTodayElectricity(String todayElectricity) {
        this.todayElectricity = todayElectricity;
    }

    public String getMonthElectricity() {
        return monthElectricity;
    }

    public void setMonthElectricity(String monthElectricity) {
        this.monthElectricity = monthElectricity;
    }

    public String getStatisticalPeriod() {
        return statisticalPeriod;
    }

    public void setStatisticalPeriod(String statisticalPeriod) {
        this.statisticalPeriod = statisticalPeriod;
    }

    public String getElectricityQuantity() {
        return electricityQuantity;
    }

    public void setElectricityQuantity(String electricityQuantity) {
        this.electricityQuantity = electricityQuantity;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getInitialCharge() {
        return initialCharge;
    }

    public void setInitialCharge(String initialCharge) {
        this.initialCharge = initialCharge;
    }

    public String getStopCodePower() {
        return stopCodePower;
    }

    public void setStopCodePower(String stopCodePower) {
        this.stopCodePower = stopCodePower;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getInstallationAreaName() {
        return installationAreaName;
    }

    public void setInstallationAreaName(String installationAreaName) {
        this.installationAreaName = installationAreaName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
                .append("projectId", getProjectId())
            .append("equipmentName", getEquipmentName())
            .append("monitoringEquipment", getMonitoringEquipment())
            .append("installationArea", getInstallationArea())
            .append("whether", getWhether())
            .append("enableDisable", getEnableDisable())
            .append("createdTime", getCreatedTime())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
