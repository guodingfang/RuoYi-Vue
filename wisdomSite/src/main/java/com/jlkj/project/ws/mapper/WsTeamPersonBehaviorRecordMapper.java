package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsTeamPersonBehaviorRecord;
import com.jlkj.project.ws.domain.vo.WsTeamPersonBehaviorRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 班组人员行为记录Mapper接口
 *
 * @author liujie
 * @date 2020-07-23
 */
public interface WsTeamPersonBehaviorRecordMapper
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
     * @param wsTeamPersonBehaviorRecord 班组人员行为记录, dataScope 数据范围
     * @return 班组人员行为记录集合
     */
    public List<WsTeamPersonBehaviorRecordVO> selectWsTeamPersonBehaviorRecordList(@Param("wsTeamPersonBehaviorRecord") WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord,@Param("dataScope")  List<WsProject>dataScope);

    /**
        * @Author liujie
        * @Description 按人员分组获取行为记录列表
        * @Date 2020/7/25 11:17
        * @Param [wsTeamPersonBehaviorRecord]，dataScope 数据范围
        * @Return java.util.List<com.jlkj.project.ws.domain.WsTeamPersonBehaviorRecord>
       **/
    List<WsTeamPersonBehaviorRecordVO>  selectWsTeamPersonBehaviorRecordListByPerson (@Param("wsTeamPersonBehaviorRecord") WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord,@Param("dataScope")  List<WsProject>dataScope);

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
        * @Param [wsTeamPersonBehaviorRecord, dataScope]
        * @Return java.util.List<com.jlkj.project.ws.domain.vo.WsTeamPersonBehaviorRecordVO>
       **/
    List<WsTeamPersonBehaviorRecordVO>  selectPersonBehaviorEvaluate(@Param("wsTeamPersonBehaviorRecord") WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord, @Param("dataScope")  List<WsProject>dataScope);
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
     * 删除班组人员行为记录
     *
     * @param id 班组人员行为记录ID
     * @return 结果
     */
    public int deleteWsTeamPersonBehaviorRecordById(Integer id);

    /**
     * 批量删除班组人员行为记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsTeamPersonBehaviorRecordByIds(Integer[] ids);

    /**
        * @Author liujie
        * @Description 统计人员可兑换点券/分值
        * @Date 2020/7/24 14:20
        * @Param [personId]
        * @Return com.jlkj.project.ws.domain.vo.WsTeamPersonBehaviorRecordVO
       **/
    WsTeamPersonBehaviorRecordVO statisticstPersonExchangeablePoints (Integer personId);

    /**
     * @Author liujie
     * @Description 统计人员月累计点券/分值
     * @Date 2020/7/24 14:20
     * @Param [personId]
     * @Return com.jlkj.project.ws.domain.vo.WsTeamPersonBehaviorRecordVO
     **/
    WsTeamPersonBehaviorRecordVO statisticstPersonTotalPoints (Integer personId);
}
