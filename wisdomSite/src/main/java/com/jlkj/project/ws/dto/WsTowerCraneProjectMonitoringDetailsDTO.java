package com.jlkj.project.ws.dto;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName WsTowerCraneProjectMonitoringDetailsDTO
 * @Description TODO
 * @Author gaowei
 * @Date 2020/7/2 19:20
 * @Version 1.0
 **/
@ApiModel("塔机监测-项目-监控详情DTO")
public class WsTowerCraneProjectMonitoringDetailsDTO extends BaseEntity {

    @ApiModelProperty("塔吊id")
    private Integer towerCraneId;

    @ApiModelProperty("吊钩视频id")
    private Integer hookVideoId;

    @ApiModelProperty("塔机人脸id")
    private String faceId;//塔机人脸id

    @ApiModelProperty("塔机人脸编号")
    private String faceNo;//塔机人脸编号

    @ApiModelProperty("吊钩视频地址")
    private String hookVideoAddress;

    @ApiModelProperty("工作记录：1：今日数据；15:15天数据")
    private String days;

    private String idCardNo;//塔机司机身份证号

    /** 项目id */
    @ApiModelProperty("项目id")
    private Integer projectId;

    /** 监控设备编号 */
    @ApiModelProperty("监控设备编号")
    private String monitoringEquipmentNo;

    /** 设备名称 */
    @ApiModelProperty("设备名称")
    private String towerCraneName;

    @ApiModelProperty("幅度")
    private String ranges;

    @ApiModelProperty("最大幅度")
    private String maxRange;

    @ApiModelProperty("最小幅度")
    private String minRange;

    @ApiModelProperty("转角")
    private String corner;

    @ApiModelProperty("最大转角")
    private String maxCorner;

    @ApiModelProperty("最小转角")
    private String minCorner;

    @ApiModelProperty("吊高")
    private String liftingheight;

    @ApiModelProperty("最大吊高")
    private String maxLiftingheight;

    @ApiModelProperty("最小吊高")
    private String minLiftingheight;

    @ApiModelProperty("风速")
    private String windSpeed;

    @ApiModelProperty("最大风速")
    private String maxWindSpeed;

    @ApiModelProperty("最小风速")
    private String minWindSpeed;

    @ApiModelProperty("吊重")
    private String liftingWeight;

    @ApiModelProperty("当前允许吊重")
    private String allowLiftingWeight;

    @ApiModelProperty("最大吊重")
    private String maxLiftingWeight;

    @ApiModelProperty("最小吊重")
    private String minLiftingWeight;

    @ApiModelProperty("倾角")
    private String angle;

    @ApiModelProperty("最大倾角")
    private String maxAngle;

    @ApiModelProperty("最小倾角")
    private String minAngle;

    @ApiModelProperty("监控状态：高度(0正常 1预警 2报警)")
    private String heightState;

    @ApiModelProperty("监控状态：幅度(0正常 1预警 2报警)")
    private String rangeState;

    @ApiModelProperty("监控状态：回转(0正常 1预警 2报警 )")
    private String rotationState;

    @ApiModelProperty("监控状态：干涉(0正常  1预警  2报警)")
    private String interferenceState;

    private String interferenceStateUpper;//干涉上

    private String interferenceStateLower;//干涉下

    private String interferenceStateFront;//干涉前

    private String interferenceStateAfter;//干涉后

    private String interferenceStateLeft;//干涉左

    private String interferenceStateRight;//干涉右

    @ApiModelProperty("监控状态：防碰撞(0正常  1预警  2报警)")
    private String antiCollisionState;

    private String antiCollisionStateUpper;//防碰撞上

    private String antiCollisionStateLower;//防碰撞下

    private String antiCollisionStateFront;//防碰撞前

    private String antiCollisionStateAfter;//防碰撞后

    private String antiCollisionStateLeft;//防碰撞左

    private String antiCollisionStateRight;//防碰撞右

    @ApiModelProperty("监控状态：风速(0正常 1预警 2报警)")
    private String windSpeedState;

