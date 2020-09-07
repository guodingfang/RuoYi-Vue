package com.jlkj.project.ws.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.constant.IntelligentMonitoringConstants;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.project.system.domain.SysUser;
import com.jlkj.project.ws.domain.WsSafetyInspectionRecord;
import com.jlkj.project.ws.domain.WsSafetyPhoto;
import com.jlkj.project.ws.mapper.WsSafetyInspectionRecordMapper;
import com.jlkj.project.ws.mapper.WsSafetyPhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsSafetyHazardRectifyMapper;
import com.jlkj.project.ws.domain.WsSafetyHazardRectify;
import com.jlkj.project.ws.service.IWsSafetyHazardRectifyService;

/**
 * 隐患整改 Service业务层处理
 *
 * @author jlkj
 * @date 2020-07-13
 */
@Service
public class WsSafetyHazardRectifyServiceImpl implements IWsSafetyHazardRectifyService {
    @Autowired
    private WsSafetyHazardRectifyMapper wsSafetyHazardRectifyMapper;

    @Autowired
    private WsSafetyPhotoMapper wsSafetyPhotoMapper;

    @Autowired
    private WsSafetyInspectionRecordMapper wsSafetyInspectionRecordMapper;

    /**
     * 查询隐患整改
     *
     * @param id 隐患整改 ID
     * @return 隐患整改
     */
    @Override
    public WsSafetyHazardRectify selectWsSafetyHazardRectifyById(Integer id) {
        return wsSafetyHazardRectifyMapper.selectWsSafetyHazardRectifyById(id);
    }

    /**
     * 查询隐患整改 列表
     *
     * @param wsSafetyHazardRectify 隐患整改
     * @return 隐患整改
     */
    @Override
    public List<WsSafetyHazardRectify> selectWsSafetyHazardRectifyList(WsSafetyHazardRectify wsSafetyHazardRectify) {
        return wsSafetyHazardRectifyMapper.selectWsSafetyHazardRectifyList(wsSafetyHazardRectify);
    }

    /**
     * 新增隐患整改
     *
     * @param wsSafetyHazardRectify 隐患整改
     * @return 结果
     */
    @Override
    public int insertWsSafetyHazardRectify(WsSafetyHazardRectify wsSafetyHazardRectify) {
        SysUser user = SecurityUtils.getUserInfo();
        wsSafetyHazardRectify.setRectifyPersonId(user.getUserId().intValue());
        wsSafetyHazardRectify.setRectifyTime(new Date());
        WsSafetyInspectionRecord wsSafetyInspectionRecord = wsSafetyInspectionRecordMapper.selectWsSafetyInspectionRecordById(wsSafetyHazardRectify.getInspectionId());
        int result = wsSafetyHazardRectifyMapper.insertWsSafetyHazardRectify(wsSafetyHazardRectify);
        if (result > 0) {
            List<Object> objectList = wsSafetyHazardRectify.getRectifyImageAddress();
            for (int i = 0; i < objectList.size(); i++) {
                String json = JSON.toJSONString(objectList.get(i));
                JSONObject object = JSONObject.parseObject(json);
                WsSafetyPhoto wsSafetyPhoto = new WsSafetyPhoto();
                wsSafetyPhoto.setProjectId(wsSafetyInspectionRecord.getProjectId());
                wsSafetyPhoto.setType(IntelligentMonitoringConstants.RECTIFY_TYPE);
                wsSafetyPhoto.setInspectionId(wsSafetyHazardRectify.getInspectionId());
                wsSafetyPhoto.setRectifyId(wsSafetyHazardRectify.getId());
                wsSafetyPhoto.setPhotoAddres(object.getString("imgurl"));
                wsSafetyPhotoMapper.insertWsSafetyPhoto(wsSafetyPhoto);
            }
            WsSafetyInspectionRecord sir = new WsSafetyInspectionRecord();
            sir.setId(wsSafetyHazardRectify.getInspectionId());
            sir.setStatus(2);
            wsSafetyInspectionRecordMapper.updateWsSafetyInspectionRecord(sir);
            return result;

        }
        return result;
    }

    /**
     * 修改隐患整改
     *
     * @param wsSafetyHazardRectify 隐患整改
     * @return 结果
     */
    @Override
    public int updateWsSafetyHazardRectify(WsSafetyHazardRectify wsSafetyHazardRectify) {
        return wsSafetyHazardRectifyMapper.updateWsSafetyHazardRectify(wsSafetyHazardRectify);
    }

    /**
     * 批量删除隐患整改
     *
     * @param ids 需要删除的隐患整改 ID
     * @return 结果
     */
    @Override
    public int deleteWsSafetyHazardRectifyByIds(Integer[] ids) {
        return wsSafetyHazardRectifyMapper.deleteWsSafetyHazardRectifyByIds(ids);
    }

    /**
     * 删除隐患整改 信息
     *
     * @param id 隐患整改 ID
     * @return 结果
     */
    @Override
    public int deleteWsSafetyHazardRectifyById(Integer id) {
        return wsSafetyHazardRectifyMapper.deleteWsSafetyHazardRectifyById(id);
    }
}
