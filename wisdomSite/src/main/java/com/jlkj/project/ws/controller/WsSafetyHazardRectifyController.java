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
import com.jlkj.project.ws.domain.WsSafetyHazardRectify;
import com.jlkj.project.ws.service.IWsSafetyHazardRectifyService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 隐患整改 Controller
 *
 * @author jlkj
 * @date 2020-07-13
 */
@Api("隐患整改接口")
@RestController
@RequestMapping("/ws/hazard/rectify")
public class WsSafetyHazardRectifyController extends BaseController
{
    @Autowired
    private IWsSafetyHazardRectifyService wsSafetyHazardRectifyService;

    /**
     * 查询隐患整改 列表
     */
    @ApiOperation("根据安全巡视id查询隐患整改信息")
    @PreAuthorize("@ss.hasPermi('ws:hazard/rectify:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsSafetyHazardRectify wsSafetyHazardRectify)
    {
        startPage();
        List<WsSafetyHazardRectify> list = wsSafetyHazardRectifyService.selectWsSafetyHazardRectifyList(wsSafetyHazardRectify);
        return getDataTable(list);
    }



    /**
     * 获取隐患整改 详细信息
     */
    @ApiOperation("查询单个隐患整改信息")
    @PreAuthorize("@ss.hasPermi('ws:hazard/rectify:query')")
    @GetMapping(value = "/select/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsSafetyHazardRectifyService.selectWsSafetyHazardRectifyById(id));
    }

    /**
     * 新增隐患整改 
     */
    @ApiOperation("新增隐患整改")
    @PreAuthorize("@ss.hasPermi('ws:hazard/rectify:add')")
    @Log(title = "隐患整改 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsSafetyHazardRectify wsSafetyHazardRectify)
    {
        return toAjax(wsSafetyHazardRectifyService.insertWsSafetyHazardRectify(wsSafetyHazardRectify));
    }


}
