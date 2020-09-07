package com.jlkj.project.ws.domain;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 项目分区名目关联表 t_ws_site_partition_item
 *
 * @author jlkj
 * @date 2020-07-02
 */
@ApiModel("项目分区名目关联表")
public class WsSitePartitionItem implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    private Integer id;

    /** 工地分区id */
    @ApiModelProperty("工地分区id")
    @Excel(name = "工地分区id")
    private Integer sitePartitionId;

    /** 名目id */
    @ApiModelProperty("名目id")
    @Excel(name = "名目id")
    private Integer itemId;


    /** 名目类型 */
    @ApiModelProperty("名目类型")
    @Excel(name = "名目类型")
    private String itemType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSitePartitionId() {
        return sitePartitionId;
    }

    public void setSitePartitionId(Integer sitePartitionId) {
        this.sitePartitionId = sitePartitionId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsArea{");
        sb.append("id=").append(id);
        sb.append(", sitePartitionId='").append(sitePartitionId).append('\'');
        sb.append(", itemId='").append(itemId).append('\'');
        sb.append(", itemType='").append(itemType).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public WsSitePartitionItem() {
    }

    public WsSitePartitionItem(Integer sitePartitionId, Integer itemId, String itemType) {
        this.sitePartitionId = sitePartitionId;
        this.itemId = itemId;
        this.itemType = itemType;
    }
}
