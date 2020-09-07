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
 * 安全图片管理对象 t_ws_safety_photo
 *
 * @author jlkj
 * @date 2020-07-13
 */
@ApiModel("安全图片管理对象")
public class WsSafetyPhoto extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("主键id")
    private Integer id;

    /** 项目id */
    @ApiModelProperty("项目id")
    @Excel(name = "项目id")
    private Integer projectId;

    /** 安全巡检id */
    @ApiModelProperty("安全巡检id")
    @Excel(name = "安全巡检id")
    private Integer inspectionId;

    /** 隐患整改id */
    @ApiModelProperty("隐患整改id")
    @Excel(name = "隐患整改id")
    private Integer rectifyId;

    /** 隐患验证id */
    @ApiModelProperty("隐患验证id")
    @Excel(name = "隐患验证id")
    private Integer verifyId;

    /** 类型1:检查；2：整改；3：验证； */
    @ApiModelProperty("类型1:检查；2：整改；3：验证")
    @Excel(name = "类型1:检查；2：整改；3：验证；")
    private Integer type;

    /** 照片地址 */
    @ApiModelProperty("照片地址")
    @Excel(name = "照片地址")
    private String photoAddres;

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
    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }

    public Integer getProjectId()
    {
        return projectId;
    }
    public void setInspectionId(Integer inspectionId)
    {
        this.inspectionId = inspectionId;
    }

    public Integer getInspectionId()
    {
        return inspectionId;
    }
    public void setRectifyId(Integer rectifyId)
    {
        this.rectifyId = rectifyId;
    }

    public Integer getRectifyId()
    {
        return rectifyId;
    }
    public void setVerifyId(Integer verifyId)
    {
        this.verifyId = verifyId;
    }

    public Integer getVerifyId()
    {
        return verifyId;
    }
    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getType()
    {
        return type;
    }
    public void setPhotoAddres(String photoAddres)
    {
        this.photoAddres = photoAddres;
    }

    public String getPhotoAddres()
    {
        return photoAddres;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("inspectionId", getInspectionId())
            .append("rectifyId", getRectifyId())
            .append("verifyId", getVerifyId())
            .append("type", getType())
            .append("photoAddres", getPhotoAddres())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("uodatedBy", getUodatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
