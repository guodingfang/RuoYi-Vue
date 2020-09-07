package com.jlkj.project.system.service;

import java.util.List;
import com.jlkj.framework.web.domain.TreeSelect;
import com.jlkj.project.system.domain.SysEnterprise;
import com.jlkj.project.system.domain.SysOrganizationAccount;

/**
 * 部门管理 服务层
 *
 * @author jlkj
 */
public interface ISysDeptService
{
    /**
     * 查询部门管理数据
     *
     * @param enterprise 企业信息
     * @return 部门信息集合
     */
    public List<SysEnterprise> selectEnterpriseList(SysEnterprise enterprise);

    /**
     * 构建前端所需要树结构
     *
     * @param enterprises 企业列表
     * @return 树结构列表
     */
    public List<SysEnterprise> buildEnterpriseTree(List<SysEnterprise> enterprises);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param enterprises 企业列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildEnterpriseTreeSelect(List<SysEnterprise> enterprises);

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    public List<Integer> selectEnterpriseListByRoleId(Long roleId);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    public SysEnterprise selectEnterpriseById(Long deptId);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    public int selectNormalChildrenEnterpriseById(Long deptId);

    /**
     * 是否存在部门子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public boolean hasChildByEnterpriseId(Long deptId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkEnterpriseExistUser(Long deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    public String checkEnterpriseNameUnique(SysEnterprise dept);

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int insertEnterprise(SysEnterprise dept);

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int updateEnterprise(SysEnterprise dept);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteEnterpriseById(Long enterpriseId);

    /**
     * 删除部门管理信息
     *
     * @param organizationId 组织ID
     * @param type 组织类型
     * @return 结果
     */
    public int organizationSelect(Long organizationId, int type);

    /**
     * 组织名称
     *
     * @param userId 账号ID
     * @return 结果
     */
    public List<SysOrganizationAccount> getOrgNames(Long userId);

    /**
     * 查询当前公司下所有公司和项目
     *
     * @param orgId 公司ID
     * @return 组织树
     */
    public List<SysEnterprise> selectOrgListByOrgId(Long orgId);
}
