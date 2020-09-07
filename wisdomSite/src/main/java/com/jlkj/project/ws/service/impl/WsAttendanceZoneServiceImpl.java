package com.jlkj.project.ws.service.impl;

import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.ws.domain.WsAttendanceMachine;
import com.jlkj.project.ws.domain.WsAttendanceZone;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.mapper.WsAttendanceMachineMapper;
import com.jlkj.project.ws.mapper.WsAttendanceZoneMapper;
import com.jlkj.project.ws.service.IWsAttendanceZoneService;
import com.jlkj.project.ws.service.IWsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 考勤分区 Service业务层处理
 *
 * @author gaowei
 * @date 2020-06-23
 */
@Service
public class WsAttendanceZoneServiceImpl implements IWsAttendanceZoneService
{
    @Autowired
    private WsAttendanceZoneMapper wsAttendanceZoneMapper;

    @Autowired
    private WsAttendanceMachineMapper wsAttendanceMachineMapper;

    @Autowired
    private IWsProjectService wsProjectService;

    /**
     * 查询考勤分区
     *
     * @param id 考勤分区 ID
     * @return 考勤分区
     */
    @Override
    public WsAttendanceZone selectWsAttendanceZoneById(Integer id)
    {
        return wsAttendanceZoneMapper.selectWsAttendanceZoneById(id);
    }

    /**
     * 查询考勤分区 列表
     *
     * @param wsAttendanceZone 考勤分区
     * @return 考勤分区
     */
    @Override
    public List<WsAttendanceZone> selectWsAttendanceZoneList(WsAttendanceZone wsAttendanceZone)
    {
        List<WsProject> projectScope = null;
        if(StringUtils.isNull(wsAttendanceZone)||StringUtils.isNull(wsAttendanceZone.getProjectId())){
            projectScope = SecurityUtils.getProjectList();
        }
        return wsAttendanceZoneMapper.selectWsAttendanceZoneWithMachineList(wsAttendanceZone,projectScope);
    }

    /**
     * 新增考勤分区
     *
     * @param wsAttendanceZone 考勤分区
     * @return 结果
     */
    @Override
    public int insertWsAttendanceZone(WsAttendanceZone wsAttendanceZone)
    {
        if (StringUtils.isNull(wsAttendanceZone)) {
            throw new CustomException("考勤区域不能为空");
        }

        int row = wsAttendanceZoneMapper.insertWsAttendanceZone(wsAttendanceZone);
        if ( StringUtils.isNotEmpty(wsAttendanceZone.getMachines())) {
            List<String> list = wsAttendanceZone.getMachines().stream().map(WsAttendanceMachine::getSerialNumber).collect(Collectors.toList());
            String[] serialNumbers = list.toArray(new String[list.size()]);
            if(StringUtils.isNotEmpty(serialNumbers)){
                wsAttendanceMachineMapper.batchUpdateZone(serialNumbers,wsAttendanceZone.getId());
            }

        }
        return row;
    }

    /**
     * 修改考勤分区
     *
     * @param wsAttendanceZone 考勤分区
     * @return 结果
     */
    @Override
    public int updateWsAttendanceZone(WsAttendanceZone wsAttendanceZone)
    {
        WsAttendanceZone zone = wsAttendanceZoneMapper.selectWsAttendanceZoneById(wsAttendanceZone.getId());
        if (StringUtils.isNull(zone)) {
            throw new CustomException("分区不存在");
        }
        List<WsAttendanceMachine> wsAttendanceMachines = wsAttendanceMachineMapper.selectWsAttendanceMachineListByZone(wsAttendanceZone.getId());

        List<String> oldSerialNumbers=null;
        List<String> newSerialNumbers=new ArrayList<>();
        if (StringUtils.isNotEmpty(wsAttendanceMachines)) {
            oldSerialNumbers = wsAttendanceMachines.stream().map(WsAttendanceMachine::getSerialNumber).collect(Collectors.toList());
        }
        for (WsAttendanceMachine machine : wsAttendanceZone.getMachines()) {
            if(StringUtils.isNotEmpty(machine.getSerialNumber())){//新增
                newSerialNumbers.add(machine.getSerialNumber());
            }else{//原来已绑定设备
                if(StringUtils.isNotEmpty(oldSerialNumbers)&&oldSerialNumbers.contains(machine.getSerialNumber())){
                    oldSerialNumbers.remove(machine.getSerialNumber());
                }
            }
        }
        //更新新增绑定设备区域
        if (StringUtils.isNotEmpty(newSerialNumbers)) {
            wsAttendanceMachineMapper.batchUpdateZone(newSerialNumbers.toArray(new String[newSerialNumbers.size()]),wsAttendanceZone.getId());
        }

        //删除解绑设备区域，设置为默认区域
        if (StringUtils.isNotEmpty(oldSerialNumbers)) {
            wsAttendanceMachineMapper.batchUpdateZone(oldSerialNumbers.toArray(new String[oldSerialNumbers.size()]),0);
        }
        return wsAttendanceZoneMapper.updateWsAttendanceZone(wsAttendanceZone);
    }

    /**
     * 批量删除考勤分区
     *
     * @param ids 需要删除的考勤分区 ID
     * @return 结果
     */
    @Override
    public int deleteWsAttendanceZoneByIds(Integer[] ids)
    {
        return wsAttendanceZoneMapper.deleteWsAttendanceZoneByIds(ids);
    }

    /**
     * 删除考勤分区 信息
     *
     * @param id 考勤分区 ID
     * @return 结果
     */
    @Override
    public int deleteWsAttendanceZoneById(Integer id)
    {
        int count = wsAttendanceZoneMapper.existedAttendanceMachine(id);
        if(count>0){
            throw new CustomException("分区下存在打卡机，不可删除");
        }
        return wsAttendanceZoneMapper.deleteWsAttendanceZoneById(id);
    }

    @Override
    public List<WsAttendanceZone> statisticsMachineListByZone(WsAttendanceZone wsAttendanceZone) {

        return wsAttendanceZoneMapper.statisticsMachineOfByZone(wsAttendanceZone);
    }
}
