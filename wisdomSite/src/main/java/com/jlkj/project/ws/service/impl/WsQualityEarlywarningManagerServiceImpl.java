package com.jlkj.project.ws.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsQualityEarlywarningManagerMapper;
import com.jlkj.project.ws.domain.WsQualityEarlywarningManager;
import com.jlkj.project.ws.service.IWsQualityEarlywarningManagerService;

/**
 * 质量预警管理 Service业务层处理
 *
 * @author gaowei
 * @date 2020-06-09
 */
@Service
public class WsQualityEarlywarningManagerServiceImpl implements IWsQualityEarlywarningManagerService
{
    @Autowired
    private WsQualityEarlywarningManagerMapper wsQualityEarlywarningManagerMapper;

    /**
     * 查询质量预警管理 
     *
     * @param id 质量预警管理 ID
     * @return 质量预警管理 
     */
    @Override
    public WsQualityEarlywarningManager selectWsQualityEarlywarningManagerById(Long id)
    {
        return wsQualityEarlywarningManagerMapper.selectWsQualityEarlywarningManagerById(id);
    }

    /**
     * 查询质量预警管理 列表
     *
     * @param wsQualityEarlywarningManager 质量预警管理 
     * @return 质量预警管理 
     */
    @Override
    public List<WsQualityEarlywarningManager> selectWsQualityEarlywarningManagerList(WsQualityEarlywarningManager wsQualityEarlywarningManager)
    {
        return wsQualityEarlywarningManagerMapper.selectWsQualityEarlywarningManagerList(wsQualityEarlywarningManager);
    }

    /**
     * 新增质量预警管理 
     *
     * @param wsQualityEarlywarningManager 质量预警管理 
     * @return 结果
     */
    @Override
    public int insertWsQualityEarlywarningManager(WsQualityEarlywarningManager wsQualityEarlywarningManager)
    {
        return wsQualityEarlywarningManagerMapper.insertWsQualityEarlywarningManager(wsQualityEarlywarningManager);
    }

    /**
     * 修改质量预警管理 
     *
     * @param wsQualityEarlywarningManager 质量预警管理 
     * @return 结果
     */
    @Override
    public int updateWsQualityEarlywarningManager(WsQualityEarlywarningManager wsQualityEarlywarningManager)
    {
        return wsQualityEarlywarningManagerMapper.updateWsQualityEarlywarningManager(wsQualityEarlywarningManager);
    }

    /**
     * 批量删除质量预警管理 
     *
     * @param ids 需要删除的质量预警管理 ID
     * @return 结果
     */
    @Override
    public int deleteWsQualityEarlywarningManagerByIds(Long[] ids)
    {
        return wsQualityEarlywarningManagerMapper.deleteWsQualityEarlywarningManagerByIds(ids);
    }

    /**
     * 删除质量预警管理 信息
     *
     * @param id 质量预警管理 ID
     * @return 结果
     */
    @Override
    public int deleteWsQualityEarlywarningManagerById(Long id)
    {
        return wsQualityEarlywarningManagerMapper.deleteWsQualityEarlywarningManagerById(id);
    }
}
