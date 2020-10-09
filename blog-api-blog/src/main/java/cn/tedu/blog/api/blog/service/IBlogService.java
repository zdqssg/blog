package cn.tedu.blog.api.blog.service;

import cn.tedu.blog.api.blog.dto.BlogDto;
import cn.tedu.blog.common.model.Blog;
import cn.tedu.blog.common.util.R;
import cn.tedu.blog.common.vo.blog.BlogInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 博客文章 服务类
 * </p>
 *
 * @author zdq247209@163.com
 * @since 2020-10-07
 */
public interface IBlogService extends IService<Blog> {


    R getIndexBlogByMySql(Integer pageNum );


    R postBlog(BlogDto blogDto );

    R getBlogDetail(Integer id);
}
