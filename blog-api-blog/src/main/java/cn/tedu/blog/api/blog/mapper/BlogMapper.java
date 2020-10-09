package cn.tedu.blog.api.blog.mapper;

import cn.tedu.blog.common.model.Blog;
import cn.tedu.blog.common.vo.blog.BlogInfoVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 博客文章 Mapper 接口
 * </p>
 *
 * @author zdq247209@163.com
 * @since 2020-10-07
 */
@Repository
public interface BlogMapper extends BaseMapper<Blog> {
    /**
     * 获取博客文章列表
     * @return
     */
    List<BlogInfoVo> selectIndexBlog();

    BlogInfoVo getBlogDetail(Integer id);
}
