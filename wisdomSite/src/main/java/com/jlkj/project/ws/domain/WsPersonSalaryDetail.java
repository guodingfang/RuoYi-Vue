package com.jlkj.project.ws.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 工资明细 对象 t_ws_person_salary_detail
 *
 * @author liujie
 * @date 2020-07-31
 */
@ApiModel("工资明细对象")
public class WsPersonSalaryDetail extends BaseEntity
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

    /** 发放日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发放日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("发放日期")
    private Date payingDate;

    /** 是否补发 */
    @Excel(name = "是否补发")
    @ApiModelProperty("是否补发")
    private String backPay;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;


    @ApiModelProperty("人员数量")
    private Integer personNumber ;


    public Integer getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(Integer personNumber) {
        this.personNumber = personNumber;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
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



    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("idNumber", getIdNumber())
            .append("payrollNumber", getPayrollNumber())
            .append("planningAmount", getPlanningAmount())
            .append("payingAmount", getPayingAmount())
            .append("salaryYear", getSalaryYear())
            .append("salaryMonth", getSalaryMonth())
            .append("payingDate", getPayingDate())
            .append("backPay", getBackPay())
            .append("updatedTime", getUpdatedTime())
            .append("createdTime", getCreatedTime())
            .toString();
    }


    public void formatCorrect(){
        if(StringUtils.isNotEmpty(salaryMonth)) this.salaryMonth = "0"+salaryMonth.substring(salaryMonth.length()-1);

        if(StringUtils.isNotEmpty(backPay)) this.backPay = "是".equals(backPay)?"1":"0";
    }
}
