package com.jlkj.project.ws.service.impl;

import cn.hutool.core.util.IdcardUtil;
import com.jlkj.common.constant.Constants;
import com.jlkj.common.constant.HttpStatus;
import com.jlkj.common.constant.LaborConstants;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.ServletUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.spring.SpringUtils;
import com.jlkj.framework.redis.KaoQinInterUtil;
import com.jlkj.framework.redis.RedisCache;
import com.jlkj.framework.redis.ResultData;
import com.jlkj.framework.security.LoginUser;
import com.jlkj.framework.security.service.TokenService;
import com.jlkj.project.system.domain.SysDictData;
import com.jlkj.project.system.domain.SysUser;
import com.jlkj.project.system.mapper.SysDictDataMapper;
import com.jlkj.project.ws.domain.*;
import com.jlkj.project.ws.domain.vo.WsTeamPersonVO;
import com.jlkj.project.ws.dto.WsTeamPersonDTO;
import com.jlkj.project.ws.mapper.*;
import com.jlkj.project.ws.service.IWsProjectService;
import com.jlkj.project.ws.service.IWsTeamPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 班组人员 Service业务层处理
 *
 * @author jlkj
 * @date 2020-06-12
 */
@Service
public class WsTeamPersonServiceImpl implements IWsTeamPersonService
{
    private static final Logger log = LoggerFactory.getLogger(WsTeamPersonServiceImpl.class);

    @Autowired
    private WsTeamPersonMapper wsTeamPersonMapper;

    @Autowired
    private WsTeamPersonLibraryMapper wsTeamPersonLibrayMapper;

    @Autowired
    private WsParticipatingTeamMapper wsParticipatingTeamMapper;

    @Autowired
    private WsBlacklistMapper wsBlacklistMapper;

    @Autowired
    private WsQualificationCertificateMapper wsQualificationCertificateMapper;

    @Autowired
    private WsProjectParticipatingUnitMapper wsProjectParticipatingUnitMapper;

    @Autowired
    private WsQualificationCertificateLibraryMapper wsQualificationCertificateLibraryMapper;

    @Autowired
    private IWsProjectService wsProjectService;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    /**
     * 查询班组人员
     *
     * @param id 班组人员 ID
     * @return 班组人员
     */
    @Override
    public WsTeamPerson selectWsTeamPersonById(Integer id)
    {
        WsTeamPerson wsTeamPerson = wsTeamPersonMapper.selectWsTeamPersonById(id);
        if (StringUtils.isNotNull(wsTeamPerson)&&StringUtils.isNotEmpty(wsTeamPerson.getHealthRelated())) {
            String[] healthRelateds = wsTeamPerson.getHealthRelated().split(",");
            wsTeamPerson.setHealthRelateds(healthRelateds);
        }
        return wsTeamPerson;
    }

    @Override
    public WsTeamPerson selectWsTeamPersonByGuid(String guid) {
        return wsTeamPersonMapper.selectWsTeamPersonByGuid(guid);
    }

    /**
     * 查询班组人员 列表
     *
     * @param wsTeamPerson 班组人员
     * @return 班组人员
     */
    @Override
    public List<WsTeamPerson> selectWsTeamPersonList(WsTeamPerson wsTeamPerson)
    {
        //若项目信息为空，则添加数据权限过滤
        List<WsProject> projectScope=null;
        if(StringUtils.isNull(wsTeamPerson)||StringUtils.isNull(wsTeamPerson.getProjectId())){
            projectScope = SecurityUtils.getProjectList();
        }
        if(StringUtils.isNull(wsTeamPerson)){
            wsTeamPerson = new WsTeamPerson();
            wsTeamPerson.setDeleted(LaborConstants.NO);
        }
        if(StringUtils.isEmpty(wsTeamPerson.getDeleted())) wsTeamPerson.setDeleted(LaborConstants.NO);

        return wsTeamPersonMapper.selectWsTeamPersonList(wsTeamPerson,projectScope);
    }

