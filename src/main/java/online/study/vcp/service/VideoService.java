package online.study.vcp.service;

import org.springframework.web.multipart.MultipartFile;

import online.study.vcp.domain.Video;

/**
 * Video service interface
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface VideoService {

    Video processVideo(MultipartFile videoFile);
}
