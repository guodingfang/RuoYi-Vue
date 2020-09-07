package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantity;

/**
 * 实测实量 Mapper接口
 * 
 * @author pyy
 * @date 2020-06-20
 */
public interface WsEnterpriseMeasuredrealquantityMapper 
{
    /**
     * 查询实测实量
     *
     * @param id 实测实量 ID
     * @return 实测实量
     */
    public List<WsEnterpriseMeasuredrealquantity> selectWsEnterpriseMeasuredrealquantityById(Integer measuredrealquantityMeasuredLocationId);


    /**
     * 根据分项工程查询实测实量
     *
     * @param measuredrealquantitySetupId 分项工程 ID
     * @return 实测实量
     */
    public WsEnterpriseMeasuredrealquantity selectWsEnterpriseMeasuredrealquantityByMeasuredrealquantitySetupId(Integer measuredrealquantitySetupId);





    /**
     * 查询实测实量 列表
     * 
     * @param wsEnterpriseMeasuredrealquantity 实测实量 
     * @return 实测实量 集合
     */
    public List<WsEnterpriseMeasuredrealquantity> selectWsEnterpriseMeasuredrealquantityList(WsEnterpriseMeasuredrealquantity wsEnterpriseMeasuredrealquantity);

    /**
     * 新增实测实量 
     * 
     * @param wsEnterpriseMeasuredrealquantity 实测实量 
     * @return 结果
     */
    public int insertWsEnterpriseMeasuredrealquantity(WsEnterpriseMeasuredrealquantity wsEnterpriseMeasuredrealquantity);

    /**
     * 修改实测实量 
     * 
     * @param wsEnterpriseMeasuredrealquantity 实测实量 
     * @return 结果
     */
    public int updateWsEnterpriseMeasuredrealquantity(WsEnterpriseMeasuredrealquantity wsEnterpriseMeasuredrealquantity);

    /**
     * 假删除
     * 
     * @param id 实测实量 ID
     * @return 结果
     */
    public int updateWsEnterpriseMeasuredrealquantityById(Integer id);

    /**
     * 根据id删除实测实量
     *
     * @param id 实测实量id
     * @return 结果
     */
    public int deleteWsEnterpriseMeasuredrealquantityById(Integer id);
}
