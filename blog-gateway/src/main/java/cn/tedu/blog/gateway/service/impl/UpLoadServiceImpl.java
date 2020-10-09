package cn.tedu.blog.gateway.service.impl;

import cn.tedu.blog.common.constant.BlogSystem;
import cn.tedu.blog.common.util.R;
import cn.tedu.blog.common.util.ServletUtils;
import cn.tedu.blog.gateway.service.UpLoadService;
import cn.tedu.blog.gateway.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/9 5:24
 */
@Service
@Slf4j
public class UpLoadServiceImpl implements UpLoadService {
    @Autowired
    private RedisUtils redisUtils;
    @Override
    public R uploadImg() {
        try {
            String openId = redisUtils.redisGetOpenId();
            int uploadType = Integer.parseInt(ServletUtils.getHeaderByName("uploadType"));
            String pathRoot="";
            for (int i = 0; i < BlogSystem.FILEPATH.length; i++) {
                pathRoot=BlogSystem.FILEPATH[uploadType];
            }
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            List<FileItem> items = upload.parseRequest((RequestContext) ServletUtils.getRequest());
            log.info("本次上传的文件数量：{}", items.size());

            List<String> resultFileUrls = new ArrayList<>();
            for (FileItem item : items) {
                String name = item.getFieldName();
                log.info("fieldName:{}", name);


                // 判断是否是一个一般的表单域,
                if (!item.isFormField()) {
                    InputStream is = item.getInputStream();

                    //创建文件目录
                    String path = pathRoot + File.separatorChar + openId  + File.separatorChar + item.getName();
                    File file = new File(path);
                    File fileParent = file.getParentFile();
                    if (!fileParent.exists()) {
                        fileParent.mkdirs();
                    }
                    file.createNewFile();

                    FileOutputStream fos = new FileOutputStream(path);
                    byte[] buff = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buff)) > 0) {
                        fos.write(buff);
                    }
                    is.close();
                    fos.close();

                    resultFileUrls.add(BlogSystem.URL  + "/resource/" +path);
                }
            }
            return R.ok(resultFileUrls);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.failure(R.State.ERR_WE_CHAT_LOGIN_SESSION,new Exception("上传文件异常"));
    }
}
