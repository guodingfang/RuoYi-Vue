package com.jlkj.framework.redis;

import com.alibaba.fastjson.JSONObject;
import com.jlkj.common.constant.Constants;
import com.jlkj.common.exception.CustomException;
import com.jlkj.common.utils.http.HttpRequester;
import com.jlkj.common.utils.http.HttpRespons;
import com.jlkj.common.utils.spring.SpringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author pyy
 * @Date 2020/7/16 15:07
 */
public class BaiDuApiUtil {
    /**
     * 全球逆地理编码
     */
    private static final String REVERSE_GEOCODING_URL = "http://api.map.baidu.com/reverse_geocoding/v3/";

    /**
     * redis缓存对象
     */
    private static RedisCache redisCache;

    static {
        redisCache = SpringUtils.getBean(RedisCache.class);
    }

    /**
     *0	正常	服务请求正常召回
     * 1	服务器内部错误	该服务响应超时或系统内部错误，请留下联系方式
     * 10	上传内容超过8M	Post上传数据不能超过8M
     * 101	AK参数不存在	请求消息没有携带AK参数
     * 102	MCODE参数不存在，mobile类型mcode参数必需	对于Mobile类型的应用请求需要携带mcode参数，该错误码代表服务器没有解析到mcode
     * 200	APP不存在，AK有误请检查再重试	根据请求的AK，找不到对应的APP
     * 201	APP被用户自己禁用，请在控制台解禁
     * 202	APP被管理员删除	恶意APP被管理员删除
     * 203	APP类型错误	当前API控制台支持Server(类型1), Mobile(类型2, 新版控制台区分为Mobile_Android(类型21)及Mobile_IPhone（类型22））及Browser（类型3），除此之外其他类型认为是APP类型错误
     * 210	APP IP校验失败	在申请SERVER类型应用的时候选择IP校验，需要填写IP白名单，如果当前请求的IP地址不在IP白名单或者不是0.0.0.0/0就认为IP校验失败
     * 211	APP SN校验失败	SERVER类型APP有两种校验方式IP校验和SN校验，当用户请求的SN和服务端计算出来的SN不相等的时候提示SN校验失败
     * 220	APP Referer校验失败	浏览器类型的APP会校验referer字段是否存且切在referer白名单里面，否则返回该错误码
     * 230	APP Mcode码校验失败	服务器能解析到mcode，但和数据库中不一致，请携带正确的mcode
     * 240	APP 服务被禁用	用户在API控制台中创建或设置某APP的时候禁用了某项服务，若需开通权限，可进入API控制台为AK勾选对应服务
     * 250	用户不存在	根据请求的user_id, 数据库中找不到该用户的信息，请携带正确的user_id
     * 251	用户被自己删除	该用户处于未激活状态
     * 252  用户被管理员删除	恶意用户被加入黑名单
     * 260	服务不存在	服务器解析不到用户请求的服务名称
     * 261	服务被禁用	该服务已下线，请在控制台确认是否有该服务的权限。
     * 301	永久配额超限，限制访问	配额超限，如果想增加配额请联系我们
     * 302	天配额超限，限制访问	配额超限，如果想增加配额请联系我们
     * 401	当前并发量已经超过约定并发配额，限制访问	并发控制超限，请控制并发量或联系我们
     * 402	当前并发量已经超过约定并发配额，并且服务总并发量也已经超过设定的总并发配额，限制访问
     */
    public static JSONObject getAddress(double longitude, double latitude) {
        try {
            String ak = redisCache.getCacheObject(Constants.SYS_CONFIG_KEY + "ws.baidu.api.ak");
            Map<String, String> param = new HashMap<>();
            param.put("ak", ak);
            param.put("output", "json");
            param.put("coordtype", "wgs84ll");
            param.put("location", latitude + "," + longitude );
            HttpRequester httpRequ = new HttpRequester();
            httpRequ.setDefaultContentEncoding("UTF-8");
            HttpRespons httpRespons = httpRequ.sendGet(REVERSE_GEOCODING_URL, param);
            String resultContent = httpRespons.getContent();
            JSONObject jsonObject = JSONObject.parseObject(resultContent);
            return jsonObject;
        } catch (Exception e) {
            throw new CustomException("系统异常，请稍后重试");
        }
    }

    public static String parseAreaCode (double longitude, double latitude){
        String areaCode = "";
        JSONObject address = BaiDuApiUtil.getAddress(longitude, latitude);
        if (0==address.getInteger("status")) {
            areaCode=address.getJSONObject("result").getJSONObject("addressComponent").getString("adcode");
        }
        return areaCode;
    }


}
