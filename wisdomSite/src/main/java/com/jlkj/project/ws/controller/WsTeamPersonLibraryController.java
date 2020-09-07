package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsTeamPersonLibrary;
import com.jlkj.project.ws.service.IWsTeamPersonLibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班组人员库Controller
 *
 * @author jlkj
 * @date 2020-06-12
 */
@Api(tags = "人员库管理")
@RestController
@RequestMapping("/ws/teamPersonLibrary")
public class WsTeamPersonLibraryController extends BaseController
{
    @Autowired
    private IWsTeamPersonLibraryService wsTeamPersonLibraryService;

    /**
     * 查询班组人员库列表
     */
    @ApiOperation("查询班组人员库列表")
    //@ApiImplicitParam(name = "班组人员库",value = "wsTeamPersonLibrary" ,dataType = "WsTeamPersonLibrary")
    @PreAuthorize("@ss.hasPermi('ws:teamPersonLibrary:list')")
    @GetMapping("/list")
    public TableDataInfo list( WsTeamPersonLibrary wsTeamPersonLibrary)
    {
        startPage();
        List<WsTeamPersonLibrary> list = wsTeamPersonLibraryService.selectWsTeamPersonLibraryList(wsTeamPersonLibrary);
        return getDataTable(list);
    }

    /**
     * 导出班组人员库列表
     *
     */
    @ApiOperation("查询班组人员库列表")
    //@ApiImplicitParam(name = "班组人员库",value = "wsTeamPersonLibrary" ,dataType = "WsTeamPersonLibrary")
    @PreAuthorize("@ss.hasPermi('ws:teamPersonLibrary:export')")
    @Log(title = "班组人员库", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsTeamPersonLibrary wsTeamPersonLibrary)
    {
        List<WsTeamPersonLibrary> list = wsTeamPersonLibraryService.selectWsTeamPersonLibraryList(wsTeamPersonLibrary);
        ExcelUtil<WsTeamPersonLibrary> util = new ExcelUtil<WsTeamPersonLibrary>(WsTeamPersonLibrary.class);
        return util.exportExcel(list, "teamPersonLibrary");
    }

    /**
     * 获取班组人员库详细信息
     */
    @PreAuthorize("@ss.hasPermi('ws:teamPersonLibrary:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsTeamPersonLibraryService.selectWsTeamPersonLibraryById(id));
    }

    /**
            * 获取班组人员库资格证书
     */
    @ApiOperation("查询人员证书列表")
    @ApiImplicitParam(name = "身份证号",value = "idNumber" ,dataType = "String",paramType = "path")
    @PreAuthorize("@ss.hasPermi('ws:teamPersonLibrary:ceitificates')")
    @GetMapping(value = "/{idNumber}/ceitificates")
    public AjaxResult getCeitificateList(@PathVariable("idNumber") String idNumber)
    {
        return AjaxResult.success(wsTeamPersonLibraryService.selectCeitificateListByIdNumber(idNumber));
    }

    /**
     * 新增班组人员库
     */
    @PreAuthorize("@ss.hasPermi('ws:teamPersonLibrary:add')")
    @Log(title = "班组人员库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WsTeamPersonLibrary wsTeamPersonLibrary)
    {
        return toAjax(wsTeamPersonLibraryService.insertWsTeamPersonLibrary(wsTeamPersonLibrary));
    }

    /**
     * 修改班组人员库
     */
    @PreAuthorize("@ss.hasPermi('ws:teamPersonLibrary:edit')")
    @Log(title = "班组人员库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsTeamPersonLibrary wsTeamPersonLibrary)
    {
        return toAjax(wsTeamPersonLibraryService.updateWsTeamPersonLibrary(wsTeamPersonLibrary));
    }

    /**
     * 删除班组人员库
     */
    @PreAuthorize("@ss.hasPermi('ws:teamPersonLibrary:remove')")
    @Log(title = "班组人员库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wsTeamPersonLibraryService.deleteWsTeamPersonLibraryByIds(ids));
    }
}
