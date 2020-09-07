package com.jlkj.project.ws.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.framework.aspectj.lang.annotation.Excel;
import com.jlkj.framework.web.domain.BaseEntity;
import com.jlkj.project.system.domain.SysEnterprise;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 项目信息 对象 t_ws_project
 *
 * @author jlkj
 * @date 2020-06-09
 */
@ApiModel("项目信息实体")
public class WsProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 项目id */
    @ApiModelProperty("项目id")
    private Integer id;

    /** 项目编号 */
    @ApiModelProperty("项目编号")
    @Excel(name = "项目编号")
    private String projectNo;

    /** 项目名称 */
    @NotBlank(message = "项目名称不能为空")
    @ApiModelProperty(value = "项目名称", required = true)
    @Excel(name = "项目名称")
    private String projectName;

    /** 项目简称 */
    @ApiModelProperty("项目简称")
    @Excel(name = "项目简称")
    private String projectAbbreviation;

    /** 经度 */
    @ApiModelProperty("经度")
    @Excel(name = "经度")
    private Double longitude;

    /** 纬度 */
    @ApiModelProperty("纬度")
    @Excel(name = "纬度")
    private Double latitude;

    /** 区域编码 */
    @ApiModelProperty("区域编码")
    @Excel(name = "区域编码")
    private String areaCode;

    /** 省份 */
    @ApiModelProperty("省份")
    @Excel(name = "省份")
    private String province;

    /** 城市 */
    @ApiModelProperty("城市")
    @Excel(name = "城市")
    private String city;

    /** 区/县 */
    @ApiModelProperty("区/县")
    @Excel(name = "区/县")
    private String district;

    /** 归属公司id */
    @NotNull(message = "归属公司id不能为空")
    @ApiModelProperty(value = "归属公司id", required = true)
    @Excel(name = "归属公司id")
    private Integer projectUnitId;

    /** 归属公司名称 */
    @ApiModelProperty("归属公司名称")
    @Excel(name = "归属公司名称")
    private String projectUnitName;

    /** 项目负责人 */
    @NotNull(message = "项目负责人id不能为空")
    @ApiModelProperty(value = "项目负责人id", required = true)
    @Excel(name = "项目负责人")
    private Integer projectPersonId;

    /** 项目负责人姓名 */
    @ApiModelProperty("项目负责人姓名")
    @Excel(name = "项目负责人姓名")
    private String projectPersonName;

    /** 项目地址 */
    @NotBlank(message = "项目地址不能为空")
    @ApiModelProperty(value = "项目地址", required = true)
    @Excel(name = "项目地址")
    private String projectAddress;

    /** 项目状态 1、在建2、完工3、未建 */
    @ApiModelProperty("项目状态 1、在建2、完工3、未建")
    @Excel(name = "项目状态 1、在建2、完工3、未建")
    private Integer projectStatus;

    /** 建筑规模 1、小型2、中型3、大型 */
    @ApiModelProperty("建筑规模 1、小型2、中型3、大型")
    @Excel(name = "建筑规模 1、小型2、中型3、大型")
    private Integer buildScale;

    /** 投资总额 */
    @ApiModelProperty("投资总额")
    @Excel(name = "投资总额")
    private Double totalInvestment;

    /** 项目logo */
    @ApiModelProperty("项目logo")
    @Excel(name = "项目logo")
    private String projectLogo;

    /** 建筑用途 1:居住建筑,2:居住建筑配置工程,3:公共建筑,4:办公建筑,5:商业建筑,6:旅游建筑,7:科教文卫建筑,8:交通运输类,9:通信建筑,10:公共建筑配套工程,11:商住楼,12:农业建筑,13:农业建筑配套工程,14:工业建筑,15:工业建筑配套工程,16:其它 */
    @ApiModelProperty("建筑用途 1:居住建筑,2:居住建筑配置工程,3:公共建筑,4:办公建筑,5:商业建筑,6:旅游建筑,7:科教文卫建筑,8:交通运输类,9:通信建筑,10:公共建筑配套工程,11:商住楼,12:农业建筑,13:农业建筑配套工程,14:工业建筑,15:工业建筑配套工程,16:其它")
    @Excel(name = "建筑用途 1:居住建筑,2:居住建筑配置工程,3:公共建筑,4:办公建筑,5:商业建筑,6:旅游建筑,7:科教文卫建筑,8:交通运输类,9:通信建筑,10:公共建筑配套工程,11:商住楼,12:农业建筑,13:农业建筑配套工程,14:工业建筑,15:工业建筑配套工程,16:其它")
    private Integer buildPurpose;

    /** 建筑结构类型 */
    @ApiModelProperty("建筑结构类型")
    @Excel(name = "建筑结构类型")
    private String buildStructureType;

    /** 单体数量(个) */
    @ApiModelProperty("单体数量(个)")
    @Excel(name = "单体数量(个)")
    private Integer numMonomers;

    /** 建筑面积 */
    @ApiModelProperty("建筑面积")
    @Excel(name = "建筑面积")
    private Double buildArea;

    /** 地上面积(m2) */
    @ApiModelProperty("地上面积(m2)")
    @Excel(name = "地上面积(m2)")
    private Double groundArea;

    /** 地下面积(m2) */
    @ApiModelProperty("地下面积(m2)")
    @Excel(name = "地下面积(m2)")
    private Double undergroundArea;

    /** 建筑高度 */
    @ApiModelProperty("建筑高度")
    @Excel(name = "建筑高度")
    private Double buildHeight;

    /** 地上层数 */
    @ApiModelProperty("地上层数")
    @Excel(name = "地上层数")
    private Integer groundLayernum;

    /** 地下深度(m) */
    @ApiModelProperty("地下深度(m)")
    @Excel(name = "地下深度(m)")
    private Double undergroundDepth;

    /** 基坑深度(m) */
    @ApiModelProperty("基坑深度(m)")
    @Excel(name = "基坑深度(m)")
    private Double foundationpitDepth;

    /** 建设单位 */
    @ApiModelProperty("建设单位")
    @Excel(name = "建设单位")
    private String constructionUnit;

    /** 设计单位 */
    @ApiModelProperty("设计单位")
    @Excel(name = "设计单位")
    private String designer;

    /** 监理单位 */
    @ApiModelProperty("监理单位")
    @Excel(name = "监理单位")
    private String controlUnit;

    /** 施工(总包)单位 */
    @ApiModelProperty("施工(总包)单位")
    @Excel(name = "施工(总包)单位")
    private String ceneralcontractUnit;

    /** 安全监督单位 */
    @ApiModelProperty("安全监督单位")
    @Excel(name = "安全监督单位")
    private String safetySupervision;

    /** 质量监督单位 */
    @ApiModelProperty("质量监督单位")
    @Excel(name = "质量监督单位")
    private String qualitySupervision;

    /** 投资类型 1：政府投资，2：国有企业投资，3：非国有企业投资，4：其它 */
    @ApiModelProperty("投资类型 1：政府投资，2：国有企业投资，3：非国有企业投资，4：其它")
    @Excel(name = "投资类型 1：政府投资，2：国有企业投资，3：非国有企业投资，4：其它")
    private Integer lnvestmentType;

    /** 中标编号 */
    @ApiModelProperty("中标编号")
    @Excel(name = "中标编号")
    private String bidNo;

    /** 合同造价(万元) */
    @ApiModelProperty("合同造价(万元)")
    @Excel(name = "合同造价(万元)")
    private Double contractCost;

    /** 施工许可证 */
    @ApiModelProperty("施工许可证")
    @Excel(name = "施工许可证")
    private String constructionPermit;

    /** 施工许可证照片URL */
    @ApiModelProperty("施工许可证照片URL")
    @Excel(name = "施工许可证照片URL")
    private String constructionPermitUrl;

    /** 开工时间 */
    @NotBlank(message = "开工时间不能为空")
    @ApiModelProperty(value = "开工时间  格式yyyy-MM-dd", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开工时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 完工时间 */
    @NotBlank(message = "完工时间不能为空")
    @ApiModelProperty(value = "完工时间  格式yyyy-MM-dd", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完工时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completeTime;

    /** 项目经理id */
    @ApiModelProperty("项目经理姓名")
    @Excel(name = "项目经理姓名")
    private String projectManagerName;

    /** 联系电话 */
    @ApiModelProperty("联系电话")
    @Excel(name = "联系电话")
    private String contactNumber;

    /** 违章通知电话 */
    @ApiModelProperty("违章通知电话")
    @Excel(name = "违章通知电话")
    private String violationNotice;

    /** 发薪银行 */
    @ApiModelProperty("发薪银行")
    @Excel(name = "发薪银行")
    private String payBank;

    /** 开户银行 */
    @ApiModelProperty("开户银行")
    @Excel(name = "开户银行")
    private String bankDeposit;

    /** 发薪账号 */
    @ApiModelProperty("发薪账号")
    @Excel(name = "发薪账号")
    private String payAccountno;

    /** 发薪日期 */
    @ApiModelProperty("发薪日期(1-31)")
    @Excel(name = "发薪日期", width = 30)
    private Integer payDate;

    /** 质量目标 */
    @ApiModelProperty("质量目标")
    @Excel(name = "质量目标")
    private String qualityObjectives;

    /** 安全目标 */
    @ApiModelProperty("安全目标")
    @Excel(name = "安全目标")
    private String safetyObjectives;

    /** 合同备案号 */
    @ApiModelProperty("合同备案号")
    @Excel(name = "合同备案号")
    private String contractRecordno;

    /** 备注 */
    @ApiModelProperty("备注")
    @Excel(name = "备注")
    private String remarks;

    /** 是否删除 Y（删除），N(不删除) */
    @ApiModelProperty("是否删除 Y（删除），N(不删除)  默认N")
    @Excel(name = "是否删除 Y", readConverterExp = "删=除")
    private String isDelete;

    /** 创建人 */
    @ApiModelProperty("创建人")
    @Excel(name = "创建人")
    private Integer createdBy;

    /** 创建时间 */
    @ApiModelProperty("创建时间 格式yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    @Excel(name = "更新人")
    private Integer updatedBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间 格式 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;


    private List<Integer> projectIds;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectAbbreviation() {
        return projectAbbreviation;
    }

    public void setProjectAbbreviation(String projectAbbreviation) {
        this.projectAbbreviation = projectAbbreviation;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getProjectUnitId() {
        return projectUnitId;
    }

    public void setProjectUnitId(Integer projectUnitId) {
        this.projectUnitId = projectUnitId;
    }

    public String getProjectUnitName() {
        return projectUnitName;
    }

    public void setProjectUnitName(String projectUnitName) {
        this.projectUnitName = projectUnitName;
    }

    public Integer getProjectPersonId() {
        return projectPersonId;
    }

    public void setProjectPersonId(Integer projectPersonId) {
        this.projectPersonId = projectPersonId;
    }

    public String getProjectPersonName() {
        return projectPersonName;
    }

    public void setProjectPersonName(String projectPersonName) {
        this.projectPersonName = projectPersonName;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Integer getBuildScale() {
        return buildScale;
    }

    public void setBuildScale(Integer buildScale) {
        this.buildScale = buildScale;
    }

    public Double getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(Double totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public String getProjectLogo() {
        return projectLogo;
    }

    public void setProjectLogo(String projectLogo) {
        this.projectLogo = projectLogo;
    }

    public Integer getBuildPurpose() {
        return buildPurpose;
    }

    public void setBuildPurpose(Integer buildPurpose) {
        this.buildPurpose = buildPurpose;
    }

    public String getBuildStructureType() {
        return buildStructureType;
    }

    public void setBuildStructureType(String buildStructureType) {
        this.buildStructureType = buildStructureType;
    }

    public Integer getNumMonomers() {
        return numMonomers;
    }

    public void setNumMonomers(Integer numMonomers) {
        this.numMonomers = numMonomers;
    }

    public Double getBuildArea() {
        return buildArea;
    }

    public void setBuildArea(Double buildArea) {
        this.buildArea = buildArea;
    }

    public Double getGroundArea() {
        return groundArea;
    }

    public void setGroundArea(Double groundArea) {
        this.groundArea = groundArea;
    }

    public Double getUndergroundArea() {
        return undergroundArea;
    }

    public void setUndergroundArea(Double undergroundArea) {
        this.undergroundArea = undergroundArea;
    }

    public Double getBuildHeight() {
        return buildHeight;
    }

    public void setBuildHeight(Double buildHeight) {
        this.buildHeight = buildHeight;
    }

    public Integer getGroundLayernum() {
        return groundLayernum;
    }

    public void setGroundLayernum(Integer groundLayernum) {
        this.groundLayernum = groundLayernum;
    }

    public Double getUndergroundDepth() {
        return undergroundDepth;
    }

    public void setUndergroundDepth(Double undergroundDepth) {
        this.undergroundDepth = undergroundDepth;
    }

    public Double getFoundationpitDepth() {
        return foundationpitDepth;
    }

    public void setFoundationpitDepth(Double foundationpitDepth) {
        this.foundationpitDepth = foundationpitDepth;
    }

    public String getConstructionUnit() {
        return constructionUnit;
    }

    public void setConstructionUnit(String constructionUnit) {
        this.constructionUnit = constructionUnit;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public String getControlUnit() {
        return controlUnit;
    }

    public void setControlUnit(String controlUnit) {
        this.controlUnit = controlUnit;
    }

    public String getCeneralcontractUnit() {
        return ceneralcontractUnit;
    }

    public void setCeneralcontractUnit(String ceneralcontractUnit) {
        this.ceneralcontractUnit = ceneralcontractUnit;
    }

    public String getSafetySupervision() {
        return safetySupervision;
    }

    public void setSafetySupervision(String safetySupervision) {
        this.safetySupervision = safetySupervision;
    }

    public String getQualitySupervision() {
        return qualitySupervision;
    }

    public void setQualitySupervision(String qualitySupervision) {
        this.qualitySupervision = qualitySupervision;
    }

    public Integer getLnvestmentType() {
        return lnvestmentType;
    }

    public void setLnvestmentType(Integer lnvestmentType) {
        this.lnvestmentType = lnvestmentType;
    }

    public String getBidNo() {
        return bidNo;
    }

    public void setBidNo(String bidNo) {
        this.bidNo = bidNo;
    }

    public Double getContractCost() {
        return contractCost;
    }

    public void setContractCost(Double contractCost) {
        this.contractCost = contractCost;
    }

    public String getConstructionPermit() {
        return constructionPermit;
    }

    public void setConstructionPermit(String constructionPermit) {
        this.constructionPermit = constructionPermit;
    }

    public String getConstructionPermitUrl() {
        return constructionPermitUrl;
    }

    public void setConstructionPermitUrl(String constructionPermitUrl) {
        this.constructionPermitUrl = constructionPermitUrl;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getProjectManagerName() {
        return projectManagerName;
    }

    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPayBank() {
        return payBank;
    }

    public void setPayBank(String payBank) {
        this.payBank = payBank;
    }

    public String getBankDeposit() {
        return bankDeposit;
    }

    public void setBankDeposit(String bankDeposit) {
        this.bankDeposit = bankDeposit;
    }

    public String getPayAccountno() {
        return payAccountno;
    }

    public void setPayAccountno(String payAccountno) {
        this.payAccountno = payAccountno;
    }

    public Integer getPayDate() {
        return payDate;
    }

    public void setPayDate(Integer payDate) {
        this.payDate = payDate;
    }

    public String getQualityObjectives() {
        return qualityObjectives;
    }

    public void setQualityObjectives(String qualityObjectives) {
        this.qualityObjectives = qualityObjectives;
    }

    public String getSafetyObjectives() {
        return safetyObjectives;
    }

    public void setSafetyObjectives(String safetyObjectives) {
        this.safetyObjectives = safetyObjectives;
    }

    public String getContractRecordno() {
        return contractRecordno;
    }

    public void setContractRecordno(String contractRecordno) {
        this.contractRecordno = contractRecordno;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getViolationNotice() {
        return violationNotice;
    }

    public void setViolationNotice(String violationNotice) {
        this.violationNotice = violationNotice;
    }

    public List<Integer> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Integer> projectIds) {
        this.projectIds = projectIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectName", getProjectName())
            .append("projectAbbreviation", getProjectAbbreviation())
            .append("longitude", getLongitude())
            .append("latitude", getLatitude())
            .append("areaCode", getAreaCode())
            .append("projectUnitId", getProjectUnitId())
            .append("projectUnitName", getProjectUnitName())
            .append("projectPersonId", getProjectPersonId())
            .append("projectPersonName", getProjectPersonName())
            .append("projectAddress", getProjectAddress())
            .append("projectStatus", getProjectStatus())
            .append("buildScale", getBuildScale())
            .append("totalInvestment", getTotalInvestment())
            .append("projectLogo", getProjectLogo())
            .append("buildPurpose", getBuildPurpose())
            .append("buildStructureType", getBuildStructureType())
            .append("numMonomers", getNumMonomers())
            .append("buildArea", getBuildArea())
            .append("groundArea", getGroundArea())
            .append("undergroundArea", getUndergroundArea())
            .append("buildHeight", getBuildHeight())
            .append("groundLayernum", getGroundLayernum())
            .append("undergroundDepth", getUndergroundDepth())
            .append("foundationpitDepth", getFoundationpitDepth())
            .append("constructionUnit", getConstructionUnit())
            .append("designer", getDesigner())
            .append("controlUnit", getControlUnit())
            .append("ceneralcontractUnit", getCeneralcontractUnit())
            .append("safetySupervision", getSafetySupervision())
            .append("qualitySupervision", getQualitySupervision())
            .append("lnvestmentType", getLnvestmentType())
            .append("bidNo", getBidNo())
            .append("contractCost", getContractCost())
            .append("constructionPermit", getConstructionPermit())
            .append("constructionPermitUrl", getConstructionPermitUrl())
            .append("startTime", getStartTime())
            .append("completeTime", getCompleteTime())
            .append("projectManagerName", getProjectManagerName())
            .append("contactNumber", getContactNumber())
            .append("payBank", getPayBank())
            .append("bankDeposit", getBankDeposit())
            .append("payAccountno", getPayAccountno())
            .append("payDate", getPayDate())
            .append("qualityObjectives", getQualityObjectives())
            .append("safetyObjectives", getSafetyObjectives())
            .append("contractRecordno", getContractRecordno())
            .append("remarks", getRemarks())
            .append("isDelete", getIsDelete())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }

    public WsProject() {
    }

    public WsProject(SysEnterprise sysEnterprise) {
        this.id = sysEnterprise.getEnterpriseId().intValue();
    }
}
