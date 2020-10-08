package cn.tedu.blog.common.exception.weChatApp;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/7 16:52
 */
public class WeChatAppException extends RuntimeException {
    public WeChatAppException() {
        super();
    }

    public WeChatAppException(String message) {
        super(message);
    }

    public WeChatAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeChatAppException(Throwable cause) {
        super(cause);
    }

    protected WeChatAppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
