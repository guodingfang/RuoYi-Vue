package com.jlkj.project.ws.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 参建单位库对象 t_ws_participating_unit_library
 *
 * @author jlkj
 * @date 2020-06-12
 */
@ApiModel("参建单位库实体")
public class WsParticipatingUnitLibrary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID 主键 */
    @ApiModelProperty("主键")
    private Integer id;

    /** 集团企业ID*/
    private Long enterpriseId;

    /** 企业名称 */
    @Excel(name = "企业名称")
    @ApiModelProperty("企业名称")
    private String unitName;

    /** 企业类型ID */
    @ApiModelProperty("企业类型ID")
    @Excel(name = "企业类型ID")
    private Integer unitTypeId;

    /** 企业类型名称 */
    @ApiModelProperty("企业类型名称")
    @Excel(name = "企业类型名称")
    private String unitTypeName;

    /** 社会统一信用代码 */
    @ApiModelProperty("社会统一信用代码")
    @Excel(name = "社会统一信用代码")
    private String unitOrgcode;

    /** 注册日期 */
    @ApiModelProperty("注册日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date regDate;

    /** 注册区域ID */
    @Excel(name = "注册区域ID")
    @ApiModelProperty("注册区域ID")
    private Integer regAreaId;

    /** 注册区域名称 */
    @Excel(name = "注册区域名称")
    @ApiModelProperty("注册区域名称")
    private String regAreaName;

    /** 注册资本 */
    @Excel(name = "注册资本")
    @ApiModelProperty("注册资本")
    private Double regCapital;

    /** 企业性质code */
    @Excel(name = "企业性质code")
    @ApiModelProperty("企业性质code")
    private String unitNatureCode;

    /** 企业性质*/
    @Excel(name = "企业性质")
    @ApiModelProperty("企业性质")
    private String unitNature;

    /** 法人代表 */
    @Excel(name = "法人代表")
    @ApiModelProperty("法人代表")
    private String legalPerson;

    /** 营业地址 */
    @Excel(name = "营业地址")
    @ApiModelProperty("营业地址")
    private String address;

    /** 联系人 */
    @Excel(name = "联系人")
    @ApiModelProperty("联系人")
    private String contacts;

    /** 联系电话 */
    @Excel(name = "联系电话")
    @ApiModelProperty("")
    private String contactNumber;

    /** 办公电话 */
    @Excel(name = "办公电话")
    @ApiModelProperty("办公电话")
    private String officeNumber;

    /** 传真号 */
    @Excel(name = "传真号")
    private String faxNumber;

    /** 备注 */
    @Excel(name = "备注")
    @ApiModelProperty("备注")
    private String comment;

    /** 班组名词连接字符（'.'隔开） */
    @Excel(name = "班组名词连接字符", readConverterExp = "'=.'隔开")
    private String teamNames;
    /** 关键字搜索*/
    private String keyWordName;

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getKeyWordName() {
        return keyWordName;
    }

    public void setKeyWordName(String keyWordName) {
        this.keyWordName = keyWordName;
    }

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
    public void setTeamNames(String teamNames)
    {
        this.teamNames = teamNames;
    }

    public String getTeamNames()
    {
        return teamNames;
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

    public String getUnitTypeName() {
        return unitTypeName;
    }

    public void setUnitTypeName(String unitTypeName) {
        this.unitTypeName = unitTypeName;
    }

    public String getRegAreaName() {
        return regAreaName;
    }

    public void setRegAreaName(String regAreaName) {
        this.regAreaName = regAreaName;
    }

    public String getUnitNature() {
        return unitNature;
    }

    public void setUnitNature(String unitNature) {
        this.unitNature = unitNature;
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
            .append("teamNames", getTeamNames())
            .toString();
    }
}
