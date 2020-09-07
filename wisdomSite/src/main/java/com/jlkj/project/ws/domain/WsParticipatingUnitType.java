package com.jlkj.project.ws.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.TreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 参建单位类型 对象 t_ws_participating_unit_type
 *
 * @author jlkj
 * @date 2020-07-02
 */
@ApiModel("参建单位类型实体")
public class WsParticipatingUnitType extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty(value = "ID")
    private Integer id;

    /** 类型码 */
    @Excel(name = "类型码")
    @ApiModelProperty(value = "类型码")
    private String code;


    /** 类型名称 */
    @Excel(name = "类型名称")
    @ApiModelProperty(value = "类型名称")
    private String typeName;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getTypeName()
    {
        return typeName;
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


}
