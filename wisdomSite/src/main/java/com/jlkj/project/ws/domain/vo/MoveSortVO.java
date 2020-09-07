package com.jlkj.project.ws.domain.vo;

import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @Author pyy
 * @Date 2020/6/11 17:55
 */
@ApiModel("项目里程碑设置上下移动参数VO")
public class MoveSortVO extends BaseEntity {
    //排序Id主键
    @ApiModelProperty(value = "排序Id主键",required = true)
    private Integer id;
    //排序的值
    @ApiModelProperty(value = "交换排序的值",required = true)
    private Integer milepostSort;
    //项目id
    @ApiModelProperty(value = "项目id",required = true)
    private Integer projectId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMilepostSort() {
        return milepostSort;
    }
    public void setMilepostSort(Integer milepostSort) {
        this.milepostSort = milepostSort;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
