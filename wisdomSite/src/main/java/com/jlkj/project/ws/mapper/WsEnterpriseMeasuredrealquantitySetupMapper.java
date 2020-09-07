package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantitySetup;

/**
 * 实测实量设置分项工程 Mapper接口
 * 
 * @author pyy
 * @date 2020-06-20
 */
public interface WsEnterpriseMeasuredrealquantitySetupMapper 
{
    /**
     * 查询实测实量设置分项工程 
     * 
     * @param id 实测实量设置分项工程 ID
     * @return 实测实量设置分项工程 
     */
    public WsEnterpriseMeasuredrealquantitySetup selectWsEnterpriseMeasuredrealquantitySetupById(Integer id);

    /**
     * 查询实测实量设置分项工程
     *
     * @param wsEnterpriseMeasuredrealquantitySetup 实测实量设置分项工程
     * @return 实测实量设置分项工程
     */
    public WsEnterpriseMeasuredrealquantitySetup selectWsEnterpriseMeasuredrealquantitySetupByName(WsEnterpriseMeasuredrealquantitySetup wsEnterpriseMeasuredrealquantitySetup );

    /**
     * 查询实测实量设置分项工程 列表
     * 
     * @param wsEnterpriseMeasuredrealquantitySetup 实测实量设置分项工程 
     * @return 实测实量设置分项工程 集合
     */
    public List<WsEnterpriseMeasuredrealquantitySetup> selectWsEnterpriseMeasuredrealquantitySetupList(WsEnterpriseMeasuredrealquantitySetup wsEnterpriseMeasuredrealquantitySetup);

    /**
     * 新增实测实量设置分项工程 
     * 
     * @param wsEnterpriseMeasuredrealquantitySetup 实测实量设置分项工程 
     * @return 结果
     */
    public int insertWsEnterpriseMeasuredrealquantitySetup(WsEnterpriseMeasuredrealquantitySetup wsEnterpriseMeasuredrealquantitySetup);

    /**
     * 修改实测实量设置分项工程 
     * 
     * @param wsEnterpriseMeasuredrealquantitySetup 实测实量设置分项工程 
     * @return 结果
     */
    public int updateWsEnterpriseMeasuredrealquantitySetup(WsEnterpriseMeasuredrealquantitySetup wsEnterpriseMeasuredrealquantitySetup);

    /**
     * 删除实测实量设置分项工程 
     * 
     * @param id 实测实量设置分项工程 ID
     * @return 结果
     */
    public int deleteWsEnterpriseMeasuredrealquantitySetupById(Integer id);

    /**
     * 批量删除实测实量设置分项工程 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsEnterpriseMeasuredrealquantitySetupByIds(Integer[] ids);
}
