package com.maliavin.vcp.service.impl;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.component.UploadVideoTempStorage;
import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.form.UploadForm;
import com.maliavin.vcp.service.ImageService;
import com.maliavin.vcp.service.ThumbnailService;
import com.maliavin.vcp.service.VideoProcessorService;
import com.maliavin.vcp.service.VideoService;


@Service("simpleVideoProcessorService")
public class SimpleVideoProcessorService implements VideoProcessorService {

    @Autowired
    private VideoService videoService;

    @Autowired
    @Qualifier("ffmpegThumbnailService")
    private ThumbnailService thumbnailService;

    @Autowired
    private ImageService imageService;
    
    @Autowired
    private UploadVideoTempStorage uploadVideoTempStorage;
    
    @Override
    public Video processVideo(UploadForm uploadForm, User user) {
        Path tempUploadedVideoPath = uploadVideoTempStorage.getTempUploadedVideoPath();
        String videoUrl = videoService.saveVideo(tempUploadedVideoPath);
        byte[] thumbnailImageData = thumbnailService.createThumbnail(tempUploadedVideoPath);
        String thumbnailImageUrl = imageService.saveImageData(thumbnailImageData);
        String title = uploadForm.getTitle();
        String description = uploadForm.getDescription();
        Video video = new Video(title, description, user, thumbnailImageUrl, videoUrl);
        return video;
    }
}
