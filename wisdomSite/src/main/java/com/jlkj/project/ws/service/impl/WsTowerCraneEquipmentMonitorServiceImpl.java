package com.jlkj.project.ws.service.impl;

import com.jlkj.common.utils.StringUtils;
import com.jlkj.framework.aspectj.lang.annotation.DataSource;
import com.jlkj.framework.aspectj.lang.enums.DataSourceType;
import com.jlkj.framework.datasource.DynamicDataSourceContextHolder;
import com.jlkj.framework.redis.YingshiVideoInterUtil;
import com.jlkj.framework.web.domain.TreeSelect;
import com.jlkj.project.system.domain.SysEnterprise;
import com.jlkj.project.system.service.ISysDeptService;
import com.jlkj.project.system.service.impl.SysUserServiceImpl;
import com.jlkj.project.ws.domain.WsArea;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsVideoChannelManagement;
import com.jlkj.project.ws.dto.*;
import com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout;
import com.jlkj.project.ws.intelligence.domain.WsTowercraneEquipment;
import com.jlkj.project.ws.intelligence.mapper.WsProjectFieldLayoutMapper;
import com.jlkj.project.ws.intelligence.mapper.WsTowercraneEquipmentMapper;
import com.jlkj.project.ws.mapper.*;
import com.jlkj.project.ws.service.IWsProjectService;
import com.jlkj.project.ws.service.IWsTowerCraneEquipmentMonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 塔机监测 Service业务层处理
 *
 * @author gaowei
 * @date 2020-06-29
 */
@Service
public class WsTowerCraneEquipmentMonitorServiceImpl implements IWsTowerCraneEquipmentMonitorService
{
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private WsTowerCraneEquipmentMonitorMapper wsTowerCraneEquipmentMonitorMapper;

    @Autowired
    private WsTowerCraneProjectRealTimeMonitoringMapper wsTowerCraneProjectRealTimeMonitoringMapper;

    @Autowired
    private WsVideoChannelManagementMapper wsVideoChannelManagementMapper;

    @Autowired
    private WsProjectMapper wsProjectMapper;

    @Autowired
    private WsProjectFieldLayoutMapper wsProjectFieldLayoutMapper;

    @Autowired
    private WsTowercraneEquipmentMapper wsTowercraneEquipmentMapper;

    @Autowired
    private WsAreaMapper wsAreaMapper;

    @Autowired
    private IWsProjectService wsProjectService;

    @Autowired
    private ISysDeptService sysDeptService;

