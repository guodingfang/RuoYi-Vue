package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsTeamPersonExchangeRecord;
import com.jlkj.project.ws.domain.vo.WsTeamPersonExchangeRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 班组人员兑换记录Mapper接口
 *
 * @author liujie
 * @date 2020-07-23
 */
public interface WsTeamPersonExchangeRecordMapper
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
     * @param wsTeamPersonExchangeRecord 班组人员兑换记录,dataScope数据范围
     * @return 班组人员兑换记录集合
     */
    public List<WsTeamPersonExchangeRecordVO> selectWsTeamPersonExchangeRecordList(@Param("wsTeamPersonExchangeRecord") WsTeamPersonExchangeRecord wsTeamPersonExchangeRecord,@Param("dataScope")  List<WsProject>dataScope);

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
     * 删除班组人员兑换记录
     *
     * @param id 班组人员兑换记录ID
     * @return 结果
     */
    public int deleteWsTeamPersonExchangeRecordById(Integer id);

    /**
     * 批量删除班组人员兑换记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsTeamPersonExchangeRecordByIds(Integer[] ids);

    /**
        * @Author liujie
        * @Description 统计人员已兑换点券信息
        * @Date 2020/7/28 11:46
        * @Param [personId]
        * @Return com.jlkj.project.ws.domain.vo.WsTeamPersonExchangeRecordVO
       **/
    WsTeamPersonExchangeRecordVO  statisticstPersonExchangedPoints(Integer personId);
}
