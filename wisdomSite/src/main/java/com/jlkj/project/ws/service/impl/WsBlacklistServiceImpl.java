package com.jlkj.project.ws.service.impl;

import com.jlkj.common.constant.LaborConstants;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.system.domain.SysUser;
import com.jlkj.project.ws.domain.WsBlacklist;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsTeamPerson;
import com.jlkj.project.ws.domain.vo.WsBlacklistVO;
import com.jlkj.project.ws.mapper.WsBlacklistMapper;
import com.jlkj.project.ws.mapper.WsTeamPersonMapper;
import com.jlkj.project.ws.service.IWsBlacklistService;
import com.jlkj.project.ws.service.IWsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 【人员黑名单】Service业务层处理
 *
 * @author liujie
 * @date 2020-06-19
 */
@Service
public class WsBlacklistServiceImpl implements IWsBlacklistService
{
    @Autowired
    private WsBlacklistMapper wsBlacklistMapper;
    @Autowired
    private WsTeamPersonMapper wsTeamPersonMapper;
    @Autowired
    private IWsProjectService wsProjectService;

    @Override
    public int batchWsBlacklist(WsBlacklist wsBlacklist) {
        if(StringUtils.isNull(wsBlacklist)){
            throw new CustomException("请选择人员并录入违章信息");
        }
        if(StringUtils.isNull(wsBlacklist.getPersonIds())){
            throw new CustomException("请选择人员");
        }
        SysUser userInfo = SecurityUtils.getUserInfo();
        List<WsBlacklist> wsBlacklists =  new ArrayList<>();
        for (int i = 0; i < wsBlacklist.getPersonIds().length; i++) {
            WsBlacklist blacklist = new WsBlacklist();
            blacklist.setIllegalDate(wsBlacklist.getIllegalDate());
            blacklist.setRecorder(userInfo.getUserName());
            blacklist.setIllegalReason(wsBlacklist.getIllegalReason());
            blacklist.setImageUrl(wsBlacklist.getImageUrl());
            blacklist.setPersonId(wsBlacklist.getPersonIds()[i]);
            blacklist.setIdNumber(wsBlacklist.getIdNumber());
            wsBlacklists.add(blacklist);
        }
        int rows =wsBlacklistMapper.batchInsertWsBlacklist(wsBlacklists);

        return rows;
    }



    /**
     * 查询【人员黑名单】
     *
     * @param id 【人员黑名单】ID
     * @return 【人员黑名单】
     */
    @Override
    public WsBlacklistVO selectWsBlacklistById(Integer id)
    {
        return wsBlacklistMapper.selectWsBlacklistById(id);
    }

    /**
     * 查询【人员黑名单】列表
     *
     * @param wsBlacklistVO 【人员黑名单】
     * @return 【人员黑名单】
     */
    @Override
    public List<WsBlacklistVO> selectWsBlacklistList(WsBlacklistVO wsBlacklistVO)
    {
        List<WsProject> projectScope = null;
        if(StringUtils.isNull(wsBlacklistVO)||StringUtils.isNull(wsBlacklistVO.getProjectId())){
            projectScope = SecurityUtils.getProjectList();
        }
        if(StringUtils.isNull(wsBlacklistVO)|| StringUtils.isEmpty(wsBlacklistVO.getDeleted())) wsBlacklistVO.setDeleted(LaborConstants.NO);
        return wsBlacklistMapper.selectWsBlacklistList(wsBlacklistVO,projectScope);
    }

    /**
     * 新增【人员黑名单】
     *
     * @param wsBlacklist 【人员黑名单】
     * @return 结果
     */
    @Override
    public int insertWsBlacklist(WsBlacklist wsBlacklist)
    {
        SysUser userInfo = SecurityUtils.getUserInfo();
        wsBlacklist.setRecorder(userInfo.getUserName());
        wsBlacklist.setDeleted(LaborConstants.NO);
        return wsBlacklistMapper.insertWsBlacklist(wsBlacklist);
    }

    /**
     * 修改【人员黑名单】
     *
     * @param wsBlacklist 【人员黑名单】
     * @return 结果
     */
    @Override
    public int updateWsBlacklist(WsBlacklist wsBlacklist)
    {
        return wsBlacklistMapper.updateWsBlacklist(wsBlacklist);
    }

    /**
     * 批量删除【人员黑名单】
     *
     * @param ids 需要删除的【人员黑名单】ID
     * @return 结果
     */
    @Override
    public int deleteWsBlacklistByIds(Integer[] ids)
    {
        return wsBlacklistMapper.deleteWsBlacklistByIds(ids);
    }

    @Override
    public int batchRemoveBlacklist(Integer[] ids) {
        int rows =0;
        if(StringUtils.isNull(ids)|| ids.length==0){
            throw new CustomException("请选择人员");
        }
        WsTeamPerson wsTeamPerson = new WsTeamPerson();
        wsTeamPerson.getParams().put("ids",ids);
        wsTeamPerson.setPersonStatus(LaborConstants.OUT);
        wsTeamPerson.setBlacklist(LaborConstants.WHITE_LIST);
        rows = wsTeamPersonMapper.batchUpateWsTeamPerson(wsTeamPerson);
        rows = wsBlacklistMapper.batchRemoveWsBlacklist(ids);
        return rows;
    }

    /**
     * 删除【人员黑名单】信息
     *
     * @param id 【人员黑名单】ID
     * @return 结果
     */
    @Override
    public int deleteWsBlacklistById(Integer id)
    {
        return wsBlacklistMapper.deleteWsBlacklistById(id);
    }
}
