package com.jlkj.project.ws.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.constant.IntelligentMonitoringConstants;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.http.HttpRequester;
import com.jlkj.common.utils.http.HttpRespons;
import com.jlkj.common.utils.spring.SpringUtils;
import com.jlkj.framework.redis.RedisCache;
import com.jlkj.framework.redis.ResultData;
import com.jlkj.framework.redis.YingshiVideoInterUtil;
import com.jlkj.project.ws.domain.*;
import com.jlkj.project.ws.domain.vo.VideoEquipmentCount;
import com.jlkj.project.ws.domain.vo.WsVideoChannelBindingVO;
import com.jlkj.project.ws.mapper.WsProjectMapper;
import com.jlkj.project.ws.mapper.WsVideoChannelManagementMapper;
import com.jlkj.project.ws.mapper.WsVideoEquipmentManagementMapper;
import com.jlkj.project.ws.service.IWsVideoEquipmentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 视频设备管理 Service业务层处理
 *
 * @author jlkj
 * @date 2020-07-07
 */
@Service
public class WsVideoEquipmentManagementServiceImpl implements IWsVideoEquipmentManagementService {
    @Autowired
    private WsVideoEquipmentManagementMapper wsVideoEquipmentManagementMapper;

    @Autowired
    private WsVideoChannelManagementMapper wsVideoChannelManagementMapper;

    @Autowired
    private WsProjectMapper wsProjectMapper;

    //萤石云添加设备URL
    public static final String STATUS_URL = "https://open.ys7.com/api/lapp/device/info";
    //萤石云查看设备通道信息URL
    public static final String CHANNEL_URL = "https://open.ys7.com/api/lapp/device/camera/list";

    /**
     * 查询视频设备管理
     *
     * @param equipmentSerialnumber 设备序列号
     * @return 视频设备管理
     */
    @Override
    public WsVideoEquipmentManagement selectWsVideoEquipmentManagementBySerialNumber(String equipmentSerialnumber) {
        return wsVideoEquipmentManagementMapper.selectWsVideoEquipmentManagementBySerialNumber(equipmentSerialnumber);
    }

    /**
     * 查询视频设备管理
     *
     * @param id 主键
     * @return 视频设备管理
     */
    @Override
    public WsVideoEquipmentManagement selectWsVideoEquipmentManagementById(Integer id) {
        return wsVideoEquipmentManagementMapper.selectWsVideoEquipmentManagementById(id);
    }


    /**
     * 查询视频设备管理 列表
     *
     * @param wsVideoEquipmentManagement 视频设备管理
     * @return 视频设备管理
     */
    @Override
    public List<WsVideoEquipmentManagement> selectWsVideoEquipmentManagementList(WsVideoEquipmentManagement wsVideoEquipmentManagement) {
        List<WsVideoEquipmentManagement>  equipmentList = new ArrayList<>();
        List<WsProject> projects = SecurityUtils.getProjectList();
        if (projects != null && projects.size() > 0) {
            List<Integer> projectIds = new ArrayList<>();
            projects.forEach(project -> projectIds.add(project.getId()));
            wsVideoEquipmentManagement.setProjectIds(projectIds);
            equipmentList = wsVideoEquipmentManagementMapper.selectWsVideoEquipmentManagementList(wsVideoEquipmentManagement);
            for (WsVideoEquipmentManagement videoEquipmentManagement : equipmentList) {
                WsProject wsProject = wsProjectMapper.selectWsProjectById(videoEquipmentManagement.getProjectId());
                videoEquipmentManagement.setProjectName(wsProject.getProjectName());
                WsVideoEquipmentManagement videoEquipment = selectWsVideoEquipmentOnlineStatus(videoEquipmentManagement.getEquipmentSerialnumber());
                if (videoEquipment !=null && videoEquipment.getEquipmentSerialnumber().equals(videoEquipmentManagement.getEquipmentSerialnumber())) {
                    videoEquipmentManagement.setOnlineStatus(videoEquipment.getOnlineStatus());
                    videoEquipmentManagement.setEnableStatus(videoEquipment.getEnableStatus());

                }
                if (videoEquipmentManagement.getEquipmentType() == 1) {
                    List<WsVideoChannelManagement> wsVideoChannelList = wsVideoChannelManagementMapper.selectWsVideoChannelManagementBySerialNumber(videoEquipmentManagement.getEquipmentSerialnumber());
                    for (WsVideoChannelManagement wsVideoChannelManagement : wsVideoChannelList) {
                        if (wsVideoChannelManagement.getEquipmentSerialnumber().equals(videoEquipmentManagement.getEquipmentSerialnumber())) {
                            videoEquipmentManagement.setChannelNumber(wsVideoChannelManagement.getChannelNumber());
                            videoEquipmentManagement.setEnableStatus(wsVideoChannelManagement.getEnableStatus());
                        }
                    }
                }
            }
        }
        return equipmentList;
    }

