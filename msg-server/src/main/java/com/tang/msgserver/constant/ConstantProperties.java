package com.tang.msgserver.constant;

/**
 * @ClassName:${CLASSNAME}
 * @Description:
 * @author: lurufeng
 * @company: 柠檬科技
 * @date: 2017/7/30
 * @version: v2.7
 */
public interface ConstantProperties {

    public interface  SendSMS{
        public static int ERROR_SENDTIME=101;
        public static String VERIFIYCODE="888888";//默认6位数验证码;
        public static String SMSERROR_TIPS="2分钟内只允许发送一次验证码";
    }

    public interface ResultCode {


        /**
         * 参数不正确
         */

        public static final int ERROR_PARAM=203;

        /**
         * 内部错误，未知异常
         */
        public static final int ERROR_OTHER=999;
        /**
         *未知异常内部错误
         */
        public static final String ERROR_OTHER_DESC="内部错误,请联系系统管理员";

        /** 接口调用成功返回码 **/
        public static final int RESP_SUCCESS = 0;

        /** 接口调用失败返回码 **/
        public static final String RESP_ERROR = "1";

        /** 接口调用异常返回码 **/
        public static final String RESP_EXCEPTION = "-1";

        /** 接口调用其他异常码，扩展用 **/
        public static final String RESP_OTHER = "-2";

        /** 状态发生变化返回码 **/
        public static final String STATUS_CHANGED_ERROR = "-999";

        /** 信息发生变化返回码 **/
        public static final String INFO_CHANGED_ERROR = "-888";

        /** feign远程调用异常 **/
        public static final String FEIGN_REQ_ERROR = "-100";
    }

    public interface HttpStatus {
        /** http请求成功返回码 **/
        public static final String HTTP_STATUS_200 = "200";

        /** http请求异常返回码 **/
        public static final String HTTP_STATUS_500 = "500";
    }

    /**
     * redis相关常量
     */
    public interface RedisProps {
        /**
         * WAIT_TIME 获取不到redis资源,等待500ms
         */
        public static final int WAIT_TIME = 500;

        /**
         * 连接redis失败
         */
        public static final int CONNECT_EXCEPTION=505;
        /**
         * CYCLE_TIMES 连续获取3次失败,则不再获取
         */
        public static final int CYCLE_TIMES = 3;
    }

    public interface  WangJianProps
    {
        /**
         * 连接网建系统失败
         */
        public static final int CONNECT_EXCEPTION=505;
    }

    public interface ResponseDesc {

        /**
         * 接口调用成功返回描述
         */
        public static final String SUCCESS_DESC = "接口调用成功！";

        /**
         * 接口调用失败返回描述
         */
        public static final String ERROR_DESC = "接口调用失败！";

        /**
         * 接口调用异常返回描述
         */
        public static final String EXCEPTION_DESC = "接口调用异常！";

        /**
         * feign调用异常
         */
        public static final String FEIGN_REQ_ERROR_DESC = "SERVER ERROR!";

    }

    /**
     * 框架静态参数说明<br>
     * <p>
     */
    public interface WebKey {

        public static final int RANDOM_NUM_PWD = 6;//生成默认随机数密码的位数

        public static final String CONTENT_TYPE = "application/json; charset=UTF-8";// ContentType类型1

        public static final String CONTENT_TYPE_HTML = "text/html;charset=UTF-8";// ContentType类型：text/html

        public static final String CHARACTER_ENCODING_UTF8 = "UTF-8";// 默认编码格式

        public static final String CHARACTER_ENCODING_GBK = "GBK";// 默认编码格式

        public static final String CHARACTER_ENCODING_ISO_8859_1 = "ISO-8859-1";// 默认编码格式

        public static final String PARAM_KEY = "jsonStr";// 获取请求参数key

        public static final int BUFFER_SIZE = 1024;// 缓冲区大小

        public static final long CACHE_TIME = 1000 * 60 * 60 * 6;// 默认缓存时间6小时

        public static final String FAIL = "1";// 接口处理失败状态码

        public static final String SUCCESS = "0";// 接口处理成功状态码

        public static final String FAIL_MSG = "Interface call failed!";// 接口处理失败消息

        public static final String SUCCESS_MSG = "Interface call success!";// 接口处理成功消息

        public static final String NETWORK_BUSY = "The network is busy!";// 网络繁忙

        public static final int FAIL_NUMBER = 1;// 失败状态码

        public static final int SUCCESS_NUMBER = 0;// 成功状态码

        /**
         * add by houxu at 2013-09-16 18:51
         * to defined status code
         */
        /**
         * STATUS_200 200—调用成功
         */
        public static final String STATUS_200 = "200";
        /**
         * STATUS_403 403---无权限调用
         */
        public static final String STATUS_403 = "403";
        /**
         * STATUS_404 404---接口不存在
         */
        public static final String STATUS_404 = "404";
        /**
         * STATUS_408 408---请求服务系统接口超时
         */
        public static final String STATUS_408 = "408";
        /**
         * STATUS_500 500---请求服务系统接口出现错误
         */
        public static final String STATUS_500 = "500";
        /**
         * STATUS_996 996---超出流量控制
         */
        public static final String STATUS_996 = "996";
        /**
         * STATUS_997 997---未经授权IP
         */
        public static final String STATUS_997 = "997";
        /**
         * STATUS_998 998---签名错误
         */
        public static final String STATUS_998 = "998";
        /**
         * STATUS_999 999---发生其它未知异常导致失败
         */
        public static final String STATUS_999 = "999";

    }

    /**
     * 返回报文
     */
    public static interface PacketParameter {
        /**
         * KEY 接口名称
         */
        public static final String KEY = "key";

        /**
         * REQ_SEQ 调用序号
         */
        public static final String REQ_SEQ = "req_seq";

        /**
         * STATUS 调用状态码(200/403/404/408/500)
         */
        public static final String STATUS = "status";

        /**
         * RESTIME 执行时间
         */
        public static final String RESTIME = "resTime";

        /**
         * RESP_CODE
         */
        public static final String RESP_CODE = "resp_code";
        /**
         * TICKET令牌
         */
        public static final String TICKET = "ticket";
        /**
         * 返回状态码
         */
        public static final String RESULT="result";
        /**
         * 返回描述
         */
        public static final String DESC="desc";
        /**
         * 返回结果详情
         */
        public static final String DETAIL="detail";

        /**
         * DATA 数据体
         */
        public static final String DATA = "data";

        /**
         * RESP_DESC 业务操作的返回结果, 0表示操作正确, 其它表示错误, 错误代码的说明参考ts_result_code表
         */
        public static final String RESP_DESC = "resp_desc";

        /**
         * LOG 转发日志,在前台html界面转发注入的 包含在body内部
         */
        public static final String LOG = "log";

        /**
         * HEADER 头部
         */
        public static final String HEADER = "header";

        /**
         * BODY 包体
         */
        public static final String BODY = "body";
    }

    /**
     * 请求头信息
     */
    public static interface RequestHeader {

        public static final String INVOKER_ID = "invokerId";

        public static final String INVOKER_TYPE = "invokerType";

    }


    /**
     * 渠道
     */
    public interface CHANNEL {

        /**
         * web端
         */
        public static final String CHANNEL_WEB = "0";

        /**
         * 微信端
         */
        public static final String CHANNEL_WECHAT = "1";

        /**
         * 安卓端
         */
        public static final String CHANNEL_ANDROID = "2";

        /**
         * IOS端
         */
        public static final String CHANNEL_IOS = "3";
    }




}
