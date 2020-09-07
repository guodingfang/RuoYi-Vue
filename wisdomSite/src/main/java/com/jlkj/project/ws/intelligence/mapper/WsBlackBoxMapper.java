package com.jlkj.project.ws.intelligence.mapper;

import com.jlkj.project.ws.intelligence.domain.WsBlackBox;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 施工电梯监控设备绑定管理Mapper接口
 *
 * @author liujie
 * @date 2020-07-03
 */
public interface WsBlackBoxMapper
{
    /**
     * @Author liujie
     * @Description 根据设备序列及类型查询黑匣子列表
     * @Date 2020/7/13 8:43
     * @Param [serialNumber, equipmentType]
     * @Return com.jlkj.project.ws.intelligence.domain.WsBlackBox
     **/
    WsBlackBox selectWsBlackBoxBySeriNumberAndType(@Param("serialNumber") String serialNumber,@Param("equipmentType") String equipmentType);

    /**
     * @Author liujie
     * @Description 获取黑匣子列表
     * @Date 2020/7/13 8:54
     * @Param [wsBlackBox]
     * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.WsBlackBox>
     **/
    List<WsBlackBox>  selectWsBlackBoxList(WsBlackBox wsBlackBox);

    /**
        * @Author liujie
        * @Description 统计黑匣子总数和在线数量
        * @Date 2020/7/13 10:12
        * @Param [serialNumbers]
        * @Return com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO
       **/
    WsElevatorManagerStatisticsVO statisticsOnline(@Param("serialNumbers") String[] serialNumbers);



}