    /**
     * 查询视频监控在线状态
     *
     * @param
     * @return 视频监控
     */
    @Override
    public WsVideoEquipmentManagement selectWsVideoEquipmentOnlineStatus(String equipmentSerialnumber) {
        try {
            JSONObject data = resultEquipmentData(STATUS_URL, equipmentSerialnumber);
            String code = data.getString("code");
            if ("200".equals(code)) {
                JSONObject jsonObject = data.getJSONObject("data");
                WsVideoEquipmentManagement wsVideoEquipmentManagement = new WsVideoEquipmentManagement();
                String status = jsonObject.getString("status");
                String serialNumber = jsonObject.getString("deviceSerial");
                wsVideoEquipmentManagement.setOnlineStatus(status);
                wsVideoEquipmentManagement.setEquipmentSerialnumber(serialNumber);
                return wsVideoEquipmentManagement;
            }
            return null;
        } catch (Exception e) {
            throw new CustomException("系统异常，请联系管理员");
        }

    }

    /**
     * 查看在线视频
     *
     * @param
     * @return 设备序列号
     */
    @Override
    public String playVideoBySerialnumber(WsVideoChannelManagement wsVideoChannelManagement) {
        Map<String, String> resultMap = YingshiVideoInterUtil.openVideoLive(wsVideoChannelManagement.getEquipmentSerialnumber(), wsVideoChannelManagement.getChannelNumber());
        if (resultMap.get("code").equals("1")) {
            Map<String, String> liveMap = YingshiVideoInterUtil.getLiveAddress(wsVideoChannelManagement.getEquipmentSerialnumber(), wsVideoChannelManagement.getChannelNumber());
            assert liveMap != null;
            if (liveMap.get("code").equals("1")) {
                return liveMap.get(wsVideoChannelManagement.getPlayMode());
            }
        }
        return null;
    }

    /**
     * 查询记录数
     *
     * @param
     * @return
     */
    @Override
    public VideoEquipmentCount selectRecordCount() {
        int equipmentCount = wsVideoEquipmentManagementMapper.selectEquipmentCount();
        int videoCount = wsVideoEquipmentManagementMapper.selectVideoCount();
        int NVRCount = wsVideoEquipmentManagementMapper.selectNVRCount();
        VideoEquipmentCount videoEquipmentCount = new VideoEquipmentCount();
        videoEquipmentCount.setEquipmentCount(equipmentCount);
        videoEquipmentCount.setVideoCount(videoCount);
        videoEquipmentCount.setNVRCount(NVRCount);
        return videoEquipmentCount;
    }

    /**
     * 查看录像机通道信息
     *
     * @param
     * @return 设备序列号
     */
    @Override
    public WsVideoChannelBindingVO selectVideoEquipmentChannel(WsVideoEquipmentManagement wsVideoEquipmentManagement) {
        WsVideoChannelManagement vcm = null;
        WsVideoChannelBindingVO vcbVO = new WsVideoChannelBindingVO();
        List<WsSetupInfo> setupInfoList = new ArrayList<>();
        JSONObject jsonObject = null;
        try {
           jsonObject = resultEquipmentData(CHANNEL_URL, wsVideoEquipmentManagement.getEquipmentSerialnumber());
            if (jsonObject.get("code").equals("200")) {
                JSONArray data = jsonObject.getJSONArray("data");
                for (int i = 0; i < data.size(); i++) {
                    vcm = new WsVideoChannelManagement();
                    JSONObject object = data.getJSONObject(i);
                    if (object.getBoolean("relatedIpc")) {
                        Integer channelNo = object.getInteger("channelNo");
                        String status = object.getInteger("status").toString();
                        vcm.setChannelNumber(channelNo);
                        vcm.setEquipmentSerialnumber(wsVideoEquipmentManagement.getEquipmentSerialnumber());
                        WsVideoChannelManagement videoChannelManagement = wsVideoChannelManagementMapper.selectWsVideoChannelManagementBySerialNumberAndChannelNumber(vcm);
                        if (videoChannelManagement != null) {
                            WsSetupInfo setupInfo = new WsSetupInfo();
                            setupInfo.setId(videoChannelManagement.getId());
                            setupInfo.setChannelNumber(videoChannelManagement.getChannelNumber());
                            setupInfo.setInstallationPosition(videoChannelManagement.getInstallationPosition());
                            setupInfo.setEnableStatus(videoChannelManagement.getEnableStatus());
                            setupInfo.setOnlineStatus(status);
                            setupInfoList.add(setupInfo);
                        } else {
                            WsSetupInfo setupInfo = new WsSetupInfo();
                            setupInfo.setChannelNumber(channelNo);
                            setupInfo.setInstallationPosition(object.getString("channelName"));
                            setupInfo.setOnlineStatus(status);
                            setupInfoList.add(setupInfo);
                        }
                    }
                }
                vcbVO.setProjectId(wsVideoEquipmentManagement.getProjectId());
                vcbVO.setEquipmentSerialnumber(wsVideoEquipmentManagement.getEquipmentSerialnumber());
                vcbVO.setVerificationCode(wsVideoEquipmentManagement.getVerificationCode());
                vcbVO.setWsSetupInfoList(setupInfoList);
                return vcbVO;
            }
            throw new CustomException("该设备已移除");
        } catch (IOException e) {
            throw  new CustomException("系统异常，请联系管理员");
        }
    }

