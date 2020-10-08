package cn.tedu.blog.api.blog.schedule;

import cn.tedu.blog.api.blog.mapper.BlogMapper;
import cn.tedu.blog.common.constant.ESConstant;
import cn.tedu.blog.common.model.Blog;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/7 19:24
 */
@Component
@Slf4j
public class BlogSchedule {
    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    @Autowired
    private BlogMapper blogMapper;


    @Scheduled(fixedRate = 1000 * 60 * 60 * 24 )
    private void addBlog() {
        //删除ElasticSearch的索引
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(ESConstant.BLOG_CONTENT_INDEX);
        try {
            client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //添加ElasticSearch的索引
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(ESConstant.BLOG_CONTENT_INDEX);
        try {
            client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }


        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("blog_state", 1)
                .orderByDesc("blog_post_time");
        List<Blog> list = blogMapper.selectList(queryWrapper);


        BulkRequest bulkRequest = new BulkRequest()
                .timeout("2m");
        for (Blog good : list) {
            bulkRequest.add(new IndexRequest(ESConstant.BLOG_CONTENT_INDEX)
                    .source(JSON.toJSONString(good), XContentType.JSON));
        }
        try {
            BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.debug("添加成功？{}", bulk.hasFailures());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
