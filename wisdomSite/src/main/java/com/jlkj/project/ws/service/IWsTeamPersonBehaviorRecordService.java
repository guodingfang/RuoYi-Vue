package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsTeamPersonBehaviorRecord;
import com.jlkj.project.ws.domain.vo.WsTeamPersonBehaviorRecordVO;

import java.util.List;

/**
 * 班组人员行为记录Service接口
 *
 * @author liujie
 * @date 2020-07-23
 */
public interface IWsTeamPersonBehaviorRecordService
{
    /**
     * 查询班组人员行为记录
     *
     * @param id 班组人员行为记录ID
     * @return 班组人员行为记录
     */
    public WsTeamPersonBehaviorRecord selectWsTeamPersonBehaviorRecordById(Integer id);

    /**
     * 查询班组人员行为记录列表
     *
     * @param wsTeamPersonBehaviorRecord 班组人员行为记录
     * @return 班组人员行为记录集合
     */
    public List<WsTeamPersonBehaviorRecordVO> selectWsTeamPersonBehaviorRecordList(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord);


    /**
     * @Author liujie
     * @Description 按人员分组获取行为记录列表
     * @Date 2020/7/25 11:17
     * @Param [wsTeamPersonBehaviorRecord]
     * @Return java.util.List<com.jlkj.project.ws.domain.WsTeamPersonBehaviorRecord>
     **/
    List<WsTeamPersonBehaviorRecordVO>  selectWsTeamPersonBehaviorRecordListByPerson (WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord);

    /**
     * @Author liujie
     * @Description 按班组分类获取行为记录列表
     * @Date 2020/7/25 11:17
     * @Param [wsTeamPersonBehaviorRecord]
     * @Return java.util.List<com.jlkj.project.ws.domain.WsTeamPersonBehaviorRecord>
     **/
    List<WsTeamPersonBehaviorRecordVO> selectWsTeamPersonBehaviorRecordListByTeam(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord);

    /**
     * @Author liujie
     * @Description 获取人员行为评价列表
     * @Date 2020/8/18 10:43
     * @Return java.util.List<com.jlkj.project.ws.domain.vo.WsTeamPersonBehaviorRecordVO>
     **/
    List<WsTeamPersonBehaviorRecordVO>  selectPersonBehaviorEvaluate(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord);

    /**
     * 新增班组人员行为记录
     *
     * @param wsTeamPersonBehaviorRecord 班组人员行为记录
     * @return 结果
     */
    public int insertWsTeamPersonBehaviorRecord(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord);

    /**
     * 修改班组人员行为记录
     *
     * @param wsTeamPersonBehaviorRecord 班组人员行为记录
     * @return 结果
     */
    public int updateWsTeamPersonBehaviorRecord(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord);

    /**
     * 批量删除班组人员行为记录
     *
     * @param ids 需要删除的班组人员行为记录ID
     * @return 结果
     */
    public int deleteWsTeamPersonBehaviorRecordByIds(Integer[] ids);

    /**
     * 删除班组人员行为记录信息
     *
     * @param id 班组人员行为记录ID
     * @return 结果
     */
    public int deleteWsTeamPersonBehaviorRecordById(Integer id);

    /**
        * @Author liujie
        * @Description 统计 人员行为点券/分值
        * @Date 2020/7/24 16:18
        * @Param [personId]
        * @Return com.jlkj.project.ws.domain.vo.WsTeamPersonBehaviorRecordVO
       **/
    WsTeamPersonBehaviorRecordVO statisticstPersonTotalPoints(Integer personId);

    /**
        * @Author liujie
        * @Description 统计人员可兑换点券
        * @Date 2020/7/24 17:02
        * @Param [personId]
        * @Return com.jlkj.project.ws.domain.vo.WsTeamPersonBehaviorRecordVO
       **/
    WsTeamPersonBehaviorRecordVO statisticstPersonExchangeablePoints(Integer personId);
}
