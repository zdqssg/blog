package cn.tedu.blog.api.blog.service.impl;

import cn.tedu.blog.api.blog.mapper.BlogMapper;
import cn.tedu.blog.api.blog.service.IBlogService;
import cn.tedu.blog.api.blog.utils.RedisSessionUtil;
import cn.tedu.blog.common.constant.ESConstant;
import cn.tedu.blog.common.exception.elasticSearch.ElasticsearchNoMoreException;
import cn.tedu.blog.common.model.Blog;
import cn.tedu.blog.common.util.PageInfoUtils;
import cn.tedu.blog.common.util.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;
    @Autowired
    private RedisSessionUtil redisSessionUtil;

    @Override
    public R getIndexBlog(Integer pageNum, HttpServletRequest request) {
        log.debug("请求第{}页数据", pageNum);
        if (pageNum == null || pageNum < 0) {
            pageNum = 0;
        }
        pageNum = pageSize * pageNum;
        try {
            String openId = redisSessionUtil.redisGetOpenId(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // SearchSourceBuilder 条件构造
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort("blogPostTime").order(SortOrder.DESC);

        sourceBuilder
                .sort(sortBuilder)
                .from(pageNum)
                .size(pageSize)
                .timeout(new TimeValue(60, TimeUnit.SECONDS));

        //索引
        SearchRequest indexRequest = new SearchRequest(ESConstant.BLOG_CONTENT_INDEX);
        indexRequest.source(sourceBuilder);

        SearchResponse searchResponse = null;
        //执行搜索
        try {
            searchResponse = client.search(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.debug("异常{}", e.getMessage());
        }

        SearchHits hits = searchResponse.getHits();
//        System.out.println(hits);
        List<T> list = new ArrayList<>();
        for (SearchHit documentFields : hits.getHits()) {
            list.add((T) documentFields.getSourceAsMap());
        }
        log.debug("搜索内容{}", list);
        //
        if (list.size() == 0) {
            throw new ElasticsearchNoMoreException("没有更多了");
        }
        int total = (int) hits.getTotalHits().value;
        PageInfo<T> pageInfo = PageInfoUtils.esToPageInfo(list, pageNum, pageSize, total);
        return R.ok(pageInfo);
    }
}
