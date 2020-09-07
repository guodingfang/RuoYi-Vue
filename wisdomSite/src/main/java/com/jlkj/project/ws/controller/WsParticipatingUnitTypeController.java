package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.project.ws.domain.WsParticipatingUnitType;
import com.jlkj.project.ws.service.IWsParticipatingUnitTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 参建单位类型 Controller
 *
 * @author jlkj
 * @date 2020-07-02
 */
@Api(tags = "参建单位类型")
@RestController
@RequestMapping("/ws/participatingUnitType")
public class WsParticipatingUnitTypeController extends BaseController
{
    @Autowired
    private IWsParticipatingUnitTypeService wsParticipatingUnitTypeService;

    /**
     * 查询参建单位类型 列表
     */
    @ApiOperation("查询参建单位类型 列表")
    @ApiImplicitParam(name = "参建单位类型",value = "wsParticipatingUnitType",paramType ="WsParticipatingUnitType" )
    //@PreAuthorize("@ss.hasPermi('ws:participatingUnitType:list')")
    @GetMapping("/list")
    public AjaxResult list(WsParticipatingUnitType wsParticipatingUnitType)
    {
        List<WsParticipatingUnitType> list = wsParticipatingUnitTypeService.selectWsParticipatingUnitTypeList(wsParticipatingUnitType);
        return AjaxResult.success(list);
    }



    /**
     * 导出参建单位类型 列表
     */
    @PreAuthorize("@ss.hasPermi('ws:participatingUnitType:export')")
    @Log(title = "参建单位类型 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsParticipatingUnitType wsParticipatingUnitType)
    {
        List<WsParticipatingUnitType> list = wsParticipatingUnitTypeService.selectWsParticipatingUnitTypeList(wsParticipatingUnitType);
        ExcelUtil<WsParticipatingUnitType> util = new ExcelUtil<WsParticipatingUnitType>(WsParticipatingUnitType.class);
        return util.exportExcel(list, "participatingUnitType");
    }

    /**
     * 获取参建单位类型 详细信息
     */
    //@PreAuthorize("@ss.hasPermi('ws:participatingUnitType:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsParticipatingUnitTypeService.selectWsParticipatingUnitTypeById(id));
    }

    /**
     * 新增参建单位类型
     */
    //@PreAuthorize("@ss.hasPermi('ws:participatingUnitType:add')")
    @Log(title = "参建单位类型 ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WsParticipatingUnitType wsParticipatingUnitType)
    {
        return toAjax(wsParticipatingUnitTypeService.insertWsParticipatingUnitType(wsParticipatingUnitType));
    }

    /**
     * 修改参建单位类型
     */
    //@PreAuthorize("@ss.hasPermi('ws:participatingUnitType:edit')")
    @Log(title = "参建单位类型 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsParticipatingUnitType wsParticipatingUnitType)
    {
        return toAjax(wsParticipatingUnitTypeService.updateWsParticipatingUnitType(wsParticipatingUnitType));
    }

    /**
     * 删除参建单位类型
     */
    //@PreAuthorize("@ss.hasPermi('ws:participatingUnitType:remove')")
    @Log(title = "参建单位类型 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wsParticipatingUnitTypeService.deleteWsParticipatingUnitTypeByIds(ids));
    }
}
