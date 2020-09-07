package com.jlkj.project.ws.intelligence.controller;

import java.util.List;

import com.jlkj.common.constant.UserConstants;
import com.jlkj.project.ws.domain.WsProjectEquipment;
import com.jlkj.project.ws.domain.WsVideoChannelManagement;
import com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout;
import com.jlkj.project.ws.intelligence.service.IWsProjectFieldLayoutService;
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
import com.jlkj.project.ws.intelligence.domain.WsTowercraneEquipment;
import com.jlkj.project.ws.intelligence.service.IWsTowercraneEquipmentService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 项目塔吊设备管理 Controller
 *
 * @author gaowei
 * @date 2020-07-07
 */
@Api(value = "项目塔吊设备管理",tags = "项目塔吊设备管理页面")
@RestController
@RequestMapping("/ws/towercraneEquipment")
public class WsTowercraneEquipmentController extends BaseController
{
    @Autowired
    private IWsTowercraneEquipmentService wsTowercraneEquipmentService;

    @Autowired
    private IWsProjectFieldLayoutService wsProjectFieldLayoutService;

    /**
     * 查询项目塔吊设备管理 列表/ 防碰撞设备位置信息查询在场设备
     */
    @ApiOperation("项目塔吊设备管理列表数据")
    @ApiImplicitParam(name = "wsTowercraneEquipment", value = "项目塔吊设备管理实体项：条件：presence（1：在场；2：离场）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsTowercraneEquipment wsTowercraneEquipment)
    {
        startPage();
        List<WsTowercraneEquipment> list = wsTowercraneEquipmentService.selectWsTowercraneEquipmentList(wsTowercraneEquipment);
        return getDataTable(list);
    }

    /**
     * 查询监测设备（塔机监测设备）
     */
    @ApiOperation("查询监测设备")
    @ApiImplicitParam(name = "wsProjectEquipment", value = "工程设备绑定实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:list')")
    @GetMapping("/listMonitoringEquipment")
    public TableDataInfo listMonitoringEquipment(WsProjectEquipment wsProjectEquipment)
    {
        startPage();
        List<WsProjectEquipment> list = wsTowercraneEquipmentService.listMonitoringEquipment(wsProjectEquipment);
        return getDataTable(list);
    }

    /**
     * 查询塔机人脸设备
     */
    @ApiOperation("查询塔机人脸设备")
    @ApiImplicitParam(name = "wsProjectEquipment", value = "工程设备绑定实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:list')")
    @GetMapping("/listTowerCraneFace")
    public TableDataInfo listTowerCraneFace(WsProjectEquipment wsProjectEquipment)
    {
        startPage();
        List<WsProjectEquipment> list = wsTowercraneEquipmentService.listTowerCraneFace(wsProjectEquipment);
        return getDataTable(list);
    }

    /**
     * 查询吊钩视频监测设备（塔机吊钩视频监测设备）
     */
    @ApiOperation("查询塔机吊钩视频监测设备")
    @ApiImplicitParam(name = "wsVideoChannelManagement", value = "视频通道管理",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:list')")
    @GetMapping("/listHookVideo")
    public TableDataInfo listHookVideo(WsVideoChannelManagement wsVideoChannelManagement)
    {
        startPage();
        List<WsVideoChannelManagement> list = wsTowercraneEquipmentService.listHookVideo(wsVideoChannelManagement);
        return getDataTable(list);
    }

    /**
     * 新增修改查询吊钩视频监测设备是否重复
     */
    @ApiOperation("新增修改查询吊钩视频监测设备是否重复：返回list.size() > 0 就重复")
    @ApiImplicitParam(name = "wsTowercraneEquipment", value = "项目塔吊设备管理实体项：传参吊钩视频id：cliverVideoId",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:list')")
    @GetMapping("/listHookVideoRepeat")
    public TableDataInfo listHookVideoRepeat(WsTowercraneEquipment wsTowercraneEquipment)
    {
        startPage();
        List<WsTowercraneEquipment> list = wsTowercraneEquipmentService.listHookVideoRepeat(wsTowercraneEquipment);
        return getDataTable(list);
    }

    /**
     * 获取项目塔吊设备管理 详细信息
     */
    @ApiOperation("获取项目塔吊设备管理 详细信息")
    @ApiImplicitParam(name = "主键", value = "id）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:query')")
    @GetMapping(value = "/query/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(wsTowercraneEquipmentService.selectWsTowercraneEquipmentById(Integer.parseInt(id)));
    }

    /**
     * 获取项目场布图信息/ 防碰撞设备场布图信息查询
     */
    @ApiOperation("获取项目场布图信息")
    @ApiImplicitParam(name = "项目id", value = "projectId）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:query')")
    @GetMapping(value = "/fieldLayout/{projectId}")
    public AjaxResult fieldLayout(@PathVariable("projectId") Integer projectId)
    {
        WsProjectFieldLayout wsProjectFieldLayout = wsProjectFieldLayoutService.selectWsProjectFieldLayoutByProjectAndType(projectId,1);
        return AjaxResult.success(wsProjectFieldLayout);
    }

    /**
     * 新增项目塔吊设备管理 
     */
    @ApiOperation("新增项目塔吊设备管理")
    @ApiImplicitParam(name = "wsTowercraneEquipment", value = "项目塔吊设备管理实体项）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:add')")
    @Log(title = "项目塔吊设备管理 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsTowercraneEquipment wsTowercraneEquipment)
    {
        if (UserConstants.NOT_UNIQUE.equals(wsTowercraneEquipmentService.selfNumbering(wsTowercraneEquipment)))
        {
            return AjaxResult.error("新增项目塔吊设备'" + wsTowercraneEquipment.getSelfNumbering() + "'失败，该自编号已存在");
        }
        return toAjax(wsTowercraneEquipmentService.insertWsTowercraneEquipment(wsTowercraneEquipment));
    }

    /**
     * 修改项目塔吊设备管理 
     */
    @ApiOperation("设备管理=修改")
    @ApiImplicitParam(name = "wsTowercraneEquipment", value = "项目塔吊设备管理实体项）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:edit')")
    @Log(title = "设备管理 ", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody WsTowercraneEquipment wsTowercraneEquipment)
    {
        if (UserConstants.NOT_UNIQUE.equals(wsTowercraneEquipmentService.selfNumbering(wsTowercraneEquipment)))
        {
            return AjaxResult.error("修改项目塔吊设备'" + wsTowercraneEquipment.getSelfNumbering() + "'失败，该自编号已存在");
        }
        return toAjax(wsTowercraneEquipmentService.updateWsTowercraneEquipment(wsTowercraneEquipment));
    }

    /**
     * 离场
     */
    @ApiOperation("离场")
    @ApiImplicitParam(name = "wsTowercraneEquipment", value = "项目塔吊设备管理实体项）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:edit')")
    @Log(title = "离场 ", businessType = BusinessType.UPDATE)
    @PutMapping("/departure/{id}")
    public AjaxResult departure(@PathVariable("id") String id)
    {
        return toAjax(wsTowercraneEquipmentService.departureWsTowercraneEquipment( Integer.parseInt(id)));
    }

    /**
     * 重新进场
     */
    @ApiOperation("重新进场=修改")
    @ApiImplicitParam(name = "wsTowercraneEquipment", value = "项目塔吊设备管理实体项）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:edit')")
    @Log(title = "重新进场 ", businessType = BusinessType.UPDATE)
    @PutMapping("/reentry")
    public AjaxResult reentry(@RequestBody WsTowercraneEquipment wsTowercraneEquipment)
    {
        if (UserConstants.NOT_UNIQUE.equals(wsTowercraneEquipmentService.selfNumbering(wsTowercraneEquipment)))
        {
            return AjaxResult.error("重新进场塔吊设备'" + wsTowercraneEquipment.getSelfNumbering() + "'失败，该自编号已存在");
        }
        return toAjax(wsTowercraneEquipmentService.reentryWsTowercraneEquipment(wsTowercraneEquipment));
    }

    /**
     * 删除项目塔吊设备管理 
     */
    @ApiOperation("删除项目塔吊设备管理 ")
    @ApiImplicitParam(name = "主键", value = "id）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:remove')")
    @Log(title = "项目塔吊设备管理 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable("id") Integer id)
    {
        return toAjax(wsTowercraneEquipmentService.deleteWsTowercraneEquipmentById(id));
    }

    /**
     * 上传场布图
     */
    @ApiOperation("上传场布图")
    @ApiImplicitParam(name = "wsProjectFieldLayout", value = "上传场布图实体项:projectId ; fieldlayoutAddress; id：主键存在修改，没有新增）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:add')")
    @Log(title = "项目塔吊设备管理 ", businessType = BusinessType.INSERT)
    @PostMapping("/addFieldLayout")
    public AjaxResult addFieldLayout(@RequestBody WsProjectFieldLayout wsProjectFieldLayout)
    {
        return toAjax(wsTowercraneEquipmentService.insertWsProjectFieldLayout(wsProjectFieldLayout));
    }

    /**
     * 防碰撞设置：添加设备
     */
    @ApiOperation("防碰撞设置：查询未设置位置设备信息")
    @ApiImplicitParam(name = "wsTowercraneEquipment", value = "项目塔吊设备管理实体",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:list')")
    @GetMapping("/listEquipment")
    public TableDataInfo listEquipment(WsTowercraneEquipment wsTowercraneEquipment)
    {
        startPage();
        List<WsTowercraneEquipment> list = wsTowercraneEquipmentService.selectlistEquipment(wsTowercraneEquipment);
        return getDataTable(list);
    }

    /**
     * 防碰撞设置：保存位置
     */
    @ApiOperation("防碰撞设置：保存位置")
    @ApiImplicitParam(name = "wsTowercraneEquipment", value = "项目塔吊设备管理实体项）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:add')")
    @Log(title = "项目塔吊设备管理 ", businessType = BusinessType.INSERT)
    @PostMapping("/addPosition")
    public AjaxResult addPosition(@RequestBody WsTowercraneEquipment wsTowercraneEquipment)
    {
        return toAjax(wsTowercraneEquipmentService.insertPosition(wsTowercraneEquipment));
    }


    /**
     * 删除场布图设备位置
     */
    @ApiOperation("删除场布图设备位置")
    @ApiImplicitParam(name = "wsTowercraneEquipment", value = "项目塔吊设备管理实体项）",required = false)
    @PreAuthorize("@ss.hasPermi('ws:towercraneEquipment:edit')")
    @Log(title = "删除场布图设备位置 ", businessType = BusinessType.UPDATE)
    @PutMapping("/position/{id}")
    public AjaxResult editPosition(@PathVariable("id") String id)
    {
        return toAjax(wsTowercraneEquipmentService.editWsTowercraneEquipment(id));
    }
}
