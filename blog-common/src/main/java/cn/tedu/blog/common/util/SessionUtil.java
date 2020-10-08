package cn.tedu.blog.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/8 12:16
 */
public class SessionUtil {
    public static String getSessionId(HttpServletRequest req){
        String sessionId = req.getHeader("Cookie");
        return sessionId;
    }
}
