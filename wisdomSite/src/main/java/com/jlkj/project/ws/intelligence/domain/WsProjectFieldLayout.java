package com.jlkj.project.ws.intelligence.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 工程场布图管理 对象 t_ws_project_field_layout
 *
 * @author liujie
 * @date 2020-07-03
 */
@ApiModel("工程场布图管理实体")
public class WsProjectFieldLayout extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("主键id ")
    private Integer id;

    /** 项目id */
    @ApiModelProperty("项目id ")
    @Excel(name = "项目id")
    private Integer projectId;

    /** 设备类型(1=塔吊，2=升降机） */
    @ApiModelProperty("设备类型(1=塔吊，2=升降机） ")
    @Excel(name = "设备类型(1=塔吊，2=升降机）")
    private Integer equipmentType;

    /** 场布图地址 */
    @ApiModelProperty("场布图地址")
    @Excel(name = "场布图地址")
    private String fieldlayoutAddress;

    /** 宽 */
    @ApiModelProperty("宽")
    @Excel(name = "宽")
    private Integer width;

    /** 高 */
    @ApiModelProperty("高")
    @Excel(name = "高")
    private Integer height;


    /** 是否删除 Y（删除），N(不删除) */
    @Excel(name = "是否删除 Y", readConverterExp = "Y=删除,N=不删除")
    private String isDelete;

    /** 创建人 */
    private Long createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    private Long updatedBy;

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
    public void setEquipmentType(Integer equipmentType)
    {
        this.equipmentType = equipmentType;
    }

    public Integer getEquipmentType()
    {
        return equipmentType;
    }
    public void setFieldlayoutAddress(String fieldlayoutAddress)
    {
        this.fieldlayoutAddress = fieldlayoutAddress;
    }

    public String getFieldlayoutAddress()
    {
        return fieldlayoutAddress;
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

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsProjectFieldLayout{");
        sb.append("id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", equipmentType=").append(equipmentType);
        sb.append(", fieldlayoutAddress='").append(fieldlayoutAddress).append('\'');
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", isDelete='").append(isDelete).append('\'');
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append('}');
        return sb.toString();
    }
}
