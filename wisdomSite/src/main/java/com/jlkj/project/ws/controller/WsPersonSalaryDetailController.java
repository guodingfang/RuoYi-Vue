package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsPersonSalaryDetail;
import com.jlkj.project.ws.domain.vo.WsPersonSalaryDetailVO;
import com.jlkj.project.ws.dto.WsPersonSalaryDetailDTO;
import com.jlkj.project.ws.service.IWsPersonSalaryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 工资明细 Controller
 *
 * @author liujie
 * @date 2020-07-31
 */
@RestController
@RequestMapping("/ws/personSalaryDetail")
public class WsPersonSalaryDetailController extends BaseController
{
    @Autowired
    private IWsPersonSalaryDetailService wsPersonSalaryDetailService;

    /**
     * 查询工资明细 列表
     */
   // @PreAuthorize("@ss.hasPermi('ws:personSalaryDetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsPersonSalaryDetailDTO wsPersonSalaryDetailDTO)
    {
        startPage();
        List<WsPersonSalaryDetailVO> list = wsPersonSalaryDetailService.selectWsPersonSalaryDetailList(wsPersonSalaryDetailDTO);
        return getDataTable(list);
    }

    /**
     * 导出工资明细 列表
     */
    //@PreAuthorize("@ss.hasPermi('ws:personSalaryDetail:export')")
    @Log(title = "工资明细 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsPersonSalaryDetailDTO wsPersonSalaryDetailDTO)
    {
        List<WsPersonSalaryDetailVO> list = wsPersonSalaryDetailService.selectWsPersonSalaryDetailList(wsPersonSalaryDetailDTO);
        ExcelUtil<WsPersonSalaryDetailVO> util = new ExcelUtil<WsPersonSalaryDetailVO>(WsPersonSalaryDetailVO.class);
        return util.exportExcel(list, "人员工资明细");
    }

    @Log(title = "导入工资明细模板", businessType = BusinessType.EXPORT)
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<WsPersonSalaryDetailVO> util = new ExcelUtil<WsPersonSalaryDetailVO>(WsPersonSalaryDetailVO.class);
        return util.importTemplateExcel("工资明细模板");
    }


    @Log(title = "导入工资明细", businessType = BusinessType.IMPORT)
    //@PreAuthorize("@ss.hasPermi('ws:personSalaryDetail:import')")
    @PostMapping("/importData/{projectId}")
    public AjaxResult importData(@RequestParam("file") MultipartFile file,@PathVariable("projectId") Integer projectId) throws Exception
    {
        ExcelUtil<WsPersonSalaryDetail> util = new ExcelUtil<WsPersonSalaryDetail>(WsPersonSalaryDetail.class);
        List<WsPersonSalaryDetail> salaryDetails = util.importExcel(file.getInputStream());
        String message = wsPersonSalaryDetailService.importPersonSalaryDetail(salaryDetails, projectId);
        return AjaxResult.success(message);
    }

    /**
     * 获取工资明细 详细信息
     */
    @PreAuthorize("@ss.hasPermi('ws:personSalaryDetail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsPersonSalaryDetailService.selectWsPersonSalaryDetailById(id));
    }

    /**
     * 统计工资明细 信息
     */
    //@PreAuthorize("@ss.hasPermi('ws:personSalaryDetail:statistics')")
    @GetMapping(value = "/satistics")
    public AjaxResult statisticsSalaryDetail( WsPersonSalaryDetailDTO wsPersonSalaryDetailDTO)
    {
        return AjaxResult.success(wsPersonSalaryDetailService.statisticsWsPersonSalaryDetail(wsPersonSalaryDetailDTO));
    }


    /**
     * 新增工资明细
     */
    @PreAuthorize("@ss.hasPermi('ws:personSalaryDetail:add')")
    @Log(title = "工资明细 ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WsPersonSalaryDetail wsPersonSalaryDetail)
    {
        return toAjax(wsPersonSalaryDetailService.insertWsPersonSalaryDetail(wsPersonSalaryDetail));
    }

    /**
     * 修改工资明细
     */
    @PreAuthorize("@ss.hasPermi('ws:personSalaryDetail:edit')")
    @Log(title = "工资明细 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsPersonSalaryDetail wsPersonSalaryDetail)
    {
        return toAjax(wsPersonSalaryDetailService.updateWsPersonSalaryDetail(wsPersonSalaryDetail));
    }

    /**
     * 删除工资明细
     */
    @PreAuthorize("@ss.hasPermi('ws:personSalaryDetail:remove')")
    @Log(title = "工资明细 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wsPersonSalaryDetailService.deleteWsPersonSalaryDetailByIds(ids));
    }
}
