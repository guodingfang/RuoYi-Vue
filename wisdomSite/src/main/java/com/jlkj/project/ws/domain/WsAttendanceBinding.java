package com.jlkj.project.ws.domain;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 考勤绑定 对象 t_ws_attendance_binding
 *
 * @author liujie
 * @date 2020-06-23
 */
@ApiModel("考勤机绑定实体")
public class WsAttendanceBinding extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 考勤云平台人员唯一标识 */
    @ApiModelProperty("考勤云平台人员唯一标识")
    @Excel(name = "考勤云平台人员唯一标识")
    private String guid;

    /** 人员姓名 */
    @ApiModelProperty("人员姓名")
    @Excel(name = "人员姓名")
    private String personName;

        /** 打卡机序列 */
    @Excel(name = "打卡机序列")
    @ApiModelProperty("打卡机序列")
    private String serialNumber;

    /** 设备名称 */
    @Excel(name = "设备名称")
    @ApiModelProperty("设备名称")
    private String deviceName;

    /** 打卡机状态 */
    @Excel(name = "打卡机状态")
    @ApiModelProperty("打卡机状态")
    private String online;

    /** 授权状态(0=失败，1=成功，2=授权中) */
    @Excel(name = "授权状态(0=失败，1=成功，2=授权中)")
    @ApiModelProperty("授权状态(0=失败，1=成功，2=授权中) ")
    private String authStatus;

    /** 授权失败原因 */
    @Excel(name = "授权失败原因")
    @ApiModelProperty("授权失败原因")
    private String reason;

    @NotBlank(message = "打卡机不能为空")
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }



    @NotBlank(message = "人员不能为空")
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getReason() {
        return reason;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public WsAttendanceBinding(String guid, String serialNumber, String authStatus, String reason) {
        this.guid = guid;
        this.serialNumber = serialNumber;
        this.authStatus = authStatus;
        this.reason = reason;
    }

    public WsAttendanceBinding() {
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsAttendanceBinding{");
        sb.append("guid='").append(guid).append('\'');
        sb.append(", personName='").append(personName).append('\'');
        sb.append(", serialNumber='").append(serialNumber).append('\'');
        sb.append(", deviceName='").append(deviceName).append('\'');
        sb.append(", online='").append(online).append('\'');
        sb.append(", authStatus='").append(authStatus).append('\'');
        sb.append(", reason='").append(reason).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
