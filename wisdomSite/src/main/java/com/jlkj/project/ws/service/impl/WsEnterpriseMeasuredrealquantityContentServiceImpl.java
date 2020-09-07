package com.jlkj.project.ws.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.project.system.domain.SysUser;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantity;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityContent;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityMeasuredLocation;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityPhoto;
import com.jlkj.project.ws.domain.vo.WsEnterpriseMeasuredrealquantityContentVO;
import com.jlkj.project.ws.domain.vo.WsEnterpriseMeasuredrealquantityVO;
import com.jlkj.project.ws.mapper.WsEnterpriseMeasuredrealquantityContentMapper;
import com.jlkj.project.ws.mapper.WsEnterpriseMeasuredrealquantityMapper;
import com.jlkj.project.ws.mapper.WsEnterpriseMeasuredrealquantityMeasuredLocationMapper;
import com.jlkj.project.ws.mapper.WsEnterpriseMeasuredrealquantityPhotoMapper;
import com.jlkj.project.ws.service.IWsEnterpriseMeasuredrealquantityContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 实测实量实测内容 Service业务层处理
 *
 * @author pyy
 * @date 2020-06-20
 */
@Service
public class WsEnterpriseMeasuredrealquantityContentServiceImpl implements IWsEnterpriseMeasuredrealquantityContentService {
    @Autowired
    private WsEnterpriseMeasuredrealquantityContentMapper wsEnterpriseMeasuredrealquantityContentMapper;

    @Autowired
    private WsEnterpriseMeasuredrealquantityMapper wsEnterpriseMeasuredrealquantityMapper;

    @Autowired
    private WsEnterpriseMeasuredrealquantityMeasuredLocationMapper wsEnterpriseMeasuredrealquantityMeasuredLocationMapper;

    @Autowired
    private WsEnterpriseMeasuredrealquantityPhotoMapper wsEnterpriseMeasuredrealquantityPhotoMapper;

    /**
     * 查询实测实量实测内容
     *
     * @param measuredrealquantityMeasuredLocationId 实测实量部位id
     * @return 实测实量实测内容
     */
    @Override
    public WsEnterpriseMeasuredrealquantityContentVO selectWsEnterpriseMeasuredrealquantityContentById(Integer measuredrealquantityMeasuredLocationId) {
        WsEnterpriseMeasuredrealquantityPhoto emp = new WsEnterpriseMeasuredrealquantityPhoto();
        HashMap<String, String> param = new HashMap<>();
        List<Object> list = new ArrayList<>();
        List<WsEnterpriseMeasuredrealquantityVO> emVOList = new ArrayList<>();
        WsEnterpriseMeasuredrealquantityContentVO emcVO = new WsEnterpriseMeasuredrealquantityContentVO();
        //根据区域查询实测实量
        List<WsEnterpriseMeasuredrealquantity> ems = wsEnterpriseMeasuredrealquantityMapper.selectWsEnterpriseMeasuredrealquantityById(measuredrealquantityMeasuredLocationId);
        //查询所属区域信息
        WsEnterpriseMeasuredrealquantityMeasuredLocation emml = wsEnterpriseMeasuredrealquantityMeasuredLocationMapper.selectWsEnterpriseMeasuredrealquantityMeasuredLocationById(measuredrealquantityMeasuredLocationId);
        emcVO.setWsEnterpriseMeasuredrealquantityMeasuredLocation(emml);
        //拼装实测实量+内容数据
        for (WsEnterpriseMeasuredrealquantity em : ems) {
            //获取实测实量图片返回前台
            emp.setMeasuredrealquantityId(em.getId());
            List<WsEnterpriseMeasuredrealquantityPhoto> empList = wsEnterpriseMeasuredrealquantityPhotoMapper.selectWsEnterpriseMeasuredrealquantityPhotoList(emp);
            if (StringUtils.isNotEmpty(empList)) {
                for (WsEnterpriseMeasuredrealquantityPhoto emp2 : empList) {
                    param.put("imgurl", emp2.getPhotoAddres());
                    list.add(param);
                    param = new HashMap<>();
                }
            }
            em.setSceneImageAddres(list);
            List<WsEnterpriseMeasuredrealquantityContent> emcs = wsEnterpriseMeasuredrealquantityContentMapper.selectWsEnterpriseMeasuredrealquantityContentById(em.getId());
            WsEnterpriseMeasuredrealquantityVO emVO = new WsEnterpriseMeasuredrealquantityVO(em, emcs);
            emVOList.add(emVO);
            emcVO.setWsEnterpriseMeasuredrealquantityVOs(emVOList);
        }
        return emcVO;
    }

    /**
     * 查询实测实量实测内容 列表
     *
     * @param wsEnterpriseMeasuredrealquantityContent 实测实量实测内容
     * @return 实测实量实测内容
     */
    @Override
    public List<WsEnterpriseMeasuredrealquantityContent> selectWsEnterpriseMeasuredrealquantityContentList(WsEnterpriseMeasuredrealquantityContent wsEnterpriseMeasuredrealquantityContent) {
        return wsEnterpriseMeasuredrealquantityContentMapper.selectWsEnterpriseMeasuredrealquantityContentList(wsEnterpriseMeasuredrealquantityContent);
    }

