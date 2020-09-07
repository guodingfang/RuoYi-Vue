package com.jlkj.project.ws.controller;

import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.redis.ResultData;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.domain.WsStartPTZControl;
import com.jlkj.project.ws.domain.WsVideoChannelManagement;
import com.jlkj.project.ws.domain.WsVideoEquipmentManagement;
import com.jlkj.project.ws.domain.vo.WsVideoChannelBindingVO;
import com.jlkj.project.ws.service.IWsVideoEquipmentManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 视频设备管理 Controller
 *
 * @author jlkj
 * @date 2020-07-07
 */
@Api(tags = "视频设备管理接口")
@RestController
@RequestMapping("/ws/Video/equipment")
public class WsVideoEquipmentManagementController extends BaseController {
    @Autowired
    private IWsVideoEquipmentManagementService wsVideoEquipmentManagementService;

    /**
     * 查询视频设备管理 列表
     */
    @ApiOperation("动态条件查询所有视频设备管理")
    // @PreAuthorize("@ss.hasPermi('ws:Video/equipmen:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsVideoEquipmentManagement wsVideoEquipmentManagement) {
        startPage();
        List<WsVideoEquipmentManagement> list = wsVideoEquipmentManagementService.selectWsVideoEquipmentManagementList(wsVideoEquipmentManagement);
        return getDataTable(list);
    }


    /**
     * 获取视频设备管理 详细信息
     */
    @ApiOperation("根据设备序列号查询单个视频设备管理信息")
    // @PreAuthorize("@ss.hasPermi('ws:Video/equipmen:selete')")
    @GetMapping(value = "/select/bySerialNumber/{equipmentSerialnumber}")
    public AjaxResult getInfoBySerialNumber(@PathVariable("equipmentSerialnumber") String equipmentSerialnumber) {
        return AjaxResult.success(wsVideoEquipmentManagementService.selectWsVideoEquipmentManagementBySerialNumber(equipmentSerialnumber));
    }

    /**
     * 获取视频设备管理 详细信息
     */
    @ApiOperation("根据ID查询单个视频设备管理信息")
    //@PreAuthorize("@ss.hasPermi('ws:Video/equipmen:selete')")
    @GetMapping(value = "/select/byId/{id}")
    public AjaxResult getInfoById(@PathVariable("id") Integer id) {
        return AjaxResult.success(wsVideoEquipmentManagementService.selectWsVideoEquipmentManagementById(id));
    }

    /**
     * 查询视频设备记录数
     */
    @ApiOperation("查询视频设备记录数")
//    @PreAuthorize("@ss.hasPermi('ws:Video/equipmen:seleteCount')")
    @GetMapping(value = "/select/count")
    public AjaxResult getCount() {
        return AjaxResult.success(wsVideoEquipmentManagementService.selectRecordCount());
    }

    /**
     * 查询视频设备在线状态
     */
    @ApiOperation("查询视频设备在线状态")
