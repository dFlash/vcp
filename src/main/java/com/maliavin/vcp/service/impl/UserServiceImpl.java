package com.maliavin.vcp.service.impl;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.component.UploadVideoTempStorage;
import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.form.UploadForm;
import com.maliavin.vcp.repository.storage.VideoRepository;
import com.maliavin.vcp.service.ImageService;
import com.maliavin.vcp.service.ThumbnailService;
import com.maliavin.vcp.service.UserService;
import com.maliavin.vcp.service.VideoService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private VideoService videoService;

    @Autowired
    private ThumbnailService thumbnailService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UploadVideoTempStorage uploadVideoTempStorage;

    @Override
    public Video uploadVideo(User currentUser, UploadForm form) {
        Path tempUploadedVideoPath = uploadVideoTempStorage.getTempUploadedVideoPath();
        String videoUrl = videoService.saveVideo(tempUploadedVideoPath);
        byte[] thumbnailImageData = thumbnailService.createThumbnail(tempUploadedVideoPath);
        String thumbnailImageUrl = imageService.saveImageData(thumbnailImageData);
        String title = form.getTitle();
        String description = form.getDescription();
        Video video = new Video(title, description, currentUser, thumbnailImageUrl, videoUrl);
        videoRepository.save(video);
        return video;
    }
}
