package com.jlkj.project.ws.dto;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName WsTowerCraneEquipmentDetailsDTO
 * @Description TODO
 * @Author gaowei
 * @Date 2020/7/1 11:00
 * @Version 1.0
 **/
@ApiModel("塔机监测-企业-设备详情DTO")
public class WsTowerCraneEquipmentDetailsDTO extends BaseEntity {

    @ApiModelProperty("条件公司id")
    private Integer companyId;

    /** 项目id */
    @ApiModelProperty("项目id")
    private Integer projectId;

    @ApiModelProperty("设备详情：所属公司/项目")
    @Excel(name = "所属公司/项目")
    private String companyOrprojectName;

    @ApiModelProperty("设备详情：塔吊设备数")
    @Excel(name = "塔吊设备数")
    private String towerCraneEquipmentNum;

    @ApiModelProperty("设备详情：塔吊监控设备数")
    @Excel(name = "监控设备数")
    private String towerCraneMonitoringEquipmentNum;

    @ApiModelProperty("设备详情：监控在线数")
    @Excel(name = "监控在线数")
    private String monitoringOnlineNum;

    @ApiModelProperty("设备详情：监控在线率")
    @Excel(name = "监控在线率")
    private String monitoringOnlineRate;

    public String getCompanyOrprojectName() {
        return companyOrprojectName;
    }

    public void setCompanyOrprojectName(String companyOrprojectName) {
        this.companyOrprojectName = companyOrprojectName;
    }

    public String getTowerCraneEquipmentNum() {
        return towerCraneEquipmentNum;
    }

    public void setTowerCraneEquipmentNum(String towerCraneEquipmentNum) {
        this.towerCraneEquipmentNum = towerCraneEquipmentNum;
    }

    public String getTowerCraneMonitoringEquipmentNum() {
        return towerCraneMonitoringEquipmentNum;
    }

    public void setTowerCraneMonitoringEquipmentNum(String towerCraneMonitoringEquipmentNum) {
        this.towerCraneMonitoringEquipmentNum = towerCraneMonitoringEquipmentNum;
    }

    public String getMonitoringOnlineNum() {
        return monitoringOnlineNum;
    }

    public void setMonitoringOnlineNum(String monitoringOnlineNum) {
        this.monitoringOnlineNum = monitoringOnlineNum;
    }

    public String getMonitoringOnlineRate() {
        return monitoringOnlineRate;
    }

    public void setMonitoringOnlineRate(String monitoringOnlineRate) {
        this.monitoringOnlineRate = monitoringOnlineRate;
    }

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
}
