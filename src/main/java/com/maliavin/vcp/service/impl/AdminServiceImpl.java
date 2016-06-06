package com.maliavin.vcp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.maliavin.vcp.form.StatisticRow;
import com.maliavin.vcp.form.StatisticsForm;
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
        Page<User> users = userRepository.findAll(pageable);
        for (User user : users){
            user.setPassword("");
        }
        return users;
    }

    @Override
    public Iterable<Company> listCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        if (user == null){
            throw new ApplicationContextException("User is empty");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setAvatar(user.getAvatar());
        user.setCompany(user.getCompany());
        user.setEmail(user.getEmail());
        user.setLogin(user.getLogin());
        user.setName(user.getName());
        user.setRole(user.getRole());
        user.setSurname(user.getSurname());
        userRepository.save(user);
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
    public void deleteCompany(String id) {
        List<User> removedUsers = userRepository.removeByUserCompanyId(id);
        for (User user : removedUsers){
            videoRepository.deleteByOwnerId(user.getId());
        }
        companyRepository.delete(id);
    }

    @Override
    public void saveCompany(Company company) {
        if (company == null) {
            throw new ApplicationContextException("Error in saving company - company is empty");
        }
        company.setAddress(company.getAddress());
        company.setContactEmail(company.getContactEmail());
        company.setName(company.getName());
        company.setPhone(company.getPhone());
        companyRepository.save(company);
    }

    @Override
    public Map<String, String> uploadAvatar(AvatarForm avatarForm) {
        String avatarUrl = avatarService.generateAvatarUrl(avatarForm.getEmail());
        Map<String, String> map = new HashMap<>();
        map.put("avatarUrl", avatarUrl);
        return map;
    }

    @Override
    public StatisticsForm statistics() {
        List<Statistics> statistics = statisticsService.list();
        List<StatisticRow> rowForms = statistics.stream().map(stat -> {
            StatisticRow row = new StatisticRow();
            row.setAddresses(stat.getAddresses().size());
            row.setUsers(stat.getUserName().size());
            row.setVideoName(stat.getVideoName());
            row.setViewCount(stat.getViewsCount());
            return row;}).collect(Collectors.toList());
        StatisticsForm statisticsForm = new StatisticsForm();
        statisticsForm.setContent(rowForms);
        return statisticsForm;
    }

}
