package com.jlkj.project.ws.service.impl;

import cn.hutool.core.date.DateUtil;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.system.domain.SysUser;
import com.jlkj.project.ws.domain.WsFileInfo;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsTeamPersonBehaviorRecord;
import com.jlkj.project.ws.domain.vo.WsTeamPersonBehaviorRecordVO;
import com.jlkj.project.ws.mapper.WsProjectMapper;
import com.jlkj.project.ws.mapper.WsTeamPersonBehaviorRecordMapper;
import com.jlkj.project.ws.service.IWsProjectService;
import com.jlkj.project.ws.service.IWsTeamPersonBehaviorRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 班组人员行为记录Service业务层处理
 *
 * @author liujie
 * @date 2020-07-23
 */
@Service
public class WsTeamPersonBehaviorRecordServiceImpl implements IWsTeamPersonBehaviorRecordService
{
    @Autowired
    private WsTeamPersonBehaviorRecordMapper wsTeamPersonBehaviorRecordMapper;

    @Autowired
    private WsProjectMapper wsProjectMapper;

    @Autowired
    private IWsProjectService wsProjectService;

    /**
     * 查询班组人员行为记录
     *
     * @param id 班组人员行为记录ID
     * @return 班组人员行为记录
     */
    @Override
    public WsTeamPersonBehaviorRecord selectWsTeamPersonBehaviorRecordById(Integer id)
    {
        return wsTeamPersonBehaviorRecordMapper.selectWsTeamPersonBehaviorRecordById(id);
    }

    /**
     * 查询班组人员行为记录列表
     *
     * @param wsTeamPersonBehaviorRecord 班组人员行为记录
     * @return 班组人员行为记录
     */
    @Override
    public List<WsTeamPersonBehaviorRecordVO> selectWsTeamPersonBehaviorRecordList(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord)
    {
        parseBeginAndEndTime(wsTeamPersonBehaviorRecord);
        //项目信息为空时添加数据范围
        List<WsProject> projectScope = null;
        if(StringUtils.isNull(wsTeamPersonBehaviorRecord)||StringUtils.isNull(wsTeamPersonBehaviorRecord.getProjectId())){
            projectScope = SecurityUtils.getProjectList();
        }
        List<WsTeamPersonBehaviorRecordVO> list = wsTeamPersonBehaviorRecordMapper.selectWsTeamPersonBehaviorRecordList(wsTeamPersonBehaviorRecord, projectScope);
        list.forEach(x->{
            if(StringUtils.isNotEmpty(x.getImageUrl())) {
                String[] split = StringUtils.split(x.getImageUrl(), ",");
                ArrayList<WsFileInfo> files = new ArrayList<>();
                for (String url : split) {
                    WsFileInfo wsFileInfo = new WsFileInfo();
                    wsFileInfo.setUrl(url);
                    files.add(wsFileInfo);
                }
                x.setImageUrls(files);
            }
        });
        return list;
    }

    @Override
    public List<WsTeamPersonBehaviorRecordVO> selectWsTeamPersonBehaviorRecordListByPerson(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord) {
        parseBeginAndEndTime(wsTeamPersonBehaviorRecord);
        //项目信息为空时添加数据范围
        List<WsProject> projectScope = null;
        if(StringUtils.isNull(wsTeamPersonBehaviorRecord)||StringUtils.isNull(wsTeamPersonBehaviorRecord.getProjectId())){
            projectScope = wsProjectService.selectWsProjectList(null);
        }
        return wsTeamPersonBehaviorRecordMapper.selectWsTeamPersonBehaviorRecordListByPerson(wsTeamPersonBehaviorRecord,projectScope);
    }

    @Override
    public List<WsTeamPersonBehaviorRecordVO> selectWsTeamPersonBehaviorRecordListByTeam(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord) {
        parseBeginAndEndTime(wsTeamPersonBehaviorRecord);
        return wsTeamPersonBehaviorRecordMapper.selectWsTeamPersonBehaviorRecordListByTeam(wsTeamPersonBehaviorRecord);
    }

