package com.maliavin.vcp.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.maliavin.vcp.domain.Company;
import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.form.AvatarForm;
import com.maliavin.vcp.repository.storage.CompanyRepository;
import com.maliavin.vcp.repository.storage.UserRepository;
import com.maliavin.vcp.service.AdminService;
import com.maliavin.vcp.service.AvatarService;
import com.maliavin.vcp.service.StatisticsService;
import com.maliavin.vcp.service.impl.AdminServiceImpl;

public class AdminServiceTest {

    @Mock
    private AvatarService avatarService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private StatisticsService statisticsService;

    @InjectMocks
    private AdminService adminService = new AdminServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void listCompaniesTest() {
        List<Company> returnList = new ArrayList<>();
        returnList.add(new Company("test1", "test1", "test1", "test1"));
        returnList.add(new Company("test2", "test2", "test2", "test2"));
        Mockito.when(companyRepository.findAll()).thenReturn(returnList);

        Iterable<Company> actualList = adminService.listCompanies();

        Iterable<Company> expectedList = new ArrayList<>(returnList);

        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void listCompaniesPageableTest() {
        Pageable pageable = new PageRequest(1, 1);
        List<Company> returnList = new ArrayList<>();
        returnList.add(new Company("test1", "test1", "test1", "test1"));
        returnList.add(new Company("test2", "test2", "test2", "test2"));
        Page<Company> returnPage = new PageImpl<>(returnList);
        Mockito.when(companyRepository.findAll(pageable)).thenReturn(returnPage);

        Page<Company> actuaPage = adminService.listCompanies(pageable);

        Page<Company> expectedPage = new PageImpl<>(returnList);

        Assert.assertEquals(expectedPage, actuaPage);
    }

    @Test
    public void getAccountsTest() {
        Pageable pageable = new PageRequest(1, 1);
        List<User> returnList = new ArrayList<>();
        returnList.add(new User());
        returnList.add(new User());
        Page<User> returnPage = new PageImpl<>(returnList);
        Mockito.when(userRepository.findAll(pageable)).thenReturn(returnPage);

        Page<User> actuaPage = adminService.getAccounts(pageable);

        Page<User> expectedPage = new PageImpl<>(returnList);

        Assert.assertEquals(expectedPage, actuaPage);
    }

    @Test
    public void uploadAvatarTest() {
        String expectedUrl = "testUrl";
        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("avatarUrl", expectedUrl);

        String email = "email";
        Mockito.when(avatarService.generateAvatarUrl(email)).thenReturn(expectedUrl);

        AvatarForm avatarForm = new AvatarForm(email);

        Map<String, String> actualMap = adminService.uploadAvatar(avatarForm);

        Assert.assertEquals(expectedMap, actualMap);
    }

}
