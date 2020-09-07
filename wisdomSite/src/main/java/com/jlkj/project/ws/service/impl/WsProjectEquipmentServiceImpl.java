package com.jlkj.project.ws.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jlkj.common.constant.IntelligentMonitoringConstants;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.framework.aspectj.lang.enums.DataSourceType;
import com.jlkj.framework.datasource.DynamicDataSourceContextHolder;
import com.jlkj.project.system.domain.SysUser;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.mapper.WsProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsProjectEquipmentMapper;
import com.jlkj.project.ws.domain.WsProjectEquipment;
import com.jlkj.project.ws.service.IWsProjectEquipmentService;

/**
 * 工程设备绑定 Service业务层处理
 *
 * @author pyy
 * @date 2020-07-02
 */
@Service
public class WsProjectEquipmentServiceImpl implements IWsProjectEquipmentService {
    @Autowired
    private WsProjectEquipmentMapper wsProjectEquipmentMapper;

    /**
     * 查询工程设备绑定
     *
     * @param id 工程设备绑定 ID
     * @return 工程设备绑定
     */
    @Override
    public WsProjectEquipment selectWsProjectEquipmentById(Integer id) {
        return wsProjectEquipmentMapper.selectWsProjectEquipmentById(id);
    }

    /**
     * 查询工程设备绑定 列表
     *
     * @param wsProjectEquipment 工程设备绑定
     * @return 工程设备绑定
     */
    @Override
    public List<WsProjectEquipment> selectWsProjectEquipmentList(WsProjectEquipment wsProjectEquipment) {
        List<WsProjectEquipment> wsProjectEquipments = new ArrayList<>();
        List<WsProject> projects = SecurityUtils.getProjectList();
        if (projects != null && projects.size() > 0) {
            List<Integer> projectIds = new ArrayList<>();
            projects.forEach(project -> projectIds.add(project.getId()));
            wsProjectEquipment.setProjectIds(projectIds);
            wsProjectEquipments = wsProjectEquipmentMapper.selectWsProjectEquipmentList(wsProjectEquipment);
            if (StringUtils.isNotEmpty(wsProjectEquipments)) {
                List<WsProjectEquipment> wsProjectEquipments1 = selectOnlineStatus(wsProjectEquipments);
                if (wsProjectEquipments1 != null) {
                    for (WsProjectEquipment equipment : wsProjectEquipments) {
                        for (WsProjectEquipment projectEquipment : wsProjectEquipments1) {
                            if (equipment.getSerialNumber().equals(projectEquipment.getSerialNumber())) {
                                equipment.setOnlineStatus(projectEquipment.getOnlineStatus());
                            }
                        }
                    }
                    return wsProjectEquipments;
                }
            }
        }
        return wsProjectEquipments;
    }

    /**
     * 查询工程设备在线状态
     *
     * @param wsProjectEquipment
     * @return 在线状态
     */
    @Override
    public WsProjectEquipment selectWsProjectEquipmentOnlineStatus(WsProjectEquipment wsProjectEquipment) {
        if (wsProjectEquipment.getEquipmentType() == IntelligentMonitoringConstants.SJJ_RL) {
            wsProjectEquipment.setEquipmentType(IntelligentMonitoringConstants.SJJ);
        }
        List<WsProjectEquipment> equipmentList = new ArrayList<>();
        equipmentList.add(wsProjectEquipment);
        List<WsProjectEquipment> wsProjectEquipments = selectOnlineStatus(equipmentList);
        if (!wsProjectEquipments.isEmpty()) {
            for (int i = 0; i < wsProjectEquipments.size(); i++) {
                wsProjectEquipment.setOnlineStatus(wsProjectEquipments.get(i).getOnlineStatus());
            }
            return wsProjectEquipment;
        }
        return null;
    }

