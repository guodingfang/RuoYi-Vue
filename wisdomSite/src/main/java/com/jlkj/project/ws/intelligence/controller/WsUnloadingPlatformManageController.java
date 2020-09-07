package com.jlkj.project.ws.intelligence.controller;

import java.util.List;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsProjectEquipment;
import com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout;
import com.jlkj.project.ws.intelligence.dto.WsUnloadingPlatformManageDTO;
import com.jlkj.project.ws.intelligence.service.IWsProjectFieldLayoutService;
import com.jlkj.project.ws.service.IWsProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.project.ws.intelligence.domain.WsUnloadingPlatformManage;
import com.jlkj.project.ws.intelligence.service.IWsUnloadingPlatformManageService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 卸料平台设备管理 Controller
 *
 * @author gaowei
 * @date 2020-07-13
 */
@Api(value = "卸料平台设备管理",tags = "卸料平台设备管理页面")
@RestController
@RequestMapping("/ws/unloadingPlatformManage")
public class WsUnloadingPlatformManageController extends BaseController
{
    @Autowired
    private IWsUnloadingPlatformManageService wsUnloadingPlatformManageService;

    @Autowired
    private IWsProjectFieldLayoutService wsProjectFieldLayoutService;

    @Autowired
    private IWsProjectService wsProjectService;

