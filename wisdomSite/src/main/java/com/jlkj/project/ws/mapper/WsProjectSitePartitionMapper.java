package com.jlkj.project.ws.mapper;

import java.util.List;

import com.jlkj.project.ws.domain.WsProjectParticipatingUnit;
import com.jlkj.project.ws.domain.WsProjectSitePartition;
import com.jlkj.project.ws.intelligence.domain.WsElecBoxMonitorData;

/**
 * 工地分区Mapper接口
 *
 * @author pyy
 * @date 2020-06-30
 */
public interface WsProjectSitePartitionMapper {
    /**
     * 查询工地分区
     *
     * @param id 工地分区ID
     * @return 工地分区
     */
    public WsProjectSitePartition selectWsProjectSitePartitionById(Integer id);

    /**
     * 查询项目下工地分区列表
     *
     * @param projectId 项目id
     * @return 工地分区集合
     */
    public List<WsProjectSitePartition> selectWsProjectSitePartitionList(Integer projectId);


    /**
     * 根据名称查询分区详细信息
     *
     * @param wsProjectSitePartition
     * @return 项目参建单位
     */
    public WsProjectSitePartition selectWsProjectSitePartitionByName(WsProjectSitePartition wsProjectSitePartition);

    /**
     * 是否存在分区详细信息
     *
     * @param wsProjectSitePartition
     * @return 项目参建单位
     */
    public WsProjectSitePartition existsWsProjectSitePartition(WsProjectSitePartition wsProjectSitePartition);



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
     * 删除工地分区
     *
     * @param id 工地分区ID
     * @return 结果
     */
    public int deleteWsProjectSitePartitionById(Integer id);

    /**
     * 批量删除工地分区
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsProjectSitePartitionByIds(Integer[] ids);

    List<WsProjectSitePartition> selectWsProjectSitePartitionLists(Integer projectId);
}
