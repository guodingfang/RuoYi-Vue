package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantitySetupContent;

/**
 * 实测实量设置分项工程实测内容 Mapper接口
 * 
 * @author pyy
 * @date 2020-06-20
 */
public interface WsEnterpriseMeasuredrealquantitySetupContentMapper 
{
    /**
     * 查询实测实量设置分项工程实测内容 
     * 
     * @param id 实测实量设置分项工程实测内容 ID
     * @return 实测实量设置分项工程实测内容 
     */
    public List<WsEnterpriseMeasuredrealquantitySetupContent> selectWsEnterpriseMeasuredrealquantitySetupContentById(Integer measuredrealquantitySetupId);

    /**
     * 查询实测实量设置分项工程实测内容 列表
     * 
     * @param wsEnterpriseMeasuredrealquantitySetupContent 实测实量设置分项工程实测内容 
     * @return 实测实量设置分项工程实测内容 集合
     */
    public List<WsEnterpriseMeasuredrealquantitySetupContent> selectWsEnterpriseMeasuredrealquantitySetupContentList(WsEnterpriseMeasuredrealquantitySetupContent wsEnterpriseMeasuredrealquantitySetupContent);

    /**
     * 新增实测实量设置分项工程实测内容 
     * 
     * @param wsEnterpriseMeasuredrealquantitySetupContent 实测实量设置分项工程实测内容 
     * @return 结果
     */
    public int insertWsEnterpriseMeasuredrealquantitySetupContent(WsEnterpriseMeasuredrealquantitySetupContent wsEnterpriseMeasuredrealquantitySetupContent);

    /**
     * 修改实测实量设置分项工程实测内容 
     * 
     * @param wsEnterpriseMeasuredrealquantitySetupContent 实测实量设置分项工程实测内容 
     * @return 结果
     */
    public int updateWsEnterpriseMeasuredrealquantitySetupContent(WsEnterpriseMeasuredrealquantitySetupContent wsEnterpriseMeasuredrealquantitySetupContent);

    /**
     * 删除实测实量设置分项工程实测内容 
     * 
     * @param id 实测实量设置分项工程实测内容 ID
     * @return 结果
     */
    public int deleteWsEnterpriseMeasuredrealquantitySetupContentById(Integer id);

    /**
     * 批量删除实测实量设置分项工程实测内容 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsEnterpriseMeasuredrealquantitySetupContentByIds(List<Integer> ids);
}