    /**
     * 新增班组人员
     *
     * @param wsTeamPerson 班组人员
     * @return 结果
     */
    @Transactional
    @Override
    public int insertWsTeamPerson(WsTeamPerson wsTeamPerson)
    {
        if(existedWsTeamPersonInProject(wsTeamPerson)){
            throw new CustomException("人员已存在");
        }
        wsTeamPerson.setBlacklist(LaborConstants.WHITE_LIST);
        wsTeamPerson.setPersonStatus(LaborConstants.IN);
        wsTeamPerson.setDeleted(LaborConstants.NO);
        wsTeamPerson.setInDate(new Date());
        wsTeamPerson.setAge(IdcardUtil.getAgeByIdCard(wsTeamPerson.getIdNumber()));
        wsTeamPerson.setProvince(IdcardUtil.getProvinceByIdCard(wsTeamPerson.getIdNumber()));
        if (StringUtils.isNotNull(wsTeamPerson.getHealthRelateds())&&wsTeamPerson.getHealthRelateds().length>0) {
            String join = StringUtils.join(wsTeamPerson.getHealthRelateds(), ",");
            wsTeamPerson.setHealthRelated(join);
        }
        return insertPerson(wsTeamPerson);
    }

    private int insertPerson(WsTeamPerson wsTeamPerson) {
        ResultData resultData= KaoQinInterUtil.adduser(wsTeamPerson);
        if (LaborConstants.FAIL.equals(resultData.getCode())) {
            throw new CustomException("人员同步云平台失败，请联系管理员!");
        }
        String guid = resultData.getData().getJSONObject("data").getString("guid");
        wsTeamPerson.setGuid(guid);
        int row = wsTeamPersonMapper.insertWsTeamPerson(wsTeamPerson);

        wsTeamPerson.setEnterpriseId(getGroupEnterpriseId());
        if(!existedWsTeamPersonInLibary(wsTeamPerson.getIdNumber())){
           row = wsTeamPersonLibrayMapper.insertWsTeamPersonLibraryByWsTeamPerson(wsTeamPerson);
        }else {
            row = wsTeamPersonLibrayMapper.updateWsTeamPersonLibraryByWsTeamPerson(wsTeamPerson);
        }

        if(StringUtils.isNotNull(wsTeamPerson.getCertificates())&&wsTeamPerson.getCertificates().size()>0){
            List<WsQualificationCertificate> certificates = wsTeamPerson.getCertificates();
            for (WsQualificationCertificate certificate : certificates) {
                row = insertWsQualificationCertificateByperson(wsTeamPerson, certificate);
            }
        }
        return row;
    }

    private int insertWsQualificationCertificateByperson(WsTeamPerson wsTeamPerson, WsQualificationCertificate certificate) {
        int row;
        certificate.setTeamPersonId(wsTeamPerson.getId());
        row= wsQualificationCertificateMapper.insertWsQualificationCertificate(certificate);
        String idNumber = wsTeamPerson.getIdNumber();
        //查询证书库是否已存在，不存在插入，存在更新
        row = saveOrUpdateWsQualificationCertificateLibraryByCertificate(certificate, idNumber);
        return row;
    }

    private int saveOrUpdateWsQualificationCertificateLibraryByCertificate(WsQualificationCertificate certificate, String idNumber) {
        int row;
        int  count = wsQualificationCertificateLibraryMapper.countWsQualificationCertificateLibraryByCertificateNumber(certificate.getCertificateNumber());
        WsQualificationCertificateLibrary library = certificate.convert2Library();
        library.setIdNumber(idNumber);
        if(count==0){
            row= wsQualificationCertificateLibraryMapper.insertWsQualificationCertificateLibrary(library);
        }else{
            row =wsQualificationCertificateLibraryMapper.updateWsQualificationCertificateLibrary(library);
        }
        return row;
    }

    private boolean existedWsTeamPersonInLibary(String idNumber) {
        int count =wsTeamPersonLibrayMapper.countWsTeamPersonByIdNumber(idNumber);
        return count>0?true:false;
    }

    private boolean existedWsTeamPersonInProject(WsTeamPerson wsTeamPerson) {
        int count = wsTeamPersonMapper.countWsTeamPersonInProject(wsTeamPerson);
        return count>0?true:false;
    }

