package com.jlkj.project.ws.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 工资明细 对象VO
 *
 * @author liujie
 * @date 2020-07-31
 */
@ApiModel("工资明细对象VO")
public class WsPersonSalaryDetailVO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("主键")
    private Integer id;

    /** 项目ID */
    @ApiModelProperty("项目ID")
    private Integer projectId;

    /** 人员姓名 */
    @Excel(name = "人员姓名")
    @ApiModelProperty("人员姓名")
    private String personName;

    /** 单位名称 */
    @Excel(name = "单位名称",type = Excel.Type.EXPORT,isExport = true)
    @ApiModelProperty("单位名称")
    private String unitName;

    /** 班组名称 */
    @Excel(name = "班组名称",type = Excel.Type.EXPORT,isExport = true)
    @ApiModelProperty("班组名称")
    private String teamName;

    /** 身份证号 */
    @Excel(name = "身份证号" )
    @ApiModelProperty("身份证号")
    private String idNumber;

    /** 工资单编号 */
    @Excel(name = "工资单编号")
    @ApiModelProperty("工资单编号")
    private String payrollNumber;

    /** 应发金额 */
    @Excel(name = "应发金额")
    @ApiModelProperty("应发金额")
    private Double planningAmount;

    /** 实发金额 */
    @Excel(name = "实发金额")
    @ApiModelProperty("实发金额")
    private Double payingAmount;

    /** 年份 */
    @Excel(name = "年份")
    @ApiModelProperty("年份")
    private String salaryYear;

    /** 月份 */
    @Excel(name = "月份")
    @ApiModelProperty("月份")
    private String salaryMonth;

    /** 出勤天数 */
    @Excel(name = "出勤天数",type = Excel.Type.EXPORT,isExport = true)
    @ApiModelProperty("出勤天数")
    private String attendanceDays;

    /** 当月工时 */
    @Excel(name = "当月工时",type = Excel.Type.EXPORT,isExport = true)
    @ApiModelProperty("当月工时")
    private String monthHours;

    /** 发放日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发放日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("发放日期")
    private Date payingDate;

    /** 是否补发 */
    @Excel(name = "是否补发",type = Excel.Type.IMPORT)
    @ApiModelProperty("是否补发")
    private String backPay;




    @ApiModelProperty("人数")
    private Integer personNumber;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;

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
    @NotNull(message = "项目不能为空")
    public Integer getProjectId()
    {
        return projectId;
    }
    public void setIdNumber(String idNumber)
    {
        this.idNumber = idNumber;
    }

    @NotEmpty(message = "身份证号")
    public String getIdNumber()
    {
        return idNumber;
    }
    public void setPayrollNumber(String payrollNumber)
    {
        this.payrollNumber = payrollNumber;
    }
    @NotEmpty(message = "工资单号")
    public String getPayrollNumber()
    {
        return payrollNumber;
    }
    public void setPlanningAmount(Double planningAmount)
    {
        this.planningAmount = planningAmount;
    }

    public Double getPlanningAmount()
    {
        return planningAmount;
    }
    public void setPayingAmount(Double payingAmount)
    {
        this.payingAmount = payingAmount;
    }

    public Double getPayingAmount()
    {
        return payingAmount;
    }
    public void setSalaryYear(String salaryYear)
    {
        this.salaryYear = salaryYear;
    }

    @NotNull(message = "年份不能为空")
    public String getSalaryYear()
    {
        return salaryYear;
    }
    public void setSalaryMonth(String salaryMonth)
    {
        this.salaryMonth = salaryMonth;
    }
    @NotNull(message = "月份不能为空")
    public String getSalaryMonth()
    {
        return salaryMonth;
    }
    public void setPayingDate(Date payingDate)
    {
        this.payingDate = payingDate;
    }

    public Date getPayingDate()
    {
        return payingDate;
    }

    public String getBackPay() {
        return backPay;
    }

    public void setBackPay(String backPay) {
        this.backPay = backPay;
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

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getAttendanceDays() {
        return attendanceDays;
    }

    public void setAttendanceDays(String attendanceDays) {
        this.attendanceDays = attendanceDays;
    }

    public String getMonthHours() {
        return monthHours;
    }

    public void setMonthHours(String monthHours) {
        this.monthHours = monthHours;
    }

    public Integer getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(Integer personNumber) {
        this.personNumber = personNumber;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsPersonSalaryDetailVO{");
        sb.append("id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", idNumber='").append(idNumber).append('\'');
        sb.append(", payrollNumber='").append(payrollNumber).append('\'');
        sb.append(", planningAmount=").append(planningAmount);
        sb.append(", payingAmount=").append(payingAmount);
        sb.append(", salaryYear='").append(salaryYear).append('\'');
        sb.append(", salaryMonth='").append(salaryMonth).append('\'');
        sb.append(", payingDate=").append(payingDate);
        sb.append(", backPay='").append(backPay).append('\'');
        sb.append(", personName='").append(personName).append('\'');
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", teamName='").append(teamName).append('\'');
        sb.append(", attendanceDays='").append(attendanceDays).append('\'');
        sb.append(", monthHours='").append(monthHours).append('\'');
        sb.append(", personNumber=").append(personNumber);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", createdTime=").append(createdTime);
        sb.append('}');
        return sb.toString();
    }
}
