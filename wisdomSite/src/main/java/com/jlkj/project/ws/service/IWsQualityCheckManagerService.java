package com.jlkj.project.ws.service;

import java.util.List;
import com.jlkj.project.ws.domain.WsQualityCheckManager;

/**
 * 质量检查Service接口
 * 
 * @author gaowei
 * @date 2020-06-09
 */
public interface IWsQualityCheckManagerService 
{
    /**
     * 查询质量检查
     * 
     * @param id 质量检查ID
     * @return 质量检查
     */
    public WsQualityCheckManager selectWsQualityCheckManagerById(int id);

    /**
     * 查询质量检查列表
     * 
     * @param wsQualityCheckManager 质量检查
     * @return 质量检查集合
     */
    public List<WsQualityCheckManager> selectWsQualityCheckManagerList(WsQualityCheckManager wsQualityCheckManager);

    /**
     * 新增质量检查
     * 
     * @param wsQualityCheckManager 质量检查
     * @return 结果
     */
    public int insertWsQualityCheckManager(WsQualityCheckManager wsQualityCheckManager);

    /**
     * 修改质量检查
     * 
     * @param wsQualityCheckManager 质量检查
     * @return 结果
     */
    public int updateWsQualityCheckManager(WsQualityCheckManager wsQualityCheckManager);

    /**
     * 批量删除质量检查
     * 
     * @param ids 需要删除的质量检查ID
     * @return 结果
     */
    public int deleteWsQualityCheckManagerByIds(Long[] ids);

    /**
     * 删除质量检查信息
     * 
     * @param id 质量检查ID
     * @return 结果
     */
    public int deleteWsQualityCheckManagerById(Long id);
}
