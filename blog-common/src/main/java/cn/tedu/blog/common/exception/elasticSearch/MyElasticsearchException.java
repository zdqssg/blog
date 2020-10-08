package cn.tedu.blog.common.exception.elasticSearch;

import java.io.IOException;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/7 21:24
 */
public class MyElasticsearchException extends RuntimeException {
    public MyElasticsearchException() {
        super();
    }

    public MyElasticsearchException(String message) {
        super(message);
    }

    public MyElasticsearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyElasticsearchException(Throwable cause) {
        super(cause);
    }

    protected MyElasticsearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
