package com.jlkj.project.ws.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 质量问题管理 对象 t_ws_quality_problem_type
 *
 * @author gaowei
 * @date 2020-06-09
 */
public class WsQualityProblemType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("主键id")
    private Long id;

    /** 集团id */
    @ApiModelProperty("集团id")
    @Excel(name = "集团id")
    private Long groupId;

    /** 父级id */
    @ApiModelProperty("父级id")
    @Excel(name = "父级id")
    private Long parentId;

    /** 问题Id集 */
    @ApiModelProperty("问题Id集")
    @Excel(name = "问题Id集")
    private String pathIds;

    /** 问题类别名称 */
    @ApiModelProperty("问题类别名称")
    @Excel(name = "问题类别名称")
    private String problemTypeName;

    /** 是否删除 Y（删除），N(不删除) */
    @ApiModelProperty("是否删除 Y（删除），N(不删除)")
    @Excel(name = "是否删除 Y", readConverterExp = "删=除")
    private String isDelete;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private Long updatedBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getParentId()
    {
        return parentId;
    }
    public void setPathIds(String pathIds)
    {
        this.pathIds = pathIds;
    }

    public String getPathIds()
    {
        return pathIds;
    }
    public void setProblemTypeName(String problemTypeName)
    {
        this.problemTypeName = problemTypeName;
    }

    public String getProblemTypeName()
    {
        return problemTypeName;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public void setCreatedBy(Long createdBy)
    {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy()
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
    public void setUpdatedBy(Long updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy()
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
            .append("groupId", getGroupId())
            .append("parentId", getParentId())
            .append("pathIds", getPathIds())
            .append("problemTypeName", getProblemTypeName())
            .append("idDelete", getIsDelete())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
