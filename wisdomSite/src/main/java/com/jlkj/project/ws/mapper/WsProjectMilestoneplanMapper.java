package com.jlkj.project.ws.mapper;

import java.util.List;

import com.jlkj.project.ws.domain.WsProjectMilestoneplan;
import com.jlkj.project.ws.domain.vo.MoveSortVO;


/**
 * 项目里程碑计划 Mapper接口
 * 
 * @author pyy
 * @date 2020-06-09
 */

public interface WsProjectMilestoneplanMapper 
{
    /**
     * 查询项目里程碑计划 
     * 
     * @param projectId 项目 ID
     * @return 项目里程碑计划 
     */
    public List<WsProjectMilestoneplan>  selectWsProjectMilestoneplanById(Integer projectId);

    /**
     * 根据名称查询项目里程碑计划
     *
     * @param wsProjectMilestoneplan
     * @return 项目里程碑计划
     */
    public WsProjectMilestoneplan  selectWsProjectMilestoneplanByName(WsProjectMilestoneplan wsProjectMilestoneplan);

    /**
     * 查询排序的最大值
     *
     * @return 总记录数
     */
    public Integer selectMaxSort();

    /**
     * 查询并排序
     *
     * @return 总记录数
     */
    public List<WsProjectMilestoneplan> selectWsProjectMilestoneplanByProjectIdOrder(Integer projectId);

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
     * 上下移动
     * 
     * @param moveSortVOS
     * @return 结果
     */
    public int removeWsProjectMilestoneplan(List<MoveSortVO> moveSortVOS);


    /**
     * 上下移动修改项目里程碑计划
     *
     * @param wsProjectMilestoneplan 项目里程碑计划
     * @return 结果
     */
    public int updateWsProjectMilestoneplanById(WsProjectMilestoneplan wsProjectMilestoneplan);


    /**
     * 删除项目里程碑计划 
     * 
     * @param id 项目里程碑计划 ID
     * @return 结果
     */
    public int deleteWsProjectMilestoneplanById(Integer id);

    /**
     * 批量删除项目里程碑计划 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsProjectMilestoneplanByIds(Integer[] ids);
}
