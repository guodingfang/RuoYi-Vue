package com.jlkj.project.ws.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.project.ws.domain.WsBehaviorEventManagement;
import com.jlkj.project.ws.domain.WsFileInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 班组人员行为记录对象VO t_ws_team_person_behavior_record
 *
 * @author liujie
 * @date 2020-07-23
 */
@ApiModel("班组人员行为记录VO对象")
public class WsTeamPersonBehaviorRecordVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Integer id;

    /** 项目id */
    @ApiModelProperty("项目id")
    @Excel(name = "项目id")
    private Integer projectId;

    /** 人员ID */
    @ApiModelProperty("人员ID")
    @Excel(name = "人员ID")
    private Integer personId;

    /** 事件ID */
    @ApiModelProperty("事件ID")
    @Excel(name = "事件ID")
    private Integer eventId;
    /** 事件类型 */
    @ApiModelProperty("事件类型")
    @Excel(name = "事件类型")
    private String eventType;

    /** 增加点券/扣分值 */
    @ApiModelProperty("增加点券/扣分值")
    @Excel(name = "增加点券/扣分值")
    private Integer point;

    /** 详细说明 */
    @ApiModelProperty("详细说明")
    @Excel(name = "详细说明")
    private String details;

    /** 现场照片 */
    @ApiModelProperty("现场照片")
    @Excel(name = "现场照片")
    private String imageUrl;

    /** 记录员 */
    @ApiModelProperty("记录员")
    @Excel(name = "记录员")
    private String recorder;

    @ApiModelProperty("人员姓名")
    @Excel(name = "人员姓名")
    private String personName;

    @ApiModelProperty("身份证号")
    @Excel(name = "身份证号")
    private String idNumber;

    @ApiModelProperty("班组名称")
    @Excel(name = "班组名称")
    private String teamName;

    @ApiModelProperty("参建单位名称")
    @Excel(name = "参建单位名称")
    private String unitName;

    @ApiModelProperty("月累计奖励点券")
    @Excel(name = "月累计奖励点券")
    private Integer monthAwardPoints;

    @ApiModelProperty("可兑换点券")
    @Excel(name = "可兑换点券")
    private Integer exchangeablePoints;

    @ApiModelProperty("累计扣除分值")
    @Excel(name = "累计扣除分值")
    private Integer totalDeductPoints;


    @ApiModelProperty("行为事件")
    @Excel(name = "行为事件")
    private WsBehaviorEventManagement event;


    @ApiModelProperty("人脸照片")
    @Excel(name = "人脸照片")
    private String faceUrl;


    @ApiModelProperty("工种名称")
    @Excel(name = "工种名称")
    private String workTypeName;

    @ApiModelProperty("总人数")
    @Excel(name = "总人数")
    private Integer totalPersons;

    @ApiModelProperty("获得或扣除点券/分值人数")
    @Excel(name = "获得或扣除点券/分值人数")
    private Integer hasPointPersons;

    /** 现场照片列表 */
    @ApiModelProperty("现场照片列表")
    @Excel(name = "现场照片列表")
    private List<WsFileInfo> imageUrls;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }
    @NotNull(message = "项目不能为空")
    public Integer getProjectId()
    {
        return projectId;
    }
    public void setPersonId(Integer personId)
    {
        this.personId = personId;
    }
    @NotNull(message = "人员不能为空")
    public Integer getPersonId()
    {
        return personId;
    }
    public void setEventId(Integer eventId)
    {
        this.eventId = eventId;
    }
    @NotNull(message = "事件不能为空")
    public Integer getEventId()
    {
        return eventId;
    }
    public void setPoint(Integer point)
    {
        this.point = point;
    }
    @NotNull(message = "点券/分值不能为空")
    public Integer getPoint()
    {
        return point;
    }
    public void setDetails(String details)
    {
        this.details = details;
    }

    public String getDetails()
    {
        return details;
    }
    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }
    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getMonthAwardPoints() {
        return monthAwardPoints;
    }

    public void setMonthAwardPoints(Integer monthAwardPoints) {
        this.monthAwardPoints = monthAwardPoints;
    }

    public Integer getTotalDeductPoints() {
        return totalDeductPoints;
    }

    public void setTotalDeductPoints(Integer totalDeductPoints) {
        this.totalDeductPoints = totalDeductPoints;
    }


    public WsBehaviorEventManagement getEvent() {
        return event;
    }

    public void setEvent(WsBehaviorEventManagement event) {
        this.event = event;
    }

    public Integer getExchangeablePoints() {
        return exchangeablePoints;
    }

    public void setExchangeablePoints(Integer exchangeablePoints) {
        this.exchangeablePoints = exchangeablePoints;

    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public Integer getTotalPersons() {
        return totalPersons;
    }

    public void setTotalPersons(Integer totalPersons) {
        this.totalPersons = totalPersons;
    }

    public Integer getHasPointPersons() {
        return hasPointPersons;
    }

    public void setHasPointPersons(Integer hasPointPersons) {
        this.hasPointPersons = hasPointPersons;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public List<WsFileInfo> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<WsFileInfo> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsTeamPersonBehaviorRecordVO{");
        sb.append("id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", personId=").append(personId);
        sb.append(", eventId=").append(eventId);
        sb.append(", point=").append(point);
        sb.append(", details='").append(details).append('\'');
        sb.append(", imageUrl='").append(imageUrl).append('\'');
        sb.append(", recorder='").append(recorder).append('\'');
        sb.append(", personName='").append(personName).append('\'');
        sb.append(", idNumber='").append(idNumber).append('\'');
        sb.append(", teamName='").append(teamName).append('\'');
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", monthAwardPoints=").append(monthAwardPoints);
        sb.append(", exchangeablePoints=").append(exchangeablePoints);
        sb.append(", totalDeductPoints=").append(totalDeductPoints);
        sb.append(", event=").append(event);
        sb.append(", faceUrl='").append(faceUrl).append('\'');
        sb.append(", workTypeName='").append(workTypeName).append('\'');
        sb.append(", totalPersons=").append(totalPersons);
        sb.append(", hasPointPersons=").append(hasPointPersons);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append('}');
        return sb.toString();
    }
}