    /**
     * 新增视频设备管理
     *
     * @param wsVideoEquipmentManagement 视频设备管理
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultData insertWsVideoEquipmentManagement(WsVideoEquipmentManagement wsVideoEquipmentManagement) {
        try {
            ResultData resultData = null;
            WsVideoEquipmentManagement wsVideoEquipmentManagement1 = wsVideoEquipmentManagementMapper.selectWsVideoEquipmentManagementBySerialNumber(wsVideoEquipmentManagement.getEquipmentSerialnumber());
            if (wsVideoEquipmentManagement1 != null) {
                resultData = new ResultData();
                resultData.setCode("500");
                resultData.setMessage("设备已添加，不能重复添加");
                return resultData;
            }
            resultData = YingshiVideoInterUtil.addVideo(wsVideoEquipmentManagement.getEquipmentSerialnumber(), wsVideoEquipmentManagement.getVerificationCode());
            if (resultData.getCode().equals("10002") || resultData.getCode().equals("10005")
                    || resultData.getCode().equals("49999") || resultData.getCode().length() < 2) {
                resultData.setCode("500");
                resultData.setMessage("系统异常，请联系管理员");
                return resultData;
            }
            if (resultData.getCode().equals("200") || resultData.getCode().equals("20017")) {
                int channelNum = selectChannelNum(CHANNEL_URL, wsVideoEquipmentManagement.getEquipmentSerialnumber());
                wsVideoEquipmentManagement.setChannels(channelNum);
                if (channelNum == 1) {
                    WsVideoChannelManagement vcm = new WsVideoChannelManagement();
                    vcm.setProjectId(wsVideoEquipmentManagement.getProjectId());
                    vcm.setInstallationPosition(wsVideoEquipmentManagement.getInstallationPosition());
                    vcm.setEquipmentSerialnumber(wsVideoEquipmentManagement.getEquipmentSerialnumber());
                    vcm.setVerificationCode(wsVideoEquipmentManagement.getVerificationCode());
                    wsVideoChannelManagementMapper.insertWsVideoChannelManagement(vcm);
                    wsVideoEquipmentManagement.setEquipmentType(1);
                    wsVideoEquipmentManagementMapper.insertWsVideoEquipmentManagement(wsVideoEquipmentManagement);
                } else {
                    wsVideoEquipmentManagement.setEquipmentType(2);
                    wsVideoEquipmentManagementMapper.insertWsVideoEquipmentManagement(wsVideoEquipmentManagement);
                }
                resultData.setCode("200");
                resultData.setMessage("添加成功");
                return resultData;
            }
            if (resultData.getCode().equals("20014")) {
                resultData.setCode("500");
                resultData.setMessage("序列号不合法");
                return resultData;
            }
            resultData.setCode("500");
            return resultData;
        } catch (Exception e) {
          e.printStackTrace();
           throw new CustomException("系统异常，请联系管理员");
        }
    }


    /**
     * 视频设备转场
     *
     * @param wsVideoEquipmentManagement 视频设备管理
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateProjectBySerialnumber(WsVideoEquipmentManagement wsVideoEquipmentManagement) {
        List<WsVideoChannelManagement> wsVideoChannelManagement = wsVideoChannelManagementMapper.selectWsVideoChannelManagementBySerialNumber(wsVideoEquipmentManagement.getEquipmentSerialnumber());
        int result = 0;
        if (wsVideoChannelManagement != null && wsVideoChannelManagement.size() == 1) {
            if (wsVideoChannelManagement.get(0).getEnableStatus() == 0) {
                WsVideoChannelManagement vcm = new WsVideoChannelManagement();
                vcm.setEquipmentSerialnumber(wsVideoEquipmentManagement.getEquipmentSerialnumber());
                vcm.setProjectId(wsVideoEquipmentManagement.getProjectId());
                vcm.setInstallationPosition(wsVideoEquipmentManagement.getInstallationPosition());
                result = wsVideoChannelManagementMapper.updateWsVideoChannelManagementByEquipmentSerialnumber(vcm);
                result = wsVideoEquipmentManagementMapper.updateWsVideoEquipmentManagement(wsVideoEquipmentManagement);
                return result;
            }
            return 0;
        }

        return 2;
    }

    /**
     * 修改视频设备管理
     *
     * @param wsVideoEquipmentManagement 视频设备管理
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateWsVideoEquipmentManagement(WsVideoEquipmentManagement wsVideoEquipmentManagement) {
        WsVideoEquipmentManagement wsVideoEquipmentManagement1 = wsVideoEquipmentManagementMapper.selectWsVideoEquipmentManagementBySerialNumber(wsVideoEquipmentManagement.getEquipmentSerialnumber());
        int result = 0;
        if (wsVideoEquipmentManagement1 != null) {
            result = wsVideoEquipmentManagementMapper.updateWsVideoEquipmentManagement(wsVideoEquipmentManagement);
            if (wsVideoEquipmentManagement1.getChannels() == 1) {
                WsVideoChannelManagement wsVideoChannelManagement = new WsVideoChannelManagement();
                wsVideoChannelManagement.setEquipmentSerialnumber(wsVideoEquipmentManagement1.getEquipmentSerialnumber());
                wsVideoChannelManagement.setInstallationPosition(wsVideoEquipmentManagement.getInstallationPosition());
                result = wsVideoChannelManagementMapper.updateWsVideoChannelManagementByEquipmentSerialnumber(wsVideoChannelManagement);
            }
            if (result > 0) {
                return result;
            }
        }
        return -1;
    }

    /**
     * 摄像头设备通道绑定
     *
     * @param wsVideoChannelBindingVO 摄像头通道绑定VO
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int videoChannelBinding(WsVideoChannelBindingVO wsVideoChannelBindingVO) {
        Integer projectId = wsVideoChannelBindingVO.getProjectId();
        String equipmentSerialnumber = wsVideoChannelBindingVO.getEquipmentSerialnumber();
        String verificationCode = wsVideoChannelBindingVO.getVerificationCode();
        List<WsSetupInfo> wsSetupInfoList = wsVideoChannelBindingVO.getWsSetupInfoList();
        List<Integer> channelList = new ArrayList<>();
        WsVideoChannelManagement vcm = null;
        if (wsSetupInfoList != null) {
            for (WsSetupInfo wsSetupInfo : wsSetupInfoList) {
                channelList.add(wsSetupInfo.getChannelNumber());
            }
            boolean flag = channelList.stream().distinct().count() == wsSetupInfoList.size();
            if (!flag) {
                return 0;
            }
            WsVideoEquipmentManagement wsVideoEquipmentManagement = wsVideoEquipmentManagementMapper.selectWsVideoEquipmentManagementBySerialNumber(equipmentSerialnumber);
            if (wsVideoEquipmentManagement.getChannels() < channelList.size()) {
                return 2;
            }

            for (WsSetupInfo wsSetupInfo : wsSetupInfoList) {
                if (wsSetupInfo.getId() != null) {
                    vcm = new WsVideoChannelManagement();
                    vcm.setId(wsSetupInfo.getId());
                    vcm.setInstallationPosition(wsSetupInfo.getInstallationPosition());
                    vcm.setChannelNumber(wsSetupInfo.getChannelNumber());
                    wsVideoChannelManagementMapper.updateWsVideoChannelManagementById(vcm);
                } else {
                    vcm = new WsVideoChannelManagement();
                    vcm.setProjectId(projectId);
                    vcm.setEquipmentSerialnumber(equipmentSerialnumber);
                    vcm.setVerificationCode(verificationCode);
                    vcm.setInstallationPosition(wsSetupInfo.getInstallationPosition());
                    vcm.setChannelNumber(wsSetupInfo.getChannelNumber());
                    wsVideoChannelManagementMapper.insertWsVideoChannelManagement(vcm);
                }
            }
            return 1;
        }
        return 0;
    }

    /**
     * 开启云台控制
     *
     * @param wsStartPTZControl 云台控制对象
     * @return 结果
     */
    @Override
    public ResultData startPzt(WsStartPTZControl wsStartPTZControl) {
        String serialNumber = wsStartPTZControl.getSerialNumber();
        String direction = wsStartPTZControl.getDirection();
        String speed = wsStartPTZControl.getSpeed();
        String channelNo = wsStartPTZControl.getChannelNumber();

        ResultData resultData = YingshiVideoInterUtil.startPtz(serialNumber, direction, speed,channelNo);
        if (resultData.getCode().equals("200")) {
            YingshiVideoInterUtil.stopPtz(serialNumber,direction,channelNo);
            return resultData;
        }
        resultData.setCode("500");
        return resultData;
    }

