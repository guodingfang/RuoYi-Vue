package com.jlkj.project.ws.service.impl;

import java.util.Date;
import java.util.List;

import com.jlkj.common.constant.Constants;
import com.jlkj.common.constant.UserConstants;
import com.jlkj.common.utils.ServletUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.spring.SpringUtils;
import com.jlkj.framework.redis.RedisCache;
import com.jlkj.framework.security.LoginUser;
import com.jlkj.framework.security.service.TokenService;
import com.jlkj.project.ws.domain.WsQualityCheckManager;
import com.jlkj.project.ws.mapper.WsQualityCheckManagerMapper;
import com.jlkj.project.ws.service.IWsQualityProblemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsQualityProblemTypeMapper;
import com.jlkj.project.ws.domain.WsQualityProblemType;

/**
 * 质量问题管理 Service业务层处理
 *
 * @author gaowei
 * @date 2020-06-09
 */
@Service
public class WsQualityProblemTypeServiceImpl implements IWsQualityProblemTypeService
{
    @Autowired
    private WsQualityProblemTypeMapper wsQualityProblemTypeMapper;

    @Autowired
    private WsQualityCheckManagerMapper wsQualityCheckManagerMapper;

    /**
     * 查询质量问题管理 
     *
     * @param id 质量问题管理 ID
     * @return 质量问题管理 
     */
    @Override
    public WsQualityProblemType selectWsQualityProblemTypeById(Long id)
    {
        return wsQualityProblemTypeMapper.selectWsQualityProblemTypeById(id);
    }

    /**
     * 查询质量问题管理 列表
     *
     * @param wsQualityProblemType 质量问题管理 
     * @return 质量问题管理 
     */
    @Override
    public List<WsQualityProblemType> selectWsQualityProblemTypeList(WsQualityProblemType wsQualityProblemType)
    {
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        String token = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest()).getToken();
        LoginUser loginUser = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY + token);
        wsQualityProblemType.setGroupId(loginUser.getUser().getEnterpriseId());
        return wsQualityProblemTypeMapper.selectWsQualityProblemTypeList(wsQualityProblemType);
    }

    /**
     * 新增质量问题管理 
     *
     * @param wsQualityProblemType 质量问题管理 
     * @return 结果
     */
    @Override
    public int insertWsQualityProblemType(WsQualityProblemType wsQualityProblemType)
    {
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        String token = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest()).getToken();
        LoginUser loginUser = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY + token);
        wsQualityProblemType.setGroupId(loginUser.getUser().getEnterpriseId());
        wsQualityProblemType.setIsDelete("N");
        wsQualityProblemType.setCreatedTime(new Date());
        wsQualityProblemType.setUpdatedTime(new Date());
        return wsQualityProblemTypeMapper.insertWsQualityProblemType(wsQualityProblemType);
    }

    /**
     * 修改质量问题管理 
     *
     * @param wsQualityProblemType 质量问题管理 
     * @return 结果
     */
    @Override
    public int updateWsQualityProblemType(WsQualityProblemType wsQualityProblemType)
    {
        wsQualityProblemType.setUpdatedTime(new Date());
        return wsQualityProblemTypeMapper.updateWsQualityProblemType(wsQualityProblemType);
    }

    /**
     * 批量删除质量问题管理 
     *
     * @param ids 需要删除的质量问题管理 ID
     * @return 结果
     */
    @Override
    public int deleteWsQualityProblemTypeByIds(Long[] ids)
    {
        return wsQualityProblemTypeMapper.deleteWsQualityProblemTypeByIds(ids);
    }

    /**
     * 删除质量问题管理 信息
     *
     * @param id 质量问题管理 ID
     * @return 结果
     */
    @Override
    public int deleteWsQualityProblemTypeById(Long id)
    {
        return wsQualityProblemTypeMapper.deleteWsQualityProblemTypeById(id);
    }

    /**
     * 校验质量问题名称是否唯一
     *
     * @param wsQualityProblemType 质量问题
     * @return
     */
    @Override
    public String checkNameType(WsQualityProblemType wsQualityProblemType)
    {
        Long problemTypeId = StringUtils.isNull(wsQualityProblemType.getId()) ? -1L : wsQualityProblemType.getId();
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        String token = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest()).getToken();
        LoginUser loginUser = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY + token);
        WsQualityProblemType problemType = wsQualityProblemTypeMapper.checkNameType(wsQualityProblemType.getProblemTypeName(),loginUser.getUser().getEnterpriseId());
        if (StringUtils.isNotNull(problemType) && problemType.getId().longValue() != problemTypeId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public List<WsQualityCheckManager> selectWsQualityProblemTypeByIdList(Long qualityProblemId) {
        return wsQualityCheckManagerMapper.selectWsQualityProblemTypeByIdList(qualityProblemId);
    }
}
