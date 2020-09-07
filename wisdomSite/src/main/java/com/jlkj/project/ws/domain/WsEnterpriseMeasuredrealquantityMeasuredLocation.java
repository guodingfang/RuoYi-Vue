package com.jlkj.project.ws.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 实测实量实测部位 对象 t_ws_enterprise_measuredrealquantity_measured_location
 *
 * @author pyy
 * @date 2020-06-20
 */
@ApiModel("实测实量实测部位实体")
public class WsEnterpriseMeasuredrealquantityMeasuredLocation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("实测部位主键")
    private Integer id;

    /** 项目id */
    @NotNull(message = "项目id不能为空")
    @ApiModelProperty(value = "项目id", required = true)
    @Excel(name = "项目id")
    private Integer projectId;

    /** 项目名称 */
    @ApiModelProperty("项目名称")
    @Excel(name = "项目名称")
    private String projectName;

    /** 所属区域 */
    @NotNull(message = "所属区域不能为空")
    @ApiModelProperty(value = "所属区域", required = true)
    @Excel(name = "所属区域")
    private Integer respectiveRegionId;

    /** 所属区域名称 */
    @ApiModelProperty("所属区域名称")
    @Excel(name = "所属区域名称")
    private String respectiveRegionName;

    /** 实测部位 */
    @NotBlank(message = "实测部位不能为空")
    @ApiModelProperty(value = "实测部位", required = true)
    @Excel(name = "实测部位")
    private String measuredLocation;

    /** 测量次数 */
    @ApiModelProperty("测量次数")
    @Excel(name = "测量次数")
    private Integer measurementsNo;

    /** 坐标x */
    @ApiModelProperty("坐标x")
    private String coordinateX;

    /** 坐标y */
    @ApiModelProperty("坐标y")
    private String coordinateY;

    /** 平面图标点 */
    @NotBlank(message = "平面图标点不能为空")
    @ApiModelProperty(value = "平面图标点", required = true)
    @Excel(name = "平面图标点")
    private String planImageAddres;

    /** 二维码 */
    @ApiModelProperty("二维码")
    @Excel(name = "二维码")
    private String qrCode;

    /** 备注 */
    @ApiModelProperty("备注")
    @Excel(name = "备注")
    private String comment;

    /** 状态 */
    @ApiModelProperty("状态")
    @Excel(name = "状态")
    private int status;

    /** 是否删除 Y（删除），N(不删除) */
    @ApiModelProperty("是否删除 Y（删除），N(不删除)")
    @Excel(name = "是否删除 Y", readConverterExp = "删=除")
    private String isDelete;

    /** 创建人 */
    @ApiModelProperty("创建人")
    @Excel(name = "创建人")
    private Integer createdBy;

    /** 创建人姓名 */
    @ApiModelProperty("创建人姓名")
    @Excel(name = "创建人姓名")
    private String createdName;

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

    public Integer getRespectiveRegionId() {
        return respectiveRegionId;
    }

    public void setRespectiveRegionId(Integer respectiveRegionId) {
        this.respectiveRegionId = respectiveRegionId;
    }

    public String getRespectiveRegionName() {
        return respectiveRegionName;
    }

    public void setRespectiveRegionName(String respectiveRegionName) {
        this.respectiveRegionName = respectiveRegionName;
    }

    public String getMeasuredLocation() {
        return measuredLocation;
    }

    public void setMeasuredLocation(String measuredLocation) {
        this.measuredLocation = measuredLocation;
    }

    public Integer getMeasurementsNo() {
        return measurementsNo;
    }

    public void setMeasurementsNo(Integer measurementsNo) {
        this.measurementsNo = measurementsNo;
    }

    public String getPlanImageAddres() {
        return planImageAddres;
    }

    public void setPlanImageAddres(String planImageAddres) {
        this.planImageAddres = planImageAddres;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
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

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("respectiveRegionId", getRespectiveRegionId())
            .append("respectiveRegionName", getRespectiveRegionName())
            .append("measuredLocation", getMeasuredLocation())
            .append("measurementsNo", getMeasurementsNo())
            .append("planImageAddres", getPlanImageAddres())
            .append("qrCode", getQrCode())
            .append("comment", getComment())
            .append("isDelete", getIsDelete())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
