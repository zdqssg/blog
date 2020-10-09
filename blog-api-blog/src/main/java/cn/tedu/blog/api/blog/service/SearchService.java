package cn.tedu.blog.api.blog.service;

import cn.tedu.blog.common.util.R;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下接口吧 }
 * @email zdq247209@163.com
 * @date 2020/10/8 17:33
 */
public interface SearchService {
    <T> R elasticSearchBlogByMsg(Integer pageNum,String msg);
}
