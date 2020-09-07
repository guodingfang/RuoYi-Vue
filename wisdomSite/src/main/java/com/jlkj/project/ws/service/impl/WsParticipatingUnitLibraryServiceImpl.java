package com.jlkj.project.ws.service.impl;

import com.jlkj.common.constant.Constants;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.ServletUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.spring.SpringUtils;
import com.jlkj.framework.redis.RedisCache;
import com.jlkj.framework.security.LoginUser;
import com.jlkj.framework.security.service.TokenService;
import com.jlkj.project.system.domain.SysUser;
import com.jlkj.project.ws.domain.WsParticipatingUnitLibrary;
import com.jlkj.project.ws.domain.vo.WsParticipatingUnitLibraryVO;
import com.jlkj.project.ws.mapper.WsParticipatingUnitLibraryMapper;
import com.jlkj.project.ws.service.IWsParticipatingUnitLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参建单位库Service业务层处理
 *
 * @author jlkj
 * @date 2020-06-12
 */
@Service
public class WsParticipatingUnitLibraryServiceImpl implements IWsParticipatingUnitLibraryService
{
    @Autowired
    private WsParticipatingUnitLibraryMapper wsParticipatingUnitLibraryMapper;

    /**
     * 查询参建单位库
     *
     * @param id 参建单位库ID
     * @return 参建单位库
     */
    @Override
    public WsParticipatingUnitLibrary selectWsParticipatingUnitLibraryById(Integer id)
    {
        return wsParticipatingUnitLibraryMapper.selectWsParticipatingUnitLibraryById(id);
    }

    /**
     * 查询参建单位库列表
     *
     * @param wsParticipatingUnitLibrary 参建单位库
     * @return 参建单位库
     */
    @Override
    public List<WsParticipatingUnitLibrary> selectWsParticipatingUnitLibraryList(WsParticipatingUnitLibrary wsParticipatingUnitLibrary)
    {
        wsParticipatingUnitLibrary.setEnterpriseId(getGroupEnterpriseId());
        return wsParticipatingUnitLibraryMapper.selectWsParticipatingUnitLibraryList(wsParticipatingUnitLibrary);
    }

    private Long getGroupEnterpriseId() {

        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        String token = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest()).getToken();
        LoginUser loginUser = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY + token);
        if(StringUtils.isNull(loginUser)) throw new CustomException("无数据权限！");
        SysUser user = loginUser.getUser();
        if(StringUtils.isNull(loginUser)) throw new CustomException("无数据权限！");
        return user.getEnterpriseId();
    }

    /**
     * 新增参建单位库
     *
     * @param wsParticipatingUnitLibrary 参建单位库
     * @return 结果
     */
    @Override
    public int insertWsParticipatingUnitLibrary(WsParticipatingUnitLibrary wsParticipatingUnitLibrary)
    {
        wsParticipatingUnitLibrary.setEnterpriseId(getGroupEnterpriseId());
        return wsParticipatingUnitLibraryMapper.insertWsParticipatingUnitLibrary(wsParticipatingUnitLibrary);
    }

    /**
     * 修改参建单位库
     *
     * @param wsParticipatingUnitLibrary 参建单位库
     * @return 结果
     */
    @Override
    public int updateWsParticipatingUnitLibrary(WsParticipatingUnitLibrary wsParticipatingUnitLibrary)
    {
        return wsParticipatingUnitLibraryMapper.updateWsParticipatingUnitLibrary(wsParticipatingUnitLibrary);
    }

    /**
     * 批量删除参建单位库
     *
     * @param ids 需要删除的参建单位库ID
     * @return 结果
     */
    @Override
    public int deleteWsParticipatingUnitLibraryByIds(Integer[] ids)
    {
        return wsParticipatingUnitLibraryMapper.deleteWsParticipatingUnitLibraryByIds(ids);
    }

    /**
     * 删除参建单位库信息
     *
     * @param id 参建单位库ID
     * @return 结果
     */
    @Override
    public int deleteWsParticipatingUnitLibraryById(Integer id)
    {
        return wsParticipatingUnitLibraryMapper.deleteWsParticipatingUnitLibraryById(id);
    }

    @Override
    public List<WsParticipatingUnitLibraryVO> statisticsProjectParticipatingUnitHistoryRecord(WsParticipatingUnitLibrary wsParticipatingUnitLibrary) {

        return wsParticipatingUnitLibraryMapper.statisticsProjectParticipatingUnitHistoryRecord(wsParticipatingUnitLibrary);
    }
}
