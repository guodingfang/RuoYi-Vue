package com.jlkj.project.ws.intelligence.mapper;

import java.util.List;
import com.jlkj.project.ws.intelligence.domain.WsTowercraneEquipment;
import org.apache.ibatis.annotations.Param;

/**
 * 项目塔吊设备管理 Mapper接口
 * 
 * @author gaowei
 * @date 2020-07-07
 */
public interface WsTowercraneEquipmentMapper 
{
    /**
     * 查询项目塔吊设备管理 
     * 
     * @param id 项目塔吊设备管理 ID
     * @return 项目塔吊设备管理 
     */
    public WsTowercraneEquipment selectWsTowercraneEquipmentById(Integer id);

    /**
     * 查询项目塔吊设备管理 列表
     * 
     * @param wsTowercraneEquipment 项目塔吊设备管理 
     * @return 项目塔吊设备管理 集合
     */
    public List<WsTowercraneEquipment> selectWsTowercraneEquipmentList(WsTowercraneEquipment wsTowercraneEquipment);

    /**
     * 新增项目塔吊设备管理 
     * 
     * @param wsTowercraneEquipment 项目塔吊设备管理 
     * @return 结果
     */
    public int insertWsTowercraneEquipment(WsTowercraneEquipment wsTowercraneEquipment);

    /**
     * 修改项目塔吊设备管理 
     * 
     * @param wsTowercraneEquipment 项目塔吊设备管理 
     * @return 结果
     */
    public int updateWsTowercraneEquipment(WsTowercraneEquipment wsTowercraneEquipment);

    /**
     * 删除项目塔吊设备管理 
     * 
     * @param id 项目塔吊设备管理 ID
     * @return 结果
     */
    public int deleteWsTowercraneEquipmentById(Integer id);

    /**
     * 批量删除项目塔吊设备管理 
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsTowercraneEquipmentByIds(Integer[] ids);

    public List<WsTowercraneEquipment> selectlistEquipment(WsTowercraneEquipment wsTowercraneEquipment);

    public int updateWsTowercraneEquipmentList(@Param("wsTowercraneEquipmentList") List<WsTowercraneEquipment> wsTowercraneEquipmentList);

    public List<WsTowercraneEquipment> listHookVideoRepeat(WsTowercraneEquipment wsTowercraneEquipment);

    public int editWsTowercraneEquipment(WsTowercraneEquipment wsTowercraneEquipment);

    public WsTowercraneEquipment getSelfNumbering(WsTowercraneEquipment wsTowercraneEquipment);
}
