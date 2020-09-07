package com.jlkj.project.ws.intelligence.service.impl;

import cn.hutool.core.date.DateUtil;
import com.jlkj.common.constant.IntelligentMonitoringConstants;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.framework.aspectj.lang.annotation.DataSource;
import com.jlkj.framework.aspectj.lang.enums.DataSourceType;
import com.jlkj.project.system.domain.SysEnterprise;
import com.jlkj.project.system.service.ISysDeptService;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.excel.CommonExportExcel;
import com.jlkj.project.ws.intelligence.domain.WsElevatorManagerDTO;
import com.jlkj.project.ws.intelligence.domain.WsElevatorMinitorDevice;
import com.jlkj.project.ws.intelligence.domain.WsElevatorWorkCycleManagerDTO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorManagerVO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleManagerVO;
import com.jlkj.project.ws.intelligence.domain.vo.WsElevatorWorkCycleStatisticsVO;
import com.jlkj.project.ws.intelligence.mapper.WsElevatorManagerMapper;
import com.jlkj.project.ws.intelligence.mapper.WsElevatorWorkCycleManagerMapper;
import com.jlkj.project.ws.intelligence.service.IWsElevatorWorkCycleManagerService;
import com.jlkj.project.ws.service.IWsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 升降机工作循环信息管理Service业务层处理
 *
 * @author liujie
 * @date 2020-07-03
 */
@Service
public class WsElevatorWorkCycleManagerServiceImpl implements IWsElevatorWorkCycleManagerService
{
    @Autowired
    private WsElevatorWorkCycleManagerMapper wsElevatorWorkCycleManagerMapper;

    @Autowired
    private WsElevatorManagerMapper wsElevatorManagerMapper;
    @Autowired
    private IWsProjectService wsProjectService;

    @Autowired
    private ISysDeptService sysDeptService;

    @DataSource( DataSourceType.SLAVE)
    @Override
    public List<WsElevatorWorkCycleManagerVO> selectElevatorIllegalWorkRecord(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO) {
        if(StringUtils.isNull(wsElevatorWorkCycleMangerDTO)){throw new CustomException("数据异常，请联系管理员"); }
        //若前台提供开启与结束时间，则不进行日期解析
        if(StringUtils.isEmpty(wsElevatorWorkCycleMangerDTO.getSerialNumbers())) return null;
        if(StringUtils.isNotNull(wsElevatorWorkCycleMangerDTO.getBeginTime())&& StringUtils.isNotNull(wsElevatorWorkCycleMangerDTO.getEndTime())){
            wsElevatorWorkCycleMangerDTO.setBeginTime(DateUtil.beginOfDay(wsElevatorWorkCycleMangerDTO.getBeginTime()));
            wsElevatorWorkCycleMangerDTO.setEndTime(DateUtil.endOfDay(wsElevatorWorkCycleMangerDTO.getEndTime()));
        }
        if(StringUtils.isNull(wsElevatorWorkCycleMangerDTO.getBeginTime())|| StringUtils.isNull(wsElevatorWorkCycleMangerDTO.getEndTime())){
            parseBeginAndEndTime(wsElevatorWorkCycleMangerDTO);
        }
        return wsElevatorWorkCycleManagerMapper.selectElevatorIllegalWorkRecord(wsElevatorWorkCycleMangerDTO);
    }

