package com.maliavin.vcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.form.UploadForm;
import com.maliavin.vcp.form.UploadForm2;
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
}
