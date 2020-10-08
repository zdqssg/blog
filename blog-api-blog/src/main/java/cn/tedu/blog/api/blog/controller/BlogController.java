package cn.tedu.blog.api.blog.controller;


import cn.tedu.blog.api.blog.service.IBlogService;
import cn.tedu.blog.common.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    public R getIndexBlog(@PathVariable("pageNum") Integer pageNum, HttpServletRequest request){
        return blogService.getIndexBlog(pageNum,request);
    }
}
