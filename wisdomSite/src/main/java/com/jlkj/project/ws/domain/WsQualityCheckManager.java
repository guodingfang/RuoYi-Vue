package com.jlkj.project.ws.domain;

import java.util.ArrayList;
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
 * 质量检查对象 t_ws_quality_check_manager
 *
 * @author gaowei
 * @date 2020-06-09
 */
public class WsQualityCheckManager extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("主键id")
    private int id;

    /** 项目id */
    @ApiModelProperty("项目id")
    private Integer projectId;

    /** 检查部位 */
    @ApiModelProperty("检查部位")
    @Excel(name = "检查部位")
    private String checkPosition;

    /** 质量问题等级 1(一般)，2（严重） */
    @ApiModelProperty("质量问题等级 1(一般)，2（严重）")
    private Long qualityProblemLevel;

    /** 质量问题类别id */
    @ApiModelProperty("质量问题类别id")
    private Long qualityProblemId;

    /** 备注 */
    @Excel(name = "问题描述")
    @ApiModelProperty("问题描述")
    private String comment;

    /** 整改要求 */
    @ApiModelProperty("整改要求")
    @Excel(name = "整改要求")
    private String rectificationRequirements;

    /** 检查人ID */
    @ApiModelProperty("检查人ID")
    private Long checkPositionId;

    /** 检查时间 */
    @ApiModelProperty("检查时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检查时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkDate;

    /** 检查照片地址 */
    @ApiModelProperty("检查照片地址")
    private List<Object> checkPositionAddres;


    /** 整改责任人id */
    @ApiModelProperty("整改责任人id")
    private Long repairPersonId;

    /** 整改期限 */
    @ApiModelProperty("整改期限")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "整改期限", width = 30, dateFormat = "yyyy-MM-dd")
    private Date repairLimitDate;

    /** 验证人id */
    @ApiModelProperty("验证人id")
    private Long verifyPersonId;

    /** 状态 1（待整改）2待验证 3 已完成 */
    @ApiModelProperty("状态 1（待整改）2待验证 3 已完成")
    private Long status;

    /** 是否删除 Y（删除），N(不删除) */
    @ApiModelProperty("是否删除 Y（删除），N(不删除)")
    private String isDelete;

    /** 创建人 */
    private Long createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    private Long updatedBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedTime;

    @Transient
    @ApiModelProperty("开始时间")
    private String kssj;

    @Transient
    @ApiModelProperty("结束时间")
    private String jssj;

    @Transient
    @ApiModelProperty("质量问题类别name")
    @Excel(name = "质量问题类别")
    private String qualityProblemName;//质量问题类别名称
    @Transient
    @Excel(name = "质量问题等级")
    @ApiModelProperty("质量问题等级name")
    private String qualityProblemLevelName;//质量问题等级name
    @Transient
    @Excel(name = "状态")
    @ApiModelProperty("状态name")
    private String statusName;//质量检查状态
    @Transient
    @Excel(name = "项目名称")
    @ApiModelProperty("项目名称")
    private String projectName;//项目名称
    @Transient
    @Excel(name = "检查人")
    @ApiModelProperty("检查人name")
    private String checkPositionName;//检查人name
    @Transient
    @Excel(name = "整改人")
    @ApiModelProperty("整改人name")
    private String repairPersonName;//整改人name
    @Transient
    @Excel(name = "验证人")
    @ApiModelProperty("验证人name")
    private String verifyPersonName;//验证人name

    /** 整改时间 */
    @ApiModelProperty("整改时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "整改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date repairDate;

    @ApiModelProperty("项目所属单位name")
    private String projectUnitName;

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }
    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
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
    public void setQualityProblemLevel(Long qualityProblemLevel)
    {
        this.qualityProblemLevel = qualityProblemLevel;
    }

    public Long getQualityProblemLevel()
    {
        return qualityProblemLevel;
    }
    public void setQualityProblemId(Long qualityProblemId)
    {
        this.qualityProblemId = qualityProblemId;
    }

    public Long getQualityProblemId()
    {
        return qualityProblemId;
    }
    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getComment()
    {
        return comment;
    }
    public void setCheckPositionId(Long checkPositionId)
    {
        this.checkPositionId = checkPositionId;
    }

    public Long getCheckPositionId()
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
    public void setCheckPositionAddres(List<Object> checkPositionAddres)
    {
        this.checkPositionAddres = checkPositionAddres;
    }

    public List<Object> getCheckPositionAddres()
    {
        return checkPositionAddres;
    }
    public void setRepairPersonId(Long repairPersonId)
    {
        this.repairPersonId = repairPersonId;
    }

    public Long getRepairPersonId()
    {
        return repairPersonId;
    }
    public void setRepairLimitDate(Date repairLimitDate)
    {
        this.repairLimitDate = repairLimitDate;
    }

    public Date getRepairLimitDate()
    {
        return repairLimitDate;
    }
    public void setVerifyPersonId(Long verifyPersonId)
    {
        this.verifyPersonId = verifyPersonId;
    }

    public Long getVerifyPersonId()
    {
        return verifyPersonId;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }
    public void setIsDelete(String isDelete)
    {
        this.isDelete = isDelete;
    }

    public String getIsDelete()
    {
        return isDelete;
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

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJssj() {
        return jssj;
    }

    public void setJssj(String jssj) {
        this.jssj = jssj;
    }

    public String getQualityProblemLevelName() {
        return qualityProblemLevelName;
    }

    public void setQualityProblemLevelName(String qualityProblemLevelName) {
        this.qualityProblemLevelName = qualityProblemLevelName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public String getQualityProblemName() {
        return qualityProblemName;
    }

    public void setQualityProblemName(String qualityProblemName) {
        this.qualityProblemName = qualityProblemName;
    }

    public String getRectificationRequirements() {
        return rectificationRequirements;
    }

    public void setRectificationRequirements(String rectificationRequirements) {
        this.rectificationRequirements = rectificationRequirements;
    }

    public Date getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(Date repairDate) {
        this.repairDate = repairDate;
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
            .append("qualityProblemLevel", getQualityProblemLevel())
            .append("qualityProblemId", getQualityProblemId())
            .append("comment", getComment())
            .append("rectificationRequirements", getRectificationRequirements())
            .append("checkPositionId", getCheckPositionId())
            .append("checkDate", getCheckDate())
            .append("checkPositionAddres", getCheckPositionAddres())
            .append("repairPersonId", getRepairPersonId())
            .append("repairLimitDate", getRepairLimitDate())
            .append("verifyPersonId", getVerifyPersonId())
            .append("status", getStatus())
            .append("isDelete", getIsDelete())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
