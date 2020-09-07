package com.jlkj.project.ws.intelligence.domain.vo;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 施工电梯设备工作记录VO t_ws_elevator_manager
 *
 * @author liujie
 * @date 2020-07-03
 */
@ApiModel("施工电梯设备监测VO")
public class WsElevatorWorkCycleManagerVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 省 */
    @ApiModelProperty("省")
    private String province;

    /** 市 */
    @ApiModelProperty("市")
    private String city;

    /** 县区 */
    @ApiModelProperty("县区")
    private String county;

    /** 告警类型（1=预警，2=报警警）*/
    @ApiModelProperty("告警类型（0=正常,1=预警，2=报警）")
    private String warningType;
    /** 上报时间 */

    @ApiModelProperty("上报时间")
    @Excel(name = "开始时间")
    private Date insertTime;

    /** 开始时间 */

    @ApiModelProperty("开始时间")
    private Date beginTime;

    /** 告警标记（1=重量，2身份未认证) */
    @ApiModelProperty("告警标记（1=重量，2身份未认证)")
    private String warningTag;

    /** 工程名称 */

    @ApiModelProperty("工程名称")
    private String projectName;

    /** 备案编号 */
    @ApiModelProperty("备案编号")
    private String recordNumber ;

    /** 笼标记 */
    @ApiModelProperty("笼标记")
    private String cageMark ;


    /** 设备名称 */
    @Excel(name = "设备名称")
    @ApiModelProperty("设备名称")
    private String deviceName;

    /** 设备序列号 */
    @ApiModelProperty("设备序列号")
    private String serialNumber;

    /** 时长 */
    @Excel(name = "工作时长（分钟）")
    @ApiModelProperty("时长")
    private Float minutes;

    /** 载重 */
    @Excel(name = "吊重（kg)")
    @ApiModelProperty("载重")
    private Float weight;

    /** 载重百分比 */
    @ApiModelProperty("载重百分比")
    private Float weightPercent;

    /** 归属公司名称 */
    @ApiModelProperty("归属公司名称")
    private String companyName;

    public String getCageMark() {
        return cageMark;
    }

    public void setCageMark(String cageMark) {
        this.cageMark = cageMark;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Float getMinutes() {
        return minutes;
    }

    public void setMinutes(Float minutes) {
        this.minutes = minutes;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getWarningType() {
        return warningType;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

    public String getWarningTag() {
        return warningTag;
    }

    public void setWarningTag(String warningTag) {
        this.warningTag = warningTag;
    }

    public Float getWeightPercent() {
        return weightPercent;
    }

    public void setWeightPercent(Float weightPercent) {
        this.weightPercent = weightPercent;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsElevatorWorkCycleManagerVO{");
        sb.append("province='").append(province).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", county='").append(county).append('\'');
        sb.append(", warningType='").append(warningType).append('\'');
        sb.append(", insertTime=").append(insertTime);
        sb.append(", beginTime=").append(beginTime);
        sb.append(", warningTag='").append(warningTag).append('\'');
        sb.append(", projectName='").append(projectName).append('\'');
        sb.append(", recordNumber='").append(recordNumber).append('\'');
        sb.append(", cageMark='").append(cageMark).append('\'');
        sb.append(", deviceName='").append(deviceName).append('\'');
        sb.append(", serialNumber='").append(serialNumber).append('\'');
        sb.append(", minutes=").append(minutes);
        sb.append(", weight=").append(weight);
        sb.append(", weightPercent=").append(weightPercent);
        sb.append(", companyName='").append(companyName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
