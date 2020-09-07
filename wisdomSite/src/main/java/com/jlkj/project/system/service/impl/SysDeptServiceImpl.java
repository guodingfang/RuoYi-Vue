package com.jlkj.project.system.service.impl;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.jlkj.common.constant.Constants;
import com.jlkj.common.utils.ServletUtils;
import com.jlkj.common.utils.spring.SpringUtils;
import com.jlkj.framework.redis.RedisCache;
import com.jlkj.framework.security.LoginUser;
import com.jlkj.framework.security.service.TokenService;
import com.jlkj.project.system.domain.SysOrganizationAccount;
import com.jlkj.project.system.domain.SysUser;
import com.jlkj.project.system.mapper.SysOrganizationAccountMapper;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.mapper.WsProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.jlkj.common.constant.UserConstants;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.framework.web.domain.TreeSelect;
import com.jlkj.project.system.domain.SysEnterprise;
import com.jlkj.project.system.mapper.SysEnterpriseMapper;
import com.jlkj.project.system.service.ISysDeptService;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * 企业管理 服务实现
 *
 * @author jlkj
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {
    @Autowired
    private SysEnterpriseMapper enterpriseMapper;

    @Autowired
    private SysOrganizationAccountMapper organizationAccountMapper;

    @Autowired
    private WsProjectMapper wsProjectMapper;

    @Autowired
    private RedisCache redisCache;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    /**
     * 查询企业管理数据
     *
     * @param enterprise 企业信息
     * @return 企业信息集合
     */
    @Override
//    @DataScope(enterpriseAlias = "d")
//    @DataSource(value = DataSourceType.SLAVE)
    public List<SysEnterprise> selectEnterpriseList(SysEnterprise enterprise) {
        List<SysEnterprise> allList = new ArrayList<>();
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        SysUser currentUser = loginUser.getUser();
        if (StringUtils.isNotNull(currentUser.getUserId()) && currentUser.isAdmin()) {
            //查询所有的树
            allList = enterpriseMapper.selectEnterpriseAllList();
            Set<Long> orgIds = new HashSet<>();
            allList.stream().forEach(n->{ orgIds.add(n.getEnterpriseId());n.setType(0);n.setAuth(1); });
            List<SysEnterprise> projectList = enterpriseMapper.selectOrgProjectList(orgIds);
            projectList = projectList.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(SysEnterprise::getEnterpriseName))), ArrayList::new));
            projectList.stream().forEach(p->{ p.setType(1);p.setAuth(1); });
            allList.addAll(projectList);
        } else if (StringUtils.isNull(currentUser.getEnterpriseId())) {
            //返回空
        } else {
            long userId = currentUser.getUserId();
            //所有的企业列表
            Set<Long> aa = new HashSet<>();
            Set<Long> bb = new HashSet<>();
            //查询组织ID
            List<Long> enterpriseIds = enterpriseMapper.selectEnterpriseIds(userId);
            List<SysEnterprise> currentEnterpriseList = new ArrayList<>();
            for (Long id : enterpriseIds) {
                enterprise.setEnterpriseId(id);
                currentEnterpriseList.addAll(enterpriseMapper.selectEnterpriseList(enterprise));
            }
            currentEnterpriseList = currentEnterpriseList.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(SysEnterprise::getEnterpriseId))), ArrayList::new));
            for (SysEnterprise e : currentEnterpriseList) {
                e.setAuth(1);
                e.setType(0);
                //获取用户 拥有的 公司ID
                bb.add(e.getEnterpriseId());
                List<String> ancestorList = new ArrayList<>();
                if(e.getAncestors().length()<6){
                    ancestorList.add(e.getEnterpriseId().toString());
                }else {
                    ancestorList = Arrays.asList(e.getAncestors().substring(6, e.getAncestors().length()).split(","));
                }
                aa.addAll(ancestorList.stream().map(x -> Long.parseLong(x)).collect(Collectors.toList()));

            }
            //根据企业IDs作为条件，查询所有的项目集合。 in作为条件查询完后去重，赋予类型和权限
            List<SysEnterprise> orgProjectList = new ArrayList<>();
            if(bb.size()>0){
                orgProjectList = enterpriseMapper.selectOrgProjectList(bb);
            }
            List<SysEnterprise> currentProjectList = enterpriseMapper.selectProjectList(userId);
            currentProjectList.addAll(orgProjectList);
            currentProjectList = currentProjectList.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(SysEnterprise::getEnterpriseName))), ArrayList::new));
            for (SysEnterprise e : currentProjectList) {
                e.setAuth(1);
                e.setType(1);
                String ancestorStr = e.getAncestors().substring(6, e.getAncestors().length());
                bb.add(e.getEnterpriseId());
                aa.addAll(Arrays.asList(ancestorStr.split(",")).stream().map(x -> Long.parseLong(x)).collect(Collectors.toList()));
            }
            aa.removeAll(bb);
            for (Long id : aa) {
                SysEnterprise sysEnterprise = enterpriseMapper.selectEnterpriseById(id);
                sysEnterprise.setAuth(0);
                sysEnterprise.setType(0);
                allList.add(sysEnterprise);
            }
            allList.addAll(currentEnterpriseList);
            allList.addAll(currentProjectList);
        }
        return allList;
    }

    /**
     * 构建前端所需要树结构
     *
     * @param enterprises 企业列表
     * @return 树结构列表
     */
    @Override
    public List<SysEnterprise> buildEnterpriseTree(List<SysEnterprise> enterprises) {
        List<SysEnterprise> returnList = new ArrayList<SysEnterprise>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysEnterprise enterprise : enterprises) {
            tempList.add(enterprise.getEnterpriseId());
        }
        for (Iterator<SysEnterprise> iterator = enterprises.iterator(); iterator.hasNext(); ) {
            SysEnterprise dept = (SysEnterprise) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(enterprises, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = enterprises;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param enterprises 企业列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildEnterpriseTreeSelect(List<SysEnterprise> enterprises) {
        List<SysEnterprise> deptTrees = buildEnterpriseTree(enterprises);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据角色ID查询企业树信息
     *
     * @param roleId 角色ID
     * @return 选中企业列表
     */
    @Override
    public List<Integer> selectEnterpriseListByRoleId(Long roleId) {
        return enterpriseMapper.selectEnterpriseListByRoleId(roleId);
    }

    /**
     * 根据企业ID查询信息
     *
     * @param enterpriseId 企业ID
     * @return 企业信息
     */
    @Override
    public SysEnterprise selectEnterpriseById(Long enterpriseId) {
        return enterpriseMapper.selectEnterpriseById(enterpriseId);
    }

    /**
     * 根据ID查询所有子企业（正常状态）
     *
     * @param enterpriseId 企业ID
     * @return 子企业数
     */
    @Override
    public int selectNormalChildrenEnterpriseById(Long enterpriseId) {
        return enterpriseMapper.selectNormalChildrenEnterpriseById(enterpriseId);
    }

    /**
     * 是否存在子节点
     *
     * @param enterpriseId 企业ID
     * @return 结果
     */
    @Override
    public boolean hasChildByEnterpriseId(Long enterpriseId) {
        int result = enterpriseMapper.hasChildByEnterpriseId(enterpriseId);
        return result > 0 ? true : false;
    }

    /**
     * 查询企业是否存在用户
     *
     * @param enterpriseId 企业ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkEnterpriseExistUser(Long enterpriseId) {
        int result = enterpriseMapper.checkEnterpriseExistUser(enterpriseId);
        return result > 0 ? true : false;
    }

    /**
     * 校验企业名称是否唯一
     *
     * @param dept 企业信息
     * @return 结果
     */
    @Override
    public String checkEnterpriseNameUnique(SysEnterprise dept) {
        Long deptId = StringUtils.isNull(dept.getEnterpriseId()) ? -1L : dept.getEnterpriseId();
        SysEnterprise info = enterpriseMapper.checkEnterpriseNameUnique(dept.getEnterpriseName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getEnterpriseId().longValue() != deptId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增保存企业信息
     *
     * @param enterprise 企业信息
     * @return 结果
     */
    @Override
    public int insertEnterprise(SysEnterprise enterprise) {
        SysEnterprise info = enterpriseMapper.selectEnterpriseById(enterprise.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
            throw new CustomException("企业停用，不允许新增");
        }
        enterprise.setAncestors(info.getAncestors() + "," + enterprise.getParentId());
        return enterpriseMapper.insertEnterprise(enterprise);
    }

    /**
     * 修改保存企业信息
     *
     * @param enterprise 企业信息
     * @return 结果
     */
    @Override
    public int updateEnterprise(SysEnterprise enterprise) {
        SysEnterprise newParentDept = enterpriseMapper.selectEnterpriseById(enterprise.getParentId());
        SysEnterprise oldDept = enterpriseMapper.selectEnterpriseById(enterprise.getEnterpriseId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getEnterpriseId();
            String oldAncestors = oldDept.getAncestors();
            enterprise.setAncestors(newAncestors);
            updateDeptChildren(enterprise.getEnterpriseId(), newAncestors, oldAncestors);
        }
        int result = enterpriseMapper.updateEnterprise(enterprise);
        if (UserConstants.DEPT_NORMAL.equals(enterprise.getStatus())) {
            // 如果该企业是启用状态，则启用该企业的所有上级企业
            updateParentDeptStatus(enterprise);
        }
        return result;
    }

    /**
     * 修改该企业的父级企业状态
     *
     * @param enterprise 当前企业
     */
    private void updateParentDeptStatus(SysEnterprise enterprise) {
        String updateBy = enterprise.getUpdateBy();
        enterprise = enterpriseMapper.selectEnterpriseById(enterprise.getEnterpriseId());
        enterprise.setUpdateBy(updateBy);
        enterpriseMapper.updateEnterpriseStatus(enterprise);
    }

    /**
     * 修改子元素关系
     *
     * @param enterpriseId 被修改的企业ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long enterpriseId, String newAncestors, String oldAncestors) {
        List<SysEnterprise> children = enterpriseMapper.selectChildrenEnterpriseById(enterpriseId);
        for (SysEnterprise child : children) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            enterpriseMapper.updateEnterpriseChildren(children);
        }
    }

    /**
     * 删除企业管理信息
     *
     * @param enterpriseId 企业ID
     * @return 结果
     */
    @Override
    public int deleteEnterpriseById(Long enterpriseId) {
        organizationAccountMapper.deleteSysOrganizationAccount(new SysOrganizationAccount(enterpriseId,0));
        return enterpriseMapper.deleteEnterpriseById(enterpriseId);
    }

    @Override
    public int organizationSelect(Long organizationId, int type) {
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        user.setOrganizationId(organizationId);
        user.setType(type);
        List<WsProject> projects = new ArrayList<>();
        if(type == Constants.ORG_TYPE_PROJECT){
            projects.add(wsProjectMapper.selectWsProjectById(Integer.valueOf(organizationId.toString())));
        }else {
            List<SysEnterprise> enterprises = enterpriseMapper.selectEnterpriseList(new SysEnterprise(organizationId));
            enterprises = enterprises.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(SysEnterprise::getEnterpriseId))), ArrayList::new));
            List<Long> enterpriseIds = new ArrayList<>();
            enterprises.stream().forEach((n)->{ enterpriseIds.add(n.getEnterpriseId()); });
            List<WsProject> projectList = wsProjectMapper.selectProjectByEnterpriseIds(enterpriseIds);
            projects.addAll(projectList);
        }
        user.setProjects(projects);
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * TokenService.MILLIS_MINUTE);
        loginUser.setUser(user);
        // 根据uuid将loginUser缓存
        String userKey = Constants.LOGIN_TOKEN_KEY + loginUser.getToken();
        redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
        return 1;
    }

    @Override
    public List<SysOrganizationAccount> getOrgNames(Long userId) {
        List<SysOrganizationAccount> orgList = organizationAccountMapper.selectSysOrganizationAccountList(new SysOrganizationAccount(userId));
        SysEnterprise enterprise = new SysEnterprise();
        for (SysOrganizationAccount org : orgList) {
            if (org.getType() == 0) {
                enterprise = enterpriseMapper.selectEnterpriseById(org.getOrganizationid());
            } else {
                enterprise = enterpriseMapper.selectByProJectId(org.getOrganizationid());
            }
            if(enterprise.getAncestors().length()<6){
                org.setOrgName(enterprise.getEnterpriseName());
            }else {
                String ancestorStr = enterprise.getAncestors().substring(6, enterprise.getAncestors().length()).trim();
                List<String> ancestors = Arrays.asList(ancestorStr.split(","));
                List<Integer> aa = ancestors.stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
//            ancestorNames.add();
                org.setOrgName(enterprise.getEnterpriseName() + "," + enterpriseMapper.selectAncestorName(aa));
            }
        }
        return orgList;
    }

    @Override
    public List<SysEnterprise> selectOrgListByOrgId(Long orgId) {
        List<SysEnterprise> enterprises = enterpriseMapper.selectEnterpriseList(new SysEnterprise(orgId));
        enterprises = enterprises.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(SysEnterprise::getEnterpriseId))), ArrayList::new));
        Set<Long> enterpriseIds = new HashSet<>();
        enterprises.stream().forEach((n)->{ enterpriseIds.add(n.getEnterpriseId()); });
        List<SysEnterprise> orgProjectList = enterpriseMapper.selectOrgProjectList(enterpriseIds);
        return orgProjectList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysEnterprise> list, SysEnterprise t) {
        // 得到子节点列表
        List<SysEnterprise> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysEnterprise tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<SysEnterprise> it = childList.iterator();
                while (it.hasNext()) {
                    SysEnterprise n = (SysEnterprise) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysEnterprise> getChildList(List<SysEnterprise> list, SysEnterprise t) {
        List<SysEnterprise> tlist = new ArrayList<SysEnterprise>();
        Iterator<SysEnterprise> it = list.iterator();
        while (it.hasNext()) {
            SysEnterprise n = (SysEnterprise) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getEnterpriseId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysEnterprise> list, SysEnterprise t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

}
