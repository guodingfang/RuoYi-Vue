package com.jlkj.framework.redis;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.constant.Constants;
import com.jlkj.common.utils.StringUtils;
import com.jlkj.common.utils.http.HttpRequester;
import com.jlkj.common.utils.http.HttpRespons;
import com.jlkj.common.utils.spring.SpringUtils;
import com.jlkj.project.ws.domain.WsStartPTZControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 萤石云视频接口操作类
 *
 * @author liujie
 * @date 2018-7-11 15:03:57
 */
public class YingshiVideoInterUtil {

    public static Logger fileLog = LoggerFactory.getLogger("FILE_LOGGER");
    /** 存储到redis缓存文件夹 */
    private final static String YINGSHI_VARIABLE = "YINGSHI";

    /** redis缓存操作对象 */

    public static  RedisCache redisCache;

    /** 萤石云接口前缀 */
    public static final String YS_CLOUD_URL = "https://open.ys7.com";

    /** 萤石云错误代码集合 */
    public static Map<String, String> YS_CLOUD_ERROR_CODE = new HashMap<String, String>();

    /** 云台控制，操作命令：0-上，1-下，2-左，3-右，4-左上，5-左下，6-右上，7-右下，8-放大，9-缩小，10-近焦距，11-远焦距 */
    public static List<String> DIRECTION_OPER_LIST = Arrays
            .asList(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" });

    /** 云台控制，云台速度：0-慢，1-适中，2-快 */
    public static List<String> SPEED_LIST = Arrays.asList(new String[] { "0", "1", "2" });

    static {
            YS_CLOUD_ERROR_CODE.put("10001", "参数为空或格式不正确");
            YS_CLOUD_ERROR_CODE.put("10002", "accessToken异常或过期 ");
            YS_CLOUD_ERROR_CODE.put("10005", "appKey异常");
            YS_CLOUD_ERROR_CODE.put("10017", "appKey不存在");
            YS_CLOUD_ERROR_CODE.put("10030", "appkey和appSecret不匹配");
            YS_CLOUD_ERROR_CODE.put("10051", "无权限进行抓图");
            YS_CLOUD_ERROR_CODE.put("20002", "设备不存在(该接口出现这个错误码表示设备未注册至萤石云)");
            YS_CLOUD_ERROR_CODE.put("20006", "检查设备网络状况，稍后再试");
            YS_CLOUD_ERROR_CODE.put("20007", "设备不在线(检查设备是否在线)");
            YS_CLOUD_ERROR_CODE.put("20008", "设备响应超时");
            YS_CLOUD_ERROR_CODE.put("20010", "设备验证码错误(检查设备验证码是否错误)");
            YS_CLOUD_ERROR_CODE.put("20011", "设备添加失败(检查设备网络等是否正常)");
            YS_CLOUD_ERROR_CODE.put("20013", "设备已被别人添加(该设备已被别的账号添加)");
            YS_CLOUD_ERROR_CODE.put("20014", "deviceSerial不合法");
            YS_CLOUD_ERROR_CODE.put("20017", "设备已添加");
            YS_CLOUD_ERROR_CODE.put("20018", "不拥有该设备");
            YS_CLOUD_ERROR_CODE.put("20032", "该用户下通道不存在 ");
            YS_CLOUD_ERROR_CODE.put("49999", "接口调用异常");
            YS_CLOUD_ERROR_CODE.put("60000", "设备不支持云台控制");
            YS_CLOUD_ERROR_CODE.put("60001", "用户无云台控制权限");
            YS_CLOUD_ERROR_CODE.put("60002", "设备云台旋转达到上限位");
            YS_CLOUD_ERROR_CODE.put("60003", "设备云台旋转达到下限位");
            YS_CLOUD_ERROR_CODE.put("60004", "设备云台旋转达到左限位");
            YS_CLOUD_ERROR_CODE.put("60005", "设备云台旋转达到右限位");
            YS_CLOUD_ERROR_CODE.put("60006", "云台当前操作失败");
            YS_CLOUD_ERROR_CODE.put("60009", "正在调用预置点");
            YS_CLOUD_ERROR_CODE.put("60017", "设备抓图失败");
            YS_CLOUD_ERROR_CODE.put("60020", "不支持该命令");
            YS_CLOUD_ERROR_CODE.put("60060", "地址未绑定（请前往官网设置直播）");
            redisCache= SpringUtils.getBean(RedisCache.class);
    }

    /**
     * 获取AccessToken
     */
    public static Map<String, String> getAccessToken() {
        Map<String, String> resultMap = new HashMap<String, String>();
        // code=1，代码成功，否则代表失败
        resultMap.put("code", "1");
        try {
            if (redisCache == null) {
                resultMap.put("code", "2"); // code=1，代码成功，否则代表失败
                resultMap.put("message", "Redis缓存未启用");
                return resultMap;
            }
            String accessToken = redisCache.getCacheObject(YINGSHI_VARIABLE + ":ACCESSTOKEN");
            if (StringUtils.isNotEmpty(accessToken)) {
                resultMap.put("code", "1"); // code=1，代码成功，否则代表失败
                resultMap.put("accessToken", accessToken);
                return resultMap;
            }
            String appKey = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY+"ws.video.param.appkey");
            if (appKey == null) {
                resultMap.put("code", "2"); // code=1，代码成功，否则代表失败
                resultMap.put("message", "未配置萤石云appKey");
            }

            String secret =redisCache.getCacheObject(Constants.SYS_CONFIG_KEY+"ws.video.param.secret");
            if (secret == null) {
                resultMap.put("code", "2"); // code=1，代码成功，否则代表失败
                resultMap.put("message", "未配置萤石云appSecret");
            }
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("appKey", appKey);
            paramMap.put("appSecret", secret);
            HttpRespons resp = httpRequ.sendPost(YS_CLOUD_URL + "/api/lapp/token/get", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("200".equals(code)) {
                JSONObject data = paramJson.getJSONObject("data");
                accessToken = data.getString("accessToken");
                // 将获取到的AccessToken存储到Redis缓存中
                redisCache.setCacheObject(YINGSHI_VARIABLE + ":ACCESSTOKEN", accessToken,6, TimeUnit.DAYS);

                resultMap.put("code", "1"); // code=1，代码成功，否则代表失败
                resultMap.put("accessToken", accessToken);
                return resultMap;
            } else {
                resultMap.put("code", "2"); // code=1，代码成功，否则代表失败
                resultMap.put("message", "获取AccessToken失败，请检测参数是否正确，错误的原因：" + YS_CLOUD_ERROR_CODE.get(code));
                return resultMap;
            }
        } catch (IOException e) {
            fileLog.error("获取AccessToken失败", e);
            resultMap.put("code", "2");
            resultMap.put("message", "获取AccessToken失败，" + e.getMessage());
            return resultMap;
        }
    }

    /**
     * 新增萤石云设备
     *
     * @param deviceSerial
     *            设备序列号
     * @param validateCode
     *            设备验证码，设备机身上的六位大写字母
     */
    public static ResultData addVideo(String deviceSerial, String validateCode) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        if (StringUtils.isEmpty(deviceSerial) || StringUtils.isEmpty(validateCode)) {
            resultData.setMessage("设备序列号或设备验证码不能为空");
            return resultData;
        }
        Map<String, String> atMap = getAccessToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage(atMap.get("message"));
            return resultData;
        }
        if (StringUtils.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("accessToken", accessToken);
            paramMap.put("deviceSerial", deviceSerial);
            paramMap.put("validateCode", validateCode);
            HttpRespons resp = httpRequ.sendPost(YS_CLOUD_URL + "/api/lapp/device/add", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            String msg = paramJson.getString("msg");

            resultData.setCode(code);
            resultData.setMessage(msg);
            return resultData;
        } catch (IOException e) {
            fileLog.error("新增视频设备失败", e);
            resultData.setMessage("新增视频设备失败，" + e.getMessage());
            return resultData;
        }
    }

