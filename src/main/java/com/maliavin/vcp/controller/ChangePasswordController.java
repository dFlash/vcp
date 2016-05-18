package com.maliavin.vcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.service.CommonService;

@Controller
public class ChangePasswordController {

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String authenticateUser(@RequestParam("id") String userId, @RequestParam("hash") String userHash) {
        User user = commonService.findUser(userId, userHash);
        if (user == null)
        {
            throw new ApplicationContextException("User is not authenticated");
        }
        return "changePassword";
    }

}
