package com.jlkj.project.ws.intelligence.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * 施工电梯设备管理对象 t_ws_elevator_manager
 *
 * @author liujie
 * @date 2020-07-03
 */
@ApiModel("施工电梯设备管理实体")
public class WsElevatorManager extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 主键ID */
    @ApiModelProperty("主键ID")
    private Integer id;

    /** 工程id */
    @ApiModelProperty("工程id")
    @Excel(name = "工程id")
    private Integer projectId;

    /** 自编号 */
    @ApiModelProperty("自编号")
    @Excel(name = "自编号")
    private String selfNumber;

    /** 产权备案编号 */
    @ApiModelProperty("产权备案编号")
    @Excel(name = "产权备案编号")
    private String recordNumber;

    /** 设备名称 */
    @ApiModelProperty("设备名称")
    @Excel(name = "设备名称")
    private String deviceName;

    /** 设备状态(0=离场,1=进场) */
    @ApiModelProperty("设备状态(0=离场,1=进场)")
    @Excel(name = "设备状态(0=离场,1=进场)")
    private String deviceStatus;

    /** 进场日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("进场日期")
    @Excel(name = "进场日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inDate;

    /** 退场日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("退场日期")
    @Excel(name = "退场日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date outDate;

    /** 设备类型(1=单笼,2=双笼) */
    @ApiModelProperty("设备类型(1=单笼,2=双笼) ")
    @Excel(name = "设备类型(1=单笼,2=双笼)")
    private String deviceType;

    /** 产权单位 */
    @ApiModelProperty("产权单位")
    @Excel(name = "产权单位")
    private String propertyUnit;

    /** 安装单位 */
    @ApiModelProperty("安装单位")
    @Excel(name = "安装单位")
    private String installationUnit;

    /** 生产单位 */
    @ApiModelProperty("生产单位")
    @Excel(name = "生产单位")
    private String produceUnit;

    /** 规格型号 */
    @ApiModelProperty("规格型号")
    @Excel(name = "规格型号")
    private String models;

    /** 出厂编号 */
    @ApiModelProperty("出厂编号")
    @Excel(name = "出厂编号")
    private String factoryNumber;

    /** 出厂日期 */
    @ApiModelProperty("出厂日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出厂日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productDate;

    /** x方向偏移量*/
    @ApiModelProperty("x方向偏移量")
    @Excel(name = "x_offset")
    private String xOffset;

    /** y方向偏移量 */
    @ApiModelProperty("y方向偏移量")
    @Excel(name = "y方向偏移量")
    private String yOffset;

    /** 是否标记 */
    @ApiModelProperty("是否标记")
    private String sign;

    /** 更新日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    /** 创建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 绑定设备列表 */
    @ApiModelProperty("绑定设备列表")
    private List<WsElevatorMinitorDevice> monitorDevices;

    /** 设备删除状态*/
    private String deleted;


    /** 场布图地址*/
    @ApiModelProperty("场布图地址")
    private String layoutUrl;
    /** 违章通知联系方式*/
    @ApiModelProperty("违章通知联系方式")
    private String violationNotice;

    @ApiModelProperty("企业Id")
    private Long companyId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

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

    public Integer getProjectId()
    {
        return projectId;
    }
    public void setSelfNumber(String selfNumber)
    {
        this.selfNumber = selfNumber;
    }

    @NotBlank(message = "自编号不能为空")
    public String getSelfNumber()
    {
        return selfNumber;
    }
    public void setRecordNumber(String recordNumber)
    {
        this.recordNumber = recordNumber;
    }

    @NotBlank(message ="产权备案号不能为空")
    public String getRecordNumber()
    {
        return recordNumber;
    }
    public void setDeviceName(String deviceName)
    {
        this.deviceName = deviceName;
    }

    public String getDeviceName()
    {
        return deviceName;
    }
    public void setDeviceStatus(String deviceStatus)
    {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceStatus()
    {
        return deviceStatus;
    }
    public void setInDate(Date inDate)
    {
        this.inDate = inDate;
    }

    public Date getInDate()
    {
        return inDate;
    }
    public void setOutDate(Date outDate)
    {
        this.outDate = outDate;
    }

    public Date getOutDate()
    {
        return outDate;
    }
    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }

    @NotBlank(message = "设备类型不能为空")
    public String getDeviceType()
    {
        return deviceType;
    }
    public void setPropertyUnit(String propertyUnit)
    {
        this.propertyUnit = propertyUnit;
    }

    public String getPropertyUnit()
    {
        return propertyUnit;
    }
    public void setInstallationUnit(String installationUnit)
    {
        this.installationUnit = installationUnit;
    }

    public String getInstallationUnit()
    {
        return installationUnit;
    }
    public void setProduceUnit(String produceUnit)
    {
        this.produceUnit = produceUnit;
    }

    public String getProduceUnit()
    {
        return produceUnit;
    }
    public void setModels(String models)
    {
        this.models = models;
    }

    public String getModels()
    {
        return models;
    }
    public void setFactoryNumber(String factoryNumber)
    {
        this.factoryNumber = factoryNumber;
    }

    public String getFactoryNumber()
    {
        return factoryNumber;
    }
    public void setProductDate(Date productDate)
    {
        this.productDate = productDate;
    }

    public Date getProductDate()
    {
        return productDate;
    }

    public String getxOffset() {
        return xOffset;
    }

    public void setxOffset(String xOffset) {
        this.xOffset = xOffset;
    }

    public String getyOffset() {
        return yOffset;
    }

    public void setyOffset(String yOffset) {
        this.yOffset = yOffset;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }

    public List<WsElevatorMinitorDevice> getMonitorDevices() {
        return monitorDevices;
    }

    public void setMonitorDevices(List<WsElevatorMinitorDevice> monitorDevices) {
        this.monitorDevices = monitorDevices;
    }

    public String getLayoutUrl() {
        return layoutUrl;
    }

    public void setLayoutUrl(String layoutUrl) {
        this.layoutUrl = layoutUrl;
    }

    public String getViolationNotice() {
        return violationNotice;
    }

    public void setViolationNotice(String violationNotice) {
        this.violationNotice = violationNotice;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsElevatorManager{");
        sb.append("id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", selfNumber='").append(selfNumber).append('\'');
        sb.append(", recordNumber='").append(recordNumber).append('\'');
        sb.append(", deviceName='").append(deviceName).append('\'');
        sb.append(", deviceStatus='").append(deviceStatus).append('\'');
        sb.append(", inDate=").append(inDate);
        sb.append(", outDate=").append(outDate);
        sb.append(", deviceType='").append(deviceType).append('\'');
        sb.append(", propertyUnit='").append(propertyUnit).append('\'');
        sb.append(", installationUnit='").append(installationUnit).append('\'');
        sb.append(", produceUnit='").append(produceUnit).append('\'');
        sb.append(", models='").append(models).append('\'');
        sb.append(", factoryNumber='").append(factoryNumber).append('\'');
        sb.append(", productDate=").append(productDate);
        sb.append(", xOffset='").append(xOffset).append('\'');
        sb.append(", yOffset='").append(yOffset).append('\'');
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", monitorDevices=").append(monitorDevices);
        sb.append(", deleted='").append(deleted).append('\'');
        sb.append(", layoutUrl='").append(layoutUrl).append('\'');
        sb.append(", violationNotice='").append(violationNotice).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
