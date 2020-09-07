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

/**
 * 实测实量设置分项工程 对象 t_ws_enterprise_measuredrealquantity_setup
 *
 * @author pyy
 * @date 2020-06-20
 */
@ApiModel("实测实量设置分项工程实体")
public class WsEnterpriseMeasuredrealquantitySetup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("分项工程主键")
    private Integer id;

    /** 分项工程名称 */
    @NotBlank(message = "分项工程名称不能为空")
    @ApiModelProperty(value = "分项工程名称",required = true)
    @Excel(name = "分项工程名称")
    private String subitemProject;

    /** 集团id */
    @ApiModelProperty("集团id")
    @Excel(name = "集团id")
    private Integer enterpriseId;

    /** 是否删除 Y（删除），N(不删除) */
    @ApiModelProperty("是否删除 Y（删除），N(不删除)")
    private String isDelete;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private Integer createdBy;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createdTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private Integer updatedBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updatedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubitemProject() {
        return subitemProject;
    }

    public void setSubitemProject(String subitemProject) {
        this.subitemProject = subitemProject;
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

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("subitemProject", getSubitemProject())
            .append("isDelete", getIsDelete())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
