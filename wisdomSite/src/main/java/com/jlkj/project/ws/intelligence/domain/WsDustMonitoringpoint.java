package com.jlkj.project.ws.intelligence.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 扬尘监测点视频管理 对象 t_ws_dust_monitoringpoint
 *
 * @author gaowei
 * @date 2020-07-07
 */
public class WsDustMonitoringpoint extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("主键id")
    private Integer id;

    /** 项目id */
    @ApiModelProperty("项目id")
    private Integer projectId;

    /** 监测点名称 */
    @ApiModelProperty("监测点名称")
    private String monitoringpointName;

    /** 设备id */
    @ApiModelProperty("设备id")
    private Integer equipmentnoId;

    /** 视频id */
    @ApiModelProperty("视频id")
    private Integer videoEquipmentId;

    /** 安装日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("安装日期")
    private Date installData;

    /** 1启用，2禁用 */
    private String isDelete;

    /** 创建人 */
    private Integer createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    private Integer updatedBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedTime;

    private String serialNumber;//监测设备编号

    private String videoSerialNumber;//视频设备编号

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
    public void setMonitoringpointName(String monitoringpointName)
    {
        this.monitoringpointName = monitoringpointName;
    }

    public String getMonitoringpointName()
    {
        return monitoringpointName;
    }
    public void setEquipmentnoId(Integer equipmentnoId)
    {
        this.equipmentnoId = equipmentnoId;
    }

    public Integer getEquipmentnoId()
    {
        return equipmentnoId;
    }
    public void setVideoEquipmentId(Integer videoEquipmentId)
    {
        this.videoEquipmentId = videoEquipmentId;
    }

    public Integer getVideoEquipmentId()
    {
        return videoEquipmentId;
    }
    public void setInstallData(Date installData)
    {
        this.installData = installData;
    }

    public Date getInstallData()
    {
        return installData;
    }
    public void setIsDelete(String isDelete)
    {
        this.isDelete = isDelete;
    }

    public String getIsDelete()
    {
        return isDelete;
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getVideoSerialNumber() {
        return videoSerialNumber;
    }

    public void setVideoSerialNumber(String videoSerialNumber) {
        this.videoSerialNumber = videoSerialNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("monitoringpointName", getMonitoringpointName())
            .append("equipmentnoId", getEquipmentnoId())
            .append("videoEquipmentId", getVideoEquipmentId())
            .append("installData", getInstallData())
            .append("isDelete", getIsDelete())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