//    @PreAuthorize("@ss.hasPermi('ws:Video/equipmen:seleteOnlineStatus')")
    @PostMapping(value = "/select/onlineStatus")
    public AjaxResult getOnlineStatus(@RequestBody WsVideoEquipmentManagement wsVideoEquipmentManagement) {
        return AjaxResult.success(wsVideoEquipmentManagementService.selectWsVideoEquipmentOnlineStatus(wsVideoEquipmentManagement.getEquipmentSerialnumber()));
    }

    /**
     * 查询录像机通道下视频信息
     */
    @ApiOperation("查询录像机通道下视频信息")
    //@PreAuthorize("@ss.hasPermi('ws:Video/equipmen:videoChannel')")
    @PostMapping(value = "/select/videoChannel")
    public AjaxResult getVideoChannel(@RequestBody WsVideoEquipmentManagement wsVideoEquipmentManagement) {
        return AjaxResult.success(wsVideoEquipmentManagementService.selectVideoEquipmentChannel(wsVideoEquipmentManagement));
    }

    /**
     * 查看在线视频
     */
    @ApiOperation("查看在线视频")
    //@PreAuthorize("@ss.hasPermi('ws:Video/equipmen:paly')")
    @PostMapping(value = "/select/play")
    public AjaxResult playVideo(@RequestBody WsVideoChannelManagement wsVideoChannelManagement) {
        return AjaxResult.success(wsVideoEquipmentManagementService.playVideoBySerialnumber(wsVideoChannelManagement));
    }


    /**
     * 新增视频设备管理
     */
    @ApiOperation("新增视频设备管理")
    //@PreAuthorize("@ss.hasPermi('ws:Video/equipmen:add')")
    @Log(title = "新增视频设备管理 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsVideoEquipmentManagement wsVideoEquipmentManagement) {
        ResultData resultData = wsVideoEquipmentManagementService.insertWsVideoEquipmentManagement(wsVideoEquipmentManagement);
        return new AjaxResult(Integer.parseInt(resultData.getCode()),resultData.getMessage());
    }

    /**
     * 修改视频设备管理
     */
    @ApiOperation("修改视频设备管理")
    //@PreAuthorize("@ss.hasPermi('ws:Video/equipmen:edit')")
    @Log(title = "修改视频设备管理 ", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody WsVideoEquipmentManagement wsVideoEquipmentManagement) {
        return toAjax(wsVideoEquipmentManagementService.updateWsVideoEquipmentManagement(wsVideoEquipmentManagement));
    }

    /**
     * 视频设备转场
     */
    @ApiOperation("视频设备转场")
    //@PreAuthorize("@ss.hasPermi('ws:Video/equipmen:change')")
    @Log(title = "视频设备转场 ", businessType = BusinessType.UPDATE)
    @PutMapping("/project/change")
    public AjaxResult projectChange(@RequestBody WsVideoEquipmentManagement wsVideoEquipmentManagement) {
        int result = wsVideoEquipmentManagementService.updateProjectBySerialnumber(wsVideoEquipmentManagement);
        if (result == 0) {
            return AjaxResult.error("该设备已被使用，请先联系客户删除");
        }
        if (result == 2) {
            return AjaxResult.error("该设备不支持转场");
        }
        return toAjax(result);
    }

    /**
     * 摄像头设备绑定通道
     */
    @ApiOperation("摄像头设备绑定通道")
    //@PreAuthorize("@ss.hasPermi('ws:Video/equipmen:binding')")
    @Log(title = "摄像头设备绑定通道 ", businessType = BusinessType.UPDATE)
    @PutMapping("/channel/binding")
    public AjaxResult channelBinding(@RequestBody WsVideoChannelBindingVO wsVideoChannelBindingVO) {
        int result = wsVideoEquipmentManagementService.videoChannelBinding(wsVideoChannelBindingVO);
        if (result == 0){
            return AjaxResult.error("请核对安装地址和通道");
        }
        if (result == 2){
            return AjaxResult.error("请核对通道号是否超过设备最大通道号");
        }
        return toAjax(result);
    }

    /**
     * 开启云台控制
     *
     * @param wsStartPTZControl 云台控制对象
     * @return 结果
     */
    @ApiOperation("开启云台控制")
  //  @PreAuthorize("@ss.hasPermi('ws:Video/equipmen:start')")
    @Log(title = "开启云台控制 ", businessType = BusinessType.UPDATE)
    @PostMapping("/start/PTZ")
    public AjaxResult startPTZ(@RequestBody WsStartPTZControl wsStartPTZControl) {

        ResultData resultData = wsVideoEquipmentManagementService.startPzt(wsStartPTZControl);

        return new AjaxResult(Integer.parseInt(resultData.getCode()),resultData.getMessage());
    }

    /**
     * 图片抓拍
     *
     * @param wsStartPTZControl 云台控制对象
     * @return 结果
     */
    @ApiOperation("图片抓拍")
    //  @PreAuthorize("@ss.hasPermi('ws:Video/equipmen:capture')")
    @Log(title = "图片抓拍 ", businessType = BusinessType.UPDATE)
    @PostMapping("/capture")
    public AjaxResult capturePic(@RequestBody WsStartPTZControl wsStartPTZControl) {

        WsStartPTZControl result = wsVideoEquipmentManagementService.capturePic(wsStartPTZControl);

        return AjaxResult.success(result);
    }

    /**
     * 删除视频设备管理
     */
    @ApiOperation("删除视频设备管理")
   //@PreAuthorize("@ss.hasPermi('ws:Video/equipmen:remove')")
    @Log(title = "视频设备管理 ", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{equipmentSerialnumber}")
    public AjaxResult remove(@PathVariable("equipmentSerialnumber") String equipmentSerialnumber) {
        int result = wsVideoEquipmentManagementService.deleteWsVideoEquipmentManagementBySerialnumber(equipmentSerialnumber);
        if (result == 0) {
            return AjaxResult.error("该设备已被使用，请先联系客户删除");
        }
        if (result == 2) {
            return AjaxResult.error("该设备通道下已绑定设备，请先联系删除");
        }
        return toAjax(result);
    }

    /**
     * 删除视频设备管理
     */
    @ApiOperation("删除视频设备通道信息")
    //@PreAuthorize("@ss.hasPermi('ws:Video/equipmen:deleteChannel')")
    @Log(title = "视频设备管理 ", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/channel/{id}")
    public AjaxResult deleteChannel(@PathVariable("id") int id) {
        int result = wsVideoEquipmentManagementService.deleteWsVideoChannelById(id);
        if (result == 0) {
            return AjaxResult.error("该设备已被使用，请先联系客户删除");
        }
        return toAjax(result);
    }


}
