package com.jlkj.project.ws.domain.vo;

import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityMeasuredLocation;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class WsEnterpriseMeasuredrealquantityContentVO {

    /** 实测实量部位实体 */
    @ApiModelProperty("实测实量部位实体")
    private WsEnterpriseMeasuredrealquantityMeasuredLocation wsEnterpriseMeasuredrealquantityMeasuredLocation;

    /** 实测实量分项工程VO */
    @ApiModelProperty("实测实量分项工程VO")
    private List<WsEnterpriseMeasuredrealquantityVO> wsEnterpriseMeasuredrealquantityVOs;

    public WsEnterpriseMeasuredrealquantityMeasuredLocation getWsEnterpriseMeasuredrealquantityMeasuredLocation() {
        return wsEnterpriseMeasuredrealquantityMeasuredLocation;
    }

    public void setWsEnterpriseMeasuredrealquantityMeasuredLocation(WsEnterpriseMeasuredrealquantityMeasuredLocation wsEnterpriseMeasuredrealquantityMeasuredLocation) {
        this.wsEnterpriseMeasuredrealquantityMeasuredLocation = wsEnterpriseMeasuredrealquantityMeasuredLocation;
    }

    public List<WsEnterpriseMeasuredrealquantityVO> getWsEnterpriseMeasuredrealquantityVOs() {
        return wsEnterpriseMeasuredrealquantityVOs;
    }

    public void setWsEnterpriseMeasuredrealquantityVOs(List<WsEnterpriseMeasuredrealquantityVO> wsEnterpriseMeasuredrealquantityVOs) {
        this.wsEnterpriseMeasuredrealquantityVOs = wsEnterpriseMeasuredrealquantityVOs;
    }
}
