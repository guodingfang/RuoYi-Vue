package com.jlkj.project.ws.mapper;

import com.jlkj.project.ws.domain.WsParticipatingTeam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 班组 Mapper接口
 *
 * @author jlkj
 * @date 2020-06-12
 */
public interface WsParticipatingTeamMapper
{
    /**
     * 查询班组
     *
     * @param id 班组 ID
     * @return 班组
     */
    public WsParticipatingTeam selectWsParticipatingTeamById(Integer id);

    /**
     * 查询班组 列表
     *
     * @param wsParticipatingTeam 班组
     * @return 班组 集合
     */
    public List<WsParticipatingTeam> selectWsParticipatingTeamList(WsParticipatingTeam wsParticipatingTeam);

    /**
     * 新增班组
     *
     * @param wsParticipatingTeam 班组
     * @return 结果
     */
    public int insertWsParticipatingTeam(WsParticipatingTeam wsParticipatingTeam);

    /**
     * 修改班组
     *
     * @param wsParticipatingTeam 班组
     * @return 结果
     */
    public int updateWsParticipatingTeam(WsParticipatingTeam wsParticipatingTeam);

    /**
     * 删除班组
     *
     * @param id 班组 ID
     * @return 结果
     */
    public int deleteWsParticipatingTeamById(Integer id);

    /**
     * 批量删除班组
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsParticipatingTeamByIds(Integer[] ids);

    /**
        * @Author liujie
        * @Description 批量插入参建单位班组
        * @Date 2020/6/13 9:25
        * @Param [list]
        * @Return int
       **/
    int batchParticipatingTeam(List<WsParticipatingTeam> list);

    /**
        * @Author liujie
        * @Description 统计班组在某项目下的同名数量
        * @Date 2020/6/13 12:23
        * @Param [WsParticipatingTeam]
        * @Return int
       **/
    int countWsParticipatingTeamInProject(WsParticipatingTeam wsParticipatingTeam);

    /**
        * @Author liujie
        * @Description  根据项目参加单位ID删除班组信息
        * @Date 2020/6/13 15:54
        * @Param [ids]
        * @Return void
       **/
    int deleteWsProjectParticipatingTeamByProjectUnitIds(Integer[] projectUnitIds);

    /**
        * @Author liujie
        * @Description //查询班组的数量
     *         * @Date 2020/6/13 16:55
        * @Param [wsParticipatingTeam]
        * @Return int
       **/
    int countWsParticipatingTeamByTeam(WsParticipatingTeam wsParticipatingTeam);

    /**
        * @Author liujie
        * @Description 通过班组名称查找同项目单位下班组数量
        * @Date 2020/6/17 17:31
        * @Param [teamName, unitId]
        * @Return int
       **/
    WsParticipatingTeam findWsParticipatingTeamByProjectTeam(@Param("teamName") String teamName, @Param("unitId")Integer unitId);
}
