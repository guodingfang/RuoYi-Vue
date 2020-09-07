package com.jlkj.project.ws.intelligence.mapper;

import com.jlkj.project.ws.intelligence.domain.WsElevatorMinitorDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 施工电梯监控设备绑定管理Mapper接口
 *
 * @author liujie
 * @date 2020-07-03
 */
public interface WsElevatorMinitorDeviceMapper
{
    /**
     * 查询施工电梯监控设备绑定管理
     *
     * @param id 施工电梯监控设备绑定管理ID
     * @return 施工电梯监控设备绑定管理
     */
    public WsElevatorMinitorDevice selectWsElevatorMinitorDeviceById(Integer id);

    /**
     * 查询施工电梯监控设备绑定管理列表
     *
     * @param wsElevatorMinitorDevice 施工电梯监控设备绑定管理
     * @return 施工电梯监控设备绑定管理集合
     */
    public List<WsElevatorMinitorDevice> selectWsElevatorMinitorDeviceList(WsElevatorMinitorDevice wsElevatorMinitorDevice);

    /**
     * 新增施工电梯监控设备绑定管理
     *
     * @param wsElevatorMinitorDevice 施工电梯监控设备绑定管理
     * @return 结果
     */
    public int insertWsElevatorMinitorDevice(WsElevatorMinitorDevice wsElevatorMinitorDevice);

    /**
     * 修改施工电梯监控设备绑定管理
     *
     * @param wsElevatorMinitorDevice 施工电梯监控设备绑定管理
     * @return 结果
     */
    public int updateWsElevatorMinitorDevice(WsElevatorMinitorDevice wsElevatorMinitorDevice);

    /**
     * 删除施工电梯监控设备绑定管理
     *
     * @param id 施工电梯监控设备绑定管理ID
     * @return 结果
     */
    public int deleteWsElevatorMinitorDeviceById(Integer id);


    /**
        * @Author liujie
        * @Description 获取项目下指定设备数
        * @Date 2020/7/4 10:59
        * @Param [projectId, serialNumber]
        * @Return int
       **/
    int countWsElevatorMinitorDeviceByProjectAndSN(WsElevatorMinitorDevice wsElevatorMinitorDevice);

    /**
        * @Author liujie
        * @Description 通过升降机笼获取绑定监控设备信息
        * @Date 2020/7/4 14:43
        * @Param [elevatorId, cageMark]
        * @Return com.jlkj.project.ws.intelligence.domain.WsElevatorMinitorDevice
       **/
    WsElevatorMinitorDevice getWsElevatorMinitorDeviceByElevatorCage(@Param("elevatorId")Integer elevatorId,@Param("cageMark")String cageMark);

    /**
        * @Author liujie
        * @Description 通过施工电梯删除绑定设备
        * @Date 2020/7/4 15:33
        * @Param [elevatorId]
        * @Return int
       **/
    int removeWsElevatorMinitorDeviceByElevator(Integer elevatorId);

    /**
        * @Author liujie
        * @Description 通过施工电梯id集合批量删除监控设备
        * @Date 2020/7/15 15:29
        * @Param [elevatorIds]
        * @Return int
       **/
    int deleteWsElevatorMinitorDeviceByElevatorIds(Integer[] elevatorIds);
}
