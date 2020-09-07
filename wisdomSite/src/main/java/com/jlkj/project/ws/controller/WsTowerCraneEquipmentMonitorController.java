package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsArea;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.dto.*;
import com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout;
import com.jlkj.project.ws.intelligence.domain.WsTowercraneEquipment;
import com.jlkj.project.ws.service.IWsAreaService;
import com.jlkj.project.ws.service.IWsTowerCraneEquipmentMonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 塔机监测 Controller
 *
 * @author gaowei
 * @date 2020-06-29
 */
@Api(value = "智能监测：塔机监测接口",tags = "塔机监测页面")
@RestController
@RequestMapping("/ws/towerCraneEquipmentMonitor")
public class WsTowerCraneEquipmentMonitorController extends BaseController
{
    @Autowired
    private IWsTowerCraneEquipmentMonitorService wsTowerCraneEquipmentMonitorService;

    @Autowired
    private IWsAreaService wsAreaService;

    /**
     * 获取塔机实时监测-企业级-项目搜索
     * 公司级
     */
    @ApiOperation("企业：获取实时监测首页数据-项目搜索")
    @ApiImplicitParam(name = "wsProject", value = "项目实体项 ：项目名称：projectName",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/realTimeProjectSearch")
    public AjaxResult getRealTimeProjectSearch(WsProject wsProject)
    {
        List<WsProject> list = wsTowerCraneEquipmentMonitorService.selectRealTimeProjectSearch(wsProject);
        return AjaxResult.success(list);
    }

    /**
     * 获取塔机实时监测-企业级-根据项目id查询场布图
     * 公司级
     */
    @ApiOperation("企业：获取实时监测首页数据-根据项目id查询场布图")
    @ApiImplicitParam(name = "wsProjectFieldLayout", value = "工程场布图实体项 ：项目id：projectId",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/realTimeProjectFieldLayout")
    public AjaxResult getRealTimeProjectFieldLayout(WsProjectFieldLayout wsProjectFieldLayout)
    {
        List<WsProjectFieldLayout> list = wsTowerCraneEquipmentMonitorService.selectRealTimeProjectFieldLayout(wsProjectFieldLayout);
        return AjaxResult.success(list);
    }

    /**
     * 获取塔机实时监测-企业级-根据项目id塔吊设备位置
     * 公司级
     */
    @ApiOperation("企业：获取实时监测首页数据-根据项目id塔吊设备位置")
    @ApiImplicitParam(name = "wsTowercraneEquipment", value = "工程场布图实体项 ：项目id：projectId",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/realTimeTowercraneEquipment")
    public AjaxResult getRealTimeTowercraneEquipment(WsTowercraneEquipment wsTowercraneEquipment)
    {
        List<WsTowercraneEquipment> list = wsTowerCraneEquipmentMonitorService.selectRealTimeTowercraneEquipment(wsTowercraneEquipment);
        return AjaxResult.success(list);
    }

    @ApiOperation("获取地图区域设备统计列表")//根据区域code查询
    @ApiImplicitParam(name = "towerCraneMonitoringRealtimeDTO", value = "塔机实时监测实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @PostMapping(value = "/area")
    public AjaxResult getAreaDeviceStatisticsList(@RequestBody WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO)
    {
        if (getAreaCode(towerCraneMonitoringRealtimeDTO)) return AjaxResult.error("区域信息不能为空");
        if(towerCraneMonitoringRealtimeDTO.getWsArea().getZoom().equals("4")){
            return AjaxResult.success(wsTowerCraneEquipmentMonitorService.getAreaDeviceStatisticsListProject(towerCraneMonitoringRealtimeDTO));
        }else{
            return AjaxResult.success(wsTowerCraneEquipmentMonitorService.getAreaDeviceStatisticsList(towerCraneMonitoringRealtimeDTO));
        }
    }

    private boolean getAreaCode(WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO) {
        if(StringUtils.isNull(towerCraneMonitoringRealtimeDTO.getWsArea())) return true;
        WsArea wsArea = towerCraneMonitoringRealtimeDTO.getWsArea();
        String areaCode = wsAreaService.getZoomCode(wsArea.getZoom(),wsArea.getLongitude(),wsArea.getLatitude());
        towerCraneMonitoringRealtimeDTO.setAreaCode(areaCode);
        return false;
    }


    /**
     * 获取塔机实时监测-企业级 ：根据区域code查询
     * 公司级
     */
    @ApiOperation("企业：获取实时监测首页数据")
    @ApiImplicitParam(name = "towerCraneMonitoringRealtimeDTO", value = "塔机实时监测实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @PostMapping(value = "/realTimeMonitoring")
    public AjaxResult getRealTimeMonitoring(@RequestBody WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO)
    {
        if (getAreaCode(towerCraneMonitoringRealtimeDTO)) return AjaxResult.error("区域信息不能为空");
        towerCraneMonitoringRealtimeDTO = wsTowerCraneEquipmentMonitorService.selectRealTimeMonitoring(towerCraneMonitoringRealtimeDTO);
        return AjaxResult.success(towerCraneMonitoringRealtimeDTO);
    }

    /**
     * 查询设备详情 列表
     */
    @ApiOperation("企业：更多详情-查询设备详情列表")
    @ApiImplicitParam(name = "设备详情",value = "towerCraneMonitoringRealtimeDTO" ,dataType = "towerCraneMonitoringRealtimeDTO")
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping("/equipmentList")
    public TableDataInfo equipmentList(WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO)
    {
        startPage();
        List<WsTowerCraneMonitoringRealtimeDTO> list = wsTowerCraneEquipmentMonitorService.selectEquipmentList(towerCraneMonitoringRealtimeDTO);
        return getDataTable(list);
    }

    /**
     * 导出设备详情
     */
    @ApiOperation("企业：更多详情-导出设备详情")
    @ApiImplicitParam(name = "towerCraneMonitoringRealtimeDTO", value = "塔机实时监测实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:export')")
    @Log(title = "设备详情", businessType = BusinessType.EXPORT)
    @GetMapping("/equipmentExport")
    public AjaxResult equipmentExport(WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO)
    {
        List<WsTowerCraneMonitoringRealtimeDTO> list = wsTowerCraneEquipmentMonitorService.selectEquipmentList(towerCraneMonitoringRealtimeDTO);
        ExcelUtil<WsTowerCraneMonitoringRealtimeDTO> util = new ExcelUtil<WsTowerCraneMonitoringRealtimeDTO>(WsTowerCraneMonitoringRealtimeDTO.class);
        return util.exportExcel(list, "设备详情");
    }

    /**
     * 查询违章详情 列表
     */
    @ApiOperation("企业：更多详情-查询违章详情列表")
    @ApiImplicitParam(name = "违章详情",value = "towerCraneMonitoringRealtimeDTO" ,dataType = "towerCraneMonitoringRealtimeDTO")
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping("/illegalList")
    public TableDataInfo illegalList(WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO)
    {
        startPage();
        List<WsTowerCraneMonitoringRealtimeDTO> list = wsTowerCraneEquipmentMonitorService.selectIllegalList(towerCraneMonitoringRealtimeDTO);
        return getDataTable(list);
    }

    /**
     * 导出违章详情
     */
    @ApiOperation("企业：更多详情-导出违章详情")
    @ApiImplicitParam(name = "towerCraneMonitoringRealtimeDTO", value = "塔机实时监测实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:export')")
    @Log(title = "违章详情", businessType = BusinessType.EXPORT)
    @GetMapping("/illegalExport")
    public AjaxResult illegalExport(WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO)
    {
        List<WsTowerCraneMonitoringRealtimeDTO> list = wsTowerCraneEquipmentMonitorService.selectIllegalList(towerCraneMonitoringRealtimeDTO);
        ExcelUtil<WsTowerCraneMonitoringRealtimeDTO> util = new ExcelUtil<WsTowerCraneMonitoringRealtimeDTO>(WsTowerCraneMonitoringRealtimeDTO.class);
        return util.exportExcel(list, "违章详情");
    }


    /**
     * 监控统计-按企业
     * 监控设备分析
     */
    @ApiOperation("企业：监控统计-监控设备分析")
    @ApiImplicitParam(name = "wsTowerCraneMonitoringStatisticsDTO", value = "监控统计实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/monitoringEquipmentAnalysis")
    public AjaxResult getMonitoringEquipmentAnalysis(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO)
    {
        List<WsTowerCraneMonitoringStatisticsDTO> list = wsTowerCraneEquipmentMonitorService.selectMonitoringEquipmentAnalysis(wsTowerCraneMonitoringStatisticsDTO);
        return AjaxResult.success(list);
    }


    /**
     * 监控统计-按企业
     * 设备违章分析
     */
    @ApiOperation("企业：监控统计-设备违章分析")
    @ApiImplicitParam(name = "wsTowerCraneMonitoringStatisticsDTO", value = "设备违章分析",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/equipmentViolationAnalysis")
    public AjaxResult getEquipmentViolationAnalysis(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO)
    {
        List<WsTowerCraneMonitoringStatisticsDTO> list = wsTowerCraneEquipmentMonitorService.selectEquipmentViolationAnalysis(wsTowerCraneMonitoringStatisticsDTO);
        return AjaxResult.success(list);
    }

    /**
     * 监控统计-按企业
     * 项目违章占比
     */
    @ApiOperation("企业：监控统计-项目违章占比")
    @ApiImplicitParam(name = "wsTowerCraneMonitoringStatisticsDTO", value = "项目违章占比",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/projectViolationProportion")
    public AjaxResult getProjectViolationProportion(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO)
    {
        wsTowerCraneMonitoringStatisticsDTO = wsTowerCraneEquipmentMonitorService.selectProjectViolationProportion(wsTowerCraneMonitoringStatisticsDTO);
        return AjaxResult.success(wsTowerCraneMonitoringStatisticsDTO);
    }


    /**
     * 监控统计-按企业
     * 违章项目排名
     */
    @ApiOperation("企业：监控统计-违章项目排名")
    @ApiImplicitParam(name = "wsTowerCraneMonitoringStatisticsDTO", value = "违章项目排名",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/rankingOfViolations")
    public AjaxResult getRankingOfViolations(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO)
    {
        List<WsTowerCraneMonitoringStatisticsDTO> list = wsTowerCraneEquipmentMonitorService.selectRankingOfViolations(wsTowerCraneMonitoringStatisticsDTO);
        return AjaxResult.success(list);
    }


    /**
     * 监控统计-按项目
     * 司机不良操作
     */
    @ApiOperation("司机不良操作-按项目")
    @ApiImplicitParam(name = "wsTowerCraneMonitoringStatisticsDTO", value = "司机不良操作-按项目:projectId，days，conditionsDate",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/badOperationDrive")
    public AjaxResult getBadOperationDrive(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO)
    {
        List<WsTowerCraneMonitoringStatisticsDTO> list = wsTowerCraneEquipmentMonitorService.selectBadOperationDrive(wsTowerCraneMonitoringStatisticsDTO);
        return AjaxResult.success(list);
    }

    /**
     * 监控统计-按项目-7-30-自定义
     * 设备违章
     */
    @ApiOperation("设备违章-按项目")
    @ApiImplicitParam(name = "wsTowerCraneMonitoringStatisticsDTO", value = "设备违章-按项目",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/equipmentViolation")
    public AjaxResult getEquipmentViolation(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO)
    {
        List<WsTowerCraneMonitoringStatisticsDTO> list = wsTowerCraneEquipmentMonitorService.selectEquipmentViolation(wsTowerCraneMonitoringStatisticsDTO);
        return AjaxResult.success(list);
    }

    /**
     * 监控统计-按项目-7-30-自定义
     * 设备工作记录
     */
    @ApiOperation("设备工作记录-按项目")
    @ApiImplicitParam(name = "wsTowerCraneMonitoringStatisticsDTO", value = "设备工作记录-按项目",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/equipmentWorkRecord")
    public AjaxResult getEquipmentWorkRecord(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO)
    {
        List<WsTowerCraneMonitoringStatisticsDTO> list = wsTowerCraneEquipmentMonitorService.selectEquipmentWorkRecord(wsTowerCraneMonitoringStatisticsDTO);
        return AjaxResult.success(list);
    }

    /**
     * 监控统计-按公司-自定义
     * 监控设备分析/设备违章分析
     */
    @ApiOperation("监控设备分析/设备违章分析")
    @ApiImplicitParam(name = "wsTowerCraneMonitoringStatisticsDTO", value = "监控设备分析/设备违章分析",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/monitoringEquipmentOrViolation")
    public AjaxResult getMonitoringEquipmentOrViolation(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO)
    {
        wsTowerCraneMonitoringStatisticsDTO = wsTowerCraneEquipmentMonitorService.selectMonitoringEquipmentOrViolation(wsTowerCraneMonitoringStatisticsDTO);
        return AjaxResult.success(wsTowerCraneMonitoringStatisticsDTO);
    }

    /**
     * 监控统计-按企业 -- 自定义
     * 项目违章占比
     */
    @ApiOperation("项目违章占比")
    @ApiImplicitParam(name = "wsTowerCraneMonitoringStatisticsDTO", value = "项目违章占比-按企业--自定义",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/projectViolationProportionCustom")
    public AjaxResult getProjectViolationProportionCustom(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO)
    {
        wsTowerCraneMonitoringStatisticsDTO = wsTowerCraneEquipmentMonitorService.selectProjectViolationProportionCustom(wsTowerCraneMonitoringStatisticsDTO);
        return AjaxResult.success(wsTowerCraneMonitoringStatisticsDTO);
    }

    /**
     * 监控统计-按企业--自定义
     * 违章项目排名
     */
    @ApiOperation("违章项目排名-按企业--自定义")
    @ApiImplicitParam(name = "wsTowerCraneMonitoringStatisticsDTO", value = "违章项目排名-按企业--自定义",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/rankingOfViolationsCustom")
    public AjaxResult getRankingOfViolationsCustom(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO)
    {
        List<WsTowerCraneMonitoringStatisticsDTO> list = wsTowerCraneEquipmentMonitorService.selectRankingOfViolationsCustom(wsTowerCraneMonitoringStatisticsDTO);
        return AjaxResult.success(list);
    }

    /*详情查询*/
    /**
     * 监控统计-按企业--设备详情
     * 设备详情
     */
    @ApiOperation("设备详情-按企业")
    @ApiImplicitParam(name = "wsTowerCraneEquipmentDetailsDTO", value = "设备详情-按企业",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/deviceDetails")
    public TableDataInfo getDeviceDetails(WsTowerCraneEquipmentDetailsDTO wsTowerCraneEquipmentDetailsDTO)
    {
        startPage();
        List<WsTowerCraneEquipmentDetailsDTO> list = wsTowerCraneEquipmentMonitorService.selectDeviceDetails(wsTowerCraneEquipmentDetailsDTO);
        return getDataTable(list);
    }

    /**
     * 导出设备详情
     */
    @ApiOperation("导出设备详情")
    @ApiImplicitParam(name = "wsTowerCraneEquipmentDetailsDTO", value = "设备详情实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:export')")
    @Log(title = "设备详情", businessType = BusinessType.EXPORT)
    @GetMapping("/illegalExportDetails")
    public AjaxResult illegalExportDetails(WsTowerCraneEquipmentDetailsDTO wsTowerCraneEquipmentDetailsDTO)
    {
        List<WsTowerCraneEquipmentDetailsDTO> list = wsTowerCraneEquipmentMonitorService.selectDeviceDetails(wsTowerCraneEquipmentDetailsDTO);
        ExcelUtil<WsTowerCraneEquipmentDetailsDTO> util = new ExcelUtil<WsTowerCraneEquipmentDetailsDTO>(WsTowerCraneEquipmentDetailsDTO.class);
        return util.exportExcel(list, "设备详情");
    }

    /**
     * 监控统计-按企业--违章详情
     * 违章详情
     */
    @ApiOperation("违章详情-按企业")
    @ApiImplicitParam(name = "wsTowerCraneEquipmentViolationDetailsDTO", value = "违章详情-按企业",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/violationDetails")
    public TableDataInfo getViolationDetails(WsTowerCraneEquipmentViolationDetailsDTO wsTowerCraneEquipmentViolationDetailsDTO)
    {
        startPage();
        List<WsTowerCraneEquipmentViolationDetailsDTO> list = wsTowerCraneEquipmentMonitorService.selectViolationDetails(wsTowerCraneEquipmentViolationDetailsDTO);
        return getDataTable(list);
    }

    /**
     * 导出违章详情-按企业
     */
    @ApiOperation("导出违章详情")
    @ApiImplicitParam(name = "wsTowerCraneEquipmentViolationDetailsDTO", value = "违章详情实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:export')")
    @Log(title = "违章详情", businessType = BusinessType.EXPORT)
    @GetMapping("/illegalExportViolationDetails")
    public AjaxResult illegalExportViolationDetails(WsTowerCraneEquipmentViolationDetailsDTO wsTowerCraneEquipmentViolationDetailsDTO)
    {
        List<WsTowerCraneEquipmentViolationDetailsDTO> list = wsTowerCraneEquipmentMonitorService.selectViolationDetails(wsTowerCraneEquipmentViolationDetailsDTO);
        ExcelUtil<WsTowerCraneEquipmentViolationDetailsDTO> util = new ExcelUtil<WsTowerCraneEquipmentViolationDetailsDTO>(WsTowerCraneEquipmentViolationDetailsDTO.class);
        return util.exportExcel(list, "违章详情");
    }

    /**
     * 监控统计-按项目--违章详情
     * 违章详情
     */
    @ApiOperation("违章详情-按项目")
    @ApiImplicitParam(name = "wsTowerCraneEquipmentViolationDetailsDTO", value = "违章详情-按项目",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/violationDetailsProject")
    public TableDataInfo getViolationDetailsProject(WsTowerCraneEquipmentViolationDetailsDTO wsTowerCraneEquipmentViolationDetailsDTO)
    {
        startPage();
        List<WsTowerCraneEquipmentViolationDetailsDTO> list = wsTowerCraneEquipmentMonitorService.selectViolationDetailsProject(wsTowerCraneEquipmentViolationDetailsDTO);
        return getDataTable(list);
    }

    /**
     * 导出违章详情-按项目
     */
    @ApiOperation("导出违章详情")
    @ApiImplicitParam(name = "wsTowerCraneEquipmentViolationDetailsDTO", value = "违章详情实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:export')")
    @Log(title = "违章详情", businessType = BusinessType.EXPORT)
    @GetMapping("/illegalExportViolationDetailsProject")
    public AjaxResult illegalExportViolationDetailsProject(WsTowerCraneEquipmentViolationDetailsDTO wsTowerCraneEquipmentViolationDetailsDTO)
    {
        List<WsTowerCraneEquipmentViolationDetailsDTO> list = wsTowerCraneEquipmentMonitorService.selectViolationDetailsProject(wsTowerCraneEquipmentViolationDetailsDTO);
        ExcelUtil<WsTowerCraneEquipmentViolationDetailsDTO> util = new ExcelUtil<WsTowerCraneEquipmentViolationDetailsDTO>(WsTowerCraneEquipmentViolationDetailsDTO.class);
        return util.exportExcel(list, "违章详情");
    }

    /**
     * 监控统计-按项目--工作详情
     * 工作详情
     */
    @ApiOperation("工作详情-按项目")
    @ApiImplicitParam(name = "wsTowerCraneEquipmentWorkDetailsDTO", value = "工作详情-按项目",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:list')")
    @GetMapping(value = "/workDetailsProject")
    public TableDataInfo list(WsTowerCraneEquipmentWorkDetailsDTO wsTowerCraneEquipmentWorkDetailsDTO)
    {
        startPage();
        List<WsTowerCraneEquipmentWorkDetailsDTO> list = wsTowerCraneEquipmentMonitorService.selectWorkDetailsProject(wsTowerCraneEquipmentWorkDetailsDTO);
        return getDataTable(list);
    }

    /**
     * 导出工作详情-按项目
     */
    @ApiOperation("导出工作详情")
    @ApiImplicitParam(name = "wsTowerCraneEquipmentWorkDetailsDTO", value = "工作详情实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:export')")
    @Log(title = "工作详情", businessType = BusinessType.EXPORT)
    @GetMapping("/exportWorkDetailsProject")
    public AjaxResult exportWorkDetailsProject(WsTowerCraneEquipmentWorkDetailsDTO wsTowerCraneEquipmentWorkDetailsDTO)
    {
        List<WsTowerCraneEquipmentWorkDetailsDTO> list = wsTowerCraneEquipmentMonitorService.selectWorkDetailsProject(wsTowerCraneEquipmentWorkDetailsDTO);
        ExcelUtil<WsTowerCraneEquipmentWorkDetailsDTO> util = new ExcelUtil<WsTowerCraneEquipmentWorkDetailsDTO>(WsTowerCraneEquipmentWorkDetailsDTO.class);
        return util.exportExcel(list, "工作详情");
    }


    /**
     * 塔机监控 - 项目 -实时监控页面
     * 实时监控：在场塔吊数；在线率；今日违章；违章率
     */
    @ApiOperation("实时监控页面：在场塔吊数；在线率；今日违章；违章率")
    @ApiImplicitParam(name = "wsTowerCraneProjectRealTimeMonitoringDTO", value = "实时监控页面-项目",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/projectRealTimeMonitoring")
    public AjaxResult getProjectRealTimeMonitoring(WsTowerCraneProjectRealTimeMonitoringDTO wsTowerCraneProjectRealTimeMonitoringDTO)
    {
        wsTowerCraneProjectRealTimeMonitoringDTO = wsTowerCraneEquipmentMonitorService.selectProjectRealTimeMonitoring(wsTowerCraneProjectRealTimeMonitoringDTO);
        return AjaxResult.success(wsTowerCraneProjectRealTimeMonitoringDTO);
    }


    /**
     * 塔机监控 - 项目 -实时监控页面
     * 实时监控
     */
    @ApiOperation("实时监控-按项目")
    @ApiImplicitParam(name = "wsTowerCraneProjectRealTimeMonitoringDTO", value = "实时监控-按项目",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/equipmentRealtimeMonitoring")
    public AjaxResult getEquipmentRealtimeMonitoring(WsTowerCraneProjectRealTimeMonitoringDTO wsTowerCraneProjectRealTimeMonitoringDTO)
    {
        List<WsTowerCraneProjectRealTimeMonitoringDTO> list = wsTowerCraneEquipmentMonitorService.selectEquipmentRealtimeMonitoring(wsTowerCraneProjectRealTimeMonitoringDTO);
        return AjaxResult.success(list);
    }

    /**
     * 塔机监控 - 项目 -监控详情页面
     * 监控详情
     */
    @ApiOperation("监控详情-项目")
    @ApiImplicitParam(name = "wsTowerCraneProjectMonitoringDetailsDTO", value = "监控详情-项目：towerCraneId；monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/projectMonitoringDetails")
    public AjaxResult getProjectMonitoringDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO)
    {
        wsTowerCraneProjectMonitoringDetailsDTO = wsTowerCraneEquipmentMonitorService.selectProjectMonitoringDetails(wsTowerCraneProjectMonitoringDetailsDTO);
        return AjaxResult.success(wsTowerCraneProjectMonitoringDetailsDTO);
    }

    /**
     * 塔机监控 - 项目 -监控详情-吊钩视频
     * 吊钩视频
     */
    @ApiOperation("监控详情—吊钩视频-项目")
    @ApiImplicitParam(name = "wsTowerCraneProjectMonitoringDetailsDTO", value = "监控详情-吊钩视频-项目：hookVideoId",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/hookVideoDetails")
    public AjaxResult getHookVideoDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO)
    {
        if(wsTowerCraneProjectMonitoringDetailsDTO.getHookVideoId() != null){
            wsTowerCraneProjectMonitoringDetailsDTO = wsTowerCraneEquipmentMonitorService.selectHookVideoDetails(wsTowerCraneProjectMonitoringDetailsDTO);
            return AjaxResult.success(wsTowerCraneProjectMonitoringDetailsDTO);
        }else{
            return AjaxResult.error("未绑定吊钩视频!");
        }
    }

    /**
     * 塔机监控 - 项目 -实时监控-页面
     * 设备状态分析
     * monitoringEquipmentNo
     */
    @ApiOperation("实时监控-按项目-设备状态分析")
    @ApiImplicitParam(name = "wsTowerCraneProjectMonitoringDetailsDTO", value = "实时监控-按项目-设备状态分析:monitoringEquipmentNo ; 次数：frequency（20次，50次，200次）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/equipmentStatusAnalysis")
    public AjaxResult getEquipmentStatusAnalysis(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO)
    {
        List<WsTowerCraneProjectMonitoringDetailsDTO> list = wsTowerCraneEquipmentMonitorService.selectEquipmentStatusAnalysis(wsTowerCraneProjectMonitoringDetailsDTO);
        return AjaxResult.success(list);
    }

    /**
     * 塔机监控 - 项目 -实时监控-页面
     * 吊装循环次数
     * monitoringEquipmentNo
     */
    @ApiOperation("实时监控-按项目-吊装循环次数")
    @ApiImplicitParam(name = "wsTowerCraneProjectMonitoringDetailsDTO", value = "实时监控-按项目-设备状态分析:monitoringEquipmentNo ; 查询类型：queryType (1:7天；2:15天；3:30天)",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/hoistingCycle")
    public AjaxResult getHoistingCycle(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO)
    {
        List<WsTowerCraneProjectMonitoringDetailsDTO> list = wsTowerCraneEquipmentMonitorService.selectHoistingCycle(wsTowerCraneProjectMonitoringDetailsDTO);
        return AjaxResult.success(list);
    }

    /**
     * 塔机监控 - 项目 -实时监控-页面
     * 工作记录
     * towerCraneId
     */
    @ApiOperation("实时监控-按项目-工作记录")
    @ApiImplicitParam(name = "wsTowerCraneProjectMonitoringDetailsDTO", value = "实时监控-按项目-工作记录:towerCraneId ; days",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/equipmentWorkCycleRecord")
    public AjaxResult getEquipmentWorkCycleRecord(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO)
    {
        List<WsTowerCraneProjectMonitoringDetailsDTO> list = wsTowerCraneEquipmentMonitorService.selectEquipmentWorkCycleRecord(wsTowerCraneProjectMonitoringDetailsDTO);
        return AjaxResult.success(list);
    }

    /**
     * 塔机监控 - 项目 -实时监控-页面
     * 工作记录详情
     * towerCraneId
     */
    @ApiOperation("实时监控-按项目-工作记录详情")
    @ApiImplicitParam(name = "wsTowerCraneProjectMonitoringDetailsDTO", value = "实时监控-按项目-工作记录详情:towerCraneId ; searchTime",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/equipmentWorkCycleDetails")
    public TableDataInfo getEquipmentWorkCycleDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO)
    {
        startPage();
        List<WsTowerCraneProjectMonitoringDetailsDTO> list = wsTowerCraneEquipmentMonitorService.selectEquipmentWorkCycleDetails(wsTowerCraneProjectMonitoringDetailsDTO);
        return getDataTable(list);
    }

    /**
     * 塔机监控 - 项目 -实时监控-页面
     * 导出工作记录详情-按项目
     */
    @ApiOperation("实时监控-按项目-出工作记录详情")
    @ApiImplicitParam(name = "wsTowerCraneProjectMonitoringDetailsDTO", value = "实时监控-按项目-工作记录详情:必传：towerCraneId ; searchTime",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:export')")
    @Log(title = "工作详情", businessType = BusinessType.EXPORT)
    @GetMapping("/exportEquipmentWorkCycleDetails")
    public AjaxResult exportEquipmentWorkCycleDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO)
    {
        List<WsTowerCraneProjectMonitoringDetailsDTO> list = wsTowerCraneEquipmentMonitorService.selectEquipmentWorkCycleDetails(wsTowerCraneProjectMonitoringDetailsDTO);
        ExcelUtil<WsTowerCraneProjectMonitoringDetailsDTO> util = new ExcelUtil<WsTowerCraneProjectMonitoringDetailsDTO>(WsTowerCraneProjectMonitoringDetailsDTO.class);
        return util.exportExcel(list, "工作记录");
    }

    /**
     * 塔机监控 - 项目 -实时监控-页面
     * 司机历史数据
     * towerCraneId
     * driversName
     * searchTime
     */
    @ApiOperation("实时监控-按项目-司机历史数据")
    @ApiImplicitParam(name = "wsTowerCraneProjectMonitoringDetailsDTO", value = "实时监控-按项目-司机历史数据:towerCraneId ; driversName；searchTime",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/equipmentWorkDriverDetails")
    public TableDataInfo getEquipmentWorkDriverDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO)
    {
        startPage();
        List<WsTowerCraneProjectMonitoringDetailsDTO> list = wsTowerCraneEquipmentMonitorService.selectEquipmentWorkDriverDetails(wsTowerCraneProjectMonitoringDetailsDTO);
        return getDataTable(list);
    }


    /**
     * 塔机监控 - 项目 -实时监控-页面
     * 告警记录；柱状图
     * towerCraneId
     */
    @ApiOperation("实时监控-按项目-告警记录：柱状图")
    @ApiImplicitParam(name = "wsTowerCraneProjectMonitoringDetailsDTO", value = "实时监控-按项目-告警记录：柱状图 :towerCraneId",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/equipmentWorkAlarmDetails")
    public AjaxResult getEquipmentWorkAlarmDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO)
    {
        List<WsTowerCraneProjectMonitoringDetailsDTO> list = wsTowerCraneEquipmentMonitorService.selectEquipmentWorkAlarmDetails(wsTowerCraneProjectMonitoringDetailsDTO);
        return AjaxResult.success(list);
    }

    /**
     * 塔机监控 - 项目 -实时监控-页面
     * 告警记录：今日告警 环形图
     * towerCraneId
     */
    @ApiOperation("实时监控-按项目-告警记录：今日告警 环形图")
    @ApiImplicitParam(name = "wsTowerCraneProjectMonitoringDetailsDTO", value = "实时监控-按项目-告警记录：今日告警 环形图 :towerCraneId",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/equipmentWorkWarningTodayDetails")
    public AjaxResult getEquipmentWorkWarningTodayDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO)
    {
        wsTowerCraneProjectMonitoringDetailsDTO = wsTowerCraneEquipmentMonitorService.selectEquipmentWorkWarningTodayDetails(wsTowerCraneProjectMonitoringDetailsDTO);
        return AjaxResult.success(wsTowerCraneProjectMonitoringDetailsDTO);
    }

    /*项目-监控统计：7天、30天、自定义*/
    /**
     * 项目监控统计-按项目-7-30-自定义
     * 司机不良操作-项目级监控统计
     */
    @ApiOperation("司机不良操作-项目级监控统计")
    @ApiImplicitParam(name = "wsTowerCraneMonitoringStatisticsDTO", value = "司机不良操作-项目级监控统计:projectId，days，statisticsTime",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/projectBadOperationDriver")
    public AjaxResult getProjectBadOperationDriver(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO)
    {
        List<WsTowerCraneMonitoringStatisticsDTO> list = wsTowerCraneEquipmentMonitorService.selectBadOperationDrive(wsTowerCraneMonitoringStatisticsDTO);
        return AjaxResult.success(list);
    }

    /**
     * 项目监控统计-按项目-7-30-自定义
     * 设备违章-项目级监控统计
     */
    @ApiOperation("设备违章-项目级监控统计")
    @ApiImplicitParam(name = "wsTowerCraneMonitoringStatisticsDTO", value = "设备违章-项目级监控统计：projectId，days，conditionsDate",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/equipmentViolationProject")
    public AjaxResult getEquipmentViolationProject(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO)
    {
        List<WsTowerCraneMonitoringStatisticsDTO> list = wsTowerCraneEquipmentMonitorService.selectEquipmentViolation(wsTowerCraneMonitoringStatisticsDTO);
        return AjaxResult.success(list);
    }

    /**
     * 项目监控统计-按项目-7-30-自定义
     * 设备工作记录
     */
    @ApiOperation("设备工作记录-项目级监控统计")
    @ApiImplicitParam(name = "wsTowerCraneMonitoringStatisticsDTO", value = "设备工作记录-项目级监控统计：projectId，days，conditionsDate",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/equipmentWorkRecordProject")
    public AjaxResult getEquipmentWorkRecordProject(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO)
    {
        List<WsTowerCraneMonitoringStatisticsDTO> list = wsTowerCraneEquipmentMonitorService.selectEquipmentWorkRecord(wsTowerCraneMonitoringStatisticsDTO);
        return AjaxResult.success(list);
    }


    /**
     * 项目监控统计-按项目-7-30-自定义
     * 违章详情-项目级监控统计
     */
    @ApiOperation("违章详情-项目级监控统计")
    @ApiImplicitParam(name = "wsTowerCraneProjectRealTimeMonitoringDTO", value = "违章详情-项目级监控统计:projectId，days，conditionsDate,equipmentName",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/detailsProjectViolation")
    public TableDataInfo getDetailsProjectViolation(WsTowerCraneProjectRealTimeMonitoringDTO wsTowerCraneProjectRealTimeMonitoringDTO)
    {
        startPage();
        List<WsTowerCraneProjectRealTimeMonitoringDTO> list = wsTowerCraneEquipmentMonitorService.selectDetailsProjectViolation(wsTowerCraneProjectRealTimeMonitoringDTO);
        return getDataTable(list);
    }

    /**
     * 项目监控统计-按项目-7-30-自定义
     * 导出违章详情-项目级监控统计
     */
    @ApiOperation("导出违章详情-项目级监控统计")
    @ApiImplicitParam(name = "wsTowerCraneProjectRealTimeMonitoringDTO", value = "导出违章详情-项目级监控统计:projectId，days，conditionsDate,equipmentName",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:export')")
    @Log(title = "违章详情", businessType = BusinessType.EXPORT)
    @GetMapping("/illegalExportdetailsProjectViolation")
    public AjaxResult illegalExportdetailsProjectViolation(WsTowerCraneProjectRealTimeMonitoringDTO wsTowerCraneProjectRealTimeMonitoringDTO)
    {
        List<WsTowerCraneProjectRealTimeMonitoringDTO> list = wsTowerCraneEquipmentMonitorService.selectDetailsProjectViolation(wsTowerCraneProjectRealTimeMonitoringDTO);
        ExcelUtil<WsTowerCraneProjectRealTimeMonitoringDTO> util = new ExcelUtil<WsTowerCraneProjectRealTimeMonitoringDTO>(WsTowerCraneProjectRealTimeMonitoringDTO.class);
        return util.exportExcel(list, "违章详情");
    }

    /**
     * 项目监控统计-按项目-7-30-自定义
     * 工作详情-项目级监控统计
     */
    @ApiOperation("工作详情-项目级监控统计")
    @ApiImplicitParam(name = "wsTowerCraneEquipmentWorkDetailsDTO", value = "工作详情-项目级监控统计:projectId，days，conditionsDate,equipmentName",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:query')")
    @GetMapping(value = "/projectDetailsQuery")
    public TableDataInfo getProjectDetailsQuery(WsTowerCraneEquipmentWorkDetailsDTO wsTowerCraneEquipmentWorkDetailsDTO)
    {
        startPage();
        List<WsTowerCraneEquipmentWorkDetailsDTO> list = wsTowerCraneEquipmentMonitorService.selectProjectDetailsQuery(wsTowerCraneEquipmentWorkDetailsDTO);
        return getDataTable(list);
    }

    /**
     * 项目监控统计-按项目-7-30-自定义
     * 导出工作详情-项目级监控统计
     */
    @ApiOperation("导出工作详情-项目级监控统计")
    @ApiImplicitParam(name = "wsTowerCraneEquipmentWorkDetailsDTO", value = "导出工作详情-项目级监控统计:projectId，days，conditionsDate,equipmentName",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towerCraneEquipmentMonitor:export')")
    @Log(title = "工作详情", businessType = BusinessType.EXPORT)
    @GetMapping("/exportProjectDetailsQuery")
    public AjaxResult exportProjectDetailsQuery(WsTowerCraneEquipmentWorkDetailsDTO wsTowerCraneEquipmentWorkDetailsDTO)
    {
        List<WsTowerCraneEquipmentWorkDetailsDTO> list = wsTowerCraneEquipmentMonitorService.selectProjectDetailsQuery(wsTowerCraneEquipmentWorkDetailsDTO);
        ExcelUtil<WsTowerCraneEquipmentWorkDetailsDTO> util = new ExcelUtil<WsTowerCraneEquipmentWorkDetailsDTO>(WsTowerCraneEquipmentWorkDetailsDTO.class);
        return util.exportExcel(list, "工作详情");
    }

}
