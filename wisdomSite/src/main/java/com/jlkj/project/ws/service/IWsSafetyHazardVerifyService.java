package com.jlkj.project.ws.service;

import java.util.List;
import com.jlkj.project.ws.domain.WsSafetyHazardVerify;

/**
 * 隐患验证 Service接口
 * 
 * @author jlkj
 * @date 2020-07-13
 */
public interface IWsSafetyHazardVerifyService 
{


    /**
     * 查询隐患验证 列表
     * 
     * @param wsSafetyHazardVerify 隐患验证 
     * @return 隐患验证 集合
     */
    public List<WsSafetyHazardVerify> selectWsSafetyHazardVerifyList(WsSafetyHazardVerify wsSafetyHazardVerify);

    /**
     * 新增隐患验证 
     * 
     * @param wsSafetyHazardVerify 隐患验证 
     * @return 结果
     */
    public int insertWsSafetyHazardVerify(WsSafetyHazardVerify wsSafetyHazardVerify);

    /**
     * 修改隐患验证 
     * 
     * @param wsSafetyHazardVerify 隐患验证 
     * @return 结果
     */
    public int updateWsSafetyHazardVerify(WsSafetyHazardVerify wsSafetyHazardVerify);

    /**
     * 批量删除隐患验证 
     * 
     * @param ids 需要删除的隐患验证 ID
     * @return 结果
     */
    public int deleteWsSafetyHazardVerifyByIds(Integer[] ids);

    /**
     * 删除隐患验证 信息
     * 
     * @param id 隐患验证 ID
     * @return 结果
     */
    public int deleteWsSafetyHazardVerifyById(Integer id);
}
