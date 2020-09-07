package com.jlkj.project.ws.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 参建单位库VO对象 t_ws_participating_unit_library
 *
 * @author liujie
 * @date 2020-06-12
 */
@ApiModel("参建单位库VO对象")
public class WsParticipatingUnitLibraryVO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID 主键 */
    private Integer id;

    /** 企业名称 */
    @ApiModelProperty("企业名称")
    @Excel(name = "企业名称")
    private String unitName;

    /** 企业类型ID */
    @Excel(name = "企业类型ID")
    private Integer unitTypeId;

    /** 企业类型名称 */
    @ApiModelProperty("企业类型名称")
    private String unitTypeName;

    /** 企业类型父级名称 */
    @ApiModelProperty("企业类型父级名称")
    private String unitTypeParentName;

    /** 社会统一信用代码 */
    @Excel(name = "社会统一信用代码")
    @ApiModelProperty("社会统一信用代码")
    private String unitOrgcode;

    /** 注册日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("注册日期")
    @Excel(name = "注册日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date regDate;

    /** 注册区域ID */
    @Excel(name = "注册区域ID")
    private Integer regAreaId;

    /** 注册区域名称 */
    @ApiModelProperty("区域名称")
    private String areaName ;

    /** 注册资本 */
    @Excel(name = "注册资本")
    @ApiModelProperty("注册资本")
    private Double regCapital;

    /** 企业性质code */
    @Excel(name = "企业性质code")
    private String unitNatureCode;

    @ApiModelProperty("企业性质名称")
    /** 企业性质名称 */
    private String unitNatureName;

    @ApiModelProperty("法人代表")
    /** 法人代表 */
    @Excel(name = "法人代表")
    private String legalPerson;

    @ApiModelProperty("营业地址")
    /** 营业地址 */
    @Excel(name = "营业地址")
    private String address;

    @ApiModelProperty("联系人")
    /** 联系人 */
    @Excel(name = "联系人")
    private String contacts;

    @ApiModelProperty("联系电话")
    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactNumber;

    /** 办公电话 */
    @Excel(name = "办公电话")
    private String officeNumber;

    /** 传真号 */
    @Excel(name = "传真号")
    private String faxNumber;

    @ApiModelProperty("备注")
    /** 备注 */
    @Excel(name = "备注")
    private String comment;

    /** 进场登记人数 */
    @ApiModelProperty("进场登记人数")
    private Integer registerNumber;

    /** 进场日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("进场日期")
    @Excel(name = "进场日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inDate;

    /** 项目编号 */
    @ApiModelProperty("项目编号")
    private String projectNo ;

    /** 项目名称 */
    @ApiModelProperty("项目名称")
    private String projectName ;


    /** 建筑用途 */
    @ApiModelProperty("建筑用途")
    private Integer buildPurpose ;





    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public String getUnitName()
    {
        return unitName;
    }

    public void setUnitOrgcode(String unitOrgcode)
    {
        this.unitOrgcode = unitOrgcode;
    }

    public String getUnitOrgcode()
    {
        return unitOrgcode;
    }
    public void setRegDate(Date regDate)
    {
        this.regDate = regDate;
    }

    public Date getRegDate()
    {
        return regDate;
    }


    public void setUnitNatureCode(String unitNatureCode)
    {
        this.unitNatureCode = unitNatureCode;
    }

    public String getUnitNatureCode()
    {
        return unitNatureCode;
    }
    public void setLegalPerson(String legalPerson)
    {
        this.legalPerson = legalPerson;
    }

    public String getLegalPerson()
    {
        return legalPerson;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setContacts(String contacts)
    {
        this.contacts = contacts;
    }

    public String getContacts()
    {
        return contacts;
    }
    public void setContactNumber(String contactNumber)
    {
        this.contactNumber = contactNumber;
    }

    public String getContactNumber()
    {
        return contactNumber;
    }
    public void setOfficeNumber(String officeNumber)
    {
        this.officeNumber = officeNumber;
    }

    public String getOfficeNumber()
    {
        return officeNumber;
    }
    public void setFaxNumber(String faxNumber)
    {
        this.faxNumber = faxNumber;
    }

    public String getFaxNumber()
    {
        return faxNumber;
    }
    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getComment()
    {
        return comment;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Integer unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public Integer getRegAreaId() {
        return regAreaId;
    }

    public void setRegAreaId(Integer regAreaId) {
        this.regAreaId = regAreaId;
    }

    public Double getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(Double regCapital) {
        this.regCapital = regCapital;
    }

    public Integer getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(Integer registerNumber) {
        this.registerNumber = registerNumber;
    }

    public String getUnitTypeName() {
        return unitTypeName;
    }

    public void setUnitTypeName(String unitTypeName) {
        this.unitTypeName = unitTypeName;
    }

    public String getUnitTypeParentName() {
        return unitTypeParentName;
    }

    public void setUnitTypeParentName(String unitTypeParentName) {
        this.unitTypeParentName = unitTypeParentName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUnitNatureName() {
        return unitNatureName;
    }

    public void setUnitNatureName(String unitNatureName) {
        this.unitNatureName = unitNatureName;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getBuildPurpose() {
        return buildPurpose;
    }

    public void setBuildPurpose(Integer buildPurpose) {
        this.buildPurpose = buildPurpose;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitName", getUnitName())
            .append("unitTypeId", getUnitTypeId())
            .append("unitOrgcode", getUnitOrgcode())
            .append("regDate", getRegDate())
            .append("regAreaId", getRegAreaId())
            .append("regCapital", getRegCapital())
            .append("unitNatureCode", getUnitNatureCode())
            .append("legalPerson", getLegalPerson())
            .append("address", getAddress())
            .append("contacts", getContacts())
            .append("contactNumber", getContactNumber())
            .append("officeNumber", getOfficeNumber())
            .append("faxNumber", getFaxNumber())
            .append("comment", getComment())
            .toString();
    }
}
