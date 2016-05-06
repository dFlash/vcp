package com.maliavin.vcp.security;

import javax.annotation.Nullable;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.maliavin.vcp.domain.User;

/**
 * Util class for security operations.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class SecurityUtils {

    public static @Nullable User getCurrentUser() {
        /*
         * Company c = new Company("test", "test", "test", "321");
         * c.setId("571cef81a409f6008f6165e6"); User u = new User("Tim",
         * "Roberts", "tim", "tim@gmail.com", c,
         * "https://s3.amazonaws.com/uifaces/faces/twitter/mantia/128.jpg",
         * "User"); u.setId("571cef81a409f6008f6165e8"); return u;
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof CurrentUser) {
            return ((CurrentUser) principal).getUser();
        } else {
            return null;
        }
    }
}
