package com.jlkj.project.ws.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 班组人员行为记录对象 t_ws_team_person_behavior_record
 *
 * @author liujie
 * @date 2020-07-23
 */
@ApiModel("班组人员行为记录对象")
public class WsTeamPersonBehaviorRecord extends BaseEntity
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
    private String imageUrl;

    /** 现场照片列表 */
    @ApiModelProperty("现场照片列表")
    @Excel(name = "现场照片列表")
    private List<WsFileInfo> imageUrls;

    /** 记录员 */
    @ApiModelProperty("记录员")
    @Excel(name = "记录员")
    private String recorder;

    /** 月份 */
    @ApiModelProperty("月份")
    @Excel(name = "月份")
    private Date month;

    /** 搜索关键字 */
    @ApiModelProperty("搜索关键字")
    @Excel(name = "搜索关键字")
    private String keyWordName;

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

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getKeyWordName() {
        return keyWordName;
    }

    public void setKeyWordName(String keyWordName) {
        this.keyWordName = keyWordName;
    }

    public List<WsFileInfo> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<WsFileInfo> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsTeamPersonBehaviorRecord{");
        sb.append("id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", personId=").append(personId);
        sb.append(", eventId=").append(eventId);
        sb.append(", eventType='").append(eventType).append('\'');
        sb.append(", point=").append(point);
        sb.append(", details='").append(details).append('\'');
        sb.append(", imageUrl='").append(imageUrl).append('\'');
        sb.append(", imageUrls=").append(imageUrls == null ? "null" : Arrays.asList(imageUrls).toString());
        sb.append(", recorder='").append(recorder).append('\'');
        sb.append(", month=").append(month);
        sb.append(", keyWordName='").append(keyWordName).append('\'');
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append('}');
        return sb.toString();
    }
}
