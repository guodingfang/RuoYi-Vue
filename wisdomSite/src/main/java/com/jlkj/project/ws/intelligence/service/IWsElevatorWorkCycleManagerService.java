package com.jlkj.project.ws.intelligence.service;

import com.jlkj.project.ws.excel.CommonExportExcel;
import com.jlkj.project.ws.intelligence.domain.WsElevatorWorkCycleManagerDTO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleManagerVO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleStatisticsVO;

import java.util.List;
import java.util.Map;

/**
 * 升降机工作循环信息管理Service接口
 *
 * @author liujie
 * @date 2020-07-03
 */
public interface IWsElevatorWorkCycleManagerService
{
    /**
     * @Author liujie
     * @Description 获取工作记录告警列表
     * @Date 2020/7/8 15:59
     * @Param [wsElevatorWorkCycleMangerDTO]
     * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.WsElevatorWorkCycleManagerVO>
     **/
    List<WsElevatorWorkCycleManagerVO> selectElevatorIllegalWorkRecord(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO);

    /**
     * @Author liujie
     * @Description 获取工作记录列表
     * @Date 2020/7/8 16:08
     * @Param [wsElevatorWorkCycleMangerDTO]
     * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.WsElevatorWorkCycleManagerVO>
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
        * @Description 组装工作记录统计数据
        * @Date 2020/7/8 17:27
        * @Param [wsElevatorManagerVOS, wsElevatorWorkCycleStatisticsVOS]
        * @Return java.util.List<WsElevatorWorkCycleStatisticsVO>
     **/
    List<WsElevatorWorkCycleStatisticsVO>  assembleWorkCycleStatistics(List<WsElevatorManagerVO> wsElevatorManagerVOS, List<WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVOS);

    /**
        * @Author liujie
        * @Description //获取监控设备列表
        * @Date 2020/7/9 18:23
        * @Param [wsElevatorWorkCycleMangerDTO]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO>
       **/
    List<WsElevatorManagerVO> selectWsElevatorMonitorDeviceList(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO);


    /**
     * @Author liujie
     * @Description //获取设备列表
     * @Date 2020/7/9 18:23
     * @Param [wsElevatorWorkCycleMangerDTO]
     * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO>
     **/
    List<WsElevatorManagerVO> selectWsElevatorManagerVOList(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO);

    /**
        * @Author liujie
        * @Description //组装工作循环数据
        * @Date 2020/7/9 18:44
        * @Param [wsElevatorManagerVOS, wsElevatorWorkCycleManagers]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleManagerVO>
       **/
    List<WsElevatorWorkCycleManagerVO> assembleWorkCycle(List<WsElevatorManagerVO> wsElevatorManagerVOS,List<WsElevatorWorkCycleManagerVO>wsElevatorWorkCycleManagers);

    /**
        * @Author liujie
        * @Description //组装告警统计
        * @Date 2020/7/10 17:56
        * @Param [wsElevatorWorkCycleMangerDTO, wsElevatorWorkCycleManagers]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleStatisticsVO>
       **/
    List<WsElevatorWorkCycleStatisticsVO> assembleAlarmingStatisticsByDate(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO, List<WsElevatorWorkCycleManagerVO> wsElevatorWorkCycleManagers);

    /**
        * @Author liujie
        * @Description 组装项目告警列表
        * @Date 2020/7/10 19:33
        * @Param [wsElevatorManagerVOS, wsElevatorWorkCycleStatisticsVOS]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleStatisticsVO>
       **/
    List<WsElevatorWorkCycleStatisticsVO> assembleAlarmingByProject(List<WsElevatorManagerVO> wsElevatorManagerVOS, List<WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVOS);
    /**
        * @Author liujie
        * @Description 组装整体整体项目的报警比例
        * @Date 2020/7/10 21:23
        * @Param [wsElevatorManagerVOS, wsElevatorWorkCycleStatisticsVOS]
        * @Return com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleStatisticsVO
       **/
    WsElevatorWorkCycleStatisticsVO  assembleAllProjectAlarming(List<WsElevatorManagerVO> wsElevatorManagerVOS, List<WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVOS);

    /**
     * @Author liujie
     * @Description 统计设备今日所有统计数据
     * @Date 2020/7/13 13:16
     * @Param [serialNumbers]
     * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleStatisticsVO>
     **/
    List<WsElevatorWorkCycleStatisticsVO> statisticsTodayAllInfo(String[] serialNumbers);

    /**
        * @Author liujie
        * @Description 组装今日设备统计信息
        * @Date 2020/7/13 15:31
        * @Param [wsElevatorWorkCycleStatisticsVOS, wsElevatorManagerVOS]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO>
       **/
    List<WsElevatorManagerVO> assembleTodayAllInfo(List<WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVOS, List<WsElevatorManagerVO> wsElevatorManagerVOS);

    /**
        * @Author liujie
        * @Description 统计近期升降机工作情况
        * @Date 2020/8/3 14:12
        * @Param [wsElevatorWorkCycleMangerDTO]
        * @Return com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleStatisticsVO
       **/
    WsElevatorWorkCycleStatisticsVO statisticsRecentRecord(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO);

    /**
        * @Author liujie
        * @Description 统计今日与昨日升降机工作情况
        * @Date 2020/8/3 14:34
        * @Param [wsElevatorWorkCycleMangerDTO]
        * @Return java.util.Map<java.lang.String,com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleStatisticsVO>
       **/
    Map<String, WsElevatorWorkCycleStatisticsVO> statisticsYesterdayAndTodayRecord(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO);

    /**
        * @Author liujie
        * @Description 按天统计近期监控设备工作录
        * @Date 2020/8/3 16:02
        * @Param [wsElevatorWorkCycleMangerDTO]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleStatisticsVO>
       **/
    List<WsElevatorWorkCycleStatisticsVO> statisticsRecordsForDays(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO);

    CommonExportExcel genIllegalExcel(List<WsElevatorWorkCycleManagerVO> records);
}
