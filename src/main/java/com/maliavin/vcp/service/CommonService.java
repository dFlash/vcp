package com.maliavin.vcp.service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.form.ChangePasswordForm;
import com.maliavin.vcp.form.UsernameForm;
import com.maliavin.vcp.security.CurrentUser;

/**
 * Common service interface
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public interface CommonService {

    @Nonnull
    Page<Video> listAllVideos(@Nonnull Pageable pageable);

    @Nonnull
    Video getVideo(String id, CurrentUser currentUser, HttpServletRequest request);

    @Nonnull
    Page<Video> listVideosBySearchQuery(@Nonnull String query, @Nonnull Pageable pageable);

    void sendMail(@Nonnull UsernameForm usernameForm);

    @Nullable User findUser(String id, String hash);

    ResponseEntity<String> changePassword(@Nonnull ChangePasswordForm changePasswordForm);

}
