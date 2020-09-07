package com.jlkj.project.ws.intelligence.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.intelligence.domain.WsElevatorMinitorDevice;
import com.jlkj.project.ws.intelligence.service.IWsElevatorMinitorDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 施工电梯监控设备绑定管理Controller
 *
 * @author liujie
 * @date 2020-07-03
 */
@Api("施工电梯监控设备绑定管理")
@RestController
@RequestMapping("/ws/elevatorMinitorDevice")
public class WsElevatorMinitorDeviceController extends BaseController
{
    @Autowired
    private IWsElevatorMinitorDeviceService wsElevatorMinitorDeviceService;

    /**
     * 查询施工电梯监控设备绑定管理列表
     */
    @ApiOperation("查询施工电梯监控设备绑定管理列表")
    //@PreAuthorize("@ss.hasPermi('ws:elevatorMinitorDevice:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsElevatorMinitorDevice wsElevatorMinitorDevice)
    {
        startPage();
        List<WsElevatorMinitorDevice> list = wsElevatorMinitorDeviceService.selectWsElevatorMinitorDeviceList(wsElevatorMinitorDevice);
        return getDataTable(list);
    }

    /**
     * 导出施工电梯监控设备绑定管理列表
     */
    //@PreAuthorize("@ss.hasPermi('ws:elevatorMinitorDevice:export')")
    @Log(title = "施工电梯监控设备绑定管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsElevatorMinitorDevice wsElevatorMinitorDevice)
    {
        List<WsElevatorMinitorDevice> list = wsElevatorMinitorDeviceService.selectWsElevatorMinitorDeviceList(wsElevatorMinitorDevice);
        ExcelUtil<WsElevatorMinitorDevice> util = new ExcelUtil<WsElevatorMinitorDevice>(WsElevatorMinitorDevice.class);
        return util.exportExcel(list, "elevatorMinitorDevice");
    }

    /**
     * 获取施工电梯监控设备绑定管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('ws:elevatorMinitorDevice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsElevatorMinitorDeviceService.selectWsElevatorMinitorDeviceById(id));
    }

    /**
     * 新增施工电梯监控设备绑定管理
     */
    @PreAuthorize("@ss.hasPermi('ws:elevatorMinitorDevice:add')")
    @Log(title = "施工电梯监控设备绑定管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WsElevatorMinitorDevice wsElevatorMinitorDevice)
    {
        return toAjax(wsElevatorMinitorDeviceService.insertWsElevatorMinitorDevice(wsElevatorMinitorDevice));
    }

    /**
     * 修改施工电梯监控设备绑定管理
     */
    @PreAuthorize("@ss.hasPermi('ws:elevatorMinitorDevice:edit')")
    @Log(title = "施工电梯监控设备绑定管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsElevatorMinitorDevice wsElevatorMinitorDevice)
    {
        return toAjax(wsElevatorMinitorDeviceService.updateWsElevatorMinitorDevice(wsElevatorMinitorDevice));
    }

    /**
     * 删除施工电梯监控设备绑定管理
     */
    //@PreAuthorize("@ss.hasPermi('ws:elevatorMinitorDevice:remove')")
    @Log(title = "施工电梯监控设备绑定管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(wsElevatorMinitorDeviceService.deleteWsElevatorMinitorDeviceById(id));
    }
}
