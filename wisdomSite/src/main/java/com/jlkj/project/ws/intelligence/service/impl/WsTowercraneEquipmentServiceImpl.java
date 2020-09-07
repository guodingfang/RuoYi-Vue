package com.jlkj.project.ws.intelligence.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jlkj.common.constant.UserConstants;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsProjectEquipment;
import com.jlkj.project.ws.domain.WsVideoChannelManagement;
import com.jlkj.project.ws.intelligence.domain.WsProjectFieldLayout;
import com.jlkj.project.ws.intelligence.domain.WsTowercraneEntranceexitRecord;
import com.jlkj.project.ws.intelligence.mapper.WsProjectFieldLayoutMapper;
import com.jlkj.project.ws.intelligence.mapper.WsTowercraneEntranceexitRecordMapper;
import com.jlkj.project.ws.mapper.WsProjectEquipmentMapper;
import com.jlkj.project.ws.mapper.WsVideoChannelManagementMapper;
import com.jlkj.project.ws.service.IWsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.intelligence.mapper.WsTowercraneEquipmentMapper;
import com.jlkj.project.ws.intelligence.domain.WsTowercraneEquipment;
import com.jlkj.project.ws.intelligence.service.IWsTowercraneEquipmentService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 项目塔吊设备管理 Service业务层处理
 *
 * @author gaowei
 * @date 2020-07-07
 */
@Service
public class WsTowercraneEquipmentServiceImpl implements IWsTowercraneEquipmentService
{
    @Autowired
    private WsTowercraneEquipmentMapper wsTowercraneEquipmentMapper;

    @Autowired
    private WsProjectEquipmentMapper wsProjectEquipmentMapper;

    @Autowired
    private WsProjectFieldLayoutMapper wsProjectFieldLayoutMapper;

    @Autowired
    private WsVideoChannelManagementMapper wsVideoChannelManagementMapper;

    @Autowired
    private WsTowercraneEntranceexitRecordMapper wsTowercraneEntranceexitRecordMapper;

    @Autowired
    private IWsProjectService wsProjectService;


    /**
     * 查询项目塔吊设备管理 
     *
     * @param id 项目塔吊设备管理 ID
     * @return 项目塔吊设备管理 
     */
    @Override
    public WsTowercraneEquipment selectWsTowercraneEquipmentById(Integer id)
    {
        return wsTowercraneEquipmentMapper.selectWsTowercraneEquipmentById(id);
    }

