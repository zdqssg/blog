package cn.tedu.blog.common.vo.wechat;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/7 16:44
 */
@Data
public class WeChatSession implements Serializable {
    private String openid;
    private String sessionKey;
}
