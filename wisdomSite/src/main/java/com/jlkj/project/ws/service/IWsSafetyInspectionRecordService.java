package com.jlkj.project.ws.service;

import java.util.List;
import com.jlkj.project.ws.domain.WsSafetyInspectionRecord;
import com.jlkj.project.ws.domain.vo.WsSafetyInspectionRecordVO;

/**
 * 安全巡视记录 Service接口
 * 
 * @author jlkj
 * @date 2020-07-13
 */
public interface IWsSafetyInspectionRecordService 
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
    public List<WsSafetyInspectionRecord> selectWsSafetyInspectionRecordList(WsSafetyInspectionRecord wsSafetyInspectionRecord);


    /**
     * 查询安全巡视和整改验证详情
     *
     * @param id 安全巡视记录 ID
     * @return 安全巡视记录VO
     */
    public WsSafetyInspectionRecordVO checkSafetyInspectionRectificationVerificationDetails(Integer id);



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
     * 批量删除安全巡视记录 
     * 
     * @param ids 需要删除的安全巡视记录 ID
     * @return 结果
     */
    public int deleteWsSafetyInspectionRecordByIds(Integer[] ids);

    /**
     * 删除安全巡视记录 信息
     * 
     * @param id 安全巡视记录 ID
     * @return 结果
     */
    public int deleteWsSafetyInspectionRecordById(Integer id);
}
