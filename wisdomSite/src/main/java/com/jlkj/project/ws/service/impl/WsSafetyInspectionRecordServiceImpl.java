package com.jlkj.project.ws.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.constant.Constants;
import com.jlkj.common.constant.IntelligentMonitoringConstants;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.ServletUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.spring.SpringUtils;
import com.jlkj.framework.redis.RedisCache;
import com.jlkj.framework.security.LoginUser;
import com.jlkj.framework.security.service.TokenService;
import com.jlkj.project.system.domain.SysUser;
import com.jlkj.project.system.mapper.SysUserMapper;
import com.jlkj.project.ws.domain.*;
import com.jlkj.project.ws.domain.vo.WsSafetyHazardRectifyAndVerifyVO;
import com.jlkj.project.ws.domain.vo.WsSafetyInspectionRecordVO;
import com.jlkj.project.ws.mapper.*;
import com.jlkj.project.ws.service.IWsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.service.IWsSafetyInspectionRecordService;

/**
 * 安全巡视记录 Service业务层处理
 *
 * @author jlkj
 * @date 2020-07-13
 */
@Service
public class WsSafetyInspectionRecordServiceImpl implements IWsSafetyInspectionRecordService {
    @Autowired
    private WsSafetyInspectionRecordMapper wsSafetyInspectionRecordMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private WsSafetyPhotoMapper wsSafetyPhotoMapper;

    @Autowired
    private WsSafetyHazardVerifyMapper wsSafetyHazardVerifyMapper;

    @Autowired
    private WsSafetyHazardRectifyMapper wsSafetyHazardRectifyMapper;


    /**
     * 查询安全巡视记录
     *
     * @param id 安全巡视记录 ID
     * @return 安全巡视记录
     */
    @Override
    public WsSafetyInspectionRecord selectWsSafetyInspectionRecordById(Integer id) {
        return wsSafetyInspectionRecordMapper.selectWsSafetyInspectionRecordById(id);
    }

    /**
     * 查询安全巡视记录 列表
     *
     * @param wsSafetyInspectionRecord 安全巡视记录
     * @return 安全巡视记录
     */
    @Override
    public List<WsSafetyInspectionRecord> selectWsSafetyInspectionRecordList(WsSafetyInspectionRecord wsSafetyInspectionRecord) {
        StringBuilder stringBuilder = null;
        List<Integer> projectIds = null;
        if (wsSafetyInspectionRecord.getProjectId() != null) {
            projectIds = new ArrayList<>();
            projectIds.add(wsSafetyInspectionRecord.getProjectId());
        }else {
            List<WsProject> wsProjects = SecurityUtils.getUserInfo().getProjects();
            projectIds = new ArrayList<>();
            for (WsProject wsProject : wsProjects) {
                projectIds.add(wsProject.getId());
            }
        }
        List<WsSafetyInspectionRecord> sirList = wsSafetyInspectionRecordMapper.selectWsSafetyInspectionRecordList(wsSafetyInspectionRecord, projectIds);
        for (WsSafetyInspectionRecord safetyInspectionRecord : sirList) {
            if (safetyInspectionRecord.getSenderId() != null) {
                String ids = safetyInspectionRecord.getSenderId();
                String[] senderId = ids.split(",");
                String[] array = new String[senderId.length];
                stringBuilder = new StringBuilder();
                for (int i1 = 0; i1 < senderId.length; i1++) {
                    SysUser sysUser = sysUserMapper.selectUserById(Long.parseLong(senderId[i1]));
                    array[i1] = sysUser.getNickName();
                    stringBuilder.append(array[i1]).append(",");
                }
                int index = stringBuilder.lastIndexOf(",");
                if (index == stringBuilder.length() - 1) {
                    String str = stringBuilder.toString();
                    String substring = str.substring(0, index);
                    safetyInspectionRecord.setSenderName(substring);
                } else {
                    safetyInspectionRecord.setSenderName(stringBuilder.toString());
                }
            }
        }
        return sirList;
    }

