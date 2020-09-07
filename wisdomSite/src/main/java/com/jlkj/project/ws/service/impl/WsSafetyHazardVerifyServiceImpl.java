package com.jlkj.project.ws.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.constant.IntelligentMonitoringConstants;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.system.domain.SysUser;
import com.jlkj.project.ws.domain.WsSafetyInspectionRecord;
import com.jlkj.project.ws.domain.WsSafetyPhoto;
import com.jlkj.project.ws.mapper.WsSafetyInspectionRecordMapper;
import com.jlkj.project.ws.mapper.WsSafetyPhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsSafetyHazardVerifyMapper;
import com.jlkj.project.ws.domain.WsSafetyHazardVerify;
import com.jlkj.project.ws.service.IWsSafetyHazardVerifyService;

/**
 * 隐患验证 Service业务层处理
 *
 * @author jlkj
 * @date 2020-07-13
 */
@Service
public class WsSafetyHazardVerifyServiceImpl implements IWsSafetyHazardVerifyService {
    @Autowired
    private WsSafetyHazardVerifyMapper wsSafetyHazardVerifyMapper;

    @Autowired
    private WsSafetyPhotoMapper wsSafetyPhotoMapper;

    @Autowired
    private WsSafetyInspectionRecordMapper wsSafetyInspectionRecordMapper;


    /**
     * 查询隐患验证 列表
     *
     * @param wsSafetyHazardVerify 隐患验证
     * @return 隐患验证
     */
    @Override
    public List<WsSafetyHazardVerify> selectWsSafetyHazardVerifyList(WsSafetyHazardVerify wsSafetyHazardVerify) {
        return wsSafetyHazardVerifyMapper.selectWsSafetyHazardVerifyList(wsSafetyHazardVerify);
    }

    /**
     * 新增隐患验证
     *
     * @param wsSafetyHazardVerify 隐患验证
     * @return 结果
     */
    @Override
    public int insertWsSafetyHazardVerify(WsSafetyHazardVerify wsSafetyHazardVerify) {
        WsSafetyInspectionRecord sir = new WsSafetyInspectionRecord();
        SysUser user = SecurityUtils.getUserInfo();
        wsSafetyHazardVerify.setVerifyPersonId(user.getUserId().intValue());
        wsSafetyHazardVerify.setVerifyDate(new Date());
        List<WsSafetyHazardVerify> wsSafetyHazardVerifies = wsSafetyHazardVerifyMapper.selectWsSafetyHazardVerifyList(wsSafetyHazardVerify);
        if (StringUtils.isEmpty(wsSafetyHazardVerifies)) {
            WsSafetyInspectionRecord wsSafetyInspectionRecord = wsSafetyInspectionRecordMapper.selectWsSafetyInspectionRecordById(wsSafetyHazardVerify.getInspectionId());
            int result = wsSafetyHazardVerifyMapper.insertWsSafetyHazardVerify(wsSafetyHazardVerify);
            if (result > 0) {
                List<Object> objectList = wsSafetyHazardVerify.getVerifyImageAddres();
                for (Object o : objectList) {
                    WsSafetyPhoto wsSafetyPhoto = new WsSafetyPhoto();
                    String json = JSON.toJSONString(o);
                    JSONObject object = JSONObject.parseObject(json);
                    wsSafetyPhoto.setProjectId(wsSafetyInspectionRecord.getProjectId());
                    wsSafetyPhoto.setInspectionId(wsSafetyHazardVerify.getInspectionId());
                    wsSafetyPhoto.setType(IntelligentMonitoringConstants.VERIFY_TYPE);
                    wsSafetyPhoto.setVerifyId(wsSafetyHazardVerify.getId());
                    wsSafetyPhoto.setPhotoAddres(object.getString("imgurl"));
                    wsSafetyPhotoMapper.insertWsSafetyPhoto(wsSafetyPhoto);
                }
                if (wsSafetyHazardVerify.getVerifyResult() == 1) {
                    sir.setId(wsSafetyHazardVerify.getInspectionId());
                    sir.setStatus(3);
                    wsSafetyInspectionRecordMapper.updateWsSafetyInspectionRecord(sir);
                } else if (wsSafetyHazardVerify.getVerifyResult() == 2) {
                    sir.setId(wsSafetyHazardVerify.getInspectionId());
                    sir.setStatus(1);
                    wsSafetyInspectionRecordMapper.updateWsSafetyInspectionRecord(sir);
                }
                return result;
            }
        }
        return 0;
    }

    /**
     * 修改隐患验证
     *
     * @param wsSafetyHazardVerify 隐患验证
     * @return 结果
     */
    @Override
    public int updateWsSafetyHazardVerify(WsSafetyHazardVerify wsSafetyHazardVerify) {
        return wsSafetyHazardVerifyMapper.updateWsSafetyHazardVerify(wsSafetyHazardVerify);
    }

    /**
     * 批量删除隐患验证
     *
     * @param ids 需要删除的隐患验证 ID
     * @return 结果
     */
    @Override
    public int deleteWsSafetyHazardVerifyByIds(Integer[] ids) {
        return wsSafetyHazardVerifyMapper.deleteWsSafetyHazardVerifyByIds(ids);
    }

    /**
     * 删除隐患验证 信息
     *
     * @param id 隐患验证 ID
     * @return 结果
     */
    @Override
    public int deleteWsSafetyHazardVerifyById(Integer id) {
        return wsSafetyHazardVerifyMapper.deleteWsSafetyHazardVerifyById(id);
    }
}
