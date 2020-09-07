package com.jlkj.project.ws.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsPartitionBindMapper;
import com.jlkj.project.ws.domain.WsPartitionBind;
import com.jlkj.project.ws.service.IWsPartitionBindService;

/**
 * 分区绑定Service业务层处理
 *
 * @author pyy
 * @date 2020-06-30
 */
@Service
public class WsPartitionBindServiceImpl implements IWsPartitionBindService {
    @Autowired
    private WsPartitionBindMapper wsPartitionBindMapper;

    /**
     * 查询分区绑定
     *
     * @param id 分区绑定ID
     * @return 分区绑定
     */
    @Override
    public WsPartitionBind selectWsPartitionBindById(Integer id) {
        return wsPartitionBindMapper.selectWsPartitionBindById(id);
    }

    /**
     * 查询分区绑定列表
     *
     * @param partitionId 分区id
     * @return 分区绑定
     */
    @Override
    public List<WsPartitionBind> selectWsPartitionBindList(Integer partitionId) {
        return wsPartitionBindMapper.selectWsPartitionBindList(partitionId);
    }

    /**
     * 新增分区绑定
     *
     * @param wsPartitionBind 分区绑定
     * @return 结果
     */
    @Override
    public int insertWsPartitionBind(WsPartitionBind wsPartitionBind) {
        return wsPartitionBindMapper.insertWsPartitionBind(wsPartitionBind);
    }

    /**
     * 修改分区绑定
     *
     * @param wsPartitionBind 分区绑定
     * @return 结果
     */
    @Override
    public int updateWsPartitionBind(WsPartitionBind wsPartitionBind) {
        return wsPartitionBindMapper.updateWsPartitionBind(wsPartitionBind);
    }

    /**
     * 批量删除分区绑定
     *
     * @param ids 需要删除的分区绑定ID
     * @return 结果
     */
    @Override
    public int deleteWsPartitionBindByIds(Integer[] ids) {
        return wsPartitionBindMapper.deleteWsPartitionBindByIds(ids);
    }

    /**
     * 删除分区绑定信息
     *
     * @param id 分区绑定ID
     * @return 结果
     */
    @Override
    public int deleteWsPartitionBindById(Integer id) {
        return wsPartitionBindMapper.deleteWsPartitionBindById(id);
    }
}
