package com.jlkj.project.ws.dto;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName WsTowerCraneEquipmentWorkDetailsDTO
 * @Description TODO
 * @Author gaowei
 * @Date 2020/7/1 18:30
 * @Version 1.0
 **/
@ApiModel("塔机监测-企业-工作详情DTO")
public class WsTowerCraneEquipmentWorkDetailsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("工作详情：监控设备编号")
    private String monitoringEquipmentNo;

    @ApiModelProperty("条件天数")
    private String days;

    @ApiModelProperty("条件时间日期")
    private String conditionsDate;

    /** 项目id */
    @ApiModelProperty("工作详情：项目id")
    private Integer projectId;

    @ApiModelProperty("工作详情：开始时间")
    @Excel(name = "开始时间")
    private String startTime;

    @ApiModelProperty("工作详情：设备名称")
    @Excel(name = "设备名称")
    private String equipmentName;

    @ApiModelProperty("工作详情：工作时长")
    @Excel(name = "工作时长")
    private String workingHours;

    @ApiModelProperty("工作详情：吊重")
    @Excel(name = "吊重")
    private String liftingWeight;

    @ApiModelProperty("工作详情：吊程")
    @Excel(name = "吊程")
    private String liftingDistance;

    public String getMonitoringEquipmentNo() {
        return monitoringEquipmentNo;
    }

    public void setMonitoringEquipmentNo(String monitoringEquipmentNo) {
        this.monitoringEquipmentNo = monitoringEquipmentNo;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getLiftingWeight() {
        return liftingWeight;
    }

    public void setLiftingWeight(String liftingWeight) {
        this.liftingWeight = liftingWeight;
    }

    public String getLiftingDistance() {
        return liftingDistance;
    }

    public void setLiftingDistance(String liftingDistance) {
        this.liftingDistance = liftingDistance;
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
}
