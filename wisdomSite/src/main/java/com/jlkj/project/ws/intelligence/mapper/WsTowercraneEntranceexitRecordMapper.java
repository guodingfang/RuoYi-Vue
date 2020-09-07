package com.jlkj.project.ws.intelligence.mapper;

import java.util.List;
import com.jlkj.project.ws.intelligence.domain.WsTowercraneEntranceexitRecord;

/**
 * 项目塔吊设备进出场记录 Mapper接口
 * 
 * @author gaowei
 * @date 2020-07-17
 */
public interface WsTowercraneEntranceexitRecordMapper 
{
    /**
     * 查询项目塔吊设备进出场记录 
     * 
     * @param id 项目塔吊设备进出场记录 ID
     * @return 项目塔吊设备进出场记录 
     */
    public WsTowercraneEntranceexitRecord selectWsTowercraneEntranceexitRecordById(Integer id);

    /**
     * 查询项目塔吊设备进出场记录 列表
     * 
     * @param wsTowercraneEntranceexitRecord 项目塔吊设备进出场记录 
     * @return 项目塔吊设备进出场记录 集合
     */
    public List<WsTowercraneEntranceexitRecord> selectWsTowercraneEntranceexitRecordList(WsTowercraneEntranceexitRecord wsTowercraneEntranceexitRecord);

    /**
     * 新增项目塔吊设备进出场记录 
     * 
     * @param wsTowercraneEntranceexitRecord 项目塔吊设备进出场记录 
     * @return 结果
     */
    public int insertWsTowercraneEntranceexitRecord(WsTowercraneEntranceexitRecord wsTowercraneEntranceexitRecord);

    /**
     * 修改项目塔吊设备进出场记录 
     * 
     * @param wsTowercraneEntranceexitRecord 项目塔吊设备进出场记录 
     * @return 结果
     */
    public int updateWsTowercraneEntranceexitRecord(WsTowercraneEntranceexitRecord wsTowercraneEntranceexitRecord);

    /**
     * 删除项目塔吊设备进出场记录 
     * 
     * @param id 项目塔吊设备进出场记录 ID
     * @return 结果
     */
    public int deleteWsTowercraneEntranceexitRecordById(Integer id);

    /**
     * 批量删除项目塔吊设备进出场记录 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsTowercraneEntranceexitRecordByIds(Integer[] ids);
}
