package com.jlkj.project.ws.intelligence.dto;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName WsDustMonitoringDetailsDTO
 * @Description TODO
 * @Author gaowei
 * @Date 2020/7/1 11:00
 * @Version 1.0
 **/
@ApiModel("扬尘监测-企业-设备详情DTO")
public class WsDustMonitoringDetailsDTO extends BaseEntity {

    /** 项目id */
    @ApiModelProperty("项目id")
    private Integer projectId;

    /** 项目负责人 */
    @ApiModelProperty("项目负责人")
    private String projectLeader;

    /** 监测设备 */
    @ApiModelProperty("监测设备")
    private String monitoringEquipmentNo;

    /** 监测视频id */
    @ApiModelProperty("监测视频id")
    private String videoEquipmentId;

    /** 监测视频地址 */
    @ApiModelProperty("监测视频地址")
    private String videoEquipmentAddress;

    /** 报警状态码 */
    private String alarmStatusCode;

    /** 今日环境监测实时总次数 */
    private String totalNum;

    /** 今日扬尘达标率 */
    @ApiModelProperty("今日扬尘达标率")
    private String todayDustControlRate;

    /** 今日噪音达标率 */
    @ApiModelProperty("今日噪音达标率")
    private String todayNoiseControlRate;

    /** 本次数据更新时间 */
    @ApiModelProperty("本次数据更新时间")
    private String updateDate;

    /** 无监测的项目数 */
    @ApiModelProperty("无监测的项目数")
    private int noMonitoringProjectNum;

    /** 项目名称*/
    @ApiModelProperty("环境监测列表：项目名称")
    private String projectName;

    @ApiModelProperty("环境监测列表：今日报警次数")
    private String todayAlarmNum;

    @ApiModelProperty("环境监测列表：PM2.5")
    private String pm25;

    @ApiModelProperty("环境监测列表：PM10")
    private String pm10;

    @ApiModelProperty("环境监测列表：噪音")
    private String noise;

    @ApiModelProperty("环境监测列表：温度")
    private String temperatureType;

    @ApiModelProperty("环境监测列表：湿度")
    private String humidityType;

    @ApiModelProperty("环境监测列表：风速")
    private String windSpeed;


    @ApiModelProperty("监测点名称")
    private String monitoringPointName;

    @ApiModelProperty("在线离线名称")
    private String onlineOfflineName;

    @ApiModelProperty("项目扬尘监测-企业-环境监测：PM2.5")
    private String pmTwentyFive;

    @ApiModelProperty("项目扬尘监测-企业-环境监测：PM10")
    private String pmTen;

    @ApiModelProperty("项目扬尘监测-企业-环境监测：噪音")
    private String strepitus;

    @ApiModelProperty("项目扬尘监测-企业-环境监测：温度")
    private String temperature;

    @ApiModelProperty("项目扬尘监测-企业-环境监测：湿度")
    private String humidity;

    @ApiModelProperty("项目扬尘监测-企业-环境监测：风速")
    private String windVelocity;

    @ApiModelProperty("项目扬尘监测-企业-环境监测：风向")
    private String windDirection;

    @ApiModelProperty("项目扬尘监测-企业-监测统计：时间")
    private String dustDate;

    @ApiModelProperty("项目扬尘监测-企业-监测统计：PM2.5")
    private String dustPm25;

    @ApiModelProperty("项目扬尘监测-企业-监测统计：PM10")
    private String dustPm10;

    @ApiModelProperty("项目扬尘监测-企业-监测统计：噪音")
    private String dustStrepitus;

    @ApiModelProperty("项目扬尘监测-企业-监测统计：温度")
    private String dustTemperature;

    @ApiModelProperty("项目扬尘监测-企业-监测统计：风速")
    private String dustWindSpeed;

    /** 设备序列号 */
    @ApiModelProperty("设备序列号")
    private String equipmentSerialnumber;

    /** 通道号 */
    @ApiModelProperty("通道号")
    private Integer channelNumber;


    public String getTemperatureType() {
        return temperatureType;
    }

    public void setTemperatureType(String temperatureType) {
        this.temperatureType = temperatureType;
    }

    public String getHumidityType() {
        return humidityType;
    }

