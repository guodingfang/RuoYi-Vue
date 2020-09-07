package com.jlkj.project.ws.intelligence.service.impl;

import cn.hutool.core.date.DateUtil;
import com.jlkj.common.constant.IntelligentMonitoringConstants;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.framework.aspectj.lang.annotation.DataSource;
import com.jlkj.framework.aspectj.lang.enums.DataSourceType;
import com.jlkj.project.system.domain.SysEnterprise;
import com.jlkj.project.system.mapper.SysEnterpriseMapper;
import com.jlkj.project.system.service.ISysDeptService;
import com.jlkj.project.ws.domain.WsArea;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsProjectEquipment;
import com.jlkj.project.ws.domain.WsTeamPerson;
import com.jlkj.project.ws.excel.CommonExportExcel;
import com.jlkj.project.ws.intelligence.domain.*;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerStatisticsVO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleManagerVO;
import com.jlkj.project.ws.intelligence.mapper.*;
import com.jlkj.project.ws.intelligence.service.IWsElevatorManagerService;
import com.jlkj.project.ws.mapper.WsAreaMapper;
import com.jlkj.project.ws.mapper.WsProjectEquipmentMapper;
import com.jlkj.project.ws.mapper.WsProjectMapper;
import com.jlkj.project.ws.mapper.WsTeamPersonMapper;
import com.jlkj.project.ws.service.IWsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 施工电梯设备管理Service业务层处理
 *
 * @author liujie
 * @date 2020-07-03
 */
@Service
public class WsElevatorManagerServiceImpl implements IWsElevatorManagerService
{
    @Autowired
    private WsElevatorManagerMapper wsElevatorManagerMapper;

    @Autowired
    private WsProjectMapper wsProjectMapper;

    @Autowired
    private WsProjectFieldLayoutMapper wsProjectFieldLayoutMapper;

    @Autowired
    private WsElevatorMinitorDeviceMapper wsElevatorMinitorDeviceMapper;

    @Autowired
    private WsTeamPersonMapper wsTeamPersonMapper;

    @Autowired
    private WsAreaMapper wsAreaMapper;

    @Autowired
    private WsBlackBoxMapper wsBlackBoxMapper;

    @Autowired
    private WsElevatorWorkCycleManagerMapper wsElevatorWorkCycleManagerMapper;

    @Autowired
    private WsProjectEquipmentMapper wsProjectEquipmentMapper;

    @Autowired
    private WsElevatorInOutMapper wsElevatorInOutMapper;

    @Autowired
    private IWsProjectService wsProjectService;


    @Autowired
    private SysEnterpriseMapper enterpriseMapper;

    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 查询施工电梯设备管理
     *
     * @param id 施工电梯设备管理ID
     * @return 施工电梯设备管理
     */
    @Override
    public WsElevatorManager selectWsElevatorManagerById(Integer id)
    {
        return wsElevatorManagerMapper.selectWsElevatorManagerById(id);
    }

    /**
     * 查询施工电梯设备管理列表
     *
     * @param wsElevatorManager 施工电梯设备管理
     * @return 施工电梯设备管理
     */
    @Override
    public List<WsElevatorManagerVO> selectWsElevatorManagerList(WsElevatorManager wsElevatorManager)
    {
        //项目信息为空时添加数据范围
        List<WsProject> projectScope = null;
        if(StringUtils.isNull(wsElevatorManager)||StringUtils.isNull(wsElevatorManager.getProjectId())){
            if(StringUtils.isNotNull(wsElevatorManager.getCompanyId())){
                List<SysEnterprise> enterprises = enterpriseMapper.selectEnterpriseList(new SysEnterprise(wsElevatorManager.getCompanyId()));
                projectScope= enterprises.stream().filter(e -> 1 == e.getType()).map(WsProject::new).collect(Collectors.toList());
            }else{
                projectScope = SecurityUtils.getProjectList();
            }
        }

        if(StringUtils.isNotNull(wsElevatorManager)&& IntelligentMonitoringConstants.OUT_SITE.equals(wsElevatorManager.getDeviceStatus())){
        return wsElevatorManagerMapper.selectWsElevatorManagerList(wsElevatorManager,projectScope);
        }
        return wsElevatorManagerMapper.selectWsElevatorManagerVOList(wsElevatorManager,projectScope);
    }

    /**
     * 新增施工电梯设备管理
     *
     * @param wsElevatorMangerDTO 施工电梯设备管理
     * @return 结果
     */

