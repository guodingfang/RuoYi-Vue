package com.jlkj.project.ws.controller;

import java.util.Date;
import java.util.List;

import com.jlkj.common.constant.Constants;
import com.jlkj.common.utils.ServletUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.spring.SpringUtils;
import com.jlkj.framework.redis.RedisCache;
import com.jlkj.framework.security.LoginUser;
import com.jlkj.framework.security.service.TokenService;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jlkj.framework.aspectj.lang.annotation.Log;
import com.jlkj.framework.aspectj.lang.enums.BusinessType;
import com.jlkj.project.ws.domain.WsQualityAcceptanceApply;
import com.jlkj.project.ws.service.IWsQualityAcceptanceApplyService;
import com.jlkj.framework.web.controller.BaseController;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.common.utils.poi.ExcelUtil;
import com.jlkj.framework.web.page.TableDataInfo;

/**
 * 质量验收申请 Controller
 *
 * @author gaowei
 * @date 2020-06-09
 */
@Api(value = "质量验收申请接口",tags = "质量验收申请页面")
@RestController
@RequestMapping("/ws/apply")
public class WsQualityAcceptanceApplyController extends BaseController
{
    @Autowired
    private IWsQualityAcceptanceApplyService wsQualityAcceptanceApplyService;

    /**
     * 查询质量验收申请 列表
     */
    @ApiOperation("质量验收列表")
    @ApiImplicitParam(name = "wsQualityAcceptanceApply", value = "质量验收实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:apply:list')")
    @GetMapping("/list")
    public TableDataInfo list(WsQualityAcceptanceApply wsQualityAcceptanceApply)
    {
        startPage();
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        String token = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest()).getToken();
        LoginUser loginUser = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY + token);
        if(wsQualityAcceptanceApply.getAcceptanceState() != null && wsQualityAcceptanceApply.getAcceptanceState().equals(3)){
            wsQualityAcceptanceApply.setAcceptancePersonId(loginUser.getUser().getUserId().intValue());
        }
        if(wsQualityAcceptanceApply.getAcceptanceState() != null && wsQualityAcceptanceApply.getAcceptanceState().equals(2)){
            wsQualityAcceptanceApply.setApplyPersonId(loginUser.getUser().getUserId().intValue());
        }
        List<WsQualityAcceptanceApply> list = wsQualityAcceptanceApplyService.selectWsQualityAcceptanceApplyList(wsQualityAcceptanceApply);
        return getDataTable(list);
    }

    /**
     * 导出质量验收申请 列表
     */
    @ApiOperation("导出质量验收列表")
    @ApiImplicitParam(name = "wsQualityAcceptanceApply", value = "质量验收实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:apply:export')")
    @Log(title = "质量验收申请 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WsQualityAcceptanceApply wsQualityAcceptanceApply)
    {
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        String token = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest()).getToken();
        LoginUser loginUser = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY + token);
        if(wsQualityAcceptanceApply.getAcceptanceState() != null && wsQualityAcceptanceApply.getAcceptanceState().equals(3)){
            wsQualityAcceptanceApply.setAcceptancePersonId(loginUser.getUser().getUserId().intValue());
        }
        if(wsQualityAcceptanceApply.getAcceptanceState() != null && wsQualityAcceptanceApply.getAcceptanceState().equals(2)){
            wsQualityAcceptanceApply.setApplyPersonId(loginUser.getUser().getUserId().intValue());
        }
        List<WsQualityAcceptanceApply> list = wsQualityAcceptanceApplyService.selectWsQualityAcceptanceApplyList(wsQualityAcceptanceApply);
        ExcelUtil<WsQualityAcceptanceApply> util = new ExcelUtil<WsQualityAcceptanceApply>(WsQualityAcceptanceApply.class);
        return util.exportExcel(list, "apply");
    }

    /**
     * 获取申请质量验收 详细信息
     */
    @ApiOperation("质量验收详细信息")
    @ApiImplicitParam(name = "wsQualityAcceptanceApply", value = "质量验收实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:apply:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(wsQualityAcceptanceApplyService.selectWsQualityAcceptanceApplyById(id));
    }

    /**
     * 新增质量验收申请 
     */
    @ApiOperation("质量验收申请")
    @ApiImplicitParam(name = "wsQualityAcceptanceApply", value = "质量验收实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:apply:add')")
    @Log(title = "质量验收申请 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WsQualityAcceptanceApply wsQualityAcceptanceApply)
    {
        return toAjax(wsQualityAcceptanceApplyService.insertWsQualityAcceptanceApply(wsQualityAcceptanceApply));
    }

    /**
     * 修改质量验收申请 
     */
/*    @PreAuthorize("@ss.hasPermi('ws:apply:edit')")
    @Log(title = "质量验收申请 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WsQualityAcceptanceApply wsQualityAcceptanceApply)
    {
        return toAjax(wsQualityAcceptanceApplyService.updateWsQualityAcceptanceApply(wsQualityAcceptanceApply));
    }*/

    /**
     * 再次质量验收申请
     */
    @ApiOperation("再次质量验收申请")
    @ApiImplicitParam(name = "wsQualityAcceptanceApply", value = "质量验收实体项",required = false)
    @PreAuthorize("@ss.hasPermi('ws:apply:editAgain')")
    @Log(title = "再次质量验收申请 ", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult editAgain(@RequestBody WsQualityAcceptanceApply wsQualityAcceptanceApply)
    {
        WsQualityAcceptanceApply apply = wsQualityAcceptanceApplyService.selectWsQualityAcceptanceApplyById(wsQualityAcceptanceApply.getId());
        int num = 0;
        if(apply.getApplyNum() != 0){
            num = apply.getApplyNum();
        }
        wsQualityAcceptanceApply.setApplyNum(num + 1);
        wsQualityAcceptanceApply.setUpdatedTime(new Date());
        wsQualityAcceptanceApply.setAcceptanceState(3);//待验收
        return toAjax(wsQualityAcceptanceApplyService.updateWsQualityAcceptanceApply(wsQualityAcceptanceApply));
    }

    /**
     * 删除质量验收申请 
     */
    @PreAuthorize("@ss.hasPermi('ws:apply:remove')")
    @Log(title = "质量验收申请 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wsQualityAcceptanceApplyService.deleteWsQualityAcceptanceApplyByIds(ids));
    }
}
