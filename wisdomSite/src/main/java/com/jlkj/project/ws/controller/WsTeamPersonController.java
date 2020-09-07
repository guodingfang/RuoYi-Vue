package com.jlkj.project.ws.controller;

import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.file.FileUploadUtils;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.config.RuoYiConfig;
import com.jlkj.framework.interceptor.annotation.RepeatSubmit;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsTeamPerson;
import com.jlkj.project.ws.domain.vo.WsTeamPersonVO;
import com.jlkj.project.ws.dto.WsTeamPersonDTO;
import com.jlkj.project.ws.service.IWsTeamPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 班组人员 Controller
 *
 * @author jlkj
 * @date 2020-06-12
 */
@Api(tags = "班组人员管理")
@RestController
@RequestMapping("/ws/teamPerson")
public class WsTeamPersonController extends BaseController
{
    @Autowired
    private IWsTeamPersonService wsTeamPersonService;

    /**
     * 查询班组人员 列表
     */
    @ApiOperation(value = "查询班组人员 列表")
   // @ApiImplicitParam(name = "班组人员",value = "wsTeamPerson" ,dataType = "WsTeamPerson",example = "WsTeamPerson")
    //@PreAuthorize("@ss.hasPermi('ws:teamPerson:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsTeamPerson wsTeamPerson)
    {
        startPage();
        List<WsTeamPerson> list = wsTeamPersonService.selectWsTeamPersonList(wsTeamPerson);
        return getDataTable(list);
    }

    /**
     * 查询班组人员 列表
     */
    @ApiOperation("查询班组人员列表(含人员打卡机授权信息)")
    @ApiImplicitParam(name = "班组人员",value = "wsTeamPerson" ,dataType = "WsTeamPerson")
    @PreAuthorize("@ss.hasPermi('ws:teamPerson:authList')")
    @GetMapping("/list/auth")
    public TableDataInfo authList(WsTeamPerson wsTeamPerson)
    {
        startPage();
        List<WsTeamPersonVO> list = wsTeamPersonService.selectWsTeamPersonAuthList(wsTeamPerson);
        return getDataTable(list);
    }

