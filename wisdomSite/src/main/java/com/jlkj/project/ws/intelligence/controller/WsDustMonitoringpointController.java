package com.jlkj.project.ws.intelligence.controller;

import java.util.List;

import com.jlkj.project.ws.domain.WsProjectEquipment;
import com.jlkj.project.ws.domain.WsVideoChannelManagement;
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
import com.jlkj.project.ws.intelligence.domain.WsDustMonitoringpoint;
import com.jlkj.project.ws.intelligence.service.IWsDustMonitoringpointService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 扬尘监测点视频管理 Controller
 *
 * @author gaowei
 * @date 2020-07-07
 */
@RestController
@RequestMapping("/ws/dustMonitoringpoint")
public class WsDustMonitoringpointController extends BaseController
{
    @Autowired
    private IWsDustMonitoringpointService wsDustMonitoringpointService;

    /**
     * 查询扬尘监测点视频管理 列表
     */
    @ApiOperation("查询扬尘监测点视频管理列表")
    @ApiImplicitParam(name = "wsDustMonitoringpoint", value = "扬尘监测点视频管理实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringpoint:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsDustMonitoringpoint wsDustMonitoringpoint)
    {
        startPage();
        List<WsDustMonitoringpoint> list = wsDustMonitoringpointService.selectWsDustMonitoringpointList(wsDustMonitoringpoint);
        return getDataTable(list);
    }

    /**
     * 查询监测设备（扬尘监测设备）
     */
    @ApiOperation("查询监测设备")
    @ApiImplicitParam(name = "wsProjectEquipment", value = "工程设备绑定实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringpoint:list')")
    @GetMapping("/listDustMonitoring")
    public TableDataInfo listDustMonitoring(WsProjectEquipment wsProjectEquipment)
    {
        startPage();
        List<WsProjectEquipment> list = wsDustMonitoringpointService.selectListDustMonitoring(wsProjectEquipment);
        return getDataTable(list);
    }


    /**
     * 新增扬尘监测点视频管理 
     */
    @ApiOperation("新增扬尘监测点视频管理")
    @ApiImplicitParam(name = "wsDustMonitoringpoint", value = "扬尘监测点视频管理实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringpoint:add')")
    @Log(title = "扬尘监测点视频管理 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsDustMonitoringpoint wsDustMonitoringpoint)
    {
        return toAjax(wsDustMonitoringpointService.insertWsDustMonitoringpoint(wsDustMonitoringpoint));
    }

    /**
     * 获取扬尘监测点视频管理 详细信息
     */
    @ApiOperation("获取扬尘监测点视频管理 详细信息")
    @ApiImplicitParam(name = "主键", value = "id）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringpoint:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsDustMonitoringpointService.selectWsDustMonitoringpointById(id));
    }

    /**
     * 修改扬尘监测点视频管理 
     */
    @ApiOperation("修改扬尘监测点视频管理")
    @ApiImplicitParam(name = "wsDustMonitoringpoint", value = "扬尘监测点视频管理实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringpoint:edit')")
    @Log(title = "扬尘监测点视频管理 ", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody WsDustMonitoringpoint wsDustMonitoringpoint)
    {
        return toAjax(wsDustMonitoringpointService.updateWsDustMonitoringpoint(wsDustMonitoringpoint));
    }

    /**
     * 启用禁用
     */
    @ApiOperation("启用禁用")
    @ApiImplicitParam(name = "wsDustMonitoringpoint", value = "扬尘监测点视频管理实体项）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringpoint:edit')")
    @Log(title = "启用禁用 ", businessType = BusinessType.UPDATE)
    @PutMapping("/enableDisable/{isDelete}/{id}")
    public AjaxResult enableDisable(@PathVariable("isDelete") String isDelete,@PathVariable("id") Integer id)
    {
        return toAjax(wsDustMonitoringpointService.enableDisableWsDustMonitoringpoint(isDelete,id));
    }

    /**
     * 删除扬尘监测点视频管理 
     */
    @ApiOperation("删除扬尘监测点视频管理")
    @ApiImplicitParam(name = "wsDustMonitoringpoint", value = "扬尘监测点视频管理实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringpoint:remove')")
    @Log(title = "扬尘监测点视频管理 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(wsDustMonitoringpointService.deleteWsDustMonitoringpointById(id));
    }

    /**
     * 查询扬尘视频监测设备（扬尘视频监测设备）
     */
    @ApiOperation("查询扬尘视频监测设备")
    @ApiImplicitParam(name = "wsVideoChannelManagement", value = "视频通道管理",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringpoint:list')")
    @GetMapping("/listHookVideo")
    public TableDataInfo listHookVideo(WsVideoChannelManagement wsVideoChannelManagement)
    {
        startPage();
        List<WsVideoChannelManagement> list = wsDustMonitoringpointService.listHookVideo(wsVideoChannelManagement);
        return getDataTable(list);
    }


    /**
     * 新增修改扬尘视频监测设备是否重复
     */
    @ApiOperation("新增修改扬尘视频监测设备是否重复：返回list.size() > 0 就重复")
    @ApiImplicitParam(name = "wsMonitoringpointVideo", value = "项目塔吊设备管理实体项：扬尘视频id：videoEquipmentId",required = false)
    @PreAuthorize("@ss.hasPermi('ws:dustMonitoringpoint:list')")
    @GetMapping("/listHookVideoRepeat")
    public TableDataInfo listHookVideoRepeat(WsDustMonitoringpoint wsDustMonitoringpoint)
    {
        startPage();
        List<WsDustMonitoringpoint> list = wsDustMonitoringpointService.listHookVideoRepeat(wsDustMonitoringpoint);
        return getDataTable(list);
    }

}
