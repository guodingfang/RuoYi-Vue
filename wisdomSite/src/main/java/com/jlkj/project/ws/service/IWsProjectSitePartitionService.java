package com.jlkj.project.ws.service;

import java.util.List;

import com.jlkj.project.ws.domain.WsProjectSitePartition;

/**
 * 工地分区Service接口
 *
 * @author pyy
 * @date 2020-06-30
 */
public interface IWsProjectSitePartitionService {
    /**
     * 查询工地分区
     *
     * @param id 工地分区ID
     * @return 工地分区
     */
    public WsProjectSitePartition selectWsProjectSitePartitionById(Integer id);

    /**
     * 查询工地分区列表
     *
     * @param projectId 项目id
     * @return 工地分区集合
     */
    public List<WsProjectSitePartition> selectWsProjectSitePartitionList(Integer projectId);

    /**
     * 新增工地分区
     *
     * @param wsProjectSitePartition 工地分区
     * @return 结果
     */
    public int insertWsProjectSitePartition(WsProjectSitePartition wsProjectSitePartition);

    /**
     * 修改工地分区
     *
     * @param wsProjectSitePartition 工地分区
     * @return 结果
     */
    public int updateWsProjectSitePartition(WsProjectSitePartition wsProjectSitePartition);

    /**
     * 批量删除工地分区
     *
     * @param ids 需要删除的工地分区ID
     * @return 结果
     */
    public int deleteWsProjectSitePartitionByIds(Integer[] ids);

    /**
     * 删除工地分区信息
     *
     * @param id 工地分区ID
     * @return 结果
     */
    public String deleteWsProjectSitePartitionById(Integer id);
}
