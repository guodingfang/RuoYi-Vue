package com.jlkj.project.system.domain.vo;


import java.util.ArrayList;
import java.util.List;

/**
 * 组织架构信息
 *
 * @author Ces
 * @create 2020-07-15 8:54
 **/
public class OrganizationVO {

    private static final long serialVersionUID = 1L;

    /** 组织ID */
    private Long organizationId;

    /** 父组织ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 组织名称 */
    private String organizationName;

    /** 类型 */
    private String type;

    /** 子组织 */
    private List<OrganizationVO> children = new ArrayList<OrganizationVO>();

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<OrganizationVO> getChildren() {
        return children;
    }

    public void setChildren(List<OrganizationVO> children) {
        this.children = children;
    }

}
