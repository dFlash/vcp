package com.maliavin.vcp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maliavin.vcp.domain.Company;
import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.exception.CantProcessMediaContentException;
import com.maliavin.vcp.form.AvatarForm;
import com.maliavin.vcp.form.CompanyForm;
import com.maliavin.vcp.service.AdminService;
import com.maliavin.vcp.service.ImageService;

/**
 * Controller for administrator's operations.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final int ELEMENT_PER_PAGE = 5;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public @ResponseBody Page<User> getAccounts(@PageableDefault(size = ELEMENT_PER_PAGE) Pageable pageable) {
        Page<User> users = adminService.getAccounts(pageable);
        return users;
    }

    @RequestMapping(value = "/companies/all", method = RequestMethod.GET)
    public @ResponseBody CompanyForm getAllCompanies() {
        return new CompanyForm(adminService.listCompanies());
    }

    @RequestMapping(value = "/avatar", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> uploadAvatar(@ModelAttribute AvatarForm avatarForm) {
        String avatarUrl = null;
        try {
            avatarUrl = imageService.saveImageData(avatarForm.getFile().getBytes());
        } catch (CantProcessMediaContentException e) {
            throw new ApplicationContextException("Error in upload avatar");
        } catch (IOException e) {
            throw new ApplicationContextException("Error in upload avatar");
        }
        Map<String, String> map = new HashMap<>();
        map.put("avatarUrl", avatarUrl);
        return map;
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public void addAccount(@RequestBody User user) {
        if (user == null) {
            throw new ApplicationContextException("Error in adding user - user is empty");
        }
        adminService.addUser(user);
    }

    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.PUT)
    public void updateAccount(@PathVariable("id") String id, @RequestBody User user){
        User currentUser = adminService.gerUser(id);
        if (currentUser == null){
            throw new ApplicationContextException("User does not exist");
        }
        currentUser.setAvatar(user.getAvatar());
        currentUser.setCompany(user.getCompany());
        currentUser.setEmail(user.getEmail());
        currentUser.setLogin(user.getLogin());
        currentUser.setName(user.getName());
        currentUser.setRole(user.getRole());
        currentUser.setSurname(user.getSurname());
        adminService.saveUser(currentUser);
    }

    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.DELETE)
    public void deleteVideo(@PathVariable("id") String id) {
        adminService.deleteUser(id);
    }

    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public @ResponseBody Page<Company> getCompanies(@PageableDefault(size = ELEMENT_PER_PAGE) Pageable pageable) {
        Page<Company> companies = adminService.listCompanies(pageable);
        return companies;
    }

    @RequestMapping(value = "/companies", method = RequestMethod.POST)
    public void addCompany(@RequestBody Company company) {
        if (company == null) {
            throw new ApplicationContextException("Error in adding company - company is empty");
        }
        adminService.addCompany(company);
    }

    @RequestMapping(value = "/companies/{id}", method = RequestMethod.DELETE)
    public void deleteCompany(@PathVariable("id") String id) {
        adminService.deleteCompany(id);
    }
    
    @RequestMapping(value = "/companies/{id}", method = RequestMethod.PUT)
    public void updateCompany(@PathVariable("id") String id, @RequestBody Company company){
        Company currentCompany = adminService.getCompany(id);
        if (currentCompany == null) {
            throw new ApplicationContextException("Error in adding company - company is empty");
        }
        currentCompany.setAddress(company.getAddress());
        currentCompany.setContactEmail(company.getContactEmail());
        currentCompany.setName(company.getName());
        currentCompany.setPhone(company.getPhone());
        adminService.saveCompany(currentCompany);
        
    }

}
