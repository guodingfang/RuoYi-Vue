package com.jlkj.project.ws.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

/**
 * 实测实量平面图管理 对象 t_ws_enterprise_measuredrealquantity_plan
 *
 * @author pyy
 * @date 2020-06-20
 */
@ApiModel("实测实量平面图管理实体")
public class WsEnterpriseMeasuredrealquantityPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("平面图管理主键")
    private Integer id;

    /** 项目id */
    @NotNull(message = "项目id不能为空")
    @ApiModelProperty(value = "项目id",required = true)
    @Excel(name = "项目id")
    private Integer projectId;

    /** 项目名称 */
    @ApiModelProperty("项目名称")
    @Excel(name = "项目名称")
    private String projectName;

    /** 父级id */
    @ApiModelProperty("父级id")
    @Excel(name = "父级id")
    private Integer parentId;

    /** Id集 */
    @ApiModelProperty("id集")
    @Excel(name = "Id集")
    private String pathIds;

    /** 区域名称 */
    @ApiModelProperty("区域名称")
    @Excel(name = "区域名称")
    private String regionName;

    /** 平面图 */
    @ApiModelProperty("平面图")
    @Excel(name = "平面图")
    private String planImageAddres;

    /** 是否删除 Y（删除），N(不删除) */
    @ApiModelProperty("是否删除 Y（删除），N(不删除)")
    @Excel(name = "是否删除 Y", readConverterExp = "删=除")
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPathIds() {
        return pathIds;
    }

    public void setPathIds(String pathIds) {
        this.pathIds = pathIds;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getPlanImageAddres() {
        return planImageAddres;
    }

    public void setPlanImageAddres(String planImageAddres) {
        this.planImageAddres = planImageAddres;
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
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("parentId", getParentId())
            .append("pathIds", getPathIds())
            .append("regionName", getRegionName())
            .append("planImageAddres", getPlanImageAddres())
            .append("isDelete", getIsDelete())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
