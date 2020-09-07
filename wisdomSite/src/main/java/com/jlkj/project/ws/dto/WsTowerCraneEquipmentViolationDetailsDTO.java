package com.jlkj.project.ws.dto;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName WsTowerCraneEquipmentViolationDetailsDTO
 * @Description TODO
 * @Author gaowei
 * @Date 2020/7/1 15:00
 * @Version 1.0
 **/
@ApiModel("塔机监测-企业-违章详情DTO")
public class WsTowerCraneEquipmentViolationDetailsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("条件公司id")
    private Integer companyId;

    @ApiModelProperty("监控设备编号")
    private String monitoringEquipmentNo;

    /** 项目id */
    @ApiModelProperty("项目id")
    private Integer projectId;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("违章详情：违章时间")
    @Excel(name = "违章时间")
    private Date violationTime;

    @ApiModelProperty("违章详情：违章内容")
    @Excel(name = "违章内容")
    private String violationContent;

    @ApiModelProperty("违章详情：项目名称")
    @Excel(name = "项目名称")
    private String projectName;

    @ApiModelProperty("违章详情：产权备案号")
    @Excel(name = "产权备案号")
    private String propertyRightRecordNo;

    @ApiModelProperty("违章详情：设备名称")
    @Excel(name = "设备名称")
    private String equipmentName;

    @ApiModelProperty("违章详情：监控设备")
    @Excel(name = "监控设备")
    private String monitoringEquipment;

    @ApiModelProperty("违章详情：所属公司")
    @Excel(name = "所属公司")
    private String companyName;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getViolationTime() {
        return violationTime;
    }

    public void setViolationTime(Date violationTime) {
        this.violationTime = violationTime;
    }

    public String getViolationContent() {
        return violationContent;
    }

    public void setViolationContent(String violationContent) {
        this.violationContent = violationContent;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPropertyRightRecordNo() {
        return propertyRightRecordNo;
    }

    public void setPropertyRightRecordNo(String propertyRightRecordNo) {
        this.propertyRightRecordNo = propertyRightRecordNo;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getMonitoringEquipment() {
        return monitoringEquipment;
    }

    public void setMonitoringEquipment(String monitoringEquipment) {
        this.monitoringEquipment = monitoringEquipment;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMonitoringEquipmentNo() {
        return monitoringEquipmentNo;
    }

    public void setMonitoringEquipmentNo(String monitoringEquipmentNo) {
        this.monitoringEquipmentNo = monitoringEquipmentNo;
    }
}
