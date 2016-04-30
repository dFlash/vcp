package com.maliavin.vcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.form.UploadForm;
import com.maliavin.vcp.security.SecurityUtils;
import com.maliavin.vcp.service.UserService;

@RestController("/my-account")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody Video uploadVideo(@ModelAttribute UploadForm uploadForm) {
        User currentAccount = SecurityUtils.getCurrentUser();
        return userService.uploadVideo(currentAccount, uploadForm);
    }

    @RequestMapping(value = "/user-video/all", method = RequestMethod.GET)
    public @ResponseBody Page<Video> listAllVideosByUser(@PageableDefault(size = 9) Pageable pageable) {
        User currentAccount = SecurityUtils.getCurrentUser();
        return userService.listAllVideosByUser(currentAccount, pageable);
    }
}
