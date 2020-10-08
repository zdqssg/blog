package cn.tedu.blog.common.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("user")
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户唯一标识
     */
    @TableField("openId")
    private String openId;

    /**
     * 用户昵称
     */
    @TableField("nickName")
    private String nickName;

    /**
     * 性别  0女  1男
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 城市
     */
    @TableField("city")
    private String city;

    /**
     * 省
     */
    @TableField("province")
    private String province;

    /**
     * 用户国家
     */
    @TableField("country")
    private String country;

    /**
     * 微信头像
     */
    @TableField("avatarUrl")
    private String avatarUrl;


}
