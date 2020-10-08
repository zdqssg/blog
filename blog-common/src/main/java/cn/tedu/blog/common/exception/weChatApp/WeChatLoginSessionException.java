package cn.tedu.blog.common.exception.weChatApp;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/7 16:53
 */
public class WeChatLoginSessionException extends WeChatAppException {
    public WeChatLoginSessionException() {
        super();
    }

    public WeChatLoginSessionException(String message) {
        super(message);
    }

    public WeChatLoginSessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeChatLoginSessionException(Throwable cause) {
        super(cause);
    }

    protected WeChatLoginSessionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
