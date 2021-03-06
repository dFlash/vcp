package com.maliavin.vcp.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
import com.maliavin.vcp.form.RestResponse;
import com.maliavin.vcp.form.ThumbnailForm;
import com.maliavin.vcp.form.UploadVideoForm;
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
    public Video uploadVideo(User currentUser, UploadVideoForm form) {
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
        String currThumbnail = currentVideo.getThumbnail();
        String newThumbnail = video.getThumbnail();
        if (!currThumbnail.equals(newThumbnail))
        {
            try {
                deleteThumbnailFile(currentVideo);
            } catch (IOException e) {
                throw new ApplicationException("Couldn't delete Thumbnail file from FS", e);
            }
            currentVideo.setThumbnail(newThumbnail);
        }
        
        currentVideo.setTitle(video.getTitle());
        currentVideo.setDescription(video.getDescription());
        videoRepository.save(currentVideo);
        videoSearchRepository.save(currentVideo);
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
            videoSearchRepository.delete(v);
        }
    }

    private void clearVideoResources(Video v) throws IOException {
        deleteThumbnailFile(v);
        deleteVideoFile(v);
    }

    private void deleteThumbnailFile(Video v) throws IOException {
        String thumbnailName = getResourceName(v.getThumbnail());
        String thumbnailFileName = FilenameUtils.concat(thumbnailsDir, thumbnailName);
        File thumbnailFile = new File(thumbnailFileName);
        Files.deleteIfExists(thumbnailFile.toPath());
    }

    private void deleteVideoFile(Video v) throws IOException {
        String videoName = getResourceName(v.getVideoUrl());
        String videoFileName = FilenameUtils.concat(videosDir, videoName);
        File videoFile = new File(videoFileName);
        Files.deleteIfExists(videoFile.toPath());
    }

    private String getResourceName(String thumbnailUrl) {
        if (thumbnailUrl.lastIndexOf('\\') > -1) {
            String thumbnailImg = thumbnailUrl.substring(thumbnailUrl.lastIndexOf('\\') + 1);
            return thumbnailImg;
        } else {
            String thumbnailImg = thumbnailUrl.substring(thumbnailUrl.lastIndexOf('/') + 1);
            return thumbnailImg;
        }
    }

    @Override
    public RestResponse uploadThumbnail(ThumbnailForm thumbnailForm) {
        String thumbnailUrl = null;
        try {
            thumbnailUrl = imageService.saveImageData(thumbnailForm.getFile().getBytes());
        } catch (CantProcessMediaContentException e) {
            throw new ApplicationContextException("Could not upload thumbnail");
        } catch (IOException e) {
            throw new ApplicationContextException("Could not upload thumbnail");
        }
        RestResponse response = new RestResponse(thumbnailUrl);
        return response;
    }
}
