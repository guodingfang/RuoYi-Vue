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
 * 作业类型 对象 t_ws_safety_operation_type
 *
 * @author jlkj
 * @date 2020-07-13
 */
@ApiModel("作业类型")
public class WsSafetyOperationType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("主键id")
    private Integer id;


    /** 集团id */
    @ApiModelProperty("集团id")
    @Excel(name = "集团id")
    private Integer enterpriseId;


    /** 作业类型名称 */
    @ApiModelProperty("作业类型名称")
    @Excel(name = "作业类型名称")
    private String operationName;


    /** 绑定状态 0表示未绑定  1表示已绑定 */
    @ApiModelProperty("绑定状态 0表示未绑定  1表示已绑定")
    @Excel(name = "绑定状态 0表示未绑定  1表示已绑定")
    private Integer bindingStatus;

    /** 创建人 */
    @Excel(name = "创建人")
    private Integer createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private Integer updatedBy;

    /** 更新时间 */
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
    public void setEnterpriseId(Integer enterpriseId)
    {
        this.enterpriseId = enterpriseId;
    }

    public Integer getEnterpriseId()
    {
        return enterpriseId;
    }
    public void setOperationName(String operationName)
    {
        this.operationName = operationName;
    }

    public String getOperationName()
    {
        return operationName;
    }
    public void setBindingStatus(Integer bindingStatus)
    {
        this.bindingStatus = bindingStatus;
    }

    public Integer getBindingStatus()
    {
        return bindingStatus;
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
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("enterpriseId", getEnterpriseId())
                .append("operationName", getOperationName())
                .append("bindingStatus", getBindingStatus())
                .append("createdBy", getCreatedBy())
                .append("createdTime", getCreatedTime())
                .append("updatedBy", getUpdatedBy())
                .append("updatedTime", getUpdatedTime())
                .toString();
    }


}
