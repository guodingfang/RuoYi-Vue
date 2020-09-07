package com.jlkj.project.ws.intelligence.mapper;

import com.jlkj.project.ws.intelligence.domain.WsElevatorInOut;

/**
 * 施工电梯监控进出场记录Mapper接口
 *
 * @author liujie
 * @date 2020-07-03
 */
public interface WsElevatorInOutMapper
{
    /**
        * @Author liujie
        * @Description 插入升降机进场记录
        * @Date 2020/7/21 14:45
        * @Param [wsElevatorInOut]
        * @Return int
       **/
 int insertWsElevatorInOut(WsElevatorInOut wsElevatorInOut);

/**
    * @Author liujie
    * @Description 更新升降机进出场记录
    * @Date 2020/7/21 14:46
    * @Param [wsElevatorInOut]
    * @Return int
   **/
 int updateWsElevatorInOut(WsElevatorInOut wsElevatorInOut);

 /**
     * @Author liujie
     * @Description 添加升降机出场日期
     * @Date 2020/7/21 15:19
     * @Param [wsElevatorInOut]
     * @Return int
    **/
 int updateWsElevatorOutDate(WsElevatorInOut wsElevatorInOut);



}
