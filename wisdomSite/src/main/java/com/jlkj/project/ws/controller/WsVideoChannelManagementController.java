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
import com.jlkj.project.ws.domain.WsVideoChannelManagement;
import com.jlkj.project.ws.service.IWsVideoChannelManagementService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 视频通道管理Controller
 *
 * @author jlkj
 * @date 2020-07-07
 */
@Api(tags = "视频通道管理接口")
@RestController
@RequestMapping("/ws/video/channel")
public class WsVideoChannelManagementController extends BaseController
{
    @Autowired
    private IWsVideoChannelManagementService wsVideoChannelManagementService;

    /**
     * 查询视频通道管理列表
     */
    @ApiOperation("动态条件查询所有视频通道管理列表")
    @PreAuthorize("@ss.hasPermi('ws:video/channel:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsVideoChannelManagement wsVideoChannelManagement)
    {
        startPage();
        List<WsVideoChannelManagement> list = wsVideoChannelManagementService.selectWsVideoChannelManagementList(wsVideoChannelManagement);
        return getDataTable(list);
    }


    /**
     * 获取视频通道管理详细信息
     */
    @ApiOperation("查询的单个视频通道管理信息")
    @PreAuthorize("@ss.hasPermi('ws:video/channel:select')")
    @GetMapping(value = "/select/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsVideoChannelManagementService.selectWsVideoChannelManagementById(id));
    }

    /**
     * 新增视频通道管理
     */
    @ApiOperation("新增视频通道管理")
    @PreAuthorize("@ss.hasPermi('ws:video/channel:add')")
    @Log(title = "视频通道管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WsVideoChannelManagement wsVideoChannelManagement)
    {
        return toAjax(wsVideoChannelManagementService.insertWsVideoChannelManagement(wsVideoChannelManagement));
    }

    /**
     * 修改视频通道管理
     */
    @ApiOperation("修改视频通道管理")
    @PreAuthorize("@ss.hasPermi('ws:video/channel:edit')")
    @Log(title = "视频通道管理", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody WsVideoChannelManagement wsVideoChannelManagement)
    {
        return toAjax(wsVideoChannelManagementService.updateWsVideoChannelManagement(wsVideoChannelManagement));
    }

    /**
     * 删除视频通道管理
     */
    @ApiOperation("删除视频通道管理")
    @PreAuthorize("@ss.hasPermi('ws:video/channel:remove')")
    @Log(title = "视频通道管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/delete/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(wsVideoChannelManagementService.deleteWsVideoChannelManagementById(id));
    }
}
