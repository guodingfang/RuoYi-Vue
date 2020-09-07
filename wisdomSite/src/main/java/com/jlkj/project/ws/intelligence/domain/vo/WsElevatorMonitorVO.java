package com.jlkj.project.ws.intelligence.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.project.ws.intelligence.domain.WsElevatorMinitorDevice;
import com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * 施工电梯设备监测VO t_ws_elevator_manager
 *
 * @author liujie
 * @date 2020-07-03
 */
@ApiModel("施工电梯设备监测VO")
public class WsElevatorMonitorVO
{
    private static final long serialVersionUID = 1L;


    /** 主键ID */
    @ApiModelProperty("主键ID")
    private Integer id;

    /** 工程id */
    @ApiModelProperty("工程id")
    @Excel(name = "工程id")
    private Integer projectId;

    /** 自编号 */
    @ApiModelProperty("自编号")
    @Excel(name = "自编号")
    private String selfNumber;

    /** 产权备案编号 */
    @ApiModelProperty("产权备案编号")
    @Excel(name = "产权备案编号")
    private String recordNumber;

    /** 设备名称 */
    @ApiModelProperty("设备名称")
    @Excel(name = "设备名称")
    private String deviceName;


    /** 进场日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("进场日期")
    @Excel(name = "进场日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inDate;

    /** 退场日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("退场日期")
    @Excel(name = "退场日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date outDate;


    /** 设备类型(1=单笼,2=双笼) */
    @ApiModelProperty("设备类型(1=单笼,2=双笼) ")
    @Excel(name = "设备类型(1=单笼,2=双笼)")
    private String deviceType;

    /** 绑定设备列表 */
    @ApiModelProperty("绑定设备列表")
    private List<WsElevatorMinitorDevice> monitorDevices;

    /** 项目场布图 */
    @ApiModelProperty("项目场布图")
    private WsProjectFieldLayout layout;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getSelfNumber() {
        return selfNumber;
    }

    public void setSelfNumber(String selfNumber) {
        this.selfNumber = selfNumber;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public List<WsElevatorMinitorDevice> getMonitorDevices() {
        return monitorDevices;
    }

    public void setMonitorDevices(List<WsElevatorMinitorDevice> monitorDevices) {
        this.monitorDevices = monitorDevices;
    }

    public WsProjectFieldLayout getLayout() {
        return layout;
    }

    public void setLayout(WsProjectFieldLayout layout) {
        this.layout = layout;
    }
}
