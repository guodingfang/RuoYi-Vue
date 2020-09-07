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
 * 实测实量实测内容 对象 t_ws_enterprise_measuredrealquantity_content
 *
 * @author pyy
 * @date 2020-06-20
 */
@ApiModel("实测实量实测内容实体")
public class WsEnterpriseMeasuredrealquantityContent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("实测内容实体主键")
    private Integer id;

    /** 实测部位id */
    @NotNull(message = "实测部位id能为空")
    @ApiModelProperty(value = "实测部位id", required = true)
    @Excel(name = "实测部位id")
    private Integer measuredrealquantityMeasuredLocationId;

    /** 实测实量id */
    @NotNull(message = "实测实量id不能为空")
    @ApiModelProperty(value = "实测实量id", required = true)
    @Excel(name = "实测实量id")
    private Integer measuredrealquantityId;

    /** 实测内容字段 */
    @NotBlank(message = "实测内容字段不能为空")
    @ApiModelProperty(value = "实测内容字段", required = true)
    @Excel(name = "实测内容字段")
    private String contentField;

    /** 实测内容字段值 */
    @NotBlank(message = "实测内容值不能为空")
    @ApiModelProperty(value = "实测内容字段值", required = true)
    @Excel(name = "实测内容字段值")
    private String contentFieldValue;

    /** 实测内容字段单位 */
    @NotBlank(message = "实测内容字段单位不能为空")
    @ApiModelProperty(value = "实测内容字段单位", required = true)
    @Excel(name = "实测内容字段单位")
    private String contentFieldCompany;

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

    public Integer getMeasuredrealquantityId() {
        return measuredrealquantityId;
    }

    public void setMeasuredrealquantityId(Integer measuredrealquantityId) {
        this.measuredrealquantityId = measuredrealquantityId;
    }

    public String getContentField() {
        return contentField;
    }

    public void setContentField(String contentField) {
        this.contentField = contentField;
    }

    public String getContentFieldValue() {
        return contentFieldValue;
    }

    public void setContentFieldValue(String contentFieldValue) {
        this.contentFieldValue = contentFieldValue;
    }

    public String getContentFieldCompany() {
        return contentFieldCompany;
    }

    public void setContentFieldCompany(String contentFieldCompany) {
        this.contentFieldCompany = contentFieldCompany;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("measuredrealquantityMeasuredLocationId", getMeasuredrealquantityMeasuredLocationId())
            .append("measuredrealquantityId", getMeasuredrealquantityId())
            .append("contentField", getContentField())
            .append("contentFieldValue", getContentFieldValue())
            .append("contentFieldCompany", getContentFieldCompany())
            .append("isDelete", getIsDelete())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
