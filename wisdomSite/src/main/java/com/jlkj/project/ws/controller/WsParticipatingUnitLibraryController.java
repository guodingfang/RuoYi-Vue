package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsParticipatingUnitLibrary;
import com.jlkj.project.ws.domain.vo.WsParticipatingUnitLibraryVO;
import com.jlkj.project.ws.service.IWsParticipatingUnitLibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 参建单位库Controller
 *
 * @author jlkj
 * @date 2020-06-12
 */
@Api(tags = "参建单位库管理")
@RestController
@RequestMapping("/ws/participatingUnitLibrary")
public class WsParticipatingUnitLibraryController extends BaseController
{
    @Autowired
    private IWsParticipatingUnitLibraryService wsParticipatingUnitLibraryService;

    /**
     * 查询参建单位库列表
     */
    @ApiOperation("查询参建单位库列表")
    //@ApiImplicitParam(name = "参建单位库",value = "wsParticipatingUnitLibrary",dataType = "WsParticipatingUnitLibrary")
    @PreAuthorize("@ss.hasPermi('ws:participatingUnitLibrary:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsParticipatingUnitLibrary wsParticipatingUnitLibrary)
    {
        startPage();
        List<WsParticipatingUnitLibrary> list = wsParticipatingUnitLibraryService.selectWsParticipatingUnitLibraryList(wsParticipatingUnitLibrary);
        return getDataTable(list);
    }

    /**
     * 查询参建单位库历史项目经历列表
     */
    @ApiOperation("查询参建单位库历史项目经历列表")
    //@ApiImplicitParam(name = "参建单位库",value = "wsParticipatingUnitLibrary",dataType = "WsParticipatingUnitLibrary",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:participatingUnitLibrary:historyList')")
    @GetMapping("/historyProject/list")
    public AjaxResult historyProjectList(@RequestBody  WsParticipatingUnitLibrary wsParticipatingUnitLibrary)
    {
        List<WsParticipatingUnitLibraryVO> list = wsParticipatingUnitLibraryService.statisticsProjectParticipatingUnitHistoryRecord(wsParticipatingUnitLibrary);
        return AjaxResult.success(list);
    }

    /**
     * 导出参建单位库列表
     */
    @PreAuthorize("@ss.hasPermi('ws:participatingUnitLibrary:export')")
    @Log(title = "参建单位库", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsParticipatingUnitLibrary wsParticipatingUnitLibrary)
    {
        List<WsParticipatingUnitLibrary> list = wsParticipatingUnitLibraryService.selectWsParticipatingUnitLibraryList(wsParticipatingUnitLibrary);
        ExcelUtil<WsParticipatingUnitLibrary> util = new ExcelUtil<WsParticipatingUnitLibrary>(WsParticipatingUnitLibrary.class);
        return util.exportExcel(list, "participatingUnitLibrary");
    }

    /**
     * 获取参建单位库详细信息
     */
    @ApiOperation("获取参建单位库详细信息")
    @ApiImplicitParam(name = "主键ID",value = "id",dataType = "Integer",paramType = "path")
    @PreAuthorize("@ss.hasPermi('ws:participatingUnitLibrary:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsParticipatingUnitLibraryService.selectWsParticipatingUnitLibraryById(id));
    }

    /**
     * 新增参建单位库
     */
    @PreAuthorize("@ss.hasPermi('ws:participatingUnitLibrary:add')")
    @Log(title = "参建单位库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WsParticipatingUnitLibrary wsParticipatingUnitLibrary)
    {
        return toAjax(wsParticipatingUnitLibraryService.insertWsParticipatingUnitLibrary(wsParticipatingUnitLibrary));
    }

    /**
     * 修改参建单位库
     */
    @PreAuthorize("@ss.hasPermi('ws:participatingUnitLibrary:edit')")
    @Log(title = "参建单位库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsParticipatingUnitLibrary wsParticipatingUnitLibrary)
    {
        return toAjax(wsParticipatingUnitLibraryService.updateWsParticipatingUnitLibrary(wsParticipatingUnitLibrary));
    }



    /**
     * 删除参建单位库
     */
    @PreAuthorize("@ss.hasPermi('ws:participatingUnitLibrary:remove')")
    @Log(title = "参建单位库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wsParticipatingUnitLibraryService.deleteWsParticipatingUnitLibraryByIds(ids));
    }
}
