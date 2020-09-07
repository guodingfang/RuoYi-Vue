package com.jlkj.project.ws.controller;

import java.util.List;
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
import com.jlkj.project.ws.domain.WsQualityEarlywarningManager;
import com.jlkj.project.ws.service.IWsQualityEarlywarningManagerService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 质量预警管理 Controller
 *
 * @author gaowei
 * @date 2020-06-09
 */
@RestController
@RequestMapping("/ws/earlywarningManager")
public class WsQualityEarlywarningManagerController extends BaseController
{
    @Autowired
    private IWsQualityEarlywarningManagerService wsQualityEarlywarningManagerService;

    /**
     * 查询质量预警管理 列表
     */
    @PreAuthorize("@ss.hasPermi('ws:earlywarningManager:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsQualityEarlywarningManager wsQualityEarlywarningManager)
    {
        startPage();
        List<WsQualityEarlywarningManager> list = wsQualityEarlywarningManagerService.selectWsQualityEarlywarningManagerList(wsQualityEarlywarningManager);
        return getDataTable(list);
    }

    /**
     * 导出质量预警管理 列表
     */
    @PreAuthorize("@ss.hasPermi('ws:earlywarningManager:export')")
    @Log(title = "质量预警管理 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsQualityEarlywarningManager wsQualityEarlywarningManager)
    {
        List<WsQualityEarlywarningManager> list = wsQualityEarlywarningManagerService.selectWsQualityEarlywarningManagerList(wsQualityEarlywarningManager);
        ExcelUtil<WsQualityEarlywarningManager> util = new ExcelUtil<WsQualityEarlywarningManager>(WsQualityEarlywarningManager.class);
        return util.exportExcel(list, "earlywarningManager");
    }

    /**
     * 获取质量预警管理 详细信息
     */
    @PreAuthorize("@ss.hasPermi('ws:earlywarningManager:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(wsQualityEarlywarningManagerService.selectWsQualityEarlywarningManagerById(id));
    }

    /**
     * 新增质量预警管理 
     */
    @PreAuthorize("@ss.hasPermi('ws:earlywarningManager:add')")
    @Log(title = "质量预警管理 ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WsQualityEarlywarningManager wsQualityEarlywarningManager)
    {
        return toAjax(wsQualityEarlywarningManagerService.insertWsQualityEarlywarningManager(wsQualityEarlywarningManager));
    }

    /**
     * 修改质量预警管理 
     */
    @PreAuthorize("@ss.hasPermi('ws:earlywarningManager:edit')")
    @Log(title = "质量预警管理 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsQualityEarlywarningManager wsQualityEarlywarningManager)
    {
        return toAjax(wsQualityEarlywarningManagerService.updateWsQualityEarlywarningManager(wsQualityEarlywarningManager));
    }

    /**
     * 删除质量预警管理 
     */
    @PreAuthorize("@ss.hasPermi('ws:earlywarningManager:remove')")
    @Log(title = "质量预警管理 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wsQualityEarlywarningManagerService.deleteWsQualityEarlywarningManagerByIds(ids));
    }
}
