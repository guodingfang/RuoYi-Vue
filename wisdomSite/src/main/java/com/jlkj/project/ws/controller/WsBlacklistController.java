package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsBlacklist;
import com.jlkj.project.ws.domain.vo.WsBlacklistVO;
import com.jlkj.project.ws.service.IWsBlacklistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 【人员黑名单】Controller
 *
 * @author liujie
 * @date 2020-06-19
 */
@Api("人员黑名单管理")
@RestController
@RequestMapping("/ws/blacklist")
public class WsBlacklistController extends BaseController
{
    @Autowired
    private IWsBlacklistService wsBlacklistService;

    /**
     * 查询【人员黑名单】列表
     */
    @ApiOperation("查询人员黑名单列表")
    @ApiImplicitParam(name="wsBlacklistVO", value="人员黑名单",dataType  ="WsBlacklistVO")
    @PreAuthorize("@ss.hasPermi('ws:blacklist:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsBlacklistVO wsBlacklistVO)
    {
        startPage();
        List<WsBlacklistVO> list = wsBlacklistService.selectWsBlacklistList(wsBlacklistVO);
        return getDataTable(list);
    }

    /**
     * 导出【人员黑名单】列表
     */
    @PreAuthorize("@ss.hasPermi('ws:blacklist:export')")
    @Log(title = "【人员黑名单】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsBlacklistVO wsBlacklistVO)
    {
        List<WsBlacklistVO> list = wsBlacklistService.selectWsBlacklistList(wsBlacklistVO);
        ExcelUtil<WsBlacklistVO> util = new ExcelUtil<WsBlacklistVO>(WsBlacklistVO.class);
        return util.exportExcel(list, "blacklist");
    }

    /**
     * 获取【人员黑名单】详细信息
     *
     */
    @ApiOperation("查询人员黑名单详细")
    @ApiImplicitParam(name="id", value="黑名单ID",dataType  ="int",paramType = "path")
    @PreAuthorize("@ss.hasPermi('ws:blacklist:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsBlacklistService.selectWsBlacklistById(id));
    }

    /**
     * 新增【人员黑名单】
     */
    @ApiOperation("新增人员黑名单")
    @ApiImplicitParam(name="wsBlacklist", value="人员黑名单",dataType  ="WsBlacklist",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:blacklist:add')")
    @Log(title = "【人员黑名单】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated  @RequestBody WsBlacklist wsBlacklist)
    {
        return toAjax(wsBlacklistService.insertWsBlacklist(wsBlacklist));
    }


    /**
     * 新增【人员黑名单】
     */
    @ApiOperation("新增人员黑名单")
   // @ApiImplicitParam(name="wsBlacklist", value="人员黑名单",dataType  ="WsBlacklist",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:blacklist:add')")
    @Log(title = "【人员黑名单】", businessType = BusinessType.INSERT)
    @PostMapping("/batch")
    public AjaxResult batch( @RequestBody WsBlacklist wsBlacklist)
    {
        return toAjax(wsBlacklistService.batchWsBlacklist(wsBlacklist));
    }

    /**
     * 修改【人员黑名单】
     */
    @ApiOperation("修改人员黑名单")
    @ApiImplicitParam(name="wsBlacklist", value="人员黑名单",dataType  ="WsBlacklist",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:blacklist:edit')")
    @Log(title = "【人员黑名单】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody WsBlacklist wsBlacklist)
    {
        return toAjax(wsBlacklistService.updateWsBlacklist(wsBlacklist));
    }

    /**
     * 批量移除【人员黑名单】
     */
    @PreAuthorize("@ss.hasPermi('ws:blacklist:remove')")
    @ApiOperation("移除人员黑名单")
    @ApiImplicitParam(name="ids", value="项目人员ID",dataType  ="int",paramType = "body")
    @Log(title = "【人员黑名单】", businessType = BusinessType.UPDATE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wsBlacklistService.batchRemoveBlacklist(ids));
    }
}
