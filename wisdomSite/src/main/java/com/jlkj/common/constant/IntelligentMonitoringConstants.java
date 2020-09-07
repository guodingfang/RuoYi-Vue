package com.jlkj.common.constant;

/**
 * 智能监测常量信息
 *
 * @author jlkj
 */
public class IntelligentMonitoringConstants {

    /** 类型：0，企业；1，项目 */
    public static final Integer TYPE_PROJECT = 1;

    /** 类型：0，企业；1，项目 */
    public static final Integer TYPE_ENTERPRISE = 0;

    /** 施工电梯进退场状态-进场 */
    public static final String  IN_SITE ="1";
    /** 施工电梯进退场状态-退场 */
    public static final String  OUT_SITE ="0";


    /** 是 yes*/
    public static final String  YES ="1";
    /** 否 no*/
    public static final String  NO ="0";

    /** 成功*/
    public static final String  SUCCESS ="1";
    /** 失败*/
    public static final String  FAIL ="0";


    /** 监测仪设备类型-塔吊 */
    public static final String  TOWER_CRANE ="1";
    /** 监测仪设备类型-施工电梯 */
    public static final String  ELEVATOR ="2";
    /** 监测仪设备类型-扬尘 */
    public static final String  DUST ="3";
    /** 监测仪设备类型-电能 */
    public static final String  ELECTRIC ="4";
    /** 监测仪设备类型-升降机人脸 */
    public static final String  ELEVATOR_FACE ="14";


    /** 正常*/
    public static final String NORMAL ="0";
    /** 预警*/
    public static final String PRE_ALARMING ="1";
    /** 报警*/
    public static final String ALARMING ="2";


    /** 升降机设备类型-双笼*/
    public static final String DOUBLE_CAGE ="2";

    /** 升降机设备类型-单笼*/
    public static final String SINGLE_CAGE ="1";


    /** 升降机监控设备-左笼标记*/
    public static final String MARK_LEFT_CAGE ="1";

    /** 升降机监控设备-右笼标记*/
    public static final String MARK_RIGHT_CAGE ="2";

    /** 升降机监控设备-单笼笼标记*/
    public static final String MARK_SINGLE_CAGE ="0";



    /** 升降机设备-在线*/
    public static final String ONLINE ="1";

    /** 升降机设备-离线*/
    public static final String OFFLINE ="0";


    /** 设备状态-未启用*/
    public static final String NOT_ENABLED ="0";

    /** 设备状态-启用*/
    public static final String ENABLE ="1";

    /** 设备状态-禁用*/
    public static final String DISABLED ="2";


    /** 安全管理-图片类型-检查图片*/
    public static final Integer CHECK_TYPE = 1;

    /** 安全管理-图片类型-整改图片*/
    public static final Integer RECTIFY_TYPE = 2;

    /** 安全管理-图片类型-验证图片*/
    public static final Integer VERIFY_TYPE = 3;

    /** 安全管理-隐患验证-合格*/
    public static final String PASS = "合格";

    /** 安全管理-隐患验证-不合格*/
    public static final String NOPASS = "不合格";


    /** 工程设备绑定-设备类型-升降机人脸*/
    public static final Integer SJJ_RL = 14;

    /** 工程设备绑定-设备类型-升降机*/
    public static final Integer SJJ = 2;

    /** 视频监控设备-视频地址类型*/
    public static final String LIVE_ADDRESS_TYPR = "hls";


}