    @Transactional
    @Override
    public int insertWsElevatorManager(WsElevatorManagerDTO wsElevatorMangerDTO)
    {
        if (StringUtils.isNull(wsElevatorMangerDTO)) {

            throw new CustomException("设备管理信息不能为空！");
        }
        WsElevatorManager wsElevatorManager = wsElevatorMangerDTO.getWsElevatorManager();

        if (StringUtils.isNull(wsElevatorManager)) {
            throw new CustomException("施工电梯信息不能为空！");
        }
        WsProject wsProject = wsProjectMapper.selectWsProjectById(wsElevatorManager.getProjectId());
        if (StringUtils.isNull(wsProject)) {
            throw new CustomException("当前工程不存在");
        }

        int num = wsElevatorManagerMapper.countWsElevatorManager(wsElevatorManager);
        if(num>0) throw new CustomException("同项目下升降机自编号不可重复");

        int row =0;
        int compare=0;
        //插入升降机设备
        wsElevatorManager.setDeviceStatus(IntelligentMonitoringConstants.IN_SITE);
        wsElevatorManager.setDeleted(IntelligentMonitoringConstants.NO);
        row +=wsElevatorManagerMapper.insertWsElevatorManager(wsElevatorManager);
        compare++;

        //插入升降机进场记录
        WsElevatorInOut wsElevatorInOut = new WsElevatorInOut();
        wsElevatorInOut.setElevatorId(wsElevatorManager.getId());
        wsElevatorInOut.setInDate(wsElevatorManager.getInDate());
        row += wsElevatorInOutMapper.insertWsElevatorInOut(wsElevatorInOut);
        compare++;

        List<WsElevatorMinitorDevice> devices = wsElevatorMangerDTO.getDevices();
        //监控设备不为空时，设置默认信息并循环插入
        if (StringUtils.isNotNull(devices)&&devices.size()>0) {
            devices.forEach(device -> {
                device.setElevatorId(wsElevatorManager.getId());
                device.setDeleted(IntelligentMonitoringConstants.NO);
            });

            for (WsElevatorMinitorDevice device : devices) {
                int count =wsElevatorMinitorDeviceMapper.countWsElevatorMinitorDeviceByProjectAndSN(device);
                if (count>0) {
                    throw new CustomException("绑定设备已存在，不可重复绑定！");
                }
                row +=wsElevatorMinitorDeviceMapper.insertWsElevatorMinitorDevice(device);
                compare++;


                //更新工程设备绑定的设备启用状态
                WsProjectEquipment wsProjectEquipment = getWsProjectEquipment(device);
                row +=wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
                compare++;
            }

        }
        //若场布图不为空，则插入场布图
        WsProjectFieldLayout wsProjectFieldLayout = wsElevatorMangerDTO.getWsProjectFieldLayout();
        if (StringUtils.isNotNull(wsProjectFieldLayout)) {
            row += saveOrUpdateWsProjectFieldLayout(wsElevatorManager, wsProjectFieldLayout);
            compare++;
        }
        if(row!=compare){
            throw new CustomException("新增失败，请联系管理员");
        }
        return row;
    }

    private WsProjectEquipment getWsProjectEquipment(WsElevatorMinitorDevice wsElevatorMinitorDevice) {
        WsProjectEquipment wsProjectEquipment = new WsProjectEquipment();
        wsProjectEquipment.setSerialNumber(wsElevatorMinitorDevice.getSerialNumber());
        wsProjectEquipment.setEnableStatus(Integer.parseInt(IntelligentMonitoringConstants.ENABLE));
        wsProjectEquipment.setEquipmentType(Integer.parseInt(wsElevatorMinitorDevice.getEquipmentType()));
        return wsProjectEquipment;
    }

    private int saveOrUpdateWsProjectFieldLayout(WsElevatorManager wsElevatorManager, WsProjectFieldLayout wsProjectFieldLayout) {
        int row=0;
        WsProjectFieldLayout layout = wsProjectFieldLayoutMapper.selectWsProjectFieldLayoutByProjecAndType(wsElevatorManager.getProjectId(), Integer.valueOf(IntelligentMonitoringConstants.ELEVATOR));
        if (StringUtils.isNull(layout)) {
            wsProjectFieldLayout.setEquipmentType(Integer.valueOf(IntelligentMonitoringConstants.ELEVATOR));
            wsProjectFieldLayout.setIsDelete(IntelligentMonitoringConstants.NO);
            row += wsProjectFieldLayoutMapper.insertWsProjectFieldLayout(wsProjectFieldLayout);
        }else{
            wsProjectFieldLayout.setId(layout.getId());
            row += wsProjectFieldLayoutMapper.updateWsProjectFieldLayout(wsProjectFieldLayout);
        }
        return row;
    }

    /**
     * 修改施工电梯设备管理
     *
     * @param wsElevatorMangerDTO 施工电梯设备管理
     * @return 结果
     */

