package cn.tedu.blog.api.blog.utils;

import cn.tedu.blog.common.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/8 12:12
 */
@Component
public class RedisSessionUtil {
    @Autowired
    private RedisUtils redisUtils;

    public  String redisGetOpenId(HttpServletRequest request) throws Exception {
        String sessionId = SessionUtil.getSessionId(request);

      return (String) redisUtils.getHash(sessionId,"sessionId");
    }
}
