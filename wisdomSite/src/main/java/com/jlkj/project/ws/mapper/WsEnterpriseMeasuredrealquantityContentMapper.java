package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityContent;

/**
 * 实测实量实测内容 Mapper接口
 * 
 * @author pyy
 * @date 2020-06-20
 */
public interface WsEnterpriseMeasuredrealquantityContentMapper 
{
    /**
     * 查询实测实量实测内容 
     * 
     * @param id 实测实量实测内容 ID
     * @return 实测实量实测内容 
     */
    public List<WsEnterpriseMeasuredrealquantityContent> selectWsEnterpriseMeasuredrealquantityContentById(Integer id);

    /**
     * 查询实测实量实测内容 列表
     * 
     * @param wsEnterpriseMeasuredrealquantityContent 实测实量实测内容 
     * @return 实测实量实测内容 集合
     */
    public List<WsEnterpriseMeasuredrealquantityContent> selectWsEnterpriseMeasuredrealquantityContentList(WsEnterpriseMeasuredrealquantityContent wsEnterpriseMeasuredrealquantityContent);

    /**
     * 新增实测实量实测内容 
     * 
     * @param wsEnterpriseMeasuredrealquantityContent 实测实量实测内容 
     * @return 结果
     */
    public int insertWsEnterpriseMeasuredrealquantityContent(WsEnterpriseMeasuredrealquantityContent wsEnterpriseMeasuredrealquantityContent);

    /**
     * 修改实测实量实测内容 
     * 
     * @param wsEnterpriseMeasuredrealquantityContent 实测实量实测内容 
     * @return 结果
     */
    public int updateWsEnterpriseMeasuredrealquantityContent(WsEnterpriseMeasuredrealquantityContent wsEnterpriseMeasuredrealquantityContent);

    /**
     * 删除实测实量实测内容 
     * 
     * @param id 实测实量实测内容 ID
     * @return 结果
     */
    public int deleteWsEnterpriseMeasuredrealquantityContentById(Integer id);

    /**
     * 批量删除实测实量实测内容 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsEnterpriseMeasuredrealquantityContentByIds(Integer[] ids);
}
