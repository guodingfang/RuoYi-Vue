package com.jlkj.project.ws.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jlkj.common.constant.UserConstants;
import com.jlkj.project.ws.domain.WsQualityCheckManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
import com.jlkj.project.ws.domain.WsQualityProblemType;
import com.jlkj.project.ws.service.IWsQualityProblemTypeService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 质量问题管理 Controller
 *
 * @author gaowei
 * @date 2020-06-09
 */
@Api(value = "质量问题管理接口",tags = "质量问题管理页面")
@RestController
@RequestMapping("/ws/problem")
public class WsQualityProblemTypeController extends BaseController
{

    @Autowired
    private IWsQualityProblemTypeService wsQualityProblemTypeService;

    /**
     * 查询质量问题管理 列表
     */
    @ApiOperation("质量问题管理列表")
    @PreAuthorize("@ss.hasPermi('ws:problem:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsQualityProblemType wsQualityProblemType)
    {
        startPage();
        List<WsQualityProblemType> list = wsQualityProblemTypeService.selectWsQualityProblemTypeList(wsQualityProblemType);
        return getDataTable(list);
    }

    /**
     * 导出质量问题管理 列表
     */
    @PreAuthorize("@ss.hasPermi('ws:problem:export')")
    @Log(title = "质量问题管理 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsQualityProblemType wsQualityProblemType)
    {
        List<WsQualityProblemType> list = wsQualityProblemTypeService.selectWsQualityProblemTypeList(wsQualityProblemType);
        ExcelUtil<WsQualityProblemType> util = new ExcelUtil<WsQualityProblemType>(WsQualityProblemType.class);
        return util.exportExcel(list, "problem");
    }

    /**
     * 获取质量问题管理 详细信息
     */
    //@ApiOperation("质量问题管理详细信息")
    @PreAuthorize("@ss.hasPermi('ws:problem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(wsQualityProblemTypeService.selectWsQualityProblemTypeById(id));
    }

    /**
     * 新增质量问题管理 
     */
    @ApiOperation("新增质量问题管理")
    @PreAuthorize("@ss.hasPermi('ws:problem:add')")
    @Log(title = "质量问题管理 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsQualityProblemType wsQualityProblemType)
    {
        if (UserConstants.NOT_UNIQUE.equals(wsQualityProblemTypeService.checkNameType(wsQualityProblemType)))
        {
            return AjaxResult.error("新增质量问题'" + wsQualityProblemType.getProblemTypeName() + "'失败，该质量问题已存在");
        }
        return toAjax(wsQualityProblemTypeService.insertWsQualityProblemType(wsQualityProblemType));
    }

    /**
     * 修改质量问题管理 
     */
    @ApiOperation("修改质量问题管理")
    @PreAuthorize("@ss.hasPermi('ws:problem:edit')")
    @Log(title = "质量问题管理 ", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody WsQualityProblemType wsQualityProblemType)
    {
        if (UserConstants.NOT_UNIQUE.equals(wsQualityProblemTypeService.checkNameType(wsQualityProblemType)))
        {
            return AjaxResult.error("修改质量问题'" + wsQualityProblemType.getProblemTypeName() + "'失败，该质量问题已存在");
        }
        return toAjax(wsQualityProblemTypeService.updateWsQualityProblemType(wsQualityProblemType));
    }

    /**
     * 启禁用质量问题管理
     */
    @ApiOperation("启禁用质量问题管理")
    @PreAuthorize("@ss.hasPermi('ws:problem:edit')")
    @Log(title = "质量问题管理 ", businessType = BusinessType.UPDATE)
    @PutMapping("/enableDisable")
    public AjaxResult enableDisable(@RequestBody WsQualityProblemType wsQualityProblemType)
    {
        return toAjax(wsQualityProblemTypeService.updateWsQualityProblemType(wsQualityProblemType));
    }

    /**
     * 删除质量问题管理 
     */
    @ApiOperation("删除质量问题管理")
    @PreAuthorize("@ss.hasPermi('ws:problem:remove')")
    @Log(title = "质量问题管理 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable String id)
    {
        return toAjax(wsQualityProblemTypeService.deleteWsQualityProblemTypeById(Long.valueOf(id)));
    }

    /**
     * 验证质量问题管理删除
     */
    @ApiOperation("验证质量问题管理删除")
    @PreAuthorize("@ss.hasPermi('ws:problem:list')")
    @GetMapping("/verification/{id}")
    public TableDataInfo list(@PathVariable String id)
    {
        startPage();
        List<WsQualityCheckManager> list = wsQualityProblemTypeService.selectWsQualityProblemTypeByIdList(Long.valueOf(id));
        return getDataTable(list);
    }
}

