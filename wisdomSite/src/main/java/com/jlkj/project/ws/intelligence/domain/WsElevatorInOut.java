package com.jlkj.project.ws.intelligence.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 施工电梯设备进出记录 t_ws_elevator_in_out
 *
 * @author liujie
 * @date 2020-07-03
 */
@ApiModel("施工电梯设备进出记录")
public class WsElevatorInOut implements Serializable
{
    private static final long serialVersionUID = 1L;


    /** 主键ID */
    @ApiModelProperty("主键ID")
    private Integer id;

    /** 升降机ID */
    @ApiModelProperty("升降机ID")
    @Excel(name = "升降机ID")
    private Integer elevatorId;


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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(Integer elevatorId) {
        this.elevatorId = elevatorId;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsElevatorInOut{");
        sb.append("id=").append(id);
        sb.append(", elevatorId=").append(elevatorId);
        sb.append(", inDate=").append(inDate);
        sb.append(", outDate=").append(outDate);
        sb.append('}');
        return sb.toString();
    }
}
