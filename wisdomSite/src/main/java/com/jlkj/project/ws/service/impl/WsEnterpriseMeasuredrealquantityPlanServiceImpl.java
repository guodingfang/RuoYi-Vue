package com.jlkj.project.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.jlkj.project.ws.domain.TreeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsEnterpriseMeasuredrealquantityPlanMapper;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityPlan;
import com.jlkj.project.ws.service.IWsEnterpriseMeasuredrealquantityPlanService;

/**
 * 实测实量平面图管理 Service业务层处理
 *
 * @author pyy
 * @date 2020-06-20
 */
@Service
public class WsEnterpriseMeasuredrealquantityPlanServiceImpl implements IWsEnterpriseMeasuredrealquantityPlanService {
    @Autowired
    private WsEnterpriseMeasuredrealquantityPlanMapper wsEnterpriseMeasuredrealquantityPlanMapper;

    /**
     * 查询实测实量平面图管理
     *
     * @param id 实测实量平面图管理 ID
     * @return 实测实量平面图管理
     */
    @Override
    public List<WsEnterpriseMeasuredrealquantityPlan> selectWsEnterpriseMeasuredrealquantityPlanById(Integer id) {
        return wsEnterpriseMeasuredrealquantityPlanMapper.selectWsEnterpriseMeasuredrealquantityPlanById(id);
    }

    /**
     * 查询实测实量平面图管理 列表
     *
     * @param wsEnterpriseMeasuredrealquantityPlan 实测实量平面图管理
     * @return 实测实量平面图管理
     */
    @Override
    public List<WsEnterpriseMeasuredrealquantityPlan> selectWsEnterpriseMeasuredrealquantityPlanList(WsEnterpriseMeasuredrealquantityPlan wsEnterpriseMeasuredrealquantityPlan) {
        return wsEnterpriseMeasuredrealquantityPlanMapper.selectWsEnterpriseMeasuredrealquantityPlanList(wsEnterpriseMeasuredrealquantityPlan);
    }

    /**
     * 查询总记录数
     *
     * @param wsEnterpriseMeasuredrealquantityPlan 实测实量平面图管理
     * @return 实测实量平面图管理
     */
    @Override
    public int selectTotalCount(WsEnterpriseMeasuredrealquantityPlan wsEnterpriseMeasuredrealquantityPlan) {
        return wsEnterpriseMeasuredrealquantityPlanMapper.selectTotalCount(wsEnterpriseMeasuredrealquantityPlan);
    }

    /**
     * 拼装树形结构数据
     *
     * @param wsEnterpriseMeasuredrealquantityPlan 实测实量平面图管理
     * @return 实测实量平面图管理
     */
    @Override
    public List<TreeData> selectWsEnterpriseMeasuredrealquantityPlanTreeList(WsEnterpriseMeasuredrealquantityPlan wsEnterpriseMeasuredrealquantityPlan) {
//        return wsEnterpriseMeasuredrealquantityPlanMapper.selectWsEnterpriseMeasuredrealquantityPlanTreeList(wsEnterpriseMeasuredrealquantityPlan);

        List<WsEnterpriseMeasuredrealquantityPlan> empParent = wsEnterpriseMeasuredrealquantityPlanMapper.selectAllParentWsEnterpriseMeasuredrealquantityPlan(wsEnterpriseMeasuredrealquantityPlan);
        List<WsEnterpriseMeasuredrealquantityPlan> empChild = wsEnterpriseMeasuredrealquantityPlanMapper.selectAllChildrenWsEnterpriseMeasuredrealquantityPlan(wsEnterpriseMeasuredrealquantityPlan);
        List<TreeData> treeDataList = new ArrayList<>();
        for (int i = 0; i < empParent.size(); i++) {
            TreeData treeData = new TreeData();
            List<TreeData> childList = new ArrayList<>();
            for (int i1 = 0; i1 < empChild.size(); i1++) {
                if (empParent.get(i).getId() == empChild.get(i1).getParentId()) {
                    TreeData childTree = new TreeData();
                    childTree.setId(empChild.get(i1).getId());
                    childTree.setRegionName(empChild.get(i1).getRegionName());
                    childList.add(childTree);
                }
            }
            treeData.setId(empParent.get(i).getId());
            treeData.setRegionName(empParent.get(i).getRegionName());
            treeData.setChildren(childList);
            treeDataList.add(treeData);
        }
        return treeDataList;
    }

    /**
     * 新增实测实量平面图管理
     *
     * @param wsEnterpriseMeasuredrealquantityPlan 实测实量平面图管理
     * @return 结果
     */
    @Override
    public int insertWsEnterpriseMeasuredrealquantityPlan(WsEnterpriseMeasuredrealquantityPlan wsEnterpriseMeasuredrealquantityPlan) {
        return wsEnterpriseMeasuredrealquantityPlanMapper.insertWsEnterpriseMeasuredrealquantityPlan(wsEnterpriseMeasuredrealquantityPlan);
    }

    /**
     * 修改实测实量平面图管理
     *
     * @param wsEnterpriseMeasuredrealquantityPlan 实测实量平面图管理
     * @return 结果
     */
    @Override
    public int updateWsEnterpriseMeasuredrealquantityPlan(WsEnterpriseMeasuredrealquantityPlan wsEnterpriseMeasuredrealquantityPlan) {
        return wsEnterpriseMeasuredrealquantityPlanMapper.updateWsEnterpriseMeasuredrealquantityPlan(wsEnterpriseMeasuredrealquantityPlan);
    }


    @Override
    public WsEnterpriseMeasuredrealquantityPlan selectParentWsEnterpriseMeasuredrealquantityPlan(Integer parentId) {
        return wsEnterpriseMeasuredrealquantityPlanMapper.selectParentWsEnterpriseMeasuredrealquantityPlan(parentId);
    }

    @Override
    public List<WsEnterpriseMeasuredrealquantityPlan> selectWsEnterpriseMeasuredrealquantityPlanByParentId(Integer projectId) {
        return wsEnterpriseMeasuredrealquantityPlanMapper.selectWsEnterpriseMeasuredrealquantityPlanByParentId(projectId);
    }

    /**
     * 删除实测实量平面图管理 信息
     *
     * @param id 实测实量平面图管理 ID
     * @return 结果
     */
    @Override
    public int deleteWsEnterpriseMeasuredrealquantityPlanById(Integer id) {

        return wsEnterpriseMeasuredrealquantityPlanMapper.deleteWsEnterpriseMeasuredrealquantityPlanById(id);
    }
}
