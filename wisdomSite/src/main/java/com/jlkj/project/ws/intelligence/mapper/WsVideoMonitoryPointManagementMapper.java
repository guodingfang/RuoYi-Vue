package com.jlkj.project.ws.intelligence.mapper;

import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.intelligence.domain.WsVideoMonitoryPointManagement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 视频监控点管理 Mapper接口
 *
 * @author liujie
 * @date 2020-07-07
 */
public interface WsVideoMonitoryPointManagementMapper
{


    /**
     * 根据id查询设备信息
     *
     * @param id 主键
     * @return 视频监控点管理
     */
    public WsVideoMonitoryPointManagement selectWsVideoMonitoryPointManagementById(Integer id);


    /**
     * 查询视频监控点管理 列表
     *
     * @param wsVideoMonitoryPointManagement 视频监控点管理
     * @return 视频监控点管理 集合
     */
    public List<WsVideoMonitoryPointManagement> selectWsVideoMonitoryPointManagementList(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement);


    /**
     * 新增视频监控点管理
     *
     * @param wsVideoMonitoryPointManagement 视频监控点管理
     * @return 结果
     */
    public int insertWsVideoMonitoryPointManagement(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement);

    /**
     * 修改视频监控点管理
     *
     * @param wsVideoMonitoryPointManagement 视频监控点管理
     * @return 结果
     */
    public int updateWsVideoMonitoryPointManagement(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement);

    /**
     * 删除视频监控点管理
     *
     * @param id 视频监控点管理 ID
     * @return 结果
     */
    public int deleteWsVideoMonitoryPointManagementById(Integer id);

    /**
        * @Author liujie
        * @Description 统计监控点数量
        * @Date 2020/7/15 14:41
        * @Param [wsVideoMonitoryPointManagement]
        * @Return int
       **/
    int countWsVideoMonitoryPointManagement(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement);

    /**
        * @Author liujie
        * @Description 获取区域下的设备统计列表
        * @Date 2020/7/18 16:52
        * @Param [wsProjectSitePartition]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.WsVideoMonitoryPointManagement>
       **/
    List<WsVideoMonitoryPointManagement> statisticsVideoByWsProjectSitePartition(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement);

    /**
        * @Author liujie
        * @Description 获取项目监控点统计列表
        * @Date 2020/8/17 16:01
        * @Param [dataScope]
        * @Return java.util.List<com.jlkj.project.ws.intelligence.domain.WsVideoMonitoryPointManagement>,WsVideoMonitoryPointManagement
       **/
    List<WsVideoMonitoryPointManagement> statisticsProjectVideoPoints(@Param("wsVideoMonitoryPointManagement")WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement,@Param("dataScope") List<WsProject>dataScope);
}
