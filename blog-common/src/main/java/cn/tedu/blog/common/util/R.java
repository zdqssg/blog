package cn.tedu.blog.common.util;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @email zdq247209@163.com
 * @date 2020/9/10 10:33
 */
@Data
public class R<T> implements Serializable {
    /**
     * 响应时的状态码
     */
    private Integer state;
    /**
     * 操作的提示信息
     */
    private String message;
    /**
     * 返回的数据
     */
    private T data;

    public R() {
        super();
    }

    public R(Integer state) {
        this.state = state;
    }


    public static R ok() {
        return new R(State.OK);
    }


    public static <T> R ok(T data) {
        R r = new R();
        r.setState(State.OK);
        r.setData(data);
        return r;
    }

    public static R failure(Integer state, Throwable e) {
        R r = new R();
        r.setState(state);
        r.setMessage(e.getMessage());
        return r;
    }

    public interface State {

        /**
         * 正确
         */
        Integer OK = 2000;
        /**
         * 未知的异常
         */
        Integer ERR_UNKNOWN = 2404;
        /**
         *
         */
        Integer ERR_BLOG_NO_BIND = 2500;
        /**
         * 3000-4000
         * 微信app的异常
         */
        Integer ERR_WE_CHAT_LOGIN_SESSION = 3400;
        /**
         * 4000-5000 ElasticSearch的异常
         */
        //无结果
        Integer ERR_ES_NO_Find = 4404;
        /**
         * 5000-6000 博客的异常
         */
        //添加博客失败
        Integer ERR_BLOG_INSERT_EXCEPTION = 5555;
    }
}