    /**
     * 新增实测实量实测内容
     *
     * @param wsEnterpriseMeasuredrealquantityVO
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertWsEnterpriseMeasuredrealquantityContent(WsEnterpriseMeasuredrealquantityVO wsEnterpriseMeasuredrealquantityVO) {
        WsEnterpriseMeasuredrealquantity em = wsEnterpriseMeasuredrealquantityVO.getWsEnterpriseMeasuredrealquantity();
        List<WsEnterpriseMeasuredrealquantityContent> emcList = wsEnterpriseMeasuredrealquantityVO.getWsEnterpriseMeasuredrealquantityContentList();
        em.setCreatedBy(SecurityUtils.getUserInfo().getUserId().intValue());
        int result = 0;
        try {
            if (em != null) {
                result = wsEnterpriseMeasuredrealquantityMapper.insertWsEnterpriseMeasuredrealquantity(em);
                if (result > 0) {
                    WsEnterpriseMeasuredrealquantityMeasuredLocation emml = wsEnterpriseMeasuredrealquantityMeasuredLocationMapper.selectWsEnterpriseMeasuredrealquantityMeasuredLocationById(em.getMeasuredrealquantityMeasuredLocationId());
                    emml.setMeasurementsNo(emml.getMeasurementsNo() + 1);
                    wsEnterpriseMeasuredrealquantityMeasuredLocationMapper.updateWsEnterpriseMeasuredrealquantityMeasuredLocation(emml);
                }
                if (em.getSceneImageAddres() != null) {
                    List<Object> objects = em.getSceneImageAddres();
                    for (Object object : objects) {
                        String json = JSON.toJSONString(object);
                        JSONObject jsonObject = JSONObject.parseObject(json);
                        WsEnterpriseMeasuredrealquantityPhoto emp = new WsEnterpriseMeasuredrealquantityPhoto();
                        emp.setPhotoAddres(jsonObject.getString("imgurl"));
                        emp.setMeasuredrealquantityId(em.getId());
                        wsEnterpriseMeasuredrealquantityPhotoMapper.insertWsEnterpriseMeasuredrealquantityPhoto(emp);
                    }
                }
                if (StringUtils.isNotEmpty(emcList)) {
                    for (WsEnterpriseMeasuredrealquantityContent emc : emcList) {
                        emc.setMeasuredrealquantityMeasuredLocationId(em.getMeasuredrealquantityMeasuredLocationId());
                        emc.setMeasuredrealquantityId(em.getId());
                        result = wsEnterpriseMeasuredrealquantityContentMapper.insertWsEnterpriseMeasuredrealquantityContent(emc);
                    }
                }
                return result;
            }
            return result;
        } catch (Exception e) {
            throw new CustomException("系统异常");
        }
    }

    /**
     * 修改实测实量实测内容
     *
     * @param wsEnterpriseMeasuredrealquantityVO
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateWsEnterpriseMeasuredrealquantityContent(WsEnterpriseMeasuredrealquantityVO wsEnterpriseMeasuredrealquantityVO) {
        WsEnterpriseMeasuredrealquantity em = wsEnterpriseMeasuredrealquantityVO.getWsEnterpriseMeasuredrealquantity();
        List<WsEnterpriseMeasuredrealquantityContent> emcList = wsEnterpriseMeasuredrealquantityVO.getWsEnterpriseMeasuredrealquantityContentList();
        int result = 0;
        try {
            if (StringUtils.isNotEmpty(emcList)) {
                for (WsEnterpriseMeasuredrealquantityContent emc : emcList) {
                    emc.setMeasuredrealquantityMeasuredLocationId(em.getMeasuredrealquantityMeasuredLocationId());
                    emc.setMeasuredrealquantityId(em.getId());
                    result = wsEnterpriseMeasuredrealquantityContentMapper.updateWsEnterpriseMeasuredrealquantityContent(emc);
                }
            }
            return result;
        } catch (Exception e) {
            throw new CustomException("系统异常");
        }
    }

    /**
     * 批量删除实测实量实测内容
     *
     * @param ids 需要删除的实测实量实测内容 ID
     * @return 结果
     */
    @Override
    public int deleteWsEnterpriseMeasuredrealquantityContentByIds(Integer[] ids) {
        return wsEnterpriseMeasuredrealquantityContentMapper.deleteWsEnterpriseMeasuredrealquantityContentByIds(ids);
    }

    /**
     * 删除实测实量实测内容 信息
     *
     * @param id 实测实量实测内容 ID
     * @return 结果
     */
    @Override
    public int deleteWsEnterpriseMeasuredrealquantityContentById(Integer id) {
        return wsEnterpriseMeasuredrealquantityContentMapper.deleteWsEnterpriseMeasuredrealquantityContentById(id);
    }
}