    private void parseBeginAndEndTime(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO) {
        if(StringUtils.isNull(wsElevatorWorkCycleMangerDTO.getDays())) return;
        int days = wsElevatorWorkCycleMangerDTO.getDays();
        days = days==0?0:Math.abs(days)-1 ;
        Date statisticsDate = wsElevatorWorkCycleMangerDTO.getStatisticsDate();
        if(StringUtils.isNull(statisticsDate))statisticsDate= new Date();
        Date beginTime;
        Date endTime;
        if(wsElevatorWorkCycleMangerDTO.getDays()>=0){
            beginTime = DateUtil.beginOfDay(statisticsDate);
            endTime = DateUtil.endOfDay(DateUtil.offsetDay(statisticsDate,days ));
        }else{
            beginTime=DateUtil.beginOfDay(DateUtil.offsetDay(statisticsDate,-days));
            endTime =DateUtil.endOfDay(statisticsDate);
        }
        wsElevatorWorkCycleMangerDTO.setBeginTime(beginTime);
        wsElevatorWorkCycleMangerDTO.setEndTime(endTime);
    }
    @DataSource( DataSourceType.SLAVE)
    @Override
    public List<WsElevatorWorkCycleManagerVO> selectElevatorWorkRecord(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO) {
        if(StringUtils.isNull(wsElevatorWorkCycleMangerDTO)){throw new CustomException("数据异常，请联系管理员"); }
        if(StringUtils.isNotNull(wsElevatorWorkCycleMangerDTO.getBeginTime())&& StringUtils.isNotNull(wsElevatorWorkCycleMangerDTO.getEndTime())){
            wsElevatorWorkCycleMangerDTO.setBeginTime(DateUtil.beginOfDay(wsElevatorWorkCycleMangerDTO.getBeginTime()));
            wsElevatorWorkCycleMangerDTO.setEndTime(DateUtil.endOfDay(wsElevatorWorkCycleMangerDTO.getEndTime()));
        }
        if(StringUtils.isNull(wsElevatorWorkCycleMangerDTO.getBeginTime())|| StringUtils.isNull(wsElevatorWorkCycleMangerDTO.getEndTime())){
            parseBeginAndEndTime(wsElevatorWorkCycleMangerDTO);
        }

        return wsElevatorWorkCycleManagerMapper.selectElevatorWorkRecord(wsElevatorWorkCycleMangerDTO);
    }
    @DataSource( DataSourceType.SLAVE)
    @Override
    public List<WsElevatorWorkCycleStatisticsVO> statisticsElevatorWorkRecord(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO) {
        if(StringUtils.isEmpty(wsElevatorWorkCycleMangerDTO.getSerialNumbers())){
            return  new ArrayList<>();
        }
        parseBeginAndEndTime(wsElevatorWorkCycleMangerDTO);
        return wsElevatorWorkCycleManagerMapper.statisticsElevatorWorkRecord(wsElevatorWorkCycleMangerDTO);
    }
    @DataSource( DataSourceType.SLAVE)
    @Override
    public WsElevatorWorkCycleStatisticsVO statisticsRecentRecord(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO) {
        if(StringUtils.isEmpty(wsElevatorWorkCycleMangerDTO.getSerialNumbers()))
            return new WsElevatorWorkCycleStatisticsVO();
        List<WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVOS = wsElevatorWorkCycleManagerMapper.statisticsElevatorWorkRecord(wsElevatorWorkCycleMangerDTO);

        return StringUtils.isNotEmpty(wsElevatorWorkCycleStatisticsVOS)?wsElevatorWorkCycleStatisticsVOS.get(0):new WsElevatorWorkCycleStatisticsVO();
    }

    @DataSource( DataSourceType.SLAVE)
    @Override
    public Map<String, WsElevatorWorkCycleStatisticsVO> statisticsYesterdayAndTodayRecord(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO) {
        Map<String, WsElevatorWorkCycleStatisticsVO>  result = new HashMap<>();
        //今日统计
        Date now = new Date();
        Date beginTime = DateUtil.beginOfDay(now) ;
        Date endTime = DateUtil.endOfDay(now);
        wsElevatorWorkCycleMangerDTO.setBeginTime(beginTime);
        wsElevatorWorkCycleMangerDTO.setEndTime(endTime);
        List<WsElevatorWorkCycleStatisticsVO> vOS = wsElevatorWorkCycleManagerMapper.statisticsElevatorWorkRecord(wsElevatorWorkCycleMangerDTO);
        WsElevatorWorkCycleStatisticsVO vo = new WsElevatorWorkCycleStatisticsVO();
        vo.setTotalNumber(0);
        vo.setAlarmingNumber(0);
        result.put("today",StringUtils.isNotEmpty(vOS)?vOS.get(0):vo);
        //昨日统计
        Date yesterday = DateUtil.offsetDay(now,-1);
        beginTime = DateUtil.beginOfDay(yesterday) ;
        endTime = DateUtil.endOfDay(yesterday);
        wsElevatorWorkCycleMangerDTO.setBeginTime(beginTime);
        wsElevatorWorkCycleMangerDTO.setEndTime(endTime);
         vOS = wsElevatorWorkCycleManagerMapper.statisticsElevatorWorkRecord(wsElevatorWorkCycleMangerDTO);
        result.put("yesterday",StringUtils.isNotEmpty(vOS)?vOS.get(0):vo);

        return result;
    }

