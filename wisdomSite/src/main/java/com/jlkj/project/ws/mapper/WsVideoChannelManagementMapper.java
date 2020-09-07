package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsVideoChannelManagement;

import java.util.List;

/**
 * 视频通道管理Mapper接口
 *
 * @author jlkj
 * @date 2020-07-07
 */
public interface WsVideoChannelManagementMapper
{
    /**
     * 根据设备序列号查询设备
     *
     * @param equipmentSerialnumber 设备序列号
     * @return 视频通道管理
     */
    public List<WsVideoChannelManagement> selectWsVideoChannelManagementBySerialNumber(String equipmentSerialnumber);

    /**
     * 根据设备序列号查询设备
     *
     * @param wsVideoChannelManagement 设备序列号
     * @return 视频通道管理
     */
    public WsVideoChannelManagement selectWsVideoChannelManagementBySerialNumberAndChannelNumber(WsVideoChannelManagement wsVideoChannelManagement);

    /**
     * 根据id查询详情
     *
     * @param id 主键
     * @return 视频通道管理
     */
    public WsVideoChannelManagement selectWsVideoChannelManagementById(Integer id);

    /**
     * 查询视频通道管理列表
     *
     * @param wsVideoChannelManagement 视频通道管理
     * @return 视频通道管理集合
     */
    public List<WsVideoChannelManagement> selectWsVideoChannelManagementList(WsVideoChannelManagement wsVideoChannelManagement);

    /**
     * 新增视频通道管理
     *
     * @param wsVideoChannelManagement 视频通道管理
     * @return 结果
     */
    public int insertWsVideoChannelManagement(WsVideoChannelManagement wsVideoChannelManagement);

    /**
     * 修改视频通道管理
     *
     * @param wsVideoChannelManagement 视频通道管理
     * @return 结果
     */
    public int updateWsVideoChannelManagementById(WsVideoChannelManagement wsVideoChannelManagement);

    /**
     * 修改视频通道管理
     *
     * @param wsVideoChannelManagement 视频通道管理
     * @return 结果
     */
    public int updateWsVideoChannelManagementByEquipmentSerialnumber(WsVideoChannelManagement wsVideoChannelManagement);

    /**
        * @Author liujie
        * @Description 通过序列号和通道号，更新视频通道管理
        * @Date 2020/7/15 14:09
        * @Param [wsVideoChannelManagement]
        * @Return int
       **/
    int updateWsVideoChannelManagementBySerialnumberAndChannel(WsVideoChannelManagement wsVideoChannelManagement);

    /**
     * @Author liujie
     * @Description 通过序列号和通道号，更新启用状态
     * @Date 2020/7/15 14:09
     * @Param [wsVideoChannelManagement]
     * @Return int
     **/
    int updateEnabledBySerialnumberAndChannel(WsVideoChannelManagement wsVideoChannelManagement);

    /**
     * 删除视频通道管理
     *
     * @param id 视频通道管理ID
     * @return 结果
     */
    public int deleteWsVideoChannelManagementById(Integer id);

    /**
     *  查询视频
     * @param wsVideoChannelManagement
     * @return
     */
    public List<WsVideoChannelManagement> selectListHookVideo(WsVideoChannelManagement wsVideoChannelManagement);
}
