package com.jlkj.project.ws.intelligence.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 项目塔吊设备进出场记录 对象 t_ws_towercrane_entranceexit_record
 *
 * @author gaowei
 * @date 2020-07-17
 */
public class WsTowercraneEntranceexitRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Integer id;

    /** 塔吊设备id */
    @Excel(name = "塔吊设备id")
    private Integer towercraneEquipmentId;

    /** 项目id */
    @Excel(name = "项目id")
    private Integer projectId;

    /** 进场时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "进场时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date enterData;

    /** 出场时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出场时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date exitData;

    /** 进出场状态 1：进场；2出场 */
    @Excel(name = "进出场状态 1：进场；2出场")
    private Integer presence;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setTowercraneEquipmentId(Integer towercraneEquipmentId)
    {
        this.towercraneEquipmentId = towercraneEquipmentId;
    }

    public Integer getTowercraneEquipmentId()
    {
        return towercraneEquipmentId;
    }
    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }

    public Integer getProjectId()
    {
        return projectId;
    }
    public void setEnterData(Date enterData)
    {
        this.enterData = enterData;
    }

    public Date getEnterData()
    {
        return enterData;
    }
    public void setExitData(Date exitData)
    {
        this.exitData = exitData;
    }

    public Date getExitData()
    {
        return exitData;
    }
    public void setPresence(Integer presence)
    {
        this.presence = presence;
    }

    public Integer getPresence()
    {
        return presence;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("towercraneEquipmentId", getTowercraneEquipmentId())
            .append("projectId", getProjectId())
            .append("enterData", getEnterData())
            .append("exitData", getExitData())
            .append("presence", getPresence())
            .toString();
    }
}
