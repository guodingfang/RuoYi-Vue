package com.jlkj.project.ws.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 开启云台控制类
 * @Author pyy
 * @Date 2020/7/10 14:17
 */
@ApiModel("开启云台控制对象")
public class WsStartPTZControl {

    @ApiModelProperty("设备编号")
    private String serialNumber;

    @ApiModelProperty("通道编号")
    private String channelNumber;

    @ApiModelProperty("操作命令：0-上，1-下，2-左，3-右，4-左上，5-左下，6-右上，7-右下，8-放大，9-缩小，10-近焦距，11-远焦距\t")
    private String direction;

    @ApiModelProperty("云台速度：0-慢，1-适中，2-快，海康设备参数不可为0")
    private String speed;

    /**  HLS流畅直播地址*/
    private String hls;
    /** HLS高清直播地址 */
    private String hlsHd;
    /** RTMP流畅直播地址 */
    private String rtmp;
    /** RTMP高清直播地址 */
    private String rtmpHd;


    /** 抓怕图片地址 */
    private String capturePicUrl;

    /** 封面图片地址 */
    private String coverPicUrl;


    public String getHls() {
        return hls;
    }

    public void setHls(String hls) {
        this.hls = hls;
    }

    public String getHlsHd() {
        return hlsHd;
    }

    public void setHlsHd(String hlsHd) {
        this.hlsHd = hlsHd;
    }

    public String getRtmp() {
        return rtmp;
    }

    public void setRtmp(String rtmp) {
        this.rtmp = rtmp;
    }

    public String getRtmpHd() {
        return rtmpHd;
    }

    public void setRtmpHd(String rtmpHd) {
        this.rtmpHd = rtmpHd;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(String channelNumber) {
        this.channelNumber = channelNumber;
    }

    public String getCapturePicUrl() {
        return capturePicUrl;
    }

    public void setCapturePicUrl(String capturePicUrl) {
        this.capturePicUrl = capturePicUrl;
    }

    public String getCoverPicUrl() {
        return coverPicUrl;
    }

    public void setCoverPicUrl(String coverPicUrl) {
        this.coverPicUrl = coverPicUrl;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsStartPTZControl{");
        sb.append("serialNumber='").append(serialNumber).append('\'');
        sb.append(", channelNumber='").append(channelNumber).append('\'');
        sb.append(", direction='").append(direction).append('\'');
        sb.append(", speed='").append(speed).append('\'');
        sb.append(", hls='").append(hls).append('\'');
        sb.append(", hlsHd='").append(hlsHd).append('\'');
        sb.append(", rtmp='").append(rtmp).append('\'');
        sb.append(", rtmpHd='").append(rtmpHd).append('\'');
        sb.append(", capturePicUrl='").append(capturePicUrl).append('\'');
        sb.append(", coverPicUrl='").append(coverPicUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
