package com.jlkj.project.ws.intelligence.service;

import java.util.List;

import com.jlkj.project.ws.domain.WsProjectEquipment;
import com.jlkj.project.ws.domain.WsVideoChannelManagement;
import com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout;
import com.jlkj.project.ws.intelligence.domain.WsTowercraneEquipment;

/**
 * 项目塔吊设备管理 Service接口
 * 
 * @author gaowei
 * @date 2020-07-07
 */
public interface IWsTowercraneEquipmentService 
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
     * 批量删除项目塔吊设备管理 
     * 
     * @param ids 需要删除的项目塔吊设备管理 ID
     * @return 结果
     */
    public int deleteWsTowercraneEquipmentByIds(Integer[] ids);

    /**
     * 删除项目塔吊设备管理 信息
     * 
     * @param id 项目塔吊设备管理 ID
     * @return 结果
     */
    public int deleteWsTowercraneEquipmentById(Integer id);

    public int departureWsTowercraneEquipment(Integer id);

    public int reentryWsTowercraneEquipment(WsTowercraneEquipment wsTowercraneEquipment);

    public int insertWsProjectFieldLayout(WsProjectFieldLayout wsProjectFieldLayout);

    public List<WsTowercraneEquipment> selectlistEquipment(WsTowercraneEquipment wsTowercraneEquipment);

    public int insertPosition(WsTowercraneEquipment wsTowercraneEquipment);

    public List<WsProjectEquipment> listMonitoringEquipment(WsProjectEquipment wsProjectEquipment);

    public List<WsProjectEquipment> listTowerCraneFace(WsProjectEquipment wsProjectEquipment);

    public List<WsVideoChannelManagement> listHookVideo(WsVideoChannelManagement wsVideoChannelManagement);

    public List<WsTowercraneEquipment> listHookVideoRepeat(WsTowercraneEquipment wsTowercraneEquipment);

    public  int editWsTowercraneEquipment(String id);

    public String selfNumbering(WsTowercraneEquipment wsTowercraneEquipment);
}
