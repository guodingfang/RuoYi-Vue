package com.jlkj.project.ws.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;

/**
 * 【人员黑名单】对象 t_ws_blacklist
 *
 * @author liujie
 * @date 2020-06-19
 */
@ApiModel("人员黑名单")
public class WsBlacklist extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ApiModelProperty("主键")
    private Integer id;

    /** 人员ID */
    @ApiModelProperty("人员ID")
    @Excel(name = "人员ID")
    private Integer personId;

    /** 违规日期 */
    @ApiModelProperty("违规日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "违规日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date illegalDate;

    /** 移除日期 */
    @ApiModelProperty("移除日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "移除日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date removeDate;

    /** 违规原因 */
    @ApiModelProperty("违规原因")
    @Excel(name = "违规原因")
    private String illegalReason;

    /** 图片url */
    @ApiModelProperty("图片url")
    @Excel(name = "图片url")
    private String imageUrl;

    @Excel(name = "记录人")
    @ApiModelProperty("记录人")
    private String recorder;


    private Integer[] personIds;

    @Excel(name = "身份证")
    @ApiModelProperty("身份证")
    private String idNumber;


    @ApiModelProperty("删除状态")
    private String deleted;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer[] getPersonIds() {
        return personIds;
    }

    public void setPersonIds(Integer[] personIds) {
        this.personIds = personIds;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
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

    public Date getIllegalDate() {
        return illegalDate;
    }

    public void setIllegalDate(Date illegalDate) {
        this.illegalDate = illegalDate;
    }

    public String getIllegalReason() {
        return illegalReason;
    }

    public void setIllegalReason(String illegalReason) {
        this.illegalReason = illegalReason;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }

    public Date getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(Date removeDate) {
        this.removeDate = removeDate;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsBlacklist{");
        sb.append("id=").append(id);
        sb.append(", personId=").append(personId);
        sb.append(", illegalDate=").append(illegalDate);
        sb.append(", removeDate=").append(removeDate);
        sb.append(", illegalReason='").append(illegalReason).append('\'');
        sb.append(", imageUrl='").append(imageUrl).append('\'');
        sb.append(", recorder='").append(recorder).append('\'');
        sb.append(", personIds=").append(personIds == null ? "null" : Arrays.asList(personIds).toString());
        sb.append(", idNumber='").append(idNumber).append('\'');
        sb.append(", deleted='").append(deleted).append('\'');
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", createdTime=").append(createdTime);
        sb.append('}');
        return sb.toString();
    }
}
