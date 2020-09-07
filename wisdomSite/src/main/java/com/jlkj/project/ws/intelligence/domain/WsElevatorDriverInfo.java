package com.jlkj.project.ws.intelligence.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 施工电梯设备管理对象 t_ws_elevator_manager
 *
 * @author liujie
 * @date 2020-07-03
 */
@ApiModel("施工电梯设备管理实体")
public class WsElevatorDriverInfo implements Serializable
{
    private static final long serialVersionUID = 1L;



    /** 工程id */
    @ApiModelProperty("工程id")
    @Excel(name = "工程id")
    private Integer projectId;

    /** 姓名 */
    @ApiModelProperty("姓名")
    @Excel(name = "姓名")
    private String name;

    /** 识别时间 */
    @ApiModelProperty("识别时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "识别时间")
    private Date recognitionTime;

    /** 身份证号 */
    @ApiModelProperty("身份证号")
    @Excel(name = "身份证号")
    private String idNumber;

    /** 监控设备编号*/
    @ApiModelProperty("监控设备编号")
    @Excel(name = "监控设备编号")
    private String serialNumber;


    /** 人脸照片 */
    @ApiModelProperty("人脸照片")
    @Excel(name = "人脸照片")
    private String faceUrl;

    /** 特种作业证 */
    @ApiModelProperty("特种作业证")
    @Excel(name = "特种作业证")
    private String specialOperationCertificate;

    public String getSpecialOperationCertificate() {
        return specialOperationCertificate;
    }

    public void setSpecialOperationCertificate(String specialOperationCertificate) {
        this.specialOperationCertificate = specialOperationCertificate;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRecognitionTime() {
        return recognitionTime;
    }

    public void setRecognitionTime(Date recognitionTime) {
        this.recognitionTime = recognitionTime;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsElevatorDriverInfo{");
        sb.append("projectId=").append(projectId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", recognitionTime=").append(recognitionTime);
        sb.append(", idNumber='").append(idNumber).append('\'');
        sb.append(", serialNumber='").append(serialNumber).append('\'');
        sb.append(", faceUrl='").append(faceUrl).append('\'');
        sb.append(", specialOperationCertificate='").append(specialOperationCertificate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
