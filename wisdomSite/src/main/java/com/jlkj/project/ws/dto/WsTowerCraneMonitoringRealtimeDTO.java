package com.jlkj.project.ws.dto;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import com.jlkj.project.ws.domain.WsArea;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @ClassName WsTowerCraneMonitoringRealtimeDTO
 * @Description TODO
 * @Author gaowei
 * @Date 2020/6/28 20:47
 * @Version 1.0
 **/
@ApiModel("塔机监测-企业-实时监控DTO")
public class WsTowerCraneMonitoringRealtimeDTO extends BaseEntity {

    /** 项目id */
    @ApiModelProperty("项目id")
    private Integer projectId;

    @ApiModelProperty("塔机设备总数")
    private Integer towerCraneNum;

    /** 所属项目总数 */
    @ApiModelProperty("所属项目总数")
    private Integer projectNum;

    /** 监控设备总数 */
    @ApiModelProperty("监控设备总数")
    private Integer monitoringEquipmentNum;

    /** 在线数量*/
    @ApiModelProperty("在线")
    private Integer onlineNum;

    /** 离线数量*/
    @ApiModelProperty("离线")
    private Integer offlineNum;

    /** 今日违章次数*/
    @ApiModelProperty("今日违章次数")
    private Integer todayIllegalNum;

    /** 项目违章总数 */
    @ApiModelProperty("项目违章总数")
    private Integer projectIllegalNum;

    /** 省 */
    @ApiModelProperty("区域")
    private String region;

    /** 省 */
    @ApiModelProperty("省")
    @Excel(name = "省")
    private String province;

    /** 市 */
    @ApiModelProperty("市")
    @Excel(name = "市")
    private String city;

    /** 区 */
    @ApiModelProperty("区")
    @Excel(name = "区")
    private String county;

    /** 项目名称 */
    @ApiModelProperty("项目名称")
    @Excel(name = "项目名称")
    private String projectName;

    /** 产权备案号 */
    @ApiModelProperty("产权备案号")
    @Excel(name = "产权备案号")
    private String propertyRecordno;

    /** 设备名称 */
    @ApiModelProperty("设备名称")
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 监测设备编号 */
    @Excel(name = "监测设备")
    private String monitoringEquipmentNo;

    /** 在线状态name */
    @Excel(name = "在线状态")
    private String onlineStatusName;

    /** 违章时间 */
    @Excel(name = "违章时间")
    private Date violationTime;

    /** 违章内容 */
    @Excel(name = "违章内容")
    private String violationContent;

    //===================================================================================

    private String number;

    @ApiModelProperty("区域")
    private WsArea wsArea;

    @ApiModelProperty("区域code")
    private String areaCode;

    @ApiModelProperty("省设备数量")
    private int provinceNum;

    @ApiModelProperty("市设备数量")
    private int cityNum;

    @ApiModelProperty("区设备数量")
    private int countyNum;

    @ApiModelProperty("项目设备数量")
    private String projectEquipmentNum;

    @ApiModelProperty("经度")
    private Double longitude;

    @ApiModelProperty("纬度")
    private Double latitude;


    public Integer getTowerCraneNum() {
        return towerCraneNum;
    }

    public void setTowerCraneNum(Integer towerCraneNum) {
        this.towerCraneNum = towerCraneNum;
    }

    public Integer getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(Integer projectNum) {
        this.projectNum = projectNum;
    }

    public Integer getMonitoringEquipmentNum() {
        return monitoringEquipmentNum;
    }

    public void setMonitoringEquipmentNum(Integer monitoringEquipmentNum) {
        this.monitoringEquipmentNum = monitoringEquipmentNum;
    }

    public Integer getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(Integer onlineNum) {
        this.onlineNum = onlineNum;
    }

    public Integer getOfflineNum() {
        return offlineNum;
    }

    public void setOfflineNum(Integer offlineNum) {
        this.offlineNum = offlineNum;
    }

    public Integer getTodayIllegalNum() {
        return todayIllegalNum;
    }

    public void setTodayIllegalNum(Integer todayIllegalNum) {
        this.todayIllegalNum = todayIllegalNum;
    }

    public Integer getProjectIllegalNum() {
        return projectIllegalNum;
    }

    public void setProjectIllegalNum(Integer projectIllegalNum) {
        this.projectIllegalNum = projectIllegalNum;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPropertyRecordno() {
        return propertyRecordno;
    }

    public void setPropertyRecordno(String propertyRecordno) {
        this.propertyRecordno = propertyRecordno;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getMonitoringEquipmentNo() {
        return monitoringEquipmentNo;
    }

    public void setMonitoringEquipmentNo(String monitoringEquipmentNo) {
        this.monitoringEquipmentNo = monitoringEquipmentNo;
    }

    public String getOnlineStatusName() {
        return onlineStatusName;
    }

    public void setOnlineStatusName(String onlineStatusName) {
        this.onlineStatusName = onlineStatusName;
    }

    public Date getViolationTime() {
        return violationTime;
    }

    public void setViolationTime(Date violationTime) {
        this.violationTime = violationTime;
    }

    public String getViolationContent() {
        return violationContent;
    }

    public void setViolationContent(String violationContent) {
        this.violationContent = violationContent;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public WsArea getWsArea() {
        return wsArea;
    }

    public void setWsArea(WsArea wsArea) {
        this.wsArea = wsArea;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getProvinceNum() {
        return provinceNum;
    }

    public void setProvinceNum(int provinceNum) {
        this.provinceNum = provinceNum;
    }

    public int getCityNum() {
        return cityNum;
    }

    public void setCityNum(int cityNum) {
        this.cityNum = cityNum;
    }

    public int getCountyNum() {
        return countyNum;
    }

    public void setCountyNum(int countyNum) {
        this.countyNum = countyNum;
    }

    public String getProjectEquipmentNum() {
        return projectEquipmentNum;
    }

    public void setProjectEquipmentNum(String projectEquipmentNum) {
        this.projectEquipmentNum = projectEquipmentNum;
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
}
