package com.jlkj.project.system.service;

import java.util.List;
import com.jlkj.project.system.domain.SysOrganizationAccount;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author jlkj
 * @date 2020-07-17
 */
public interface ISysOrganizationAccountService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param organizationid 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SysOrganizationAccount selectSysOrganizationAccountById(Long organizationid);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysOrganizationAccount 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SysOrganizationAccount> selectSysOrganizationAccountList(SysOrganizationAccount sysOrganizationAccount);

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysOrganizationAccount 【请填写功能名称】
     * @return 结果
     */
    public int insertSysOrganizationAccount(SysOrganizationAccount sysOrganizationAccount);

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysOrganizationAccount 【请填写功能名称】
     * @return 结果
     */
    public int updateSysOrganizationAccount(SysOrganizationAccount sysOrganizationAccount);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param organizationids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSysOrganizationAccountByIds(Long[] organizationids);

}
