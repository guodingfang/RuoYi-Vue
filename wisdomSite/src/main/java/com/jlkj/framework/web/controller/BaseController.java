package com.jlkj.framework.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jlkj.common.constant.Constants;
import com.jlkj.common.constant.HttpStatus;
import com.jlkj.common.utils.DateUtils;
import com.jlkj.common.utils.ServletUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.spring.SpringUtils;
import com.jlkj.common.utils.sql.SqlUtil;
import com.jlkj.framework.redis.RedisCache;
import com.jlkj.framework.security.LoginUser;
import com.jlkj.framework.security.service.TokenService;
import com.jlkj.framework.web.domain.AjaxResult;
import com.jlkj.framework.web.page.PageDomain;
import com.jlkj.framework.web.page.TableDataInfo;
import com.jlkj.framework.web.page.TableSupport;
import com.jlkj.project.system.domain.SysUser;
import com.jlkj.project.ws.domain.WsProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * web层通用数据处理
 *
 * @author jlkj
 */
public class BaseController
{
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /** redis缓存操作对象 */
    public final static RedisCache redisCache=SpringUtils.getBean(RedisCache.class);

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
        * @Author liujie
        * @Description 获取用户登录信息
        * @Date 2020/8/4 15:08
        * @Param []
        * @Return com.jlkj.framework.security.LoginUser
       **/
    protected LoginUser getLoginUser(){

        String token =SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest()).getToken();
        LoginUser loginUser = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY+token);
        return loginUser;
    }

    /**
        * @Author liujie
        * @Description 获取用户信息
        * @Date 2020/8/4 15:12
        * @Param []
        * @Return com.jlkj.project.system.domain.SysUser
       **/
    protected SysUser getSysUser(){
        LoginUser loginUser = getLoginUser();
        return StringUtils.isNull(loginUser)?new SysUser():loginUser.getUser();
    }

    protected Integer[] getProjectScope(){
        SysUser sysUser = getSysUser();
        List<WsProject> projects = sysUser.getProjects();
        Integer[] projectIds = null;
        if(StringUtils.isNotEmpty(projects))
            projectIds = (Integer[]) projects.stream().map(WsProject::getId).collect(Collectors.toList())
                    .stream().toArray(Integer[]::new);
        return projectIds;
    }
}
