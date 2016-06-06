package com.maliavin.vcp.service;

import javax.annotation.Nonnull;

import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.form.UploadVideoForm;

public interface VideoProcessorService {

    @Nonnull Video processVideo(UploadVideoForm uploadForm, User user);
}
