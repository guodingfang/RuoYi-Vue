package com.jlkj.project.ws.service;

import java.util.List;
import com.jlkj.project.ws.domain.WsSafetyHazardRectify;

/**
 * 隐患整改 Service接口
 * 
 * @author jlkj
 * @date 2020-07-13
 */
public interface IWsSafetyHazardRectifyService 
{
    /**
     * 查询隐患整改 
     * 
     * @param id 隐患整改 ID
     * @return 隐患整改 
     */
    public WsSafetyHazardRectify selectWsSafetyHazardRectifyById(Integer id);

    /**
     * 查询隐患整改 列表
     * 
     * @param wsSafetyHazardRectify 隐患整改 
     * @return 隐患整改 集合
     */
    public List<WsSafetyHazardRectify> selectWsSafetyHazardRectifyList(WsSafetyHazardRectify wsSafetyHazardRectify);

    /**
     * 新增隐患整改 
     * 
     * @param wsSafetyHazardRectify 隐患整改 
     * @return 结果
     */
    public int insertWsSafetyHazardRectify(WsSafetyHazardRectify wsSafetyHazardRectify);

    /**
     * 修改隐患整改 
     * 
     * @param wsSafetyHazardRectify 隐患整改 
     * @return 结果
     */
    public int updateWsSafetyHazardRectify(WsSafetyHazardRectify wsSafetyHazardRectify);

    /**
     * 批量删除隐患整改 
     * 
     * @param ids 需要删除的隐患整改 ID
     * @return 结果
     */
    public int deleteWsSafetyHazardRectifyByIds(Integer[] ids);

    /**
     * 删除隐患整改 信息
     * 
     * @param id 隐患整改 ID
     * @return 结果
     */
    public int deleteWsSafetyHazardRectifyById(Integer id);
}
