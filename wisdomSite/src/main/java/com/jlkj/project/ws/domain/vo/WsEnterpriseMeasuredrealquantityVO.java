package com.jlkj.project.ws.domain.vo;

import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantity;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityContent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author pyy
 * @Date 2020/6/22 18:52
 */
@ApiModel("实测实量VO")
public class WsEnterpriseMeasuredrealquantityVO {
    /** 实测实量实体 */
    @ApiModelProperty("实测实量实体")
    private WsEnterpriseMeasuredrealquantity wsEnterpriseMeasuredrealquantity;
    /** 实测实量内容实体 */
    @ApiModelProperty("实测实量内容实体")
    private List<WsEnterpriseMeasuredrealquantityContent>  wsEnterpriseMeasuredrealquantityContentList;

    public WsEnterpriseMeasuredrealquantity getWsEnterpriseMeasuredrealquantity() {
        return wsEnterpriseMeasuredrealquantity;
    }

    public void setWsEnterpriseMeasuredrealquantity(WsEnterpriseMeasuredrealquantity wsEnterpriseMeasuredrealquantity) {
        this.wsEnterpriseMeasuredrealquantity = wsEnterpriseMeasuredrealquantity;
    }

    public List<WsEnterpriseMeasuredrealquantityContent> getWsEnterpriseMeasuredrealquantityContentList() {
        return wsEnterpriseMeasuredrealquantityContentList;
    }

    public void setWsEnterpriseMeasuredrealquantityContentList(List<WsEnterpriseMeasuredrealquantityContent> wsEnterpriseMeasuredrealquantityContentList) {
        this.wsEnterpriseMeasuredrealquantityContentList = wsEnterpriseMeasuredrealquantityContentList;
    }

    public WsEnterpriseMeasuredrealquantityVO(WsEnterpriseMeasuredrealquantity wsEnterpriseMeasuredrealquantity, List<WsEnterpriseMeasuredrealquantityContent> wsEnterpriseMeasuredrealquantityContentList) {
        this.wsEnterpriseMeasuredrealquantity = wsEnterpriseMeasuredrealquantity;
        this.wsEnterpriseMeasuredrealquantityContentList = wsEnterpriseMeasuredrealquantityContentList;
    }

    public WsEnterpriseMeasuredrealquantityVO() {
    }
}
