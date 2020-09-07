package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.interceptor.annotation.RepeatSubmit;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsProjectParticipatingUnit;
import com.jlkj.project.ws.domain.vo.WsProjectTeamTreeVO;
import com.jlkj.project.ws.dto.WsProjectParticipatingUnitDTO;
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
 * 项目参建单位 Controller
 *
 * @author jlkj
 * @date 2020-06-12
 */
@Api(tags = "项目参建单位管理")
@RestController
@RequestMapping("/ws/projectParticipatingUnit")
public class WsProjectParticipatingUnitController extends BaseController
{
    @Autowired
    private IWsProjectParticipatingUnitService wsProjectParticipatingUnitService;

    @Autowired
    private IWsParticipatingTeamService wsParticipatingTeamService;


    /**
     * 查询项目参建单位 列表
     */
    @ApiOperation("获取项目参建单位列表")
    //@ApiImplicitParam(name="wsProjectParticipatingUnit", value="项目参建单位",dataType  ="WsProjectParticipatingUnit")
    //@PreAuthorize("@ss.hasPermi('ws:projectParticipatingUnit:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsProjectParticipatingUnit wsProjectParticipatingUnit)
    {
        startPage();
        List<WsProjectParticipatingUnit> list = wsProjectParticipatingUnitService.selectWsProjectParticipatingUnitList(wsProjectParticipatingUnit);
        return getDataTable(list);
    }

    /**
     * 导出项目参建单位 列表
     */
    @PreAuthorize("@ss.hasPermi('ws:projectParticipatingUnit:export')")
    @Log(title = "项目参建单位 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsProjectParticipatingUnit wsProjectParticipatingUnit)
    {
        List<WsProjectParticipatingUnit> list = wsProjectParticipatingUnitService.selectWsProjectParticipatingUnitList(wsProjectParticipatingUnit);
        ExcelUtil<WsProjectParticipatingUnit> util = new ExcelUtil<WsProjectParticipatingUnit>(WsProjectParticipatingUnit.class);
        return util.exportExcel(list, "projectParticipatingUnit");
    }

    /**
     * 获取项目参建单位 详细信息
     */
    @ApiOperation("查看项目参建单位详情")
   // @ApiImplicitParam(name="id", value="项目参建单位",dataType  ="int",paramType = "path")
    @PreAuthorize("@ss.hasPermi('ws:projectParticipatingUnit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsProjectParticipatingUnitService.selectWsProjectParticipatingUnitById(id));
    }

    /**
     * 新增项目参建单位
     */
    @RepeatSubmit
    @PreAuthorize("@ss.hasPermi('ws:projectParticipatingUnit:add')")
    @ApiOperation("新增项目参建单位")
    @Log(title = "项目参建单位 ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody WsProjectParticipatingUnit wsProjectParticipatingUnit)
    {
        if(wsProjectParticipatingUnitService.existWsProjectParticipatingUnitInProject(wsProjectParticipatingUnit)){
            return AjaxResult.error("同一项目下参建单位不能重复");
        }
        if(StringUtils.isEmpty(wsProjectParticipatingUnit.getTeams())) AjaxResult.error("班组不能为空！");
        if(wsParticipatingTeamService.existWsParticipatingUnitTeamInProject(wsProjectParticipatingUnit))
            return AjaxResult.error("同一项目下班组不能重复");

        if(wsParticipatingTeamService.repeatedTeamName(wsProjectParticipatingUnit.getTeams()))
            return AjaxResult.error("班组名称不能重复");

        return toAjax(wsProjectParticipatingUnitService.insertWsProjectParticipatingUnit(wsProjectParticipatingUnit));
    }

    /**
     * 批量新增项目参建单位
     */
    @PreAuthorize("@ss.hasPermi('ws:projectParticipatingUnit:batchAdd')")
    @ApiOperation("批量新增项目参建单位")
    //@ApiImplicitParam(name="wsProjectParticipatingUnitDTO", value="项目参建单位",dataType  ="WsProjectParticipatingUnitDTO",paramType = "body")
    @Log(title = "项目参建单位 ", businessType = BusinessType.INSERT)
    @PostMapping("/batchAdd")
    public AjaxResult batchAdd(@RequestBody WsProjectParticipatingUnitDTO wsProjectParticipatingUnitDTO)
    {

        return toAjax(wsProjectParticipatingUnitService.patchAdd(wsProjectParticipatingUnitDTO)) ;
    }
    /**
     * 修改项目参建单位
     */
    @ApiOperation("修改项目参建单位类型")
    //@ApiImplicitParam(name="wsProjectParticipatingUnit", value="项目参建单位",dataType  ="WsProjectParticipatingUnit",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:projectParticipatingUnit:edit')")
    @Log(title = "项目参建单位 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody WsProjectParticipatingUnit wsProjectParticipatingUnit)
    {
        if(StringUtils.isNull(wsProjectParticipatingUnit))
            return AjaxResult.error("项目参加单位不能为空");
        WsProjectParticipatingUnit curUnit = wsProjectParticipatingUnitService.selectWsProjectParticipatingUnitById(wsProjectParticipatingUnit.getId());
        if(StringUtils.isNull(curUnit))
            return AjaxResult.error("项目参加单位不存在");

        return toAjax(wsProjectParticipatingUnitService.updateWsProjectParticipatingUnit(wsProjectParticipatingUnit));
    }

    /**
     * 批量修改项目参建单位
     */
    @RepeatSubmit
    @ApiOperation("批量修改项目参建单位")
    //@ApiImplicitParam(name="wsProjectParticipatingUnit", value="项目参建单位",dataType  ="WsProjectParticipatingUnit",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:projectParticipatingUnit:bacthEdit')")
    @Log(title = "项目参建单位 ", businessType = BusinessType.UPDATE)
    @PutMapping("/bacthEdit")
    public AjaxResult bacthEdit(@Validated @RequestBody WsProjectParticipatingUnit wsProjectParticipatingUnit)
    {
        if(StringUtils.isNull(wsProjectParticipatingUnit))
            return AjaxResult.error("项目参加单位不能为空");
        WsProjectParticipatingUnit curUnit = wsProjectParticipatingUnitService.selectWsProjectParticipatingUnitById(wsProjectParticipatingUnit.getId());
        if(StringUtils.isNull(curUnit))
            return AjaxResult.error("项目参加单位不存在");

        return toAjax(wsProjectParticipatingUnitService.updateWsProjectParticipatingUnit(wsProjectParticipatingUnit));
    }

    /**
     * 删除项目参建单位
     */

    @ApiOperation("删除项目参建单位")
    //@ApiImplicitParam(name="ids", value="项目参建单位IDs",dataType  ="int",paramType = "path")
    @PreAuthorize("@ss.hasPermi('ws:projectParticipatingUnit:remove')")
    @Log(title = "项目参建单位 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wsProjectParticipatingUnitService.deleteWsProjectParticipatingUnitByIds(ids));
    }

    /**
     * 获取工程班组树列表
     */
    @ApiOperation("获取工程班组树列表")
    @GetMapping("/treeselect")
    @PreAuthorize("@ss.hasPermi('ws:projectParticipatingUnit:treeSelect')")
    public AjaxResult treeSelect()
    {
        List<WsProjectTeamTreeVO> projectTeamList = wsProjectParticipatingUnitService.selectProjectTeamList();
        return AjaxResult.success(wsProjectParticipatingUnitService.buildProjectTeamTreeSelect(projectTeamList));
    }
}
