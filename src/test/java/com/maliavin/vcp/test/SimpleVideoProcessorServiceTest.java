package com.maliavin.vcp.test;

import java.io.File;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.maliavin.vcp.component.UploadVideoTempStorage;
import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.form.UploadVideoForm;
import com.maliavin.vcp.service.ImageService;
import com.maliavin.vcp.service.ThumbnailService;
import com.maliavin.vcp.service.VideoProcessorService;
import com.maliavin.vcp.service.VideoService;
import com.maliavin.vcp.service.impl.SimpleVideoProcessorService;

public class SimpleVideoProcessorServiceTest {

    @Mock
    private VideoService videoService;

    @Mock
    private ImageService imageService;

    @Mock
    private UploadVideoTempStorage uploadVideoTempStorage;

    @Mock
    private ThumbnailService thumbnailService;

    @InjectMocks
    private VideoProcessorService videoProcessorService = new SimpleVideoProcessorService();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    public void processVideoTest() {
        Path tempUploadedVideoPath = new File(".").toPath();
        Mockito.when(uploadVideoTempStorage.getTempUploadedVideoPath()).thenReturn(tempUploadedVideoPath);

        String videoUrl = "videoUrl";
        Mockito.when(videoService.saveVideo(tempUploadedVideoPath)).thenReturn(videoUrl);

        byte[] thumbnailImageData = null;
        String thumbnailImageUrl = "thumbnailImageUrl";
        Mockito.when(imageService.saveImageData(thumbnailImageData)).thenReturn(thumbnailImageUrl);

        String title = "title";
        String desc = "desc";
        UploadVideoForm uf = new UploadVideoForm(title, desc, null);
        User user = new User();
        Video actualVideo = videoProcessorService.processVideo(uf, user);

        Video expectedVideo = new Video(title, desc, user, thumbnailImageUrl, videoUrl);

        Assert.assertEquals(expectedVideo, actualVideo);

    }

}
