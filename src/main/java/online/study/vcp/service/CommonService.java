package online.study.vcp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import online.study.vcp.domain.Video;

/**
 * Common service interface
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface CommonService {

    Page<Video> listAllVideos(Pageable pageable);

    long getVideosCount();

    Video getVideo(String id);

}
