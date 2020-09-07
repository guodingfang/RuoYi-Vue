package com.jlkj.project.ws.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 质量预警管理 对象 t_ws_quality_earlywarning_manager
 *
 * @author gaowei
 * @date 2020-06-09
 */
public class WsQualityEarlywarningManager extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 质量检查ID */
    @Excel(name = "质量检查ID")
    private Long qualityCheckManagerId;

    /** 督促内容 */
    @Excel(name = "督促内容")
    private String superviseContents;

    /** 超期天数 */
    @Excel(name = "超期天数")
    private Long overdueDate;

    /** 人员ID */
    @Excel(name = "人员ID")
    private Long personId;

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
    public void setQualityCheckManagerId(Long qualityCheckManagerId)
    {
        this.qualityCheckManagerId = qualityCheckManagerId;
    }

    public Long getQualityCheckManagerId()
    {
        return qualityCheckManagerId;
    }
    public void setSuperviseContents(String superviseContents)
    {
        this.superviseContents = superviseContents;
    }

    public String getSuperviseContents()
    {
        return superviseContents;
    }
    public void setOverdueDate(Long overdueDate)
    {
        this.overdueDate = overdueDate;
    }

    public Long getOverdueDate()
    {
        return overdueDate;
    }
    public void setPersonId(Long personId)
    {
        this.personId = personId;
    }

    public Long getPersonId()
    {
        return personId;
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
            .append("qualityCheckManagerId", getQualityCheckManagerId())
            .append("superviseContents", getSuperviseContents())
            .append("overdueDate", getOverdueDate())
            .append("personId", getPersonId())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
