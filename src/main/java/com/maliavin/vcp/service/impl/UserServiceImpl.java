package com.maliavin.vcp.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.exception.ApplicationException;
import com.maliavin.vcp.exception.CantProcessMediaContentException;
import com.maliavin.vcp.form.ThumbnailForm;
import com.maliavin.vcp.form.UploadForm;
import com.maliavin.vcp.repository.search.VideoSearchRepository;
import com.maliavin.vcp.repository.storage.VideoRepository;
import com.maliavin.vcp.service.ImageService;
import com.maliavin.vcp.service.UserService;
import com.maliavin.vcp.service.VideoProcessorService;

/**
 * Service for registered users.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${thumbnails.dir}")
    private String thumbnailsDir;

    @Value("${videos.dir}")
    private String videosDir;

    @Autowired
    @Qualifier("asyncVideoProcessorService")
    private VideoProcessorService videoProcessorService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoSearchRepository videoSearchRepository;

    @Override
    public Video uploadVideo(User currentUser, UploadForm form) {
        Video video = videoProcessorService.processVideo(form, currentUser);
        videoRepository.save(video);
        videoSearchRepository.save(video);
        return video;
    }

    @Override
    public Page<Video> listAllVideosByUser(User owner, Pageable pageable) {
        return videoRepository.findByOwner(owner, pageable);
    }

    @Override
    public Video getVideo(String id) {
        return videoRepository.findOne(id);
    }

    @Override
    public void updateVideo(String id, Video video) {
        Video currentVideo = getVideo(id);
        currentVideo.setThumbnail(video.getThumbnail());
        currentVideo.setTitle(video.getTitle());
        currentVideo.setDescription(video.getDescription());
        videoRepository.save(currentVideo);
    }

    @Override
    public void deleteVideo(String id) {
        Video v = videoRepository.findOne(id);
        try {
            clearVideoResources(v);
        } catch (IOException e) {
            throw new ApplicationException("Couldn't delete video file from FS", e);
        }
        finally {
            videoRepository.delete(v);
        }
    }

    private void clearVideoResources(Video v) throws IOException {
        String thumbnailName = getThumbnailImg(v.getThumbnail());
        String thumbnailFileName = FilenameUtils.concat(thumbnailsDir, thumbnailName);
        File thumbnailFile = new File(thumbnailFileName);
        Files.deleteIfExists(thumbnailFile.toPath());

        String videoName = getThumbnailImg(v.getVideoUrl());
        String videoFileName = FilenameUtils.concat(videosDir, videoName);
        File videoFile = new File(videoFileName);
        Files.deleteIfExists(videoFile.toPath());

    }

    private String getThumbnailImg(String thumbnailUrl) {
        if (thumbnailUrl.lastIndexOf('\\') > -1) {
            String thumbnailImg = thumbnailUrl.substring(thumbnailUrl.lastIndexOf('\\') + 1);
            return thumbnailImg;
        } else {
            String thumbnailImg = thumbnailUrl.substring(thumbnailUrl.lastIndexOf('/') + 1);
            return thumbnailImg;
        }
    }

    @Override
    public Map<String, String> uploadThumbnail(ThumbnailForm thumbnailForm) {
        String thumbnailUrl = null;
        try {
            thumbnailUrl = imageService.saveImageData(thumbnailForm.getFile().getBytes());
        } catch (CantProcessMediaContentException e) {
            throw new ApplicationContextException("Could not upload thumbnail");
        } catch (IOException e) {
            throw new ApplicationContextException("Could not upload thumbnail");
        }
        Map<String, String> map = new HashMap<>();
        map.put("thumbnailUrl", thumbnailUrl);
        return map;
    }
}
