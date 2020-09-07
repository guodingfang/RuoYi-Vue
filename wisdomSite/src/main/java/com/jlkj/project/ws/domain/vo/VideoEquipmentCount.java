package com.jlkj.project.ws.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author pyy
 * @Date 2020/7/8 18:55
 */
@ApiModel("视频设备记录数")
public class VideoEquipmentCount  implements Serializable {
    //总记录数
    @ApiModelProperty("设备总记录数")
    private Integer equipmentCount;

    //视频总记录数
    @ApiModelProperty("视频总记录数")
    private Integer videoCount;

    //录像机总记录数
    @ApiModelProperty("录像机总记录数")
    private Integer NVRCount;


    public Integer getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(Integer equipmentCount) {
        this.equipmentCount = equipmentCount;
    }

    public Integer getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }

    public Integer getNVRCount() {
        return NVRCount;
    }

    public void setNVRCount(Integer NVRCount) {
        this.NVRCount = NVRCount;
    }
}
