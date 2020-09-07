package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsTeamPersonExchangeRecord;
import com.jlkj.project.ws.domain.WsTeamPersonExchangeRecordDTO;
import com.jlkj.project.ws.domain.vo.WsTeamPersonExchangeRecordVO;

import java.util.List;

/**
 * 班组人员兑换记录Service接口
 *
 * @author liujie
 * @date 2020-07-23
 */
public interface IWsTeamPersonExchangeRecordService
{
    /**
     * 查询班组人员兑换记录
     *
     * @param id 班组人员兑换记录ID
     * @return 班组人员兑换记录
     */
    public WsTeamPersonExchangeRecord selectWsTeamPersonExchangeRecordById(Integer id);

    /**
     * 查询班组人员兑换记录列表
     *
     * @param wsTeamPersonExchangeRecord 班组人员兑换记录
     * @return 班组人员兑换记录集合
     */
    public List<WsTeamPersonExchangeRecordVO> selectWsTeamPersonExchangeRecordList(WsTeamPersonExchangeRecord wsTeamPersonExchangeRecord);

    /**
     * 新增班组人员兑换记录
     *
     * @param wsTeamPersonExchangeRecord 班组人员兑换记录
     * @return 结果
     */
    public int insertWsTeamPersonExchangeRecord(WsTeamPersonExchangeRecord wsTeamPersonExchangeRecord);

    /**
     * 修改班组人员兑换记录
     *
     * @param wsTeamPersonExchangeRecord 班组人员兑换记录
     * @return 结果
     */
    public int updateWsTeamPersonExchangeRecord(WsTeamPersonExchangeRecord wsTeamPersonExchangeRecord);

    /**
     * 批量删除班组人员兑换记录
     *
     * @param ids 需要删除的班组人员兑换记录ID
     * @return 结果
     */
    public int deleteWsTeamPersonExchangeRecordByIds(Integer[] ids);

    /**
     * 删除班组人员兑换记录信息
     *
     * @param id 班组人员兑换记录ID
     * @return 结果
     */
    public int deleteWsTeamPersonExchangeRecordById(Integer id);

/**
    * @Author liujie
    * @Description 批量插入物品兑换
    * @Date 2020/7/25 8:54
    * @Param [wsTeamPersonExchangeRecordDTO]
    * @Return int
   **/
    int bacthWsTeamPersonExchangeRecord(WsTeamPersonExchangeRecordDTO wsTeamPersonExchangeRecordDTO);


    /**
     * @Author liujie
     * @Description 统计人员已兑换点券信息
     * @Date 2020/7/28 11:46
     * @Param [personId]
     * @Return com.jlkj.project.ws.domain.vo.WsTeamPersonExchangeRecordVO
     **/
    WsTeamPersonExchangeRecordVO  statisticstPersonExchangedPoints(Integer personId);
}
