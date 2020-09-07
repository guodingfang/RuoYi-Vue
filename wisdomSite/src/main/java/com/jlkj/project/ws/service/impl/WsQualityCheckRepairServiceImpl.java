package com.jlkj.project.ws.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.ws.domain.WsQualityCheckManager;
import com.jlkj.project.ws.domain.WsQualityCheckPhoto;
import com.jlkj.project.ws.mapper.WsQualityCheckManagerMapper;
import com.jlkj.project.ws.mapper.WsQualityCheckPhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsQualityCheckRepairMapper;
import com.jlkj.project.ws.domain.WsQualityCheckRepair;
import com.jlkj.project.ws.service.IWsQualityCheckRepairService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 质量检查整改 Service业务层处理
 *
 * @author gaowei
 * @date 2020-06-09
 */
@Service
public class WsQualityCheckRepairServiceImpl implements IWsQualityCheckRepairService
{
    @Autowired
    private WsQualityCheckRepairMapper wsQualityCheckRepairMapper;

    @Autowired
    private WsQualityCheckManagerMapper wsQualityCheckManagerMapper;

    @Autowired
    private WsQualityCheckPhotoMapper wsQualityCheckPhotoMapper;

    /**
     * 查询质量检查整改 
     *
     * @param checkId 质量检查 ID
     * @return 质量检查整改 
     */
    @Override
    public WsQualityCheckRepair selectWsQualityCheckRepairById(Long checkId)
    {
        return wsQualityCheckRepairMapper.selectWsQualityCheckRepairById(checkId);
    }

    /**
     * 查询质量检查整改 列表
     *
     * @param wsQualityCheckRepair 质量检查整改 
     * @return 质量检查整改 
     */
    @Override
    public List<WsQualityCheckRepair> selectWsQualityCheckRepairList(WsQualityCheckRepair wsQualityCheckRepair)
    {
        return wsQualityCheckRepairMapper.selectWsQualityCheckRepairList(wsQualityCheckRepair);
    }

    /**
     * 新增质量检查整改 
     *
     * @param wsQualityCheckRepair 质量检查整改 
     * @return 结果
     */
    @Override
    @Transactional
    public int insertWsQualityCheckRepair(WsQualityCheckRepair wsQualityCheckRepair)
    {
        WsQualityCheckManager checkManager = new WsQualityCheckManager();
        if(StringUtils.isNotEmpty(String.valueOf(wsQualityCheckRepair.getCheckmanagerId()))){
            checkManager = wsQualityCheckManagerMapper.selectWsQualityCheckManagerById(wsQualityCheckRepair.getCheckmanagerId());
        }
        wsQualityCheckRepair.setRepairPersonId(checkManager.getRepairPersonId());
        wsQualityCheckRepair.setRepairDate(new Date());
        wsQualityCheckRepair.setCreatedTime(new Date());
        wsQualityCheckRepair.setUpdatedTime(new Date());
        long status = 2;
        WsQualityCheckManager wsQualityCheckManager = new WsQualityCheckManager();
        wsQualityCheckManager.setStatus(status);
        wsQualityCheckManager.setId(checkManager.getId());
        int manager = wsQualityCheckManagerMapper.updateWsQualityCheckManager(wsQualityCheckManager);
        wsQualityCheckRepairMapper.insertWsQualityCheckRepair(wsQualityCheckRepair);
        List<Object> repairImageAddres = wsQualityCheckRepair.getRepairImageAddres();
        if(repairImageAddres != null){
            for (int i = 0; i < repairImageAddres.size(); i++) {
                String json = JSON.toJSONString(repairImageAddres.get(i));
                JSONObject object=JSONObject.parseObject(json);
                WsQualityCheckPhoto qualityCheckPhoto = new WsQualityCheckPhoto();
                qualityCheckPhoto.setProjectId(checkManager.getProjectId());
                qualityCheckPhoto.setCheckRepairId(wsQualityCheckRepair.getId());
                qualityCheckPhoto.setQualityType(2);
                qualityCheckPhoto.setPhotoAddres(object.getString("imgurl"));
                qualityCheckPhoto.setCreatedTime(new Date());
                qualityCheckPhoto.setUpdatedTime(new Date());
                wsQualityCheckPhotoMapper.insertWsQualityCheckPhoto(qualityCheckPhoto);
            }
        }
        return manager;
    }

    /**
     * 修改质量检查整改 
     *
     * @param wsQualityCheckRepair 质量检查整改 
     * @return 结果
     */
    @Override
    public int updateWsQualityCheckRepair(WsQualityCheckRepair wsQualityCheckRepair)
    {
        return wsQualityCheckRepairMapper.updateWsQualityCheckRepair(wsQualityCheckRepair);
    }

    /**
     * 批量删除质量检查整改 
     *
     * @param ids 需要删除的质量检查整改 ID
     * @return 结果
     */
    @Override
    public int deleteWsQualityCheckRepairByIds(Long[] ids)
    {
        return wsQualityCheckRepairMapper.deleteWsQualityCheckRepairByIds(ids);
    }

    /**
     * 删除质量检查整改 信息
     *
     * @param id 质量检查整改 ID
     * @return 结果
     */
    @Override
    public int deleteWsQualityCheckRepairById(Long id)
    {
        return wsQualityCheckRepairMapper.deleteWsQualityCheckRepairById(id);
    }

    @Override
    public List<WsQualityCheckRepair> selectWsQualityCheckRepairByIdList(int checkId) {
        List<WsQualityCheckRepair> checkRepairList = wsQualityCheckRepairMapper.selectWsQualityCheckRepairByIdList(checkId);
        WsQualityCheckManager checkManager = wsQualityCheckManagerMapper.selectWsQualityCheckManagerById(checkId);
        for (int i = 0; i < checkRepairList.size(); i++) {
            WsQualityCheckPhoto qualityCheckPhoto = new WsQualityCheckPhoto();
            qualityCheckPhoto.setProjectId(checkManager.getProjectId());
            qualityCheckPhoto.setCheckRepairId(checkRepairList.get(i).getId());
            qualityCheckPhoto.setQualityType(2);
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
            checkRepairList.get(i).setRepairImageAddres(addresRepair);

            WsQualityCheckPhoto qualityCheckPhoto2 = new WsQualityCheckPhoto();
            qualityCheckPhoto2.setProjectId(checkManager.getProjectId());
            qualityCheckPhoto2.setCheckVerifyId(checkRepairList.get(i).getVerifyId());
            qualityCheckPhoto2.setQualityType(3);
            List<WsQualityCheckPhoto> verifyPhoto = wsQualityCheckPhotoMapper.selectWsQualityCheckPhotoByCheckManagerId(qualityCheckPhoto2);
            List<Object> addresVerify= new ArrayList();
            Map param2 = new HashMap();
            if(verifyPhoto.size() > 0){
                for (int j = 0; j < verifyPhoto.size(); j++) {
                    param2.put("imgurl",verifyPhoto.get(j).getPhotoAddres());
                    addresVerify.add(param2);
                    param2=new HashMap();
                }
            }
            checkRepairList.get(i).setVerifyImageAddres(addresVerify);
        }
        return checkRepairList;
    }
}
