package com.jlkj.project.ws.domain;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 人员考勤平台注册对象 t_ws_attendance_register
 *
 * @author gaowei
 * @date 2020-06-23
 */
public class WsAttendanceRegister extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    /** 宇泛人员标识ID */
    @Excel(name = "宇泛人员标识ID")
    private String uuid;

    /** 人员ID */
    @Excel(name = "人员ID")
    private Integer personId;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getUuid()
    {
        return uuid;
    }
    public void setPersonId(Integer personId)
    {
        this.personId = personId;
    }

    public Integer getPersonId()
    {
        return personId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("uuid", getUuid())
            .append("personId", getPersonId())
            .toString();
    }
}
