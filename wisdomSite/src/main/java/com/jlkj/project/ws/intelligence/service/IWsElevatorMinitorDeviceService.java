package com.jlkj.project.ws.intelligence.service;

import com.jlkj.project.ws.intelligence.domain.WsElevatorMinitorDevice;

import java.util.List;

/**
 * 施工电梯监控设备绑定管理Service接口
 *
 * @author liujie
 * @date 2020-07-03
 */
public interface IWsElevatorMinitorDeviceService
{
    /**
     * 查询施工电梯监控设备绑定管理
     *
     * @param id 施工电梯监控设备绑定管理ID
     * @return 施工电梯监控设备绑定管理
     */
    public WsElevatorMinitorDevice selectWsElevatorMinitorDeviceById(Integer id);

    /**
     * 查询施工电梯监控设备绑定管理列表
     *
     * @param wsElevatorMinitorDevice 施工电梯监控设备绑定管理
     * @return 施工电梯监控设备绑定管理集合
     */
    public List<WsElevatorMinitorDevice> selectWsElevatorMinitorDeviceList(WsElevatorMinitorDevice wsElevatorMinitorDevice);

    /**
     * 新增施工电梯监控设备绑定管理
     *
     * @param wsElevatorMinitorDevice 施工电梯监控设备绑定管理
     * @return 结果
     */
    public int insertWsElevatorMinitorDevice(WsElevatorMinitorDevice wsElevatorMinitorDevice);

    /**
     * 修改施工电梯监控设备绑定管理
     *
     * @param wsElevatorMinitorDevice 施工电梯监控设备绑定管理
     * @return 结果
     */
    public int updateWsElevatorMinitorDevice(WsElevatorMinitorDevice wsElevatorMinitorDevice);


    /**
     * 删除施工电梯监控设备绑定管理信息
     *
     * @param id 施工电梯监控设备绑定管理ID
     * @return 结果
     */
    public int deleteWsElevatorMinitorDeviceById(Integer id);
}
