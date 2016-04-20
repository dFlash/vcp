package online.study.vcp.service;

import org.springframework.web.multipart.MultipartFile;

import online.study.vcp.domain.Video;

public interface VideoService {

    Video processVideo(MultipartFile videoFile);
}
