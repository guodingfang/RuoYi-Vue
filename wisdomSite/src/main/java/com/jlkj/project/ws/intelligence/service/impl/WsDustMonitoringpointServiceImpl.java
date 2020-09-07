package com.jlkj.project.ws.intelligence.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsProjectEquipment;
import com.jlkj.project.ws.domain.WsVideoChannelManagement;
import com.jlkj.project.ws.mapper.WsProjectEquipmentMapper;
import com.jlkj.project.ws.mapper.WsVideoChannelManagementMapper;
import com.jlkj.project.ws.service.IWsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.intelligence.mapper.WsDustMonitoringpointMapper;
import com.jlkj.project.ws.intelligence.domain.WsDustMonitoringpoint;
import com.jlkj.project.ws.intelligence.service.IWsDustMonitoringpointService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 扬尘监测点视频管理 Service业务层处理
 *
 * @author gaowei
 * @date 2020-07-07
 */
@Service
public class WsDustMonitoringpointServiceImpl implements IWsDustMonitoringpointService
{
    @Autowired
    private WsDustMonitoringpointMapper wsDustMonitoringpointMapper;

    @Autowired
    private WsProjectEquipmentMapper wsProjectEquipmentMapper;

    @Autowired
    private WsVideoChannelManagementMapper wsVideoChannelManagementMapper;

    @Autowired
    private IWsProjectService wsProjectService;

    /**
     * 查询扬尘监测点视频管理 
     *
     * @param id 扬尘监测点视频管理 ID
     * @return 扬尘监测点视频管理 
     */
    @Override
    public WsDustMonitoringpoint selectWsDustMonitoringpointById(Integer id)
    {
        return wsDustMonitoringpointMapper.selectWsDustMonitoringpointById(id);
    }

