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
 * 
 * </p>
 *
 * @author zdq247209@163.com
 * @since 2020-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_focus_blog")
public class UserFocusBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户唯一标识
     */
    @TableField("user_open_id")
    private String userOpenId;

    /**
     * 博客Id
     */
    @TableField("blog_id")
    private Long blogId;

    /**
     * 关注状态   0未关注  1已关注
     */
    @TableField("is_focus")
    private Integer isFocus;

    /**
     * 创立时间
     */
    @TableField("post_time")
    private LocalDateTime postTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


}
