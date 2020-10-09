package cn.tedu.blog.api.blog.controller;

import cn.tedu.blog.api.blog.service.SearchService;
import cn.tedu.blog.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function {搜索模块}
 * @email zdq247209@163.com
 * @date 2020/10/8 17:31
 */

@RestController
@RequestMapping("/search")
@Slf4j
public class SerachController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/{pageNum}/{msg}")
    public R searchBlogByMsg(@PathVariable("pageNum") Integer pageNum,
                             @PathVariable("msg") String msg) {
       return searchService.elasticSearchBlogByMsg(pageNum,msg);
    }


}
