package cn.tedu.blog.api.blog.controller;

import cn.tedu.blog.common.exception.elasticSearch.ElasticsearchNoMoreException;
import cn.tedu.blog.common.exception.elasticSearch.MyElasticsearchException;
import cn.tedu.blog.common.util.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/7 21:27
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MyElasticsearchException.class})
    public R handleException(Throwable e) {
        if (e instanceof ElasticsearchNoMoreException) {
            return R.failure(R.State.ERR_ES_NO_MORE, e);
        } else {
            return R.failure(R.State.ERR_UNKNOWN, e);
        }
    }
}
