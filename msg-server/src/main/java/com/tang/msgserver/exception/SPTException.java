package com.tang.msgserver.exception;


import com.tang.msgserver.util.CommonUtil;

/**
 * 自定义异常类
 *
 * @ClassName:SPTException
 * @Description:
 * @author: lurufeng
 * @company: 柠檬科技
 * @date: 2017/7/30
 * @version: v2.7
 */
public class SPTException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private String message;

    /**
     * 方法描述:重写父类带参构造方法
     *
     * @param message:创建异常时指定的异常信息 date:2013-12-31
     *                             add by: liu_tao@xwtec.cn
     */
    public SPTException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * 方法描述:重写父类无参构造方法
     * date:2013-12-31
     * add by: liu_tao@xwtec.cn
     */
//	public SPTException() {
//		super();
//    }

    /**
     * 方法描述:重写父类带参构造方法
     *
     * @param throwable:异常对象 date:2013-12-31
     *                       add by: liu_tao@xwtec.cn
     */
    public SPTException(Throwable throwable) {
        super(throwable);
        this.message = throwable.getMessage();
    }

    /**
     * 方法描述:重写父类带参构造方法
     *
     * @param message:创建异常时指定的异常信息
     * @param throwable:异常对象       date:2013-12-31
     *                             add by: liu_tao@xwtec.cn
     */
    public SPTException(String message, Throwable throwable) {
        super(message, throwable);
        if (!CommonUtil.isEmpty(message)) {
            this.message = message;
        } else {
            this.message = throwable.getMessage();
        }
    }

    /**
     * 方法描述:重写父类取消息方法
     *
     * @return 错误消息
     * date:2013-12-31
     * add by: liu_tao@xwtec.cn
     */
    public String getMessage() {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(this.message + "\n");
        StackTraceElement[] stacks = super.getStackTrace();
        if (stacks != null) {
            for (StackTraceElement stack : stacks) {
                errorMessage.append("\t" + stack.toString() + "\n");
            }
        }
        return errorMessage.toString();
    }
}
