package com.jlkj.project.ws.intelligence.controller;

import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.interceptor.annotation.RepeatSubmit;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.project.ws.intelligence.domain.WsVideoMonitoryPointManagement;
import com.jlkj.project.ws.intelligence.service.IWsVideoMonitoryPointManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 视频监控点管理  Controller
 *
 * @author liujie
 * @date 2020-07-02
 */
@Api(tags = "视频监控点管理")
@RestController
@RequestMapping("/ws/video/monitoryPointManagement")
public class WsVideoMonitoryPointManagementController extends BaseController
{
    @Autowired
    private IWsVideoMonitoryPointManagementService wsVideoMonitoryPointManagementService;

    /**
     * 查询视频监控点 列表
     */
    @ApiOperation(value="查询视频监控点列表",response = AjaxResult.class)
   // @ApiImplicitParam(name = "区域",value = "wsVideoMonitoryPointManagement",paramType ="WsVideoMonitoryPointManagement")
    //@PreAuthorize("@ss.hasPermi('ws:videoMonitoryPointManagement:list')")
    @GetMapping("/list")
    public AjaxResult list(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement)
    {
        List<WsVideoMonitoryPointManagement> list = wsVideoMonitoryPointManagementService.selectWsVideoMonitoryPointManagementList(wsVideoMonitoryPointManagement);
        return AjaxResult.success(list);
    }



    /**
     * 查询视频监控点按区域统计列表
     */
    @ApiOperation(value="查询视频监控点按区域统计列表",response = AjaxResult.class)
    // @ApiImplicitParam(name = "区域视频点列表",value = "wsVideoMonitoryPointManagement",paramType ="WsVideoMonitoryPointManagement")
    @GetMapping("/list/zoneStatistics")
    public AjaxResult statisticsVideoListByZone(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement)
    {
        List<WsVideoMonitoryPointManagement> list = wsVideoMonitoryPointManagementService.statisticsVideoListByZone(wsVideoMonitoryPointManagement);
        return AjaxResult.success(list);
    }

    /**
     * 查询项目视频监控点统计列表
     */
    @ApiOperation(value="查询项目视频监控点统计列表",response = AjaxResult.class)
    // @ApiImplicitParam(name = "项目视频点列表",value = "wsVideoMonitoryPointManagement",paramType ="WsVideoMonitoryPointManagement")
    @GetMapping("/list/projectStatistics")
    public AjaxResult statisticsVideoListByProject(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement)
    {
        List<WsVideoMonitoryPointManagement> list = wsVideoMonitoryPointManagementService.statisticsProjectVideoPoints(wsVideoMonitoryPointManagement);
        return AjaxResult.success(list);
    }


    /**
     * 获取视频监控点详细信息
     */
    @PreAuthorize("@ss.hasPermi('ws:videoMonitoryPointManagement:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsVideoMonitoryPointManagementService.selectWsVideoMonitoryPointManagementById(id));
    }

    /**
     * 新增视频监控点
     */
    @RepeatSubmit
    @PreAuthorize("@ss.hasPermi('ws:videoMonitoryPointManagement:add')")
    @Log(title = "区域 ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement)
    {
        return toAjax(wsVideoMonitoryPointManagementService.insertWsVideoMonitoryPointManagement(wsVideoMonitoryPointManagement));
    }

    /**
     * 修改视频监控点
     */
    @RepeatSubmit
    @PreAuthorize("@ss.hasPermi('ws:videoMonitoryPointManagement:edit')")
    @Log(title = "区域 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement)
    {
        return toAjax(wsVideoMonitoryPointManagementService.updateWsVideoMonitoryPointManagement(wsVideoMonitoryPointManagement));
    }

    /**
     * 设置启用状态
     */
    @PreAuthorize("@ss.hasPermi('ws:videoMonitoryPointManagement:enabled')")
    @Log(title = "设置启用状态 ", businessType = BusinessType.UPDATE)
    @PutMapping("/enabled")
    public AjaxResult modifyEnabled( @RequestBody WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement)
    {
        return toAjax(wsVideoMonitoryPointManagementService.updateWsVideoMonitoryPointManagement(wsVideoMonitoryPointManagement));
    }

    /**
     * 删除视频监控点
     */
    @PreAuthorize("@ss.hasPermi('ws:videoMonitoryPointManagement:remove')")
    @Log(title = "区域 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(wsVideoMonitoryPointManagementService.deleteWsVideoMonitoryPointManagementById(id));
    }


    /**
     * 查询视频列表
     */
    @ApiOperation(value="查询视频直播列表",response = AjaxResult.class)
    //@PreAuthorize("@ss.hasPermi('ws:videoMonitoryPointManagement:videoList')")
    @GetMapping("/videoList")
    public AjaxResult videoList(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement)
    {
        List<WsVideoMonitoryPointManagement> list = wsVideoMonitoryPointManagementService.selectWsVideoLiveList(wsVideoMonitoryPointManagement);

        return AjaxResult.success(list);
    }

}
