package com.jlkj.project.ws.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantitySetup;
import com.jlkj.project.ws.service.IWsEnterpriseMeasuredrealquantitySetupService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 实测实量设置分项工程 Controller
 *
 * @author pyy
 * @date 2020-06-20
 */
@Api(value = "实测实量设置分项工程接口",tags = "实测实量设置分项工程接口")
@RestController
@RequestMapping("/ws/subdivisional")
public class WsEnterpriseMeasuredrealquantitySetupController extends BaseController
{
    @Autowired
    private IWsEnterpriseMeasuredrealquantitySetupService wsEnterpriseMeasuredrealquantitySetupService;

    /**
     * 查询实测实量设置分项工程 列表
     */
    @ApiOperation("查询所有实测实量设置分项工程 ")
   // @ApiImplicitParam(value = "查询所有实测实量设置分项工程",name = "wsEnterpriseMeasuredrealquantitySetup",dataType = "WsEnterpriseMeasuredrealquantitySetup")
    @PreAuthorize("@ss.hasPermi('ws:subdivisional:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsEnterpriseMeasuredrealquantitySetup wsEnterpriseMeasuredrealquantitySetup)
    {
        startPage();
        List<WsEnterpriseMeasuredrealquantitySetup> list = wsEnterpriseMeasuredrealquantitySetupService.selectWsEnterpriseMeasuredrealquantitySetupList(wsEnterpriseMeasuredrealquantitySetup);
        return getDataTable(list);
    }


//    /**
//     * 获取实测实量设置分项工程 详细信息
//     */
//    @ApiOperation("查询单个实测实量设置分项工程 ")
//    @ApiImplicitParam(value = "主键",name = "id",dataType = "int")
//    @PreAuthorize("@ss.hasPermi('ws:subdivisional:query')")
//    @GetMapping(value = "/select/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Integer id)
//    {
//        return AjaxResult.success(wsEnterpriseMeasuredrealquantitySetupService.selectWsEnterpriseMeasuredrealquantitySetupById(id));
//    }
//
//    /**
//     * 新增实测实量设置分项工程
//     */
//    @ApiOperation("新增实测实量设置分项工程 ")
//   // @ApiImplicitParam(value = "新增实测实量设置分项工程",name = "wsEnterpriseMeasuredrealquantitySetup",dataType = "WsEnterpriseMeasuredrealquantitySetup")
//    @PreAuthorize("@ss.hasPermi('ws:subdivisional:add')")
//    @Log(title = "实测实量设置分项工程 ", businessType = BusinessType.INSERT)
//    @PostMapping("/add")
//    public AjaxResult add(@RequestBody WsEnterpriseMeasuredrealquantitySetup wsEnterpriseMeasuredrealquantitySetup)
//    {
//        return toAjax(wsEnterpriseMeasuredrealquantitySetupService.insertWsEnterpriseMeasuredrealquantitySetup(wsEnterpriseMeasuredrealquantitySetup));
//    }
//
//    /**
//     * 修改实测实量设置分项工程
//     */
//    @ApiOperation("修改实测实量设置分项工程 ")
//    //@ApiImplicitParam(value = "修改实测实量设置分项工程",name = "wsEnterpriseMeasuredrealquantitySetup",dataType = "WsEnterpriseMeasuredrealquantitySetup")
//    @PreAuthorize("@ss.hasPermi('ws:subdivisional:edit')")
//    @Log(title = "实测实量设置分项工程 ", businessType = BusinessType.UPDATE)
//    @PutMapping("/update")
//    public AjaxResult edit(@RequestBody WsEnterpriseMeasuredrealquantitySetup wsEnterpriseMeasuredrealquantitySetup)
//    {
//        return toAjax(wsEnterpriseMeasuredrealquantitySetupService.updateWsEnterpriseMeasuredrealquantitySetup(wsEnterpriseMeasuredrealquantitySetup));
//    }
//
    /**
     * 删除实测实量设置分项工程
     */
    @ApiOperation("删除实测实量设置分项工程 ")
    @ApiImplicitParam(value = "主键",name = "ids",dataType = "int")
    @PreAuthorize("@ss.hasPermi('ws:subdivisional:remove')")
    @Log(title = "实测实量设置分项工程 ", businessType = BusinessType.UPDATE)
	@PutMapping("/delete/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(wsEnterpriseMeasuredrealquantitySetupService.deleteWsEnterpriseMeasuredrealquantitySetupById(id));
    }
}