    /**
     * 查询安全巡视和整改验证详情
     *
     * @param id 安全巡视记录 ID
     * @return 安全巡视记录VO
     */
    @Override
    public WsSafetyInspectionRecordVO checkSafetyInspectionRectificationVerificationDetails(Integer id) {
        List<Object> imgAddress = new ArrayList<>();
        List<WsSafetyHazardRectifyAndVerifyVO> rectifyAndVerifyVOList = new ArrayList<>();
        HashMap<String, String> param = new HashMap<>();
        WsSafetyPhoto wsSafetyPhoto = null;
        WsSafetyInspectionRecordVO wsSafetyInspectionRecordVO = new WsSafetyInspectionRecordVO();
        //获取安全巡视信息
        WsSafetyInspectionRecord sir = wsSafetyInspectionRecordMapper.selectWsSafetyInspectionRecordById(id);
        wsSafetyPhoto = new WsSafetyPhoto();
        wsSafetyPhoto.setInspectionId(sir.getId());
        wsSafetyPhoto.setType(IntelligentMonitoringConstants.CHECK_TYPE);
        List<WsSafetyPhoto> wsSafetyPhotos = wsSafetyPhotoMapper.selectWsSafetyPhotoList(wsSafetyPhoto);
        if (StringUtils.isNotEmpty(wsSafetyPhotos)) {
            imgAddress = new ArrayList<>();
            for (WsSafetyPhoto safetyPhoto : wsSafetyPhotos) {
                param.put("imgurl", safetyPhoto.getPhotoAddres());
                imgAddress.add(param);
                param = new HashMap<>();
            }
        }
        sir.setUploadAddress(imgAddress);
        wsSafetyInspectionRecordVO.setWsSafetyInspectionRecord(sir);
        //获取隐患整改信息
        WsSafetyHazardRectify safetyHazardRectify = new WsSafetyHazardRectify();
        safetyHazardRectify.setInspectionId(sir.getId());
        List<WsSafetyHazardRectify> wsSafetyHazardRectifies = wsSafetyHazardRectifyMapper.selectWsSafetyHazardRectifyList(safetyHazardRectify);
        //获取隐患验证信息
        WsSafetyHazardVerify shv = new WsSafetyHazardVerify();
        shv.setInspectionId(sir.getId());
        List<WsSafetyHazardVerify> safetyHazardVerifies = wsSafetyHazardVerifyMapper.selectWsSafetyHazardVerifyList(shv);
        if (StringUtils.isNotEmpty(wsSafetyHazardRectifies)) {
            for (int i = 0; i < wsSafetyHazardRectifies.size(); i++) {
                WsSafetyHazardRectifyAndVerifyVO wsSafetyHazardRectifyAndVerifyVO = new WsSafetyHazardRectifyAndVerifyVO();
                wsSafetyPhoto = new WsSafetyPhoto();
                wsSafetyPhoto.setType(IntelligentMonitoringConstants.RECTIFY_TYPE);
                wsSafetyPhoto.setRectifyId(wsSafetyHazardRectifies.get(i).getId());
                List<WsSafetyPhoto> safetyPhotos = wsSafetyPhotoMapper.selectWsSafetyPhotoList(wsSafetyPhoto);
                if (safetyPhotos != null) {
                    imgAddress = new ArrayList<>();
                    for (WsSafetyPhoto safetyPhoto : safetyPhotos) {
                        param.put("imgurl", safetyPhoto.getPhotoAddres());
                        imgAddress.add(param);
                        param = new HashMap<>();
                    }
                }
                wsSafetyHazardRectifies.get(i).setRectifyImageAddress(imgAddress);
                //获取隐患验证信息
                if (i < safetyHazardVerifies.size() && safetyHazardVerifies.get(i) != null) {
                    Integer verifyResult = safetyHazardVerifies.get(i).getVerifyResult();
                    if (verifyResult == 1) {
                        safetyHazardVerifies.get(i).setVerifyResultName(IntelligentMonitoringConstants.PASS);
                    } else {
                        safetyHazardVerifies.get(i).setVerifyResultName(IntelligentMonitoringConstants.NOPASS);
                    }
                    wsSafetyPhoto = new WsSafetyPhoto();
                    wsSafetyPhoto.setType(IntelligentMonitoringConstants.VERIFY_TYPE);
                    wsSafetyPhoto.setVerifyId(safetyHazardVerifies.get(i).getId());
                    List<WsSafetyPhoto> photoList = wsSafetyPhotoMapper.selectWsSafetyPhotoList(wsSafetyPhoto);
                    if (photoList != null) {
                        imgAddress = new ArrayList<>();
                        for (WsSafetyPhoto safetyPhoto : photoList) {
                            param.put("imgurl", safetyPhoto.getPhotoAddres());
                            imgAddress.add(param);
                            param = new HashMap<>();
                        }
                    }
                    safetyHazardVerifies.get(i).setVerifyImageAddres(imgAddress);
                    wsSafetyHazardRectifyAndVerifyVO.setWsSafetyHazardVerify(safetyHazardVerifies.get(i));
                }
                wsSafetyHazardRectifyAndVerifyVO.setWsSafetyHazardRectify(wsSafetyHazardRectifies.get(i));
                rectifyAndVerifyVOList.add(wsSafetyHazardRectifyAndVerifyVO);
            }
            wsSafetyInspectionRecordVO.setWsSafetyHazardRectifyAndVerifyList(rectifyAndVerifyVOList);
        }
        return wsSafetyInspectionRecordVO;
    }

