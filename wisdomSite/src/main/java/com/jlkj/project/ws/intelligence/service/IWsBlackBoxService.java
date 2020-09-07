package com.jlkj.project.ws.intelligence.service;

import com.jlkj.project.ws.intelligence.domain.WsBlackBox;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO;

import java.util.List;

/**
 * 黑匣子信息service接口
 *
 * @author liujie
 * @date 2020-07-03
 */
public interface IWsBlackBoxService
{
    /**
     * 查询黑匣子信息
     *
     * @param serialNumber 设备序列号
     * @param equipmentType 设备类型
     * @return 黑匣子信息
     */
    public WsBlackBox selectWsBlackBoxBySeriNumberAndType(String serialNumber,String equipmentType);

    /**
     * 查询黑匣子信息列表
     *
     * @param wsBlackBox 黑匣子信息
     * @return 黑匣子信息列表
     */
    public List<WsBlackBox> selectWsBlackBoxList(WsBlackBox wsBlackBox);

    /**
        * @Author liujie
        * @Description 统计黑匣子总数和在线数量
        * @Date 2020/7/13 10:13
        * @Param [serialNumbers]
        * @Return com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO
       **/
    WsElevatorManagerStatisticsVO statisticsOnline( String[] serialNumbers);

}