    @DataSource( DataSourceType.SLAVE)
    @Override
    public List<WsElevatorWorkCycleStatisticsVO> statisticsDriverIllegalRecord(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO) {
        if(StringUtils.isEmpty(wsElevatorWorkCycleMangerDTO.getSerialNumbers()))
            return new ArrayList<WsElevatorWorkCycleStatisticsVO>();
        parseBeginAndEndTime(wsElevatorWorkCycleMangerDTO);
        return wsElevatorWorkCycleManagerMapper.statisticsDriverIllegalRecord(wsElevatorWorkCycleMangerDTO);
    }

    @Override
    public List<WsElevatorWorkCycleStatisticsVO> assembleWorkCycleStatistics(List<WsElevatorManagerVO> wsElevatorManagerVOS, List<WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVOS) {
        if(StringUtils.isEmpty(wsElevatorWorkCycleStatisticsVOS)|| StringUtils.isNull(wsElevatorWorkCycleStatisticsVOS.get(0))){
            return new ArrayList<WsElevatorWorkCycleStatisticsVO>();
        }
        List<WsElevatorWorkCycleStatisticsVO> list = Arrays.asList(new WsElevatorWorkCycleStatisticsVO[wsElevatorManagerVOS.size()] );
        HashMap<String, Integer> map = new HashMap<>();
        int index =0;
        for (WsElevatorManagerVO wsElevatorManagerVO : wsElevatorManagerVOS) {
            WsElevatorWorkCycleStatisticsVO vo = new WsElevatorWorkCycleStatisticsVO();
            vo.setDriverName(wsElevatorManagerVO.getDeviceName());
            vo.setElevatorId(wsElevatorManagerVO.getId());
            for (WsElevatorMinitorDevice monitorDevice : wsElevatorManagerVO.getMonitorDevices()) {
               //存放监测设备对应的升降机下标
                map.put(monitorDevice.getSerialNumber(),index);
            }
            list.set(index,vo);
            index++;
        }

        for (WsElevatorWorkCycleStatisticsVO wsElevatorWorkCycleStatisticsVO : wsElevatorWorkCycleStatisticsVOS) {
            if (!map.containsKey(wsElevatorWorkCycleStatisticsVO.getSerialNumber())) {
                continue;
            }

            //如果已添加则做叠加处理
            if(list.stream().anyMatch(vo ->StringUtils.isNotNull(vo) && wsElevatorWorkCycleStatisticsVO.getSerialNumber().equals(vo.getSerialNumber()))){
                WsElevatorWorkCycleStatisticsVO vo1 = list.get(map.get(wsElevatorWorkCycleStatisticsVO.getSerialNumber()));
                vo1.setAlarmingNumber(wsElevatorWorkCycleStatisticsVO.getAlarmingNumber()+vo1.getAlarmingNumber());
                vo1.setPreAlarmingNumber(wsElevatorWorkCycleStatisticsVO.getPreAlarmingNumber()+vo1.getPreAlarmingNumber());
                vo1.setTotalHour(wsElevatorWorkCycleStatisticsVO.getTotalHour()+vo1.getTotalHour());
                vo1.setTotalTravel(wsElevatorWorkCycleStatisticsVO.getTotalTravel()+vo1.getTotalTravel());
                vo1.setTotalWeight(wsElevatorWorkCycleStatisticsVO.getTotalWeight()+vo1.getTotalWeight());
                vo1.setTotalNumber(wsElevatorWorkCycleStatisticsVO.getTotalNumber()+vo1.getTotalNumber());
                list.set(map.get(wsElevatorWorkCycleStatisticsVO.getSerialNumber()),vo1);
            }else{
                WsElevatorWorkCycleStatisticsVO vo2 = list.get(map.get(wsElevatorWorkCycleStatisticsVO.getSerialNumber()));
                vo2.setAlarmingNumber(wsElevatorWorkCycleStatisticsVO.getAlarmingNumber());
                vo2.setPreAlarmingNumber(wsElevatorWorkCycleStatisticsVO.getPreAlarmingNumber());
                vo2.setTotalHour(wsElevatorWorkCycleStatisticsVO.getTotalHour());
                vo2.setTotalTravel(wsElevatorWorkCycleStatisticsVO.getTotalTravel());
                vo2.setTotalWeight(wsElevatorWorkCycleStatisticsVO.getTotalWeight());
                vo2.setSerialNumber(wsElevatorWorkCycleStatisticsVO.getSerialNumber());
                vo2.setTotalNumber(wsElevatorWorkCycleStatisticsVO.getTotalNumber());
                list.set(map.get(wsElevatorWorkCycleStatisticsVO.getSerialNumber()),vo2);
            }
        }
        return list;
    }

