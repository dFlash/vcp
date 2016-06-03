package com.maliavin.vcp.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.form.ThumbnailForm;
import com.maliavin.vcp.form.UploadForm;
import com.maliavin.vcp.repository.search.VideoSearchRepository;
import com.maliavin.vcp.repository.storage.VideoRepository;
import com.maliavin.vcp.service.ImageService;
import com.maliavin.vcp.service.UserService;
import com.maliavin.vcp.service.VideoProcessorService;
import com.maliavin.vcp.service.impl.UserServiceImpl;

public class UserServiceImplTest {

    @Mock
    private VideoProcessorService videoProcessorService;

    @Mock
    private ImageService imageService;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoSearchRepository videoSearchRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void uploadVideoTest() {
        User currentUser = new User();
        UploadForm form = new UploadForm();
        Video expected = new Video("videoUrl", "thumbnail");
        Mockito.when(videoProcessorService.processVideo(form, currentUser)).thenReturn(expected);
        Mockito.when(videoRepository.save(expected)).thenReturn(null);
        Mockito.when(videoSearchRepository.save(expected)).thenReturn(null);

        Video actual = userService.uploadVideo(currentUser, form);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getVideoTest() {
        String id = "id";
        Video expected = new Video("videoUrl", "thumbnail");
        Mockito.when(videoRepository.findOne(id)).thenReturn(expected);

        Video actual = userService.getVideo(id);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void uploadThumbnailTest() {
        byte[] bytes = null;
        String thumbnailUrl = "thumbnailUrl";

        Mockito.when(imageService.saveImageData(bytes)).thenReturn(thumbnailUrl);

        Map<String, String> expected = new HashMap<>();
        expected.put("thumbnailUrl", thumbnailUrl);

        ThumbnailForm thumbnailForm = new ThumbnailForm(new TestMultipartFile());
        Map<String, String> actual = userService.uploadThumbnail(thumbnailForm);

        Assert.assertEquals(expected, actual);
    }

    private static class TestMultipartFile implements MultipartFile {

        @Override
        public String getName() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getOriginalFilename() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getContentType() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean isEmpty() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public long getSize() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public byte[] getBytes() throws IOException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            // TODO Auto-generated method stub

        }

    }
}
