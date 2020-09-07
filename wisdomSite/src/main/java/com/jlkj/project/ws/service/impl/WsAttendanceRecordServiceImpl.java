package com.jlkj.project.ws.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.jlkj.common.constant.Constants;
import com.jlkj.common.constant.LaborConstants;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.DateUtils;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.ServletUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.spring.SpringUtils;
import com.jlkj.framework.redis.RedisCache;
import com.jlkj.framework.security.LoginUser;
import com.jlkj.framework.security.service.TokenService;
import com.jlkj.project.system.domain.SysUser;
import com.jlkj.project.ws.domain.*;
import com.jlkj.project.ws.domain.vo.WsAttendanceRecordDetailVO;
import com.jlkj.project.ws.domain.vo.WsAttendanceRecordVO;
import com.jlkj.project.ws.dto.WsSiteAttendanceStatisticsDTO;
import com.jlkj.project.ws.mapper.*;
import com.jlkj.project.ws.service.IWsAttendanceRecordService;
import com.jlkj.project.ws.service.IWsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 考勤记录 Service业务层处理
 *
 * @author gaowei
 * @date 2020-06-23
 */
@Service
public class WsAttendanceRecordServiceImpl implements IWsAttendanceRecordService
{
    @Autowired
    private WsAttendanceRecordMapper wsAttendanceRecordMapper;
    @Autowired
    private WsSiteAttendanceStatisticsMapper wsSiteAttendanceStatisticsMapper;
    @Autowired
    private WsProjectMapper wsProjectMapper;
    @Autowired
    private WsParticipatingTeamMapper wsParticipatingTeamMapper;

    @Autowired
    private WsProjectParticipatingUnitMapper wsProjectParticipatingUnitMapper;

    @Autowired
    private WsTeamPersonMapper wsTeamPersonMapper;

    @Autowired
    private IWsProjectService wsProjectService;

    /**
     * 查询考勤记录
     *
     * @param id 考勤记录 ID
     * @return 考勤记录
     */
    @Override
    public WsAttendanceRecord selectWsAttendanceRecordById(Integer id)
    {
        return wsAttendanceRecordMapper.selectWsAttendanceRecordById(id);
    }



    /**
     * 查询考勤记录 列表
     *
     * @param wsAttendanceRecordVO 考勤记录
     * @return 考勤记录
     */
    @Override
    public List<WsTeamPerson> selectAttendanceReportForms(WsAttendanceRecordVO wsAttendanceRecordVO)
    {
        if(StringUtils.isNull(wsAttendanceRecordVO.getYearAndMonth())){
            throw new CustomException("请选择月份");
        }

        int monthOfDays = DateUtil.endOfMonth(wsAttendanceRecordVO.getYearAndMonth()).dayOfMonth();
        paresBeginAndEndTime(wsAttendanceRecordVO);

        //获取人员列表
        WsTeamPerson wsTeamPerson = new WsTeamPerson();
        wsTeamPerson.setProjectId(wsAttendanceRecordVO.getProjectId());
        wsTeamPerson.setUnitId(wsAttendanceRecordVO.getUnitId());
        wsTeamPerson.setTeamId(wsAttendanceRecordVO.getTeamId());
        wsTeamPerson.setId(wsAttendanceRecordVO.getTeamPersonId());
        wsTeamPerson.setWorkType(wsAttendanceRecordVO.getWorkType());
        wsTeamPerson.setDeleted(LaborConstants.NO);
        List projectScope=null;
        if(StringUtils.isNull(wsTeamPerson.getProjectId())){
            projectScope=  SecurityUtils.getProjectList();
        }
        wsTeamPerson.setDeleted(LaborConstants.NO);
        List<WsTeamPerson> personList = wsTeamPersonMapper.selectWsTeamPersonList(wsTeamPerson,projectScope);
        List<WsAttendanceRecordVO> list = wsAttendanceRecordMapper.selectAttendanceReportForms(wsAttendanceRecordVO);
        personList.forEach(x->{
            List<Float> days = Arrays.asList(new Float[monthOfDays]);
            Float totalAttendanceHours = 0f;
            List<WsAttendanceRecordVO> vos = StringUtils.isEmpty(list) ? null:
                    list.stream().filter(r -> r.getTeamPersonId().equals(x.getId())).collect(Collectors.toList());
            int totalAttendanceDays = StringUtils.isNull(vos)?0:vos.size();
            for (int i = 0; i < days.size(); i++) {
                int finalI = i;
                WsAttendanceRecordVO record =StringUtils.isEmpty(vos) ? null:
                        vos.stream().filter(vo -> vo.getMonthOfDay() - 1 == finalI).findAny().orElse(null);
                if(StringUtils.isNull(record)){
                    days.set(i,null);
                }else{
                    days.set(i,record.getManHour());
                    totalAttendanceHours +=record.getManHour();
                }
            }
            x.setTotalAttendanceDays(totalAttendanceDays);
            x.setTotalAttendanceHours(NumberUtil.round(totalAttendanceHours,1).floatValue());
            x.setManHours(days);
        });
        return personList;
        /*
        //工程人员ID为key，考勤记录VOvalue
        HashMap<Integer, WsAttendanceRecordVO> recordMap   = new HashMap<>();
        List days = null;

        for (WsAttendanceRecordVO attendanceRecordVO : list) {
            if (recordMap.containsKey(attendanceRecordVO.getTeamPersonId())) {
                WsAttendanceRecordVO record= recordMap.get(attendanceRecordVO.getTeamPersonId());
                //获取时长
                List<Float> manHours = record.getManHours();
                manHours.set(attendanceRecordVO.getMonthOfDay()-1,attendanceRecordVO.getManHour());
                //向出勤记录累加时长、天数，并更新时长列表
                record.setManHours(manHours);
                record.setTotalAttendanceDays(record.getTotalAttendanceDays()+1);
                record.setTotalAttendanceHours(record.getTotalAttendanceHours()+attendanceRecordVO.getManHour());
                recordMap.put(record.getTeamPersonId(),record);
            }else{
                //首次新增，设置首次时长、天数（默认为0），添加时长列表
                days = Arrays.asList(new Float[monthOfDays]);
                attendanceRecordVO.setTotalAttendanceHours(0F);
                attendanceRecordVO.setTotalAttendanceDays(0);
                if(StringUtils.isNotNull(wsAttendanceRecordVO.getMonthOfDay())){
                    days.set(attendanceRecordVO.getMonthOfDay()-1,attendanceRecordVO.getManHour());
                    attendanceRecordVO.setTotalAttendanceHours(attendanceRecordVO.getManHour());
                    attendanceRecordVO.setTotalAttendanceDays(1);
                }
                attendanceRecordVO.setManHours(days);
                recordMap.put(attendanceRecordVO.getTeamPersonId(),attendanceRecordVO);
            }
        }

        List<WsAttendanceRecordVO> resultList = new ArrayList<>();
        for (Map.Entry<Integer, WsAttendanceRecordVO> entry : recordMap.entrySet()) {
            resultList.add(entry.getValue());
        } */

    }

