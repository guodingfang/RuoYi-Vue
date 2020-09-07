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
import com.jlkj.project.ws.domain.WsSafetyOperationType;
import com.jlkj.project.ws.service.IWsSafetyOperationTypeService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 作业类型 Controller
 *
 * @author jlkj
 * @date 2020-07-13
 */
@Api(tags = "作业类型接口")
@RestController
@RequestMapping("/ws/operation/type")
public class WsSafetyOperationTypeController extends BaseController
{
    @Autowired
    private IWsSafetyOperationTypeService wsSafetyOperationTypeService;

    /**
     * 查询作业类型 列表
     */
    @ApiOperation("动态条件查询所有作业类型")
    @PreAuthorize("@ss.hasPermi('ws:operation/type:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsSafetyOperationType wsSafetyOperationType)
    {
        startPage();
        List<WsSafetyOperationType> list = wsSafetyOperationTypeService.selectWsSafetyOperationTypeList(wsSafetyOperationType);
        return getDataTable(list);
    }

    /**
     * 获取作业类型 详细信息
     */
    @ApiOperation("查询单个作业类型")
    @PreAuthorize("@ss.hasPermi('ws:operation/type:query')")
    @GetMapping(value = "/select/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsSafetyOperationTypeService.selectWsSafetyOperationTypeById(id));
    }

    /**
     * 新增作业类型 
     */
    @ApiOperation("新增作业类型")
    @PreAuthorize("@ss.hasPermi('ws:operation/type:add')")
    @Log(title = "作业类型 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsSafetyOperationType wsSafetyOperationType)
    {
        int result = wsSafetyOperationTypeService.insertWsSafetyOperationType(wsSafetyOperationType);
        if (result == 0){
            return AjaxResult.error("作业类型名称重复");
        }
        return toAjax(result);
    }

    /**
     * 修改作业类型 
     */
    @ApiOperation("修改作业类型")
    @PreAuthorize("@ss.hasPermi('ws:operation/type:edit')")
    @Log(title = "作业类型 ", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody WsSafetyOperationType wsSafetyOperationType)
    {
        int result = wsSafetyOperationTypeService.updateWsSafetyOperationType(wsSafetyOperationType);
        if (result == 0){
            return AjaxResult.error("作业类型名称重复");
        }
        return toAjax(result);
    }

    /**
     * 删除作业类型 
     */
    @ApiOperation("删除作业类型")
    @PreAuthorize("@ss.hasPermi('ws:operation/type:remove')")
    @Log(title = "作业类型 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/delete/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        int result = wsSafetyOperationTypeService.deleteWsSafetyOperationTypeById(id);
        if (result == 0){
            return AjaxResult.error("已绑定巡视记录，请勿删除");
        }
        return toAjax(result);
    }
}