    /**
     * 删除萤石云设备
     *
     * @param deviceSerial
     *            设备序列号
     * @param deviceName
     *            设备名称，长度不大于50字节，不能包含特殊字符
     */
    public static ResultData updateVideoName(String deviceSerial, String deviceName) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        if (StringUtils.isEmpty(deviceSerial)) {
            resultData.setMessage("设备序列号不能为空");
            return resultData;
        }
        Map<String, String> atMap = getAccessToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage(atMap.get("message"));
            return resultData;
        }
        if (StringUtils.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("accessToken", accessToken);
            paramMap.put("deviceSerial", deviceSerial);
            paramMap.put("deviceName", deviceName);
            HttpRespons resp = httpRequ.sendPost(YS_CLOUD_URL + "/api/lapp/device/name/update", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("200".equals(code)) {
                resultData.setCode("1");
                resultData.setData(paramJson);
                return resultData;
            } else if ("10002".equals(code)) { // 如果AccessToken过期，清除Redis中缓存的AccessToken值
                redisCache.deleteObject(YINGSHI_VARIABLE + ":ACCESSTOKEN");
                resultData.setMessage("AccessToken过期，请重新操作");
                return resultData;
            } else {
                resultData.setMessage("设备维护失败，" + YS_CLOUD_ERROR_CODE.get(code));
                return resultData;
            }
        } catch (IOException e) {
            fileLog.error("删除视频设备失败", e);
            resultData.setMessage("删除视频设备失败，" + e.getMessage());
            return resultData;
        }
    }

    /**
     * 删除萤石云设备
     *
     * @param deviceSerial
     *            设备序列号
     */
    public static ResultData deleteVideo(String deviceSerial) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        if (StringUtils.isEmpty(deviceSerial)) {
            resultData.setMessage("设备序列号不能为空");
            return resultData;
        }
        Map<String, String> atMap = getAccessToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage( atMap.get("message"));
            return resultData;
        }
        if (StringUtils.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("accessToken", accessToken);
            paramMap.put("deviceSerial", deviceSerial);
            HttpRespons resp = httpRequ.sendPost(YS_CLOUD_URL + "/api/lapp/device/delete", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("200".equals(code)) {
                resultData.setCode("1");
                resultData.setData(paramJson);
                return resultData;
            } else if ("10002".equals(code)) { // 如果AccessToken过期，清除Redis中缓存的AccessToken值
                redisCache.deleteObject(YINGSHI_VARIABLE + ":ACCESSTOKEN");
                resultData.setMessage("AccessToken过期，请重新操作");
                return resultData;
            } else {
                resultData.setMessage("设备维护失败，" + YS_CLOUD_ERROR_CODE.get(code));
                return resultData;
            }
        } catch (IOException e) {
            fileLog.error("删除视频设备失败", e);
            resultData.setMessage("删除视频设备失败，" + e.getMessage());
            return resultData;
        }
    }

    /**
     * 关闭设备视频加密
     *
     * @param deviceSerial
     *            设备序列号
     * @param validateCode
     *            设备验证码，设备机身上的六位大写字母
     */
    public static ResultData offVideoEncrypt(String deviceSerial, String validateCode) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        if (StringUtils.isEmpty(deviceSerial) || StringUtils.isEmpty(validateCode)) {
            resultData.setMessage("设备序列号或设备验证码不能为空");
            return resultData;
        }
        Map<String, String> atMap = getAccessToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage( atMap.get("message"));
            return resultData;
        }
        if (StringUtils.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("accessToken", accessToken);
            paramMap.put("deviceSerial", deviceSerial);
            paramMap.put("validateCode", validateCode);
            HttpRespons resp = httpRequ.sendPost(YS_CLOUD_URL + "/api/lapp/device/encrypt/off", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            // 60016加密未开启，无需关闭
            if ("200".equals(code) || "60016".equals(code)) {
                resultData.setCode("1");
                resultData.setData(paramJson);
                return resultData;
            } else if ("10002".equals(code)) { // 如果AccessToken过期，清除Redis中缓存的AccessToken值
                redisCache.deleteObject(YINGSHI_VARIABLE + ":ACCESSTOKEN");
                resultData.setMessage("AccessToken过期，请重新操作");
                return resultData;
            } else {
                resultData.setMessage("设备维护失败，" + YS_CLOUD_ERROR_CODE.get(code));
                return resultData;
            }
        } catch (IOException e) {
            fileLog.error("新增视频设备失败", e);
            resultData.setMessage("新增视频设备失败，" + e.getMessage());
            return resultData;
        }
    }

    /**
     * 设备抓拍图片
     *
     * @param deviceSerial
     *            设备序列号
     */
    public static Map<String, String> videoCapture(String deviceSerial) {
        Map<String, String> resultMap = new HashMap<String, String>();
        // code = 1，代码成功，否则代表失败
        resultMap.put("code", "1");
        if (StringUtils.isEmpty(deviceSerial)) {
            resultMap.put("code", "2");
            resultMap.put("message", "设备序列号不能为空");
            return resultMap;
        }
        Map<String, String> atMap = getAccessToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultMap.put("code", "2");
            resultMap.put("message", atMap.get("message"));
            return resultMap;
        }
        if (StringUtils.isEmpty(accessToken)) {
            resultMap.put("code", "2");
            resultMap.put("message", "accessToken获取失败");
            return resultMap;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("accessToken", accessToken);
            paramMap.put("deviceSerial", deviceSerial);
            paramMap.put("channelNo", "1"); // 通道号，IPC设备填写1
            HttpRespons resp = httpRequ.sendPost(YS_CLOUD_URL + "/api/lapp/device/capture", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            // 60016加密未开启，无需关闭
            if ("200".equals(code)) {
                JSONObject data = paramJson.getJSONObject("data");
                String picUrl = data.getString("picUrl");
                resultMap.put("code", "1");
                resultMap.put("picUrl", picUrl);
                return resultMap;
            } else if ("10002".equals(code)) { // 如果AccessToken过期，清除Redis中缓存的AccessToken值
                redisCache.deleteObject(YINGSHI_VARIABLE + ":ACCESSTOKEN");
                resultMap.put("code", "2");
                resultMap.put("message", "AccessToken过期，请重新操作");
                return resultMap;
            } else {
                resultMap.put("code", "2");
                resultMap.put("message", "设备维护失败，" + YS_CLOUD_ERROR_CODE.get(code));
                return resultMap;
            }
        } catch (IOException e) {
            fileLog.error("设备抓拍图片失败", e);
            resultMap.put("code", "2");
            resultMap.put("message", "设备抓拍图片失败，" + e.getMessage());
            return resultMap;
        }
    }

    /**
     * 开通直播功能
     *
     * @param deviceSerial
     *            设备序列号
     */
    public static Map<String, String> openVideoLive(String deviceSerial,Integer passageway) {
        Map<String, String> resultMap = new HashMap<String, String>();
        // code = 1，代码成功，否则代表失败
        resultMap.put("code", "1");
        if (StringUtils.isEmpty(deviceSerial)) {
            resultMap.put("code", "2");
            resultMap.put("message", "设备序列号不能为空");
            return resultMap;
        }
        Map<String, String> atMap = getAccessToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultMap.put("code", "2");
            resultMap.put("message", atMap.get("message"));
            return resultMap;
        }
        if (StringUtils.isEmpty(accessToken)) {
            resultMap.put("code", "2");
            resultMap.put("message", "accessToken获取失败");
            return resultMap;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("accessToken", accessToken);
            paramMap.put("source", deviceSerial + ":"+passageway);
            HttpRespons resp = httpRequ.sendPost(YS_CLOUD_URL + "/api/lapp/live/video/open", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            // 60016加密未开启，无需关闭
            if ("200".equals(code)) {
                JSONArray data = paramJson.getJSONArray("data");
                if (data.size() > 0) {
                    JSONObject dataVideo = data.getJSONObject(0);
                    String ret = dataVideo.getString("ret");
                    if ("200".equals(ret) || "60062".equals(ret)) {
                        resultMap.put("code", "1");
                        return resultMap;
                    } else {
                        resultMap.put("code", "2");
                        resultMap.put("message", "开通直播功能失败，" + YS_CLOUD_ERROR_CODE.get(ret));
                    }
                } else {
                    resultMap.put("code", "2");
                    resultMap.put("message", "开通直播功能失败");
                }
            } else if ("10002".equals(code)) { // 如果AccessToken过期，清除Redis中缓存的AccessToken值
                redisCache.deleteObject(YINGSHI_VARIABLE + ":ACCESSTOKEN");
                resultMap.put("code", "2");
                resultMap.put("message", "AccessToken过期，请重新操作");
                return resultMap;
            } else {
                resultMap.put("code", "2");
                resultMap.put("message", "开通直播功能失败，" + YS_CLOUD_ERROR_CODE.get(code));
                return resultMap;
            }
        } catch (IOException e) {
            fileLog.error("开通直播功能失败", e);
            resultMap.put("code", "2");
            resultMap.put("message", "开通直播功能失败，" + e.getMessage());
            return resultMap;
        }
        return resultMap;
    }

    /**
     * 根据设备序列号获取指定有效期的直播地址
     *
     * @param deviceSerial
     *            设备序列号
     */
    public static Map<String, String> getLiveAddress(String deviceSerial,Integer passageway) {
        Map<String, String> resultMap = new HashMap<String, String>();
        // code=1，代码成功，否则代表失败
        resultMap.put("code", "1");
        if (StringUtils.isEmpty(deviceSerial)) {
            resultMap.put("code", "2");
            resultMap.put("message", "设备序列号不能为空");
            return resultMap;
        }
        /*
        //视频观看地址不存储在缓存中
        RedisCacheElement hMapElement = (RedisCacheElement) redisCache.get(YINGSHI_VARIABLE + ":" + deviceSerial);
        String rc = hMapElement == null ? null : (String) hMapElement.get();
        if (!StringUtils.isEmpty(rc)) {
            JSONObject paramJson = JSON.parseObject(rc);

            JSONArray dataArray = paramJson.getJSONArray("data");
            if (dataArray != null && dataArray.size() > 0) {
                JSONObject dataJson = dataArray.getJSONObject(0);
                resultMap.put("code", "1");
                resultMap.put("liveAddress", dataJson.getString("liveAddress"));
                resultMap.put("hdAddress", dataJson.getString("hdAddress"));
                resultMap.put("rtmp", dataJson.getString("rtmp"));
                resultMap.put("rtmpHd", dataJson.getString("rtmpHd"));
                return resultMap;
            }
        }*/

        Map<String, String> atMap = getAccessToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultMap.put("code", "2");
            resultMap.put("message", atMap.get("message"));
            return resultMap;
        }
        if (StringUtils.isEmpty(accessToken)) {
            resultMap.put("code", "2");
            resultMap.put("message", "accessToken获取失败");
            return resultMap;
        }

        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("accessToken", accessToken);
            paramMap.put("source", deviceSerial + ":"+passageway);
            paramMap.put("expireTime", String.valueOf(6 * 24 * 3600));
            HttpRespons resp = httpRequ.sendPost(YS_CLOUD_URL + "/api/lapp/live/address/get", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("200".equals(code)) {
                resultMap.put("code", "1");
                JSONArray dataArray = paramJson.getJSONArray("data");
                if (dataArray == null || dataArray.size() < 1) {
                    resultMap.put("code", "2");
                    resultMap.put("message", "获取直播地址失败");
                    return resultMap;
                }
                JSONObject dataJson = dataArray.getJSONObject(0);
                resultMap.put("hls", dataJson.getString("hls"));
                resultMap.put("hlsHd", dataJson.getString("hlsHd"));
                resultMap.put("rtmp", dataJson.getString("rtmp"));
                resultMap.put("rtmpHd", dataJson.getString("rtmpHd"));

                //redisCache.put(YINGSHI_VARIABLE + ":" + deviceSerial, resultContent);
                return resultMap;
            } else if ("10002".equals(code)) { // 如果AccessToken过期，清除Redis中缓存的AccessToken值
                //redisCache.evict(YINGSHI_VARIABLE + ":ACCESSTOKEN");
                resultMap.put("code", "2");
                resultMap.put("message", "accessToken获取失败");
                return resultMap;
            } else {
                resultMap.put("code", "2");
                resultMap.put("message", "获取直播地址失败，" + YS_CLOUD_ERROR_CODE.get(code));
                return resultMap;
            }
        } catch (IOException e) {
            fileLog.error("新增视频设备失败", e);
        }
        return null;
    }

    /**
     * 开始云台控制
     *
     * @param deviceSerial
     *            设备序列号
     * @param direction
     *            操作命令：0-上，1-下，2-左，3-右，4-左上，5-左下，6-右上，7-右下，8-放大，9-缩小，10-近焦距，11-
     *            远焦距
     * @param speed
     *            云台速度：0-慢，1-适中，2-快
     */
    public static ResultData startPtz(String deviceSerial, String direction, String speed, String channelNo ) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        if (StringUtils.isEmpty(deviceSerial)) {
            resultData.setMessage("设备序列号不能为空");
            return resultData;
        }
        if (StringUtils.isEmpty(direction) || StringUtils.isEmpty(speed)) {
            resultData.setMessage("操作命令与云台速度不能为空");
            return resultData;
        }
        if (!DIRECTION_OPER_LIST.contains(direction.trim())) {
            resultData.setMessage("操作命令不正确");
            return resultData;
        }
        if (!SPEED_LIST.contains(speed.trim())) {
            resultData.setMessage("云台速度不正确");
            return resultData;
        }
        Map<String, String> atMap = getAccessToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage( atMap.get("message"));
            return resultData;
        }
        if (StringUtils.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("accessToken", accessToken);
            paramMap.put("deviceSerial", deviceSerial);
            paramMap.put("channelNo", channelNo);
            paramMap.put("direction", direction);
            paramMap.put("speed", speed);
            HttpRespons resp = httpRequ.sendPost(YS_CLOUD_URL + "/api/lapp/device/ptz/start", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            String msg = paramJson.getString("msg");
            if ("200".equals(code)) {
                resultData.setCode(code);
                resultData.setMessage(msg);
                return resultData;
            } else if ("10002".equals(code)) { // 如果AccessToken过期，清除Redis中缓存的AccessToken值
                redisCache.deleteObject(YINGSHI_VARIABLE + ":ACCESSTOKEN");
                resultData.setCode("500");
                resultData.setMessage("AccessToken过期，请重新操作");
                return resultData;
            } else {
                resultData.setCode("500");
                resultData.setMessage(msg);
                return resultData;
            }
        } catch (IOException e) {
            fileLog.error("开始云台控制失败", e);
            resultData.setMessage("开始云台控制失败，" + e.getMessage());
            return resultData;
        }
    }

    /**
     * 停止云台控制
     *
     * @param deviceSerial
     *            设备序列号
     */
    public static ResultData stopPtz(String deviceSerial, String direction, String channelNo) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        if (StringUtils.isEmpty(deviceSerial)) {
            resultData.setMessage("设备序列号不能为空");
            return resultData;
        }
        Map<String, String> atMap = getAccessToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage( atMap.get("message"));
            return resultData;
        }
        if (StringUtils.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("accessToken", accessToken);
            paramMap.put("deviceSerial", deviceSerial);
            paramMap.put("channelNo", channelNo);
            paramMap.put("direction",direction);
            HttpRespons resp = httpRequ.sendPost(YS_CLOUD_URL + "/api/lapp/device/ptz/stop", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("200".equals(code)) {
                resultData.setCode("1");
                resultData.setData(paramJson);
                return resultData;
            } else if ("10002".equals(code)) { // 如果AccessToken过期，清除Redis中缓存的AccessToken值
                redisCache.deleteObject(YINGSHI_VARIABLE + ":ACCESSTOKEN");
                resultData.setMessage( "AccessToken过期，请重新操作");
                return resultData;
            } else {
                resultData.setMessage("停止云台控制失败，" + YS_CLOUD_ERROR_CODE.get(code));
                return resultData;
            }
        } catch (IOException e) {
            fileLog.error("停止云台控制失败", e);
            resultData.setMessage("停止云台控制失败，" + e.getMessage());
            return resultData;
        }
    }


    /**
     * 抓拍图片
     *
     * @param wsStartPTZControl
     *            设备序列号
     */
    public static ResultData capturePic(WsStartPTZControl wsStartPTZControl) {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        if (StringUtils.isNull(wsStartPTZControl)||StringUtils.isEmpty(wsStartPTZControl.getSerialNumber())) {
            resultData.setMessage("设备序列号不能为空");
            return resultData;
        }
        Map<String, String> atMap = getAccessToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage( atMap.get("message"));
            return resultData;
        }
        if (StringUtils.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("accessToken", accessToken);
            paramMap.put("deviceSerial", wsStartPTZControl.getSerialNumber());
            paramMap.put("channelNo", wsStartPTZControl.getChannelNumber());
            HttpRespons resp = httpRequ.sendPost(YS_CLOUD_URL + "/api/lapp/device/capture", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("200".equals(code)) {
                resultData.setCode("1");
                resultData.setData(paramJson);
                return resultData;
            } else if ("10002".equals(code)) { // 如果AccessToken过期，清除Redis中缓存的AccessToken值
                redisCache.deleteObject(YINGSHI_VARIABLE + ":ACCESSTOKEN");
                resultData.setMessage( "AccessToken过期，请重新操作");
                return resultData;
            } else {
                resultData.setMessage("图片抓取失败，" + YS_CLOUD_ERROR_CODE.get(code));
                return resultData;
            }
        } catch (IOException e) {
            fileLog.error("图片抓取失败", e);
            resultData.setMessage("图片抓取失败，" + e.getMessage());
            return resultData;
        }
    }

    /**
     * 获取摄像头列表
     *
     *
     */
    public static ResultData getCameraList() {
        ResultData resultData = new ResultData();
        resultData.setCode("0");
        Map<String, String> atMap = getAccessToken();
        String accessToken = null;
        if ("1".equals(atMap.get("code"))) {
            accessToken = atMap.get("accessToken");
        } else {
            resultData.setMessage( atMap.get("message"));
            return resultData;
        }
        if (StringUtils.isEmpty(accessToken)) {
            resultData.setMessage("accessToken获取失败");
            return resultData;
        }
        try {
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("accessToken", accessToken);
            paramMap.put("pageStart", "1");
            paramMap.put("pageSize", String.valueOf(Integer.MAX_VALUE));
            HttpRespons resp = httpRequ.sendPost(YS_CLOUD_URL + "/api/lapp/camera/list", paramMap);
            String resultContent = resp.getContent();
            JSONObject paramJson = JSON.parseObject(resultContent);
            String code = paramJson.getString("code");
            if ("200".equals(code)) {
                resultData.setCode("1");
                resultData.setData(paramJson);
                return resultData;
            } else if ("10002".equals(code)) { // 如果AccessToken过期，清除Redis中缓存的AccessToken值
                redisCache.deleteObject(YINGSHI_VARIABLE + ":ACCESSTOKEN");
                resultData.setMessage( "AccessToken过期，请重新操作");
                return resultData;
            } else {
                resultData.setMessage("获取摄像头列表失败，" + YS_CLOUD_ERROR_CODE.get(code));
                return resultData;
            }
        } catch (IOException e) {
            fileLog.error("获取摄像头列表失败", e);
            resultData.setMessage("获取摄像头列表失败，" + e.getMessage());
            return resultData;
        }
    }

}
