package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsQualityEarlywarningManager;

/**
 * 质量预警管理 Mapper接口
 * 
 * @author gaowei
 * @date 2020-06-09
 */
public interface WsQualityEarlywarningManagerMapper 
{
    /**
     * 查询质量预警管理 
     * 
     * @param id 质量预警管理 ID
     * @return 质量预警管理 
     */
    public WsQualityEarlywarningManager selectWsQualityEarlywarningManagerById(Long id);

    /**
     * 查询质量预警管理 列表
     * 
     * @param wsQualityEarlywarningManager 质量预警管理 
     * @return 质量预警管理 集合
     */
    public List<WsQualityEarlywarningManager> selectWsQualityEarlywarningManagerList(WsQualityEarlywarningManager wsQualityEarlywarningManager);

    /**
     * 新增质量预警管理 
     * 
     * @param wsQualityEarlywarningManager 质量预警管理 
     * @return 结果
     */
    public int insertWsQualityEarlywarningManager(WsQualityEarlywarningManager wsQualityEarlywarningManager);

    /**
     * 修改质量预警管理 
     * 
     * @param wsQualityEarlywarningManager 质量预警管理 
     * @return 结果
     */
    public int updateWsQualityEarlywarningManager(WsQualityEarlywarningManager wsQualityEarlywarningManager);

    /**
     * 删除质量预警管理 
     * 
     * @param id 质量预警管理 ID
     * @return 结果
     */
    public int deleteWsQualityEarlywarningManagerById(Long id);

    /**
     * 批量删除质量预警管理 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsQualityEarlywarningManagerByIds(Long[] ids);
}