    private void parseBeginAndEndTime(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord) {
        if(StringUtils.isNotNull(wsTeamPersonBehaviorRecord)){
            if(StringUtils.isNotNull(wsTeamPersonBehaviorRecord.getMonth())){
                String beginTime = DateUtil.format(DateUtil.beginOfMonth(wsTeamPersonBehaviorRecord.getMonth()), "yyyy-MM-dd HH:mm:ss");
                String endTime = DateUtil.format(DateUtil.endOfMonth(wsTeamPersonBehaviorRecord.getMonth()), "yyyy-MM-dd HH:mm:ss");
                wsTeamPersonBehaviorRecord.setBeginTime(beginTime);
                wsTeamPersonBehaviorRecord.setEndTime(endTime);
            }
        }
    }

    /**
     * 新增班组人员行为记录
     *
     * @param wsTeamPersonBehaviorRecord 班组人员行为记录
     * @return 结果
     */
    @Override
    public int insertWsTeamPersonBehaviorRecord(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord)
    {

        if(StringUtils.isNull(wsTeamPersonBehaviorRecord)) throw new CustomException("行为记录不能为空");
        //获取登陆人员姓名
        SysUser userInfo = SecurityUtils.getUserInfo();
        wsTeamPersonBehaviorRecord.setRecorder(userInfo.getUserName());
        //如果有图片则转换为字符串存储
        if(StringUtils.isNotEmpty(wsTeamPersonBehaviorRecord.getImageUrls())){
            List<String> collect = wsTeamPersonBehaviorRecord.getImageUrls().stream().map(WsFileInfo::getUrl).collect(Collectors.toList());
            wsTeamPersonBehaviorRecord.setImageUrl(StringUtils.join(collect, ","));
        }
        return wsTeamPersonBehaviorRecordMapper.insertWsTeamPersonBehaviorRecord(wsTeamPersonBehaviorRecord);
    }

    /**
     * 修改班组人员行为记录
     *
     * @param wsTeamPersonBehaviorRecord 班组人员行为记录
     * @return 结果
     */
    @Override
    public int updateWsTeamPersonBehaviorRecord(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord)
    {
        //如果有图片则转换为字符串存储
        if(StringUtils.isNotEmpty(wsTeamPersonBehaviorRecord.getImageUrls())) {
            List<String> collect = wsTeamPersonBehaviorRecord.getImageUrls().stream().map(WsFileInfo::getUrl).collect(Collectors.toList());
            wsTeamPersonBehaviorRecord.setImageUrl(StringUtils.join(collect, ","));
        }

        return wsTeamPersonBehaviorRecordMapper.updateWsTeamPersonBehaviorRecord(wsTeamPersonBehaviorRecord);
    }

    /**
     * 批量删除班组人员行为记录
     *
     * @param ids 需要删除的班组人员行为记录ID
     * @return 结果
     */
    @Override
    public int deleteWsTeamPersonBehaviorRecordByIds(Integer[] ids)
    {
        return wsTeamPersonBehaviorRecordMapper.deleteWsTeamPersonBehaviorRecordByIds(ids);
    }

    /**
     * 删除班组人员行为记录信息
     *
     * @param id 班组人员行为记录ID
     * @return 结果
     */
    @Override
    public int deleteWsTeamPersonBehaviorRecordById(Integer id)
    {
        return wsTeamPersonBehaviorRecordMapper.deleteWsTeamPersonBehaviorRecordById(id);
    }


    @Override
    public WsTeamPersonBehaviorRecordVO statisticstPersonTotalPoints(Integer personId) {
        if(StringUtils.isNull(personId)) return new WsTeamPersonBehaviorRecordVO();
        return wsTeamPersonBehaviorRecordMapper.statisticstPersonTotalPoints(personId);
    }

    @Override
    public WsTeamPersonBehaviorRecordVO statisticstPersonExchangeablePoints(Integer personId) {
        if(StringUtils.isNull(personId)) return new WsTeamPersonBehaviorRecordVO();
        return wsTeamPersonBehaviorRecordMapper.statisticstPersonExchangeablePoints(personId);
    }

    @Override
    public List<WsTeamPersonBehaviorRecordVO> selectPersonBehaviorEvaluate(WsTeamPersonBehaviorRecord wsTeamPersonBehaviorRecord) {
        //若项目信息为空，则添加数据权限过滤
        List<WsProject> projectScope=null;
        if(StringUtils.isNull(wsTeamPersonBehaviorRecord)||StringUtils.isNull(wsTeamPersonBehaviorRecord.getProjectId())){
            projectScope = SecurityUtils.getProjectList();
        }
        return wsTeamPersonBehaviorRecordMapper.selectPersonBehaviorEvaluate(wsTeamPersonBehaviorRecord,projectScope);
    }
}
