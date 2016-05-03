package com.maliavin.vcp.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.maliavin.vcp.domain.Video;

/**
 * DAO for elasticsearch operations with Video entity.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface VideoSearchRepository extends ElasticsearchRepository<Video, String> {
}
