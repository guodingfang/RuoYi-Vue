package com.jlkj.project.ws.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 质量检查验证 对象 t_ws_quality_check_verify
 *
 * @author gaowei
 * @date 2020-06-09
 */
public class WsQualityCheckVerify extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ApiModelProperty("主键id")
    private Integer id;

    /** 质量检查id */
    @ApiModelProperty("质量检查id")
    @Excel(name = "质量检查id")
    private int checkmanagerId;

    /** 整改id */
    @ApiModelProperty("整改id")
    @Excel(name = "整改id")
    private Integer repairId;

    /** 验证人id */
    @ApiModelProperty("验证人id")
    @Excel(name = "验证人id")
    private Long verifyPersonId;

    /** 验证时间 */
    @ApiModelProperty("验证时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "验证时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date verifyDate;

    /** 验证结果描述 */
    @ApiModelProperty("验证结果描述")
    @Excel(name = "验证结果描述")
    private String verifyResultDescription;

    /** 验证结果(1:合格，2：不合格) */
    @Excel(name = "验证结果")
    @ApiModelProperty("验证结果")
    private Long verifyResult;

    /** 验证照片地址 */
    @ApiModelProperty("验证照片地址")
    @Excel(name = "验证照片地址")
    private List<Object> verifyImageAddres;

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

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setCheckmanagerId(int checkmanagerId)
    {
        this.checkmanagerId = checkmanagerId;
    }

    public int getCheckmanagerId()
    {
        return checkmanagerId;
    }
    public void setVerifyDate(Date verifyDate)
    {
        this.verifyDate = verifyDate;
    }

    public Date getVerifyDate()
    {
        return verifyDate;
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

    public Integer getRepairId() {
        return repairId;
    }

    public void setRepairId(Integer repairId) {
        this.repairId = repairId;
    }

    public Long getVerifyPersonId() {
        return verifyPersonId;
    }

    public void setVerifyPersonId(Long verifyPersonId) {
        this.verifyPersonId = verifyPersonId;
    }

    public Long getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(Long verifyResult) {
        this.verifyResult = verifyResult;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("checkmanagerId", getCheckmanagerId())
            .append("repairId", getRepairId())
            .append("verifyPersonId", getVerifyPersonId())
            .append("verifyDate", getVerifyDate())
            .append("verifyResultDescription", getVerifyResultDescription())
            .append("verifyResult", getVerifyResult())
            .append("verifyImageAddres", getVerifyImageAddres())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
