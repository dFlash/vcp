package com.maliavin.vcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maliavin.vcp.domain.Video;
import com.maliavin.vcp.form.ChangePasswordForm;
import com.maliavin.vcp.security.CurrentUser;
import com.maliavin.vcp.service.CommonService;

/**
 * Common controller for all users operations.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@RestController
public class CommonController {

    private static final int ELEMENT_PER_PAGE = 9;

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/video/all", method = RequestMethod.GET)
    public @ResponseBody Page<Video> listAllVideos(@PageableDefault(size = ELEMENT_PER_PAGE) Pageable pageable) {
        Page<Video> videos = commonService.listAllVideos(pageable);
        return videos;
    }

    @RequestMapping(value = "/send-mail", method = RequestMethod.POST)
    public void sendEmail(@RequestParam("username") final String username) {
        commonService.sendMail(username);
    }
    
    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public ResponseEntity<String> changePassword(@AuthenticationPrincipal CurrentUser currentUser, 
            @RequestBody ChangePasswordForm changePasswordForm) {
        return commonService.changePassword(changePasswordForm, currentUser);
    }

}
