package com.jlkj.project.ws.service.impl;

import java.util.List;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.framework.interceptor.annotation.RepeatSubmit;
import com.jlkj.project.ws.domain.vo.MoveSortVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsProjectMilestoneplanMapper;
import com.jlkj.project.ws.domain.WsProjectMilestoneplan;
import com.jlkj.project.ws.service.IWsProjectMilestoneplanService;

/**
 * 项目里程碑计划 Service业务层处理
 *
 * @author pyy
 * @date 2020-06-09
 */
@Service
public class WsProjectMilestoneplanServiceImpl implements IWsProjectMilestoneplanService {
    @Autowired
    private WsProjectMilestoneplanMapper wsProjectMilestoneplanMapper;


    /**
     * 查询并排序
     *
     * @return
     */
    @Override
    public List<WsProjectMilestoneplan> selectWsProjectMilestoneplanByProjectIdOrder(Integer projectId) {
        return wsProjectMilestoneplanMapper.selectWsProjectMilestoneplanByProjectIdOrder(projectId);
    }

    /**
     * 查询项目里程碑计划
     *
     * @param projectId 项目里程碑计划 ID
     * @return 项目里程碑计划
     */
    @Override
    public List<WsProjectMilestoneplan> selectWsProjectMilestoneplanByProjectId(Integer projectId) {
        return wsProjectMilestoneplanMapper.selectWsProjectMilestoneplanById(projectId);
    }

    /**
     * 查询项目里程碑计划 列表
     *
     * @param wsProjectMilestoneplan 项目里程碑计划
     * @return 项目里程碑计划
     */
    @Override
    public List<WsProjectMilestoneplan> selectWsProjectMilestoneplanList(WsProjectMilestoneplan wsProjectMilestoneplan) {
        return wsProjectMilestoneplanMapper.selectWsProjectMilestoneplanList(wsProjectMilestoneplan);
    }

    /**
     * 新增项目里程碑计划
     *
     * @param wsProjectMilestoneplan 项目里程碑计划
     * @return 结果
     */
    @Override
    public int insertWsProjectMilestoneplan(WsProjectMilestoneplan wsProjectMilestoneplan) {
        WsProjectMilestoneplan projectMilestoneplan = new WsProjectMilestoneplan();
        projectMilestoneplan.setMilepostName(wsProjectMilestoneplan.getMilepostName());
        projectMilestoneplan.setProjectId(wsProjectMilestoneplan.getProjectId());
        List<WsProjectMilestoneplan> projectMilestoneplans = wsProjectMilestoneplanMapper.selectWsProjectMilestoneplanList(projectMilestoneplan);
        if (StringUtils.isEmpty(projectMilestoneplans)) {
            int maxSort = 0;
            if (wsProjectMilestoneplanMapper.selectMaxSort() != null) {
                maxSort = wsProjectMilestoneplanMapper.selectMaxSort();
            }

            if (maxSort > 0) {
                wsProjectMilestoneplan.setMilepostSort(++maxSort);
            } else {
                wsProjectMilestoneplan.setMilepostSort(1);
            }
            return wsProjectMilestoneplanMapper.insertWsProjectMilestoneplan(wsProjectMilestoneplan);
        }
        return 0;
    }

    /**
     * 修改项目里程碑计划
     *
     * @param moveSortVOS
     * @return 结果
     */
    @Override
    @RepeatSubmit
    public synchronized int removeWsProjectMilestoneplan(List<MoveSortVO> moveSortVOS) {

        return wsProjectMilestoneplanMapper.removeWsProjectMilestoneplan(moveSortVOS);
    }

    @Override
    public int updateWsProjectMilestoneplanById(WsProjectMilestoneplan wsProjectMilestoneplan) {
        WsProjectMilestoneplan projectMliestoneplan = wsProjectMilestoneplanMapper.selectWsProjectMilestoneplanByName(wsProjectMilestoneplan);
        if (projectMliestoneplan != null) {
            return 0;
        }
        return wsProjectMilestoneplanMapper.updateWsProjectMilestoneplanById(wsProjectMilestoneplan);
    }

    /**
     * 批量删除项目里程碑计划
     *
     * @param ids 需要删除的项目里程碑计划 ID
     * @return 结果
     */
    @Override
    public int deleteWsProjectMilestoneplanByIds(Integer[] ids) {
        return wsProjectMilestoneplanMapper.deleteWsProjectMilestoneplanByIds(ids);
    }

    /**
     * 删除项目里程碑计划 信息
     *
     * @param id 项目里程碑计划 ID
     * @return 结果
     */
    @Override
    public int deleteWsProjectMilestoneplanById(Integer id) {
        return wsProjectMilestoneplanMapper.deleteWsProjectMilestoneplanById(id);
    }
}
