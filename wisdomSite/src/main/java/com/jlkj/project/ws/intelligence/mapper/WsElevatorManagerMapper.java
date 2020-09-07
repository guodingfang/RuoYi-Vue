package com.jlkj.project.ws.intelligence.mapper;

import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.intelligence.domain.WsElevatorDriverInfo;
import com.jlkj.project.ws.intelligence.domain.WsElevatorManager;
import com.jlkj.project.ws.intelligence.domain.WsElevatorManagerDTO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 施工电梯设备管理Mapper接口
 *
 * @author liujie
 * @date 2020-07-03
 */
public interface WsElevatorManagerMapper
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
     * @param wsElevatorManager 施工电梯设备管理,dataScope 数据范围
     * @return 施工电梯设备管理集合
     */
    public List<WsElevatorManagerVO> selectWsElevatorManagerList(@Param("wsElevatorManager") WsElevatorManager wsElevatorManager,@Param("dataScope")  List<WsProject> dataScope);

    /**
     * 新增施工电梯设备管理
     *
     * @param wsElevatorManager 施工电梯设备管理
     * @return 结果
     */
    public int insertWsElevatorManager(WsElevatorManager wsElevatorManager);

    /**
     * 修改施工电梯设备管理
     *
     * @param wsElevatorManager 施工电梯设备管理
     * @return 结果
     */
    public int updateWsElevatorManager(WsElevatorManager wsElevatorManager);

    /**
     * 删除施工电梯设备管理
     *
     * @param id 施工电梯设备管理ID
     * @return 结果
     */
    public int deleteWsElevatorManagerById(Integer id);

    /**
     * 批量删除施工电梯设备管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWsElevatorManagerByIds(Integer[] ids);

    /**
        * @Author liujie
        * @Description 施工电梯设备管理集合
        * @Date 2020/7/3 23:16
        * @Param [ElevatorManager],dataScope 数据范围
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.WsElevatorManagerVO>
       **/
    List<WsElevatorManagerVO> selectWsElevatorManagerVOList(@Param("wsElevatorManager") WsElevatorManager wsElevatorManager,@Param("dataScope")  List<WsProject>dataScope);

    /**
        * @Author liujie
        * @Description  获取施工电梯设备列表
        * @Date 2020/7/9 18:08
        * @Param [wsElevatorManagerDTO],dataScope 数据范围
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO>
       **/
    List<WsElevatorManagerVO> selectWsElevatorManagerVOListByDTO(@Param("wsElevatorManagerDTO") WsElevatorManagerDTO wsElevatorManagerDTO,@Param("dataScope")  List<WsProject> dataScope);

    /**
    * @Author liujie
    * @Description //获取进出场列表selectWsElevatorManagerVOListByDTO
    * @Date 2020/7/10 9:41
    * @Param [wsElevatorManagerDTO],dataScope数据范围
    * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO>
   **/
    List<WsElevatorManagerVO> selectInAndOutList(@Param("wsElevatorManagerDTO") WsElevatorManagerDTO wsElevatorManagerDTO,@Param("dataScope")  List<WsProject> dataScope);

    /**
        * @Author liujie
        * @Description 查询升降机监控设备列表
        * @Date 2020/7/13 15:51
        * @Param [wsElevatorManagerDTO],dataScope数据范围
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO>
       **/
    List<WsElevatorManagerVO>  selectWsElevatorMonitorDeviceListByDTO(@Param("wsElevatorManagerDTO") WsElevatorManagerDTO wsElevatorManagerDTO,@Param("dataScope")  List<WsProject> dataScope);

    /**
        * @Author liujie
        * @Description 获取人脸认证司机详情列表
        * @Date 2020/7/13 20:45
        * @Param [wsElevatorDriverInfo]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.WsElevatorDriverInfo>
       **/
    List<WsElevatorDriverInfo> selectDriverRecognitionListByDriver(WsElevatorDriverInfo wsElevatorDriverInfo);

    /**
        * @Author liujie
        * @Description 获取最新人员认证信息
        * @Date 2020/7/13 20:47
        * @Param [serialNumber]
        * @Return com.jlkj.project.ws.intelligence.domain.WsElevatorDriverInfo
       **/
    WsElevatorDriverInfo findNewestRecognitionBySerialNumber(@Param("serialNumber") String serialNumber);

/**
    * @Author liujie
    * @Description 获取所有设备统计数据
    * @Date 2020/7/14 16:58
    * @Param [wsElevatorManagerDTO]
    * @Return com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO
   **/
    WsElevatorManagerStatisticsVO statisticsAllDeviceData(@Param("wsElevatorManagerDTO") WsElevatorManagerDTO wsElevatorManagerDTO,@Param("dataScope")  List<WsProject> dataScope);

    /**
        * @Author liujie
        * @Description 获取区域下的升降机设备列表
        * @Date 2020/7/23 10:59
        * @Param [wsElevatorManagerDTO]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO>
       **/
    List<WsElevatorManagerVO> selectDeviceListByArea(@Param("wsElevatorManagerDTO") WsElevatorManagerDTO wsElevatorManagerDTO,@Param("dataScope")  List<WsProject> dataScope);

    /**
        * @Author liujie
        * @Description 获取监控设备序列号列表
        * @Date 2020/7/28 9:18
        * @Param [wsElevatorManagerDTO]
        * @Return java.util.List<java.lang.String>
       **/
    List<String> selectMonitorDeviceSerialNumberList (WsElevatorManagerDTO wsElevatorManagerDTO);

    /**
        * @Author liujie
        * @Description 获取项目设备统计列表
        * @Date 2020/7/28 9:21
        * @Param [wsElevatorManagerDTO]，dataScope数据范围
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO>
       **/
    List<WsElevatorManagerStatisticsVO> statisticsProjectDeviceList(@Param("wsElevatorManagerDTO") WsElevatorManagerDTO wsElevatorManagerDTO,@Param("dataScope")  List<WsProject> dataScope);

    /**
        * @Author liujie
        * @Description 批量更新
        * @Date 2020/8/25 11:00
        * @Param [elevators]
        * @Return int
       **/
    int batchUpdate(List<WsElevatorManager> elevators);


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
        * @Description 统计升降机数量
        * @Date 2020/8/28 11:46
        * @Param [wsElevatorManager]
        * @Return int
       **/
    int countWsElevatorManager(WsElevatorManager wsElevatorManager);
}
