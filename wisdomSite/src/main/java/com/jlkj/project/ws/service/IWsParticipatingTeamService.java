package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsParticipatingTeam;
import com.jlkj.project.ws.domain.WsProjectParticipatingUnit;
import com.jlkj.project.ws.dto.WsParticipatingTeamDTO;

import java.util.List;

/**
 * 班组 Service接口
 *
 * @author jlkj
 * @date 2020-06-12
 */
public interface IWsParticipatingTeamService
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
    public int updateWsParticipatingTeam(WsParticipatingTeamDTO wsParticipatingTeam);

    /**
     * 批量删除班组
     *
     * @param ids 需要删除的班组 ID
     * @return 结果
     */
    public int deleteWsParticipatingTeamByIds(Integer[] ids);

    /**
     * 删除班组 信息
     *
     * @param id 班组 ID
     * @return 结果
     */
    public int deleteWsParticipatingTeamById(Integer id);


    /**
        * @Author liujie
        * @Description //判断某项目项目是否存在参建单位下属班组
        * @Date 2020/6/13 12:31
        * @Param [wsProjectParticipatingUnit]
        * @Return boolean
       **/
    boolean existWsParticipatingUnitTeamInProject(WsProjectParticipatingUnit wsProjectParticipatingUnit);

    boolean repeatedTeamName(List<WsParticipatingTeam> teams);

    /**
        * @Author liujie
        * @Description 批量修改
        * @Date 2020/7/12 21:11
        * @Param [wsParticipatingTeam]
        * @Return int
       **/
    int batchEdit(WsParticipatingTeamDTO wsParticipatingTeam);
}
