package com.jlkj.project.ws.intelligence.service.impl;

import com.jlkj.framework.aspectj.lang.annotation.DataSource;
import com.jlkj.framework.aspectj.lang.enums.DataSourceType;
import com.jlkj.framework.datasource.DynamicDataSourceContextHolder;
import com.jlkj.framework.redis.YingshiVideoInterUtil;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsProjectEquipment;
import com.jlkj.project.ws.domain.WsVideoChannelManagement;
import com.jlkj.project.ws.intelligence.dto.WsDustMonitoringDetailsDTO;
import com.jlkj.project.ws.intelligence.mapper.WsDustMonitoringIntelligentMapper;
import com.jlkj.project.ws.intelligence.service.IWsDustMonitoringIntelligentService;
import com.jlkj.project.ws.mapper.WsVideoChannelManagementMapper;
import com.jlkj.project.ws.service.IWsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 智能监测：扬尘智能监测 Service业务层处理
 *
 * @author gaowei
 * @date 2020-07-09
 */
@Service
public class WsDustMonitoringIntelligentServiceImpl implements IWsDustMonitoringIntelligentService
{
    @Autowired
    private WsDustMonitoringIntelligentMapper wsDustMonitoringIntelligentMapper;

    @Autowired
    private WsVideoChannelManagementMapper wsVideoChannelManagementMapper;

    @Autowired
    private IWsProjectService wsProjectService;

