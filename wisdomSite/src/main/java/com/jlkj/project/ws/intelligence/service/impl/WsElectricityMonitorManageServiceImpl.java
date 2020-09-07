package com.jlkj.project.ws.intelligence.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.jlkj.common.utils.StringUtils;
import com.jlkj.framework.aspectj.lang.annotation.DataSource;
import com.jlkj.framework.aspectj.lang.enums.DataSourceType;
import com.jlkj.framework.datasource.DynamicDataSourceContextHolder;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsProjectEquipment;
import com.jlkj.project.ws.domain.WsProjectSitePartition;
import com.jlkj.project.ws.domain.WsSitePartitionItem;
import com.jlkj.project.ws.intelligence.domain.WsElecBoxMonitorData;
import com.jlkj.project.ws.mapper.WsProjectEquipmentMapper;
import com.jlkj.project.ws.mapper.WsProjectSitePartitionMapper;
import com.jlkj.project.ws.mapper.WsSitePartitionItemMapper;
import com.jlkj.project.ws.service.IWsProjectService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.intelligence.mapper.WsElectricityMonitorManageMapper;
import com.jlkj.project.ws.intelligence.domain.WsElectricityMonitorManage;
import com.jlkj.project.ws.intelligence.service.IWsElectricityMonitorManageService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 电能监测设备管理 Service业务层处理
 *
 * @author gaowei
 * @date 2020-07-14
 */
@Service
public class WsElectricityMonitorManageServiceImpl implements IWsElectricityMonitorManageService
{
    @Autowired
    private WsElectricityMonitorManageMapper wsElectricityMonitorManageMapper;

    @Autowired
    private WsProjectEquipmentMapper wsProjectEquipmentMapper;

    @Autowired
    private WsProjectSitePartitionMapper wsProjectSitePartitionMapper;

    @Autowired
    private IWsProjectService wsProjectService;

    @Autowired
    private WsSitePartitionItemMapper wsSitePartitionItemMapper;


    /**
     * 查询电能监测设备管理 
     *
     * @param id 电能监测设备管理 ID
     * @return 电能监测设备管理 
     */
    @Override
    public WsElectricityMonitorManage selectWsElectricityMonitorManageById(Integer id)
    {
        return wsElectricityMonitorManageMapper.selectWsElectricityMonitorManageById(id);
    }

