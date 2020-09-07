package com.jlkj.project.ws.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.constant.Constants;
import com.jlkj.common.constant.IntelligentMonitoringConstants;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.DictUtils;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.ServletUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.spring.SpringUtils;
import com.jlkj.framework.redis.BaiDuApiUtil;
import com.jlkj.framework.redis.RedisCache;
import com.jlkj.framework.security.LoginUser;
import com.jlkj.framework.security.service.TokenService;
import com.jlkj.project.system.domain.SysDictData;
import com.jlkj.project.system.domain.SysOrganizationAccount;
import com.jlkj.project.system.domain.SysUser;
import com.jlkj.project.system.mapper.SysOrganizationAccountMapper;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsProjectEquipment;
import com.jlkj.project.ws.domain.WsVideoEquipmentManagement;
import com.jlkj.project.ws.mapper.WsProjectEquipmentMapper;
import com.jlkj.project.ws.mapper.WsProjectMapper;
import com.jlkj.project.ws.mapper.WsVideoEquipmentManagementMapper;
import com.jlkj.project.ws.service.IWsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目信息 Service业务层处理
 *
 * @author jlkj
 * @date 2020-06-09
 */
@Service
public class WsProjectServiceImpl implements IWsProjectService {
    @Autowired
    private WsProjectMapper wsProjectMapper;

    @Autowired
    private WsProjectEquipmentMapper wsProjectEquipmentMapper;

    @Autowired
    private WsVideoEquipmentManagementMapper wsVideoEquipmentManagementMapper;

    @Autowired
    private SysOrganizationAccountMapper organizationAccountMapper;

    /**
     * 查询项目信息
     *
     * @param id 项目信息 ID
     * @return 项目信息
     */
    @Override
    public WsProject selectWsProjectById(Integer id) {
        return wsProjectMapper.selectWsProjectById(id);
    }


    /**
     * 查询项目信息 列表
     *
     * @param wsProject 项目信息
     * @return 项目信息
     */
    @Override
    public List<WsProject> selectWsProjectList(WsProject wsProject) {
        return SecurityUtils.getProjectList();
    }

    /**
     * 查询项目信息 列表
     *
     * @param wsProject 项目信息
     * @return 项目信息
     */
    @Override
    public List<WsProject> selectWsProjectListByUser(WsProject wsProject) {
        if (wsProject == null) {
            return wsProjectMapper.selectWsProjectList(wsProject);
        }
        List<WsProject> projects = SecurityUtils.getProjectList();
        if (StringUtils.isEmpty(projects)){
            return projects;
        }
        List<Integer> projectIds = new ArrayList<>();
        projects.forEach(project -> projectIds.add(project.getId()));
        wsProject.setProjectIds(projectIds);
        return wsProjectMapper.selectWsProjectList(wsProject);
    }


    /**
     * 查询所有项目信息
     *
     * @return 项目信息
     */
    @Override
    public List<WsProject> selectAllWsProjectList() {
        List<WsProject> projects = SecurityUtils.getProjectList();
        if (StringUtils.isNotEmpty(projects)) {
            List<Integer> projectIds = new ArrayList<>();
            projects.forEach(project -> projectIds.add(project.getId()));
            WsProject wsProject = new WsProject();
            wsProject.setProjectIds(projectIds);
            return wsProjectMapper.selectWsProjectList(wsProject);
        }
        return projects;
    }

    /**
     * 新增项目信息
     *
     * @param wsProject 项目信息
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertWsProject(WsProject wsProject) {
        //判断项目名是否重复
        WsProject wsProject1 = new WsProject();
        wsProject1.setProjectName(wsProject.getProjectName());
        List<WsProject> project = wsProjectMapper.selectWsProjectList(wsProject1);
        if (StringUtils.isNotEmpty(project)) {
            return 0;
        }
        JSONObject jsonObject = BaiDuApiUtil.getAddress(wsProject.getLongitude(), wsProject.getLatitude());
        String status = jsonObject.getString("status");
        if (status.equals("0")) {
            JSONObject result = jsonObject.getJSONObject("result");
            JSONObject addressComponent = result.getJSONObject("addressComponent");
            String province = addressComponent.getString("province");
            String city = addressComponent.getString("city");
            String district = addressComponent.getString("district");
            String adcode = addressComponent.getString("adcode");
            wsProject.setAreaCode(adcode);
            wsProject.setDistrict(district);
            wsProject.setCity(city);
            wsProject.setProvince(province);
            return wsProjectMapper.insertWsProject(wsProject);
        }
        return -2;
    }

    /**
     * 修改项目信息
     *
     * @param wsProject 项目信息
     * @return 结果
     */
    @Override
    public int updateWsProject(WsProject wsProject) {

        WsProject project = wsProjectMapper.selectWsProjectByName(wsProject);
        if (project != null) {
            return 0;
        }
        if (wsProject.getLatitude() == null || wsProject.getLongitude() == null) {
            return wsProjectMapper.updateWsProject(wsProject);
        }
        JSONObject jsonObject = BaiDuApiUtil.getAddress(wsProject.getLongitude(), wsProject.getLatitude());
        String status = jsonObject.getString("status");
        if (status.equals("0")) {
            JSONObject result = jsonObject.getJSONObject("result");
            String address = result.getString("formatted_address");
            JSONObject addressComponent = result.getJSONObject("addressComponent");
            String province = addressComponent.getString("province");
            String city = addressComponent.getString("city");
            String district = addressComponent.getString("district");
            String adcode = addressComponent.getString("adcode");
            wsProject.setAreaCode(adcode);
            wsProject.setDistrict(district);
            wsProject.setCity(city);
            wsProject.setProvince(province);
            wsProject.setProjectAddress(address);
            return wsProjectMapper.updateWsProject(wsProject);
        }
        return -1;
    }

    /**
     * 删除项目信息 信息
     *
     * @param id 项目信息 ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteWsProjectById(Integer id) {
        List<WsProjectEquipment> equipmentList = wsProjectEquipmentMapper.selectWsProjectEquipmentByProjectId(id);
        List<WsVideoEquipmentManagement> videoEquipmentList = wsVideoEquipmentManagementMapper.selectWsVideoEquipmentManagementByProjectId(id);
        if (StringUtils.isEmpty(equipmentList) && StringUtils.isEmpty(videoEquipmentList)) {
            SysOrganizationAccount organizationAccount = new SysOrganizationAccount();
            organizationAccount.setOrganizationid(id.longValue());
            organizationAccount.setType(IntelligentMonitoringConstants.TYPE_PROJECT);
            int result1 = organizationAccountMapper.deleteSysOrganizationAccount(organizationAccount);
            int result2 = wsProjectMapper.deleteWsProjectById(id);
            if (result1 >= 0 && result2 > 0) {
                return 1;
            }
            throw new CustomException("删除失败");
        }
        return 0;
    }

}
