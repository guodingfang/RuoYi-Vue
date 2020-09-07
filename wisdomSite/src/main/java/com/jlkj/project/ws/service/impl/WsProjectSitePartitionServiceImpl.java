package com.jlkj.project.ws.service.impl;

import java.util.List;

import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.ws.mapper.WsSitePartitionItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsProjectSitePartitionMapper;
import com.jlkj.project.ws.domain.WsProjectSitePartition;
import com.jlkj.project.ws.service.IWsProjectSitePartitionService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工地分区Service业务层处理
 *
 * @author pyy
 * @date 2020-06-30
 */
@Service
public class WsProjectSitePartitionServiceImpl implements IWsProjectSitePartitionService {
    @Autowired
    private WsProjectSitePartitionMapper wsProjectSitePartitionMapper;
    @Autowired
    private WsSitePartitionItemMapper wsSitePartitionItemMapper;


    /**
     * 查询工地分区
     *
     * @param id 工地分区ID
     * @return 工地分区
     */
    @Override
    public WsProjectSitePartition selectWsProjectSitePartitionById(Integer id) {
        return wsProjectSitePartitionMapper.selectWsProjectSitePartitionById(id);
    }

    /**
     * 查询工地分区列表
     *
     * @param projectId 项目Id
     * @return 工地分区
     */
    @Override
    public List<WsProjectSitePartition> selectWsProjectSitePartitionList(Integer projectId) {
        return wsProjectSitePartitionMapper.selectWsProjectSitePartitionList(projectId);
    }


    /**
     * 新增工地分区
     *
     * @param wsProjectSitePartition 工地分区
     * @return 结果
     */
    @Override
    public int insertWsProjectSitePartition(WsProjectSitePartition wsProjectSitePartition) {
        WsProjectSitePartition wsProjectSitePartition1 = wsProjectSitePartitionMapper.existsWsProjectSitePartition(wsProjectSitePartition);
        if (wsProjectSitePartition1 != null) {
            return 0;
        }
        return wsProjectSitePartitionMapper.insertWsProjectSitePartition(wsProjectSitePartition);
    }

    /**
     * 修改工地分区
     *
     * @param wsProjectSitePartition 工地分区
     * @return 结果
     */
    @Override
    public int updateWsProjectSitePartition(WsProjectSitePartition wsProjectSitePartition) {
        WsProjectSitePartition wsProjectSitePartition1 = wsProjectSitePartitionMapper.selectWsProjectSitePartitionByName(wsProjectSitePartition);
        if (wsProjectSitePartition1 != null) {
            return 0;
        }
        return wsProjectSitePartitionMapper.updateWsProjectSitePartition(wsProjectSitePartition);
    }

    /**
     * 批量删除工地分区
     *
     * @param ids 需要删除的工地分区ID
     * @return 结果
     */
    @Override
    public int deleteWsProjectSitePartitionByIds(Integer[] ids) {
        return wsProjectSitePartitionMapper.deleteWsProjectSitePartitionByIds(ids);
    }

    /**
     * 删除工地分区信息
     *
     * @param id 工地分区ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String deleteWsProjectSitePartitionById(Integer id) {
        String str = wsSitePartitionItemMapper.ExistsSitePartitionItem(id);
        if (StringUtils.isEmpty(str)){
            return wsProjectSitePartitionMapper.deleteWsProjectSitePartitionById(id)+"";
        }
         return  str;
    }
}
