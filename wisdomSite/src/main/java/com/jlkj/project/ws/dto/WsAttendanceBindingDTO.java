package com.jlkj.project.ws.dto;

import com.jlkj.framework.web.domain.BaseEntity;
import com.jlkj.project.ws.domain.WsTeamPerson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName WsAttendanceBindingDTO
 * @Description 人员考勤绑定DTO
 * @Author liujie
 * @Date 2020/6/15 19:01
 * @Version 1.0
 **/
@ApiModel("人员考勤绑定DTO")
public class WsAttendanceBindingDTO extends BaseEntity {
    @ApiModelProperty("人员列表")
    private List<WsTeamPerson> WsTeamPersonList;

    @ApiModelProperty("打卡机序列集合（多个用英文逗号隔开）")
    private String deviceKeys;


    private String[] guids;

    private String[] serialNumbers;

    public String[] getGuids() {
        return guids;
    }

    public void setGuids(String[] guids) {
        this.guids = guids;
    }

    public String[] getSerialNumbers() {
        return serialNumbers;
    }

    public void setSerialNumbers(String[] serialNumbers) {
        this.serialNumbers = serialNumbers;
    }

    public List<WsTeamPerson> getWsTeamPersonList() {
        return WsTeamPersonList;
    }

    public void setWsTeamPersonList(List<WsTeamPerson> wsTeamPersonList) {
        WsTeamPersonList = wsTeamPersonList;
    }

    public String getDeviceKeys() {
        return deviceKeys;
    }

    public void setDeviceKeys(String deviceKeys) {
        this.deviceKeys = deviceKeys;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsAttendanceBindingDTO{");
        sb.append("WsTeamPersonList=").append(WsTeamPersonList);
        sb.append(", deviceKeys='").append(deviceKeys).append('\'');
        sb.append(", guids=").append(guids == null ? "null" : Arrays.asList(guids).toString());
        sb.append(", serialNumbers=").append(serialNumbers == null ? "null" : Arrays.asList(serialNumbers).toString());
        sb.append('}');
        return sb.toString();
    }
}
