package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 项目信息 Mapper接口
 * 
 * @author jlkj
 * @date 2020-06-09
 */
@Mapper
public interface WsProjectMapper 
{
    /**
     * 查询项目信息 
     * 
     * @param id 项目信息 ID
     * @return 项目信息 
     */
    public WsProject selectWsProjectById(Integer id);

    /**
     * 查询项目信息
     *
     * @param wsProject
     * @return 项目信息
     */
    public WsProject selectWsProjectByName(WsProject wsProject);

    /**
     * 查询项目信息 列表
     * 
     * @param wsProject 项目信息 
     * @return 项目信息 集合
     */
    public List<WsProject> selectWsProjectList(WsProject wsProject);

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
     * 删除项目信息 
     * 
     * @param id 项目信息 ID
     * @return 结果
     */
    public int deleteWsProjectById(Integer id);

    /**
     * 批量删除项目信息 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsProjectByIds(Integer[] ids);

    public List<WsProject> selectRealTimeProjectSearch(Integer[] ids, WsProject wsProject);

    /**
     * 根据公司ID集合查询项目信息 列表
     *
     * @param projectUnitIds 所属公司ID集合
     * @return 项目信息 集合
     */
    public List<WsProject> selectProjectByEnterpriseIds(@Param("projectUnitIds") List<Long> projectUnitIds);

}