    @Override
    public List<WsProject> selectRealTimeProjectSearch(WsProject wsProject) {
        List<WsProject> listProject = new ArrayList<WsProject>();
        Integer[] ids = null;//根据当前账号 查询项目id 集合
        WsProject wsProject2 = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject2);
        if(list.size() > 0) {
            LinkedList<Integer> linkedlists = new LinkedList<Integer>();
            for (int i = 0; i < list.size(); i++) {
                linkedlists.add(list.get(i).getId());
            }
            ids = linkedlists.toArray(new Integer[linkedlists.size()]);
            listProject = wsProjectMapper.selectRealTimeProjectSearch(ids,wsProject);
        }
        return listProject;
    }

    @Override
    public List<WsProjectFieldLayout> selectRealTimeProjectFieldLayout(WsProjectFieldLayout wsProjectFieldLayout) {
        wsProjectFieldLayout.setEquipmentType(1);
        return wsProjectFieldLayoutMapper.selectWsProjectFieldLayoutList(wsProjectFieldLayout);
    }

    @Override
    public List<WsTowercraneEquipment> selectRealTimeTowercraneEquipment(WsTowercraneEquipment wsTowercraneEquipment) {
        wsTowercraneEquipment.setPresence(1);
        return wsTowercraneEquipmentMapper.selectWsTowercraneEquipmentList(wsTowercraneEquipment);
    }


    @Override
    public WsTowerCraneMonitoringRealtimeDTO selectRealTimeMonitoring(WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO) {
        WsTowerCraneMonitoringRealtimeDTO towerCraneDTOTowerCraneNum = new WsTowerCraneMonitoringRealtimeDTO();
        WsTowerCraneMonitoringRealtimeDTO towerCraneDTOMonitoringEquipmentNum = new WsTowerCraneMonitoringRealtimeDTO();
        List<WsTowerCraneMonitoringRealtimeDTO> equipmentList = new ArrayList<WsTowerCraneMonitoringRealtimeDTO>();
        List<WsTowerCraneMonitoringRealtimeDTO> listRealtimeDTO = new ArrayList<WsTowerCraneMonitoringRealtimeDTO>();
        WsProject wsProject = new WsProject();
        List<WsProject> wsProjectJt = wsProjectService.selectWsProjectList(wsProject);
        if(towerCraneMonitoringRealtimeDTO.getWsArea().getZoom().equals("1")){
            wsProject.setAreaCode(null);
        }else if(towerCraneMonitoringRealtimeDTO.getWsArea().getZoom().equals("2")){
            wsProject.setAreaCode(towerCraneMonitoringRealtimeDTO.getAreaCode().substring(0,2));
        }else if(towerCraneMonitoringRealtimeDTO.getWsArea().getZoom().equals("3")){
            wsProject.setAreaCode(towerCraneMonitoringRealtimeDTO.getAreaCode().substring(0,4));
        }else{
            wsProject.setAreaCode(towerCraneMonitoringRealtimeDTO.getAreaCode());
        }

       /* List<Integer> ids = new ArrayList<>();
        wsProjectJt.forEach(project -> ids.add(project.getId()));
        wsProject.setProjectIds(ids);*/
        List<WsProject> wsProjects = wsProjectMapper.selectWsProjectList(wsProject);

        Integer[] projectIds = null;
        int illegalNum = 0;
        int projectIllegalNum = 0;
        if(wsProjects.size() > 0) {
            LinkedList<Integer> linkedlist = new LinkedList<Integer>();
            for (int i = 0; i < wsProjects.size(); i++) {
                if(wsProjectJt.size() > 0){
                    for (int a = 0; a < wsProjectJt.size(); a++) {
                        if(wsProjects.get(i).getId() == wsProjectJt.get(a).getId()){
                            linkedlist.add(wsProjects.get(i).getId());
                            listRealtimeDTO = wsTowerCraneEquipmentMonitorMapper.selectlistRealtimeDTO(wsProjects.get(i).getId());
                            if(listRealtimeDTO.size() > 0){
                                LinkedList<String> linkedlists = new LinkedList<String>();
                                for (int k = 0; k < listRealtimeDTO.size(); k++) {
                                    linkedlists.add(listRealtimeDTO.get(k).getMonitoringEquipmentNo());
                                }
                                String[] monitoringEquipmentNos = linkedlists.toArray(new String[linkedlists.size()]);
                                List<WsTowerCraneMonitoringRealtimeDTO> realtimeDTOprojectIllegalNum = selectProjectIllegalNum(monitoringEquipmentNos);
                                if(realtimeDTOprojectIllegalNum.size() > 0){
                                    projectIllegalNum += illegalNum + realtimeDTOprojectIllegalNum.size();
                                }
                            }
                        }
                    }
                }
            }
            towerCraneMonitoringRealtimeDTO.setProjectIllegalNum(projectIllegalNum);
            projectIds = linkedlist.toArray(new Integer[linkedlist.size()]);
            if(projectIds.length > 0){
                List<WsTowerCraneMonitoringRealtimeDTO> listRealtimeDTOProjectNum = wsTowerCraneEquipmentMonitorMapper.selectlistRealtimeDTOProjectNum(projectIds);
                towerCraneMonitoringRealtimeDTO.setProjectNum(listRealtimeDTOProjectNum.size());//根据账号获取项目数
                towerCraneDTOTowerCraneNum = wsTowerCraneEquipmentMonitorMapper.selectRealTimeMonitoringTowerCraneNum(projectIds);
                towerCraneDTOMonitoringEquipmentNum = wsTowerCraneEquipmentMonitorMapper.selectRealTimeMonitoringMonitoringEquipmentNum(projectIds);
                equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoList(projectIds);
            }
        }
        if(towerCraneDTOTowerCraneNum != null){
            if(towerCraneDTOTowerCraneNum.getTowerCraneNum() != null){
                towerCraneMonitoringRealtimeDTO.setTowerCraneNum(towerCraneDTOTowerCraneNum.getTowerCraneNum());
            }else{
                towerCraneMonitoringRealtimeDTO.setTowerCraneNum(0);
            }
        }else{
            towerCraneMonitoringRealtimeDTO.setTowerCraneNum(0);
        }

        if(towerCraneDTOMonitoringEquipmentNum != null){
            if(towerCraneDTOMonitoringEquipmentNum.getMonitoringEquipmentNum() != null){
                towerCraneMonitoringRealtimeDTO.setMonitoringEquipmentNum(towerCraneDTOMonitoringEquipmentNum.getMonitoringEquipmentNum());
            }else{
                towerCraneMonitoringRealtimeDTO.setMonitoringEquipmentNum(0);
            }
        }else{
            towerCraneMonitoringRealtimeDTO.setMonitoringEquipmentNum(0);
        }

        if(equipmentList.size() > 0){
            LinkedList<String> linkedlist = new LinkedList<String>();
            for (int i = 0; i < equipmentList.size(); i++) {
                linkedlist.add(equipmentList.get(i).getMonitoringEquipmentNo());
            }
            String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
            WsTowerCraneMonitoringRealtimeDTO realtimeDTOonlineNumORofflineNum = selectOnlineNum(monitoringEquipmentNos);
            if(realtimeDTOonlineNumORofflineNum != null){
                if(realtimeDTOonlineNumORofflineNum.getOnlineNum() != null){
                    towerCraneMonitoringRealtimeDTO.setOnlineNum(realtimeDTOonlineNumORofflineNum.getOnlineNum());
                }else{
                    towerCraneMonitoringRealtimeDTO.setOnlineNum(0);
                }
                if(realtimeDTOonlineNumORofflineNum.getOfflineNum() != null){
                    towerCraneMonitoringRealtimeDTO.setOfflineNum(realtimeDTOonlineNumORofflineNum.getOfflineNum());
                }else{
                    towerCraneMonitoringRealtimeDTO.setOfflineNum(0);
                }
            }else{
                towerCraneMonitoringRealtimeDTO.setOnlineNum(0);
                towerCraneMonitoringRealtimeDTO.setOfflineNum(0);
            }

            WsTowerCraneMonitoringRealtimeDTO realtimeDTOtodayIllegalNum = selectTodayIllegalNum(monitoringEquipmentNos);
            if(realtimeDTOtodayIllegalNum != null){
                if(realtimeDTOtodayIllegalNum.getTodayIllegalNum() != null){
                    towerCraneMonitoringRealtimeDTO.setTodayIllegalNum(realtimeDTOtodayIllegalNum.getTodayIllegalNum());
                }else{
                    towerCraneMonitoringRealtimeDTO.setTodayIllegalNum(0);
                }
            }


        }else{
            towerCraneMonitoringRealtimeDTO.setOnlineNum(0);
            towerCraneMonitoringRealtimeDTO.setOfflineNum(0);
            towerCraneMonitoringRealtimeDTO.setTodayIllegalNum(0);
            towerCraneMonitoringRealtimeDTO.setProjectIllegalNum(0);
        }
        return towerCraneMonitoringRealtimeDTO;
    }

    public  List<WsTowerCraneMonitoringRealtimeDTO> selectProjectIllegalNum(String[] monitoringEquipmentNos) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsTowerCraneMonitoringRealtimeDTO> realtimeDTO = wsTowerCraneEquipmentMonitorMapper.selectProjectIllegalNum(monitoringEquipmentNos);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return realtimeDTO;
    }

    public WsTowerCraneMonitoringRealtimeDTO selectTodayIllegalNum(String[] monitoringEquipmentNos) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringRealtimeDTO realtimeDTO = wsTowerCraneEquipmentMonitorMapper.selectTodayIllegalNum(monitoringEquipmentNos);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return realtimeDTO;
    }

    //@DataSource(value = DataSourceType.SLAVE)
    public WsTowerCraneMonitoringRealtimeDTO selectOnlineNum(String[] monitoringEquipmentNos) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringRealtimeDTO realtimeDTO = wsTowerCraneEquipmentMonitorMapper.selectOnlineNum(monitoringEquipmentNos);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return realtimeDTO;
    }

    @Override
    public List<WsTowerCraneMonitoringRealtimeDTO> selectEquipmentList(WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO) {
        List<WsTowerCraneMonitoringRealtimeDTO> realtimeDTOlist = new ArrayList<WsTowerCraneMonitoringRealtimeDTO>();
        //根据公司id去查询 该公司下所有项目；
        Integer[] projectIds = null;
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0) {
            LinkedList<Integer> linkedlists = new LinkedList<Integer>();
            for (int i = 0; i < list.size(); i++) {
                linkedlists.add(list.get(i).getId());
            }
            projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);
        }
        realtimeDTOlist = wsTowerCraneEquipmentMonitorMapper.selectEquipmentList(towerCraneMonitoringRealtimeDTO,projectIds);
        if(realtimeDTOlist.size() > 0){
            for (int i = 0; i < realtimeDTOlist.size(); i++) {
                WsTowerCraneMonitoringRealtimeDTO realtimeDTOonlineNumAndOfflineNum = selectOnlineNumAndOfflineNum(realtimeDTOlist.get(i).getMonitoringEquipmentNo());
                if(realtimeDTOonlineNumAndOfflineNum != null){
                    if(realtimeDTOonlineNumAndOfflineNum.getOnlineStatusName() != null){
                        realtimeDTOlist.get(i).setOnlineStatusName(realtimeDTOonlineNumAndOfflineNum.getOnlineStatusName());
                    }else{
                        realtimeDTOlist.get(i).setOnlineStatusName("");
                    }
                }
            }
        }
        return realtimeDTOlist;
    }

    public WsTowerCraneMonitoringRealtimeDTO selectOnlineNumAndOfflineNum(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringRealtimeDTO realtimeDTO = wsTowerCraneEquipmentMonitorMapper.selectOnlineNumAndOfflineNum(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return realtimeDTO;
    }

    @Override
    public List<WsTowerCraneMonitoringRealtimeDTO> selectIllegalList(WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO) {//查询条件未完成
        List<WsTowerCraneMonitoringRealtimeDTO> realtimeDTOlist = new ArrayList<WsTowerCraneMonitoringRealtimeDTO>();
        //根据公司id去查询 该公司下所有项目；
        Integer[] projectIds = null;
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0) {
            LinkedList<Integer> linkedlists = new LinkedList<Integer>();
            for (int i = 0; i < list.size(); i++) {
                linkedlists.add(list.get(i).getId());
            }
            projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);
        }

        List<WsTowerCraneMonitoringRealtimeDTO> realtimeDTOviolationContentList = new ArrayList<WsTowerCraneMonitoringRealtimeDTO>();
        realtimeDTOlist = wsTowerCraneEquipmentMonitorMapper.selectEquipmentList(towerCraneMonitoringRealtimeDTO,projectIds);
        if(realtimeDTOlist.size() > 0){
            for (int i = 0; i < realtimeDTOlist.size(); i++) {
                if(realtimeDTOlist.get(i).getMonitoringEquipmentNo() != null && realtimeDTOlist.get(i).getMonitoringEquipmentNo() != ""){
                    realtimeDTOviolationContentList = selectViolationContent(realtimeDTOlist.get(i).getMonitoringEquipmentNo(),"","");
                    if(realtimeDTOviolationContentList.size() > 0) {
                        for (int k = 0; k < realtimeDTOviolationContentList.size(); k++) {
                            realtimeDTOviolationContentList.get(k).setProjectName(realtimeDTOlist.get(i).getProjectName());
                            realtimeDTOviolationContentList.get(k).setPropertyRecordno(realtimeDTOlist.get(i).getPropertyRecordno());
                            realtimeDTOviolationContentList.get(k).setEquipmentName(realtimeDTOlist.get(i).getEquipmentName());
                            realtimeDTOviolationContentList.get(k).setMonitoringEquipmentNo(realtimeDTOlist.get(i).getMonitoringEquipmentNo());
                            realtimeDTOviolationContentList.get(k).setViolationTime(realtimeDTOviolationContentList.get(k).getViolationTime());
                            realtimeDTOviolationContentList.get(k).setViolationContent(realtimeDTOviolationContentList.get(k).getViolationContent());
                            realtimeDTOviolationContentList.get(k).setProvince(realtimeDTOlist.get(i).getProvince());
                            realtimeDTOviolationContentList.get(k).setCity(realtimeDTOlist.get(i).getCity());
                            realtimeDTOviolationContentList.get(k).setCounty(realtimeDTOlist.get(i).getCounty());
                        }
                    }
                }
            }
        }
        return realtimeDTOviolationContentList;
    }

    public List<WsTowerCraneMonitoringRealtimeDTO> selectViolationContent(String monitoringEquipmentNo,String startTime,String endTime) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsTowerCraneMonitoringRealtimeDTO> realtimeDTO = wsTowerCraneEquipmentMonitorMapper.selectViolationContent(monitoringEquipmentNo,startTime,endTime);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return realtimeDTO;
    }


    @Override
    public List<WsTowerCraneMonitoringStatisticsDTO> selectMonitoringEquipmentAnalysis(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO) {
        List<WsTowerCraneMonitoringStatisticsDTO> statisticsDTOlist = new ArrayList<WsTowerCraneMonitoringStatisticsDTO>();
        if(wsTowerCraneMonitoringStatisticsDTO.getDays() == 7){
             //根据公司id查询项目
            if(wsTowerCraneMonitoringStatisticsDTO.getCompanyId() == null){//默认该账号下全项目
                Integer[] projectIds = null;
                WsProject wsProject = new WsProject();
                List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
                if(list.size() > 0) {
                    LinkedList<Integer> linkedlists = new LinkedList<Integer>();
                    for (int i = 0; i < list.size(); i++) {
                        linkedlists.add(list.get(i).getId());
                    }
                    projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);

                    statisticsDTOlist = wsTowerCraneEquipmentMonitorMapper.selectMonitoringEquipmentAnalysisSeven(projectIds);
                    for(int i = 0; i < statisticsDTOlist.size(); i++){
                        WsTowerCraneMonitoringStatisticsDTO monitoringStatisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectMonitoringEquipmentAnalysisSevenTj(projectIds,statisticsDTOlist.get(i).getStatisticsTime());
                        statisticsDTOlist.get(i).setTowerCraneEquipmentNum(monitoringStatisticsDTO.getTowerCraneEquipmentNum());
                        if(statisticsDTOlist.get(i).getTowerCraneEquipmentNum() > 0 && statisticsDTOlist.get(i).getTowerCraneMonitoringEquipmentNum() > 0){
                            float a = statisticsDTOlist.get(i).getTowerCraneMonitoringEquipmentNum();
                            float b = statisticsDTOlist.get(i).getTowerCraneEquipmentNum();
                            double onlineRates = a / b * 100;
                            DecimalFormat df = new DecimalFormat("#.00");
                            String online = df.format(onlineRates);
                            statisticsDTOlist.get(i).setMonitoringOnlineRate(online);
                        }else{
                            statisticsDTOlist.get(i).setMonitoringOnlineRate("0.00");
                        }
                        /*List<WsTowerCraneMonitoringStatisticsDTO> listTowerCraneMonitoring = wsTowerCraneEquipmentMonitorMapper.selectMonitoringEquipmentTjs(projectIds,statisticsDTOlist.get(i).getStatisticsTime());
                        int rate = 0;
                        int onlineRate = 0;
                        if(listTowerCraneMonitoring.size() > 0){
                            for (int k = 0; k < listTowerCraneMonitoring.size(); k++) {
                                //List<WsTowerCraneMonitoringStatisticsDTO> monitoringStatisticsList =  selectMonitoringEquipmentTjZxl(listTowerCraneMonitoring.get(k).getMonitoringEquipmentNo(),statisticsDTOlist.get(i).getStatisticsTime());
                                onlineRate += listTowerCraneMonitoring.size() + 1;
                            }
                            if(onlineRate > 0){
                                double onlineRates = onlineRate / statisticsDTOlist.get(i).getTowerCraneEquipmentNum() * 100;
                                DecimalFormat df = new DecimalFormat("#.00");
                                String online = df.format(onlineRates);
                                statisticsDTOlist.get(i).setMonitoringOnlineRate(online);
                            }else{
                                statisticsDTOlist.get(i).setMonitoringOnlineRate("0.00");
                            }
                        }else{
                            statisticsDTOlist.get(i).setMonitoringOnlineRate("0.00");
                        }*/
                    }
                }
            }else{
                Integer[] projectIds = null;
                //根据公司id去查询 该公司下所有项目；
                //判断条件 companyId 不为空，根据其中条件 查询项目id集合并转成Integer[] 数组；
                List<SysEnterprise> listTree= sysDeptService.selectOrgListByOrgId(wsTowerCraneMonitoringStatisticsDTO.getCompanyId().longValue());
                if(listTree.size() > 0) {
                    LinkedList<Integer> linkedlists = new LinkedList<Integer>();
                    for (int i = 0; i < listTree.size(); i++) {
                        linkedlists.add(listTree.get(i).getEnterpriseId().intValue());
                    }
                    projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);

                    statisticsDTOlist = wsTowerCraneEquipmentMonitorMapper.selectMonitoringEquipmentAnalysisSeven(projectIds);
                    for(int i = 0; i < statisticsDTOlist.size(); i++){
                        WsTowerCraneMonitoringStatisticsDTO monitoringStatisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectMonitoringEquipmentAnalysisSevenTj(projectIds,statisticsDTOlist.get(i).getStatisticsTime());
                        statisticsDTOlist.get(i).setTowerCraneEquipmentNum(monitoringStatisticsDTO.getTowerCraneEquipmentNum());
                        if(statisticsDTOlist.get(i).getTowerCraneEquipmentNum() > 0 && statisticsDTOlist.get(i).getTowerCraneMonitoringEquipmentNum() > 0){
                            float a = statisticsDTOlist.get(i).getTowerCraneMonitoringEquipmentNum();
                            float b = statisticsDTOlist.get(i).getTowerCraneEquipmentNum();
                            double onlineRates = a / b * 100;
                            DecimalFormat df = new DecimalFormat("#.00");
                            String online = df.format(onlineRates);
                            statisticsDTOlist.get(i).setMonitoringOnlineRate(online);
                        }else{
                            statisticsDTOlist.get(i).setMonitoringOnlineRate("0.00");
                        }
                        /*List<WsTowerCraneMonitoringStatisticsDTO> listTowerCraneMonitoring = wsTowerCraneEquipmentMonitorMapper.selectMonitoringEquipmentTjs(projectIds,statisticsDTOlist.get(i).getStatisticsTime());
                        int rate = 0;
                        int onlineRate = 0;
                        if(listTowerCraneMonitoring.size() > 0){
                            for (int k = 0; k < listTowerCraneMonitoring.size(); k++) {
                                //List<WsTowerCraneMonitoringStatisticsDTO> monitoringStatisticsList =  selectMonitoringEquipmentTjZxl(listTowerCraneMonitoring.get(k).getMonitoringEquipmentNo(),statisticsDTOlist.get(i).getStatisticsTime());
                                onlineRate += rate + listTowerCraneMonitoring.size();
                            }
                            if(onlineRate > 0){
                                double onlineRates = onlineRate / statisticsDTOlist.get(i).getTowerCraneEquipmentNum() * 100;
                                DecimalFormat df = new DecimalFormat("#.00");
                                String online = df.format(onlineRates);
                                statisticsDTOlist.get(i).setMonitoringOnlineRate(online);
                            }else{
                                statisticsDTOlist.get(i).setMonitoringOnlineRate("0.00");
                            }
                        }else{
                            statisticsDTOlist.get(i).setMonitoringOnlineRate("0.00");
                        }*/
                    }
                }
            }
        }
        if(wsTowerCraneMonitoringStatisticsDTO.getDays() == 30){
            //根据公司id查询项目
            if(wsTowerCraneMonitoringStatisticsDTO.getCompanyId() == null){//默认该账号下全项目
                Integer[] projectIds = null;
                WsProject wsProject = new WsProject();
                List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
                if(list.size() > 0) {
                    LinkedList<Integer> linkedlists = new LinkedList<Integer>();
                    for (int i = 0; i < list.size(); i++) {
                        linkedlists.add(list.get(i).getId());
                    }
                    projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);

                    statisticsDTOlist = wsTowerCraneEquipmentMonitorMapper.selectMonitoringEquipmentAnalysisThirty(projectIds);
                    for(int i = 0; i < statisticsDTOlist.size(); i++){
                        WsTowerCraneMonitoringStatisticsDTO monitoringStatisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectMonitoringEquipmentAnalysisSevenTj(projectIds,statisticsDTOlist.get(i).getStatisticsTime());
                        statisticsDTOlist.get(i).setTowerCraneEquipmentNum(monitoringStatisticsDTO.getTowerCraneEquipmentNum());
                        if(statisticsDTOlist.get(i).getTowerCraneEquipmentNum() > 0 && statisticsDTOlist.get(i).getTowerCraneMonitoringEquipmentNum() > 0){
                            float a = statisticsDTOlist.get(i).getTowerCraneMonitoringEquipmentNum();
                            float b = statisticsDTOlist.get(i).getTowerCraneEquipmentNum();
                            double onlineRates = a / b * 100;
                            DecimalFormat df = new DecimalFormat("#.00");
                            String online = df.format(onlineRates);
                            statisticsDTOlist.get(i).setMonitoringOnlineRate(online);
                        }else{
                            statisticsDTOlist.get(i).setMonitoringOnlineRate("0.00");
                        }
                    }
                }
            }else{
                //根据公司id去查询 该公司下所有项目；
                Integer[] projectIds = null;
                //根据公司id去查询 该公司下所有项目；
                //判断条件 companyId 不为空，根据其中条件 查询项目id集合并转成Integer[] 数组；
                List<SysEnterprise> listTree= sysDeptService.selectOrgListByOrgId(wsTowerCraneMonitoringStatisticsDTO.getCompanyId().longValue());
                if(listTree.size() > 0) {
                    LinkedList<Integer> linkedlists = new LinkedList<Integer>();
                    for (int i = 0; i < listTree.size(); i++) {
                        linkedlists.add(listTree.get(i).getEnterpriseId().intValue());
                    }
                    projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);

                    statisticsDTOlist = wsTowerCraneEquipmentMonitorMapper.selectMonitoringEquipmentAnalysisThirty(projectIds);
                    for(int i = 0; i < statisticsDTOlist.size(); i++){
                        WsTowerCraneMonitoringStatisticsDTO monitoringStatisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectMonitoringEquipmentAnalysisSevenTj(projectIds,statisticsDTOlist.get(i).getStatisticsTime());
                        statisticsDTOlist.get(i).setTowerCraneEquipmentNum(monitoringStatisticsDTO.getTowerCraneEquipmentNum());
                        if(statisticsDTOlist.get(i).getTowerCraneEquipmentNum() > 0 && statisticsDTOlist.get(i).getTowerCraneMonitoringEquipmentNum() > 0){
                            float a = statisticsDTOlist.get(i).getTowerCraneMonitoringEquipmentNum();
                            float b = statisticsDTOlist.get(i).getTowerCraneEquipmentNum();
                            double onlineRates = a / b * 100;
                            DecimalFormat df = new DecimalFormat("#.00");
                            String online = df.format(onlineRates);
                            statisticsDTOlist.get(i).setMonitoringOnlineRate(online);
                        }else{
                            statisticsDTOlist.get(i).setMonitoringOnlineRate("0.00");
                        }
                    }
                }
            }
        }
        return statisticsDTOlist;
    }

    private List<WsTowerCraneMonitoringStatisticsDTO> selectMonitoringEquipmentTjZxl(String monitoringEquipmentNo,String statisticsTime) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsTowerCraneMonitoringStatisticsDTO> towerCraneMonitoring = wsTowerCraneEquipmentMonitorMapper.selectMonitoringEquipmentTjZxl(monitoringEquipmentNo,statisticsTime);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return towerCraneMonitoring;
    }


    @Override
    public List<WsTowerCraneMonitoringStatisticsDTO> selectEquipmentViolationAnalysis(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO) {
        List<WsTowerCraneMonitoringStatisticsDTO> statisticsDTOlist = new ArrayList<WsTowerCraneMonitoringStatisticsDTO>();
        List<WsTowerCraneMonitoringRealtimeDTO> equipmentList = new ArrayList<WsTowerCraneMonitoringRealtimeDTO>();
        if(wsTowerCraneMonitoringStatisticsDTO.getDays() == 7){
            //根据公司id查询项目
            if(wsTowerCraneMonitoringStatisticsDTO.getCompanyId() == null){//默认该账号下全项目
                Integer[] projectIds = null;
                WsProject wsProject = new WsProject();
                List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
                if(list.size() > 0) {
                    LinkedList<Integer> linkedlists = new LinkedList<Integer>();
                    for (int i = 0; i < list.size(); i++) {
                        linkedlists.add(list.get(i).getId());
                    }
                    projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);

                    equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoList(projectIds);
                    if(equipmentList.size() > 0) {
                        LinkedList<String> linkedlist = new LinkedList<String>();
                        for (int i = 0; i < equipmentList.size(); i++) {
                            linkedlist.add(equipmentList.get(i).getMonitoringEquipmentNo());
                        }
                        String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                        statisticsDTOlist = selectEquipmentViolationAnalysisSeven(monitoringEquipmentNos);
                    }
                }
            }else{
                //根据公司id去查询 该公司下所有项目；
                Integer[] projectIds = null;
                //根据公司id去查询 该公司下所有项目；
                //判断条件 companyId 不为空，根据其中条件 查询项目id集合并转成Integer[] 数组；
                List<SysEnterprise> listTree= sysDeptService.selectOrgListByOrgId(wsTowerCraneMonitoringStatisticsDTO.getCompanyId().longValue());
                if(listTree.size() > 0) {
                    LinkedList<Integer> linkedlists = new LinkedList<Integer>();
                    for (int i = 0; i < listTree.size(); i++) {
                        linkedlists.add(listTree.get(i).getEnterpriseId().intValue());
                    }
                    projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);

                    equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoList(projectIds);
                    if(equipmentList.size() > 0) {
                        LinkedList<String> linkedlist = new LinkedList<String>();
                        for (int i = 0; i < equipmentList.size(); i++) {
                            linkedlist.add(equipmentList.get(i).getMonitoringEquipmentNo());
                        }
                        String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                        statisticsDTOlist = selectEquipmentViolationAnalysisSeven(monitoringEquipmentNos);
                    }
                }
            }
        }
        if(wsTowerCraneMonitoringStatisticsDTO.getDays() == 30){
            //根据公司id查询项目
            if(wsTowerCraneMonitoringStatisticsDTO.getCompanyId() == null){//默认该账号下全项目
                Integer[] projectIds = null;
                WsProject wsProject = new WsProject();
                List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
                if(list.size() > 0) {
                    LinkedList<Integer> linkedlists = new LinkedList<Integer>();
                    for (int i = 0; i < list.size(); i++) {
                        linkedlists.add(list.get(i).getId());
                    }
                    projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);

                    equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoList(projectIds);
                    if(equipmentList.size() > 0) {
                        LinkedList<String> linkedlist = new LinkedList<String>();
                        for (int i = 0; i < equipmentList.size(); i++) {
                            linkedlist.add(equipmentList.get(i).getMonitoringEquipmentNo());
                        }
                        String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                        statisticsDTOlist = selectEquipmentViolationAnalysisThirty(monitoringEquipmentNos);
                    }
                }
            }else{
                //根据公司id去查询 该公司下所有项目；
                Integer[] projectIds = null;
                //根据公司id去查询 该公司下所有项目；
                //判断条件 companyId 不为空，根据其中条件 查询项目id集合并转成Integer[] 数组；
                List<SysEnterprise> listTree= sysDeptService.selectOrgListByOrgId(wsTowerCraneMonitoringStatisticsDTO.getCompanyId().longValue());
                if(listTree.size() > 0) {
                    LinkedList<Integer> linkedlists = new LinkedList<Integer>();
                    for (int i = 0; i < listTree.size(); i++) {
                        linkedlists.add(listTree.get(i).getEnterpriseId().intValue());
                    }
                    projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);

                    equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoList(projectIds);
                    if(equipmentList.size() > 0) {
                        LinkedList<String> linkedlist = new LinkedList<String>();
                        for (int i = 0; i < equipmentList.size(); i++) {
                            linkedlist.add(equipmentList.get(i).getMonitoringEquipmentNo());
                        }
                        String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                        statisticsDTOlist = selectEquipmentViolationAnalysisThirty(monitoringEquipmentNos);
                    }
                }
            }
        }
        return statisticsDTOlist;
    }

    public List<WsTowerCraneMonitoringStatisticsDTO> selectEquipmentViolationAnalysisThirty(String[] monitoringEquipmentNos) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsTowerCraneMonitoringStatisticsDTO> statisticsDTOlist = wsTowerCraneEquipmentMonitorMapper.selectEquipmentViolationAnalysisThirty(monitoringEquipmentNos);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return statisticsDTOlist;
    }

    public List<WsTowerCraneMonitoringStatisticsDTO> selectEquipmentViolationAnalysisSeven(String[] monitoringEquipmentNos) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsTowerCraneMonitoringStatisticsDTO> statisticsDTOlist = wsTowerCraneEquipmentMonitorMapper.selectEquipmentViolationAnalysisSeven(monitoringEquipmentNos);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return statisticsDTOlist;
    }

    @Override
    public WsTowerCraneMonitoringStatisticsDTO selectProjectViolationProportion(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO) {
        List<WsTowerCraneMonitoringRealtimeDTO> equipmentList = new ArrayList<WsTowerCraneMonitoringRealtimeDTO>();
        List<WsTowerCraneMonitoringStatisticsDTO> statisticsDTO = new ArrayList<WsTowerCraneMonitoringStatisticsDTO>();
        List<WsTowerCraneMonitoringStatisticsDTO> statisticsDTOs = new ArrayList<WsTowerCraneMonitoringStatisticsDTO>();
        WsTowerCraneMonitoringStatisticsDTO monitoringStatistics = new WsTowerCraneMonitoringStatisticsDTO();
        if(wsTowerCraneMonitoringStatisticsDTO.getDays() == 7){
            //根据公司id查询项目
            if(wsTowerCraneMonitoringStatisticsDTO.getCompanyId() == null){//默认该账号下全项目
                Integer[] projectIds = null;
                WsProject wsProject = new WsProject();
                List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
                if(list.size() > 0) {
                    LinkedList<Integer> linkedlists = new LinkedList<Integer>();
                    for (int i = 0; i < list.size(); i++) {
                        linkedlists.add(list.get(i).getId());
                    }
                    projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);

                    equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoList(projectIds);
                    if(equipmentList.size() > 0) {
                        LinkedList<String> linkedlist = new LinkedList<String>();
                        for (int i = 0; i < equipmentList.size(); i++) {
                            linkedlist.add(equipmentList.get(i).getMonitoringEquipmentNo());
                        }
                        String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                        statisticsDTO = selectQueryIllegalEquipmentSeven(monitoringEquipmentNos);
                        if(statisticsDTO.size() > 0) {
                            LinkedList<String> linkedlist2 = new LinkedList<String>();
                            for (int i = 0; i < statisticsDTO.size(); i++) {
                                linkedlist2.add(statisticsDTO.get(i).getMonitoringEquipmentNo());
                            }
                            String[] quipmentNos = linkedlist2.toArray(new String[linkedlist2.size()]);
                            statisticsDTOs = wsTowerCraneEquipmentMonitorMapper.selectProjectIllegalSeven(quipmentNos);
                            int projectIllegalNum = statisticsDTOs.size();
                            monitoringStatistics.setProportionProjectViolation(String.valueOf(projectIllegalNum));
                        }
                    }
                    monitoringStatistics.setProjectNumber(list.size());
                }
            }else{
                //根据公司id去查询 该公司下所有项目；
                Integer[] projectIds = null;
                //根据公司id去查询 该公司下所有项目；
                //判断条件 companyId 不为空，根据其中条件 查询项目id集合并转成Integer[] 数组；
                List<SysEnterprise> listTree= sysDeptService.selectOrgListByOrgId(wsTowerCraneMonitoringStatisticsDTO.getCompanyId().longValue());
                if(listTree.size() > 0) {
                    LinkedList<Integer> linkedlists = new LinkedList<Integer>();
                    for (int i = 0; i < listTree.size(); i++) {
                        linkedlists.add(listTree.get(i).getEnterpriseId().intValue());
                    }
                    projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);

                    equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoList(projectIds);
                    if(equipmentList.size() > 0) {
                        LinkedList<String> linkedlist = new LinkedList<String>();
                        for (int i = 0; i < equipmentList.size(); i++) {
                            linkedlist.add(equipmentList.get(i).getMonitoringEquipmentNo());
                        }
                        String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                        statisticsDTO = selectQueryIllegalEquipmentSeven(monitoringEquipmentNos);
                        if(statisticsDTO.size() > 0) {
                            LinkedList<String> linkedlist2 = new LinkedList<String>();
                            for (int i = 0; i < statisticsDTO.size(); i++) {
                                linkedlist2.add(statisticsDTO.get(i).getMonitoringEquipmentNo());
                            }
                            String[] quipmentNos = linkedlist2.toArray(new String[linkedlist2.size()]);
                            statisticsDTOs = wsTowerCraneEquipmentMonitorMapper.selectProjectIllegalSeven(quipmentNos);
                            int projectIllegalNum = statisticsDTOs.size();
                            monitoringStatistics.setProportionProjectViolation(String.valueOf(projectIllegalNum));
                        }
                    }
                    monitoringStatistics.setProjectNumber(listTree.size());
                }
            }
        }else if(wsTowerCraneMonitoringStatisticsDTO.getDays() == 30){
            //根据公司id查询项目
            if(wsTowerCraneMonitoringStatisticsDTO.getCompanyId() == null){//默认该账号下全项目
                Integer[] projectIds = null;
                WsProject wsProject = new WsProject();
                List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
                if(list.size() > 0) {
                    LinkedList<Integer> linkedlists = new LinkedList<Integer>();
                    for (int i = 0; i < list.size(); i++) {
                        linkedlists.add(list.get(i).getId());
                    }
                    projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);

                    equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoList(projectIds);
                    if(equipmentList.size() > 0) {
                        LinkedList<String> linkedlist = new LinkedList<String>();
                        for (int i = 0; i < equipmentList.size(); i++) {
                            linkedlist.add(equipmentList.get(i).getMonitoringEquipmentNo());
                        }
                        String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                        statisticsDTO = selectQueryIllegalEquipmentThirty(monitoringEquipmentNos);
                        if(statisticsDTO.size() > 0) {
                            LinkedList<String> linkedlist2 = new LinkedList<String>();
                            for (int i = 0; i < statisticsDTO.size(); i++) {
                                linkedlist2.add(statisticsDTO.get(i).getMonitoringEquipmentNo());
                            }
                            String[] quipmentNos = linkedlist2.toArray(new String[linkedlist2.size()]);
                            statisticsDTOs = wsTowerCraneEquipmentMonitorMapper.selectProjectIllegalThirty(quipmentNos);
                            int projectIllegalNum = statisticsDTOs.size();
                            monitoringStatistics.setProportionProjectViolation(String.valueOf(projectIllegalNum));
                        }
                    }
                    monitoringStatistics.setProjectNumber(list.size());
                }
            }else{
                //根据公司id去查询 该公司下所有项目；
                Integer[] projectIds = null;
                //根据公司id去查询 该公司下所有项目；
                //判断条件 companyId 不为空，根据其中条件 查询项目id集合并转成Integer[] 数组；
                List<SysEnterprise> listTree= sysDeptService.selectOrgListByOrgId(wsTowerCraneMonitoringStatisticsDTO.getCompanyId().longValue());
                if(listTree.size() > 0) {
                    LinkedList<Integer> linkedlists = new LinkedList<Integer>();
                    for (int i = 0; i < listTree.size(); i++) {
                        linkedlists.add(listTree.get(i).getEnterpriseId().intValue());
                    }
                    projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);

                    equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoList(projectIds);
                    if(equipmentList.size() > 0) {
                        LinkedList<String> linkedlist = new LinkedList<String>();
                        for (int i = 0; i < equipmentList.size(); i++) {
                            linkedlist.add(equipmentList.get(i).getMonitoringEquipmentNo());
                        }
                        String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                        statisticsDTO = selectQueryIllegalEquipmentThirty(monitoringEquipmentNos);
                        if(statisticsDTO.size() > 0) {
                            LinkedList<String> linkedlist2 = new LinkedList<String>();
                            for (int i = 0; i < statisticsDTO.size(); i++) {
                                linkedlist2.add(statisticsDTO.get(i).getMonitoringEquipmentNo());
                            }
                            String[] quipmentNos = linkedlist2.toArray(new String[linkedlist2.size()]);
                            statisticsDTOs = wsTowerCraneEquipmentMonitorMapper.selectProjectIllegalThirty(quipmentNos);
                            int projectIllegalNum = statisticsDTOs.size();
                            monitoringStatistics.setProportionProjectViolation(String.valueOf(projectIllegalNum));
                        }
                    }
                    monitoringStatistics.setProjectNumber(listTree.size());
                }
            }
        }
        return monitoringStatistics;
    }


    public List<WsTowerCraneMonitoringStatisticsDTO> selectQueryIllegalEquipmentThirty(String[] monitoringEquipmentNos) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsTowerCraneMonitoringStatisticsDTO> statisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectQueryIllegalEquipmentThirty(monitoringEquipmentNos);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return statisticsDTO;
    }

    public List<WsTowerCraneMonitoringStatisticsDTO> selectQueryIllegalEquipmentSeven(String[] monitoringEquipmentNos) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsTowerCraneMonitoringStatisticsDTO> statisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectQueryIllegalEquipmentSeven(monitoringEquipmentNos);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return statisticsDTO;
    }


    @Override
    public List<WsTowerCraneMonitoringStatisticsDTO> selectRankingOfViolations(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO) {
        WsTowerCraneMonitoringStatisticsDTO statisticsDTO = null;
        List<WsTowerCraneMonitoringRealtimeDTO> equipmentList = new ArrayList<WsTowerCraneMonitoringRealtimeDTO>();
        List<WsTowerCraneMonitoringStatisticsDTO> towerCraneMonitoringStatisticsDTOList = new ArrayList<WsTowerCraneMonitoringStatisticsDTO>();
        if(wsTowerCraneMonitoringStatisticsDTO.getDays() == 7){
            if(wsTowerCraneMonitoringStatisticsDTO.getCompanyId() == null){//默认该账号下全项目
                WsProject wsProject = new WsProject();
                List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
                if(list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        Integer projectId = list.get(i).getId();
                        String projectName = list.get(i).getProjectName();
                        equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoListByProjectId(projectId);
                        if (equipmentList.size() > 0) {
                            LinkedList<String> linkedlist = new LinkedList<String>();
                            for (int k = 0; k < equipmentList.size(); k++) {
                                linkedlist.add(equipmentList.get(k).getMonitoringEquipmentNo());
                            }
                            String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                            statisticsDTO = selectEquipmentNoListByProjectIdSeven(monitoringEquipmentNos);
                            if(statisticsDTO.getProjectNumber() > 0){
                                WsTowerCraneMonitoringStatisticsDTO monitoringStatisticsDTO = new WsTowerCraneMonitoringStatisticsDTO();
                                monitoringStatisticsDTO.setProjectName(projectName);
                                monitoringStatisticsDTO.setProjectNumber(statisticsDTO.getProjectNumber());
                                towerCraneMonitoringStatisticsDTOList.add(monitoringStatisticsDTO);
                            }
                        }
                    }
                }
            }else{
                //根据公司id去查询 该公司下所有项目；
                //判断条件 companyId 不为空，根据其中条件 查询项目id集合并转成Integer[] 数组；
                List<SysEnterprise> listTree= sysDeptService.selectOrgListByOrgId(wsTowerCraneMonitoringStatisticsDTO.getCompanyId().longValue());
                if(listTree.size() > 0) {
                    for (int i = 0; i < listTree.size(); i++) {
                            WsProject wsProject = wsProjectService.selectWsProjectById(listTree.get(i).getEnterpriseId().intValue());
                            String projectName = wsProject.getProjectName();
                            equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoListByProjectId(wsProject.getId());
                            if (equipmentList.size() > 0) {
                                LinkedList<String> linkedlist = new LinkedList<String>();
                                for (int a = 0; a < equipmentList.size(); a++) {
                                    linkedlist.add(equipmentList.get(a).getMonitoringEquipmentNo());
                                }
                                String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                                statisticsDTO = selectEquipmentNoListByProjectIdSeven(monitoringEquipmentNos);
                                if(statisticsDTO.getProjectNumber() > 0){
                                    WsTowerCraneMonitoringStatisticsDTO monitoringStatisticsDTO = new WsTowerCraneMonitoringStatisticsDTO();
                                    monitoringStatisticsDTO.setProjectName(projectName);
                                    monitoringStatisticsDTO.setProjectNumber(statisticsDTO.getProjectNumber());
                                    towerCraneMonitoringStatisticsDTOList.add(monitoringStatisticsDTO);
                                }
                            }
                    }
                }
            }
        }if(wsTowerCraneMonitoringStatisticsDTO.getDays() == 30){
            if(wsTowerCraneMonitoringStatisticsDTO.getCompanyId() == null){//默认该账号下全项目
                //根据公司id查询公司下所有项目list
                WsProject wsProject = new WsProject();
                List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
                for (int i = 0; i < list.size(); i++) {
                    Integer projectId = list.get(i).getId();
                    String projectName = list.get(i).getProjectName();
                    equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoListByProjectId(projectId);
                    if (equipmentList.size() > 0) {
                        LinkedList<String> linkedlist = new LinkedList<String>();
                        for (int k = 0; k < equipmentList.size(); k++) {
                            linkedlist.add(equipmentList.get(k).getMonitoringEquipmentNo());
                        }
                        String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                        statisticsDTO = selectEquipmentNoListByProjectIdThirty(monitoringEquipmentNos);
                        if(statisticsDTO.getProjectNumber() > 0){
                            WsTowerCraneMonitoringStatisticsDTO monitoringStatisticsDTO = new WsTowerCraneMonitoringStatisticsDTO();
                            monitoringStatisticsDTO.setProjectName(projectName);
                            monitoringStatisticsDTO.setProjectNumber(statisticsDTO.getProjectNumber());
                            towerCraneMonitoringStatisticsDTOList.add(monitoringStatisticsDTO);
                        }
                    }
                }
            }else{
                //根据公司id查询公司下所有项目list
                //判断条件 companyId 不为空，根据其中条件 查询项目id集合并转成Integer[] 数组；
                List<SysEnterprise> listTree= sysDeptService.selectOrgListByOrgId(wsTowerCraneMonitoringStatisticsDTO.getCompanyId().longValue());
                for (int i = 0; i < listTree.size(); i++) {
                        WsProject wsProject = wsProjectService.selectWsProjectById(listTree.get(i).getEnterpriseId().intValue());
                        String projectName = wsProject.getProjectName();
                        equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoListByProjectId(wsProject.getId());
                        if (equipmentList.size() > 0) {
                            LinkedList<String> linkedlist = new LinkedList<String>();
                            for (int a = 0; a < equipmentList.size(); a++) {
                                linkedlist.add(equipmentList.get(a).getMonitoringEquipmentNo());
                            }
                            String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                            statisticsDTO = selectEquipmentNoListByProjectIdThirty(monitoringEquipmentNos);
                            if(statisticsDTO.getProjectNumber() > 0){
                                WsTowerCraneMonitoringStatisticsDTO monitoringStatisticsDTO = new WsTowerCraneMonitoringStatisticsDTO();
                                monitoringStatisticsDTO.setProjectName(projectName);
                                monitoringStatisticsDTO.setProjectNumber(statisticsDTO.getProjectNumber());
                                towerCraneMonitoringStatisticsDTOList.add(monitoringStatisticsDTO);
                            }
                        }
                }
            }
        }
        return towerCraneMonitoringStatisticsDTOList;
    }

    public WsTowerCraneMonitoringStatisticsDTO selectEquipmentNoListByProjectIdThirty(String[] monitoringEquipmentNos) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringStatisticsDTO statisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoListByProjectIdThirty(monitoringEquipmentNos);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return statisticsDTO;
    }

    public WsTowerCraneMonitoringStatisticsDTO selectEquipmentNoListByProjectIdSeven(String[] monitoringEquipmentNos) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringStatisticsDTO statisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoListByProjectIdSeven(monitoringEquipmentNos);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return statisticsDTO;
    }

    /*按项目*/
    @Override
    public List<WsTowerCraneMonitoringStatisticsDTO> selectBadOperationDrive(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO) {
        List<WsTowerCraneMonitoringStatisticsDTO> driveStatisticsDTOList = new ArrayList<WsTowerCraneMonitoringStatisticsDTO>();
        WsTowerCraneMonitoringStatisticsDTO driveStatistics = new WsTowerCraneMonitoringStatisticsDTO();
        List<WsTowerCraneMonitoringStatisticsDTO> statisticsFaceNumberList = new ArrayList<WsTowerCraneMonitoringStatisticsDTO>();
        List<WsTowerCraneMonitoringRealtimeDTO> equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoListByProjectId(wsTowerCraneMonitoringStatisticsDTO.getProjectId());
        String[] monitoringEquipmentNos = null;
        if (equipmentList.size() > 0) {
            LinkedList<String> linkedlist = new LinkedList<String>();
            for (int i = 0; i < equipmentList.size(); i++) {
                linkedlist.add(equipmentList.get(i).getMonitoringEquipmentNo());
            }
            monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
        }
        if(monitoringEquipmentNos != null){
            statisticsFaceNumberList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoListBymonitoringEquipmentNos(monitoringEquipmentNos);
        }
        if(wsTowerCraneMonitoringStatisticsDTO.getDays() != null && wsTowerCraneMonitoringStatisticsDTO.getDays() == 7){
            if (statisticsFaceNumberList.size() > 0) {
                for (int a = 0; a < statisticsFaceNumberList.size(); a++) {
                    String faceNumber = statisticsFaceNumberList.get(a).getFaceNumber();
                    String monitoringEquipmentNo = statisticsFaceNumberList.get(a).getMonitoringEquipmentNo();
                    WsTowerCraneMonitoringStatisticsDTO faceNumberStatisticsDTO = selectEquipmentNoListByFaceNumber(faceNumber);
                    WsTowerCraneMonitoringStatisticsDTO monitoringEquipmentNoStatisticsDTO = selectEquipmentNoListByMonitoringEquipmentNo(monitoringEquipmentNo);
                    if (faceNumberStatisticsDTO != null) {
                        if(faceNumberStatisticsDTO.getDriverName() != null && faceNumberStatisticsDTO.getDriverName() != ""){
                            driveStatistics.setDriverName(faceNumberStatisticsDTO.getDriverName());
                        }else{
                            driveStatistics.setDriverName("未知司机");
                        }
                    }/*else{
                        driveStatisticsDTOList.get(a).setDriverName("未知司机");
                    }*/
                    if(monitoringEquipmentNoStatisticsDTO != null){
                        driveStatistics.setViolationsNumber(monitoringEquipmentNoStatisticsDTO.getViolationsNumber());
                        driveStatistics.setViolationsRate(monitoringEquipmentNoStatisticsDTO.getViolationsRate());
                    }
                    driveStatisticsDTOList.add(driveStatistics);
                }
            }

        } else if(wsTowerCraneMonitoringStatisticsDTO.getDays() != null && wsTowerCraneMonitoringStatisticsDTO.getDays() == 30){
            if (statisticsFaceNumberList.size() > 0) {
                for (int a = 0; a < statisticsFaceNumberList.size(); a++) {
                    String faceNumber = statisticsFaceNumberList.get(a).getFaceNumber();
                    String monitoringEquipmentNo = statisticsFaceNumberList.get(a).getMonitoringEquipmentNo();
                    WsTowerCraneMonitoringStatisticsDTO faceNumberStatisticsDTO = selectEquipmentNoListByFaceNumber(faceNumber);
                    WsTowerCraneMonitoringStatisticsDTO monitoringEquipmentNoStatisticsDTO = selectEquipmentNoListByMonitoringEquipmentNoThirty(monitoringEquipmentNo);
                    if (faceNumberStatisticsDTO != null) {
                        if(faceNumberStatisticsDTO.getDriverName() != null && faceNumberStatisticsDTO.getDriverName() != ""){
                            driveStatistics.setDriverName(faceNumberStatisticsDTO.getDriverName());
                        }else{
                            driveStatistics.setDriverName("未知司机");
                        }
                    }/*else{
                        driveStatistics.setDriverName("未知司机");
                    }*/
                    if(monitoringEquipmentNoStatisticsDTO != null){
                        driveStatistics.setViolationsNumber(monitoringEquipmentNoStatisticsDTO.getViolationsNumber());
                        driveStatistics.setViolationsRate(monitoringEquipmentNoStatisticsDTO.getViolationsRate());
                    }
                    driveStatisticsDTOList.add(driveStatistics);
                }
            }
        }else{
            if (statisticsFaceNumberList.size() > 0) {
                for (int a = 0; a < statisticsFaceNumberList.size(); a++) {
                    String faceNumber = statisticsFaceNumberList.get(a).getFaceNumber();
                    String monitoringEquipmentNo = statisticsFaceNumberList.get(a).getMonitoringEquipmentNo();
                    WsTowerCraneMonitoringStatisticsDTO faceNumberStatisticsDTO = selectEquipmentNoListByFaceNumber(faceNumber);
                    WsTowerCraneMonitoringStatisticsDTO monitoringEquipmentNoStatisticsDTO = selectEquipmentMonitoringEquipmentNoOneDay(monitoringEquipmentNo,wsTowerCraneMonitoringStatisticsDTO.getConditionsDate());
                    if (faceNumberStatisticsDTO != null) {
                        if(faceNumberStatisticsDTO.getDriverName() != null && faceNumberStatisticsDTO.getDriverName() != ""){
                            driveStatistics.setDriverName(faceNumberStatisticsDTO.getDriverName());
                        }else{
                            driveStatistics.setDriverName("未知司机");
                        }
                    }/*else{
                        driveStatistics.setDriverName("未知司机");
                    }*/
                    if(monitoringEquipmentNoStatisticsDTO != null){
                        driveStatistics.setViolationsNumber(monitoringEquipmentNoStatisticsDTO.getViolationsNumber());
                        driveStatistics.setViolationsRate(monitoringEquipmentNoStatisticsDTO.getViolationsRate());
                    }
                    driveStatisticsDTOList.add(driveStatistics);
                }
            }
        }
        return driveStatisticsDTOList;
    }

    public WsTowerCraneMonitoringStatisticsDTO selectEquipmentMonitoringEquipmentNoOneDay(String monitoringEquipmentNo, String conditionsDate) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringStatisticsDTO monitoringEquipmentNoStatisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectEquipmentMonitoringEquipmentNoOneDay(monitoringEquipmentNo,conditionsDate);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return monitoringEquipmentNoStatisticsDTO;
    }

    public WsTowerCraneMonitoringStatisticsDTO selectEquipmentNoListByMonitoringEquipmentNoThirty(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringStatisticsDTO monitoringEquipmentNoStatisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoListByMonitoringEquipmentNoThirty(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return monitoringEquipmentNoStatisticsDTO;
    }

    public WsTowerCraneMonitoringStatisticsDTO selectEquipmentNoListByMonitoringEquipmentNo(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringStatisticsDTO monitoringEquipmentNoStatisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoListByMonitoringEquipmentNo(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return monitoringEquipmentNoStatisticsDTO;
    }

    public WsTowerCraneMonitoringStatisticsDTO selectEquipmentNoListByFaceNumber(String faceNumber) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringStatisticsDTO faceNumberStatisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoListByFaceNumber(faceNumber);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return faceNumberStatisticsDTO;
    }

    /**
     * 设备违章
     * @param wsTowerCraneMonitoringStatisticsDTO
     * @return conditionsDate，projectId
     *
     */
    @Override
    public List<WsTowerCraneMonitoringStatisticsDTO> selectEquipmentViolation(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO) {
        List<WsTowerCraneMonitoringRealtimeDTO> equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentViolationList(wsTowerCraneMonitoringStatisticsDTO.getProjectId());
        List<WsTowerCraneMonitoringStatisticsDTO> statisticsDTOList = new ArrayList<WsTowerCraneMonitoringStatisticsDTO>();
        for (int i = 0; i < equipmentList.size(); i++) {
            WsTowerCraneMonitoringStatisticsDTO statisticsDTO = null;
            String monitoringEquipmentNo = equipmentList.get(i).getMonitoringEquipmentNo();
            if(wsTowerCraneMonitoringStatisticsDTO.getDays() != null){
                if(wsTowerCraneMonitoringStatisticsDTO.getDays() == 7){//7
                    statisticsDTO = selectEquipmentViolationConditionsDateSeven(monitoringEquipmentNo);
                }else{//30
                    statisticsDTO = selectEquipmentViolationConditionsDateThirty(monitoringEquipmentNo);
                }
            }else{//自定义
                if(wsTowerCraneMonitoringStatisticsDTO.getConditionsDate() != null){
                    statisticsDTO = selectEquipmentViolationConditionsDate(monitoringEquipmentNo,wsTowerCraneMonitoringStatisticsDTO.getConditionsDate());
                }
            }
            WsTowerCraneMonitoringStatisticsDTO monitoringStatisticsDTO = new WsTowerCraneMonitoringStatisticsDTO();
            if(statisticsDTO != null){
                monitoringStatisticsDTO.setViolationsNumber(statisticsDTO.getViolationsNumber());
                monitoringStatisticsDTO.setViolationsRate(statisticsDTO.getViolationsRate());
            }else{
                monitoringStatisticsDTO.setViolationsNumber(0);
                monitoringStatisticsDTO.setViolationsRate("0.0");
            }
            monitoringStatisticsDTO.setTowerCraneEquipmentName(equipmentList.get(i).getEquipmentName());
            statisticsDTOList.add(monitoringStatisticsDTO);
        }
        return statisticsDTOList;
    }

    private WsTowerCraneMonitoringStatisticsDTO selectEquipmentViolationConditionsDateThirty(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringStatisticsDTO statisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectEquipmentViolationConditionsDateThirty(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return statisticsDTO;
    }

    public WsTowerCraneMonitoringStatisticsDTO selectEquipmentViolationConditionsDateSeven(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringStatisticsDTO statisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectEquipmentViolationConditionsDateSeven(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return statisticsDTO;
    }

    public WsTowerCraneMonitoringStatisticsDTO selectEquipmentViolationConditionsDate(String monitoringEquipmentNo, String conditionsDate) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringStatisticsDTO statisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectEquipmentViolationConditionsDate(monitoringEquipmentNo,conditionsDate);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return statisticsDTO;
    }

    /**
     * 设备工作记录
     * @param wsTowerCraneMonitoringStatisticsDTO
     * @return
     */
    @Override
    public List<WsTowerCraneMonitoringStatisticsDTO> selectEquipmentWorkRecord(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO) {
        List<WsTowerCraneMonitoringRealtimeDTO> equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentViolationList(wsTowerCraneMonitoringStatisticsDTO.getProjectId());
        List<WsTowerCraneMonitoringStatisticsDTO> statisticsDTOList = new ArrayList<WsTowerCraneMonitoringStatisticsDTO>();
        if(equipmentList.size() > 0) {
            for (int i = 0; i < equipmentList.size(); i++) {
                WsTowerCraneMonitoringStatisticsDTO statisticsDTO = null;
                String monitoringEquipmentNo = equipmentList.get(i).getMonitoringEquipmentNo();
                if (wsTowerCraneMonitoringStatisticsDTO.getDays() != null) {
                    if (wsTowerCraneMonitoringStatisticsDTO.getDays() == 7) {//7
                        statisticsDTO = selectEquipmentWorkRecordSeven(monitoringEquipmentNo);
                    } else {//30
                        statisticsDTO = selectEquipmentWorkRecordThirty(monitoringEquipmentNo);
                    }
                } else {//自定义
                    if (wsTowerCraneMonitoringStatisticsDTO.getConditionsDate() != null) {
                        statisticsDTO = selectEquipmentWorkRecordData(monitoringEquipmentNo, wsTowerCraneMonitoringStatisticsDTO.getConditionsDate());
                    }
                }
                if (statisticsDTO != null) {
                    WsTowerCraneMonitoringStatisticsDTO monitoringStatisticsDTO = new WsTowerCraneMonitoringStatisticsDTO();
                    if(StringUtils.isNotEmpty(equipmentList.get(i).getEquipmentName())){
                        monitoringStatisticsDTO.setTowerCraneEquipmentName(equipmentList.get(i).getEquipmentName()); //设备名称
                    }else{
                        monitoringStatisticsDTO.setTowerCraneEquipmentName(""); //设备名称
                    }
                    int weight = 0;
                    if(statisticsDTO.getLiftingWeight() != null){
                        weight = Integer.parseInt(statisticsDTO.getLiftingWeight()) / 1000;
                    }
                    DecimalFormat df = new DecimalFormat("#.00");
                    String liftingWeight = df.format(weight);
                    if(liftingWeight.equals(".00")){
                        monitoringStatisticsDTO.setLiftingWeight("0.00"); //吊重
                    }else{
                        monitoringStatisticsDTO.setLiftingWeight(liftingWeight); //吊重
                    }
                    monitoringStatisticsDTO.setHoistingTimes(statisticsDTO.getHoistingTimes());//吊次
                    int hours = 0;
                    if(statisticsDTO.getWorkingHours() != null){
                        hours = Integer.parseInt(statisticsDTO.getWorkingHours()) / 60;
                    }
                    String workingHours = df.format(hours);
                    if(workingHours.equals(".00")){
                        monitoringStatisticsDTO.setWorkingHours("0.00"); //工作时长
                    }else{
                        monitoringStatisticsDTO.setWorkingHours(workingHours); //工作时长
                    }
                    if(statisticsDTO.getLiftingDistance() != null){
                        monitoringStatisticsDTO.setLiftingDistance(statisticsDTO.getLiftingDistance());
                    }else{
                        monitoringStatisticsDTO.setLiftingDistance("0");
                    }
                    statisticsDTOList.add(monitoringStatisticsDTO);
                }
            }
        }
        return statisticsDTOList;
    }


    public WsTowerCraneMonitoringStatisticsDTO selectEquipmentWorkRecordData(String monitoringEquipmentNo, String conditionsDate) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringStatisticsDTO statisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectEquipmentWorkRecordData(monitoringEquipmentNo,conditionsDate);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return statisticsDTO;
    }

    public WsTowerCraneMonitoringStatisticsDTO selectEquipmentWorkRecordThirty(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringStatisticsDTO statisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectEquipmentWorkRecordThirty(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return statisticsDTO;
    }

    public WsTowerCraneMonitoringStatisticsDTO selectEquipmentWorkRecordSeven(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringStatisticsDTO statisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectEquipmentWorkRecordSeven(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return statisticsDTO;
    }


    /**
     *  监控设备分析/设备违章分析
     * @param wsTowerCraneMonitoringStatisticsDTO
     * @return
     */
    @Override
    public WsTowerCraneMonitoringStatisticsDTO selectMonitoringEquipmentOrViolation(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO) {
        //根据公司id去查询 该公司下所有项目；
        WsTowerCraneMonitoringStatisticsDTO statisticsDTO = new WsTowerCraneMonitoringStatisticsDTO();
        Integer[] projectIds = null;
        List<SysEnterprise> listTree= sysDeptService.selectOrgListByOrgId(wsTowerCraneMonitoringStatisticsDTO.getCompanyId().longValue());
        if(listTree.size() > 0) {
            LinkedList<Integer> linkedlists = new LinkedList<Integer>();
            for (int i = 0; i < listTree.size(); i++) {
                linkedlists.add(listTree.get(i).getEnterpriseId().intValue());
            }
            projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);
            //判断条件 companyId 不为空，根据其中条件 查询项目id集合并转成Integer[] 数组；

            statisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectEquipmentBePresent(projectIds,wsTowerCraneMonitoringStatisticsDTO.getConditionsDate());//在场设备数，监控设备数
            List<WsTowerCraneMonitoringRealtimeDTO> equipmentList = wsTowerCraneEquipmentMonitorMapper.selectMonitoringEquipmentOrViolation(projectIds,wsTowerCraneMonitoringStatisticsDTO.getConditionsDate());
            if (equipmentList.size() > 0) {
                LinkedList<String> linkedlist = new LinkedList<String>();
                for (int k = 0; k < equipmentList.size(); k++) {
                    linkedlist.add(equipmentList.get(k).getMonitoringEquipmentNo());
                }
                String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                //WsTowerCraneMonitoringStatisticsDTO monitoringStatisticsDTO = selectMonitoringEquipmentNosOrConditionsDate(monitoringEquipmentNos);
                double onLineRate = equipmentList.size() / Integer.parseInt(statisticsDTO.getTonitoringEquipmentNum()) * 100;
                //statisticsDTO.setMonitorOnLineNum(monitoringStatisticsDTO.getMonitorOnLineNum());//在线数
                BigDecimal bgs = new BigDecimal(onLineRate);
                double fs = bgs.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                statisticsDTO.setMonitorOnLineRate(String.valueOf(fs));//使用率

                WsTowerCraneMonitoringStatisticsDTO monitoring = selectMonitoringEquipmentNoViolation(monitoringEquipmentNos,wsTowerCraneMonitoringStatisticsDTO.getConditionsDate());
                if(monitoring.getEquipmentViolationNum() != null){
                    statisticsDTO.setEquipmentViolationNum(monitoring.getEquipmentViolationNum());//设备违章次数
                }else{
                    monitoring.setEquipmentViolationNum("0");
                }
                if(monitoring.getEquipmentHoistingNum() != null){
                    statisticsDTO.setEquipmentHoistingNum(monitoring.getEquipmentHoistingNum());//设备吊装次数
                }else{
                    monitoring.setEquipmentHoistingNum("0");
                }
                if(Integer.parseInt(monitoring.getEquipmentViolationNum()) > 0 && Integer.parseInt(monitoring.getEquipmentHoistingNum()) >0 ){
                    float a = Float.parseFloat(monitoring.getEquipmentViolationNum());
                    float b = Float.parseFloat(monitoring.getEquipmentHoistingNum());
                    double violationRate = a / b * 100;
                    BigDecimal bg = new BigDecimal(violationRate);
                    double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    statisticsDTO.setEquipmentViolationRate(String.valueOf(f1));//违章率
                }else{
                    statisticsDTO.setEquipmentViolationRate("0.0");//违章率
                }
            }
        }
        return statisticsDTO;
    }

    public WsTowerCraneMonitoringStatisticsDTO selectMonitoringEquipmentNoViolation(String[] monitoringEquipmentNos, String conditionsDate) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringStatisticsDTO statisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectMonitoringEquipmentNoViolation(monitoringEquipmentNos,conditionsDate);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return statisticsDTO;
    }

    public WsTowerCraneMonitoringStatisticsDTO selectMonitoringEquipmentNosOrConditionsDate(String[] monitoringEquipmentNos) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringStatisticsDTO statisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectMonitoringEquipmentNosOrConditionsDate(monitoringEquipmentNos);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return statisticsDTO;
    }

    /**
     * 项目违章占比-自定义
     * @param wsTowerCraneMonitoringStatisticsDTO
     * @return
     */
    @Override
    public WsTowerCraneMonitoringStatisticsDTO selectProjectViolationProportionCustom(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO) {
        List<WsTowerCraneMonitoringRealtimeDTO> equipmentList = new ArrayList<WsTowerCraneMonitoringRealtimeDTO>();
        List<WsTowerCraneMonitoringStatisticsDTO> statisticsDTO = new ArrayList<WsTowerCraneMonitoringStatisticsDTO>();
        List<WsTowerCraneMonitoringStatisticsDTO> statisticsDTOs = new ArrayList<WsTowerCraneMonitoringStatisticsDTO>();
        WsTowerCraneMonitoringStatisticsDTO monitoringStatistics = new WsTowerCraneMonitoringStatisticsDTO();
        //根据公司id去查询 该公司下所有项目；
        Integer[] projectIds = null;
        //根据公司id去查询 该公司下所有项目；
        //判断条件 companyId 不为空，根据其中条件 查询项目id集合并转成Integer[] 数组；
        List<SysEnterprise> listTree= sysDeptService.selectOrgListByOrgId(wsTowerCraneMonitoringStatisticsDTO.getCompanyId().longValue());
        if(listTree.size() > 0) {
            LinkedList<Integer> linkedlists = new LinkedList<Integer>();
            for (int i = 0; i < listTree.size(); i++) {
                linkedlists.add(listTree.get(i).getEnterpriseId().intValue());
            }
            projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);

            equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoList(projectIds);
            if (equipmentList.size() > 0) {
                LinkedList<String> linkedlist = new LinkedList<String>();
                for (int i = 0; i < equipmentList.size(); i++) {
                    linkedlist.add(equipmentList.get(i).getMonitoringEquipmentNo());
                }
                String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                statisticsDTO = selectQueryIllegalEquipmentSameDay(monitoringEquipmentNos, wsTowerCraneMonitoringStatisticsDTO.getConditionsDate());
                if (statisticsDTO.size() > 0) {
                    LinkedList<String> linkedlist2 = new LinkedList<String>();
                    for (int i = 0; i < statisticsDTO.size(); i++) {
                        linkedlist2.add(statisticsDTO.get(i).getMonitoringEquipmentNo());
                    }
                    String[] quipmentNos = linkedlist2.toArray(new String[linkedlist2.size()]);
                    statisticsDTOs = wsTowerCraneEquipmentMonitorMapper.selectProjectIllegalThirty(quipmentNos);
                    int projectIllegalNum = statisticsDTOs.size();
                    monitoringStatistics.setProportionProjectViolation(String.valueOf(projectIllegalNum));
                }
            }
            monitoringStatistics.setProjectNumber(listTree.size());
        }

       /* WsTowerCraneMonitoringStatisticsDTO monitoringStatisticsDTO = new WsTowerCraneMonitoringStatisticsDTO();
        //根据公司id去查询 该公司下所有项目；
        Integer[] projectIds = null;
        List<SysEnterprise> listTree= sysDeptService.selectOrgListByOrgId(wsTowerCraneMonitoringStatisticsDTO.getCompanyId().longValue());
        if(listTree.size() > 0) {
            LinkedList<Integer> linkedlists = new LinkedList<Integer>();
            for (int i = 0; i < listTree.size(); i++) {
                linkedlists.add(listTree.get(i).getEnterpriseId().intValue());
            }
            projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);
            //判断条件 companyId 不为空，根据其中条件 查询项目id集合并转成Integer[] 数组；
            List<WsTowerCraneMonitoringRealtimeDTO> equipmentList = wsTowerCraneEquipmentMonitorMapper.selectMonitoringEquipmentOrViolation(projectIds,wsTowerCraneMonitoringStatisticsDTO.getConditionsDate());
            if (equipmentList.size() > 0) {
                LinkedList<String> linkedlist = new LinkedList<String>();
                for (int i = 0; i < equipmentList.size(); i++) {
                    linkedlist.add(equipmentList.get(i).getMonitoringEquipmentNo());
                }
                String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                List<WsTowerCraneMonitoringStatisticsDTO> statisticsDTOList = selectQueryIllegalEquipmentSameDay(monitoringEquipmentNos, wsTowerCraneMonitoringStatisticsDTO.getConditionsDate());
                if (statisticsDTOList.size() > 0) {
                    LinkedList<String> linkedlist2 = new LinkedList<String>();
                    for (int i = 0; i < statisticsDTOList.size(); i++) {
                        linkedlist2.add(statisticsDTOList.get(i).getMonitoringEquipmentNo());
                    }
                    String[] quipmentNos = linkedlist2.toArray(new String[linkedlist2.size()]);
                    List<WsTowerCraneMonitoringStatisticsDTO> statisticsDTOLists = wsTowerCraneEquipmentMonitorMapper.selectProjectIllegalSameDay(quipmentNos);
                    int projectIllegalNum = statisticsDTOLists.size();
                    monitoringStatisticsDTO.setProportionProjectViolation(String.valueOf(projectIllegalNum));//违章项目占比
                }else{
                    monitoringStatisticsDTO.setProportionProjectViolation("0");//违章项目占比
                }
            }else{
                monitoringStatisticsDTO.setProportionProjectViolation("0");//违章项目占比
            }
            monitoringStatisticsDTO.setProjectNumber(listTree.size());*/

        return monitoringStatistics;
    }

    public List<WsTowerCraneMonitoringStatisticsDTO> selectQueryIllegalEquipmentSameDay(String[] monitoringEquipmentNos, String conditionsDate) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsTowerCraneMonitoringStatisticsDTO> statisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectQueryIllegalEquipmentSameDay(monitoringEquipmentNos,conditionsDate);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return statisticsDTO;
    }


    @Override
    public List<WsTowerCraneMonitoringStatisticsDTO> selectRankingOfViolationsCustom(WsTowerCraneMonitoringStatisticsDTO wsTowerCraneMonitoringStatisticsDTO) {
        List<WsTowerCraneMonitoringStatisticsDTO> towerCraneMonitoringStatisticsList = new ArrayList<WsTowerCraneMonitoringStatisticsDTO>();
        //根据公司id 条件 查询 该公司下所有项目
        List<SysEnterprise> listTree= sysDeptService.selectOrgListByOrgId(wsTowerCraneMonitoringStatisticsDTO.getCompanyId().longValue());
        if(listTree.size() > 0) {
            for (int i = 0; i < listTree.size(); i++) {
                Integer projectId = listTree.get(i).getEnterpriseId().intValue();
                String projectName = listTree.get(i).getEnterpriseName();
                List<WsTowerCraneMonitoringRealtimeDTO> equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoListByProjectId(projectId);
                if (equipmentList.size() > 0) {
                    LinkedList<String> linkedlist = new LinkedList<String>();
                    for (int k = 0; k < equipmentList.size(); k++) {
                        linkedlist.add(equipmentList.get(k).getMonitoringEquipmentNo());
                    }
                    String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                    WsTowerCraneMonitoringStatisticsDTO statisticsDTO = selectEquipmentNoListByProjectIdCustom(monitoringEquipmentNos, wsTowerCraneMonitoringStatisticsDTO.getConditionsDate());
                    if(statisticsDTO.getProjectNumber() > 0){
                        WsTowerCraneMonitoringStatisticsDTO monitoringStatisticsDTO = new WsTowerCraneMonitoringStatisticsDTO();
                        monitoringStatisticsDTO.setProjectName(projectName);
                        monitoringStatisticsDTO.setProjectNumber(statisticsDTO.getProjectNumber());
                        towerCraneMonitoringStatisticsList.add(monitoringStatisticsDTO);
                    }
                }
            }
        }
        return towerCraneMonitoringStatisticsList;
    }

    public WsTowerCraneMonitoringStatisticsDTO selectEquipmentNoListByProjectIdCustom(String[] monitoringEquipmentNos, String conditionsDate) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneMonitoringStatisticsDTO statisticsDTO = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoListByProjectIdCustom(monitoringEquipmentNos,conditionsDate);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return statisticsDTO;
    }

    /**
     * 设备详情
     * @param wsTowerCraneEquipmentDetailsDTO
     * @return
     */
    @Override
    public List<WsTowerCraneEquipmentDetailsDTO> selectDeviceDetails(WsTowerCraneEquipmentDetailsDTO wsTowerCraneEquipmentDetailsDTO) {

        List<WsTowerCraneEquipmentDetailsDTO> detailsDTOList = new ArrayList<WsTowerCraneEquipmentDetailsDTO>();
        //根据公司id 条件 查询 该公司下所有项目
        List<SysEnterprise> list= sysDeptService.selectOrgListByOrgId(wsTowerCraneEquipmentDetailsDTO.getCompanyId().longValue());
        for (int i = 0; i < list.size(); i++) {
            Integer projectId = list.get(i).getEnterpriseId().intValue();
            String projectName = list.get(i).getEnterpriseName();
            WsTowerCraneEquipmentDetailsDTO equipmentDetailsDTO = wsTowerCraneEquipmentMonitorMapper.selectDeviceDetailsByProjectId(projectId);
            List<WsTowerCraneMonitoringRealtimeDTO> equipmentList = wsTowerCraneEquipmentMonitorMapper.selectEquipmentNoListByProjectId(projectId);
            if (equipmentList.size() > 0) {
                LinkedList<String> linkedlist = new LinkedList<String>();
                for (int k = 0; k < equipmentList.size(); k++) {
                    linkedlist.add(equipmentList.get(k).getMonitoringEquipmentNo());
                }
                String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                WsTowerCraneMonitoringStatisticsDTO monitoringStatisticsDTO = selectMonitoringEquipmentNosOrConditionsDate(monitoringEquipmentNos);
                WsTowerCraneEquipmentDetailsDTO towerCraneEquipmentDetailsDTO = new WsTowerCraneEquipmentDetailsDTO();
                if(monitoringStatisticsDTO != null){
                   if(monitoringStatisticsDTO.getMonitorOnLineNum() != null && equipmentDetailsDTO.getTowerCraneMonitoringEquipmentNum() != null){
                       double onLineRate = Double.parseDouble(monitoringStatisticsDTO.getMonitorOnLineNum()) / Double.parseDouble(equipmentDetailsDTO.getTowerCraneMonitoringEquipmentNum()) * 100;
                       DecimalFormat df = new DecimalFormat("#.00");
                       String monitorOnLineRate = df.format(onLineRate);
                       towerCraneEquipmentDetailsDTO.setMonitoringOnlineRate(monitorOnLineRate);//在线率
                       towerCraneEquipmentDetailsDTO.setMonitoringOnlineNum(monitoringStatisticsDTO.getMonitorOnLineNum());//在线数
                   }else{
                       towerCraneEquipmentDetailsDTO.setMonitoringOnlineRate("0.00");//在线率
                       towerCraneEquipmentDetailsDTO.setMonitoringOnlineNum("0");//在线数
                   }
                }else{
                    towerCraneEquipmentDetailsDTO.setMonitoringOnlineRate("0.00");//在线率
                    towerCraneEquipmentDetailsDTO.setMonitoringOnlineNum("0");//在线数
                }
                towerCraneEquipmentDetailsDTO.setCompanyOrprojectName(projectName);//名称
                towerCraneEquipmentDetailsDTO.setTowerCraneEquipmentNum(equipmentDetailsDTO.getTowerCraneEquipmentNum());//塔吊数
                if(equipmentDetailsDTO.getTowerCraneMonitoringEquipmentNum() == null){
                    equipmentDetailsDTO.setTowerCraneMonitoringEquipmentNum("0");
                }
                towerCraneEquipmentDetailsDTO.setTowerCraneMonitoringEquipmentNum(equipmentDetailsDTO.getTowerCraneMonitoringEquipmentNum());//监控设备数
                detailsDTOList.add(towerCraneEquipmentDetailsDTO);
            }
        }
        return detailsDTOList;
    }

    /**
     * 违章详情
     * @param wsTowerCraneEquipmentViolationDetailsDTO
     * @return
     */
    @Override
    public List<WsTowerCraneEquipmentViolationDetailsDTO> selectViolationDetails(WsTowerCraneEquipmentViolationDetailsDTO wsTowerCraneEquipmentViolationDetailsDTO) {
        List<WsTowerCraneEquipmentViolationDetailsDTO> equipmentViolationDetailsDTOList = new ArrayList<WsTowerCraneEquipmentViolationDetailsDTO>();

        //根据公司id去查询 该公司下所有项目；
        Integer[] projectIds = null;
        List<SysEnterprise> listTree= sysDeptService.selectOrgListByOrgId(wsTowerCraneEquipmentViolationDetailsDTO.getCompanyId().longValue());
        if(listTree.size() > 0) {
            LinkedList<Integer> linkedlists = new LinkedList<Integer>();
            for (int i = 0; i < listTree.size(); i++) {
                linkedlists.add(listTree.get(i).getEnterpriseId().intValue());
            }
            projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);

            List<WsTowerCraneEquipmentViolationDetailsDTO> violationDetailsDTOlist = wsTowerCraneEquipmentMonitorMapper.selectViolationDetailsList(wsTowerCraneEquipmentViolationDetailsDTO, projectIds);

            List<WsTowerCraneMonitoringRealtimeDTO> realtimeDTOviolationContentList = new ArrayList<WsTowerCraneMonitoringRealtimeDTO>();
            if (violationDetailsDTOlist.size() > 0) {
                for (int i = 0; i < violationDetailsDTOlist.size(); i++) {
                    if (violationDetailsDTOlist.get(i).getMonitoringEquipmentNo() != null && violationDetailsDTOlist.get(i).getMonitoringEquipmentNo() != "") {
                        realtimeDTOviolationContentList = selectViolationContent(violationDetailsDTOlist.get(i).getMonitoringEquipmentNo(),wsTowerCraneEquipmentViolationDetailsDTO.getStartTime(),wsTowerCraneEquipmentViolationDetailsDTO.getEndTime());
                        if (realtimeDTOviolationContentList.size() > 0) {
                            for (int k = 0; k < realtimeDTOviolationContentList.size(); k++) {
                                WsTowerCraneEquipmentViolationDetailsDTO violationDetailsDTO =new WsTowerCraneEquipmentViolationDetailsDTO();
                                violationDetailsDTO.setProjectName(violationDetailsDTOlist.get(i).getProjectName());
                                violationDetailsDTO.setPropertyRightRecordNo(violationDetailsDTOlist.get(i).getPropertyRightRecordNo());
                                violationDetailsDTO.setEquipmentName(violationDetailsDTOlist.get(i).getEquipmentName());
                                violationDetailsDTO.setMonitoringEquipment(violationDetailsDTOlist.get(i).getMonitoringEquipmentNo());
                                violationDetailsDTO.setCompanyName(violationDetailsDTOlist.get(i).getCompanyName());
                                violationDetailsDTO.setViolationTime(realtimeDTOviolationContentList.get(k).getViolationTime());
                                violationDetailsDTO.setViolationContent(realtimeDTOviolationContentList.get(k).getViolationContent());
                                equipmentViolationDetailsDTOList.add(violationDetailsDTO);
                            }
                        }
                    }
                }
            }
        }
        return equipmentViolationDetailsDTOList;
    }

    @Override
    public List<WsTowerCraneEquipmentViolationDetailsDTO> selectViolationDetailsProject(WsTowerCraneEquipmentViolationDetailsDTO wsTowerCraneEquipmentViolationDetailsDTO) {
        List<WsTowerCraneEquipmentViolationDetailsDTO> violationDetailsDTOlist = wsTowerCraneEquipmentMonitorMapper.selectViolationDetailsProjectList(wsTowerCraneEquipmentViolationDetailsDTO);
        List<WsTowerCraneEquipmentViolationDetailsDTO> equipmentViolationDetailsDTOList = new ArrayList<WsTowerCraneEquipmentViolationDetailsDTO>();
        List<WsTowerCraneMonitoringRealtimeDTO> realtimeDTOviolationContentList = new ArrayList<WsTowerCraneMonitoringRealtimeDTO>();
        if(violationDetailsDTOlist.size() > 0){
            for (int i = 0; i < violationDetailsDTOlist.size(); i++) {
                if(violationDetailsDTOlist.get(i).getMonitoringEquipmentNo() != null && violationDetailsDTOlist.get(i).getMonitoringEquipmentNo() != ""){
                    realtimeDTOviolationContentList = selectViolationContent(violationDetailsDTOlist.get(i).getMonitoringEquipmentNo(),"","");
                    if(realtimeDTOviolationContentList.size() > 0) {
                        for (int k = 0; k < realtimeDTOviolationContentList.size(); k++) {
                            WsTowerCraneEquipmentViolationDetailsDTO violationDetailsDTO =new WsTowerCraneEquipmentViolationDetailsDTO();
                            violationDetailsDTO.setProjectName(violationDetailsDTOlist.get(i).getProjectName());
                            violationDetailsDTO.setPropertyRightRecordNo(violationDetailsDTOlist.get(i).getPropertyRightRecordNo());
                            violationDetailsDTO.setEquipmentName(violationDetailsDTOlist.get(i).getEquipmentName());
                            violationDetailsDTO.setMonitoringEquipment(violationDetailsDTOlist.get(i).getMonitoringEquipmentNo());
                            violationDetailsDTO.setCompanyName(violationDetailsDTOlist.get(i).getCompanyName());
                            violationDetailsDTO.setViolationTime(realtimeDTOviolationContentList.get(k).getViolationTime());
                            violationDetailsDTO.setViolationContent(realtimeDTOviolationContentList.get(k).getViolationContent());
                            equipmentViolationDetailsDTOList.add(violationDetailsDTO);
                        }
                    }
                }
            }
        }
        return equipmentViolationDetailsDTOList;
    }

    @Override
    public List<WsTowerCraneEquipmentWorkDetailsDTO> selectWorkDetailsProject(WsTowerCraneEquipmentWorkDetailsDTO wsTowerCraneEquipmentWorkDetailsDTO) {
        List<WsTowerCraneEquipmentWorkDetailsDTO> workDetailsDTOlist = wsTowerCraneEquipmentMonitorMapper.selectWorkDetailsProjectList(wsTowerCraneEquipmentWorkDetailsDTO);
        List<WsTowerCraneEquipmentWorkDetailsDTO> workDetailslist = new ArrayList<WsTowerCraneEquipmentWorkDetailsDTO>();
        for (int i = 0; i < workDetailsDTOlist.size(); i++) {
            String monitoringEquipmentNo = workDetailsDTOlist.get(i).getMonitoringEquipmentNo();
            List<WsTowerCraneEquipmentWorkDetailsDTO> workList = selectTowerCraneEquipmentWorkDetails(monitoringEquipmentNo);
            if(workList.size() > 0){
                for (int k = 0; k < workList.size(); k++){
                    WsTowerCraneEquipmentWorkDetailsDTO workDTO =new WsTowerCraneEquipmentWorkDetailsDTO();
                    workDTO.setEquipmentName(workDetailsDTOlist.get(i).getEquipmentName()); //设备名称
                    double weight = Double.parseDouble(workList.get(k).getLiftingWeight()) * 0.001;
                    NumberFormat df = NumberFormat.getNumberInstance();
                    df.setMaximumFractionDigits(2);
                    String liftingWeight = df.format(weight);
                    if(liftingWeight.equals("0")){
                        workDTO.setLiftingWeight("0.00"); //吊重
                    }else{
                        workDTO.setLiftingWeight(liftingWeight); //吊重
                    }
                    double hours = Double.parseDouble(workList.get(k).getWorkingHours()) * 0.6;
                    String workingHours = df.format(hours);
                    if(workingHours.equals("0")){
                        workDTO.setWorkingHours("0.00"); //工作时长
                    }else{
                        workDTO.setWorkingHours(workingHours); //工作时长
                    }
                    workDTO.setLiftingDistance(workList.get(k).getLiftingDistance());
                    workDTO.setStartTime(workList.get(k).getStartTime());
                    workDetailslist.add(workDTO);
                }
            }
        }
        return workDetailslist; //数据有问题
    }

    public List<WsTowerCraneEquipmentWorkDetailsDTO> selectTowerCraneEquipmentWorkDetails(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsTowerCraneEquipmentWorkDetailsDTO> workDetailsDTO = wsTowerCraneEquipmentMonitorMapper.selectTowerCraneEquipmentWorkDetails(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return workDetailsDTO;
    }


    /* 塔机监控-项目级*/
    /*根据账号需要获取项目id*/

    /**
     *实时监控：在场塔吊数；在线率；今日违章
     * @param wsTowerCraneProjectRealTimeMonitoringDTO
     * @return
     */
    @Override
    public WsTowerCraneProjectRealTimeMonitoringDTO selectProjectRealTimeMonitoring(WsTowerCraneProjectRealTimeMonitoringDTO wsTowerCraneProjectRealTimeMonitoringDTO) {

        WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectTowerCraneOnSiteNumQuery(wsTowerCraneProjectRealTimeMonitoringDTO);
        List<WsTowerCraneProjectRealTimeMonitoringDTO> monitoringDTOList = wsTowerCraneProjectRealTimeMonitoringMapper.selectTowerCraneOnSiteNumQueryList(wsTowerCraneProjectRealTimeMonitoringDTO);
        if(monitoringDTOList.size() > 0) {
            LinkedList<String> linkedlist = new LinkedList<String>();
            for (int i = 0; i < monitoringDTOList.size(); i++) {
                linkedlist.add(monitoringDTOList.get(i).getMonitoringEquipmentNo());
            }
            String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
            WsTowerCraneProjectRealTimeMonitoringDTO realTimeMonitoringDTO = selectMonitoringEquipmentNosOnlineRate(monitoringEquipmentNos);
            int num = monitoringDTOList.size();
            double hours = Double.parseDouble(realTimeMonitoringDTO.getMonitoringOnlineNum()) / num * 100;
            NumberFormat df = NumberFormat.getNumberInstance();
            df.setMaximumFractionDigits(2);
            String monitoringOnlineRate = df.format(hours);//在线率

            WsTowerCraneProjectRealTimeMonitoringDTO projectrealTimeMonitoringDTO = selectMonitoringEquipmentNosViolationTodayNum(monitoringEquipmentNos);
            monitoringDTO.setMonitoringOnlineRate(monitoringOnlineRate);
            if(projectrealTimeMonitoringDTO != null){
                if(projectrealTimeMonitoringDTO.getViolationTodayNum() != null){
                    monitoringDTO.setViolationTodayNum(projectrealTimeMonitoringDTO.getViolationTodayNum());//今日违章
                }else{
                    monitoringDTO.setViolationTodayNum("0");//今日违章
                }
                if(projectrealTimeMonitoringDTO.getViolationRate() != null){
                    monitoringDTO.setViolationRate(projectrealTimeMonitoringDTO.getViolationRate());//违章率
                }else{
                    monitoringDTO.setViolationRate("0.00");//违章率
                }
            }else{
                monitoringDTO.setViolationTodayNum("0");//今日违章
                monitoringDTO.setViolationRate("0.00");//违章率
            }
        }else{
            monitoringDTO.setMonitoringOnlineRate("0.00");
            monitoringDTO.setViolationTodayNum("0");//今日违章
            monitoringDTO.setViolationRate("0.0");//违章率
        }
        return monitoringDTO;
    }

    public WsTowerCraneProjectRealTimeMonitoringDTO selectMonitoringEquipmentNosViolationTodayNum(String[] monitoringEquipmentNos) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneProjectRealTimeMonitoringDTO realTimeMonitoringDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectMonitoringEquipmentNosViolationTodayNum(monitoringEquipmentNos);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return realTimeMonitoringDTO;
    }

    public WsTowerCraneProjectRealTimeMonitoringDTO selectMonitoringEquipmentNosOnlineRate(String[] monitoringEquipmentNos) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneProjectRealTimeMonitoringDTO realTimeMonitoringDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectMonitoringEquipmentNosOnlineRate(monitoringEquipmentNos);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return realTimeMonitoringDTO;
    }

    /**
     *实时监控
     * @param wsTowerCraneProjectRealTimeMonitoringDTO
     * @return
     */
    @Override
    public List<WsTowerCraneProjectRealTimeMonitoringDTO> selectEquipmentRealtimeMonitoring(WsTowerCraneProjectRealTimeMonitoringDTO wsTowerCraneProjectRealTimeMonitoringDTO) {

        //查询塔吊设备信息
        List<WsTowerCraneProjectRealTimeMonitoringDTO> monitoringDTOList = wsTowerCraneProjectRealTimeMonitoringMapper.selectProjectTowerCraneList(wsTowerCraneProjectRealTimeMonitoringDTO);
        //查询项目场布图
        WsTowerCraneProjectRealTimeMonitoringDTO projectfieldLayout = wsTowerCraneProjectRealTimeMonitoringMapper.selectProjectfieldLayout(wsTowerCraneProjectRealTimeMonitoringDTO);
        //查询监控设备在线状态、监控设备预报警状态、今日总吊重、今日违章
        if(monitoringDTOList.size() > 0) {
            for (int i = 0; i < monitoringDTOList.size(); i++) {
                if(monitoringDTOList.get(i).getEquipmentSiteStatus().equals("1")){
                    if(StringUtils.isNotEmpty(monitoringDTOList.get(i).getMonitoringEquipmentNo())){
                        WsTowerCraneProjectMonitoringDetailsDTO monitoringDTOOnlineOffline = selectEquipmentOnlineStatusData(monitoringDTOList.get(i).getMonitoringEquipmentNo());
                        if(monitoringDTOOnlineOffline != null){
                            if(monitoringDTOOnlineOffline.getEquipmentOnlineStatus().equals("1")){
                                monitoringDTOList.get(i).setMonitoringOnlineStatus("在线");
                            }else{
                                monitoringDTOList.get(i).setMonitoringOnlineStatus("离线");
                            }
                        }
                    }else{
                        monitoringDTOList.get(i).setMonitoringOnlineStatus("未绑定");
                    }
                }else{
                    monitoringDTOList.get(i).setMonitoringOnlineStatus("离场");
                }
               if(StringUtils.isNotEmpty(monitoringDTOList.get(i).getMonitoringEquipmentNo())){
                    //查询今日吊重、今日违章
                   WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTOTotalLiftingWeightTodayOrTotalViolationToday = selectTowerCraneEquipmentWorkDetailsQuery(monitoringDTOList.get(i).getMonitoringEquipmentNo());
                   WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTOWarningStatusOrAlarmStatus = selectWarningStatusOrAlarmStatusQuery(monitoringDTOList.get(i).getMonitoringEquipmentNo());
                   monitoringDTOList.get(i).setHoistingToday(monitoringDTOTotalLiftingWeightTodayOrTotalViolationToday.getHoistingToday());//今日吊次
                   monitoringDTOList.get(i).setTotalViolationToday(monitoringDTOTotalLiftingWeightTodayOrTotalViolationToday.getTotalViolationToday());//今日总违章
                   if(monitoringDTOWarningStatusOrAlarmStatus != null){
                       monitoringDTOList.get(i).setWarningStatus(monitoringDTOWarningStatusOrAlarmStatus.getWarningStatus());//预警状态(0:正常，1：预警)
                       monitoringDTOList.get(i).setAlarmStatus(monitoringDTOWarningStatusOrAlarmStatus.getAlarmStatus());//报警状态(0：正常，1：报警)
                       monitoringDTOList.get(i).setLiftingWeight(monitoringDTOWarningStatusOrAlarmStatus.getLiftingWeight());
                   }else{
                       monitoringDTOList.get(i).setWarningStatus("");//预警状态(0:正常，1：预警)
                       monitoringDTOList.get(i).setAlarmStatus("");//报警状态(0：正常，1：报警)
                       monitoringDTOList.get(i).setLiftingWeight("");
                   }
               }
               //判断塔机人脸ID 不能为空
               if(StringUtils.isNotEmpty(monitoringDTOList.get(i).getFaceId())){
                   WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTOFaceNo = wsTowerCraneProjectRealTimeMonitoringMapper.selectTowerCraneEquipmentFaceNoQuery(monitoringDTOList.get(i).getFaceId());
                   if(monitoringDTOFaceNo != null){
                       WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTOIdCardNo = selectTowerCraneEquipmentIdCardNoQuery(monitoringDTOFaceNo.getFaceNo());
                       if(StringUtils.isNotEmpty(monitoringDTOIdCardNo.getIdCardNo())){
                           WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTOPhotoAddress = wsTowerCraneProjectRealTimeMonitoringMapper.selectTowerCraneEquipmentPhotoAddressQuery(monitoringDTOIdCardNo.getIdCardNo(),wsTowerCraneProjectRealTimeMonitoringDTO.getProjectId());
                           if(monitoringDTOPhotoAddress != null){
                               if(StringUtils.isNotEmpty(monitoringDTOPhotoAddress.getPhotoAddress())){
                                   monitoringDTOList.get(i).setPhotoAddress(monitoringDTOPhotoAddress.getPhotoAddress());//司机照片地址
                               }
                           }
                       }
                   }
               }
               if(projectfieldLayout != null){
                   monitoringDTOList.get(i).setFieldLayout(projectfieldLayout.getFieldLayout());//场布图图片地址
               }
            }
        }
        return monitoringDTOList;

    }


    public WsTowerCraneProjectRealTimeMonitoringDTO selectTowerCraneEquipmentWorkDetailsQuery(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTOTotalLiftingWeightTodayOrTotalViolationToday = wsTowerCraneProjectRealTimeMonitoringMapper.selectTowerCraneEquipmentWorkDetailsQuery(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return monitoringDTOTotalLiftingWeightTodayOrTotalViolationToday;
    }

    private WsTowerCraneProjectRealTimeMonitoringDTO selectWarningStatusOrAlarmStatusQuery(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTOWarningStatusOrAlarmStatus = wsTowerCraneProjectRealTimeMonitoringMapper.selectWarningStatusOrAlarmStatusQuery(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return monitoringDTOWarningStatusOrAlarmStatus;
    }

    private WsTowerCraneProjectRealTimeMonitoringDTO selectTowerCraneEquipmentIdCardNoQuery(String faceNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTOIdCardNo = wsTowerCraneProjectRealTimeMonitoringMapper.selectTowerCraneEquipmentIdCardNoQuery(faceNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return monitoringDTOIdCardNo;
    }


    /**
     * 塔机监控 - 项目 -监控详情页面
     * 监控详情
     * @param wsTowerCraneProjectMonitoringDetailsDTO
     * @return
     */
    @Override
    public WsTowerCraneProjectMonitoringDetailsDTO selectProjectMonitoringDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO) {
        WsTowerCraneProjectMonitoringDetailsDTO towerCraneDetails = new WsTowerCraneProjectMonitoringDetailsDTO();
        //根据towerCraneId查询塔吊信息
        WsTowerCraneProjectMonitoringDetailsDTO towerCraneInformationDetailsDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectProjectTowerCraneInformation(wsTowerCraneProjectMonitoringDetailsDTO);
        if (towerCraneInformationDetailsDTO != null) {
            towerCraneDetails.setHookVideoId(towerCraneInformationDetailsDTO.getHookVideoId());
            towerCraneDetails.setTowerCraneName(towerCraneInformationDetailsDTO.getTowerCraneName());
            towerCraneDetails.setRecordNo(towerCraneInformationDetailsDTO.getRecordNo());
            towerCraneDetails.setTowerCraneModel(towerCraneInformationDetailsDTO.getTowerCraneModel());
            towerCraneDetails.setPropertyUnit(towerCraneInformationDetailsDTO.getPropertyUnit());
            towerCraneDetails.setInstallUnit(towerCraneInformationDetailsDTO.getInstallUnit());

            //监控设备实时数据查询
            if (towerCraneInformationDetailsDTO.getMonitoringEquipmentNo() != null && towerCraneInformationDetailsDTO.getMonitoringEquipmentNo() != "") {
                WsTowerCraneProjectMonitoringDetailsDTO monitorrRealTimeDataDetailsDTO = selectProjectMonitorrRealTimeData(towerCraneInformationDetailsDTO.getMonitoringEquipmentNo());
                if (monitorrRealTimeDataDetailsDTO != null) {
                    double fd = Integer.parseInt(monitorrRealTimeDataDetailsDTO.getRanges()) * 0.1;
                    double zj = Integer.parseInt(monitorrRealTimeDataDetailsDTO.getCorner()) * 0.1;
                    double dg = Integer.parseInt(monitorrRealTimeDataDetailsDTO.getLiftingheight()) * 0.1;
                    double fs = Integer.parseInt(monitorrRealTimeDataDetailsDTO.getWindSpeed()) * 0.1;
                    double dz = Integer.parseInt(monitorrRealTimeDataDetailsDTO.getLiftingWeight()) * 0.001;
                    double yxzz = Integer.parseInt(monitorrRealTimeDataDetailsDTO.getAllowLiftingWeight()) * 0.001;
                    double qj = Integer.parseInt(monitorrRealTimeDataDetailsDTO.getAngle()) * 0.01;
                    NumberFormat df = NumberFormat.getNumberInstance();
                    df.setMaximumFractionDigits(1);
                    String ranges = df.format(fd);
                    String corner = df.format(zj);
                    String liftingheight = df.format(dg);
                    String windSpeed = df.format(fs);
                    String liftingWeight = df.format(dz);
                    String sllowLiftingWeight = df.format(yxzz);
                    String angle = df.format(qj);

                    towerCraneDetails.setRanges(ranges);
                    towerCraneDetails.setCorner(corner);
                    towerCraneDetails.setLiftingheight(liftingheight);
                    towerCraneDetails.setWindSpeed(windSpeed);
                    towerCraneDetails.setLiftingWeight(liftingWeight);
                    towerCraneDetails.setAllowLiftingWeight(sllowLiftingWeight);
                    towerCraneDetails.setAngle(angle);
                }
            }
            //判断塔机人脸ID 不能为空
            if (towerCraneInformationDetailsDTO.getFaceId() != null && towerCraneInformationDetailsDTO.getFaceId() != "") {
                WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTOFaceNo = wsTowerCraneProjectRealTimeMonitoringMapper.selectTowerCraneEquipmentFaceNoQuery(towerCraneInformationDetailsDTO.getFaceId());
                if (monitoringDTOFaceNo != null) {
                    if (monitoringDTOFaceNo.getFaceNo() != null) {
                        WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTOIdCardNo = selectTowerCraneEquipmentIdCardNoQuery(monitoringDTOFaceNo.getFaceNo());
                        if (monitoringDTOIdCardNo != null) {
                            if (monitoringDTOIdCardNo.getIdCardNo() != null && monitoringDTOIdCardNo.getIdCardNo() != "") {
                                WsTowerCraneProjectMonitoringDetailsDTO detailsDTODriverInformation = wsTowerCraneProjectRealTimeMonitoringMapper.selectTowerCraneDriverInformationQuery(monitoringDTOIdCardNo.getIdCardNo(), towerCraneInformationDetailsDTO.getProjectId());
                                if (detailsDTODriverInformation != null) {
                                    towerCraneDetails.setDriversName(detailsDTODriverInformation.getDriversName());
                                    towerCraneDetails.setIdNumber(detailsDTODriverInformation.getIdNumber());
                                    towerCraneDetails.setPhotoAddress(detailsDTODriverInformation.getPhotoAddress());
                                    towerCraneDetails.setIdentificationTime(monitoringDTOIdCardNo.getIdentificationTime());
                                    towerCraneDetails.setSpecialOperationNumber("");
                                }
                            }
                        }
                    }
                }
            }
            //监控设备实时数据最大最小值查询
            WsTowerCraneProjectMonitoringDetailsDTO monitorrRealTimeDataMaxMinDetailsDTO = selectProjectMonitorrRealTimeMaxMinData(towerCraneInformationDetailsDTO.getMonitoringEquipmentNo());
            if (monitorrRealTimeDataMaxMinDetailsDTO != null) {
                towerCraneDetails.setMaxRange(String.valueOf(Integer.parseInt(monitorrRealTimeDataMaxMinDetailsDTO.getMaxRange()) * 0.1));
                towerCraneDetails.setMinRange(monitorrRealTimeDataMaxMinDetailsDTO.getMinRange());
                towerCraneDetails.setMaxCorner(monitorrRealTimeDataMaxMinDetailsDTO.getMaxCorner());
                towerCraneDetails.setMinCorner(monitorrRealTimeDataMaxMinDetailsDTO.getMinCorner());
                towerCraneDetails.setMaxLiftingheight(String.valueOf(Integer.parseInt(monitorrRealTimeDataMaxMinDetailsDTO.getMaxLiftingheight()) * 0.1));
                towerCraneDetails.setMinLiftingheight(monitorrRealTimeDataMaxMinDetailsDTO.getMinLiftingheight());
                towerCraneDetails.setMaxWindSpeed(monitorrRealTimeDataMaxMinDetailsDTO.getMaxWindSpeed());
                towerCraneDetails.setMinWindSpeed(monitorrRealTimeDataMaxMinDetailsDTO.getMinWindSpeed());
                towerCraneDetails.setMaxLiftingWeight(monitorrRealTimeDataMaxMinDetailsDTO.getMaxLiftingWeight());
                towerCraneDetails.setMinLiftingWeight(monitorrRealTimeDataMaxMinDetailsDTO.getMinLiftingWeight());
                towerCraneDetails.setMaxAngle(monitorrRealTimeDataMaxMinDetailsDTO.getMaxAngle());
                towerCraneDetails.setMinAngle(monitorrRealTimeDataMaxMinDetailsDTO.getMinAngle());
            }

            //查询近期违章率；
            WsTowerCraneProjectMonitoringDetailsDTO realTimeDataDetailsDTOViolationRate = selectRecentViolationRateData(towerCraneInformationDetailsDTO.getMonitoringEquipmentNo());
            if (realTimeDataDetailsDTOViolationRate != null) {
                towerCraneDetails.setRecentViolationRate(realTimeDataDetailsDTOViolationRate.getRecentViolationRate());//近期违章率
                towerCraneDetails.setRecentViolationNum(realTimeDataDetailsDTOViolationRate.getRecentViolationNum());//近期违章次数
            }

            //昨日吊装次数；昨日吊装状态（60以下 空闲，60-80 正常， 80以上 忙碌）；昨日违章次数
            WsTowerCraneProjectMonitoringDetailsDTO realTimeDataDetailsDTOyesterdayHoisting = selectYesterdayHoistingData(towerCraneInformationDetailsDTO.getMonitoringEquipmentNo());
            if (realTimeDataDetailsDTOyesterdayHoisting != null) {
                int hoistingNum = Integer.parseInt(realTimeDataDetailsDTOyesterdayHoisting.getYesterdayHoistingNum());
                if (hoistingNum < 60) {
                    towerCraneDetails.setYesterdayHoistingState("空闲");
                } else if (hoistingNum >= 60 || hoistingNum <= 80) {
                    towerCraneDetails.setYesterdayHoistingState("正常");
                } else {
                    towerCraneDetails.setYesterdayHoistingState("忙碌");
                }
                towerCraneDetails.setYesterdayHoistingNum(realTimeDataDetailsDTOyesterdayHoisting.getYesterdayHoistingNum());
                towerCraneDetails.setYesterdayViolationNum(realTimeDataDetailsDTOyesterdayHoisting.getYesterdayViolationNum());
            }

            //昨日吊装次数；昨日违章次数
            WsTowerCraneProjectMonitoringDetailsDTO realTimeDataDetailsDTOtodayHoisting = selectTodayHoistingData(towerCraneInformationDetailsDTO.getMonitoringEquipmentNo());
            if (realTimeDataDetailsDTOtodayHoisting != null) {
                towerCraneDetails.setTodayHoistingNum(realTimeDataDetailsDTOtodayHoisting.getTodayHoistingNum());
                towerCraneDetails.setTodayViolationNum(realTimeDataDetailsDTOtodayHoisting.getTodayViolationNum());
            }

            if (towerCraneInformationDetailsDTO.getMonitoringEquipmentNo() != null && towerCraneInformationDetailsDTO.getMonitoringEquipmentNo() != "") {
                WsTowerCraneProjectMonitoringDetailsDTO monitorrForewarningDetailsDTO = selectProjectMonitorrForewarningData(towerCraneInformationDetailsDTO.getMonitoringEquipmentNo());
                if (monitorrForewarningDetailsDTO != null) {
                    towerCraneDetails.setHeightState(monitorrForewarningDetailsDTO.getHeightState());
                    towerCraneDetails.setRangeState(monitorrForewarningDetailsDTO.getRangeState());
                    towerCraneDetails.setRotationState(monitorrForewarningDetailsDTO.getRotationState());
                    towerCraneDetails.setWindSpeedState(monitorrForewarningDetailsDTO.getWindSpeedState());
                    towerCraneDetails.setTiltState(monitorrForewarningDetailsDTO.getTiltState());
                    towerCraneDetails.setOverweightState(monitorrForewarningDetailsDTO.getOverweightState());
                    if (monitorrForewarningDetailsDTO.getInterferenceStateUpper() == "2" || monitorrForewarningDetailsDTO.getInterferenceStateLower() == "2" || monitorrForewarningDetailsDTO.getInterferenceStateFront() == "2" ||
                            monitorrForewarningDetailsDTO.getInterferenceStateAfter() == "2" || monitorrForewarningDetailsDTO.getInterferenceStateLeft() == "2" || monitorrForewarningDetailsDTO.getInterferenceStateRight() == "2") {
                        towerCraneDetails.setInterferenceState("2");
                    } else if (monitorrForewarningDetailsDTO.getInterferenceStateUpper() == "1" || monitorrForewarningDetailsDTO.getInterferenceStateLower() == "1" || monitorrForewarningDetailsDTO.getInterferenceStateFront() == "1" ||
                            monitorrForewarningDetailsDTO.getInterferenceStateAfter() == "1" || monitorrForewarningDetailsDTO.getInterferenceStateLeft() == "1" || monitorrForewarningDetailsDTO.getInterferenceStateRight() == "1") {
                        towerCraneDetails.setInterferenceState("1");
                    } else {
                        towerCraneDetails.setInterferenceState("0");
                    }
                    if (monitorrForewarningDetailsDTO.getAntiCollisionStateUpper() == "2" || monitorrForewarningDetailsDTO.getAntiCollisionStateLower() == "2" || monitorrForewarningDetailsDTO.getAntiCollisionStateFront() == "2" ||
                            monitorrForewarningDetailsDTO.getAntiCollisionStateAfter() == "2" || monitorrForewarningDetailsDTO.getAntiCollisionStateLeft() == "2" || monitorrForewarningDetailsDTO.getAntiCollisionStateRight() == "2") {
                        towerCraneDetails.setAntiCollisionState("2");
                    } else if (monitorrForewarningDetailsDTO.getAntiCollisionStateUpper() == "1" || monitorrForewarningDetailsDTO.getAntiCollisionStateLower() == "1" || monitorrForewarningDetailsDTO.getAntiCollisionStateFront() == "1" ||
                            monitorrForewarningDetailsDTO.getAntiCollisionStateAfter() == "1" || monitorrForewarningDetailsDTO.getAntiCollisionStateLeft() == "1" || monitorrForewarningDetailsDTO.getAntiCollisionStateRight() == "1") {
                        towerCraneDetails.setAntiCollisionState("1");
                    } else {
                        towerCraneDetails.setAntiCollisionState("0");
                    }
                    towerCraneDetails.setUpdateDate(monitorrForewarningDetailsDTO.getUpdateDate());
                }
                WsTowerCraneProjectMonitoringDetailsDTO equipmentOnlineStatusDetailsDTO = selectEquipmentOnlineStatusData(towerCraneInformationDetailsDTO.getMonitoringEquipmentNo());
                if (equipmentOnlineStatusDetailsDTO != null) {
                    towerCraneDetails.setEquipmentOnlineStatus(equipmentOnlineStatusDetailsDTO.getEquipmentOnlineStatus());
                }
            }
        }
        return towerCraneDetails;
    }

    private WsTowerCraneProjectMonitoringDetailsDTO selectTodayHoistingData(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneProjectMonitoringDetailsDTO equipmentOnlineStatusDetailsDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectTodayHoistingData(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return equipmentOnlineStatusDetailsDTO;
    }

    private WsTowerCraneProjectMonitoringDetailsDTO selectYesterdayHoistingData(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneProjectMonitoringDetailsDTO equipmentOnlineStatusDetailsDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectYesterdayHoistingData(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return equipmentOnlineStatusDetailsDTO;
    }

    private WsTowerCraneProjectMonitoringDetailsDTO selectRecentViolationRateData(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneProjectMonitoringDetailsDTO equipmentOnlineStatusDetailsDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectRecentViolationRateData(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return equipmentOnlineStatusDetailsDTO;
    }

    public WsTowerCraneProjectMonitoringDetailsDTO selectEquipmentOnlineStatusData(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneProjectMonitoringDetailsDTO equipmentOnlineStatusDetailsDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectEquipmentOnlineStatusData(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return equipmentOnlineStatusDetailsDTO;
    }

    public WsTowerCraneProjectMonitoringDetailsDTO selectProjectMonitorrRealTimeData(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneProjectMonitoringDetailsDTO monitorrRealTimeDataDetailsDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectProjectMonitorrRealTimeData(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return monitorrRealTimeDataDetailsDTO;
    }
    public WsTowerCraneProjectMonitoringDetailsDTO selectProjectMonitorrRealTimeMaxMinData(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneProjectMonitoringDetailsDTO monitorrRealTimeDataMaxMinDetailsDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectProjectMonitorrRealTimeMaxMinData(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return monitorrRealTimeDataMaxMinDetailsDTO;
    }

    public WsTowerCraneProjectMonitoringDetailsDTO selectProjectMonitorrForewarningData(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneProjectMonitoringDetailsDTO monitorrForewarningDetailsDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectProjectMonitorrForewarningData(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return monitorrForewarningDetailsDTO;
    }


    /**
     * 吊钩视频
     * @param wsTowerCraneProjectMonitoringDetailsDTO
     * @return
     */
    @Override
    public WsTowerCraneProjectMonitoringDetailsDTO selectHookVideoDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO) {
        WsVideoChannelManagement videoChannelManagement = wsVideoChannelManagementMapper.selectWsVideoChannelManagementById(wsTowerCraneProjectMonitoringDetailsDTO.getHookVideoId());
        if(videoChannelManagement != null){
            Map<String, String> resultMap = YingshiVideoInterUtil.getLiveAddress(videoChannelManagement.getEquipmentSerialnumber(),videoChannelManagement.getChannelNumber());
            wsTowerCraneProjectMonitoringDetailsDTO.setHookVideoAddress(resultMap.get("hlsHd"));
        }
        return wsTowerCraneProjectMonitoringDetailsDTO;
    }

    /**
     * 工作记录
     * @param wsTowerCraneProjectMonitoringDetailsDTO
     * @return
     */
    @Override
    public List<WsTowerCraneProjectMonitoringDetailsDTO> selectEquipmentWorkCycleRecord(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO) {
        List<WsTowerCraneProjectMonitoringDetailsDTO> detailsDTOList = new ArrayList<WsTowerCraneProjectMonitoringDetailsDTO>();
        WsTowerCraneProjectMonitoringDetailsDTO towerCraneInformationDetailsDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectProjectTowerCraneInformation(wsTowerCraneProjectMonitoringDetailsDTO);
        if(towerCraneInformationDetailsDTO != null){
            if(wsTowerCraneProjectMonitoringDetailsDTO.getDays() == "1"){
                detailsDTOList = wsTowerCraneProjectRealTimeMonitoringMapper.selectEquipmentWorkCycleRecordOneList(towerCraneInformationDetailsDTO.getMonitoringEquipmentNo());
            }else{
                detailsDTOList = wsTowerCraneProjectRealTimeMonitoringMapper.selectEquipmentWorkCycleRecordfifteenList(towerCraneInformationDetailsDTO.getMonitoringEquipmentNo());
            }
        }
        return detailsDTOList;
    }

    /**
     * 工作记录详情
     * @param wsTowerCraneProjectMonitoringDetailsDTO
     * @return
     */
    @Override
    public List<WsTowerCraneProjectMonitoringDetailsDTO> selectEquipmentWorkCycleDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO) {
        List<WsTowerCraneProjectMonitoringDetailsDTO> detailsDTOList = new ArrayList<WsTowerCraneProjectMonitoringDetailsDTO>();
        WsTowerCraneProjectMonitoringDetailsDTO towerCraneInformationDetailsDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectProjectTowerCraneInformation(wsTowerCraneProjectMonitoringDetailsDTO);
        if(towerCraneInformationDetailsDTO != null){
            detailsDTOList = wsTowerCraneProjectRealTimeMonitoringMapper.selectEquipmentWorkCycleDetails(towerCraneInformationDetailsDTO.getMonitoringEquipmentNo(),wsTowerCraneProjectMonitoringDetailsDTO.getSearchTime());
        }
        return detailsDTOList;
    }

    /**
     * 司机历史数据
     * @param wsTowerCraneProjectMonitoringDetailsDTO
     * @return
     */
    @Override
    public List<WsTowerCraneProjectMonitoringDetailsDTO> selectEquipmentWorkDriverDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO) {
        List<WsTowerCraneProjectMonitoringDetailsDTO> detailsDTOList = new ArrayList<WsTowerCraneProjectMonitoringDetailsDTO>();
        WsTowerCraneProjectMonitoringDetailsDTO towerCraneInformationDetailsDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectProjectTowerCraneInformation(wsTowerCraneProjectMonitoringDetailsDTO);
        if(towerCraneInformationDetailsDTO != null){
            //判断塔机人脸ID 不能为空
            if(towerCraneInformationDetailsDTO.getFaceId() != null && towerCraneInformationDetailsDTO.getFaceId() != ""){
                WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTOFaceNo = wsTowerCraneProjectRealTimeMonitoringMapper.selectTowerCraneEquipmentFaceNoQuery(towerCraneInformationDetailsDTO.getFaceId());
                detailsDTOList = selectEquipmentWorkDriverInformation(monitoringDTOFaceNo.getFaceNo(),wsTowerCraneProjectMonitoringDetailsDTO.getSearchTime(),wsTowerCraneProjectMonitoringDetailsDTO.getDriversName());
                if(detailsDTOList.size() > 0){
                    for (int i = 0; i < detailsDTOList.size(); i++) {
                        if(detailsDTOList.get(i).getIdCardNo() != null && detailsDTOList.get(i).getIdCardNo() != ""){
                            WsTowerCraneProjectMonitoringDetailsDTO detailsDTODriverInformation = wsTowerCraneProjectRealTimeMonitoringMapper.selectTowerCraneDriverInformationQuery(detailsDTOList.get(i).getIdCardNo(),towerCraneInformationDetailsDTO.getProjectId());
                            if(detailsDTODriverInformation != null){
                                detailsDTOList.get(i).setPhotoAddress(detailsDTODriverInformation.getPhotoAddress());
                            }
                        }
                    }
                }
            }
        }
        return detailsDTOList;
    }

    public List<WsTowerCraneProjectMonitoringDetailsDTO> selectEquipmentWorkDriverInformation(String faceNo, String searchTime, String driversName) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsTowerCraneProjectMonitoringDetailsDTO> monitorrDriverInDetailsDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectEquipmentWorkDriverInformation(faceNo,searchTime,driversName);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return monitorrDriverInDetailsDTO;
    }

    /**
     * 告警记录；柱状图
     * @param wsTowerCraneProjectMonitoringDetailsDTO
     * @return
     */
    @Override
    public List<WsTowerCraneProjectMonitoringDetailsDTO> selectEquipmentWorkAlarmDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO) {
        List<WsTowerCraneProjectMonitoringDetailsDTO> detailsDTOList = new ArrayList<WsTowerCraneProjectMonitoringDetailsDTO>();
        WsTowerCraneProjectMonitoringDetailsDTO towerCraneInformationDetailsDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectProjectTowerCraneInformation(wsTowerCraneProjectMonitoringDetailsDTO);
        if(towerCraneInformationDetailsDTO != null){
            detailsDTOList =selectProjectMonitorrRealTimeAlarmData(towerCraneInformationDetailsDTO.getMonitoringEquipmentNo());
        }
        return detailsDTOList;
    }

    public  List<WsTowerCraneProjectMonitoringDetailsDTO> selectProjectMonitorrRealTimeAlarmData(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsTowerCraneProjectMonitoringDetailsDTO> detailsDTOList = wsTowerCraneProjectRealTimeMonitoringMapper.selectProjectMonitorrRealTimeAlarmData(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return detailsDTOList;
    }

    /**
     * 告警记录：今日告警 环形图
     * @param wsTowerCraneProjectMonitoringDetailsDTO
     * @return
     */
    @Override
    public WsTowerCraneProjectMonitoringDetailsDTO selectEquipmentWorkWarningTodayDetails(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO) {
        WsTowerCraneProjectMonitoringDetailsDTO towerCraneInformationDetailsDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectProjectTowerCraneInformation(wsTowerCraneProjectMonitoringDetailsDTO);
        WsTowerCraneProjectMonitoringDetailsDTO workWarningTodayDetails = new WsTowerCraneProjectMonitoringDetailsDTO();
        if(towerCraneInformationDetailsDTO != null){
            workWarningTodayDetails =selectProjectMonitorrWarningTodayData(towerCraneInformationDetailsDTO.getMonitoringEquipmentNo());
        }
        return workWarningTodayDetails;
    }

    public WsTowerCraneProjectMonitoringDetailsDTO selectProjectMonitorrWarningTodayData(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneProjectMonitoringDetailsDTO detailsDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectProjectMonitorrWarningTodayData(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return detailsDTO;
    }


    /**
     * 项目监控统计-按项目-7-30-自定义
     * 违章详情
     * @param wsTowerCraneProjectRealTimeMonitoringDTO
     * @return
     */
    @Override
    public List<WsTowerCraneProjectRealTimeMonitoringDTO> selectDetailsProjectViolation(WsTowerCraneProjectRealTimeMonitoringDTO wsTowerCraneProjectRealTimeMonitoringDTO) {
        List<WsTowerCraneProjectRealTimeMonitoringDTO> detailsProjectViolationMonitoringDTOList = new ArrayList<WsTowerCraneProjectRealTimeMonitoringDTO>();

        //查询塔吊设备信息
        List<WsTowerCraneProjectRealTimeMonitoringDTO> monitoringDTOList = wsTowerCraneProjectRealTimeMonitoringMapper.selectProjectTowerCraneDetailsList(wsTowerCraneProjectRealTimeMonitoringDTO);
        if(wsTowerCraneProjectRealTimeMonitoringDTO.getDays().equals("7")){
            if(monitoringDTOList.size() > 0) {
                for (int i = 0; i < monitoringDTOList.size(); i++) {
                    //判断塔机人脸ID 不能为空
                    WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTOIdCardNo = null;
                    if (monitoringDTOList.get(i).getFaceId() != null && monitoringDTOList.get(i).getFaceId() != "") {
                        WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTOFaceNo = wsTowerCraneProjectRealTimeMonitoringMapper.selectTowerCraneEquipmentFaceNoQuery(monitoringDTOList.get(i).getFaceId());
                        monitoringDTOIdCardNo = selectTowerCraneEquipmentNameQuery(monitoringDTOFaceNo.getFaceNo());
                    }
                    if(monitoringDTOList.get(i).getMonitoringEquipmentNo() != null && monitoringDTOList.get(i).getMonitoringEquipmentNo() != ""){
                        List<WsTowerCraneProjectRealTimeMonitoringDTO> detailsProjectViolationList = selectDetailsProjectViolationQuerySeven(monitoringDTOList.get(i).getMonitoringEquipmentNo());
                        if(detailsProjectViolationList.size() > 0) {
                            for (int k = 0; k < detailsProjectViolationList.size(); k++) {
                                WsTowerCraneProjectRealTimeMonitoringDTO detailsProjectViolationMonitoringDTO = new WsTowerCraneProjectRealTimeMonitoringDTO();
                                detailsProjectViolationMonitoringDTO.setEquipmentName(monitoringDTOList.get(i).getEquipmentName());
                                if(monitoringDTOIdCardNo != null){
                                    if (monitoringDTOIdCardNo.getDriverName() != null && monitoringDTOIdCardNo.getDriverName() != "") {
                                        detailsProjectViolationMonitoringDTO.setDriverName(monitoringDTOIdCardNo.getDriverName());
                                    }else{
                                        detailsProjectViolationMonitoringDTO.setDriverName("未知司机");
                                    }
                                }else{
                                    detailsProjectViolationMonitoringDTO.setDriverName("未知司机");
                                }
                                detailsProjectViolationMonitoringDTO.setViolationTime(detailsProjectViolationList.get(k).getViolationTime());
                                detailsProjectViolationMonitoringDTO.setViolationContent(detailsProjectViolationList.get(k).getViolationContent());
                                detailsProjectViolationMonitoringDTOList.add(detailsProjectViolationMonitoringDTO);
                            }
                        }
                    }
                }
            }
        }else if(wsTowerCraneProjectRealTimeMonitoringDTO.getDays().equals("30")){
            if(monitoringDTOList.size() > 0) {
                for (int i = 0; i < monitoringDTOList.size(); i++) {
                    //判断塔机人脸ID 不能为空
                    WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTOIdCardNo = null;
                    if (monitoringDTOList.get(i).getFaceId() != null && monitoringDTOList.get(i).getFaceId() != "") {
                        WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTOFaceNo = wsTowerCraneProjectRealTimeMonitoringMapper.selectTowerCraneEquipmentFaceNoQuery(monitoringDTOList.get(i).getFaceId());
                        monitoringDTOIdCardNo = selectTowerCraneEquipmentNameQuery(monitoringDTOFaceNo.getFaceNo());
                    }
                    if(monitoringDTOList.get(i).getMonitoringEquipmentNo() != null && monitoringDTOList.get(i).getMonitoringEquipmentNo() != ""){
                        List<WsTowerCraneProjectRealTimeMonitoringDTO> detailsProjectViolationList = selectDetailsProjectViolationQueryThirty(monitoringDTOList.get(i).getMonitoringEquipmentNo());
                        if(detailsProjectViolationList.size() > 0) {
                            for (int k = 0; k < detailsProjectViolationList.size(); k++) {
                                WsTowerCraneProjectRealTimeMonitoringDTO detailsProjectViolationMonitoringDTO = new WsTowerCraneProjectRealTimeMonitoringDTO();
                                detailsProjectViolationMonitoringDTO.setEquipmentName(monitoringDTOList.get(i).getEquipmentName());
                                if(monitoringDTOIdCardNo != null){
                                    if (monitoringDTOIdCardNo.getDriverName() != null && monitoringDTOIdCardNo.getDriverName() != "") {
                                        detailsProjectViolationMonitoringDTO.setDriverName(monitoringDTOIdCardNo.getDriverName());
                                    }else{
                                        detailsProjectViolationMonitoringDTO.setDriverName("未知司机");
                                    }
                                }else{
                                    detailsProjectViolationMonitoringDTO.setDriverName("未知司机");
                                }
                                detailsProjectViolationMonitoringDTO.setViolationTime(detailsProjectViolationList.get(k).getViolationTime());
                                detailsProjectViolationMonitoringDTO.setViolationContent(detailsProjectViolationList.get(k).getViolationContent());
                                detailsProjectViolationMonitoringDTOList.add(detailsProjectViolationMonitoringDTO);
                            }
                        }
                    }
                }
            }
        }else{
            if(monitoringDTOList.size() > 0) {
                for (int i = 0; i < monitoringDTOList.size(); i++) {
                    //判断塔机人脸ID 不能为空
                    WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTOIdCardNo = null;
                    if (monitoringDTOList.get(i).getFaceId() != null && monitoringDTOList.get(i).getFaceId() != "") {
                        WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTOFaceNo = wsTowerCraneProjectRealTimeMonitoringMapper.selectTowerCraneEquipmentFaceNoQuery(monitoringDTOList.get(i).getFaceId());
                        monitoringDTOIdCardNo = selectTowerCraneEquipmentNameQuery(monitoringDTOFaceNo.getFaceNo());
                    }
                    if(monitoringDTOList.get(i).getMonitoringEquipmentNo() != null && monitoringDTOList.get(i).getMonitoringEquipmentNo() != ""){
                        List<WsTowerCraneProjectRealTimeMonitoringDTO> detailsProjectViolationList = selectDetailsProjectViolationQuerySame(monitoringDTOList.get(i).getMonitoringEquipmentNo(),wsTowerCraneProjectRealTimeMonitoringDTO.getConditionsDate());
                        if(detailsProjectViolationList.size() > 0) {
                            for (int k = 0; k < detailsProjectViolationList.size(); k++) {
                                WsTowerCraneProjectRealTimeMonitoringDTO detailsProjectViolationMonitoringDTO = new WsTowerCraneProjectRealTimeMonitoringDTO();
                                detailsProjectViolationMonitoringDTO.setEquipmentName(monitoringDTOList.get(i).getEquipmentName());
                                if(monitoringDTOIdCardNo != null){
                                    if (monitoringDTOIdCardNo.getDriverName() != null && monitoringDTOIdCardNo.getDriverName() != "") {
                                        detailsProjectViolationMonitoringDTO.setDriverName(monitoringDTOIdCardNo.getDriverName());
                                    }else{
                                        detailsProjectViolationMonitoringDTO.setDriverName("未知司机");
                                    }
                                }else{
                                    detailsProjectViolationMonitoringDTO.setDriverName("未知司机");
                                }
                                detailsProjectViolationMonitoringDTO.setViolationTime(detailsProjectViolationList.get(k).getViolationTime());
                                detailsProjectViolationMonitoringDTO.setViolationContent(detailsProjectViolationList.get(k).getViolationContent());
                                detailsProjectViolationMonitoringDTOList.add(detailsProjectViolationMonitoringDTO);
                            }
                        }
                    }
                }
            }
        }
        return detailsProjectViolationMonitoringDTOList;
    }

    public List<WsTowerCraneProjectRealTimeMonitoringDTO> selectDetailsProjectViolationQuerySame(String monitoringEquipmentNo, String conditionsDate) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsTowerCraneProjectRealTimeMonitoringDTO> monitoringDTOList = wsTowerCraneProjectRealTimeMonitoringMapper.selectDetailsProjectViolationQuerySame(monitoringEquipmentNo,conditionsDate);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return monitoringDTOList;
    }

    public List<WsTowerCraneProjectRealTimeMonitoringDTO> selectDetailsProjectViolationQueryThirty(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsTowerCraneProjectRealTimeMonitoringDTO> monitoringDTOList = wsTowerCraneProjectRealTimeMonitoringMapper.selectDetailsProjectViolationQueryThirty(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return monitoringDTOList;
    }

    public List<WsTowerCraneProjectRealTimeMonitoringDTO> selectDetailsProjectViolationQuerySeven(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsTowerCraneProjectRealTimeMonitoringDTO> monitoringDTOList = wsTowerCraneProjectRealTimeMonitoringMapper.selectDetailsProjectViolationQuerySeven(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return monitoringDTOList;
    }

    public WsTowerCraneProjectRealTimeMonitoringDTO selectTowerCraneEquipmentNameQuery(String faceNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsTowerCraneProjectRealTimeMonitoringDTO monitoringDTO = wsTowerCraneProjectRealTimeMonitoringMapper.selectTowerCraneEquipmentNameQuery(faceNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return monitoringDTO;
    }


    /**
     * 项目监控统计-按项目-7-30-自定义
     *  工作详情
     * @param wsTowerCraneEquipmentWorkDetailsDTO
     * @return
     */
    @Override
    public List<WsTowerCraneEquipmentWorkDetailsDTO> selectProjectDetailsQuery(WsTowerCraneEquipmentWorkDetailsDTO wsTowerCraneEquipmentWorkDetailsDTO) {
        List<WsTowerCraneEquipmentWorkDetailsDTO> equipmentWorkDetailsDTOList = new ArrayList<WsTowerCraneEquipmentWorkDetailsDTO>();

        WsTowerCraneProjectRealTimeMonitoringDTO wsTowerCraneProjectRealTimeMonitoringDTO = new WsTowerCraneProjectRealTimeMonitoringDTO();
        wsTowerCraneProjectRealTimeMonitoringDTO.setEquipmentName(wsTowerCraneEquipmentWorkDetailsDTO.getEquipmentName());
        wsTowerCraneProjectRealTimeMonitoringDTO.setProjectId(wsTowerCraneEquipmentWorkDetailsDTO.getProjectId());
        //查询塔吊设备信息
        List<WsTowerCraneProjectRealTimeMonitoringDTO> monitoringDTOList = wsTowerCraneProjectRealTimeMonitoringMapper.selectProjectTowerCraneDetailsList(wsTowerCraneProjectRealTimeMonitoringDTO);
        if(wsTowerCraneEquipmentWorkDetailsDTO.getDays().equals("7")){
            if(monitoringDTOList.size() > 0) {
                for (int i = 0; i < monitoringDTOList.size(); i++) {
                    if(monitoringDTOList.get(i).getMonitoringEquipmentNo() != null && monitoringDTOList.get(i).getMonitoringEquipmentNo() != ""){
                        List<WsTowerCraneEquipmentWorkDetailsDTO> projectDetailsList = selectProjectDetailsQuerySeven(monitoringDTOList.get(i).getMonitoringEquipmentNo());
                        if(projectDetailsList.size() > 0) {
                            for (int k = 0; k < projectDetailsList.size(); k++) {
                                WsTowerCraneEquipmentWorkDetailsDTO workDetailsDTO = new WsTowerCraneEquipmentWorkDetailsDTO();
                                workDetailsDTO.setEquipmentName(monitoringDTOList.get(i).getEquipmentName());
                                workDetailsDTO.setStartTime(projectDetailsList.get(k).getStartTime());
                                workDetailsDTO.setWorkingHours(projectDetailsList.get(k).getWorkingHours());
                                workDetailsDTO.setLiftingWeight(projectDetailsList.get(k).getLiftingWeight());
                                workDetailsDTO.setLiftingDistance(projectDetailsList.get(k).getLiftingDistance());
                                equipmentWorkDetailsDTOList.add(workDetailsDTO);
                            }
                        }
                    }
                }
            }
        }else if(wsTowerCraneEquipmentWorkDetailsDTO.getDays().equals("30")){
            if(monitoringDTOList.size() > 0) {
                for (int i = 0; i < monitoringDTOList.size(); i++) {
                    if(monitoringDTOList.get(i).getMonitoringEquipmentNo() != null && monitoringDTOList.get(i).getMonitoringEquipmentNo() != ""){
                        List<WsTowerCraneEquipmentWorkDetailsDTO> projectDetailsList = selectProjectDetailsQueryThirty(monitoringDTOList.get(i).getMonitoringEquipmentNo());
                        if(projectDetailsList.size() > 0) {
                            for (int k = 0; k < projectDetailsList.size(); k++) {
                                WsTowerCraneEquipmentWorkDetailsDTO workDetailsDTO = new WsTowerCraneEquipmentWorkDetailsDTO();
                                workDetailsDTO.setEquipmentName(monitoringDTOList.get(i).getEquipmentName());
                                workDetailsDTO.setStartTime(projectDetailsList.get(k).getStartTime());
                                workDetailsDTO.setWorkingHours(projectDetailsList.get(k).getWorkingHours());
                                workDetailsDTO.setLiftingWeight(projectDetailsList.get(k).getLiftingWeight());
                                workDetailsDTO.setLiftingDistance(projectDetailsList.get(k).getLiftingDistance());
                                equipmentWorkDetailsDTOList.add(workDetailsDTO);
                            }
                        }
                    }
                }
            }
        }else{
            if(monitoringDTOList.size() > 0) {
                for (int i = 0; i < monitoringDTOList.size(); i++) {
                    if(monitoringDTOList.get(i).getMonitoringEquipmentNo() != null && monitoringDTOList.get(i).getMonitoringEquipmentNo() != ""){
                        List<WsTowerCraneEquipmentWorkDetailsDTO> projectDetailsList = selectProjectDetailsQueryToday(monitoringDTOList.get(i).getMonitoringEquipmentNo(),wsTowerCraneEquipmentWorkDetailsDTO.getConditionsDate());
                        if(projectDetailsList.size() > 0) {
                            for (int k = 0; k < projectDetailsList.size(); k++) {
                                WsTowerCraneEquipmentWorkDetailsDTO workDetailsDTO = new WsTowerCraneEquipmentWorkDetailsDTO();
                                workDetailsDTO.setEquipmentName(monitoringDTOList.get(i).getEquipmentName());
                                workDetailsDTO.setStartTime(projectDetailsList.get(k).getStartTime());
                                workDetailsDTO.setWorkingHours(projectDetailsList.get(k).getWorkingHours());
                                workDetailsDTO.setLiftingWeight(projectDetailsList.get(k).getLiftingWeight());
                                workDetailsDTO.setLiftingDistance(projectDetailsList.get(k).getLiftingDistance());
                                equipmentWorkDetailsDTOList.add(workDetailsDTO);
                            }
                        }
                    }
                }
            }
        }
        return equipmentWorkDetailsDTOList;
    }

    public List<WsTowerCraneEquipmentWorkDetailsDTO> selectProjectDetailsQueryToday(String monitoringEquipmentNo, String conditionsDate) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsTowerCraneEquipmentWorkDetailsDTO> workDetailsDTOList = wsTowerCraneEquipmentMonitorMapper.selectProjectDetailsQueryToday(monitoringEquipmentNo,conditionsDate);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return workDetailsDTOList;
    }

    public List<WsTowerCraneEquipmentWorkDetailsDTO> selectProjectDetailsQueryThirty(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsTowerCraneEquipmentWorkDetailsDTO> workDetailsDTOList = wsTowerCraneEquipmentMonitorMapper.selectProjectDetailsQueryThirty(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return workDetailsDTOList;
    }

    public List<WsTowerCraneEquipmentWorkDetailsDTO> selectProjectDetailsQuerySeven(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsTowerCraneEquipmentWorkDetailsDTO> workDetailsDTOList = wsTowerCraneEquipmentMonitorMapper.selectProjectDetailsQuerySeven(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return workDetailsDTOList;
    }


    @Override
    public List<WsTowerCraneMonitoringRealtimeDTO> getAreaDeviceStatisticsList(WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO) {
        List<WsTowerCraneMonitoringRealtimeDTO> towerCraneMonitoringRealtimeDTOList = new ArrayList<WsTowerCraneMonitoringRealtimeDTO>();
        List list=new ArrayList();
        if (StringUtils.isNull(towerCraneMonitoringRealtimeDTO))
            towerCraneMonitoringRealtimeDTO = new WsTowerCraneMonitoringRealtimeDTO();
        if(StringUtils.isNull(towerCraneMonitoringRealtimeDTO.getWsArea()))
            return null;
        //获取子级区域列表
        List<WsArea> wsAreas =wsAreaMapper.selectChidrenAreaList(towerCraneMonitoringRealtimeDTO.getAreaCode());
        for (int i = 0; i < wsAreas.size(); i++) {
            //获取塔吊设备列表
            List<WsTowerCraneMonitoringRealtimeDTO> realtimeDTOList = wsTowerCraneEquipmentMonitorMapper.selectDeviceListByArea(wsAreas.get(i).getCode());
            if(realtimeDTOList.size() > 0){
                WsTowerCraneMonitoringRealtimeDTO wsTowerCraneMonitoringRealtimeDTO = new WsTowerCraneMonitoringRealtimeDTO();
                if(realtimeDTOList.size() > 0){
                    LinkedList<Integer> linkedlists = new LinkedList<Integer>();
                    for (int k = 0; k < realtimeDTOList.size(); k++) {
                        WsProject wsProjects = new WsProject();
                        List<WsProject> wsProjectJt = wsProjectService.selectWsProjectList(wsProjects);
                        for (int a = 0; a < wsProjectJt.size(); a++) {
                            if (realtimeDTOList.get(k).getProjectId() == wsProjectJt.get(a).getId()) {
                                linkedlists.add(realtimeDTOList.get(k).getProjectId());
                            }
                        }
                    }
                    Integer[] projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);
                    if(StringUtils.isNotEmpty(projectIds)){
                        List<WsTowerCraneMonitoringRealtimeDTO> listRealtimeDTO = wsTowerCraneEquipmentMonitorMapper.selectlistRealtimeDTOProjectIds(projectIds);
                        wsTowerCraneMonitoringRealtimeDTO.setProvinceNum(listRealtimeDTO.size());
                        wsTowerCraneMonitoringRealtimeDTO.setCityNum(listRealtimeDTO.size());
                        wsTowerCraneMonitoringRealtimeDTO.setCountyNum(listRealtimeDTO.size());
                    }else{
                        wsTowerCraneMonitoringRealtimeDTO.setProvinceNum(0);
                        wsTowerCraneMonitoringRealtimeDTO.setCityNum(0);
                        wsTowerCraneMonitoringRealtimeDTO.setCountyNum(0);
                    }
                }
                wsTowerCraneMonitoringRealtimeDTO.setProvince(wsAreas.get(i).getName());
                wsTowerCraneMonitoringRealtimeDTO.setCity(wsAreas.get(i).getName());
                wsTowerCraneMonitoringRealtimeDTO.setCounty(wsAreas.get(i).getName());
                wsTowerCraneMonitoringRealtimeDTO.setLatitude(wsAreas.get(i).getLatitude());
                wsTowerCraneMonitoringRealtimeDTO.setLongitude(wsAreas.get(i).getLongitude());
                list.add(wsTowerCraneMonitoringRealtimeDTO);
            }
        }
        return list;
    }

    @Override
    public List<WsTowerCraneMonitoringRealtimeDTO> getAreaDeviceStatisticsListProject(WsTowerCraneMonitoringRealtimeDTO towerCraneMonitoringRealtimeDTO) {
        List<WsTowerCraneMonitoringRealtimeDTO> realtimeDTOList = new ArrayList<WsTowerCraneMonitoringRealtimeDTO>();
        if (StringUtils.isNull(towerCraneMonitoringRealtimeDTO))
            towerCraneMonitoringRealtimeDTO = new WsTowerCraneMonitoringRealtimeDTO();
        if(StringUtils.isNull(towerCraneMonitoringRealtimeDTO.getWsArea()))
            return null;
        //获取塔吊设备列表
        realtimeDTOList = wsTowerCraneEquipmentMonitorMapper.selectDeviceListByAreaProject(towerCraneMonitoringRealtimeDTO.getAreaCode());
        for(int k = 0; k < realtimeDTOList.size(); k++){
            WsProject wsProjects = new WsProject();
            List<WsProject> wsProjectJt = wsProjectService.selectWsProjectList(wsProjects);
            for (int a = 0; a < wsProjectJt.size(); a++) {
                if (realtimeDTOList.get(k).getProjectId() == wsProjectJt.get(a).getId()) {
                    WsTowerCraneMonitoringRealtimeDTO realtimeDTO = wsTowerCraneEquipmentMonitorMapper.selectListByAreaProject(realtimeDTOList.get(k).getProjectId());
                    WsProject wsProject = wsProjectMapper.selectWsProjectById(realtimeDTOList.get(k).getProjectId());
                    realtimeDTOList.get(k).setLatitude(wsProject.getLatitude());
                    realtimeDTOList.get(k).setLongitude(wsProject.getLongitude());
                    realtimeDTOList.get(k).setProjectEquipmentNum(realtimeDTO.getProjectEquipmentNum());
                }
            }
        }
        return realtimeDTOList;
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsTowerCraneProjectMonitoringDetailsDTO> selectEquipmentStatusAnalysis(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO) {
        List<WsTowerCraneProjectMonitoringDetailsDTO> detailsDTOList = new ArrayList<WsTowerCraneProjectMonitoringDetailsDTO>();
        if(StringUtils.isNotEmpty(wsTowerCraneProjectMonitoringDetailsDTO.getMonitoringEquipmentNo())){
            detailsDTOList = wsTowerCraneProjectRealTimeMonitoringMapper.selectEquipmentStatusAnalysis(wsTowerCraneProjectMonitoringDetailsDTO);
        }
        return detailsDTOList;
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsTowerCraneProjectMonitoringDetailsDTO> selectHoistingCycle(WsTowerCraneProjectMonitoringDetailsDTO wsTowerCraneProjectMonitoringDetailsDTO) {
        List<WsTowerCraneProjectMonitoringDetailsDTO> detailsDTOList = new ArrayList<WsTowerCraneProjectMonitoringDetailsDTO>();
        if(StringUtils.isNotEmpty(wsTowerCraneProjectMonitoringDetailsDTO.getMonitoringEquipmentNo())){
            if(wsTowerCraneProjectMonitoringDetailsDTO.getQueryType().equals("1")){//7天
                detailsDTOList = wsTowerCraneProjectRealTimeMonitoringMapper.selectHoistingCycleSeven(wsTowerCraneProjectMonitoringDetailsDTO);
            }else if(wsTowerCraneProjectMonitoringDetailsDTO.getQueryType().equals("2")){//15天
                detailsDTOList = wsTowerCraneProjectRealTimeMonitoringMapper.selectHoistingCycleFifteen(wsTowerCraneProjectMonitoringDetailsDTO);
            }else{//30天
                detailsDTOList = wsTowerCraneProjectRealTimeMonitoringMapper.selectHoistingCycleThirty(wsTowerCraneProjectMonitoringDetailsDTO);
            }
        }
        return detailsDTOList;
    }


}