    @Override
    public WsStartPTZControl capturePic(WsStartPTZControl wsStartPTZControl) {
        ResultData resultData = YingshiVideoInterUtil.capturePic(wsStartPTZControl);
        if (resultData.getCode().equals("1")) {
            JSONObject data = resultData.getData();
            WsStartPTZControl capture = new WsStartPTZControl();
            capture.setCapturePicUrl(data.getJSONObject("data").getString("picUrl"));
            return capture;
        }else{
            throw new CustomException(resultData.getMessage());
        }
    }

    /**
     * 删除视频设备管理 信息
     *
     * @param equipmentSerialnumber 设备序列号
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteWsVideoEquipmentManagementBySerialnumber(String equipmentSerialnumber) {
        WsVideoEquipmentManagement vem = wsVideoEquipmentManagementMapper.selectWsVideoEquipmentManagementBySerialNumber(equipmentSerialnumber);
        List<WsVideoChannelManagement> vcm = wsVideoChannelManagementMapper.selectWsVideoChannelManagementBySerialNumber(equipmentSerialnumber);

        int result = 0;
        if (vem != null && vem.getChannels() > 1) {
            if (vcm == null) {
                return wsVideoEquipmentManagementMapper.deleteWsVideoEquipmentManagementById(vem.getId());
            }
            return 2;
        } else if (vem != null && vem.getChannels() == 1) {
            if (vcm.get(0).getEnableStatus() == 0) {
                result = wsVideoChannelManagementMapper.deleteWsVideoChannelManagementById(vcm.get(0).getId());
                result = wsVideoEquipmentManagementMapper.deleteWsVideoEquipmentManagementById(vem.getId());
                return result;
            }
            return 0;
        }

        return -1;
    }

    /**
     * 删除视频通道信息
     *
     * @param id 主键
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteWsVideoChannelById(int id) {
        WsVideoChannelManagement wsVideoChannelManagement = wsVideoChannelManagementMapper.selectWsVideoChannelManagementById(id);
        if (wsVideoChannelManagement != null && wsVideoChannelManagement.getEnableStatus() == 0) {
            return wsVideoChannelManagementMapper.deleteWsVideoChannelManagementById(id);
        }
        return 0;
    }


    public int selectChannelNum(String url, String equipmentSerialnumber) throws IOException {
        JSONObject data = resultEquipmentData(url, equipmentSerialnumber);
        JSONArray jsonArray = data.getJSONArray("data");
        String channelNo = jsonArray.getJSONObject(jsonArray.size() - 1).getString("channelNo");
        return Integer.parseInt(channelNo);

    }

    public JSONObject resultEquipmentData(String url, String equipmentSerialnumber) throws IOException {
        Map<String, String> queryMap = new HashMap<>();
        Map<String, String> resultMap = YingshiVideoInterUtil.getAccessToken();
        if (resultMap.get("code").equals("1")) {
            String accessToken = resultMap.get("accessToken");
            queryMap.put("accessToken", accessToken);
            queryMap.put("deviceSerial", equipmentSerialnumber);
            HttpRequester httpRequ = new HttpRequester();
            HttpRespons httpRespons = httpRequ.sendPost(url, queryMap);
            String jsonStr = httpRespons.getContent();
            JSONObject paramJson = JSON.parseObject(jsonStr);
            boolean code = paramJson.getString("code").equals("10002");
            if (code) {
                RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
                redisCache.deleteObject("YINGSHI:ACCESSTOKEN");
                resultEquipmentData(url, equipmentSerialnumber);
            }

            return paramJson;
        }
        queryMap.put("msg", "系统异常，请联系管理员");
        String json = JSON.toJSONString(queryMap);
        return JSONObject.parseObject(json);
    }


}
