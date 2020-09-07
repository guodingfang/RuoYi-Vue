package com.jlkj.project.ws.intelligence.service;

import com.jlkj.project.ws.excel.CommonExportExcel;
import com.jlkj.project.ws.intelligence.domain.WsBlackBox;
import com.jlkj.project.ws.intelligence.domain.WsElevatorDriverInfo;
import com.jlkj.project.ws.intelligence.domain.WsElevatorManager;
import com.jlkj.project.ws.intelligence.domain.WsElevatorManagerDTO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO;

import java.util.List;

/**
 * 施工电梯设备管理Service接口
 *
 * @author liujie
 * @date 2020-07-03
 */
public interface IWsElevatorManagerService
{
    /**
     * 查询施工电梯设备管理
     *
     * @param id 施工电梯设备管理ID
     * @return 施工电梯设备管理
     */
    public WsElevatorManager selectWsElevatorManagerById(Integer id);

    /**
     * 查询施工电梯设备管理列表
     *
     * @param wsElevatorManager 施工电梯设备管理
     * @return 施工电梯设备管理集合
     */
    public List<WsElevatorManagerVO> selectWsElevatorManagerList(WsElevatorManager wsElevatorManager);

    /**
     * 新增施工电梯设备管理
     *
     * @param wsElevatorMangerDTO 施工电梯设备管理DTO
     * @return 结果
     */
    public int insertWsElevatorManager(WsElevatorManagerDTO wsElevatorMangerDTO);

    /**
     * 修改施工电梯设备管理
     *
     * @param wsElevatorMangerDTO 施工电梯设备管理
     * @return 结果
     */
    public int updateWsElevatorManager(WsElevatorManagerDTO wsElevatorMangerDTO);

    /**
     * 批量删除施工电梯设备管理
     *
     * @param ids 需要删除的施工电梯设备管理ID
     * @return 结果
     */
    public int deleteWsElevatorManagerByIds(Integer[] ids);

    /**
     * 删除施工电梯设备管理信息
     *
     * @param id 施工电梯设备管理ID
     * @return 结果
     */
    public int deleteWsElevatorManagerById(Integer id);

    /**
        * @Author liujie
        * @Description //施工电梯退场
        * @Date 2020/7/4 16:49
        * @Param [wsElevatorManager]
        * @Return int
       **/
    int offSite(Integer id);


    /**
     * @Author liujie
     * @Description 获取检测设备序号列表
     * @Date 2020/7/8 17:21
     * @Param [wsElevatorManagerVOS]
     * @Return java.lang.String[]
     **/
    String[] getSerialNumbers(List<WsElevatorManagerVO> wsElevatorManagerVOS);

    /**
        * @Author liujie
        * @Description 统计设备
        * @Date 2020/7/10 9:44
        * @Param [wsElevatorManagerDTO]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO>
       **/
    List<WsElevatorManagerStatisticsVO> statisticsDeviceManagerList(WsElevatorManagerDTO wsElevatorManagerDTO);

    /**
        * @Author liujie
        * @Description 按项目统计设备
        * @Date 2020/7/10 22:08
        * @Param [wsElevatorManagerDTO]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO>
       **/
    List<WsElevatorManagerStatisticsVO>  statisticsDeviceManagerListByProject(WsElevatorManagerDTO wsElevatorManagerDTO);

    /**
        * @Author liujie
        * @Description 根据升降机Dto获取列表
        * @Date 2020/7/13 9:11
        * @Param [wsElevatorManagerDTO]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO>
       **/
    List<WsElevatorManagerVO>  selectWsElevatorManagerVOListByDTO(WsElevatorManagerDTO wsElevatorManagerDTO);



    /**
     * @Author liujie
     * @Description 查询升降机监控设备列表
     * @Date 2020/7/13 15:51
     * @Param [wsElevatorManagerDTO]
     * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO>
     **/
    List<WsElevatorManagerVO>  selectWsElevatorMonitorDeviceListByDTO(WsElevatorManagerDTO wsElevatorManagerDTO);

    /**
        * @Author liujie
        * @Description 组装升降机列表
        * @Date 2020/7/13 16:59
        * @Param [wsBlackBoxes, wsElevatorManagerVOS]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO>
       **/
    List<WsElevatorManagerVO> assembleWsElevatorManagerVOList(List<WsBlackBox> wsBlackBoxes, List<WsElevatorManagerVO> wsElevatorManagerVOS);

