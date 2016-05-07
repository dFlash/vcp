package com.maliavin.vcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    private static final int ELEMENT_PER_PAGE = 3;

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public @ResponseBody Page<User> getAccounts(@PageableDefault(size = ELEMENT_PER_PAGE) Pageable pageable) {
        Page<User> users = adminService.getAccounts(pageable);
        return users;
    }

}
