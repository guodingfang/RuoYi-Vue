package com.jlkj.project.ws.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.constant.IntelligentMonitoringConstants;
import com.jlkj.common.constant.LaborConstants;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.framework.redis.KaoQinInterUtil;
import com.jlkj.framework.redis.ResultData;
import com.jlkj.project.ws.domain.WsAttendanceBinding;
import com.jlkj.project.ws.domain.WsAttendanceMachine;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.mapper.WsAttendanceBindingMapper;
import com.jlkj.project.ws.mapper.WsAttendanceMachineMapper;
import com.jlkj.project.ws.service.IWsAttendanceMachineService;
import com.jlkj.project.ws.service.IWsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 考勤机 Service业务层处理
 *
 * @author liujie
 * @date 2020-06-23
 */
@Service
public class WsAttendanceMachineServiceImpl implements IWsAttendanceMachineService
{
    @Autowired
    private WsAttendanceMachineMapper wsAttendanceMachineMapper;

    @Autowired
    private WsAttendanceBindingMapper wsAttendanceBindingMapper;

    @Autowired
    private IWsProjectService wsProjectService;

    /**
     * 查询考勤机
     *
     * @param id 考勤机 ID
     * @return 考勤机
     */
    @Override
    public WsAttendanceMachine selectWsAttendanceMachineById(Integer id)
    {
        WsAttendanceMachine wsAttendanceMachine = wsAttendanceMachineMapper.selectWsAttendanceMachineById(id);
        if(null==wsAttendanceMachine) return wsAttendanceMachine;
        ResultData resultData = KaoQinInterUtil.getDeviceInfo(wsAttendanceMachine.getSerialNumber());
        wsAttendanceMachine.setOnline("离线");
        if(LaborConstants.SUCCESS.equals(resultData.getCode())){
            JSONObject result = resultData.getData();
            JSONObject data = result.getJSONObject("data");
            if(LaborConstants.ONLINE.equals(data.getInteger("status").toString())){
                wsAttendanceMachine.setOnline("在线");
            }
        }
        return wsAttendanceMachine;
    }

    /**
     * 查询考勤机 列表
     *
     * @param wsAttendanceMachine 考勤机
     * @return 考勤机
     */
    @Override
    public List<WsAttendanceMachine> selectWsAttendanceMachineList(WsAttendanceMachine wsAttendanceMachine)
    {
        //项目信息为空时添加数据范围
        List<WsProject> projectScope = null;
        if(StringUtils.isNull(wsAttendanceMachine)||StringUtils.isNull(wsAttendanceMachine.getProjectId())){
            projectScope = SecurityUtils.getProjectList();
        }

        List<WsAttendanceMachine> wsAttendanceMachines = wsAttendanceMachineMapper.selectWsAttendanceMachineList(wsAttendanceMachine,projectScope);
        if (null==wsAttendanceMachines||wsAttendanceMachines.size()==0) {
            return wsAttendanceMachines;
        }
        //获取所有设备在线状态并存储至map
        ResultData resultData = KaoQinInterUtil.getDeviceInfoList();
        HashMap<String,String> deviceStatusMap = new HashMap<>();
        if(LaborConstants.SUCCESS.equals(resultData.getCode())){
            JSONArray dataList = resultData.getData().getJSONObject("data").getJSONArray("content");
            for(int i=0;i<dataList.size();i++){
                JSONObject data = dataList.getJSONObject(i);
                deviceStatusMap.put(data.getString("deviceKey"),data.getInteger("status").toString());
            }
        }

        //遍历考勤机列表，添加设备状态
       List<WsAttendanceMachine> result = new ArrayList<>();
        for (WsAttendanceMachine attendanceMachine : wsAttendanceMachines) {
            if(deviceStatusMap.containsKey(attendanceMachine.getSerialNumber())){
                String  status = deviceStatusMap.get(attendanceMachine.getSerialNumber());
                attendanceMachine.setOnline(IntelligentMonitoringConstants.ONLINE.equals(status)?"在线":"离线");
            }else {
                attendanceMachine.setOnline("离线");
            }
            result.add(attendanceMachine);
        }

        return result;
    }

