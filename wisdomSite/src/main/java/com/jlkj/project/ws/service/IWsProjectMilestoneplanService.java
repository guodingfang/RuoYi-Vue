package com.jlkj.project.ws.service;

import java.util.List;
import com.jlkj.project.ws.domain.WsProjectMilestoneplan;
import com.jlkj.project.ws.domain.vo.MoveSortVO;

/**
 * 项目里程碑计划 Service接口
 * 
 * @author pyy
 * @date 2020-06-09
 */
public interface IWsProjectMilestoneplanService 
{
    /**
     * 查询项目里程碑计划 
     * 
     * @param projectId 项目 ID
     * @return 项目里程碑计划 
     */
    public List<WsProjectMilestoneplan> selectWsProjectMilestoneplanByProjectId(Integer projectId);

    /**
     * 查询项目里程碑计划 列表
     * 
     * @param wsProjectMilestoneplan 项目里程碑计划 
     * @return 项目里程碑计划 集合
     */
    public List<WsProjectMilestoneplan> selectWsProjectMilestoneplanList(WsProjectMilestoneplan wsProjectMilestoneplan);

    /**
     * 新增项目里程碑计划 
     * 
     * @param wsProjectMilestoneplan 项目里程碑计划 
     * @return 结果
     */
    public int insertWsProjectMilestoneplan(WsProjectMilestoneplan wsProjectMilestoneplan);

    /**
     * 上下移动修改项目里程碑计划
     * 
     * @param moveSortVOS
     * @return 结果
     */
    public  int removeWsProjectMilestoneplan(List<MoveSortVO> moveSortVOS);


    /**
     * 修改项目里程碑计划
     *
     * @param wsProjectMilestoneplan 项目里程碑计划
     * @return 结果
     */
    public int updateWsProjectMilestoneplanById(WsProjectMilestoneplan wsProjectMilestoneplan);


    /**
     * 查询并排序
     *
     * @return
     */
    public List<WsProjectMilestoneplan> selectWsProjectMilestoneplanByProjectIdOrder(Integer projectId);

    /**
     * 批量删除项目里程碑计划 
     * 
     * @param ids 需要删除的项目里程碑计划 ID
     * @return 结果
     */
    public int deleteWsProjectMilestoneplanByIds(Integer[] ids);

    /**
     * 删除项目里程碑计划 信息
     * 
     * @param id 项目里程碑计划 ID
     * @return 结果
     */
    public int deleteWsProjectMilestoneplanById(Integer id);
}
