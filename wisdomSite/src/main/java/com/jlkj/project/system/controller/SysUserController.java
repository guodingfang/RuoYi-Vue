package com.jlkj.project.system.controller;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.jlkj.common.constant.UserConstants;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.ServletUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.common.utils.spring.SpringUtils;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.framework.security.LoginUser;
import com.jlkj.framework.security.service.TokenService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.project.system.domain.SysEnterprise;
import com.jlkj.project.system.domain.SysOrganizationAccount;
import com.jlkj.project.system.domain.SysUser;
import com.jlkj.project.system.service.ISysDeptService;
import com.jlkj.project.system.service.ISysPostService;
import com.jlkj.project.system.service.ISysRoleService;
import com.jlkj.project.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * 用户信息
 *
 * @author jlkj
 */
@Api(value = "组织人员接口", tags = {"组织接口"})
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysDeptService enterpriseService;

    /**
     * 获取用户列表
     */
    @ApiOperation("组织-账号接口，组织对应人员，集团人员")
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    @DynamicParameters(name = "user", properties = {
            @DynamicParameter(name = "organizationId", value = "组织id", example = "1", required = false, dataTypeClass = Integer.class),
            @DynamicParameter(name = "type", value = "组织类型", example = "1", required = false, dataTypeClass = Integer.class),
            @DynamicParameter(name = "name", value = "姓名/手机号", example = "18326079417", required = false, dataTypeClass = String.class),
            @DynamicParameter(name = "enterpriseId", value = "集团ID", example = "1", required = false, dataTypeClass = Integer.class),
    })
    public TableDataInfo list(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

//    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('system:user:export')")
//    @GetMapping("/export")
//    public AjaxResult export(SysUser user)
//    {
//        List<SysUser> list = userService.selectUserList(user);
//        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
//        return util.exportExcel(list, "用户数据");
//    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.importTemplateExcel("用户数据");
    }

    /**
     * 根据用户编号获取详细信息
     */
    @ApiOperation("组织-编辑-获取用户回显")
    @ApiParam(name = "userId", value = "1", required = true)
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = {"/", "/{userId}"})
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("roles", roleService.selectRoleAll());
        if (StringUtils.isNotNull(userId)) {
            ajax.put(AjaxResult.DATA_TAG, userService.selectUserById(userId));
            ajax.put("roleIds", roleService.selectRoleListByUserId(userId));
        }
        return ajax;
    }

    /**
     * 新增用户
     */
    @ApiOperation("组织-新增成员-保存")
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user) {
        if (StringUtils.isNull(user.getUserId())&&UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getPhonenumber() + "'失败，登录账号/手机号已存在");
        }
        if (UserConstants.NOT_UNIQUE.equals(userService.checkOrgUser(user)))
        {
            return AjaxResult.error("新增用户'" + user.getPhonenumber() + "'失败，账户已存在该组织架构中。请勿重复添加");
        }
        return toAjax(userService.saveUser(user));
    }

    /**
     * 修改用户
     */
    @ApiOperation("企业用户-保存")
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
//        else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
//            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
//        }
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.updateUser(user));
    }

    /**
     * 重置密码
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.updateUserStatus(user));
    }


    /**
     * 选择已有成员
     */
    @ApiOperation("组织-选择已有成员")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/selectAlreadyHave")
    @DynamicParameters(name = "map", properties = {
            @DynamicParameter(name = "organizationId", value = "组织id", example = "1", required = true, dataTypeClass = Integer.class),
            @DynamicParameter(name = "type", value = "组织类型", example = "1", required = true, dataTypeClass = Integer.class),
            @DynamicParameter(name = "accountIds", value = "用户IDs", example = "“1,2,3,4”", required = true, dataTypeClass = String.class),
    })
    public AjaxResult selectAlreadyHave(@RequestBody HashMap map) {
        return toAjax(userService.selectAlreadyHave(Integer.parseInt(map.get("organizationId").toString()), Integer.parseInt(map.get("type").toString()), map.get("accountIds").toString()));
    }

    /**
     * 添加成员-根据手机号查询用户信息
     */
    @ApiOperation("组织-添加成员-根据手机号查询用户信息，返回信息：user是用户信息，roleIds是角色ID集合 类型：List<Integer>")
    @GetMapping(value = {"/getUserInfo"})
    public AjaxResult getUserInfo(@ApiParam(name = "phonenumber", value = "手机号", required = false) String phonenumber) {
        AjaxResult ajax = AjaxResult.success();
        SysUser user = userService.selectUserByPhonenumber(phonenumber);
        //新增的用户已经存在的情况
        if (StringUtils.isNotNull(user)) {
            LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
            SysUser currentUser = loginUser.getUser();
            //新增的用户集团ID和当前集团ID不一致
            if (!user.getEnterpriseId().equals(currentUser.getEnterpriseId())) {
                return AjaxResult.error("该用户'" + user.getUserName() + "'已绑定其他集团，请先解绑！");
            }
            ajax.put("roleIds", roleService.selectRoleListByUserId(user.getUserId()));
            ajax.put("user", user);
        }
        return ajax;
    }


    /**
     * 删除用户
     */
    @ApiOperation("组织-移除/删除 用户与组织的关系")
    @DynamicParameters(name = "sysOrganizationAccount", properties = {
            @DynamicParameter(name = "accountids", value = "用户IDs", example = "[1,2,3,4]", required = true, dataTypeClass = Long.class),
            @DynamicParameter(name = "organizationId", value = "组织ID", example = "1", required = true, dataTypeClass = Long.class),
    })
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/removeOrgUser")
    public AjaxResult removeOrgUser(@RequestBody SysOrganizationAccount sysOrganizationAccount) {
        return toAjax(userService.removeOrgUser(sysOrganizationAccount));
    }

    /**
     * 根据用户编号获取详细信息
     */
    @ApiOperation("组织-编辑-企业用户-点击回显组织角色&角色权限")
    @ApiParam(name = "userId", value = "1", required = true)
    @GetMapping(value = {"/getOrgUserInfo/{userId}"})
    public AjaxResult getOrgUserInfo(@PathVariable(value = "userId", required = true) Long userId) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("roles", roleService.selectRoleAll());
        ajax.put(AjaxResult.DATA_TAG, userService.selectUserById(userId));
        ajax.put("roleIds", roleService.selectRoleListByUserId(userId));
        List<SysOrganizationAccount> orgNames = enterpriseService.getOrgNames(userId);
        ajax.put("orgNames",orgNames);
        return ajax;
    }

    /**
     * 删除人员和组织关系
     */
    @ApiOperation("企业用户-组织角色-删除")
    @DeleteMapping(value = {"/removeOrgUser/{organizationAccount}"})
    public AjaxResult getOrgUserInfo(@PathVariable(value = "organizationAccount", required = true) SysOrganizationAccount organizationAccount) {
        return toAjax(userService.deleteOrgUser(organizationAccount));
    }


    /**
     * 校验当前选中人员是否已属于该架构
     */
    @ApiOperation("组织-添加成员-选择已有成员-校验是否已属于该组织")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/checkOrgUser")
    public AjaxResult checkOrgUser(@RequestBody SysUser user) {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkOrgUser(user)))
        {
            return AjaxResult.error("该账户已存在该组织架构中，请勿重复添加");
        }
        return toAjax(1);
    }
}