    /**
     * 新增安全巡视记录
     *
     * @param wsSafetyInspectionRecord 安全巡视记录
     * @return 结果
     */
    @Override
    public int insertWsSafetyInspectionRecord(WsSafetyInspectionRecord wsSafetyInspectionRecord) {
        SysUser sysUser = SecurityUtils.getUserInfo();
        wsSafetyInspectionRecord.setCheckPositionId(sysUser.getUserId().intValue());
        int projectId = 0;
        if (sysUser.getType() == 1) {
            projectId = sysUser.getProjects().get(0).getId();
        }
        wsSafetyInspectionRecord.setProjectId(projectId);
        if (wsSafetyInspectionRecord.getUploadAddress() != null) {
            List<Object> objectList = wsSafetyInspectionRecord.getUploadAddress();
            for (Object o : objectList) {
                String json = JSON.toJSONString(o);
                JSONObject object = JSONObject.parseObject(json);
                WsSafetyPhoto wsSafetyPhoto = new WsSafetyPhoto();
                wsSafetyPhoto.setType(IntelligentMonitoringConstants.CHECK_TYPE);
                wsSafetyPhoto.setProjectId(wsSafetyInspectionRecord.getProjectId());
                wsSafetyPhoto.setInspectionId(wsSafetyInspectionRecord.getId());
                wsSafetyPhoto.setPhotoAddres(object.getString("imgurl"));
                wsSafetyPhotoMapper.insertWsSafetyPhoto(wsSafetyPhoto);
            }
        }
        return wsSafetyInspectionRecordMapper.insertWsSafetyInspectionRecord(wsSafetyInspectionRecord);
    }

    /**
     * 修改安全巡视记录
     *
     * @param wsSafetyInspectionRecord 安全巡视记录
     * @return 结果
     */
    @Override
    public int updateWsSafetyInspectionRecord(WsSafetyInspectionRecord wsSafetyInspectionRecord) {
        wsSafetyInspectionRecord.setUpdatedTime(new Date());
        return wsSafetyInspectionRecordMapper.updateWsSafetyInspectionRecord(wsSafetyInspectionRecord);
    }

    /**
     * 批量删除安全巡视记录
     *
     * @param ids 需要删除的安全巡视记录 ID
     * @return 结果
     */
    @Override
    public int deleteWsSafetyInspectionRecordByIds(Integer[] ids) {
        return wsSafetyInspectionRecordMapper.deleteWsSafetyInspectionRecordByIds(ids);
    }

    /**
     * 删除安全巡视记录 信息
     *
     * @param id 安全巡视记录 ID
     * @return 结果
     */
    @Override
    public int deleteWsSafetyInspectionRecordById(Integer id) {
        return wsSafetyInspectionRecordMapper.deleteWsSafetyInspectionRecordById(id);
    }

}
