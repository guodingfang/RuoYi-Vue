package com.jlkj.project.ws.mapper;

import java.util.List;
import com.jlkj.project.ws.domain.WsSafetyInspectionRecord;
import org.springframework.data.repository.query.Param;

/**
 * 安全巡视记录 Mapper接口
 * 
 * @author jlkj
 * @date 2020-07-13
 */
public interface WsSafetyInspectionRecordMapper 
{
    /**
     * 查询安全巡视记录 
     * 
     * @param id 安全巡视记录 ID
     * @return 安全巡视记录 
     */
    public WsSafetyInspectionRecord selectWsSafetyInspectionRecordById(Integer id);

    /**
     * 查询安全巡视记录 列表
     * 
     * @param wsSafetyInspectionRecord 安全巡视记录 
     * @return 安全巡视记录 集合
     */
    public List<WsSafetyInspectionRecord> selectWsSafetyInspectionRecordList(@Param("wsSafetyInspectionRecord") WsSafetyInspectionRecord wsSafetyInspectionRecord, @Param("projectIds") List<Integer> projectIds);

    /**
     * 新增安全巡视记录 
     * 
     * @param wsSafetyInspectionRecord 安全巡视记录 
     * @return 结果
     */
    public int insertWsSafetyInspectionRecord(WsSafetyInspectionRecord wsSafetyInspectionRecord);

    /**
     * 修改安全巡视记录 
     * 
     * @param wsSafetyInspectionRecord 安全巡视记录 
     * @return 结果
     */
    public int updateWsSafetyInspectionRecord(WsSafetyInspectionRecord wsSafetyInspectionRecord);

    /**
     * 删除安全巡视记录 
     * 
     * @param id 安全巡视记录 ID
     * @return 结果
     */
    public int deleteWsSafetyInspectionRecordById(Integer id);

    /**
     * 批量删除安全巡视记录 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsSafetyInspectionRecordByIds(Integer[] ids);
}
