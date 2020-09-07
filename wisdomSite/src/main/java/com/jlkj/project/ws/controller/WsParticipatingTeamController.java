package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.interceptor.annotation.RepeatSubmit;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsParticipatingTeam;
import com.jlkj.project.ws.domain.WsProjectParticipatingUnit;
import com.jlkj.project.ws.dto.WsParticipatingTeamDTO;
import com.jlkj.project.ws.service.IWsParticipatingTeamService;
import com.jlkj.project.ws.service.IWsProjectParticipatingUnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班组 Controller
 *
 * @author liujie
 * @date 2020-06-12
 */
@Api(tags = "班组管理")
@RestController
@RequestMapping("/ws/participatingTeam")
public class WsParticipatingTeamController extends BaseController
{
    @Autowired
    private IWsParticipatingTeamService wsParticipatingTeamService;

    @Autowired
    private IWsProjectParticipatingUnitService wsProjectParticipatingUnitService;

    /**
     * 查询班组 列表
     */
    @ApiOperation("查询班组 列表")
   // @PreAuthorize("@ss.hasPermi('ws:participatingTeam:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsParticipatingTeam wsParticipatingTeam)
    {
        startPage();
        List<WsParticipatingTeam> list = wsParticipatingTeamService.selectWsParticipatingTeamList(wsParticipatingTeam);
        return getDataTable(list);
    }

    /**
     * 导出班组 列表
     */
    @ApiOperation("导出班组 列表")
    @PreAuthorize("@ss.hasPermi('ws:participatingTeam:export')")
    @Log(title = "班组 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsParticipatingTeam wsParticipatingTeam)
    {
        List<WsParticipatingTeam> list = wsParticipatingTeamService.selectWsParticipatingTeamList(wsParticipatingTeam);
        ExcelUtil<WsParticipatingTeam> util = new ExcelUtil<WsParticipatingTeam>(WsParticipatingTeam.class);
        return util.exportExcel(list, "participatingTeam");
    }

    /**
     * 获取班组 详细信息
     */
    @PreAuthorize("@ss.hasPermi('ws:participatingTeam:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsParticipatingTeamService.selectWsParticipatingTeamById(id));
    }

    /**
     * 新增班组
     */
    @RepeatSubmit
    @ApiOperation("新增班组")
   // @ApiImplicitParam(name="wsParticipatingTeam", value="班组",dataType  ="WsParticipatingTeam",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:participatingTeam:add')")
    @Log(title = "班组 ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody WsParticipatingTeam wsParticipatingTeam)
    {
        WsProjectParticipatingUnit wsProjectParticipatingUnit = wsProjectParticipatingUnitService.selectWsProjectParticipatingUnitById(wsParticipatingTeam.getUnitId());
        if(StringUtils.isNull(wsProjectParticipatingUnit)){
            return AjaxResult.error("班组归属单位不存在");
        }
        return toAjax(wsParticipatingTeamService.insertWsParticipatingTeam(wsParticipatingTeam));
    }

    /**
     * 修改班组
     */
    @RepeatSubmit
    @ApiOperation("修改班组")
    //@ApiImplicitParam(name="wsParticipatingTeam", value="班组",dataType  ="WsParticipatingTeamDTO",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:participatingTeam:edit')")
    @Log(title = "班组 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit( @RequestBody WsParticipatingTeamDTO wsParticipatingTeam)
    {
        return toAjax(wsParticipatingTeamService.updateWsParticipatingTeam(wsParticipatingTeam));
    }


    /**
     * 修改班组
     */
    @RepeatSubmit
    @ApiOperation("批量修改班组")
    //@ApiImplicitParam(name="wsParticipatingTeam", value="班组",dataType  ="WsParticipatingTeamDTO",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:participatingTeam:batchEdit')")
    @Log(title = "班组 ", businessType = BusinessType.UPDATE)
    @PutMapping("/batch")
    public AjaxResult batchEdit( @RequestBody WsParticipatingTeamDTO wsParticipatingTeam)
    {
        return toAjax(wsParticipatingTeamService.batchEdit(wsParticipatingTeam));
    }
    /**
     * 删除班组
     */
    @ApiOperation("删除班组")
    //@ApiImplicitParam(name="ids", value="班组ID集合",dataType  ="Integer",paramType = "path")
    @PreAuthorize("@ss.hasPermi('ws:participatingTeam:remove')")
    @Log(title = "班组 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wsParticipatingTeamService.deleteWsParticipatingTeamByIds(ids));
    }
}
