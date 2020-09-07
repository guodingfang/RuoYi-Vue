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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 实测实量 对象 t_ws_enterprise_measuredrealquantity
 *
 * @author pyy
 * @date 2020-06-20
 */
@ApiModel("实测实量实体")
public class WsEnterpriseMeasuredrealquantity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("实测实量实体ID")
    private Integer id;

    /** 实测部位id */

    @NotNull(message = "实测部位id能为空")
    @ApiModelProperty(value = "实测部位id", required = true)
    @Excel(name = "实测部位id")
    private Integer measuredrealquantityMeasuredLocationId;

    /** 分项工程id */
    @NotNull(message = "分项工程id能为空")
    @ApiModelProperty(value = "分项工程id", required = true)
    @Excel(name = "分项工程id")
    private Integer measuredrealquantitySetupId;

    /** 分项工程名称 */
    @NotBlank(message = "分项工程名称不能为空")
    @ApiModelProperty(value = "分项工程名称", required = true)
    @Excel(name = "分项工程名称")
    private String subitemProject;

    /** 参与人 */
    @NotBlank(message = "参与人不能为空")
    @ApiModelProperty(value = "参与人", required = true)
    @Excel(name = "参与人")
    private String participant;

    /** 备注 */
    @ApiModelProperty("备注")
    @Excel(name = "备注")
    private String comment;

    /** 现场照片 */
    @ApiModelProperty("现场照片")
    @Excel(name = "现场照片")
    private List<Object> sceneImageAddres;

    /** 是否删除 Y（删除），N(不删除) */
    @ApiModelProperty("是否删除 Y（删除），N(不删除) ")
    private String isDelete;

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

    /** 创建人姓名 */
    @ApiModelProperty("创建人姓名")
    @Excel(name = "创建人姓名")
    private String createdName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMeasuredrealquantityMeasuredLocationId() {
        return measuredrealquantityMeasuredLocationId;
    }

    public void setMeasuredrealquantityMeasuredLocationId(Integer measuredrealquantityMeasuredLocationId) {
        this.measuredrealquantityMeasuredLocationId = measuredrealquantityMeasuredLocationId;
    }

    public Integer getMeasuredrealquantitySetupId() {
        return measuredrealquantitySetupId;
    }

    public void setMeasuredrealquantitySetupId(Integer measuredrealquantitySetupId) {
        this.measuredrealquantitySetupId = measuredrealquantitySetupId;
    }

    public String getSubitemProject() {
        return subitemProject;
    }

    public void setSubitemProject(String subitemProject) {
        this.subitemProject = subitemProject;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Object> getSceneImageAddres() {
        return sceneImageAddres;
    }

    public void setSceneImageAddres(List<Object> sceneImageAddres) {
        this.sceneImageAddres = sceneImageAddres;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("measuredrealquantityMeasuredLocationId", getMeasuredrealquantityMeasuredLocationId())
            .append("measuredrealquantitySetupId", getMeasuredrealquantitySetupId())
            .append("subitemProject", getSubitemProject())
            .append("participant", getParticipant())
            .append("comment", getComment())
            .append("sceneImageAddres", getSceneImageAddres())
            .append("isDelete", getIsDelete())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
