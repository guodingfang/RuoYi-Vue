package com.jlkj.project.ws.controller;

import java.util.List;

import com.jlkj.project.ws.domain.TreeData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityPlan;
import com.jlkj.project.ws.service.IWsEnterpriseMeasuredrealquantityPlanService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 实测实量平面图管理 Controller
 *
 * @author pyy
 * @date 2020-06-20
 */
@Api(value = "实测实量平面图管理", tags = {"实测实量平面图页面"})
@RestController
@RequestMapping("/ws/manage")
public class WsEnterpriseMeasuredrealquantityPlanController extends BaseController
{
    @Autowired
    private IWsEnterpriseMeasuredrealquantityPlanService wsEnterpriseMeasuredrealquantityPlanService;

    /**
     * 查询实测实量平面图管理 列表
     */
    @ApiOperation("动态条件查询所有实测实量平面图管理 ")
  //  @ApiImplicitParam(name = "wsEnterpriseMeasuredrealquantityPlan", value = "实测实量平面图实体项", dataType = "WsEnterpriseMeasuredrealquantityPlan")
    @PreAuthorize("@ss.hasPermi('ws:manage:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsEnterpriseMeasuredrealquantityPlan wsEnterpriseMeasuredrealquantityPlan)
    {
        startPage();
        List<WsEnterpriseMeasuredrealquantityPlan> list = wsEnterpriseMeasuredrealquantityPlanService.selectWsEnterpriseMeasuredrealquantityPlanList(wsEnterpriseMeasuredrealquantityPlan);
        return getDataTable(list);
    }

    /**
     * 查询所有父级实测实量平面图管理
     */
    @ApiOperation("查询所有父级实测实量平面图管理 ")
    //  @ApiImplicitParam(name = "wsEnterpriseMeasuredrealquantityPlan", value = "实测实量平面图实体项", dataType = "WsEnterpriseMeasuredrealquantityPlan")
    @PreAuthorize("@ss.hasPermi('ws:manage:parentList')")
    @GetMapping("/parent/list/{projectId}")
    public AjaxResult parentList(@PathVariable("projectId") Integer projectId)
    {

       return AjaxResult.success(wsEnterpriseMeasuredrealquantityPlanService.selectWsEnterpriseMeasuredrealquantityPlanByParentId(projectId));

    }

    /**
     * 区域树
     */
    @ApiOperation("区域树 ")
    //  @ApiImplicitParam(name = "wsEnterpriseMeasuredrealquantityPlan", value = "实测实量平面图实体项", dataType = "WsEnterpriseMeasuredrealquantityPlan")
    @PreAuthorize("@ss.hasPermi('ws:manage:treeList')")
    @GetMapping("/tree/list")
    public AjaxResult treeList(WsEnterpriseMeasuredrealquantityPlan wsEnterpriseMeasuredrealquantityPlan)
    {
        int total = wsEnterpriseMeasuredrealquantityPlanService.selectTotalCount(wsEnterpriseMeasuredrealquantityPlan);
        return AjaxResult.success("查询成功", wsEnterpriseMeasuredrealquantityPlanService.selectWsEnterpriseMeasuredrealquantityPlanTreeList(wsEnterpriseMeasuredrealquantityPlan), total);

    }

    /**
     * 根据子级查询父级
     */
    @ApiOperation("根据子级查询父级信息")
    //  @ApiImplicitParam(name = "wsEnterpriseMeasuredrealquantityPlan", value = "实测实量平面图实体项", dataType = "WsEnterpriseMeasuredrealquantityPlan")
    @PreAuthorize("@ss.hasPermi('ws:manage:parent')")
    @GetMapping("/parent/{parentId}")
    public AjaxResult parent(@PathVariable("parentId") int parentId)
    {

        return AjaxResult.success(wsEnterpriseMeasuredrealquantityPlanService.selectParentWsEnterpriseMeasuredrealquantityPlan(parentId));

    }


    /**
     * 获取实测实量平面图管理 详细信息
     */
    @ApiOperation("根据id查询详细")
    @ApiImplicitParam(name = "id", value = "主键", dataType = "int")
    @PreAuthorize("@ss.hasPermi('ws:manage:query')")
    @GetMapping(value = "/select/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsEnterpriseMeasuredrealquantityPlanService.selectWsEnterpriseMeasuredrealquantityPlanById(id));
    }

    /**
     * 新增实测实量平面图管理 
     */
    @ApiOperation("新增实测实量平面图管理")
  //  @ApiImplicitParam(name = "wsEnterpriseMeasuredrealquantityPlan", value = "实测实量平面图实体项",required = true, dataType = "WsEnterpriseMeasuredrealquantityPlan")
    @PreAuthorize("@ss.hasPermi('ws:manage:add')")
    @Log(title = "实测实量平面图管理 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsEnterpriseMeasuredrealquantityPlan wsEnterpriseMeasuredrealquantityPlan)
    {
        return toAjax(wsEnterpriseMeasuredrealquantityPlanService.insertWsEnterpriseMeasuredrealquantityPlan(wsEnterpriseMeasuredrealquantityPlan));
    }

    /**
     * 修改实测实量平面图管理 
     */
    @ApiOperation("修改实测实量平面图管理")
  //  @ApiImplicitParam(name = "实测实量实体", value = "wsEnterpriseMeasuredrealquantityPlan", dataType = "WsEnterpriseMeasuredrealquantityPlan")
    @PreAuthorize("@ss.hasPermi('ws:manage:edit')")
    @Log(title = "实测实量平面图管理 ", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody WsEnterpriseMeasuredrealquantityPlan wsEnterpriseMeasuredrealquantityPlan)
    {
        return toAjax(wsEnterpriseMeasuredrealquantityPlanService.updateWsEnterpriseMeasuredrealquantityPlan(wsEnterpriseMeasuredrealquantityPlan));
    }

    /**
     * 删除实测实量平面图管理 
     */
    @ApiOperation("删除实测实量平面图管理")
    @ApiImplicitParam(name = "id", value = "主键", dataType = "int")
    @PreAuthorize("@ss.hasPermi('ws:manage:remove')")
    @Log(title = "实测实量平面图管理 ", businessType = BusinessType.UPDATE)
	@PutMapping("/delete/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(wsEnterpriseMeasuredrealquantityPlanService.deleteWsEnterpriseMeasuredrealquantityPlanById(id));
    }
}
