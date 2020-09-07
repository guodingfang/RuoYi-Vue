package com.jlkj.project.ws.intelligence.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.project.ws.domain.WsArea;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName WsElevatorMangerDTO
 * @Description 升降机设备管理DTO
 * @Author liujie
 * @Date 2020/7/3 20:59
 **/
@ApiModel("施工电梯设备管理DTO")
public class WsElevatorManagerDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("黑匣子信息")
    private WsBlackBox wsBlackBox;
    @ApiModelProperty("施工电梯设备管理")
    private WsElevatorManager wsElevatorManager;
    @ApiModelProperty("工程场布图")
    private WsProjectFieldLayout wsProjectFieldLayout;

    @ApiModelProperty("施工电梯设备管理")
    private List<WsElevatorManager> elevators;
    @ApiModelProperty("区域")
    private WsArea wsArea;

    @ApiModelProperty("施工电梯安全检测仪列表")
    private List<WsElevatorMinitorDevice> devices;

    @ApiModelProperty("施工单位ID")
    private Integer constructionId;
    @ApiModelProperty("项目ID")
    private Integer projectId;

    @ApiModelProperty("设备名称")
    private  String deviceName;
    @ApiModelProperty("工程名称")
    private String projectName;

    @ApiModelProperty("备案编号")
    private String recordNumber;

    @ApiModelProperty("设备类型")
    private String deviceType;

    @ApiModelProperty("统计日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date statisticsDate;
    @ApiModelProperty("统计天数，负则向前统计，正向后统计")
    private Integer days;

    private Date beginTime;

    private Date endTime;

    private String serialNumber;

    private String areaCode;

    /** 是否标记 */
    @ApiModelProperty("是否标记")
    private String sign;

    @ApiModelProperty("关键字类型")
    private String keyType;

    @ApiModelProperty("关键字名称")
    private String keyWordName;

    @ApiModelProperty("企业Id")
    private Long companyId;

    /** 设备状态(0=离场,1=进场) */
    @ApiModelProperty("设备状态(0=离场,1=进场)")
    private String deviceStatus;

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getKeyWordName() {
        return keyWordName;
    }

    public void setKeyWordName(String keyWordName) {
        this.keyWordName = keyWordName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(Date statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(Integer constructionId) {
        this.constructionId = constructionId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }


    public WsBlackBox getWsBlackBox() {
        return wsBlackBox;
    }

    public void setWsBlackBox(WsBlackBox wsBlackBox) {
        this.wsBlackBox = wsBlackBox;
    }

    public WsElevatorManager getWsElevatorManager() {
        return wsElevatorManager;
    }

    public void setWsElevatorManager(WsElevatorManager wsElevatorManager) {
        this.wsElevatorManager = wsElevatorManager;
    }

    public WsProjectFieldLayout getWsProjectFieldLayout() {
        return wsProjectFieldLayout;
    }

    public void setWsProjectFieldLayout(WsProjectFieldLayout wsProjectFieldLayout) {
        this.wsProjectFieldLayout = wsProjectFieldLayout;
    }

    public List<WsElevatorMinitorDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<WsElevatorMinitorDevice> devices) {
        this.devices = devices;
    }


    public WsArea getWsArea() {
        return wsArea;
    }

    public void setWsArea(WsArea wsArea) {
        this.wsArea = wsArea;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<WsElevatorManager> getElevators() {
        return elevators;
    }

    public void setElevators(List<WsElevatorManager> elevators) {
        this.elevators = elevators;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsElevatorManagerDTO{");
        sb.append("wsBlackBox=").append(wsBlackBox);
        sb.append(", wsElevatorManager=").append(wsElevatorManager);
        sb.append(", wsProjectFieldLayout=").append(wsProjectFieldLayout);
        sb.append(", elevators=").append(elevators);
        sb.append(", wsArea=").append(wsArea);
        sb.append(", devices=").append(devices);
        sb.append(", constructionId=").append(constructionId);
        sb.append(", projectId=").append(projectId);
        sb.append(", deviceName='").append(deviceName).append('\'');
        sb.append(", projectName='").append(projectName).append('\'');
        sb.append(", recordNumber='").append(recordNumber).append('\'');
        sb.append(", deviceType='").append(deviceType).append('\'');
        sb.append(", statisticsDate=").append(statisticsDate);
        sb.append(", days=").append(days);
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", serialNumber='").append(serialNumber).append('\'');
        sb.append(", areaCode='").append(areaCode).append('\'');
        sb.append(", sign='").append(sign).append('\'');
        sb.append(", keyType='").append(keyType).append('\'');
        sb.append(", keyWordName='").append(keyWordName).append('\'');
        sb.append(", companyId=").append(companyId);
        sb.append('}');
        return sb.toString();
    }
}
