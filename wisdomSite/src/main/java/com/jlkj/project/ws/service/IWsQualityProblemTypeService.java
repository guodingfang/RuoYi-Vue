package com.jlkj.project.ws.service;

import java.util.List;

import com.jlkj.project.ws.domain.WsQualityCheckManager;
import com.jlkj.project.ws.domain.WsQualityProblemType;

/**
 * 质量问题管理 Service接口
 * 
 * @author gaowei
 * @date 2020-06-09
 */
public interface IWsQualityProblemTypeService 
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
     * 批量删除质量问题管理 
     * 
     * @param ids 需要删除的质量问题管理 ID
     * @return 结果
     */
    public int deleteWsQualityProblemTypeByIds(Long[] ids);

    /**
     * 删除质量问题管理 信息
     * 
     * @param id 质量问题管理 ID
     * @return 结果
     */
    public int deleteWsQualityProblemTypeById(Long id);

    public String checkNameType(WsQualityProblemType wsQualityProblemType);

    public List<WsQualityCheckManager> selectWsQualityProblemTypeByIdList(Long id);
}