    @Override
    public List<WsAttendanceMachine> selectWsAttendanceMachineListByZone(Integer zoneId) {
        return wsAttendanceMachineMapper.selectWsAttendanceMachineListByZone(zoneId);
    }

    /**
     * 新增考勤机
     *
     * @param wsAttendanceMachine 考勤机
     * @return 结果
     */
    @Override
    public int insertWsAttendanceMachine(WsAttendanceMachine wsAttendanceMachine)
    {
        int number = wsAttendanceMachineMapper.countWsAttendanceBySerialNumber(wsAttendanceMachine.getSerialNumber());
        if(number>0){
            throw  new CustomException("设备已存在，请先删除");
        }
        ResultData resultData = KaoQinInterUtil.addKQ(wsAttendanceMachine.getName(), wsAttendanceMachine.getSerialNumber());
        if (resultData.getCode().equals(LaborConstants.FAIL)) {
            throw  new CustomException(resultData.getMessage());
        }
        wsAttendanceMachine.setZoneId(0);

        return wsAttendanceMachineMapper.insertWsAttendanceMachine(wsAttendanceMachine);
    }

    /**
     * 修改考勤机
     *
     * @param wsAttendanceMachine 考勤机
     * @return 结果
     */
    @Override
    public int updateWsAttendanceMachine(WsAttendanceMachine wsAttendanceMachine)
    {
        WsAttendanceMachine  curMachine = wsAttendanceMachineMapper.selectWsAttendanceMachineById(wsAttendanceMachine.getId());
        if (null==curMachine) {
            throw new CustomException("打卡机不存在");
        }
        if(!curMachine.getName().equals(wsAttendanceMachine.getName())){
            ResultData resultData =KaoQinInterUtil.updateKQ(wsAttendanceMachine.getName(),wsAttendanceMachine.getSerialNumber());
            if (LaborConstants.FAIL.equals(resultData.getCode())){
                throw new CustomException(resultData.getMessage());
            }
        }
        return wsAttendanceMachineMapper.updateWsAttendanceMachine(wsAttendanceMachine);
    }

    /**
     * 批量删除考勤机
     *
     * @param ids 需要删除的考勤机 ID
     * @return 结果
     */
    @Override
    public int deleteWsAttendanceMachineByIds(Integer[] ids)
    {

        return wsAttendanceMachineMapper.deleteWsAttendanceMachineByIds(ids);
    }

    /**
     * 删除考勤机 信息
     *
     * @param id 考勤机 ID
     * @return 结果
     */
    @Override
    public int deleteWsAttendanceMachineById(Integer id)
    {
        WsAttendanceMachine wsAttendanceMachine = wsAttendanceMachineMapper.selectWsAttendanceMachineById(id);
        if(StringUtils.isNotNull(wsAttendanceMachine)){
            WsAttendanceBinding wsAttendanceBinding = new WsAttendanceBinding();
            wsAttendanceBinding.setSerialNumber(wsAttendanceMachine.getSerialNumber());
            List<WsAttendanceBinding> wsAttendanceBindings = wsAttendanceBindingMapper.selectWsAttendanceBindingList(wsAttendanceBinding,null);
            if (StringUtils.isNotNull(wsAttendanceBindings)) {
                throw new CustomException("设备已被授权，请先解绑");
            }

            //删除云平台设备
            ResultData resultData = KaoQinInterUtil.deleteKQ(wsAttendanceMachine.getSerialNumber());
            if (LaborConstants.FAIL.equals(resultData.getCode())) {
                throw new CustomException(resultData.getMessage());
            }
        }
        return wsAttendanceMachineMapper.deleteWsAttendanceMachineById(id);
    }

    @Override
    public WsAttendanceMachine findWsAttendanceMachineBySerialNumber(String serialNumber) {
        return wsAttendanceMachineMapper.findWsAttendanceMachineBySerialNumber(serialNumber);
    }
}
