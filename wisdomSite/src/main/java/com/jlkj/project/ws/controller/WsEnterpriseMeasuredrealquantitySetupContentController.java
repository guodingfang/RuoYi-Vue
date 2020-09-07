package com.jlkj.project.ws.controller;

import java.util.List;

import com.jlkj.project.ws.domain.vo.WsEnterpriseMeasuredrealquantitySetupVO;
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
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantitySetupContent;
import com.jlkj.project.ws.service.IWsEnterpriseMeasuredrealquantitySetupContentService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 实测实量设置分项工程实测内容 Controller
 *
 * @author pyy
 * @date 2020-06-20
 */
@Api(value = "实测实量设置分项工程实测内容",tags = "实测实量设置分项工程实测内容")
@RestController
@RequestMapping("/ws/measurement")
public class WsEnterpriseMeasuredrealquantitySetupContentController extends BaseController
{
    @Autowired
    private IWsEnterpriseMeasuredrealquantitySetupContentService wsEnterpriseMeasuredrealquantitySetupContentService;



    /**
     * 获取实测实量设置分项工程实测内容 详细信息
     */
    @ApiOperation("根据分项工程id查询分项工程实测内容 ")
    @ApiImplicitParam(value = "主键",name = "id",dataType = "int")
    @PreAuthorize("@ss.hasPermi('ws:measurement:query')")
    @GetMapping(value = "/select/{measuredrealquantityMeasuredLocationId}")
    public AjaxResult getInfo(@PathVariable("measuredrealquantityMeasuredLocationId") Integer measuredrealquantityMeasuredLocationId)
    {
        return AjaxResult.success(wsEnterpriseMeasuredrealquantitySetupContentService.selectWsEnterpriseMeasuredrealquantitySetupContentById(measuredrealquantityMeasuredLocationId));
    }

    /**
     * 新增实测实量设置分项工程实测内容 
     */
    @ApiOperation("新增实测实量设置分项工程实测内容 ")
  //  @ApiImplicitParam(value = "wsEnterpriseMeasuredrealquantitySetupVO",name = "新增实测实量设置分项工程实测内容",dataType = "WsEnterpriseMeasuredrealquantitySetupVO")
    @PreAuthorize("@ss.hasPermi('ws:measurement:add')")
    @Log(title = "实测实量设置分项工程实测内容 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsEnterpriseMeasuredrealquantitySetupVO wsEnterpriseMeasuredrealquantitySetupVO)
    {
        int result = wsEnterpriseMeasuredrealquantitySetupContentService.insertWsEnterpriseMeasuredrealquantitySetupContent(wsEnterpriseMeasuredrealquantitySetupVO);
        if (result == 0){
            return AjaxResult.error("实测内容为空");
        }
        return toAjax(result);
    }

    /**
     * 编辑保存实测实量设置分项工程实测内容
     */
    @ApiOperation("编辑保存实测实量设置分项工程实测内容 ")
  //  @ApiImplicitParam(value = "wsEnterpriseMeasuredrealquantitySetupVO",name = "编辑保存实测实量设置分项工程实测内容",dataType = "WsEnterpriseMeasuredrealquantitySetupVO")
    @PreAuthorize("@ss.hasPermi('ws:measurement:set')")
    @Log(title = "实测实量设置分项工程实测内容 ", businessType = BusinessType.UPDATE)
    @PutMapping("/set")
    public AjaxResult set(@RequestBody WsEnterpriseMeasuredrealquantitySetupVO wsEnterpriseMeasuredrealquantitySetupVO)
    {
        return toAjax(wsEnterpriseMeasuredrealquantitySetupContentService.setWsEnterpriseMeasuredrealquantitySetupContent(wsEnterpriseMeasuredrealquantitySetupVO));
    }

//    /**
//     * 修改实测实量设置分项工程实测内容
//     */
//    @ApiOperation("修改实测实量设置分项工程实测内容 ")
//  //  @ApiImplicitParam(value = "wsEnterpriseMeasuredrealquantitySetupContent",name = "修改实测实量设置分项工程实测内容",dataType = "WsEnterpriseMeasuredrealquantitySetupContent")
//    @PreAuthorize("@ss.hasPermi('ws:measurement:update')")
//    @Log(title = "实测实量设置分项工程实测内容 ", businessType = BusinessType.UPDATE)
//    @PutMapping("/update")
//    public AjaxResult update(@RequestBody List<WsEnterpriseMeasuredrealquantitySetupContent> wsEnterpriseMeasuredrealquantitySetupContent)
//    {
//        return toAjax(wsEnterpriseMeasuredrealquantitySetupContentService.updateWsEnterpriseMeasuredrealquantitySetupContent(wsEnterpriseMeasuredrealquantitySetupContent));
//    }

    /**
     * 删除实测实量设置分项工程实测内容 
     */
    @ApiOperation("删除实测实量设置分项工程实测内容 ")
    @ApiImplicitParam(value = "主键",name = "ids",dataType = "int")
    @PreAuthorize("@ss.hasPermi('ws:measurement:remove')")
    @Log(title = "实测实量设置分项工程实测内容 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/delete/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(wsEnterpriseMeasuredrealquantitySetupContentService.deleteWsEnterpriseMeasuredrealquantitySetupContentById(id));
    }
}
