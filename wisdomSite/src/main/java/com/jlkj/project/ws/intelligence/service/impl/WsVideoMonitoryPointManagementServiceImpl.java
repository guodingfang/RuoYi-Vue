package com.jlkj.project.ws.intelligence.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.constant.IntelligentMonitoringConstants;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.framework.redis.ResultData;
import com.jlkj.framework.redis.YingshiVideoInterUtil;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsSitePartitionItem;
import com.jlkj.project.ws.domain.WsStartPTZControl;
import com.jlkj.project.ws.domain.WsVideoChannelManagement;
import com.jlkj.project.ws.intelligence.domain.WsVideoMonitoryPointManagement;
import com.jlkj.project.ws.intelligence.mapper.WsVideoMonitoryPointManagementMapper;
import com.jlkj.project.ws.intelligence.service.IWsVideoMonitoryPointManagementService;
import com.jlkj.project.ws.mapper.WsSitePartitionItemMapper;
import com.jlkj.project.ws.mapper.WsVideoChannelManagementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * 黑匣子信息ervice业务层处理
 *
 * @author liujie
 * @date 2020-07-03
 */
@Service
public class WsVideoMonitoryPointManagementServiceImpl implements IWsVideoMonitoryPointManagementService
{
    @Autowired
    private WsVideoMonitoryPointManagementMapper wsVideoMonitoryPointManagementMapper;

    @Autowired
    private WsVideoChannelManagementMapper  wsVideoChannelManagementMapper;

    @Autowired
    private WsSitePartitionItemMapper wsSitePartitionItemMapper;

    @Override
    public List<WsVideoMonitoryPointManagement> selectWsVideoMonitoryPointManagementList(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement) {
        return wsVideoMonitoryPointManagementMapper.selectWsVideoMonitoryPointManagementList(wsVideoMonitoryPointManagement);
    }

    @Override
    public List<WsVideoMonitoryPointManagement> selectWsVideoLiveList(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement) {

        List<WsVideoMonitoryPointManagement> wsVideoMonitoryPointManagements = wsVideoMonitoryPointManagementMapper.selectWsVideoMonitoryPointManagementList(wsVideoMonitoryPointManagement);
        ResultData resultData = YingshiVideoInterUtil.getCameraList();
        HashMap<String, String> coverPicMap = new HashMap<>();
        if("1".equals(resultData.getCode())){//1，表示获取列表成功过
            JSONArray arr = resultData.getData().getJSONArray("data");
            for (int i = 0; i < arr.size(); i++) {
                JSONObject json =arr.getJSONObject(i);
                coverPicMap.put(json.getString("deviceSerial")+"-"+json.getString("channelNo"),json.getString("picUrl"));
            }
            wsVideoMonitoryPointManagements.forEach(x->{
                WsStartPTZControl wsStartPTZControl = new WsStartPTZControl();
                wsStartPTZControl.setCoverPicUrl(coverPicMap.get(x.getVideoSerialNumber()+"-"+x.getVideoChannel()));
                x.setWsStartPTZControl(wsStartPTZControl);
            });
        }

        return wsVideoMonitoryPointManagements;
    }

    @Override
    public WsVideoMonitoryPointManagement selectWsVideoMonitoryPointManagementById(Integer id) {
        return wsVideoMonitoryPointManagementMapper.selectWsVideoMonitoryPointManagementById(id);
    }
    @Transactional
    @Override
    public int insertWsVideoMonitoryPointManagement(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement) {
        int rows =0;
        int compare =0;
        int count= wsVideoMonitoryPointManagementMapper.countWsVideoMonitoryPointManagement(wsVideoMonitoryPointManagement);
        if(count>0){ throw new CustomException("监控点已存在");}
        wsVideoMonitoryPointManagement.setEnabled(IntelligentMonitoringConstants.ENABLE);
        rows =wsVideoMonitoryPointManagementMapper.insertWsVideoMonitoryPointManagement(wsVideoMonitoryPointManagement);
        compare++;

        WsVideoChannelManagement wsVideoChannelManagement = getWsVideoChannelManagement(wsVideoMonitoryPointManagement);
        rows += wsVideoChannelManagementMapper.updateEnabledBySerialnumberAndChannel(wsVideoChannelManagement);
        compare++;

        WsSitePartitionItem wsSitePartitionItem = new WsSitePartitionItem(wsVideoMonitoryPointManagement.getZoneId(), wsVideoMonitoryPointManagement.getId(), "1");
        rows += wsSitePartitionItemMapper.insertWsSitePartitionItem(wsSitePartitionItem);
        compare++;

        if(rows!=compare){
            throw new CustomException("新增失败，请联系管理员");
        }
        return rows;
    }

    private WsVideoChannelManagement getWsVideoChannelManagement(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement) {
        WsVideoChannelManagement wsVideoChannelManagement = new WsVideoChannelManagement();
        wsVideoChannelManagement.setEquipmentSerialnumber(wsVideoMonitoryPointManagement.getVideoSerialNumber());
        wsVideoChannelManagement.setChannelNumber(wsVideoMonitoryPointManagement.getVideoChannel());
        wsVideoChannelManagement.setEnableStatus(Integer.parseInt(wsVideoMonitoryPointManagement.getEnabled()));
        return wsVideoChannelManagement;
    }

