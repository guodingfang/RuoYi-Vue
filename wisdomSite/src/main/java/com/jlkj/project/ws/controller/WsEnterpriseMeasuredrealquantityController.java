package com.jlkj.project.ws.controller;

import java.util.List;

import com.jlkj.project.ws.domain.vo.WsEnterpriseMeasuredrealquantityVO;
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
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantity;
import com.jlkj.project.ws.service.IWsEnterpriseMeasuredrealquantityService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 实测实量 Controller
 *
 * @author pyy
 * @date 2020-06-20
 */
@Api(value = "实测实量接口",tags ={"实测实量测量页面"} )
@RestController
@RequestMapping("/ws/measured")
public class WsEnterpriseMeasuredrealquantityController extends BaseController
{
    @Autowired
    private IWsEnterpriseMeasuredrealquantityService wsEnterpriseMeasuredrealquantityService;

    /**
     * 查询实测实量 列表
     */
    @ApiOperation("查询所有实测实量")
 //   @ApiImplicitParam(name = "wsEnterpriseMeasuredrealquantity", value = "实测实量实体项",dataType = "WsEnterpriseMeasuredrealquantity")
    @PreAuthorize("@ss.hasPermi('ws:measured:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsEnterpriseMeasuredrealquantity wsEnterpriseMeasuredrealquantity)
    {
        startPage();
        List<WsEnterpriseMeasuredrealquantity> list = wsEnterpriseMeasuredrealquantityService.selectWsEnterpriseMeasuredrealquantityList(wsEnterpriseMeasuredrealquantity);
        return getDataTable(list);
    }

    /**
     * 获取实测实量 详细信息
     */
    @ApiOperation("根据分项工程查询实测实量")
    @ApiImplicitParam(name = "id", value = "分项工程id",required = true, dataType = "int")
    @PreAuthorize("@ss.hasPermi('ws:measured:query')")
    @GetMapping(value = "/select/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsEnterpriseMeasuredrealquantityService.deleteWsEnterpriseMeasuredrealquantityById(id));
    }

    /**
     * 新增实测实量 
     */
    @ApiOperation("新增实测实量信息")
   // @ApiImplicitParam(name = "wsEnterpriseMeasuredrealquantityVO", value = "实测实量VO",required = true,dataType = "WsEnterpriseMeasuredrealquantityVO")
    @PreAuthorize("@ss.hasPermi('ws:measured:add')")
    @Log(title = "实测实量 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsEnterpriseMeasuredrealquantity wsEnterpriseMeasuredrealquantity)
    {
        return toAjax(wsEnterpriseMeasuredrealquantityService.insertWsEnterpriseMeasuredrealquantity(wsEnterpriseMeasuredrealquantity));
    }

//    /**
//     * 修改实测实量
//     */
//    @ApiOperation("修改实测实量")
//   // @ApiImplicitParam(name = "wsEnterpriseMeasuredrealquantityVO", value = "实测实量VO", required = true,dataType = "WsEnterpriseMeasuredrealquantityVO")
//    @PreAuthorize("@ss.hasPermi('ws:measured:edit')")
//    @Log(title = "实测实量 ", businessType = BusinessType.UPDATE)
//    @PutMapping("/update")
//    public AjaxResult edit(@RequestBody WsEnterpriseMeasuredrealquantity wsEnterpriseMeasuredrealquantity)
//    {
//        return toAjax(wsEnterpriseMeasuredrealquantityService.updateWsEnterpriseMeasuredrealquantity(wsEnterpriseMeasuredrealquantity));
//    }
//
    /**
     * 删除实测实量
     */
    @ApiOperation("删除实测实量")
    @ApiImplicitParam(name = "id", value = "实测实量id", dataType = "int")
    @PreAuthorize("@ss.hasPermi('ws:measured:remove')")
    @Log(title = "实测实量 ", businessType = BusinessType.UPDATE)
	@DeleteMapping("/delete/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(wsEnterpriseMeasuredrealquantityService.deleteWsEnterpriseMeasuredrealquantityById(id));
    }
}
