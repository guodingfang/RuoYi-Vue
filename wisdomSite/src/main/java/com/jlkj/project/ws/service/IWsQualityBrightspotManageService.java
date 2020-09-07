package com.jlkj.project.ws.service;

import java.util.List;
import com.jlkj.project.ws.domain.WsQualityBrightspotManage;

/**
 * 亮点管理 Service接口
 * 
 * @author gaowei
 * @date 2020-06-11
 */
public interface IWsQualityBrightspotManageService 
{
    /**
     * 查询亮点管理 
     * 
     * @param id 亮点管理 ID
     * @return 亮点管理 
     */
    public WsQualityBrightspotManage selectWsQualityBrightspotManageById(Integer id);

    /**
     * 查询亮点管理 列表
     * 
     * @param wsQualityBrightspotManage 亮点管理 
     * @return 亮点管理 集合
     */
    public List<WsQualityBrightspotManage> selectWsQualityBrightspotManageList(WsQualityBrightspotManage wsQualityBrightspotManage);

    /**
     * 新增亮点管理 
     * 
     * @param wsQualityBrightspotManage 亮点管理 
     * @return 结果
     */
    public int insertWsQualityBrightspotManage(WsQualityBrightspotManage wsQualityBrightspotManage);

    /**
     * 修改亮点管理 
     * 
     * @param wsQualityBrightspotManage 亮点管理 
     * @return 结果
     */
    public int updateWsQualityBrightspotManage(WsQualityBrightspotManage wsQualityBrightspotManage);

    /**
     * 批量删除亮点管理 
     * 
     * @param ids 需要删除的亮点管理 ID
     * @return 结果
     */
    public int deleteWsQualityBrightspotManageByIds(Integer[] ids);

    /**
     * 删除亮点管理 信息
     * 
     * @param id 亮点管理 ID
     * @return 结果
     */
    public int deleteWsQualityBrightspotManageById(Integer id);
}
