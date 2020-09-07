package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsExchangeItemManagement;

import java.util.List;

/**
 * 物品兑换管理
Service接口
 *
 * @author liujie
 * @date 2020-07-23
 */
public interface IWsExchangeItemManagementService
{
    /**
     * 查询物品兑换管理

     *
     * @param id 物品兑换管理
ID
     * @return 物品兑换管理

     */
    public WsExchangeItemManagement selectWsExchangeItemManagementById(Integer id);

    /**
     * 查询物品兑换管理
列表
     *
     * @param wsExchangeItemManagement 物品兑换管理

     * @return 物品兑换管理
集合
     */
    public List<WsExchangeItemManagement> selectWsExchangeItemManagementList(WsExchangeItemManagement wsExchangeItemManagement);

    /**
     * 新增物品兑换管理

     *
     * @param wsExchangeItemManagement 物品兑换管理

     * @return 结果
     */
    public int insertWsExchangeItemManagement(WsExchangeItemManagement wsExchangeItemManagement);

    /**
     * 修改物品兑换管理

     *
     * @param wsExchangeItemManagement 物品兑换管理

     * @return 结果
     */
    public int updateWsExchangeItemManagement(WsExchangeItemManagement wsExchangeItemManagement);

    /**
     * 批量删除物品兑换管理

     *
     * @param ids 需要删除的物品兑换管理
ID
     * @return 结果
     */
    public int deleteWsExchangeItemManagementByIds(Integer[] ids);

    /**
     * 删除物品兑换管理
信息
     *
     * @param id 物品兑换管理
ID
     * @return 结果
     */
    public int deleteWsExchangeItemManagementById(Integer id);
}
