package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.project.ws.domain.WsArea;
import com.jlkj.project.ws.service.IWsAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 区域 Controller
 *
 * @author jlkj
 * @date 2020-07-02
 */
@Api(tags = "区域")
@RestController
@RequestMapping("/ws/area")
public class WsAreaController extends BaseController
{
    @Autowired
    private IWsAreaService wsAreaService;

    /**
     * 查询区域 列表
     */
    @ApiOperation(value="查询参建单位类型 列表",response = AjaxResult.class)
    @ApiImplicitParam(name = "区域",value = "wsArea",paramType ="WsArea",example ="parentId=0" )
    //@PreAuthorize("@ss.hasPermi('ws:area:list')")
    @GetMapping("/list")
    public AjaxResult list(WsArea wsArea)
    {
        List<WsArea> list = wsAreaService.selectWsAreaList(wsArea);
        return AjaxResult.success(list);
    }

    /**
     * 导出区域 列表
     */
    @PreAuthorize("@ss.hasPermi('ws:area:export')")
    @Log(title = "区域 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsArea wsArea)
    {
        List<WsArea> list = wsAreaService.selectWsAreaList(wsArea);
        ExcelUtil<WsArea> util = new ExcelUtil<WsArea>(WsArea.class);
        return util.exportExcel(list, "area");
    }

    /**
     * 获取区域 详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ws:area:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsAreaService.selectWsAreaById(id));
    }

    /**
     * 新增区域
     */
    //@PreAuthorize("@ss.hasPermi('ws:area:add')")
    @Log(title = "区域 ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WsArea wsArea)
    {
        return toAjax(wsAreaService.insertWsArea(wsArea));
    }

    /**
     * 修改区域
     */
    //@PreAuthorize("@ss.hasPermi('ws:area:edit')")
    @Log(title = "区域 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsArea wsArea)
    {
        return toAjax(wsAreaService.updateWsArea(wsArea));
    }

    /**
     * 删除区域
     */
    //@PreAuthorize("@ss.hasPermi('ws:area:remove')")
    @Log(title = "区域 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wsAreaService.deleteWsAreaByIds(ids));
    }
}