    @Override
    public WsElevatorWorkCycleStatisticsVO assembleAllProjectAlarming(List<WsElevatorManagerVO> wsElevatorManagerVOS, List<WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVOS) {

        if(StringUtils.isEmpty(wsElevatorWorkCycleStatisticsVOS)|| StringUtils.isNull(wsElevatorWorkCycleStatisticsVOS.get(0))){
            return new WsElevatorWorkCycleStatisticsVO();
        }
        //获取企业下项目类别
       // List<WsProject> wsProjectScope = getWsProjectScope(wsElevatorWorkCycleMangerDTO);
        //获取不重复的项目列表
        List<WsElevatorWorkCycleStatisticsVO> projectList = wsElevatorManagerVOS.stream()
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(WsElevatorManagerVO::getProjectId))))
                .stream().map(WsElevatorWorkCycleStatisticsVO::new).collect(Collectors.toList());
        //生成项目与监测设备对应中间map
        Map<String, Integer> map = new HashMap<>();
        for (WsElevatorManagerVO wsElevatorManagerVO : wsElevatorManagerVOS) {
            for (WsElevatorMinitorDevice monitorDevice : wsElevatorManagerVO.getMonitorDevices()) {
                map.put(monitorDevice.getSerialNumber(),wsElevatorManagerVO.getProjectId());
            }
        }
        int alarmingProject =0;
        int preAlarmingProject=0;
        //遍历获取报警和告警项目数
        for (WsElevatorWorkCycleStatisticsVO wsElevatorWorkCycleStatisticsVO : projectList) {
            int alarmingCount = wsElevatorWorkCycleStatisticsVOS.stream().filter(vo -> wsElevatorWorkCycleStatisticsVO.getProjectId().equals(map.get(vo.getSerialNumber()))).mapToInt(WsElevatorWorkCycleStatisticsVO::getAlarmingNumber).sum();
            int preAlarmingCount = wsElevatorWorkCycleStatisticsVOS.stream().filter(vo -> wsElevatorWorkCycleStatisticsVO.getProjectId().equals(map.get(vo.getSerialNumber()))).mapToInt(WsElevatorWorkCycleStatisticsVO::getPreAlarmingNumber).sum();
            alarmingProject +=alarmingCount>0?1:0;
            preAlarmingProject +=preAlarmingCount>0?1:0;
        }
        WsElevatorWorkCycleStatisticsVO result = new WsElevatorWorkCycleStatisticsVO();
        result.setWarningProjectPercent((alarmingProject+preAlarmingProject)*100/projectList.size());
        result.setAlarmingProjectPercent(alarmingProject*100/projectList.size());
        result.setAlarmingNumber(alarmingProject);
        result.setTotalNumber(projectList.size());
        result.setPreAlarmingNumber(preAlarmingProject);
        return result;
    }

    @Override
    public List<WsElevatorWorkCycleStatisticsVO> assembleAlarmingByProject(List<WsElevatorManagerVO> wsElevatorManagerVOS, List<WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVOs) {

        if(StringUtils.isEmpty(wsElevatorWorkCycleStatisticsVOs)|| StringUtils.isNull(wsElevatorWorkCycleStatisticsVOs.get(0))){
            return  new ArrayList<WsElevatorWorkCycleStatisticsVO>();
        }
       //获取不重复的项目列表
        List<WsElevatorWorkCycleStatisticsVO> result = wsElevatorManagerVOS.stream()
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(WsElevatorManagerVO::getProjectId))))
                .stream().map(WsElevatorWorkCycleStatisticsVO::new).collect(Collectors.toList());

        //生成项目与监测设备对应中间map
        Map<String, Integer> map = new HashMap<>();
        for (WsElevatorManagerVO wsElevatorManagerVO : wsElevatorManagerVOS) {
            for (WsElevatorMinitorDevice monitorDevice : wsElevatorManagerVO.getMonitorDevices()) {
                map.put(monitorDevice.getSerialNumber(),wsElevatorManagerVO.getProjectId());
            }

        }
        //遍历保存项目对应预警和报警数据
        result.forEach(x->{
            Integer alarmingCount = wsElevatorWorkCycleStatisticsVOs.stream().filter(vo -> x.getProjectId().equals(map.get(vo.getSerialNumber()))).mapToInt(WsElevatorWorkCycleStatisticsVO::getAlarmingNumber).sum();
            Integer preAlarmingCount = wsElevatorWorkCycleStatisticsVOs.stream().filter(vo -> x.getProjectId().equals(map.get(vo.getSerialNumber()))).mapToInt(WsElevatorWorkCycleStatisticsVO::getPreAlarmingNumber).sum();
            x.setAlarmingNumber(alarmingCount);
            x.setPreAlarmingNumber(preAlarmingCount);
        });
        return result;

    }

    @Override
    public List<WsElevatorManagerVO> selectWsElevatorManagerVOList(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO) {
        WsElevatorManagerDTO wsElevatorManagerDTO = new WsElevatorManagerDTO();
        if (StringUtils.isNotNull(wsElevatorWorkCycleMangerDTO)) {
            wsElevatorManagerDTO.setProjectId(wsElevatorWorkCycleMangerDTO.getProjectId());
            wsElevatorManagerDTO.setDeviceName(wsElevatorWorkCycleMangerDTO.getDeviceName());
            wsElevatorManagerDTO.setDeviceType(wsElevatorWorkCycleMangerDTO.getDeviceType());
            wsElevatorManagerDTO.setProjectName(wsElevatorWorkCycleMangerDTO.getProjectName());
            wsElevatorManagerDTO.setRecordNumber(wsElevatorWorkCycleMangerDTO.getRecordNumber());
            wsElevatorManagerDTO.setSerialNumber(wsElevatorWorkCycleMangerDTO.getSerialNumber());
            String keyWordName= wsElevatorWorkCycleMangerDTO.getKeyWordName();
            wsElevatorManagerDTO.setKeyWordName(keyWordName);
            if(StringUtils.isNotEmpty(keyWordName)){
                if(StringUtils.isNotEmpty(wsElevatorWorkCycleMangerDTO.getKeyType())){
                    wsElevatorManagerDTO.setKeyWordName(null);
                    switch (wsElevatorWorkCycleMangerDTO.getKeyType()){
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

        }
    //项目信息为空时添加数据范围
        List<WsProject> projectScope = getWsProjectScope(wsElevatorWorkCycleMangerDTO);

        List<WsElevatorManagerVO> data = wsElevatorManagerMapper.selectWsElevatorManagerVOListByDTO(wsElevatorManagerDTO,projectScope);
        return StringUtils.isEmpty(data)?null:data;
    }

    private List<WsProject> getWsProjectScope(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO) {
        List<WsProject> projectScope = null;
        if(StringUtils.isNull(wsElevatorWorkCycleMangerDTO)||StringUtils.isNull(wsElevatorWorkCycleMangerDTO.getProjectId())){
            if(StringUtils.isNotNull(wsElevatorWorkCycleMangerDTO.getCompanyId())){
                projectScope = getWsProjectScopeByCompanyId(wsElevatorWorkCycleMangerDTO);
            }else{
                projectScope = SecurityUtils.getProjectList();
            }
            if(StringUtils.isEmpty(projectScope)){
                projectScope=new ArrayList<>();
                projectScope.add(new WsProject());
            }
        }
        return projectScope;
    }

    private List<WsProject> getWsProjectScopeByCompanyId(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO) {
        List<WsProject> projectScope;
        List<SysEnterprise> listTree= sysDeptService.selectOrgListByOrgId(wsElevatorWorkCycleMangerDTO.getCompanyId());
        if(StringUtils.isEmpty(listTree)) return  null ;
        projectScope= listTree.stream().map(WsProject::new).collect(Collectors.toList());
        return projectScope;
    }

    @Override
    public List<WsElevatorManagerVO> selectWsElevatorMonitorDeviceList(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO) {
        WsElevatorManagerDTO wsElevatorMangerDTO = new WsElevatorManagerDTO();
        if (StringUtils.isNotNull(wsElevatorWorkCycleMangerDTO)) {
            wsElevatorMangerDTO.setDeviceName(wsElevatorWorkCycleMangerDTO.getDeviceName());
            wsElevatorMangerDTO.setDeviceType(wsElevatorWorkCycleMangerDTO.getDeviceType());
            wsElevatorMangerDTO.setProjectName(wsElevatorWorkCycleMangerDTO.getProjectName());
            wsElevatorMangerDTO.setRecordNumber(wsElevatorWorkCycleMangerDTO.getRecordNumber());
            wsElevatorMangerDTO.setProjectId(wsElevatorWorkCycleMangerDTO.getProjectId());
        }
        //项目信息为空时添加数据范围
        List<WsProject> projectScope = getWsProjectScope(wsElevatorWorkCycleMangerDTO);
        return wsElevatorManagerMapper.selectWsElevatorMonitorDeviceListByDTO(wsElevatorMangerDTO,projectScope);
    }

    @Override
    public List<WsElevatorWorkCycleManagerVO> assembleWorkCycle(List<WsElevatorManagerVO> wsElevatorManagerVOS, List<WsElevatorWorkCycleManagerVO> wsElevatorWorkCycleManagers) {

        if(StringUtils.isEmpty(wsElevatorWorkCycleManagers)|| StringUtils.isNull(wsElevatorWorkCycleManagers.get(0))){
            return  new ArrayList<WsElevatorWorkCycleManagerVO>();
        }
        Map<String, WsElevatorManagerVO> map = new HashMap<>();
        for (WsElevatorManagerVO wsElevatorManagerVO : wsElevatorManagerVOS) {
            for (WsElevatorMinitorDevice monitorDevice : wsElevatorManagerVO.getMonitorDevices()) {
                //key为序列号，value为wsElevatorManagerVO
                map.put(monitorDevice.getSerialNumber(),wsElevatorManagerVO);
            }
        }
        wsElevatorWorkCycleManagers.forEach(record->{
            WsElevatorManagerVO wsElevatorManagerVO = map.get(record.getSerialNumber());
            record.setDeviceName(wsElevatorManagerVO.getDeviceName());
            record.setProvince(wsElevatorManagerVO.getProvince());
            record.setCity(wsElevatorManagerVO.getCity());
            record.setCounty(wsElevatorManagerVO.getCounty());
            record.setProjectName(wsElevatorManagerVO.getProjectName());
            record.setRecordNumber(wsElevatorManagerVO.getRecordNumber());
            record.setCompanyName(wsElevatorManagerVO.getCompanyName());
            for (WsElevatorMinitorDevice monitorDevice : wsElevatorManagerVO.getMonitorDevices()) {
                if(monitorDevice.getSerialNumber().equals(record.getSerialNumber())) record.setCageMark(monitorDevice.getCageMark());
            }

        });

        return wsElevatorWorkCycleManagers;
    }


    @Override
    public CommonExportExcel genIllegalExcel(List<WsElevatorWorkCycleManagerVO> records) {
        ArrayList<String> header = new ArrayList<>();
        ArrayList<List<String>> body = new ArrayList<>();
        ArrayList<String> data;
        for (WsElevatorWorkCycleManagerVO record : records) {
            if(header.size()==0){
                header.add("违章时间");
                header.add("违章内容");
                header.add("项目名称");
                header.add("产权备案号");
                header.add("监控设备(SN)");
                header.add("所属公司");
            }
            data = new ArrayList<>();
            data.add(DateUtil.formatDateTime(record.getInsertTime()));
            data.add(record.getWarningTag());
            data.add(record.getProjectName());
            data.add(record.getRecordNumber());;
            data.add(record.getSerialNumber());
            data.add(record.getCompanyName());
            body.add(data);
        }


        return new CommonExportExcel("违章详细",body,header);
    }

    @Override
    public List<WsElevatorWorkCycleStatisticsVO> assembleAlarmingStatisticsByDate(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO, List<WsElevatorWorkCycleManagerVO> wsElevatorWorkCycleManagers) {
        if(StringUtils.isNull(wsElevatorWorkCycleMangerDTO)){throw new CustomException("数据异常，请联系管理员"); }
        if(StringUtils.isEmpty(wsElevatorWorkCycleManagers)|| StringUtils.isNull(wsElevatorWorkCycleManagers.get(0))){
            return  new ArrayList<WsElevatorWorkCycleStatisticsVO>();
        }
        parseBeginAndEndTime(wsElevatorWorkCycleMangerDTO);
        //初始化前台上传统计格式列表
        List<WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVOS = getIntervalDateStatisticsList(wsElevatorWorkCycleMangerDTO);

        if(StringUtils.isEmpty(wsElevatorWorkCycleManagers)|| StringUtils.isNull(wsElevatorWorkCycleManagers.get(0))){
            return wsElevatorWorkCycleStatisticsVOS;
        }
        //获取符合日期的预警、报警数
        wsElevatorWorkCycleStatisticsVOS.forEach(x -> {
            long preAlarmingCount = wsElevatorWorkCycleManagers.stream().filter(vo -> DateUtil.isSameDay(x.getStatisticsDate(), vo.getInsertTime()) && IntelligentMonitoringConstants.PRE_ALARMING.equals(vo.getWarningType())).count();
            long alarmingCount = wsElevatorWorkCycleManagers.stream().filter(vo -> DateUtil.isSameDay(x.getStatisticsDate(), vo.getInsertTime()) && IntelligentMonitoringConstants.ALARMING.equals(vo.getWarningType())).count();
            x.setPreAlarmingNumber((int) preAlarmingCount);
            x.setAlarmingNumber((int) alarmingCount);
        });
        return wsElevatorWorkCycleStatisticsVOS;
    }

    private List<WsElevatorWorkCycleStatisticsVO> getIntervalDateStatisticsList(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO) {
        int days = Math.abs(StringUtils.isNull(wsElevatorWorkCycleMangerDTO.getDays())?0:wsElevatorWorkCycleMangerDTO.getDays());
        List<WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVOS = Arrays.asList(new WsElevatorWorkCycleStatisticsVO[days]);

        for (int i = 0; i < days; i++) {
            WsElevatorWorkCycleStatisticsVO wsElevatorWorkCycleStatisticsVO = new WsElevatorWorkCycleStatisticsVO();
            wsElevatorWorkCycleStatisticsVO.setStatisticsDate(DateUtil.offsetDay(wsElevatorWorkCycleMangerDTO.getBeginTime(),i));
            wsElevatorWorkCycleStatisticsVOS.set(i,wsElevatorWorkCycleStatisticsVO);
        }
        return wsElevatorWorkCycleStatisticsVOS;
    }

    @Override
    public List<WsElevatorWorkCycleStatisticsVO> statisticsTodayAllInfo(String[] serialNumbers) {
        if(StringUtils.isEmpty(serialNumbers)) return  new ArrayList<WsElevatorWorkCycleStatisticsVO>();
        return wsElevatorWorkCycleManagerMapper.statisticsTodayAllInfo(serialNumbers);
    }

    @Override
    public List<WsElevatorManagerVO> assembleTodayAllInfo(List<WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVOS, List<WsElevatorManagerVO> wsElevatorManagerVOS) {
        List<WsElevatorWorkCycleStatisticsVO> data = wsElevatorManagerVOS.stream().map(WsElevatorWorkCycleStatisticsVO::new).collect(Collectors.toList());
        ArrayList<WsElevatorManagerVO> resultList = wsElevatorManagerVOS.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<WsElevatorManagerVO>(Comparator.comparing(WsElevatorManagerVO::getId))), ArrayList::new));

        data.forEach(x->{
            WsElevatorWorkCycleStatisticsVO wsElevatorWorkCycleStatisticsVO = wsElevatorWorkCycleStatisticsVOS.stream().filter(vo -> vo.getSerialNumber().equals(x.getSerialNumber())).findAny().orElse(null);
            if(StringUtils.isNotNull(wsElevatorWorkCycleStatisticsVO)){
                x.setTotalWeight(wsElevatorWorkCycleStatisticsVO.getTotalWeight());
                x.setTotalNumber(wsElevatorWorkCycleStatisticsVO.getTotalNumber());
                x.setAuthen(wsElevatorWorkCycleStatisticsVO.getAuthen());
                x.setRealTimeWarningStatus(wsElevatorWorkCycleStatisticsVO.getRealTimeWarningStatus());
                x.setOnlineStatus(wsElevatorWorkCycleStatisticsVO.getOnlineStatus());
                x.setPreAlarmingNumber(wsElevatorWorkCycleStatisticsVO.getPreAlarmingNumber());
                x.setAlarmingNumber(wsElevatorWorkCycleStatisticsVO.getAlarmingNumber());

            }

        });

        resultList.forEach(x->{
            List<WsElevatorWorkCycleStatisticsVO> collect = data.stream().filter(d -> x.getId().equals(d.getElevatorId()))
                    .sorted(Comparator.comparing(WsElevatorWorkCycleStatisticsVO::getCageMark)).collect(Collectors.toList());
            x.setStatisticsList(collect);
        });

        return resultList;
    }

    @Override
    public List<WsElevatorWorkCycleStatisticsVO> statisticsRecordsForDays(WsElevatorWorkCycleManagerDTO wsElevatorWorkCycleMangerDTO) {
        if(StringUtils.isNull(wsElevatorWorkCycleMangerDTO)){throw new CustomException("数据异常，请联系管理员"); }

        parseBeginAndEndTime(wsElevatorWorkCycleMangerDTO);
        //初始化前台上传统计格式列表
        List<WsElevatorWorkCycleStatisticsVO> wsElevatorWorkCycleStatisticsVOS = getIntervalDateStatisticsList(wsElevatorWorkCycleMangerDTO);
        List<WsElevatorWorkCycleManagerVO> wsElevatorWorkCycleManagerVOS = wsElevatorWorkCycleManagerMapper.selectElevatorWorkRecord(wsElevatorWorkCycleMangerDTO);

        wsElevatorWorkCycleStatisticsVOS.forEach(x->{
            long totalNumber = wsElevatorWorkCycleManagerVOS.stream().filter(vo -> DateUtil.isSameDay(vo.getInsertTime(), x.getStatisticsDate())).count();
            long alarmingCount = wsElevatorWorkCycleManagerVOS.stream().filter(vo -> DateUtil.isSameDay(x.getStatisticsDate(), vo.getInsertTime()) && IntelligentMonitoringConstants.ALARMING.equals(vo.getWarningType())).count();
            x.setTotalNumber((int)totalNumber);
            x.setAlarmingNumber((int)alarmingCount);

        });


        return wsElevatorWorkCycleStatisticsVOS;
    }
}