    @Transactional
    @Override
    public int updateWsVideoMonitoryPointManagement(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement) {
        int rows =0;
        int compare =0;
        WsVideoMonitoryPointManagement query= wsVideoMonitoryPointManagementMapper.selectWsVideoMonitoryPointManagementById(wsVideoMonitoryPointManagement.getId());
        if(StringUtils.isNull(query)){ throw new CustomException("监控点不存在");}

        if(query.getVideoSerialNumber().equals(wsVideoMonitoryPointManagement.getVideoSerialNumber()) && query.getVideoChannel().equals(wsVideoMonitoryPointManagement.getVideoChannel())){
            //未更换监控设备
            rows += wsVideoMonitoryPointManagementMapper.updateWsVideoMonitoryPointManagement(wsVideoMonitoryPointManagement);
            compare++;

            if (StringUtils.isNotEmpty(wsVideoMonitoryPointManagement.getEnabled())) {
                WsVideoChannelManagement wsVideoChannelManagement = getWsVideoChannelManagement(wsVideoMonitoryPointManagement);
                rows += wsVideoChannelManagementMapper.updateEnabledBySerialnumberAndChannel(wsVideoChannelManagement);
                compare++;
            }
        }else{//更换监控设备
            int count= wsVideoMonitoryPointManagementMapper.countWsVideoMonitoryPointManagement(wsVideoMonitoryPointManagement);
            if(count>0) throw new CustomException("视频监控已被使用");
            //更新视频监控点
            rows += wsVideoMonitoryPointManagementMapper.updateWsVideoMonitoryPointManagement(wsVideoMonitoryPointManagement);
            compare++;
            //修改原来监控设备为未启用状态
            query.setEnabled(IntelligentMonitoringConstants.NOT_ENABLED);
            WsVideoChannelManagement channel1 = getWsVideoChannelManagement(query);
            rows += wsVideoChannelManagementMapper.updateEnabledBySerialnumberAndChannel(channel1);
            compare++;

            //修改更新后监控设备为启用状态
            wsVideoMonitoryPointManagement.setEnabled(IntelligentMonitoringConstants.ENABLE);
            WsVideoChannelManagement channel2 = getWsVideoChannelManagement(wsVideoMonitoryPointManagement);
            rows += wsVideoChannelManagementMapper.updateEnabledBySerialnumberAndChannel(channel2);
            compare++;

        }

        //若更新分区需同步更新分区名目绑定
        if(!query.getZoneId().equals(wsVideoMonitoryPointManagement.getZoneId()) && StringUtils.isNotNull(wsVideoMonitoryPointManagement.getZoneId())){
            WsSitePartitionItem wsSitePartitionItem = new WsSitePartitionItem(wsVideoMonitoryPointManagement.getZoneId(), wsVideoMonitoryPointManagement.getId(), "1");
            rows += wsSitePartitionItemMapper.updateWsSitePartitionItemByItemAndType(wsSitePartitionItem);
            compare++;
        }

        if(rows!=compare){
            throw new CustomException("更新失败，请联系管理员");
        }
        return rows;
    }

    @Transactional
    @Override
    public int deleteWsVideoMonitoryPointManagementById(Integer id) {
        int rows =0;
        int compare =0;
        WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement = wsVideoMonitoryPointManagementMapper.selectWsVideoMonitoryPointManagementById(id);
        if (StringUtils.isNull(wsVideoMonitoryPointManagement)) {
            throw new CustomException("视频监控点不存在，请联系管理员");
        }
        rows += wsVideoMonitoryPointManagementMapper.deleteWsVideoMonitoryPointManagementById(id);
        compare++;

        WsVideoChannelManagement wsVideoChannelManagement = getWsVideoChannelManagement(wsVideoMonitoryPointManagement);
        wsVideoChannelManagement.setEnableStatus(Integer.parseInt(IntelligentMonitoringConstants.NOT_ENABLED));
        rows += wsVideoChannelManagementMapper.updateWsVideoChannelManagementBySerialnumberAndChannel(wsVideoChannelManagement);
        compare++;

        rows  += wsSitePartitionItemMapper.deleteWsSitePartitionItemByItemAndType(wsVideoMonitoryPointManagement.getId(), "1");
        compare++;
        if(rows!=compare){
            throw new CustomException("删除失败，请联系管理员");
        }
        return rows;
    }

    @Override
    public List<WsVideoMonitoryPointManagement> statisticsVideoListByZone(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement) {
        if(StringUtils.isNotNull(wsVideoMonitoryPointManagement)&& StringUtils.isEmpty(wsVideoMonitoryPointManagement.getEnabled())) wsVideoMonitoryPointManagement.setEnabled(IntelligentMonitoringConstants.ENABLE);
        return wsVideoMonitoryPointManagementMapper.statisticsVideoByWsProjectSitePartition(wsVideoMonitoryPointManagement);
    }

    @Override
    public List<WsVideoMonitoryPointManagement> statisticsProjectVideoPoints(WsVideoMonitoryPointManagement wsVideoMonitoryPointManagement) {
        List<WsProject> dataScope = SecurityUtils.getProjectList();
        return wsVideoMonitoryPointManagementMapper.statisticsProjectVideoPoints(wsVideoMonitoryPointManagement,dataScope);
    }
}
