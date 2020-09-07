package com.jlkj.project.ws.controller;

import java.util.List;

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
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityMeasuredLocation;
import com.jlkj.project.ws.service.IWsEnterpriseMeasuredrealquantityMeasuredLocationService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 实测实量实测部位 Controller
 *
 * @author pyy
 * @date 2020-06-20
 */
@Api(value ="实测实量实测部位接口",tags = "实测实量实测部位页面")
@RestController
@RequestMapping("/ws/location")
public class WsEnterpriseMeasuredrealquantityMeasuredLocationController extends BaseController
{
    @Autowired
    private IWsEnterpriseMeasuredrealquantityMeasuredLocationService wsEnterpriseMeasuredrealquantityMeasuredLocationService;

    /**
     * 查询实测实量实测部位 列表
     */
    @ApiOperation(value = "查询所有实测部位")
  //  @ApiImplicitParam(value = "wsEnterpriseMeasuredrealquantityMeasuredLocation",name = "查询所有实测部位信息",dataType = "WsEnterpriseMeasuredrealquantityMeasuredLocation")
    @PreAuthorize("@ss.hasPermi('ws:location:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsEnterpriseMeasuredrealquantityMeasuredLocation wsEnterpriseMeasuredrealquantityMeasuredLocation)
    {
        startPage();
        List<WsEnterpriseMeasuredrealquantityMeasuredLocation> list = wsEnterpriseMeasuredrealquantityMeasuredLocationService.selectWsEnterpriseMeasuredrealquantityMeasuredLocationList(wsEnterpriseMeasuredrealquantityMeasuredLocation);
        return getDataTable(list);
    }

    /**
     * 获取实测实量实测部位 详细信息
     */
    @ApiOperation(value = "查询单个实测部位")
    @ApiImplicitParam(value = "主键",name = "id",dataType = "int")
    @PreAuthorize("@ss.hasPermi('ws:location:query')")
    @GetMapping(value = "/select/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsEnterpriseMeasuredrealquantityMeasuredLocationService.selectWsEnterpriseMeasuredrealquantityMeasuredLocationById(id));
    }

    /**
     * 新增实测实量实测部位 
     */
    @ApiOperation(value = "新增实测部位")
  //  @ApiImplicitParam(value = "wsEnterpriseMeasuredrealquantityMeasuredLocation",name = "新增实测部位信息",dataType = "WsEnterpriseMeasuredrealquantityMeasuredLocation")
    @PreAuthorize("@ss.hasPermi('ws:location:add')")
    @Log(title = "实测实量实测部位 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsEnterpriseMeasuredrealquantityMeasuredLocation wsEnterpriseMeasuredrealquantityMeasuredLocation)
    {
        return toAjax(wsEnterpriseMeasuredrealquantityMeasuredLocationService.insertWsEnterpriseMeasuredrealquantityMeasuredLocation(wsEnterpriseMeasuredrealquantityMeasuredLocation));
    }

    /**
     * 修改实测实量实测部位 
     */
    @ApiOperation(value = "修改实测部位")
  //  @ApiImplicitParam(value = "wsEnterpriseMeasuredrealquantityMeasuredLocation",name = "修改实测部位信息",dataType = "WsEnterpriseMeasuredrealquantityMeasuredLocation")
    @PreAuthorize("@ss.hasPermi('ws:location:edit')")
    @Log(title = "实测实量实测部位 ", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody WsEnterpriseMeasuredrealquantityMeasuredLocation wsEnterpriseMeasuredrealquantityMeasuredLocation)
    {
        return toAjax(wsEnterpriseMeasuredrealquantityMeasuredLocationService.updateWsEnterpriseMeasuredrealquantityMeasuredLocation(wsEnterpriseMeasuredrealquantityMeasuredLocation));
    }

    /**
     * 删除实测实量实测部位 
     */
    @ApiOperation(value = "批量删除实测部位")
    @ApiImplicitParam(value = "主键",name = "ids",dataType = "int")
    @PreAuthorize("@ss.hasPermi('ws:location:remove')")
    @Log(title = "实测实量实测部位 ", businessType = BusinessType.UPDATE)
	@PutMapping("/delete/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(wsEnterpriseMeasuredrealquantityMeasuredLocationService.deleteWsEnterpriseMeasuredrealquantityMeasuredLocationById(id));
    }
}
