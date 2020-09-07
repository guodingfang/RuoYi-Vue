package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.service.IWsProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目信息 Controller
 *
 * @author jlkj
 * @date 2020-06-09
 */
@RestController
@Api(value = "项目信息接口", tags = {"项目信息页面"})
@RequestMapping("/ws/project")
public class WsProjectController extends BaseController {
    @Autowired
    private IWsProjectService wsProjectService;

    /**
     * 查询项目信息 列表
     */
    @ApiOperation("查询项目信息")
    @ApiImplicitParam(name = "wsProject", value = "项目实体项")
    @PreAuthorize("@ss.hasPermi('ws:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsProject wsProject) {
        startPage();
        List<WsProject> list = wsProjectService.selectWsProjectListByUser(wsProject);
        return getDataTable(list);
    }


    /**
     * 获取项目信息 详细信息
     */
    @ApiOperation("查询单个项目信息")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, type = "int", paramType = "path")
    @PreAuthorize("@ss.hasPermi('ws:project:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(wsProjectService.selectWsProjectById(id));
    }

    /**
     * 查询所有项目信息
     */
    @ApiOperation("查询所有项目信息")
    @GetMapping(value = "/all/list")
    public AjaxResult getAll() {
        return AjaxResult.success(wsProjectService.selectAllWsProjectList());
    }

    /**
     * 新增项目信息
     */
    @ApiOperation("新增项目信息")
    //@ApiImplicitParam(name = "wsProject", value = "项目实体项",required = true)
    @PreAuthorize("@ss.hasPermi('ws:project:add')")
    @Log(title = "项目信息 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsProject wsProject) {
        int i = wsProjectService.insertWsProject(wsProject);
        if (i == 0) {
            return AjaxResult.error("项目名称重复");
        }
        if (i == -2) {
            return AjaxResult.error("系统异常，请联系管理员");
        }
        return toAjax(i);
    }

    /**
     * 修改项目信息
     */
    @ApiOperation("修改项目信息")
    @ApiImplicitParam(name = "wsProject", value = "项目实体项", required = true)
    @PreAuthorize("@ss.hasPermi('ws:project:edit')")
    @Log(title = "项目信息 ", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody WsProject wsProject) {
        int result = wsProjectService.updateWsProject(wsProject);
        if (result == -1) {
            return AjaxResult.error("系统异常，请联系管理员");
        }
        if (result == 0){
            return AjaxResult.error("项目名称重复");
        }
        return toAjax(result);
    }

    /**
     * 删除项目信息
     */
    @ApiOperation("批量删除项目信息")
    @ApiImplicitParam(name = "ids", value = "主键集", required = true, type = "int", paramType = "path")
    @PreAuthorize("@ss.hasPermi('ws:project:remove')")
    @Log(title = "项目信息 ", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{id}")
    public AjaxResult remove(@PathVariable Integer id) {
        int result = wsProjectService.deleteWsProjectById(id);
        if (result == 0) {
            return AjaxResult.error("项目已关联设备，请勿删除");
        }
        return toAjax(result);
    }
}
