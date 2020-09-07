package com.jlkj.project.system.domain;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import com.jlkj.framework.web.domain.server.Sys;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 sys_organization_account
 *
 * @author jlkj
 * @date 2020-07-17
 */
public class SysOrganizationAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 组织ID */
    @Excel(name = "组织ID")
    private Long organizationid;

    /** 账号ID */
    @Excel(name = "账号ID")
    private Long accountid;

    /** 类型：0，企业；1，项目 */
    @Excel(name = "类型：0，企业；1，项目")
    private Integer type;

    private String orgName;

    public void setOrganizationid(Long organizationid)
    {
        this.organizationid = organizationid;
    }

    public Long getOrganizationid()
    {
        return organizationid;
    }
    public void setAccountid(Long accountid)
    {
        this.accountid = accountid;
    }

    public Long getAccountid()
    {
        return accountid;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public Integer getType()
    {
        return type;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("organizationid", getOrganizationid())
            .append("accountid", getAccountid())
            .append("type", getType())
            .toString();
    }

    public SysOrganizationAccount(Long accountid){
        this.accountid = accountid;
    }
    public SysOrganizationAccount(){

    }
    public SysOrganizationAccount(Long accountid,Integer type){
        this.accountid = accountid;
        this.type = type;
    }
    public SysOrganizationAccount(Long accountid, Long organizationid, Integer type){
        this.accountid = accountid;
        this.organizationid = organizationid;
        this.type = type;
    }
}
