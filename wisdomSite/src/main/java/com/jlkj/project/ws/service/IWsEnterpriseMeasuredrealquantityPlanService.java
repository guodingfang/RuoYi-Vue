package com.jlkj.project.ws.service;

import java.util.List;

import com.jlkj.project.ws.domain.TreeData;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityPlan;

/**
 * 实测实量平面图管理 Service接口
 * 
 * @author pyy
 * @date 2020-06-20
 */
public interface IWsEnterpriseMeasuredrealquantityPlanService 
{
    /**
     * 查询实测实量平面图管理 
     * 
     * @param id 实测实量平面图管理 ID
     * @return 实测实量平面图管理 
     */
    public List<WsEnterpriseMeasuredrealquantityPlan> selectWsEnterpriseMeasuredrealquantityPlanById(Integer id);

    /**
     * 查询父级信息
     *
     * @param parentId 父级id
     * @return 实测实量平面图管理
     */
    public WsEnterpriseMeasuredrealquantityPlan selectParentWsEnterpriseMeasuredrealquantityPlan(Integer parentId);


    /**
     * 查询实测实量平面图管理 列表
     * 
     * @param wsEnterpriseMeasuredrealquantityPlan 实测实量平面图管理 
     * @return 实测实量平面图管理 集合
     */
    public List<WsEnterpriseMeasuredrealquantityPlan> selectWsEnterpriseMeasuredrealquantityPlanList(WsEnterpriseMeasuredrealquantityPlan wsEnterpriseMeasuredrealquantityPlan);

    /**
     * 查询父级实测实量平面图管理
     *
     * @param
     * @return 实测实量平面图管理 集合
     */
    public List<WsEnterpriseMeasuredrealquantityPlan> selectWsEnterpriseMeasuredrealquantityPlanByParentId(Integer projectId);

    /**
     * 查询实测实量平面图管理 树
     *
     * @param wsEnterpriseMeasuredrealquantityPlan 实测实量平面图管理
     * @return 实测实量平面图管理 集合
     */
    public List<TreeData> selectWsEnterpriseMeasuredrealquantityPlanTreeList(WsEnterpriseMeasuredrealquantityPlan wsEnterpriseMeasuredrealquantityPlan);

    /**
     * 查询总记录数
     *
     * @param wsEnterpriseMeasuredrealquantityPlan 实测实量平面图管理
     * @return 实测实量平面图管理 集合
     */
    public int selectTotalCount(WsEnterpriseMeasuredrealquantityPlan wsEnterpriseMeasuredrealquantityPlan);


    /**
     * 新增实测实量平面图管理 
     * 
     * @param wsEnterpriseMeasuredrealquantityPlan 实测实量平面图管理 
     * @return 结果
     */
    public int insertWsEnterpriseMeasuredrealquantityPlan(WsEnterpriseMeasuredrealquantityPlan wsEnterpriseMeasuredrealquantityPlan);

    /**
     * 修改实测实量平面图管理 
     * 
     * @param wsEnterpriseMeasuredrealquantityPlan 实测实量平面图管理 
     * @return 结果
     */
    public int updateWsEnterpriseMeasuredrealquantityPlan(WsEnterpriseMeasuredrealquantityPlan wsEnterpriseMeasuredrealquantityPlan);



    /**
     * 删除实测实量平面图管理 信息
     * 
     * @param id 实测实量平面图管理 ID
     * @return 结果
     */
    public int deleteWsEnterpriseMeasuredrealquantityPlanById(Integer id);
}
