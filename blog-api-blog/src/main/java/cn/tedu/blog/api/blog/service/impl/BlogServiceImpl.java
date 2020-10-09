package cn.tedu.blog.api.blog.service.impl;


import cn.tedu.blog.api.blog.dto.BlogDto;
import cn.tedu.blog.api.blog.mapper.BlogMapper;
import cn.tedu.blog.api.blog.mapper.UserFocusBlogMapper;
import cn.tedu.blog.api.blog.service.IBlogService;
import cn.tedu.blog.api.blog.utils.RedisUtils;
import cn.tedu.blog.common.exception.service.BlogInsetException;
import cn.tedu.blog.common.model.Blog;
import cn.tedu.blog.common.model.UserFocusBlog;
import cn.tedu.blog.common.util.R;
import cn.tedu.blog.common.vo.blog.BlogInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 博客文章 服务实现类
 * </p>
 *
 * @author zdq247209@163.com
 * @since 2020-10-07
 */
@Service
@Slf4j
public class BlogServiceImpl<T> extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

    @Value("${project.blog-index.pageSize}")
    private Integer pageSize;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private UserFocusBlogMapper focusBlogMapper;


    //    mysql的实现
    @Override
    public R getIndexBlogByMySql(Integer pageNum) {
        log.debug("请求第{}页数据", pageNum);
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        String openId = null;
        try {
            openId = redisUtils.redisGetOpenId();
        } catch (Exception e) {
        }
        PageHelper.startPage(pageNum, pageSize);
        List<BlogInfoVo> blogInfoVoList = blogMapper.selectIndexBlog();

        //组装数据
        QueryWrapper<UserFocusBlog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_open_id", openId)
                .eq("is_focus", 1);
        List<UserFocusBlog> focusBlogList = focusBlogMapper.selectList(queryWrapper);
        if (focusBlogList.size() != 0) {
            for (UserFocusBlog focusBlog : focusBlogList) {
                for (int i = 0; i < blogInfoVoList.size(); i++) {
                    if (blogInfoVoList.get(i).getId().equals(focusBlog.getBlogId())) {
                        blogInfoVoList.get(i).setIsFocus(1);
                    }
                }
            }
        }
        log.debug("博客列表的详细信息{}", blogInfoVoList);
        PageInfo<BlogInfoVo> pageInfo = new PageInfo<>(blogInfoVoList);

        return R.ok(pageInfo);
    }

    @Override
    public R postBlog(BlogDto blogDto) {
        log.debug("文章内容{}",blogDto);
        String openId = null;
        try {
            openId = redisUtils.redisGetOpenId();
        } catch (Exception e) {
        }
        Blog blog = new Blog()
                .setBlogTitle(blogDto.getTitle())
                .setBlogContent(blogDto.getContent())
                .setBlogViewTimes(0)
                .setBlogState(1)
                .setUserOpenId(openId)
                .setBlogPostTime(LocalDateTime.now())
                .setBlogUpdateTime(LocalDateTime.now());
        int insert = blogMapper.insert(blog);
        if (insert==1){
            return R.ok(blog.getId());
        }else {
            return R.failure(R.State.ERR_BLOG_INSERT_EXCEPTION,new BlogInsetException("系统异常"));
        }

    }

    @Override
    public R getBlogDetail(Integer id) {

        BlogInfoVo blogInfoVo=  blogMapper.getBlogDetail(id);
        Long blogId = blogInfoVo.getId();
        Integer viewTimes = blogInfoVo.getBlogViewTimes();

        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",blogId);
        blogMapper.update(new Blog().setBlogViewTimes(++viewTimes),updateWrapper);


        blogInfoVo.setBlogViewTimes(viewTimes);
        return R.ok(blogInfoVo);
    }


}
