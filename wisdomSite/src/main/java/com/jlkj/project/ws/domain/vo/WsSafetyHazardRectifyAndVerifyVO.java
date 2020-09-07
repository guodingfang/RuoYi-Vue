package com.jlkj.project.ws.domain.vo;

import com.jlkj.project.ws.domain.WsSafetyHazardRectify;
import com.jlkj.project.ws.domain.WsSafetyHazardVerify;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author pyy
 * @Date 2020/7/15 10:56
 */
@ApiModel("隐患整改和验证封装对象")
public class WsSafetyHazardRectifyAndVerifyVO {

    @ApiModelProperty("隐患整改对象")
    private WsSafetyHazardRectify wsSafetyHazardRectify;

    @ApiModelProperty("隐患验证对象")
    private WsSafetyHazardVerify wsSafetyHazardVerify;


    public WsSafetyHazardRectify getWsSafetyHazardRectify() {
        return wsSafetyHazardRectify;
    }

    public void setWsSafetyHazardRectify(WsSafetyHazardRectify wsSafetyHazardRectify) {
        this.wsSafetyHazardRectify = wsSafetyHazardRectify;
    }

    public WsSafetyHazardVerify getWsSafetyHazardVerify() {
        return wsSafetyHazardVerify;
    }

    public void setWsSafetyHazardVerify(WsSafetyHazardVerify wsSafetyHazardVerify) {
        this.wsSafetyHazardVerify = wsSafetyHazardVerify;
    }
}
