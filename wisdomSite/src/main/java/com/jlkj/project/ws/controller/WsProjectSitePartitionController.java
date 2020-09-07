package com.jlkj.project.ws.controller;

import java.util.List;

import io.swagger.annotations.Api;
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
import com.jlkj.project.ws.domain.WsProjectSitePartition;
import com.jlkj.project.ws.service.IWsProjectSitePartitionService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 工地分区Controller
 *
 * @author pyy
 * @date 2020-06-30
 */
@Api(tags = "工地分区接口")
@RestController
@RequestMapping("/ws/partition")
public class WsProjectSitePartitionController extends BaseController {
    @Autowired
    private IWsProjectSitePartitionService wsProjectSitePartitionService;

    /**
     * 查询工地分区列表
     */
    @ApiOperation("查询项目下工地分区列表")
    @PreAuthorize("@ss.hasPermi('ws:partition:list')")
    @GetMapping("/list")
    public AjaxResult list(Integer projectId) {

        return AjaxResult.success(wsProjectSitePartitionService.selectWsProjectSitePartitionList(projectId));

    }

    /**
     * 获取工地分区详细信息
     */
    @ApiOperation("获取工地分区详细信息")
    @PreAuthorize("@ss.hasPermi('ws:partition:query')")
    @GetMapping(value = "/select/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(wsProjectSitePartitionService.selectWsProjectSitePartitionById(id));
    }

    /**
     * 新增工地分区
     */
    @ApiOperation("新增工地分区")
    @PreAuthorize("@ss.hasPermi('ws:partition:add')")
    @Log(title = "工地分区", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsProjectSitePartition wsProjectSitePartition) {
        int i = wsProjectSitePartitionService.insertWsProjectSitePartition(wsProjectSitePartition);
        if (i == 0) {
            return AjaxResult.error("分区名称重复");
        }
        return toAjax(i);
    }

    /**
     * 修改工地分区
     */
    @ApiOperation("修改工地分区")
    @PreAuthorize("@ss.hasPermi('ws:partition:edit')")
    @Log(title = "工地分区", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody WsProjectSitePartition wsProjectSitePartition) {
        int i = wsProjectSitePartitionService.updateWsProjectSitePartition(wsProjectSitePartition);
        if (i == 0) {
            return AjaxResult.error("分区名称重复");
        }
        return toAjax(i);
    }

    /**
     * 删除工地分区
     */
    @ApiOperation("删除工地分区")
    @PreAuthorize("@ss.hasPermi('ws:partition:remove')")
    @Log(title = "工地分区", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{id}")
    public AjaxResult remove(@PathVariable Integer id) {
        String str = wsProjectSitePartitionService.deleteWsProjectSitePartitionById(id);
        if (str.equals("1")){
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.error("该分区已被（"+str+"）启用，不能删除");
    }
}
