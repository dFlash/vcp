package com.maliavin.vcp.service;

import javax.annotation.Nonnull;

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

    @Nonnull Page<Video> listAllVideos(@Nonnull Pageable pageable);

    @Nonnull Video getVideo(String id);
    
    @Nonnull Page<Video> listVideosBySearchQuery(@Nonnull String query, @Nonnull Pageable pageable);

}
