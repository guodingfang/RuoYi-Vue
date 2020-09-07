package com.jlkj.project.ws.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 人员资格证书 对象 t_ws_area
 *
 * @author liujie
 * @date 2020-06-12
 */
@ApiModel("人员资格证书实体")
public class WsQualificationCertificateLibrary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("主键")
    private Integer id;

    /** 身份证号 */
    @ApiModelProperty("身份证号")
    @Excel(name = "身份证号")
    private String idNumber;

    /** 证书名称 */
    @ApiModelProperty("证书名称")
    @Excel(name = "证书名称")
    private String certificateName;

        /** 证书编号 */
    @Excel(name = "证书编号")
    @ApiModelProperty("证书编号")
    private String certificateNumber;

    /** 证书类型 */
    @ApiModelProperty("证书类型")
    @Excel(name = "证书类型 " ,readConverterExp = "1=上岗证,2=职业资格证")
    private String certificateType;

    /** 证书等级代码 */
    @ApiModelProperty("证书等级代码")
    @Excel(name = "证书等级代码")
    private String certificateLevelCode;

    /** 证书等级 */
    @Excel(name = "证书等级")
    @ApiModelProperty("证书等级")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String certificateLevel;

    /** 生效日期 */
    @Excel(name = "生效日期")
    @ApiModelProperty("生效日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date effectiveDate;

    /** 失效日期 */
    @Excel(name = "失效日期")
    @ApiModelProperty("失效日期")
    private Date invalidDate;

    /** 正面照片地址 */
    @Excel(name = "正面照片地址")
    @ApiModelProperty("正面照片地址")
    private String frontImage;

    /** 反面照片地址 */
    @Excel(name = "反面照片地址")
    @ApiModelProperty("反面照片地址")
    private String reverseImage;

    /** 更新时间 */
    private Date updatedTime;

    private String certificateTypeName;

    public String getCertificateTypeName() {
        return certificateTypeName;
    }

    public void setCertificateTypeName(String certificateTypeName) {
        this.certificateTypeName = certificateTypeName;
    }

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateLevelCode() {
        return certificateLevelCode;
    }

    public void setCertificateLevelCode(String certificateLevelCode) {
        this.certificateLevelCode = certificateLevelCode;
    }

    public String getCertificateLevel() {
        return certificateLevel;
    }

    public void setCertificateLevel(String certificateLevel) {
        this.certificateLevel = certificateLevel;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    public String getReverseImage() {
        return reverseImage;
    }

    public void setReverseImage(String reverseImage) {
        this.reverseImage = reverseImage;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
