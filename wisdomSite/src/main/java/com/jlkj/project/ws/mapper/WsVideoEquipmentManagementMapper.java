package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsVideoEquipmentManagement;

/**
 * 视频设备管理 Mapper接口
 * 
 * @author jlkj
 * @date 2020-07-07
 */
public interface WsVideoEquipmentManagementMapper 
{
    /**
     * 根据设备编号查询
     *
     * @param equipmentSerialnumber 设备序列号
     * @return 视频设备管理
     */
    public WsVideoEquipmentManagement selectWsVideoEquipmentManagementBySerialNumber(String equipmentSerialnumber);


    /**
     * 根据id查询设备信息
     *
     * @param id 主键
     * @return 视频设备管理
     */
    public WsVideoEquipmentManagement selectWsVideoEquipmentManagementById(Integer id);


    /**
     * 根据项目id查询设备信息
     *
     * @param id 主键
     * @return 视频设备管理
     */
    public List<WsVideoEquipmentManagement> selectWsVideoEquipmentManagementByProjectId(Integer id);


    /**
     * 查询视频设备管理 列表
     * 
     * @param wsVideoEquipmentManagement 视频设备管理 
     * @return 视频设备管理 集合
     */
    public List<WsVideoEquipmentManagement> selectWsVideoEquipmentManagementList(WsVideoEquipmentManagement wsVideoEquipmentManagement);

    /**
     * 查询所有视频设备总记录数
     *
     * @return 视频设备管理
     */
    public int selectEquipmentCount();

    /**
     * 查询所有视频总记录数
     *
     * @return 视频设备管理
     */
    public int selectVideoCount();

    /**
     * 查询所有录像机设备总记录数
     *
     * @return 视频设备管理
     */
    public int selectNVRCount();
    /**
     * 新增视频设备管理 
     * 
     * @param wsVideoEquipmentManagement 视频设备管理 
     * @return 结果
     */
    public int insertWsVideoEquipmentManagement(WsVideoEquipmentManagement wsVideoEquipmentManagement);

    /**
     * 修改视频设备管理 
     * 
     * @param wsVideoEquipmentManagement 视频设备管理 
     * @return 结果
     */
    public int updateWsVideoEquipmentManagement(WsVideoEquipmentManagement wsVideoEquipmentManagement);

    /**
     * 删除视频设备管理 
     * 
     * @param id 视频设备管理 ID
     * @return 结果
     */
    public int deleteWsVideoEquipmentManagementById(Integer id);

}
