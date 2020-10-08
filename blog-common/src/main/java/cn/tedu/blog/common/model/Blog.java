package cn.tedu.blog.common.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 博客文章
 * </p>
 *
 * @author zdq247209@163.com
 * @since 2020-10-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("blog")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章标题
     */
    @TableField("blog_title")
    private String blogTitle;

    /**
     * 文章副标题
     */
    @TableField("blog_sub_title")
    private String blogSubTitle;

    /**
     * 文章作者
     */
    @TableField("blog_author")
    private String blogAuthor;

    /**
     * 文章内容，支持HTML格式
     */
    @TableField("blog_content")
    private String blogContent;

    /**
     * 文章阅读量
     */
    @TableField("blog_view_times")
    private Integer blogViewTimes;

    /**
     * 标签
     */
    @TableField("blog_label")
    private String blogLabel;

    /**
     * 文章状态
     */
    @TableField("blog_state")
    private Integer blogState;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 文章发表时间
     */
    @TableField("blog_post_time")
    private LocalDateTime blogPostTime;

    /**
     * 文章修改时间
     */
    @TableField("blog_update_time")
    private LocalDateTime blogUpdateTime;


}
