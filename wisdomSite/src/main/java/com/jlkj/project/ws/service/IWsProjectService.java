package com.jlkj.project.ws.service;

import java.util.List;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsProjectMilestoneplan;

/**
 * 项目信息 Service接口
 * 
 * @author jlkj
 * @date 2020-06-09
 */
public interface IWsProjectService 
{
    /**
     * 查询项目信息 
     * 
     * @param id 项目信息 ID
     * @return 项目信息 
     */
    public WsProject selectWsProjectById(Integer id);


    /**
     * 查询项目信息 列表
     * 
     * @param wsProject 项目信息 
     * @return 项目信息 集合
     */
    public List<WsProject> selectWsProjectList(WsProject wsProject);


    /**
     * 查询项目信息 列表
     *
     * @param wsProject 项目信息
     * @return 项目信息 集合
     */
    public List<WsProject> selectWsProjectListByUser(WsProject wsProject);

    /**
     * 查询所有项目信息
     *
     * @return 项目信息 集合
     */
    public List<WsProject> selectAllWsProjectList();

    /**
     * 新增项目信息 
     * 
     * @param wsProject 项目信息 
     * @return 结果
     */
    public int insertWsProject(WsProject wsProject);

    /**
     * 修改项目信息 
     * 
     * @param wsProject 项目信息 
     * @return 结果
     */
    public int updateWsProject(WsProject wsProject);


    /**
     * 删除项目信息 信息
     * 
     * @param id 项目信息 ID
     * @return 结果
     */
    public int deleteWsProjectById(Integer id);
}
