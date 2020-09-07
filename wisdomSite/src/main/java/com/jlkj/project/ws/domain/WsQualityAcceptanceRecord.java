package com.jlkj.project.ws.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.annotation.Transient;

/**
 * 质量验收记录 对象 t_ws_quality_acceptance_record
 *
 * @author gaowei
 * @date 2020-06-09
 */
public class WsQualityAcceptanceRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ApiModelProperty("主键id")
    private Integer id;

    /** 验收申请id */
    @ApiModelProperty("验收申请id")
    @Excel(name = "验收申请id")
    private Integer applyId;

    /** 计划验收时间 */
    @ApiModelProperty("计划验收时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划验收时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planverificationDate;

    /** 计划验收人id */
    @ApiModelProperty("计划验收人id")
    @Excel(name = "计划验收人id")
    private Integer acceptancePersonId;

    /** 验收时间 */
    @ApiModelProperty("验收时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "验收时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date verificationDate;

    /** 验收人id */
    @ApiModelProperty("验收人id")
    @Excel(name = "验收人id")
    private Integer verificationPersonId;

    /** 验收结果 （1：合格；2：不合格；3：待验收） */
    @ApiModelProperty("验收结果")
    @Excel(name = "验收结果 （1：合格；2：不合格；3：待验收）")
    private Integer acceptanceState;

    /** 验收次数 */
    @ApiModelProperty("验收次数")
    @Excel(name = "验收次数")
    private Integer acceptanceFrequency;

    /** 验收描述 */
    @ApiModelProperty("验收描述")
    @Excel(name = "验收描述")
    private String acceptanceDescribe;

    /** 验收照片地址 */
    @ApiModelProperty("验收照片地址")
    @Excel(name = "验收照片地址")
    private List<Object> acceptanceImageAddres;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private Long updatedBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    @Transient
    @ApiModelProperty("计划验收人name")
    private String acceptancePersonName;//计划验收人name

    @Transient
    @ApiModelProperty("验收人name")
    private String verifyPersonName;//验收人name

    @Transient
    @ApiModelProperty("验收结果name")
    private String acceptanceStateName;//验收结果name

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setApplyId(Integer applyId)
    {
        this.applyId = applyId;
    }

    public Integer getApplyId()
    {
        return applyId;
    }
    public void setPlanverificationDate(Date planverificationDate)
    {
        this.planverificationDate = planverificationDate;
    }

    public Date getPlanverificationDate()
    {
        return planverificationDate;
    }
    public void setAcceptancePersonId(Integer acceptancePersonId)
    {
        this.acceptancePersonId = acceptancePersonId;
    }

    public Integer getAcceptancePersonId()
    {
        return acceptancePersonId;
    }
    public void setVerificationDate(Date verificationDate)
    {
        this.verificationDate = verificationDate;
    }

    public Date getVerificationDate()
    {
        return verificationDate;
    }
    public void setVerificationPersonId(Integer verificationPersonId)
    {
        this.verificationPersonId = verificationPersonId;
    }

    public Integer getVerificationPersonId()
    {
        return verificationPersonId;
    }
    public void setAcceptanceState(Integer acceptanceState)
    {
        this.acceptanceState = acceptanceState;
    }

    public Integer getAcceptanceState()
    {
        return acceptanceState;
    }
    public void setAcceptanceFrequency(Integer acceptanceFrequency)
    {
        this.acceptanceFrequency = acceptanceFrequency;
    }

    public Integer getAcceptanceFrequency()
    {
        return acceptanceFrequency;
    }
    public void setAcceptanceDescribe(String acceptanceDescribe)
    {
        this.acceptanceDescribe = acceptanceDescribe;
    }

    public String getAcceptanceDescribe()
    {
        return acceptanceDescribe;
    }
    public void setAcceptanceImageAddres(List<Object> acceptanceImageAddres)
    {
        this.acceptanceImageAddres = acceptanceImageAddres;
    }

    public List<Object> getAcceptanceImageAddres()
    {
        return acceptanceImageAddres;
    }
    public void setCreatedBy(Long createdBy)
    {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy()
    {
        return createdBy;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }
    public void setUpdatedBy(Long updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy()
    {
        return updatedBy;
    }
    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }

    public String getAcceptancePersonName() {
        return acceptancePersonName;
    }

    public void setAcceptancePersonName(String acceptancePersonName) {
        this.acceptancePersonName = acceptancePersonName;
    }

    public String getVerifyPersonName() {
        return verifyPersonName;
    }

    public void setVerifyPersonName(String verifyPersonName) {
        this.verifyPersonName = verifyPersonName;
    }

    public String getAcceptanceStateName() {
        return acceptanceStateName;
    }

    public void setAcceptanceStateName(String acceptanceStateName) {
        this.acceptanceStateName = acceptanceStateName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("applyId", getApplyId())
            .append("planverificationDate", getPlanverificationDate())
            .append("acceptancePersonId", getAcceptancePersonId())
            .append("verificationDate", getVerificationDate())
            .append("verificationPersonId", getVerificationPersonId())
            .append("acceptanceState", getAcceptanceState())
            .append("acceptanceFrequency", getAcceptanceFrequency())
            .append("acceptanceDescribe", getAcceptanceDescribe())
            .append("acceptanceImageAddres", getAcceptanceImageAddres())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
