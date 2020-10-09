package cn.tedu.blog.common.exception.elasticSearch;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/7 21:25
 */
public class ElasticsearchNoFindException extends MyElasticsearchException{
    public ElasticsearchNoFindException() {
        super();
    }

    public ElasticsearchNoFindException(String message) {
        super(message);
    }

    public ElasticsearchNoFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElasticsearchNoFindException(Throwable cause) {
        super(cause);
    }

    protected ElasticsearchNoFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
