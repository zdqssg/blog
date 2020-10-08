package cn.tedu.blog.gateway.controller;

import cn.tedu.blog.common.exception.weChatApp.WeChatLoginSessionException;
import cn.tedu.blog.common.model.User;
import cn.tedu.blog.common.util.R;
import cn.tedu.blog.common.vo.wechat.Code;
import cn.tedu.blog.common.vo.wechat.WeChatSession;
import cn.tedu.blog.gateway.service.IUserService;
import cn.tedu.blog.gateway.utils.RedisUtils;
import com.alibaba.druid.wall.violation.ErrorCode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import freemarker.template.utility.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/8 3:35
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class UserLoginByWeChatController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("/byWeixin")
    public R loginByWeixin(@RequestBody Code code) {
        //得到用户的openId + sessionKey
        JSONObject jsonObject = null;
        try {
            jsonObject = getSessionKeyAndOpenId(code.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(jsonObject.toString());
        System.out.println(jsonObject);
        String openId = jsonObject.getString("openid");

        String sessionKey = jsonObject.getString("session_key");
        //组装结果
        Map<String,Object> resMap = new HashMap<String,Object>();
        //判断openId是否存在用户表中
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openId",openId);
        User one = userService.getOne(queryWrapper);
        if(one==null){
            userService.save(new User().setOpenId(openId));
        }

        //将通过md5生成sessionId（一般是用个操作系统提供的真正随机数算法生成新的session）
        String sessionId = DigestUtils.md5Hex(openId + sessionKey + (new Date().getTime()));
        redisUtils.setHash(sessionId,"sessionId",openId);
        log.info("sessionId{}",sessionId);
        return R.ok(sessionId);

//        System.out.println(code);
////       https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
//        StringBuilder sb = new StringBuilder()
//                .append("https://api.weixin.qq.com/sns/jscode2session?appid=")
//                .append(applicationValues.getBlogAppId())
//                .append("&secret=")
//                .append(applicationValues.getBlogSecret())
//                .append("&js_code=")
//                .append(code.getCode())
//                .append("&grant_type=authorization_code");
//        String url = sb.toString();
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
//        if (!responseEntity.getStatusCode().isError()) {
//            WeChatSession weChatSession = JSONObject.parseObject(responseEntity.getBody(), WeChatSession.class);
//            System.out.println(weChatSession);
//            redisUtils.setValue(weChatSession.getOpenid(), weChatSession.getSessionKey());
//            return R.ok(weChatSession);
//        } else {
//            throw new WeChatLoginSessionException("获取openid和session失败");
//        }
    }

    /**
     * 获取微信小程序的session_key和openid
     *
     * @author hengyang4
     * @param code 微信前端login()方法返回的code
     * @return jsonObject
     *
     * */
    public JSONObject getSessionKeyAndOpenId(String code)throws Exception{
        //微信登录的code值
        String wxCode = code;
        //读取属性文件
        ResourceBundle resourceBundle = ResourceBundle.getBundle("weixin");
        //服务器端调用接口的url
        String requestUrl = resourceBundle.getString("url");
        //封装需要的参数信息
        Map<String,String> requestUrlParam = new HashMap<String,String>();
        //开发者设置中的appId
        requestUrlParam.put("appid",resourceBundle.getString("appId"));
        //开发者设置中的appSecret
        requestUrlParam.put("secret",resourceBundle.getString("appSecret"));
        //小程序调用wx.login返回的code
        requestUrlParam.put("js_code", wxCode);
        //默认参数
        requestUrlParam.put("grant_type", "authorization_code");

        JSONObject jsonObject = JSON.parseObject(sendPost(requestUrl,requestUrlParam));
        return jsonObject;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    public String sendPost(String url, Map<String, ?> paramMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        String param = "";
        Iterator<String> it = paramMap.keySet().iterator();

        while(it.hasNext()) {
            String key = it.next();
            param += key + "=" + paramMap.get(key) + "&";
        }

        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
