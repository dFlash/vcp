package com.maliavin.vcp.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.maliavin.vcp.domain.Company;
import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.repository.storage.UserRepository;
import com.maliavin.vcp.security.CurrentUser;
import com.maliavin.vcp.service.impl.AuthentificationService;

public class AuthentificationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    UserDetailsService userDetailsService = new AuthentificationService();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameNullUserTest() {
        String username = "name";
        Mockito.when(userRepository.findByLogin(username)).thenReturn(null);
        userDetailsService.loadUserByUsername(username);
    }

    @Test
    public void loadUserByUsernameNonNullUserTest() {
        String username = "name";
        User u = new User(username, "surname", "login", "email", new Company(), "role", "password");
        Mockito.when(userRepository.findByLogin(username)).thenReturn(u);
        CurrentUser actualCurrentUser = (CurrentUser) userDetailsService.loadUserByUsername(username);

        CurrentUser expectedCurrentUser = new CurrentUser(u);

        Assert.assertEquals(expectedCurrentUser, actualCurrentUser);
    }

}
