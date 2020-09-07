package com.jlkj.project.ws.controller;

import java.util.List;

import io.swagger.annotations.Api;
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
import com.jlkj.project.ws.domain.WsProjectEquipment;
import com.jlkj.project.ws.service.IWsProjectEquipmentService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 工程设备绑定 Controller
 *
 * @author pyy
 * @date 2020-07-02
 */
@Api(tags = "工程设备绑定接口")
@RestController
@RequestMapping("/ws/project/equipment")
public class WsProjectEquipmentController extends BaseController
{
    @Autowired
    private IWsProjectEquipmentService wsProjectEquipmentService;

    /**
     * 查询工程设备绑定 列表
     */
    @ApiOperation("动态带条件查询所有工程设备绑定")
    @PreAuthorize("@ss.hasPermi('ws:project/equipment:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsProjectEquipment wsProjectEquipment)
    {
        startPage();
        List<WsProjectEquipment> list = wsProjectEquipmentService.selectWsProjectEquipmentList(wsProjectEquipment);
        return getDataTable(list);
    }

    /**
     * 获取工程设备绑定 详细信息
     */
    @ApiOperation("查询单个工程设备绑定信息")
    @PreAuthorize("@ss.hasPermi('ws:project/equipment:select')")
    @GetMapping(value = "/select/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsProjectEquipmentService.selectWsProjectEquipmentById(id));
    }

    /**
     * 查询是否存在工程设备绑定信息
     */
    @ApiOperation("查询是否存在工程设备绑定信息")
    @PreAuthorize("@ss.hasPermi('ws:project/equipment:isExist')")
    @GetMapping(value = "/isExist")
    public AjaxResult isExist(WsProjectEquipment wsProjectEquipment)
    {
        boolean result = wsProjectEquipmentService.isExistSerialNumber(wsProjectEquipment);
        if (result){
            return AjaxResult.success("设备编号不存在,可以添加");
        }
         return AjaxResult.error("设备编号已存在");
    }

    /**
     * 获取工程设备在线状态
     */
    @ApiOperation("获取工程设备在线状态")
    @PreAuthorize("@ss.hasPermi('ws:project/equipment:query')")
    @PostMapping(value = "/query/onlineStatus")
    public AjaxResult getStatus(@RequestBody WsProjectEquipment wsProjectEquipment)
    {
        WsProjectEquipment projectEquipment = wsProjectEquipmentService.selectWsProjectEquipmentOnlineStatus(wsProjectEquipment);
        if (projectEquipment == null){
            return AjaxResult.error("系统异常，请联系管理员");
        }
        return AjaxResult.success(projectEquipment);
    }

    /**
     * 新增工程设备绑定 
     */
    @ApiOperation("新增工程设备绑定")
    @PreAuthorize("@ss.hasPermi('ws:project/equipment:add')")
    @Log(title = "工程设备绑定 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsProjectEquipment wsProjectEquipment)
    {
        int result = wsProjectEquipmentService.insertWsProjectEquipment(wsProjectEquipment);
        if (result == 0){
            return AjaxResult.error("设备编号有误");
        }
        if (result == 2){
            return AjaxResult.error("设备编号已添加");
        }
        return toAjax(result);
    }

    /**
     * 修改工程设备绑定 
     */
    @ApiOperation("修改工程设备绑定")
    @PreAuthorize("@ss.hasPermi('ws:project/equipment:edit')")
    @Log(title = "工程设备绑定 ", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody WsProjectEquipment wsProjectEquipment)
    {
        return toAjax(wsProjectEquipmentService.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment));
    }

    /**
     *工程设备转场
     */
    @ApiOperation("工程设备转场")
    @PreAuthorize("@ss.hasPermi('ws:project/equipment:change')")
    @Log(title = "工程设备绑定 ", businessType = BusinessType.UPDATE)
    @PutMapping("/project/change")
    public AjaxResult change(@RequestBody WsProjectEquipment wsProjectEquipment)
    {
        int result = wsProjectEquipmentService.updateProjectNameBySerialNumber(wsProjectEquipment);
        if (result == 0){
            return AjaxResult.error("设备已被使用，请联系客户删除");
        }
        return toAjax(result);
    }



    /**
     * 删除工程设备绑定 
     */
    @ApiOperation("删除工程设备绑定，未绑定才能删除")
    @PreAuthorize("@ss.hasPermi('ws:project/equipment:delete')")
    @Log(title = "工程设备绑定 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/delete/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        int result = wsProjectEquipmentService.deleteWsProjectEquipmentById(id);
        if (result == 0){
            return AjaxResult.error("设备已被使用，请联系客户删除");
        }
        return toAjax(result);
    }
}
