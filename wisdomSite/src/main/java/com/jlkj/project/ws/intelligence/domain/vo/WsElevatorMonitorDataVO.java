package com.jlkj.project.ws.intelligence.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * 施工电梯实时数据管理对象 t_ws_elevator_monitor_dataVO
 *
 * @author liujie
 * @date 2020-07-03
 */
public class WsElevatorMonitorDataVO implements Serializable
{
    private static final long serialVersionUID = 1L;


    /** 黑匣子OID */
    @Excel(name = "黑匣子OID")
    private String blackBoxOid;


    /** 升降机编号 */
    @Excel(name = "升降机编号")
    private String serialNumber;


    /** 上报时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上报时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date insertTime;

    /** 实时起重量 : 单位KG */
    @Excel(name = "实时起重量 : 单位KG")
    private Integer weight;

    /** 重量百分比 : 百分比为1个百分点 */
    @Excel(name = "重量百分比 : 百分比为1个百分点")
    private Integer weightPercent;

    /** 实时人数 */
    @Excel(name = "实时人数")
    private Integer personNum;

    /** 实时高度 : 单位为0.1米 */
    @Excel(name = "实时高度 : 单位为0.1米")
    private Integer height;



    /** 高度百分比  */
    @Excel(name = "高度百分比")
    private Integer heightPercent;

    /** 实时速度 : 0.1米/秒 */
    @Excel(name = "实时速度 : 0.1米/秒")
    private Integer speed;

    /** 实时速度-状态 */
    @Excel(name = "实时速度-状态")
    private Integer speedStatus;

    /** 实时倾斜度 : 倾斜数值减去1500取绝对值再除以100 */
    @Excel(name = "实时倾斜度 : 倾斜数值减去1500取绝对值再除以100")
    private Integer tilt;


    /** 实时倾斜度2 */
    @Excel(name = "实时倾斜度2")
    private Integer tilt2;


    /** 驾驶员身份认证结果 : 0为未认证，非零为驾驶员工号 */
    @Excel(name = "驾驶员身份认证结果 : 0为未认证，非零为驾驶员工号",readConverterExp ="1=已认证,0=未认证")
    private Integer authen;

    /** 前门状态 : 1-开启，0-关闭 */
    @Excel(name = "前门状态 : 1-开启，0-关闭",readConverterExp ="1=开启,0=关闭" )
    private Integer foreDoorStatus;

    /** 后门状态 : 1-开启，0-关闭 */
    @Excel(name = "后门状态 : 1-开启，0-关闭",readConverterExp ="1=开启,0=关闭")
    private Integer behindDoorStatus;

    /** 门锁异常指示 : 0无异常1有异常 */
    @Excel(name = "门锁异常指示 : 0无异常1有异常")
    private Integer lockstatus;

    /** 系统状态-重量 : 0-正常，1-预警，2-报警 */
    @Excel(name = "系统状态-重量 : 0-正常，1-预警，2-报警")
    private Integer systemStatusWeight;

    /** 系统状态-高度限位 : 0-正常，1-预警，2-报警 */
    @Excel(name = "系统状态-高度限位 : 0-正常，1-预警，2-报警")
    private Integer systemStatusHeight;

    /** 系统状态-超速 : 0-正常，1-预警，2-报警 */
    @Excel(name = "系统状态-超速 : 0-正常，1-预警，2-报警")
    private Integer systemStatusSpeed;

    /** 系统状态-人数 : 0-正常，1-预警，2-报警 */
    @Excel(name = "系统状态-人数 : 0-正常，1-预警，2-报警")
    private Integer systemStatusNum;

    /** 系统状态-倾斜 : 0-正常，1-预警，2-报警 */
    @Excel(name = "系统状态-倾斜 : 0-正常，1-预警，2-报警")
    private Integer systemStatusTilt;

    /** 系统状态-前门锁状态 : 0-正常,1-异常 */
    @Excel(name = "系统状态-前门锁状态 : 0-正常,1-异常")
    private Integer systemStatusForelockStatus;

    /** 系统状态-后门锁状态 : 0-正常,1-异常 */
    @Excel(name = "系统状态-后门锁状态 : 0-正常,1-异常")
    private Integer systemStatusBehindlockStatus;

    /** 运行状态 */
    @Excel(name = "运行状态")
    private String  runningStatus;

    /** 异常状态，0-正常，1-异常 */
    @Excel(name = "异常状态，0-正常，1-异常")
    private Integer abnormalStatus;

    private String onlineStatus;


    private Integer maxHeight;

    private Integer maxWeight;


    private Integer maxSpeed;

    private Integer maxPeople;

    private Integer maxTilt;

