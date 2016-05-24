package com.maliavin.vcp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.domain.Company;
import com.maliavin.vcp.domain.Statistics;
import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.form.AvatarForm;
import com.maliavin.vcp.repository.storage.CompanyRepository;
import com.maliavin.vcp.repository.storage.UserRepository;
import com.maliavin.vcp.repository.storage.VideoRepository;
import com.maliavin.vcp.service.AdminService;
import com.maliavin.vcp.service.AvatarService;
import com.maliavin.vcp.service.StatisticsService;

/**
 * Service for administrators.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private StatisticsService statisticsService;

    @Override
    public Page<User> getAccounts(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Iterable<Company> listCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public void addUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public void saveUser(String id, User user) {
        User currentUser = gerUser(id);
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
        userRepository.save(currentUser);
    }

    @Override
    public User gerUser(String userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public void deleteUser(String id) {
        videoRepository.deleteByOwnerId(id);
        userRepository.delete(id);
    }

    @Override
    public Page<Company> listCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    @Override
    public void addCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public void deleteCompany(String id) {
        List<User> removedUsers = userRepository.removeByUserCompanyId(id);
        for (User user : removedUsers){
            videoRepository.deleteByOwnerId(user.getId());
        }
        companyRepository.delete(id);
    }

    @Override
    public Company getCompany(String id) {
        return companyRepository.findOne(id);
    }

    @Override
    public void saveCompany(String id, Company company) {
        Company currentCompany = getCompany(id);
        if (currentCompany == null) {
            throw new ApplicationContextException("Error in saving company - company is empty");
        }
        currentCompany.setAddress(company.getAddress());
        currentCompany.setContactEmail(company.getContactEmail());
        currentCompany.setName(company.getName());
        currentCompany.setPhone(company.getPhone());
        companyRepository.save(currentCompany);
    }

    @Override
    public Map<String, String> uploadAvatar(AvatarForm avatarForm) {
        String avatarUrl = avatarService.generateAvatarUrl(avatarForm.getEmail());
        Map<String, String> map = new HashMap<>();
        map.put("avatarUrl", avatarUrl);
        return map;
    }

    @Override
    public List<Statistics> statistics() {
        List<Statistics> statistics = statisticsService.list();
        return statistics;
    }

}
