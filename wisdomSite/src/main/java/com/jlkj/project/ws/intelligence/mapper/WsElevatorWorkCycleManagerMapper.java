package com.jlkj.project.ws.intelligence.mapper;

import com.jlkj.project.ws.intelligence.domain.WsElevatorWorkCycleManagerDTO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleManagerVO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 升降机工作循环信息管理Mapper接口
 *
 * @author liujie
 * @date 2020-07-03
 */
public interface WsElevatorWorkCycleManagerMapper
{

    /**
        * @Author liujie
        * @Description 获取工作记录告警列表
        * @Date 2020/7/8 15:59
        * @Param [wsElevatorWorkCycleMangerDTO]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.WsElevatorWorkCycleManager>
       **/
    List<WsElevatorWorkCycleManagerVO> selectElevatorIllegalWorkRecord(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO);

    /**
        * @Author liujie
        * @Description 获取工作记录列表
        * @Date 2020/7/8 16:08
        * @Param [wsElevatorWorkCycleMangerDTO]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.WsElevatorWorkCycleManager>
       **/
    List<WsElevatorWorkCycleManagerVO> selectElevatorWorkRecord(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO);

    /**
        * @Author liujie
        * @Description 获取工作记录统计
        * @Date 2020/7/8 16:09
        * @Param [wsElevatorWorkCycleMangerDTO]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleStatisticsVO>
       **/
    List<WsElevatorWorkCycleStatisticsVO> statisticsElevatorWorkRecord(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO);

    /**
        * @Author liujie
        * @Description 统计报警工作记录
        * @Date 2020/7/8 16:11
        * @Param [wsElevatorWorkCycleMangerDTO]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleStatisticsVO>
       **/
    List<WsElevatorWorkCycleStatisticsVO> statisticsDriverIllegalRecord(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO);

    /**
        * @Author liujie
        * @Description 统计设备今日所有统计数据
        * @Date 2020/7/13 13:16
        * @Param [serialNumbers]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleStatisticsVO>
       **/
    List<WsElevatorWorkCycleStatisticsVO> statisticsTodayAllInfo(@Param("serialNumbers") String[] serialNumbers);
}
