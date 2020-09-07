package com.jlkj.project.ws.intelligence.controller;

import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.dto.WsTowerCraneProjectMonitoringDetailsDTO;
import com.jlkj.project.ws.intelligence.dto.WsDustMonitoringDetailsDTO;
import com.jlkj.project.ws.intelligence.service.IWsDustMonitoringIntelligentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 智能监测：扬尘智能监测 Controller
 *
 * @author gaowei
 * @date 2020-07-09
 */
@RestController
@RequestMapping("/ws/dustMonitoringIntelligent")
public class WsDustMonitoringIntelligentController extends BaseController
{
    @Autowired
    private IWsDustMonitoringIntelligentService wsDustMonitoringIntelligentService;

    /**
     * 查询扬尘监测列表-企业
     */
    @ApiOperation("查询扬尘监测列表-企业")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项：条件：projectId",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        startPage();
        List<WsDustMonitoringDetailsDTO> list = wsDustMonitoringIntelligentService.selectWsDustMonitoringDetailsList(wsDustMonitoringDetailsDTO);
        return getDataTable(list);
    }

    /**
     * 查询扬尘监测列表中 无监测项目数-企业
     */
    @ApiOperation("查询扬尘监测列表中:无监测项目数-企业")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:list')")
    @GetMapping(value = "/nothingMonitoring")
    public AjaxResult getNothingMonitoring(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        startPage();
        wsDustMonitoringDetailsDTO = wsDustMonitoringIntelligentService.selectNothingMonitoring(wsDustMonitoringDetailsDTO);
        return AjaxResult.success(wsDustMonitoringDetailsDTO);
    }

    /**
     * 查询无监测项目列表数据-企业
     */
    @ApiOperation("查询无监测项目列表数据-企业")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:list')")
    @GetMapping(value = "/nothingMonitoringList")
    public TableDataInfo getNothingMonitoringList(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        startPage();
        List<WsDustMonitoringDetailsDTO> list = wsDustMonitoringIntelligentService.selectNothingMonitoringList(wsDustMonitoringDetailsDTO);
        return getDataTable(list);
    }

    /**
     * 根据项目查询监测设备-企业-项目
     */
    @ApiOperation("根据项目查询监测设备-企业-项目")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项：projectId",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:list')")
    @GetMapping(value = "/projectIdMonitoringEquipmentNo")
    public TableDataInfo getProjectIdMonitoringEquipmentNo(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        startPage();
        List<WsDustMonitoringDetailsDTO> list = wsDustMonitoringIntelligentService.selectProjectIdMonitoringEquipmentNo(wsDustMonitoringDetailsDTO);
        return getDataTable(list);
    }

    /**
     * 根据监测视频Id查询监测设备-监测视频-企业-项目
     * 监测视频
     */
    @ApiOperation("根据监测视频Id查询监测设备-监测视频-企业-项目")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项：videoEquipmentId",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:query')")
    @GetMapping(value = "/monitoringVideoDetails")
    public AjaxResult getMonitoringVideoDetails(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        if(wsDustMonitoringDetailsDTO.getVideoEquipmentId() != null){
            wsDustMonitoringDetailsDTO = wsDustMonitoringIntelligentService.selectMonitoringVideoDetails(wsDustMonitoringDetailsDTO);
            return AjaxResult.success(wsDustMonitoringDetailsDTO);
        }else{
            return AjaxResult.error("监测点未绑定视频!");
        }
    }

    /**
     * 根据监测设备查询环境监测数据-企业-项目
     */
    @ApiOperation("根据监测设备查询环境监测数据-企业")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:list')")
    @GetMapping(value = "/monitoringEquipmentNoDust")
    public AjaxResult getMonitoringEquipmentNoDust(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        startPage();
        wsDustMonitoringDetailsDTO = wsDustMonitoringIntelligentService.selectMonitoringEquipmentNoDust(wsDustMonitoringDetailsDTO);
        return AjaxResult.success(wsDustMonitoringDetailsDTO);
    }


    /**
     * 根据监测设备查询监测统计：近7天扬尘变化情况-企业-项目
     */
    @ApiOperation("根据监测设备查询监测统计：近7天扬尘变化情况-企业")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:list')")
    @GetMapping(value = "/dustSevenDaySituation")
    public TableDataInfo getDustSevenDaySituation(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        startPage();
        List<WsDustMonitoringDetailsDTO> list = wsDustMonitoringIntelligentService.selectDustSevenDaySituation(wsDustMonitoringDetailsDTO);
        return getDataTable(list);
    }

    /**
     * 根据监测设备查询监测统计：近7天噪音变化情况-企业-项目
     */
    @ApiOperation("根据监测设备查询监测统计：近7天噪音变化情况--企业")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:list')")
    @GetMapping(value = "/noiseSevenDaySituation")
    public TableDataInfo getNoiseSevenDaySituation(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        startPage();
        List<WsDustMonitoringDetailsDTO> list = wsDustMonitoringIntelligentService.selectNoiseSevenDaySituation(wsDustMonitoringDetailsDTO);
        return getDataTable(list);
    }


    /**
     * 根据监测设备查询监测统计：扬尘 pm2.5,pm10-企业-项目
     */
    @ApiOperation("根据监测设备查询监测统计：扬尘 pm2.5,pm10（每日）-企业")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项：默认当天日期条件：dustDate 年月日 ；监测设备：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:list')")
    @GetMapping(value = "/sameDayDustPm25OrDustPm10")
    public TableDataInfo getSameDayDustPm25OrDustPm10(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        startPage();
        List<WsDustMonitoringDetailsDTO> list = wsDustMonitoringIntelligentService.selectSameDayDustPm25OrDustPm10(wsDustMonitoringDetailsDTO);
        return getDataTable(list);
    }

    /**
     * 根据监测设备查询监测统计：扬尘 pm2.5,pm10-企业-项目
     */
    @ApiOperation("根据监测设备查询监测统计：扬尘 pm2.5,pm10（每月）-企业")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项: 默认当月日期条件：dustDate  年月； 监测设备：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:list')")
    @GetMapping(value = "/monthlyDayDustPm25OrDustPm10")
    public TableDataInfo getMonthlyDayDustPm25OrDustPm10(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        startPage();
        List<WsDustMonitoringDetailsDTO> list = wsDustMonitoringIntelligentService.selectMonthlyDayDustPm25OrDustPm10(wsDustMonitoringDetailsDTO);
        return getDataTable(list);
    }

    /**
     * 根据监测设备查询监测统计：噪音-企业-项目
     */
    @ApiOperation("根据监测设备查询监测统计：噪音（每日）-企业")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项：默认当天日期条件：dustDate 年月日 ；监测设备：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:list')")
    @GetMapping(value = "/sameDayDustStrepitus")
    public TableDataInfo getSameDayDustStrepitus(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        startPage();
        List<WsDustMonitoringDetailsDTO> list = wsDustMonitoringIntelligentService.selectSameDayDustStrepitus(wsDustMonitoringDetailsDTO);
        return getDataTable(list);
    }

    /**
     * 根据监测设备查询监测统计：噪音-企业-项目
     */
    @ApiOperation("根据监测设备查询监测统计：噪音（每月）-企业")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项: 默认当月日期条件：dustDate  年月； 监测设备：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:list')")
    @GetMapping(value = "/monthlyDayDustStrepitus")
    public TableDataInfo getMonthlyDayDustStrepitus(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        startPage();
        List<WsDustMonitoringDetailsDTO> list = wsDustMonitoringIntelligentService.selectMonthlyDayDustStrepitus(wsDustMonitoringDetailsDTO);
        return getDataTable(list);
    }

    /**
     * 根据监测设备查询监测统计：温度-企业-项目
     */
    @ApiOperation("根据监测设备查询监测统计：温度（每日）-企业")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项：默认当天日期条件：dustDate 年月日 ；监测设备：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:list')")
    @GetMapping(value = "/sameDayDustTemperature")
    public TableDataInfo getSameDayDustTemperature(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        startPage();
        List<WsDustMonitoringDetailsDTO> list = wsDustMonitoringIntelligentService.selectSameDayDustTemperature(wsDustMonitoringDetailsDTO);
        return getDataTable(list);
    }

    /**
     * 根据监测设备查询监测统计：温度-企业-项目
     */
    @ApiOperation("根据监测设备查询监测统计：温度（每月）-企业")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项: 默认当月日期条件：dustDate  年月； 监测设备：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:list')")
    @GetMapping(value = "/monthlyDayDustTemperature")
    public TableDataInfo getMonthlyDayDustTemperature(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        startPage();
        List<WsDustMonitoringDetailsDTO> list = wsDustMonitoringIntelligentService.selectMonthlyDayDustTemperature(wsDustMonitoringDetailsDTO);
        return getDataTable(list);
    }


    /**
     * 根据监测设备查询监测统计：风速-企业-项目
     */
    @ApiOperation("根据监测设备查询监测统计：风速（每日）-企业")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项：默认当天日期条件：dustDate 年月日 ；监测设备：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:list')")
    @GetMapping(value = "/sameDayDustWindSpeed")
    public TableDataInfo getSameDayDustWindSpeed(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        startPage();
        List<WsDustMonitoringDetailsDTO> list = wsDustMonitoringIntelligentService.selectSameDayDustWindSpeed(wsDustMonitoringDetailsDTO);
        return getDataTable(list);
    }

    /**
     * 根据监测设备查询监测统计：风速-企业-项目
     */
    @ApiOperation("根据监测设备查询监测统计：风速（每月）-企业")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项: 默认当月日期条件：dustDate  年月； 监测设备：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:list')")
    @GetMapping(value = "/monthlyDayDustWindSpeed")
    public TableDataInfo getMonthlyDayDustWindSpeed(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        startPage();
        List<WsDustMonitoringDetailsDTO> list = wsDustMonitoringIntelligentService.selectMonthlyDayDustWindSpeed(wsDustMonitoringDetailsDTO);
        return getDataTable(list);
    }

    /**
     * 根据监测设备查询监测统计：pm10,噪音，温度，风速 - 每日
     */
    @ApiOperation("根据监测设备查询监测统计：pm10,噪音，温度，风速")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项: 默认当日日期条件：dustDate  年月日； 监测设备：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:list')")
    @GetMapping(value = "/averageDustMonitoringDetailsDay")
    public AjaxResult getAverageDustMonitoringDetailsDay(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        startPage();
        wsDustMonitoringDetailsDTO = wsDustMonitoringIntelligentService.selectAverageDustMonitoringDetailsDay(wsDustMonitoringDetailsDTO);
        return AjaxResult.success(wsDustMonitoringDetailsDTO);
    }

    /**
     * 根据监测设备查询监测统计：pm10,噪音，温度，风速 - 每月
     */
    @ApiOperation("根据监测设备查询监测统计：pm10,噪音，温度，风速")
    @ApiImplicitParam(name = "wsDustMonitoringDetailsDTO", value = "扬尘监测管理实体项: 默认当月日期条件：dustDate  年月； 监测设备：monitoringEquipmentNo",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringIntelligent:list')")
    @GetMapping(value = "/averageDustMonitoringDetailsMonth")
    public AjaxResult getAverageDustMonitoringDetailsMonth(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO)
    {
        startPage();
        wsDustMonitoringDetailsDTO = wsDustMonitoringIntelligentService.selectAverageDustMonitoringDetailsMonth(wsDustMonitoringDetailsDTO);
        return AjaxResult.success(wsDustMonitoringDetailsDTO);
    }

}
