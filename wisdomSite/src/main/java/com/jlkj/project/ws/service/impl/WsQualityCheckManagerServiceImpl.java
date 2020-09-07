package com.jlkj.project.ws.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.constant.Constants;
import com.jlkj.common.utils.ServletUtils;
import com.jlkj.common.utils.spring.SpringUtils;
import com.jlkj.framework.redis.RedisCache;
import com.jlkj.framework.security.LoginUser;
import com.jlkj.framework.security.service.TokenService;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsQualityCheckPhoto;
import com.jlkj.project.ws.mapper.WsQualityCheckPhotoMapper;
import com.jlkj.project.ws.service.IWsProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.project.ws.mapper.WsQualityCheckManagerMapper;
import com.jlkj.project.ws.domain.WsQualityCheckManager;
import com.jlkj.project.ws.service.IWsQualityCheckManagerService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 质量检查Service业务层处理
 *
 * @author gaowei
 * @date 2020-06-09
 */
@Service
public class WsQualityCheckManagerServiceImpl implements IWsQualityCheckManagerService
{
    @Autowired
    private WsQualityCheckManagerMapper wsQualityCheckManagerMapper;

    @Autowired
    private WsQualityCheckPhotoMapper wsQualityCheckPhotoMapper;

    @Autowired
    private IWsProjectService wsProjectService;


    /**
     * 查询质量检查
     *
     * @param id 质量检查ID
     * @return 质量检查
     */
    @Override
    public WsQualityCheckManager selectWsQualityCheckManagerById(int id)
    {
        WsQualityCheckManager checkManager = wsQualityCheckManagerMapper.selectWsQualityCheckManagerById(id);
        WsQualityCheckPhoto qualityCheckPhoto = new WsQualityCheckPhoto();
        qualityCheckPhoto.setProjectId(checkManager.getProjectId());
        qualityCheckPhoto.setCheckManagerId(checkManager.getId());
        qualityCheckPhoto.setQualityType(1);
        List<WsQualityCheckPhoto> checkPhoto = wsQualityCheckPhotoMapper.selectWsQualityCheckPhotoByCheckManagerId(qualityCheckPhoto);
        List<Object> addres = new ArrayList<Object>();
        HashMap<String, String> param = new HashMap<String, String>();
        if(checkPhoto.size() > 0){
            for (int i = 0; i < checkPhoto.size(); i++) {
                param.put("imgurl",checkPhoto.get(i).getPhotoAddres());
                addres.add(param);
                param=new HashMap();
            }
        }

        checkManager.setCheckPositionAddres(addres);
        return checkManager;
    }


    /**
     * 查询质量检查列表
     *
     * @param wsQualityCheckManager 质量检查
     * @return 质量检查
     */
    @Override
    public List<WsQualityCheckManager> selectWsQualityCheckManagerList(WsQualityCheckManager wsQualityCheckManager)
    {
        Integer[] projectIds = null;
        if(wsQualityCheckManager.getProjectId() == null){
            WsProject wsProject = new WsProject();
            List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
            if(list.size() > 0) {
                LinkedList<Integer> linkedlists = new LinkedList<Integer>();
                for (int i = 0; i < list.size(); i++) {
                    linkedlists.add(list.get(i).getId());
                }
                projectIds = linkedlists.toArray(new Integer[linkedlists.size()]);
            }
        }
        return wsQualityCheckManagerMapper.selectWsQualityCheckManagerList(wsQualityCheckManager,projectIds);
    }

    /**
     * 新增质量检查
     *
     * @param wsQualityCheckManager 质量检查
     * @return 结果
     */
    @Override
    @Transactional
    public int insertWsQualityCheckManager(WsQualityCheckManager wsQualityCheckManager)
    {
        wsQualityCheckManager.setIsDelete("N");
        wsQualityCheckManager.setCreatedTime(new Date());
        wsQualityCheckManager.setUpdatedTime(new Date());
        long status = 1;
        wsQualityCheckManager.setStatus(status);
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0) {
            LinkedList<Integer> linkedlists = new LinkedList<Integer>();
            for (int i = 0; i < list.size(); i++) {
                linkedlists.add(list.get(i).getId());
                wsQualityCheckManager.setProjectId(list.get(i).getId());
            }
        }
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        String token = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest()).getToken();
        LoginUser loginUser = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY + token);
        wsQualityCheckManager.setCheckPositionId(loginUser.getUser().getUserId());
        wsQualityCheckManager.setCheckDate(new Date());

        int checkManager = wsQualityCheckManagerMapper.insertWsQualityCheckManager(wsQualityCheckManager);
        List<Object> checkPositionAddres = wsQualityCheckManager.getCheckPositionAddres();
        if(checkPositionAddres != null){
            for (int i = 0; i < checkPositionAddres.size(); i++) {
                String json = JSON.toJSONString(checkPositionAddres.get(i));
                JSONObject object=JSONObject.parseObject(json);
                WsQualityCheckPhoto qualityCheckPhoto = new WsQualityCheckPhoto();
                qualityCheckPhoto.setProjectId(wsQualityCheckManager.getProjectId());
                qualityCheckPhoto.setCheckManagerId(wsQualityCheckManager.getId());
                qualityCheckPhoto.setQualityType(1);
                qualityCheckPhoto.setPhotoAddres(object.getString("imgurl"));
                qualityCheckPhoto.setCreatedTime(new Date());
                qualityCheckPhoto.setUpdatedTime(new Date());
                wsQualityCheckPhotoMapper.insertWsQualityCheckPhoto(qualityCheckPhoto);
            }
        }
        return checkManager;
    }

    /**
     * 修改质量检查
     *
     * @param wsQualityCheckManager 质量检查
     * @return 结果
     */
    @Override
    public int updateWsQualityCheckManager(WsQualityCheckManager wsQualityCheckManager)
    {
        return wsQualityCheckManagerMapper.updateWsQualityCheckManager(wsQualityCheckManager);
    }

    /**
     * 批量删除质量检查
     *
     * @param ids 需要删除的质量检查ID
     * @return 结果
     */
    @Override
    public int deleteWsQualityCheckManagerByIds(Long[] ids)
    {
        return wsQualityCheckManagerMapper.deleteWsQualityCheckManagerByIds(ids);
    }

    /**
     * 删除质量检查信息
     *
     * @param id 质量检查ID
     * @return 结果
     */
    @Override
    public int deleteWsQualityCheckManagerById(Long id)
    {
        return wsQualityCheckManagerMapper.deleteWsQualityCheckManagerById(id);
    }
}
