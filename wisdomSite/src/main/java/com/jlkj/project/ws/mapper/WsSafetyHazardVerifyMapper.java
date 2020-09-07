package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsSafetyHazardVerify;

/**
 * 隐患验证 Mapper接口
 * 
 * @author jlkj
 * @date 2020-07-13
 */
public interface WsSafetyHazardVerifyMapper 
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
     * 删除隐患验证 
     * 
     * @param id 隐患验证 ID
     * @return 结果
     */
    public int deleteWsSafetyHazardVerifyById(Integer id);

    /**
     * 批量删除隐患验证 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsSafetyHazardVerifyByIds(Integer[] ids);
}
