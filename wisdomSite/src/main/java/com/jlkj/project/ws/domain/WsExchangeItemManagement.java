package com.jlkj.project.ws.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 物品兑换管理
对象 t_ws_exchange_item_management
 *
 * @author liujie
 * @date 2020-07-23
 */
@ApiModel("物品兑换管理")
public class WsExchangeItemManagement implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键
 */
    private Integer id;
    @ApiModelProperty("项目id")
    /** 项目id */
    @Excel(name = "项目id")
    private Integer projectId;
    @ApiModelProperty("物品名称")
    /** 物品名称 */
    @Excel(name = "物品名称")
    private String itemName;
    @ApiModelProperty("")
    /** 计量单位 */
    @Excel(name = "计量单位")
    private String unit;
    @ApiModelProperty("兑换点券")
    /** 兑换点券 */
    @Excel(name = "兑换点券")
    private Integer changePoint;
    @ApiModelProperty("库存数量")
    /** 库存数量 */
    @Excel(name = "库存数量")
    private Integer inventoryMount;
    @ApiModelProperty("已兑换数量")
    /** 已兑换数量 */
    @Excel(name = "已兑换数量")
    private Integer hasChangeMount;
    @ApiModelProperty("是否启用(0禁用,1启用）")
    /** 是否启用(0禁用,1启用） */
    @Excel(name = "是否启用(0禁用,1启用）")
    private String enabled;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }
    @NotNull(message = "项目不能为空")
    public Integer getProjectId()
    {
        return projectId;
    }
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }
    @NotBlank(message = "物品名称不能为空")
    public String getItemName()
    {
        return itemName;
    }
    public void setUnit(String unit)
    {
        this.unit = unit;
    }
    @NotBlank(message = "计量单位不能为空")
    public String getUnit()
    {
        return unit;
    }
    public void setChangePoint(Integer changePoint)
    {
        this.changePoint = changePoint;
    }
    @NotNull(message = "兑换点券不能为空")
    public Integer getChangePoint()
    {
        return changePoint;
    }
    public void setInventoryMount(Integer inventoryMount)
    {
        this.inventoryMount = inventoryMount;
    }
    @NotNull(message = "库存量不能为空")
    public Integer getInventoryMount()
    {
        return inventoryMount;
    }
    public void setHasChangeMount(Integer hasChangeMount)
    {
        this.hasChangeMount = hasChangeMount;
    }

    public Integer getHasChangeMount()
    {
        return hasChangeMount;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }
    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsExchangeItemManagement{");
        sb.append("id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", itemName='").append(itemName).append('\'');
        sb.append(", unit='").append(unit).append('\'');
        sb.append(", changePoint=").append(changePoint);
        sb.append(", inventoryMount=").append(inventoryMount);
        sb.append(", hasChangeMount=").append(hasChangeMount);
        sb.append(", enabled='").append(enabled).append('\'');
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append('}');
        return sb.toString();
    }
}
