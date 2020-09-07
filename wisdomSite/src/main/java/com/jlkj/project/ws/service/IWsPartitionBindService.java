package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsPartitionBind;

import java.util.List;

/**
 * 分区绑定Service接口
 *
 * @author pyy
 * @date 2020-06-30
 */
public interface IWsPartitionBindService {
    /**
     * 查询分区绑定
     *
     * @param id 分区绑定ID
     * @return 分区绑定
     */
    public WsPartitionBind selectWsPartitionBindById(Integer id);

    /**
     * 查询分区绑定列表
     *
     * @param partitionId 分区id
     * @return 分区绑定集合
     */
    public List<WsPartitionBind> selectWsPartitionBindList(Integer partitionId);

    /**
     * 新增分区绑定
     *
     * @param wsPartitionBind 分区绑定
     * @return 结果
     */
    public int insertWsPartitionBind(WsPartitionBind wsPartitionBind);

    /**
     * 修改分区绑定
     *
     * @param wsPartitionBind 分区绑定
     * @return 结果
     */
    public int updateWsPartitionBind(WsPartitionBind wsPartitionBind);

    /**
     * 批量删除分区绑定
     *
     * @param ids 需要删除的分区绑定ID
     * @return 结果
     */
    public int deleteWsPartitionBindByIds(Integer[] ids);

    /**
     * 删除分区绑定信息
     *
     * @param id 分区绑定ID
     * @return 结果
     */
    public int deleteWsPartitionBindById(Integer id);
}
