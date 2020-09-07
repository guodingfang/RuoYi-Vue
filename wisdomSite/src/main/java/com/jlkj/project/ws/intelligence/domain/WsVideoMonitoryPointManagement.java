package com.jlkj.project.ws.intelligence.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import com.jlkj.project.ws.domain.WsStartPTZControl;
import com.jlkj.project.ws.domain.WsVideoChannelManagement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**       视频监控点管理对象 对象 t_ws_video_monitory_point_management
    * @Author liujie
    * @Description
    * @Date 2020/7/15 10:31
    * @Param
    * @Return
   **/
@ApiModel("视频监控点管理对象")
public class WsVideoMonitoryPointManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("主键id")
    private Integer id;

    /** 项目id */
    @ApiModelProperty("项目id")
    @Excel(name = "项目id")
    private Integer projectId;

    /** 项目名称 */
    @ApiModelProperty("项目名称")
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /** 监控点名称 */
    @ApiModelProperty(value = "监控点名称", required = true)
    @Excel(name = "监控点名称")
    private String videoMonitoryPointName;


    /** 视频序列号 */
    @ApiModelProperty("视频序列号")
    @Excel(name = "视频序列号")
    private String videoSerialNumber;

    /** 视频通道号 */
    @ApiModelProperty("视频通道号")
    @Excel(name = "视频通道号")
    private Integer videoChannel;

    /** 区域ID */
    @ApiModelProperty("区域ID")
    private Integer zoneId;

    /** 区域名称 */
    @ApiModelProperty("区域名称")
    @Excel(name = "区域名称")
    private String  zoneName;

    /** 启用状态 1：启用，2：禁用 */
    @ApiModelProperty("启用状态 1：启用，2：禁用")
    @Excel(name = "启用状态",readConverterExp = "1=启用，2=禁用")
    private String enabled;

    /** 绑定视频*/
    private WsVideoChannelManagement video;

    /** 直播地址*/
    private WsStartPTZControl wsStartPTZControl;
    /** 视频监控点数量*/
    private Integer videoNumber;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;


    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;


    public Integer getVideoNumber() {
        return videoNumber;
    }

    public void setVideoNumber(Integer videoNumber) {
        this.videoNumber = videoNumber;
    }

    public WsStartPTZControl getWsStartPTZControl() {
        return wsStartPTZControl;
    }

    public void setWsStartPTZControl(WsStartPTZControl wsStartPTZControl) {
        this.wsStartPTZControl = wsStartPTZControl;
    }

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

    @NotBlank(message = "视频监控点不能为空")
    public String getVideoMonitoryPointName() {
        return videoMonitoryPointName;
    }

    public void setVideoMonitoryPointName(String videoMonitoryPointName) {
        this.videoMonitoryPointName = videoMonitoryPointName;
    }

    public String getVideoSerialNumber() {
        return videoSerialNumber;
    }

    public void setVideoSerialNumber(String videoSerialNumber) {
        this.videoSerialNumber = videoSerialNumber;
    }

    @NotNull(message = "监控设备不能为空")
    public Integer getVideoChannel() {
        return videoChannel;
    }

    public void setVideoChannel(Integer videoChannel) {
        this.videoChannel = videoChannel;
    }

    @NotNull(message = "区域名称不能为空")
    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public WsVideoChannelManagement getVideo() {
        return video;
    }

    public void setVideo(WsVideoChannelManagement video) {
        this.video = video;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsVideoMonitoryPointManagement{");
        sb.append("id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", videoMonitoryPointName='").append(videoMonitoryPointName).append('\'');
        sb.append(", videoSerialNumber='").append(videoSerialNumber).append('\'');
        sb.append(", videoChannel=").append(videoChannel);
        sb.append(", zoneId=").append(zoneId);
        sb.append(", zoneName=").append(zoneName);
        sb.append(", enabled='").append(enabled).append('\'');
        sb.append(", video=").append(video);
        sb.append(", wsStartPTZControl=").append(wsStartPTZControl);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append('}');
        return sb.toString();
    }
}
