package com.jlkj.project.ws.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 考勤机 对象 t_ws_attendance_machine
 *
 * @author liujie
 * @date 2020-06-23
 */
@ApiModel("考勤机实体")
public class WsAttendanceMachine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("主键")
    private Integer id;

    /** 项目ID */
    @ApiModelProperty("项目ID")
    private Integer projectId;

    /** 分区ID */
    @ApiModelProperty("分区ID")
    private Integer zoneId;

    /** 名称 */
    @ApiModelProperty("打卡机名称")
    @Excel(name = "打卡机名称")
    private String name;

    /** 设备序列号 */
    @ApiModelProperty("设备号")
    @Excel(name = "设备号")
    private String serialNumber;

    /** 厂商代码 */
    @ApiModelProperty("打卡机厂商")
    @Excel(name = "打卡机厂商")
    private String vendorCode;

    /** 方向 进：1；出：2 */
    @ApiModelProperty("方向")
    @Excel(name = "方向",readConverterExp = "1=进,2=出")
    private String direction;

    /** 纬度 */
    private Integer latitude;

    /** 经度 */
    private Integer longitude;

    /** 打卡机类型（1=人员考勤，2=门禁，3=安全培训） */
    @Excel(name = "打卡机类型", readConverterExp = "1=人员考勤，2=门禁，3=安全培训")
    @ApiModelProperty("打卡机类型")
    private String machineType;

    @ApiModelProperty("打卡机类型名称")
    private String machineTypeName;

    /** 方向名称 */
    @ApiModelProperty("方向名称")
    @Excel(name = "方向名称",readConverterExp = "方向名称")
    private String directionName;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;
    /** 方向 离线：0；在线：1*/
    @Excel(name = "状态",readConverterExp = "1=在线,0=离线")
    @ApiModelProperty("状态")
    private String online;

    @ApiModelProperty("班组人员ID")
    private Integer personId;

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }


    public String getDirectionName() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public String getMachineTypeName() {
        return machineTypeName;
    }

    public void setMachineTypeName(String machineTypeName) {
        this.machineTypeName = machineTypeName;
    }

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
    @NotNull(message = "归属项目不能为空")
    public Integer getProjectId()
    {
        return projectId;
    }
    public void setZoneId(Integer zoneId)
    {
        this.zoneId = zoneId;
    }

    public Integer getZoneId()
    {
        return zoneId;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    @NotBlank(message = "打卡机名称不能为空")
    public String getName()
    {
        return name;
    }
    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }
    @NotBlank(message = "打卡机序列号不能为空")
    public String getSerialNumber()
    {
        return serialNumber;
    }
    public void setVendorCode(String vendorCode)
    {
        this.vendorCode = vendorCode;
    }

    public String getVendorCode()
    {
        return vendorCode;
    }
    @NotBlank(message = "考勤机方向不能为空")
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setLatitude(Integer latitude)
    {
        this.latitude = latitude;
    }

    public Integer getLatitude()
    {
        return latitude;
    }
    public void setLongitude(Integer longitude)
    {
        this.longitude = longitude;
    }

    public Integer getLongitude()
    {
        return longitude;
    }
    public void setMachineType(String machineType)
    {
        this.machineType = machineType;
    }

    public String getMachineType()
    {
        return machineType;
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

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsAttendanceMachine{");
        sb.append("id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", zoneId=").append(zoneId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", serialNumber='").append(serialNumber).append('\'');
        sb.append(", vendorCode='").append(vendorCode).append('\'');
        sb.append(", direction='").append(direction).append('\'');
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", machineType='").append(machineType).append('\'');
        sb.append(", machineTypeName='").append(machineTypeName).append('\'');
        sb.append(", directionName='").append(directionName).append('\'');
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", online='").append(online).append('\'');
        sb.append(", person_id=").append(personId);
        sb.append('}');
        return sb.toString();
    }
}
