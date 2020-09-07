package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsQualityCheckVerify;

/**
 * 质量检查验证 Mapper接口
 * 
 * @author gaowei
 * @date 2020-06-09
 */
public interface WsQualityCheckVerifyMapper 
{
    /**
     * 查询质量检查验证 
     * 
     * @param id 质量检查验证 ID
     * @return 质量检查验证 
     */
    public WsQualityCheckVerify selectWsQualityCheckVerifyById(Long id);

    /**
     * 查询质量检查验证 列表
     * 
     * @param wsQualityCheckVerify 质量检查验证 
     * @return 质量检查验证 集合
     */
    public List<WsQualityCheckVerify> selectWsQualityCheckVerifyList(WsQualityCheckVerify wsQualityCheckVerify);

    /**
     * 新增质量检查验证 
     * 
     * @param wsQualityCheckVerify 质量检查验证 
     * @return 结果
     */
    public int insertWsQualityCheckVerify(WsQualityCheckVerify wsQualityCheckVerify);

    /**
     * 修改质量检查验证 
     * 
     * @param wsQualityCheckVerify 质量检查验证 
     * @return 结果
     */
    public int updateWsQualityCheckVerify(WsQualityCheckVerify wsQualityCheckVerify);

    /**
     * 删除质量检查验证 
     * 
     * @param id 质量检查验证 ID
     * @return 结果
     */
    public int deleteWsQualityCheckVerifyById(Long id);

    /**
     * 批量删除质量检查验证 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsQualityCheckVerifyByIds(Long[] ids);
}
