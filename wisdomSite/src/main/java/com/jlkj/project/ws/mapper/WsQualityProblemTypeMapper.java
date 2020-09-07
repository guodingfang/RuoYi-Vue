package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsQualityProblemType;

/**
 * 质量问题管理 Mapper接口
 * 
 * @author gaowei
 * @date 2020-06-09
 */
public interface WsQualityProblemTypeMapper 
{
    /**
     * 查询质量问题管理 
     * 
     * @param id 质量问题管理 ID
     * @return 质量问题管理 
     */
    public WsQualityProblemType selectWsQualityProblemTypeById(Long id);

    /**
     * 查询质量问题管理 列表
     * 
     * @param wsQualityProblemType 质量问题管理 
     * @return 质量问题管理 集合
     */
    public List<WsQualityProblemType> selectWsQualityProblemTypeList(WsQualityProblemType wsQualityProblemType);

    /**
     * 新增质量问题管理 
     * 
     * @param wsQualityProblemType 质量问题管理 
     * @return 结果
     */
    public int insertWsQualityProblemType(WsQualityProblemType wsQualityProblemType);

    /**
     * 修改质量问题管理 
     * 
     * @param wsQualityProblemType 质量问题管理 
     * @return 结果
     */
    public int updateWsQualityProblemType(WsQualityProblemType wsQualityProblemType);

    /**
     * 删除质量问题管理 
     * 
     * @param id 质量问题管理 ID
     * @return 结果
     */
    public int deleteWsQualityProblemTypeById(Long id);

    /**
     * 批量删除质量问题管理 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsQualityProblemTypeByIds(Long[] ids);

    /**
     * 校验质量问题名称是否唯一
     *
     * @param problemTypeName 质量问题名称
     * @param  groupId    集团id
     * @return
     */
    public WsQualityProblemType checkNameType(String problemTypeName, long groupId);
}
