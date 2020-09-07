package com.jlkj.framework.redis;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * @ClassName ResultData
 * @Description 请求回复数据
 * @Author liujie
 * @Date 2020/6/23 18:46
 **/
public class ResultData implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 请求结果code 0=请求失败 1=请求成功 */
    private String code ;
    /** 返回Json数据 */
    private JSONObject data;
    /** 失败信息*/
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultData(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultData(String code, JSONObject data) {
        this.code = code;
        this.data = data;
    }

    public ResultData(String code, JSONObject data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public ResultData() {
    }
}
