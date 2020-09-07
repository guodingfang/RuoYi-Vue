package com.jlkj.project.ws.service;

import com.jlkj.framework.redis.ResultData;
import com.jlkj.project.ws.domain.WsStartPTZControl;
import com.jlkj.project.ws.domain.WsVideoChannelManagement;
import com.jlkj.project.ws.domain.WsVideoEquipmentManagement;
import com.jlkj.project.ws.domain.vo.VideoEquipmentCount;
import com.jlkj.project.ws.domain.vo.WsVideoChannelBindingVO;

import java.util.List;

/**
 * 视频设备管理 Service接口
 *
 * @author jlkj
 * @date 2020-07-07
 */
public interface IWsVideoEquipmentManagementService
{
    /**
     * 查询视频设备管理
     *
     * @param equipmentSerialnumber 设备序列号
     * @return 视频设备管理
     */
    public WsVideoEquipmentManagement selectWsVideoEquipmentManagementBySerialNumber(String equipmentSerialnumber);

    /**
     * 根据id查询设备信息
     *
     * @param id 主键
     * @return 视频设备管理
     */
    public WsVideoEquipmentManagement selectWsVideoEquipmentManagementById(Integer id);

    /**
     * 查询视频设备管理 列表
     *
     * @param wsVideoEquipmentManagement 视频设备管理
     * @return 视频设备管理 集合
     */
    public List<WsVideoEquipmentManagement> selectWsVideoEquipmentManagementList(WsVideoEquipmentManagement wsVideoEquipmentManagement);

    /**
     * 查询录像机通道信息
     *
     * @param wsVideoEquipmentManagement 视频设备管理
     * @return 视频设备管理 集合
     */
    public WsVideoChannelBindingVO selectVideoEquipmentChannel(WsVideoEquipmentManagement wsVideoEquipmentManagement);


    /**
     * 查询视频设备在线状态
     *
     * @return 视频设备管理 集合
     */
    public WsVideoEquipmentManagement selectWsVideoEquipmentOnlineStatus(String equipmentSerialnumber);

    /**
     * 查询记录数
     *
     * @return
     */
    public VideoEquipmentCount selectRecordCount();


    /**
     * 查看在线视频
     *
     * @return wsVideoChannelManagement 视频设备表
     */
    public String  playVideoBySerialnumber(WsVideoChannelManagement wsVideoChannelManagement);

    /**
     * 新增视频设备管理
     *
     * @param wsVideoEquipmentManagement 视频设备管理
     * @return 结果
     */
    public ResultData insertWsVideoEquipmentManagement(WsVideoEquipmentManagement wsVideoEquipmentManagement);

    /**
     * 修改视频设备管理
     *
     * @param wsVideoEquipmentManagement 视频设备管理
     * @return 结果
     */
    public int updateWsVideoEquipmentManagement(WsVideoEquipmentManagement wsVideoEquipmentManagement);

    /**
     * 视频设备转场
     *
     * @param wsVideoEquipmentManagement 视频设备管理
     * @return 结果
     */
    public int updateProjectBySerialnumber(WsVideoEquipmentManagement wsVideoEquipmentManagement);

    /**
     * 摄像头设备通道绑定
     *
     * @param wsVideoChannelBindingVO
     * @return 结果
     */
    public int videoChannelBinding(WsVideoChannelBindingVO wsVideoChannelBindingVO);


    /**
     * 开启云台控制
     *
     * @param wsStartPTZControl 云台控制对象
     * @return 结果
     */
    public ResultData startPzt(WsStartPTZControl wsStartPTZControl);


    /**
     * 删除视频设备管理 信息
     *
     * @param equipmentSerialnumber 设备序列号
     * @return 结果
     */
    public int deleteWsVideoEquipmentManagementBySerialnumber(String equipmentSerialnumber);


    /**
     * 删除视频设备通道号
     *
     * @param id 主键
     * @return 结果
     */
    public int deleteWsVideoChannelById(int id);


    /**
        * @Author liujie
        * @Description  抓拍图片
        * @Date 2020/8/23 12:27
        * @Param [wsStartPTZControl]
        * @Return com.jlkj.project.ws.domain.WsStartPTZControl
       **/
    WsStartPTZControl capturePic(WsStartPTZControl wsStartPTZControl);
}
