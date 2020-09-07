package com.jlkj.project.ws.service.impl;

import com.jlkj.common.constant.Constants;
import com.jlkj.common.constant.HttpStatus;
import com.jlkj.common.constant.IntelligentMonitoringConstants;
import com.jlkj.common.constant.LaborConstants;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.ServletUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.spring.SpringUtils;
import com.jlkj.framework.redis.RedisCache;
import com.jlkj.framework.security.LoginUser;
import com.jlkj.framework.security.service.TokenService;
import com.jlkj.project.system.domain.SysUser;
import com.jlkj.project.ws.domain.WsParticipatingTeam;
import com.jlkj.project.ws.domain.WsParticipatingUnitLibrary;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsProjectParticipatingUnit;
import com.jlkj.project.ws.domain.vo.WsProjectTeamTreeVO;
import com.jlkj.project.ws.dto.WsProjectParticipatingUnitDTO;
import com.jlkj.project.ws.mapper.*;
import com.jlkj.project.ws.service.IWsProjectParticipatingUnitService;
import com.jlkj.project.ws.service.IWsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目参建单位 Service业务层处理
 *
 * @author jlkj
 * @date 2020-06-12
 */
@Service
public class WsProjectParticipatingUnitServiceImpl implements IWsProjectParticipatingUnitService
{
    @Autowired
    private WsProjectParticipatingUnitMapper wsProjectParticipatingUnitMapper;
    @Autowired
    private WsParticipatingUnitLibraryMapper wsParticipatingUnitLibraryMapper;

    @Autowired
    private WsParticipatingTeamMapper wsParticipatingTeamMapper;

    @Autowired
    private WsProjectMapper wsProjectMapper;

    @Autowired
    private WsTeamPersonMapper wsTeamPersonMapper;

    @Autowired
    private IWsProjectService wsProjectService;

    /**
     * 查询项目参建单位
     *
     * @param id 项目参建单位 ID
     * @return 项目参建单位
     */
    @Override
    public WsProjectParticipatingUnit selectWsProjectParticipatingUnitById(Integer id)
    {
        return wsProjectParticipatingUnitMapper.selectWsProjectParticipatingUnitById(id);
    }

    /**
     * 查询项目参建单位 列表
     *selectProjectTeamList
     * @param wsProjectParticipatingUnit 项目参建单位
     * @return 项目参建单位
     */
    @Override
    public List<WsProjectParticipatingUnit> selectWsProjectParticipatingUnitList(WsProjectParticipatingUnit wsProjectParticipatingUnit)
    {
        List<WsProject> projectScope = null;
        if(StringUtils.isNull(wsProjectParticipatingUnit)||StringUtils.isNull(wsProjectParticipatingUnit.getProjectId())){
            projectScope = wsProjectService.selectWsProjectList(null);
        }
        if (StringUtils.isNotNull(wsProjectParticipatingUnit)&& IntelligentMonitoringConstants.YES.equals(wsProjectParticipatingUnit.getDeleted())) {
             return wsProjectParticipatingUnitMapper.selectWsProjectParticipatingUnitProjectExperience(wsProjectParticipatingUnit) ;
        }
        return wsProjectParticipatingUnitMapper.selectWsProjectParticipatingUnitList(wsProjectParticipatingUnit,projectScope);
    }

    /**
     * 新增项目参建单位
     *
     * @param wsProjectParticipatingUnit 项目参建单位
     * @return 结果
     */
    @Transactional
    @Override
    public int insertWsProjectParticipatingUnit(WsProjectParticipatingUnit wsProjectParticipatingUnit)
    {
        wsProjectParticipatingUnit.setDeleted(LaborConstants.NO);
        int rows = wsProjectParticipatingUnitMapper.insertWsProjectParticipatingUnit(wsProjectParticipatingUnit);
        int count = wsParticipatingUnitLibraryMapper.findWsParticipatingUnitByOrgcode(wsProjectParticipatingUnit.getUnitOrgcode());
        if(count>0){
            rows = wsParticipatingUnitLibraryMapper.updateWsParticipatingUnitLibraryByProject(wsProjectParticipatingUnit);
        }else{
            rows = insertWsParticipatingUnitLibrary(wsProjectParticipatingUnit);
        }
        return insertWsParticipatingTeam(wsProjectParticipatingUnit);
    }