    /**
     * 查询卸料平台设备管理 列表
     */
    @ApiOperation("查询卸料平台设备管理 列表")
    @ApiImplicitParam(name = "wsUnloadingPlatformManage", value = "卸料平台设备管理实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsUnloadingPlatformManage wsUnloadingPlatformManage)
    {
        startPage();
        List<WsUnloadingPlatformManage> list = wsUnloadingPlatformManageService.selectWsUnloadingPlatformManageList(wsUnloadingPlatformManage);
        return getDataTable(list);
    }

    /**
     * 查询监测设备（卸料平台监测设备）
     */
    @ApiOperation("查询卸料平台监测设备")
    @ApiImplicitParam(name = "wsProjectEquipment", value = "工程设备绑定实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:list')")
    @GetMapping("/listMonitoringEquipment")
    public TableDataInfo listMonitoringEquipment(WsProjectEquipment wsProjectEquipment)
    {
        startPage();
        List<WsProjectEquipment> list = wsUnloadingPlatformManageService.listMonitoringEquipment(wsProjectEquipment);
        return getDataTable(list);
    }

    /**
     * 获取项目场布图信息/ 防碰撞设备场布图信息查询
     */
    @ApiOperation("获取项目场布图信息")
    @ApiImplicitParam(name = "wsProjectFieldLayout", value = "场布图实体）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:query')")
    @GetMapping(value = "/fieldLayout")
    public AjaxResult fieldLayout(WsProjectFieldLayout wsProjectFieldLayout)
    {
        startPage();
        wsProjectFieldLayout = wsProjectFieldLayoutService.selectWsProjectFieldLayoutByProjectAndType(wsProjectFieldLayout.getProjectId(),3);
        return AjaxResult.success(wsProjectFieldLayout);
    }

    /**
     * 新增卸料平台设备管理 
     */
    @ApiOperation("新增卸料平台设备管理")
    @ApiImplicitParam(name = "wsUnloadingPlatformManage", value = "卸料平台设备管理 实体项）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:add')")
    @Log(title = "卸料平台设备管理 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsUnloadingPlatformManage wsUnloadingPlatformManage)
    {
        return toAjax(wsUnloadingPlatformManageService.insertWsUnloadingPlatformManage(wsUnloadingPlatformManage));
    }

    /**
     * 获取卸料平台设备管理 详细信息
     */
    @ApiOperation("获取卸料平台设备管理 详细信息")
    @ApiImplicitParam(name = "主键", value = "id）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsUnloadingPlatformManageService.selectWsUnloadingPlatformManageById(id));
    }

    /**
     * 修改卸料平台设备管理 
     */
    @ApiOperation("修改卸料平台设备管理")
    @ApiImplicitParam(name = "wsUnloadingPlatformManage", value = "卸料平台设备管理实体项）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:edit')")
    @Log(title = "卸料平台设备管理 ", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody WsUnloadingPlatformManage wsUnloadingPlatformManage)
    {
        return toAjax(wsUnloadingPlatformManageService.updateWsUnloadingPlatformManage(wsUnloadingPlatformManage));
    }

    /**
     * 启用禁用
     */
    @ApiOperation("启用禁用")
    @ApiImplicitParam(name = "wsUnloadingPlatformManage", value = "卸料平台设备管理实体项）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:edit')")
    @Log(title = "启用禁用 ", businessType = BusinessType.UPDATE)
    @PutMapping("/enableDisable")
    public AjaxResult enableDisable(@RequestBody WsUnloadingPlatformManage wsUnloadingPlatformManage)
    {
        return toAjax(wsUnloadingPlatformManageService.updateWsUnloadingPlatformManages(wsUnloadingPlatformManage));
    }

    /**
     * 上传场布图
     */
    @ApiOperation("上传场布图")
    @ApiImplicitParam(name = "wsProjectFieldLayout", value = "上传场布图实体项 fieldlayoutAddress; id：主键存在修改，没有新增）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:add')")
    @Log(title = "项目塔吊设备管理 ", businessType = BusinessType.INSERT)
    @PostMapping("/addFieldLayout")
    public AjaxResult addFieldLayout(@RequestBody WsProjectFieldLayout wsProjectFieldLayout)
    {
        return toAjax(wsUnloadingPlatformManageService.insertWsProjectFieldLayout(wsProjectFieldLayout));
    }

    /**
     * 场布图设置：添加设备
     */
    @ApiOperation("场布图设置：查询未设置位置设备信息")
    @ApiImplicitParam(name = "wsUnloadingPlatformManage", value = "卸料平台设备管理实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:list')")
    @GetMapping("/listUnloadingPlatformManage")
    public TableDataInfo listUnloadingPlatformManage(WsUnloadingPlatformManage wsUnloadingPlatformManage)
    {
        startPage();
        List<WsUnloadingPlatformManage> list = wsUnloadingPlatformManageService.selectlistUnloadingPlatformManage(wsUnloadingPlatformManage);
        return getDataTable(list);
    }

    /**
     * 场布图设置：保存位置
     */
    @ApiOperation("场布图设置：保存位置")
    @ApiImplicitParam(name = "wsUnloadingPlatformManage", value = "卸料平台设备管理实体项）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:add')")
    @Log(title = "卸料平台设备管理 ", businessType = BusinessType.INSERT)
    @PostMapping("/addPosition")
    public AjaxResult addPosition(@RequestBody WsUnloadingPlatformManage wsUnloadingPlatformManage)
    {
        return toAjax(wsUnloadingPlatformManageService.insertPosition(wsUnloadingPlatformManage));
    }

    /**
     * 删除场布图设备位置
     */
    @ApiOperation("删除场布图设备位置")
    @ApiImplicitParam(name = "wsUnloadingPlatformManage", value = "卸料平台设备管理实体项）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:edit')")
    @Log(title = "删除场布图设备位置 ", businessType = BusinessType.UPDATE)
    @PutMapping("/position/{id}")
    public AjaxResult editPosition(@PathVariable("id") String id)
    {
        return toAjax(wsUnloadingPlatformManageService.editUnloadingPlatformManage(id));
    }

    /**
     * 删除卸料平台设备管理 
     */
    @ApiOperation("删除卸料平台设备管理 ")
    @ApiImplicitParam(name = "主键", value = "id）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:remove')")
    @Log(title = "卸料平台设备管理 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(wsUnloadingPlatformManageService.deleteWsUnloadingPlatformManageById(id));
    }


    /**
     * 查询卸料平台-实时监控-设备数量信息
     */
    @ApiOperation("查询卸料平台-实时监控-设备数量信息")
    @ApiImplicitParam(name = "wsUnloadingPlatformManageDTO", value = "卸料平台-实时监控实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:list')")
    @GetMapping(value = "/realTimeMonitoring")
    public AjaxResult getRealTimeMonitoring(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO)
    {
        startPage();
        wsUnloadingPlatformManageDTO = wsUnloadingPlatformManageService.selectRealTimeMonitoring(wsUnloadingPlatformManageDTO);
        return AjaxResult.success(wsUnloadingPlatformManageDTO);
    }

    /**
     * 查询卸料平台-实时监控-实时数据信息
     */
    @ApiOperation("查询卸料平台-实时监控-实时数据信息")
    @ApiImplicitParam(name = "wsUnloadingPlatformManageDTO", value = "卸料平台-实时监控实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:list')")
    @GetMapping(value = "/realTimeMonitoringData")
    public TableDataInfo getRealTimeMonitoringData(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO)
    {
        startPage();
        List<WsUnloadingPlatformManageDTO> list = wsUnloadingPlatformManageService.selectRealTimeMonitoringDataList(wsUnloadingPlatformManageDTO);
        return getDataTable(list);
    }


    /**
     * 查询卸料平台-监控详情-设备信息和实时数据信息
     */
    @ApiOperation("查询卸料平台-监控详情-设备信息和实时数据信息")
    @ApiImplicitParam(name = "wsUnloadingPlatformManageDTO", value = "卸料平台-实时监控实体项：监测设备编号：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:list')")
    @GetMapping(value = "/equipmentRealTimeInformation")
    public AjaxResult getEquipmentRealTimeInformation(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO)
    {
        startPage();
        wsUnloadingPlatformManageDTO = wsUnloadingPlatformManageService.selectEquipmentRealTimeInformation(wsUnloadingPlatformManageDTO);
        return AjaxResult.success(wsUnloadingPlatformManageDTO);
    }

    /**
     * 查询卸料平台-监控详情-工作记录-今日
     */
    @ApiOperation("查询卸料平台-监控详情-工作记录-今日")
    @ApiImplicitParam(name = "wsUnloadingPlatformManageDTO", value = "卸料平台-实时监控实体项：监测设备编号：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:list')")
    @GetMapping(value = "/todayWorkRecord")
    public TableDataInfo getTodayWorkRecord(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO)
    {
        startPage();
        List<WsUnloadingPlatformManageDTO> list = wsUnloadingPlatformManageService.selectTodayWorkRecordList(wsUnloadingPlatformManageDTO);
        return getDataTable(list);
    }

    /**
     * 查询卸料平台-监控详情-工作记录-15天
     */
    @ApiOperation("查询卸料平台-监控详情-工作记录-15天")
    @ApiImplicitParam(name = "wsUnloadingPlatformManageDTO", value = "卸料平台-实时监控实体项：监测设备编号：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:list')")
    @GetMapping(value = "/fifteenWorkRecord")
    public TableDataInfo getFifteenWorkRecord(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO)
    {
        startPage();
        List<WsUnloadingPlatformManageDTO> list = wsUnloadingPlatformManageService.selectFifteenWorkRecordList(wsUnloadingPlatformManageDTO);
        return getDataTable(list);
    }

    /**
     * 查询卸料平台-监控详情-告警记录-环形图-今日告警
     */
    @ApiOperation("查询卸料平台-监控详情-告警记录-环形图-今日告警")
    @ApiImplicitParam(name = "wsUnloadingPlatformManageDTO", value = "卸料平台-实时监控实体项：监测设备编号：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:list')")
    @GetMapping(value = "/todayAlarmLog")
    public AjaxResult getTodayAlarmLog(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO)
    {
        startPage();
        wsUnloadingPlatformManageDTO = wsUnloadingPlatformManageService.selectTodayAlarmLog(wsUnloadingPlatformManageDTO);
        return AjaxResult.success(wsUnloadingPlatformManageDTO);
    }

    /**
     * 查询卸料平台-监控详情-告警记录-15天
     */
    @ApiOperation("查询卸料平台-监控详情-告警记录-15天")
    @ApiImplicitParam(name = "wsUnloadingPlatformManageDTO", value = "卸料平台-实时监控实体项：监测设备编号：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:list')")
    @GetMapping(value = "/fifteenAlarmLog")
    public TableDataInfo getFifteenAlarmLog(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO)
    {
        startPage();
        List<WsUnloadingPlatformManageDTO> list = wsUnloadingPlatformManageService.selectFifteenAlarmLogList(wsUnloadingPlatformManageDTO);
        return getDataTable(list);
    }

    /**
     * 查询卸料平台-监控详情-工作记录-更多详情
     */
    @ApiOperation("查询卸料平台-监控详情-工作记录-更多详情")
    @ApiImplicitParam(name = "wsUnloadingPlatformManageDTO", value = "卸料平台-实时监控实体项：监测设备编号：monitoringEquipmentNo，开始时间：startTime，结束时间：endTime",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:list')")
    @GetMapping(value = "/equipmentMoredetails")
    public TableDataInfo getEquipmentMoredetails(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO)
    {
        startPage();
        List<WsUnloadingPlatformManageDTO> list = wsUnloadingPlatformManageService.selectEquipmentMoredetailsList(wsUnloadingPlatformManageDTO);
        return getDataTable(list);
    }


    /**
     * 查询卸料平台-监控详情-工作记录-更多详情
     * 导出历史记录
     */
    @ApiOperation("查询卸料平台-监控详情-工作记录-更多详情导出历史记录")
    @ApiImplicitParam(name = "wsUnloadingPlatformManageDTO", value = "卸料平台-实时监控实体项：监测设备编号：monitoringEquipmentNo，开始时间：startTime，结束时间：endTime",required = false)
    @PreAuthorize("@ss.hasPermi('ws:unloadingPlatformManage:export')")
    @Log(title = "历史记录", businessType = BusinessType.EXPORT)
    @GetMapping("/exportHistoricalData")
    public AjaxResult exportHistoricalData(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO)
    {
        List<WsUnloadingPlatformManageDTO> list = wsUnloadingPlatformManageService.selectEquipmentMoredetailsList(wsUnloadingPlatformManageDTO);
        ExcelUtil<WsUnloadingPlatformManageDTO> util = new ExcelUtil<WsUnloadingPlatformManageDTO>(WsUnloadingPlatformManageDTO.class);
        return util.exportExcel(list, "历史记录");
    }
}
