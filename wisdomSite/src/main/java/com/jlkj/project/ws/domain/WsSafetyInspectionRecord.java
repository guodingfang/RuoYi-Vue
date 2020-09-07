package com.jlkj.project.ws.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.aspectj.lang.annotation.Excels;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Transient;

/**
 * 安全巡视记录 对象 t_ws_safety_inspection_record
 *
 * @author jlkj
 * @date 2020-07-13
 */
@ApiModel("安全巡视记录")
public class WsSafetyInspectionRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("主键id")
    @Excel(name = "序号")
    private Integer id;

    /** 项目id */
    @ApiModelProperty("项目id")
    private Integer projectId;

    /** 项目id */
    @ApiModelProperty("项目名称")
    @Excel(name = "项目名称")
    private String projectName;

    /** 归属公司名称 */
    @ApiModelProperty("归属公司名称")
    @Excel(name = "归属公司名称")
    private String projectUnitName;

    /** 检查部位 */
    @ApiModelProperty("检查部位")
    @Excel(name = "检查部位")
    private String checkPosition;

    /** 隐患等级1(一般)，2（严重） */
    @ApiModelProperty("隐患等级1(一般)，2（严重）")
    private String hazardLevel;

    /** 作业类型id */
    @ApiModelProperty("作业类型id")
    private Integer operationTypeId;

    @Transient
    @Excel(name = "作业类型名")
    @ApiModelProperty("作业类型名")
    private String operationName;

    /** 隐患描述 */
    @ApiModelProperty("隐患描述")
    @Excel(name = "隐患描述")
    private String hazardDescribe;

    /** 整改要求 */
    @ApiModelProperty("整改要求")
    @Excel(name = "整改要求")
    private String rectifyRequire;

    /** 整改人id */
    @ApiModelProperty("整改人id")
    private Integer rectifyPersonId;

    /** 整改期限 */
    @ApiModelProperty("整改期限")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "整改期限", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rectifyLimitDate;

    /** 验证人id */
    @ApiModelProperty("验证人id")
    private Integer verifyPersonId;

    /** 整改状态 1（待整改） ，2（待验证），3（已完成） */
    @ApiModelProperty("整改状态 1（待整改） ，2（待验证），3（已完成） ")
    private Integer status;

    /** 检查人id */
    @ApiModelProperty("检查人id")
    private Integer checkPositionId;

    /** 检查时间 */
    @ApiModelProperty("检查时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检查时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkDate;

    /** 检查结果 */
    @ApiModelProperty("检查结果")
    private String checkResult;

    /** 抄送人 */
    @ApiModelProperty("抄送人")
    private String senderId;

    /** 上传图片地址 */
    @ApiModelProperty("上传图片地址")
    private List<Object> uploadAddress;

    /** 创建人 */
    private Integer createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    private Integer updatedBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedTime;

    @Transient
    @ApiModelProperty("开始时间")
    private String beginTime;

    @Transient
    @ApiModelProperty("结束时间")
    private String endTime;

    @Transient
    @Excel(name = "检查人")
    @ApiModelProperty("检查人名")
    private String checkPositionName;//检查人name
    @Transient
    @Excel(name = "整改人")
    @ApiModelProperty("整改人name")
    private String repairPersonName;//整改人name
    @Transient
    @Excel(name = "验证人")
    @ApiModelProperty("验证人name")
    private String verifyPersonName;//验证人name
    @Transient
    @ApiModelProperty("抄送人name")
    private String senderName;//抄送人
    @Transient
    @Excel(name = "隐患等级名")
    @ApiModelProperty("隐患等级名")
    private String hazardLevelName;
    @Transient
    @Excel(name = "整改状态名")
    @ApiModelProperty("整改状态名")
    private String statusName;
    @Transient
    @Excel(name = "抄送人")
    private String senderNames;



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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getProjectId()
    {
        return projectId;
    }
    public void setCheckPosition(String checkPosition)
    {
        this.checkPosition = checkPosition;
    }

    public String getCheckPosition()
    {
        return checkPosition;
    }
    public void setHazardLevel(String hazardLevel)
    {
        this.hazardLevel = hazardLevel;
    }

    public String getHazardLevel()
    {
        return hazardLevel;
    }
    public void setOperationTypeId(Integer operationTypeId)
    {
        this.operationTypeId = operationTypeId;
    }

    public Integer getOperationTypeId()
    {
        return operationTypeId;
    }
    public void setHazardDescribe(String hazardDescribe)
    {
        this.hazardDescribe = hazardDescribe;
    }

    public String getHazardDescribe()
    {
        return hazardDescribe;
    }
    public void setRectifyRequire(String rectifyRequire)
    {
        this.rectifyRequire = rectifyRequire;
    }

    public String getRectifyRequire()
    {
        return rectifyRequire;
    }
    public void setRectifyPersonId(Integer rectifyPersonId)
    {
        this.rectifyPersonId = rectifyPersonId;
    }

    public Integer getRectifyPersonId()
    {
        return rectifyPersonId;
    }
    public void setRectifyLimitDate(Date rectifyLimitDate)
    {
        this.rectifyLimitDate = rectifyLimitDate;
    }

    public Date getRectifyLimitDate()
    {
        return rectifyLimitDate;
    }
    public void setVerifyPersonId(Integer verifyPersonId)
    {
        this.verifyPersonId = verifyPersonId;
    }

    public Integer getVerifyPersonId()
    {
        return verifyPersonId;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }
    public void setCheckPositionId(Integer checkPositionId)
    {
        this.checkPositionId = checkPositionId;
    }

    public Integer getCheckPositionId()
    {
        return checkPositionId;
    }
    public void setCheckDate(Date checkDate)
    {
        this.checkDate = checkDate;
    }

    public Date getCheckDate()
    {
        return checkDate;
    }
    public void setCheckResult(String checkResult)
    {
        this.checkResult = checkResult;
    }

    public String getCheckResult()
    {
        return checkResult;
    }
    public void setSenderId(String senderId)
    {
        this.senderId = senderId;
    }

    public String getSenderId()
    {
        return senderId;
    }
    public void setUploadAddress(List<Object>uploadAddress)
    {
        this.uploadAddress = uploadAddress;
    }

    public List<Object> getUploadAddress()
    {
        return uploadAddress;
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
    public void setUpdatedBy(Integer updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    public Integer getUpdatedBy()
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

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    @Override
    public String getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCheckPositionName() {
        return checkPositionName;
    }

    public void setCheckPositionName(String checkPositionName) {
        this.checkPositionName = checkPositionName;
    }

    public String getRepairPersonName() {
        return repairPersonName;
    }

    public void setRepairPersonName(String repairPersonName) {
        this.repairPersonName = repairPersonName;
    }

    public String getVerifyPersonName() {
        return verifyPersonName;
    }

    public void setVerifyPersonName(String verifyPersonName) {
        this.verifyPersonName = verifyPersonName;
    }

    public String getHazardLevelName() {
        return hazardLevelName;
    }

    public void setHazardLevelName(String hazardLevelName) {
        this.hazardLevelName = hazardLevelName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getSenderNames() {
        return senderNames;
    }

    public void setSenderNames(String senderNames) {
        this.senderNames = senderNames;
    }

    public String getProjectUnitName() {
        return projectUnitName;
    }

    public void setProjectUnitName(String projectUnitName) {
        this.projectUnitName = projectUnitName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("checkPosition", getCheckPosition())
            .append("hazardLevel", getHazardLevel())
            .append("operationTypeId", getOperationTypeId())
            .append("hazardDescribe", getHazardDescribe())
            .append("rectifyRequire", getRectifyRequire())
            .append("rectifyPersonId", getRectifyPersonId())
            .append("rectifyLimitDate", getRectifyLimitDate())
            .append("verifyPersonId", getVerifyPersonId())
            .append("status", getStatus())
            .append("checkPositionId", getCheckPositionId())
            .append("checkDate", getCheckDate())
            .append("checkResult", getCheckResult())
            .append("senderId", getSenderId())
            .append("uploadAddress", getUploadAddress())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
