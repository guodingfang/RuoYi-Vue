package com.jlkj.project.ws.intelligence.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 升降机工作循环信息管理对象 t_ws_elevator_work_cycle_manager
 *
 * @author liujie
 * @date 2020-07-03
 */
public class WsElevatorWorkCycleManager extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String oid;

    /** 黑匣子OID */
    @Excel(name = "黑匣子OID")
    private String blackBoxOid;

    /** 黑匣子注册编号 */
    @Excel(name = "黑匣子注册编号")
    private String blackBoxRrOid;

    /** 工程编号 */
    @Excel(name = "工程编号")
    private String projectOid;

    /** 升降机编号 */
    @Excel(name = "升降机编号")
    private String serialNumber;

    /** 当前时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "当前时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date insertTime;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beginDate;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 驾驶员姓名 */
    @Excel(name = "驾驶员姓名")
    private String driverName;

    /** 驾驶员身份证号 */
    @Excel(name = "驾驶员身份证号")
    private String driverCardNo;

    /** 本次载重 */
    @Excel(name = "本次载重")
    private Integer weight;

    /** 本次载重百分比 */
    @Excel(name = "本次载重百分比")
    private Integer weightPercent;

    /** 起点高度 */
    @Excel(name = "起点高度")
    private Integer startHeight;

    /** 终点高度 */
    @Excel(name = "终点高度")
    private Integer endHeight;

    /** 行程高度 */
    @Excel(name = "行程高度")
    private Integer tripHeight;

    /** 起升方向 */
    @Excel(name = "起升方向")
    private Integer liftingDirection;

    /** 平均速度 */
    @Excel(name = "平均速度")
    private Integer avgSpeed;

    /** 最大X向倾斜度 */
    @Excel(name = "最大X向倾斜度")
    private Integer maxXTilt;

    /** 最大Y向倾斜度 */
    @Excel(name = "最大Y向倾斜度")
    private Integer maxYTilt;

    /** 装载人数 */
    @Excel(name = "装载人数")
    private Integer loadPersons;

    /** 报警状态 */
    @Excel(name = "报警状态")
    private Integer warningState;

    /** 报态-重量 : bit0-1：00重量正常 01重量预警 10重量报警警状 */
    @Excel(name = "报态-重量 : bit0-1：00重量正常 01重量预警 10重量报警警状")
    private Integer warningStateWeight;

    /** 报警状态-身份认证 : bit2-3： 00身份认证未正常  01身份认证通过 */
    @Excel(name = "报警状态-身份认证 : bit2-3： 00身份认证未正常  01身份认证通过")
    private Integer warningStateIdentity;

    /** 报警状态-速度 : bit4-5:00速度正常 01速度预警 10速度报警 */
    @Excel(name = "报警状态-速度 : bit4-5:00速度正常 01速度预警 10速度报警")
    private Integer warningStateSpeed;

    /** 报警状态-高度 : bit6-7：00高度正常 01高度预警 10高度告警 */
    @Excel(name = "报警状态-高度 : bit6-7：00高度正常 01高度预警 10高度告警")
    private Integer warningStateHeight;

    /** 报警状态-人数 : bit8-9：00人数正常 01人数超载 */
    @Excel(name = "报警状态-人数 : bit8-9：00人数正常 01人数超载")
    private Integer warningStatePersons;

    /** 报警状态-倾斜 : bit10-11:  00 倾斜正常 01倾斜预警 10倾斜报警 */
    @Excel(name = "报警状态-倾斜 : bit10-11:  00 倾斜正常 01倾斜预警 10倾斜报警")
    private Integer warningStateTilt;

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
    public void setInsertTime(Date insertTime)
    {
        this.insertTime = insertTime;
    }

    public Date getInsertTime()
    {
        return insertTime;
    }
    public void setBeginDate(Date beginDate)
    {
        this.beginDate = beginDate;
    }

    public Date getBeginDate()
    {
        return beginDate;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }
    public void setDriverName(String driverName)
    {
        this.driverName = driverName;
    }

    public String getDriverName()
    {
        return driverName;
    }
    public void setDriverCardNo(String driverCardNo)
    {
        this.driverCardNo = driverCardNo;
    }

    public String getDriverCardNo()
    {
        return driverCardNo;
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
    public void setStartHeight(Integer startHeight)
    {
        this.startHeight = startHeight;
    }

    public Integer getStartHeight()
    {
        return startHeight;
    }
    public void setEndHeight(Integer endHeight)
    {
        this.endHeight = endHeight;
    }

    public Integer getEndHeight()
    {
        return endHeight;
    }
    public void setTripHeight(Integer tripHeight)
    {
        this.tripHeight = tripHeight;
    }

    public Integer getTripHeight()
    {
        return tripHeight;
    }
    public void setLiftingDirection(Integer liftingDirection)
    {
        this.liftingDirection = liftingDirection;
    }

    public Integer getLiftingDirection()
    {
        return liftingDirection;
    }
    public void setAvgSpeed(Integer avgSpeed)
    {
        this.avgSpeed = avgSpeed;
    }

    public Integer getAvgSpeed()
    {
        return avgSpeed;
    }
    public void setMaxXTilt(Integer maxXTilt)
    {
        this.maxXTilt = maxXTilt;
    }

    public Integer getMaxXTilt()
    {
        return maxXTilt;
    }
    public void setMaxYTilt(Integer maxYTilt)
    {
        this.maxYTilt = maxYTilt;
    }

    public Integer getMaxYTilt()
    {
        return maxYTilt;
    }
    public void setLoadPersons(Integer loadPersons)
    {
        this.loadPersons = loadPersons;
    }

    public Integer getLoadPersons()
    {
        return loadPersons;
    }
    public void setWarningState(Integer warningState)
    {
        this.warningState = warningState;
    }

    public Integer getWarningState()
    {
        return warningState;
    }
    public void setWarningStateWeight(Integer warningStateWeight)
    {
        this.warningStateWeight = warningStateWeight;
    }

    public Integer getWarningStateWeight()
    {
        return warningStateWeight;
    }
    public void setWarningStateIdentity(Integer warningStateIdentity)
    {
        this.warningStateIdentity = warningStateIdentity;
    }

    public Integer getWarningStateIdentity()
    {
        return warningStateIdentity;
    }
    public void setWarningStateSpeed(Integer warningStateSpeed)
    {
        this.warningStateSpeed = warningStateSpeed;
    }

    public Integer getWarningStateSpeed()
    {
        return warningStateSpeed;
    }
    public void setWarningStateHeight(Integer warningStateHeight)
    {
        this.warningStateHeight = warningStateHeight;
    }

    public Integer getWarningStateHeight()
    {
        return warningStateHeight;
    }
    public void setWarningStatePersons(Integer warningStatePersons)
    {
        this.warningStatePersons = warningStatePersons;
    }

    public Integer getWarningStatePersons()
    {
        return warningStatePersons;
    }
    public void setWarningStateTilt(Integer warningStateTilt)
    {
        this.warningStateTilt = warningStateTilt;
    }

    public Integer getWarningStateTilt()
    {
        return warningStateTilt;
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
        final StringBuffer sb = new StringBuffer("WsElevatorWorkCycleManager{");
        sb.append("oid='").append(oid).append('\'');
        sb.append(", blackBoxOid='").append(blackBoxOid).append('\'');
        sb.append(", blackBoxRrOid='").append(blackBoxRrOid).append('\'');
        sb.append(", projectOid='").append(projectOid).append('\'');
        sb.append(", serialNumber='").append(serialNumber).append('\'');
        sb.append(", insertTime=").append(insertTime);
        sb.append(", beginDate=").append(beginDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", driverName='").append(driverName).append('\'');
        sb.append(", driverCardNo='").append(driverCardNo).append('\'');
        sb.append(", weight=").append(weight);
        sb.append(", weightPercent=").append(weightPercent);
        sb.append(", startHeight=").append(startHeight);
        sb.append(", endHeight=").append(endHeight);
        sb.append(", tripHeight=").append(tripHeight);
        sb.append(", liftingDirection=").append(liftingDirection);
        sb.append(", avgSpeed=").append(avgSpeed);
        sb.append(", maxXTilt=").append(maxXTilt);
        sb.append(", maxYTilt=").append(maxYTilt);
        sb.append(", loadPersons=").append(loadPersons);
        sb.append(", warningState=").append(warningState);
        sb.append(", warningStateWeight=").append(warningStateWeight);
        sb.append(", warningStateIdentity=").append(warningStateIdentity);
        sb.append(", warningStateSpeed=").append(warningStateSpeed);
        sb.append(", warningStateHeight=").append(warningStateHeight);
        sb.append(", warningStatePersons=").append(warningStatePersons);
        sb.append(", warningStateTilt=").append(warningStateTilt);
        sb.append(", messageBody='").append(messageBody).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append('}');
        return sb.toString();
    }
}
