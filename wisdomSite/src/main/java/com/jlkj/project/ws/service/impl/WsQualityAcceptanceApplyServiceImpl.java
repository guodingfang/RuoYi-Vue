package com.jlkj.project.ws.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.jlkj.common.constant.Constants;
import com.jlkj.common.utils.ServletUtils;
import com.jlkj.common.utils.spring.SpringUtils;
import com.jlkj.framework.redis.RedisCache;
import com.jlkj.framework.security.LoginUser;
import com.jlkj.framework.security.service.TokenService;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.service.IWsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsQualityAcceptanceApplyMapper;
import com.jlkj.project.ws.domain.WsQualityAcceptanceApply;
import com.jlkj.project.ws.service.IWsQualityAcceptanceApplyService;

/**
 * 质量验收申请 Service业务层处理
 *
 * @author gaowei
 * @date 2020-06-09
 */
@Service
public class WsQualityAcceptanceApplyServiceImpl implements IWsQualityAcceptanceApplyService
{
    @Autowired
    private WsQualityAcceptanceApplyMapper wsQualityAcceptanceApplyMapper;

    @Autowired
    private IWsProjectService wsProjectService;

    /**
     * 查询质量验收申请 
     *
     * @param id 质量验收申请 ID
     * @return 质量验收申请 
     */
    @Override
    public WsQualityAcceptanceApply selectWsQualityAcceptanceApplyById(Integer id)
    {
        return wsQualityAcceptanceApplyMapper.selectWsQualityAcceptanceApplyById(id);
    }

    /**
     * 查询质量验收申请 列表
     *
     * @param wsQualityAcceptanceApply 质量验收申请 
     * @return 质量验收申请 
     */
    @Override
    public List<WsQualityAcceptanceApply> selectWsQualityAcceptanceApplyList(WsQualityAcceptanceApply wsQualityAcceptanceApply)
    {
        wsQualityAcceptanceApply.setIsDelete("N");
        Integer[] projectIds = null;
        if(wsQualityAcceptanceApply.getProjectId() == null){
            WsProject wsProject = new WsProject();
            List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
            if(list.size() > 0) {
                LinkedList<Integer> linkedlists = new LinkedList<Integer>();
                for (int i = 0; i < list.size(); i++) {
                    linkedlists.add(list.get(i).getId());
                }
                projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);
            }
        }
        return wsQualityAcceptanceApplyMapper.selectWsQualityAcceptanceApplyList(wsQualityAcceptanceApply,projectIds);
    }

    /**
     * 新增质量验收申请 
     *
     * @param wsQualityAcceptanceApply 质量验收申请 
     * @return 结果
     */
    @Override
    public int insertWsQualityAcceptanceApply(WsQualityAcceptanceApply wsQualityAcceptanceApply)
    {
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                wsQualityAcceptanceApply.setProjectId(list.get(i).getId());
            }
        }
        wsQualityAcceptanceApply.setApplyDate(new Date());
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        String token = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest()).getToken();
        LoginUser loginUser = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY + token);
        Integer userId = Integer.valueOf(loginUser.getUser().getUserId().toString());
        wsQualityAcceptanceApply.setApplyPersonId(userId);
        wsQualityAcceptanceApply.setIsDelete("N");
        //待验收
        wsQualityAcceptanceApply.setAcceptanceState(3);
        wsQualityAcceptanceApply.setCreatedTime(new Date());
        wsQualityAcceptanceApply.setUpdatedTime(new Date());
        return wsQualityAcceptanceApplyMapper.insertWsQualityAcceptanceApply(wsQualityAcceptanceApply);
    }

    /**
     * 修改质量验收申请 
     *
     * @param wsQualityAcceptanceApply 质量验收申请 
     * @return 结果
     */
    @Override
    public int updateWsQualityAcceptanceApply(WsQualityAcceptanceApply wsQualityAcceptanceApply)
    {
        return wsQualityAcceptanceApplyMapper.updateWsQualityAcceptanceApply(wsQualityAcceptanceApply);
    }

    /**
     * 批量删除质量验收申请 
     *
     * @param ids 需要删除的质量验收申请 ID
     * @return 结果
     */
    @Override
    public int deleteWsQualityAcceptanceApplyByIds(Integer[] ids)
    {
        return wsQualityAcceptanceApplyMapper.deleteWsQualityAcceptanceApplyByIds(ids);
    }

    /**
     * 删除质量验收申请 信息
     *
     * @param id 质量验收申请 ID
     * @return 结果
     */
    @Override
    public int deleteWsQualityAcceptanceApplyById(Integer id)
    {
        return wsQualityAcceptanceApplyMapper.deleteWsQualityAcceptanceApplyById(id);
    }
}
