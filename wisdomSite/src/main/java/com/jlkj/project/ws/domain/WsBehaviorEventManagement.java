package com.jlkj.project.ws.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 行为事件管理对象 t_ws_behavior_event_management
 *
 * @author liujie
 * @date 2020-07-23
 */
@ApiModel("行为事件管理对象")
public class WsBehaviorEventManagement implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Integer id;

    /** 项目ID */
    @ApiModelProperty("项目ID")
    @Excel(name = "项目ID")
    private Integer projectId;

    /** 事件名称 */
    @ApiModelProperty("事件名称")
    @Excel(name = "事件名称")
    private String eventName;

    /** 事件类型(1正向,0反向） */
    @Excel(name = "事件类型(1正向,0反向）" ,readConverterExp = "0=负向,1=正向")
    @ApiModelProperty("事件类型(1正向,0反向）")
    private String eventType;

    /** 增加点券/扣分值 */
    @Excel(name = "增加点券/扣分值")
    @ApiModelProperty("增加点券/扣分值")
    private Integer point;

    /** 是否删除*/
    @Excel(name = "是否删除")
    @ApiModelProperty("是否删除")
    private String deleted;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

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
    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }
    @NotNull(message = "项目ID不能为空")
    public Integer getProjectId()
    {
        return projectId;
    }
    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }

    @NotBlank(message = "事件名不能为空")
    public String getEventName()
    {
        return eventName;
    }
    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }
    @NotBlank(message = "事件类型不能为空")
    public String getEventType()
    {
        return eventType;
    }
    public void setPoint(Integer point)
    {
        this.point = point;
    }
    @NotNull(message = "增加点券/扣分值")
    public Integer getPoint()
    {
        return point;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }
    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsBehaviorEventManagement{");
        sb.append("id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", eventName='").append(eventName).append('\'');
        sb.append(", eventType='").append(eventType).append('\'');
        sb.append(", point=").append(point);
        sb.append(", deleted='").append(deleted).append('\'');
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append('}');
        return sb.toString();
    }
}
