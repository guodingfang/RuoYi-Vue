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
 * 质量检查整改 对象 t_ws_quality_check_repair
 *
 * @author gaowei
 * @date 2020-06-09
 */
public class WsQualityCheckRepair extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ApiModelProperty("主键id")
    private Integer id;

    /** 质量检查id */
    @ApiModelProperty("质量检查id")
    @Excel(name = "质量检查id")
    private int checkmanagerId;

    /** 整改责任人id */
    @ApiModelProperty("整改责任人id")
    @Excel(name = "整改责任人id")
    private Long repairPersonId;

    /** 整改日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("整改日期")
    @Excel(name = "整改日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date repairDate;

    /** 整改结果描述 */
    @ApiModelProperty("整改结果描述")
    @Excel(name = "整改结果描述")
    private String repairResultDescription;

    /** 整改照片地址 */
    @Excel(name = "整改照片地址")
    @ApiModelProperty("整改照片地址")
    private List<Object> repairImageAddres;

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
    @ApiModelProperty("整改人name")
    private String repairPersonName;//整改人name

    @Transient
    @ApiModelProperty("验证人name")
    private String verifyPersonNmae;//验证人name

    /** 验证时间 */
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("验证时间")
    private Date verifyDate;

    /** 验证结果描述 */
    @Transient
    @ApiModelProperty("验证结果描述")
    private String verifyResultDescription;

    /** 验证照片地址 */
    @Transient
    @ApiModelProperty("验证照片地址")
    private List<Object> verifyImageAddres;

    @Transient
    @ApiModelProperty("验证结果")
    private String verifyResult;//验证结果（1：合格，2 不合格）

    @Transient
    @ApiModelProperty("验证id")
    private Integer verifyId;//验证id

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
    public void setRepairDate(Date repairDate)
    {
        this.repairDate = repairDate;
    }

    public Date getRepairDate()
    {
        return repairDate;
    }
    public void setRepairResultDescription(String repairResultDescription)
    {
        this.repairResultDescription = repairResultDescription;
    }

    public String getRepairResultDescription()
    {
        return repairResultDescription;
    }
    public void setRepairImageAddres(List<Object> repairImageAddres)
    {
        this.repairImageAddres = repairImageAddres;
    }

    public List<Object> getRepairImageAddres()
    {
        return repairImageAddres;
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

    public Long getRepairPersonId() {
        return repairPersonId;
    }

    public void setRepairPersonId(Long repairPersonId) {
        this.repairPersonId = repairPersonId;
    }

    public String getRepairPersonName() {
        return repairPersonName;
    }

    public void setRepairPersonName(String repairPersonName) {
        this.repairPersonName = repairPersonName;
    }

    public String getVerifyPersonNmae() {
        return verifyPersonNmae;
    }

    public void setVerifyPersonNmae(String verifyPersonNmae) {
        this.verifyPersonNmae = verifyPersonNmae;
    }

    public Date getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(Date verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getVerifyResultDescription() {
        return verifyResultDescription;
    }

    public void setVerifyResultDescription(String verifyResultDescription) {
        this.verifyResultDescription = verifyResultDescription;
    }

    public List<Object> getVerifyImageAddres() {
        return verifyImageAddres;
    }

    public void setVerifyImageAddres(List<Object> verifyImageAddres) {
        this.verifyImageAddres = verifyImageAddres;
    }

    public String getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(String verifyResult) {
        this.verifyResult = verifyResult;
    }

    public Integer getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(Integer verifyId) {
        this.verifyId = verifyId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("checkmanagerId", getCheckmanagerId())
            .append("repairPersonId", getRepairPersonId())
            .append("repairDate", getRepairDate())
            .append("repairResultDescription", getRepairResultDescription())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
