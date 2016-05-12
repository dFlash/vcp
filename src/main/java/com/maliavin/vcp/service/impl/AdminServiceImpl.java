package com.maliavin.vcp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.domain.Company;
import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.repository.storage.CompanyRepository;
import com.maliavin.vcp.repository.storage.UserRepository;
import com.maliavin.vcp.repository.storage.VideoRepository;
import com.maliavin.vcp.service.AdminService;

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
    public void saveUser(User user) {
        userRepository.save(user);
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
    public void saveCompany(Company company) {
        companyRepository.save(company);
    }

}
