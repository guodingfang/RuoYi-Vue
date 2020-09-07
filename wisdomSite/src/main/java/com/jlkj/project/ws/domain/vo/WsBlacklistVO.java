package com.jlkj.project.ws.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import com.jlkj.project.ws.domain.WsFileInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * 【人员黑名单】对象 t_ws_blacklist
 *
 * @author liujie
 * @date 2020-06-19
 */
@ApiModel("人员黑名单")
public class WsBlacklistVO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ApiModelProperty("主键")
    private Integer id;

    /** 人员ID */
    @ApiModelProperty("人员ID")
    private Integer personId;

    /** 违规日期 */
    @ApiModelProperty("违规日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "违规日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date illegalDate;

    private Date removeDate;

    /** 违规原因 */
    @ApiModelProperty("违规原因")
    @Excel(name = "违规原因")
    private String illegalReason;

    /** 图片url */
    @ApiModelProperty("图片url")
    private String imageUrl;

    @Excel(name = "记录人")
    @ApiModelProperty("记录人")
    private String recorder;

    @Excel(name = "参建单位名称")
    @ApiModelProperty("参建单位名称")
    private String participatingUnitName;

    @ApiModelProperty("参建单位ID")
    private Integer unitId;

    @Excel(name = "班组名称")
    @ApiModelProperty("班组名称")
    private String teamName;

    @ApiModelProperty("班组ID")
    private Integer teamId;


    @Excel(name = "项目名称")
    @ApiModelProperty("项目名称")
    private String projectName;

    @Excel(name = "姓名")
    @ApiModelProperty("姓名")
    private String name ;

    @ApiModelProperty("工种")
    private String workType;

    @Excel(name = "工种名称")
    @ApiModelProperty("工种名称")
    private String workTypeName;

    @Excel(name = "身份证")
    @ApiModelProperty("身份证")
    private String idNumber;

    @ApiModelProperty("关键字（姓名或者身份证号）")
    private String keyWordName;

    @ApiModelProperty("项目ID")
    private Integer projectId;


    @ApiModelProperty("文件信息")
    private List<WsFileInfo> fileInfo;

    @ApiModelProperty("删除状态")
    private String deleted;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;

    public List<WsFileInfo> getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(List<WsFileInfo> fileInfo) {
        this.fileInfo = fileInfo;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
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

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public String getParticipatingUnitName() {
        return participatingUnitName;
    }

    public void setParticipatingUnitName(String participatingUnitName) {
        this.participatingUnitName = participatingUnitName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Date getIllegalDate() {
        return illegalDate;
    }

    public void setIllegalDate(Date illegalDate) {
        this.illegalDate = illegalDate;
    }

    public Date getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(Date removeDate) {
        this.removeDate = removeDate;
    }
    public String getIllegalReason() {
        return illegalReason;
    }

    public void setIllegalReason(String illegalReason) {
        this.illegalReason = illegalReason;
    }

    public String getKeyWordName() {
        return keyWordName;
    }

    public void setKeyWordName(String keyWordName) {
        this.keyWordName = keyWordName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsBlacklistVO{");
        sb.append("id=").append(id);
        sb.append(", personId=").append(personId);
        sb.append(", illegalDate=").append(illegalDate);
        sb.append(", removeDate=").append(removeDate);
        sb.append(", illegalReason='").append(illegalReason).append('\'');
        sb.append(", imageUrl='").append(imageUrl).append('\'');
        sb.append(", recorder='").append(recorder).append('\'');
        sb.append(", participatingUnitName='").append(participatingUnitName).append('\'');
        sb.append(", unitId=").append(unitId);
        sb.append(", teamName='").append(teamName).append('\'');
        sb.append(", teamId=").append(teamId);
        sb.append(", projectName='").append(projectName).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", workType='").append(workType).append('\'');
        sb.append(", workTypeName='").append(workTypeName).append('\'');
        sb.append(", idNumber='").append(idNumber).append('\'');
        sb.append(", keyWordName='").append(keyWordName).append('\'');
        sb.append(", projectId=").append(projectId);
        sb.append(", fileInfo=").append(fileInfo);
        sb.append(", deleted='").append(deleted).append('\'');
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", createdTime=").append(createdTime);
        sb.append('}');
        return sb.toString();
    }
}
