package com.jlkj.project.ws.intelligence.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 施工电梯监控设备绑定管理对象 t_ws_elevator_minitor_device
 *
 * @author liujie
 * @date 2020-07-03
 */
@ApiModel("施工电梯监控设备绑定管理对象")
public class WsElevatorMinitorDevice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ApiModelProperty("主键")
    private Integer id;

    /** 施工电梯ID */
    @ApiModelProperty("施工电梯ID")
    @Excel(name = "施工电梯ID")
    private Integer elevatorId;

    /** 监控设备编号 */
    @ApiModelProperty("监控设备编号")
    @Excel(name = "监控设备编号")
    private String serialNumber;

    /** 监控设备类型(1=塔机,2=升降机,扬尘=3,用电=4,5=视频,6=打卡机,7=卸料,8=喷淋,9=塔机人员,10=移动传感器,11=洗车,12=渣土遗留,14=升降机人脸) */
    @Excel(name = "监控设备类型(1=塔机,2=升降机,扬尘=3,用电=4,5=视频,6=打卡机,7=卸料,8=喷淋,9=塔机人员,10=移动传感器,11=洗车,12=渣土遗留,14=升降机人脸)")
    private String equipmentType;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 是否删除（0=N,1=Y) */
    @Excel(name = "是否删除", readConverterExp = "是否删除（0=N,1=Y)")
    private String deleted;

    /** 厂商 */
    @Excel(name = "厂商")
    @ApiModelProperty("厂商")
    private String manufacturer;
    /** 安装日期 */
    @Excel(name = "安装日期")
    @ApiModelProperty("安装日期")
    private Date installDate;

    @ApiModelProperty("笼号")
    @Excel(name = "笼号",readConverterExp = "0=单笼,1=左笼,2=右笼")
    private String cageMark;

    @Autowired
    private Integer  projectId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @NotBlank(message = "笼号不能为空")
    public String getCageMark() {
        return cageMark;
    }

    public void setCageMark(String cageMark) {
        this.cageMark = cageMark;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setElevatorId(Integer elevatorId)
    {
        this.elevatorId = elevatorId;
    }

    public Integer getElevatorId()
    {
        return elevatorId;
    }
    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }
    public void setEquipmentType(String equipmentType)
    {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentType()
    {
        return equipmentType;
    }
    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
}
