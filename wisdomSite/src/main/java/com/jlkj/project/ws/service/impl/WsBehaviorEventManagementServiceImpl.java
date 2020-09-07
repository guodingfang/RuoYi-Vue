package com.jlkj.project.ws.service.impl;

import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.ws.domain.WsBehaviorEventManagement;
import com.jlkj.project.ws.mapper.WsBehaviorEventManagementMapper;
import com.jlkj.project.ws.service.IWsBehaviorEventManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 行为事件管理Service业务层处理
 *
 * @author liujie
 * @date 2020-07-23
 */
@Service
public class WsBehaviorEventManagementServiceImpl implements IWsBehaviorEventManagementService
{
    @Autowired
    private WsBehaviorEventManagementMapper wsBehaviorEventManagementMapper;

    /**
     * 查询行为事件管理
     *
     * @param id 行为事件管理ID
     * @return 行为事件管理
     */
    @Override
    public WsBehaviorEventManagement selectWsBehaviorEventManagementById(Integer id)
    {
        return wsBehaviorEventManagementMapper.selectWsBehaviorEventManagementById(id);
    }

    /**
     * 查询行为事件管理列表
     *
     * @param wsBehaviorEventManagement 行为事件管理
     * @return 行为事件管理
     */
    @Override
    public List<WsBehaviorEventManagement> selectWsBehaviorEventManagementList(WsBehaviorEventManagement wsBehaviorEventManagement)
    {

        return wsBehaviorEventManagementMapper.selectWsBehaviorEventManagementList(wsBehaviorEventManagement);
    }

    /**
     * 新增行为事件管理
     *
     * @param wsBehaviorEventManagement 行为事件管理
     * @return 结果
     */
    @Override
    public int insertWsBehaviorEventManagement(WsBehaviorEventManagement wsBehaviorEventManagement)
    {
        if(StringUtils.isNull(wsBehaviorEventManagement)) throw new CustomException("行为事件不能为空");
        return wsBehaviorEventManagementMapper.insertWsBehaviorEventManagement(wsBehaviorEventManagement);
    }

    /**
     * 修改行为事件管理
     *
     * @param wsBehaviorEventManagement 行为事件管理
     * @return 结果
     */
    @Override
    public int updateWsBehaviorEventManagement(WsBehaviorEventManagement wsBehaviorEventManagement)
    {
        if(StringUtils.isNull(wsBehaviorEventManagement)) throw new CustomException("事件不能为空");
        WsBehaviorEventManagement event = wsBehaviorEventManagementMapper.selectWsBehaviorEventManagementById(wsBehaviorEventManagement.getId());
        if(StringUtils.isNull(event)) throw new CustomException("事件不存在");
        return wsBehaviorEventManagementMapper.updateWsBehaviorEventManagement(wsBehaviorEventManagement);
    }

    /**
     * 批量删除行为事件管理
     *
     * @param ids 需要删除的行为事件管理ID
     * @return 结果
     */
    @Override
    public int deleteWsBehaviorEventManagementByIds(Integer[] ids)
    {
        return wsBehaviorEventManagementMapper.deleteWsBehaviorEventManagementByIds(ids);
    }

    /**
     * 删除行为事件管理信息
     *
     * @param id 行为事件管理ID
     * @return 结果
     */
    @Override
    public int deleteWsBehaviorEventManagementById(Integer id)
    {
        WsBehaviorEventManagement wsBehaviorEventManagement = wsBehaviorEventManagementMapper.selectWsBehaviorEventManagementById(id);
        if(StringUtils.isNull(wsBehaviorEventManagement)) throw new CustomException("事件不存在");
        return wsBehaviorEventManagementMapper.deleteWsBehaviorEventManagementById(id);
    }
}
