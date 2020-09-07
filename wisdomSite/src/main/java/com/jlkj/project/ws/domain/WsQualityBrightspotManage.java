package com.jlkj.project.ws.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.annotation.Transient;

/**
 * 亮点管理 对象 t_ws_quality_brightspot_manage
 *
 * @author gaowei
 * @date 2020-06-11
 */
public class WsQualityBrightspotManage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("主键id")
    private Integer id;

    /** 项目id */
    @ApiModelProperty("项目id")
    @Excel(name = "项目id")
    private Integer projectId;

    /** 亮点部位 */
    @Excel(name = "亮点部位")
    @ApiModelProperty("亮点部位")
    private String brightspotPosition;

    /** 亮点描述 */
    @Excel(name = "亮点描述")
    @ApiModelProperty("亮点描述")
    private String brightspotDescribe;

    /** 亮点照片地址 */
    @Excel(name = "亮点照片地址")
    @ApiModelProperty("亮点照片地址")
    private List<Object> brightspotImageAddres;

    /** 上传人 */
    @Excel(name = "上传人")
    @ApiModelProperty("上传人")
    private Integer uploadPersonId;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("上传时间")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date uploadDate;

    /** 是否删除 Y（删除），N(不删除) */
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

    @Transient
    @Excel(name = "项目名称")
    @ApiModelProperty("项目名称")
    private String projectName;//项目名称

    @Transient
    @Excel(name = "上传人姓名")
    @ApiModelProperty("上传人姓名")
    private String uploadPersonName;//上传人name

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
    public void setBrightspotPosition(String brightspotPosition)
    {
        this.brightspotPosition = brightspotPosition;
    }

    public String getBrightspotPosition()
    {
        return brightspotPosition;
    }
    public void setBrightspotDescribe(String brightspotDescribe)
    {
        this.brightspotDescribe = brightspotDescribe;
    }

    public String getBrightspotDescribe()
    {
        return brightspotDescribe;
    }
    public void setBrightspotImageAddres(List<Object> brightspotImageAddres)
    {
        this.brightspotImageAddres = brightspotImageAddres;
    }

    public List<Object> getBrightspotImageAddres()
    {
        return brightspotImageAddres;
    }
    public void setUploadPersonId(Integer uploadPersonId)
    {
        this.uploadPersonId = uploadPersonId;
    }

    public Integer getUploadPersonId()
    {
        return uploadPersonId;
    }
    public void setUploadDate(Date uploadDate)
    {
        this.uploadDate = uploadDate;
    }

    public Date getUploadDate()
    {
        return uploadDate;
    }
    public void setIsDelete(String isDelete)
    {
        this.isDelete = isDelete;
    }

    public String getIsDelete()
    {
        return isDelete;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUploadPersonName() {
        return uploadPersonName;
    }

    public void setUploadPersonName(String uploadPersonName) {
        this.uploadPersonName = uploadPersonName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("brightspotPosition", getBrightspotPosition())
            .append("brightspotDescribe", getBrightspotDescribe())
            .append("brightspotImageAddres", getBrightspotImageAddres())
            .append("uploadPersonId", getUploadPersonId())
            .append("uploadDate", getUploadDate())
            .append("isDelete", getIsDelete())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
