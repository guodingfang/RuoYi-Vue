package com.jlkj.framework.web.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jlkj.project.system.domain.SysEnterprise;
import com.jlkj.project.system.domain.SysMenu;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 *
 * @author jlkj
 */
public class TreeSelect implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 类型 */
    private int type;

    /** 权限:0无权限,1有权限 */
    private int auth;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect()
    {

    }

    public TreeSelect(SysEnterprise enterprise)
    {
        this.id = enterprise.getEnterpriseId();
        this.label = enterprise.getEnterpriseName();
        this.type = enterprise.getType();
        this.auth = enterprise.getAuth();
        this.children = enterprise.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(SysMenu menu)
    {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }



    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<TreeSelect> getChildren()
    {
        return children;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public void setChildren(List<TreeSelect> children)
    {
        this.children = children;
    }
}
