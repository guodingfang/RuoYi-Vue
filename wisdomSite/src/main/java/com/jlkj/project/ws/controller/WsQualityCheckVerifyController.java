package com.jlkj.project.ws.controller;

import java.util.List;

import com.jlkj.common.constant.Constants;
import com.jlkj.common.utils.ServletUtils;
import com.jlkj.common.utils.spring.SpringUtils;
import com.jlkj.framework.redis.RedisCache;
import com.jlkj.framework.security.LoginUser;
import com.jlkj.framework.security.service.TokenService;
import com.jlkj.project.ws.domain.WsQualityCheckManager;
import com.jlkj.project.ws.service.IWsQualityCheckManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.project.ws.domain.WsQualityCheckVerify;
import com.jlkj.project.ws.service.IWsQualityCheckVerifyService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 质量检查验证 Controller
 *
 * @author gaowei
 * @date 2020-06-09
 */
@Api(value = "质量检查验证接口",tags = "质量检查验证页面")
@RestController
@RequestMapping("/ws/verify")
public class WsQualityCheckVerifyController extends BaseController
{
    @Autowired
    private IWsQualityCheckVerifyService wsQualityCheckVerifyService;

    @Autowired
    private IWsQualityCheckManagerService wsQualityCheckManagerService;

    /**
     * 查询质量检查列表
     */
    @ApiOperation("质量待验证列表")
    @ApiImplicitParam(name = "wsQualityCheckManager", value = "质量检查实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:verify:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsQualityCheckManager wsQualityCheckManager)
    {
        startPage();
        long status = 2;
        wsQualityCheckManager.setStatus(status);
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        String token = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest()).getToken();
        LoginUser loginUser = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY + token);
        wsQualityCheckManager.setVerifyPersonId(loginUser.getUser().getUserId());
        List<WsQualityCheckManager> list = wsQualityCheckManagerService.selectWsQualityCheckManagerList(wsQualityCheckManager);
        return getDataTable(list);
    }

    /**
     * 新增质量检查验证 
     */
    @ApiOperation("新增质量检查验证")
    @ApiImplicitParam(name = "wsQualityCheckVerify", value = "质量检查验证实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:verify:add')")
    @Log(title = "质量检查验证 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsQualityCheckVerify wsQualityCheckVerify)
    {
        return toAjax(wsQualityCheckVerifyService.insertWsQualityCheckVerify(wsQualityCheckVerify));
    }


}
