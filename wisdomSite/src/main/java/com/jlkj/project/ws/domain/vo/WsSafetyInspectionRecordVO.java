package com.jlkj.project.ws.domain.vo;

import com.jlkj.project.ws.domain.WsSafetyInspectionRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author pyy
 * @Date 2020/7/15 11:00
 */
@ApiModel("查看安全巡视记录对象")
public class WsSafetyInspectionRecordVO {

    @ApiModelProperty("安全巡视对象")
    private WsSafetyInspectionRecord wsSafetyInspectionRecord;

    @ApiModelProperty("隐患整改和验证封装对象")
    private List<WsSafetyHazardRectifyAndVerifyVO> wsSafetyHazardRectifyAndVerifyList;

    public WsSafetyInspectionRecord getWsSafetyInspectionRecord() {
        return wsSafetyInspectionRecord;
    }

    public void setWsSafetyInspectionRecord(WsSafetyInspectionRecord wsSafetyInspectionRecord) {
        this.wsSafetyInspectionRecord = wsSafetyInspectionRecord;
    }

    public List<WsSafetyHazardRectifyAndVerifyVO> getWsSafetyHazardRectifyAndVerifyList() {
        return wsSafetyHazardRectifyAndVerifyList;
    }

    public void setWsSafetyHazardRectifyAndVerifyList(List<WsSafetyHazardRectifyAndVerifyVO> wsSafetyHazardRectifyAndVerifyList) {
        this.wsSafetyHazardRectifyAndVerifyList = wsSafetyHazardRectifyAndVerifyList;
    }
}
