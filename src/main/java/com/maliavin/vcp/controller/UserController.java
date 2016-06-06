package com.maliavin.vcp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.form.ThumbnailForm;
import com.maliavin.vcp.form.UploadVideoForm;
import com.maliavin.vcp.security.CurrentUser;
import com.maliavin.vcp.service.CommonService;
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
    private CommonService commonService;

    @RequestMapping(value = "/videos", method = RequestMethod.POST)
    public @ResponseBody Video uploadVideo(@AuthenticationPrincipal CurrentUser currentUser,
            @ModelAttribute UploadVideoForm uploadForm) {
        return userService.uploadVideo(currentUser.getUser(), uploadForm);
    }

    @RequestMapping(value = "/videos", method = RequestMethod.GET)
    public @ResponseBody Page<Video> listAllVideosByUser(@AuthenticationPrincipal CurrentUser currentUser,
            @PageableDefault(size = ELEMENT_PER_PAGE) Pageable pageable) {
        return userService.listAllVideosByUser(currentUser.getUser(), pageable);
    }

    @RequestMapping(value = "/thumbnail", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> uploadThumbnail(@ModelAttribute ThumbnailForm thumbnailForm){
        Map<String, String> map = userService.uploadThumbnail(thumbnailForm);
        return map;
    }

    @RequestMapping(value = "/videos/{id}", method = RequestMethod.PUT)
    public void updateVideo(@PathVariable("id") String id, @RequestBody Video video){
        userService.updateVideo(id, video);
    }
    
    @RequestMapping(value = "/videos/{id}", method = RequestMethod.DELETE)
    public void deleteVideo(@PathVariable("id") String id) {
        userService.deleteVideo(id);
    }
    
    @RequestMapping(value = "/video/{videoId}", method = RequestMethod.GET)
    public @ResponseBody Video getVideo(@PathVariable String videoId, @AuthenticationPrincipal CurrentUser currentUser,
            HttpServletRequest request) {
        Video video = commonService.getVideo(videoId, currentUser, request);
        return video;
    }

    @RequestMapping(value = "/videos/search", method = RequestMethod.GET)
    public @ResponseBody Page<Video> findVideos(@RequestParam("query") String query,
            @PageableDefault(size = ELEMENT_PER_PAGE) Pageable pageable) {
        Page<Video> videos = commonService.listVideosBySearchQuery(query, pageable);
        return videos;
    }
}
