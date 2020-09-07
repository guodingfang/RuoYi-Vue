package com.jlkj.project.ws.intelligence.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 卸料平台设备管理 对象 t_ws_unloading_platform_manage
 *
 * @author gaowei
 * @date 2020-07-13
 */
public class WsUnloadingPlatformManage extends BaseEntity
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
    private String equipmentName;

    /** 监控设备编号 */
    @ApiModelProperty("监控设备编号")
    private String deviceNumber;

    /** 坐标x */
    @ApiModelProperty("坐标x")
    private String coordinateX;

    /** 坐标y */
    @ApiModelProperty("坐标y")
    private String coordinateY;

    /** 半径 */
    @ApiModelProperty("半径")
    private String radius;


    /** 启禁用状态 1：启用；2：禁用 */
    @ApiModelProperty("启禁用状态 1：启用；2：禁用")
    private Integer enableDisable;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedTime;

    @ApiModelProperty("场布图地址")
    private String fieldlayoutAddress;

    @ApiModelProperty("场布图ID")
    private Integer fieldlayoutId;

    /** 卸料设备位置集合 */
    @ApiModelProperty("卸料设备位置对象集合")
    private List<WsUnloadingPlatformManage> wsUnloadingPlatformManageList;

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
    public void setDeviceNumber(String deviceNumber)
    {
        this.deviceNumber = deviceNumber;
    }

    public String getDeviceNumber()
    {
        return deviceNumber;
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

    public String getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(String coordinateX) {
        this.coordinateX = coordinateX;
    }

    public String getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(String coordinateY) {
        this.coordinateY = coordinateY;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getFieldlayoutAddress() {
        return fieldlayoutAddress;
    }

    public void setFieldlayoutAddress(String fieldlayoutAddress) {
        this.fieldlayoutAddress = fieldlayoutAddress;
    }

    public Integer getFieldlayoutId() {
        return fieldlayoutId;
    }

    public void setFieldlayoutId(Integer fieldlayoutId) {
        this.fieldlayoutId = fieldlayoutId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<WsUnloadingPlatformManage> getWsUnloadingPlatformManageList() {
        return wsUnloadingPlatformManageList;
    }

    public void setWsUnloadingPlatformManageList(List<WsUnloadingPlatformManage> wsUnloadingPlatformManageList) {
        this.wsUnloadingPlatformManageList = wsUnloadingPlatformManageList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
                .append("projectId", getProjectId())
            .append("equipmentName", getEquipmentName())
            .append("deviceNumber", getDeviceNumber())
            .append("coordinateX", getCoordinateX())
            .append("coordinateY", getCoordinateY())
            .append("radius", getRadius())
            .append("enableDisable", getEnableDisable())
            .append("createdTime", getCreatedTime())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
