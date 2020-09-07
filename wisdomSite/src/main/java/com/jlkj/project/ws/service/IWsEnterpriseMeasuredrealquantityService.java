package com.jlkj.project.ws.service;

import java.util.List;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantity;
import com.jlkj.project.ws.domain.vo.WsEnterpriseMeasuredrealquantityVO;

/**
 * 实测实量 Service接口
 * 
 * @author pyy
 * @date 2020-06-20
 */
public interface IWsEnterpriseMeasuredrealquantityService 
{

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
     * 根据id删除实测实量
     *
     * @param id 实测实量 ID
     * @return 结果
     */
    public int deleteWsEnterpriseMeasuredrealquantityById(Integer id);


    /**
     * 假删除
     * 
     * @param id 实测实量 ID
     * @return 结果
     */
    public int updateWsEnterpriseMeasuredrealquantityById(Integer id);
}
