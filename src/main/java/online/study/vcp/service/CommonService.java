package online.study.vcp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import online.study.vcp.domain.Video;

public interface CommonService {
    
    Page<Video> listAllVideos(Pageable pageable);

}
