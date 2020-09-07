package com.jlkj.project.ws.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.project.ws.domain.WsQualityAcceptanceApply;
import com.jlkj.project.ws.domain.WsQualityCheckPhoto;
import com.jlkj.project.ws.mapper.WsQualityAcceptanceApplyMapper;
import com.jlkj.project.ws.mapper.WsQualityCheckPhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsQualityAcceptanceRecordMapper;
import com.jlkj.project.ws.domain.WsQualityAcceptanceRecord;
import com.jlkj.project.ws.service.IWsQualityAcceptanceRecordService;

/**
 * 质量验收记录 Service业务层处理
 *
 * @author gaowei
 * @date 2020-06-09
 */
@Service
public class WsQualityAcceptanceRecordServiceImpl implements IWsQualityAcceptanceRecordService
{
    @Autowired
    private WsQualityAcceptanceRecordMapper wsQualityAcceptanceRecordMapper;

    @Autowired
    private WsQualityAcceptanceApplyMapper wsQualityAcceptanceApplyMapper;

    @Autowired
    private WsQualityCheckPhotoMapper wsQualityCheckPhotoMapper;

    /**
     * 查询质量验收记录 
     *
     * @param id 质量验收记录 ID
     * @return 质量验收记录 
     */
    @Override
    public WsQualityAcceptanceRecord selectWsQualityAcceptanceRecordById(Integer id)
    {
        return wsQualityAcceptanceRecordMapper.selectWsQualityAcceptanceRecordById(id);
    }

    /**
     * 查询质量验收记录 列表
     *
     * @param wsQualityAcceptanceRecord 质量验收记录 
     * @return 质量验收记录 
     */
    @Override
    public List<WsQualityAcceptanceRecord> selectWsQualityAcceptanceRecordList(WsQualityAcceptanceRecord wsQualityAcceptanceRecord)
    {
        return wsQualityAcceptanceRecordMapper.selectWsQualityAcceptanceRecordList(wsQualityAcceptanceRecord);
    }

    /**
     * 新增质量验收记录 
     *
     * @param wsQualityAcceptanceRecord 质量验收记录 
     * @return 结果
     */
    @Override
    public int insertWsQualityAcceptanceRecord(WsQualityAcceptanceRecord wsQualityAcceptanceRecord)
    {
        WsQualityAcceptanceApply acceptanceApply = wsQualityAcceptanceApplyMapper.selectWsQualityAcceptanceApplyById(wsQualityAcceptanceRecord.getApplyId());
        List<WsQualityAcceptanceApply> frequencyList = wsQualityAcceptanceRecordMapper.countWsQualityAcceptanceRecordByApplyId(wsQualityAcceptanceRecord.getApplyId());
        wsQualityAcceptanceRecord.setPlanverificationDate(acceptanceApply.getPlanverificationDate());
        wsQualityAcceptanceRecord.setAcceptancePersonId(acceptanceApply.getAcceptancePersonId());
        wsQualityAcceptanceRecord.setVerificationPersonId(acceptanceApply.getAcceptancePersonId());
        wsQualityAcceptanceRecord.setVerificationDate(new Date());
        wsQualityAcceptanceRecord.setCreatedTime(new Date());
        wsQualityAcceptanceRecord.setUpdatedTime(new Date());
        Integer acceptanceFrequency = frequencyList.size() + 1;
        wsQualityAcceptanceRecord.setAcceptanceFrequency(acceptanceFrequency);
        WsQualityAcceptanceApply apply = new WsQualityAcceptanceApply();
        apply.setId(acceptanceApply.getId());
        apply.setAcceptanceState(wsQualityAcceptanceRecord.getAcceptanceState());
        apply.setApplyNum(acceptanceApply.getApplyNum());
        int row = wsQualityAcceptanceApplyMapper.updateWsQualityAcceptanceApply(apply);
        List<Object> acceptanceImageAddres = wsQualityAcceptanceRecord.getAcceptanceImageAddres();
        if(acceptanceImageAddres != null){
            for (int i = 0; i < acceptanceImageAddres.size(); i++) {
                String json = JSON.toJSONString(acceptanceImageAddres.get(i));
                JSONObject object=JSONObject.parseObject(json);
                WsQualityCheckPhoto qualityCheckPhoto = new WsQualityCheckPhoto();
                qualityCheckPhoto.setProjectId(acceptanceApply.getProjectId());
                qualityCheckPhoto.setAcceptanceId(acceptanceApply.getId());
                qualityCheckPhoto.setQualityType(4);
                qualityCheckPhoto.setPhotoAddres(object.getString("imgurl"));
                qualityCheckPhoto.setCreatedTime(new Date());
                qualityCheckPhoto.setUpdatedTime(new Date());
                wsQualityCheckPhotoMapper.insertWsQualityCheckPhoto(qualityCheckPhoto);
            }
        }
        wsQualityAcceptanceRecordMapper.insertWsQualityAcceptanceRecord(wsQualityAcceptanceRecord);
        return row;
    }

    /**
     * 修改质量验收记录 
     *
     * @param wsQualityAcceptanceRecord 质量验收记录 
     * @return 结果
     */
    @Override
    public int updateWsQualityAcceptanceRecord(WsQualityAcceptanceRecord wsQualityAcceptanceRecord)
    {
        return wsQualityAcceptanceRecordMapper.updateWsQualityAcceptanceRecord(wsQualityAcceptanceRecord);
    }

    /**
     * 批量删除质量验收记录 
     *
     * @param ids 需要删除的质量验收记录 ID
     * @return 结果
     */
    @Override
    public int deleteWsQualityAcceptanceRecordByIds(Long[] ids)
    {
        return wsQualityAcceptanceRecordMapper.deleteWsQualityAcceptanceRecordByIds(ids);
    }

    /**
     * 删除质量验收记录 信息
     *
     * @param id 质量验收记录 ID
     * @return 结果
     */
    @Override
    public int deleteWsQualityAcceptanceRecordById(Integer id)
    {
        return wsQualityAcceptanceRecordMapper.deleteWsQualityAcceptanceRecordById(id);
    }

    /**
     * 验收记录list
     * @param applyId
     * @return
     */
    @Override
    public List<WsQualityAcceptanceRecord> listWsQualityAcceptanceRecordByApplyId(Integer applyId) {
        List<WsQualityAcceptanceRecord> listRecord = wsQualityAcceptanceRecordMapper.listWsQualityAcceptanceRecordByApplyId(applyId);
        WsQualityAcceptanceApply acceptanceApply = wsQualityAcceptanceApplyMapper.selectWsQualityAcceptanceApplyById(applyId);
        for (int i = 0; i < listRecord.size(); i++) {
            WsQualityCheckPhoto qualityCheckPhoto = new WsQualityCheckPhoto();
            qualityCheckPhoto.setProjectId(acceptanceApply.getProjectId());
            qualityCheckPhoto.setAcceptanceId(acceptanceApply.getId());
            qualityCheckPhoto.setQualityType(4);
            List<WsQualityCheckPhoto> repairPhoto = wsQualityCheckPhotoMapper.selectWsQualityCheckPhotoByCheckManagerId(qualityCheckPhoto);
            List<Object> addresRepair = new ArrayList();
            Map param = new HashMap();
            if(repairPhoto.size() > 0){
                for (int j = 0; j < repairPhoto.size(); j++) {
                    param.put("imgurl",repairPhoto.get(j).getPhotoAddres());
                    addresRepair.add(param);
                    param=new HashMap();
                }
            }
            listRecord.get(i).setAcceptanceImageAddres(addresRepair);
        }
        return listRecord;
    }
}
