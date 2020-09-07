package com.jlkj.project.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.system.mapper.SysOrganizationAccountMapper;
import com.jlkj.project.system.domain.SysOrganizationAccount;
import com.jlkj.project.system.service.ISysOrganizationAccountService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jlkj
 * @date 2020-07-17
 */
@Service
public class SysOrganizationAccountServiceImpl implements ISysOrganizationAccountService
{
    @Autowired
    private SysOrganizationAccountMapper sysOrganizationAccountMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param organizationid 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public SysOrganizationAccount selectSysOrganizationAccountById(Long organizationid)
    {
        return sysOrganizationAccountMapper.selectSysOrganizationAccountById(organizationid);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param sysOrganizationAccount 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysOrganizationAccount> selectSysOrganizationAccountList(SysOrganizationAccount sysOrganizationAccount)
    {
        return sysOrganizationAccountMapper.selectSysOrganizationAccountList(sysOrganizationAccount);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param sysOrganizationAccount 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysOrganizationAccount(SysOrganizationAccount sysOrganizationAccount)
    {
        return sysOrganizationAccountMapper.insertSysOrganizationAccount(sysOrganizationAccount);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param sysOrganizationAccount 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysOrganizationAccount(SysOrganizationAccount sysOrganizationAccount)
    {
        return sysOrganizationAccountMapper.updateSysOrganizationAccount(sysOrganizationAccount);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param organizationids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSysOrganizationAccountByIds(Long[] organizationids)
    {
        return sysOrganizationAccountMapper.deleteSysOrganizationAccountByIds(organizationids);
    }

}