    public void setHumidityType(String humidityType) {
        this.humidityType = humidityType;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getTodayDustControlRate() {
        return todayDustControlRate;
    }

    public void setTodayDustControlRate(String todayDustControlRate) {
        this.todayDustControlRate = todayDustControlRate;
    }

    public String getTodayNoiseControlRate() {
        return todayNoiseControlRate;
    }

    public void setTodayNoiseControlRate(String todayNoiseControlRate) {
        this.todayNoiseControlRate = todayNoiseControlRate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public int getNoMonitoringProjectNum() {
        return noMonitoringProjectNum;
    }

    public void setNoMonitoringProjectNum(int noMonitoringProjectNum) {
        this.noMonitoringProjectNum = noMonitoringProjectNum;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTodayAlarmNum() {
        return todayAlarmNum;
    }

    public void setTodayAlarmNum(String todayAlarmNum) {
        this.todayAlarmNum = todayAlarmNum;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getNoise() {
        return noise;
    }

    public void setNoise(String noise) {
        this.noise = noise;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getMonitoringEquipmentNo() {
        return monitoringEquipmentNo;
    }

    public void setMonitoringEquipmentNo(String monitoringEquipmentNo) {
        this.monitoringEquipmentNo = monitoringEquipmentNo;
    }

    public String getAlarmStatusCode() {
        return alarmStatusCode;
    }

    public void setAlarmStatusCode(String alarmStatusCode) {
        this.alarmStatusCode = alarmStatusCode;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    public String getMonitoringPointName() {
        return monitoringPointName;
    }

    public void setMonitoringPointName(String monitoringPointName) {
        this.monitoringPointName = monitoringPointName;
    }

    public String getPmTwentyFive() {
        return pmTwentyFive;
    }

    public void setPmTwentyFive(String pmTwentyFive) {
        this.pmTwentyFive = pmTwentyFive;
    }

    public String getPmTen() {
        return pmTen;
    }

    public void setPmTen(String pmTen) {
        this.pmTen = pmTen;
    }

    public String getStrepitus() {
        return strepitus;
    }

    public void setStrepitus(String strepitus) {
        this.strepitus = strepitus;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindVelocity() {
        return windVelocity;
    }

    public void setWindVelocity(String windVelocity) {
        this.windVelocity = windVelocity;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getDustDate() {
        return dustDate;
    }

    public void setDustDate(String dustDate) {
        this.dustDate = dustDate;
    }

    public String getDustPm25() {
        return dustPm25;
    }

    public void setDustPm25(String dustPm25) {
        this.dustPm25 = dustPm25;
    }

    public String getDustPm10() {
        return dustPm10;
    }

    public void setDustPm10(String dustPm10) {
        this.dustPm10 = dustPm10;
    }

    public String getDustStrepitus() {
        return dustStrepitus;
    }

    public void setDustStrepitus(String dustStrepitus) {
        this.dustStrepitus = dustStrepitus;
    }

    public String getDustTemperature() {
        return dustTemperature;
    }

    public void setDustTemperature(String dustTemperature) {
        this.dustTemperature = dustTemperature;
    }

    public String getDustWindSpeed() {
        return dustWindSpeed;
    }

    public void setDustWindSpeed(String dustWindSpeed) {
        this.dustWindSpeed = dustWindSpeed;
    }

    public String getOnlineOfflineName() {
        return onlineOfflineName;
    }

    public void setOnlineOfflineName(String onlineOfflineName) {
        this.onlineOfflineName = onlineOfflineName;
    }

    public String getVideoEquipmentId() {
        return videoEquipmentId;
    }

    public void setVideoEquipmentId(String videoEquipmentId) {
        this.videoEquipmentId = videoEquipmentId;
    }

    public String getVideoEquipmentAddress() {
        return videoEquipmentAddress;
    }

    public void setVideoEquipmentAddress(String videoEquipmentAddress) {
        this.videoEquipmentAddress = videoEquipmentAddress;
    }

    public String getEquipmentSerialnumber() {
        return equipmentSerialnumber;
    }

    public void setEquipmentSerialnumber(String equipmentSerialnumber) {
        this.equipmentSerialnumber = equipmentSerialnumber;
    }

    public Integer getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(Integer channelNumber) {
        this.channelNumber = channelNumber;
    }
}