    /**
     * 查询扬尘监测点视频管理 列表
     *
     * @param wsDustMonitoringpoint 扬尘监测点视频管理
     * @return 扬尘监测点视频管理 
     */
    @Override
    public List<WsDustMonitoringpoint> selectWsDustMonitoringpointList(WsDustMonitoringpoint wsDustMonitoringpoint)
    {
        List<WsDustMonitoringpoint> listDust = new ArrayList<WsDustMonitoringpoint>();
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                wsDustMonitoringpoint.setProjectId(list.get(i).getId());
            }
            listDust = wsDustMonitoringpointMapper.selectWsDustMonitoringpointList(wsDustMonitoringpoint);
        }
        return listDust;
    }

    /**
     * 新增扬尘监测点视频管理 
     *
     * @param wsDustMonitoringpoint 扬尘监测点视频管理
     * @return 结果
     */
    @Override
    @Transactional
    public int insertWsDustMonitoringpoint(WsDustMonitoringpoint wsDustMonitoringpoint)
    {
        int monitoringpoint = 0;
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                wsDustMonitoringpoint.setProjectId(list.get(i).getId());
            }
            wsDustMonitoringpoint.setInstallData(new Date());
            wsDustMonitoringpoint.setCreatedTime(new Date());
            wsDustMonitoringpoint.setUpdatedTime(new Date());
            wsDustMonitoringpoint.setIsDelete("1");//启禁用状态(1：启用；2：禁用)
            monitoringpoint = wsDustMonitoringpointMapper.insertWsDustMonitoringpoint(wsDustMonitoringpoint);
            if(wsDustMonitoringpoint.getEquipmentnoId() != null){
                WsProjectEquipment projectEquipment = wsProjectEquipmentMapper.selectWsProjectEquipmentById(wsDustMonitoringpoint.getEquipmentnoId());
                WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
                wsProjectEquipment.setSerialNumber(projectEquipment.getSerialNumber());
                wsProjectEquipment.setEnableStatus(1);
                wsProjectEquipment.setEquipmentType(3);
                wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
            }
        }
        return monitoringpoint;
    }

    /**
     * 修改扬尘监测点视频管理 
     *
     * @param wsDustMonitoringpoint 扬尘监测点视频管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updateWsDustMonitoringpoint(WsDustMonitoringpoint wsDustMonitoringpoint)
    {
        WsDustMonitoringpoint dustMonitoringpoint = wsDustMonitoringpointMapper.selectWsDustMonitoringpointById(wsDustMonitoringpoint.getId());
        if(dustMonitoringpoint.getEquipmentnoId() != null){
            WsProjectEquipment projectEquipment = wsProjectEquipmentMapper.selectWsProjectEquipmentById(dustMonitoringpoint.getEquipmentnoId());
            WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
            wsProjectEquipment.setSerialNumber(projectEquipment.getSerialNumber());
            wsProjectEquipment.setEnableStatus(0);
            wsProjectEquipment.setEquipmentType(3);
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }

        wsDustMonitoringpoint.setUpdatedTime(new Date());
        int monitoringpointVideo = wsDustMonitoringpointMapper.updateWsDustMonitoringpoint(wsDustMonitoringpoint);

        if(wsDustMonitoringpoint.getEquipmentnoId() != null){
            WsProjectEquipment projectEquipment = wsProjectEquipmentMapper.selectWsProjectEquipmentById(wsDustMonitoringpoint.getEquipmentnoId());
            WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
            wsProjectEquipment.setEnableStatus(1);
            wsProjectEquipment.setEquipmentType(3);
            wsProjectEquipment.setSerialNumber(projectEquipment.getSerialNumber());
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }
        return monitoringpointVideo;
    }

    /**
     * 批量删除扬尘监测点视频管理 
     *
     * @param ids 需要删除的扬尘监测点视频管理 ID
     * @return 结果
     */
    @Override
    public int deleteWsMonitoringpointVideoByIds(Integer[] ids)
    {
        return wsDustMonitoringpointMapper.deleteWsMonitoringpointVideoByIds(ids);
    }

    /**
     * 删除扬尘监测点视频管理 信息
     *
     * @param id 扬尘监测点视频管理 ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteWsDustMonitoringpointById(Integer id)
    {
        WsDustMonitoringpoint monitoringpointVideo = wsDustMonitoringpointMapper.selectWsDustMonitoringpointById(id);
        if(monitoringpointVideo.getEquipmentnoId() != null){
            WsProjectEquipment projectEquipment = wsProjectEquipmentMapper.selectWsProjectEquipmentById(monitoringpointVideo.getEquipmentnoId());
            WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
            wsProjectEquipment.setSerialNumber(projectEquipment.getSerialNumber());
            wsProjectEquipment.setEnableStatus(0);
            wsProjectEquipment.setEquipmentType(3);
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }
        return wsDustMonitoringpointMapper.deleteWsDustMonitoringpointById(id);
    }

    @Override
    public List<WsProjectEquipment> selectListDustMonitoring(WsProjectEquipment wsProjectEquipment) {
        List<WsProjectEquipment> listProjectEquipment = new ArrayList<WsProjectEquipment>();
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                wsProjectEquipment.setProjectId(list.get(i).getId());
            }
            wsProjectEquipment.setEquipmentType(3);
            wsProjectEquipment.setEnableStatus(0);
            listProjectEquipment = wsProjectEquipmentMapper.selectWsProjectEquipmentList(wsProjectEquipment);
        }
        return listProjectEquipment;
    }

    @Override
    public List<WsVideoChannelManagement> listHookVideo(WsVideoChannelManagement wsVideoChannelManagement) {
        List<WsVideoChannelManagement> listVideoChannel = new ArrayList<WsVideoChannelManagement>();
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                wsVideoChannelManagement.setProjectId(list.get(i).getId());
            }
            listVideoChannel = wsVideoChannelManagementMapper.selectListHookVideo(wsVideoChannelManagement);
        }
        return listVideoChannel;
    }


    @Override
    public List<WsDustMonitoringpoint> listHookVideoRepeat(WsDustMonitoringpoint wsDustMonitoringpoint) {
        return wsDustMonitoringpointMapper.listHookVideoRepeat(wsDustMonitoringpoint);
    }

    @Override
    public int enableDisableWsDustMonitoringpoint(String isDelete,Integer id) {
        WsDustMonitoringpoint wsDustMonitoringpoint = new WsDustMonitoringpoint();
        wsDustMonitoringpoint.setId(id);
        wsDustMonitoringpoint.setIsDelete(isDelete);
        return wsDustMonitoringpointMapper.updateWsDustMonitoringpointIsDelete(wsDustMonitoringpoint);
    }
}
