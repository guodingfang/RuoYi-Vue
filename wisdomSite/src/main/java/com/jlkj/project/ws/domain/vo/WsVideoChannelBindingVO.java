package com.jlkj.project.ws.domain.vo;

import com.jlkj.project.ws.domain.WsSetupInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author pyy
 * @Date 2020/7/9 15:25
 */
@ApiModel("摄像头通道绑定VO")
public class WsVideoChannelBindingVO {
    /** 序列号 */
    @ApiModelProperty("项目id")
    private Integer projectId;

    /** 序列号 */
    @ApiModelProperty("序列号")
    private String equipmentSerialnumber;

    /** 验证码 */
    @ApiModelProperty("验证码")
    private String verificationCode;

    /** 安装信息对象 */
    @ApiModelProperty("安装信息对象")
    private List<WsSetupInfo> wsSetupInfoList ;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getEquipmentSerialnumber() {
        return equipmentSerialnumber;
    }

    public void setEquipmentSerialnumber(String equipmentSerialnumber) {
        this.equipmentSerialnumber = equipmentSerialnumber;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public List<WsSetupInfo> getWsSetupInfoList() {
        return wsSetupInfoList;
    }

    public void setWsSetupInfoList(List<WsSetupInfo> wsSetupInfoList) {
        this.wsSetupInfoList = wsSetupInfoList;
    }
}
