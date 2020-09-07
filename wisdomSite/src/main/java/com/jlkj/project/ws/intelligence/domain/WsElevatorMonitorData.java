package com.jlkj.project.ws.intelligence.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 施工电梯实时数据管理对象 t_ws_elevator_monitor_data
 *
 * @author liujie
 * @date 2020-07-03
 */
public class WsElevatorMonitorData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String oid;

    /** 黑匣子OID */
    @Excel(name = "黑匣子OID")
    private String blackBoxOid;

    /** 黑匣子注册记录OID */
    @Excel(name = "黑匣子注册记录OID")
    private String blackBoxRrOid;

    /** 工程编号 */
    private String projectOid;

    /** 升降机编号 */
    @Excel(name = "升降机编号")
    private String serialNumber;

    /** 主机编号 */
    @Excel(name = "主机编号")
    private String hostSn;

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

    /** 高度百分比 : 百分比为1个百分点 */
    @Excel(name = "高度百分比 : 百分比为1个百分点")
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

    /** 倾斜百分比 : 单位为0.01度 */
    @Excel(name = "倾斜百分比 : 单位为0.01度")
    private Integer tiltPercent;

    /** 实时倾斜度2 */
    @Excel(name = "实时倾斜度2")
    private Integer tilt2;

    /** 倾斜百分比2 */
    @Excel(name = "倾斜百分比2")
    private Integer tilt2Percent;

    /** 驾驶员身份认证结果 : 0为未认证，非零为驾驶员工号 */
    @Excel(name = "驾驶员身份认证结果 : 0为未认证，非零为驾驶员工号")
    private Integer authen;

    /** 前门状态 : 1-开启，0-关闭 */
    @Excel(name = "前门状态 : 1-开启，0-关闭")
    private Integer foreDoorStatus;

    /** 后门状态 : 1-开启，0-关闭 */
    @Excel(name = "后门状态 : 1-开启，0-关闭")
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

    /** 预警状态 */
    @Excel(name = "预警状态")
    private Integer warningStatus;

    /** 报警状态 */
    @Excel(name = "报警状态")
    private Integer alarmStatus;

    /** 异常状态，0-正常，1-异常 */
    @Excel(name = "异常状态，0-正常，1-异常")
    private Integer abnormalStatus;

    /** 消息体 */
    @Excel(name = "消息体")
    private String messageBody;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateDate;

    public void setOid(String oid)
    {
        this.oid = oid;
    }

    public String getOid()
    {
        return oid;
    }
    public void setBlackBoxOid(String blackBoxOid)
    {
        this.blackBoxOid = blackBoxOid;
    }

    public String getBlackBoxOid()
    {
        return blackBoxOid;
    }
    public void setBlackBoxRrOid(String blackBoxRrOid)
    {
        this.blackBoxRrOid = blackBoxRrOid;
    }

    public String getBlackBoxRrOid()
    {
        return blackBoxRrOid;
    }
    public void setProjectOid(String projectOid)
    {
        this.projectOid = projectOid;
    }

    public String getProjectOid()
    {
        return projectOid;
    }
    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }
    public void setHostSn(String hostSn)
    {
        this.hostSn = hostSn;
    }

    public String getHostSn()
    {
        return hostSn;
    }
    public void setInsertTime(Date insertTime)
    {
        this.insertTime = insertTime;
    }

    public Date getInsertTime()
    {
        return insertTime;
    }
    public void setWeight(Integer weight)
    {
        this.weight = weight;
    }

    public Integer getWeight()
    {
        return weight;
    }
    public void setWeightPercent(Integer weightPercent)
    {
        this.weightPercent = weightPercent;
    }

    public Integer getWeightPercent()
    {
        return weightPercent;
    }
    public void setPersonNum(Integer personNum)
    {
        this.personNum = personNum;
    }

    public Integer getPersonNum()
    {
        return personNum;
    }
    public void setHeight(Integer height)
    {
        this.height = height;
    }

    public Integer getHeight()
    {
        return height;
    }
    public void setHeightPercent(Integer heightPercent)
    {
        this.heightPercent = heightPercent;
    }

    public Integer getHeightPercent()
    {
        return heightPercent;
    }
    public void setSpeed(Integer speed)
    {
        this.speed = speed;
    }

    public Integer getSpeed()
    {
        return speed;
    }
    public void setSpeedStatus(Integer speedStatus)
    {
        this.speedStatus = speedStatus;
    }

    public Integer getSpeedStatus()
    {
        return speedStatus;
    }
    public void setTilt(Integer tilt)
    {
        this.tilt = tilt;
    }

    public Integer getTilt()
    {
        return tilt;
    }
    public void setTiltPercent(Integer tiltPercent)
    {
        this.tiltPercent = tiltPercent;
    }

    public Integer getTiltPercent()
    {
        return tiltPercent;
    }
    public void setTilt2(Integer tilt2)
    {
        this.tilt2 = tilt2;
    }

    public Integer getTilt2()
    {
        return tilt2;
    }
    public void setTilt2Percent(Integer tilt2Percent)
    {
        this.tilt2Percent = tilt2Percent;
    }

    public Integer getTilt2Percent()
    {
        return tilt2Percent;
    }
    public void setAuthen(Integer authen)
    {
        this.authen = authen;
    }

    public Integer getAuthen()
    {
        return authen;
    }
    public void setForeDoorStatus(Integer foreDoorStatus)
    {
        this.foreDoorStatus = foreDoorStatus;
    }

    public Integer getForeDoorStatus()
    {
        return foreDoorStatus;
    }
    public void setBehindDoorStatus(Integer behindDoorStatus)
    {
        this.behindDoorStatus = behindDoorStatus;
    }

    public Integer getBehindDoorStatus()
    {
        return behindDoorStatus;
    }
    public void setLockstatus(Integer lockstatus)
    {
        this.lockstatus = lockstatus;
    }

    public Integer getLockstatus()
    {
        return lockstatus;
    }
    public void setSystemStatusWeight(Integer systemStatusWeight)
    {
        this.systemStatusWeight = systemStatusWeight;
    }

    public Integer getSystemStatusWeight()
    {
        return systemStatusWeight;
    }
    public void setSystemStatusHeight(Integer systemStatusHeight)
    {
        this.systemStatusHeight = systemStatusHeight;
    }

    public Integer getSystemStatusHeight()
    {
        return systemStatusHeight;
    }
    public void setSystemStatusSpeed(Integer systemStatusSpeed)
    {
        this.systemStatusSpeed = systemStatusSpeed;
    }

    public Integer getSystemStatusSpeed()
    {
        return systemStatusSpeed;
    }
    public void setSystemStatusNum(Integer systemStatusNum)
    {
        this.systemStatusNum = systemStatusNum;
    }

    public Integer getSystemStatusNum()
    {
        return systemStatusNum;
    }
    public void setSystemStatusTilt(Integer systemStatusTilt)
    {
        this.systemStatusTilt = systemStatusTilt;
    }

    public Integer getSystemStatusTilt()
    {
        return systemStatusTilt;
    }
    public void setSystemStatusForelockStatus(Integer systemStatusForelockStatus)
    {
        this.systemStatusForelockStatus = systemStatusForelockStatus;
    }

    public Integer getSystemStatusForelockStatus()
    {
        return systemStatusForelockStatus;
    }
    public void setSystemStatusBehindlockStatus(Integer systemStatusBehindlockStatus)
    {
        this.systemStatusBehindlockStatus = systemStatusBehindlockStatus;
    }

    public Integer getSystemStatusBehindlockStatus()
    {
        return systemStatusBehindlockStatus;
    }
    public void setWarningStatus(Integer warningStatus)
    {
        this.warningStatus = warningStatus;
    }

    public Integer getWarningStatus()
    {
        return warningStatus;
    }
    public void setAlarmStatus(Integer alarmStatus)
    {
        this.alarmStatus = alarmStatus;
    }

    public Integer getAlarmStatus()
    {
        return alarmStatus;
    }
    public void setAbnormalStatus(Integer abnormalStatus)
    {
        this.abnormalStatus = abnormalStatus;
    }

    public Integer getAbnormalStatus()
    {
        return abnormalStatus;
    }
    public void setMessageBody(String messageBody)
    {
        this.messageBody = messageBody;
    }

    public String getMessageBody()
    {
        return messageBody;
    }
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Date getCreateDate()
    {
        return createDate;
    }
    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate()
    {
        return updateDate;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsElevatorMonitorData{");
        sb.append("oid='").append(oid).append('\'');
        sb.append(", blackBoxOid='").append(blackBoxOid).append('\'');
        sb.append(", blackBoxRrOid='").append(blackBoxRrOid).append('\'');
        sb.append(", projectOid='").append(projectOid).append('\'');
        sb.append(", serialNumber='").append(serialNumber).append('\'');
        sb.append(", hostSn='").append(hostSn).append('\'');
        sb.append(", insertTime=").append(insertTime);
        sb.append(", weight=").append(weight);
        sb.append(", weightPercent=").append(weightPercent);
        sb.append(", personNum=").append(personNum);
        sb.append(", height=").append(height);
        sb.append(", heightPercent=").append(heightPercent);
        sb.append(", speed=").append(speed);
        sb.append(", speedStatus=").append(speedStatus);
        sb.append(", tilt=").append(tilt);
        sb.append(", tiltPercent=").append(tiltPercent);
        sb.append(", tilt2=").append(tilt2);
        sb.append(", tilt2Percent=").append(tilt2Percent);
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
        sb.append(", warningStatus=").append(warningStatus);
        sb.append(", alarmStatus=").append(alarmStatus);
        sb.append(", abnormalStatus=").append(abnormalStatus);
        sb.append(", messageBody='").append(messageBody).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append('}');
        return sb.toString();
    }
}