    /**
     * 查询是否存在工程设备
     *
     * @param wsProjectEquipment
     * @return 在线状态
     */
    @Override
    public boolean isExistSerialNumber(WsProjectEquipment wsProjectEquipment) {
        WsProjectEquipment wsProjectEquipment1 = wsProjectEquipmentMapper.selectWsProjectEquipmentBySerialNumber(wsProjectEquipment.getSerialNumber());
        if (wsProjectEquipment1 != null) {
            if (wsProjectEquipment.getSerialNumber().equals(wsProjectEquipment1.getSerialNumber())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 新增工程设备绑定
     *
     * @param wsProjectEquipment 工程设备绑定
     * @return 结果
     */
    @Override
    public int insertWsProjectEquipment(WsProjectEquipment wsProjectEquipment) {
        boolean flag = isExistSerialNumber(wsProjectEquipment);
        if (!flag) {
            return 2;
        }

        if (wsProjectEquipment.getEquipmentType().equals(IntelligentMonitoringConstants.SJJ_RL)) {
            Integer equipmentType = wsProjectEquipment.getEquipmentType();
            wsProjectEquipment.setEquipmentType(IntelligentMonitoringConstants.SJJ);
            boolean result = findBindingStatusBySerialNumber(wsProjectEquipment);
            if (!result) {
                return 0;
            }
            wsProjectEquipment.setEquipmentType(equipmentType);
        }
        flag = findBindingStatusBySerialNumber(wsProjectEquipment);
        if (flag) {
            return wsProjectEquipmentMapper.insertWsProjectEquipment(wsProjectEquipment);
        }
        return 0;
    }

    /**
     * 修改工程设备绑定
     *
     * @param wsProjectEquipment 工程设备绑定
     * @return 结果
     */
    @Override
    public int updateWsProjectEquipmentBySerialNumber(WsProjectEquipment wsProjectEquipment) {
        return wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
    }


    /**
     * 工程设备转场
     *
     * @param wsProjectEquipment 工程设备绑定
     * @return 结果
     */
    @Override
    public int updateProjectNameBySerialNumber(WsProjectEquipment wsProjectEquipment) {
        WsProjectEquipment wsProjectEquipment1 = wsProjectEquipmentMapper.selectWsProjectEquipmentBySerialNumber(wsProjectEquipment.getSerialNumber());
        if (wsProjectEquipment1 != null && wsProjectEquipment1.getEnableStatus() == 0) {
            return wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }
        return 0;
    }

    /**
     * 删除工程设备绑定 信息
     *
     * @param id 工程设备绑定 ID
     * @return 结果
     */
    @Override
    public int deleteWsProjectEquipmentById(Integer id) {
        WsProjectEquipment wsProjectEquipment = wsProjectEquipmentMapper.selectWsProjectEquipmentById(id);
        if (wsProjectEquipment != null) {
            if (wsProjectEquipment.getEnableStatus() == 0) {
                return wsProjectEquipmentMapper.deleteWsProjectEquipmentById(id);
            }
            return 0;
        }
        return -1;
    }


    /**
     * 多数据源查询设备编号和绑定状态
     *
     * @param wsProjectEquipment 设备编号
     * @return 结果
     */
    public boolean findBindingStatusBySerialNumber(WsProjectEquipment wsProjectEquipment) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        int number = wsProjectEquipmentMapper.isExistBySerialNumber(wsProjectEquipment);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return number != 0;
    }

    /**
     * 多数据源查询工程设备在线状态
     *
     * @param wsProjectEquipments 工程设备绑定对象集合
     * @return 在线状态
     */
    public List<WsProjectEquipment> selectOnlineStatus(List<WsProjectEquipment> wsProjectEquipments) {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<WsProjectEquipment> projectEquipments = wsProjectEquipmentMapper.selectWsProjectEquipmentOnlineStatus(wsProjectEquipments);
        DynamicDataSourceContextHolder.clearDataSourceType();
        return projectEquipments;
    }
}
