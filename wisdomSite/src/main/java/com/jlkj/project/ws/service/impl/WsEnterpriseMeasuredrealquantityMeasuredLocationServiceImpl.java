package com.jlkj.project.ws.service.impl;

import java.util.List;

import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.project.system.domain.SysUser;
import com.jlkj.project.ws.mapper.WsEnterpriseMeasuredrealquantityContentMapper;
import com.jlkj.project.ws.mapper.WsEnterpriseMeasuredrealquantityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsEnterpriseMeasuredrealquantityMeasuredLocationMapper;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantityMeasuredLocation;
import com.jlkj.project.ws.service.IWsEnterpriseMeasuredrealquantityMeasuredLocationService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 实测实量实测部位 Service业务层处理
 *
 * @author pyy
 * @date 2020-06-20
 */
@Service
public class WsEnterpriseMeasuredrealquantityMeasuredLocationServiceImpl implements IWsEnterpriseMeasuredrealquantityMeasuredLocationService
{
    @Autowired
    private WsEnterpriseMeasuredrealquantityMeasuredLocationMapper wsEnterpriseMeasuredrealquantityMeasuredLocationMapper;

    @Autowired
    private WsEnterpriseMeasuredrealquantityMapper wsEnterpriseMeasuredrealquantityMapper;

    @Autowired
    private WsEnterpriseMeasuredrealquantityContentMapper wsEnterpriseMeasuredrealquantityContentMapper;
    /**
     * 查询实测实量实测部位 
     *
     * @param id 实测实量实测部位 ID
     * @return 实测实量实测部位 
     */
    @Override
    public WsEnterpriseMeasuredrealquantityMeasuredLocation selectWsEnterpriseMeasuredrealquantityMeasuredLocationById(Integer id)
    {
        return wsEnterpriseMeasuredrealquantityMeasuredLocationMapper.selectWsEnterpriseMeasuredrealquantityMeasuredLocationById(id);
    }

    /**
     * 查询实测实量实测部位 列表
     *
     * @param wsEnterpriseMeasuredrealquantityMeasuredLocation 实测实量实测部位 
     * @return 实测实量实测部位 
     */
    @Override
    public List<WsEnterpriseMeasuredrealquantityMeasuredLocation> selectWsEnterpriseMeasuredrealquantityMeasuredLocationList(WsEnterpriseMeasuredrealquantityMeasuredLocation wsEnterpriseMeasuredrealquantityMeasuredLocation)
    {
        return wsEnterpriseMeasuredrealquantityMeasuredLocationMapper.selectWsEnterpriseMeasuredrealquantityMeasuredLocationList(wsEnterpriseMeasuredrealquantityMeasuredLocation);
    }

    /**
     * 新增实测实量实测部位 
     *
     * @param wsEnterpriseMeasuredrealquantityMeasuredLocation 实测实量实测部位 
     * @return 结果
     */
    @Override
    public int insertWsEnterpriseMeasuredrealquantityMeasuredLocation(WsEnterpriseMeasuredrealquantityMeasuredLocation wsEnterpriseMeasuredrealquantityMeasuredLocation)
    {
        SysUser user = SecurityUtils.getUserInfo();
        wsEnterpriseMeasuredrealquantityMeasuredLocation.setCreatedBy(user.getUserId().intValue());
        return wsEnterpriseMeasuredrealquantityMeasuredLocationMapper.insertWsEnterpriseMeasuredrealquantityMeasuredLocation(wsEnterpriseMeasuredrealquantityMeasuredLocation);
    }

    /**
     * 修改实测实量实测部位 
     *
     * @param wsEnterpriseMeasuredrealquantityMeasuredLocation 实测实量实测部位 
     * @return 结果
     */
    @Override
    public int updateWsEnterpriseMeasuredrealquantityMeasuredLocation(WsEnterpriseMeasuredrealquantityMeasuredLocation wsEnterpriseMeasuredrealquantityMeasuredLocation)
    {
        return wsEnterpriseMeasuredrealquantityMeasuredLocationMapper.updateWsEnterpriseMeasuredrealquantityMeasuredLocation(wsEnterpriseMeasuredrealquantityMeasuredLocation);
    }

    /**
     * 批量删除实测实量实测部位 
     *
     * @param ids 需要删除的实测实量实测部位 ID
     * @return 结果
     */
    @Transactional(rollbackFor =  Exception.class)
    @Override
    public int deleteWsEnterpriseMeasuredrealquantityMeasuredLocationByIds(Integer[] ids)
    {
//        int i1 = wsEnterpriseMeasuredrealquantityMapper.deleteWsEnterpriseMeasuredrealquantityByIds(ids);
        int i2 = wsEnterpriseMeasuredrealquantityContentMapper.deleteWsEnterpriseMeasuredrealquantityContentByIds(ids);
        int i3 = wsEnterpriseMeasuredrealquantityMeasuredLocationMapper.deleteWsEnterpriseMeasuredrealquantityMeasuredLocationByIds(ids);
        if (  i2 > 0 && i3 > 0){
            return 1;
        }else {
            return -1;
        }
    }

    /**
     * 删除实测实量实测部位 信息
     *
     * @param id 实测实量实测部位 ID
     * @return 结果
     */
    @Override
    public int deleteWsEnterpriseMeasuredrealquantityMeasuredLocationById(Integer id)
    {
        return wsEnterpriseMeasuredrealquantityMeasuredLocationMapper.deleteWsEnterpriseMeasuredrealquantityMeasuredLocationById(id);
    }
}
