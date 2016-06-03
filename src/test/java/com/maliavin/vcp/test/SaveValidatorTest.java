package com.maliavin.vcp.test;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.maliavin.vcp.component.SaveValidator;
import com.maliavin.vcp.domain.Company;
import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.exception.SavingException;
import com.maliavin.vcp.repository.storage.CompanyRepository;
import com.maliavin.vcp.repository.storage.UserRepository;

public class SaveValidatorTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SaveValidator saveValidator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = SavingException.class)
    public void validateAddCompanyTest() {
        String name = "name";
        String email = "contactEmail1";
        String phone = "phone1";
        Company inputCompany = new Company(name, "address", email, phone);
        Object[] objects = new Object[1];
        objects[0] = inputCompany;
        JoinPoint jp = Mockito.mock(JoinPoint.class);
        Mockito.when(jp.getArgs()).thenReturn(objects);

        List<Company> companies = new ArrayList<>();
        Company dbCompany = new Company(name, "address", "contactEmail2", "phone2");
        dbCompany.setId("id");
        companies.add(dbCompany);
        Mockito.when(companyRepository.findByNameOrContactEmailOrPhone(name, email, phone)).thenReturn(companies);

        saveValidator.validateAddCompany(jp);
    }

    @Test(expected = SavingException.class)
    public void validateSaveCompanyTest() {
        String name = "name";
        String email = "contactEmail1";
        String phone = "phone1";
        Company inputCompany = new Company(name, "address", email, phone);
        inputCompany.setId("id1");
        Object[] objects = new Object[2];
        objects[1] = inputCompany;
        JoinPoint jp = Mockito.mock(JoinPoint.class);
        Mockito.when(jp.getArgs()).thenReturn(objects);

        List<Company> companies = new ArrayList<>();
        Company dbCompany = new Company(name, "address", "contactEmail2", "phone2");
        dbCompany.setId("id2");
        companies.add(dbCompany);
        Mockito.when(companyRepository.findByNameOrContactEmailOrPhone(name, email, phone)).thenReturn(companies);

        saveValidator.validateSaveCompany(jp);
    }

    @Test(expected = SavingException.class)
    public void validateAddUserTest() {
        String login = "login";
        String email = "email";
        User inputUser = new User();
        inputUser.setLogin(login);
        inputUser.setEmail(email);

        Object[] objects = new Object[1];
        objects[0] = inputUser;
        JoinPoint jp = Mockito.mock(JoinPoint.class);
        Mockito.when(jp.getArgs()).thenReturn(objects);

        List<User> users = new ArrayList<>();
        User dbUser = new User();
        dbUser.setLogin("testLogin");
        dbUser.setEmail("testEmail");
        users.add(dbUser);
        Mockito.when(userRepository.findByLoginOrEmail(login, email)).thenReturn(users);

        saveValidator.validateAddUser(jp);
    }

    @Test(expected = SavingException.class)
    public void validateSaveUserTest() {
        String login = "login";
        String email = "email";
        String id1 = "id1";
        User inputUser = new User();
        inputUser.setLogin(login);
        inputUser.setEmail(email);
        inputUser.setId(id1);

        Object[] objects = new Object[2];
        objects[1] = inputUser;
        JoinPoint jp = Mockito.mock(JoinPoint.class);
        Mockito.when(jp.getArgs()).thenReturn(objects);

        List<User> users = new ArrayList<>();
        String id2 = "id2";
        User dbUser = new User();
        dbUser.setLogin("testLogin");
        dbUser.setEmail("testEmail");
        dbUser.setId(id2);
        users.add(dbUser);
        Mockito.when(userRepository.findByLoginOrEmail(login, email)).thenReturn(users);

        saveValidator.validateSaveUser(jp);
    }

}
