package com.jlkj.framework.web.domain;

import com.jlkj.common.constant.HttpStatus;
import com.jlkj.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 操作消息提醒
 *
 * @author jlkj
 */
@ApiModel(description = "响应数据")
public class AjaxResult extends HashMap<String, Object>implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 状态码 */
    @ApiModelProperty(value = "状态码",example = "200,成功")
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    @ApiModelProperty(value = "错误信息",example = "操作失败")
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    @ApiModelProperty(value = "返回对象")
    public static final String DATA_TAG = "data";

    /** 总记录数 */
    @ApiModelProperty(value = "总记录数")
    public static final String TOTAL_TAG = "total";

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult()
    {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     */
    public AjaxResult(int code, String msg)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public AjaxResult(int code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data))
        {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     * @param data 总记录数
     */
    public AjaxResult(int code, String msg, Object data, Integer total)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data))
        {
            super.put(DATA_TAG, data);
        }
        super.put(TOTAL_TAG, total);
    }


    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success()
    {
        return AjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static AjaxResult success(Object data)
    {
        return AjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg)
    {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }


    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data, Integer total)
    {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data, total);
    }


    /**
     * 返回错误消息
     *
     * @return
     */
    public static AjaxResult error()
    {
        return AjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AjaxResult error(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.ERROR, msg, data);
    }



    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(int code, String msg)
    {
        return new AjaxResult(code, msg, null);
    }

    public static String getCodeTag() {
        return CODE_TAG;
    }

    public static String getMsgTag() {
        return MSG_TAG;
    }

    public static String getDataTag() {
        return DATA_TAG;
    }
}
