package com.jlkj.project.ws.service;

import java.util.List;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityMeasuredLocation;

/**
 * 实测实量实测部位 Service接口
 * 
 * @author pyy
 * @date 2020-06-20
 */
public interface IWsEnterpriseMeasuredrealquantityMeasuredLocationService 
{
    /**
     * 查询实测实量实测部位 
     * 
     * @param id 实测实量实测部位 ID
     * @return 实测实量实测部位 
     */
    public WsEnterpriseMeasuredrealquantityMeasuredLocation selectWsEnterpriseMeasuredrealquantityMeasuredLocationById(Integer id);

    /**
     * 查询实测实量实测部位 列表
     * 
     * @param wsEnterpriseMeasuredrealquantityMeasuredLocation 实测实量实测部位 
     * @return 实测实量实测部位 集合
     */
    public List<WsEnterpriseMeasuredrealquantityMeasuredLocation> selectWsEnterpriseMeasuredrealquantityMeasuredLocationList(WsEnterpriseMeasuredrealquantityMeasuredLocation wsEnterpriseMeasuredrealquantityMeasuredLocation);

    /**
     * 新增实测实量实测部位 
     * 
     * @param wsEnterpriseMeasuredrealquantityMeasuredLocation 实测实量实测部位 
     * @return 结果
     */
    public int insertWsEnterpriseMeasuredrealquantityMeasuredLocation(WsEnterpriseMeasuredrealquantityMeasuredLocation wsEnterpriseMeasuredrealquantityMeasuredLocation);

    /**
     * 修改实测实量实测部位 
     * 
     * @param wsEnterpriseMeasuredrealquantityMeasuredLocation 实测实量实测部位 
     * @return 结果
     */
    public int updateWsEnterpriseMeasuredrealquantityMeasuredLocation(WsEnterpriseMeasuredrealquantityMeasuredLocation wsEnterpriseMeasuredrealquantityMeasuredLocation);

    /**
     * 批量删除实测实量实测部位 
     * 
     * @param ids 需要删除的实测实量实测部位 ID
     * @return 结果
     */
    public int deleteWsEnterpriseMeasuredrealquantityMeasuredLocationByIds(Integer[] ids);

    /**
     * 删除实测实量实测部位 信息
     * 
     * @param id 实测实量实测部位 ID
     * @return 结果
     */
    public int deleteWsEnterpriseMeasuredrealquantityMeasuredLocationById(Integer id);
}
