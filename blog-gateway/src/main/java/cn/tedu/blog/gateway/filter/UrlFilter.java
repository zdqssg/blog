package cn.tedu.blog.gateway.filter;

import cn.tedu.blog.common.util.SessionUtil;
import cn.tedu.blog.gateway.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/8 5:47
 */
//@WebFilter(urlPatterns = {"/api-blog/v1/**","/api-blog/v1/**"})
@WebFilter(urlPatterns = "/api-blog/v1/*")
@Slf4j
public class UrlFilter implements Filter {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("555555555555555555555555");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //设置跨域请求
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Access-Control-Allow-Origin", "*");

        //获取请求时的sessionId
        String sessionId = SessionUtil.getSessionId((HttpServletRequest) request);
        log.info("sessionId:{}", sessionId);
        //只有在缓存中存在该sessionId才能进行请求
        if (!redisUtils.hasKey(sessionId)) {
            // 登录信息已过期，请重新登录
            log.info("sessionId过滤{}", "登录信息失效，请重新登录");
            response.getWriter().write("401");
            return;
        }

        log.info("sessionId过滤{}", "session验证成功");
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
        System.out.println("555555555555555555555555");
    }
}
