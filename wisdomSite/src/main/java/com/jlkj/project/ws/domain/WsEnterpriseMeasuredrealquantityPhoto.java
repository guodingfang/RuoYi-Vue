package com.jlkj.project.ws.domain;

import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 实测实量图片 t_ws_enterprise_measuredrealquantity_photo
 *
 * @author jlkj
 * @date 2020-08-20
 */
public class WsEnterpriseMeasuredrealquantityPhoto extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Integer id;

    /** 实测实量id */
    private Integer measuredrealquantityId;

    /** 图片地址 */
    private String photoAddres;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setMeasuredrealquantityId(Integer measuredrealquantityId)
    {
        this.measuredrealquantityId = measuredrealquantityId;
    }

    public Integer getMeasuredrealquantityId()
    {
        return measuredrealquantityId;
    }
    public void setPhotoAddres(String photoAddres)
    {
        this.photoAddres = photoAddres;
    }

    public String getPhotoAddres()
    {
        return photoAddres;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("measuredrealquantityId", getMeasuredrealquantityId())
            .append("photoAddres", getPhotoAddres())
            .toString();
    }
}
