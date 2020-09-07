package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsQualityCheckManager;
import org.apache.ibatis.annotations.Param;

/**
 * 质量检查Mapper接口
 * 
 * @author gaowei
 * @date 2020-06-09
 */
public interface WsQualityCheckManagerMapper 
{
    /**
     * 查询质量检查
     * 
     * @param id 质量检查ID
     * @return 质量检查
     */
    public WsQualityCheckManager selectWsQualityCheckManagerById(int id);

    /**
     * 查询质量检查列表
     * 
     * @param wsQualityCheckManager 质量检查
     * @return 质量检查集合
     */
    public List<WsQualityCheckManager> selectWsQualityCheckManagerList(@Param("wsQualityCheckManager") WsQualityCheckManager wsQualityCheckManager,@Param("projectIds") Integer[] projectIds);

    /**
     * 新增质量检查
     * 
     * @param wsQualityCheckManager 质量检查
     * @return 结果
     */
    public int insertWsQualityCheckManager(WsQualityCheckManager wsQualityCheckManager);

    /**
     * 修改质量检查
     * 
     * @param wsQualityCheckManager 质量检查
     * @return 结果
     */
    public int updateWsQualityCheckManager(WsQualityCheckManager wsQualityCheckManager);

    /**
     * 删除质量检查
     * 
     * @param id 质量检查ID
     * @return 结果
     */
    public int deleteWsQualityCheckManagerById(Long id);

    /**
     * 批量删除质量检查
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsQualityCheckManagerByIds(Long[] ids);

    public List<WsQualityCheckManager> selectWsQualityProblemTypeByIdList(Long qualityProblemId);
}
