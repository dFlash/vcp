package com.maliavin.vcp.service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.form.RestResponse;
import com.maliavin.vcp.form.ThumbnailForm;
import com.maliavin.vcp.form.UploadVideoForm;

/**
 * User service interface which specify operations for registered users.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface UserService {

    @Nonnull
    Video uploadVideo(@Nonnull User currentUser, @Nonnull UploadVideoForm form);

    @Nonnull Page<Video> listAllVideosByUser(@Nonnull User owner, Pageable pageable);

    @Nullable Video getVideo(String id);

    void updateVideo(@Nonnull String id, @Nonnull Video video);

    void deleteVideo(String id);

    @Nonnull RestResponse uploadThumbnail(ThumbnailForm thumbnailForm);
}
