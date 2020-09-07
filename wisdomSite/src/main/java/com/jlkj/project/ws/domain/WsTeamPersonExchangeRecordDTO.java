package com.jlkj.project.ws.domain;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 班组人员兑换记录DTO
 *
 * @author liujie
 * @date 2020-07-23
 */
@ApiModel("班组人员兑换记录DTO")
public class WsTeamPersonExchangeRecordDTO implements Serializable
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

    /** 兑换物品列表 */
    @ApiModelProperty("兑换物品列表")
    @Excel(name = "兑换物品列表")
    private List<WsTeamPersonExchangeRecord> exchanges;



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
    public void setPersonId(Integer personId)
    {
        this.personId = personId;
    }

    public Integer getPersonId()
    {
        return personId;
    }

    public List<WsTeamPersonExchangeRecord> getExchanges() {
        return exchanges;
    }

    public void setExchanges(List<WsTeamPersonExchangeRecord> exchanges) {
        this.exchanges = exchanges;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsTeamPersonExchangeRecordDTO{");
        sb.append("id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", personId=").append(personId);
        sb.append(", exchanges=").append(exchanges);
        sb.append('}');
        return sb.toString();
    }
}