    /**
     * 导出班组人员 列表
     */
    @ApiOperation("导出班组人员列表")
    @ApiImplicitParam(name = "班组人员",value = "wsTeamPerson" ,dataType = "WsTeamPerson")
    @PreAuthorize("@ss.hasPermi('ws:teamPerson:export')")
    @Log(title = "班组人员 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsTeamPerson wsTeamPerson,String sheetName)
    {
        List<WsTeamPerson> list = wsTeamPersonService.selectWsTeamPersonList(wsTeamPerson);
        ExcelUtil<WsTeamPerson> util = new ExcelUtil<WsTeamPerson>(WsTeamPerson.class);
        return util.exportExcel(list, StringUtils.isEmpty(sheetName)?"班组人员列表":sheetName);
    }



    @Log(title = "导入班组人员", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('ws:teamPerson:import')")
    @PostMapping("/importData/{unitId}")
    public AjaxResult importData(@RequestParam("file") MultipartFile file,@PathVariable("unitId") Integer unitId) throws Exception
    {
        ExcelUtil<WsTeamPerson> util = new ExcelUtil<WsTeamPerson>(WsTeamPerson.class);
        List<WsTeamPerson> personList = util.importExcel(file.getInputStream());
        String message = wsTeamPersonService.importPerson(personList,unitId);
        return AjaxResult.success(message);
    }


    @Log(title = "导出班组人员模板", businessType = BusinessType.EXPORT)
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<WsTeamPerson> util = new ExcelUtil<WsTeamPerson>(WsTeamPerson.class);
        return util.importTemplateExcel("班组人员模板");
    }

    /**
     * 获取班组人员 详细信息
     */
    @ApiOperation(value="获取班组人员详细信息" )
   // @ApiImplicitParam(name = "人员ID",value = "id" ,dataType = "int",paramType = "path")
   @PreAuthorize("@ss.hasPermi('ws:teamPerson:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsTeamPersonService.selectWsTeamPersonById(id));
    }

    /**
     * 新增班组人员
     */
    @ApiOperation("新增班组人员")
    //@ApiImplicitParam(name = "班组人员",value = "wsTeamPerson" ,dataType = "WsTeamPerson",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:teamPerson:add')")
    @Log(title = "班组人员 ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody WsTeamPerson wsTeamPerson)
    {
        return toAjax(wsTeamPersonService.insertWsTeamPerson(wsTeamPerson));
    }

    /** 批量新增新增班组人员
     */
    @ApiOperation("批量新增班组人员")
    //@ApiImplicitParam(name = "班组人员",value = "personList" ,dataType = "WsTeamPerson",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:teamPerson:batchAdd')")
    @Log(title = "班组人员 ", businessType = BusinessType.INSERT)
    @PostMapping("/batch")
    public AjaxResult batchAdd( @RequestBody WsTeamPerson wsTeamPerson)
    {
        if (StringUtils.isNull(wsTeamPerson)|| StringUtils.isNull(wsTeamPerson.getPersonLibraryIds())) {
          return AjaxResult.error("请选择人员信息")  ;
        }

        return toAjax(wsTeamPersonService.batchInsertWsTeamPerson(wsTeamPerson));
    }


    /**
     * 修改班组人员
     */
    @RepeatSubmit
    @ApiOperation("修改班组人员")
    @ApiImplicitParam(name = "班组人员",value = "wsTeamPerson" ,dataType = "WsTeamPerson",paramType = "body")
    @PreAuthorize("@ss.hasPermi('ws:teamPerson:edit')")
    @Log(title = "班组人员 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody WsTeamPerson wsTeamPerson)
    {
        return toAjax(wsTeamPersonService.updateWsTeamPerson(wsTeamPerson));
    }

    /**
     * 修改班组人员
     */
    @RepeatSubmit
    @ApiOperation("批量修改班组人员")
    @PreAuthorize("@ss.hasPermi('ws:teamPerson:batchChange')")
    @Log(title = "班组人员 ", businessType = BusinessType.UPDATE)
    @PutMapping("/batchChange")
    public AjaxResult batchChanges(@RequestBody WsTeamPersonDTO wsTeamPersonDTO)
    {
        return toAjax(wsTeamPersonService.batchUpdateWsTeamPerson(wsTeamPersonDTO));
    }


    /**
     * 删除班组人员
     */
    @ApiOperation("删除班组人员")
    @ApiImplicitParam(name = "人员ID集合",value = "ids" ,dataType = "int",paramType = "path")
    @PreAuthorize("@ss.hasPermi('ws:teamPerson:remove')")
    @Log(title = "班组人员 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wsTeamPersonService.deleteWsTeamPersonByIds(ids));
    }


    @ApiOperation("上传人脸")
    //@ApiImplicitParam(name="file", value="人脸图片",dataType  ="MultipartFile")
    @PreAuthorize("@ss.hasPermi('ws:teamPerson:face')")
    @PostMapping("/upload/face")
    public AjaxResult uploadFace(@RequestParam("file") MultipartFile file) throws IOException
    {
        // 上传文件路径
        String filePath = RuoYiConfig.getUploadPath();
        // 上传并返回新文件路径
        filePath = FileUploadUtils.upload(filePath, file);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("faceUrl",filePath);
        return ajax;
    }


    /**
     * 查询花名册 列表
     */
    @ApiOperation("查询花名册列表")
    @ApiImplicitParam(name = "花名册",value = "wsTeamPerson" ,dataType = "WsTeamPerson")
    @GetMapping("/rosterList")
    public TableDataInfo rosterList(WsTeamPerson wsTeamPerson)
    {
        startPage();
        List<WsTeamPerson> list = wsTeamPersonService.selectWsTeamPersonListRoster(wsTeamPerson);
        return getDataTable(list);
    }

    /**
     * 导出花名册 列表
     */
    @ApiOperation("导出花名册列表")
    @ApiImplicitParam(name = "花名册",value = "wsTeamPerson" ,dataType = "WsTeamPerson")
    @Log(title = "花名册 ", businessType = BusinessType.EXPORT)
    @GetMapping("/rosterExport")
    public AjaxResult rosterExport(WsTeamPerson wsTeamPerson)
    {
        List<WsTeamPerson> list = wsTeamPersonService.selectWsTeamPersonListRoster(wsTeamPerson);
        ExcelUtil<WsTeamPerson> util = new ExcelUtil<WsTeamPerson>(WsTeamPerson.class);
        return util.exportExcel(list, "花名册");
    }
}
