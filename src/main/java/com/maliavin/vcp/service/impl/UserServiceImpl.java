package com.maliavin.vcp.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContextException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;
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
        videoRepository.delete(id);
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
