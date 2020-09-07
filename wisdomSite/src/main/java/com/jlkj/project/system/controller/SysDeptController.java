package com.jlkj.project.system.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.jlkj.common.constant.UserConstants;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.project.system.domain.SysEnterprise;
import com.jlkj.project.system.service.ISysDeptService;

/**
 * 企业信息
 *
 * @author jlkj
 */
@Api(value = "组织接口", tags = {"组织接口"})
@RestController
@RequestMapping("/system/enterprise")
public class SysDeptController extends BaseController {
    @Autowired
    private ISysDeptService deptService;

    /**
     * 获取企业列表
     */
    @PreAuthorize("@ss.hasPermi('system:enterprise:list')")
    @GetMapping("/list")
    public AjaxResult list(SysEnterprise enterprise) {
        List<SysEnterprise> enterprises = deptService.selectEnterpriseList(enterprise);
        return AjaxResult.success(enterprises);
    }

    /**
     * 查询企业列表（排除节点）
     */
    @PreAuthorize("@ss.hasPermi('system:enterprise:list')")
    @GetMapping("/list/exclude/{enterpriseId}")
    public AjaxResult excludeChild(@PathVariable(value = "enterpriseId", required = false) Long enterpriseId) {
        List<SysEnterprise> enterprises = deptService.selectEnterpriseList(new SysEnterprise());
        Iterator<SysEnterprise> it = enterprises.iterator();
        while (it.hasNext()) {
            SysEnterprise d = (SysEnterprise) it.next();
            if (d.getEnterpriseId().intValue() == enterpriseId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), enterpriseId + "")) {
                it.remove();
            }
        }
        return AjaxResult.success(enterprises);
    }

    /**
     * 根据企业编号获取详细信息
     */
    @ApiOperation("组织-编辑-回显数据")
    @ApiParam(name = "enterpriseId", value = "1", required = true )
    @PreAuthorize("@ss.hasPermi('system:enterprise:query')")
    @GetMapping(value = "/{enterpriseId}")
    public AjaxResult getInfo(@PathVariable Long enterpriseId) {
        return AjaxResult.success(deptService.selectEnterpriseById(enterpriseId));
    }

    /**
     * 获取企业下拉树列表
     */
    @ApiOperation("获取组织项目树列表")
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysEnterprise enterprise) {
        List<SysEnterprise> enterprises = deptService.selectEnterpriseList(new SysEnterprise());
        return AjaxResult.success(deptService.buildEnterpriseTreeSelect(enterprises));
    }

    /**
     * 加载对应角色企业列表树
     */
    @GetMapping(value = "/roleEnterpriseTreeselect/{roleId}")
    public AjaxResult roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
        List<SysEnterprise> enterprises = deptService.selectEnterpriseList(new SysEnterprise());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", deptService.selectEnterpriseListByRoleId(roleId));
        ajax.put("enterprises", deptService.buildEnterpriseTreeSelect(enterprises));
        return ajax;
    }

    /**
     * 新增组织
     */
    @ApiOperation("新增组织")
    @PreAuthorize("@ss.hasPermi('system:enterprise:add')")
    @Log(title = "组织管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysEnterprise enterprise) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkEnterpriseNameUnique(enterprise))) {
            return AjaxResult.error("新增组织'" + enterprise.getEnterpriseName() + "'失败，组织名称已存在");
        }
        enterprise.setCreateBy(SecurityUtils.getUsername());
        return toAjax(deptService.insertEnterprise(enterprise));
    }

    /**
     * 修改企业
     */
    @ApiOperation("组织-编辑-更新")
    @PreAuthorize("@ss.hasPermi('system:enterprise:edit')")
    @Log(title = "企业管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysEnterprise enterprise) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkEnterpriseNameUnique(enterprise))) {
            return AjaxResult.error("修改企业'" + enterprise.getEnterpriseName() + "'失败，企业名称已存在");
        } else if (enterprise.getParentId().equals(enterprise.getEnterpriseId())) {
            return AjaxResult.error("修改企业'" + enterprise.getEnterpriseName() + "'失败，上级企业不能是自己");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, enterprise.getStatus())
                && deptService.selectNormalChildrenEnterpriseById(enterprise.getEnterpriseId()) > 0) {
            return AjaxResult.error("该企业包含未停用的子企业！");
        }
        enterprise.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(deptService.updateEnterprise(enterprise));
    }

    /**
     * 删除企业
     */
    @ApiOperation("组织-编辑-公司删除")
    @PreAuthorize("@ss.hasPermi('system:enterprise:remove')")
    @Log(title = "企业管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{enterpriseId}")
    public AjaxResult remove(@PathVariable Long enterpriseId) {
        if (deptService.hasChildByEnterpriseId(enterpriseId)) {
            return AjaxResult.error("存在下级企业,不允许删除");
        }
//        if (deptService.checkEnterpriseExistUser(enterpriseId)) {
//            return AjaxResult.error("企业存在用户,不允许删除");
//        }
        return toAjax(deptService.deleteEnterpriseById(enterpriseId));
    }

    /**
     * 登陆后，选择组织
     */
    @PostMapping(value = "/organizationSelect")
    @Log(title = "选择组织", businessType = BusinessType.INSERT)
    @ApiOperation("登陆选择组织接口")
    @DynamicParameters(name = "map",properties = {
            @DynamicParameter(name = "organizationId",value = "组织id",example = "1",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "type",value = "组织类型",example = "1",required = true,dataTypeClass = Integer.class),
    })
    public AjaxResult organizationSelect(@RequestBody HashMap map) {
        return toAjax(deptService.organizationSelect(Long.parseLong(map.get("organizationId").toString()),Integer.parseInt(map.get("type").toString())));
    }

    /**
     * 塔机、升降机 企业级树
     * 查询当前公司下所有公司和项目
     *
     * @param companyId 公司ID
     * @return 组织树
     */
    @ApiOperation("获取组织项目树列表")
    @GetMapping("/companyTreeselect")
    public AjaxResult companyTreeselect(@PathVariable Long companyId) {
        return AjaxResult.success(deptService.selectOrgListByOrgId(companyId));
    }
}