    @Transactional
    @Override
    public int updateWsElevatorManager(WsElevatorManagerDTO wsElevatorMangerDTO)
    {
        int row =0;
        int compare=0;
        if(!existedWsElevatorManager(wsElevatorMangerDTO)){
            throw new  CustomException("设备不存在，更新失败");
        }
        int num = wsElevatorManagerMapper.countWsElevatorManager(wsElevatorMangerDTO.getWsElevatorManager());
        if(num>0) throw new CustomException("同项目下升降机自编号不可重复");

        //更新升降机设备
        WsElevatorManager wsElevatorManager = wsElevatorMangerDTO.getWsElevatorManager();
        WsElevatorManager curElevator = wsElevatorManagerMapper.selectWsElevatorManagerById(wsElevatorManager.getId());
        if (StringUtils.isNull(curElevator)) throw new CustomException("设备不存在，更新失败");
        //如果已存在的设备状态为离场，则表示本次更新为重新进场，则需重新插入进场记录
        if (IntelligentMonitoringConstants.OUT_SITE.equals(curElevator.getDeviceStatus())) {
            //插入升降机进场记录
            WsElevatorInOut wsElevatorInOut = new WsElevatorInOut();
            wsElevatorInOut.setElevatorId(wsElevatorManager.getId());
            wsElevatorInOut.setInDate(wsElevatorManager.getInDate());
            row += wsElevatorInOutMapper.insertWsElevatorInOut(wsElevatorInOut);
            compare++;
            //设置升降机进场状态为1
            wsElevatorManager.setDeviceStatus(IntelligentMonitoringConstants.IN_SITE);

            //TODO原升降机设备出场时间未清空
        }

        //如果进场设备修改进场日期，需同步修改进场出场表
        if (IntelligentMonitoringConstants.IN_SITE.equals(curElevator.getDeviceStatus())
                && !DateUtil.isSameDay(wsElevatorManager.getInDate(),curElevator.getInDate())) {
            WsElevatorInOut wsElevatorInOut = new WsElevatorInOut();
            wsElevatorInOut.setInDate(wsElevatorManager.getInDate());
            wsElevatorInOut.setElevatorId(wsElevatorManager.getId());
            row+=wsElevatorInOutMapper.updateWsElevatorOutDate(wsElevatorInOut);
            compare++;
        }

        row+= wsElevatorManagerMapper.updateWsElevatorManager(wsElevatorManager);
        compare++;


        WsElevatorMinitorDevice wsElevatorMinitorDevice = new WsElevatorMinitorDevice();
        wsElevatorMinitorDevice.setElevatorId(wsElevatorManager.getId());
        List<WsElevatorMinitorDevice> devices = wsElevatorMangerDTO.getDevices();
        //数据库已存在的监控设备列表
        List<WsElevatorMinitorDevice> devices1 = wsElevatorMinitorDeviceMapper.selectWsElevatorMinitorDeviceList(wsElevatorMinitorDevice);
        List<Integer> ids=null;
        if (StringUtils.isNotEmpty(devices1))
            ids = devices1.stream().map(WsElevatorMinitorDevice::getId).collect(Collectors.toList());

        if(StringUtils.isNotEmpty(devices)){
            for (WsElevatorMinitorDevice device : devices) {
                if(StringUtils.isNull(device)) continue;
                if (StringUtils.isNull(device.getId())) {//为null，则新增
                    int count =wsElevatorMinitorDeviceMapper.countWsElevatorMinitorDeviceByProjectAndSN(device);
                    if (count>0) {
                        throw new CustomException("绑定设备已存在，不可重复绑定！");
                    }
                    device.setDeleted(IntelligentMonitoringConstants.NO);
                    device.setEquipmentType(IntelligentMonitoringConstants.ELEVATOR);
                    device.setElevatorId(wsElevatorManager.getId());
                    row += wsElevatorMinitorDeviceMapper.insertWsElevatorMinitorDevice(device);
                    compare++;
                    WsProjectEquipment wsProjectEquipment =getWsProjectEquipment(device);
                    row += wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
                    compare++;
                }else{//否则为更新
                    WsElevatorMinitorDevice wsElevatorMinitorDevice1 = wsElevatorMinitorDeviceMapper.selectWsElevatorMinitorDeviceById(device.getId());
                    if (StringUtils.isNull(wsElevatorMinitorDevice1)) {
                        throw new CustomException("绑定设备不存在！，设备名称："+device.getSerialNumber());
                    }
                    row +=wsElevatorMinitorDeviceMapper.updateWsElevatorMinitorDevice(device);
                    compare++;
                    if(StringUtils.isNotNull(ids)&&ids.contains(device.getId())) {
                        ids.remove(device.getId());
                    }

                }
            }
        }


        //删除未绑定监控设备
        if (ids != null) {
            for (Integer id : ids) {
                WsElevatorMinitorDevice device = wsElevatorMinitorDeviceMapper.selectWsElevatorMinitorDeviceById(id);
                row += wsElevatorMinitorDeviceMapper.deleteWsElevatorMinitorDeviceById(id);
                compare++;

                WsProjectEquipment wsProjectEquipment = getWsProjectEquipment(device);
                wsProjectEquipment.setEnableStatus(Integer.parseInt(IntelligentMonitoringConstants.NOT_ENABLED));
                row += wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
                compare++;
            }
        }

        if(row!=compare){
            throw new CustomException("修改失败，请联系管理员");
        }
        return row;
    }

    private boolean  existedWsElevatorManager(WsElevatorManagerDTO wsElevatorMangerDTO) {
        if (StringUtils.isNull(wsElevatorMangerDTO)) {

            return false;
        }
        WsElevatorManager wsElevatorManager = wsElevatorMangerDTO.getWsElevatorManager();

        if (StringUtils.isNull(wsElevatorManager)) {
            return false;
        }
        WsElevatorManager elevator= wsElevatorManagerMapper.selectWsElevatorManagerById(wsElevatorManager.getId());
        return !StringUtils.isNull(elevator);
    }

