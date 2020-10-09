package cn.tedu.blog.common.exception.service;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/9 4:49
 */
public class BlogInsetException extends ServiceException {
    public BlogInsetException() {
        super();
    }

    public BlogInsetException(String message) {
        super(message);
    }

    public BlogInsetException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlogInsetException(Throwable cause) {
        super(cause);
    }

    protected BlogInsetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
