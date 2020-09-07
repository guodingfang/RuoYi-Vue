package com.jlkj.project.ws.controller;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jlkj.common.utils.ServletUtils;
import com.jlkj.common.utils.file.FileUploadUtils;
import com.jlkj.framework.config.RuoYiConfig;
import com.jlkj.framework.security.LoginUser;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.project.ws.domain.WsQualityCheckManager;
import com.jlkj.project.ws.service.IWsQualityCheckManagerService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 质量检查Controller
 *
 * @author gaowei
 * @date 2020-06-09
 */
@Api(value = "质量检查接口",tags = "质量检查页面")
@RestController
@RequestMapping("/ws/checkManager")
public class WsQualityCheckManagerController extends BaseController
{

    @Autowired
    private IWsQualityCheckManagerService wsQualityCheckManagerService;

    /**
     * 查询质量检查列表
     */
    @ApiOperation("质量检查列表")
    @ApiImplicitParam(name = "wsQualityCheckManager", value = "质量检查实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:checkManager:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsQualityCheckManager wsQualityCheckManager)
    {
        startPage();
        List<WsQualityCheckManager> list = wsQualityCheckManagerService.selectWsQualityCheckManagerList(wsQualityCheckManager);
        return getDataTable(list);
    }

    /**
     * 导出质量检查列表
     */
    @ApiOperation("导出质量检查")
    @ApiImplicitParam(name = "wsQualityCheckManager", value = "质量检查实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:checkManager:export')")
    @Log(title = "质量检查", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsQualityCheckManager wsQualityCheckManager)
    {
        List<WsQualityCheckManager> list = wsQualityCheckManagerService.selectWsQualityCheckManagerList(wsQualityCheckManager);
        ExcelUtil<WsQualityCheckManager> util = new ExcelUtil<WsQualityCheckManager>(WsQualityCheckManager.class);
        return util.exportExcel(list, "checkManager");
    }

    /**
     * 获取质量检查详细信息
     */
    @ApiOperation("质量检查详细信息")
    @ApiImplicitParam(name = "wsQualityCheckManager", value = "质量检查实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:checkManager:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") int id)
    {
        return AjaxResult.success(wsQualityCheckManagerService.selectWsQualityCheckManagerById(id));
    }

    /**
     * 新增质量检查
     */
    @ApiOperation("新增质量检查")
    @ApiImplicitParam(name = "wsQualityCheckManager", value = "质量检查实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:checkManager:add')")
    @Log(title = "质量检查", businessType = BusinessType.INSERT)
    @PostMapping(value = "/add")
    public AjaxResult add(@RequestBody WsQualityCheckManager wsQualityCheckManager)
    {
        return toAjax(wsQualityCheckManagerService.insertWsQualityCheckManager(wsQualityCheckManager));
    }

    /**
     * 修改质量检查
     */
   // @ApiOperation("修改质量检查")
    @PreAuthorize("@ss.hasPermi('ws:checkManager:edit')")
    @Log(title = "质量检查", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsQualityCheckManager wsQualityCheckManager)
    {
        return toAjax(wsQualityCheckManagerService.updateWsQualityCheckManager(wsQualityCheckManager));
    }

    /**
     * 删除质量检查
     */
   // @ApiOperation("删除质量检查")
    @PreAuthorize("@ss.hasPermi('ws:checkManager:remove')")
    @Log(title = "质量检查", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wsQualityCheckManagerService.deleteWsQualityCheckManagerByIds(ids));
    }

    /**
     * 图片上传
     */
    @Log(title = "图片上传", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws IOException
    {
        if (!file.isEmpty())
        {
            String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgUrl", avatar);
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }
}

