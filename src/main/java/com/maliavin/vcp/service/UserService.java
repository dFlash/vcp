package com.maliavin.vcp.service;

import javax.annotation.Nonnull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.form.UploadForm;

public interface UserService {

    @Nonnull
    Video uploadVideo(@Nonnull User currentUser, @Nonnull UploadForm form);

    Page<Video> listAllVideosByUser(User owner, Pageable pageable);
}
