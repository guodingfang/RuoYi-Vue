package com.jlkj.project.ws.controller;

import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.project.ws.domain.WsPartitionBind;
import com.jlkj.project.ws.service.IWsPartitionBindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 分区绑定Controller
 *
 * @author pyy
 * @date 2020-06-30
 */
@Api(tags = "分区绑定接口")
@RestController
@RequestMapping("/ws/bind")
public class WsPartitionBindController extends BaseController {
    @Autowired
    private IWsPartitionBindService wsPartitionBindService;

    /**
     * 查询分区绑定列表
     */
    @ApiOperation("根据分区id查询分区绑定列表")
    @ApiImplicitParam(name = "partitionId", value = "工地分区id", dataType = "int")
    @PreAuthorize("@ss.hasPermi('ws:bind:list')")
    @GetMapping("/list")
    public AjaxResult list(Integer partitionId) {
        return AjaxResult.success(wsPartitionBindService.selectWsPartitionBindList(partitionId));
    }

    /**
     * 获取分区绑定详细信息
     */
    @ApiImplicitParam(name = "id", value = "主键", dataType = "int")
    @ApiOperation("获取分区绑定详细信息")
    @PreAuthorize("@ss.hasPermi('ws:bind:query')")
    @GetMapping(value = "/select/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(wsPartitionBindService.selectWsPartitionBindById(id));
    }

    /**
     * 新增分区绑定
     */
    @ApiOperation("新增分区绑定")
    @PreAuthorize("@ss.hasPermi('ws:bind:add')")
    @Log(title = "分区绑定", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsPartitionBind wsPartitionBind) {
        return toAjax(wsPartitionBindService.insertWsPartitionBind(wsPartitionBind));
    }

    /**
     * 修改分区绑定
     */
    @ApiOperation("修改分区绑定")
    @PreAuthorize("@ss.hasPermi('ws:bind:edit')")
    @Log(title = "分区绑定", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody WsPartitionBind wsPartitionBind) {
        return toAjax(wsPartitionBindService.updateWsPartitionBind(wsPartitionBind));
    }

    /**
     * 删除分区绑定
     */
    @ApiOperation("删除分区绑定")
    @PreAuthorize("@ss.hasPermi('ws:bind:remove')")
    @Log(title = "分区绑定", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(wsPartitionBindService.deleteWsPartitionBindByIds(ids));
    }
}
