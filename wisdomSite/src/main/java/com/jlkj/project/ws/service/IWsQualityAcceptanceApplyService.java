package com.jlkj.project.ws.service;

import java.util.List;
import com.jlkj.project.ws.domain.WsQualityAcceptanceApply;

/**
 * 质量验收申请 Service接口
 * 
 * @author gaowei
 * @date 2020-06-09
 */
public interface IWsQualityAcceptanceApplyService 
{
    /**
     * 查询质量验收申请 
     * 
     * @param id 质量验收申请 ID
     * @return 质量验收申请 
     */
    public WsQualityAcceptanceApply selectWsQualityAcceptanceApplyById(Integer id);

    /**
     * 查询质量验收申请 列表
     * 
     * @param wsQualityAcceptanceApply 质量验收申请 
     * @return 质量验收申请 集合
     */
    public List<WsQualityAcceptanceApply> selectWsQualityAcceptanceApplyList(WsQualityAcceptanceApply wsQualityAcceptanceApply);

    /**
     * 新增质量验收申请 
     * 
     * @param wsQualityAcceptanceApply 质量验收申请 
     * @return 结果
     */
    public int insertWsQualityAcceptanceApply(WsQualityAcceptanceApply wsQualityAcceptanceApply);

    /**
     * 修改质量验收申请 
     * 
     * @param wsQualityAcceptanceApply 质量验收申请 
     * @return 结果
     */
    public int updateWsQualityAcceptanceApply(WsQualityAcceptanceApply wsQualityAcceptanceApply);

    /**
     * 批量删除质量验收申请 
     * 
     * @param ids 需要删除的质量验收申请 ID
     * @return 结果
     */
    public int deleteWsQualityAcceptanceApplyByIds(Integer[] ids);

    /**
     * 删除质量验收申请 信息
     * 
     * @param id 质量验收申请 ID
     * @return 结果
     */
    public int deleteWsQualityAcceptanceApplyById(Integer id);
}
