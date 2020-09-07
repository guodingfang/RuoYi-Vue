package com.jlkj.project.ws.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 工地分区对象 t_ws_project_site_partition
 *
 * @author pyy
 * @date 2020-06-30
 */
@ApiModel("工地分区对象")
public class WsProjectSitePartition extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键id")
    private Integer id;

    /**
     * 分区名称
     */
    @ApiModelProperty("分区名称")
    @Excel(name = "分区名称")
    private String partitionName;

    /**
     * 项目id
     */
    @ApiModelProperty("项目id")
    @Excel(name = "项目id")
    private Integer projectId;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    @Excel(name = "创建人")
    private Integer createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间 ")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    @Excel(name = "更新人")
    private Integer updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setPartitionName(String partitionName) {
        this.partitionName = partitionName;
    }

    public String getPartitionName() {
        return partitionName;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("partitionName", getPartitionName())
                .append("projectId", getProjectId())
                .append("createdBy", getCreatedBy())
                .append("createdTime", getCreatedTime())
                .append("updatedBy", getUpdatedBy())
                .append("updatedTime", getUpdatedTime())
                .toString();
    }
}