    /**
     * 批量删除施工电梯设备管理
     *
     * @param ids 需要删除的施工电梯设备管理ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWsElevatorManagerByIds(Integer[] ids)
    {
        if (StringUtils.isNull(ids)||ids.length==0) {
            return 0;
        }
        //更新工程设备绑定启用状态
        for (Integer id : ids) updateWsProjectEquipmentEnabledByElevatorId(id);
        wsElevatorMinitorDeviceMapper.deleteWsElevatorMinitorDeviceByElevatorIds(ids);
        return wsElevatorManagerMapper.deleteWsElevatorManagerByIds(ids);
    }

    private void updateWsProjectEquipmentEnabledByElevatorId(Integer id) {

        WsElevatorMinitorDevice wsElevatorMinitorDevice = new WsElevatorMinitorDevice();
        wsElevatorMinitorDevice.setElevatorId(id);
        List<WsElevatorMinitorDevice> devices = wsElevatorMinitorDeviceMapper.selectWsElevatorMinitorDeviceList(wsElevatorMinitorDevice);
        for (WsElevatorMinitorDevice device : devices) {
            WsProjectEquipment wsProjectEquipment = getWsProjectEquipment(device);
            wsProjectEquipment.setEnableStatus(Integer.parseInt(IntelligentMonitoringConstants.NOT_ENABLED));
            wsProjectEquipmentMapper.updateWsProjectEquipmentBySerialNumber(wsProjectEquipment);
        }
    }

    /**
     * 删除施工电梯设备管理信息
     *
     * @param id 施工电梯设备管理ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWsElevatorManagerById(Integer id)
    {
        updateWsProjectEquipmentEnabledByElevatorId(id);
        wsElevatorMinitorDeviceMapper.removeWsElevatorMinitorDeviceByElevator(id);
        return wsElevatorManagerMapper.deleteWsElevatorManagerById(id);
    }

    @Transactional
    @Override
    public int offSite(Integer id) {
        //设置升降机出场
        WsElevatorManager wsElevatorManager = new WsElevatorManager();
        wsElevatorManager.setId(id);
        wsElevatorManager.setxOffset("");
        wsElevatorManager.setyOffset("");
        wsElevatorManager.setDeviceStatus(IntelligentMonitoringConstants.OUT_SITE);
        wsElevatorManager.setOutDate(new Date());
        wsElevatorManagerMapper.updateWsElevatorManager(wsElevatorManager);

        //设置升降机出场日期
        WsElevatorInOut wsElevatorInOut = new WsElevatorInOut();
        wsElevatorInOut.setOutDate(wsElevatorManager.getOutDate());
        wsElevatorInOut.setElevatorId(wsElevatorManager.getId());
        wsElevatorInOutMapper.updateWsElevatorOutDate(wsElevatorInOut);
        //设置工程设备绑定，设备启用状态
        updateWsProjectEquipmentEnabledByElevatorId(id);
        //解绑监控设备
        return wsElevatorMinitorDeviceMapper.removeWsElevatorMinitorDeviceByElevator(id);
    }



    @Override
    public String[] getSerialNumbers(List<WsElevatorManagerVO> wsElevatorManagerVOS) {

        if(StringUtils.isNull(wsElevatorManagerVOS)||StringUtils.isEmpty(wsElevatorManagerVOS)) return null;
        ArrayList<String> serialNumbers = new ArrayList<>();
        for (WsElevatorManagerVO wsElevatorManagerVO : wsElevatorManagerVOS) {
            if(StringUtils.isNull(wsElevatorManagerVO)) continue;
            if(!StringUtils.isEmpty(wsElevatorManagerVO.getMonitorDevices())){
                for (WsElevatorMinitorDevice monitorDevice : wsElevatorManagerVO.getMonitorDevices()) {
                    serialNumbers.add(monitorDevice.getSerialNumber());
                }
            }else{
                serialNumbers.add(wsElevatorManagerVO.getSerialNumber());
            }

        }
        return serialNumbers.toArray(new String[serialNumbers.size()]);
    }

    @Override
    public List<WsElevatorManagerStatisticsVO> statisticsDeviceManagerList(WsElevatorManagerDTO wsElevatorManagerDTO) {
        parseBeginAndEndTime(wsElevatorManagerDTO);
        int days = Math.abs(wsElevatorManagerDTO.getDays());
        //初始化前台上传统计格式列表
        List<WsElevatorManagerStatisticsVO> wsElevatorManagerStatisticsVOS = Arrays.asList(new WsElevatorManagerStatisticsVO[days]);
        for (int i = 0; i < days; i++) {
            WsElevatorManagerStatisticsVO wsElevatorManagerStatisticsVO = new WsElevatorManagerStatisticsVO();
            wsElevatorManagerStatisticsVO.setStatisticsDate(DateUtil.offsetDay(wsElevatorManagerDTO.getBeginTime(),i));
            wsElevatorManagerStatisticsVOS.set(i,wsElevatorManagerStatisticsVO);
        }

        //项目信息为空时添加数据范围
        List<WsProject> projectScope = getWsProjectScope(wsElevatorManagerDTO);
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorManagerMapper.selectInAndOutList(wsElevatorManagerDTO,projectScope);

        //循环录入每日统计数据
        wsElevatorManagerStatisticsVOS.forEach(x->{
            //统计日期在进场日期之前且（出场日期为空，或者出场日期在统计日期之后）即当天在场
            long monitorDeviceNum = wsElevatorManagerVOS.stream().filter(vo-> isInSite(x, vo)).count();
            Integer deviceNum= wsElevatorManagerVOS.stream().filter(vo -> isInSite(x, vo)).collect(Collectors.toMap(WsElevatorManagerVO::getId, a -> a, (k1, k2) -> k1)).size();
            Integer unCoverDeviceNumber= wsElevatorManagerVOS.stream().filter(vo -> isInSite(x, vo)&& StringUtils.isNull(vo.getSerialNumber())).collect(Collectors.toMap(WsElevatorManagerVO::getId, a -> a, (k1, k2) -> k1)).size();
            x.setDeviceNumber(deviceNum);
            x.setTotalMonitorDeviceNumber((int) monitorDeviceNum);
            x.setCoverDeviceNumber(deviceNum-unCoverDeviceNumber);
        });

        return wsElevatorManagerStatisticsVOS;
    }

    private List<WsProject> getWsProjectScopeByCompanyId(WsElevatorManagerDTO wsElevatorManagerDTO) {
        List<WsProject> projectScope;
        List<SysEnterprise> listTree= sysDeptService.selectOrgListByOrgId(wsElevatorManagerDTO.getCompanyId());
        if(StringUtils.isEmpty(listTree)) return  null ;
        projectScope= listTree.stream().map(WsProject::new).collect(Collectors.toList());
        return projectScope;
    }

    private boolean isInSite(WsElevatorManagerStatisticsVO x, WsElevatorManagerVO vo) {
        return x.getStatisticsDate().after(vo.getInDate()) && (StringUtils.isNull(vo.getOutDate()) || x.getStatisticsDate().before(vo.getOutDate()));
    }

    @Override
    public List<WsElevatorManagerStatisticsVO> statisticsDeviceManagerListByProject(WsElevatorManagerDTO wsElevatorManagerDTO) {
        if(StringUtils.isNotNull(wsElevatorManagerDTO)){
            if(StringUtils.isNotNull(wsElevatorManagerDTO.getStatisticsDate())&&StringUtils.isNotNull(wsElevatorManagerDTO.getDays())){
                parseBeginAndEndTime(wsElevatorManagerDTO);
            }
        }
        //项目信息为空时添加数据范围
        List<WsProject> projectScope = getWsProjectScope(wsElevatorManagerDTO);
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorManagerMapper.selectInAndOutList(wsElevatorManagerDTO,projectScope);
        List<WsElevatorManagerStatisticsVO> result = wsElevatorManagerVOS.stream()
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(WsElevatorManagerVO::getProjectId))))
                .stream().map(WsElevatorManagerStatisticsVO::new).collect(Collectors.toList());

        result.forEach(x->{
            long monitorDeviceNum = wsElevatorManagerVOS.stream().filter(vo-> x.getProjectId().equals(vo.getProjectId())).count();
            long doubleDeviceNum = wsElevatorManagerVOS.stream().filter(vo-> vo.getDeviceType().equals(IntelligentMonitoringConstants.DOUBLE_CAGE)).count();
            Integer deviceNum= wsElevatorManagerVOS.stream().filter(vo -> x.getProjectId().equals(vo.getProjectId())).collect(Collectors.toMap(WsElevatorManagerVO::getId, a -> a, (k1, k2) -> k1)).size();
            Integer unCoverDeviceNumber= wsElevatorManagerVOS.stream().filter(vo -> x.getProjectId().equals(vo.getProjectId())&& StringUtils.isNull(vo.getSerialNumber())).collect(Collectors.toMap(WsElevatorManagerVO::getId, a -> a, (k1, k2) -> k1)).size();
            x.setDeviceNumber(deviceNum);
            x.setTotalMonitorDeviceNumber((int) monitorDeviceNum);
            x.setCoverDeviceNumber(deviceNum-unCoverDeviceNumber);
            x.setDoubleDeviceNumber((int) doubleDeviceNum);
        });

        return result;
    }

    @Override
    public List<WsElevatorManagerVO> selectWsElevatorManagerVOListByDTO(WsElevatorManagerDTO wsElevatorManagerDTO) {
        //项目信息为空时添加数据范围
        List<WsProject> projectScope = getWsProjectScope(wsElevatorManagerDTO);
        if(StringUtils.isNotNull(wsElevatorManagerDTO)){
            String keyWordName= wsElevatorManagerDTO.getKeyWordName();
            if(StringUtils.isNotEmpty(keyWordName)){
                wsElevatorManagerDTO.setKeyWordName(null);
                switch (wsElevatorManagerDTO.getKeyType()){
                    case "1" :
                        wsElevatorManagerDTO.setProjectName(keyWordName);
                        break;
                    case "2" :
                        wsElevatorManagerDTO.setRecordNumber(keyWordName);
                        break;
                    case "3" :
                        wsElevatorManagerDTO.setDeviceName(keyWordName);
                        break;
                }
            }

        }

        return wsElevatorManagerMapper.selectWsElevatorManagerVOListByDTO(wsElevatorManagerDTO,projectScope);
    }

    private void parseBeginAndEndTime(WsElevatorManagerDTO wsElevatorManagerDTO) {
        if(StringUtils.isNull(wsElevatorManagerDTO)){throw new CustomException("数据异常，请联系管理员");}
        int days = wsElevatorManagerDTO.getDays();
        days = days==0?0:Math.abs(days)-1 ;
        Date statisticsDate = wsElevatorManagerDTO.getStatisticsDate();
        if(StringUtils.isNull(statisticsDate)){throw new CustomException("数据异常，请联系管理员");}
        Date beginTime;
        Date endTime;
        if(wsElevatorManagerDTO.getDays()>=0){
            beginTime = DateUtil.beginOfDay(statisticsDate);
            endTime = DateUtil.endOfDay(DateUtil.offsetDay(statisticsDate,days));
        }else{
            beginTime=DateUtil.beginOfDay(DateUtil.offsetDay(statisticsDate,-days));
            endTime =DateUtil.endOfDay(statisticsDate);
        }
        wsElevatorManagerDTO.setBeginTime(beginTime);
        wsElevatorManagerDTO.setEndTime(endTime);
    }

    @Override
    public List<WsElevatorManagerVO> selectWsElevatorMonitorDeviceListByDTO(WsElevatorManagerDTO wsElevatorManagerDTO) {

        //项目信息为空时添加数据范围
        List<WsProject> projectScope = getWsProjectScope(wsElevatorManagerDTO);
        return wsElevatorManagerMapper.selectWsElevatorMonitorDeviceListByDTO(wsElevatorManagerDTO,projectScope);
    }

    private List<WsProject> getWsProjectScope(WsElevatorManagerDTO wsElevatorManagerDTO) {
        List<WsProject> projectScope = null;
        if (StringUtils.isNull(wsElevatorManagerDTO) || StringUtils.isNull(wsElevatorManagerDTO.getProjectId())) {
            if (StringUtils.isNotNull(wsElevatorManagerDTO.getCompanyId())) {
                projectScope = getWsProjectScopeByCompanyId(wsElevatorManagerDTO);
            } else {
                projectScope = SecurityUtils.getProjectList();
            }
            if (StringUtils.isEmpty(projectScope)) {
                projectScope = new ArrayList<>();
                projectScope.add(new WsProject());
            }
        }
        return projectScope;
    }

    @Override
    public List<WsElevatorManagerVO> assembleWsElevatorManagerVOList(List<WsBlackBox> wsBlackBoxes, List<WsElevatorManagerVO> wsElevatorManagerVOS) {
        wsElevatorManagerVOS.forEach(x->{
            x.setOnlineStatus(IntelligentMonitoringConstants.OFFLINE);
            if (StringUtils.isNotEmpty(x.getMonitorDevices())) {
                for (WsElevatorMinitorDevice monitorDevice : x.getMonitorDevices()) {
                    WsBlackBox wsBlackBox = wsBlackBoxes.stream().filter(b -> monitorDevice.getSerialNumber().equals(b.getSerialNumber())).findAny().orElse(null);
                    if (StringUtils.isNotNull(wsBlackBox)&&IntelligentMonitoringConstants.ONLINE.equals(wsBlackBox.getOnlineStatus())) {
                        x.setOnlineStatus(IntelligentMonitoringConstants.ONLINE);
                    }
                }
            }
        });
        return wsElevatorManagerVOS;
    }

    @Override
    public CommonExportExcel genDetailExcel(List<WsElevatorManagerVO> records) {
        ArrayList<String> header = new ArrayList<>();
        ArrayList<List<String>> body = new ArrayList<>();
        ArrayList<String> data;
        for (WsElevatorManagerVO record : records) {
            if(header.size()==0){
                header.add("项目名称");
            }
            data = new ArrayList<>();
            data.add(record.getProjectName());

            body.add(data);
        }


        return new CommonExportExcel("设备详情",body,header);
    }

    @Override
    public WsElevatorDriverInfo findNewestRecognitionBySerialNumber(String serialNumber) {
        if (StringUtils.isEmpty(serialNumber)) {
            return  new WsElevatorDriverInfo();
        }
        WsElevatorDriverInfo newestRecognition = wsElevatorManagerMapper.findNewestRecognitionBySerialNumber(serialNumber);
        if(StringUtils.isNotNull(newestRecognition)&&StringUtils.isNotEmpty(newestRecognition.getIdNumber())){
            List<WsTeamPerson> wsTeamPeoples = wsTeamPersonMapper.selectWsTeamPersonByserialNumber(serialNumber);
            WsTeamPerson wsTeamPerson = wsTeamPeoples.stream().filter(x -> newestRecognition.getIdNumber().equals(x.getIdNumber())).findAny().orElse(null);
            if(StringUtils.isNull(wsTeamPerson)){
                return newestRecognition;
            }
            newestRecognition.setFaceUrl(wsTeamPerson.getFaceUrl());
            if(StringUtils.isNotEmpty(wsTeamPerson.getName())) newestRecognition.setName(wsTeamPerson.getName());
            if(StringUtils.isNotEmpty(wsTeamPerson.getCertificates())){
                newestRecognition.setSpecialOperationCertificate(wsTeamPerson.getCertificates().get(0).getCertificateNumber());
            }
        }
        return newestRecognition;
    }



    @Override
    public List<WsElevatorDriverInfo> selectRecognitionListByDriver(WsElevatorDriverInfo wsElevatorDriverInfo) {
        if (StringUtils.isNull(wsElevatorDriverInfo)||StringUtils.isEmpty(wsElevatorDriverInfo.getSerialNumber())) {
            return  null;
        }
        List<WsElevatorDriverInfo> wsElevatorDriverInfos = wsElevatorManagerMapper.selectDriverRecognitionListByDriver(wsElevatorDriverInfo);
        List<WsTeamPerson> wsTeamPeoples = wsTeamPersonMapper.selectWsTeamPersonByserialNumber(wsElevatorDriverInfo.getSerialNumber());

        wsElevatorDriverInfos.forEach(x->{
            WsTeamPerson wsTeamPerson = wsTeamPeoples.stream().filter(team -> StringUtils.isNotEmpty(x.getIdNumber())&&x.getIdNumber().equals(team.getIdNumber())).findAny().orElse(null);
            if(StringUtils.isNotNull(wsTeamPerson)){
                x.setFaceUrl(wsTeamPerson.getFaceUrl());
                if(StringUtils.isNotEmpty(wsTeamPerson.getName())) x.setName(wsTeamPerson.getName());
                if(StringUtils.isNotEmpty(wsTeamPerson.getCertificates())){
                    x.setSpecialOperationCertificate(wsTeamPerson.getCertificates().get(0).getCertificateNumber());
                }
            }

        });
        return wsElevatorDriverInfos;
    }


    @Override
    public List<WsElevatorManagerStatisticsVO> getAreaDeviceStatisticsList(WsElevatorManagerDTO wsElevatorManagerDTO) {
        if (StringUtils.isNull(wsElevatorManagerDTO))
            wsElevatorManagerDTO = new WsElevatorManagerDTO();
        if(StringUtils.isNull(wsElevatorManagerDTO.getWsArea()))
                return null;
        //获取子级区域列表
        List<WsArea> wsAreas =wsAreaMapper.selectChidrenAreaList(wsElevatorManagerDTO.getAreaCode());
        List<WsProject> wsProjectScope = getWsProjectScope(wsElevatorManagerDTO);
        if(StringUtils.isNotEmpty(wsProjectScope)){
            if (StringUtils.isNull(wsProjectScope.get(0).getId())) return null;
        }
        //获取升降机设设备列表
        wsElevatorManagerDTO.setDeviceStatus(IntelligentMonitoringConstants.IN_SITE);
        List<WsElevatorManagerVO> wsElevatorManagerVOS = wsElevatorManagerMapper.selectDeviceListByArea(wsElevatorManagerDTO,wsProjectScope);
        //过滤不存在的区域
        wsAreas.removeIf(x->
                !wsElevatorManagerVOS.stream().anyMatch(vo->
                       StringUtils.isNotEmpty(vo.getAncestors())&& vo.getAncestors().contains(x.getCode()))
        );

        String zoom = wsElevatorManagerDTO.getWsArea().getZoom();
        //构造返回结果格式列表
        List<WsElevatorManagerStatisticsVO> result;
        if ("4".equals(zoom)) {
            WsProject wsProject = new WsProject();
            wsProject.setAreaCode(wsElevatorManagerDTO.getAreaCode());
            List<Integer> projectIds = wsProjectScope.stream().map(WsProject::getId).collect(Collectors.toList());
            wsProject.setProjectIds(projectIds);
            List<WsProject> wsProjects = wsProjectMapper.selectWsProjectList(wsProject);
            result = wsProjects.stream().map(WsElevatorManagerStatisticsVO::new).collect(Collectors.toList());
        }else{
             result = wsAreas.stream().map(WsElevatorManagerStatisticsVO::new).collect(Collectors.toList());
        }



        //统计设备数量
        result.forEach(x->{
            long count = wsElevatorManagerVOS.stream().filter(vo -> isInArea(x, vo)&&StringUtils.isNotNull(vo.getId())).count();
            x.setDeviceNumber((int) count);
            x.setDataType(zoom);
        });
        return result;
    }


    private boolean isInArea(WsElevatorManagerStatisticsVO x, WsElevatorManagerVO vo) {
        if(StringUtils.isNotNull(x.getProjectId())) return vo.getProjectId().equals(x.getProjectId());
        if(StringUtils.isEmpty(vo.getProvinceCode()) ||StringUtils.isEmpty(vo.getCountyCode())||StringUtils.isEmpty(vo.getCityCode()) ) return false;
        return vo.getProvinceCode().equals(x.getAreaCode()) || vo.getCityCode().equals(x.getAreaCode() )|| vo.getCountyCode().equals(x.getAreaCode());
    }

    @DataSource(DataSourceType.SLAVE)
    @Override
    public WsElevatorManagerStatisticsVO getDeviceOnlineAndAlarmingStatistics(List<WsElevatorManagerVO> wsElevatorManagerVOS) {
      if(StringUtils.isEmpty(wsElevatorManagerVOS)){
          WsElevatorManagerStatisticsVO statistics = new WsElevatorManagerStatisticsVO();
          statistics.setOnlineMonitorDeviceNumber(0);
          statistics.setPreAlarmingNumber(0);
          statistics.setAlarmingNumber(0);
          return  statistics;
      }
       // 获取设备序号集合
        String[] serialNumbers = wsElevatorManagerVOS.stream().map(WsElevatorManagerVO::getSerialNumber).toArray(String[]::new);

        //获取在线统计数据
        WsElevatorManagerStatisticsVO wsElevatorManagerStatisticsVO = wsBlackBoxMapper.statisticsOnline(serialNumbers);


        //获取报警统计数据
        WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleManagerDTO = new WsElevatorWorkCycleManagerDTO();
        wsElevatorWorkCycleManagerDTO.setSerialNumbers(serialNumbers);
        wsElevatorWorkCycleManagerDTO.setBeginTime(DateUtil.beginOfDay(new Date()));
        wsElevatorWorkCycleManagerDTO.setEndTime(DateUtil.endOfDay(new Date()));
        List<WsElevatorWorkCycleManagerVO> wsElevatorWorkCycleManagerVOS = wsElevatorWorkCycleManagerMapper.selectElevatorIllegalWorkRecord(wsElevatorWorkCycleManagerDTO);
        List<WsElevatorWorkCycleManagerVO> alarmingWorkRecordList = wsElevatorWorkCycleManagerVOS.stream().filter(vo -> vo.getWarningType().equals(IntelligentMonitoringConstants.ALARMING) ).collect(Collectors.toList());
        Integer alarmingNumber = alarmingWorkRecordList.size();
        Integer preAlarmingNumber =wsElevatorWorkCycleManagerVOS.size()-alarmingNumber;
        wsElevatorManagerStatisticsVO.setAlarmingNumber(alarmingNumber);
        wsElevatorManagerStatisticsVO.setPreAlarmingNumber(preAlarmingNumber);


        HashSet<Integer> projects = new HashSet<>();
        //获取报警项目数据
        int projectNumber = wsElevatorManagerVOS.stream().collect(Collectors.toMap(WsElevatorManagerVO::getProjectId, a -> a, (k1, k2) -> k1)).size();
        wsElevatorManagerStatisticsVO.setProjectNumber(projectNumber);
        List<String> alarmingSerialNumbers = alarmingWorkRecordList.stream().map(WsElevatorWorkCycleManagerVO::getSerialNumber).collect(Collectors.toList());
        wsElevatorManagerVOS.forEach(x->{
            if (alarmingSerialNumbers.contains(x.getSerialNumber())) {
                projects.add(x.getProjectId());
            }
        });
        wsElevatorManagerStatisticsVO.setAlarmingProjectNumber(projects.size());
        return wsElevatorManagerStatisticsVO;
    }

    @Override
    public WsElevatorManagerStatisticsVO statisticsAllDeviceData(WsElevatorManagerDTO wsElevatorManagerDTO) {
        List<WsProject> wsProjectScope = getWsProjectScope(wsElevatorManagerDTO);
        wsElevatorManagerDTO.setDeviceStatus(IntelligentMonitoringConstants.IN_SITE);
        WsElevatorManagerStatisticsVO  wsElevatorManagerStatisticsVO = wsElevatorManagerMapper.statisticsAllDeviceData(wsElevatorManagerDTO,wsProjectScope);

        if(StringUtils.isNull(wsElevatorManagerStatisticsVO)) return new WsElevatorManagerStatisticsVO();
        return wsElevatorManagerStatisticsVO;
    }

    @Override
    public List<WsElevatorManagerStatisticsVO> assembleProjectDeviceStatisticsList(List<WsBlackBox> wsBlackBoxes, List<WsElevatorManagerStatisticsVO> wsElevatorManagerStatisticsVOs) {
        wsElevatorManagerStatisticsVOs.forEach(x->{
            long count = wsBlackBoxes.stream().filter(box -> x.getSerialNumbers().contains(box.getSerialNumber()) && IntelligentMonitoringConstants.ONLINE.equals(box.getOnlineStatus())).count();
            x.setOnlineMonitorDeviceNumber((int)count);
        });
        return wsElevatorManagerStatisticsVOs;
    }

    @Override
    public CommonExportExcel genProjectStatisticsDetailExcel(List<WsElevatorManagerStatisticsVO> records) {
        ArrayList<String> header = new ArrayList<>();
        ArrayList<List<String>> body = new ArrayList<>();
        ArrayList<String> data;
        for (WsElevatorManagerStatisticsVO record : records) {
            if(header.size()==0){
                header.add("项目名称");
                header.add("升降机设备（台）");
                header.add("监控设备（台）");

                header.add("在线监控设备（台）");
                header.add("监控在线率（%）");
            }
            data = new ArrayList<>();
            data.add(record.getProjectName());
            data.add(record.getDeviceNumber().toString());
            data.add(record.getTotalMonitorDeviceNumber().toString());
            data.add(record.getOnlineMonitorDeviceNumber().toString());
             Integer onlineRate =(record.getOnlineMonitorDeviceNumber()*100)/record.getTotalMonitorDeviceNumber();
            data.add(onlineRate.toString());
            body.add(data);
        }


        return new CommonExportExcel("设备详情",body,header);
    }

    @Override
    public String[] selectMonitorDeviceSerialNumberList(WsElevatorManagerDTO wsElevatorManagerDTO) {
        List<String>  list =wsElevatorManagerMapper.selectMonitorDeviceSerialNumberList(wsElevatorManagerDTO);
        String[] serialNumbers = null;
        if(StringUtils.isNotEmpty(list)) serialNumbers = list.toArray(new String[list.size()]);
        return serialNumbers;
    }

    @Override
    public List<WsElevatorManagerStatisticsVO> statisticsProjectDeviceList(WsElevatorManagerDTO wsElevatorManagerDTO) {
        //项目信息为空时添加数据范围
        List<WsProject> projectScope = getWsProjectScope(wsElevatorManagerDTO);
        return wsElevatorManagerMapper.statisticsProjectDeviceList(wsElevatorManagerDTO,projectScope);
    }

    @Override
    public List<WsElevatorManagerVO> assembleWsElevatorManagerOnlineStateList(List<WsBlackBox> wsBlackBoxes, List<WsElevatorManagerVO> wsElevatorManagerVOS) {
        if(StringUtils.isEmpty(wsElevatorManagerVOS)||StringUtils.isNull(wsElevatorManagerVOS.get(0))) return wsElevatorManagerVOS;
        wsElevatorManagerVOS.forEach(x->{
            x.setOnlineStatus(IntelligentMonitoringConstants.OFFLINE);
            if(!StringUtils.isEmpty(x.getSerialNumber())) {
                boolean b = wsBlackBoxes.stream().anyMatch(box -> box.getOnlineStatus().equals(IntelligentMonitoringConstants.ONLINE)&&x.getSerialNumber().contains(box.getSerialNumber()) );
                if(b) x.setOnlineStatus(IntelligentMonitoringConstants.ONLINE);
            }
        });
        return wsElevatorManagerVOS;
    }

    @Override
    public int bacthSignPoint(WsElevatorManagerDTO wsElevatorMangerDTO) {
        if(StringUtils.isNull(wsElevatorMangerDTO)||StringUtils.isEmpty(wsElevatorMangerDTO.getElevators())) throw new CustomException("请选择设备");

        int rows=wsElevatorManagerMapper.batchUpdate(wsElevatorMangerDTO.getElevators());
        return rows;

    }

    @Override
    public int unSignPoint(Integer id) {
        WsElevatorManager wsElevatorManager = wsElevatorManagerMapper.selectWsElevatorManagerById(id);
        if(StringUtils.isNull(wsElevatorManager)) throw new CustomException("设备不存在！");
        return wsElevatorManagerMapper.unSignPoint(id);
    }
}