    public Integer getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Integer maxHeight) {
        this.maxHeight = maxHeight;
    }

    public Integer getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Integer maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Integer getMaxTilt() {
        return maxTilt;
    }

    public void setMaxTilt(Integer maxTilt) {
        this.maxTilt = maxTilt;
    }

    public String getRunningStatus() {
        return runningStatus;
    }

    public void setRunningStatus(String runningStatus) {
        this.runningStatus = runningStatus;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getBlackBoxOid() {
        return blackBoxOid;
    }

    public void setBlackBoxOid(String blackBoxOid) {
        this.blackBoxOid = blackBoxOid;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getWeightPercent() {
        return weightPercent;
    }

    public void setWeightPercent(Integer weightPercent) {
        this.weightPercent = weightPercent;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getSpeedStatus() {
        return speedStatus;
    }

    public void setSpeedStatus(Integer speedStatus) {
        this.speedStatus = speedStatus;
    }

    public Integer getTilt() {
        return tilt;
    }

    public void setTilt(Integer tilt) {
        this.tilt = tilt;
    }

    public Integer getTilt2() {
        return tilt2;
    }

    public void setTilt2(Integer tilt2) {
        this.tilt2 = tilt2;
    }

    public Integer getAuthen() {
        return authen;
    }

    public void setAuthen(Integer authen) {
        this.authen = authen;
    }

    public Integer getForeDoorStatus() {
        return foreDoorStatus;
    }

    public void setForeDoorStatus(Integer foreDoorStatus) {
        this.foreDoorStatus = foreDoorStatus;
    }

    public Integer getBehindDoorStatus() {
        return behindDoorStatus;
    }

    public void setBehindDoorStatus(Integer behindDoorStatus) {
        this.behindDoorStatus = behindDoorStatus;
    }

    public Integer getLockstatus() {
        return lockstatus;
    }

    public void setLockstatus(Integer lockstatus) {
        this.lockstatus = lockstatus;
    }

    public Integer getSystemStatusWeight() {
        return systemStatusWeight;
    }

    public void setSystemStatusWeight(Integer systemStatusWeight) {
        this.systemStatusWeight = systemStatusWeight;
    }

    public Integer getSystemStatusHeight() {
        return systemStatusHeight;
    }

    public void setSystemStatusHeight(Integer systemStatusHeight) {
        this.systemStatusHeight = systemStatusHeight;
    }

    public Integer getSystemStatusSpeed() {
        return systemStatusSpeed;
    }

    public void setSystemStatusSpeed(Integer systemStatusSpeed) {
        this.systemStatusSpeed = systemStatusSpeed;
    }

    public Integer getSystemStatusNum() {
        return systemStatusNum;
    }

    public void setSystemStatusNum(Integer systemStatusNum) {
        this.systemStatusNum = systemStatusNum;
    }

    public Integer getSystemStatusTilt() {
        return systemStatusTilt;
    }

    public void setSystemStatusTilt(Integer systemStatusTilt) {
        this.systemStatusTilt = systemStatusTilt;
    }

    public Integer getSystemStatusForelockStatus() {
        return systemStatusForelockStatus;
    }

    public void setSystemStatusForelockStatus(Integer systemStatusForelockStatus) {
        this.systemStatusForelockStatus = systemStatusForelockStatus;
    }

    public Integer getSystemStatusBehindlockStatus() {
        return systemStatusBehindlockStatus;
    }

    public void setSystemStatusBehindlockStatus(Integer systemStatusBehindlockStatus) {
        this.systemStatusBehindlockStatus = systemStatusBehindlockStatus;
    }


    public Integer getAbnormalStatus() {
        return abnormalStatus;
    }

    public void setAbnormalStatus(Integer abnormalStatus) {
        this.abnormalStatus = abnormalStatus;
    }

    public Integer getHeightPercent() {

        return heightPercent;
    }

    public void setHeightPercent(Integer heightPercent) {
        this.heightPercent = heightPercent;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsElevatorMonitorDataVO{");
        sb.append("blackBoxOid='").append(blackBoxOid).append('\'');
        sb.append(", serialNumber='").append(serialNumber).append('\'');
        sb.append(", insertTime=").append(insertTime);
        sb.append(", weight=").append(weight);
        sb.append(", weightPercent=").append(weightPercent);
        sb.append(", personNum=").append(personNum);
        sb.append(", height=").append(height);
        sb.append(", heightPercent=").append(heightPercent);
        sb.append(", speed=").append(speed);
        sb.append(", speedStatus=").append(speedStatus);
        sb.append(", tilt=").append(tilt);
        sb.append(", tilt2=").append(tilt2);
        sb.append(", authen=").append(authen);
        sb.append(", foreDoorStatus=").append(foreDoorStatus);
        sb.append(", behindDoorStatus=").append(behindDoorStatus);
        sb.append(", lockstatus=").append(lockstatus);
        sb.append(", systemStatusWeight=").append(systemStatusWeight);
        sb.append(", systemStatusHeight=").append(systemStatusHeight);
        sb.append(", systemStatusSpeed=").append(systemStatusSpeed);
        sb.append(", systemStatusNum=").append(systemStatusNum);
        sb.append(", systemStatusTilt=").append(systemStatusTilt);
        sb.append(", systemStatusForelockStatus=").append(systemStatusForelockStatus);
        sb.append(", systemStatusBehindlockStatus=").append(systemStatusBehindlockStatus);
        sb.append(", runningStatus='").append(runningStatus).append('\'');
        sb.append(", abnormalStatus=").append(abnormalStatus);
        sb.append(", onlineStatus='").append(onlineStatus).append('\'');
        sb.append(", maxHeight=").append(maxHeight);
        sb.append(", maxWeight=").append(maxWeight);
        sb.append(", maxSpeed=").append(maxSpeed);
        sb.append(", maxPeople=").append(maxPeople);
        sb.append(", maxTilt=").append(maxTilt);
        sb.append('}');
        return sb.toString();
    }
}
