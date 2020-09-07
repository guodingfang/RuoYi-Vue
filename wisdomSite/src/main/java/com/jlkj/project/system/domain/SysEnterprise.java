package com.jlkj.project.system.domain;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.framework.web.domain.BaseEntity;

/**
 * 部门表 sys_enterprise
 *
 * @author jlkj
 */
@ApiModel("组织项目实体")
public class SysEnterprise extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 企业ID */
    @ApiModelProperty("企业ID/或者 项目ID")
    private Long enterpriseId;

    /** 父企业ID */
    @ApiModelProperty("父级ID")
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 企业名称 */
    @ApiModelProperty("名称")
    private String enterpriseName;

    /** 显示顺序 */
    @ApiModelProperty("排序")
    private String orderNum;

    /** 负责人 */
    private String leader;

    /** 联系电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 企业状态:0正常,1停用 */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 父企业名称 */
    private String parentName;

    /** 组织类型:0企业,1项目 */
    @ApiModelProperty("组织类型：0企业,1项目")
    private int type;

    /** 权限:0无权限,1有权限 */
    @ApiModelProperty("权限：0无权限,1有权限")
    private int auth;

    /** 集团简称 */
    @ApiModelProperty("集团简称")
    private String abbreviation;

    /** 子企业 */
    private List<SysEnterprise> children = new ArrayList<SysEnterprise>();

    public Long getEnterpriseId()
    {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId)
    {
        this.enterpriseId = enterpriseId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getAncestors()
    {
        return ancestors;
    }

    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    @NotBlank(message = "企业名称不能为空")
    @Size(min = 0, max = 30, message = "企业名称长度不能超过30个字符")
    public String getEnterpriseName()
    {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName)
    {
        this.enterpriseName = enterpriseName;
    }

    @NotBlank(message = "显示顺序不能为空")
    public String getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(String orderNum)
    {
        this.orderNum = orderNum;
    }

    public String getLeader()
    {
        return leader;
    }

    public void setLeader(String leader)
    {
        this.leader = leader;
    }

    @Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public List<SysEnterprise> getChildren()
    {
        return children;
    }

    public void setChildren(List<SysEnterprise> children)
    {
        this.children = children;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("enterpriseId", getEnterpriseId())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("enterpriseName", getEnterpriseName())
            .append("orderNum", getOrderNum())
            .append("leader", getLeader())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
    public SysEnterprise(){

    }
    public SysEnterprise(Long enterpriseId){
        this.enterpriseId = enterpriseId;
    }
}
