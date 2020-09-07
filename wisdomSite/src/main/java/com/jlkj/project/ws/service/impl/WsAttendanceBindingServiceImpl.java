package com.jlkj.project.ws.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.constant.Constants;
import com.jlkj.common.constant.LaborConstants;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.SecurityUtils;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.framework.config.RuoYiConfig;
import com.jlkj.framework.redis.KaoQinInterUtil;
import com.jlkj.framework.redis.ResultData;
import com.jlkj.project.ws.domain.ImageCompress;
import com.jlkj.project.ws.domain.WsAttendanceBinding;
import com.jlkj.project.ws.domain.WsProject;
import com.jlkj.project.ws.domain.WsTeamPerson;
import com.jlkj.project.ws.dto.WsAttendanceBindingDTO;
import com.jlkj.project.ws.mapper.WsAttendanceBindingMapper;
import com.jlkj.project.ws.service.IWsAttendanceBindingService;
import com.jlkj.project.ws.service.IWsProjectService;
import com.jlkj.project.ws.service.IWsTeamPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考勤绑定 Service业务层处理
 *
 * @author gaowei
 * @date 2020-06-23
 */
@Service
public class WsAttendanceBindingServiceImpl implements IWsAttendanceBindingService
{
    @Autowired
    private WsAttendanceBindingMapper wsAttendanceBindingMapper;
    @Autowired
    private IWsProjectService wsProjectService;

    @Autowired
    private IWsTeamPersonService wsTeamPersonService;

    /**
     * 查询考勤绑定
     *
     * @param id 考勤绑定 ID
     * @return 考勤绑定
     */
    @Override
    public WsAttendanceBinding selectWsAttendanceBindingById(Integer id)
    {
        return wsAttendanceBindingMapper.selectWsAttendanceBindingById(id);
    }

    /**
     * 查询考勤绑定 列表
     *
     * @param wsAttendanceBinding 考勤绑定
     * @return 考勤绑定
     */
    @Override
    public List<WsAttendanceBinding> selectWsAttendanceBindingList(WsAttendanceBinding wsAttendanceBinding)
    {
        ResultData resultData = KaoQinInterUtil.getDeviceInfoList();
        JSONArray arr = null;
        Map<String,Integer> onlineMap = new HashMap<>();
        if(LaborConstants.SUCCESS.equals(resultData.getCode())){
            arr = resultData.getData().getJSONObject("data").getJSONArray("content");
            for (int i = 0; i < arr.size(); i++) {
                JSONObject json =arr.getJSONObject(i);
                onlineMap.put(json.getString("deviceKey"),json.getInteger("status"));
            }
        }

        List<WsProject> projectScope = null;
        if(StringUtils.isNull(wsAttendanceBinding)){
            projectScope = SecurityUtils.getProjectList();
        }
        List<WsAttendanceBinding> wsAttendanceBindings = wsAttendanceBindingMapper.selectWsAttendanceBindingList(wsAttendanceBinding,projectScope);
        wsAttendanceBindings.forEach(one -> {
            if (StringUtils.isNotEmpty(onlineMap)&&onlineMap.containsKey(one.getSerialNumber())) {
                one.setOnline(onlineMap.get(one.getSerialNumber())==1?"在线":"离线");
            }else{
                one.setOnline("离线");
            }

        });
        return wsAttendanceBindings;
    }

    /**
     * 新增考勤绑定
     *
     * @param wsAttendanceBinding 考勤绑定
     * @return 结果
     */
    @Override
    public int insertWsAttendanceBinding(WsAttendanceBinding wsAttendanceBinding)
    {
        return wsAttendanceBindingMapper.insertWsAttendanceBinding(wsAttendanceBinding);
    }

    /**
     * 修改考勤绑定
     *
     * @param wsAttendanceBinding 考勤绑定
     * @return 结果
     */
    @Override
    public int updateWsAttendanceBinding(WsAttendanceBinding wsAttendanceBinding)
    {
        return wsAttendanceBindingMapper.updateWsAttendanceBinding(wsAttendanceBinding);
    }

