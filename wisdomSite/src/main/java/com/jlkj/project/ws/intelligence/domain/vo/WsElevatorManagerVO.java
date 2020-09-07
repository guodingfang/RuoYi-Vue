package com.jlkj.project.ws.intelligence.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.project.ws.intelligence.domain.WsElevatorMinitorDevice;
import com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 施工电梯设备管理VO t_ws_elevator_manager
 *
 * @author liujie
 * @date 2020-07-03
 */
@ApiModel("施工电梯设备管理VO")
public class WsElevatorManagerVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    private Integer id;

    /**
     * 工程id
     */
    @ApiModelProperty("工程id")
    @Excel(name = "工程id")
    private Integer projectId;

    /**
     * 自编号
     */
    @ApiModelProperty("自编号")
    @Excel(name = "自编号")
    private String selfNumber;

    /**
     * 产权备案编号
     */
    @ApiModelProperty("产权备案编号")
    @Excel(name = "产权备案编号")
    private String recordNumber;

    /**
     * 设备名称
     */
    @ApiModelProperty("设备名称")
    @Excel(name = "设备名称")
    private String deviceName;


    /**
     * 进场日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("进场日期")
    @Excel(name = "进场日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inDate;

    /**
     * 退场日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("退场日期")
    @Excel(name = "退场日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date outDate;


    /**
     * 设备类型(1=单笼,2=双笼)
     */
    @ApiModelProperty("设备类型(1=单笼,2=双笼) ")
    @Excel(name = "设备类型(1=单笼,2=双笼)")
    private String deviceType;

    /**
     * 绑定设备列表
     */
    @ApiModelProperty("绑定设备列表")
    private List<WsElevatorMinitorDevice> monitorDevices;

    /**
     * 项目场布图
     */
    @ApiModelProperty("项目场布图")
    private WsProjectFieldLayout layout;

    /**
     * 省
     */
    @ApiModelProperty("省")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty("市")
    private String city;

    /**
     * 县区
     */
    @ApiModelProperty("县区")
    private String county;

    /**
     * 省
     */
    @ApiModelProperty("省Code")
    private String provinceCode;

    /**
     * 市
     */
    @ApiModelProperty("市Code")
    private String cityCode;

    /**
     * 县区ID
     */
    @ApiModelProperty("县区Code")
    private String countyCode;

    /**
     * 行政区域Code集合
     */
    @ApiModelProperty("行政区域Code集合")
    private String ancestors;

    /**
     * 工程名称
     */
    @ApiModelProperty("工程名称")
    private String projectName;

    /**
     * 监控状态（0=离线，1=在线）
     */
    @ApiModelProperty("监控状态（0=离线，1=在线） ")
    private String onlineStatus;

    /** 监控设备ID*/
    private Integer monitorDeviceId;

    private Integer inOutId;


    private String serialNumber ;

    private String cageMark;

    /** 设备状态(0=离场,1=进场) */
    @ApiModelProperty("设备状态(0=离场,1=进场)")
    @Excel(name = "设备状态(0=离场,1=进场)")
    private String deviceStatus;

    private Double longitude;

    private Double latitude;

    /** x方向偏移量*/
    @ApiModelProperty("x方向偏移量")
    @Excel(name = "x_offset")
    private String xOffset;

    /** y方向偏移量 */
    @ApiModelProperty("y方向偏移量")
    @Excel(name = "y方向偏移量")
    private String yOffset;

    /** 是否标记 */
    @ApiModelProperty("是否标记")
    private String sign;

    /** 归属公司名称 */
    @ApiModelProperty("归属公司名称")
    private String companyName;

    /** 设备统计数据列表 */
    @ApiModelProperty("设备统计数据列表")
    private List<WsElevatorWorkCycleStatisticsVO> statisticsList;

    public List<WsElevatorWorkCycleStatisticsVO> getStatisticsList() {
        return statisticsList;
    }

    public void setStatisticsList(List<WsElevatorWorkCycleStatisticsVO> statisticsList) {
        this.statisticsList = statisticsList;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getCageMark() {
        return cageMark;
    }

    public void setCageMark(String cageMark) {
        this.cageMark = cageMark;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getMonitorDeviceId() {
        return monitorDeviceId;
    }

    public void setMonitorDeviceId(Integer monitorDeviceId) {
        this.monitorDeviceId = monitorDeviceId;
    }

    public Integer getInOutId() {
        return inOutId;
    }

    public void setInOutId(Integer inOutId) {
        this.inOutId = inOutId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public String getSelfNumber() {
        return selfNumber;
    }

    public void setSelfNumber(String selfNumber) {
        this.selfNumber = selfNumber;
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

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public List<WsElevatorMinitorDevice> getMonitorDevices() {
        return monitorDevices;
    }

    public void setMonitorDevices(List<WsElevatorMinitorDevice> monitorDevices) {
        this.monitorDevices = monitorDevices;
    }

    public WsProjectFieldLayout getLayout() {
        return layout;
    }

    public void setLayout(WsProjectFieldLayout layout) {
        this.layout = layout;
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

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getxOffset() {
        return xOffset;
    }

    public void setxOffset(String xOffset) {
        this.xOffset = xOffset;
    }

    public String getyOffset() {
        return yOffset;
    }

    public void setyOffset(String yOffset) {
        this.yOffset = yOffset;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
