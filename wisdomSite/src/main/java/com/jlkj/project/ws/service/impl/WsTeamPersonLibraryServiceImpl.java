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
import com.jlkj.project.ws.domain.WsQualificationCertificate;
import com.jlkj.project.ws.domain.WsTeamPersonLibrary;
import com.jlkj.project.ws.mapper.WsTeamPersonLibraryMapper;
import com.jlkj.project.ws.service.IWsTeamPersonLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 班组人员库Service业务层处理
 *
 * @author jlkj
 * @date 2020-06-12
 */
@Service
public class WsTeamPersonLibraryServiceImpl implements IWsTeamPersonLibraryService
{
    @Autowired
    private WsTeamPersonLibraryMapper wsTeamPersonLibraryMapper;

    /**
     * 查询班组人员库
     *
     * @param id 班组人员库ID
     * @return 班组人员库
     */
    @Override
    public WsTeamPersonLibrary selectWsTeamPersonLibraryById(Integer id)
    {
        return wsTeamPersonLibraryMapper.selectWsTeamPersonLibraryById(id);
    }

    /**
     * 查询班组人员库列表
     *
     * @param wsTeamPersonLibrary 班组人员库
     * @return 班组人员库
     */
    @Override
    public List<WsTeamPersonLibrary> selectWsTeamPersonLibraryList(WsTeamPersonLibrary wsTeamPersonLibrary)
    {
        wsTeamPersonLibrary.setEnterpriseId(getGroupEnterpriseId());
        return wsTeamPersonLibraryMapper.selectWsTeamPersonLibraryList(wsTeamPersonLibrary);
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
     * 新增班组人员库
     *
     * @param wsTeamPersonLibrary 班组人员库
     * @return 结果
     */
    @Override
    public int insertWsTeamPersonLibrary(WsTeamPersonLibrary wsTeamPersonLibrary)
    {
        wsTeamPersonLibrary.setEnterpriseId(getGroupEnterpriseId());
        return wsTeamPersonLibraryMapper.insertWsTeamPersonLibrary(wsTeamPersonLibrary);
    }

    /**
     * 修改班组人员库
     *
     * @param wsTeamPersonLibrary 班组人员库
     * @return 结果
     */
    @Override
    public int updateWsTeamPersonLibrary(WsTeamPersonLibrary wsTeamPersonLibrary)
    {
        return wsTeamPersonLibraryMapper.updateWsTeamPersonLibrary(wsTeamPersonLibrary);
    }

    /**
     * 批量删除班组人员库
     *
     * @param ids 需要删除的班组人员库ID
     * @return 结果
     */
    @Override
    public int deleteWsTeamPersonLibraryByIds(Integer[] ids)
    {
        return wsTeamPersonLibraryMapper.deleteWsTeamPersonLibraryByIds(ids);
    }

    /**
     * 删除班组人员库信息
     *
     * @param id 班组人员库ID
     * @return 结果
     */
    @Override
    public int deleteWsTeamPersonLibraryById(Integer id)
    {
        return wsTeamPersonLibraryMapper.deleteWsTeamPersonLibraryById(id);
    }

    @Override
    public List<WsQualificationCertificate> selectCeitificateListByIdNumber(String idNumber) {
        return wsTeamPersonLibraryMapper.selectCertificateListByIdNumber(idNumber);
    }
}