    /**
     * 批量删除考勤绑定
     *
     * @param guids 需要删除的考勤绑定 ID
     * @return 结果
     */
    @Override
    public int deleteWsAttendanceBindingByGuids(String[] guids)
    {
        ResultData resultData=null;
        for (String guid : guids) {
            resultData= KaoQinInterUtil.userDestructionAuthorization(guid);
            //TODO  测试环境注释掉，真实环境需恢复
 /*           if (LaborConstants.FAIL.equals(resultData.getCode())) {
                throw new CustomException("人员删除失败，请联系管理员!");
             }
   */        }
        return wsAttendanceBindingMapper.deleteWsAttendanceBindingByGuids(guids);
    }

    /**
     * 删除考勤绑定 信息
     *
     * @param id 考勤绑定 ID
     * @return 结果
     */
    @Override
    public int deleteWsAttendanceBindingById(Integer id)
    {
        return wsAttendanceBindingMapper.deleteWsAttendanceBindingById(id);
    }

    @Override
    public int batchAddWsAttendanceBinding(WsAttendanceBindingDTO wsAttendanceBindingDTO) {

        if (StringUtils.isNull(wsAttendanceBindingDTO)) {
            throw new CustomException("人员列表不能为空");
        }
        if(StringUtils.isEmpty(wsAttendanceBindingDTO.getGuids())){
            throw new CustomException("人员列表不能为空");
        }
        if(StringUtils.isEmpty(wsAttendanceBindingDTO.getSerialNumbers())){
            throw new CustomException("请选择设备");
        }
        int auth =0;
        String[] serialNumbers = wsAttendanceBindingDTO.getSerialNumbers();
        String[] guids =wsAttendanceBindingDTO.getGuids();
        List<WsAttendanceBinding> wsAttendanceBindings = new ArrayList<>();
        String authStatus =LaborConstants.FAIL;
        String reason ="";
        // 上传文件路径前缀（如：D:/jlkj/uploadPath)
        String uploadPath = RuoYiConfig.getProfile();
        for (String guid : guids) {
            //注册人脸
            WsTeamPerson wsTeamPerson = wsTeamPersonService.selectWsTeamPersonByGuid(guid);
            if(StringUtils.isNull(wsTeamPerson)) continue;
            String base64="";
            if(StringUtils.isNotEmpty(wsTeamPerson.getFaceUrl())){
                String faceUrl = wsTeamPerson.getFaceUrl();
                String faceUrlSuffix =faceUrl.substring(faceUrl.indexOf(Constants.RESOURCE_PREFIX)+Constants.RESOURCE_PREFIX.length());
                String absolutePath = uploadPath+faceUrlSuffix;
                try {
                    BufferedImage image = ImageIO.read(new File(absolutePath));

                    //构造压缩后的图片
                    BufferedImage pressImage = new ImageCompress(image).resizeFix(480, 640);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(pressImage, "jpg", baos);
                    baos.flush();
                    BASE64Encoder encoder = new BASE64Encoder();
                    base64=  encoder.encode(baos.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ResultData resultFace= KaoQinInterUtil.registerFaceBase64Photo(base64,guid);
            if (LaborConstants.FAIL.equals(resultFace.getCode())) {
                continue;
            }
            //人员授权
            ResultData resultData = KaoQinInterUtil.userAuthorization(guid, StringUtils.join(serialNumbers,","));
            if (LaborConstants.SUCCESS.equals(resultData.getCode())) {
                authStatus = LaborConstants.SUCCESS;
                auth++;
            }
            if (LaborConstants.FAIL.equals(resultData.getCode())) {
                reason = resultData.getMessage();
            }
            for (int i = 0; i < serialNumbers.length; i++) {
                wsAttendanceBindings.add(new WsAttendanceBinding(guid,serialNumbers[i],authStatus,reason));
            }
            authStatus = LaborConstants.FAIL;
            reason = "";
        }
        List<WsAttendanceBinding> updateList = new ArrayList<>();
        List<WsAttendanceBinding> insertList = new ArrayList<>();
        for (WsAttendanceBinding wsAttendanceBinding : wsAttendanceBindings) {
            int count =wsAttendanceBindingMapper.countWsAttdanceBindingByGuidAndkey(wsAttendanceBinding.getGuid(),wsAttendanceBinding.getSerialNumber());
            if (count==0) {
                insertList.add(wsAttendanceBinding);
            }else {
                updateList.add(wsAttendanceBinding);
            }
        }
        if (insertList.size()>0)
            wsAttendanceBindingMapper.batchAdd(insertList);
        if (updateList.size()>0)
            wsAttendanceBindingMapper.batchUpdate(updateList);
        return auth;
    }


}
