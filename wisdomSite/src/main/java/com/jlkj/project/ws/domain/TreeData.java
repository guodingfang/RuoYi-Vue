package com.jlkj.project.ws.domain;

import java.util.List;

/**
 * 平面图管理区域二级树
 * @Author pyy
 * @Date 2020/7/4 19:00
 */
public class TreeData {

    private Integer id;

    private String label;


    private List<TreeData> children;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegionName() {
        return label;
    }

    public void setRegionName(String regionName) {
        this.label = regionName;
    }

    public List<TreeData> getChildren() {
        return children;
    }

    public void setChildren(List<TreeData> children) {
        this.children = children;
    }
}