    /**
     * 修改班组人员
     *
     * @param wsTeamPerson 班组人员
     * @return 结果
     */
    @Transactional
    @Override
    public int updateWsTeamPerson(WsTeamPerson wsTeamPerson)
    {
        //检查人员是否存在
        WsTeamPerson person = wsTeamPersonMapper.selectWsTeamPersonById(wsTeamPerson.getId());
        if(StringUtils.isNull(person)){
            throw new CustomException("修改失败，班组人员不存在", HttpStatus.NO_RECORD);
        }
        //若手机号和身份证号被修改，检查同项目下是否已存在
        List<WsTeamPerson> wsTeamPersonInProject = wsTeamPersonMapper.findWsTeamPersonInProject(wsTeamPerson);
        if(!StringUtils.isEmpty(wsTeamPersonInProject)){
            throw new CustomException("修改失败 ，手机号或身份证号已存在",HttpStatus.REPEAT_RECORD);
        }
        int rows =0;
        int compare =0;
        rows +=wsTeamPersonMapper.updateWsTeamPerson(wsTeamPerson);
        compare++;


        //人员证书操作
        List<WsQualificationCertificate> queryCertificates = wsQualificationCertificateMapper.selectCertificateListByPersonId(wsTeamPerson.getId());
        List<WsQualificationCertificate> certificates = wsTeamPerson.getCertificates();
        for (WsQualificationCertificate certificate : certificates) {
            //如果证书id为空，则新增
            if(StringUtils.isNull(certificate.getId())){
                rows += insertWsQualificationCertificateByperson(wsTeamPerson, certificate);
                compare++;
            }else{//否则证书更新
                rows +=wsQualificationCertificateMapper.updateWsQualificationCertificate(certificate);
                compare++;
                rows += saveOrUpdateWsQualificationCertificateLibraryByCertificate(certificate, wsTeamPerson.getIdNumber());
                compare++;
                queryCertificates.removeIf(x->x.getId().equals(certificate.getId()));
            }
        }
        if(StringUtils.isNotEmpty(queryCertificates)){//剩余未移除证书为已删除证书
            for (WsQualificationCertificate queryCertificate : queryCertificates) {
                rows +=wsQualificationCertificateMapper.deleteWsQualificationCertificateById(queryCertificate.getId());
                compare++;
            }
        }


        //建筑工人库操作
        WsTeamPersonLibrary wsTeamPersonLibrary = wsTeamPersonLibrayMapper.selectWsTeamPersonLibraryByIdNumber(wsTeamPerson.getIdNumber());
        wsTeamPerson.setEnterpriseId(getGroupEnterpriseId());
        if(StringUtils.isNotNull(wsTeamPersonLibrary)){
            rows +=wsTeamPersonLibrayMapper.updateWsTeamPersonLibraryByWsTeamPerson(wsTeamPerson);
            compare++;
        }else{
            rows +=wsTeamPersonLibrayMapper.insertWsTeamPersonLibraryByWsTeamPerson(wsTeamPerson);
            compare++;
        }


        if(rows!=compare){
            throw new CustomException("更新失败，请联系管理员");
        }
        return rows;
    }


    /**
     * 批量删除班组人员
     *
     * @param ids 需要删除的班组人员 ID
     * @return 结果
     */
    @Override
    public int deleteWsTeamPersonByIds(Integer[] ids)
    {
        return wsTeamPersonMapper.deleteWsTeamPersonByIds(ids);
    }

    /**
     * 删除班组人员 信息
     *
     * @param id 班组人员 ID
     * @return 结果
     */
    @Override
    public int deleteWsTeamPersonById(Integer id)
    {
        return wsTeamPersonMapper.deleteWsTeamPersonById(id);
    }

    @Transactional
    @Override
    public int batchUpdateWsTeamPerson(WsTeamPersonDTO wsTeamPersonDTO) {
        if (StringUtils.isNull(wsTeamPersonDTO)) {
            throw new  CustomException("请选择人员信息");
        }
        if (StringUtils.isEmpty(wsTeamPersonDTO.getPersonIds())) {
            throw new  CustomException("请选择人员信息");
        }
        WsTeamPerson wsTeamPerson = new WsTeamPerson();
        wsTeamPerson.getParams().put("ids",wsTeamPersonDTO.getPersonIds());
        wsTeamPerson.setProjectId(wsTeamPersonDTO.getProjectId());
        wsTeamPerson.setUnitId(wsTeamPersonDTO.getUnitId());
        wsTeamPerson.setTeamId(wsTeamPersonDTO.getTeamId());
        wsTeamPerson.setPersonStatus(wsTeamPersonDTO.getPersonStatus());
        wsTeamPerson.setWorkType(wsTeamPersonDTO.getWorkType());
        wsTeamPerson.setBlacklist(wsTeamPersonDTO.getBlacklist());
        wsTeamPerson.setPersonType(wsTeamPersonDTO.getPersonType());
        wsTeamPerson.setEnterpriseId(getGroupEnterpriseId());

        int rows =wsTeamPersonMapper.batchUpateWsTeamPerson(wsTeamPerson);
        ArrayList<WsBlacklist> wsBlacklists = null;
        WsBlacklist black = wsTeamPersonDTO.getWsBlacklist();
        if(StringUtils.isNotEmpty(wsTeamPersonDTO.getBlacklist())){
            wsBlacklists =  new ArrayList<>();
            for (int i = 0; i < wsTeamPersonDTO.getPersonIds().length; i++) {
                WsBlacklist wsBlacklist = new WsBlacklist();
                wsBlacklist.setIllegalDate(wsTeamPersonDTO.getIllegalDate());
                wsBlacklist.setRecorder(wsTeamPersonDTO.getRecorder());
                wsBlacklist.setIllegalReason(wsTeamPersonDTO.getIllegalReason());
                wsBlacklist.setImageUrl(wsTeamPersonDTO.getImageUrl());
                wsBlacklist.setDeleted(LaborConstants.NO);
                wsBlacklist.setPersonId(wsTeamPersonDTO.getPersonIds()[i]);
                wsBlacklists.add(wsBlacklist);
            }
            rows =wsBlacklistMapper.batchInsertWsBlacklist(wsBlacklists);
        }
        return rows;
    }

