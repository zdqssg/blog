package cn.tedu.blog.gateway.controller;

import cn.tedu.blog.common.util.R;
import cn.tedu.blog.gateway.service.UpLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/9 5:23
 */
@RestController
@RequestMapping("/upload")
public class UpLoad {
    @Autowired
    private UpLoadService upLoadService;
    @PostMapping("/img")
    public R uploadImg(){
        return upLoadService.uploadImg();
    }
}
