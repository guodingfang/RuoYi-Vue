package com.jlkj.project.ws.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import com.jlkj.project.ws.domain.WsBlacklist;
import com.jlkj.project.ws.domain.WsTeamPersonLibrary;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @ClassName WsTeamPersonDTO
 * @Description
 * @Author Thinkpad
 * @Date 2020/6/15 19:01
 * @Version 1.0
 **/
@ApiModel("班组人员DTO")
public class WsTeamPersonDTO extends BaseEntity {
    @ApiModelProperty("项目Id")
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer projectId;

    /** 参建单位ID */
    @ApiModelProperty("参建单位ID")
    @Excel(name = "参建单位ID")
    private Integer unitId;

    /** 班组ID */
    @ApiModelProperty("班组ID")
    @Excel(name = "班组ID")
    private Integer teamId;

    /** 人员类型*/
    @ApiModelProperty("人员类型")
    private String personType;
    /** 人员状态  退场：0 ;在场：1； */
    @ApiModelProperty("人员状态")
    @Excel(name = "人员状态  退场：0 ;在场：1；")
    private String personStatus;

    /** 进场日期 */
    private Date InDate;

    /** 人员黑名单 黑名单：0；白名单：1 */
    @ApiModelProperty("人员黑名单")
    @Excel(name = "人员黑名单 黑名单：1；白名单：0")
    private String blacklist;

    /** 工种*/
    @ApiModelProperty("工种")
    @Excel(name = "工种")
    private String workType;

    /** 是否删除（0 N, 1Y) */
    @Excel(name = "是否删除", readConverterExp = "是否删除（0 N, 1Y)")
    private String deleted;

    /** 人员集合id*/
    private Integer[] personIds;

    /**黑名单列表*/
    @ApiModelProperty("黑名单列表")
    private List<WsBlacklist> wsBlacklists;

    /**黑名单*/
    @ApiModelProperty("黑名单")
    private WsBlacklist wsBlacklist;

    /** 人员库Id集合*/
    @ApiModelProperty("人员库Id集合")
    private Integer[] personLibraryIds;

    /** 人员库*/
    @ApiModelProperty("人员库")
    private WsTeamPersonLibrary  personLibrary;




    /** 违规日期 */
    @ApiModelProperty("违规日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date illegalDate;

    /** 移除日期 */
    @ApiModelProperty("移除日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date removeDate;

    /** 违规原因 */
    @ApiModelProperty("违规原因")
    private String illegalReason;

    /** 图片url */
    @ApiModelProperty("图片url")
    @Excel(name = "图片url")
    private String imageUrl;

    @Excel(name = "记录人")
    @ApiModelProperty("记录人")
    private String recorder;


    public WsBlacklist getWsBlacklist() {
        return wsBlacklist;
    }

    public void setWsBlacklist(WsBlacklist wsBlacklist) {
        this.wsBlacklist = wsBlacklist;
    }

    public Integer[] getPersonIds() {
        return personIds;
    }

    public void setPersonIds(Integer[] personIds) {
        this.personIds = personIds;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public Date getInDate() {
        return InDate;
    }

    public void setInDate(Date inDate) {
        InDate = inDate;
    }

    public WsTeamPersonLibrary getPersonLibrary() {
        return personLibrary;
    }

    public void setPersonLibrary(WsTeamPersonLibrary personLibrary) {
        this.personLibrary = personLibrary;
    }

    public Integer[] getPersonLibraryIds() {
        return personLibraryIds;
    }

    public void setPersonLibraryIds(Integer[] personLibraryIds) {
        this.personLibraryIds = personLibraryIds;
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

    public String getPersonStatus() {
        return personStatus;
    }

    public void setPersonStatus(String personStatus) {
        this.personStatus = personStatus;
    }

    public String getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public List<WsBlacklist> getWsBlacklists() {
        return wsBlacklists;
    }

    public void setWsBlacklists(List<WsBlacklist> wsBlacklists) {
        this.wsBlacklists = wsBlacklists;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WsTeamPersonDTO{");
        sb.append("projectId=").append(projectId);
        sb.append(", unitId=").append(unitId);
        sb.append(", teamId=").append(teamId);
        sb.append(", personType='").append(personType).append('\'');
        sb.append(", personStatus='").append(personStatus).append('\'');
        sb.append(", InDate=").append(InDate);
        sb.append(", blacklist='").append(blacklist).append('\'');
        sb.append(", workType='").append(workType).append('\'');
        sb.append(", deleted='").append(deleted).append('\'');
        sb.append(", personIds=").append(personIds == null ? "null" : Arrays.asList(personIds).toString());
        sb.append(", wsBlacklists=").append(wsBlacklists);
        sb.append(", wsBlacklist=").append(wsBlacklist);
        sb.append(", personLibraryIds=").append(personLibraryIds == null ? "null" : Arrays.asList(personLibraryIds).toString());
        sb.append(", personLibrary=").append(personLibrary);
        sb.append(", illegalDate=").append(illegalDate);
        sb.append(", removeDate=").append(removeDate);
        sb.append(", illegalReason='").append(illegalReason).append('\'');
        sb.append(", imageUrl='").append(imageUrl).append('\'');
        sb.append(", recorder='").append(recorder).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
