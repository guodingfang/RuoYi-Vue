package com.jlkj.project.ws.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import com.jlkj.project.ws.domain.WsFileInfo;
import com.jlkj.project.ws.domain.WsQualificationCertificate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

/**
 * 班组人员 对象 t_ws_team_person
 *
 * @author jlkj
 * @date 2020-06-12
 */
@ApiModel("班组人员VO")
public class WsTeamPersonVO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("班组人员ID")
    private Integer id;

    /** $column.columnComment */
    @ApiModelProperty("项目Id")
    private Integer projectId;

    /** 参建单位ID */
    @ApiModelProperty("参建单位ID")
    private Integer unitId;

    /** 班组ID */
    @ApiModelProperty("班组ID")
    private Integer teamId;

    @Excel(name ="班组名称")
    private String teamName;

    /** 归属省份 */
    @ApiModelProperty("归属省份")
    private String province;

    /** 人员类型 建筑工人：0；管理人员：1； */
    @ApiModelProperty("人员类型")
    @Excel(name = "人员类型", readConverterExp="0=建筑工人,1=管理人员")
    private String personType;


    /** 人员状态  退场：0 ;在场：1； */
    @ApiModelProperty("人员状态")
    private String personStatus;

    /** 进场日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("进场日期")
    private Date inDate;

    /** 退场日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("退场日期")
    private Date outDate;

    /** 人员黑名单 黑名单：0；白名单：1 */
    @ApiModelProperty("人员黑名单")
    private String blacklist;

    /** 是否班组长 是：1；否：0 */
    @ApiModelProperty("是否班组长")
    private String monitor;

    /** 是否特殊作业人员 是：1；否：0 */
    private String specialWork;

    /** 姓名 */
    @ApiModelProperty("姓名代码")
    @Excel(name = "姓名")
    private String name;

    /** 工种*/
    @ApiModelProperty("工种code")
    private String workType;

    /** 工种*/
    @ApiModelProperty("工种名称")
    @Excel(name = "工种名称")
    private String workTypeName;


    /** 年龄*/
    @ApiModelProperty("年龄")
    @Excel(name = "年龄")
    private int age;


    /** 性别 男：1，女：0 */
    @Excel(name = "性别",readConverterExp = "1=男,0=女")
    private String sex;

    /** 民族代码 */
    private String nationCode;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    /** 身份证编号 */
    @ApiModelProperty("身份证号")
    @Excel(name = "身份证号")
    private String idNumber;

    /** 地址 */
    @Excel(name = "住址")
    private String address;

    /** 签发机关 */
    @Excel(name = "发证机关")
    private String authority;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效起始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectiveDate;

    /** 失效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效截止日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date invalidDate;

    /** 户口性质 */
    @Excel(name = "户口性质",readConverterExp = "0=农村,1=城市")
    private String residenceNature;

    /** 实名卡号 */
    @ApiModelProperty("实名卡号")
    @Excel(name = "实名卡号")
    private String cardNo;

    /** 手机号码 */
    @ApiModelProperty("手机号码")
    @Excel(name = "手机号")
    private String mobilePhone;

    /** 工资卡号 */
    @Excel(name = "银行卡号")
    private String payCard;

    /** 工资卡银行类型 */
    private String payCardBank;

    /** 开户行名称 */
    @Excel(name = "开户银行")
    private String nameOfBank;

    /** 政治面貌 */
    private String politicsStatusCode;

    /** 文化程度代码（0 群众； 1 共青团员 ； 2 预备党员 ；3 党员； 4  民主党派）  */
    private String cultureStandardCode;

    /** 血压 */
    private Integer bloodPressure;

    /** 心率（次/分） */
    private Integer heartRate;

    /** 人脸照片路径 */
    private String faceUrl;

    /** 体重（kg） */
    private Integer weight;

    /** 身高(cm) */
    private Integer height;

    /** 健康相关（1、是否有重大病史 2、是否有建筑行业禁忌病史 3、有无购买工伤或意外伤害保险 */
    private String healthRelated;

    /** 健康备注 */
    private String healthRemark;

    /** 合同编号 */
    private String contactNumber;

    /** 合同类型（0 无合同； 1 固定期限合同 2 不定期合同） */
    private String contactType;

    /** 合同生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date contactEffectiveDate;

    /** 合同失效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date contactInvalidDate;

    /** 合同附件1地址 */
    private String attachmentOneUrl;

    /** 备注 */
    private String comment;

    /** 更新时间 */
    private Date updatedTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;

    /** 是否删除（0 N, 1Y) */
    private String deleted;

    /** 工程名称 */
    @ApiModelProperty("工程名称")
    private String projectName;

    /** 所属参建单位 */
    @ApiModelProperty("所属参建单位")
    private String unitName;
    /** 成功授权打卡机数 */
    @ApiModelProperty("成功授权打卡机数")
    private Integer authNumber;

    /** 未成功授权打卡机数 */
    @ApiModelProperty("未成功授权打卡机数")
    private Integer unauthNumber;

    /** 云平台人员注册唯一标识 */
    @ApiModelProperty("云平台人员注册唯一标识")
    private String guid;



    /**证书列表*/
    @ApiModelProperty("证书列表")
    private List<WsQualificationCertificate> certificates;


    /** 文件信息 */
    @ApiModelProperty("文件信息")
    private List<WsFileInfo> fileInfo;


    public List<WsFileInfo> getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(List<WsFileInfo> fileInfo) {
        this.fileInfo = fileInfo;
    }

    public List<WsQualificationCertificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<WsQualificationCertificate> certificates) {
        this.certificates = certificates;
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
    @NotNull(message = "项目Id")
    public Integer getProjectId()
    {

        return projectId;
    }
    public void setUnitId(Integer unitId)
    {
        this.unitId = unitId;
    }
    @NotNull(message = "参建单位Id")
    public Integer getUnitId()
    {
        return unitId;
    }
    public void setTeamId(Integer teamId)
    {
        this.teamId = teamId;
    }
    @NotNull(message = "班组Id")
    public Integer getTeamId()
    {
        return teamId;
    }
    public void setPersonType(String personType)
    {
        this.personType = personType;
    }
    @NotBlank(message = "人员类型不能为空")
    public String getPersonType()
    {
        return personType;
    }
    public void setPersonStatus(String personStatus)
    {
        this.personStatus = personStatus;
    }

    public String getPersonStatus()
    {
        return personStatus;
    }
    public void setInDate(Date inDate)
    {
        this.inDate = inDate;
    }

    public Date getInDate()
    {
        return inDate;
    }
    public void setOutDate(Date outDate)
    {
        this.outDate = outDate;
    }

    public Date getOutDate()
    {
        return outDate;
    }

    public String getSpecialWork() {
        return specialWork;
    }

    public void setSpecialWork(String specialWork) {
        this.specialWork = specialWork;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    @NotBlank(message = "人员姓名不能为空")
    public String getName()
    {
        return name;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSex()
    {
        return sex;
    }
    public void setNationCode(String nationCode)
    {
        this.nationCode = nationCode;
    }

    public String getNationCode()
    {
        return nationCode;
    }
    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }
    public void setIdNumber(String idNumber)
    {
        this.idNumber = idNumber;
    }
    @Pattern(regexp = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)")
    @NotBlank(message = "身份证号不能为空")
    public String getIdNumber()
    {
        return idNumber;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setAuthority(String authority)
    {
        this.authority = authority;
    }

    public String getAuthority()
    {
        return authority;
    }
    public void setEffectiveDate(Date effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }
    @NotNull(message = "身份证生效日期不能为空")
    public Date getEffectiveDate()
    {
        return effectiveDate;
    }
    public void setInvalidDate(Date invalidDate)
    {
        this.invalidDate = invalidDate;
    }
    @NotNull(message = "身份证失效日期不能为空")
    public Date getInvalidDate()
    {
        return invalidDate;
    }
    public void setResidenceNature(String residenceNature)
    {
        this.residenceNature = residenceNature;
    }

    public String getResidenceNature()
    {
        return residenceNature;
    }
    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }

    public String getCardNo()
    {
        return cardNo;
    }
    public void setMobilePhone(String mobilePhone)
    {
        this.mobilePhone = mobilePhone;
    }

    @NotBlank(message = "手机号码不能为空")
    @Pattern(message = "手机号非法" ,regexp = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$")
    public String getMobilePhone()
    {
        return mobilePhone;
    }
    public void setPayCard(String payCard)
    {
        this.payCard = payCard;
    }

    public String getPayCard()
    {
        return payCard;
    }
    public void setPayCardBank(String payCardBank)
    {
        this.payCardBank = payCardBank;
    }

    public String getPayCardBank()
    {
        return payCardBank;
    }
    public void setNameOfBank(String nameOfBank)
    {
        this.nameOfBank = nameOfBank;
    }

    public String getNameOfBank()
    {
        return nameOfBank;
    }
    public void setPoliticsStatusCode(String politicsStatusCode)
    {
        this.politicsStatusCode = politicsStatusCode;
    }

    public String getPoliticsStatusCode()
    {
        return politicsStatusCode;
    }
    public void setCultureStandardCode(String cultureStandardCode)
    {
        this.cultureStandardCode = cultureStandardCode;
    }

    public String getCultureStandardCode()
    {
        return cultureStandardCode;
    }
    public void setBloodPressure(Integer bloodPressure)
    {
        this.bloodPressure = bloodPressure;
    }

    public Integer getBloodPressure()
    {
        return bloodPressure;
    }
    public void setHeartRate(Integer heartRate)
    {
        this.heartRate = heartRate;
    }

    public Integer getHeartRate()
    {
        return heartRate;
    }
    public void setFaceUrl(String faceUrl)
    {
        this.faceUrl = faceUrl;
    }

    public String getFaceUrl()
    {
        return faceUrl;
    }
    public void setWeight(Integer weight)
    {
        this.weight = weight;
    }

    public Integer getWeight()
    {
        return weight;
    }
    public void setHeight(Integer height)
    {
        this.height = height;
    }

    public Integer getHeight()
    {
        return height;
    }
    public void setHealthRelated(String healthRelated)
    {
        this.healthRelated = healthRelated;
    }

    public String getHealthRelated()
    {
        return healthRelated;
    }
    public void setHealthRemark(String healthRemark)
    {
        this.healthRemark = healthRemark;
    }

    public String getHealthRemark()
    {
        return healthRemark;
    }
    public void setContactNumber(String contactNumber)
    {
        this.contactNumber = contactNumber;
    }

    public String getContactNumber()
    {
        return contactNumber;
    }
    public void setContactType(String contactType)
    {
        this.contactType = contactType;
    }

    public String getContactType()
    {
        return contactType;
    }
    public void setContactEffectiveDate(Date contactEffectiveDate)
    {
        this.contactEffectiveDate = contactEffectiveDate;
    }

    public Date getContactEffectiveDate()
    {
        return contactEffectiveDate;
    }
    public void setContactInvalidDate(Date contactInvalidDate)
    {
        this.contactInvalidDate = contactInvalidDate;
    }

    public Date getContactInvalidDate()
    {
        return contactInvalidDate;
    }
    public void setAttachmentOneUrl(String attachmentOneUrl)
    {
        this.attachmentOneUrl = attachmentOneUrl;
    }

    public String getAttachmentOneUrl()
    {
        return attachmentOneUrl;
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

    public String getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
    @NotBlank(message = "工种不能为空")
    public String getWorkType() {
        return workType;
    }


    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getAuthNumber() {
        return authNumber;
    }

    public void setAuthNumber(Integer authNumber) {
        this.authNumber = authNumber;
    }

    public Integer getUnauthNumber() {
        return unauthNumber;
    }

    public void setUnauthNumber(Integer unauthNumber) {
        this.unauthNumber = unauthNumber;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsTeamPersonVO{");
        sb.append("id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", unitId=").append(unitId);
        sb.append(", teamId=").append(teamId);
        sb.append(", teamName='").append(teamName).append('\'');
        sb.append(", province='").append(province).append('\'');
        sb.append(", personType='").append(personType).append('\'');
        sb.append(", personStatus='").append(personStatus).append('\'');
        sb.append(", inDate=").append(inDate);
        sb.append(", outDate=").append(outDate);
        sb.append(", blacklist='").append(blacklist).append('\'');
        sb.append(", monitor='").append(monitor).append('\'');
        sb.append(", specialWork='").append(specialWork).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", workType='").append(workType).append('\'');
        sb.append(", workTypeName='").append(workTypeName).append('\'');
        sb.append(", age=").append(age);
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", nationCode='").append(nationCode).append('\'');
        sb.append(", birthDate=").append(birthDate);
        sb.append(", idNumber='").append(idNumber).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", authority='").append(authority).append('\'');
        sb.append(", effectiveDate=").append(effectiveDate);
        sb.append(", invalidDate=").append(invalidDate);
        sb.append(", residenceNature='").append(residenceNature).append('\'');
        sb.append(", cardNo='").append(cardNo).append('\'');
        sb.append(", mobilePhone='").append(mobilePhone).append('\'');
        sb.append(", payCard='").append(payCard).append('\'');
        sb.append(", payCardBank='").append(payCardBank).append('\'');
        sb.append(", nameOfBank='").append(nameOfBank).append('\'');
        sb.append(", politicsStatusCode='").append(politicsStatusCode).append('\'');
        sb.append(", cultureStandardCode='").append(cultureStandardCode).append('\'');
        sb.append(", bloodPressure=").append(bloodPressure);
        sb.append(", heartRate=").append(heartRate);
        sb.append(", faceUrl='").append(faceUrl).append('\'');
        sb.append(", weight=").append(weight);
        sb.append(", height=").append(height);
        sb.append(", healthRelated='").append(healthRelated).append('\'');
        sb.append(", healthRemark='").append(healthRemark).append('\'');
        sb.append(", contactNumber='").append(contactNumber).append('\'');
        sb.append(", contactType='").append(contactType).append('\'');
        sb.append(", contactEffectiveDate=").append(contactEffectiveDate);
        sb.append(", contactInvalidDate=").append(contactInvalidDate);
        sb.append(", attachmentOneUrl='").append(attachmentOneUrl).append('\'');
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", deleted='").append(deleted).append('\'');
        sb.append(", projectName='").append(projectName).append('\'');
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", authNumber=").append(authNumber);
        sb.append(", unauthNumber=").append(unauthNumber);
        sb.append(", guid='").append(guid).append('\'');
        sb.append(", certificates=").append(certificates);
        sb.append('}');
        return sb.toString();
    }
}
