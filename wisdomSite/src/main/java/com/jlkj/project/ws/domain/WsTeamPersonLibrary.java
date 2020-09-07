package com.jlkj.project.ws.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 班组人员库对象 t_ws_team_person_library
 *
 * @author jlkj
 * @date 2020-06-12
 */
public class WsTeamPersonLibrary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;

    /** 集团企业ID*/
    private Long enterpriseId;

    /** 归属省份 */
    @ApiModelProperty("归属省份")
    private String  province;

    /** 身份证编号 */
    @Excel(name = "身份证编号")
    private String idNumber;

    /** 人员类型 建筑工人：0；管理人员：1； */
    @Excel(name = "人员类型 建筑工人：0；管理人员：1；")
    private String personType;

    /**工种cdoe*/
    private String workType;

    /** 工种*/
    @ApiModelProperty("工种名称")
    @Excel(name = "工种名称")
    private String workTypeName;


    /** 是否特殊作业人员 是：1；否：2 */
    @Excel(name = "是否特殊作业人员 是：1；否：2")
    private String specialWork;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 年龄*/
    @ApiModelProperty("年龄")
    @Excel(name = "年龄")
    private int age;

    /** 性别 男：1，女：2 */
    @Excel(name = "性别 男：1，女：0")
    private String sex;

    /** 民族代码 */
    @Excel(name = "民族代码")
    private String nationCode;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthDate;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 签发机关 */
    @Excel(name = "签发机关")
    private String authority;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectiveDate;

    /** 失效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "失效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date invalidDate;

    /** 户口性质 */
    @Excel(name = "户口性质")
    private String residenceNature;

    /** 实名卡号 */
    @Excel(name = "实名卡号")
    private String cardNo;

    /** 手机号 */
    @Excel(name = "手机号")
    private String mobilePhone;

    /** 工资卡号 */
    @Excel(name = "工资卡号")
    private String payCard;

    /** 工资卡银行 */
    @Excel(name = "工资卡银行")
    private String payCardBank;

    /** 开户行名称 */
    @Excel(name = "开户行名称")
    private String nameOfBank;

    /** 政治面貌 */
    @Excel(name = "政治面貌")
    private String politicsStatusCode;

    /** 文化程度代码（0 群众； 1 共青团员 ； 2 预备党员 ；3 党员； 4  民主党派）  */
    @Excel(name = "文化程度代码", readConverterExp = "0=,群=众；,1=,共=青团员,；=,2=,预=备党员,；=3,党=员；,4=,民=主党派")
    private String cultureStandardCode;

    /** 血压 */
    @Excel(name = "血压")
    private Integer bloodPressure;

    /** 心率（次/分） */
    @Excel(name = "心率", readConverterExp = "次=/分")
    private Integer heartRate;

    /** 人脸照片路径 */
    @Excel(name = "人脸照片路径")
    private String faceUrl;

    /** 体重（kg） */
    @Excel(name = "体重", readConverterExp = "k=g")
    private Integer weight;

    /** 身高(cm) */
    @Excel(name = "身高(cm)")
    private Integer height;

    /** 健康相关（1、是否有重大病史 2、是否有建筑行业禁忌病史 3、有无购买工伤或意外伤害保险 */
    @Excel(name = "健康相关", readConverterExp = "健康相关（1、是否有重大病史 2、是否有建筑行业禁忌病史 3、有无购买工伤或意外伤害保险")
    private String healthRelated;

    /** 健康备注 */
    @Excel(name = "健康备注")
    private String healthRemark;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String contactNumber;

    /** 合同类型（0 无合同； 1 固定期限合同 2 不定期合同） */
    @Excel(name = "合同类型", readConverterExp = "0=,无=合同；,1=,固=定期限合同,2=,不=定期合同")
    private String contactType;

    /** 合同生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "合同生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date contactEffectiveDate;

    /** 合同失效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "合同失效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date contactInvalidDate;

    /** 合同附件1地址 */
    @Excel(name = "合同附件1地址")
    private String attachmentOneUrl;

    /** 备注 */
    @Excel(name = "备注")
    private String comment;

    /** 更新时间 */
    private Date updatedTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 搜索关键字*/
    private  String keyWordName;

    private String sexName;

    private String cultureStandard;

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getCultureStandard() {
        return cultureStandard;
    }

    public void setCultureStandard(String cultureStandard) {
        this.cultureStandard = cultureStandard;
    }

    public String getKeyWordName() {
        return keyWordName;
    }

    public void setKeyWordName(String keyWordName) {
        this.keyWordName = keyWordName;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setIdNumber(String idNumber)
    {
        this.idNumber = idNumber;
    }

    public String getIdNumber()
    {
        return idNumber;
    }
    public void setPersonType(String personType)
    {
        this.personType = personType;
    }

    public String getPersonType()
    {
        return personType;
    }
    public void setName(String name)
    {
        this.name = name;
    }

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

    public Date getEffectiveDate()
    {
        return effectiveDate;
    }
    public void setInvalidDate(Date invalidDate)
    {
        this.invalidDate = invalidDate;
    }

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

    public String getSpecialWork() {
        return specialWork;
    }

    public void setSpecialWork(String specialWork) {
        this.specialWork = specialWork;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsTeamPersonLibrary{");
        sb.append("id=").append(id);
        sb.append(", province='").append(province).append('\'');
        sb.append(", idNumber='").append(idNumber).append('\'');
        sb.append(", personType='").append(personType).append('\'');
        sb.append(", workType='").append(workType).append('\'');
        sb.append(", workTypeName='").append(workTypeName).append('\'');
        sb.append(", specialWork='").append(specialWork).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", nationCode='").append(nationCode).append('\'');
        sb.append(", birthDate=").append(birthDate);
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
        sb.append(", keywordName='").append(keyWordName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
