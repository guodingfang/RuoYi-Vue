package com.jlkj.project.ws.domain;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 分区绑定对象 t_ws_partition_bind
 *
 * @author pyy
 * @date 2020-06-30
 */
@ApiModel("分区绑定对象")
public class WsPartitionBind extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键id")
    private Integer id;

    /**
     * 分区id
     */
    @ApiModelProperty("分区id")
    @Excel(name = "分区id")
    private Integer partitionId;

    /**
     * 绑定id
     */
    @ApiModelProperty("绑定id")
    @Excel(name = "绑定id")
    private Integer bindId;

    /**
     * 绑定类型
     */
    @ApiModelProperty("绑定类型")
    @Excel(name = "绑定类型")
    private String bindType;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setPartitionId(Integer partitionId) {
        this.partitionId = partitionId;
    }

    public Integer getPartitionId() {
        return partitionId;
    }

    public void setBindId(Integer bindId) {
        this.bindId = bindId;
    }

    public Integer getBindId() {
        return bindId;
    }

    public void setBindType(String bindType) {
        this.bindType = bindType;
    }

    public String getBindType() {
        return bindType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("partitionId", getPartitionId())
                .append("bindId", getBindId())
                .append("bindType", getBindType())
                .toString();
    }
}
