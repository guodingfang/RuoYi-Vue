package com.jlkj.project.ws.intelligence.controller;

import java.util.List;

import com.jlkj.project.ws.domain.WsProjectEquipment;
import com.jlkj.project.ws.domain.WsProjectSitePartition;
import com.jlkj.project.ws.intelligence.domain.WsElecBoxMonitorData;
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
import com.jlkj.project.ws.intelligence.domain.WsElectricityMonitorManage;
import com.jlkj.project.ws.intelligence.service.IWsElectricityMonitorManageService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 电能监测设备管理 Controller
 *
 * @author gaowei
 * @date 2020-07-14
 */
@Api(value = "电能监测设备管理",tags = "电能监测设备管理页面")
@RestController
@RequestMapping("/ws/electricityMonitorManage")
public class WsElectricityMonitorManageController extends BaseController
{
    @Autowired
    private IWsElectricityMonitorManageService wsElectricityMonitorManageService;

    /**
     * 电能监测设备管理 列表
     */
    @ApiOperation("配置中心：电能监测设备管理 列表")
    @ApiImplicitParam(name = "wsElectricityMonitorManage", value = "电能监测设备管理实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:electricityMonitorManage:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsElectricityMonitorManage wsElectricityMonitorManage)
    {
        startPage();
        List<WsElectricityMonitorManage> list = wsElectricityMonitorManageService.selectWsElectricityMonitorManageList(wsElectricityMonitorManage);
        return getDataTable(list);
    }


    /**
     * 查询监测设备（电能监测设备）
     */
    @ApiOperation("配置中心：查询电能监测设备")
    @ApiImplicitParam(name = "wsProjectEquipment", value = "工程设备绑定实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:electricityMonitorManage:list')")
    @GetMapping("/listMonitoringEquipment")
    public TableDataInfo listMonitoringEquipment(WsProjectEquipment wsProjectEquipment)
    {
        startPage();
        List<WsProjectEquipment> list = wsElectricityMonitorManageService.listMonitoringEquipment(wsProjectEquipment);
        return getDataTable(list);
    }

    /**
     * 查询安装区域（安装区域）
     */
    @ApiOperation("配置中心：查询安装区域")
    @ApiImplicitParam(name = "wsProjectSitePartition", value = "工程设备绑定实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:electricityMonitorManage:list')")
    @GetMapping("/listProjectSitePartition")
    public TableDataInfo listProjectSitePartition(WsProjectSitePartition wsProjectSitePartition)
    {
        startPage();
        List<WsProjectSitePartition> list = wsElectricityMonitorManageService.listProjectSitePartition(wsProjectSitePartition);
        return getDataTable(list);
    }

    /**
     * 新增电能监测设备管理 
     */
    @ApiOperation("配置中心：新增电能监测设备管理")
    @ApiImplicitParam(name = "wsElectricityMonitorManage", value = "电能监测设备管理实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:electricityMonitorManage:add')")
    @Log(title = "电能监测设备管理 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsElectricityMonitorManage wsElectricityMonitorManage)
    {
        return toAjax(wsElectricityMonitorManageService.insertWsElectricityMonitorManage(wsElectricityMonitorManage));
    }

    /**
     * 获取电能监测设备管理 详细信息
     */
    @ApiOperation("获取电能监测设备管理 详细信息")
    @ApiImplicitParam(name = "主键", value = "id）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:electricityMonitorManage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsElectricityMonitorManageService.selectWsElectricityMonitorManageById(id));
    }

    /**
     * 修改电能监测设备管理 
     */
    @ApiOperation("配置中心：修改电能监测设备管理")
    @ApiImplicitParam(name = "wsElectricityMonitorManage", value = "电能监测设备管理实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:electricityMonitorManage:edit')")
    @Log(title = "电能监测设备管理 ", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody WsElectricityMonitorManage wsElectricityMonitorManage)
    {
        return toAjax(wsElectricityMonitorManageService.updateWsElectricityMonitorManage(wsElectricityMonitorManage));
    }

    /**
     * 启用，禁用
     */
    @ApiOperation("配置中心：启用，禁用 ")
    @ApiImplicitParam(name = "wsElectricityMonitorManage", value = "电能监测设备管理实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:electricityMonitorManage:edit')")
    @Log(title = "电能监测设备管理 ", businessType = BusinessType.UPDATE)
    @PutMapping("/enableDisable")
    public AjaxResult enableDisable(@RequestBody WsElectricityMonitorManage wsElectricityMonitorManage)
    {
        return toAjax(wsElectricityMonitorManageService.updateEnableDisable(wsElectricityMonitorManage));
    }

    /**
     * 删除电能监测设备管理 
     */
    @ApiOperation("配置中心：删除电能监测设备管理")
    @ApiImplicitParam(name = "主键", value = "id",required = false)
    @PreAuthorize("@ss.hasPermi('ws:electricityMonitorManage:remove')")
    @Log(title = "电能监测设备管理 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(wsElectricityMonitorManageService.deleteWsElectricityMonitorManageById(id));
    }


    /**
     * 用电安全设备信息列表
     */
    @ApiOperation("用电安全：设备信息列表")
    @ApiImplicitParam(name = "wsElectricityMonitorManage", value = "电能监测设备管理实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:electricityMonitorManage:list')")
    @GetMapping("/listElectricitySecurity")
    public TableDataInfo listElectricitySecurity(WsElectricityMonitorManage wsElectricityMonitorManage)
    {
        startPage();
        List<WsElectricityMonitorManage> list = wsElectricityMonitorManageService.selectElectricitySecurityList(wsElectricityMonitorManage);
        return getDataTable(list);
    }


    /**
     * 查询用电安全-监测详情-设备信息及实时数据
     */
    @ApiOperation("查询用电安全-监测详情-设备信息及实时数据")
    @ApiImplicitParam(name = "wsElecBoxMonitorData", value = "电箱监测数据信息实体项: 设备编号;电箱监测设备:monitoringEquipment",required = false)
    @PreAuthorize("@ss.hasPermi('ws:electricityMonitorManage:list')")
    @GetMapping(value = "/realTimeMonitoring")
    public AjaxResult getRealTimeMonitoring(WsElecBoxMonitorData wsElecBoxMonitorData)
    {
        startPage();
        wsElecBoxMonitorData = wsElectricityMonitorManageService.selectRealTimeMonitoring(wsElecBoxMonitorData);
        return AjaxResult.success(wsElecBoxMonitorData);
    }


    /**
     * 查询用电安全-监测详情-监测记录
     */
    @ApiOperation("查询用电安全-监测详情-监测记录")
    @ApiImplicitParam(name = "wsElecBoxMonitorData", value = "电箱监测数据信息实体项：monitorType：监测时间类型（1:近24小时；2:近3天；3：近7天），设备编号;电箱监测设备:monitoringEquipment；",required = false)
    @PreAuthorize("@ss.hasPermi('ws:electricityMonitorManage:list')")
    @GetMapping("/listMonitoringRecords")
    public TableDataInfo listMonitoringRecords(WsElecBoxMonitorData wsElecBoxMonitorData)
    {
        startPage();
        List<WsElecBoxMonitorData> list = wsElectricityMonitorManageService.selectMonitoringRecordsList(wsElecBoxMonitorData);
        return getDataTable(list);
    }


    /**
     * 查询能耗监测-用电量信息
     */
    @ApiOperation("查询能耗监测-用电量")
    @ApiImplicitParam(name = "wsElecBoxMonitorData", value = "电箱监测数据信息实体项:参数：projectId",required = false)
    @PreAuthorize("@ss.hasPermi('ws:electricityMonitorManage:list')")
    @GetMapping(value = "/electricityConsumption")
    public AjaxResult getElectricityConsumption(WsElecBoxMonitorData wsElecBoxMonitorData)
    {
        startPage();
        wsElecBoxMonitorData = wsElectricityMonitorManageService.selectElectricityConsumption(wsElecBoxMonitorData);
        return AjaxResult.success(wsElecBoxMonitorData);
    }

    /**
     * 查询能耗监测-能耗分析
     */
    @ApiOperation("查询能耗监测-能耗分析")
    @ApiImplicitParam(name = "wsElecBoxMonitorData", value = "电箱监测数据信息实体项：analysisType：能耗分析类型（1:按日；2:按月；3：按区域）；projectId",required = false)
    @PreAuthorize("@ss.hasPermi('ws:electricityMonitorManage:list')")
    @GetMapping("/listEnergyConsumptionAnalysis")
    public TableDataInfo listEnergyConsumptionAnalysis(WsElecBoxMonitorData wsElecBoxMonitorData)
    {
        startPage();
        List<WsElecBoxMonitorData> list = wsElectricityMonitorManageService.selectEnergyConsumptionAnalysisList(wsElecBoxMonitorData);
        return getDataTable(list);
    }


    /**
     * 查询能耗监测-电表详情
     */
    @ApiOperation("查询能耗监测-电表详情")
    @ApiImplicitParam(name = "wsElectricityMonitorManage", value = "电能监测设备管理实体项：参数：projectId",required = false)
    @PreAuthorize("@ss.hasPermi('ws:electricityMonitorManage:list')")
    @GetMapping("/listMeterDetails")
    public TableDataInfo listMeterDetails(WsElectricityMonitorManage wsElectricityMonitorManage)
    {
        startPage();
        List<WsElectricityMonitorManage> list = wsElectricityMonitorManageService.selectMeterDetailsList(wsElectricityMonitorManage);
        return getDataTable(list);
    }


    /**
     * 查询能耗监测-用电详情
     */
    @ApiOperation("查询能耗监测-用电详情")
    @ApiImplicitParam(name = "wsElectricityMonitorManage", value = "电能监测设备管理实体项；queryType ：查询类型（1：按日；2：按月）；statisticalPeriod ：统计周期；monitoringEquipment：监测设备编号",required = false)
    @PreAuthorize("@ss.hasPermi('ws:electricityMonitorManage:list')")
    @GetMapping("/listPowerConsumptionDetails")
    public TableDataInfo listPowerConsumptionDetails(WsElectricityMonitorManage wsElectricityMonitorManage)
    {
        startPage();
        List<WsElectricityMonitorManage> list = wsElectricityMonitorManageService.selectPowerConsumptionDetailsList(wsElectricityMonitorManage);
        return getDataTable(list);
    }
}
