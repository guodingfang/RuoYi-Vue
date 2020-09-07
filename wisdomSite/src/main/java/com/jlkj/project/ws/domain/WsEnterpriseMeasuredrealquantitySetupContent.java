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
 * 实测实量设置分项工程实测内容 对象 t_ws_enterprise_measuredrealquantity_setup_content
 *
 * @author pyy
 * @date 2020-06-20
 */
@ApiModel("实测实量设置分项工程实测内容实体")
public class WsEnterpriseMeasuredrealquantitySetupContent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("设置分项工程实测内容主键")
    private Integer id;

    /** 分项工程id */
    @NotNull(message = "分项工程id不能为空")
    @ApiModelProperty(value = "分项工程id", required = true)
    @Excel(name = "分项工程id")
    private Integer measuredrealquantitySetupId;

    /** 分项工程名称 */
    @ApiModelProperty(value = "分项工程名称")
    @Excel(name = "分项工程名称")
    private String subitemProject;

    /** 实测内容 */
    @NotBlank(message = "实测内容不能为空")
    @ApiModelProperty(value = "实测内容", required = true)
    @Excel(name = "实测内容")
    private String measuredContent;

    /** 最小值 */
    @NotBlank(message = "最小值不能为空")
    @ApiModelProperty(value = "最小值", required = true)
    @Excel(name = "最小值")
    private Double minNum;

    /** 最大值 */
    @NotBlank(message = "最大值不能为空")
    @ApiModelProperty(value = "最大值", required = true)
    @Excel(name = "最大值")
    private Double maxNum;

    /** 单位 */
    @NotBlank(message = "单位不能为空")
    @ApiModelProperty(value = "单位", required = true)
    @Excel(name = "单位")
    private String company;

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

    public String getMeasuredContent() {
        return measuredContent;
    }

    public void setMeasuredContent(String measuredContent) {
        this.measuredContent = measuredContent;
    }

    public Double getMinNum() {
        return minNum;
    }

    public void setMinNum(Double minNum) {
        this.minNum = minNum;
    }

    public Double getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Double maxNum) {
        this.maxNum = maxNum;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
            .append("measuredrealquantitySetupId", getMeasuredrealquantitySetupId())
            .append("subitemProject", getSubitemProject())
            .append("measuredContent", getMeasuredContent())
            .append("minNum", getMinNum())
            .append("maxNum", getMaxNum())
            .append("company", getCompany())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
