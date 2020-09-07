package com.jlkj.project.ws.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 项目里程碑计划 对象 t_ws_project_milestoneplan
 *
 * @author pyy
 * @date 2020-06-09
 */
@ApiModel("项目里程碑计划实体")
public class WsProjectMilestoneplan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
  @ApiModelProperty("主键")
    private Integer id;

    /** 项目ID */
    @NotNull(message = "项目id能为空")
    @ApiModelProperty(value = "项目ID", required = true)
    @Excel(name = "项目ID")
    private Integer projectId;

    /** 里程碑名称 */
    @NotBlank(message = "里程碑名称不能为空")
    @ApiModelProperty(value = "里程碑名称", required = true)
    @Excel(name = "里程碑名称")
    private String milepostName;

    /** 里程碑排序 */
    @ApiModelProperty("里程碑排序")
    @Excel(name = "里程碑排序")
    private Integer milepostSort;

    /** 里程碑时间 */
    @ApiModelProperty("里程碑时间 格式 yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Excel(name = "里程碑时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date milepostDate;

    /** 创建人 */
    @ApiModelProperty("创建人")
    @Excel(name = "创建人")
    private Integer createdBy;

    /** 创建时间 */
    @ApiModelProperty("创建时间 格式 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    @Excel(name = "更新人")
    private Integer updatedBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间 格式 yyyy-MM-dd")
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

    public String getMilepostName() {
        return milepostName;
    }

    public void setMilepostName(String milepostName) {
        this.milepostName = milepostName;
    }

    public Integer getMilepostSort() {
        return milepostSort;
    }

    public void setMilepostSort(Integer milepostSort) {
        this.milepostSort = milepostSort;
    }

    public void setMilepostDate(Date milepostDate) {
        this.milepostDate = milepostDate;
    }

    public Date getMilepostDate()
    {
        return milepostDate;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("milepostName", getMilepostName())
            .append("milepostSort", getMilepostSort())
            .append("milepostDate", getMilepostDate())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
