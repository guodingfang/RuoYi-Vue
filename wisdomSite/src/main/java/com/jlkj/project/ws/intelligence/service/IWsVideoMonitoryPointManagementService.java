package com.jlkj.project.ws.intelligence.service;

import com.jlkj.project.ws.intelligence.domain.WsVideoMonitoryPointManagement;

import java.util.List;

/**
 * 视频监控点管理service接口
 *
 * @author liujie
 * @date 2020-07-03
 */
public interface IWsVideoMonitoryPointManagementService
{
    /**
        * @Author liujie
        * @Description 获取视频监控点列表
        * @Date 2020/7/15 13:44
        * @Param [wsVideoMonitoryPointManagement]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.WsVideoMonitoryPointManagement>
       **/
    List<WsVideoMonitoryPointManagement> selectWsVideoMonitoryPointManagementList(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement);

    /**
        * @Author liujie
        * @Description 获取视频监控点详细
        * @Date 2020/7/15 13:44
        * @Param
        * @Return WsVideoMonitoryPointManagement
       **/
    WsVideoMonitoryPointManagement selectWsVideoMonitoryPointManagementById(Integer id);

    /**
        * @Author liujie
        * @Description 新增视频监控点
        * @Date 2020/7/15 13:45
        * @Param [wsVideoMonitoryPointManagement]
        * @Return int
       **/
    int insertWsVideoMonitoryPointManagement(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement);

    /**
        * @Author liujie
        * @Description 修改视频监控点
        * @Date 2020/7/15 13:45
        * @Param [wsVideoMonitoryPointManagement]
        * @Return int
       **/
    int updateWsVideoMonitoryPointManagement(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement);

    /**
        * @Author liujie
        * @Description 删除视频监控点
        * @Date 2020/7/15 13:45
        * @Param id
        * @Return int
       **/
    int deleteWsVideoMonitoryPointManagementById(Integer id);

    /**
        * @Author liujie
        * @Description 获取视频直播列表
        * @Date 2020/7/15 17:53
        * @Param [wsVideoMonitoryPointManagement]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.WsVideoMonitoryPointManagement>
       **/
    List<WsVideoMonitoryPointManagement> selectWsVideoLiveList(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement);

    /**
        * @Author liujie
        * @Description 按区域统计设备列表
        * @Date 2020/7/18 16:50
        * @Param [wsProjectSitePartition]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.WsVideoMonitoryPointManagement>
       **/
    List<WsVideoMonitoryPointManagement> statisticsVideoListByZone(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement);

    /**
        * @Author liujie
        * @Description 获取项目视频监控点列表
        * @Date 2020/8/17 16:03
        * @Param []wsVideoMonitoryPointManagement
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.WsVideoMonitoryPointManagement>
       **/
    List<WsVideoMonitoryPointManagement> statisticsProjectVideoPoints(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement);

}