    @Override
    public int batchInsertWsTeamPerson(WsTeamPerson teamPerson) {
        int rows = 0;

        StringBuilder dupName = new StringBuilder();
        Integer[] ids = teamPerson.getPersonLibraryIds();
        teamPerson.setPersonStatus(LaborConstants.IN);
        teamPerson.setBlacklist(LaborConstants.WHITE_LIST);
        teamPerson.setDeleted(LaborConstants.NO);
        teamPerson.setInDate(new Date());
        teamPerson.setAge(IdcardUtil.getAgeByIdCard(teamPerson.getIdNumber()));
        Long groupEnterpriseId = getGroupEnterpriseId();
        for (Integer i = 0; i < ids.length; i++) {
            WsTeamPersonLibrary wsTeamPersonLibrary = wsTeamPersonLibrayMapper.selectWsTeamPersonLibraryById(ids[i]);
            convert2WsTeamPerson(wsTeamPersonLibrary,teamPerson);
            int num = wsTeamPersonMapper.countWsTeamPersonInProjectAndId(teamPerson.getProjectId(),wsTeamPersonLibrary.getIdNumber(),wsTeamPersonLibrary.getMobilePhone());
            if(num>0){
                dupName.append(wsTeamPersonLibrary.getName()).append(",");
            }else{
                ResultData resultData= KaoQinInterUtil.adduser(teamPerson);
                if (LaborConstants.FAIL.equals(resultData.getCode())) {
                    dupName.append(wsTeamPersonLibrary.getName()).append(",");
                    continue;
                }
                String guid = resultData.getData().getJSONObject("data").getString("guid");
                teamPerson.setGuid(guid);
                teamPerson.setEnterpriseId(groupEnterpriseId);
                rows =wsTeamPersonMapper.insertWsTeamPerson(teamPerson);
            }
        }
        if(StringUtils.isNotEmpty(dupName.toString())){
            throw new CustomException("以下人员在该项目下已存在或者注册失败 ： "+dupName.toString().substring(0,dupName.lastIndexOf(",")),HttpStatus.REPEAT_RECORD);
        }
        return rows;
    }

    private void convert2WsTeamPerson(WsTeamPersonLibrary wsTeamPersonLibrary, WsTeamPerson teamPerson) {
        teamPerson.setProvince(wsTeamPersonLibrary.getProvince());
        teamPerson.setAge(wsTeamPersonLibrary.getAge());
        teamPerson.setIdNumber(wsTeamPersonLibrary.getIdNumber());
        teamPerson.setAddress(wsTeamPersonLibrary.getAddress());
        teamPerson.setAuthority(wsTeamPersonLibrary.getAuthority());
        teamPerson.setBirthDate(wsTeamPersonLibrary.getBirthDate());
        teamPerson.setBloodPressure(wsTeamPersonLibrary.getBloodPressure());
        teamPerson.setFaceUrl(wsTeamPersonLibrary.getFaceUrl());
        teamPerson.setCultureStandardCode(wsTeamPersonLibrary.getCultureStandardCode());
        teamPerson.setName(wsTeamPersonLibrary.getName());
        teamPerson.setEffectiveDate(wsTeamPersonLibrary.getEffectiveDate());
        teamPerson.setInvalidDate(wsTeamPersonLibrary.getInvalidDate());
        teamPerson.setMobilePhone(wsTeamPersonLibrary.getMobilePhone());
        teamPerson.setWeight(wsTeamPersonLibrary.getWeight());
        teamPerson.setHeight(wsTeamPersonLibrary.getHeight());
        teamPerson.setHealthRelated(wsTeamPersonLibrary.getHealthRelated());
        teamPerson.setResidenceNature(wsTeamPersonLibrary.getResidenceNature());
        teamPerson.setPoliticsStatusCode(wsTeamPersonLibrary.getPoliticsStatusCode());
        teamPerson.setNationCode(wsTeamPersonLibrary.getNationCode());
        teamPerson.setSex(wsTeamPersonLibrary.getSex());

    }



