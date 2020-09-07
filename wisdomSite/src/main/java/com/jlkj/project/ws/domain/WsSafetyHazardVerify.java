package com.jlkj.project.ws.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Transient;

/**
 * 隐患验证 对象 t_ws_safety_hazard_verify
 *
 * @author jlkj
 * @date 2020-07-13
 */
@ApiModel("隐患验证对象")
public class WsSafetyHazardVerify extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("主键id")
    private Integer id;

    /** 安全巡视id */
    @ApiModelProperty("安全巡视id")
    @Excel(name = "安全巡视id")
    private Integer inspectionId;

    /** 验证人 */
    @ApiModelProperty("验证人")
    @Excel(name = "验证人")
    private Integer verifyPersonId;

    /** 验证人姓名 */
    @Transient
    @ApiModelProperty("验证人姓名")
    @Excel(name = "验证人姓名")
    private String verifyPersonName;

    /** 验证时间 */
    @ApiModelProperty("验证时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "验证时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date verifyDate;

    /** 验证结果（1：合格，2：不合格） */
    @ApiModelProperty("验证结果（1：合格，2：不合格）")
    @Excel(name = "验证结果", readConverterExp = "1=：合格，2：不合格")
    private Integer verifyResult;


    /** 验证结果 */
    @Transient
    @ApiModelProperty("验证结果name")
    @Excel(name = "验证结果name")
    private String verifyResultName;

    /** 验证结果描述 */
    @ApiModelProperty("验证结果描述")
    @Excel(name = "验证结果描述")
    private String verifyResultDescription;

    /** 验证图片地址 */
    @ApiModelProperty("验证图片地址")
    @Excel(name = "验证图片地址")
    private List<Object> verifyImageAddres;

    /** 创建人 */
    @Excel(name = "创建人")
    private Integer createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private Integer uodatedBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setInspectionId(Integer inspectionId)
    {
        this.inspectionId = inspectionId;
    }

    public Integer getInspectionId()
    {
        return inspectionId;
    }

    public void setVerifyPersonId(Integer verifyPersonId)
    {
        this.verifyPersonId = verifyPersonId;
    }

    public Integer getVerifyPersonId()
    {
        return verifyPersonId;
    }
    public void setVerifyDate(Date verifyDate)
    {
        this.verifyDate = verifyDate;
    }

    public Date getVerifyDate()
    {
        return verifyDate;
    }
    public void setVerifyResult(Integer verifyResult)
    {
        this.verifyResult = verifyResult;
    }

    public Integer getVerifyResult()
    {
        return verifyResult;
    }
    public void setVerifyResultDescription(String verifyResultDescription)
    {
        this.verifyResultDescription = verifyResultDescription;
    }

    public String getVerifyResultDescription()
    {
        return verifyResultDescription;
    }
    public void setVerifyImageAddres(List<Object> verifyImageAddres)
    {
        this.verifyImageAddres = verifyImageAddres;
    }

    public List<Object> getVerifyImageAddres()
    {
        return verifyImageAddres;
    }
    public void setCreatedBy(Integer createdBy)
    {
        this.createdBy = createdBy;
    }

    public Integer getCreatedBy()
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
    public void setUodatedBy(Integer uodatedBy)
    {
        this.uodatedBy = uodatedBy;
    }

    public Integer getUodatedBy()
    {
        return uodatedBy;
    }
    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }

    public String getVerifyPersonName() {
        return verifyPersonName;
    }

    public void setVerifyPersonName(String verifyPersonName) {
        this.verifyPersonName = verifyPersonName;
    }

    public String getVerifyResultName() {
        return verifyResultName;
    }

    public void setVerifyResultName(String verifyResultName) {
        this.verifyResultName = verifyResultName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("inspectionId", getInspectionId())
            .append("verifyPersonId", getVerifyPersonId())
            .append("verifyDate", getVerifyDate())
            .append("verifyResult", getVerifyResult())
            .append("verifyResultDescription", getVerifyResultDescription())
            .append("verifyImageAddres", getVerifyImageAddres())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("uodatedBy", getUodatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
