package com.jlkj.project.ws.intelligence.domain.vo;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Map;

/**
 * 施工电梯设备工作记录VO t_ws_elevator_manager
 *
 * @author liujie
 * @date 2020-07-03
 */
@ApiModel("施工电梯设备工作记录统计VO")
public class WsElevatorWorkCycleStatisticsVO
{
    private static final long serialVersionUID = 1L;


    /** 项目名称 */
    @ApiModelProperty("项目名称")
    @Excel(name = "项目名称")
    private String projectName;

    /** 项目ID */
    @ApiModelProperty("项目ID")
    @Excel(name = "项目ID")
    private Integer projectId;

    /** 设备名称 */
    @ApiModelProperty("设备名称")
    @Excel(name = "设备名称")
    private String deviceName;

    @ApiModelProperty("设备id")
    @Excel(name = "设备id")
    private Integer elevatorId;

    /** 设备序列号 */
    @ApiModelProperty("设备序列号")
    private String serialNumber;

    /** 司机名称 */
    @ApiModelProperty("司机名称")
    @Excel(name = "司机名称")
    private String driverName;

    /** 身份证号 */
    @ApiModelProperty("身份证号")
    @Excel(name = "身份证号")
    private String idNumber;

    /** 报警次数 */
    @ApiModelProperty("报警次数")
    private Integer alarmingNumber;

    /** 预警次数 */
    @ApiModelProperty("预警次数")
    private Integer preAlarmingNumber;

    /** 累计工作次数 */
    @ApiModelProperty("累计工作次数")
    private Integer totalNumber;

    /** 累计时长（小时） */
    @ApiModelProperty("累计时长（小时）")
    private Float totalHour;

    /** 累计行程（ m)*/
    @ApiModelProperty("累计行程（ m)")
    private Float totalTravel;

    /** 累计载重(t) */
    @ApiModelProperty("累计载重(t)")
    private Float totalWeight;

    /** 监控设备在线状态（1=在线，2离线） */
    @ApiModelProperty("监控设备在线状态（1=在线，2离线）")
    private String onlineStatus;

    @ApiModelProperty("笼号")
    @Excel(name = "笼号",readConverterExp = "0=单笼,1=左笼,2=右笼")
    private String cageMark;

    @ApiModelProperty("设备类型（1=单笼,2=双笼笼）")
    @Excel(name = "设备类型",readConverterExp = "1=单笼,2=双笼笼")
    private String deviceType;


    /** 实时告警状态（0=正常,1=预警，2=报警） */
    @ApiModelProperty("实时告警状态（0=正常,1=预警，2=报警）")
    private String realTimeWarningStatus;

    /** 设备进退场状态（0=离场,1=进场） */
    @ApiModelProperty("设备进退场状态（0=离场,1=进场）")
    private String inOutStatus;

    /** 认证状态（0=未认证，1=已认证） */
    @ApiModelProperty("认证状态（0=未认证，1=已认证）")
    private String authen;

    /** 项目告警占比 */
    @ApiModelProperty("项目告警占比")
    private Integer warningProjectPercent;

    /** 项目告警占比 */
    @ApiModelProperty("项目告警占比 ")
    private Integer alarmingProjectPercent;

    /** 统计日期 */
    @ApiModelProperty("统计日期")
    private Date statisticsDate ;



    /** 请求参数 */
    private Map<String, Object> params;

    public WsElevatorWorkCycleStatisticsVO( WsElevatorManagerVO wsElevatorManagerVO) {
        this.elevatorId = wsElevatorManagerVO.getId();
        this.projectId = wsElevatorManagerVO.getProjectId();
        this.projectName = wsElevatorManagerVO.getProjectName();
        this.serialNumber=wsElevatorManagerVO.getSerialNumber();
        this.cageMark=wsElevatorManagerVO.getCageMark();
        this.inOutStatus =wsElevatorManagerVO.getDeviceStatus();
        this.deviceName = wsElevatorManagerVO.getDeviceName();
        this.deviceType=wsElevatorManagerVO.getDeviceType();
    }


    public WsElevatorWorkCycleStatisticsVO() {
    }


    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAuthen() {
        return authen;
    }

    public void setAuthen(String authen) {
        this.authen = authen;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getCageMark() {
        return cageMark;
    }

    public void setCageMark(String cageMark) {
        this.cageMark = cageMark;
    }

    public String getRealTimeWarningStatus() {
        return realTimeWarningStatus;
    }

    public void setRealTimeWarningStatus(String realTimeWarningStatus) {
        this.realTimeWarningStatus = realTimeWarningStatus;
    }

    public String getInOutStatus() {
        return inOutStatus;
    }

    public void setInOutStatus(String inOutStatus) {
        this.inOutStatus = inOutStatus;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(Date statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    public Integer getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(Integer elevatorId) {
        this.elevatorId = elevatorId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Integer getAlarmingNumber() {
        return alarmingNumber;
    }

    public void setAlarmingNumber(Integer alarmingNumber) {
        this.alarmingNumber = alarmingNumber;
    }

    public Integer getPreAlarmingNumber() {
        return preAlarmingNumber;
    }

    public void setPreAlarmingNumber(Integer preAlarmingNumber) {
        this.preAlarmingNumber = preAlarmingNumber;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Float getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(Float totalHour) {
        this.totalHour = totalHour;
    }

    public Float getTotalTravel() {
        return totalTravel;
    }

    public void setTotalTravel(Float totalTravel) {
        this.totalTravel = totalTravel;
    }

    public Float getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Float totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Integer getWarningProjectPercent() {
        return warningProjectPercent;
    }

    public void setWarningProjectPercent(Integer warningProjectPercent) {
        this.warningProjectPercent = warningProjectPercent;
    }

    public Integer getAlarmingProjectPercent() {
        return alarmingProjectPercent;
    }

    public void setAlarmingProjectPercent(Integer alarmingProjectPercent) {
        this.alarmingProjectPercent = alarmingProjectPercent;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsElevatorWorkCycleStatisticsVO{");
        sb.append("projectName='").append(projectName).append('\'');
        sb.append(", projectId=").append(projectId);
        sb.append(", deviceName='").append(deviceName).append('\'');
        sb.append(", elevatorId=").append(elevatorId);
        sb.append(", serialNumber='").append(serialNumber).append('\'');
        sb.append(", driverName='").append(driverName).append('\'');
        sb.append(", idNumber='").append(idNumber).append('\'');
        sb.append(", alarmingNumber=").append(alarmingNumber);
        sb.append(", preAlarmingNumber=").append(preAlarmingNumber);
        sb.append(", totalNumber=").append(totalNumber);
        sb.append(", totalHour=").append(totalHour);
        sb.append(", totalTravel=").append(totalTravel);
        sb.append(", totalWeight=").append(totalWeight);
        sb.append(", onlineStatus='").append(onlineStatus).append('\'');
        sb.append(", cageMark='").append(cageMark).append('\'');
        sb.append(", realTimeWarningStatus='").append(realTimeWarningStatus).append('\'');
        sb.append(", inOutStatus='").append(inOutStatus).append('\'');
        sb.append(", warningProjectPercent=").append(warningProjectPercent);
        sb.append(", alarmingProjectPercent=").append(alarmingProjectPercent);
        sb.append(", statisticsDate=").append(statisticsDate);
        sb.append(", params=").append(params);
        sb.append('}');
        return sb.toString();
    }
}
