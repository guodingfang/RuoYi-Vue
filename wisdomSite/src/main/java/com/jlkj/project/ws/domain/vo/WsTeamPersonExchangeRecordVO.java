package com.jlkj.project.ws.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 班组人员兑换记录对象 t_ws_team_person_exchange_record
 *
 * @author liujie
 * @date 2020-07-23
 */
@ApiModel("班组人员兑换记录对象")
public class WsTeamPersonExchangeRecordVO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Integer id;

    /** 项目id */
    @ApiModelProperty("项目id")
    @Excel(name = "项目id")
    private Integer projectId;

    /** 人员ID */
    @ApiModelProperty("人员ID")
    @Excel(name = "人员ID")
    private Integer personId;

    /** 使用点券 */
    @ApiModelProperty("使用点券")
    @Excel(name = "使用点券")
    private Integer costPoint;

    /** 兑换数量 */
    @ApiModelProperty("兑换数量")
    @Excel(name = "兑换数量")
    private Integer exchangeMount;

    /** 物品名称 */
    @ApiModelProperty("项目id")
    @Excel(name = "物品名称")
    private String itemName;

    @ApiModelProperty("人员姓名")
    @Excel(name = "人员姓名")
    private String personName;

    @ApiModelProperty("身份证号")
    @Excel(name = "身份证号")
    private String idNumber;

    @ApiModelProperty("班组名称")
    @Excel(name = "班组名称")
    private String teamName;

    @ApiModelProperty("参建单位名称")
    @Excel(name = "参建单位名称")
    private String unitName;


    @ApiModelProperty("扣除积分值")
    @Excel(name = "扣除积分值")
    private String totalDeductPoints;

    @ApiModelProperty("人脸照片")
    @Excel(name = "人脸照片")
    private String faceUrl;

    /** 创建时间 */
    @ApiModelProperty("兑换时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

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
    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }
    @NotNull(message = "项目id不能为空")
    public Integer getProjectId()
    {
        return projectId;
    }
    public void setPersonId(Integer personId)
    {
        this.personId = personId;
    }
    @NotNull(message = "人员id不能为空")
    public Integer getPersonId()
    {
        return personId;
    }
    public void setCostPoint(Integer costPoint)
    {
        this.costPoint = costPoint;
    }

    public Integer getCostPoint()
    {
        return costPoint;
    }
    public void setExchangeMount(Integer exchangeMount)
    {
        this.exchangeMount = exchangeMount;
    }

    public Integer getExchangeMount()
    {
        return exchangeMount;
    }
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public String getItemName()
    {
        return itemName;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }
    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getTotalDeductPoints() {
        return totalDeductPoints;
    }

    public void setTotalDeductPoints(String totalDeductPoints) {
        this.totalDeductPoints = totalDeductPoints;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsTeamPersonExchangeRecordVO{");
        sb.append("id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", personId=").append(personId);
        sb.append(", costPoint=").append(costPoint);
        sb.append(", exchangeMount=").append(exchangeMount);
        sb.append(", itemName='").append(itemName).append('\'');
        sb.append(", personName='").append(personName).append('\'');
        sb.append(", idNumber='").append(idNumber).append('\'');
        sb.append(", teamName='").append(teamName).append('\'');
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", totalDeductPoints='").append(totalDeductPoints).append('\'');
        sb.append(", faceUrl='").append(faceUrl).append('\'');
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append('}');
        return sb.toString();
    }
}
