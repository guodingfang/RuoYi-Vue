package com.jlkj.project.ws.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 视频设备管理 对象 t_ws_video_equipment_management
 *
 * @author pyy
 * @date 2020-07-02
 */
@ApiModel("视频设备管理对象")
public class WsVideoEquipmentManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("主键id")
    private Integer id;

    /** 项目id */
    @ApiModelProperty("项目id")
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

    /** 设备类型 1：摄像仪，2：录像机 */
    @ApiModelProperty("设备类型 1：摄像仪，2：录像机")
    @Excel(name = "设备类型 1：摄像仪，2：录像机")
    private Integer equipmentType;

    /** 序列号 */
    @ApiModelProperty("序列号")
    @Excel(name = "序列号")
    private String equipmentSerialnumber;

    /** 验证码 */
    @ApiModelProperty("验证码")
    @Excel(name = "验证码")
    private String verificationCode;

    /** 安装位置 */
    @ApiModelProperty("安装位置")
    @Excel(name = "安装位置")
    private String installationPosition;

    /** 通道数 */
    @ApiModelProperty("通道数")
    @Excel(name = " 通道数")
    private Integer channels;

    /** 通道编号 */
    @ApiModelProperty("通道编号")
    @Excel(name = " 通道编号")
    private Integer channelNumber;

    /** 工程设备在线状态 1：在线，2：离线 */
    @ApiModelProperty("工程设备在线状态 1：在线，0：离线")
    @Excel(name = "工程设备在线状态 1：在线，0：离线")
    private String onlineStatus;

    /** 启用状态 1：启用，2：禁用 */
    @ApiModelProperty("启用状态 1：启用，2：禁用")
    @Excel(name = "启用状态 1：启用，2：禁用")
    private Integer enableStatus;

    /** 创建人 */
    @Excel(name = "创建人")
    private Integer createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private Integer updatedBy;

    private List<Integer> projectIds;


    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }
    public Integer getProjectId()
    {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setEquipmentSerialnumber(String equipmentSerialnumber)
    {
        this.equipmentSerialnumber = equipmentSerialnumber;
    }

    public Integer getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(Integer equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentSerialnumber()
    {
        return equipmentSerialnumber;
    }
    public void setVerificationCode(String verificationCode)
    {
        this.verificationCode = verificationCode;
    }

    public String getVerificationCode()
    {
        return verificationCode;
    }

    public String getInstallationPosition() {
        return installationPosition;
    }

    public void setInstallationPosition(String installationPosition) {
        this.installationPosition = installationPosition;
    }

    public Integer getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(Integer channelNumber) {
        this.channelNumber = channelNumber;
    }

    public Integer getChannels() {
        return channels;
    }

    public void setChannels(Integer channels) {
        this.channels = channels;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public void setCreatedBy(Integer createdBy)
    {
        this.createdBy = createdBy;
    }

    public Integer getCreatedBy()
    {
        return createdBy;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }
    public void setUpdatedBy(Integer updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    public Integer getUpdatedBy()
    {
        return updatedBy;
    }
    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }

    public List<Integer> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Integer> projectIds) {
        this.projectIds = projectIds;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("equipmentType", getEquipmentType())
            .append("equipmentSerialnumber", getEquipmentSerialnumber())
            .append("verificationCode", getVerificationCode())
            .append("equipmentName", getInstallationPosition())
            .append("channelType", getChannels())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
