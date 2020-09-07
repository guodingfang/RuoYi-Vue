package com.jlkj.project.ws.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Transient;

/**
 * 隐患整改 对象 t_ws_safety_hazard_rectify
 *
 * @author jlkj
 * @date 2020-07-13
 */
@ApiModel("隐患整改对象")
public class WsSafetyHazardRectify extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("主键id")
    private Integer id;

    /** 安全巡视id */
    @ApiModelProperty("安全巡视id")
    @Excel(name = "安全巡视id")
    private Integer inspectionId;

    /** 整改人 */
    @ApiModelProperty("整改人")
    @Excel(name = "整改人")
    private Integer rectifyPersonId;

    /** 整改人姓名 */
    @Transient
    @ApiModelProperty("整改人姓名")
    @Excel(name = "整改人姓名")
    private String rectifyPersonName;

    /** 整改时间 */
    @ApiModelProperty("整改时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "整改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rectifyTime;

    /** 整改描述 */
    @ApiModelProperty("整改描述")
    @Excel(name = "整改描述")
    private String resultDescription;

    /** 整改照片地址 */
    @ApiModelProperty("整改照片地址")
    @Excel(name = "整改照片地址")
    private List<Object> rectifyImageAddress;

    /** 创建人 */
    @Excel(name = "创建人")
    private Integer createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private Integer uodatedBy;

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
    public void setInspectionId(Integer inspectionId)
    {
        this.inspectionId = inspectionId;
    }

    public Integer getInspectionId()
    {
        return inspectionId;
    }
    public void setRectifyPersonId(Integer rectifyPersonId)
    {
        this.rectifyPersonId = rectifyPersonId;
    }

    public Integer getRectifyPersonId()
    {
        return rectifyPersonId;
    }
    public void setRectifyTime(Date rectifyTime)
    {
        this.rectifyTime = rectifyTime;
    }

    public Date getRectifyTime()
    {
        return rectifyTime;
    }
    public void setResultDescription(String resultDescription)
    {
        this.resultDescription = resultDescription;
    }

    public String getResultDescription()
    {
        return resultDescription;
    }
    public void setRectifyImageAddress(List<Object>  rectifyImageAddress)
    {
        this.rectifyImageAddress = rectifyImageAddress;
    }

    public List<Object>  getRectifyImageAddress()
    {
        return rectifyImageAddress;
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
    public void setUodatedBy(Integer uodatedBy)
    {
        this.uodatedBy = uodatedBy;
    }

    public Integer getUodatedBy()
    {
        return uodatedBy;
    }
    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }

    public String getRectifyPersonName() {
        return rectifyPersonName;
    }

    public void setRectifyPersonName(String rectifyPersonName) {
        this.rectifyPersonName = rectifyPersonName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("inspectionId", getInspectionId())
            .append("rectifyPersonId", getRectifyPersonId())
            .append("rectifyTime", getRectifyTime())
            .append("resultDescription", getResultDescription())
            .append("rectifyImageAddress", getRectifyImageAddress())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("uodatedBy", getUodatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
