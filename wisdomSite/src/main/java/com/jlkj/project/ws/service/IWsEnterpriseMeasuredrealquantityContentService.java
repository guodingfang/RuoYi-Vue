package com.jlkj.project.ws.service;

import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityContent;
import com.jlkj.project.ws.domain.vo.WsEnterpriseMeasuredrealquantityContentVO;
import com.jlkj.project.ws.domain.vo.WsEnterpriseMeasuredrealquantityVO;

import java.util.List;

/**
 * 实测实量实测内容 Service接口
 *
 * @author pyy
 * @date 2020-06-20
 */
public interface IWsEnterpriseMeasuredrealquantityContentService
{
    /**
     * 查询实测实量实测内容
     *
     * @param measuredrealquantityMeasuredLocationId 实测实量部位 ID
     * @return 实测实量实测内容
     */
    public WsEnterpriseMeasuredrealquantityContentVO selectWsEnterpriseMeasuredrealquantityContentById(Integer measuredrealquantityMeasuredLocationId);

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
     * @param wsEnterpriseMeasuredrealquantityVO
     * @return 结果
     */
    public int insertWsEnterpriseMeasuredrealquantityContent(WsEnterpriseMeasuredrealquantityVO wsEnterpriseMeasuredrealquantityVO);

    /**
     * 修改实测实量实测内容
     *
     * @param wsEnterpriseMeasuredrealquantityVO
     * @return 结果
     */
    public int updateWsEnterpriseMeasuredrealquantityContent(WsEnterpriseMeasuredrealquantityVO wsEnterpriseMeasuredrealquantityVO);

    /**
     * 批量删除实测实量实测内容
     *
     * @param ids 需要删除的实测实量实测内容 ID
     * @return 结果
     */
    public int deleteWsEnterpriseMeasuredrealquantityContentByIds(Integer[] ids);

    /**
     * 删除实测实量实测内容 信息
     *
     * @param id 实测实量实测内容 ID
     * @return 结果
     */
    public int deleteWsEnterpriseMeasuredrealquantityContentById(Integer id);
}