    private int  insertWsParticipatingUnitLibrary(WsProjectParticipatingUnit wsProjectParticipatingUnit) {
        int row =0;
        if(StringUtils.isNull(wsProjectParticipatingUnit))
            return row;
        if(StringUtils.isNotEmpty(parseTeamNameString(wsProjectParticipatingUnit))) {
            wsProjectParticipatingUnit.getParams().put("teamNames",parseTeamNameString(wsProjectParticipatingUnit));
            wsProjectParticipatingUnit.setEnterpriseId(getGroupEnterpriseId());
            row=wsParticipatingUnitLibraryMapper.insertWsParticipatingUnitLibraryByProject(wsProjectParticipatingUnit);
        }
        return row;
    }
    private Long getGroupEnterpriseId() {

        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        String token = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest()).getToken();
        LoginUser loginUser = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY + token);
        if(StringUtils.isNull(loginUser)) throw new CustomException("无数据权限！");
        SysUser user = loginUser.getUser();
        if(StringUtils.isNull(loginUser)) throw new CustomException("无数据权限！");
        return user.getEnterpriseId();
    }

    private String parseTeamNameString(WsProjectParticipatingUnit wsProjectParticipatingUnit) {
        StringBuilder teamNames = new StringBuilder();
        for (WsParticipatingTeam wsProjectParticipatingUnitTeam : wsProjectParticipatingUnit.getTeams()) {
            teamNames.append(wsProjectParticipatingUnitTeam.getTeamName()).append(",");
        }
        return teamNames.toString().substring(0,teamNames.lastIndexOf(","));
    }

    private int insertWsParticipatingTeam(WsProjectParticipatingUnit wsProjectParticipatingUnit) {
        int rows =1;
        if(StringUtils.isNull(wsProjectParticipatingUnit))
            return rows;
        List<WsParticipatingTeam>  list = new ArrayList<WsParticipatingTeam>();
        for (WsParticipatingTeam team : wsProjectParticipatingUnit.getTeams()) {

            team.setProjectId(wsProjectParticipatingUnit.getProjectId());
            team.setUnitId(wsProjectParticipatingUnit.getId());
            team.setDeleted(LaborConstants.NO);
            int count = wsParticipatingTeamMapper.countWsParticipatingTeamInProject(team);
            if(count>0) throw new CustomException("项目下已存在该班组："+team.getTeamName());
            list.add(team);
        }
        if(list.size()>0)
            rows =wsParticipatingTeamMapper.batchParticipatingTeam(list);
        return rows;
    }

    /**
     * 修改项目参建单位
     *
     * @param wsProjectParticipatingUnit 项目参建单位
     * @return 结果
     */
    @Transactional
    @Override
    public int updateWsProjectParticipatingUnit(WsProjectParticipatingUnit wsProjectParticipatingUnit)
    {
        int rows =wsProjectParticipatingUnitMapper.updateWsProjectParticipatingUnit(wsProjectParticipatingUnit);
        int count = wsParticipatingUnitLibraryMapper.findWsParticipatingUnitByOrgcode(wsProjectParticipatingUnit.getUnitOrgcode());
        if(count>0){
            rows = wsParticipatingUnitLibraryMapper.updateWsParticipatingUnitLibraryByProject(wsProjectParticipatingUnit);
        }else{
            rows = insertWsParticipatingUnitLibrary(wsProjectParticipatingUnit);
        }
        return rows;
    }

    /**
     * 批量删除项目参建单位
     *
     * @param ids 需要删除的项目参建单位 ID
     * @return 结果
     */
    @Override
    public int deleteWsProjectParticipatingUnitByIds(Integer[] ids)
    {
        if (StringUtils.isNotNull(ids)&&ids.length>0) {
            for (int i = 0; i < ids.length; i++) {
                int count = wsTeamPersonMapper.countWsTeamPersonInWsProjectParticipatingUnit(ids[i]);
                if (count>0) {
                    throw new CustomException("参建单位下存在班组人员，不可删除");
                }
            }
        }
        return wsProjectParticipatingUnitMapper.deleteWsProjectParticipatingUnitByIds(ids);
    }

    /**
     * 删除项目参建单位 信息
     *
     * @param id 项目参建单位 ID
     * @return 结果
     */
    @Override
    public int deleteWsProjectParticipatingUnitById(Integer id)
    {
        int count = wsTeamPersonMapper.countWsTeamPersonInWsProjectParticipatingUnit(id);
        if (count>0) {
            throw new CustomException("参建单位下存在班组人员，不可删除");
        }
        return wsProjectParticipatingUnitMapper.deleteWsProjectParticipatingUnitById(id);
    }

    @Override
    public boolean existWsProjectParticipatingUnitInProject(WsProjectParticipatingUnit wsProjectParticipatingUnit) {
        return wsProjectParticipatingUnitMapper.countWsProjectParticipatingUnitInProject(wsProjectParticipatingUnit)==1?true:false;
    }

    @Override
    @Transactional
    public int patchAdd(WsProjectParticipatingUnitDTO wsProjectParticipatingUnitDTO) {
        //获取单位库列表
        WsParticipatingUnitLibrary wsParticipatingUnitLibrary = new WsParticipatingUnitLibrary();
        wsParticipatingUnitLibrary.getParams().put("ids",wsProjectParticipatingUnitDTO.getIds());
        List<WsParticipatingUnitLibrary> list = wsParticipatingUnitLibraryMapper.selectWsParticipatingUnitLibraryList(wsParticipatingUnitLibrary);
        WsProjectParticipatingUnit wsProjectParticipatingUnit =null;
        int count=0;
        int rows =0;
        for (WsParticipatingUnitLibrary participatingUnitLibrary : list) {
            wsProjectParticipatingUnit =  convertToWsProjectParticipatingUnit(participatingUnitLibrary,wsProjectParticipatingUnitDTO.getProjectId());
            count=  wsProjectParticipatingUnitMapper.countWsProjectParticipatingUnitInProject(wsProjectParticipatingUnit);
           if(count>0){
               throw  new CustomException("参建单位已存在： "+wsProjectParticipatingUnit.getUnitName(), HttpStatus.REPEAT_RECORD);
           }
            rows =insertWsProjectParticipatingUnit(wsProjectParticipatingUnit);
        }

        return rows;
    }
    @Override
    public List<WsProjectTeamTreeVO> selectProjectTeamList() {

        //添加数据范围
        List<WsProject> projectScope =  wsProjectService.selectWsProjectList(null);
       //获取工程班组列表
        List<WsProjectTeamTreeVO> wsProjectTeamTreeVOS = wsProjectParticipatingUnitMapper.selectProjectTeamTree(projectScope);

        return wsProjectTeamTreeVOS;
    }
    /*
        private void addUnitTypes(List<WsProjectTeamTreeVO> treeList, List<WsProjectParticipatingUnit> unitList) {
            for (WsProjectParticipatingUnit wsProjectParticipatingUnit : unitList) {
                WsProjectTeamTreeVO wsProjectTeamTreeVO = new WsProjectTeamTreeVO(treeList.size()+1,wsProjectParticipatingUnit.getId(),2, wsProjectParticipatingUnit.getUnitTypeName(),wsProjectParticipatingUnit.getProjectId(),wsProjectParticipatingUnit.getProjectId());
                treeList.add(wsProjectTeamTreeVO);
            }
        }

        private void addUnitsAndTeams(List<WsProjectTeamTreeVO> treeList, List<WsProjectParticipatingUnit> unitList) {
            for (WsProjectParticipatingUnit wsProjectParticipatingUnit : unitList) {
                WsProjectTeamTreeVO wsProjectTeamTreeVO = new WsProjectTeamTreeVO(treeList.size()+1,wsProjectParticipatingUnit.getId(),3, wsProjectParticipatingUnit.getUnitName(),wsProjectParticipatingUnit.getUnitTypeId(),wsProjectParticipatingUnit.getProjectId());
                treeList.add(wsProjectTeamTreeVO);
                addTeams(treeList,wsProjectParticipatingUnit.getTeams());
            }
        }

        private void addTeams(List<WsProjectTeamTreeVO> treeList, List<WsParticipatingTeam> teams) {
            for (WsParticipatingTeam team : teams) {
                if(StringUtils.isNotNull(team.getId())){
                    WsProjectTeamTreeVO wsProjectTeamTreeVO = new WsProjectTeamTreeVO(treeList.size()+1,team.getId(),4, team.getTeamName(),team.getUnitId(),team.getProjectId());
                    treeList.add(wsProjectTeamTreeVO);
                }
            }
        }
    */
    @Override
    public List<WsProjectTeamTreeVO> buildProjectTeamTreeSelect(List<WsProjectTeamTreeVO> projectTeamList) {
        List<WsProjectTeamTreeVO> list = buildProjectTeamTreeVO(projectTeamList);

        return list.stream().map(WsProjectTeamTreeVO::new).collect(Collectors.toList());
    }
    /**
     * 构建前端所需要树结构
     *
     * @param projectTeamList 部门列表
     * @return 树结构列表
     */
    @Override
    public List<WsProjectTeamTreeVO> buildProjectTeamTreeVO(List<WsProjectTeamTreeVO> projectTeamList)
    {
        List<WsProjectTeamTreeVO> returnList = new ArrayList<WsProjectTeamTreeVO>();
        List<String> tempList = new ArrayList<String>();
        for (WsProjectTeamTreeVO projectTeamVO : projectTeamList)
        {
            tempList.add(projectTeamVO.getId());
        }
        for (Iterator<WsProjectTeamTreeVO> iterator = projectTeamList.iterator(); iterator.hasNext();)
        {
            WsProjectTeamTreeVO treeVO = (WsProjectTeamTreeVO) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(treeVO.getParentId()))
            {
                recursionFn(projectTeamList, treeVO);
                returnList.add(treeVO);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = projectTeamList;
        }
        return returnList;

    }


    /**
     * 递归列表
     */
    private void recursionFn(List<WsProjectTeamTreeVO> list, WsProjectTeamTreeVO vo)
    {
        // 得到子节点列表
        List<WsProjectTeamTreeVO> childList = getChildList(list, vo);
        vo.setChildren(childList);
        for (WsProjectTeamTreeVO tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<WsProjectTeamTreeVO> it = childList.iterator();
                while (it.hasNext())
                {
                    WsProjectTeamTreeVO n = (WsProjectTeamTreeVO) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<WsProjectTeamTreeVO> getChildList(List<WsProjectTeamTreeVO> list, WsProjectTeamTreeVO vo)
    {
        List<WsProjectTeamTreeVO> tlist = new ArrayList<WsProjectTeamTreeVO>();
        Iterator<WsProjectTeamTreeVO> it = list.iterator();
        while (it.hasNext())
        {
            WsProjectTeamTreeVO n = (WsProjectTeamTreeVO) it.next();
            if (StringUtils.isNotEmpty(n.getParentId()) && n.getParentId().equals(vo.getId()))
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<WsProjectTeamTreeVO> list, WsProjectTeamTreeVO t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }


    private WsProjectParticipatingUnit convertToWsProjectParticipatingUnit(WsParticipatingUnitLibrary pul, Integer projectId) {
        List<WsParticipatingTeam> teams = new ArrayList<WsParticipatingTeam>();
        String[] teamNames = pul.getTeamNames().split(",");
        for (String teamName : teamNames) {
            WsParticipatingTeam wsParticipatingTeam = new WsParticipatingTeam();
            wsParticipatingTeam.setTeamName(teamName);
            teams.add(wsParticipatingTeam);
        }

        WsProjectParticipatingUnit wsProjectParticipatingUnit = new WsProjectParticipatingUnit(
                projectId,pul.getUnitName(),pul.getUnitTypeId(),pul.getUnitOrgcode(),
                pul.getRegDate(),pul.getRegAreaId(),pul.getRegCapital(),pul.getUnitNatureCode(),
                pul.getLegalPerson(),pul.getAddress(),pul.getContacts(),pul.getContactNumber(),
                pul.getOfficeNumber(),pul.getFaxNumber(),pul.getComment(),
                LaborConstants.BUNDING_STATUS,teams);
        return wsProjectParticipatingUnit;
    }



}
