package com.jlkj.project.ws.intelligence.domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

/**
 * 黑匣子信息
 *
 * @author : dusd
 * @date : 2018-06-09 22:53:58
 */

@Api("黑匣子信息实体")
public class WsBlackBox implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * @COLUMN_EXPLAIN : 主键
     * @TABLE_COLUMN_TYPE : varchar
     */
    @Length(min = 0, max = 32)
    private String oid;

    /**
     * @COLUMN_EXPLAIN : 设备名称
     * @TABLE_COLUMN_TYPE : varchar
     */

    @ApiModelProperty("设备名称")
    @Length(min = 0, max = 100)
    private String name;

    /**
     * @COLUMN_EXPLAIN : 设备类型
     * @TABLE_COLUMN_TYPE : varchar
     */
    @ApiModelProperty("设备类型")
    @Length(min = 0, max = 2)
    private String equipmentType;

    /**
     * @COLUMN_EXPLAIN : 编号
     * @TABLE_COLUMN_TYPE : varchar
     */
    @ApiModelProperty("编号")
    @Length(min = 0, max = 100)
    private String code;

    /**
     * @COLUMN_EXPLAIN : 设备编号
     * @TABLE_COLUMN_TYPE : varchar
     */
    @ApiModelProperty( "设备编号")
    @Length(min = 0, max = 100)
    private String serialNumber;

    /**
     * @COLUMN_EXPLAIN : 厂家代码
     * @TABLE_COLUMN_TYPE : varchar
     */
    @ApiModelProperty("厂家代码")
    @Length(min = 0, max = 50)
    private String plantCode;

    /**
     * @COLUMN_EXPLAIN : 消息体
     * @TABLE_COLUMN_TYPE : varchar
     */
    @ApiModelProperty( "消息体")
    @Length(min = 0, max = 200)
    private String messageBody;

    /**
     * @COLUMN_EXPLAIN : 连接端Netty编号
     * @TABLE_COLUMN_TYPE : varchar
     */
    @ApiModelProperty("连接端Netty编号")
    @Length(min = 0, max = 50)
    private String dtuNumber;

    /**
     * @COLUMN_EXPLAIN : 备注
     * @TABLE_COLUMN_TYPE : varchar
     */
    @ApiModelProperty( "备注")
    @Length(min = 0, max = 2000)
    private String memo;

    /**
     * 报警状态 1-正常 1-异常
     */
    @ApiModelProperty("报警状态")
    private String alertStatus;
    /**
     * 在线状态 1-在线 2-离线
     */
    @ApiModelProperty( "在线状态")
    private String onlineStatus;

    /**
     * 塔机当天违章次数
     */
    @ApiModelProperty("当天违章次数")
    private String tjIllegalNum;

    /** 塔吊工作循环数  */
    @ApiModelProperty( "塔吊工作循环数")
    private Integer tjWorkCycleNum;
    /**
     * 升降机当天违章次数
     */
    @ApiModelProperty( "当天违章次数")
    private String elevatorIllegalNum;




    /** 升降机工作循环数  */
    @ApiModelProperty( "升降机工作循环数")
    private Integer elevatorWorkCycleNum;



    /**
     * 数据转发规则
     */
    @ApiModelProperty("数据转发规则")
    private String dataForwareOid;

    /**
     * 人脸绑定设备编号
     */
    @ApiModelProperty("塔机人脸绑定设备编号")
    private String rlbdSerialNumber;

    @ApiModelProperty("创建时间")
    private Date createdDate;

    @ApiModelProperty("设备序列号集合")
    private String[] serialNumbers;


    public String[] getSerialNumbers() {
        return serialNumbers;
    }

    public void setSerialNumbers(String[] serialNumbers) {
        this.serialNumbers = serialNumbers;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPlantCode() {
        return plantCode;
    }

    public void setPlantCode(String plantCode) {
        this.plantCode = plantCode;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getDtuNumber() {
        return dtuNumber;
    }

    public void setDtuNumber(String dtuNumber) {
        this.dtuNumber = dtuNumber;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAlertStatus() {
        return alertStatus;
    }

    public void setAlertStatus(String alertStatus) {
        this.alertStatus = alertStatus;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getTjIllegalNum() {
        return tjIllegalNum;
    }

    public void setTjIllegalNum(String tjIllegalNum) {
        this.tjIllegalNum = tjIllegalNum;
    }


    public String getDataForwareOid() {
        return dataForwareOid;
    }

    public void setDataForwareOid(String dataForwareOid) {
        this.dataForwareOid = dataForwareOid;
    }

    public String getRlbdSerialNumber() {
        return rlbdSerialNumber;
    }

    public void setRlbdSerialNumber(String rlbdSerialNumber) {
        this.rlbdSerialNumber = rlbdSerialNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getTjWorkCycleNum() {
        return tjWorkCycleNum;
    }

    public void setTjWorkCycleNum(Integer tjWorkCycleNum) {
        this.tjWorkCycleNum = tjWorkCycleNum;
    }

    public Integer getElevatorWorkCycleNum() {
        return elevatorWorkCycleNum;
    }

    public void setElevatorWorkCycleNum(Integer elevatorWorkCycleNum) {
        this.elevatorWorkCycleNum = elevatorWorkCycleNum;
    }

    public String getElevatorIllegalNum() {
        return elevatorIllegalNum;
    }

    public void setElevatorIllegalNum(String elevatorIllegalNum) {
        this.elevatorIllegalNum = elevatorIllegalNum;
    }
}
