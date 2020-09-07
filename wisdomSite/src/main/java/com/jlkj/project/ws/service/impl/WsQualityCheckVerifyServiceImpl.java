package com.jlkj.project.ws.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.ws.domain.WsQualityCheckManager;
import com.jlkj.project.ws.domain.WsQualityCheckPhoto;
import com.jlkj.project.ws.domain.WsQualityCheckRepair;
import com.jlkj.project.ws.mapper.WsQualityCheckManagerMapper;
import com.jlkj.project.ws.mapper.WsQualityCheckPhotoMapper;
import com.jlkj.project.ws.mapper.WsQualityCheckRepairMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsQualityCheckVerifyMapper;
import com.jlkj.project.ws.domain.WsQualityCheckVerify;
import com.jlkj.project.ws.service.IWsQualityCheckVerifyService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 质量检查验证 Service业务层处理
 *
 * @author gaowei
 * @date 2020-06-09
 */
@Service
public class WsQualityCheckVerifyServiceImpl implements IWsQualityCheckVerifyService
{
    @Autowired
    private WsQualityCheckVerifyMapper wsQualityCheckVerifyMapper;

    @Autowired
    private WsQualityCheckManagerMapper wsQualityCheckManagerMapper;

    @Autowired
    private WsQualityCheckRepairMapper wsQualityCheckRepairMapper;

    @Autowired
    private WsQualityCheckPhotoMapper wsQualityCheckPhotoMapper;

    /**
     * 查询质量检查验证 
     *
     * @param id 质量检查验证 ID
     * @return 质量检查验证 
     */
    @Override
    public WsQualityCheckVerify selectWsQualityCheckVerifyById(Long id)
    {
        return wsQualityCheckVerifyMapper.selectWsQualityCheckVerifyById(id);
    }

    /**
     * 查询质量检查验证 列表
     *
     * @param wsQualityCheckVerify 质量检查验证 
     * @return 质量检查验证 
     */
    @Override
    public List<WsQualityCheckVerify> selectWsQualityCheckVerifyList(WsQualityCheckVerify wsQualityCheckVerify)
    {
        return wsQualityCheckVerifyMapper.selectWsQualityCheckVerifyList(wsQualityCheckVerify);
    }

    /**
     * 新增质量检查验证 
     *
     * @param wsQualityCheckVerify 质量检查验证 
     * @return 结果
     */
    @Override
    @Transactional
    public int insertWsQualityCheckVerify(WsQualityCheckVerify wsQualityCheckVerify)
    {
        WsQualityCheckManager checkManager = new WsQualityCheckManager();
        WsQualityCheckRepair checkRepair = new WsQualityCheckRepair();
        if(StringUtils.isNotEmpty(String.valueOf(wsQualityCheckVerify.getCheckmanagerId()))){
            checkManager = wsQualityCheckManagerMapper.selectWsQualityCheckManagerById(wsQualityCheckVerify.getCheckmanagerId());
            checkRepair = wsQualityCheckRepairMapper.selectWsQualityCheckRepairByCheckmanagerId(wsQualityCheckVerify.getCheckmanagerId());
        }
        wsQualityCheckVerify.setRepairId(checkRepair.getId());
        wsQualityCheckVerify.setVerifyPersonId(checkManager.getVerifyPersonId());
        wsQualityCheckVerify.setVerifyDate(new Date());
        wsQualityCheckVerify.setCreatedTime(new Date());
        wsQualityCheckVerify.setUpdatedTime(new Date());
        WsQualityCheckManager wsQualityCheckManager = new WsQualityCheckManager();
        int verifyResult = wsQualityCheckVerify.getVerifyResult().intValue();
        if(verifyResult == 1){//合格
            long status = 3;//已完成
            wsQualityCheckManager.setStatus(status);
        }else{//不合格
            long status = 1;//待整改
            wsQualityCheckManager.setStatus(status);
        }
        wsQualityCheckManager.setId(checkManager.getId());
        int manager = wsQualityCheckManagerMapper.updateWsQualityCheckManager(wsQualityCheckManager);
        wsQualityCheckVerifyMapper.insertWsQualityCheckVerify(wsQualityCheckVerify);
        List<Object> verifyImageAddres = wsQualityCheckVerify.getVerifyImageAddres();
        for (int i = 0; i < verifyImageAddres.size(); i++) {
            String json = JSON.toJSONString(verifyImageAddres.get(i));
            JSONObject object=JSONObject.parseObject(json);
            WsQualityCheckPhoto qualityCheckPhoto = new WsQualityCheckPhoto();
            qualityCheckPhoto.setProjectId(checkManager.getProjectId());
            qualityCheckPhoto.setCheckVerifyId(wsQualityCheckVerify.getId());
            qualityCheckPhoto.setQualityType(3);
            qualityCheckPhoto.setPhotoAddres(object.getString("imgurl"));
            qualityCheckPhoto.setCreatedTime(new Date());
            qualityCheckPhoto.setUpdatedTime(new Date());
            wsQualityCheckPhotoMapper.insertWsQualityCheckPhoto(qualityCheckPhoto);
        }
        return manager;
    }

    /**
     * 修改质量检查验证 
     *
     * @param wsQualityCheckVerify 质量检查验证 
     * @return 结果
     */
    @Override
    public int updateWsQualityCheckVerify(WsQualityCheckVerify wsQualityCheckVerify)
    {
        return wsQualityCheckVerifyMapper.updateWsQualityCheckVerify(wsQualityCheckVerify);
    }

    /**
     * 批量删除质量检查验证 
     *
     * @param ids 需要删除的质量检查验证 ID
     * @return 结果
     */
    @Override
    public int deleteWsQualityCheckVerifyByIds(Long[] ids)
    {
        return wsQualityCheckVerifyMapper.deleteWsQualityCheckVerifyByIds(ids);
    }

    /**
     * 删除质量检查验证 信息
     *
     * @param id 质量检查验证 ID
     * @return 结果
     */
    @Override
    public int deleteWsQualityCheckVerifyById(Long id)
    {
        return wsQualityCheckVerifyMapper.deleteWsQualityCheckVerifyById(id);
    }
}
