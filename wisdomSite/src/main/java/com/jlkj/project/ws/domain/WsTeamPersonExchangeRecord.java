package com.jlkj.project.ws.domain;

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
public class WsTeamPersonExchangeRecord extends BaseEntity
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
    @ApiModelProperty("物品名称")
    @Excel(name = "物品名称")
    private String itemName;

    /** 筛选月份 */
    @ApiModelProperty("筛选月份")
    @Excel(name = "筛选月份")
    private Date month;
    /** 筛选关键字 */
    @ApiModelProperty("筛选关键字")
    @Excel(name = "筛选关键字")
    private String keyWordName;

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

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getKeyWordName() {
        return keyWordName;
    }

    public void setKeyWordName(String keyWordName) {
        this.keyWordName = keyWordName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsTeamPersonExchangeRecord{");
        sb.append("id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", personId=").append(personId);
        sb.append(", costPoint=").append(costPoint);
        sb.append(", exchangeMount=").append(exchangeMount);
        sb.append(", itemName='").append(itemName).append('\'');
        sb.append(", month=").append(month);
        sb.append(", keyWordName='").append(keyWordName).append('\'');
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append('}');
        return sb.toString();
    }
}
