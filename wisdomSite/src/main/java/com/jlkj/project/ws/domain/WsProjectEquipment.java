package com.jlkj.project.ws.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 工程设备绑定 对象 t_ws_project_equipment
 *
 * @author pyy
 * @date 2020-07-02
 */
@ApiModel("工程设备绑定对象")
public class WsProjectEquipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("主键id")
    private Integer id;

    /** 项目id */
    @ApiModelProperty(value = "项目id", required = true)
    @Excel(name = "项目id")
    private Integer projectId;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", required = true)
    @Excel(name = "项目名称")
    private String projectName;

    /** 归属公司名 */
    @ApiModelProperty(value = "归属公司名")
    @Excel(name = "归属公司名")
    private String enterpriseName;

    /** 设备编号 */
    @ApiModelProperty(value = "设备编号", required = true)
    @Excel(name = "设备编号")
    private String serialNumber;

    /** 设备类型 */
    @ApiModelProperty(value = "设备类型(1.塔机 2.升降机 3.环境 4.电箱 7.卸料 8.喷淋 9.塔机人脸 14.升降机人脸)", required = true)
    @Excel(name = "设备类型(1.塔机 2.升降机 3.环境 4.电箱 7.卸料 8.喷淋 9.塔机人脸 14.升降机人脸)")
    private Integer equipmentType;

    /** 安装位置 */
    @ApiModelProperty(value = "安装位置", required = true)
    @Excel(name = "安装位置")
    private String installationPosition;

    /** 流量卡号 */
    @ApiModelProperty("流量卡号")
    @Excel(name = "流量卡号")
    private String flowcardNo;

    /** 启用状态 1：启用，2：禁用 */
    @ApiModelProperty("启用状态 0：未启用 1：启用，2：禁用")
    @Excel(name = "启用状态 0：未启用 1：启用，2：禁用")
    private Integer enableStatus;

    /** 运行 1-在线，2-离线*/
    @ApiModelProperty("运行状态 1-在线，2-离线")
    private Integer onlineStatus;

    /** 创建人 */
    @ApiModelProperty("创建人")
    @Excel(name = "创建人")
    private Integer createdBy;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    @Excel(name = "更新人")
    private Integer updatedBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    private List<Integer> projectIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(Integer equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getInstallationPosition() {
        return installationPosition;
    }

    public void setInstallationPosition(String installationPosition) {
        this.installationPosition = installationPosition;
    }

    public String getFlowcardNo() {
        return flowcardNo;
    }

    public void setFlowcardNo(String flowcardNo) {
        this.flowcardNo = flowcardNo;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public List<Integer> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Integer> projectIds) {
        this.projectIds = projectIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("devicenumber", getSerialNumber())
            .append("equipmentType", getEquipmentType())
            .append("installationPosition", getInstallationPosition())
            .append("flowcardNo", getFlowcardNo())
            .append("enableStatus", getEnableStatus())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
