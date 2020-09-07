package com.jlkj.project.ws.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 考勤分区 对象 t_ws_attendance_zone
 *
 * @author liujie
 * @date 2020-06-23
 */
@ApiModel("考勤区域实体")
public class WsAttendanceZone extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("主键")
    private Integer id;

    /** 项目ID */
    @ApiModelProperty("项目ID")
    @Excel(name = "项目ID")
    private Integer projectId;

    /** 名称 */
    @Excel(name = "区域名称")
    @ApiModelProperty("区域名称")
    private String name;


    @Excel(name = "绑定设备")
    @ApiModelProperty("绑定设备")
    private String machineContact;


    @ApiModelProperty("考勤设备列表")
    private List<WsAttendanceMachine> machines;

    /** 打卡机数量 */
    @Excel(name = "打卡机数量")
    @ApiModelProperty("打卡机数量")
    private Integer machineNumber;

    /** 更新人 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新人", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;



    public List<WsAttendanceMachine> getMachines() {
        return machines;
    }

    public void setMachines(List<WsAttendanceMachine> machines) {
        this.machines = machines;
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
    public void setName(String name)
    {
        this.name = name;
    }

    @NotBlank(message = "区域名称不能为空")
    public String getName()
    {
        return name;
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

    public String getMachineContact() {
        return machineContact;
    }

    public void setMachineContact(String machineContact) {
        this.machineContact = machineContact;
    }

    public Integer getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(Integer machineNumber) {
        this.machineNumber = machineNumber;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsAttendanceZone{");
        sb.append("id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", machineContact='").append(machineContact).append('\'');
        sb.append(", machines=").append(machines);
        sb.append(", machineNumber=").append(machineNumber);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", createdTime=").append(createdTime);
        sb.append('}');
        return sb.toString();
    }
}
