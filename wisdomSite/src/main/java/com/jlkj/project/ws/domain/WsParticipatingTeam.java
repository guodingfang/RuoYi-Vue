package com.jlkj.project.ws.domain;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 班组 对象 t_ws_participating_team
 *
 * @author jlkj
 * @date 2020-06-12
 */
@ApiModel("参建单位班组实体")
public class WsParticipatingTeam extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("班组ID")
    private Integer id;

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

    private List<WsTeamPerson> personList;

    /** 是否删除  */
    private String deleted;

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public List<WsTeamPerson> getPersonList() {
        return personList;
    }

    public void setPersonList(List<WsTeamPerson> personList) {
        this.personList = personList;
    }

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

    @NotNull(message = "所属项目不能为空")
    public Integer getProjectId()
    {
        return projectId;
    }
    public void setUnitId(Integer unitId)
    {
        this.unitId = unitId;
    }

    @NotNull(message = "归属单位不能为空")
    public Integer getUnitId()
    {
        return unitId;
    }
    public void setTeamName(String name)
    {
        this.teamName = name;
    }
    @NotBlank(message = "班组名称不能为空")
    public String getTeamName()
    {
        return teamName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("unitId", getUnitId())
            .append("teamName", getTeamName())
            .toString();
    }
}
