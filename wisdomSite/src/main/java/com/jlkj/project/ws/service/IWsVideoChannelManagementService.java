package com.jlkj.project.ws.service;

import java.util.List;
import com.jlkj.project.ws.domain.WsVideoChannelManagement;

/**
 * 视频通道管理Service接口
 * 
 * @author jlkj
 * @date 2020-07-07
 */
public interface IWsVideoChannelManagementService 
{
    /**
     * 查询视频通道管理
     * 
     * @param id 视频通道管理ID
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
    public int updateWsVideoChannelManagement(WsVideoChannelManagement wsVideoChannelManagement);



    /**
     * 删除视频通道管理信息
     * 
     * @param id 视频通道管理ID
     * @return 结果
     */
    public int deleteWsVideoChannelManagementById(Integer id);


}
