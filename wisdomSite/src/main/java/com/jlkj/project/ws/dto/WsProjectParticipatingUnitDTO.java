package com.jlkj.project.ws.dto;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName WsProjectParticipatingUnitDTO
 * @Description
 * @Author Thinkpad
 * @Date 2020/6/16 6:00
 * @Version 1.0
 **/
@ApiModel("项目参建单位实体")
public class WsProjectParticipatingUnitDTO extends BaseEntity {
    /** 项目ID */
    @ApiModelProperty("项目ID")
    @Excel(name = "项目ID")
    private Integer projectId;

    /** ID集合 */
    @ApiModelProperty("单位库ID集合")
    private Integer[] ids;

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
