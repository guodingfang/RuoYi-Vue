package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsProjectEquipment;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;

/**
 * 工程设备绑定 Mapper接口
 * 
 * @author pyy
 * @date 2020-07-02
 */

public interface WsProjectEquipmentMapper 
{
    /**
     * 查询工程设备绑定 
     * 
     * @param id 工程设备绑定 ID
     * @return 工程设备绑定 
     */
    public WsProjectEquipment selectWsProjectEquipmentById(Integer id);

    /**
     * 根据项目id查询工程设备绑定
     *
     * @param id 工程设备绑定 ID
     * @return 工程设备绑定
     */
    public List<WsProjectEquipment> selectWsProjectEquipmentByProjectId(Integer id);

    /**
     * 查询工程设备绑定
     *
     * @param SerialNumber  设备编号
     * @return 工程设备绑定
     */
    public WsProjectEquipment selectWsProjectEquipmentBySerialNumber(String SerialNumber);


    /**
     * 查询工程设备在线状态
     *
     * @param wsProjectEquipments  设备编号
     * @return  在线状态
     */
    public List<WsProjectEquipment> selectWsProjectEquipmentOnlineStatus(List<WsProjectEquipment> wsProjectEquipments);

    /**
     * 查询工程设备绑定 列表
     * 
     * @param wsProjectEquipment 工程设备绑定 
     * @return 工程设备绑定 集合
     */
    public List<WsProjectEquipment> selectWsProjectEquipmentList(WsProjectEquipment wsProjectEquipment);


    /**
     * 查询工程设备编号是否存在
     *
     * @param wsProjectEquipment
     * @return
     */
    public int isExistBySerialNumber(WsProjectEquipment wsProjectEquipment);

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
     * 删除工程设备绑定 
     * 
     * @param id 工程设备绑定 ID
     * @return 结果
     */
    public int deleteWsProjectEquipmentById(Integer id);

}