    /**
     * 查询电能监测设备管理 列表
     *
     * @param wsElectricityMonitorManage 电能监测设备管理 
     * @return 电能监测设备管理 
     */
    @Override
    public List<WsElectricityMonitorManage> selectWsElectricityMonitorManageList(WsElectricityMonitorManage wsElectricityMonitorManage)
    {
        List<WsElectricityMonitorManage> listElectricity = new ArrayList<WsElectricityMonitorManage>();
        //获取当前登录项目id
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                wsElectricityMonitorManage.setProjectId(list.get(i).getId());
            }
            listElectricity = wsElectricityMonitorManageMapper.selectWsElectricityMonitorManageList(wsElectricityMonitorManage,null);
        }
        return listElectricity;
    }

    /**
     * 新增电能监测设备管理 
     *
     * @param wsElectricityMonitorManage 电能监测设备管理 
     * @return 结果
     */
    @Override
    @Transactional
    public int insertWsElectricityMonitorManage(WsElectricityMonitorManage wsElectricityMonitorManage)
    {
        int electricity = 0;
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                wsElectricityMonitorManage.setProjectId(list.get(i).getId());
            }
            wsElectricityMonitorManage.setEnableDisable(1);
            wsElectricityMonitorManage.setCreatedTime(new Date());
            wsElectricityMonitorManage.setUpdatedTime(new Date());
            electricity = wsElectricityMonitorManageMapper.insertWsElectricityMonitorManage(wsElectricityMonitorManage);
            if(StringUtils.isNotEmpty(wsElectricityMonitorManage.getMonitoringEquipment())){
                WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
                wsProjectEquipment.setEnableStatus(1);
                wsProjectEquipment.setEquipmentType(4);
                wsProjectEquipment.setSerialNumber(wsElectricityMonitorManage.getMonitoringEquipment());
                wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
            }
            WsSitePartitionItem wsSitePartitionItem = new WsSitePartitionItem();
            wsSitePartitionItem.setItemId(wsElectricityMonitorManage.getId());
            wsSitePartitionItem.setSitePartitionId(wsElectricityMonitorManage.getInstallationArea());
            wsSitePartitionItem.setItemType("2");
            wsSitePartitionItemMapper.insertWsSitePartitionItem(wsSitePartitionItem);
        }
        return electricity;
    }

    /**
     * 修改电能监测设备管理 
     *
     * @param wsElectricityMonitorManage 电能监测设备管理 
     * @return 结果
     */
    @Override
    @Transactional
    public int updateWsElectricityMonitorManage(WsElectricityMonitorManage wsElectricityMonitorManage)
    {
        WsElectricityMonitorManage electricityMonitorManage = wsElectricityMonitorManageMapper.selectWsElectricityMonitorManageById(wsElectricityMonitorManage.getId());
        if(StringUtils.isNotEmpty(electricityMonitorManage.getMonitoringEquipment())){
            WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
            wsProjectEquipment.setSerialNumber(electricityMonitorManage.getMonitoringEquipment());
            wsProjectEquipment.setEnableStatus(0);
            wsProjectEquipment.setEquipmentType(4);
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }
        int monitorManage = wsElectricityMonitorManageMapper.updateWsElectricityMonitorManage(wsElectricityMonitorManage);
        if(StringUtils.isNotEmpty(wsElectricityMonitorManage.getMonitoringEquipment())){
            WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
            wsProjectEquipment.setEnableStatus(1);
            wsProjectEquipment.setEquipmentType(4);
            wsProjectEquipment.setSerialNumber(wsElectricityMonitorManage.getMonitoringEquipment());
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }
        WsSitePartitionItem wsSitePartitionItem = new WsSitePartitionItem();
        wsSitePartitionItem.setItemId(wsElectricityMonitorManage.getId());
        wsSitePartitionItem.setSitePartitionId(wsElectricityMonitorManage.getInstallationArea());
        wsSitePartitionItem.setItemType("2");
        wsSitePartitionItemMapper.updateWsSitePartitionItemByItemAndType(wsSitePartitionItem);
        return monitorManage;
    }

    /**
     * 批量删除电能监测设备管理 
     *
     * @param ids 需要删除的电能监测设备管理 ID
     * @return 结果
     */
    @Override
    public int deleteWsElectricityMonitorManageByIds(Integer[] ids)
    {
        return wsElectricityMonitorManageMapper.deleteWsElectricityMonitorManageByIds(ids);
    }

    /**
     * 删除电能监测设备管理 信息
     *
     * @param id 电能监测设备管理 ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteWsElectricityMonitorManageById(Integer id)
    {
        WsElectricityMonitorManage electricityMonitorManage = wsElectricityMonitorManageMapper.selectWsElectricityMonitorManageById(id);
        if(StringUtils.isNotEmpty(electricityMonitorManage.getMonitoringEquipment())){
            WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
            wsProjectEquipment.setSerialNumber(electricityMonitorManage.getMonitoringEquipment());
            wsProjectEquipment.setEnableStatus(0);
            wsProjectEquipment.setEquipmentType(4);
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }
        wsSitePartitionItemMapper.deleteWsSitePartitionItemByItemAndType(id, "2");
        return wsElectricityMonitorManageMapper.deleteWsElectricityMonitorManageById(id);
    }

    /**
     * 查询监测设备（电能监测设备）
     * @param wsProjectEquipment
     * @return
     */
    @Override
    public List<WsProjectEquipment> listMonitoringEquipment(WsProjectEquipment wsProjectEquipment) {
        List<WsProjectEquipment> listProjectEquipment = new ArrayList<WsProjectEquipment>();
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                wsProjectEquipment.setProjectId(list.get(i).getId());
            }
            wsProjectEquipment.setEquipmentType(4);
            wsProjectEquipment.setEnableStatus(0);
            listProjectEquipment = wsProjectEquipmentMapper.selectWsProjectEquipmentList(wsProjectEquipment);
        }
        return listProjectEquipment;
    }

    /**
     * 查询安装区域
     * @param wsProjectSitePartition
     * @return
     */
    @Override
    public List<WsProjectSitePartition> listProjectSitePartition(WsProjectSitePartition wsProjectSitePartition) {
        List<WsProjectSitePartition> listProjectSite = new ArrayList<WsProjectSitePartition>();
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                wsProjectSitePartition.setProjectId(list.get(i).getId());
            }
            listProjectSite = wsProjectSitePartitionMapper.selectWsProjectSitePartitionList(wsProjectSitePartition.getProjectId());
        }
        return listProjectSite;
    }

    /**
     * 启用，禁用
     * @param wsElectricityMonitorManage
     * @return
     */
    @Override
    public int updateEnableDisable(WsElectricityMonitorManage wsElectricityMonitorManage) {
        return wsElectricityMonitorManageMapper.updateWsElectricityMonitorManage(wsElectricityMonitorManage);
    }

    /**
     * 用电安全：设备信息列表
     * @param wsElectricityMonitorManage
     * @return
     */
    @Override
    public List<WsElectricityMonitorManage> selectElectricitySecurityList(WsElectricityMonitorManage wsElectricityMonitorManage) {
        List<WsElectricityMonitorManage> electricityMonitorManageList = new ArrayList<WsElectricityMonitorManage>();
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0) {
            LinkedList<Integer> linkedlists = new LinkedList<Integer>();
            for (int i = 0; i < list.size(); i++) {
                linkedlists.add(list.get(i).getId());
            }
            Integer[] projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);
            wsElectricityMonitorManage.setEnableDisable(1);//只查询启用状态数据
            electricityMonitorManageList = wsElectricityMonitorManageMapper.selectWsElectricityMonitorManageList(wsElectricityMonitorManage,projectIds);
            if(electricityMonitorManageList.size() > 0){
                for (int i = 0; i < electricityMonitorManageList.size(); i++) {
                    WsElectricityMonitorManage manageDTO = selectElectricityMonitorMonitoringStatus(electricityMonitorManageList.get(i).getMonitoringEquipment());
                    electricityMonitorManageList.get(i).setMonitoringStatus(manageDTO.getMonitoringStatus());
                }
            }
        }
        return electricityMonitorManageList;
    }

    public WsElectricityMonitorManage selectElectricityMonitorMonitoringStatus(String monitoringEquipment) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsElectricityMonitorManage manageDTO = wsElectricityMonitorManageMapper.selectElectricityMonitorMonitoringStatus(monitoringEquipment);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return manageDTO;
    }

    /**
     * 查询用电安全-监测详情-设备信息及实时数据
     * @param wsElecBoxMonitorData
     * @return
     */
    @Override
    public WsElecBoxMonitorData selectRealTimeMonitoring(WsElecBoxMonitorData wsElecBoxMonitorData) {
        WsElecBoxMonitorData elecBoxMonitor = new WsElecBoxMonitorData();
        WsElecBoxMonitorData monitorData = wsElectricityMonitorManageMapper.selectRealTimeMonitoringEquipmentName(wsElecBoxMonitorData);
        WsElecBoxMonitorData elecBoxMonitorData = selectRealTimeMonitoringInformation(wsElecBoxMonitorData);
        if(elecBoxMonitorData != null){
            elecBoxMonitor.setMonitoringEquipment(wsElecBoxMonitorData.getMonitoringEquipment());
            elecBoxMonitor.setEquipmentName(monitorData.getEquipmentName());
            elecBoxMonitor.setVoltageU(elecBoxMonitorData.getVoltageU());
            elecBoxMonitor.setVoltageV(elecBoxMonitorData.getVoltageV());
            elecBoxMonitor.setVoltageW(elecBoxMonitorData.getVoltageW());
            elecBoxMonitor.setCurrentU(elecBoxMonitorData.getCurrentU());
            elecBoxMonitor.setCurrentV(elecBoxMonitorData.getCurrentV());
            elecBoxMonitor.setCurrentW(elecBoxMonitorData.getCurrentW());
            elecBoxMonitor.setActiveElectricity(elecBoxMonitorData.getActiveElectricity());
            elecBoxMonitor.setReactiveElectricity(elecBoxMonitorData.getReactiveElectricity());
            elecBoxMonitor.setInsertTime(elecBoxMonitorData.getInsertTime());
        }else{
            elecBoxMonitor.setMonitoringEquipment(wsElecBoxMonitorData.getMonitoringEquipment());
            elecBoxMonitor.setEquipmentName(monitorData.getEquipmentName());
            elecBoxMonitor.setVoltageU("0.0");
            elecBoxMonitor.setVoltageV("0.0");
            elecBoxMonitor.setVoltageW("0.0");
            elecBoxMonitor.setCurrentU("0.0");
            elecBoxMonitor.setCurrentV("0.0");
            elecBoxMonitor.setCurrentW("0.0");
            elecBoxMonitor.setActiveElectricity("0");
            elecBoxMonitor.setReactiveElectricity("0");
            elecBoxMonitor.setInsertTime("");
        }
        return elecBoxMonitor;
    }

    private WsElecBoxMonitorData selectRealTimeMonitoringInformation(WsElecBoxMonitorData wsElecBoxMonitorData) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsElecBoxMonitorData monitorData = wsElectricityMonitorManageMapper.selectRealTimeMonitoringInformation(wsElecBoxMonitorData);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return monitorData;
    }

    /**
     * 查询用电安全-监测详情-监测记录
     * @param wsElecBoxMonitorData
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsElecBoxMonitorData> selectMonitoringRecordsList(WsElecBoxMonitorData wsElecBoxMonitorData) {
        List<WsElecBoxMonitorData> list = new ArrayList<WsElecBoxMonitorData>();
        if(wsElecBoxMonitorData.getMonitorType().equals("1")){//近24小时 数据
            list = wsElectricityMonitorManageMapper.selectMonitoringRecordsListTwentyFourTime(wsElecBoxMonitorData);
        }else if(wsElecBoxMonitorData.getMonitorType().equals("2")){//近3天 数据
            list = wsElectricityMonitorManageMapper.selectMonitoringRecordsListThreeDays(wsElecBoxMonitorData);
        }else{//近7天数据
            list = wsElectricityMonitorManageMapper.selectMonitoringRecordsListSevenDays(wsElecBoxMonitorData);
        }
        return list;
    }

    @Override
    public WsElecBoxMonitorData selectElectricityConsumption(WsElecBoxMonitorData wsElecBoxMonitorData) {
        List<WsElecBoxMonitorData> list = wsElectricityMonitorManageMapper.selectMonitoringEquipmentList(wsElecBoxMonitorData);
        if(list.size() > 0){
            LinkedList<String> linkedlist = new LinkedList<String>();
            for (int i = 0; i < list.size(); i++) {
                linkedlist.add(list.get(i).getMonitoringEquipment());
            }
            String[] monitoringEquipments = linkedlist.toArray(new String[linkedlist.size()]);
            wsElecBoxMonitorData = selectElectricityConsumptionORpower(monitoringEquipments);
        }
        return wsElecBoxMonitorData;
    }


    public WsElecBoxMonitorData selectElectricityConsumptionORpower(String[] monitoringEquipments) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsElecBoxMonitorData wsElecBoxMonitorData = wsElectricityMonitorManageMapper.selectElectricityConsumptionORpower(monitoringEquipments);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return wsElecBoxMonitorData;
    }


    /**
     * 查询能耗监测-能耗分析
     * @param wsElecBoxMonitorData
     * @return
     */
    @Override
    public List<WsElecBoxMonitorData> selectEnergyConsumptionAnalysisList(WsElecBoxMonitorData wsElecBoxMonitorData) {
        List<WsElecBoxMonitorData> WsElecBoxMonitorDataList = new ArrayList<WsElecBoxMonitorData>();
        List<WsProjectSitePartition> listPartition = wsProjectSitePartitionMapper.selectWsProjectSitePartitionLists(wsElecBoxMonitorData.getProjectId());
        if(listPartition.size() > 0){
            for (int i = 0; i < listPartition.size(); i++) {
                int installationArea = listPartition.get(i).getId();
                List<WsElectricityMonitorManage> electricityMonitorList = wsElectricityMonitorManageMapper.selectWsElectricityMonitorManage(installationArea);
                LinkedList<String> linkedlist = new LinkedList<String>();
                if(electricityMonitorList.size() > 0 ){
                    for (int k = 0; k < electricityMonitorList.size(); k++) {
                        linkedlist.add(electricityMonitorList.get(k).getMonitoringEquipment());
                    }
                    String[] monitoringEquipments = linkedlist.toArray(new String[linkedlist.size()]);
                    WsElecBoxMonitorData elecBoxMonitor = selectEnergyConsumptionAnalysis(monitoringEquipments);
                       /* WsElecBoxMonitorDataList.get(i).setRegion(listPartition.get(i).getPartitionName());
                        WsElecBoxMonitorDataList.get(i).setTotalPower(elecBoxMonitor.getActiveElectricity());*/
                    WsElecBoxMonitorData boxMonitorData = new WsElecBoxMonitorData();
                    boxMonitorData.setRegion(listPartition.get(i).getPartitionName());
                    boxMonitorData.setTotalPower(elecBoxMonitor.getTotalPower());
                    WsElecBoxMonitorDataList.add(boxMonitorData);
                }
            }
        }
        return WsElecBoxMonitorDataList;
    }


    public WsElecBoxMonitorData selectEnergyConsumptionAnalysis(String[] monitoringEquipments) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsElecBoxMonitorData wsElecBoxMonitorData = wsElectricityMonitorManageMapper.selectElectricityConsumptionORpower(monitoringEquipments);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return wsElecBoxMonitorData;
    }


    /**
     * 查询能耗监测-电表详情
     * @param wsElectricityMonitorManage
     * @return
     */
    @Override
    public List<WsElectricityMonitorManage> selectMeterDetailsList(WsElectricityMonitorManage wsElectricityMonitorManage) {
        List<WsElectricityMonitorManage> electricitylist = new ArrayList<WsElectricityMonitorManage>();
        electricitylist = wsElectricityMonitorManageMapper.selectWsElectricityMonitorManageList(wsElectricityMonitorManage,null);
        if(electricitylist.size() > 0){
            for (int i = 0; i < electricitylist.size(); i++) {
                WsElectricityMonitorManage electricityMonitorJr = selectWsElectricityTodayElectricity(electricitylist.get(i).getMonitoringEquipment());//今日
                WsElectricityMonitorManage electricityMonitorZr = selectWsElectricityYesterdayElectricity(electricitylist.get(i).getMonitoringEquipment());//昨日
                int todayElectricity = 0;
                if(electricityMonitorJr != null){
                    if(electricityMonitorZr == null){
                        todayElectricity = Integer.parseInt(electricityMonitorJr.getTodayElectricity()) - 0;//今日电量
                    }else{
                        todayElectricity = Integer.parseInt(electricityMonitorJr.getTodayElectricity()) - Integer.parseInt(electricityMonitorZr.getTodayElectricity());//今日电量
                    }
                }
                WsElectricityMonitorManage electricityMonitorDy = selectWsElectricitySameMonthElectricity(electricitylist.get(i).getMonitoringEquipment());//当月
                WsElectricityMonitorManage electricityMonitorSy = selectWsElectricityLastMonthElectricity(electricitylist.get(i).getMonitoringEquipment());//上月
                int monthElectricity = 0;//本月电量
                if(electricityMonitorDy != null){
                    if(electricityMonitorSy == null){
                        monthElectricity = Integer.parseInt(electricityMonitorDy.getMonthElectricity()) - 0;//本月电量
                    }else{
                        monthElectricity = Integer.parseInt(electricityMonitorDy.getMonthElectricity()) - Integer.parseInt(electricityMonitorSy.getMonthElectricity());//本月电量
                    }
                }
                electricitylist.get(i).setTodayElectricity(Integer.toString(todayElectricity));
                electricitylist.get(i).setMonthElectricity(Integer.toString(monthElectricity));
            }
        }
        return electricitylist;
    }


    public WsElectricityMonitorManage selectWsElectricityLastMonthElectricity(String monitoringEquipment) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsElectricityMonitorManage electricityMonitor = wsElectricityMonitorManageMapper.selectWsElectricityLastMonthElectricity(monitoringEquipment);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return electricityMonitor;
    }

    public WsElectricityMonitorManage selectWsElectricitySameMonthElectricity(String monitoringEquipment) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsElectricityMonitorManage electricityMonitor = wsElectricityMonitorManageMapper.selectWsElectricitySameMonthElectricity(monitoringEquipment);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return electricityMonitor;
    }

    public WsElectricityMonitorManage selectWsElectricityYesterdayElectricity(String monitoringEquipment) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsElectricityMonitorManage electricityMonitor = wsElectricityMonitorManageMapper.selectWsElectricityYesterdayElectricity(monitoringEquipment);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return electricityMonitor;
    }

    public WsElectricityMonitorManage selectWsElectricityTodayElectricity(String monitoringEquipment) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsElectricityMonitorManage electricityMonitor = wsElectricityMonitorManageMapper.selectWsElectricityTodayElectricity(monitoringEquipment);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return electricityMonitor;
    }


    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsElectricityMonitorManage> selectPowerConsumptionDetailsList(WsElectricityMonitorManage wsElectricityMonitorManage) {
        List<WsElectricityMonitorManage> list = new ArrayList<WsElectricityMonitorManage>();
        if(wsElectricityMonitorManage.getQueryType().equals("1")){//按日查询
            list = wsElectricityMonitorManageMapper.selectPowerConsumptionDetailsDayList(wsElectricityMonitorManage);
        }else{//按月查询
            list = wsElectricityMonitorManageMapper.selectPowerConsumptionDetailsMonthList(wsElectricityMonitorManage);
        }
        return list;
    }
}
