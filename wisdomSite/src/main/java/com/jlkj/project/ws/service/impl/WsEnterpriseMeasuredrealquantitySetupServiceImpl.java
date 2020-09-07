package com.jlkj.project.ws.service.impl;

import java.util.List;

import com.jlkj.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsEnterpriseMeasuredrealquantitySetupMapper;
import com.jlkj.project.ws.domain.WsEnterpriseMeasuredrealquantitySetup;
import com.jlkj.project.ws.service.IWsEnterpriseMeasuredrealquantitySetupService;

/**
 * 实测实量设置分项工程 Service业务层处理
 *
 * @author pyy
 * @date 2020-06-20
 */
@Service
public class WsEnterpriseMeasuredrealquantitySetupServiceImpl implements IWsEnterpriseMeasuredrealquantitySetupService
{
    @Autowired
    private WsEnterpriseMeasuredrealquantitySetupMapper wsEnterpriseMeasuredrealquantitySetupMapper;

    /**
     * 查询实测实量设置分项工程 
     *
     * @param id 实测实量设置分项工程 ID
     * @return 实测实量设置分项工程 
     */
    @Override
    public WsEnterpriseMeasuredrealquantitySetup selectWsEnterpriseMeasuredrealquantitySetupById(Integer id)
    {
        return wsEnterpriseMeasuredrealquantitySetupMapper.selectWsEnterpriseMeasuredrealquantitySetupById(id);
    }

    /**
     * 查询实测实量设置分项工程 列表
     *
     * @param wsEnterpriseMeasuredrealquantitySetup 实测实量设置分项工程 
     * @return 实测实量设置分项工程 
     */
    @Override
    public List<WsEnterpriseMeasuredrealquantitySetup> selectWsEnterpriseMeasuredrealquantitySetupList(WsEnterpriseMeasuredrealquantitySetup wsEnterpriseMeasuredrealquantitySetup)
    {
        Long organizationId = SecurityUtils.getUserInfo().getOrganizationId();
        wsEnterpriseMeasuredrealquantitySetup.setEnterpriseId(organizationId.intValue());
        return wsEnterpriseMeasuredrealquantitySetupMapper.selectWsEnterpriseMeasuredrealquantitySetupList(wsEnterpriseMeasuredrealquantitySetup);
    }

    /**
     * 新增实测实量设置分项工程 
     *
     * @param wsEnterpriseMeasuredrealquantitySetup 实测实量设置分项工程 
     * @return 结果
     */
    @Override
    public int insertWsEnterpriseMeasuredrealquantitySetup(WsEnterpriseMeasuredrealquantitySetup wsEnterpriseMeasuredrealquantitySetup)
    {
        return wsEnterpriseMeasuredrealquantitySetupMapper.insertWsEnterpriseMeasuredrealquantitySetup(wsEnterpriseMeasuredrealquantitySetup);
    }

    /**
     * 修改实测实量设置分项工程 
     *
     * @param wsEnterpriseMeasuredrealquantitySetup 实测实量设置分项工程 
     * @return 结果
     */
    @Override
    public int updateWsEnterpriseMeasuredrealquantitySetup(WsEnterpriseMeasuredrealquantitySetup wsEnterpriseMeasuredrealquantitySetup)
    {
        return wsEnterpriseMeasuredrealquantitySetupMapper.updateWsEnterpriseMeasuredrealquantitySetup(wsEnterpriseMeasuredrealquantitySetup);
    }

    /**
     * 批量删除实测实量设置分项工程 
     *
     * @param ids 需要删除的实测实量设置分项工程 ID
     * @return 结果
     */
    @Override
    public int deleteWsEnterpriseMeasuredrealquantitySetupByIds(Integer[] ids)
    {
        return wsEnterpriseMeasuredrealquantitySetupMapper.deleteWsEnterpriseMeasuredrealquantitySetupByIds(ids);
    }

    /**
     * 删除实测实量设置分项工程 信息
     *
     * @param id 实测实量设置分项工程 ID
     * @return 结果
     */
    @Override
    public int deleteWsEnterpriseMeasuredrealquantitySetupById(Integer id)
    {
        return wsEnterpriseMeasuredrealquantitySetupMapper.deleteWsEnterpriseMeasuredrealquantitySetupById(id);
    }
}
