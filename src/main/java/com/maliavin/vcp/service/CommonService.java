package com.maliavin.vcp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maliavin.vcp.domain.Video;

/**
 * Common service interface
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface CommonService {

    Page<Video> listAllVideos(Pageable pageable);

    Video getVideo(String id);

}
