package com.jlkj.project.ws.intelligence.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.web.domain.BaseEntity;
import com.jlkj.project.ws.domain.WsProjectMilestoneplan;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 项目塔吊设备管理 对象 t_ws_towercrane_equipment
 *
 * @author gaowei
 * @date 2020-07-07
 */
@ApiModel("项目塔吊设备管理实体类")
public class WsTowercraneEquipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("主键id")
    private Integer id;

    /** 自编号 */
    @ApiModelProperty("自编号")
    private String selfNumbering;

    /** 项目id */
    @ApiModelProperty("项目id")
    private Integer projectId;

    /** 产权备案号 */
    @ApiModelProperty("产权备案号")
    private String propertyRecordno;

    /** 设备名称 */
    @ApiModelProperty("设备名称")
    private String equipmentName;

    /** 进场日期 */
    @ApiModelProperty("进场日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date enterData;

    /** 出场日期 */
    @ApiModelProperty("出场日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date exitData;

    /** 监测设备 */
    @ApiModelProperty("监测设备")
    private String monitoringEquipment;

    /** 吊钩视频 */
    @ApiModelProperty("吊钩视频id")
    private Integer cliverVideoId;

    /** 塔机人脸 */
    @ApiModelProperty("塔机人脸")
    private Integer faceId;

    /** 产权单位 */
    @ApiModelProperty("产权单位")
    private String propertyUnit;

    /** 安装单位 */
    @ApiModelProperty("安装单位")
    private String installUnit;

    /** 生产单位 */
    @ApiModelProperty("生产单位")
    private String productionUnit;

    /** 规格型号 */
    @ApiModelProperty("规格型号")
    private String specificationsModel;

    /** 在场状态(1:是，2;否) */
    @ApiModelProperty("在场状态(1:是，2;否)")
    private Integer presence;

    /** 坐标x */
    @ApiModelProperty("坐标x")
    private String coordinateX;

    /** 坐标y */
    @ApiModelProperty("坐标y")
    private String coordinateY;

    /** 半径 */
    @ApiModelProperty("半径")
    private String radius;

    /** 是否删除 Y（删除），N(不删除) */
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

    @ApiModelProperty("塔机人脸编号")
    private String faceNumber;

    @ApiModelProperty("视频设备编号")
    private String videoEquipmentNo;

    @ApiModelProperty("场布图地址")
    private String fieldlayoutAddress;

    @ApiModelProperty("场布图ID")
    private Integer fieldlayoutId;

    /** 塔吊设备位置集合 */
    @ApiModelProperty("塔吊设备位置对象集合")
    private List<WsTowercraneEquipment> wsTowercraneEquipmentList;


    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setSelfNumbering(String selfNumbering)
    {
        this.selfNumbering = selfNumbering;
    }

    public String getSelfNumbering()
    {
        return selfNumbering;
    }
    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }

    public Integer getProjectId()
    {
        return projectId;
    }
    public void setPropertyRecordno(String propertyRecordno)
    {
        this.propertyRecordno = propertyRecordno;
    }

    public String getPropertyRecordno()
    {
        return propertyRecordno;
    }
    public void setEquipmentName(String equipmentName)
    {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName()
    {
        return equipmentName;
    }
    public void setEnterData(Date enterData)
    {
        this.enterData = enterData;
    }

    public Date getEnterData()
    {
        return enterData;
    }
    public void setExitData(Date exitData)
    {
        this.exitData = exitData;
    }

    public Date getExitData()
    {
        return exitData;
    }
    public void setMonitoringEquipment(String monitoringEquipment)
    {
        this.monitoringEquipment = monitoringEquipment;
    }

    public String getMonitoringEquipment()
    {
        return monitoringEquipment;
    }
    public void setCliverVideoId(Integer cliverVideoId)
    {
        this.cliverVideoId = cliverVideoId;
    }

    public Integer getCliverVideoId()
    {
        return cliverVideoId;
    }
    public void setFaceId(Integer faceId)
    {
        this.faceId = faceId;
    }

    public Integer getFaceId()
    {
        return faceId;
    }
    public void setPropertyUnit(String propertyUnit)
    {
        this.propertyUnit = propertyUnit;
    }

    public String getPropertyUnit()
    {
        return propertyUnit;
    }
    public void setInstallUnit(String installUnit)
    {
        this.installUnit = installUnit;
    }

    public String getInstallUnit()
    {
        return installUnit;
    }
    public void setProductionUnit(String productionUnit)
    {
        this.productionUnit = productionUnit;
    }

    public String getProductionUnit()
    {
        return productionUnit;
    }
    public void setSpecificationsModel(String specificationsModel)
    {
        this.specificationsModel = specificationsModel;
    }

    public String getSpecificationsModel()
    {
        return specificationsModel;
    }
    public void setPresence(Integer presence)
    {
        this.presence = presence;
    }

    public Integer getPresence()
    {
        return presence;
    }
    public void setCoordinateX(String coordinateX)
    {
        this.coordinateX = coordinateX;
    }

    public String getCoordinateX()
    {
        return coordinateX;
    }
    public void setCoordinateY(String coordinateY)
    {
        this.coordinateY = coordinateY;
    }

    public String getCoordinateY()
    {
        return coordinateY;
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

    public String getFaceNumber() {
        return faceNumber;
    }

    public void setFaceNumber(String faceNumber) {
        this.faceNumber = faceNumber;
    }

    public String getVideoEquipmentNo() {
        return videoEquipmentNo;
    }

    public void setVideoEquipmentNo(String videoEquipmentNo) {
        this.videoEquipmentNo = videoEquipmentNo;
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

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public List<WsTowercraneEquipment> getWsTowercraneEquipmentList() {
        return wsTowercraneEquipmentList;
    }

    public void setWsTowercraneEquipmentList(List<WsTowercraneEquipment> wsTowercraneEquipmentList) {
        this.wsTowercraneEquipmentList = wsTowercraneEquipmentList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("selfNumbering", getSelfNumbering())
            .append("projectId", getProjectId())
            .append("propertyRecordno", getPropertyRecordno())
            .append("equipmentName", getEquipmentName())
            .append("enterData", getEnterData())
            .append("exitData", getExitData())
            .append("monitoringEquipment", getMonitoringEquipment())
            .append("cliverVideoId", getCliverVideoId())
            .append("faceId", getFaceId())
            .append("propertyUnit", getPropertyUnit())
            .append("installUnit", getInstallUnit())
            .append("productionUnit", getProductionUnit())
            .append("specificationsModel", getSpecificationsModel())
            .append("presence", getPresence())
            .append("coordinateX", getCoordinateX())
            .append("coordinateY", getCoordinateY())
            .append("radius", getRadius())
            .append("isDelete", getIsDelete())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
