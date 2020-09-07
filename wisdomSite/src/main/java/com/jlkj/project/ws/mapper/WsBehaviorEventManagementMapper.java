package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsBehaviorEventManagement;

import java.util.List;

/**
 * 行为事件管理Mapper接口
 *
 * @author liujie
 * @date 2020-07-23
 */
public interface WsBehaviorEventManagementMapper
{
    /**
     * 查询行为事件管理
     *
     * @param id 行为事件管理ID
     * @return 行为事件管理
     */
    public WsBehaviorEventManagement selectWsBehaviorEventManagementById(Integer id);

    /**
     * 查询行为事件管理列表
     *
     * @param wsBehaviorEventManagement 行为事件管理
     * @return 行为事件管理集合
     */
    public List<WsBehaviorEventManagement> selectWsBehaviorEventManagementList(WsBehaviorEventManagement wsBehaviorEventManagement);

    /**
     * 新增行为事件管理
     *
     * @param wsBehaviorEventManagement 行为事件管理
     * @return 结果
     */
    public int insertWsBehaviorEventManagement(WsBehaviorEventManagement wsBehaviorEventManagement);

    /**
     * 修改行为事件管理
     *
     * @param wsBehaviorEventManagement 行为事件管理
     * @return 结果
     */
    public int updateWsBehaviorEventManagement(WsBehaviorEventManagement wsBehaviorEventManagement);

    /**
     * 删除行为事件管理
     *
     * @param id 行为事件管理ID
     * @return 结果
     */
    public int deleteWsBehaviorEventManagementById(Integer id);

    /**
     * 批量删除行为事件管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsBehaviorEventManagementByIds(Integer[] ids);


}
