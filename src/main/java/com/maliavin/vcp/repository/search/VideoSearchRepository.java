package com.maliavin.vcp.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.maliavin.vcp.domain.Video;

public interface VideoSearchRepository extends ElasticsearchRepository<Video, String> {
}
