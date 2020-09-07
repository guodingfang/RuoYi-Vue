package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsQualityAcceptanceApply;
import org.apache.ibatis.annotations.Param;

/**
 * 质量验收申请 Mapper接口
 * 
 * @author gaowei
 * @date 2020-06-09
 */
public interface WsQualityAcceptanceApplyMapper 
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
    public List<WsQualityAcceptanceApply> selectWsQualityAcceptanceApplyList(@Param("wsQualityAcceptanceApply") WsQualityAcceptanceApply wsQualityAcceptanceApply, @Param("projectIds") Integer[] projectIds);

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
     * 删除质量验收申请 
     * 
     * @param id 质量验收申请 ID
     * @return 结果
     */
    public int deleteWsQualityAcceptanceApplyById(Integer id);

    /**
     * 批量删除质量验收申请 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsQualityAcceptanceApplyByIds(Integer[] ids);
}
