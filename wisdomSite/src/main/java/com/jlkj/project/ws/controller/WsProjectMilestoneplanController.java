package com.jlkj.project.ws.controller;

import java.util.List;

import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.ws.domain.vo.MoveSortVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.project.ws.domain.WsProjectMilestoneplan;
import com.jlkj.project.ws.service.IWsProjectMilestoneplanService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;

/**
 * 项目里程碑计划 Controller
 *
 * @author pyy
 * @date 2020-06-09
 */
@Api(tags = "项目里程碑计划查询")
@RestController
@RequestMapping("/ws/milestoneplan")
public class WsProjectMilestoneplanController extends BaseController {
    @Autowired
    private IWsProjectMilestoneplanService wsProjectMilestoneplanService;


    /**
     * 获取项目里程碑计划 详细信息
     */
    @ApiOperation(value = "获取项目里程碑计划")
    @PreAuthorize("@ss.hasPermi('ws:milestoneplan:query')")
    @GetMapping("/select/{projectId}")
    public AjaxResult getInfo(@PathVariable("projectId") Integer projectId) {
        return AjaxResult.success(wsProjectMilestoneplanService.selectWsProjectMilestoneplanByProjectIdOrder(projectId));
    }

    /**
     * 根据id修改里程碑计划
     */
    @ApiOperation(value = "修改项目里程碑计划")
    @PreAuthorize("@ss.hasPermi('ws:milestoneplan:update')")
    @PostMapping("/update")
    public AjaxResult update(@RequestBody WsProjectMilestoneplan wsProjectMilestoneplan) {

        int result = wsProjectMilestoneplanService.updateWsProjectMilestoneplanById(wsProjectMilestoneplan);
        if (result == 0){
            return AjaxResult.error("里程碑名称重复");
        }
        return toAjax(result);

    }

    @ApiOperation("上移下移操作")
    //  @ApiImplicitParam(name = "moveSortVo",value = "上下移动", required = true)
    @PreAuthorize("@ss.hasPermi('ws:WsProjectMilestoneplan:remove')")
    @PostMapping("/remove")
    @Transactional(rollbackFor = Exception.class)
    public  AjaxResult remove(@RequestBody List<MoveSortVO> moveSortVo) {
        int result = wsProjectMilestoneplanService.removeWsProjectMilestoneplan(moveSortVo);
        return toAjax(result);
    }

    /**
     * 新增里程碑计划
     */
    @ApiOperation("新增里程碑计划")
    //  @ApiImplicitParam(name = "wsProjectMilepost",value = "项目里程碑模板项", required = true)
    @PreAuthorize("@ss.hasPermi('ws:milestoneplan:add')")
    @Log(title = "项目里程碑计划 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsProjectMilestoneplan wsProjectMilestoneplan) {

        int result = wsProjectMilestoneplanService.insertWsProjectMilestoneplan(wsProjectMilestoneplan);
        if (result == 0) {
            return AjaxResult.error("里程碑名称重复");
        }
        return toAjax(result);
    }

    /**
     * 删除里程碑计划
     */
    @ApiOperation("删除里程碑计划")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true)
    @PreAuthorize("@ss.hasPermi('ws:milepost:remove')")
    @Log(title = "删除里程碑计划 ", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(wsProjectMilestoneplanService.deleteWsProjectMilestoneplanByIds(ids));
    }

}