    @Transactional
    @Override
    public String importPerson(List<WsTeamPerson> personList, Integer unitId) {
        if (StringUtils.isNull(personList) || personList.size() == 0)
        {
            throw new CustomException("导入用户数据不能为空！");
        }
        WsProjectParticipatingUnit wsProjectParticipatingUnit = wsProjectParticipatingUnitMapper.selectWsProjectParticipatingUnitById(unitId);
        if (StringUtils.isNull(wsProjectParticipatingUnit))
        {
            throw new CustomException("人员所属参建单位不存在");
        }
        List<SysDictData> workTypes = dictDataMapper.selectDictDataByType("ws_work_type");
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (WsTeamPerson person : personList)
        {
            try
            {
                SysDictData sysDictData = workTypes.stream().filter(w -> w.getDictLabel().equals(person.getWorkTypeName())).findAny().orElse(null);
                if(StringUtils.isNotNull(sysDictData)) person.setWorkType(sysDictData.getDictValue());

                person.setUnitId(unitId);
                person.setProjectId(wsProjectParticipatingUnit.getProjectId());
                //验证班组是否存在，不存在则新增
                WsParticipatingTeam team = wsParticipatingTeamMapper.findWsParticipatingTeamByProjectTeam(person.getTeamName(),unitId);
                if(StringUtils.isNull(team)){
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、人员 " + person.getName() + ",归属班组不存在");
                    continue;
                }else{
                    person.setTeamId(team.getId());
                }
                // 验证是否存在这个用户
                int pCount = wsTeamPersonMapper.countWsTeamPersonInProject(person);
                if (pCount==0)
                {
                    person.setBlacklist(LaborConstants.WHITE_LIST);
                    person.setPersonStatus(LaborConstants.IN);
                    person.setDeleted(LaborConstants.NO);
                    this.insertPerson(person);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、人员 " + person.getName() + " 导入成功");
                }else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、人员 " + person.getName() + " 已存在");
                }
            }
            catch (Exception e){
                failureNum++;
                String msg = "<br/>" + failureNum + "、人员 " + person.getName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();

    }

    @Override
    public List<WsTeamPersonVO> selectWsTeamPersonAuthList(WsTeamPerson wsTeamPerson) {
        List<WsProject> projectScope = null;
        if(StringUtils.isNull(wsTeamPerson)||StringUtils.isNull(wsTeamPerson.getProjectId())){
            projectScope = wsProjectService.selectWsProjectList(null);
        }
        return wsTeamPersonMapper.selectWsTeamPersonListWithAuth(wsTeamPerson,projectScope);
    }

    private Integer insertWsParticipatingTeam(WsTeamPerson person ){
        WsParticipatingTeam wsParticipatingTeam = new WsParticipatingTeam();
        wsParticipatingTeam.setProjectId(person.getProjectId());
        wsParticipatingTeam.setTeamName(person.getTeamName());
        wsParticipatingTeam.setUnitId(person.getUnitId());
        wsParticipatingTeamMapper.insertWsParticipatingTeam(wsParticipatingTeam);
        return wsParticipatingTeam.getId() ;
    }


    /**
     * 查询花名册
     * @param wsTeamPerson
     * @return
     */
    @Override
    public List<WsTeamPerson> selectWsTeamPersonListRoster(WsTeamPerson wsTeamPerson) {
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
        return wsTeamPersonMapper.selectWsTeamPersonListRoster(wsTeamPerson,projectIds);
    }

    private Long getGroupEnterpriseId() {

        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        String token = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest()).getToken();
        LoginUser loginUser = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY + token);
        if(StringUtils.isNull(loginUser)) throw new CustomException("无数据权限！");
        SysUser user = loginUser.getUser();
        if(StringUtils.isNull(loginUser)) throw new CustomException("无数据权限！");
        return user.getEnterpriseId();
    }
}
