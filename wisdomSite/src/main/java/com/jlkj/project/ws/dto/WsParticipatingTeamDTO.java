package com.jlkj.project.ws.dto;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import com.jlkj.project.ws.domain.WsParticipatingTeam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 班组 对象 t_ws_participating_team
 *
 * @author liujie
 * @date 2020-06-12
 */
@ApiModel("参建单位班组DTO")
public class WsParticipatingTeamDTO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 项目id */
    @ApiModelProperty("项目ID")
    private Integer projectId;

    /** 参建单位ID */
    @ApiModelProperty("参建单位ID")
    private Integer unitId;

    /** 班组名称 */
    @ApiModelProperty("班组名称")
    @Excel(name = "班组名称")
    private String teamName;

    /** 班组列表 */
    @ApiModelProperty("班组列表")
    private List<WsParticipatingTeam> teams;

    public List<WsParticipatingTeam> getTeams() {
        return teams;
    }

    public void setTeams(List<WsParticipatingTeam> teams) {
        this.teams = teams;
    }


    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName()
    {
        return teamName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("projectId", getProjectId())
            .append("unitId", getUnitId())
            .append("teamName", getTeamName())
            .toString();
    }
}
