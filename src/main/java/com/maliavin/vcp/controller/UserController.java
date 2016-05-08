package com.maliavin.vcp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.exception.CantProcessMediaContentException;
import com.maliavin.vcp.form.ThumbnailForm;
import com.maliavin.vcp.form.UploadForm;
import com.maliavin.vcp.security.CurrentUser;
import com.maliavin.vcp.service.ImageService;
import com.maliavin.vcp.service.UserService;

/**
 * Controller for registered users operations.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@RestController
@RequestMapping("/my-account")
public class UserController {

    private static final int ELEMENT_PER_PAGE = 9;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody Video uploadVideo(@AuthenticationPrincipal CurrentUser currentUser,
            @ModelAttribute UploadForm uploadForm) {
        return userService.uploadVideo(currentUser.getUser(), uploadForm);
    }

    @RequestMapping(value = "/user-video/all", method = RequestMethod.GET)
    public @ResponseBody Page<Video> listAllVideosByUser(@AuthenticationPrincipal CurrentUser currentUser,
            @PageableDefault(size = ELEMENT_PER_PAGE) Pageable pageable) {
        return userService.listAllVideosByUser(currentUser.getUser(), pageable);
    }

    @RequestMapping(value = "/upload-thumbnail", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> uploadThumbnail(@ModelAttribute ThumbnailForm thumbnailForm){
        String thumbnailUrl = null;
        try {
            thumbnailUrl = imageService.saveImageData(thumbnailForm.getFile().getBytes());
        } catch (CantProcessMediaContentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();
        map.put("thumbnailUrl", thumbnailUrl);
        return map;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public void updateVideo(@PathVariable("id") String id, @RequestBody Video video){
        Video currentVideo = userService.getVideo(id);
        currentVideo.setThumbnail(video.getThumbnail());
        currentVideo.setTitle(video.getTitle());
        currentVideo.setDescription(video.getDescription());
        userService.updateVideo(currentVideo);
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteVideo(@PathVariable("id") String id) {
        userService.deleteVideo(id);
    }
}
