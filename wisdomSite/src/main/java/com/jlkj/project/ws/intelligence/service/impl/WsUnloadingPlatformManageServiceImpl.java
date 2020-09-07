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
import com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout;
import com.jlkj.project.ws.intelligence.dto.WsUnloadingPlatformManageDTO;
import com.jlkj.project.ws.intelligence.mapper.WsProjectFieldLayoutMapper;
import com.jlkj.project.ws.mapper.WsProjectEquipmentMapper;
import com.jlkj.project.ws.service.IWsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.intelligence.mapper.WsUnloadingPlatformManageMapper;
import com.jlkj.project.ws.intelligence.domain.WsUnloadingPlatformManage;
import com.jlkj.project.ws.intelligence.service.IWsUnloadingPlatformManageService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 卸料平台设备管理 Service业务层处理
 *
 * @author gaowei
 * @date 2020-07-13
 */
@Service
public class WsUnloadingPlatformManageServiceImpl implements IWsUnloadingPlatformManageService
{
    @Autowired
    private WsUnloadingPlatformManageMapper wsUnloadingPlatformManageMapper;

    @Autowired
    private WsProjectEquipmentMapper wsProjectEquipmentMapper;

    @Autowired
    private WsProjectFieldLayoutMapper wsProjectFieldLayoutMapper;

    @Autowired
    private IWsProjectService wsProjectService;

    /**
     * 查询卸料平台设备管理 
     *
     * @param id 卸料平台设备管理 ID
     * @return 卸料平台设备管理 
     */
    @Override
    public WsUnloadingPlatformManage selectWsUnloadingPlatformManageById(Integer id)
    {
        return wsUnloadingPlatformManageMapper.selectWsUnloadingPlatformManageById(id);
    }

    /**
     * 查询卸料平台设备管理 列表
     *
     * @param wsUnloadingPlatformManage 卸料平台设备管理 
     * @return 卸料平台设备管理 
     */
    @Override
    public List<WsUnloadingPlatformManage> selectWsUnloadingPlatformManageList(WsUnloadingPlatformManage wsUnloadingPlatformManage)
    {
        List<WsUnloadingPlatformManage> listUnloadingPlatformManage = new ArrayList<WsUnloadingPlatformManage>();
        if(wsUnloadingPlatformManage.getProjectId() != null){
            listUnloadingPlatformManage = wsUnloadingPlatformManageMapper.selectWsUnloadingPlatformManageList(wsUnloadingPlatformManage);
        }else{
            WsProject wsProject = new WsProject();
            List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
            if(list.size() > 0){
                for(int i = 0; i < list.size(); i++){
                    wsUnloadingPlatformManage.setProjectId(list.get(i).getId());
                }
                listUnloadingPlatformManage = wsUnloadingPlatformManageMapper.selectWsUnloadingPlatformManageList(wsUnloadingPlatformManage);
            }
        }
        return listUnloadingPlatformManage;
    }

