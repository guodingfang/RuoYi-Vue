package com.jlkj.project.ws.intelligence.service.impl;

import com.jlkj.common.constant.IntelligentMonitoringConstants;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.ws.domain.WsProjectEquipment;
import com.jlkj.project.ws.intelligence.domain.WsElevatorMinitorDevice;
import com.jlkj.project.ws.intelligence.mapper.WsElevatorMinitorDeviceMapper;
import com.jlkj.project.ws.intelligence.service.IWsElevatorMinitorDeviceService;
import com.jlkj.project.ws.mapper.WsProjectEquipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 施工电梯监控设备绑定管理Service业务层处理
 *
 * @author liujie
 * @date 2020-07-03
 */
@Service
public class WsElevatorMinitorDeviceServiceImpl implements IWsElevatorMinitorDeviceService
{
    @Autowired
    private WsElevatorMinitorDeviceMapper wsElevatorMinitorDeviceMapper;

    @Autowired
    private WsProjectEquipmentMapper wsProjectEquipmentMapper;

    /**
     * 查询施工电梯监控设备绑定管理
     *
     * @param id 施工电梯监控设备绑定管理ID
     * @return 施工电梯监控设备绑定管理
     */
    @Override
    public WsElevatorMinitorDevice selectWsElevatorMinitorDeviceById(Integer id)
    {
        return wsElevatorMinitorDeviceMapper.selectWsElevatorMinitorDeviceById(id);
    }

    /**
     * 查询施工电梯监控设备绑定管理列表
     *
     * @param wsElevatorMinitorDevice 施工电梯监控设备绑定管理
     * @return 施工电梯监控设备绑定管理
     */
    @Override
    public List<WsElevatorMinitorDevice> selectWsElevatorMinitorDeviceList(WsElevatorMinitorDevice wsElevatorMinitorDevice)
    {
        return wsElevatorMinitorDeviceMapper.selectWsElevatorMinitorDeviceList(wsElevatorMinitorDevice);
    }

    /**
     * 新增施工电梯监控设备绑定管理
     *
     * @param wsElevatorMinitorDevice 施工电梯监控设备绑定管理
     * @return 结果
     */
    @Transactional
    @Override
    public int insertWsElevatorMinitorDevice(WsElevatorMinitorDevice wsElevatorMinitorDevice)
    {
        int rows =0;
        int compare =0;
        int count =wsElevatorMinitorDeviceMapper.countWsElevatorMinitorDeviceByProjectAndSN(wsElevatorMinitorDevice);
        if (count>0) {
            throw new CustomException("绑定设备已存在，不可重复绑定！");
        }
        rows += wsElevatorMinitorDeviceMapper.insertWsElevatorMinitorDevice(wsElevatorMinitorDevice);
        compare++;


        WsProjectEquipment wsProjectEquipment =getWsProjectEquipment(wsElevatorMinitorDevice);
        rows += wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        compare++;
        return rows==compare?1:0;
    }

    private WsProjectEquipment getWsProjectEquipment(WsElevatorMinitorDevice wsElevatorMinitorDevice) {
        WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
        wsProjectEquipment.setSerialNumber(wsElevatorMinitorDevice.getSerialNumber());
        wsProjectEquipment.setEnableStatus(Integer.parseInt(IntelligentMonitoringConstants.ENABLE));
        wsProjectEquipment.setEquipmentType(Integer.parseInt(wsElevatorMinitorDevice.getEquipmentType()));
        return wsProjectEquipment;
    }

    /**
     * 修改施工电梯监控设备绑定管理
     *
     * @param wsElevatorMinitorDevice 施工电梯监控设备绑定管理
     * @return 结果
     */
    @Override
    public int updateWsElevatorMinitorDevice(WsElevatorMinitorDevice wsElevatorMinitorDevice)
    {
        return wsElevatorMinitorDeviceMapper.updateWsElevatorMinitorDevice(wsElevatorMinitorDevice);
    }


    /**
     * 删除施工电梯监控设备绑定管理信息
     *
     * @param id 施工电梯监控设备绑定管理ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWsElevatorMinitorDeviceById(Integer id)
    {

        int rows =0;
        int compare =0;
        WsElevatorMinitorDevice wsElevatorMinitorDevice = wsElevatorMinitorDeviceMapper.selectWsElevatorMinitorDeviceById(id);
        if(StringUtils.isNull(wsElevatorMinitorDevice)){throw new CustomException("设备不存在");}

        WsProjectEquipment wsProjectEquipment = getWsProjectEquipment(wsElevatorMinitorDevice);
        wsProjectEquipment.setEnableStatus(Integer.parseInt(IntelligentMonitoringConstants.NOT_ENABLED));
        rows =wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        compare++;
        rows =wsElevatorMinitorDeviceMapper.deleteWsElevatorMinitorDeviceById(id);
        compare++;
        return rows==compare?1:0;


    }
}
