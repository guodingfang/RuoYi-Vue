package com.jlkj.project.ws.service;

import java.util.List;
import com.jlkj.project.ws.domain.WsProjectEquipment;

/**
 * 工程设备绑定 Service接口
 * 
 * @author pyy
 * @date 2020-07-02
 */
public interface IWsProjectEquipmentService 
{
    /**
     * 查询工程设备绑定 
     * 
     * @param id 工程设备绑定 ID
     * @return 工程设备绑定 
     */
    public WsProjectEquipment selectWsProjectEquipmentById(Integer id);

    /**
     * 查询工程设备绑定 列表
     * 
     * @param wsProjectEquipment 工程设备绑定 
     * @return 工程设备绑定 集合
     */
    public List<WsProjectEquipment> selectWsProjectEquipmentList(WsProjectEquipment wsProjectEquipment);


    /**
     * 查询是否存在工程设备
     *
     * @param wsProjectEquipment 工程设备绑定
     * @return 工程设备绑定
     */
    public boolean isExistSerialNumber(WsProjectEquipment wsProjectEquipment);


    /**
     * 查询工程设备在线状态列表
     *
     * @param wsProjectEquipment
     * @return 在线状态
     */
    public WsProjectEquipment selectWsProjectEquipmentOnlineStatus(WsProjectEquipment wsProjectEquipment);


    /**
     * 新增工程设备绑定 
     * 
     * @param wsProjectEquipment 工程设备绑定 
     * @return 结果
     */
    public int insertWsProjectEquipment(WsProjectEquipment wsProjectEquipment);

    /**
     * 修改工程设备绑定 
     * 
     * @param wsProjectEquipment 工程设备绑定 
     * @return 结果
     */
    public int updateWsProjectEquipmentBySerialNumber(WsProjectEquipment wsProjectEquipment);


    /**
     * 工程设备转场
     *
     * @param wsProjectEquipment 工程设备绑定
     * @return 结果
     */
    public int updateProjectNameBySerialNumber(WsProjectEquipment wsProjectEquipment);

    /**
     * 删除工程设备绑定 信息
     *
     * @param id 工程设备绑定 ID
     * @return 结果
     */
    public int deleteWsProjectEquipmentById(Integer id);
}
