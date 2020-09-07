package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsExchangeItemManagement;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsTeamPersonExchangeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物品兑换管理
Mapper接口
 *
 * @author liujie
 * @date 2020-07-23
 */
public interface WsExchangeItemManagementMapper
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
     * @param wsExchangeItemManagement 物品兑换管理,dataScope 数据范围

     * @return 物品兑换管理
集合
     */
    public List<WsExchangeItemManagement> selectWsExchangeItemManagementList(@Param("wsExchangeItemManagement") WsExchangeItemManagement wsExchangeItemManagement,@Param("dataScope")  List<WsProject>dataScope);

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
     * 删除物品兑换管理

     *
     * @param id 物品兑换管理
ID
     * @return 结果
     */
    public int deleteWsExchangeItemManagementById(Integer id);

    /**
     * 批量删除物品兑换管理

     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsExchangeItemManagementByIds(Integer[] ids);

    /**
        * @Author liujie
        * @Description 获取项目内同名物品数量
        * @Date 2020/7/24 18:12
        * @Param [wsExchangeItemManagement]
        * @Return int
       **/
    int countItemOfProjectByName(WsExchangeItemManagement wsExchangeItemManagement);

    /**
        * @Author liujie
        * @Description 通过物品名称获取项目库存数量
        * @Date 2020/7/25 9:12
        * @Param [projectId, itemName]
        * @Return int
       **/
    int getItemStockByName(@Param("projectId") Integer projectId, @Param("itemName")String itemName);

    /**
        * @Author liujie
        * @Description 通过物品兑换更新项目物品库存
        * @Date 2020/7/25 10:33
        * @Param [wsTeamPersonExchangeRecord]
        * @Return int
       **/
    int updateItemStockByName(WsTeamPersonExchangeRecord wsTeamPersonExchangeRecord);

}
