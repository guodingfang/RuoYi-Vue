package com.jlkj.project.ws.controller;

import java.util.List;

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
import com.jlkj.project.ws.domain.WsSafetyHazardVerify;
import com.jlkj.project.ws.service.IWsSafetyHazardVerifyService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 隐患验证 Controller
 *
 * @author jlkj
 * @date 2020-07-13
 */
@RestController
@RequestMapping("/ws/hazard/verify")
public class WsSafetyHazardVerifyController extends BaseController
{
    @Autowired
    private IWsSafetyHazardVerifyService wsSafetyHazardVerifyService;

    /**
     * 查询隐患验证 列表
     */
    @ApiOperation("动态条件查询所有隐患验证信息")
    @PreAuthorize("@ss.hasPermi('ws:hazard/verify:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsSafetyHazardVerify wsSafetyHazardVerify)
    {
        startPage();
        List<WsSafetyHazardVerify> list = wsSafetyHazardVerifyService.selectWsSafetyHazardVerifyList(wsSafetyHazardVerify);
        return getDataTable(list);
    }

    /**
     * 新增隐患验证 
     */
    @ApiOperation("新增隐患验证")
    @PreAuthorize("@ss.hasPermi('ws:hazard/verify:add')")
    @Log(title = "隐患验证 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsSafetyHazardVerify wsSafetyHazardVerify)
    {
        int result = wsSafetyHazardVerifyService.insertWsSafetyHazardVerify(wsSafetyHazardVerify);
        if (result == 0){
            return AjaxResult.error("已验证，请勿重复验证");
        }
        return toAjax(result);
    }


}
