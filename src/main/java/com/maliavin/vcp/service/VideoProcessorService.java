package com.maliavin.vcp.service;

import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.form.UploadForm;

public interface VideoProcessorService {

    Video processVideo(UploadForm uploadForm, User user);
}
