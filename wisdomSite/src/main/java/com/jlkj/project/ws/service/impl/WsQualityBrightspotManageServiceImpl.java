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
import com.jlkj.project.ws.mapper.WsQualityBrightspotManageMapper;
import com.jlkj.project.ws.domain.WsQualityBrightspotManage;
import com.jlkj.project.ws.service.IWsQualityBrightspotManageService;

/**
 * 亮点管理 Service业务层处理
 *
 * @author gaowei
 * @date 2020-06-11
 */
@Service
public class WsQualityBrightspotManageServiceImpl implements IWsQualityBrightspotManageService
{
    @Autowired
    private WsQualityBrightspotManageMapper wsQualityBrightspotManageMapper;

    @Autowired
    private IWsProjectService wsProjectService;

    @Autowired
    private WsQualityCheckPhotoMapper wsQualityCheckPhotoMapper;

    /**
     * 查询亮点管理 
     *
     * @param id 亮点管理 ID
     * @return 亮点管理 
     */
    @Override
    public WsQualityBrightspotManage selectWsQualityBrightspotManageById(Integer id)
    {
        WsQualityBrightspotManage brightspotManage = wsQualityBrightspotManageMapper.selectWsQualityBrightspotManageById(id);
        WsQualityCheckPhoto qualityCheckPhoto = new WsQualityCheckPhoto();
        qualityCheckPhoto.setProjectId(brightspotManage.getProjectId());
        qualityCheckPhoto.setCheckManagerId(brightspotManage.getId());
        qualityCheckPhoto.setQualityType(5);
        List<WsQualityCheckPhoto> brightspotPhoto = wsQualityCheckPhotoMapper.selectWsQualityCheckPhotoByCheckManagerId(qualityCheckPhoto);
        List<Object> addres = new ArrayList<Object>();
        HashMap<String, String> param = new HashMap<String, String>();
        if(brightspotPhoto.size() > 0){
            for (int i = 0; i < brightspotPhoto.size(); i++) {
                param.put("imgurl",brightspotPhoto.get(i).getPhotoAddres());
                addres.add(param);
                param=new HashMap();
            }
        }
        brightspotManage.setBrightspotImageAddres(addres);
        return brightspotManage;
    }

    /**
     * 查询亮点管理 列表
     *
     * @param wsQualityBrightspotManage 亮点管理 
     * @return 亮点管理 
     */
    @Override
    public List<WsQualityBrightspotManage> selectWsQualityBrightspotManageList(WsQualityBrightspotManage wsQualityBrightspotManage)
    {
        wsQualityBrightspotManage.setIsDelete("N");
        Integer[] projectIds = null;
        if(wsQualityBrightspotManage.getProjectId() == null){
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
        return wsQualityBrightspotManageMapper.selectWsQualityBrightspotManageList(wsQualityBrightspotManage,projectIds);
    }

    /**
     * 新增亮点管理 
     *
     * @param wsQualityBrightspotManage 亮点管理 
     * @return 结果
     */
    @Override
    public int insertWsQualityBrightspotManage(WsQualityBrightspotManage wsQualityBrightspotManage)
    {
        WsProject wsProject = new WsProject();
        List<WsProject> list = wsProjectService.selectWsProjectList(wsProject);
        if(list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                wsQualityBrightspotManage.setProjectId(list.get(i).getId());
            }
        }
        wsQualityBrightspotManage.setIsDelete("N");
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        String token = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest()).getToken();
        LoginUser loginUser = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY + token);
        Integer userId = Integer.valueOf(loginUser.getUser().getUserId().toString());
        wsQualityBrightspotManage.setUploadPersonId(userId);
        wsQualityBrightspotManage.setCreatedTime(new Date());
        wsQualityBrightspotManage.setUpdatedTime(new Date());
        wsQualityBrightspotManage.setUploadDate(new Date());
        int brightspot = wsQualityBrightspotManageMapper.insertWsQualityBrightspotManage(wsQualityBrightspotManage);
        List<Object> brightspotImageAddres = wsQualityBrightspotManage.getBrightspotImageAddres();
        if(brightspotImageAddres != null){
            for (int i = 0; i < brightspotImageAddres.size(); i++) {
                String json = JSON.toJSONString(brightspotImageAddres.get(i));
                JSONObject object=JSONObject.parseObject(json);
                WsQualityCheckPhoto qualityCheckPhoto = new WsQualityCheckPhoto();
                qualityCheckPhoto.setProjectId(wsQualityBrightspotManage.getProjectId());
                qualityCheckPhoto.setBrightspotId(wsQualityBrightspotManage.getId());
                qualityCheckPhoto.setQualityType(5);
                qualityCheckPhoto.setPhotoAddres(object.getString("url"));
                qualityCheckPhoto.setCreatedTime(new Date());
                qualityCheckPhoto.setUpdatedTime(new Date());
                wsQualityCheckPhotoMapper.insertWsQualityCheckPhoto(qualityCheckPhoto);
            }
        }
        return brightspot;
    }

    /**
     * 修改亮点管理 
     *
     * @param wsQualityBrightspotManage 亮点管理 
     * @return 结果
     */
    @Override
    public int updateWsQualityBrightspotManage(WsQualityBrightspotManage wsQualityBrightspotManage)
    {
        wsQualityBrightspotManage.setUpdatedTime(new Date());
        return wsQualityBrightspotManageMapper.updateWsQualityBrightspotManage(wsQualityBrightspotManage);
    }

    /**
     * 批量删除亮点管理 
     *
     * @param ids 需要删除的亮点管理 ID
     * @return 结果
     */
    @Override
    public int deleteWsQualityBrightspotManageByIds(Integer[] ids)
    {
        return wsQualityBrightspotManageMapper.deleteWsQualityBrightspotManageByIds(ids);
    }

    /**
     * 删除亮点管理 信息
     *
     * @param id 亮点管理 ID
     * @return 结果
     */
    @Override
    public int deleteWsQualityBrightspotManageById(Integer id)
    {
        return wsQualityBrightspotManageMapper.deleteWsQualityBrightspotManageById(id);
    }
}
