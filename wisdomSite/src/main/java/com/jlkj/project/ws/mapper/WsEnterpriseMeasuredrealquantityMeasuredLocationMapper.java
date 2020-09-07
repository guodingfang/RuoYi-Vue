package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityMeasuredLocation;

/**
 * 实测实量实测部位 Mapper接口
 * 
 * @author pyy
 * @date 2020-06-20
 */
public interface WsEnterpriseMeasuredrealquantityMeasuredLocationMapper 
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
     * 删除实测实量实测部位 
     * 
     * @param id 实测实量实测部位 ID
     * @return 结果
     */
    public int deleteWsEnterpriseMeasuredrealquantityMeasuredLocationById(Integer id);

    /**
     * 批量删除实测实量实测部位 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsEnterpriseMeasuredrealquantityMeasuredLocationByIds(Integer[] ids);
}
