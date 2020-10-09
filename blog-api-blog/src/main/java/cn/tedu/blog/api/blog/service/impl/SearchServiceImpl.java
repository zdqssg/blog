package cn.tedu.blog.api.blog.service.impl;

import cn.tedu.blog.api.blog.mapper.UserFocusBlogMapper;
import cn.tedu.blog.api.blog.mapper.UserMapper;
import cn.tedu.blog.api.blog.service.SearchService;
import cn.tedu.blog.api.blog.utils.RedisUtils;
import cn.tedu.blog.common.constant.ESConstant;
import cn.tedu.blog.common.exception.elasticSearch.ElasticsearchNoFindException;
import cn.tedu.blog.common.model.User;
import cn.tedu.blog.common.model.UserFocusBlog;
import cn.tedu.blog.common.util.PageInfoUtils;
import cn.tedu.blog.common.util.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Zhou
 * @version 1.0
 * @function { 描述一下功能吧 }
 * @email zdq247209@163.com
 * @date 2020/10/8 17:33
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {
    @Value("${project.blog-index.pageSize}")
    private Integer pageSize;
    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserFocusBlogMapper focusBlogMapper;
    @Autowired
    private UserMapper userMapper;

    //es的实现
    @Override
    public <T> R elasticSearchBlogByMsg(Integer pageNum, String msg ) {

        log.debug("请求第{}页数据，关键字{}", pageNum,msg);
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        pageNum = --pageNum * pageSize;
        String openId = null;
        try {
            openId = redisUtils.redisGetOpenId();
        } catch (Exception e) {

        }

        // 条件构造器  SearchSourceBuilder
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();


        //构建多字段分词
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("blogState", 1));
        boolQueryBuilder.should(QueryBuilders.matchQuery("blogTitle", msg));
        boolQueryBuilder.should(QueryBuilders.matchQuery("blogContent", msg));

//        mustQuery.must();
//
//        QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(msg);
//        queryBuilder.analyzer(ESConstant.analyzer);
//        queryBuilder.field("blogTitle").field("blogContent");

        //构建高亮 HighlightBuilder
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("blogTitle");
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        highlightBuilder.field("blogContent");
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");

        sourceBuilder
                .query(boolQueryBuilder)
                .highlighter(highlightBuilder)
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
//            log.debug("es查询到的searchResponse{}", searchResponse);
        } catch (IOException e) {
            log.debug("异常{}", e.getMessage());
        }
//        System.out.println(hits);
        SearchHit[] hits = searchResponse.getHits().getHits();
        List<T> list = new ArrayList<>();

        //组装数据
        QueryWrapper<UserFocusBlog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_open_id", openId)
                .eq("is_focus", 1);
        //查询用户的关注
        List<UserFocusBlog> focusBlogList = focusBlogMapper.selectList(queryWrapper);
        //把用户的关注博客文章的ID装进map
        Map<Long, Object> hashMap = new HashMap<>();
        for (UserFocusBlog blog : focusBlogList) {
            hashMap.put(blog.getBlogId(),blog.getBlogId());
        }
        for (SearchHit fields : hits) {
            Map<String, Object> map = fields.getSourceAsMap();
            map.put("isFocus", 0);
            //组装是否关注
            if (hashMap.containsKey(Long.valueOf(map.get("id").toString() ))){
                map.put("isFocus", 1);
            }
            //组装博客文章的作者
            String userOpenId = (String) map.get("userOpenId");
            QueryWrapper<User> userWrapper = new QueryWrapper<>();
            userWrapper.eq("user_open_id", userOpenId);
            User user = userMapper.selectOne(userWrapper);
            map.put("nickName", user.getNickName());
            map.put("avatarUrl", user.getAvatarUrl());


            //组装高亮字段
            Map<String, HighlightField> highlightFields = fields.getHighlightFields();
            HighlightField blogTitle = highlightFields.get("blogTitle");
            HighlightField blogContent = highlightFields.get("blogContent");
            if (blogTitle != null) {
                Text[] fragments = blogTitle.fragments();
                String new_blogTitle = "";
                for (Text fragment : fragments) {
                    new_blogTitle += fragment;
                }
                map.put("blogTitle", new_blogTitle);
            }
            if (blogContent != null) {
                Text[] fragments = blogContent.fragments();
                String new_blogContent = "";
                for (Text fragment : fragments) {
                    new_blogContent += fragment;
                }
                map.put("blogContent", new_blogContent);
            }

            list.add((T) map);
        }

//        log.debug("搜索内容{}", list);
        int total = (int) searchResponse.getHits().getTotalHits().value;
        ;
        if (searchResponse.getHits().getMaxScore() == 1.0) {
            throw new ElasticsearchNoFindException("我能力有限，不要为难我-.-丨！");
        }
        PageInfo<T> pageInfo = PageInfoUtils.esToPageInfo(list, ++pageNum, pageSize, total);
        return R.ok(pageInfo);
    }
}
