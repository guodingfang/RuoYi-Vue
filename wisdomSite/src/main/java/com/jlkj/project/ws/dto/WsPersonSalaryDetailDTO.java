package com.jlkj.project.ws.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**   工资明细DTO
 * @ClassName WsPersonSalaryDetailDTO
 * @Description
 * @Author liujie
 * @Date 2020/7/31 18:04
 **/
public class WsPersonSalaryDetailDTO {
    @ApiModelProperty("工程ID")
    private Integer projectId ;
    @ApiModelProperty("单位ID")
    private Integer unitId ;
    @ApiModelProperty("班组ID")
    private Integer teamId;
    @ApiModelProperty("人员ID")
    private Integer personId;
    @ApiModelProperty("筛选日期yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM")
    private Date month;

    private String salaryYear;

    private String salaryMonth;

    private Date beginTime;

    private Date endTime;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getSalaryYear() {
        return salaryYear;
    }

    public void setSalaryYear(String salaryYear) {
        this.salaryYear = salaryYear;
    }

    public String getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(String salaryMonth) {
        this.salaryMonth = salaryMonth;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsPersonSalaryDetailDTO{");
        sb.append("projectId=").append(projectId);
        sb.append(", unitId=").append(unitId);
        sb.append(", teamId=").append(teamId);
        sb.append(", personId=").append(personId);
        sb.append(", month=").append(month);
        sb.append(", salaryYear='").append(salaryYear).append('\'');
        sb.append(", salaryMonth='").append(salaryMonth).append('\'');
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append('}');
        return sb.toString();
    }
}
