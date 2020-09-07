package com.jlkj.project.ws.intelligence.controller;

import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.ws.intelligence.domain.WsElevatorMonitorData;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorMonitorDataVO;
import com.jlkj.project.ws.intelligence.service.IWsElevatorMonitorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 施工电梯实时数据管理Controller
 *
 * @author liujie
 * @date 2020-07-03
 */
@RestController
@RequestMapping("/ws/elevatorMonitorData")
public class WsElevatorMonitorDataController extends BaseController
{
    @Autowired
    private IWsElevatorMonitorDataService wsElevatorMonitorDataService;

    /**
     * 查询施工电梯实时数据管理列表
     */
   // @PreAuthorize("@ss.hasPermi('ws:elevatorMonitorData:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsElevatorMonitorData wsElevatorMonitorData)
    {
        startPage();
        List<WsElevatorMonitorData> list = wsElevatorMonitorDataService.selectWsElevatorMonitorDataList(wsElevatorMonitorData);
        return getDataTable(list);
    }

    /**
     * 查询施工电梯实时数据
     */
   // @PreAuthorize("@ss.hasPermi('ws:elevatorMonitorData:realTime')")
    @GetMapping("/realTime")
    public AjaxResult getMonitorDeviceRealTimeData(WsElevatorMonitorData wsElevatorMonitorData)
    {
       WsElevatorMonitorDataVO  vo= wsElevatorMonitorDataService.findWsElevatorMonitorDataBySerialNumber(wsElevatorMonitorData);
        return AjaxResult.success(vo);
    }




}