    /**
     * 新增卸料平台设备管理 
     *
     * @param wsUnloadingPlatformManage 卸料平台设备管理 
     * @return 结果
     */
    @Override
    @Transactional
    public int insertWsUnloadingPlatformManage(WsUnloadingPlatformManage wsUnloadingPlatformManage)
    {
        int platformManage = 0;
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                wsUnloadingPlatformManage.setProjectId(list.get(i).getId());
            }
            wsUnloadingPlatformManage.setEnableDisable(1);
            platformManage = wsUnloadingPlatformManageMapper.insertWsUnloadingPlatformManage(wsUnloadingPlatformManage);
            if(StringUtils.isNotEmpty(wsUnloadingPlatformManage.getDeviceNumber())){
                WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
                wsProjectEquipment.setEnableStatus(1);
                wsProjectEquipment.setEquipmentType(7);
                wsProjectEquipment.setSerialNumber(wsUnloadingPlatformManage.getDeviceNumber());
                wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
            }
            WsProjectFieldLayout fieldLayout = new WsProjectFieldLayout();
            if(wsUnloadingPlatformManage.getFieldlayoutId() != null){
                if(StringUtils.isNotEmpty(wsUnloadingPlatformManage.getFieldlayoutAddress())){
                    fieldLayout.setId(wsUnloadingPlatformManage.getFieldlayoutId());
                    fieldLayout.setProjectId(wsUnloadingPlatformManage.getProjectId());
                    fieldLayout.setEquipmentType(3);
                    fieldLayout.setFieldlayoutAddress(wsUnloadingPlatformManage.getFieldlayoutAddress());
                    fieldLayout.setUpdatedTime(new Date());
                    wsProjectFieldLayoutMapper.updateWsProjectFieldLayout(fieldLayout);
                }
            }else{
                if(StringUtils.isNotEmpty(wsUnloadingPlatformManage.getFieldlayoutAddress())){
                    fieldLayout.setProjectId(wsUnloadingPlatformManage.getProjectId());
                    fieldLayout.setEquipmentType(3);
                    fieldLayout.setFieldlayoutAddress(wsUnloadingPlatformManage.getFieldlayoutAddress());
                    fieldLayout.setCreatedTime(new Date());
                    fieldLayout.setUpdatedTime(new Date());
                    wsProjectFieldLayoutMapper.insertWsProjectFieldLayout(fieldLayout);
                }
            }
        }
        return platformManage;
    }

    /**
     * 修改卸料平台设备管理 
     *
     * @param wsUnloadingPlatformManage 卸料平台设备管理 
     * @return 结果
     */
    @Override
    @Transactional
    public int updateWsUnloadingPlatformManage(WsUnloadingPlatformManage wsUnloadingPlatformManage)
    {
        WsUnloadingPlatformManage unloadingPlatformManage = wsUnloadingPlatformManageMapper.selectWsUnloadingPlatformManageById(wsUnloadingPlatformManage.getId());
        if(StringUtils.isNotEmpty(unloadingPlatformManage.getDeviceNumber())){
            WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
            wsProjectEquipment.setEnableStatus(0);
            wsProjectEquipment.setEquipmentType(7);
            wsProjectEquipment.setSerialNumber(unloadingPlatformManage.getDeviceNumber());
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }

        int platformManage = wsUnloadingPlatformManageMapper.updateWsUnloadingPlatformManage(wsUnloadingPlatformManage);
        if(StringUtils.isNotEmpty(wsUnloadingPlatformManage.getDeviceNumber())){
            WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
            wsProjectEquipment.setEnableStatus(1);
            wsProjectEquipment.setEquipmentType(7);
            wsProjectEquipment.setSerialNumber(wsUnloadingPlatformManage.getDeviceNumber());
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }
        WsProjectFieldLayout fieldLayout = new WsProjectFieldLayout();
        if(wsUnloadingPlatformManage.getFieldlayoutId() != null){
            if(StringUtils.isNotEmpty(wsUnloadingPlatformManage.getFieldlayoutAddress())){
                fieldLayout.setId(wsUnloadingPlatformManage.getFieldlayoutId());
                fieldLayout.setProjectId(wsUnloadingPlatformManage.getProjectId());
                fieldLayout.setEquipmentType(3);
                fieldLayout.setFieldlayoutAddress(wsUnloadingPlatformManage.getFieldlayoutAddress());
                fieldLayout.setUpdatedTime(new Date());
                wsProjectFieldLayoutMapper.updateWsProjectFieldLayout(fieldLayout);
            }
        }else{
            if(StringUtils.isNotEmpty(wsUnloadingPlatformManage.getFieldlayoutAddress())){
                fieldLayout.setProjectId(wsUnloadingPlatformManage.getProjectId());
                fieldLayout.setEquipmentType(3);
                fieldLayout.setFieldlayoutAddress(wsUnloadingPlatformManage.getFieldlayoutAddress());
                fieldLayout.setCreatedTime(new Date());
                fieldLayout.setUpdatedTime(new Date());
                wsProjectFieldLayoutMapper.insertWsProjectFieldLayout(fieldLayout);
            }
        }
        return platformManage;
    }

    /**
     * 批量删除卸料平台设备管理 
     *
     * @param ids 需要删除的卸料平台设备管理 ID
     * @return 结果
     */
    @Override
    public int deleteWsUnloadingPlatformManageByIds(Integer[] ids)
    {
        return wsUnloadingPlatformManageMapper.deleteWsUnloadingPlatformManageByIds(ids);
    }

    /**
     * 删除卸料平台设备管理 信息
     *
     * @param id 卸料平台设备管理 ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteWsUnloadingPlatformManageById(Integer id)
    {
        WsUnloadingPlatformManage unloadingPlatformManage = wsUnloadingPlatformManageMapper.selectWsUnloadingPlatformManageById(id);
        if(StringUtils.isNotEmpty(unloadingPlatformManage.getDeviceNumber())){
            WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
            wsProjectEquipment.setEnableStatus(0);
            wsProjectEquipment.setEquipmentType(7);
            wsProjectEquipment.setSerialNumber(unloadingPlatformManage.getDeviceNumber());
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }
        return wsUnloadingPlatformManageMapper.deleteWsUnloadingPlatformManageById(id);
    }

    @Override
    public List<WsProjectEquipment> listMonitoringEquipment(WsProjectEquipment wsProjectEquipment) {
        List<WsProjectEquipment> listProjectEquipment = new ArrayList<WsProjectEquipment>();
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                wsProjectEquipment.setProjectId(list.get(i).getId());
            }
            wsProjectEquipment.setEquipmentType(7);
            wsProjectEquipment.setEnableStatus(0);
            listProjectEquipment = wsProjectEquipmentMapper.selectWsProjectEquipmentList(wsProjectEquipment);
        }
        return listProjectEquipment;
    }

    @Override
    public int insertWsProjectFieldLayout(WsProjectFieldLayout wsProjectFieldLayout) {
        if(wsProjectFieldLayout.getId() != null){
            return wsProjectFieldLayoutMapper.updateWsProjectFieldLayout(wsProjectFieldLayout);
        }else{
            wsProjectFieldLayout.setEquipmentType(3);
            wsProjectFieldLayout.setIsDelete("0");
            return wsProjectFieldLayoutMapper.insertWsProjectFieldLayout(wsProjectFieldLayout);
        }
    }

    /**
     * 删除塔吊设备位置
     *
     * @param id 项目塔吊设备管理
     * @return 结果
     */
    @Override
    public int editUnloadingPlatformManage(String id) {
        WsUnloadingPlatformManage wsUnloadingPlatformManage = new WsUnloadingPlatformManage();
        wsUnloadingPlatformManage.setId(Integer.parseInt(id));
        wsUnloadingPlatformManage.setCoordinateY("");
        wsUnloadingPlatformManage.setCoordinateX("");
        wsUnloadingPlatformManage.setRadius("");
        return wsUnloadingPlatformManageMapper.editUnloadingPlatformManage(wsUnloadingPlatformManage);
    }

    @Override
    public List<WsUnloadingPlatformManage> selectlistUnloadingPlatformManage(WsUnloadingPlatformManage wsUnloadingPlatformManage) {
        List<WsUnloadingPlatformManage> listUnloadingPlatformManage = new ArrayList<WsUnloadingPlatformManage>();
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                wsUnloadingPlatformManage.setProjectId(list.get(i).getId());
            }
            listUnloadingPlatformManage = wsUnloadingPlatformManageMapper.selectlistUnloadingPlatformManage(wsUnloadingPlatformManage);
        }
        return listUnloadingPlatformManage;
    }

    @Override
    public int insertPosition(WsUnloadingPlatformManage wsUnloadingPlatformManage) {
        List<WsUnloadingPlatformManage> wsUnloadingPlatformManageList = wsUnloadingPlatformManage.getWsUnloadingPlatformManageList();
        int platform = 0;
        for (int i = 0; i < wsUnloadingPlatformManageList.size(); i++) {
            WsUnloadingPlatformManage platformManage = wsUnloadingPlatformManageList.get(i);
            platform = wsUnloadingPlatformManageMapper.updateWsUnloadingPlatformManageList(platformManage);
        }
        return platform;
    }

    /**
     * 查询卸料平台-实时监控-设备数量信息
     * @param wsUnloadingPlatformManageDTO
     * @return
     */
    @Override
    public WsUnloadingPlatformManageDTO selectRealTimeMonitoring(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO) {
        if(wsUnloadingPlatformManageDTO.getProjectId() != null){
            List<WsUnloadingPlatformManage> platformManageList = wsUnloadingPlatformManageMapper.selectRealTimeMonitoring(wsUnloadingPlatformManageDTO);
            wsUnloadingPlatformManageDTO.setEquipmentNum(platformManageList.size());//实时监控：在场设备数

            List<WsUnloadingPlatformManageDTO> platformList = wsUnloadingPlatformManageMapper.selectRealTimeMonitoringUnloadingPlatform(wsUnloadingPlatformManageDTO);
            if(platformList.size() > 0){
                LinkedList<String> linkedlist = new LinkedList<String>();
                for (int k = 0; k < platformList.size(); k++) {
                    linkedlist.add(platformList.get(k).getMonitoringEquipmentNo());
                }
                String[] monitoringEquipmentNos = linkedlist.toArray(new String[linkedlist.size()]);
                WsUnloadingPlatformManageDTO manageDTO = selectEquipmentIdleRate(monitoringEquipmentNos);
                if(manageDTO != null){
                    wsUnloadingPlatformManageDTO.setEquipmentIdleRate(manageDTO.getEquipmentIdleRate());//实时监控：当前设备空闲率
                    wsUnloadingPlatformManageDTO.setCallPoliceEquipmentNum(manageDTO.getCallPoliceEquipmentNum());//实时监控：当前报警设备数
                }else{
                    wsUnloadingPlatformManageDTO.setEquipmentIdleRate("0.0");//实时监控：当前设备空闲率
                    wsUnloadingPlatformManageDTO.setCallPoliceEquipmentNum("0");//实时监控：当前报警设备数
                }
            }else{
                wsUnloadingPlatformManageDTO.setEquipmentIdleRate("0");//实时监控：当前设备空闲率
                wsUnloadingPlatformManageDTO.setCallPoliceEquipmentNum("0");//实时监控：当前报警设备数
            }
        }else{
            wsUnloadingPlatformManageDTO.setEquipmentNum(0);//实时监控：在场设备数
            wsUnloadingPlatformManageDTO.setEquipmentIdleRate("0.0");//实时监控：当前设备空闲率
            wsUnloadingPlatformManageDTO.setCallPoliceEquipmentNum("0");//实时监控：当前报警设备数
            wsUnloadingPlatformManageDTO.setEquipmentIdleRate("0");//实时监控：当前设备空闲率
            wsUnloadingPlatformManageDTO.setCallPoliceEquipmentNum("0");//实时监控：当前报警设备数
        }
        return wsUnloadingPlatformManageDTO;
    }

    public WsUnloadingPlatformManageDTO selectEquipmentIdleRate(String[] monitoringEquipmentNos) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsUnloadingPlatformManageDTO manageDTO = wsUnloadingPlatformManageMapper.selectEquipmentIdleRate(monitoringEquipmentNos);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return manageDTO;
    }

    /**
     * 查询卸料平台-实时监控-实时数据信息
     * @param wsUnloadingPlatformManageDTO
     * @return
     */
    @Override
    public List<WsUnloadingPlatformManageDTO> selectRealTimeMonitoringDataList(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO) {
        List<WsUnloadingPlatformManageDTO> manageList = new ArrayList<WsUnloadingPlatformManageDTO>();
        List<WsUnloadingPlatformManage> platformManageList = new ArrayList<WsUnloadingPlatformManage>();
        if(wsUnloadingPlatformManageDTO.getProjectId() != null){
            //查询项目下所有卸料设备信息
            platformManageList = wsUnloadingPlatformManageMapper.selectRealTimeMonitorings(wsUnloadingPlatformManageDTO);
        }
        if(platformManageList.size() > 0){
            for (int i = 0; i < platformManageList.size(); i++) {
               if(StringUtils.isNotEmpty(platformManageList.get(i).getDeviceNumber())){//根据监测设备编号查询设备实时数据
                   WsUnloadingPlatformManageDTO manageDTO = selectRealTimeMonitoringData(platformManageList.get(i).getDeviceNumber());
                   WsUnloadingPlatformManageDTO platformManageDTO = new WsUnloadingPlatformManageDTO();
                   platformManageDTO.setEquipmentName(platformManageList.get(i).getEquipmentName());
                   platformManageDTO.setMonitoringEquipmentNo(platformManageList.get(i).getDeviceNumber());
                   platformManageDTO.setEnableDisable(platformManageList.get(i).getEnableDisable());
                   if(manageDTO != null){
                       platformManageDTO.setWeight(manageDTO.getWeight());
                       platformManageDTO.setVoltage(manageDTO.getVoltage());
                       platformManageDTO.setWarningStatus(manageDTO.getWarningStatus());
                   }else{
                       platformManageDTO.setWeight("0");
                       platformManageDTO.setVoltage("0.0");
                       platformManageDTO.setWarningStatus("0");
                   }
                   manageList.add(platformManageDTO);
               }else{
                   WsUnloadingPlatformManageDTO platformManageDTO = new WsUnloadingPlatformManageDTO();
                   platformManageDTO.setEquipmentName(platformManageList.get(i).getEquipmentName());
                   platformManageDTO.setMonitoringEquipmentNo(platformManageList.get(i).getDeviceNumber());
                   platformManageDTO.setEnableDisable(platformManageList.get(i).getEnableDisable());
                   platformManageDTO.setWeight("0");
                   platformManageDTO.setVoltage("0.0");
                   platformManageDTO.setWarningStatus("0");
                   manageList.add(platformManageDTO);
               }
            }
        }
        return manageList;
    }

    public WsUnloadingPlatformManageDTO selectRealTimeMonitoringData(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsUnloadingPlatformManageDTO manageDTO = wsUnloadingPlatformManageMapper.selectRealTimeMonitoringData(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return manageDTO;
    }

    /**
     * 查询卸料平台-监控详情-设备信息和实时数据信息
     * @param wsUnloadingPlatformManageDTO
     * @return
     */
    @Override
    public WsUnloadingPlatformManageDTO selectEquipmentRealTimeInformation(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO) {
        WsUnloadingPlatformManageDTO unloadingPlatformManage = new WsUnloadingPlatformManageDTO();
        wsUnloadingPlatformManageDTO = wsUnloadingPlatformManageMapper.selectUnloadingEquipmentName(wsUnloadingPlatformManageDTO.getMonitoringEquipmentNo());
        WsUnloadingPlatformManageDTO platformManage = selectRealTimeInformation(wsUnloadingPlatformManageDTO.getUnloadingMonitoringEquipmentNo());
        if(platformManage != null){
            unloadingPlatformManage.setCurrentLoad(platformManage.getCurrentLoad());
            unloadingPlatformManage.setMaxAllowableLoad(platformManage.getMaxAllowableLoad());
            unloadingPlatformManage.setBatteryPercentage(platformManage.getBatteryPercentage());
            unloadingPlatformManage.setRealUpdateTime(platformManage.getRealUpdateTime());
            unloadingPlatformManage.setUnloadingEquipmentName(wsUnloadingPlatformManageDTO.getUnloadingEquipmentName());
            unloadingPlatformManage.setUnloadingMonitoringEquipmentNo(wsUnloadingPlatformManageDTO.getMonitoringEquipmentNo());
        }else{
            unloadingPlatformManage.setCurrentLoad("0");
            unloadingPlatformManage.setMaxAllowableLoad("0");
            unloadingPlatformManage.setBatteryPercentage("0.0");
            unloadingPlatformManage.setRealUpdateTime("");
            unloadingPlatformManage.setUnloadingEquipmentName(wsUnloadingPlatformManageDTO.getUnloadingEquipmentName());
            unloadingPlatformManage.setUnloadingMonitoringEquipmentNo(wsUnloadingPlatformManageDTO.getMonitoringEquipmentNo());
        }
        return unloadingPlatformManage;
    }

    public WsUnloadingPlatformManageDTO selectRealTimeInformation(String monitoringEquipmentNo) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        WsUnloadingPlatformManageDTO manageDTO = wsUnloadingPlatformManageMapper.selectRealTimeInformation(monitoringEquipmentNo);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return manageDTO;
    }

    /**
     * 查询卸料平台-监控详情-工作记录-今日
     * @param wsUnloadingPlatformManageDTO
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsUnloadingPlatformManageDTO> selectTodayWorkRecordList(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO) {
        return wsUnloadingPlatformManageMapper.selectTodayWorkRecordList(wsUnloadingPlatformManageDTO);
    }

    /**
     * 查询卸料平台-监控详情-工作记录-15天
     * @param wsUnloadingPlatformManageDTO
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsUnloadingPlatformManageDTO> selectFifteenWorkRecordList(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO) {
        return wsUnloadingPlatformManageMapper.selectFifteenWorkRecordList(wsUnloadingPlatformManageDTO);
    }

    /**
     * 查询卸料平台-监控详情-告警记录-15天
     * @param wsUnloadingPlatformManageDTO
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsUnloadingPlatformManageDTO> selectFifteenAlarmLogList(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO) {
        return wsUnloadingPlatformManageMapper.selectFifteenAlarmLogList(wsUnloadingPlatformManageDTO);
    }

    /**
     * 查询卸料平台-监控详情-工作记录-更多详情
     * @param wsUnloadingPlatformManageDTO
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<WsUnloadingPlatformManageDTO> selectEquipmentMoredetailsList(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO) {
        return wsUnloadingPlatformManageMapper.selectEquipmentMoredetailsList(wsUnloadingPlatformManageDTO);
    }

    @Override
    public int updateWsUnloadingPlatformManages(WsUnloadingPlatformManage wsUnloadingPlatformManage) {
        return wsUnloadingPlatformManageMapper.updateWsUnloadingPlatformManages(wsUnloadingPlatformManage);
    }

    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public WsUnloadingPlatformManageDTO selectTodayAlarmLog(WsUnloadingPlatformManageDTO wsUnloadingPlatformManageDTO) {
        return wsUnloadingPlatformManageMapper.selectTodayAlarmLog(wsUnloadingPlatformManageDTO);
    }
}
