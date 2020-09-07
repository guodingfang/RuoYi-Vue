package com.jlkj.project.ws.controller;

import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityContent;
import com.jlkj.project.ws.domain.vo.WsEnterpriseMeasuredrealquantityVO;
import com.jlkj.project.ws.service.IWsEnterpriseMeasuredrealquantityContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 实测实量实测内容 Controller
 *
 * @author pyy
 * @date 2020-06-20
 */
@Api(value = "实测实量实测内容接口",tags = "实测实量实测内容页面")
@RestController
@RequestMapping("/ws/content")
public class WsEnterpriseMeasuredrealquantityContentController extends BaseController
{
    @Autowired
    private IWsEnterpriseMeasuredrealquantityContentService wsEnterpriseMeasuredrealquantityContentService;

    /**
     * 查询实测实量实测内容 列表
     */
    @ApiOperation("查询所有实测实量实测内容")
 //   @ApiImplicitParam(value = "wsEnterpriseMeasuredrealquantityContent",name = "查询所有实测实量实测内容",dataType = "WsEnterpriseMeasuredrealquantityContent")
    @PreAuthorize("@ss.hasPermi('ws:content:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsEnterpriseMeasuredrealquantityContent wsEnterpriseMeasuredrealquantityContent)
    {
        startPage();
        List<WsEnterpriseMeasuredrealquantityContent> list = wsEnterpriseMeasuredrealquantityContentService.selectWsEnterpriseMeasuredrealquantityContentList(wsEnterpriseMeasuredrealquantityContent);
        return getDataTable(list);
    }

    /**
     * 获取实测实量实测内容 详细信息
     */


    @ApiOperation("查询单个实测实量实测内容")
    @ApiImplicitParam(value = "主键",name = "id",dataType = "int")
    @PreAuthorize("@ss.hasPermi('ws:content:query')")
    @GetMapping(value = "/select/{measuredrealquantityMeasuredLocationId}")
    public AjaxResult getInfo(@PathVariable("measuredrealquantityMeasuredLocationId") Integer measuredrealquantityMeasuredLocationId)
    {
        return AjaxResult.success(wsEnterpriseMeasuredrealquantityContentService.selectWsEnterpriseMeasuredrealquantityContentById(measuredrealquantityMeasuredLocationId));
    }

    /**
     * 新增实测实量实测内容
     */
    @ApiOperation("新增实测实量实测内容")
   // @ApiImplicitParam(value = "wsEnterpriseMeasuredrealquantityContent",name = "新增实测实量实测内容",dataType = "WsEnterpriseMeasuredrealquantityContent")
    @PreAuthorize("@ss.hasPermi('ws:content:add')")
    @Log(title = "实测实量实测内容 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsEnterpriseMeasuredrealquantityVO wsEnterpriseMeasuredrealquantityVO)
    {
        return toAjax(wsEnterpriseMeasuredrealquantityContentService.insertWsEnterpriseMeasuredrealquantityContent(wsEnterpriseMeasuredrealquantityVO));
    }

    /**
     * 修改实测实量实测内容
     */
    @ApiOperation("修改实测实量实测内容")
    //@ApiImplicitParam(value = "wsEnterpriseMeasuredrealquantityContent",name = "修改实测实量实测内容",dataType = "WsEnterpriseMeasuredrealquantityContent")
    @PreAuthorize("@ss.hasPermi('ws:content:edit')")
    @Log(title = "实测实量实测内容 ", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody WsEnterpriseMeasuredrealquantityVO wsEnterpriseMeasuredrealquantityVO)
    {
        return toAjax(wsEnterpriseMeasuredrealquantityContentService.updateWsEnterpriseMeasuredrealquantityContent(wsEnterpriseMeasuredrealquantityVO));
    }

    /**
     * 删除实测实量实测内容
     */
    @ApiOperation("删除实测实量实测内容")
    @ApiImplicitParam(value = "主键",name = "ids",dataType = "int")
    @PreAuthorize("@ss.hasPermi('ws:content:remove')")
    @Log(title = "实测实量实测内容 ", businessType = BusinessType.UPDATE)
	@PutMapping("/delete/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(wsEnterpriseMeasuredrealquantityContentService.deleteWsEnterpriseMeasuredrealquantityContentById(id));
    }
}
