package com.jlkj.project.ws.service;

import java.util.List;
import com.jlkj.project.ws.domain.WsQualityCheckRepair;

/**
 * 质量检查整改 Service接口
 * 
 * @author gaowei
 * @date 2020-06-09
 */
public interface IWsQualityCheckRepairService 
{
    /**
     * 查询质量检查整改 
     * 
     * @param checkId 质量检查 ID
     * @return 质量检查整改 
     */
    public WsQualityCheckRepair selectWsQualityCheckRepairById(Long checkId);

    /**
     * 查询质量检查整改 列表
     * 
     * @param wsQualityCheckRepair 质量检查整改 
     * @return 质量检查整改 集合
     */
    public List<WsQualityCheckRepair> selectWsQualityCheckRepairList(WsQualityCheckRepair wsQualityCheckRepair);

    /**
     * 新增质量检查整改 
     * 
     * @param wsQualityCheckRepair 质量检查整改 
     * @return 结果
     */
    public int insertWsQualityCheckRepair(WsQualityCheckRepair wsQualityCheckRepair);

    /**
     * 修改质量检查整改 
     * 
     * @param wsQualityCheckRepair 质量检查整改 
     * @return 结果
     */
    public int updateWsQualityCheckRepair(WsQualityCheckRepair wsQualityCheckRepair);

    /**
     * 批量删除质量检查整改 
     * 
     * @param ids 需要删除的质量检查整改 ID
     * @return 结果
     */
    public int deleteWsQualityCheckRepairByIds(Long[] ids);

    /**
     * 删除质量检查整改 信息
     * 
     * @param id 质量检查整改 ID
     * @return 结果
     */
    public int deleteWsQualityCheckRepairById(Long id);

    public List<WsQualityCheckRepair> selectWsQualityCheckRepairByIdList(int checkId);
}
