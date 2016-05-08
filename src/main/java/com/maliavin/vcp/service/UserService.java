package com.maliavin.vcp.service;

import javax.annotation.Nonnull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.form.UploadForm;

/**
 * User service interface which specify operations for registered users.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface UserService {

    @Nonnull
    Video uploadVideo(@Nonnull User currentUser, @Nonnull UploadForm form);

    Page<Video> listAllVideosByUser(@Nonnull User owner, Pageable pageable);

    Video getVideo(String id);

    void updateVideo(@Nonnull Video video);

    void deleteVideo(String id);
}
