package com.jlkj.project.ws.intelligence.mapper;

import com.jlkj.project.ws.intelligence.domain.WsElevatorMonitorData;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorMonitorDataVO;

import java.util.List;

/**
 * 施工电梯实时数据管理Mapper接口
 *
 * @author liujie
 * @date 2020-07-03
 */
public interface WsElevatorMonitorDataMapper
{

    /**
     * 查询施工电梯实时数据管理列表
     *
     * @param wsElevatorMonitorData 施工电梯实时数据管理
     * @return 施工电梯实时数据管理集合
     */
    public List<WsElevatorMonitorData> selectWsElevatorMonitorDataList(WsElevatorMonitorData wsElevatorMonitorData);

    /**
        * @Author liujie
        * @Description 根据设备编号获取监控设备信息
        * @Date 2020/7/13 18:23
        * @Param [wsElevatorMonitorData]
        * @Return com.jlkj.project.ws.intelligence.domain.vo.WsElevatorMonitorDataVO
       **/
    WsElevatorMonitorDataVO selectWsElevatorMonitorDataBySerialNumber(WsElevatorMonitorData wsElevatorMonitorData);


}
