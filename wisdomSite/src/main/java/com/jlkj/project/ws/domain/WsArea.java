package com.jlkj.project.ws.domain;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.TreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 区域 对象 t_ws_area
 *
 * @author jlkj
 * @date 2020-07-02
 */
@ApiModel("区域实体")
public class WsArea extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    private Integer id;

    /** 行政区域码 */
    @ApiModelProperty("行政区域码")
    @Excel(name = "行政区域码")
    private String code;

    /** 区域名称 */
    @ApiModelProperty("区域名称")
    @Excel(name = "区域名称")
    private String name;


    /** 备注 */
    @Excel(name = "备注")
    private String comment;

    /** 更新时间 */
    private Date updatedTime;

    /** 创建时间 */
    private Date createdTime;
    /** 县区code */
    private String countyCode;
    /** 市code */
    private String cityCode;
    /** 省code */
    private String provinceCode;
    /** 县区名称 */
    private String countyName;
    /** 市名称 */
    private String cityName;
    /** 省名称 */
    private String provinceName;

    @ApiModelProperty("缩放比列")
    private String  zoom;

    @ApiModelProperty("经度")
    private Double longitude;

    @ApiModelProperty("纬度")
    private Double latitude;

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getComment()
    {
        return comment;
    }
    public void setUpdatedTime(Date updatedTime)
    {
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


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsArea{");
        sb.append("id=").append(id);
        sb.append(", code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", countyCode='").append(countyCode).append('\'');
        sb.append(", cityCode='").append(cityCode).append('\'');
        sb.append(", provinceCode='").append(provinceCode).append('\'');
        sb.append(", countyName='").append(countyName).append('\'');
        sb.append(", cityName='").append(cityName).append('\'');
        sb.append(", provinceName='").append(provinceName).append('\'');
        sb.append(", zoom='").append(zoom).append('\'');
        sb.append(", longitude='").append(longitude).append('\'');
        sb.append(", latitude='").append(latitude).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
