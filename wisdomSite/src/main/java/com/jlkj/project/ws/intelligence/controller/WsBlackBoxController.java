package com.jlkj.project.ws.intelligence.controller;

import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.intelligence.domain.WsBlackBox;
import com.jlkj.project.ws.intelligence.service.IWsBlackBoxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 黑匣子信息管理Controller
 *
 * @author liujie
 * @date 2020-07-03
 */
@Api(tags = "黑匣子信息管理")
@RestController
@RequestMapping("/ws/blackBox")
public class WsBlackBoxController extends BaseController
{
    @Autowired
    private IWsBlackBoxService wsBlackBoxService;

    /**
     * 查询黑匣子信息列表
     */
    @ApiOperation("查询黑匣子信息列表")
   // @PreAuthorize("@ss.hasPermi('ws:blackBox:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsBlackBox wsBlackBox)
    {
        startPage();
        List<WsBlackBox> list = wsBlackBoxService.selectWsBlackBoxList(wsBlackBox);
        return getDataTable(list);
    }

    /**
     * 获取黑匣子信息详细信息
     */
    @ApiOperation("获取黑匣子信息详细信息")
    //@PreAuthorize("@ss.hasPermi('ws:blackBox:query')")
    @GetMapping(value = "/{equipmentType}/{serialNumber}")
    public AjaxResult getInfo(@PathVariable("equipmentType") String equipmentType,@PathVariable("serialNumber")  String serialNumber)
    {
        return AjaxResult.success(wsBlackBoxService.selectWsBlackBoxBySeriNumberAndType(serialNumber,equipmentType));
    }




}
