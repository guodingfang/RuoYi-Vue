package com.jlkj.project.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.project.ws.domain.WsProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsVideoChannelManagementMapper;
import com.jlkj.project.ws.domain.WsVideoChannelManagement;
import com.jlkj.project.ws.service.IWsVideoChannelManagementService;

/**
 * 视频通道管理Service业务层处理
 *
 * @author jlkj
 * @date 2020-07-07
 */
@Service
public class WsVideoChannelManagementServiceImpl implements IWsVideoChannelManagementService
{
    @Autowired
    private WsVideoChannelManagementMapper wsVideoChannelManagementMapper;

    /**
     * 查询视频通道管理
     *
     * @param id 视频通道管理ID
     * @return 视频通道管理
     */
    @Override
    public WsVideoChannelManagement selectWsVideoChannelManagementById(Integer id)
    {
        return wsVideoChannelManagementMapper.selectWsVideoChannelManagementById(id);
    }

    /**
     * 查询视频通道管理列表
     *
     * @param wsVideoChannelManagement 视频通道管理
     * @return 视频通道管理
     */
    @Override
    public List<WsVideoChannelManagement> selectWsVideoChannelManagementList(WsVideoChannelManagement wsVideoChannelManagement)
    {
        List<Integer> list = new ArrayList<>();
        List<WsProject> projectList = SecurityUtils.getProjectList();
        if (!projectList.isEmpty()){
            projectList.forEach(project -> list.add(project.getId()));
            wsVideoChannelManagement.setProjectIds(list);
        }
        return wsVideoChannelManagementMapper.selectWsVideoChannelManagementList(wsVideoChannelManagement);
    }

    /**
     * 新增视频通道管理
     *
     * @param wsVideoChannelManagement 视频通道管理
     * @return 结果
     */
    @Override
    public int insertWsVideoChannelManagement(WsVideoChannelManagement wsVideoChannelManagement)
    {
        return wsVideoChannelManagementMapper.insertWsVideoChannelManagement(wsVideoChannelManagement);
    }

    /**
     * 修改视频通道管理
     *
     * @param wsVideoChannelManagement 视频通道管理
     * @return 结果
     */
    @Override
    public int updateWsVideoChannelManagement(WsVideoChannelManagement wsVideoChannelManagement)
    {
        return wsVideoChannelManagementMapper.updateWsVideoChannelManagementById(wsVideoChannelManagement);
    }



    /**
     * 删除视频通道管理信息
     *
     * @param id 视频通道管理ID
     * @return 结果
     */
    @Override
    public int deleteWsVideoChannelManagementById(Integer id)
    {
        return wsVideoChannelManagementMapper.deleteWsVideoChannelManagementById(id);
    }
}