    private List<WsProject> getProjectScope() {

        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        String token = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest()).getToken();
        LoginUser loginUser = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY + token);
        if(StringUtils.isNull(loginUser)) throw new CustomException("无数据权限！");
        SysUser user = loginUser.getUser();
        if(StringUtils.isNull(loginUser)) throw new CustomException("无数据权限！");
        return user.getProjects();
    }

    private void paresBeginAndEndTime(WsAttendanceRecordVO wsAttendanceRecordVO) {
        Date beginTime = DateUtil.beginOfMonth(wsAttendanceRecordVO.getYearAndMonth());
        Date endTime = DateUtil.endOfMonth(wsAttendanceRecordVO.getYearAndMonth());
        wsAttendanceRecordVO.setBeginTime(DateUtil.format(beginTime,"yyyy-MM-dd HH:mm:ss"));
        wsAttendanceRecordVO.setEndTime(DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 新增考勤记录
     *
     * @param wsAttendanceRecord 考勤记录
     * @return 结果
     */
    @Override
    public int insertWsAttendanceRecord(WsAttendanceRecord wsAttendanceRecord)
    {
        return wsAttendanceRecordMapper.insertWsAttendanceRecord(wsAttendanceRecord);
    }

    /**
     * 修改考勤记录
     *
     * @param wsAttendanceRecord 考勤记录
     * @return 结果
     */
    @Override
    public int updateWsAttendanceRecord(WsAttendanceRecord wsAttendanceRecord)
    {
        return wsAttendanceRecordMapper.updateWsAttendanceRecord(wsAttendanceRecord);
    }

    /**
     * 批量删除考勤记录
     *
     * @param ids 需要删除的考勤记录 ID
     * @return 结果
     */
    @Override
    public int deleteWsAttendanceRecordByIds(Integer[] ids)
    {
        return wsAttendanceRecordMapper.deleteWsAttendanceRecordByIds(ids);
    }

    /**
     * 删除考勤记录 信息
     *
     * @param id 考勤记录 ID
     * @return 结果
     */
    @Override
    public int deleteWsAttendanceRecordById(Integer id)
    {
        return wsAttendanceRecordMapper.deleteWsAttendanceRecordById(id);
    }

    /**
     * 获取首页 人员在岗，在册，在场，出勤数据
     * @return
     */
    @Override
    public WsSiteAttendanceStatisticsDTO selectPersonnelNumber() {

        WsSiteAttendanceStatisticsDTO attendanceStatisticsDTO = new WsSiteAttendanceStatisticsDTO();
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
        //今日在岗
        //WsSiteAttendanceStatisticsDTO attendanceStatisticsDTOOnDutyJc = wsSiteAttendanceStatisticsMapper.selectPersonnelNumberOnDutyJc(projectIds);//进场人数
        //WsSiteAttendanceStatisticsDTO attendanceStatisticsDTOOnDutyCc = wsSiteAttendanceStatisticsMapper.selectPersonnelNumberOnDutyCc(projectIds);//出场人数
        /*if(attendanceStatisticsDTOOnDutyJc != null || attendanceStatisticsDTOOnDutyCc != null){
            if(attendanceStatisticsDTOOnDutyJc.getOnDuty() != null && attendanceStatisticsDTOOnDutyJc.getOnDuty() > 0){
                if(attendanceStatisticsDTOOnDutyCc.getOnDuty() != null && attendanceStatisticsDTOOnDutyCc.getOnDuty() >= 0){
                    int xcrs = attendanceStatisticsDTOOnDutyJc.getOnDuty() - attendanceStatisticsDTOOnDutyCc.getOnDuty();
                    String str1 = Integer.toString(xcrs);
                    int result1 = str1.indexOf("-");
                    if(result1 != -1){
                        attendanceStatisticsDTO.setOnDuty(0);
                    }else{
                        attendanceStatisticsDTO.setOnDuty(xcrs);
                    }
                }
            }else{
                attendanceStatisticsDTO.setOnDuty(0);
            }
        }else{
            attendanceStatisticsDTO.setOnDuty(0);
        }*/
        //今日现场
        WsSiteAttendanceStatisticsDTO attendanceStatisticsDTOOnDutyCc = wsSiteAttendanceStatisticsMapper.selectPersonnelNumberOnDutyCc(projectIds);//出场人数
        //今日出勤
        WsSiteAttendanceStatisticsDTO attendanceStatisticsDTOAttendanceToday = wsSiteAttendanceStatisticsMapper.selectPersonnelNumberAttendanceToday(projectIds);
        if(attendanceStatisticsDTOAttendanceToday != null){
            if(attendanceStatisticsDTOAttendanceToday.getAttendanceToday() != null){
                attendanceStatisticsDTO.setAttendanceToday(attendanceStatisticsDTOAttendanceToday.getAttendanceToday());
                if(attendanceStatisticsDTOOnDutyCc.getOnDuty() != null && attendanceStatisticsDTOOnDutyCc.getOnDuty() >= 0){
                    int xcrs = attendanceStatisticsDTOAttendanceToday.getAttendanceToday() - attendanceStatisticsDTOOnDutyCc.getOnDuty();
                    String str1 = Integer.toString(xcrs);
                    int result1 = str1.indexOf("-");
                    if(result1 != -1){
                        attendanceStatisticsDTO.setOnDuty(0);
                    }else{
                        attendanceStatisticsDTO.setOnDuty(xcrs);
                    }
                }else{
                    attendanceStatisticsDTO.setOnDuty(0);
                }
            }else{
                attendanceStatisticsDTO.setAttendanceToday(0);
                attendanceStatisticsDTO.setOnDuty(0);
            }
        }else{
            attendanceStatisticsDTO.setAttendanceToday(0);
            attendanceStatisticsDTO.setOnDuty(0);
        }


        //在场
        WsSiteAttendanceStatisticsDTO attendanceStatisticsDTOBePresent = wsSiteAttendanceStatisticsMapper.selectPersonnelNumberBePresent(projectIds);
        if(attendanceStatisticsDTOBePresent != null){
            if(attendanceStatisticsDTOBePresent.getBePresent() != null){
                attendanceStatisticsDTO.setBePresent(attendanceStatisticsDTOBePresent.getBePresent());
            }else{
                attendanceStatisticsDTO.setBePresent(0);
            }
        }else{
            attendanceStatisticsDTO.setBePresent(0);
        }

        //今日出勤率
        if(attendanceStatisticsDTOAttendanceToday != null && attendanceStatisticsDTOBePresent != null){
            if(attendanceStatisticsDTOAttendanceToday.getAttendanceToday() != null && attendanceStatisticsDTOBePresent.getBePresent() != null){
                if(attendanceStatisticsDTOAttendanceToday.getAttendanceToday() > 0 && attendanceStatisticsDTOBePresent.getBePresent() > 0){
                    double attendanceTodayRate = attendanceStatisticsDTOAttendanceToday.getAttendanceToday() / attendanceStatisticsDTOBePresent.getBePresent() * 100;
                    DecimalFormat df = new DecimalFormat("#.00");
                    String str = df.format(attendanceTodayRate);
                    attendanceStatisticsDTO.setAttendanceTodayRate(Double.parseDouble(str));
                }
            }else{
                attendanceStatisticsDTO.setAttendanceTodayRate(0.00);
            }
        }else{
            attendanceStatisticsDTO.setAttendanceTodayRate(0.00);
        }

        //在册
        WsSiteAttendanceStatisticsDTO attendanceStatisticsDTORegistered = wsSiteAttendanceStatisticsMapper.selectPersonnelNumberRegistered(projectIds);
        if(attendanceStatisticsDTORegistered != null){
            if(attendanceStatisticsDTORegistered.getRegistered() != null){
                attendanceStatisticsDTO.setRegistered(attendanceStatisticsDTORegistered.getRegistered());
            }else{
                attendanceStatisticsDTO.setRegistered(0);
            }
        }else{
            attendanceStatisticsDTO.setRegistered(0);
        }
        //项目
        attendanceStatisticsDTO.setProjectNumber(list.size());
        //参建单位
        List<WsSiteAttendanceStatisticsDTO> attendanceStatisticsDTOParticipatingUnitNumber = wsSiteAttendanceStatisticsMapper.selectParticipatingUnitNumber(projectIds);
        if(attendanceStatisticsDTOParticipatingUnitNumber.size() > 0){
            attendanceStatisticsDTO.setParticipatingUnitNumber(attendanceStatisticsDTOParticipatingUnitNumber.size());
        }else{
            attendanceStatisticsDTO.setParticipatingUnitNumber(0);
        }
        return attendanceStatisticsDTO;
    }

    @Override
    public List<WsSiteAttendanceStatisticsDTO> selectBePresentORattendanceToday() {
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
        List<WsSiteAttendanceStatisticsDTO> listStatisticsDTO = wsSiteAttendanceStatisticsMapper.selectBePresentORattendanceToday(projectIds);
        for(int i = 0; i < listStatisticsDTO.size(); i++){
            WsSiteAttendanceStatisticsDTO attendanceStatisticsDTO = wsSiteAttendanceStatisticsMapper.selectBePresentORattendanceTodayCq(projectIds,listStatisticsDTO.get(i).getBePresentORattendanceTodayTime());
            listStatisticsDTO.get(i).setBePresentNumber(attendanceStatisticsDTO.getBePresentNumber());
        }
        return listStatisticsDTO;
    }

    @Override
    public List<WsSiteAttendanceStatisticsDTO> selectPersonnelProjectProportion() {
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
        return wsSiteAttendanceStatisticsMapper.selectPersonnelProjectProportion(projectIds);
    }

    @Override
    public List<WsSiteAttendanceStatisticsDTO> selectPersonnelProjectProportionWhole() {
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
        return wsSiteAttendanceStatisticsMapper.selectPersonnelProjectProportionWhole(projectIds);
    }

    @Override
    public List<WsSiteAttendanceStatisticsDTO> selectProjectAttendance(String randomData) {
        List<WsSiteAttendanceStatisticsDTO> listSiteAttendance = new ArrayList<WsSiteAttendanceStatisticsDTO>();
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
        listSiteAttendance = wsSiteAttendanceStatisticsMapper.selectProjectAttendanceProject(projectIds);
        if(list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                WsSiteAttendanceStatisticsDTO siteAttendance = wsSiteAttendanceStatisticsMapper.selectProjectAttendance(list.get(i).getId(),randomData);
                WsProject project = wsProjectMapper.selectWsProjectById(list.get(i).getId());
                listSiteAttendance.get(i).setSceneNumber(siteAttendance.getSceneNumber());
                listSiteAttendance.get(i).setAttendanceNumber(siteAttendance.getAttendanceNumber());
                listSiteAttendance.get(i).setPresenceNumber(siteAttendance.getPresenceNumber());
                listSiteAttendance.get(i).setProjectName(project.getProjectName());
            }
        }
        return listSiteAttendance;
    }

    @Override
    public List<WsSiteAttendanceStatisticsDTO> selectScenePersonnelChangeTrend() {
        Integer projectId = 0;
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                projectId = list.get(i).getId();
            }
        }
        return wsSiteAttendanceStatisticsMapper.selectScenePersonnelChangeTrend(projectId);
    }

    @Override
    public List<WsSiteAttendanceStatisticsDTO> selectSevenDaysPersonnelAttendance() {
        Integer projectId = 0;
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                projectId = list.get(i).getId();
            }
        }
        return wsSiteAttendanceStatisticsMapper.selectSevenDaysPersonnelAttendance(projectId);
    }

    @Override
    public List<WsSiteAttendanceStatisticsDTO> selectFieldWork() {
        Integer projectId = 0;
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                projectId = list.get(i).getId();
            }
        }
        return wsSiteAttendanceStatisticsMapper.selectFieldWork(projectId);
    }

    @Override
    public List<WsSiteAttendanceStatisticsDTO> selectFieldWorkWhole() {
        Integer projectId = 0;
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                projectId = list.get(i).getId();
            }
        }
        return wsSiteAttendanceStatisticsMapper.selectFieldWorkWhole(projectId);
    }

    @Override
    public List<WsSiteAttendanceStatisticsDTO> selectConstructionWorker(WsSiteAttendanceStatisticsDTO wsSiteAttendanceStatisticsDTO) {
        List<WsSiteAttendanceStatisticsDTO> list = new ArrayList<WsSiteAttendanceStatisticsDTO>();
        Integer projectId = 0;
        WsProject wsProject = new WsProject();
        List<WsProject> listWsProject = wsProjectService.selectWsProjectList(wsProject);
        if(listWsProject.size() > 0) {
            for (int i = 0; i < listWsProject.size(); i++) {
                projectId = listWsProject.get(i).getId();
            }
        }
        if(wsSiteAttendanceStatisticsDTO.getType().equals("1")){//地区
            list = wsSiteAttendanceStatisticsMapper.selectConstructionWorkerRegion(projectId);
        }else if(wsSiteAttendanceStatisticsDTO.getType().equals("2")){//年龄
            list = wsSiteAttendanceStatisticsMapper.selectConstructionWorkerAge(projectId);
        }else if(wsSiteAttendanceStatisticsDTO.getType().equals("3")){//性别
            list = wsSiteAttendanceStatisticsMapper.selectConstructionWorkerSex(projectId);
        }else if(wsSiteAttendanceStatisticsDTO.getType().equals("4")){//工种
            list = wsSiteAttendanceStatisticsMapper.selectConstructionWorkerProfession(projectId);
        }
        return list;
    }

    @Override
    public List<WsSiteAttendanceStatisticsDTO> selectConstructionWorkerWhole(WsSiteAttendanceStatisticsDTO wsSiteAttendanceStatisticsDTO) {
        List<WsSiteAttendanceStatisticsDTO> list = new ArrayList<WsSiteAttendanceStatisticsDTO>();
        Integer projectId = 0;
        WsProject wsProject = new WsProject();
        List<WsProject> listWsProject = wsProjectService.selectWsProjectList(wsProject);
        if(listWsProject.size() > 0) {
            for (int i = 0; i < listWsProject.size(); i++) {
                projectId = listWsProject.get(i).getId();
            }
        }
        if(wsSiteAttendanceStatisticsDTO.getType().equals("1")){//地区
            list = wsSiteAttendanceStatisticsMapper.selectConstructionWorkerRegionWhole(projectId);
        }else if(wsSiteAttendanceStatisticsDTO.getType().equals("2")){//年龄
            list = wsSiteAttendanceStatisticsMapper.selectConstructionWorkerAgeWhole(projectId);
        }else if(wsSiteAttendanceStatisticsDTO.getType().equals("3")){//性别
            list = wsSiteAttendanceStatisticsMapper.selectConstructionWorkerSexWhole(projectId);
        }else if(wsSiteAttendanceStatisticsDTO.getType().equals("4")){//工种
            list = wsSiteAttendanceStatisticsMapper.selectConstructionWorkerProfessionWhole(projectId);
        }
        return list;
    }

    @Override
    public HashMap<String, Object> getFilterInfo(WsAttendanceRecordVO wsAttendanceRecordVO) {
        HashMap<String,Object> excelMap = new HashMap<>();
        if (StringUtils.isNull(wsAttendanceRecordVO)) {return excelMap;}
        Date yearAndMonth =wsAttendanceRecordVO.getYearAndMonth();
        excelMap.put("yearAndMonth",StringUtils.isNull(yearAndMonth)?new Date():yearAndMonth);
        String projectName="";
        if (StringUtils.isNotNull(wsAttendanceRecordVO.getProjectId())) {
            WsProject wsProject = wsProjectMapper.selectWsProjectById(wsAttendanceRecordVO.getProjectId());
            if (StringUtils.isNotNull(wsProject)) {
                projectName = wsProject.getProjectName();
            }
        }
        String teamName ="全部";
        if (StringUtils.isNotNull(wsAttendanceRecordVO.getTeamId())) {
            WsParticipatingTeam wsParticipatingTeam = wsParticipatingTeamMapper.selectWsParticipatingTeamById(wsAttendanceRecordVO.getTeamId());
            if (StringUtils.isNotNull(wsParticipatingTeam)) {
                teamName=wsParticipatingTeam.getTeamName();
            }
        }
        String unitName ="全部";
        if (StringUtils.isNotNull(wsAttendanceRecordVO.getUnitId())) {
            WsProjectParticipatingUnit wsProjectParticipatingUnit = wsProjectParticipatingUnitMapper.selectWsProjectParticipatingUnitById(wsAttendanceRecordVO.getUnitId());
            unitName=wsProjectParticipatingUnit.getUnitName();
        }
        excelMap.put("projectName",projectName);
        excelMap.put("teamName",teamName);
        excelMap.put("unitName",unitName);
        return excelMap;
    }

    @Override
    public WsAttendanceRecordDetailVO selectWsAttendanceRecordDetailList(WsAttendanceRecordDetailVO wsAttendanceRecordDetailVO) {
        int monthOfDays = DateUtil.endOfMonth(wsAttendanceRecordDetailVO.getYearAndMonth()).dayOfMonth();
        Date beginTime = DateUtil.beginOfMonth(wsAttendanceRecordDetailVO.getYearAndMonth());
        Date endTime = DateUtil.endOfMonth(wsAttendanceRecordDetailVO.getYearAndMonth());
        wsAttendanceRecordDetailVO.setBeginTime(DateUtil.format(beginTime,"yyyy-MM-dd HH:mm:ss"));
        wsAttendanceRecordDetailVO.setEndTime(DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss"));
        List<WsAttendanceRecordDetailVO> recordDetaiList = wsAttendanceRecordMapper.selectWsAttendanceRecordDetailList(wsAttendanceRecordDetailVO);
        List<WsAttendanceRecordDetailVO> recordDays = Arrays.asList(new WsAttendanceRecordDetailVO[monthOfDays]);
        WsAttendanceRecordDetailVO resultRecord = new WsAttendanceRecordDetailVO();
/*        //给列表赋初始值
        WsAttendanceRecordDetailVO detailVO=null;
        for (int i = 0; i <monthOfDays ; i++) {
            detailVO = new WsAttendanceRecordDetailVO();
            detailVO.setRecordDays(new ArrayList<>(Arrays.asList(new WsAttendanceRecordDetailVO(),new WsAttendanceRecordDetailVO(),new WsAttendanceRecordDetailVO(),new WsAttendanceRecordDetailVO(),new WsAttendanceRecordDetailVO(),
                    new WsAttendanceRecordDetailVO(),new WsAttendanceRecordDetailVO(),new WsAttendanceRecordDetailVO(),new WsAttendanceRecordDetailVO(),new WsAttendanceRecordDetailVO(),
                    new WsAttendanceRecordDetailVO(),new WsAttendanceRecordDetailVO(),new WsAttendanceRecordDetailVO(),new WsAttendanceRecordDetailVO(),new WsAttendanceRecordDetailVO(),
                    new WsAttendanceRecordDetailVO(),new WsAttendanceRecordDetailVO(),new WsAttendanceRecordDetailVO(),new WsAttendanceRecordDetailVO(),new WsAttendanceRecordDetailVO())));
            recordDays.set(i,detailVO);
        }
        resultRecord.setRecordDays(recordDays);
        //当人员无考勤数据返回元素为null的空数组
        if(StringUtils.isNull(recordDetaiList)||recordDetaiList.size()==0){return  resultRecord;}
        boolean b =true;
        int totalDays=0;
        HashMap<Integer, List<WsAttendanceRecordDetailVO>> realRecordsOfDays = new HashMap<>();
        List<WsAttendanceRecordDetailVO> realRecords;//人员真实考勤列表
        List<WsAttendanceRecordDetailVO> replacer;
        for (WsAttendanceRecordDetailVO record : recordDetaiList) {
            //首次存入整体考勤信息，供页面抬头显示
            if (b) {
                resultRecord.setUnitName(record.getUnitName());
                resultRecord.setTeamName(record.getTeamName());
                resultRecord.setWorkTypeName(record.getWorkTypeName());
                resultRecord.setDirection(record.getDirection());
                b=false;
            }
            //人员无考勤则进行下次循环
            if(StringUtils.isNull(record.getMonthOfDay())){
                continue;
            }
            //人员一天有多次考勤则追加至考勤记录列表中，否则添加考勤列表并加入本次考勤，然后放入临时map中
            if(!realRecordsOfDays.containsKey(record.getMonthOfDay())){
                realRecords= new ArrayList<WsAttendanceRecordDetailVO>();
                realRecords.add(record);
                realRecordsOfDays.put(record.getMonthOfDay(),realRecords);
            }else{
                replacer= realRecordsOfDays.get(record.getMonthOfDay());
                replacer.add(record);
                realRecordsOfDays.put(record.getMonthOfDay(),replacer);

            }
        }
        //循环临时map，将考勤数据列表放入指定下标元素的考勤列表中
        for (Map.Entry<Integer, List<WsAttendanceRecordDetailVO>> entry : realRecordsOfDays.entrySet()) {
            WsAttendanceRecordDetailVO recordDetailVO = new WsAttendanceRecordDetailVO();
            //获取初始化考勤列表
            List<WsAttendanceRecordDetailVO> curAttendance = recordDays.get(entry.getKey()).getRecordDays();
            curAttendance.addAll(entry.getValue());
            Collections.sort(curAttendance, new Comparator<WsAttendanceRecordDetailVO>() {
                @Override
                public int compare(WsAttendanceRecordDetailVO o1, WsAttendanceRecordDetailVO o2) {
                    if (StringUtils.isNull(o1.getAttendanceTime())&&StringUtils.isNull(o2.getAttendanceTime())) {
                        return 0;
                    }
                    if (StringUtils.isNull(o1.getAttendanceTime())) {
                        return -1;
                    }
                    if(StringUtils.isNull(o2.getAttendanceTime())){
                        return 1;
                    }
                    return((Long) DateUtil.betweenMs(o1.getAttendanceTime(),o2.getAttendanceTime())).intValue();
                }
            });
            recordDetailVO.setRecordDays(curAttendance.subList(0,19));
            recordDays.set(entry.getKey(),recordDetailVO);
            totalDays++;
        }
*/
        //初始化出勤天数列表
        List<List<Map<String,String>>>recordDaysOfAttendance = Arrays.asList(new ArrayList[monthOfDays]);
        //给列表赋初始值
        List<Map<String,String>> detail=null;
        for (int i = 0; i <monthOfDays ; i++) {
            detail = new ArrayList(Arrays.asList(new HashMap(),new HashMap(),new HashMap(),new HashMap(),new HashMap(),
                    new HashMap(),new HashMap(),new HashMap(),new HashMap(),new HashMap(),
                    new HashMap(),new HashMap(),new HashMap(),new HashMap(),new HashMap(),
                    new HashMap(),new HashMap(),new HashMap(),new HashMap(),new HashMap()));

            recordDaysOfAttendance.set(i,detail);
        }
        resultRecord.setAttendanceDataOfMonth(recordDaysOfAttendance);
        //当人员无考勤数据返回元素为null的空数组
        if(StringUtils.isNull(recordDetaiList)||recordDetaiList.size()==0){return  resultRecord;}
        boolean isFirst =true;
        int totalDaysCount=0;
        HashMap<Integer, List<HashMap<String,String>>> realRecordsOfDaysMap = new HashMap<>();
        List<HashMap<String,String>> realRecordsList;//人员真实考勤列表
        List<HashMap<String,String>> tempList;
        for (WsAttendanceRecordDetailVO record : recordDetaiList) {
            //首次存入整体考勤信息，供页面抬头显示
            if (isFirst) {
                resultRecord.setUnitName(record.getUnitName());
                resultRecord.setTeamName(record.getTeamName());
                resultRecord.setWorkTypeName(record.getWorkTypeName());
                resultRecord.setDirection(record.getDirection());
                isFirst=false;
            }
            //人员无考勤则进行下次循环
            if(StringUtils.isNull(record.getMonthOfDay())){
                continue;
            }
            //人员一天有多次考勤则追加至考勤记录列表中，否则添加考勤列表并加入本次考勤，然后放入临时map中
            if(!realRecordsOfDaysMap.containsKey(record.getMonthOfDay())){
                realRecordsList= new ArrayList<HashMap<String,String>>();
                HashMap<String, String> attendance = new HashMap<>();
                attendance.put("direction",record.getDirection());
                attendance.put("attendanceTime",DateUtil.format(record.getAttendanceTime(),"HH:mm:ss"));
                realRecordsList.add(attendance);
                realRecordsOfDaysMap.put(record.getMonthOfDay(),realRecordsList);
            }else{
                tempList= realRecordsOfDaysMap.get(record.getMonthOfDay());
                HashMap<String, String> attendance = new HashMap<>();
                attendance.put("direction",record.getDirection());
                attendance.put("attendanceTime",DateUtil.format(record.getAttendanceTime(),"HH:mm:ss"));
                tempList.add(attendance);
                realRecordsOfDaysMap.put(record.getMonthOfDay(),tempList);

            }
        }

        //循环临时map，将考勤数据列表放入指定下标元素的考勤列表中
        for (Map.Entry<Integer, List<HashMap<String,String>>> entry : realRecordsOfDaysMap.entrySet()) {
            List<Map<String,String>>  recordDetailMapList = new ArrayList<>();
            //获取初始化考勤列表
            List<Map<String,String>> curAttendance = recordDaysOfAttendance.get(entry.getKey());
            curAttendance.addAll(entry.getValue());
            Collections.sort(curAttendance, new Comparator<Map<String, String>>() {
                @Override
                public int compare(Map<String, String> o1, Map<String, String> o2) {
                    if (StringUtils.isEmpty(o1.get("attendanceTime"))&&StringUtils.isEmpty(o2.get("attendanceTime"))) {
                        return 0;
                    }
                    if (StringUtils.isEmpty(o1.get("attendanceTime"))) {
                        return 1;
                    }
                    if(StringUtils.isEmpty(o2.get("attendanceTime"))){
                        return -1;
                    }
                    //转换为date后，获取日期差值
                    return((Long) DateUtil.betweenMs(DateUtil.parse(o1.get("attendanceTime")),DateUtil.parse(o2.get("attendanceTime")))).intValue();
                }
            });
            if(curAttendance.size()>20){
                for (int i = 0; i < curAttendance.size()-20; i++) {
                    curAttendance.remove(i+19);
                }
            }
            recordDaysOfAttendance.set(entry.getKey(),curAttendance);
            totalDaysCount++;
        }
        resultRecord.setAttendanceDataOfMonth(recordDaysOfAttendance);
        resultRecord.setTotalAttendanceDays(totalDaysCount);
        return resultRecord;
    }


    @Override
    public List<WsSiteAttendanceStatisticsDTO> selectParticipatingUnits(String randomData) {
        Integer projectId = 0;
        WsProject wsProject = new WsProject();
        List<WsProject> listWsProject = wsProjectService.selectWsProjectList(wsProject);
        if(listWsProject.size() > 0) {
            for (int i = 0; i < listWsProject.size(); i++) {
                projectId = listWsProject.get(i).getId();
            }
        }
        return wsSiteAttendanceStatisticsMapper.selectParticipatingUnits(projectId,randomData);
    }


    @Override
    public List<WsAttendanceRecord> selectAttendanceRecordList(WsAttendanceRecord wsAttendanceRecord) {
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
        return wsAttendanceRecordMapper.selectAttendanceRecordList(wsAttendanceRecord,projectIds);
    }


    @Override
    public List<WsSiteAttendanceStatisticsDTO> selectPersonAttendanceTrend(WsAttendanceRecordVO wsAttendanceRecordVO) {

        //获取前端所需格式：连续整点时间
        List<WsSiteAttendanceStatisticsDTO> result = get24HourWsSiteAttendanceStatisticsDTOS();
        List<WsProject> projectScope=null;
        if(StringUtils.isNull(wsAttendanceRecordVO.getProjectId())){
            projectScope=  SecurityUtils.getProjectList();
        }
        Date now = new Date();
        if(StringUtils.isNotEmpty(projectScope)) wsAttendanceRecordVO.setProjectId(projectScope.get(0).getId());
        wsAttendanceRecordVO.setBeginTime(DateUtil.format(DateUtil.beginOfDay(now), DateUtils.YYYY_MM_DD_HH_MM_SS));
        wsAttendanceRecordVO.setEndTime(DateUtil.format(DateUtil.endOfDay(now), DateUtils.YYYY_MM_DD_HH_MM_SS));


        int curHour = DateUtil.hour(now,true);
        //建筑工人统计：0
        wsAttendanceRecordVO.setPersonType(LaborConstants.BUILDING_WORKER);
        List<WsAttendanceRecordVO> records = wsAttendanceRecordMapper.selectPersonHourPointAttendance(wsAttendanceRecordVO);
        for(int i =0;i<result.size();i++){
            int finalI = i;
            int inPersons = 0;
            if(i<=curHour) {
                inPersons=getInPersons(records, finalI);
            }
            WsSiteAttendanceStatisticsDTO statistics = result.get(i);
            statistics.setConstructionWorkerNumber(inPersons);
            result.set(i,statistics);
        }

        //管理人员: 1
        wsAttendanceRecordVO.setPersonType(LaborConstants.MANAGER);
        records = wsAttendanceRecordMapper.selectPersonHourPointAttendance(wsAttendanceRecordVO);
        for(int i =0;i<result.size();i++){
            int finalI = i;
            int inPersons = 0;
            if(i<=curHour) {
                inPersons= getInPersons(records, finalI);
            }
            WsSiteAttendanceStatisticsDTO statistics = result.get(i);
            statistics.setManagementNumber(inPersons);
            result.set(i,statistics);
        }

        return result;
    }

    private List<WsSiteAttendanceStatisticsDTO> get24HourWsSiteAttendanceStatisticsDTOS() {
        List<WsSiteAttendanceStatisticsDTO> result = Arrays.asList(new WsSiteAttendanceStatisticsDTO[24]);
        for(int i =0;i<result.size();i++){
            WsSiteAttendanceStatisticsDTO wsSiteAttendanceStatisticsDTO = new WsSiteAttendanceStatisticsDTO();
            String hour = i<10?"0"+i+":00": i +":00";
            wsSiteAttendanceStatisticsDTO.setTimeQuantum(hour);
            result.set(i,wsSiteAttendanceStatisticsDTO);
        }
        return result;
    }

    private int getInPersons(List<WsAttendanceRecordVO> records, int finalI) {
        //整点前打卡记录列表
        List<WsAttendanceRecordVO> beforeRecords = records.stream().filter(x -> finalI >= Integer.valueOf(x.getHourOfDay())).collect(Collectors.toList());
        //按照人员将考勤记录分组
        Map<Integer, List<WsAttendanceRecordVO>> personMap = beforeRecords.stream().collect(Collectors.groupingBy(WsAttendanceRecordVO::getTeamPersonId));
        int inPersons=0;
        for (Map.Entry<Integer, List<WsAttendanceRecordVO>> entry : personMap.entrySet()) {
            List<WsAttendanceRecordVO> value = entry.getValue();
            //获取人员进场记录数
            long inCount = value.stream().filter(r -> "1".equals(r.getDirection())).count();
            //获取人员出场记录数
            long outCount = value.stream().filter(r -> "2".equals(r.getDirection())).count();
            inPersons +=inCount-outCount>0?1:0;
        }
        return inPersons;
    }
}
