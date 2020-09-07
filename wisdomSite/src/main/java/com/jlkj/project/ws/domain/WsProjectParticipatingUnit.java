package com.jlkj.project.ws.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 项目参建单位 对象 t_ws_project_participating_unit
 *
 * @author jlkj
 * @date 2020-06-12
 */
@ApiModel("项目参建单位实体")
public class WsProjectParticipatingUnit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID 主键 */
    private Integer id;

    /** 集团企业ID*/
    private Long enterpriseId;

    /** 项目ID */
    @ApiModelProperty("项目ID")
    @Excel(name = "项目ID")
    private Integer projectId;

    /** 企业名称 */
    @ApiModelProperty("企业名称")
    @Excel(name = "企业名称")
    private String unitName;

    /** 企业类型ID */
    @Excel(name = "企业类型ID")
    private Integer unitTypeId;

    /** 企业类型父级ID */
    @Excel(name = "企业类型父级ID")
    private Integer unitTypeParentId;

    /**企业类型名称*/
    private String unitTypeName;

    /** 社会统一信用代码 */
    @ApiModelProperty("社会统一信用代码")
    @Excel(name = "社会统一信用代码")
    private String unitOrgcode;

    /** 注册日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date regDate;

    /** 注册区域ID */
    @Excel(name = "注册区域ID")
    private Integer regAreaId;

    /** 省Id */
    @Excel(name = "省Id")
    private Integer provinceId;

    /** 市id */
    @Excel(name = "市id")
    private Integer cityId;

    /** 县区id */
    @Excel(name = "县区id")
    private Integer countyId;

    /** 注册资本 */
    @Excel(name = "注册资本")
    private Double regCapital;

    /** 企业性质code */
    @ApiModelProperty("企业性质code")
    @Excel(name = "企业性质code")
    private String unitNatureCode;

    /** 法人代表 */
    @Excel(name = "法人代表")
    private String legalPerson;

    /** 营业地址 */
    @Excel(name = "营业地址")
    private String address;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contacts;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactNumber;

    /** 办公电话 */
    @Excel(name = "办公电话")
    private String officeNumber;

    /** 传真号 */
    @Excel(name = "传真号")
    private String faxNumber;

    /** 备注 */
    @Excel(name = "备注")
    private String comment;

    private String projectName;

    private String buildStructureType;

    private String projectNo ;

    /** 更新时间 */
    private Date updatedTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;



    @ApiModelProperty("班组列表")
    private List<WsParticipatingTeam> teams;

    @ApiModelProperty("是否删除（0 N,1 Y)")
    private String deleted;

    public String getProjectName() {
        return projectName;
    }


    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBuildStructureType() {
        return buildStructureType;
    }

    public void setBuildStructureType(String buildStructureType) {
        this.buildStructureType = buildStructureType;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @NotEmpty(message = "班组不能为空")
    public List<WsParticipatingTeam> getTeams() {
        return teams;
    }

    public void setTeams(List<WsParticipatingTeam> teams) {
        this.teams = teams;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }

    @NotNull(message = "项目不存在")
    public Integer getProjectId()
    {
        return projectId;
    }
    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    @NotBlank(message = "单位名称不能为空")
    public String getUnitName()
    {
        return unitName;
    }
    public void setUnitTypeId(Integer unitTypeId)
    {
        this.unitTypeId = unitTypeId;
    }

    @NotNull(message = "单位类型不能为空")
    public Integer getUnitTypeId()
    {
        return unitTypeId;
    }
    public void setUnitOrgcode(String unitOrgcode)
    {
        this.unitOrgcode = unitOrgcode;
    }
    @NotBlank(message = "统一信用代码不能为空")
    @Size(min = 18,max = 18,message = "社会统一信用代码为18位")
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
    public void setRegAreaId(Integer regAreaId)
    {
        this.regAreaId = regAreaId;
    }

    @NotNull(message = "区域不能为空")
    public Integer getRegAreaId()
    {
        return regAreaId;
    }
    public void setRegCapital(Double regCapital)
    {
        this.regCapital = regCapital;
    }

    public Double getRegCapital()
    {
        return regCapital;
    }
    public void setUnitNatureCode(String unitNatureCode)
    {
        this.unitNatureCode = unitNatureCode;
    }
    @NotBlank(message = "企业性质不能为空")
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
    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }


    public String getUnitTypeName() {
        return unitTypeName;
    }

    public void setUnitTypeName(String unitTypeName) {
        this.unitTypeName = unitTypeName;
    }

    public Integer getUnitTypeParentId() {
        return unitTypeParentId;
    }

    public void setUnitTypeParentId(Integer unitTypeParentId) {
        this.unitTypeParentId = unitTypeParentId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public WsProjectParticipatingUnit() {
    }

    public WsProjectParticipatingUnit(Integer projectId, String unitName, Integer unitTypeId, String unitOrgcode, Date regDate, Integer regAreaId, Double regCapital, String unitNatureCode, String legalPerson, String address, String contacts, String contactNumber, String officeNumber, String faxNumber, String comment,String deleted, List<WsParticipatingTeam> teams) {
        this.projectId = projectId;
        this.unitName = unitName;
        this.unitTypeId = unitTypeId;
        this.unitOrgcode = unitOrgcode;
        this.regDate = regDate;
        this.regAreaId = regAreaId;
        this.regCapital = regCapital;
        this.unitNatureCode = unitNatureCode;
        this.legalPerson = legalPerson;
        this.address = address;
        this.contacts = contacts;
        this.contactNumber = contactNumber;
        this.officeNumber = officeNumber;
        this.faxNumber = faxNumber;
        this.comment = comment;
        this.teams = teams;
        this.deleted=deleted;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsProjectParticipatingUnit{");
        sb.append("id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", unitTypeId=").append(unitTypeId);
        sb.append(", unitTypeParentId=").append(unitTypeParentId);
        sb.append(", unitTypeName='").append(unitTypeName).append('\'');
        sb.append(", unitOrgcode='").append(unitOrgcode).append('\'');
        sb.append(", regDate=").append(regDate);
        sb.append(", regAreaId=").append(regAreaId);
        sb.append(", provinceId=").append(provinceId);
        sb.append(", cityId=").append(cityId);
        sb.append(", countyId=").append(countyId);
        sb.append(", regCapital=").append(regCapital);
        sb.append(", unitNatureCode='").append(unitNatureCode).append('\'');
        sb.append(", legalPerson='").append(legalPerson).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", contacts='").append(contacts).append('\'');
        sb.append(", contactNumber='").append(contactNumber).append('\'');
        sb.append(", officeNumber='").append(officeNumber).append('\'');
        sb.append(", faxNumber='").append(faxNumber).append('\'');
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", teams=").append(teams);
        sb.append(", deleted='").append(deleted).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
