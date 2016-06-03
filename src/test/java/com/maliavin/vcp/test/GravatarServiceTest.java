package com.maliavin.vcp.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.maliavin.vcp.service.AvatarService;
import com.maliavin.vcp.service.impl.GravatarService;

public class GravatarServiceTest {

    private AvatarService avatarService = new GravatarService();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void generateAvatarUrlTest() {
        String email = "email";
        String gravatarUrl = "url";
        String avatarSizeString = "5";
        String avatarDefault = "avatarDefault";
        ReflectionTestUtils.setField(avatarService, "gravatarUrl", gravatarUrl);
        ReflectionTestUtils.setField(avatarService, "avatarSizeString", avatarSizeString);
        ReflectionTestUtils.setField(avatarService, "avatarDefault", avatarDefault);
        String avatarUrl = avatarService.generateAvatarUrl(email);

        String expectedStartsWith = "url";
        String expectedEndsWith = ".jpg?size=5&d=avatarDefault";

        Assert.assertTrue(avatarUrl.startsWith(expectedStartsWith));
        Assert.assertTrue(avatarUrl.endsWith(expectedEndsWith));
    }

}