    @ApiModelProperty("监控状态：倾斜(0正常 1预警 2报警)")
    private String tiltState;

    @ApiModelProperty("监控状态：超重(0正常  1预警  2报警)")
    private String overweightState;

    @ApiModelProperty("监控状态：设备在线状态(1-在线，2-离线)")
    private String equipmentOnlineStatus;

    @ApiModelProperty("监控状态：数据更新时间")
    private String updateDate;

    @ApiModelProperty("塔机信息：备案号")
    private String recordNo;

    @ApiModelProperty("塔机信息：塔机型号")
    private String towerCraneModel;

    @ApiModelProperty("塔机信息：产权单位")
    private String propertyUnit;

    @ApiModelProperty("塔机信息：安装单位")
    private String installUnit;

    @ApiModelProperty("塔机信息：违章通知")
    private String violationNotice;

    @ApiModelProperty("司机信息：姓名")
    private String driversName;

    @ApiModelProperty("司机信息：身份证号")
    private String idNumber;

    @ApiModelProperty("司机信息：特种作业号")
    private String specialOperationNumber;

    @ApiModelProperty("司机信息：身份确认时间")
    private String identificationTime;

    @ApiModelProperty("司机信息：照片地址")
    private String photoAddress;

    @ApiModelProperty("搜索条件：时间")
    private String searchTime;

    @ApiModelProperty("工作记录图表：吊重")
    private String tjLiftingWeightChart;

    @ApiModelProperty("工作记录图表：吊程")
    private String tjLiftingDistanceChart;

    @ApiModelProperty("工作记录图表：违章次数")
    private String violationFrequencyChart;

    @ApiModelProperty("工作记录图表：时间")
    private String timeChart;

    @ApiModelProperty("工作记录详情：吊重")
    @Excel(name = "吊重")
    private String tjLiftingWeight;

    @ApiModelProperty("工作记录详情：吊程")
    @Excel(name = "吊程")
    private String tjLiftingDistance;

    @ApiModelProperty("工作记录详情：力矩百分比")
    @Excel(name = "力矩百分比")
    private String tjPercentageMoment;

    @ApiModelProperty("工作记录详情：转角范围")
    @Excel(name = "转角范围")
    private String tjAngleRange;

    @ApiModelProperty("工作记录详情：幅度范围")
    @Excel(name = "幅度范围")
    private String tjAmplitudeRange;

    @ApiModelProperty("工作记录详情：起吊时间")
    @Excel(name = "起吊时间")
    private String tjLiftingTime;

    @ApiModelProperty("工作记录详情：落吊时间")
    @Excel(name = "落吊时间")
    private String tjFallingTime;

    @ApiModelProperty("告警记录：预警次数")
    private String earlyWarningNum;

    @ApiModelProperty("告警记录：报警次数")
    private String callPoliceNum;

    @ApiModelProperty("告警记录：日期时间")
    private String giveAlarmTime;

    @ApiModelProperty("今日告警：预警数")
    private String todayGiveWarning;

    @ApiModelProperty("今日告警：报警数")
    private String todayGiveAlarm;


    @ApiModelProperty("近期违章率")
    private String recentViolationRate;

    @ApiModelProperty("近期违章次数")
    private String recentViolationNum;

    @ApiModelProperty("昨日吊装次数")
    private String yesterdayHoistingNum;

    @ApiModelProperty("昨日吊装状态（60以下 空闲，60-80 正常， 80以上 忙碌）")
    private String yesterdayHoistingState;

    @ApiModelProperty("昨日违章次数")
    private String yesterdayViolationNum;

    @ApiModelProperty("昨日吊装次数")
    private String todayHoistingNum;

    @ApiModelProperty("昨日违章次数")
    private String todayViolationNum;


    @ApiModelProperty("设备状态分析:次数")
    private int frequency;

    @ApiModelProperty("设备状态分析:吊装重量")
    private String analysisLiftingWeight;

    @ApiModelProperty("设备状态分析:载重百分比")
    private String analysisLoadPercentage;

