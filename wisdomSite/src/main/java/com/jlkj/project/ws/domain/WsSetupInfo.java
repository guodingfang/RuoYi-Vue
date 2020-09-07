package com.jlkj.project.ws.domain;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 录像机通道信息
 * @Author pyy
 * @Date 2020/7/9 15:19
 */

@ApiModel("安装信息对象")
public class WsSetupInfo implements Serializable {

    /** 主键id */
    @ApiModelProperty("主键id")
    private Integer id;
    /** 安装位置 */
    @ApiModelProperty("安装位置")
    private String installationPosition;

    /** 通道号 */
    @ApiModelProperty("通道号")
    private Integer channelNumber;

    /** 启用状态 1：启用，2：禁用 */
    @ApiModelProperty("启用状态 1：启用，2：禁用")
    @Excel(name = "启用状态 1：启用，2：禁用")
    private Integer enableStatus;


    /** 工程设备在线状态 1：在线，2：离线 */
    @ApiModelProperty("工程设备在线状态 1：在线，0：离线")
    @Excel(name = "工程设备在线状态 1：在线，0：离线")
    private String onlineStatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstallationPosition() {
        return installationPosition;
    }

    public void setInstallationPosition(String installationPosition) {
        this.installationPosition = installationPosition;
    }

    public Integer getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(Integer channelNumber) {
        this.channelNumber = channelNumber;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
}