    /**
        * @Author liujie
        * @Description 获取最新司机信息
        * @Date 2020/7/13 20:49
        * @Param [serialNumber]
        * @Return WsElevatorDriverInfo
       **/
    WsElevatorDriverInfo findNewestRecognitionBySerialNumber(String serialNumber);

    /**
        * @Author liujie
        * @Description 获取设备历史打卡记录
        * @Date 2020/7/13 22:16
        * @Param [wsElevatorDriverInfo]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.WsElevatorDriverInfo>
       **/

    List<WsElevatorDriverInfo> selectRecognitionListByDriver(WsElevatorDriverInfo wsElevatorDriverInfo);

    /**
        * @Author liujie
        * @Description 获取区域设备统计列表
        * @Date 2020/7/14 11:59
        * @Param [wsElevatorManagerDTO]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO>
       **/
    List<WsElevatorManagerStatisticsVO> getAreaDeviceStatisticsList(WsElevatorManagerDTO wsElevatorManagerDTO);


/**
    * @Author liujie
    * @Description 获取设备整体在线与报警统计
    * @Date 2020/7/14 15:15
    * @Param [wsElevatorManagerVOS]
    * @Return com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO
   **/
    WsElevatorManagerStatisticsVO getDeviceOnlineAndAlarmingStatistics(List<WsElevatorManagerVO> wsElevatorManagerVOS);

    /**
        * @Author liujie
        * @Description 获取设备整体统计数据
        * @Date 2020/7/14 16:18
        * @Param [wsElevatorManagerDTO]
        * @Return com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO
       **/
    WsElevatorManagerStatisticsVO statisticsAllDeviceData(WsElevatorManagerDTO wsElevatorManagerDTO);

    /**
        * @Author liujie
        * @Description 获取项目设备统计列表
        * @Date 2020/7/27 18:19
        * @Param [wsBlackBoxes, wsElevatorManagerStatisticsVOs]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO>
       **/
    List<WsElevatorManagerStatisticsVO> assembleProjectDeviceStatisticsList(List<WsBlackBox> wsBlackBoxes, List<WsElevatorManagerStatisticsVO> wsElevatorManagerStatisticsVOs);


    /**
     * @Author liujie
     * @Description 获取监控设备序列号列表
     * @Date 2020/7/28 9:18
     * @Param [wsElevatorManagerDTO]
     * @Return java.util.Array<java.lang.String>
     **/
    String[] selectMonitorDeviceSerialNumberList (WsElevatorManagerDTO wsElevatorManagerDTO);

    /**
     * @Author liujie
     * @Description 获取项目设备统计列表
     * @Date 2020/7/28 9:21
     * @Param [wsElevatorManagerDTO]
     * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO>
     **/
    List<WsElevatorManagerStatisticsVO> statisticsProjectDeviceList(WsElevatorManagerDTO wsElevatorManagerDTO);

    /**
        * @Author liujie
        * @Description 组装设备在线状态列表
        * @Date 2020/7/29 11:43
        * @Param [wsBlackBoxes, wsElevatorManagerVOS]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO>
       **/
    List<WsElevatorManagerVO> assembleWsElevatorManagerOnlineStateList(List<WsBlackBox> wsBlackBoxes, List<WsElevatorManagerVO> wsElevatorManagerVOS);

    /**
        * @Author liujie
        * @Description 批量标记设备点位
        * @Date 2020/8/25 10:52
        * @Param [wsElevatorMangerDTO]
        * @Return int
       **/
    int bacthSignPoint(WsElevatorManagerDTO wsElevatorMangerDTO);

    /**
        * @Author liujie
        * @Description 删除设备标记点位
        * @Date 2020/8/26 23:45
        * @Param [id]
        * @Return int
       **/
    int unSignPoint(Integer id);

    /**
        * @Author liujie
        * @Description 获取设备详情导出
        * @Date 2020/8/28 16:08
        * @Param [result]
        * @Return com.jlkj.project.ws.excel.CommonExportExcel
       **/
    CommonExportExcel genDetailExcel(List<WsElevatorManagerVO> records);

    /**
        * @Author liujie
        * @Description 获取项目设备详情统计导出
        * @Date 2020/8/28 16:19
        * @Param [records]
        * @Return com.jlkj.project.ws.excel.CommonExportExcel
       **/
    CommonExportExcel genProjectStatisticsDetailExcel(List<WsElevatorManagerStatisticsVO> records);
}
