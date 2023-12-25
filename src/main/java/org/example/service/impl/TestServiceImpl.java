package org.example.service.impl;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.example.bean.ResourceInstanceQueryVO;
import org.example.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author ：liuwq
 * @Date ：Created in 2023/12/22 17:58
 * @Description：
 * @Version : v1.0
 * @Copyright : Powerd By Winhong Tec Inc On 2023. All rights reserved.
 */

@Service
@Slf4j
public class TestServiceImpl implements TestService {

    public final static String RESOURCE_INSTANCE_INDEX = "yjz_resource_instance_index";

    @Autowired
    private RestHighLevelClient client;

    /**
     * 查询产品下的资源数量,按产品uuid分组
     * @param query
     * @return
     */
    @Override
    public Map<String, Map<String, Long>> getCountMap(ResourceInstanceQueryVO query) {
        log.info("获取资源实例数量接口入参为：{}", JSONUtil.toJsonStr(query));

//        if (StringUtils.isEmpty(query.getResourceInstanceFieldUuid())) {
//            throw new AppException("获取资源实例数量接口-资源实例字段uuid不能为空");
//        }

        Map<String, Map<String, Long>> data = new HashMap<>();
        String searchCriteria = query.getSearchCriteria();
        List<String> searchCriteriaList = new ArrayList<>();
        if (StringUtils.isNotEmpty(query.getSearchCriteria()) && searchCriteria.contains(",")) {
            List<String> list = Arrays.asList(StringUtils.split(searchCriteria, ","));
            for (String search : list) {
                searchCriteriaList.add("metaData.label." + search + ".keyword");
            }
        } else if (StringUtils.isNotEmpty(query.getSearchCriteria())) {
            searchCriteriaList.add("metaData.label." + searchCriteria + ".keyword");
        }
        SearchRequest searchRequest = new SearchRequest(RESOURCE_INSTANCE_INDEX + "*");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        // 个人
        if (query.getOtherCondition() != null && query.getOtherCondition() == 1) {
            builder.must(QueryBuilders.termQuery("creatorUuid", "12941e0b8ff947edb3ecc2dc376b3a04"));
        } else if (query.getOtherCondition() != null && query.getOtherCondition() == 2) {
            // 本单位
            builder.must(QueryBuilders.termQuery("unitName", "云宏信息科技有限公司"));
        } else {
            //默认查询仅个人
            builder.must(QueryBuilders.termQuery("creatorUuid", "12941e0b8ff947edb3ecc2dc376b3a04"));
        }
        if (StringUtils.isNotEmpty(query.getProductUuid())){
            builder.must(QueryBuilders.termQuery("productUuid",query.getProductUuid()));
        }
        //默认搜索在用资源
        builder.must(QueryBuilders.termQuery("canceled",0));
        if (StringUtils.isNotEmpty(query.getResourceType())) {
            builder.must(QueryBuilders.termQuery("resourceType", query.getResourceType()));
        }
        if (StringUtils.isNotEmpty(query.getProductType())) {
            builder.must(QueryBuilders.termQuery("productType", query.getProductType()));
        }
        if (StringUtils.isNotEmpty(query.getResourceInstanceFieldUuid())) {
            builder.must(QueryBuilders.termQuery("resourceInstanceFieldUuid", query.getResourceInstanceFieldUuid()));
        }
        if (!CollectionUtils.isEmpty(searchCriteriaList) && StringUtils.isNotEmpty(query.getKeyword())) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            for (String searchCondition : searchCriteriaList) {
                WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery(searchCondition, "*" + query.getKeyword() + "*");
                boolQueryBuilder.should(wildcardQueryBuilder);
            }
            builder.must(QueryBuilders.nestedQuery("metaData", boolQueryBuilder, org.apache.lucene.search.join.ScoreMode.None));
        }
        if (StringUtils.isNotEmpty(query.getBusSysUuid())) {
            NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery("metaData", QueryBuilders.termQuery("metaData.value.busSysName.keyword", query.getBusSysUuid()), ScoreMode.None);
            builder.must(nestedQueryBuilder);
        }
        log.info("获取资源实例数量接口-构建查询脚本内容：{}", builder.toString());
        searchSourceBuilder.query(builder);
        // 设置size=0的意思就是，仅返回聚合查询结果，不返回普通query查询结果。
        searchSourceBuilder.size(0);
        //执行es搜索
        SearchResponse searchResponse = null;
        try {
            // 按资源实例字段uuid，产品uuid分组聚合查count
            TermsAggregationBuilder termsAggregationBuilder  = AggregationBuilders.terms("terms_by_resourceInstanceFieldUuid")
                    .field("resourceInstanceFieldUuid")
                    .size(Integer.MAX_VALUE)
                    .subAggregation(
                            AggregationBuilders.terms("terms_by_productUuid")
                            .field("productUuid").size(Integer.MAX_VALUE)
                    );
            searchSourceBuilder.aggregation(termsAggregationBuilder);
            searchRequest.source(searchSourceBuilder);
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            log.info("获取资源实例数量接口 searchResponse:{}", JSONUtil.toJsonStr(searchResponse));
            // 遍历terms聚合结果
            Terms terms = searchResponse.getAggregations().get(termsAggregationBuilder.getName());
            data = convertTerms(terms);
        } catch (Exception e) {
            log.error("获取资源实例数量接口-接口执行搜索出现异常：", e);
        }
        if (searchResponse == null) {
            log.error("获取资源实例数量接口-接口执行搜索出现异常：null");
        }
        log.info("data:{}", JSONUtil.toJsonStr(data));
        return data;
    }

    private Map<String, Map<String, Long>> convertTerms(Terms terms) {
        Map<String, Map<String, Long>> data = new HashMap<>();
        if (CollectionUtils.isEmpty(terms.getBuckets())) {
            return data;
        }

        terms.getBuckets().forEach(bucket -> {
            // resourceInstanceFieldUuid
            String key = bucket.getKeyAsString();
            long docCount = bucket.getDocCount();
            Terms productTerms = bucket.getAggregations().get("terms_by_productUuid");
            if (Objects.isNull(productTerms)) {
                return;
            }

            Map<String, Long> subMap = new HashMap<>();
            productTerms.getBuckets().forEach(subBucket -> {
                // productUuid count
                subMap.put(subBucket.getKeyAsString(), subBucket.getDocCount());
            });
            data.put(key, subMap);

        });
        return data;
    }

}
