package cn.tedu.blog.common.util;


import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/9 5:06
 */
public class ServletUtils {

    public static final String TEXT_TYPE = "text/plain";
    public static final String JSON_TYPE = "application/json";
    public static final String XML_TYPE = "text/xml";
    public static final String HTML_TYPE = "text/html";
    public static final String JS_TYPE = "text/javascript";
    public static final String EXCEL_TYPE = "application/vnd.ms-excel";

    public static final String AUTHENTICATION_HEADER = "Authorization";


    public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

    public static final String HEADER_ENCODING = "encoding";
    public static final String HEADER_NOCACHE = "no-cache";
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final boolean DEFAULT_NOCACHE = true;


    /**
     * 设置禁止客户端缓存的Header
     *
     * @param response
     */
    public static void setDisableCacheHeader(HttpServletResponse response) {
        // Http 1.0 header
        response.setDateHeader("Expires", 1L);
        response.addHeader("Pragma", "no-cache");
        // Http 1.1 header
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
    }

    /**
     * 设置客户端缓存过期时间 Header
     *
     * @param response
     * @param expiresSeconds
     */
    public static void setExpiresHeader(HttpServletResponse response,
                                        long expiresSeconds) {
        // Http 1.0 header
        response.setDateHeader("Expires", System.currentTimeMillis()
                + expiresSeconds * 1000);
        // Http 1.1 header
        response.setHeader("Cache-Control", "private, max-age="
                + expiresSeconds);
    }

    /**
     * 设置客户端无缓存Header
     *
     * @param response
     */
    public static void setNoCacheHeader(HttpServletResponse response) {
        // Http 1.0 header
        response.setDateHeader("Expires", 0);
        response.addHeader("Pragma", "no-cache");
        // Http 1.1 header
        response.setHeader("Cache-Control", "no-cache");
    }


    public static StringBuffer getRequestURL(HttpServletRequest req) {
        StringBuffer url = new StringBuffer();
        String scheme = req.getScheme();
        int port = req.getServerPort();
        String urlPath = req.getRequestURI();

        url.append(scheme);
        url.append("://");
        url.append(req.getServerName());
        if (((scheme.equals("http")) && (port != 80)) || ((scheme.equals("https")) && (port != 443))) {
            url.append(':');
            url.append(req.getServerPort());
        }

        url.append(urlPath);
        return url;
    }

    private static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    public static String getHeaderByName(String name) {

        return getRequest().getHeader(name);
    }

    public static String getCookie() {
        return getRequest().getHeader("Cookie");
    }
}