    @ApiModelProperty("设备状态分析:时间")
    private String analysisTime;

    @ApiModelProperty("吊装循环次数:时间")
    private String hoistingCycleTime;

    @ApiModelProperty("吊装循环次数:工作次数")
    private String hoistingCycleNum;

    @ApiModelProperty("吊装循环次数:违章次数")
    private String regulationsNum;

    @ApiModelProperty("吊装循环次数:查询类型(1:7天；2:15天；3:30天)")
    private String queryType;



    public Integer getTowerCraneId() {
        return towerCraneId;
    }

    public void setTowerCraneId(Integer towerCraneId) {
        this.towerCraneId = towerCraneId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getMonitoringEquipmentNo() {
        return monitoringEquipmentNo;
    }

    public void setMonitoringEquipmentNo(String monitoringEquipmentNo) {
        this.monitoringEquipmentNo = monitoringEquipmentNo;
    }

    public String getTowerCraneName() {
        return towerCraneName;
    }

    public void setTowerCraneName(String towerCraneName) {
        this.towerCraneName = towerCraneName;
    }

    public String getRanges() {
        return ranges;
    }

    public void setRanges(String ranges) {
        this.ranges = ranges;
    }

    public String getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(String maxRange) {
        this.maxRange = maxRange;
    }

    public String getMinRange() {
        return minRange;
    }

    public void setMinRange(String minRange) {
        this.minRange = minRange;
    }

    public String getCorner() {
        return corner;
    }

    public void setCorner(String corner) {
        this.corner = corner;
    }

    public String getMaxCorner() {
        return maxCorner;
    }

    public void setMaxCorner(String maxCorner) {
        this.maxCorner = maxCorner;
    }

    public String getMinCorner() {
        return minCorner;
    }

    public void setMinCorner(String minCorner) {
        this.minCorner = minCorner;
    }

    public String getLiftingheight() {
        return liftingheight;
    }

    public void setLiftingheight(String liftingheight) {
        this.liftingheight = liftingheight;
    }

    public String getMaxLiftingheight() {
        return maxLiftingheight;
    }

    public void setMaxLiftingheight(String maxLiftingheight) {
        this.maxLiftingheight = maxLiftingheight;
    }

    public String getMinLiftingheight() {
        return minLiftingheight;
    }

    public void setMinLiftingheight(String minLiftingheight) {
        this.minLiftingheight = minLiftingheight;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getMaxWindSpeed() {
        return maxWindSpeed;
    }

    public void setMaxWindSpeed(String maxWindSpeed) {
        this.maxWindSpeed = maxWindSpeed;
    }

    public String getMinWindSpeed() {
        return minWindSpeed;
    }

    public void setMinWindSpeed(String minWindSpeed) {
        this.minWindSpeed = minWindSpeed;
    }

    public String getLiftingWeight() {
        return liftingWeight;
    }

    public void setLiftingWeight(String liftingWeight) {
        this.liftingWeight = liftingWeight;
    }

    public String getMaxLiftingWeight() {
        return maxLiftingWeight;
    }

    public void setMaxLiftingWeight(String maxLiftingWeight) {
        this.maxLiftingWeight = maxLiftingWeight;
    }

    public String getMinLiftingWeight() {
        return minLiftingWeight;
    }

    public void setMinLiftingWeight(String minLiftingWeight) {
        this.minLiftingWeight = minLiftingWeight;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    public String getMaxAngle() {
        return maxAngle;
    }

    public void setMaxAngle(String maxAngle) {
        this.maxAngle = maxAngle;
    }

    public String getMinAngle() {
        return minAngle;
    }

    public void setMinAngle(String minAngle) {
        this.minAngle = minAngle;
    }

    public String getHeightState() {
        return heightState;
    }

    public void setHeightState(String heightState) {
        this.heightState = heightState;
    }

    public String getRangeState() {
        return rangeState;
    }

    public void setRangeState(String rangeState) {
        this.rangeState = rangeState;
    }

    public String getRotationState() {
        return rotationState;
    }

    public void setRotationState(String rotationState) {
        this.rotationState = rotationState;
    }

    public String getInterferenceState() {
        return interferenceState;
    }

    public void setInterferenceState(String interferenceState) {
        this.interferenceState = interferenceState;
    }

    public String getAntiCollisionState() {
        return antiCollisionState;
    }

    public void setAntiCollisionState(String antiCollisionState) {
        this.antiCollisionState = antiCollisionState;
    }

    public String getWindSpeedState() {
        return windSpeedState;
    }

    public void setWindSpeedState(String windSpeedState) {
        this.windSpeedState = windSpeedState;
    }

    public String getTiltState() {
        return tiltState;
    }

    public void setTiltState(String tiltState) {
        this.tiltState = tiltState;
    }

    public String getOverweightState() {
        return overweightState;
    }

    public void setOverweightState(String overweightState) {
        this.overweightState = overweightState;
    }

    public String getEquipmentOnlineStatus() {
        return equipmentOnlineStatus;
    }

    public void setEquipmentOnlineStatus(String equipmentOnlineStatus) {
        this.equipmentOnlineStatus = equipmentOnlineStatus;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getTowerCraneModel() {
        return towerCraneModel;
    }

    public void setTowerCraneModel(String towerCraneModel) {
        this.towerCraneModel = towerCraneModel;
    }

    public String getPropertyUnit() {
        return propertyUnit;
    }

    public void setPropertyUnit(String propertyUnit) {
        this.propertyUnit = propertyUnit;
    }

    public String getInstallUnit() {
        return installUnit;
    }

    public void setInstallUnit(String installUnit) {
        this.installUnit = installUnit;
    }

    public String getDriversName() {
        return driversName;
    }

    public void setDriversName(String driversName) {
        this.driversName = driversName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSpecialOperationNumber() {
        return specialOperationNumber;
    }

    public void setSpecialOperationNumber(String specialOperationNumber) {
        this.specialOperationNumber = specialOperationNumber;
    }

    public String getIdentificationTime() {
        return identificationTime;
    }

    public void setIdentificationTime(String identificationTime) {
        this.identificationTime = identificationTime;
    }

    public String getPhotoAddress() {
        return photoAddress;
    }

    public void setPhotoAddress(String photoAddress) {
        this.photoAddress = photoAddress;
    }

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }

    public String getTjLiftingWeight() {
        return tjLiftingWeight;
    }

    public void setTjLiftingWeight(String tjLiftingWeight) {
        this.tjLiftingWeight = tjLiftingWeight;
    }

    public String getTjLiftingDistance() {
        return tjLiftingDistance;
    }

    public void setTjLiftingDistance(String tjLiftingDistance) {
        this.tjLiftingDistance = tjLiftingDistance;
    }

    public String getTjPercentageMoment() {
        return tjPercentageMoment;
    }

    public void setTjPercentageMoment(String tjPercentageMoment) {
        this.tjPercentageMoment = tjPercentageMoment;
    }

    public String getTjAngleRange() {
        return tjAngleRange;
    }

    public void setTjAngleRange(String tjAngleRange) {
        this.tjAngleRange = tjAngleRange;
    }

    public String getTjAmplitudeRange() {
        return tjAmplitudeRange;
    }

    public void setTjAmplitudeRange(String tjAmplitudeRange) {
        this.tjAmplitudeRange = tjAmplitudeRange;
    }

    public String getTjLiftingTime() {
        return tjLiftingTime;
    }

    public void setTjLiftingTime(String tjLiftingTime) {
        this.tjLiftingTime = tjLiftingTime;
    }

    public String getTjFallingTime() {
        return tjFallingTime;
    }

    public void setTjFallingTime(String tjFallingTime) {
        this.tjFallingTime = tjFallingTime;
    }

    public Integer getHookVideoId() {
        return hookVideoId;
    }

    public void setHookVideoId(Integer hookVideoId) {
        this.hookVideoId = hookVideoId;
    }

    public String getHookVideoAddress() {
        return hookVideoAddress;
    }

    public void setHookVideoAddress(String hookVideoAddress) {
        this.hookVideoAddress = hookVideoAddress;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getFaceNo() {
        return faceNo;
    }

    public void setFaceNo(String faceNo) {
        this.faceNo = faceNo;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getInterferenceStateUpper() {
        return interferenceStateUpper;
    }

    public void setInterferenceStateUpper(String interferenceStateUpper) {
        this.interferenceStateUpper = interferenceStateUpper;
    }

    public String getInterferenceStateLower() {
        return interferenceStateLower;
    }

    public void setInterferenceStateLower(String interferenceStateLower) {
        this.interferenceStateLower = interferenceStateLower;
    }

    public String getInterferenceStateFront() {
        return interferenceStateFront;
    }

    public void setInterferenceStateFront(String interferenceStateFront) {
        this.interferenceStateFront = interferenceStateFront;
    }

    public String getInterferenceStateAfter() {
        return interferenceStateAfter;
    }

    public void setInterferenceStateAfter(String interferenceStateAfter) {
        this.interferenceStateAfter = interferenceStateAfter;
    }

    public String getInterferenceStateLeft() {
        return interferenceStateLeft;
    }

    public void setInterferenceStateLeft(String interferenceStateLeft) {
        this.interferenceStateLeft = interferenceStateLeft;
    }

    public String getInterferenceStateRight() {
        return interferenceStateRight;
    }

    public void setInterferenceStateRight(String interferenceStateRight) {
        this.interferenceStateRight = interferenceStateRight;
    }

    public String getAntiCollisionStateUpper() {
        return antiCollisionStateUpper;
    }

    public void setAntiCollisionStateUpper(String antiCollisionStateUpper) {
        this.antiCollisionStateUpper = antiCollisionStateUpper;
    }

    public String getAntiCollisionStateLower() {
        return antiCollisionStateLower;
    }

    public void setAntiCollisionStateLower(String antiCollisionStateLower) {
        this.antiCollisionStateLower = antiCollisionStateLower;
    }

    public String getAntiCollisionStateFront() {
        return antiCollisionStateFront;
    }

    public void setAntiCollisionStateFront(String antiCollisionStateFront) {
        this.antiCollisionStateFront = antiCollisionStateFront;
    }

    public String getAntiCollisionStateAfter() {
        return antiCollisionStateAfter;
    }

    public void setAntiCollisionStateAfter(String antiCollisionStateAfter) {
        this.antiCollisionStateAfter = antiCollisionStateAfter;
    }

    public String getAntiCollisionStateLeft() {
        return antiCollisionStateLeft;
    }

    public void setAntiCollisionStateLeft(String antiCollisionStateLeft) {
        this.antiCollisionStateLeft = antiCollisionStateLeft;
    }

    public String getAntiCollisionStateRight() {
        return antiCollisionStateRight;
    }

    public void setAntiCollisionStateRight(String antiCollisionStateRight) {
        this.antiCollisionStateRight = antiCollisionStateRight;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTjLiftingWeightChart() {
        return tjLiftingWeightChart;
    }

    public void setTjLiftingWeightChart(String tjLiftingWeightChart) {
        this.tjLiftingWeightChart = tjLiftingWeightChart;
    }

    public String getTjLiftingDistanceChart() {
        return tjLiftingDistanceChart;
    }

    public void setTjLiftingDistanceChart(String tjLiftingDistanceChart) {
        this.tjLiftingDistanceChart = tjLiftingDistanceChart;
    }

    public String getViolationFrequencyChart() {
        return violationFrequencyChart;
    }

    public void setViolationFrequencyChart(String violationFrequencyChart) {
        this.violationFrequencyChart = violationFrequencyChart;
    }

    public String getTimeChart() {
        return timeChart;
    }

    public void setTimeChart(String timeChart) {
        this.timeChart = timeChart;
    }

    public String getEarlyWarningNum() {
        return earlyWarningNum;
    }

    public void setEarlyWarningNum(String earlyWarningNum) {
        this.earlyWarningNum = earlyWarningNum;
    }

    public String getCallPoliceNum() {
        return callPoliceNum;
    }

    public void setCallPoliceNum(String callPoliceNum) {
        this.callPoliceNum = callPoliceNum;
    }

    public String getGiveAlarmTime() {
        return giveAlarmTime;
    }

    public void setGiveAlarmTime(String giveAlarmTime) {
        this.giveAlarmTime = giveAlarmTime;
    }

    public String getTodayGiveAlarm() {
        return todayGiveAlarm;
    }

    public void setTodayGiveAlarm(String todayGiveAlarm) {
        this.todayGiveAlarm = todayGiveAlarm;
    }

    public String getTodayGiveWarning() {
        return todayGiveWarning;
    }

    public void setTodayGiveWarning(String todayGiveWarning) {
        this.todayGiveWarning = todayGiveWarning;
    }

    public String getAllowLiftingWeight() {
        return allowLiftingWeight;
    }

    public void setAllowLiftingWeight(String allowLiftingWeight) {
        this.allowLiftingWeight = allowLiftingWeight;
    }

    public String getViolationNotice() {
        return violationNotice;
    }

    public void setViolationNotice(String violationNotice) {
        this.violationNotice = violationNotice;
    }

    public String getRecentViolationRate() {
        return recentViolationRate;
    }

    public void setRecentViolationRate(String recentViolationRate) {
        this.recentViolationRate = recentViolationRate;
    }

    public String getRecentViolationNum() {
        return recentViolationNum;
    }

    public void setRecentViolationNum(String recentViolationNum) {
        this.recentViolationNum = recentViolationNum;
    }

    public String getYesterdayHoistingNum() {
        return yesterdayHoistingNum;
    }

    public void setYesterdayHoistingNum(String yesterdayHoistingNum) {
        this.yesterdayHoistingNum = yesterdayHoistingNum;
    }

    public String getYesterdayHoistingState() {
        return yesterdayHoistingState;
    }

    public void setYesterdayHoistingState(String yesterdayHoistingState) {
        this.yesterdayHoistingState = yesterdayHoistingState;
    }

    public String getYesterdayViolationNum() {
        return yesterdayViolationNum;
    }

    public void setYesterdayViolationNum(String yesterdayViolationNum) {
        this.yesterdayViolationNum = yesterdayViolationNum;
    }

    public String getTodayHoistingNum() {
        return todayHoistingNum;
    }

    public void setTodayHoistingNum(String todayHoistingNum) {
        this.todayHoistingNum = todayHoistingNum;
    }

    public String getTodayViolationNum() {
        return todayViolationNum;
    }

    public void setTodayViolationNum(String todayViolationNum) {
        this.todayViolationNum = todayViolationNum;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getAnalysisLiftingWeight() {
        return analysisLiftingWeight;
    }

    public void setAnalysisLiftingWeight(String analysisLiftingWeight) {
        this.analysisLiftingWeight = analysisLiftingWeight;
    }

    public String getAnalysisLoadPercentage() {
        return analysisLoadPercentage;
    }

    public void setAnalysisLoadPercentage(String analysisLoadPercentage) {
        this.analysisLoadPercentage = analysisLoadPercentage;
    }

    public String getAnalysisTime() {
        return analysisTime;
    }

    public void setAnalysisTime(String analysisTime) {
        this.analysisTime = analysisTime;
    }

    public String getHoistingCycleTime() {
        return hoistingCycleTime;
    }

    public void setHoistingCycleTime(String hoistingCycleTime) {
        this.hoistingCycleTime = hoistingCycleTime;
    }

    public String getHoistingCycleNum() {
        return hoistingCycleNum;
    }

    public void setHoistingCycleNum(String hoistingCycleNum) {
        this.hoistingCycleNum = hoistingCycleNum;
    }

    public String getRegulationsNum() {
        return regulationsNum;
    }

    public void setRegulationsNum(String regulationsNum) {
        this.regulationsNum = regulationsNum;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }
}
