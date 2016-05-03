package com.maliavin.vcp.service.impl;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.repository.search.VideoSearchRepository;
import com.maliavin.vcp.repository.storage.VideoRepository;
import com.maliavin.vcp.service.CommonService;

/**
 * Common service
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoSearchRepository videoSearchRepository;

    @Override
    public Page<Video> listAllVideos(Pageable pageable) {
        return videoRepository.findAll(pageable);
    }

    @Override
    public Video getVideo(String id) {
        return videoRepository.findOne(id);
    }

    @Override
    public Page<Video> listVideosBySearchQuery(String searchQuery, Pageable pageable) {
        SearchQuery query = createSearchQuery(searchQuery, pageable);
        return videoSearchRepository.search(query);
    }

    private SearchQuery createSearchQuery(String searchQuery, Pageable pageable){
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(new MultiMatchQueryBuilder(searchQuery, 
                                     "owner.name", "title", "owner.company.name")
                                .fuzziness(Fuzziness.ONE)
                                .operator(Operator.OR))
                .withPageable(pageable)
                .build();
        return query;
    }

}
