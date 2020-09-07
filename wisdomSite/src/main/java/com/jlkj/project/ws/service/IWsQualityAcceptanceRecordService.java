package com.jlkj.project.ws.service;

import java.util.List;
import com.jlkj.project.ws.domain.WsQualityAcceptanceRecord;

/**
 * 质量验收记录 Service接口
 * 
 * @author gaowei
 * @date 2020-06-09
 */
public interface IWsQualityAcceptanceRecordService 
{
    /**
     * 查询质量验收记录 
     * 
     * @param id 质量验收记录 ID
     * @return 质量验收记录 
     */
    public WsQualityAcceptanceRecord selectWsQualityAcceptanceRecordById(Integer id);

    /**
     * 查询质量验收记录 列表
     * 
     * @param wsQualityAcceptanceRecord 质量验收记录 
     * @return 质量验收记录 集合
     */
    public List<WsQualityAcceptanceRecord> selectWsQualityAcceptanceRecordList(WsQualityAcceptanceRecord wsQualityAcceptanceRecord);

    /**
     * 新增质量验收记录 
     * 
     * @param wsQualityAcceptanceRecord 质量验收记录 
     * @return 结果
     */
    public int insertWsQualityAcceptanceRecord(WsQualityAcceptanceRecord wsQualityAcceptanceRecord);

    /**
     * 修改质量验收记录 
     * 
     * @param wsQualityAcceptanceRecord 质量验收记录 
     * @return 结果
     */
    public int updateWsQualityAcceptanceRecord(WsQualityAcceptanceRecord wsQualityAcceptanceRecord);

    /**
     * 批量删除质量验收记录 
     * 
     * @param ids 需要删除的质量验收记录 ID
     * @return 结果
     */
    public int deleteWsQualityAcceptanceRecordByIds(Long[] ids);

    /**
     * 删除质量验收记录 信息
     * 
     * @param id 质量验收记录 ID
     * @return 结果
     */
    public int deleteWsQualityAcceptanceRecordById(Integer id);

    public List<WsQualityAcceptanceRecord> listWsQualityAcceptanceRecordByApplyId(Integer applyId);
}
