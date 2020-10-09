package cn.tedu.blog.common.vo.blog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/8 16:11
 */
@Data
@Accessors(chain = true)
public class BlogInfoVo {
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 文章作者openid
     */
    private String userOpenId;

    /**
     * 文章作者
     */
    private String nickName;
    /**
     * 文章作者头像
     */
    private String avatarUrl;

    /**
     * 是否在关注
     */
    private Integer isFocus=0;
    /**
     * 文章标题
     */
    private String blogTitle;

    /**
     * 文章副标题
     */
    private String blogSubTitle;

    /**
     * 文章内容，支持HTML格式
     */
    private String blogContent;

    /**
     * 文章阅读量
     */
    private Integer blogViewTimes;

    /**
     * 标签
     */
    private String blogLabel;

    /**
     * 文章状态
     */
    private Integer blogState;


    /**
     * 文章发表时间
     */
    private LocalDateTime blogPostTime;


}
