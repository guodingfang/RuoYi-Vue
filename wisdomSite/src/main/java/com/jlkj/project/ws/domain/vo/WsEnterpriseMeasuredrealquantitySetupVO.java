package com.jlkj.project.ws.domain.vo;

import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantitySetup;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantitySetupContent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @Author pyy
 * @Date 2020/6/20 16:01
 */
@ApiModel("实测实量分项工程VO")
public class WsEnterpriseMeasuredrealquantitySetupVO implements Serializable {
    /** 实测实量分项工程实体 */
    @ApiModelProperty("实测实量分项工程实体")
    private WsEnterpriseMeasuredrealquantitySetup wsEnterpriseMeasuredrealquantitySetup;

    /** 实测实量分项工程内容实体 */
    @ApiModelProperty("实测实量分项工程内容实体")
    private List<WsEnterpriseMeasuredrealquantitySetupContent> wsEnterpriseMeasuredrealquantitySetupContent;

    public WsEnterpriseMeasuredrealquantitySetup getWsEnterpriseMeasuredrealquantitySetup() {
        return wsEnterpriseMeasuredrealquantitySetup;
    }

    public void setWsEnterpriseMeasuredrealquantitySetup(WsEnterpriseMeasuredrealquantitySetup wsEnterpriseMeasuredrealquantitySetup) {
        this.wsEnterpriseMeasuredrealquantitySetup = wsEnterpriseMeasuredrealquantitySetup;
    }

    public List<WsEnterpriseMeasuredrealquantitySetupContent> getWsEnterpriseMeasuredrealquantitySetupContent() {
        return wsEnterpriseMeasuredrealquantitySetupContent;
    }

    public void setWsEnterpriseMeasuredrealquantitySetupContent(List<WsEnterpriseMeasuredrealquantitySetupContent> wsEnterpriseMeasuredrealquantitySetupContent) {
        this.wsEnterpriseMeasuredrealquantitySetupContent = wsEnterpriseMeasuredrealquantitySetupContent;
    }
}