    /**
     * 查询扬尘监测列表-企业
     * @param wsDustMonitoringDetailsDTO
     * @return
     */
    @Override
    public List<WsDustMonitoringDetailsDTO> selectWsDustMonitoringDetailsList(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        List<WsDustMonitoringDetailsDTO> wsDustMonitoringDetailsDTOList = new ArrayList<WsDustMonitoringDetailsDTO>();
        //根据当前登录集团公司id查询项目集合；
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0){
            LinkedList<Integer> linkedlists = new LinkedList<Integer>();
            for(int i = 0; i < list.size(); i++){
                linkedlists.add(list.get(i).getId());
            }
            Integer[] projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);
            List<WsDustMonitoringDetailsDTO> projectEquipmentList = wsDustMonitoringIntelligentMapper.selectProjectEquipmentList(projectIds,wsDustMonitoringDetailsDTO.getProjectId());
            if(projectEquipmentList.size() > 0){
                for (int i = 0; i < projectEquipmentList.size(); i++) {
                    int todayAlarmNum = 0;
                    int totalNum = 0;
                    //按项目查询监测设备集合
                    List<WsDustMonitoringDetailsDTO> equipmentList = wsDustMonitoringIntelligentMapper.selectEquipmentList(projectEquipmentList.get(i).getProjectId());
                    if(equipmentList.size() > 0) {
                        LinkedList<String> linkedlist = new LinkedList<String>();
                        for (int k = 0; k < equipmentList.size(); k++) {
                            linkedlist.add(equipmentList.get(k).getMonitoringEquipmentNo());
                           //查询今日报警次数
                            WsDustMonitoringDetailsDTO todayAlarmNumDTO = selectTodayAlarmNumMonitoringEquipmentNo(equipmentList.get(k).getMonitoringEquipmentNo());
                            if(todayAlarmNumDTO != null){
                                if(todayAlarmNumDTO.getTotalNum() != null && todayAlarmNumDTO.getTotalNum() != ""){
                                    totalNum += Integer.parseInt(todayAlarmNumDTO.getTotalNum());
                                }
                                if(todayAlarmNumDTO.getTodayAlarmNum() != null && todayAlarmNumDTO.getTodayAlarmNum() != ""){
                                    todayAlarmNum += Integer.parseInt(todayAlarmNumDTO.getTodayAlarmNum());
                                }
                            }
                        }
                        String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                        //查询环境监测设备报警状态码
                        List<WsDustMonitoringDetailsDTO> alarmStatusCodelist = selectAlarmStatusCodelist(monitoringEquipmentNos);

                        if(alarmStatusCodelist.size() > 0){
                            String code = "0000000000000000";
                            StringBuilder statusCode = new StringBuilder(code);
                            for (int j = 0; j < alarmStatusCodelist.size(); j++) {
                                String alarmStatusCode = alarmStatusCodelist.get(j).getAlarmStatusCode();
                                for (int a = 0; a < alarmStatusCode.length(); a++) {
                                    String alarm = alarmStatusCode.substring(j,j+1);
                                    if(alarm != "0"){
                                        statusCode.replace(j,j+1,alarm);
                                    }
                                }
                            }
                            WsDustMonitoringDetailsDTO detailsDTO = new WsDustMonitoringDetailsDTO();
                            if(!statusCode.substring(14,16).equals("00")){
                                detailsDTO.setPm25("不正常");
                            }else{
                                detailsDTO.setPm25("正常");
                            }
                            if(!statusCode.substring(12,14).equals("00")){
                                detailsDTO.setPm10("不正常");
                            }else{
                                detailsDTO.setPm10("正常");
                            }
                            if(!statusCode.substring(10,12).equals("00")){
                                detailsDTO.setWindSpeed("不正常");
                            }else{
                                detailsDTO.setWindSpeed("正常");
                            }
                            if(!statusCode.substring(8,10).equals("00")){
                                detailsDTO.setNoise("不正常");
                            }else{
                                detailsDTO.setNoise("正常");
                            }
                            if(!statusCode.substring(6,8).equals("00")){
                                detailsDTO.setTemperatureType("不正常");
                            }else{
                                detailsDTO.setTemperatureType("正常");
                            }
                            if(!statusCode.substring(4,6).equals("00")){
                                detailsDTO.setHumidityType("不正常");
                            }else{
                                detailsDTO.setHumidityType("正常");
                            }
                            detailsDTO.setProjectName(projectEquipmentList.get(i).getProjectName());
                            detailsDTO.setProjectId(projectEquipmentList.get(i).getProjectId());
                            detailsDTO.setTodayAlarmNum(Integer.toString(todayAlarmNum));
                            wsDustMonitoringDetailsDTOList.add(detailsDTO);
                        }
                    }
                }
            }
        }
        return wsDustMonitoringDetailsDTOList;
    }

    public WsDustMonitoringDetailsDTO selectTodayAlarmNumMonitoringEquipmentNo(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsDustMonitoringDetailsDTO todayAlarmNumDTO = wsDustMonitoringIntelligentMapper.selectTodayAlarmNumMonitoringEquipmentNo(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return todayAlarmNumDTO;
    }

    public List<WsDustMonitoringDetailsDTO> selectAlarmStatusCodelist(String[] monitoringEquipmentNos) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsDustMonitoringDetailsDTO> alarmStatusCodelist = wsDustMonitoringIntelligentMapper.selectAlarmStatusCodelist(monitoringEquipmentNos);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return alarmStatusCodelist;
    }

    @Override
    public WsDustMonitoringDetailsDTO selectNothingMonitoring(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        //根据当前登录集团公司id查询项目集合；
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0) {
            LinkedList<Integer> linkedlist = new LinkedList<Integer>();
            for (int i = 0; i < list.size(); i++) {
                linkedlist.add(list.get(i).getId());
            }
            Integer[] projectIds = linkedlist.toArray(new Integer[linkedlist.size()]);
            List<WsDustMonitoringDetailsDTO> projectEquipmentList = wsDustMonitoringIntelligentMapper.selectNothingMonitoringList(projectIds);
            wsDustMonitoringDetailsDTO.setNoMonitoringProjectNum(linkedlist.size() - projectEquipmentList.size());
        }
        return wsDustMonitoringDetailsDTO;
    }

    @Override
    public List<WsDustMonitoringDetailsDTO> selectNothingMonitoringList(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        List<WsDustMonitoringDetailsDTO> projectEquipmentList = new ArrayList<WsDustMonitoringDetailsDTO>();
        List<WsDustMonitoringDetailsDTO> projectList = new ArrayList<WsDustMonitoringDetailsDTO>();
        //根据当前登录集团公司id查询项目集合；
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0) {
            LinkedList<Integer> linkedlist = new LinkedList<Integer>();
            for (int i = 0; i < list.size(); i++) {
                linkedlist.add(list.get(i).getId());
            }
            Integer[] projectIds = linkedlist.toArray(new Integer[linkedlist.size()]);
            projectEquipmentList = wsDustMonitoringIntelligentMapper.selectProjectNothingMonitoringList(projectIds);
        }
        return projectEquipmentList;
    }

    @Override
    public List<WsDustMonitoringDetailsDTO> selectProjectIdMonitoringEquipmentNo(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        List<WsDustMonitoringDetailsDTO> dustMonitoringDetailsList = new ArrayList<WsDustMonitoringDetailsDTO>();
        if(wsDustMonitoringDetailsDTO.getProjectId() == null){
            WsProject wsProject = new WsProject();
            List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
            if(list.size() > 0){
                for(int i = 0; i < list.size(); i++){
                    wsDustMonitoringDetailsDTO.setProjectId(list.get(i).getId());
                }
                dustMonitoringDetailsList = wsDustMonitoringIntelligentMapper.selectProjectIdMonitoringEquipmentNo(wsDustMonitoringDetailsDTO);
                if(dustMonitoringDetailsList.size() > 0){
                    for (int i = 0; i < dustMonitoringDetailsList.size(); i++) {
                        WsDustMonitoringDetailsDTO onlineOfflineNameDTO = selectOnlineOfflineName(dustMonitoringDetailsList.get(i).getMonitoringEquipmentNo());
                        dustMonitoringDetailsList.get(i).setOnlineOfflineName(onlineOfflineNameDTO.getOnlineOfflineName());
                    }
                }
            }
        }else{
            dustMonitoringDetailsList = wsDustMonitoringIntelligentMapper.selectProjectIdMonitoringEquipmentNo(wsDustMonitoringDetailsDTO);
            if(dustMonitoringDetailsList.size() > 0){
                for (int i = 0; i < dustMonitoringDetailsList.size(); i++) {
                    WsDustMonitoringDetailsDTO onlineOfflineNameDTO = selectOnlineOfflineName(dustMonitoringDetailsList.get(i).getMonitoringEquipmentNo());
                    dustMonitoringDetailsList.get(i).setOnlineOfflineName(onlineOfflineNameDTO.getOnlineOfflineName());
                }
            }
        }
        return dustMonitoringDetailsList;
    }

    public WsDustMonitoringDetailsDTO selectOnlineOfflineName(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsDustMonitoringDetailsDTO onlineOfflineNameDTO = wsDustMonitoringIntelligentMapper.selectOnlineOfflineName(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return onlineOfflineNameDTO;
    }


    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public WsDustMonitoringDetailsDTO selectMonitoringEquipmentNoDust(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        return wsDustMonitoringIntelligentMapper.selectMonitoringEquipmentNoDust(wsDustMonitoringDetailsDTO);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsDustMonitoringDetailsDTO> selectDustSevenDaySituation(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        return wsDustMonitoringIntelligentMapper.selectDustSevenDaySituation(wsDustMonitoringDetailsDTO);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsDustMonitoringDetailsDTO> selectNoiseSevenDaySituation(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        return wsDustMonitoringIntelligentMapper.selectNoiseSevenDaySituation(wsDustMonitoringDetailsDTO);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsDustMonitoringDetailsDTO> selectSameDayDustPm25OrDustPm10(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        return wsDustMonitoringIntelligentMapper.selectSameDayDustPm25OrDustPm10(wsDustMonitoringDetailsDTO);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsDustMonitoringDetailsDTO> selectMonthlyDayDustPm25OrDustPm10(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        return wsDustMonitoringIntelligentMapper.selectMonthlyDayDustPm25OrDustPm10(wsDustMonitoringDetailsDTO);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsDustMonitoringDetailsDTO> selectSameDayDustStrepitus(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        return wsDustMonitoringIntelligentMapper.selectSameDayDustStrepitus(wsDustMonitoringDetailsDTO);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsDustMonitoringDetailsDTO> selectMonthlyDayDustStrepitus(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        return wsDustMonitoringIntelligentMapper.selectMonthlyDayDustStrepitus(wsDustMonitoringDetailsDTO);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsDustMonitoringDetailsDTO> selectSameDayDustTemperature(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        return wsDustMonitoringIntelligentMapper.selectSameDayDustTemperature(wsDustMonitoringDetailsDTO);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsDustMonitoringDetailsDTO> selectMonthlyDayDustTemperature(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        return wsDustMonitoringIntelligentMapper.selectMonthlyDayDustTemperature(wsDustMonitoringDetailsDTO);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsDustMonitoringDetailsDTO> selectSameDayDustWindSpeed(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        return wsDustMonitoringIntelligentMapper.selectSameDayDustWindSpeed(wsDustMonitoringDetailsDTO);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsDustMonitoringDetailsDTO> selectMonthlyDayDustWindSpeed(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        return wsDustMonitoringIntelligentMapper.selectMonthlyDayDustWindSpeed(wsDustMonitoringDetailsDTO);
    }

    @Override
    public WsDustMonitoringDetailsDTO selectMonitoringVideoDetails(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        WsVideoChannelManagement videoChannelManagement = wsVideoChannelManagementMapper.selectWsVideoChannelManagementById(Integer.parseInt(wsDustMonitoringDetailsDTO.getVideoEquipmentId()));
        Map<String, String> resultMap = YingshiVideoInterUtil.getLiveAddress(videoChannelManagement.getEquipmentSerialnumber(),videoChannelManagement.getChannelNumber());
        wsDustMonitoringDetailsDTO.setVideoEquipmentAddress(resultMap.get("hlsHd"));
        wsDustMonitoringDetailsDTO.setEquipmentSerialnumber(videoChannelManagement.getEquipmentSerialnumber());
        wsDustMonitoringDetailsDTO.setChannelNumber(videoChannelManagement.getChannelNumber());
        return wsDustMonitoringDetailsDTO;
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public WsDustMonitoringDetailsDTO selectAverageDustMonitoringDetailsDay(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        return wsDustMonitoringIntelligentMapper.selectAverageDustMonitoringDetailsDay(wsDustMonitoringDetailsDTO);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public WsDustMonitoringDetailsDTO selectAverageDustMonitoringDetailsMonth(WsDustMonitoringDetailsDTO wsDustMonitoringDetailsDTO) {
        return wsDustMonitoringIntelligentMapper.selectAverageDustMonitoringDetailsMonth(wsDustMonitoringDetailsDTO);
    }
}
