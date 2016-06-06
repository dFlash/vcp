package com.maliavin.vcp.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.maliavin.vcp.service.AvatarService;

@Service
public class GravatarService implements AvatarService {

    private static final String DEFAULT_EXTENSION = ".jpg";

    @Value("${gravatar.url}")
    private String gravatarUrl;

    @Value("${avatar.size}")
    private String avatarSizeString;

    @Value("${avatar.default}")
    private String avatarDefault;

    @Override
    public String generateAvatarUrl(String email) {
        email = email.trim().toLowerCase();
        final String hash = md5Hex(email);
        String url = createUrl(hash);
        return url;
    }

    private String md5Hex(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex(md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    private String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    private String createUrl(final String hash) {
        String url = gravatarUrl + 
                     hash + DEFAULT_EXTENSION +
                     "?size=" + Integer.parseInt(avatarSizeString) +
                     "&d=" + avatarDefault;
        return url;
    }

}
