package com.maliavin.vcp.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.form.UploadVideoForm;
import com.maliavin.vcp.repository.search.VideoSearchRepository;
import com.maliavin.vcp.repository.storage.VideoRepository;
import com.maliavin.vcp.service.VideoProcessorService;

@Service("asyncVideoProcessorService")
public class AsyncVideoProcessorService implements VideoProcessorService {

    private static final String STUB_IMG = "Reload.png";
    private ExecutorService executorService;

    @Value("${image.dir}")
    private String imgDir;

    @Autowired
    @Qualifier("simpleVideoProcessorService")
    private VideoProcessorService simpleVideoProcessorService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoSearchRepository videoSearchRepository;

    @PostConstruct
    private void postConstruct() {
        executorService = Executors.newFixedThreadPool(3);
    }

    @PreDestroy
    private void preDestroy() {
        executorService.shutdown();
    }

    @Override
    public Video processVideo(UploadVideoForm uploadForm, User user) {
        String title = uploadForm.getTitle();
        String description = uploadForm.getDescription();
        String stubImgPath = FilenameUtils.concat(imgDir, STUB_IMG);
        Video video = new Video(title, description, user, stubImgPath, null);
        executorService.submit(new VideoItem(uploadForm, video, user));
        return video;
    }

    private class VideoItem implements Runnable {
        
        private UploadVideoForm uploadForm;
        private Video video;
        private User user;

        public VideoItem(UploadVideoForm uploadForm, Video video, User user) {
            super();
            this.uploadForm = uploadForm;
            this.video = video;
            this.user = user;
        }

        @Override
        public void run() {
            Video processedVideo = simpleVideoProcessorService.processVideo(uploadForm, user);
            video.setVideoUrl(processedVideo.getVideoUrl());
            video.setThumbnail(processedVideo.getThumbnail());
            videoRepository.save(video);
            videoSearchRepository.save(video);
        }
    }
}
