package cn.tedu.blog.api.blog.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/9 4:34
 */
@Data
@Accessors(chain = true)
public class BlogDto implements Serializable {
    private String title;
    private String content;
}