    /**
     * 查询项目塔吊设备管理 列表
     *
     * @param wsTowercraneEquipment 项目塔吊设备管理 
     * @return 项目塔吊设备管理 
     */
    @Override
    public List<WsTowercraneEquipment> selectWsTowercraneEquipmentList(WsTowercraneEquipment wsTowercraneEquipment)
    {
        List<WsTowercraneEquipment> listTowercraneEquipment = new ArrayList<WsTowercraneEquipment>();
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                wsTowercraneEquipment.setProjectId(list.get(i).getId());
            }
            listTowercraneEquipment = wsTowercraneEquipmentMapper.selectWsTowercraneEquipmentList(wsTowercraneEquipment);
        }
        return listTowercraneEquipment;
    }

    /**
     * 新增项目塔吊设备管理 
     *
     * @param wsTowercraneEquipment 项目塔吊设备管理 
     * @return 结果
     */
    @Override
    @Transactional
    public int insertWsTowercraneEquipment(WsTowercraneEquipment wsTowercraneEquipment)
    {
        int equipment = 0;
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                wsTowercraneEquipment.setProjectId(list.get(i).getId());
            }
            wsTowercraneEquipment.setEnterData(new Date());
            wsTowercraneEquipment.setPresence(1);
            equipment = wsTowercraneEquipmentMapper.insertWsTowercraneEquipment(wsTowercraneEquipment);
            if(StringUtils.isNotEmpty(wsTowercraneEquipment.getMonitoringEquipment())){
                WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
                wsProjectEquipment.setEnableStatus(1);
                wsProjectEquipment.setEquipmentType(1);
                wsProjectEquipment.setSerialNumber(wsTowercraneEquipment.getMonitoringEquipment());
                wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
            }
            if(wsTowercraneEquipment.getFaceId() != null){
                WsProjectEquipment projectEquipment = wsProjectEquipmentMapper.selectWsProjectEquipmentById(wsTowercraneEquipment.getFaceId());
                WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
                wsProjectEquipment.setEnableStatus(1);
                wsProjectEquipment.setEquipmentType(9);
                wsProjectEquipment.setSerialNumber(projectEquipment.getSerialNumber());
                wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
            }
            WsProjectFieldLayout fieldLayout = new WsProjectFieldLayout();
            if(wsTowercraneEquipment.getFieldlayoutId() != null){
                if(StringUtils.isNotEmpty(wsTowercraneEquipment.getFieldlayoutAddress())){
                    fieldLayout.setId(wsTowercraneEquipment.getFieldlayoutId());
                    fieldLayout.setProjectId(wsTowercraneEquipment.getProjectId());
                    fieldLayout.setEquipmentType(1);
                    fieldLayout.setFieldlayoutAddress(wsTowercraneEquipment.getFieldlayoutAddress());
                    fieldLayout.setUpdatedTime(new Date());
                    wsProjectFieldLayoutMapper.updateWsProjectFieldLayout(fieldLayout);
                }
            }else{
                if(StringUtils.isNotEmpty(wsTowercraneEquipment.getFieldlayoutAddress())){
                    fieldLayout.setProjectId(wsTowercraneEquipment.getProjectId());
                    fieldLayout.setEquipmentType(1);
                    fieldLayout.setFieldlayoutAddress(wsTowercraneEquipment.getFieldlayoutAddress());
                    fieldLayout.setCreatedTime(new Date());
                    fieldLayout.setUpdatedTime(new Date());
                    wsProjectFieldLayoutMapper.insertWsProjectFieldLayout(fieldLayout);
                }
            }
            WsTowercraneEntranceexitRecord entranceexitRecord = new WsTowercraneEntranceexitRecord();
            entranceexitRecord.setProjectId(wsTowercraneEquipment.getProjectId());
            entranceexitRecord.setPresence(wsTowercraneEquipment.getPresence());
            entranceexitRecord.setTowercraneEquipmentId(wsTowercraneEquipment.getId());
            entranceexitRecord.setEnterData(new Date());
            wsTowercraneEntranceexitRecordMapper.insertWsTowercraneEntranceexitRecord(entranceexitRecord);
        }
        return equipment;
    }

    /**
     * 修改项目塔吊设备管理 
     *
     * @param wsTowercraneEquipment 项目塔吊设备管理 
     * @return 结果
     */
    @Override
    @Transactional
    public int updateWsTowercraneEquipment(WsTowercraneEquipment wsTowercraneEquipment)
    {
        WsTowercraneEquipment equipment = wsTowercraneEquipmentMapper.selectWsTowercraneEquipmentById(wsTowercraneEquipment.getId());
        if(StringUtils.isNotEmpty(equipment.getMonitoringEquipment())){
            WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
            wsProjectEquipment.setEnableStatus(0);
            wsProjectEquipment.setEquipmentType(1);
            wsProjectEquipment.setSerialNumber(equipment.getMonitoringEquipment());
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }
        if(equipment.getFaceId() != null){
            WsProjectEquipment projectEquipment = wsProjectEquipmentMapper.selectWsProjectEquipmentById(equipment.getFaceId());
            WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
            wsProjectEquipment.setEnableStatus(0);
            wsProjectEquipment.setEquipmentType(9);
            wsProjectEquipment.setSerialNumber(projectEquipment.getSerialNumber());
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }

        int updateEquipment = wsTowercraneEquipmentMapper.updateWsTowercraneEquipment(wsTowercraneEquipment);
        if(StringUtils.isNotEmpty(wsTowercraneEquipment.getMonitoringEquipment())){
            WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
            wsProjectEquipment.setEnableStatus(1);
            wsProjectEquipment.setEquipmentType(1);
            wsProjectEquipment.setSerialNumber(wsTowercraneEquipment.getMonitoringEquipment());
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }
        if(wsTowercraneEquipment.getFaceId() != null){
            WsProjectEquipment projectEquipment = wsProjectEquipmentMapper.selectWsProjectEquipmentById(wsTowercraneEquipment.getFaceId());
            WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
            wsProjectEquipment.setEnableStatus(1);
            wsProjectEquipment.setEquipmentType(9);
            wsProjectEquipment.setSerialNumber(projectEquipment.getSerialNumber());
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }
        WsProjectFieldLayout fieldLayout = new WsProjectFieldLayout();
        if(wsTowercraneEquipment.getFieldlayoutId() != null){
            if(StringUtils.isNotEmpty(wsTowercraneEquipment.getFieldlayoutAddress())){
                fieldLayout.setId(wsTowercraneEquipment.getFieldlayoutId());
                fieldLayout.setProjectId(wsTowercraneEquipment.getProjectId());
                fieldLayout.setEquipmentType(1);
                fieldLayout.setFieldlayoutAddress(wsTowercraneEquipment.getFieldlayoutAddress());
                fieldLayout.setUpdatedTime(new Date());
                wsProjectFieldLayoutMapper.updateWsProjectFieldLayout(fieldLayout);
            }
        }else{
            if(StringUtils.isNotEmpty(wsTowercraneEquipment.getFieldlayoutAddress())){
                fieldLayout.setProjectId(wsTowercraneEquipment.getProjectId());
                fieldLayout.setEquipmentType(1);
                fieldLayout.setFieldlayoutAddress(wsTowercraneEquipment.getFieldlayoutAddress());
                fieldLayout.setCreatedTime(new Date());
                fieldLayout.setUpdatedTime(new Date());
                wsProjectFieldLayoutMapper.insertWsProjectFieldLayout(fieldLayout);
            }
        }
        return updateEquipment;
    }

    /**
     * 批量删除项目塔吊设备管理 
     *
     * @param ids 需要删除的项目塔吊设备管理 ID
     * @return 结果
     */
    @Override
    public int deleteWsTowercraneEquipmentByIds(Integer[] ids)
    {
        return wsTowercraneEquipmentMapper.deleteWsTowercraneEquipmentByIds(ids);
    }

    /**
     * 删除项目塔吊设备管理 信息
     *
     * @param id 项目塔吊设备管理 ID
     * @return 结果
     */
    @Override
    public int deleteWsTowercraneEquipmentById(Integer id)
    {
        return wsTowercraneEquipmentMapper.deleteWsTowercraneEquipmentById(id);
    }

    @Override
    @Transactional
    public int departureWsTowercraneEquipment(Integer id) {
        WsTowercraneEquipment towercraneEquipment = wsTowercraneEquipmentMapper.selectWsTowercraneEquipmentById(id);
        if(StringUtils.isNotEmpty(towercraneEquipment.getMonitoringEquipment())){
            WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
            wsProjectEquipment.setSerialNumber(towercraneEquipment.getMonitoringEquipment());
            wsProjectEquipment.setEnableStatus(0);
            wsProjectEquipment.setEquipmentType(1);
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }
        if(towercraneEquipment.getFaceId() != null){
            WsProjectEquipment projectEquipment = wsProjectEquipmentMapper.selectWsProjectEquipmentById(towercraneEquipment.getFaceId());
            WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
            wsProjectEquipment.setSerialNumber(projectEquipment.getSerialNumber());
            wsProjectEquipment.setEnableStatus(0);
            wsProjectEquipment.setEquipmentType(9);
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }
        towercraneEquipment.setMonitoringEquipment("");
        towercraneEquipment.setFaceId(null);
        towercraneEquipment.setCliverVideoId(null);
        towercraneEquipment.setPresence(2);
        towercraneEquipment.setExitData(new Date());

        WsTowercraneEntranceexitRecord entranceexitRecord = new WsTowercraneEntranceexitRecord();
        entranceexitRecord.setProjectId(towercraneEquipment.getProjectId());
        entranceexitRecord.setPresence(towercraneEquipment.getPresence());
        entranceexitRecord.setTowercraneEquipmentId(towercraneEquipment.getId());
        entranceexitRecord.setEnterData(new Date());
        wsTowercraneEntranceexitRecordMapper.insertWsTowercraneEntranceexitRecord(entranceexitRecord);

        return wsTowercraneEquipmentMapper.updateWsTowercraneEquipment(towercraneEquipment);


    }

    @Override
    @Transactional
    public int reentryWsTowercraneEquipment(WsTowercraneEquipment wsTowercraneEquipment) {
        wsTowercraneEquipment.setEnterData(new Date());
        wsTowercraneEquipment.setPresence(1);
        int updateEquipment = wsTowercraneEquipmentMapper.updateWsTowercraneEquipment(wsTowercraneEquipment);

        WsTowercraneEntranceexitRecord entranceexitRecord = new WsTowercraneEntranceexitRecord();
        entranceexitRecord.setProjectId(wsTowercraneEquipment.getProjectId());
        entranceexitRecord.setPresence(wsTowercraneEquipment.getPresence());
        entranceexitRecord.setTowercraneEquipmentId(wsTowercraneEquipment.getId());
        entranceexitRecord.setEnterData(new Date());
        wsTowercraneEntranceexitRecordMapper.insertWsTowercraneEntranceexitRecord(entranceexitRecord);

        if(StringUtils.isNotEmpty(wsTowercraneEquipment.getMonitoringEquipment())){
            WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
            wsProjectEquipment.setSerialNumber(wsTowercraneEquipment.getMonitoringEquipment());
            wsProjectEquipment.setEnableStatus(1);
            wsProjectEquipment.setEquipmentType(1);
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }
        if(wsTowercraneEquipment.getFaceId() != null){
            WsProjectEquipment projectEquipment = wsProjectEquipmentMapper.selectWsProjectEquipmentById(wsTowercraneEquipment.getFaceId());
            WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
            wsProjectEquipment.setSerialNumber(projectEquipment.getSerialNumber());
            wsProjectEquipment.setEnableStatus(1);
            wsProjectEquipment.setEquipmentType(9);
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }
        WsProjectFieldLayout fieldLayout = new WsProjectFieldLayout();
        if(wsTowercraneEquipment.getFieldlayoutId() != null){
            if(StringUtils.isNotEmpty(wsTowercraneEquipment.getFieldlayoutAddress())){
                fieldLayout.setId(wsTowercraneEquipment.getFieldlayoutId());
                fieldLayout.setProjectId(wsTowercraneEquipment.getProjectId());
                fieldLayout.setEquipmentType(1);
                fieldLayout.setFieldlayoutAddress(wsTowercraneEquipment.getFieldlayoutAddress());
                fieldLayout.setUpdatedTime(new Date());
                wsProjectFieldLayoutMapper.updateWsProjectFieldLayout(fieldLayout);
            }
        }else{
            if(StringUtils.isNotEmpty(wsTowercraneEquipment.getFieldlayoutAddress())){
                fieldLayout.setProjectId(wsTowercraneEquipment.getProjectId());
                fieldLayout.setEquipmentType(1);
                fieldLayout.setFieldlayoutAddress(wsTowercraneEquipment.getFieldlayoutAddress());
                fieldLayout.setCreatedTime(new Date());
                fieldLayout.setUpdatedTime(new Date());
                wsProjectFieldLayoutMapper.insertWsProjectFieldLayout(fieldLayout);
            }
        }
        return updateEquipment;
    }

    @Override
    public int insertWsProjectFieldLayout(WsProjectFieldLayout wsProjectFieldLayout) {
        if(wsProjectFieldLayout.getId() != null){
            return wsProjectFieldLayoutMapper.updateWsProjectFieldLayout(wsProjectFieldLayout);
        }else{
            wsProjectFieldLayout.setEquipmentType(1);
            wsProjectFieldLayout.setIsDelete("0");
            return wsProjectFieldLayoutMapper.insertWsProjectFieldLayout(wsProjectFieldLayout);
        }
    }

    @Override
    public List<WsTowercraneEquipment> selectlistEquipment(WsTowercraneEquipment wsTowercraneEquipment) {
        List<WsTowercraneEquipment> listTowercraneEquipment = new ArrayList<WsTowercraneEquipment>();
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                wsTowercraneEquipment.setProjectId(list.get(i).getId());
            }
            listTowercraneEquipment = wsTowercraneEquipmentMapper.selectlistEquipment(wsTowercraneEquipment);
        }
        return listTowercraneEquipment;
    }

    @Override
    public int insertPosition(WsTowercraneEquipment wsTowercraneEquipment) {
        List<WsTowercraneEquipment> wsTowercraneEquipmentList = wsTowercraneEquipment.getWsTowercraneEquipmentList();
        for(int i = 0; i < wsTowercraneEquipmentList.size(); i++){
            wsTowercraneEquipment = wsTowercraneEquipmentList.get(i);
            wsTowercraneEquipmentMapper.editWsTowercraneEquipment(wsTowercraneEquipment);
        }
        return 1;
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
            wsProjectEquipment.setEquipmentType(1);
            wsProjectEquipment.setEnableStatus(0);
            listProjectEquipment = wsProjectEquipmentMapper.selectWsProjectEquipmentList(wsProjectEquipment);
        }
        return listProjectEquipment;
    }

    @Override
    public List<WsProjectEquipment> listTowerCraneFace(WsProjectEquipment wsProjectEquipment) {
        List<WsProjectEquipment> listProjectEquipment = new ArrayList<WsProjectEquipment>();
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                wsProjectEquipment.setProjectId(list.get(i).getId());
            }
            wsProjectEquipment.setEquipmentType(9);
            wsProjectEquipment.setEnableStatus(0);
            listProjectEquipment = wsProjectEquipmentMapper.selectWsProjectEquipmentList(wsProjectEquipment);
        }
        return listProjectEquipment;
    }

    @Override
    public List<WsVideoChannelManagement> listHookVideo(WsVideoChannelManagement wsVideoChannelManagement) {
        List<WsVideoChannelManagement> listVideoChannel = new ArrayList<WsVideoChannelManagement>();
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                wsVideoChannelManagement.setProjectId(list.get(i).getId());
            }
            listVideoChannel = wsVideoChannelManagementMapper.selectListHookVideo(wsVideoChannelManagement);
        }
        return listVideoChannel;
    }

    @Override
    public List<WsTowercraneEquipment> listHookVideoRepeat(WsTowercraneEquipment wsTowercraneEquipment) {
        return wsTowercraneEquipmentMapper.listHookVideoRepeat(wsTowercraneEquipment);
    }

    /**
     * 删除塔吊设备位置
     *
     * @param id 项目塔吊设备管理
     * @return 结果
     */
    @Override
    @Transactional
    public int editWsTowercraneEquipment(String id)
    {
        WsTowercraneEquipment wsTowercraneEquipment = new WsTowercraneEquipment();
        wsTowercraneEquipment.setId(Integer.parseInt(id));
        wsTowercraneEquipment.setCoordinateY("");
        wsTowercraneEquipment.setCoordinateX("");
        wsTowercraneEquipment.setRadius("");
        return wsTowercraneEquipmentMapper.editWsTowercraneEquipment(wsTowercraneEquipment);
    }

    @Override
    public String selfNumbering(WsTowercraneEquipment wsTowercraneEquipment) {
        Long problemTypeId = StringUtils.isNull(wsTowercraneEquipment.getId()) ? -1L : wsTowercraneEquipment.getId();
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                wsTowercraneEquipment.setProjectId(list.get(i).getId());
            }
        }
        WsTowercraneEquipment towercraneEquipment = wsTowercraneEquipmentMapper.getSelfNumbering(wsTowercraneEquipment);
        if (StringUtils.isNotNull(towercraneEquipment) && towercraneEquipment.getId().longValue() != problemTypeId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

}
