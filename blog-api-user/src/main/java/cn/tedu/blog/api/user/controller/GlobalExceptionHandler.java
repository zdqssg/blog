package cn.tedu.blog.api.user.controller;

import cn.tedu.blog.common.exception.elasticSearch.ElasticsearchNoMoreException;
import cn.tedu.blog.common.exception.elasticSearch.MyElasticsearchException;
import cn.tedu.blog.common.exception.weChatApp.WeChatAppException;
import cn.tedu.blog.common.exception.weChatApp.WeChatLoginSessionException;
import cn.tedu.blog.common.util.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/7 21:27
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({WeChatAppException.class})
    public R handleException(Throwable e){
          if (e instanceof WeChatLoginSessionException){
             return R.failure(R.State.ERR_WE_CHAT_LOGIN_SESSION,e);
          }
          else {
              return R.failure(R.State.ERR_UNKNOWN,e);
          }
    }
}
