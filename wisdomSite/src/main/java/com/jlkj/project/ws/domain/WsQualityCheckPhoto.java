package com.jlkj.project.ws.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 质量巡检照片 对象 t_ws_quality_check_photo
 *
 * @author jlkj
 * @date 2020-07-06
 */
public class WsQualityCheckPhoto extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Integer id;

    /** 项目id */
    @Excel(name = "项目id")
    private Integer projectId;

    /** 质量巡检id */
    @Excel(name = "质量巡检id")
    private Integer checkManagerId;

    /** 质量整改id */
    @Excel(name = "质量整改id")
    private Integer checkRepairId;

    /** 质量验证id */
    @Excel(name = "质量验证id")
    private Integer checkVerifyId;

    /** 质量验收id */
    @Excel(name = "质量验收id")
    private Integer acceptanceId;

    /** 亮点id */
    @Excel(name = "亮点id")
    private Integer brightspotId;

    /** 类型 1:检查；2：整改；3：验证；4：验收；5：亮点 */
    @Excel(name = "类型 1:检查；2：整改；3：验证；4：验收；5：亮点")
    private Integer qualityType;

    /** 照片地址 */
    @Excel(name = "照片地址")
    private String photoAddres;

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

    public Integer getProjectId()
    {
        return projectId;
    }
    public void setCheckManagerId(Integer checkManagerId)
    {
        this.checkManagerId = checkManagerId;
    }

    public Integer getCheckManagerId()
    {
        return checkManagerId;
    }
    public void setAcceptanceId(Integer acceptanceId)
    {
        this.acceptanceId = acceptanceId;
    }

    public Integer getAcceptanceId()
    {
        return acceptanceId;
    }
    public void setBrightspotId(Integer brightspotId)
    {
        this.brightspotId = brightspotId;
    }

    public Integer getBrightspotId()
    {
        return brightspotId;
    }
    public void setQualityType(Integer qualityType)
    {
        this.qualityType = qualityType;
    }

    public Integer getQualityType()
    {
        return qualityType;
    }
    public void setPhotoAddres(String photoAddres)
    {
        this.photoAddres = photoAddres;
    }

    public String getPhotoAddres()
    {
        return photoAddres;
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

    public Integer getCheckRepairId() {
        return checkRepairId;
    }

    public void setCheckRepairId(Integer checkRepairId) {
        this.checkRepairId = checkRepairId;
    }

    public Integer getCheckVerifyId() {
        return checkVerifyId;
    }

    public void setCheckVerifyId(Integer checkVerifyId) {
        this.checkVerifyId = checkVerifyId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("checkManagerId", getCheckManagerId())
                .append("checkRepairId", getCheckRepairId())
                .append("checkVerifyId", getCheckVerifyId())
            .append("acceptanceId", getAcceptanceId())
            .append("brightspotId", getBrightspotId())
            .append("qualityType", getQualityType())
            .append("photoAddres", getPhotoAddres())
            .append("createdTime", getCreatedTime())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
