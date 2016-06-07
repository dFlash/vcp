package com.maliavin.vcp.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.maliavin.vcp.domain.Company;
import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.exception.ApplicationException;
import com.maliavin.vcp.form.ChangePasswordForm;
import com.maliavin.vcp.repository.storage.UserRepository;
import com.maliavin.vcp.service.CommonService;
import com.maliavin.vcp.service.impl.CommonServiceImpl;

public class CommonServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CommonService commonService = new CommonServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findUserTest() {
        User expectedUser = new User("name", "surname", "login", "email", new Company(), "role", "password");
        String id = "id";
        String hash = "hash";
        Mockito.when(userRepository.findByIdAndHash(id, hash)).thenReturn(expectedUser);

        User actualUser = commonService.findUser(id, hash);

        Assert.assertEquals(expectedUser, actualUser);
    }

    @Test(expected=ApplicationException.class)
    public void changePasswordEmptyTest() {
        User u = new User();
        String newPassword = "";
        String repeatPassword = "";
        String id = "id";

        ChangePasswordForm changePasswordForm = new ChangePasswordForm(newPassword, repeatPassword, id);
        Mockito.when(userRepository.findOne(id)).thenReturn(u);
        Mockito.when(userRepository.save(u)).thenReturn(null);

        commonService.changePassword(changePasswordForm);
    }

    @Test(expected=ApplicationException.class)
    public void changePasswordDifferentTest() {
        User u = new User();
        String newPassword = "newPassword";
        String repeatPassword = "repeatPassword";
        String id = "id";

        ChangePasswordForm changePasswordForm = new ChangePasswordForm(newPassword, repeatPassword, id);
        Mockito.when(userRepository.findOne(id)).thenReturn(u);
        Mockito.when(userRepository.save(u)).thenReturn(null);

        commonService.changePassword(changePasswordForm);
    }

    @Test
    public void changePasswordStatusOKTest() {
        User u = new User();
        String newPassword = "newPassword";
        String repeatPassword = "newPassword";
        String id = "id";

        ChangePasswordForm changePasswordForm = new ChangePasswordForm(newPassword, repeatPassword, id);
        Mockito.when(userRepository.findOne(id)).thenReturn(u);
        Mockito.when(userRepository.save(u)).thenReturn(u);

        ResponseEntity<String> expected = new ResponseEntity<>(HttpStatus.OK);

        ResponseEntity<String> actual = commonService.changePassword(changePasswordForm);

        Assert.assertEquals(expected, actual);
    }

}
