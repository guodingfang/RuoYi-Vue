package com.jlkj.project.system.mapper;

import java.util.List;
import java.util.Set;

import com.jlkj.project.system.domain.vo.OrganizationVO;
import org.apache.ibatis.annotations.Param;
import com.jlkj.project.system.domain.SysEnterprise;

/**
 * 企业管理 数据层
 *
 * @author ruoyi
 */
public interface SysEnterpriseMapper
{
    /**
     * 查询企业管理数据
     *
     * @param enterprise 企业信息
     * @return 企业信息集合
     */
    public List<SysEnterprise> selectEnterpriseList(SysEnterprise enterprise);

    /**
     * 根据角色ID查询企业树信息
     *
     * @param roleId 角色ID
     * @return 选中企业列表
     */
    public List<Integer> selectEnterpriseListByRoleId(Long roleId);

    /**
     * 根据企业ID查询信息
     *
     * @param enterpriseId 企业ID
     * @return 企业信息
     */
    public SysEnterprise selectEnterpriseById(Long enterpriseId);

    /**
     * 根据ID查询所有子企业
     *
     * @param enterpriseId 企业ID
     * @return 企业列表
     */
    public List<SysEnterprise> selectChildrenEnterpriseById(Long enterpriseId);

    /**
     * 根据ID查询所有子企业（正常状态）
     *
     * @param enterpriseId 企业ID
     * @return 子企业数
     */
    public int selectNormalChildrenEnterpriseById(Long enterpriseId);

    /**
     * 是否存在子节点
     *
     * @param enterpriseId 企业ID
     * @return 结果
     */
    public int hasChildByEnterpriseId(Long enterpriseId);

    /**
     * 查询企业是否存在用户
     *
     * @param enterpriseId 企业ID
     * @return 结果
     */
    public int checkEnterpriseExistUser(Long enterpriseId);

    /**
     * 校验企业名称是否唯一
     *
     * @param enterpriseName 企业名称
     * @param parentId 父企业ID
     * @return 结果
     */
    public SysEnterprise checkEnterpriseNameUnique(@Param("enterpriseName") String enterpriseName, @Param("parentId") Long parentId);

    /**
     * 新增企业信息
     *
     * @param enterprise 企业信息
     * @return 结果
     */
    public int insertEnterprise(SysEnterprise enterprise);

    /**
     * 修改企业信息
     *
     * @param enterprise 企业信息
     * @return 结果
     */
    public int updateEnterprise(SysEnterprise enterprise);

    /**
     * 修改所在企业的父级企业状态
     *
     * @param enterprise 企业
     */
    public void updateEnterpriseStatus(SysEnterprise enterprise);

    /**
     * 修改子元素关系
     *
     * @param enterprises 子元素
     * @return 结果
     */
    public int updateEnterpriseChildren(@Param("enterprises") List<SysEnterprise> enterprises);

    /**
     * 删除企业管理信息
     *
     * @param enterpriseId 企业ID
     * @return 结果
     */
    public int deleteEnterpriseById(Long enterpriseId);

    /**
     * @author CES
     * @Description TODO 查询项目列表组装成组织VO
     * @date 2020-07-15
     * @return java.util.List<com.jlkj.project.system.domain.vo.OrganizationVO>
     */
    public List<SysEnterprise> selectProjectList(Long userId);

    /**
     * @author CES
     * @Description TODO 获取当前用户所有的公司ID
     * @date 2020-07-17
     * @param userId 用户ID
     * @return java.util.List<java.lang.Integer>
     */
    public List<Long> selectEnterpriseIds(long userId);

    /**
     * 查询企业管理数据
     *
     * @return 企业信息集合
     */
    public List<SysEnterprise> selectEnterpriseAllList();

    /**
     * @author CES
     * @Description TODO 查询项目列表
     * @date 2020-07-15
     * @return java.util.List<com.jlkj.project.system.domain.vo.OrganizationVO>
     */
    public List<SysEnterprise> selectOrgProjectList(@Param("orgIds") Set<Long> orgIds);

    /**
     * @author CES
     * @Description TODO 查询项目列表
     * @date 2020-07-15
     * @return java.util.List<com.jlkj.project.system.domain.vo.OrganizationVO>
     */
    public String selectAncestorName(@Param("ancestors") List<Integer> ancestors);

    /**
     * @author CES
     * @Description TODO 查询项目列表
     * @date 2020-07-15
     * @return java.util.List<com.jlkj.project.system.domain.vo.OrganizationVO>
     */
    public SysEnterprise selectByProJectId(Long projectId);

}
