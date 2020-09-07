package com.jlkj.project.ws.service.impl;

import com.jlkj.common.constant.HttpStatus;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.ws.domain.WsParticipatingTeam;
import com.jlkj.project.ws.domain.WsProjectParticipatingUnit;
import com.jlkj.project.ws.dto.WsParticipatingTeamDTO;
import com.jlkj.project.ws.mapper.WsParticipatingTeamMapper;
import com.jlkj.project.ws.service.IWsParticipatingTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 班组 Service业务层处理
 *
 * @author jlkj
 * @date 2020-06-12
 */
@Service
public class WsParticipatingTeamServiceImpl implements IWsParticipatingTeamService
{
    @Autowired
    private WsParticipatingTeamMapper wsParticipatingTeamMapper;

    /**
     * 查询班组
     *
     * @param id 班组 ID
     * @return 班组
     */
    @Override
    public WsParticipatingTeam selectWsParticipatingTeamById(Integer id)
    {
        return wsParticipatingTeamMapper.selectWsParticipatingTeamById(id);
    }

    /**
     * 查询班组 列表
     *
     * @param wsParticipatingTeam 班组
     * @return 班组
     */
    @Override
    public List<WsParticipatingTeam> selectWsParticipatingTeamList(WsParticipatingTeam wsParticipatingTeam)
    {
        return wsParticipatingTeamMapper.selectWsParticipatingTeamList(wsParticipatingTeam);
    }

    /**
     * 新增班组
     *
     * @param wsParticipatingTeam 班组
     * @return 结果
     */
    @Override
    public int insertWsParticipatingTeam(WsParticipatingTeam wsParticipatingTeam)
    {
        int count = wsParticipatingTeamMapper.countWsParticipatingTeamByTeam(wsParticipatingTeam);
        if(count>=1)
           throw new CustomException("同一项目下班组名称不可重复", HttpStatus.NO_RECORD);
        return wsParticipatingTeamMapper.insertWsParticipatingTeam(wsParticipatingTeam);
    }

    /**
     * 修改班组
     *
     * @param wsParticipatingTeam 班组
     * @return 结果
     */
    @Override
    public int updateWsParticipatingTeam(WsParticipatingTeamDTO wsParticipatingTeam)
    {
        int rows = 0;
        for (WsParticipatingTeam team : wsParticipatingTeam.getTeams()) {
            rows +=wsParticipatingTeamMapper.updateWsParticipatingTeam(team);
        }
        return rows;
    }

    /**
     * 批量删除班组
     *
     * @param ids 需要删除的班组 ID
     * @return 结果
     */
    @Override
    public int deleteWsParticipatingTeamByIds(Integer[] ids)
    {
        return wsParticipatingTeamMapper.deleteWsParticipatingTeamByIds(ids);
    }

    /**
     * 删除班组 信息
     *
     * @param id 班组 ID
     * @return 结果
     */
    @Override
    public int deleteWsParticipatingTeamById(Integer id)
    {
        return wsParticipatingTeamMapper.deleteWsParticipatingTeamById(id);
    }

    @Override
    public boolean existWsParticipatingUnitTeamInProject(WsProjectParticipatingUnit wsProjectParticipatingUnit) {
        if(StringUtils.isNull(wsProjectParticipatingUnit)) return false;
        int count = 0 ;
        List<WsParticipatingTeam> teams = wsProjectParticipatingUnit.getTeams();
        for (WsParticipatingTeam team : teams) {
           count += wsParticipatingTeamMapper.countWsParticipatingTeamInProject(team);
        }
        return count>=1?true:false;
    }

    @Override
    public boolean repeatedTeamName(List<WsParticipatingTeam> teams) {
        return isRepeated(teams);
    }

    private boolean isRepeated(List<WsParticipatingTeam> teams) {
        Set<String> names = new HashSet<>();
        for (WsParticipatingTeam team : teams) {
            names.add(team.getTeamName());
        }
        if(teams.size()>names.size())
            return true;
        return false;
    }

    @Transactional
    @Override
    public int batchEdit(WsParticipatingTeamDTO wsParticipatingTeamDTO) {
        if (StringUtils.isNull(wsParticipatingTeamDTO)) {
            throw new CustomException("班组不能为空");
        }
        if(StringUtils.isNull(wsParticipatingTeamDTO.getProjectId())){
            throw new CustomException("项目不能为空");
        }
        if (StringUtils.isNull(wsParticipatingTeamDTO.getUnitId())) {
            throw new CustomException("单位不能为空");
        }
        if (StringUtils.isNull(wsParticipatingTeamDTO.getTeams())||wsParticipatingTeamDTO.getTeams().size()==0) {
            throw new CustomException("班组不能为空");
        }
        WsParticipatingTeam wsParticipatingTeam = new WsParticipatingTeam();
        wsParticipatingTeam.setUnitId(wsParticipatingTeamDTO.getUnitId());
        wsParticipatingTeam.setProjectId(wsParticipatingTeamDTO.getProjectId());

        List<WsParticipatingTeam> wsParticipatingTeams = wsParticipatingTeamMapper.selectWsParticipatingTeamList(wsParticipatingTeam);
        List<Integer> ids = wsParticipatingTeams.stream().map(WsParticipatingTeam::getId).collect(Collectors.toList());

       if(isRepeated(wsParticipatingTeamDTO.getTeams())) throw new CustomException("班组名称不能重复");
        int row =0;
        int compare =0;
        for (WsParticipatingTeam team : wsParticipatingTeamDTO.getTeams()) {
            int n = wsParticipatingTeamMapper.countWsParticipatingTeamInProject(team);
            if(n>0) throw new CustomException("班组名称："+team.getTeamName()+",项目已存在");
            //id空为新增
            if (StringUtils.isNull(team.getId())) {
                team.setDeleted("0");
                row +=wsParticipatingTeamMapper.insertWsParticipatingTeam(team);
                compare++;

            }else{//否则为修改
                row +=wsParticipatingTeamMapper.updateWsParticipatingTeam(team);
                if(ids.contains(team.getId())){
                    ids.remove(team.getId());
                }
                compare++;

            }
        }
        if(StringUtils.isNotEmpty(ids)) wsParticipatingTeamMapper.deleteWsParticipatingTeamByIds(ids.toArray(new Integer[ids.size()]));
        return compare==row?1:0;
    }
}
