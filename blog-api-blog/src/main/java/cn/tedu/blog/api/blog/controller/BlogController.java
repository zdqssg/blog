package cn.tedu.blog.api.blog.controller;


import cn.tedu.blog.api.blog.dto.BlogDto;
import cn.tedu.blog.api.blog.service.IBlogService;
import cn.tedu.blog.common.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 博客文章 前端控制器
 * </p>
 *
 * @author zdq247209@163.com
 * @since 2020-10-07
 */
@RestController
public class BlogController {

    @Autowired
    private IBlogService blogService;
    @GetMapping("/indexBlog/{pageNum}")
    public R getIndexBlog(@PathVariable("pageNum") Integer pageNum){
        return blogService.getIndexBlogByMySql(pageNum);
    }

    @GetMapping("/blogDetail/{id}")
    public R getBlogDetail(@PathVariable("id") Integer id){
        return blogService.getBlogDetail(id);
    }

    @PostMapping("/postBlog")
    public R postBlog(@RequestBody  BlogDto blogDto ){
       return blogService.postBlog(blogDto);
    }
}
