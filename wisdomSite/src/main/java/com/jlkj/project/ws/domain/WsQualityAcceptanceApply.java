package com.jlkj.project.ws.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.annotation.Transient;

/**
 * 质量验收申请 对象 t_ws_quality_acceptance_apply
 *
 * @author gaowei
 * @date 2020-06-09
 */
public class WsQualityAcceptanceApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("主键id")
    private Integer id;

    /** 项目id */
    @ApiModelProperty("项目id")
    private Integer projectId;

    /** 申请人id */
    @ApiModelProperty("申请人id")
    private Integer applyPersonId;

    /** 验收部位 */
    @ApiModelProperty("验收部位")
    @Excel(name = "验收部位")
    private String acceptancePosition;

    /** 验收内容 */
    @ApiModelProperty("验收内容")
    @Excel(name = "验收内容")
    private String acceptanceContent;

    /** 申请时间 */
    @ApiModelProperty("申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请时间",width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyDate;

    /** 计划验收时间 */
    @ApiModelProperty("计划验收时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划验收时间",width = 30, dateFormat = "yyyy-MM-dd")
    private Date planverificationDate;

    /** 计划验收人id */
    @ApiModelProperty("计划验收人id")
    private Integer acceptancePersonId;

    /** 验收状态 （（1：合格；2：不合格；3：待验收） */
    @ApiModelProperty("验收状态")
    private Integer acceptanceState;

    /** 是否删除 Y（删除），N(不删除) */
    private String isDelete;

    /** 创建人 */
    private Long createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    private Long updatedBy;

    /** 更新时间 */
    private Date updatedTime;

    /** 再次申请次数 */
    private int applyNum;

    @Transient
    @ApiModelProperty("开始时间")
    private String kssj;

    @Transient
    @ApiModelProperty("结束时间")
    private String jssj;

    @Transient
    @Excel(name = "项目名称")
    @ApiModelProperty("项目名称")
    private String projectName;//项目名称

    @Transient
    @Excel(name = "验收人")
    @ApiModelProperty("验收人")
    private String verifyPersonName;//验收人name

    @Transient
    @Excel(name = "申请人")
    @ApiModelProperty("申请人")
    private String applyPersonName;//申请人name

    @Transient
    @Excel(name = "验收结果")
    @ApiModelProperty("验收结果")
    private String acceptanceStateName;//验收结果name

    @Transient
    @Excel(name = "验收次数")
    @ApiModelProperty("验收次数")
    private String acceptanceNum;//验收次数

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

    public Integer getProjectId()
    {
        return projectId;
    }
    public void setApplyPersonId(Integer applyPersonId)
    {
        this.applyPersonId = applyPersonId;
    }

    public Integer getApplyPersonId()
    {
        return applyPersonId;
    }
    public void setAcceptancePosition(String acceptancePosition)
    {
        this.acceptancePosition = acceptancePosition;
    }

    public String getAcceptancePosition()
    {
        return acceptancePosition;
    }
    public void setAcceptanceContent(String acceptanceContent)
    {
        this.acceptanceContent = acceptanceContent;
    }

    public String getAcceptanceContent()
    {
        return acceptanceContent;
    }
    public void setApplyDate(Date applyDate)
    {
        this.applyDate = applyDate;
    }

    public Date getApplyDate()
    {
        return applyDate;
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
    public void setAcceptanceState(Integer acceptanceState)
    {
        this.acceptanceState = acceptanceState;
    }

    public Integer getAcceptanceState()
    {
        return acceptanceState;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public String getApplyPersonName() {
        return applyPersonName;
    }

    public void setApplyPersonName(String applyPersonName) {
        this.applyPersonName = applyPersonName;
    }

    public String getAcceptanceNum() {
        return acceptanceNum;
    }

    public void setAcceptanceNum(String acceptanceNum) {
        this.acceptanceNum = acceptanceNum;
    }

    public int getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(int applyNum) {
        this.applyNum = applyNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("applyPersonId", getApplyPersonId())
            .append("acceptancePosition", getAcceptancePosition())
            .append("acceptanceContent", getAcceptanceContent())
            .append("applyDate", getApplyDate())
            .append("planverificationDate", getPlanverificationDate())
            .append("acceptancePersonId", getAcceptancePersonId())
            .append("acceptanceState", getAcceptanceState())
            .append("applyNum", getApplyNum())
            .append("isDelete", getIsDelete())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
