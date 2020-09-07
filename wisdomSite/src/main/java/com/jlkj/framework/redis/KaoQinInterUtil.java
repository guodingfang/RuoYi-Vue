package com.jlkj.framework.redis;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.jlkj.common.constant.Constants;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.http.HttpRequester;
import com.jlkj.common.utils.http.HttpRespons;
import com.jlkj.common.utils.spring.SpringUtils;
import com.jlkj.project.ws.domain.WsTeamPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author liujie
 * @Description 人脸考勤接口操作类
 * @Date 2020/6/23 14:34
 **/
@Component
public class KaoQinInterUtil {

    public static Logger logger = LoggerFactory.getLogger(KaoQinInterUtil.class);

    /**
     * 存储到redis缓存文件夹
     */
    private final static String KAOQIN_VARIABLE = "KAOQIN_VAR";

    /**
     * redis缓存操作对象
     */
    private final static RedisCache redisCache;

    /**
     * 考勤接口前缀
     */
    public static final String KQ_CLOUD_URL = "http://gs-api.uface.uni-ubi.com";


    /**
     * 考勤错误代码集合
     */
    public static Map<String, String> KQ_CLOUD_ERROR_CODE = new HashMap<String, String>();


    static {
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-100", "接口授权appKey错误");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-101", "接口授权sign错误");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-102", "token错误或失效");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-200", "人员录入失败");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-201", "人员查询失败");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-202", "人员创建name为空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-203", "人员-设备授权deviceKeys为空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-204", "人员-设备销权deviceKeys为空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-205", "注册任务状态变更state无效");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-206", "查询注册任务状态失败");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-207", "更新注册任务状态失败");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-209", "拍照注册taskId为空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-212", "人员不存在");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-213", "注册任务状态无效");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-214", "taskId不存在或状态错误");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-215", "输入taskId状态不合法");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-216", "taskId状态未就绪");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-217", "人员不属于该应用");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-300", "设备通信失败");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-303", "设备录入参数错误");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-304", "设备获取失败");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-305", "设备创建deviceKey为空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-308", "人员-设备授权personGuids为空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-309", "设备绑定deviceKey为空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-310", "设备解绑deviceKey为空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-311", "设备禁用deviceKey为空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-312", "设备启用deviceKey为空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-313", "设备同步deviceKey为空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-314", "设备重置deviceKey为空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-316", "设备人员批量销权personGuids参数格式错误");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-317", "设备查询设备名deviceKey为空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-318", "设备更新设备名deviceKey为空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-319", "更新设备失败");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-320", "设备离线");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-321", "序列号被占用");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-322", "设备已存在");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-323", "设备不存在");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-325", "没有可更新字段");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-326", "设备不在线");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-327", "设备配置不存在");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-328", "语音配置内容含有非法符号");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-329", "语音模板格式错误");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-330", "自定义内容格式错误");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-331", "显示模板格式错误");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-332", "串口模板格式错误");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-333", "语音模式下自定义内容不能空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-334", "显示模式下自定义内容不能空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-335", "串口模式下自定义内容不能空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-336", "语音模式类型未定义");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-337", "显示模式类型未定义");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-338", "串口模式类型未定义");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-339", "识别距离模式类型未定义");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-340", "预览视频流开关模式类型未定义");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-341", "设备名称显示类型未定义");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-342", "设备未启动");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-343", "识别陌生人类型未定义");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-344", "方向配置类型未定义");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-345", "设备序列号类型错误");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-352", "时间段参数数量不正确或超出3段限制");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-353", "时间段参数后时间段早于前时间段");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-354", "时间段参数超出限制");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-355", "时间段参数格式错误");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-356", "logoUrl格式错误");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-357", "设备不属于该应用");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-358", "设备不属于任何应用");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-359", "识别模式硬件TTL接口重复");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-360", "识别模式硬件232接口重复");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-361", "硬件类型只能为IC读卡器");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-362", "硬件类型只能为ID读卡器");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-600", "照片补推失败");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-601", "照片状态查询失败");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-602", "照片创建失败");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-603", "照片创建img为空");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-604", "照片更新状态state无效");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-606", "超出照片添加数量");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-607", "照片过大");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-608", "img为空,可能未传输或者数据过大");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-609", "照片不存在");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-610", "文件不为jpg或png类型");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-611", "照片不属于该应用");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-612", "该照片与之前注册照相似度不符，请重新提供");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-1006", "邮件服务不可用");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-1007", "消息服务不可用");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-1008", "统一客户服务不可用");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-1009", "设备服务不可用");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-1300", "上传base64非图片");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-1301", "人脸框过小,人脸面积小于图片面积5%");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-1302", "人脸侧角度大于15度");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-1303", "照片中无人脸或不止一张人脸");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-1304", "上传图片过大");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-1305", "base64可能未上传或图片过大");
        KQ_CLOUD_ERROR_CODE.put("GS_EXP-1306", "检测异常");
        KQ_CLOUD_ERROR_CODE.put("OP_EXP-2002", "图片没有检测到人脸");
        KQ_CLOUD_ERROR_CODE.put("OP_EXP-2006", "图片人脸数量过多");
        KQ_CLOUD_ERROR_CODE.put("OP_EXP-3000", "人脸过小");
        KQ_CLOUD_ERROR_CODE.put("OP_EXP-3001", "人脸超出或过于靠近图片边界");
        KQ_CLOUD_ERROR_CODE.put("OP_EXP-3002", "脸过于模糊");
        KQ_CLOUD_ERROR_CODE.put("OP_EXP-3003", "脸光照过暗");
        KQ_CLOUD_ERROR_CODE.put("OP_EXP-3004", "脸光照过亮");
        KQ_CLOUD_ERROR_CODE.put("OP_EXP-3005", "脸左右亮度不对称");
        KQ_CLOUD_ERROR_CODE.put("OP_EXP-3006", "三维旋转之俯仰角度过大");
        KQ_CLOUD_ERROR_CODE.put("OP_EXP-3007", "三维旋转之左右旋转角过大");
        KQ_CLOUD_ERROR_CODE.put("OP_EXP-3008", "平面内旋转角过大");
        KQ_CLOUD_ERROR_CODE.put("ERR001", "数据库异常");
        KQ_CLOUD_ERROR_CODE.put("ERR002", "网络异常");
        KQ_CLOUD_ERROR_CODE.put("ERR003", "参数异常");
        KQ_CLOUD_ERROR_CODE.put("ERR004", "系统内部异常");
        KQ_CLOUD_ERROR_CODE.put("ERR005", "数据不存在异常");
        KQ_CLOUD_ERROR_CODE.put("ERR006", "验证异常");
        redisCache=SpringUtils.getBean(RedisCache.class);
    }

    /**
     * 获取token
     */
    public static Map<String, String> getToken() {
        Map<String, String> resultMap = new HashMap<String, String>();
        // code=1，代码成功，否则代表失败
        resultMap.put("code", "1");
        try {
            if (redisCache == null) {
                resultMap.put("code", "2"); // code=1，代码成功，否则代表失败
                resultMap.put("message", "Redis缓存未启用");
                return resultMap;
            }
            String accessToken = redisCache.getCacheObject(KAOQIN_VARIABLE + ":Token");
            if (StringUtils.isNotEmpty(accessToken)) {
                resultMap.put("code", "1"); // code=1，代码成功，否则代表失败
                resultMap.put("accessToken", accessToken);
                return resultMap;
            }
            String appKey = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY + "ws.kaoqin.param.appkey");
            if (StringUtil.isEmpty(appKey)) {
                resultMap.put("code", "2"); // code=1，代码成功，否则代表失败
                resultMap.put("message", "未配置考勤ws.kaoqin.param.appkey");
            }

            String secret = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY + "ws.kaoqin.param.appsecret");
            if (StringUtil.isEmpty(secret)) {
                resultMap.put("code", "2"); // code=1，代码成功，否则代表失败
                resultMap.put("message", "未配置考勤ws.kaoqin.param.appsecret");
            }



            String appid = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY + "ws.kaoqin.param.appid");

            if (StringUtil.isEmpty(appid)) {
                resultMap.put("code", "2"); // code=1，代码成功，否则代表失败
                resultMap.put("message", "ws.kaoqin.param.appid不能为空");
            }

            String timestamp = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY + "ws.kaoqin.param.timestamp");

            if (StringUtil.isEmpty(timestamp)) {
                resultMap.put("code", "2"); // code=1，代码成功，否则代表失败
                resultMap.put("message", "ws.kaoqin.param.timestamp不能为空");
            }


            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("appId", appid);
            paramMap.put("appKey", appKey);
            paramMap.put("timestamp", timestamp);

            String MD5 = md5(appKey + timestamp + secret);

            paramMap.put("sign", MD5);
            String paramString = HttpUtil.toParams(paramMap);
            String resultContent = HttpUtil.post(KQ_CLOUD_URL + "/v1/" + appid + "/auth", paramString);
            JSONObject paramJson = JSON.parseObject(resultContent);
            String result = paramJson.getString("result");

            if ("1".equals(result)) {
                accessToken = paramJson.getString("data");
                // 将获取到的AccessToken存储到Redis缓存中
                redisCache.setCacheObject(KAOQIN_VARIABLE + ":Token", accessToken,10, TimeUnit.HOURS);
                resultMap.put("code", "1"); // code=1，代码成功，否则代表失败
                resultMap.put("accessToken", accessToken);
                return resultMap;
            } else {
                resultMap.put("code", "2"); // code=1，代码成功，否则代表失败
                resultMap.put("message", paramJson.getString("msg"));
                return resultMap;
            }
        } catch (Exception e) {
            logger.error("获取AccessToken失败", e);
            resultMap.put("code", "2");
            resultMap.put("message", "获取AccessToken失败，" + e.getMessage());
            return resultMap;
        }
    }


    /**
     * 获取md5加密
     *
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String md5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update((str).getBytes("UTF-8"));
        byte b[] = md5.digest();

        int i;
        StringBuffer buf = new StringBuffer("");

        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }

        return buf.toString();
    }

    /**
     * 新增考勤设备
     */
    public static ResultData addKQ(String name,String deviceKey) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        if (StringUtil.isEmpty(name)) {
            resultData.setMessage("设备序列号不能为空");
            return resultData;
        }
        if (StringUtil.isEmpty(deviceKey)) {
            resultData.setMessage("设备名称不能为空");
            return resultData;
        }

        Map<String, String> atMap = getToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage(atMap.get("message"));
            return resultData;
        }

        if (StringUtil.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData ;
        }

        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            String appid = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY+"ws.kaoqin.param.appid");
            paramMap.put("appId",appid);
            paramMap.put("token", accessToken);
            paramMap.put("deviceKey", deviceKey);
            paramMap.put("name", name);
            paramMap.put("tag", "");
            HttpRespons resp = httpRequ.sendPost(KQ_CLOUD_URL + "/v1/"+appid+"/device", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("GS_SUS300".equals(code)){
                resultData.setCode("1");
                return resultData;
            }else {
                resultData.setMessage("设备维护失败，" + KQ_CLOUD_ERROR_CODE.get(code));
                return resultData;
            }
        }catch (Exception e) {
            logger.error("新增考勤设备失败", e);
            resultData.setMessage("新增考勤设备失败，" + e.getMessage());
            return resultData;
        }

    }

    /**
        * @Author liujie
        * @Description 更新设备名称
        * @Date 2020/6/24 11:37
        * @Param [name, deviceKey]
        * @Return com.jlkj.framework.redis.ResultData
       **/
    public static ResultData updateKQ(String name,String deviceKey) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        if (StringUtil.isEmpty(name)) {
            resultData.setMessage("设备序列号不能为空");
            return resultData;
        }
        if (StringUtil.isEmpty(deviceKey)) {
            resultData.setMessage("设备名称不能为空");
            return resultData;
        }

        Map<String, String> atMap = getToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage(atMap.get("message"));
            return resultData;
        }

        if (StringUtil.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData ;
        }

        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            String appid = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY+"ws.kaoqin.param.appid");
            paramMap.put("appId",appid);
            paramMap.put("token", accessToken);
            paramMap.put("deviceKey", deviceKey);
            paramMap.put("name", name);
            paramMap.put("tag", "");
            HttpRespons resp = httpRequ.sendPut(KQ_CLOUD_URL + "/v1/"+appid+"/device/"+deviceKey, paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("GS_SUS332".equals(code)){
                resultData.setCode("1");
                return resultData;
            }else {
                resultData.setMessage("设备维护失败，" + KQ_CLOUD_ERROR_CODE.get(code));
                return resultData;
            }
        }catch (Exception e) {
            logger.error("更新考勤设备失败", e);
            resultData.setMessage("更新考勤设备失败，" + e.getMessage());
            return resultData;
        }

    }


    /**
     * 删除考勤设备
     */
    public static ResultData deleteKQ(String deviceKey) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        if (StringUtil.isEmpty(deviceKey)) {
            resultData.setMessage("设备序列号不能为空");
            return resultData;
        }
        Map<String, String> atMap = getToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage(atMap.get("message"));
            return resultData;
        }
        if (StringUtil.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            String appid = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY+"ws.kaoqin.param.appid");
            paramMap.put("appId", appid);
            paramMap.put("token", accessToken);
            paramMap.put("deviceKey", deviceKey);
            HttpRespons resp = httpRequ.sendDelete(KQ_CLOUD_URL + "/v1/"+appid+"/device/"+deviceKey,paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("GS_SUS302".equals(code)) {
                resultData.setCode("1");
                return resultData;
            }else {
                resultData.setMessage("设备维护失败，" + KQ_CLOUD_ERROR_CODE.get(code));
                return resultData ;
            }
        }catch (Exception e) {
            logger.error("删除考勤设备失败", e);
            resultData.setMessage( "删除考勤设备失败，" + e.getMessage());
            return resultData;
        }
    }

    /**
     * 新增考勤人员信息
     */
    public static ResultData adduser(WsTeamPerson wsTeamPerson) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        Map<String, String> atMap = getToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage(atMap.get("message"));
            return resultData;
        }
        if (StringUtil.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            String appid = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY+"ws.kaoqin.param.appid");
            paramMap.put("appId", appid);
            paramMap.put("token", accessToken);
            paramMap.put("name", wsTeamPerson.getName());
            paramMap.put("idNo", wsTeamPerson.getIdNumber());
            paramMap.put("phone", "");
            paramMap.put("tag", "");
            HttpRespons resp = httpRequ.sendPost(KQ_CLOUD_URL + "/v1/"+appid+"/person", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("GS_SUS200".equals(code)) {
                JSONObject data = paramJson.getJSONObject("data");
                String guid = data.getString("guid");
                resultData.setCode("1");
                resultData.setData(paramJson);
                return resultData;
            }else {
                resultData.setMessage("考勤人员信息新增失败，" + KQ_CLOUD_ERROR_CODE.get(code));
                return resultData;
            }
        }catch (Exception e) {
            logger.error("考勤人员信息新增失败", e);
            resultData.setMessage("考勤人员信息新增失败，" + e.getMessage());
            return resultData;
        }

    }


    public static ResultData updateuser(WsTeamPerson wsTeamPerson) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        Map<String, String> atMap = getToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage(atMap.get("message"));
            return resultData;
        }
        if (StringUtil.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            String appid = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY+"ws.kaoqin.param.appid");
            paramMap.put("appId", appid);
            paramMap.put("token", accessToken);
            paramMap.put("guid", wsTeamPerson.getGuid());


            paramMap.put("name",  URLEncoder.encode(wsTeamPerson.getName(),"UTF-8"));

            paramMap.put("idNo",  URLEncoder.encode(wsTeamPerson.getIdNumber(),"UTF-8"));
            paramMap.put("phone", "");
            paramMap.put("tag", "");
            HttpRespons resp = httpRequ.sendPut(KQ_CLOUD_URL + "/v1/"+appid+"/person/"+wsTeamPerson.getGuid(), paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("GS_SUS203".equals(code)) {
                resultData.setCode("1");
                resultData.setData(paramJson);
                return resultData;
            }else {
                resultData.setMessage("考勤人员信息修改失败，" + KQ_CLOUD_ERROR_CODE.get(code));
                return resultData ;
            }
        }catch (Exception e) {
            logger.error("考勤人员信息修改失败", e);
            resultData.setMessage("考勤人员信息修改失败，" + e.getMessage());
            return resultData;
        }
    }

    public static ResultData deleteuser(String guid) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        Map<String, String> atMap = getToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage(atMap.get("message"));
            return resultData;
        }
        if (StringUtil.isEmpty(accessToken)) {
            resultData.setMessage( "accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            String appid = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY+"ws.kaoqin.param.appid");
            paramMap.put("appId", appid);
            paramMap.put("token", accessToken);
            paramMap.put("guid", guid);
            paramMap.put("tag", "");
            HttpRespons resp = httpRequ.sendDelete(KQ_CLOUD_URL + "/v1/"+appid+"/person/"+guid, paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("GS_SUS201".equals(code)) {
                resultData.setCode("1");
                resultData.setData(paramJson);
                return resultData;
            }else {
                resultData.setMessage("考勤人员信息删除失败，" + KQ_CLOUD_ERROR_CODE.get(code));
                return resultData;
            }
        }catch (Exception e) {
            logger.error("考勤人员信息删除失败", e);
            resultData.setMessage("考勤人员信息删除失败，" + e.getMessage());
            return resultData;
        }
    }

    public static ResultData openDevicePhotograph(WsTeamPerson wsTeamPerson,String deviceKey) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        Map<String, String> atMap = getToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage(atMap.get("message"));
            return resultData;
        }
        if (StringUtil.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }

        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            String appid = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY+"ws.kaoqin.param.appid");
            paramMap.put("appId", appid);
            paramMap.put("token", accessToken);
            paramMap.put("personGuid", wsTeamPerson.getGuid());
            paramMap.put("type", "1");
            paramMap.put("deviceKey", deviceKey);
            HttpRespons resp = httpRequ.sendPost(KQ_CLOUD_URL + "/v1/"+appid+"/device/"+deviceKey+"/mode/state", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("GS_SUS339".equals(code)) {
                resultData.setCode("1");
                resultData.setData(paramJson);
                return resultData;
            }else {
                resultData.setMessage("开启人脸设备注册失败，" + KQ_CLOUD_ERROR_CODE.get(code));
                return resultData;
            }
        }catch (Exception e) {
            logger.error("开启人脸设备注册失败", e);
            resultData.setMessage("开启人脸设备注册失败，" + e.getMessage());
            return resultData;
        }
    }

     public static ResultData  getDeviceInfo(String deviceKey){
         ResultData resultData = new ResultData();
         resultData.setCode("0");
         Map<String, String> atMap = getToken();
         String accessToken = null;
         if ("1".equals(atMap.get("code"))) {
             accessToken = atMap.get("accessToken");
         } else {
             resultData.setMessage(atMap.get("message"));
             return resultData;
         }
         if (StringUtil.isEmpty(accessToken)) {
             resultData.setMessage("accessToken获取失败");
             return resultData;
         }
         try {
             HttpRequester httpRequ = new HttpRequester();
             httpRequ.setDefaultContentEncoding("UTF-8");
             Map<String, String> paramMap = new HashMap<String, String>();
             String appid = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY+"ws.kaoqin.param.appid");
             paramMap.put("appId", appid);
             paramMap.put("token", accessToken);
             paramMap.put("deviceKey", deviceKey);
             HttpRespons resp = httpRequ.sendGet(KQ_CLOUD_URL + "/v1/"+appid+"/device/"+deviceKey, paramMap);
             String resultContent = resp.getContent();
             JSONObject paramJson = JSON.parseObject(resultContent);
             String code = paramJson.getString("code");
             if ("GS_SUS301".equals(code)) {
                 resultData.setCode("1");
                 resultData.setData(paramJson);
                 return resultData;
             }else {
                 resultData.setMessage("设备信息获取失败，" + KQ_CLOUD_ERROR_CODE.get(code));
                 return resultData;
             }
         }catch (Exception e) {
             logger.error("设备信息获取失败", e);
             resultData.setMessage("设备信息获取失败，" + e.getMessage());
             return resultData;
         }
     }

    public static ResultData  getDeviceInfoList(){
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        Map<String, String> atMap = getToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage(atMap.get("message"));
            return resultData;
        }
        if (StringUtil.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            String appid = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY+"ws.kaoqin.param.appid");
            paramMap.put("appId", appid);
            paramMap.put("token", accessToken.trim());
            paramMap.put("length",String.valueOf(Integer.MAX_VALUE));
            HttpRespons resp = httpRequ.sendPost(KQ_CLOUD_URL + "/v1/"+appid+"/devices/search", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("GS_SUS303".equals(code)) {
                resultData.setCode("1");
                resultData.setData(paramJson);
                return resultData;
            }else {
                resultData.setMessage("设备信息获取失败，" + KQ_CLOUD_ERROR_CODE.get(code));
                return resultData;
            }
        }catch (Exception e) {
            logger.error("设备信息获取失败", e);
            resultData.setMessage("设备信息获取失败，" + e.getMessage());
            return resultData;
        }
    }


    public static ResultData getFacesUrl(WsTeamPerson wsTeamPerson ,String guid) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        Map<String, String> atMap = getToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage(atMap.get("message"));
            return resultData;
        }
        if (StringUtil.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            String appid = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY+"ws.kaoqin.param.appid");
            paramMap.put("appId", appid);
            paramMap.put("token", accessToken);
            paramMap.put("guid", guid);
            HttpRespons resp = httpRequ.sendGet(KQ_CLOUD_URL + "/v1/"+appid+"/person/"+guid+"/faces", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("GS_SUS207".equals(code)) {
                resultData.setCode("1");
                resultData.setData(paramJson);
                return resultData;
            }else {
                resultData.setMessage("人脸照片获取失败，" + KQ_CLOUD_ERROR_CODE.get(code));
                return resultData;
            }
        }catch (Exception e) {
            logger.error("人脸照片获取失败", e);
            resultData.setMessage("人脸照片获取失败，" + e.getMessage());
            return resultData;
        }
    }

    public static ResultData registerFaceBase64Photo(String base64, String guid) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        Map<String, String> atMap = getToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage(atMap.get("message"));
            return resultData;
        }
        if (StringUtil.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            String appid = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY+"ws.kaoqin.param.appid");
            paramMap.put("appId", appid);
            paramMap.put("token", accessToken);
            paramMap.put("guid", guid);
            paramMap.put("img",base64);//普通照片
            paramMap.put("type","1");//普通照片类型
            paramMap.put("validLevel","0");//检测全部属性

            HttpRespons resp = httpRequ.sendPost(KQ_CLOUD_URL + "/v1/"+appid+"/person/"+guid+"/face/valid", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("GS_SUS600".equals(code)) {
                resultData.setData(paramJson);
                resultData.setCode("1");
                return resultData;
            }else {
                resultData.setMessage("人脸照片注册失败，" + KQ_CLOUD_ERROR_CODE.get(code));
                return resultData;
            }
        }catch (Exception e) {
            logger.error("人脸照片注册失败", e);
            resultData.setMessage("人脸照片注册失败，" + e.getMessage());
            return resultData;
        }
    }


    public static ResultData registerFacePhotoUrl(WsTeamPerson wsTeamPerson) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        Map<String, String> atMap = getToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage(atMap.get("message"));
            return resultData;
        }
        if (StringUtil.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            String appid = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY+"ws.kaoqin.param.appid");
            paramMap.put("appId", appid);
            paramMap.put("token", accessToken);
            paramMap.put("guid", wsTeamPerson.getGuid());
            paramMap.put("img",wsTeamPerson.getFaceUrl());//普通照片
            paramMap.put("type","1");//普通照片类型
            paramMap.put("validLevel","0");//检测全部属性

            HttpRespons resp = httpRequ.sendPost(KQ_CLOUD_URL + "/v1/"+appid+"/person/"+wsTeamPerson.getGuid()+"/face/imageUrl", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("GS_SUS600".equals(code)) {
                resultData.setData(paramJson);
                resultData.setCode("1");
                return resultData;
            }else {
                resultData.setMessage("人脸照片注册失败，" + KQ_CLOUD_ERROR_CODE.get(code));
                return resultData;
            }
        }catch (Exception e) {
            logger.error("人脸照片注册失败", e);
            resultData.setMessage("人脸照片注册失败，" + e.getMessage());
            return resultData;
        }
    }

    public static ResultData userAuthorization(String guid,String deviceKeys) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        ResultData rs = userDestructionAuthorization(guid);
        if("0".equals(rs.getCode())){
            resultData.setMessage(rs.getMessage());
            return resultData;
        }

        Map<String, String> atMap = getToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage(atMap.get("message"));
            return resultData ;
        }
        if (StringUtil.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }

        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            String appid = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY+"ws.kaoqin.param.appid");
            paramMap.put("appId", appid);
            paramMap.put("token", accessToken);
            paramMap.put("guid", guid);
            paramMap.put("deviceKeys", deviceKeys);
            HttpRespons resp = httpRequ.sendPost(KQ_CLOUD_URL + "/v1/"+appid+"/person/"+guid+"/devices", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("GS_SUS204".equals(code)) {
                resultData.setData(paramJson);
                resultData.setCode("1");
                return resultData;
            }else {
                resultData.setMessage("授权设备失败，" + KQ_CLOUD_ERROR_CODE.get(code));
                return resultData;
            }
        }catch (Exception e) {
            logger.error("授权设备失败", e);
            resultData.setMessage("授权设备失败，" + e.getMessage());
            return resultData;
        }
    }








    public static ResultData userDestructionAuthorization(String guid) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        Map<String, String> atMap = getToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage(atMap.get("message"));
            return resultData ;
        }
        if (StringUtil.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            String appid = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY+"ws.kaoqin.param.appid");
            paramMap.put("appId", appid);
            paramMap.put("token", accessToken);
            paramMap.put("guid", guid);
            paramMap.put("deviceKey", "");
            HttpRespons resp = httpRequ.sendDelete(KQ_CLOUD_URL + "/v1/"+appid+"/person/"+guid+"/devices", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("GS_SUS205".equals(code)) {
                resultData.setData(paramJson);
                resultData.setCode("1");
                return resultData;
            }else {
                resultData.setMessage("销权设备失败，" + KQ_CLOUD_ERROR_CODE.get(code));
                return  resultData;
            }
        }catch (Exception e) {
            logger.error("销权设备失败", e);
            resultData.setMessage("销权设备失败，" + e.getMessage());
            return resultData;
        }

    }


    public static ResultData userDeleteImg(String imageGuid, String personGuid) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        Map<String, String> atMap = getToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage(atMap.get("message"));
            return resultData;
        }
        if (StringUtil.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            String appid = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY+"ws.kaoqin.param.appid");
            paramMap.put("appId", appid);
            paramMap.put("token", accessToken);
            paramMap.put("guid",imageGuid);
            paramMap.put("personGuid",personGuid);
            HttpRespons resp = httpRequ.sendDelete(KQ_CLOUD_URL + "/v1/"+appid+"/person/"+personGuid+"/face/"+imageGuid, paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("GS_SUS601".equals(code)) {
                resultData.setCode("1");
                resultData.setData(paramJson);
                return resultData;
            }else {
                resultData.setMessage("人员照片删除失败，" + KQ_CLOUD_ERROR_CODE.get(code));
                return resultData;
            }
        }catch (Exception e) {
            logger.error("人员照片删除失败", e);
            resultData.setMessage("人员照片删除失败，" + e.getMessage());
            return resultData;
        }
    }

}
